package mcpews.listeners

import java.util.HashMap
import java.util.Locale

import mcpews.MCClient
import mcpews.MCListener
import mcpews.MCSocketServer
import mcpews.command.CommandType
import mcpews.command.SayCommand
import mcpews.command.SetBlockCommand
import mcpews.command.TestForBlockCommand
import mcpews.event.EventType
import mcpews.event.PlayerMessageEvent
import mcpews.event.PlayerTransformEvent
import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.message.MCEvent
import mcpews.message.MCMessage
import mcpews.message.MCResponse
import mcpews.response.TestForBlockResponse
import mcpews.util.ChatFormatCode

/**
 *
 * @author Jocopa3
 */
open class HideAndSeekListener(private val server: MCSocketServer) : MCListener {

    private val runningGames: HashMap<MCClient, HideAndSeekRunner> = HashMap()

    override fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage) {
        val response = responseMessage.getBody() as MCResponse

        when (response.getResponseType()) {
            CommandType.TESTFORBLOCK -> {
                if (response is TestForBlockResponse) {
                    val game = runningGames[client]

                    if (game != null) {
                        game.setPlaying(response.matches())
                    }
                }
            }

            else -> {
            }
        }
    }

    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        val event = eventMessage.getBody() as MCEvent

        when (event.getEventType()) {
            EventType.PLAYER_MESSAGE -> {
                if (event is PlayerMessageEvent) {
                    if (event.getMessage()!!.lowercase(Locale.getDefault()) == "play hide and seek") {
                        val game = HideAndSeekRunner(this, client)
                        runningGames[client] = game
                        Thread(game).start()
                        return
                    }
                }
            }

            EventType.PLAYER_TRANSFORM -> {
                if (event is PlayerTransformEvent) {
                    val game = runningGames[client]

                    if (game != null) {
                        game.setPlayerPos(event.getPosition())
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
        client.subscribeToEvent(EventType.PLAYER_MESSAGE)
        client.subscribeToEvent(EventType.PLAYER_TRANSFORM)
    }

    override fun onDisconnected(client: MCClient?) {
    }

    private fun finishGame(client: MCClient) {
        runningGames.remove(client)
    }

    private inner class HideAndSeekRunner(
        private val listener: HideAndSeekListener,
        private val client: MCClient
    ) : Runnable {

        private var playing = true
        private var randomPos: BlockPos = randomPos()
        private var playerPos: BlockPos = BlockPos(10000.0, 10000.0, 10000.0)
        private var distanceDelta = 0.0

        init {
            client.send(
                listener,
                SetBlockCommand(SetBlockCommand.SetBlockInput(randomPos, BlockType.DIAMOND_BLOCK))
            )
        }

        private fun randomPos(): BlockPos {
            return BlockPos(Math.random() * 200 - 100, Math.random() * 64 + 4, Math.random() * 200 - 100)
        }

        override fun run() {
            var a = 0
            while (playing) {
                client.send(
                    listener,
                    TestForBlockCommand(TestForBlockCommand.TestForBlockInput(randomPos, BlockType.DIAMOND_BLOCK))
                )

                val message: String

                if (distanceDelta < -1.1) {
                    message = ChatFormatCode.BLUE_STRING + ChatFormatCode.BOLD_STRING + "Getting Super Cold"
                } else if (distanceDelta < -0.6) {
                    message = ChatFormatCode.BLUE_STRING + "Getting Colder"
                } else if (distanceDelta < -0.1) {
                    message = ChatFormatCode.AQUA_STRING + "Getting Cooler"
                } else if (distanceDelta > 1.1) {
                    message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "Getting Extremly Hot"
                } else if (distanceDelta > 0.6) {
                    message = ChatFormatCode.RED_STRING + "Getting Hotter"
                } else if (distanceDelta > 0.1) {
                    message = ChatFormatCode.YELLOW_STRING + "Getting Warmer"
                } else {
                    val dist = playerPos.distance(randomPos)

                    if (dist < 4.0) {
                        message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "On Fire!"
                    } else if (dist < 8.5) {
                        message = ChatFormatCode.RED_STRING + ChatFormatCode.BOLD_STRING + "Super Hot"
                    } else if (dist < 15.0) {
                        message = ChatFormatCode.RED_STRING + "Hot"
                    } else if (dist < 25.0) {
                        message = ChatFormatCode.YELLOW_STRING + "Warm"
                    } else if (dist < 50.0) {
                        message = ChatFormatCode.AQUA_STRING + "Cold"
                    } else {
                        message = ChatFormatCode.BLUE_STRING + "Super Cold"
                    }
                }

                distanceDelta = 0.0

                a++
                if (a % 2 == 0) {
                    client.send(listener, SayCommand(SayCommand.SayCommandInput(message)))
                }

                try {
                    Thread.sleep(500)
                } catch (ex: InterruptedException) {
                    // Match the original empty catch block
                }
            }

            client.send(
                listener,
                SayCommand(SayCommand.SayCommandInput(ChatFormatCode.GREEN_STRING + "You won!"))
            )
            listener.finishGame(client)
        }

        fun setPlaying(playing: Boolean) {
            this.playing = playing
        }

        fun setPlayerPos(pos: BlockPos) {
            val distance = playerPos.distance(randomPos)
            this.playerPos = pos
            distanceDelta = distance - playerPos.distance(randomPos)
        }
    }
}
