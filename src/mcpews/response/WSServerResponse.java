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
public class WSServerResponse extends MCResponse {
    private String requestedUri;
    
    public String getRequestedUri() {
        return requestedUri;
    }
}
