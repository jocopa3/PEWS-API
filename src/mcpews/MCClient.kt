/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews

import java.util.LinkedList
import java.util.Queue
import java.util.Timer
import java.util.TimerTask
import java.util.UUID
import mcpews.command.CloseWebSocketCommand
import mcpews.event.EventType
import mcpews.message.MCBody
import mcpews.message.MCCommand
import mcpews.message.MCMessage
import mcpews.message.MCSubscribe
import mcpews.message.MCUnsubscribe
import mcpews.message.MessagePurposeType
import org.java_websocket.WebSocket
import org.java_websocket.framing.CloseFrame

/**
 *
 * @author Jocopa3
 */
open class MCClient(
    private val socketConnection: WebSocket,
    private val encryptionRequired: Boolean
) {

    private val clientName: String =
        socketConnection.getRemoteSocketAddress().getHostString() + ":" +
            socketConnection.getRemoteSocketAddress().getPort()

    private val subscribedEvents = ArrayList<EventType>()

    // A null requestId is a legitimate HashMap key in the original Java code
    // (MCHeader.getRequestId() returns null when the header carries no id).
    private val requestQuery = HashMap<UUID?, ListenerRequest>()

    // Requests that were temporarily rejected get stored here
    private val requestWaitList: Queue<ListenerRequest> = LinkedList()

    private val requestTimer = Timer()
    private var requestTimeout = 60000 // Wait 60 seconds before kicking out requests

    constructor(socket: WebSocket) : this(socket, socket.getDraft() is WSEncrypt)

    open fun getSocket(): WebSocket = socketConnection

    open fun requiresEncryption(): Boolean = encryptionRequired

    open fun subscribeToEvent(event: EventType) {
        subscribedEvents.add(event)
        send(MCSubscribe(event))
    }

    open fun unsubscribeFromEvent(event: EventType) {
        subscribedEvents.remove(event)
        send(MCUnsubscribe(event))
    }

    open fun unsubscribeFromAll() {
        for (event in subscribedEvents) {
            send(MCUnsubscribe(event))
        }
    }

    open fun isSubscribedToEvent(event: EventType): Boolean = subscribedEvents.contains(event)

    open fun isSubscribedToEvent(eventName: String?): Boolean {
        val event = EventType.fromString(eventName)
        return event != null && isSubscribedToEvent(event)
    }

    // Server sends close command to client
    open fun closeServerSide() {
        socketConnection.close(CloseFrame.NORMAL)
    }

    // Close the websocket using the client's closewebsocket command
    open fun closeClientSide() {
        val close: MCCommand = CloseWebSocketCommand()
        send(close)
    }

    open fun close() {
        unsubscribeFromAll() // Clean up for the next server the client connects to
        closeClientSide()
    }

    /*
    public String decryptMessage(String message) {
        try {
            byte[] bytes = Base64.decode(message);
        } catch (Base64DecodingException ex) {
            Logger.getLogger(MCClient.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }

        return "";
    }

    public String encrypttMessage(String message) {
        String encrypted;
        try {
            encrypted = Base64.encode(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            encrypted = Base64.encode(message.getBytes());
        }

        return encrypted;
    }
     */

    open fun send(body: MCBody) {
        send(null, body)
    }

    open fun send(message: MCMessage) {
        send(null, message)
    }

    open fun send(listener: MCListener?, body: MCBody) {
        send(listener, body.getAsMessage())
    }

    open fun send(listener: MCListener?, message: MCMessage?) {
        if (message == null) {
            return
        }

        val messageJson = message.getMessageText()
        println(messageJson)

        // Add Command messages to the request map
        if (message.getPurpose() == MessagePurposeType.COMMAND_REQUEST) {
            val request = ListenerRequest(listener, message)
            requestQuery[message.getHeader()!!.getRequestId()] = request
        }

        socketConnection.send(messageJson)
    }

    private fun send(request: ListenerRequest?) {
        if (request == null) {
            return
        }

        val message = request.requestMessage
        val messageJson = message.getMessageText()

        if (message.getPurpose() == MessagePurposeType.COMMAND_REQUEST) {
            request.reset()
            requestQuery[message.getHeader()!!.getRequestId()] = request
        }

        socketConnection.send(messageJson)
    }

    @JvmName("getCorrespondingRequest")
    internal fun getCorrespondingRequest(response: MCMessage): ListenerRequest? {
        return when (response.getPurpose()) {
            MessagePurposeType.COMMAND_RESPONSE,
            MessagePurposeType.ERROR -> requestQuery.remove(response.getHeader()!!.getRequestId())

            else -> null
        }
    }

    @JvmName("getRequestByUUID")
    internal fun getRequestByUUID(requestId: UUID): ListenerRequest? = requestQuery[requestId]

    @JvmName("removeRequest")
    internal fun removeRequest(requestId: UUID): ListenerRequest? = requestQuery.remove(requestId)

    open fun getRequestTimeoutTime(): Int = requestTimeout

    open fun setRequestTimeoutTime(millis: Int) {
        requestTimeout = millis
    }

    @JvmName("sendWaitlisted")
    internal fun sendWaitlisted() {
        if (requestWaitList.size > 0) {
            send(requestWaitList.remove())
        }
    }

    override fun toString(): String = clientName

    internal inner class ListenerRequest internal constructor(
        val requestor: MCListener?,
        val requestMessage: MCMessage
    ) {

        private var timer: TimerTask = createRemoveTimer(requestMessage)

        init {
            requestTimer.schedule(timer, requestTimeout.toLong())
        }

        private fun createRemoveTimer(request: MCMessage): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    requestQuery.remove(request.getHeader()!!.getRequestId())
                }
            }
        }

        private fun createResendTimer(request: MCMessage): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    send(requestor, request)
                }
            }
        }

        @JvmName("resetTimer")
        internal fun resetTimer() {
            timer.cancel()
            timer = createRemoveTimer(requestMessage)
            requestTimer.schedule(timer, requestTimeout.toLong())
        }

        @JvmName("addToWaitList")
        internal fun addToWaitList() {
            timer.cancel()
            requestWaitList.add(this)
        }

        internal fun reset() {
            timer = createRemoveTimer(requestMessage)
            requestTimer.schedule(timer, requestTimeout.toLong())
        }
    }
}
