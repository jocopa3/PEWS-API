/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.event;

import java.util.HashMap;

/**
 *
 * @author Jocopa3
 */
public enum EventType {
    // Event classes must not be null!
    
    HARDWARE_INFO("HardwareInfo", null, EventProperties.class, EventMeasurements.class),
    HEARTBEAT("ScreenHeartbeat", null, EventProperties.class, EventMeasurements.class),
    PERFORMANCE_METRICS("performanceMetrics", PerformanceMetricsEvent.class, EventProperties.class, EventMeasurements.class),
    PLAYER_JOIN("PlayerJoin", null, EventProperties.class, EventMeasurements.class),
    PLAYER_MESSAGE("PlayerMessage", PlayerMessageEvent.class, PlayerMessageEvent.PlayerMessageEventProperties.class, EventMeasurements.class);
    
    private static HashMap<String, EventType> events = new HashMap<>();
    
    static {
        for(EventType event : values()) {
            events.put(event.getName(), event);
        }
    }
    
    private String eventName;
    private Class eventClass;
    private Class propertiesClass;
    private Class measurementsClass;
    
    EventType(String name, Class eventClass, Class propertiesClass, Class measurementsClass) {
        eventName = name;
        this.eventClass = eventClass;
        this.propertiesClass = propertiesClass;
        this.measurementsClass = measurementsClass;
    }
    
    public String getName() {
        return eventName;
    }
    
    public Class getEventClassType() {
        return eventClass;
    }
    
    public Class getPropertiesClassType() {
        return propertiesClass;
    }
    
    public Class getMeasurementsClassType() {
        return measurementsClass;
    }
    
    public static EventType fromString(String name) {
        return events.get(name);
    }
}
