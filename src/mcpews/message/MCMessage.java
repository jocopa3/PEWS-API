/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.UUID;
import mcpews.event.*;

/**
 *
 * @author Jocopa3
 */
public class MCMessage {

    protected static final Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder()
                .registerTypeAdapter(MCEvent.class, new EventSerializer())
                .registerTypeAdapter(MCMessage.class, new MessageSerializer())
                .registerTypeAdapter(MCResponse.class, new ResponseSerializer());

        gson = gb.create();
    }

    private MCBody body;
    private MCHeader header;

    protected MCMessage() {

    }

    public MCMessage(MCBody body, MCHeader header) {
        this.body = body;
        this.header = header;
    }

    public MCMessage(MCBody body) {
        MessagePurposeType purpose = body.getPurpose();
        switch (purpose) {
            case COMMAND_REQUEST:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                header = new MCHeader(1, UUID.randomUUID(), purpose, MessagePurposeType.COMMAND_REQUEST);
                break;
            default:
                header = new MCHeader(1, new UUID(0, 0), purpose);
        }
        this.body = body;
    }

    public static MCMessage getAsMessage(String messageJSON) {
        return gson.fromJson(messageJSON, MCMessage.class);
    }

    public static MCMessage getAsMessage(JsonObject JSON) {
        return gson.fromJson(JSON, MCMessage.class);
    }

    public String getMessageText() {
        return gson.toJson(this);
    }

    public MessagePurposeType getPurpose() {
        return header.getPurpose();
    }

    public MessagePurposeType getType() {
        if (header.getType() == null) {
            return MessagePurposeType.COMMAND_RESPONSE;
        }

        return header.getType();
    }

    public MCBody getBody() {
        return body;
    }

    public MCHeader getHeader() {
        return header;
    }
    
    @Override
    public String toString() {
        return body.toString();
    }
}
