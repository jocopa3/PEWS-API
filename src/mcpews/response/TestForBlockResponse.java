/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.response;

import mcpews.mcenum.BlockPos;
import mcpews.message.MCResponse;

/**
 *
 * @author Jocopa3
 */
public class TestForBlockResponse extends MCResponse {
    BlockPos position;
    boolean matches;
    
    public BlockPos getPosition() {
        return position;
    }
    
    public boolean matches() {
        return matches;
    }
}
