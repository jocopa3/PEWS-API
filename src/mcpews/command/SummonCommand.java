/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.command;

import mcpews.mcenum.BlockPos;
import mcpews.mcenum.EntityType;
import mcpews.message.MCCommand;

/**
 *
 * @author Jocopa3
 */
public class SummonCommand extends MCCommand {

    public static class SummonCommandInput extends CommandInput {

        String entityType;
        BlockPos spawnPos;

        public SummonCommandInput(String entity) {
            this.entityType = entity;
        }

        public SummonCommandInput(String entity, BlockPos pos) {
            this.entityType = entity;
            this.spawnPos = pos;
        }

        public SummonCommandInput(EntityType entity) {
            this(entity.getName());
        }
        
        public SummonCommandInput(EntityType entity, BlockPos pos) {
            this(entity.getName(), pos);
        }
    }

    public SummonCommand(SummonCommandInput input) {
        setInput(input);
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SUMMON.getName());
        setVersion(1);
    }
}
