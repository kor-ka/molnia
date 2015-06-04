//
//  Generated by the J2ObjC translator.  DO NOT EDIT!
//  source: /Users/ex3ndr/Develop/actor-model/library/actor-cocoa-base/build/java/im/actor/model/api/rpc/RequestInviteUser.java
//

#ifndef _APRequestInviteUser_H_
#define _APRequestInviteUser_H_

#include "J2ObjC_header.h"
#include "im/actor/model/network/parser/Request.h"

@class APGroupOutPeer;
@class APUserOutPeer;
@class BSBserValues;
@class BSBserWriter;
@class IOSByteArray;

#define APRequestInviteUser_HEADER 69

@interface APRequestInviteUser : APRequest

#pragma mark Public

- (instancetype)init;

- (instancetype)initWithAPGroupOutPeer:(APGroupOutPeer *)groupPeer
                              withLong:(jlong)rid
                     withAPUserOutPeer:(APUserOutPeer *)user;

+ (APRequestInviteUser *)fromBytesWithByteArray:(IOSByteArray *)data;

- (APGroupOutPeer *)getGroupPeer;

- (jint)getHeaderKey;

- (jlong)getRid;

- (APUserOutPeer *)getUser;

- (void)parseWithBSBserValues:(BSBserValues *)values;

- (void)serializeWithBSBserWriter:(BSBserWriter *)writer;

- (NSString *)description;

@end

J2OBJC_EMPTY_STATIC_INIT(APRequestInviteUser)

J2OBJC_STATIC_FIELD_GETTER(APRequestInviteUser, HEADER, jint)

FOUNDATION_EXPORT APRequestInviteUser *APRequestInviteUser_fromBytesWithByteArray_(IOSByteArray *data);

FOUNDATION_EXPORT void APRequestInviteUser_initWithAPGroupOutPeer_withLong_withAPUserOutPeer_(APRequestInviteUser *self, APGroupOutPeer *groupPeer, jlong rid, APUserOutPeer *user);

FOUNDATION_EXPORT APRequestInviteUser *new_APRequestInviteUser_initWithAPGroupOutPeer_withLong_withAPUserOutPeer_(APGroupOutPeer *groupPeer, jlong rid, APUserOutPeer *user) NS_RETURNS_RETAINED;

FOUNDATION_EXPORT void APRequestInviteUser_init(APRequestInviteUser *self);

FOUNDATION_EXPORT APRequestInviteUser *new_APRequestInviteUser_init() NS_RETURNS_RETAINED;

J2OBJC_TYPE_LITERAL_HEADER(APRequestInviteUser)

typedef APRequestInviteUser ImActorModelApiRpcRequestInviteUser;

#endif // _APRequestInviteUser_H_
