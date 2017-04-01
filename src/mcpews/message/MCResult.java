/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mcpews.message;

/**
 *
 * @author Jocopa3
 */
public class MCResult {
    private int value;
    
    public boolean isSuccess() {
        return value > -1;
    }
}
