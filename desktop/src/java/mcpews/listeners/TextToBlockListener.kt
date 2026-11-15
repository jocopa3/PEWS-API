/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.listeners

import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage
import java.util.Arrays
import java.util.Locale
import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.command.SayCommand
import mcpews.command.SetBlockCommand
import mcpews.event.EventType
import mcpews.event.PlayerMessageEvent
import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.message.MCCommand
import mcpews.message.MCEvent
import mcpews.message.MCMessage
import mcpews.message.MCResponse

/**
 *
 * @author Jocopa3
 */
open class TextToBlockListener(private var server: MCSocketServer?) : MCListener {

    /*
     * This listener requires a reference to the server in-order to get all
     * conncted clients.
     */

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

    private var blocks: Array<BlockType> = arrayOf(
        BlockType.DIAMOND_BLOCK,
        BlockType.REDSTONE_BLOCK,
        BlockType.EMERALD_BLOCK,
        BlockType.IRON_BLOCK,
        BlockType.GOLD_BLOCK,
        BlockType.LAPIS_BLOCK
    )

    /*
     * Sends a message using /say to a specific client only.
     */
    private fun setBlock(client: MCClient, block: BlockType?, x: Int, y: Int, z: Int) {
        val blockY = y + 4

        // Create a new /say command and send it to the client
        val say: MCCommand = SetBlockCommand(
            SetBlockCommand.SetBlockInput(BlockPos(x.toDouble(), blockY.toDouble(), z.toDouble()), block)
        )
        client.send(this, say)
    }

    /*
     * This method is called anytime a new client connects to the server
     */
    override fun onConnected(client: MCClient) {
        // Subscribe to player message events to recieve messages from the client
        client.subscribeToEvent(EventType.PLAYER_MESSAGE)
    }

    /*
     * This method is called >after< a client disconnects from the server.
     */
    override fun onDisconnected(client: MCClient?) {
    }

    private var fontName: String = "Sans Serif"
    private var fontSize: Int = 12
    private var fontType: Int = Font.PLAIN
    private var block: BlockType? = BlockType.STONE

    private fun drawText(client: MCClient, message: String) {
        val font = Font(fontName, fontType, fontSize)
        val f = BufferedImage(1, 1, BufferedImage.TYPE_BYTE_BINARY)
        var g = f.getGraphics()
        val metrics = g.getFontMetrics(font)

        val img = BufferedImage(
            (metrics.stringWidth(message) * 1.2).toInt(),
            metrics.getHeight() + 8,
            BufferedImage.TYPE_INT_ARGB
        )
        g = img.createGraphics()
        g.setColor(Color.WHITE)
        g.fillRect(0, 0, img.getWidth(), img.getHeight())
        g.setColor(Color.BLACK)
        g.setFont(font)
        g.drawString(message, fontSize / 16, fontSize / 2 + fontSize / 4)

        // Reverted to old setblock algorithm. New algorithm was slower.
        // Todo: implement a better algorithm

        var cycle = 1
        var y = 0
        var x = 0
        while (y < img.getHeight()) {
            val rgb = img.getRGB(x, y)
            if ((rgb and 1) == 0) {
                setBlock(client, block, x, img.getHeight() - y, 0)
                cycle++
            }
            x++
            System.out.print(if ((rgb and 1) == 0) "\u2593" else "\u2591")

            if (x == img.getWidth() - 1) {
                x = 0
                y++
                System.out.println()
            }
        }
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
                    // Ignore the event if the sender is named External
                    if (event.getSender() != "External") {
                        val m = event.getMessage() ?: return
                        if (m.lowercase(Locale.getDefault()).startsWith("setfont:")) {
                            fontName = m.replace("setfont:", "").trim()

                            if (Font.decode(fontName) != null) {
                                sendMessageToClient(client, "Set font to: " + fontName)
                            } else {
                                sendMessageToClient(client, "Invalid font name")
                            }
                        }
                        if (m.lowercase(Locale.getDefault()).startsWith("setsize:")) {
                            try {
                                fontSize = Integer.parseInt(m.replace("setsize:", "").trim())
                                sendMessageToClient(client, "Set font size to: " + fontSize)
                            } catch (e: Exception) {
                                sendMessageToClient(client, "Invalid font size")
                            }
                        }
                        if (m.lowercase(Locale.getDefault()).startsWith("setblock:")) {
                            try {
                                val b = m.replace("setblock:", "").trim().split(" ")
                                System.out.println(b)
                                if (b.size == 1) {
                                    block = BlockType.fromString(b[0].trim())
                                } else if (b.size == 2) {
                                    block = BlockType.fromString(b[0].trim(), Integer.parseInt(b[1].trim()))
                                }
                                sendMessageToClient(client, "Set block to: " + block)
                            } catch (e: Exception) {
                                sendMessageToClient(client, "Invalid block")
                            }
                        }
                        if (m.lowercase(Locale.getDefault()).startsWith("drawtext:")) {
                            val text = m.replace("drawtext:", "").trim()
                            try {
                                drawText(client, text)
                            } catch (e: Exception) {
                                sendMessageToClient(client, "Couldn't draw text")
                            }
                        }
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
            else -> {}
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
        //server.getLog().log(LogLevel.WARNING, errorMessage.toString());
        System.out.println(errorMessage)
    }
}

internal open class CoordinatePair(@JvmField var x: Int, @JvmField var y: Int) {

    override fun hashCode(): Int {
        val A = if (x >= 0) 2 * x else -2 * x - 1
        val B = if (y >= 0) 2 * y else -2 * y - 1
        val C = (if (A >= B) A * A + A + B else A + B * B) / 2
        return if (x < 0 && y < 0 || x >= 0 && y >= 0) C else -C - 1
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val otherPair = other as CoordinatePair
        if (this.x != otherPair.x) {
            return false
        }
        if (this.y != otherPair.y) {
            return false
        }
        return true
    }

    open fun getX(): Int {
        return x
    }

    open fun getY(): Int {
        return y
    }

    open fun getXY(): Long {
        return ((x shl 31) or y).toLong()
    }
}

internal open class AABB(@JvmField var A: CoordinatePair, @JvmField var B: CoordinatePair) {

    open fun getLower(): CoordinatePair {
        return A
    }

    open fun getUpper(): CoordinatePair {
        return B
    }

    open fun contains(C: CoordinatePair): Boolean {
        return C.x >= A.x && C.x <= B.x && C.y >= A.y && C.y <= B.y
    }
}
