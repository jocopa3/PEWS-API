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
public class FillResponse extends MCResponse {
    private String blockName;
    private int fillCount;
    
    public String getBlockName() {
        return blockName;
    }
    
    public int getFillCount() {
        return fillCount;
    }
    
    @Override
    public String toString() {
        return statusMessage == null ? "" : statusMessage;
    }
}
