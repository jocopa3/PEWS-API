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
import java.lang.reflect.Type;

/**
 *
 * @author Jocopa3
 */
public class MessageSerializer implements JsonSerializer<MCMessage>, JsonDeserializer<MCMessage> {

    @Override
    public JsonElement serialize(MCMessage message, Type type, JsonSerializationContext jsc) {
        if (message == null) {
            return null;
        }

        JsonObject messageObj = new JsonObject();

        // Get the class representing the body object from the purpose enum
        Class bodyClassType = message.getPurpose().getBodyClass();
        messageObj.add("body", jsc.serialize(message.getBody(), bodyClassType));
        messageObj.add("header", jsc.serialize(message.getHeader(), MCHeader.class));

        return messageObj;
    }

    @Override
    public MCMessage deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        MCHeader header = jdc.deserialize(je.getAsJsonObject().get("header"), MCHeader.class);
        
        Class bodyClassType = header.getPurpose().getBodyClass();
        MCBody body = jdc.deserialize(je.getAsJsonObject().get("body"), bodyClassType);
        
        return new MCMessage(body, header);
    }

}
