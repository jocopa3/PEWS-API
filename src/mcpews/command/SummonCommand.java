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

    public class SummonCommandInput extends CommandInput {

        String entityType;
        BlockPos spawnPos;

        public SummonCommandInput(String entity) {
            this.entityType = entity;
        }

        public SummonCommandInput(String entity, BlockPos pos) {
            this.entityType = entity;
            this.spawnPos = pos;
        }
    }

    public SummonCommand(String entity) {
        setInput(new SummonCommandInput(entity));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SUMMON.getName());
        setVersion(1);
    }

    public SummonCommand(String entity, BlockPos pos) {
        setInput(new SummonCommandInput(entity, pos));
        setOrigin(new BasicOrigin("player"));
        setName(CommandType.SUMMON.getName());
        setVersion(1);
    }
    
    public SummonCommand(EntityType entity) {
        this(entity.getName());
    }
    
    public SummonCommand(EntityType entity, BlockPos pos) {
        this(entity.getName(), pos);
    }
}
