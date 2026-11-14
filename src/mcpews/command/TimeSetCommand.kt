package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class TimeSetCommand : MCCommand {

    enum class TimeOfDay {
        night,
        day
    }

    open class TimeSetByNumberInput(time: Int?) : CommandInput() {

        private var time: Int = 0

        init {
            overloadName = "byNumber"
            this.time = time!!
        }
    }

    open class TimeSetByNameInput : CommandInput {

        private var time: String? = null

        constructor(time: TimeOfDay) : super() {
            overloadName = "byName"
            this.time = time.name
        }

        constructor(time: String?) : super() {
            overloadName = "byName"
            this.time = time
        }
    }

    constructor(input: TimeSetByNameInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.TIME_SET.getName())
        setVersion(1)
    }

    constructor(input: TimeSetByNumberInput) : super() {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.TIME_SET.getName())
        setVersion(1)
    }
}
