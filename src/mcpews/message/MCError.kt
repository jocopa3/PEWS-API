package mcpews.message

open class MCError protected constructor() : MCBody(MessagePurposeType.ERROR) {

    @JvmField
    internal var statusMessage: String? = null

    @JvmField
    internal var statusCode: Int = 0

    open fun getStatusMessage(): String? {
        return statusMessage
    }

    open fun getStatusCode(): Int {
        return statusCode
    }

    override fun toString(): String {
        return "Error ($statusCode): $statusMessage"
    }
}
