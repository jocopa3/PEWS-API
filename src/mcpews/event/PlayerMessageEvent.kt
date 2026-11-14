package mcpews.event

import mcpews.message.MCEvent

class PlayerMessageEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    inner class PlayerMessageProperties : EventProperties() {
        @JvmField var Message: String? = null
        @JvmField var Sender: String? = null
    }

    fun getSender(): String? = (properties as? PlayerMessageProperties)?.Sender

    fun getMessage(): String? = (properties as? PlayerMessageProperties)?.Message

    override fun toString(): String = "${getSender()}: ${getMessage()}"
}
