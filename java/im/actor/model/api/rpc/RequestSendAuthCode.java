package im.actor.model.api.rpc;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.model.droidkit.bser.Bser;
import im.actor.model.droidkit.bser.BserParser;
import im.actor.model.droidkit.bser.BserObject;
import im.actor.model.droidkit.bser.BserValues;
import im.actor.model.droidkit.bser.BserWriter;
import im.actor.model.droidkit.bser.DataInput;
import im.actor.model.droidkit.bser.DataOutput;
import im.actor.model.droidkit.bser.util.SparseArray;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import com.google.j2objc.annotations.ObjectiveCName;
import static im.actor.model.droidkit.bser.Utils.*;
import java.io.IOException;
import im.actor.model.network.parser.*;
import java.util.List;
import java.util.ArrayList;
import im.actor.model.api.*;

public class RequestSendAuthCode extends Request<ResponseSendAuthCode> {

    public static final int HEADER = 0x1;
    public static RequestSendAuthCode fromBytes(byte[] data) throws IOException {
        return Bser.parse(new RequestSendAuthCode(), data);
    }

    private long phoneNumber;
    private int appId;
    private String apiKey;

    public RequestSendAuthCode(long phoneNumber, int appId, @NotNull String apiKey) {
        this.phoneNumber = phoneNumber;
        this.appId = appId;
        this.apiKey = apiKey;
    }

    public RequestSendAuthCode() {

    }

    public long getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getAppId() {
        return this.appId;
    }

    @NotNull
    public String getApiKey() {
        return this.apiKey;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        this.phoneNumber = values.getLong(1);
        this.appId = values.getInt(2);
        this.apiKey = values.getString(3);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeLong(1, this.phoneNumber);
        writer.writeInt(2, this.appId);
        if (this.apiKey == null) {
            throw new IOException();
        }
        writer.writeString(3, this.apiKey);
    }

    @Override
    public String toString() {
        String res = "rpc SendAuthCode{";
        res += "phoneNumber=" + this.phoneNumber;
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
