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
public class TimeQueryCommand extends MCCommand {

    public enum TimeType {
        daytime,
        gametime,
        day;
    }
    
    public static class TimeQueryCommandInput extends CommandInput {
        String time;
        
        public TimeQueryCommandInput(TimeType time) {
            this(time.name());
        }
        
        public TimeQueryCommandInput(String time) {
            this.time = time;
        }
    }
    
    public TimeQueryCommand(TimeQueryCommandInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TIME_QUERY.getName());
        setVersion(1);  
    }
}
