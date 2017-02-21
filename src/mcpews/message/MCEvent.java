/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import mcpews.event.*;

/**
 *
 * @author Jocopa3
 */
public class MCEvent extends MCBody {
    
    private final String eventName;
    protected EventProperties properties;
    protected EventMeasurements measurements;
    
    protected MCEvent(EventType type, EventProperties properties, EventMeasurements measurements) {
        super(MessagePurposeType.EVENT);
        
        eventName = type.getName();
        this.properties = properties;
        this.measurements = measurements;
    }
    
    public EventType getEventType() {
        return EventType.fromString(eventName);
    }
    
    public EventProperties getProperties() {
        return properties;
    }
    
    public EventMeasurements getMeasurements() {
        return measurements;
    }
    
    @Override
    public String toString() {
        return "";
    }
}
