/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.message.MCCommand;

/**
 *
 * @author Jocopa3
 */
public class CloseWebSocketCommand extends MCCommand {

    public CloseWebSocketCommand() {
        super(new EmptyInput(),
                new BasicOrigin("player"),
                CommandType.CLOSE_WEBSOCKET.getName(),
                1,
                "default"
        );
    }
}
