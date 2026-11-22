package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class TimeQueryCommand(input: TimeQueryCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.TIME_QUERY.getName())
        setVersion(1)
    }

    enum class TimeType {
        daytime,
        gametime,
        day
    }

    open class TimeQueryCommandInput : CommandInput {

        private var time: String? = null

        constructor(time: TimeType) : super() {
            this.time = time.name
        }

        constructor(time: String?) : super() {
            this.time = time
        }
    }
}
