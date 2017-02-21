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
    
    private class ListDetailsInput extends CommandInput {
        private String details;
        
        private ListDetailsInput(Detail detail) {
            details = detail.name();
        }
    }
    
    public ListDetailCommand() {
        super(new EmptyInput(),
                new BasicOrigin("player"),
                CommandType.LISTD.getName(),
                1,
                "default"
        );
    }
    
    public ListDetailCommand(Detail detail) {
        super();
        
        setInput(new ListDetailsInput(detail));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.LISTD.getName());
        setVersion(1);
        setOverload("default");
    }
}