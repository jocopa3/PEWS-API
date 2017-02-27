/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import mcpews.message.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import mcpews.event.EventMeasurements;
import mcpews.event.EventProperties;
import mcpews.event.EventType;
import mcpews.message.MCMessage;

/**
 *
 * @author Jocopa3
 */
public class EventSerializer implements JsonSerializer<MCEvent>, JsonDeserializer<MCEvent> {

    @Override
    public JsonElement serialize(MCEvent event, Type type, JsonSerializationContext jsc) {
        if(event == null) {
            return null;
        }
        
        JsonObject messageObj = new JsonObject();
        
        // Get the class representing the body object from the purpose enum
        Class measurementsClassType = event.getEventType().getMeasurementsClassType();
        Class propertiesClassType = event.getEventType().getPropertiesClassType();
        
        messageObj.add("properties", jsc.serialize(event.getProperties(), propertiesClassType));
        messageObj.add("measurements", jsc.serialize(event.getMeasurements(), measurementsClassType));
        messageObj.add("eventName", jsc.serialize(event.getEventType().getName()));
        
        return messageObj;
    }
    
    @Override
    public MCEvent deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        EventType eventType = EventType.fromString(je.getAsJsonObject().get("eventName").getAsString());
        
        EventProperties properties = jdc.deserialize(je.getAsJsonObject().get("properties"), eventType.getPropertiesClassType());
        EventMeasurements measurements = jdc.deserialize(je.getAsJsonObject().get("measurements"), eventType.getMeasurementsClassType());
        
        Constructor<?> ctor;
        MCEvent event;
        try {
            ctor = eventType.getEventClassType().getConstructor(EventType.class, EventProperties.class, EventMeasurements.class);
            event = (MCEvent)eventType.getEventClassType().cast(ctor.newInstance(new Object[] { eventType, properties, measurements }));
        } catch (Exception ex) {
            //ex.printStackTrace();
            event = new MCEvent(eventType, properties, measurements);
        }
        
        return event;
    }

}
