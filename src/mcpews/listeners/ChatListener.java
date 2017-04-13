package mcpews.listeners;

/*
 * This is a basic example of a global chatroom for Minecraft PE/Win10 made
 * using the Pocket Edition WebSocket API.
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.Scanner;

import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.MCSocketServer;
import mcpews.command.SayCommand;
import mcpews.event.EventType;
import mcpews.event.PlayerMessageEvent;
import mcpews.logger.LogLevel;
import mcpews.message.MCCommand;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;
import mcpews.message.MCResponse;
import mcpews.response.*;

public class ChatListener implements MCListener {

    private MCSocketServer server;

    /*
     * This listener requires a reference to the server in-order to get all
     * conncted clients.
     */
    public ChatListener(MCSocketServer server) {
        this.server = server;
    }

    /*
     * Sends a message using /say to a specific client only.
     */
    private void sendMessageToClient(MCClient client, String message) {
        if (server == null) {
            return;
        }

        // Create a new /say command and send it to the client
        MCCommand say = new SayCommand(new SayCommand.SayCommandInput(message));
        client.send(this, say);
    }

    /*
     * Sends a message using /say to all connected clients except the sender.
     */
    private void sendMessageToOthers(MCClient sender, String message) {
        if (server == null) {
            return;
        }

        MCCommand say = new SayCommand(new SayCommand.SayCommandInput(message));

        Collection<MCClient> clients = server.getClients();
        
        synchronized (clients) {
            for (MCClient client : clients) {
                if (!client.equals(sender)) {
                    client.send(this, say);
                }
            }
        }

        // Optional: log the chat message
        server.getLog().log(LogLevel.CHAT, message);
    }

    /*
     * This method is called anytime a new client connects to the server
     */
    @Override
    public void onConnected(MCClient client) {
        // Subscribe to player message events to recieve messages from the client
        client.subscribeToEvent(EventType.PLAYER_MESSAGE);

        sendMessageToClient(client, "§eWelcome to §bPEWS§e chat! Say hi!");
        sendMessageToOthers(client, "§eSomeone has joined the chat!");
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    @Override
    public void onDisconnected(MCClient client) {
        // Inform other clients that someone has disconnected
        sendMessageToOthers(client, "§eSomeone has left the chat!");
    }

    /*
     * This method is called whenever the server recieves an Event message.
     * The eventMessage parameter will contain an MCEvent as the body.
     */
    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_MESSAGE:
                if (event instanceof PlayerMessageEvent) {
                    PlayerMessageEvent pme = (PlayerMessageEvent) event;

                    // Ignore messages from External which are sent by the WebSocket server
                    if (!pme.getSender().equals("External")) {
                        sendMessageToOthers(client, pme.getSender() + ": " + pme.getMessage());
                    }
                }
                break;
        }
    }

    /*
     * This method is called whenever the server recieves a command response.
     * 
     * The responseMessage parameter will contain an MCResponse as the body.
     * The requestMessage parameter is the original MCCommand request that 
     * triggered the response.
     */
    @Override
    public void onResponse(MCClient client, MCMessage responseMessage, MCMessage requestMessage) {
        MCResponse response = (MCResponse) responseMessage.getBody();

        switch (response.getResponseType()) {
            case SAY:
                server.getLog().log(LogLevel.DEBUG, ((SayResponse) response).toString());
                break;
            case LIST:
                server.getLog().log(LogLevel.DEBUG, ((ListResponse) response).toString());
                break;
            case LISTD:
                server.getLog().log(LogLevel.DEBUG, ((ListDetailResponse) response).toString());
                break;
        }
    }

    /*
     * This method is called whenever the server recieves an error from a client.
     * 
     * The errorMessage parameter will contain an MCError as the body.
     * The requestMessage parameter is the original MCCommand request that 
     * triggered the error.
     */
    @Override
    public void onError(MCClient client, MCMessage errorMessage, MCMessage requestMessage) {
    }

    /**
     * ***********************************************************************\
     * |* Everything after this point is just to test the listener. *|
     * \************************************************************************
     */
    // Proper way to stop a server
    public static void stopServer(MCSocketServer server) {
        server.getLog().log(Level.INFO, "Stopping server: {0}:" + server.getAddress().getPort(), server.getAddress().getHostString());
        MCCommand cm = new SayCommand(new SayCommand.SayCommandInput("§cPEWS chat server is shutting down..."));

        Collection<MCClient> con = server.getClients();
        synchronized (con) {
            for (MCClient client : con) {
                client.send(cm); // Send warning to the client

                // Close the client using the /closewebsocket command
                // Also unsubscribes the client from all subscribed events
                client.close();
            }
        }

        try {
            // Wait half a second to ensure all closing requests get sent to clients
            Thread.sleep(500);
        } catch (InterruptedException ex) {

        }

        try {
            // Give the server 5 seconds to fully shutdown
            server.stop(5000);
        } catch (InterruptedException ex) {
            server.getLog().log(Level.SEVERE, "Server shutdown was interrupted.", ex);
        }
    }

    /*
     * Creates a new MCSocketServer and adds a ChatListener to the server
     */
    public static void main(String[] args) throws UnknownHostException {
        //String host = "localhost";
        String host = InetAddress.getLocalHost().getHostAddress();
        int port = 1789;

        MCSocketServer server = new MCSocketServer(new InetSocketAddress(host, port));
        server.addListener(new ChatListener(server));

        // Start the server on a new thread
        Thread t = new Thread(server);
        t.start();

        Scanner cmdline = new Scanner(System.in);

        // Continue until "stop" is typed in the command line
        while (!cmdline.next().toLowerCase().trim().equals("stop")) {
        }

        stopServer(server);
        System.exit(0);
    }
}
