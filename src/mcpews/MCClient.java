/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Timer;
import mcpews.command.CloseWebSocketCommand;
import mcpews.event.EventType;
import mcpews.logger.LogLevel;
import mcpews.message.*;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.CloseFrame;

/**
 *
 * @author Jocopa3
 */
public class MCClient {

    private final WebSocket socket;
    private final String clientName;

    private ArrayList<EventType> subscribedEvents;

    private HashMap<UUID, ListenerRequest> requestQuery;

    private Timer requestTimer;
    private int requestTimeout = 30000; // Wait 30 seconds before kicking out requests

    public MCClient(WebSocket socket) {
        subscribedEvents = new ArrayList<>();
        requestQuery = new HashMap<>();
        requestTimer = new Timer();

        this.socket = socket;
        clientName = socket.getRemoteSocketAddress().getHostString() + ":" + socket.getRemoteSocketAddress().getPort();
    }

    public WebSocket getSocket() {
        return socket;
    }

    public void subscribeToEvent(EventType event) {
        subscribedEvents.add(event);
        send(new MCSubscribe(event));
    }

    public void unsubscribeFromEvent(EventType event) {
        subscribedEvents.remove(event);
        send(new MCUnsubscribe(event));
    }

    public void unsubscribeFromAll() {
        for (EventType event : subscribedEvents) {
            send(new MCUnsubscribe(event));
        }
    }

    public boolean isSubscribedToEvent(EventType event) {
        return subscribedEvents.contains(event);
    }

    public boolean isSubscribedToEvent(String eventName) {
        return isSubscribedToEvent(EventType.fromString(eventName));
    }

    // Server sends close command to client
    public void closeServerSide() {
        socket.close(CloseFrame.NORMAL);
    }

    // Close the websocket using the client's closewebsocket command
    public void closeClientSide() {
        MCCommand close = new CloseWebSocketCommand();
        send(close);
    }

    public void close() {
        unsubscribeFromAll(); // Clean up for the next server the client connects to
        closeClientSide();
    }

    public void send(MCBody body) {
        send(null, body);
    }
    
    public void send(MCMessage message) {
        send(null, message);
    }
    
    public void send(MCListener listener, MCBody body) {
        send(listener, body.getAsMessage());
    }

    public void send(MCListener listener, MCMessage message) {
        String messageJson = message.getMessageText();
        
        // Add Command messages to the request map
        if (message.getPurpose() == MessagePurposeType.COMMAND_REQUEST) {
            ListenerRequest request = new ListenerRequest(listener, message);
            requestQuery.put(message.getHeader().getRequestId(), request);

            
            // Removes the request if it doesn't recieve a timely response
            requestTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    requestQuery.remove(message.getHeader().getRequestId());
                }
            }, requestTimeout);
            
        }

        socket.send(messageJson);
    }

    protected ListenerRequest getCorrespondingRequest(MCMessage response) {
        switch (response.getPurpose()) {
            case COMMAND_RESPONSE:
            case ERROR:
                return requestQuery.remove(response.getHeader().getRequestId());
            default:
                return null;
        }
    }

    protected ListenerRequest getRequestByUUID(UUID requestId) {
        return requestQuery.remove(requestId);
    }

    public int getRequestTimeoutTime() {
        return requestTimeout;
    }

    public void setRequestTimeoutTime(int millis) {
        this.requestTimeout = millis;
    }

    @Override
    public String toString() {
        return clientName;
    }
}
