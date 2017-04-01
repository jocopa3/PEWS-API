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
public class TimeSetCommand extends MCCommand {

    public enum TimeOfDay {
        night,
        day;
    }
    
    public static class TimeSetByNumberInput extends CommandInput {
        int time;

        public TimeSetByNumberInput(Integer time) {
            overloadName = "byNumber";
            this.time = time;
        }
    }
    
    public static class TimeSetByNameInput extends CommandInput {
        String time;

        public TimeSetByNameInput(TimeOfDay time) {
            overloadName = "byName";
            this.time = time.name();
        }
        
        public TimeSetByNameInput(String time) {
            overloadName = "byName";
            this.time = time;
        }
    }

    public TimeSetCommand(TimeSetByNameInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TIME_SET.getName());
        setVersion(1);
    }
    
    public TimeSetCommand(TimeSetByNumberInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TIME_SET.getName());
        setVersion(1);
    }
}
