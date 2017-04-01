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
public class FillCommand extends MCCommand {

    public enum OldBlockHandling {
        destroy,
        hollow,
        keep,
        outline,
        replace
    }

    public static class FillCommandInput extends CommandInput {

        BlockPos from;
        BlockPos to;
        String tileName;
        Integer tileData;
        String oldBlockHandling;
        String replaceTileName;
        Integer replaceDataValue;

        public FillCommandInput(BlockPos from, BlockPos to, String tileName) {
            overloadName = "byName";
            this.from = from;
            this.to = to;
            this.tileName = tileName;
        }

        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData) {
            overloadName = "byName";
            this.from = from;
            this.to = to;
            this.tileName = tileName;
            this.tileData = tileData;
        }

        public FillCommandInput(BlockPos from, BlockPos to, BlockType block) {
            this(from, to, block.getName(), block.getData());
        }

        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData, String oldBlockHandling) {
            this(from, to, tileName, tileData);
            this.oldBlockHandling = oldBlockHandling;
        }

        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData, OldBlockHandling oldBlockHandling) {
            this(from, to, tileName, tileData, oldBlockHandling.name());
        }
        
        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData, OldBlockHandling oldBlockHandling, String replaceTileName) {
            this(from, to, tileName, tileData, oldBlockHandling.name());
            this.replaceTileName = replaceTileName;
        }
        
        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData, String oldBlockHandling, String replaceTileName) {
            this(from, to, tileName, tileData, oldBlockHandling);
            this.replaceTileName = replaceTileName;
        }
        
        public FillCommandInput(BlockPos from, BlockPos to, String tileName, Integer tileData, OldBlockHandling oldBlockHandling, String replaceTileName, int replaceDataValue) {
            this(from, to, tileName, tileData, oldBlockHandling.name(), replaceTileName);
            this.replaceDataValue = replaceDataValue;
        }
    }

    public FillCommand(FillCommandInput input) {
        super();
        
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.FILL.getName());
        setVersion(1);
    }
}
