/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.listeners;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mcpews.*;
import mcpews.event.*;
import mcpews.logger.LogLevel;
import mcpews.message.*;

/**
 *
 * @author Matt S.
 */
public class DebugListener implements MCListener {

    private ArrayList<EventType> events = new ArrayList<>();

    public void addEvent(EventType event) {
        if (!events.contains(event)) {
            events.add(event);
        }
    }

    @Override
    public void onEvent(MCClient client, MCMessage message) {
        MCSocketServer.messageLogger.log(LogLevel.DEBUG, "Received event from {0}; Message Text: {1}",
                new Object[]{
                    client.toString(),
                    message.getMessageText()
                });
    }

    @Override
    public void onConnected(MCClient client) {
        for (EventType event : events) {
            client.subscribeToEvent(event);
        }
    }

    @Override
    public void onDisconnected(MCClient client) {
    }

    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
        MCSocketServer.messageLogger.log(LogLevel.DEBUG, "Received response from {0}; Message: {1}; Request message: {2}",
                new Object[]{
                    client.toString(),
                    responseMessage.getBody().toString(),
                    requestMessage.getMessageText()
                });
    }

    @Override
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage) {
        MCSocketServer.messageLogger.log(LogLevel.DEBUG, "Received error from {0}; Message: {1}; Request message: {2}",
                new Object[]{
                    client.toString(),
                    errorMessage.getBody().toString(),
                    requestMessage.getMessageText()
                });
    }
}
