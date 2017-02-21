/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.message;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import mcpews.command.CommandType;
import mcpews.event.EventMeasurements;
import mcpews.event.EventProperties;
import mcpews.event.EventType;
import mcpews.message.MCMessage;

/**
 *
 * @author Jocopa3
 */
public class ResponseSerializer implements JsonDeserializer<MCResponse> {

    public static final String PROPERTY_HINT_COMMAND = "COMMANDNAME";
    public static final String PROPERTY_HINT_OVERLOAD = "OVERLOAD";
    
    @Override
    public MCResponse deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        String responseName = je.getAsJsonObject().remove(PROPERTY_HINT_COMMAND).getAsString();
        String responseOverload = je.getAsJsonObject().remove(PROPERTY_HINT_OVERLOAD).getAsString();
        
        CommandType commandType = CommandType.fromString(responseName);
        
        MCResponse response = jdc.deserialize(je, commandType.getResponseClass());
        response.setCommandType(commandType);
        
        return response;
    }

}
