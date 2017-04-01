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
public class WSServerCommand extends MCCommand {
    
    public class WSServerCommandInput extends CommandInput {
        String serverUri;
        
        public WSServerCommandInput(String uri) {
            serverUri = uri;
        }
    }
    
    public WSServerCommand(WSServerCommandInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.WSSERVER.getName());
        setVersion(1);  
    }
}
