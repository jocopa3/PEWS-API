package mcpews.response

import mcpews.message.MCResponse

open class EnchantResponse : MCResponse() {

    private var result: String? = null

    open fun result(): String? {
        return result
    }

    override fun toString(): String {
        return returnAsJava(statusMessage)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> returnAsJava(value: Any?): T {
        return value as T
    }
}
