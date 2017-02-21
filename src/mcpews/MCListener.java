/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews;

import mcpews.message.*;

/**
 *
 * @author Jocopa3
 */
public interface MCListener {
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage);
    public void onEvent(MCClient client, MCMessage eventMessage);
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage);
    
    public void onConnected(MCClient client);
    public void onDisconnected(MCClient client);
}
