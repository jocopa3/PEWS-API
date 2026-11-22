package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class SayCommand(input: SayCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.SAY.getName())
        setVersion(1)
    }

    open class SayCommandInput(message: String?) : CommandInput() {
        private var message: String? = message
    }
}
