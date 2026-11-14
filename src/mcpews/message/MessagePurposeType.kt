package mcpews.message

import java.util.concurrent.ConcurrentHashMap

enum class MessagePurposeType(
    private val purpose: String,
    private val classi: Class<*>
) {
    COMMAND_RESPONSE("commandResponse", MCResponse::class.java),
    COMMAND_REQUEST("commandRequest", MCCommand::class.java),
    ERROR("error", MCError::class.java),
    EVENT("event", MCEvent::class.java),
    SUBSCRIBE("subscribe", MCSubscribe::class.java),
    UNSUBSCRIBE("unsubscribe", MCUnsubscribe::class.java);

    fun getPurposeName(): String {
        return purpose
    }

    fun getBodyClass(): Class<*> {
        return classi
    }

    companion object {
        private val messagePurposeTypes: ConcurrentHashMap<String, MessagePurposeType> = ConcurrentHashMap()

        init {
            for (type in values()) {
                messagePurposeTypes[type.getPurposeName()] = type
            }
        }

        @JvmStatic
        fun fromString(typeName: String?): MessagePurposeType? {
            if (typeName == null) {
                return null
            }

            return messagePurposeTypes[typeName]
        }
    }
}
