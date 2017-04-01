/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.mcenum.BlockPos;
import mcpews.mcenum.BlockType;
import mcpews.mcenum.CommandTarget;
import mcpews.message.MCCommand;
import mcpews.util.StringArray;

/**
 *
 * @author Jocopa3
 */
public class ExecuteCommand extends MCCommand {

    public enum DetectType {
        detect;
    }
    
    public static class ExecuteDetectInput extends CommandInput {
        private CommandTarget origin;
        private BlockPos position;
        private DetectType detect;
        private String detectBlock;
        private int detectData;
        private String command;
        
        public ExecuteDetectInput(CommandTarget target, BlockPos position, DetectType detect, String block, int data, String command) {
            this.overloadName = "detect";
            this.origin = target;
            this.position = position;
            this.detect = detect;
            this.detectBlock = block;
            this.detectData = data;
            this.command = command;
        }
        
        public ExecuteDetectInput(CommandTarget target, BlockPos position, DetectType detect, BlockType block, String command) {
            this(target, position, detect, block.getName(), block.getData(), command);
        }
    }
    
    public static class ExecuteAsOtherInput extends CommandInput {
        private CommandTarget origin;
        private BlockPos position;
        private String command;
        
        public ExecuteAsOtherInput(CommandTarget target, BlockPos position, String command) {
            this.overloadName = "asOther";
            this.origin = target;
            this.position = position;
            this.command = command;
        }
        
        public ExecuteAsOtherInput(CommandTarget target, BlockPos position, String... command) {
            this.overloadName = "asOther";
            this.origin = target;
            this.position = position;
            this.command = String.join(" ", command);
        }
    }
    
    public ExecuteCommand(ExecuteAsOtherInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.EXECUTE.getName());
        setVersion(1);
    }
    
    public ExecuteCommand(ExecuteDetectInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.EXECUTE.getName());
        setVersion(1);
    }
}