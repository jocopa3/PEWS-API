package mcpews.message

import mcpews.event.EventType

open class MCUnsubscribe(type: EventType) : MCBody(MessagePurposeType.UNSUBSCRIBE) {

    private var eventName: String? = type.getName()

    open fun getEventType(): EventType? {
        return EventType.fromString(eventName)
    }
}
