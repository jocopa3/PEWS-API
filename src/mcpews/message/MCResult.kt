package mcpews.message

open class MCResult {
    private var value: Int = 0

    open fun isSuccess(): Boolean {
        return value > -1
    }
}
