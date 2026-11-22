package mcpews.listeners

import java.util.Locale

import mcpews.MCClient
import mcpews.MCListener
import mcpews.command.SayCommand
import mcpews.command.SummonCommand
import mcpews.command.TimeSetCommand
import mcpews.command.TimeSetCommand.TimeOfDay
import mcpews.event.EventType
import mcpews.event.PlayerMessageEvent
import mcpews.mcenum.EntityType
import mcpews.message.MCEvent
import mcpews.message.MCMessage

open class CommandsListener : MCListener {

    override fun onConnected(client: MCClient) {
        client.subscribeToEvent(EventType.PLAYER_MESSAGE)
    }

    override fun onDisconnected(client: MCClient?) {
    }

    override fun onEvent(client: MCClient, eventMessage: MCMessage) {
        val event = eventMessage.getBody() as MCEvent

        when (event.getEventType()) {
            EventType.PLAYER_MESSAGE -> {
                if (event is PlayerMessageEvent) {
                    val message = event.getMessage()!!.lowercase(Locale.getDefault())

                    if (message == "blow me up" || message == "send me to space") {
                        client.send(this, SayCommand(SayCommand.SayCommandInput("Boom goes the dynamite")))
                        for (i in 0 until 100) {
                            val tnt = SummonCommand(SummonCommand.SummonCommandInput(EntityType.TNT))
                            client.send(this, tnt)
                        }
                        return
                    }

                    if (message.startsWith("spawn")) {
                        val entity = message.replaceFirst("spawn ", "")
                        val entityT = EntityType.fromString(entity)
                        if (entityT == null) {
                            client.send(
                                this,
                                SayCommand(
                                    SayCommand.SayCommandInput("\"" + entity + "\" is not a valid entity name")
                                )
                            )
                            return
                        }
                        client.send(this, SayCommand(SayCommand.SayCommandInput("Spawning " + entity)))
                        val tnt = SummonCommand(SummonCommand.SummonCommandInput(entityT))
                        client.send(this, tnt)
                        return
                    }

                    if (message == "day pls") {
                        client.send(this, SayCommand(SayCommand.SayCommandInput("Makin it day time")))
                        client.send(this, TimeSetCommand(TimeSetCommand.TimeSetByNameInput(TimeOfDay.day)))
                        return
                    }

                    if (message == "night pls") {
                        client.send(this, SayCommand(SayCommand.SayCommandInput("Makin it night time")))
                        client.send(this, TimeSetCommand(TimeSetCommand.TimeSetByNameInput(TimeOfDay.night)))
                        return
                    }

                    if (message == "all") {
                        client.send(this, SayCommand(SayCommand.SayCommandInput("Spawning all entities")))
                        for (type in EntityType.values()) {
                            val tnt = SummonCommand(SummonCommand.SummonCommandInput(type))
                            client.send(this, tnt)
                        }
                        return
                    }
                }
            }

            else -> {
            }
        }
    }

    override fun onResponse(client: MCClient, responseMessage: MCMessage, requestMessage: MCMessage) {
    }

    override fun onError(client: MCClient, errorMessage: MCMessage, requestMessage: MCMessage) {
    }
}
