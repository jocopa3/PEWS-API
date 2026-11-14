/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews

import com.google.gson.JsonParser
import java.io.IOException
import java.net.BindException
import java.net.InetSocketAddress
import java.nio.channels.ClosedByInterruptException
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.logging.Level
import java.util.logging.Logger
import mcpews.logger.LogLevel
import mcpews.message.MCCommand
import mcpews.message.MCMessage
import mcpews.message.MessagePurposeType
import mcpews.message.ResponseSerializer
import org.java_websocket.WebSocket
import org.java_websocket.drafts.Draft
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

/**
 *
 * @author Jocopa3
 */
open class MCSocketServer(address: InetSocketAddress) : WebSocketServer(address, drafts) {

    private val listeners = ArrayList<MCListener>()

    private val clients = ConcurrentHashMap<WebSocket, MCClient>()

    private val parser = JsonParser()

    companion object {
        // Kept as a real public static field: Java listeners (e.g. DebugListener)
        // read MCSocketServer.messageLogger directly.
        @JvmField
        val messageLogger: Logger = Logger.getLogger("PEWS-MessageLog")

        private val drafts: ArrayList<Draft> = arrayListOf(
            WSEncrypt(), // RFC 6455 with subprotocol com.microsoft.minecraft.wsencrypt; encrypted
            Draft_6455() // Default RFC 6455 protocol; no encryption
        )

        @JvmStatic
        fun getLog(): Logger = messageLogger
    }

    override fun onStart() {
        messageLogger.log(Level.INFO, "PEWS server started")
    }

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        val client = MCClient(conn)
        clients.put(conn, client)

        messageLogger.log(
            Level.INFO, "New connection: {0}; Encryption Status: {1}",
            arrayOf<Any>(
                conn.getRemoteSocketAddress().getHostString() + ":" + conn.getRemoteSocketAddress().getPort(),
                if (client.requiresEncryption()) "required" else "none"
            )
        )

