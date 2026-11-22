package mcpews.command

import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class CloseWebSocketCommand : MCCommand(
    EmptyInput(),
    BasicOrigin("player"),
    CommandType.CLOSE_WEBSOCKET.getName(),
    1
)
