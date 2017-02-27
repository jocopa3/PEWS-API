/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.response;

import mcpews.mcenum.BlockPos;
import mcpews.mcenum.EntityType;
import mcpews.message.MCResponse;

/**
 *
 * @author Jocopa3
 */
public class SummonResponse extends MCResponse {
    private String entityType;
    private BlockPos spawnPos;
    private boolean wasSpawned;
    
    public EntityType getEntityType() {
        return EntityType.fromString(entityType);
    }
    
    public BlockPos getSpawnPos() {
        return spawnPos;
    }
    
    public boolean wasSpawned() {
        return wasSpawned;
    }
    
    @Override
    public String toString() {
        if(statusCode != 0) {
            return statusMessage;
        }
        
        return statusMessage.toLowerCase().replace("object", entityType);
    }
}
