package mcpews.message

import mcpews.command.CommandType

open class MCResponse protected constructor() : MCBody(MessagePurposeType.COMMAND_RESPONSE) {

    @JvmField
    protected var statusCode: Int = 0

    @JvmField
    protected var statusMessage: String? = null

    @JvmField
    @Transient
    protected var type: CommandType? = null

    protected open fun setCommandType(type: CommandType?) {
        this.type = type
    }

    /**
     * Kotlin-only accessor for [ResponseSerializer]. The original protected
     * [setCommandType] stays available to Java subclasses; this bridge is synthetic
     * so javac never sees it.
     */
    @JvmSynthetic
    internal fun assignCommandType(type: CommandType?) {
        setCommandType(type)
    }

    open fun getResponseType(): CommandType? {
        return type
    }

    open fun getStatusCode(): Int {
        return statusCode
    }

    open fun getStatusMessage(): String? {
        return statusMessage
    }

    override fun toString(): String {
        return statusMessage ?: ""
    }
}
