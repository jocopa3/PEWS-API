package mcpews.event

import mcpews.mcenum.BlockPos
import mcpews.message.MCEvent

class PlayerTransformEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    inner class PlayerTransformProperties : EventProperties() {
        @JvmField var PlayerYRot: Double = 0.0
        @JvmField var PosX: Double = 0.0
        @JvmField var PosY: Double = 0.0
        @JvmField var PosZ: Double = 0.0
    }

    fun getPosition(): BlockPos {
        val props = properties as PlayerTransformProperties
        return BlockPos(props.PosX, props.PosY, props.PosZ)
    }

    override fun toString(): String = getPosition().toString()
}
