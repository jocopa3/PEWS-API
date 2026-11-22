package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class TimeAddCommand(input: TimeAddCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.TIME_ADD.getName())
        setVersion(1)
    }

    open class TimeAddCommandInput(amount: Int?) : CommandInput() {

        private var amount: Int = 0

        init {
            this.amount = amount!!
        }
    }
}
