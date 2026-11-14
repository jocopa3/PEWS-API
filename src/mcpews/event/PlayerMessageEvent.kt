package mcpews.event

import mcpews.message.MCEvent

open class PlayerMessageEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    open inner class PlayerMessageProperties : EventProperties() {
        @JvmField var Message: String? = null
        @JvmField var Sender: String? = null
    }

    open fun getSender(): String? = (properties as PlayerMessageProperties).Sender

    open fun getMessage(): String? = (properties as PlayerMessageProperties).Message

    override fun toString(): String = "${getSender()}: ${getMessage()}"
}
