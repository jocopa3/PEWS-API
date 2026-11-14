package mcpews.message

import mcpews.event.EventType

open class MCSubscribe(type: EventType) : MCBody(MessagePurposeType.SUBSCRIBE) {

    private var eventName: String? = type.getName()

    open fun getEventType(): EventType? {
        return EventType.fromString(eventName)
    }
}