        for (l in listeners) {
            l.onConnected(client)
        }
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String?, remote: Boolean) {
        messageLogger.log(
            Level.INFO, "Closed {0} with exit code {1} additional info: {2}",
            arrayOf<Any?>(conn.getRemoteSocketAddress(), code, reason)
        )

        val client = clients.get(conn)

        for (l in listeners) {
            l.onDisconnected(client)
        }

        clients.remove(conn)
    }

    override fun onMessage(conn: WebSocket, message: String?) {
        if (message == null) {
            return
        }

        var shouldSendWaitlisted = true

        var request: MCClient.ListenerRequest? = null

        val client = clients.get(conn)

        val mess: MCMessage? = try {
            val messageJson = parser.parse(message).getAsJsonObject()
            val headerJson = messageJson.get("header").getAsJsonObject()

            // Parse enough of the message to get the purpose
            val purpose = MessagePurposeType.fromString(headerJson.get("messagePurpose").getAsString())

            if (purpose == MessagePurposeType.COMMAND_RESPONSE) {
                // Parse enough of the message to get the requestId
                val requestId = UUID.fromString(
                    messageJson.get("header").getAsJsonObject().get("requestId").getAsString()
                )
                request = client!!.getRequestByUUID(requestId)

                // The request body is never null for a request created by this class
                // (the Java code also guarded against it here).
                val requestMessage = request!!.requestMessage

                if (requestMessage.getBody() is MCCommand) {
                    // Add a tip to the deserializer about what command triggered the response
                    val body = messageJson.get("body").getAsJsonObject()
                    val statusCodeJson = body.get("statusCode")

                    if (statusCodeJson != null) {
                        val statusCode = statusCodeJson.getAsInt()

                        body.addProperty(
                            ResponseSerializer.PROPERTY_HINT_COMMAND,
                            (requestMessage.getBody() as MCCommand).getName()
                        )
                        body.addProperty(
                            ResponseSerializer.PROPERTY_HINT_OVERLOAD,
                            (requestMessage.getBody() as MCCommand).getOverload()
                        )

                        if (statusCode == 131075) {
                            request.resetTimer()
                            shouldSendWaitlisted = false
                        } else {
                            client.removeRequest(requestId)
                        }
                    }
                } else {
                    throw Exception("Response with no corresponding request")
                }
            } else if (purpose == MessagePurposeType.ERROR) {
                val requestId = UUID.fromString(
                    messageJson.get("header").getAsJsonObject().get("requestId").getAsString()
                )

                request = client!!.getRequestByUUID(requestId)

                val body = messageJson.get("body").getAsJsonObject()
                val statusCodeJson = body.get("statusCode")

                if (statusCodeJson != null && request != null) {
                    val statusCode = statusCodeJson.getAsInt()

                    if (statusCode == -2147418109) {
                        //System.out.println("Resending: " + requestId);
                        request.addToWaitList()
                        shouldSendWaitlisted = false
                    }
                }
            }

            MCMessage.getAsMessage(messageJson)
        } catch (e: Exception) {
            messageLogger.log(
                LogLevel.DEBUG, "Failed to parse message; Reason: {0}; Message: {1}",
                arrayOf<Any?>(e.message, message)
            )
            e.printStackTrace()

            return
        }

        if (mess == null) {
            // Couldn't parse the message or it's empty; don't bother alerting listeners
            return
        }

        if (shouldSendWaitlisted && mess.getPurpose() != MessagePurposeType.EVENT) {
            client!!.sendWaitlisted()
        }

        //System.out.println(message);
        // If the request was made by a specific listener, send the response directly to it
        val requestor = request?.requestor
        if (requestor != null) {
            when (mess.getPurpose()) {
                MessagePurposeType.EVENT -> requestor.onEvent(client!!, mess)
                MessagePurposeType.COMMAND_RESPONSE ->
                    requestor.onResponse(client!!, mess, request.requestMessage)
                MessagePurposeType.ERROR ->
                    requestor.onError(client!!, mess, request.requestMessage)

                else -> Unit
            }
        } else {
            when (mess.getPurpose()) {
                MessagePurposeType.EVENT -> {
                    println(message)
                    for (l in listeners) {
                        l.onEvent(client!!, mess)
                    }
                }

                MessagePurposeType.COMMAND_RESPONSE -> for (l in listeners) {
                    l.onResponse(client!!, mess, request!!.requestMessage)
                }

                MessagePurposeType.ERROR -> for (l in listeners) {
                    l.onError(client!!, mess, request!!.requestMessage)
                }

                else -> Unit
            }
        }
    }

    override fun onError(conn: WebSocket?, ex: Exception) {
        if (conn != null) {
            val clientAddress =
                conn.getRemoteSocketAddress().getHostString() + ":" + conn.getRemoteSocketAddress().getPort()

            if (ex is ClosedByInterruptException) {
                messageLogger.log(Level.WARNING, "Server took to long to stop; forcibly stopped the server.")
                return
            } else if (ex is IOException) {
                if (ex.message!!.endsWith("forcibly closed by the remote host")) {
                    messageLogger.log(Level.INFO, "Client {0} forcibly quit.", arrayOf<Any>(clientAddress))
                }
            } else {
                messageLogger.log(
                    LogLevel.DEBUG, "An error occured on connection {0}: {1}",
                    arrayOf<Any?>(clientAddress, ex)
                )
            }

            ex.printStackTrace()
        } else {
            if (ex is BindException) {
                messageLogger.log(
                    Level.SEVERE,
                    "Failed to start server. Another server is likely running on the same port."
                )
                return
            } else if (ex is ClosedByInterruptException) {
                messageLogger.log(Level.WARNING, "Server took to long to stop; forcibly stopped the server.")
                return
            }

            messageLogger.log(LogLevel.DEBUG, "An error occured: {0}", arrayOf<Any?>(ex))
            ex.printStackTrace()
        }
    }

    override fun run() {
        messageLogger.log(
            Level.INFO, "Starting server on: {0}:" + getAddress().getPort(),
            arrayOf<Any>(getAddress().getHostString())
        )

        super.run()
    }

    open fun getClients(): Collection<MCClient> = clients.values

    open fun addListener(m: MCListener) {
        listeners.add(m)
    }

    open fun removeListener(m: MCListener) {
        listeners.remove(m)
    }
}
