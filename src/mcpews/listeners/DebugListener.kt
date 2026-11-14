/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.listeners

import java.util.ArrayList

import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.event.EventType
import mcpews.logger.LogLevel
import mcpews.message.MCMessage

/**
 *
 * @author Jocopa3 S.
 */
open class DebugListener : MCListener {

    private val events: ArrayList<EventType> = ArrayList()

    open fun addEvent(event: EventType) {
        if (!events.contains(event)) {
            events.add(event)
        }
    }

    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        MCSocketServer.messageLogger.log(
            LogLevel.DEBUG, "Received event from {0}; ToString: {1}; Message Text: {2}",
            arrayOf<Any>(client.toString(), eventMessage.toString(), eventMessage.getMessageText())
        )
    }

    override fun onConnected(client: MCClient) {
        for (event in events) {
            client.subscribeToEvent(event)
        }
    }

    override fun onDisconnected(client: MCClient?) {
    }

    override fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage) {
        MCSocketServer.messageLogger.log(
            LogLevel.DEBUG, "Received response from {0}; Message: {1}; Request message: {2}",
            arrayOf<Any?>(
                client.toString(),
                responseMessage.getBody().toString(),
                requestMessage.getMessageText()
            )
        )
    }

    override fun onError(client: MCClient, errorMessage: MCMessage, requestMessage: MCMessage) {
        MCSocketServer.messageLogger.log(
            LogLevel.DEBUG, "Received error from {0}; Message: {1}; Request message: {2}",
            arrayOf<Any>(client.toString(), errorMessage.getBody().toString(), requestMessage.getMessageText())
        )
    }
}
