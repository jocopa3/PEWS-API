/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews

import mcpews.message.MCMessage

/**
 *
 * @author Jocopa3
 */
interface MCListener {
    fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage)

    fun onEvent(client: MCClient, eventMessage: MCMessage)

    fun onError(client: MCClient, errorMessage: MCMessage, requestMessage: MCMessage)

    fun onConnected(client: MCClient)

    // The server notifies listeners even when the connection was never registered
    // as an MCClient, so Java could (and did) pass null here.
    fun onDisconnected(client: MCClient?)
}
