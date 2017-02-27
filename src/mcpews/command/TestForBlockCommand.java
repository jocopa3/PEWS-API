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

    private class TestForBlockInput extends CommandInput {

        private BlockPos position;
        private String tileName;
        private int dataValue;

        private TestForBlockInput(BlockPos pos, BlockType block) {
            position = pos;
            tileName = block.getName();
            dataValue = block.getData();
        }
    }

    public TestForBlockCommand(BlockPos pos, BlockType block) {
        super();

        setInput(new TestForBlockInput(pos, block));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TESTFORBLOCK.getName());
        setVersion(1);
    }

    public TestForBlockCommand(BlockPos pos, String block) {
        super();

        setInput(new TestForBlockInput(pos, BlockType.fromString(block)));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TESTFORBLOCK.getName());
        setVersion(1);
    }
    
    public TestForBlockCommand(BlockPos pos, String block, Integer data) {
        super();

        setInput(new TestForBlockInput(pos, BlockType.fromString(block, data)));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.TESTFORBLOCK.getName());
        setVersion(1);
    }
}
