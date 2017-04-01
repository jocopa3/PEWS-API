/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.response;

import com.google.gson.JsonObject;
import mcpews.message.MCResponse;

/**
 *
 * @author Jocopa3
 */
public class EnchantResponse extends MCResponse {

    private String result;
    
    public String result() {
        return result;
    }
    
    @Override
    public String toString() {
        return statusMessage;
    }
}
