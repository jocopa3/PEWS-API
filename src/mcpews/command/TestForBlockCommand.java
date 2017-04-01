/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.mcenum.BlockPos;
import mcpews.mcenum.BlockType;
import mcpews.message.MCCommand;

/**
 *
 * @author Jocopa3
 */
public class TestForBlockCommand extends MCCommand {

    public static class TestForBlockInput extends CommandInput {

        private BlockPos position;
        private String tileName;
        private int dataValue;

        public TestForBlockInput(BlockPos pos, String block) {
            this(pos, block, 0);
        }
        
        public TestForBlockInput(BlockPos pos, String block, Integer data) {
            position = pos;
            tileName = block;
            dataValue = data;
        }
        
        public TestForBlockInput(BlockPos pos, BlockType block) {
            this(pos, block.getName(), block.getData());
        }
    }

    public TestForBlockCommand(TestForBlockInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TESTFORBLOCK.getName());
        setVersion(1);
    }
}
