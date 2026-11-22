package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class WSServerCommand(input: WSServerCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.WSSERVER.getName())
        setVersion(1)
    }

    open inner class WSServerCommandInput(uri: String?) : CommandInput() {
        private var serverUri: String? = uri
    }
}
