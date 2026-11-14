package mcpews.event

import mcpews.mcenum.BlockPos
import mcpews.message.MCEvent

class PlayerTravelledEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    inner class PlayerTravelledMeasurements : EventMeasurements() {
        @JvmField var MetersTravelled: Double = 0.0
        @JvmField var NewBiome: Int = 0
        @JvmField var PosXAvg: Double = 0.0
        @JvmField var PosYAvg: Double = 0.0
        @JvmField var PosZAvg: Double = 0.0
    }

    inner class PlayerTravelledProperties : EventProperties() {
        @JvmField var HasRelevantBuff: Boolean = false
        @JvmField var MobType: Int = 0
        @JvmField var TravelMethodType: Int = 0
        @JvmField var WorldFeature: Int = 0
    }

    fun getTravelMethod(): Int = (properties as PlayerTravelledProperties).TravelMethodType

    fun getAvgPos(): BlockPos {
        val m = measurements as PlayerTravelledMeasurements
        return BlockPos(m.PosXAvg, m.PosYAvg, m.PosZAvg)
    }

    fun getMetersTravelled(): Double = (measurements as PlayerTravelledMeasurements).MetersTravelled

    override fun toString(): String = super.toString()
}
