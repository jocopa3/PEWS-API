package mcpews.listeners;

/*
 * This is a basic example of a global chatroom for Minecraft PE/Win10 made
 * using the Pocket Edition WebSocket API.
 */
import java.util.HashMap;

import mcpews.MCClient;
import mcpews.MCListener;
import mcpews.MCSocketServer;
import mcpews.command.SayCommand;
import mcpews.event.EventType;
import mcpews.logger.LogLevel;
import mcpews.message.MCEvent;
import mcpews.message.MCMessage;
import mcpews.message.MCResponse;
import mcpews.response.*;
import mcpews.util.Biome;

public class BiomeListener implements MCListener {

    private HashMap<MCClient, Biome> clientBiomes;
    private MCSocketServer server;

    /*
     * This listener requires a reference to the server in-order to get all
     * conncted clients.
     */
    public BiomeListener(MCSocketServer server) {
        this.server = server;
        clientBiomes = new HashMap<>();
    }

    /*
     * This method is called anytime a new client connects to the server
     */
    @Override
    public void onConnected(MCClient client) {
        client.subscribeToEvent(EventType.PLAYER_TRAVELLED);
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    @Override
    public void onDisconnected(MCClient client) {
    }

    /*
     * This method is called whenever the server recieves an Event message.
     * The eventMessage parameter will contain an MCEvent as the body.
     */
    @Override
    public void onEvent(MCClient client, MCMessage eventMessage) {
        MCEvent event = (MCEvent) eventMessage.getBody();

        switch (event.getEventType()) {
            case PLAYER_TRAVELLED:
                Biome current = clientBiomes.get(client);
                Biome latest = event.getProperties().getBiome();
                if (current == null || current != latest) {
                    client.send(new SayCommand(new SayCommand.SayCommandInput("Biome: " + latest.getType().getChatColor() + latest.getName())));
                    clientBiomes.put(client, latest);
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
}
