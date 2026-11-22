package mcpews.event

import mcpews.mcenum.BlockPos
import mcpews.message.MCEvent

open class PlayerTravelledEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    open inner class PlayerTravelledMeasurements : EventMeasurements() {
        @JvmField var MetersTravelled: Double = 0.0
        @JvmField var NewBiome: Int = 0
        @JvmField var PosXAvg: Double = 0.0
        @JvmField var PosYAvg: Double = 0.0
        @JvmField var PosZAvg: Double = 0.0
    }

    open inner class PlayerTravelledProperties : EventProperties() {
        @JvmField var HasRelevantBuff: Boolean = false
        @JvmField var MobType: Int = 0
        @JvmField var TravelMethodType: Int = 0
        @JvmField var WorldFeature: Int = 0
    }

    open fun getTravelMethod(): Int = (properties as PlayerTravelledProperties).TravelMethodType

    open fun getAvgPos(): BlockPos {
        val m = measurements as PlayerTravelledMeasurements
        return BlockPos(m.PosXAvg, m.PosYAvg, m.PosZAvg)
    }

    open fun getMetersTravelled(): Double = (measurements as PlayerTravelledMeasurements).MetersTravelled

    override fun toString(): String = super.toString()
}
