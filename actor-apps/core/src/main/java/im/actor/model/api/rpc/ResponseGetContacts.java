package im.actor.model.api.rpc;
/*
 *  Generated by the Actor API Scheme generator.  DO NOT EDIT!
 */

import im.actor.model.droidkit.bser.Bser;
import im.actor.model.droidkit.bser.BserValues;
import im.actor.model.droidkit.bser.BserWriter;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import im.actor.model.network.parser.*;
import java.util.List;
import java.util.ArrayList;
import im.actor.model.api.*;

public class ResponseGetContacts extends Response {

    public static final int HEADER = 0x58;
    public static ResponseGetContacts fromBytes(byte[] data) throws IOException {
        return Bser.parse(new ResponseGetContacts(), data);
    }

    private List<User> users;
    private boolean isNotChanged;

    public ResponseGetContacts(@NotNull List<User> users, boolean isNotChanged) {
        this.users = users;
        this.isNotChanged = isNotChanged;
    }

    public ResponseGetContacts() {

    }

    @NotNull
    public List<User> getUsers() {
        return this.users;
    }

    public boolean isNotChanged() {
        return this.isNotChanged;
    }

    @Override
    public void parse(BserValues values) throws IOException {
        List<User> _users = new ArrayList<User>();
        for (int i = 0; i < values.getRepeatedCount(1); i ++) {
            _users.add(new User());
        }
        this.users = values.getRepeatedObj(1, _users);
        this.isNotChanged = values.getBool(2);
    }

    @Override
    public void serialize(BserWriter writer) throws IOException {
        writer.writeRepeatedObj(1, this.users);
        writer.writeBool(2, this.isNotChanged);
    }

    @Override
    public String toString() {
        String res = "tuple GetContacts{";
        res += "}";
        return res;
    }

    @Override
    public int getHeaderKey() {
        return HEADER;
    }
}
