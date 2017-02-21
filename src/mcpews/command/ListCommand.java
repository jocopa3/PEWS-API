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
public class ListCommand extends MCCommand {

    public ListCommand() {
        super(new EmptyInput(),
                new BasicOrigin("player"),
                CommandType.LIST.getName(),
                1,
                "default"
        );
    }
}
