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

    public static class SetBlockInput extends CommandInput {

        private BlockPos position;
        private String tileName;
        private int tileData;
        private String oldBlockHandling;

        public SetBlockInput(BlockPos pos, BlockType block) {
            position = pos;
            tileName = block.getName();
            tileData = block.getData();
        }

        public SetBlockInput(BlockPos pos, BlockType block, OldBlockHandling blockHandling) {
            this(pos, block, blockHandling.name());
        }
        
        public SetBlockInput(BlockPos pos, BlockType block, String blockHandling) {
            position = pos;
            tileName = block.getName();
            tileData = block.getData();
            oldBlockHandling = blockHandling;
        }
        
        public SetBlockInput(BlockPos pos, String blockId) {
            this(pos, BlockType.fromString(blockId));
        }
        
        public SetBlockInput(BlockPos pos, String blockId, Integer data) {
            this(pos, BlockType.fromString(blockId, data));
        }
        
        public SetBlockInput(BlockPos pos, String blockId, OldBlockHandling handling) {
            this(pos, BlockType.fromString(blockId), handling);
        }
        
        public SetBlockInput(BlockPos pos, String blockId, Integer data, String handling) {
            this(pos, BlockType.fromString(blockId, data), handling);
        }
    }

    public SetBlockCommand(SetBlockInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SETBLOCK.getName());
        setVersion(1);
    }
}
