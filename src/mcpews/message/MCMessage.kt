package mcpews.message

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.util.UUID

open class MCMessage {

    private var body: MCBody? = null
    private var header: MCHeader? = null

    protected constructor()

    constructor(body: MCBody?, header: MCHeader?) {
        this.body = body
        this.header = header
    }

    constructor(body: MCBody) {
        val purpose = body.purposeForMessage()!!
        when (purpose) {
            MessagePurposeType.COMMAND_REQUEST,
            MessagePurposeType.SUBSCRIBE,
            MessagePurposeType.UNSUBSCRIBE ->
                header = MCHeader(1, UUID.randomUUID(), purpose, MessagePurposeType.COMMAND_REQUEST)

            else ->
                header = MCHeader(1, UUID(0L, 0L), purpose)
        }
        this.body = body
    }

    open fun getPurpose(): MessagePurposeType? {
        return header!!.getPurpose()
    }

    open fun getType(): MessagePurposeType {
        return header!!.getType() ?: MessagePurposeType.COMMAND_RESPONSE
    }

    open fun getBody(): MCBody? {
        return body
    }

    open fun getHeader(): MCHeader? {
        return header
    }

    override fun toString(): String {
        return body!!.toString()
    }

    open fun getMessageText(): String {
        return gson.toJson(this)
    }

    companion object {
        @JvmField
        protected val gson: Gson = GsonBuilder()
            .registerTypeAdapter(MCEvent::class.java, EventSerializer())
            .registerTypeAdapter(MCMessage::class.java, MessageSerializer())
            .registerTypeAdapter(MCResponse::class.java, ResponseSerializer())
            .create()

        @JvmStatic
        fun getAsMessage(messageJSON: String?): MCMessage? {
            return gson.fromJson(messageJSON, MCMessage::class.java)
        }

        @JvmStatic
        fun getAsMessage(JSON: JsonObject?): MCMessage? {
            return gson.fromJson(JSON, MCMessage::class.java)
        }
    }
}
