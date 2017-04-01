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
public class TimeAddCommand extends MCCommand {
    
    public static class TimeAddCommandInput extends CommandInput {
        int amount;
        
        public TimeAddCommandInput(Integer amount) {
            this.amount = amount;
        }
    }
    
    public TimeAddCommand(TimeAddCommandInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TIME_ADD.getName());
        setVersion(1);
    }
}
