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
public class SetBlockCommand extends MCCommand {

    public enum OldBlockHandling {
        destroy,
        keep,
        replace;
    }

    private class SetBlockInput extends CommandInput {

        private BlockPos position;
        private String tileName;
        private int tileData;
        private String oldBlockHandling;

        private SetBlockInput(BlockPos pos, BlockType block) {
            position = pos;
            tileName = block.getName();
            tileData = block.getData();
        }

        private SetBlockInput(BlockPos pos, BlockType block, OldBlockHandling blockHandling) {
            position = pos;
            tileName = block.getName();
            tileData = block.getData();
            oldBlockHandling = blockHandling.name();
        }
        
        private SetBlockInput(BlockPos pos, BlockType block, String blockHandling) {
            position = pos;
            tileName = block.getName();
            tileData = block.getData();
            oldBlockHandling = blockHandling;
        }
    }

    public SetBlockCommand(BlockPos pos, BlockType block) {
        super();

        setInput(new SetBlockInput(pos, block));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }

    public SetBlockCommand(BlockPos pos, BlockType block, OldBlockHandling handling) {
        super();

        setInput(new SetBlockInput(pos, block, handling));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }
    
    public SetBlockCommand(BlockPos pos, String block) {
        super();

        setInput(new SetBlockInput(pos, BlockType.fromString(block)));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }
    
    public SetBlockCommand(BlockPos pos, String block, Integer data) {
        super();

        setInput(new SetBlockInput(pos, BlockType.fromString(block, data)));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }
    
    public SetBlockCommand(BlockPos pos, String block, Integer data, String oldBlockHandling) {
        super();

        setInput(new SetBlockInput(pos, BlockType.fromString(block, data), oldBlockHandling));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }
}
