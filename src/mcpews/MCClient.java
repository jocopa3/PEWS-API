/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TimerTask;
import java.util.UUID;
import java.util.Timer;
import mcpews.command.CloseWebSocketCommand;
import mcpews.event.EventType;
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

    // Requests that were temporarily rejected get stored here
    private Queue<ListenerRequest> requestWaitList;

    private Timer requestTimer;
    private int requestTimeout = 60000; // Wait 60 seconds before kicking out requests

    public MCClient(WebSocket socket) {
        subscribedEvents = new ArrayList<>();
        requestQuery = new HashMap<>();
        requestWaitList = new LinkedList<>();
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
        if(message == null) {
            return;
        }
        
        String messageJson = message.getMessageText();
        System.out.println(messageJson);

        // Add Command messages to the request map
        if (message.getPurpose() == MessagePurposeType.COMMAND_REQUEST) {
            ListenerRequest request = new ListenerRequest(listener, message);
            requestQuery.put(message.getHeader().getRequestId(), request);
        }

        socket.send(messageJson);
    }

    private void send(ListenerRequest request) {
        if (request == null || request.getRequestMessage() == null) {
            return;
        }

        MCMessage message = request.getRequestMessage();
        String messageJson = message.getMessageText();

        if (message.getPurpose() == MessagePurposeType.COMMAND_REQUEST) {
            request.reset();
            requestQuery.put(message.getHeader().getRequestId(), request);
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
        return requestQuery.get(requestId);
    }

    protected ListenerRequest removeRequest(UUID requestId) {
        return requestQuery.remove(requestId);
    }

    public int getRequestTimeoutTime() {
        return requestTimeout;
    }

    public void setRequestTimeoutTime(int millis) {
        this.requestTimeout = millis;
    }

    protected void sendWaitlisted() {
        if (requestWaitList.size() > 0) {
            send(requestWaitList.remove());
        }
    }

    @Override
    public String toString() {
        return clientName;
    }

    protected class ListenerRequest {

        private MCListener requestor;
        private MCMessage request;
        private TimerTask timer;

        private ListenerRequest(MCListener listener, MCMessage request) {
            requestor = listener;
            this.request = request;
            timer = createRemoveTimer(request);

            requestTimer.schedule(timer, requestTimeout);
        }

        private TimerTask createRemoveTimer(MCMessage request) {
            return new TimerTask() {
                @Override
                public void run() {
                    requestQuery.remove(request.getHeader().getRequestId());
                }
            };
        }

        private TimerTask createResendTimer(MCMessage request) {
            return new TimerTask() {
                @Override
                public void run() {
                    send(requestor, request);
                }
            };
        }

        public MCListener getRequestor() {
            return requestor;
        }

        public MCMessage getRequestMessage() {
            return request;
        }

        protected void resetTimer() {
            timer.cancel();
            timer = createRemoveTimer(request);
            requestTimer.schedule(timer, requestTimeout);
        }

        protected void addToWaitList() {
            timer.cancel();
            requestWaitList.add(this);
        }

        private void reset() {
            timer = createRemoveTimer(request);
            requestTimer.schedule(timer, requestTimeout);
        }
    }

}
