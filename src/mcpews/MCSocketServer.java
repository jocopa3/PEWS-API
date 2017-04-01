/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.ClosedByInterruptException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import mcpews.logger.LogLevel;
import mcpews.message.MCCommand;
import mcpews.message.MCMessage;
import mcpews.message.MessagePurposeType;
import mcpews.message.ResponseSerializer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

/**
 *
 * @author Jocopa3
 */
public class MCSocketServer extends WebSocketServer {

    private ArrayList<MCListener> listeners;

    private ConcurrentHashMap<WebSocket, MCClient> clients;

    public static final Logger messageLogger = Logger.getLogger("PEWS-MessageLog");

    public MCSocketServer(InetSocketAddress address) {
        super(address);

        listeners = new ArrayList<>();
        clients = new ConcurrentHashMap<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        messageLogger.log(Level.INFO, "New connection: {0}", conn.getRemoteSocketAddress().getHostString() + ":" + conn.getRemoteSocketAddress().getPort());

        MCClient client = new MCClient(conn);
        clients.put(conn, client);

        for (MCListener l : listeners) {
            l.onConnected(client);
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        messageLogger.log(Level.INFO, "Closed {0} with exit code {1} additional info: {2}", new Object[]{conn.getRemoteSocketAddress(), code, reason});
        MCClient client = clients.get(conn);

        for (MCListener l : listeners) {
            l.onDisconnected(client);
        }

        clients.remove(conn);
    }

    private JsonParser parser = new JsonParser();

    @Override
    public void onMessage(WebSocket conn, String message) {
        if (message == null) {
            return;
        }

        boolean shouldSendWaitlisted = true;

        MCMessage mess;
        MCClient.ListenerRequest request = null;

        MCClient client = clients.get(conn);

        try {
            JsonObject messageJson = parser.parse(message).getAsJsonObject();
            JsonObject headerJson = messageJson.get("header").getAsJsonObject();

            // Parse enough of the message to get the purpose
            MessagePurposeType purpose = MessagePurposeType.fromString(
                    headerJson.get("messagePurpose").getAsString());

            if (purpose == MessagePurposeType.COMMAND_RESPONSE) {
                // Parse enough of the message to get the requestId
                UUID requestId = UUID.fromString(messageJson.get("header").getAsJsonObject()
                        .get("requestId").getAsString());
                request = client.getRequestByUUID(requestId);

                MCMessage requestMessage = request.getRequestMessage();

                if (requestMessage != null && purpose == MessagePurposeType.COMMAND_RESPONSE && requestMessage.getBody() instanceof MCCommand) {
                    // Add a tip to the deserializer about what command triggered the response
                    JsonObject body = messageJson.get("body").getAsJsonObject();
                    JsonElement statusCodeJson = body.get("statusCode");

                    if (statusCodeJson != null) {
                        int statusCode = statusCodeJson.getAsInt();

                        body.addProperty(ResponseSerializer.PROPERTY_HINT_COMMAND,
                                ((MCCommand) requestMessage.getBody()).getName());
                        body.addProperty(ResponseSerializer.PROPERTY_HINT_OVERLOAD,
                                ((MCCommand) requestMessage.getBody()).getOverload());

                        if (statusCode == 131075) {
                            request.resetTimer();
                            shouldSendWaitlisted = false;
                        } else {
                            client.removeRequest(requestId);
                        }
                    }
                } else {
                    throw new Exception("Response with no corresponding request");
                }
            } else if (purpose == MessagePurposeType.ERROR) {
                UUID requestId = UUID.fromString(messageJson.get("header").getAsJsonObject()
                        .get("requestId").getAsString());

                request = client.getRequestByUUID(requestId);

                JsonObject body = messageJson.get("body").getAsJsonObject();
                JsonElement statusCodeJson = body.get("statusCode");

                if (statusCodeJson != null && request != null) {
                    int statusCode = statusCodeJson.getAsInt();

                    if (statusCode == -2147418109) {
                        //System.out.println("Resending: " + requestId);
                        request.addToWaitList();
                        shouldSendWaitlisted = false;
                    }
                }
            }

            mess = MCMessage.getAsMessage(messageJson);
        } catch (Exception e) {
            messageLogger.log(LogLevel.DEBUG, "Failed to parse message; Reason: {0}; Message: {1}", new Object[]{e.getMessage(), message});
            e.printStackTrace();

            return;
        }

        if (mess == null) {
            // Couldn't parse the message or it's empty; don't bother alerting listeners
            return;
        }

        if (shouldSendWaitlisted && (mess.getPurpose() != MessagePurposeType.EVENT)) {
            client.sendWaitlisted();
        }

        //System.out.println(message);
        // If the request was made by a specific listener, send the response directly to it
        if (request != null && request.getRequestor() != null) {
            switch (mess.getPurpose()) {
                case EVENT:
                    request.getRequestor().onEvent(client, mess);
                    break;
                case COMMAND_RESPONSE:
                    request.getRequestor().onResponse(client, mess, request.getRequestMessage());
                    break;
                case ERROR:
                    request.getRequestor().onError(client, mess, request.getRequestMessage());
                    break;
            }
        } else {
            switch (mess.getPurpose()) {
                case EVENT:
                    System.out.println(message);
                    for (MCListener l : listeners) {
                        l.onEvent(client, mess);
                    }
                    break;
                case COMMAND_RESPONSE:
                    for (MCListener l : listeners) {
                        l.onResponse(client, mess, request.getRequestMessage());
                    }
                    break;
                case ERROR:
                    for (MCListener l : listeners) {
                        l.onError(client, mess, request.getRequestMessage());
                    }
                    break;
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            String clientAddress = conn.getRemoteSocketAddress().getHostString() + ":" + conn.getRemoteSocketAddress().getPort();

            if (ex instanceof ClosedByInterruptException) {
                messageLogger.log(Level.WARNING, "Server took to long to stop; forcibly stopped the server.");
                return;
            } else if (ex instanceof IOException) {
                if (ex.getMessage().endsWith("forcibly closed by the remote host")) {
                    messageLogger.log(Level.INFO, "Client {0} forcibly quit.", clientAddress);
                }
            } else {
                messageLogger.log(LogLevel.DEBUG, "An error occured on connection {0}: {1}",
                        new Object[]{clientAddress, ex});
            }

            ex.printStackTrace();
        } else {
            if (ex instanceof BindException) {
                messageLogger.log(Level.SEVERE, "Failed to start server. Another server is likely running on the same port.");
                return;
            } else if (ex instanceof ClosedByInterruptException) {
                messageLogger.log(Level.WARNING, "Server took to long to stop; forcibly stopped the server.");
                return;
            }

            messageLogger.log(LogLevel.DEBUG, "An error occured: {0}", new Object[]{ex});
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        messageLogger.log(Level.INFO, "Starting server on: {0}:" + getAddress().getPort(), new Object[]{getAddress().getHostString()});

        super.run();
    }

    public Collection<MCClient> getClients() {
        return clients.values();
    }

    public void addListener(MCListener m) {
        listeners.add(m);
    }

    public void removeListener(MCListener m) {
        listeners.remove(m);
    }

    public static Logger getLog() {
        return messageLogger;
    }
}
