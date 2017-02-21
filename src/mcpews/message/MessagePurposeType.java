/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Matt
 */
public enum MessagePurposeType {
    COMMAND_RESPONSE("commandResponse", MCResponse.class),
    COMMAND_REQUEST("commandRequest", MCCommand.class),
    ERROR("error", MCError.class),
    EVENT("event", MCEvent.class),
    SUBSCRIBE("subscribe", MCSubscribe.class),
    UNSUBSCRIBE("unsubscribe", MCUnsubscribe.class);
    
    private final String purpose;
    private final Class classi; // With an I
    
    private static ConcurrentHashMap<String, MessagePurposeType> messagePurposeTypes = new ConcurrentHashMap();
    
    static {
        for(MessagePurposeType type : values()) {
            messagePurposeTypes.put(type.getPurposeName(), type);
        }
    }
    
    MessagePurposeType(String purpose, Class classi) {
        this.purpose = purpose;
        this.classi = classi;
    }
    
    public String getPurposeName() {
        return purpose;
    }
    
    public Class getBodyClass() {
        return classi;
    }
    
    public static MessagePurposeType fromString(String typeName) {
        if(typeName == null) {
            return null;
        }
        
        return messagePurposeTypes.get(typeName);
    }
}
