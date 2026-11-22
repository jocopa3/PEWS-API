package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class ListCommand : MCCommand(
    EmptyInput(),
    BasicOrigin("player"),
    CommandType.LIST.getName(),
    1
)
