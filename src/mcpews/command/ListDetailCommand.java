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
public class ListDetailCommand extends MCCommand {

    public enum Detail {
        ids,
        stats,
        uuids;
    }
    
    public static class ListDetailsInput extends CommandInput {
        private String details;
        
        public ListDetailsInput(Detail detail) {
            this(detail.name());
        }
        
        public ListDetailsInput(String detail) {
            details = detail;
        }
    }
    
    public ListDetailCommand() {
        super(new EmptyInput(),
                new BasicOrigin("player"),
                CommandType.LISTD.getName(),
                1
        );
    }
    
    public ListDetailCommand(ListDetailsInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.LISTD.getName());
        setVersion(1);
    }
}