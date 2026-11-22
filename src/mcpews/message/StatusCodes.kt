package mcpews.message

enum class StatusCodes(@JvmField internal var value: Int) {
    SUCCESS(0),
    PROCESSING_COMMAND(131075),
    TOO_MANY_REQUESTS(-2147418109)
}
