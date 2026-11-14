package mcpews.message

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import mcpews.command.CommandType

open class ResponseSerializer : JsonDeserializer<MCResponse> {

    override fun deserialize(
        je: JsonElement,
        type: Type?,
        jdc: JsonDeserializationContext?
    ): MCResponse {
        val responseObj = je.getAsJsonObject()
        val responseName = responseObj.remove(PROPERTY_HINT_COMMAND).getAsString()
        responseObj.remove(PROPERTY_HINT_OVERLOAD).getAsString()

        val commandType = CommandType.fromString(responseName)!!
        val context = jdc!!

        val response: MCResponse = context.deserialize(je, commandType.getResponseClass())
        response.assignCommandType(commandType)

        return response
    }

    companion object {
        const val PROPERTY_HINT_COMMAND: String = "COMMANDNAME"
        const val PROPERTY_HINT_OVERLOAD: String = "OVERLOAD"
    }
}
