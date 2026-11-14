package mcpews.message

import mcpews.event.EventMeasurements
import mcpews.event.EventProperties
import mcpews.event.EventType

open class MCEvent protected constructor(
    type: EventType,
    properties: EventProperties?,
    measurements: EventMeasurements?
) : MCBody(MessagePurposeType.EVENT) {

    private val eventName: String? = type.getName()

    @JvmField
    protected var properties: EventProperties? = properties

    @JvmField
    protected var measurements: EventMeasurements? = measurements

    open fun getEventType(): EventType? {
        return EventType.fromString(eventName)
    }

    open fun getProperties(): EventProperties? {
        return properties
    }

    open fun getMeasurements(): EventMeasurements? {
        return measurements
    }

    override fun toString(): String {
        return ""
    }

    companion object {
        /**
         * The constructor is protected (Java subclasses in mcpews.event call super),
         * and [EventSerializer] lives in this package but is not a subclass, so the
         * base-event fallback goes through this synthetic, Java-invisible factory.
         */
        @JvmSynthetic
        internal fun create(
            type: EventType,
            properties: EventProperties?,
            measurements: EventMeasurements?
        ): MCEvent {
            return MCEvent(type, properties, measurements)
        }
    }
}
