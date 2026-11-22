package mcpews.message

import java.util.UUID

open class MCHeader {

    @JvmField
    protected var version: Int = 0

    @JvmField
    protected var requestId: String? = null

    @JvmField
    protected var messagePurpose: String? = null

    @JvmField
    protected var messageType: String? = null

    constructor(version: Int, requestId: UUID, purpose: MessagePurposeType) {
        this.version = version
        this.requestId = requestId.toString()
        this.messagePurpose = purpose.getPurposeName()
    }

    constructor(
        version: Int,
        requestId: UUID,
        purpose: MessagePurposeType,
        type: MessagePurposeType
    ) : this(version, requestId, purpose) {
        this.messageType = type.getPurposeName()
    }

    open fun getRequestId(): UUID {
        return UUID.fromString(requestId!!)
    }

    open fun getPurpose(): MessagePurposeType? {
        return MessagePurposeType.fromString(messagePurpose)
    }

    open fun getType(): MessagePurposeType? {
        return MessagePurposeType.fromString(messageType)
    }

    open fun getVersion(): Int {
        return version
    }
}
