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
public class ListDetailResponse extends MCResponse {

    private String details;
    private int currentPlayerCount;
    private int maxPlayerCount;
    private String players;
    
    public String details() {
        return details;
    }
    
    public int currentPlayerCount() {
        return currentPlayerCount;
    }
    
    public int maxPlayerCount() {
        return maxPlayerCount;
    }
    
    public String players() {
        return players;
    }
    
    @Override
    public String toString() {
        return statusMessage;
    }
}
