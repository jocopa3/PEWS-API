package mcpews.listeners

import java.util.HashMap
import java.util.logging.Level
import java.util.logging.Logger

import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.command.ExecuteCommand
import mcpews.event.EventType
import mcpews.event.PlayerTravelledEvent
import mcpews.logger.LogLevel
import mcpews.mcenum.BlockPos
import mcpews.mcenum.CommandTarget
import mcpews.message.MCEvent
import mcpews.message.MCMessage

/**
 *
 * @author Jocopa3
 */
open class ElytraListener(private val server: MCSocketServer) : MCListener {

    private val playerPositions: HashMap<MCClient, BlockPos> = HashMap()
    private val flyers: HashMap<MCClient, ElytraFlyer> = HashMap()

    private fun getClientPosition(client: MCClient): BlockPos? {
        return playerPositions[client]
    }

    private fun setClientPosition(client: MCClient, pos: BlockPos) {
        playerPositions[client] = pos
    }

    @JvmField
    internal var radius: BlockPos = BlockPos(10.0, 7.0, 10.0)

    private fun doElytraFlying(client: MCClient, pos: BlockPos, metersTravelled: Double) {
        val clientPosition = getClientPosition(client)

        if (clientPosition == null) {
            setClientPosition(client, pos)
            return
        }

        val lowerBound = pos.sub(radius)
        val upperBound = pos.add(radius)

        //FillCommand fill = new FillCommand(new FillCommand.FillCommandInput(lowerBound, upperBound, BlockType.AIR));
        //client.send(fill);
        //SetBlockCommand setBlock
        setClientPosition(client, pos)
    }

    override fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage) {
    }

    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        val event = eventMessage.getBody() as MCEvent

        when (event.getEventType()) {
            EventType.PLAYER_TRAVELLED -> {
                if (event is PlayerTravelledEvent) {
                    val travelMethod = event.getTravelMethod()
                    MCSocketServer.getLog().log(LogLevel.DEBUG, "TravelMethodType: {0}", travelMethod)
                    when (travelMethod) {
                        2 -> flyers[client]!!.setFlying(true)
                        //doElytraFlying(client, pte.getAvgPos(), pte.getMetersTravelled());
                        else -> flyers[client]!!.setFlying(false)
                    }
                }
            }

            else -> {
            }
        }
    }

    override fun onError(client: MCClient, errorMessage: MCMessage, requestMessage: MCMessage) {
    }

    override fun onConnected(client: MCClient) {
        client.subscribeToEvent(EventType.PLAYER_TRAVELLED)
        val flyer = ElytraFlyer(client)
        flyers[client] = flyer
        val t = Thread(flyer)
        t.start()
    }

    override fun onDisconnected(client: MCClient?) {
    }

    private inner class ElytraFlyer(private val client: MCClient) : Runnable {

        private var flying = false

        private val relative: BlockPos = BlockPos("~", "~", "~")
        private val localPlayer: CommandTarget = CommandTarget("@p")

        override fun run() {
            while (true) {
                if (flying) {
                    val exec = ExecuteCommand(
                        ExecuteCommand.ExecuteAsOtherInput(
                            localPlayer,
                            relative,
                            "fill ~-4 ~-4 ~-4 ~4 ~4 ~4 air"
                        )
                    )
                    client.send(exec)
                }
                try {
                    Thread.sleep(40)
                } catch (ex: InterruptedException) {
                    Logger.getLogger(ElytraListener::class.java.name).log(Level.SEVERE, null, ex)
                }
            }
        }

        fun setFlying(flying: Boolean) {
            this.flying = flying
        }
    }
}
