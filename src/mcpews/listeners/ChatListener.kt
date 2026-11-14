package mcpews.listeners

/*
 * This is a basic example of a global chatroom for Minecraft PE/Win10 made
 * using the Pocket Edition WebSocket API.
 */
import java.net.InetAddress
import java.net.InetSocketAddress
import java.util.Locale
import java.util.Scanner
import java.util.logging.Level

import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.command.CommandType
import mcpews.command.SayCommand
import mcpews.event.EventType
import mcpews.event.PlayerMessageEvent
import mcpews.logger.LogLevel
import mcpews.message.MCCommand
import mcpews.message.MCEvent
import mcpews.message.MCMessage
import mcpews.message.MCResponse
import mcpews.response.ListDetailResponse
import mcpews.response.ListResponse
import mcpews.response.SayResponse

open class ChatListener(private val server: MCSocketServer?) : MCListener {

    /*
     * Sends a message using /say to a specific client only.
     */
    private fun sendMessageToClient(client: MCClient, message: String) {
        if (server == null) {
            return
        }

        // Create a new /say command and send it to the client
        val say: MCCommand = SayCommand(SayCommand.SayCommandInput(message))
        client.send(this, say)
    }

    /*
     * Sends a message using /say to all connected clients except the sender.
     */
    private fun sendMessageToOthers(sender: MCClient?, message: String) {
        val currentServer = server ?: return

        val say: MCCommand = SayCommand(SayCommand.SayCommandInput(message))

        val clients = currentServer.getClients()

        synchronized(clients) {
            for (client in clients) {
                if (client != sender) {
                    client.send(this, say)
                }
            }
        }

        // Optional: log the chat message
        MCSocketServer.getLog().log(LogLevel.CHAT, message)
    }

    /*
     * This method is called anytime a new client connects to the server
     */
    override fun onConnected(client: MCClient) {
        // Subscribe to player message events to recieve messages from the client
        client.subscribeToEvent(EventType.PLAYER_MESSAGE)

        sendMessageToClient(client, "§eWelcome to §bPEWS§e chat! Say hi!")
        sendMessageToOthers(client, "§eSomeone has joined the chat!")
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    override fun onDisconnected(client: MCClient?) {
        // Inform other clients that someone has disconnected
        sendMessageToOthers(client, "§eSomeone has left the chat!")
    }

    /*
     * This method is called whenever the server recieves an Event message.
     * The eventMessage parameter will contain an MCEvent as the body.
     */
    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        val event = eventMessage.getBody() as MCEvent

        when (event.getEventType()) {
            EventType.PLAYER_MESSAGE -> {
                if (event is PlayerMessageEvent) {
                    // Ignore messages from External which are sent by the WebSocket server
                    if (event.getSender() != "External") {
                        sendMessageToOthers(client, event.getSender() + ": " + event.getMessage())
                    }
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

    /**
     * ***********************************************************************\
     * |* Everything after this point is just to test the listener. *|
     * \************************************************************************
     */
    companion object {

        // Proper way to stop a server
        @JvmStatic
        fun stopServer(server: MCSocketServer) {
            MCSocketServer.getLog().log(
                Level.INFO,
                "Stopping server: {0}:" + server.getAddress().getPort(),
                server.getAddress().getHostString()
            )
            val cm: MCCommand = SayCommand(SayCommand.SayCommandInput("§cPEWS chat server is shutting down..."))

            val con = server.getClients()
            synchronized(con) {
                for (client in con) {
                    client.send(cm) // Send warning to the client

                    // Close the client using the /closewebsocket command
                    // Also unsubscribes the client from all subscribed events
                    client.close()
                }
            }

            try {
                // Wait half a second to ensure all closing requests get sent to clients
                Thread.sleep(500)
            } catch (ex: InterruptedException) {
                // Ignore the interruption, matching the original empty catch block
            }

            try {
                // Give the server 5 seconds to fully shutdown
                server.stop(5000)
            } catch (ex: InterruptedException) {
                MCSocketServer.getLog().log(Level.SEVERE, "Server shutdown was interrupted.", ex)
            }
        }

        /*
         * Creates a new MCSocketServer and adds a ChatListener to the server
         */
        @JvmStatic
        fun main(args: Array<String>) {
            //String host = "localhost";
            val host = InetAddress.getLocalHost().getHostAddress()
            val port = 1789

            val server = MCSocketServer(InetSocketAddress(host, port))
            server.addListener(ChatListener(server))

            // Start the server on a new thread
            val t = Thread(server)
            t.start()

            val cmdline = Scanner(System.`in`)

            // Continue until "stop" is typed in the command line
            while (cmdline.next().lowercase(Locale.getDefault()).trim() != "stop") {
            }

            stopServer(server)
            System.exit(0)
        }
    }
}
