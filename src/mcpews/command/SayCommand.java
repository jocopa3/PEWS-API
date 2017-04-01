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
public class SayCommand extends MCCommand {
    
    public static class SayCommandInput extends CommandInput {
        String message;
        
        public SayCommandInput(String message) {
            this.message = message;
        }
    }
    
    public SayCommand(SayCommandInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SAY.getName());
        setVersion(1);  
    }
}
