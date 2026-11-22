package mcpews.listeners

/*
 * This is a basic example of a global chatroom for Minecraft PE/Win10 made
 * using the Pocket Edition WebSocket API.
 */
import java.util.HashMap

import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.command.CommandType
import mcpews.command.SayCommand
import mcpews.event.EventType
import mcpews.logger.LogLevel
import mcpews.message.MCEvent
import mcpews.message.MCMessage
import mcpews.message.MCResponse
import mcpews.response.ListDetailResponse
import mcpews.response.ListResponse
import mcpews.response.SayResponse
import mcpews.util.Biome

/*
 * This listener requires a reference to the server in-order to get all
 * conncted clients.
 */
open class BiomeListener(private val server: MCSocketServer) : MCListener {

    private val clientBiomes: HashMap<MCClient, Biome> = HashMap()

    /*
     * This method is called anytime a new client connects to the server
     */
    override fun onConnected(client: MCClient) {
        client.subscribeToEvent(EventType.PLAYER_TRAVELLED)
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    override fun onDisconnected(client: MCClient?) {
    }

    /*
     * This method is called whenever the server recieves an Event message.
     * The eventMessage parameter will contain an MCEvent as the body.
     */
    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        val event = eventMessage.getBody() as MCEvent

        when (event.getEventType()) {
            EventType.PLAYER_TRAVELLED -> {
                val current = clientBiomes[client]
                val latest = event.getProperties()!!.getBiome()!!
                if (current == null || current !== latest) {
                    client.send(
                        SayCommand(
                            SayCommand.SayCommandInput(
                                "Biome: " + latest.getType().getChatColor() + latest.getName()
                            )
                        )
                    )
                    clientBiomes[client] = latest
                }
            }

            else -> {
            }
        }
    }

    /*
     * This method is called whenever the server recieves a command response.
     *
     * The responseMessage parameter will contain an MCResponse as the body.
     * The requestMessage parameter is the original MCCommand request that
     * triggered the response.
     */
    override fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage) {
        val response = responseMessage.getBody() as MCResponse

        when (response.getResponseType()) {
            CommandType.SAY ->
                MCSocketServer.getLog().log(LogLevel.DEBUG, (response as SayResponse).toString())

            CommandType.LIST ->
                MCSocketServer.getLog().log(LogLevel.DEBUG, (response as ListResponse).toString())

            CommandType.LISTD ->
                MCSocketServer.getLog().log(LogLevel.DEBUG, (response as ListDetailResponse).toString())

            else -> {
            }
        }
    }

    /*
     * This method is called whenever the server recieves an error from a client.
     *
     * The errorMessage parameter will contain an MCError as the body.
     * The requestMessage parameter is the original MCCommand request that
     * triggered the error.
     */
    override fun onError(client: MCClient, errorMessage: MCMessage, requestMessage: MCMessage) {
    }
}
