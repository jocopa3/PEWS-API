package mcpews.message

abstract class MCBody(purpose: MessagePurposeType?) {

    // These fields are transient in the Java original: Gson walks superclass fields
    // too, and serializing the parent message would recurse forever.
    @Transient
    private val purpose: MessagePurposeType? = purpose

    @Transient
    private val parentMessage: MCMessage

    init {
        parentMessage = MCMessage(this)
    }

    protected open fun getPurpose(): MessagePurposeType? {
        return purpose
    }

    /**
     * Kotlin-only accessor used by [MCMessage]. The original protected [getPurpose]
     * stays available to Java subclasses; this bridge is synthetic so javac never
     * sees it.
     */
    @JvmSynthetic
    internal fun purposeForMessage(): MessagePurposeType? {
        return getPurpose()
    }

    open fun getAsMessage(): MCMessage {
        return parentMessage
    }
}
