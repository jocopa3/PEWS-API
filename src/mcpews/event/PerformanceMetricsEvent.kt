package mcpews.event

import mcpews.message.MCEvent

open class PerformanceMetricsEvent(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?,
) : MCEvent(type, properties, measurements) {
    protected open inner class PerformanceMetricsMeasurements : EventMeasurements() {
        @JvmField protected var Message: String? = null
        @JvmField protected var Sender: String? = null
    }

    override fun toString(): String = ""
}
