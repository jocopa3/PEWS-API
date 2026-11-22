package mcpews.event

import mcpews.mcenum.BlockPos
import mcpews.message.MCEvent

open class PlayerTransformEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    open inner class PlayerTransformProperties : EventProperties() {
        @JvmField var PlayerYRot: Double = 0.0
        @JvmField var PosX: Double = 0.0
        @JvmField var PosY: Double = 0.0
        @JvmField var PosZ: Double = 0.0
    }

    open fun getPosition(): BlockPos {
        val props = properties as PlayerTransformProperties
        return BlockPos(props.PosX, props.PosY, props.PosZ)
    }

    override fun toString(): String = getPosition().toString()
}
