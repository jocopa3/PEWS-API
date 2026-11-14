package mcpews.message

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

open class MessageSerializer : JsonSerializer<MCMessage>, JsonDeserializer<MCMessage> {

    override fun serialize(
        message: MCMessage?,
        type: Type,
        jsc: JsonSerializationContext
    ): JsonElement? {
        if (message == null) {
            return null
        }

        val messageObj = JsonObject()

        // Get the class representing the body object from the purpose enum
        val bodyClassType = message.getPurpose()!!.getBodyClass()
        messageObj.add("body", jsc.serialize(message.getBody(), bodyClassType))
        messageObj.add("header", jsc.serialize(message.getHeader(), MCHeader::class.java))

        return messageObj
    }

    override fun deserialize(
        je: JsonElement,
        type: Type,
        jdc: JsonDeserializationContext
    ): MCMessage {
        val messageObj = je.getAsJsonObject()
        val header: MCHeader? = jdc.deserialize(messageObj.get("header"), MCHeader::class.java)

        val bodyClassType = header!!.getPurpose()!!.getBodyClass()
        val body: MCBody? = jdc.deserialize(messageObj.get("body"), bodyClassType)

        return MCMessage(body, header)
    }
}
