package mcpews.response

import mcpews.message.MCResponse

open class SayResponse : MCResponse() {

    private var message: String? = null

    open fun getMessage(): String? {
        return message
    }

    override fun toString(): String {
        return returnAsJava(message)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> returnAsJava(value: Any?): T {
        return value as T
    }
}
