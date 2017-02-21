/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.response;

import mcpews.message.MCResponse;

/**
 *
 * @author Jocopa3
 */
public class SayResponse extends MCResponse {
    private String message;
    
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
