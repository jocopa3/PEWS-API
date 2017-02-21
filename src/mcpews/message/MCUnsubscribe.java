/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import mcpews.event.EventType;

/**
 *
 * @author Jocopa3
 */
public class MCUnsubscribe extends MCBody {

    private String eventName;
    
    public MCUnsubscribe(EventType type) {
        super(MessagePurposeType.UNSUBSCRIBE);
        
        eventName = type.getName();
    }
    
    public EventType getEventType() {
        return EventType.fromString(eventName);
    }
}
