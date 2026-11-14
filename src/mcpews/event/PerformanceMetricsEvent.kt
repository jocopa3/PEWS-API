package mcpews.event

import mcpews.message.MCEvent

class PerformanceMetricsEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    protected inner class PerformanceMetricsMeasurements : EventMeasurements() {
        @JvmField protected var Message: String? = null
        @JvmField protected var Sender: String? = null
    }

    override fun toString(): String = ""
}
