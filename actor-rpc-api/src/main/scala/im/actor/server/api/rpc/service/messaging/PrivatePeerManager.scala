package im.actor.server.api.rpc.service.messaging

import scala.concurrent.{ Future, ExecutionContext }

import akka.actor.{ Status, ActorSystem, Props, ActorRef }
import akka.contrib.pattern.{ ClusterSharding, ShardRegion }
import akka.pattern.ask
import akka.pattern.pipe
import akka.util.Timeout
import org.joda.time.DateTime
import slick.driver.PostgresDriver.api._

import im.actor.api.PeersImplicits
import im.actor.api.rpc.messaging.{ Message ⇒ ApiMessage, _ }
import im.actor.api.rpc.peers.{ Peer, PeerType }
import im.actor.server.models
import im.actor.server.push.{ SeqUpdatesManager, SeqUpdatesManagerRegion }
import im.actor.server.social.{ SocialManager, SocialManagerRegion }
import im.actor.server.util.{ HistoryUtils, UserUtils }

case class PrivatePeerManagerRegion(ref: ActorRef)

object PrivatePeerManager {
  private sealed trait Message

  private case class Envelope(userId: Int, payload: Message)

  private case class SendMessage(senderUserId: Int, senderAuthId: Long, randomId: Long, date: DateTime, message: ApiMessage) extends Message

  private case class MessageReceived(receiverUserId: Int, date: Long, receivedDate: Long) extends Message

  private val idExtractor: ShardRegion.IdExtractor = {
    case env @ Envelope(userId, payload) ⇒ (userId.toString, env)
  }

  private val shardResolver: ShardRegion.ShardResolver = msg ⇒ msg match {
    case Envelope(userId, _) ⇒ (userId % 100).toString // TODO: configurable
  }

  private def startRegion(props: Option[Props])(implicit system: ActorSystem): PrivatePeerManagerRegion =
    PrivatePeerManagerRegion(ClusterSharding(system).start(
      typeName = "PrivatePeerManager",
      entryProps = props,
      idExtractor = idExtractor,
      shardResolver = shardResolver
    ))

  def startRegion()(
    implicit
    system:              ActorSystem,
    db:                  Database,
    seqUpdManagerRegion: SeqUpdatesManagerRegion,
    socialManagerRegion: SocialManagerRegion
  ): PrivatePeerManagerRegion =
    startRegion(Some(props))

  def startRegionProxy()(implicit system: ActorSystem): PrivatePeerManagerRegion =
    startRegion(None)

  def props(
    implicit
    db:                  Database,
    seqUpdManagerRegion: SeqUpdatesManagerRegion,
    socialManagerRegion: SocialManagerRegion
  ): Props =
    Props(classOf[PrivatePeerManager], db, seqUpdManagerRegion, socialManagerRegion)

  def sendMessage(userId: Int, senderUserId: Int, senderAuthId: Long, randomId: Long, date: DateTime, message: ApiMessage)(
    implicit
    peerManagerRegion: PrivatePeerManagerRegion,
    timeout:           Timeout,
    ec:                ExecutionContext
  ): Future[SeqUpdatesManager.SequenceState] = {
    (peerManagerRegion.ref ? Envelope(userId, SendMessage(senderUserId, senderAuthId, randomId, date, message))).mapTo[SeqUpdatesManager.SequenceState]
  }

  def messageReceived(userId: Int, receiverUserId: Int, date: Long, receivedDate: Long)(implicit peerManagerRegion: PrivatePeerManagerRegion): Unit = {
    peerManagerRegion.ref ! Envelope(userId, MessageReceived(receiverUserId, date, receivedDate))
  }
}

class PrivatePeerManager(
  implicit
  db:                  Database,
  seqUpdManagerRegion: SeqUpdatesManagerRegion,
  socialManagerRegion: SocialManagerRegion
) extends PeerManager with PeersImplicits {
  import PrivatePeerManager._

  import HistoryUtils._
  import SeqUpdatesManager._
  import SocialManager._
  import UserUtils._

  implicit private val ec: ExecutionContext = context.dispatcher

  def receive = {
    case Envelope(userId, SendMessage(senderUserId, senderAuthId, randomId, date, message)) ⇒
      val replyTo = sender()

      val peerUpdate = UpdateMessage(
        peer = privatePeerStruct(senderUserId),
        senderUserId = senderUserId,
        date = date.getMillis,
        randomId = randomId,
        message = message
      )

      val senderUpdate = UpdateMessage(
        peer = privatePeerStruct(userId),
        senderUserId = senderUserId,
        date = date.getMillis,
        randomId = randomId,
        message = message
      )

      val clientUpdate = UpdateMessageSent(privatePeerStruct(userId), randomId, date.getMillis)

      db.run(for {

        clientUser ← getUserUnsafe(senderUserId)
        pushText ← getPushText(message, clientUser, userId)

        _ ← broadcastUserUpdate(userId, peerUpdate, Some(pushText))

        _ ← notifyUserUpdate(senderUserId, senderAuthId, senderUpdate, None)
        seqstate ← persistAndPushUpdate(senderAuthId, clientUpdate, None)
      } yield {
        recordRelation(senderUserId, userId)
        db.run(writeHistoryMessage(models.Peer.privat(senderUserId), models.Peer.privat(userId), date, randomId, message.header, message.toByteArray))
        seqstate
      }) pipeTo replyTo onFailure {
        case e ⇒
          log.error(e, "Failed to send message")
          sender() ! Status.Failure(e)
      }
    case Envelope(userId, MessageReceived(receiverUserId, date, receivedDate)) ⇒
      val update = UpdateMessageReceived(Peer(PeerType.Private, receiverUserId), date, receivedDate)

      db.run(for {
        _ ← broadcastUserUpdate(userId, update, None)
      } yield {
        db.run(markMessagesReceived(models.Peer.privat(receiverUserId), models.Peer.privat(userId), new DateTime(date)))
      }) onFailure {
        case e ⇒
          log.error(e, "Failed to mark messages received")
      }
  }
}