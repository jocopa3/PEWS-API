/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.event;

import mcpews.message.MCEvent;

/**
 *
 * @author Jocopa3
 */
public class PlayerMessageEvent extends MCEvent {

    public PlayerMessageEvent(EventType type, EventProperties properties, EventMeasurements measurements) {
        super(type, properties, measurements);
    }

    protected class PlayerMessageProperties extends EventProperties {
        protected String Message;
        protected String Sender;
    }
    
    public String getSender() {
        return ((PlayerMessageProperties) properties).Sender;
    }

    public String getMessage() {
        return ((PlayerMessageProperties) properties).Message;
    }

    @Override
    public String toString() {
        return getSender() + ": " + getMessage();
    }
}
