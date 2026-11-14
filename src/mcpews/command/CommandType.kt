package mcpews.command

import java.util.HashMap
import mcpews.message.MCResponse
import mcpews.response.*

/**
 *
 * @author Jocopa3
 */
enum class CommandType(
    private val commandName: String,
    private val requestBodyClass: Class<*>,
    private val responseBodyClass: Class<*>
) {
    // Command classes must not be null!
    CLOSE_WEBSOCKET("closewebsocket", CloseWebSocketCommand::class.java, CloseWebSocketResponse::class.java),
    CONNECT("connect", WSServerCommand::class.java, WSServerResponse::class.java),
    ENCHANT("enchant", EnchantCommand::class.java, EnchantResponse::class.java),
    EXECUTE("execute", ExecuteCommand::class.java, MCResponse::class.java),
    FILL("fill", FillCommand::class.java, FillResponse::class.java),
    LIST("list", ListCommand::class.java, ListResponse::class.java),
    LISTD("listd", ListDetailCommand::class.java, ListDetailResponse::class.java),
    SAY("say", SayCommand::class.java, SayResponse::class.java),
    SETBLOCK("setblock", SetBlockCommand::class.java, SetBlockResponse::class.java),
    SUMMON("summon", SummonCommand::class.java, SummonResponse::class.java),
    TESTFORBLOCK("testforblock", TestForBlockCommand::class.java, TestForBlockResponse::class.java),
    TIME_ADD("time add", TimeAddCommand::class.java, TimeResponse::class.java),
    TIME_QUERY("time query", TimeQueryCommand::class.java, TimeResponse::class.java),
    TIME_SET("time set", TimeSetCommand::class.java, TimeResponse::class.java),
    WSSERVER("wsserver", WSServerCommand::class.java, WSServerResponse::class.java);

    fun getName(): String {
        return commandName
    }

    fun getRequestClass(): Class<*> {
        return requestBodyClass
    }

    fun getResponseClass(): Class<*> {
        return responseBodyClass
    }

    companion object {
        private val commands: HashMap<String?, CommandType> = HashMap()

        init {
            for (command in CommandType.values()) {
                commands[command.getName()] = command
            }
        }

        @JvmStatic
        fun fromString(name: String?): CommandType? {
            return commands[name]
        }

        @JvmStatic
        fun fromChatString(chat: String): CommandType? {
            var text = chat.trim { it <= ' ' }

            if (text.startsWith("/")) {
                text = text.substring(1)
            }

            for (type in CommandType.values()) {
                if (text.startsWith(type.getName())) {
                    return type
                }
            }

            return null
        }
    }
}
