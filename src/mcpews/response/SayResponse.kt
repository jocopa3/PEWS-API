package mcpews.response

import mcpews.message.MCResponse

class SayResponse : MCResponse() {
    private var message: String? = null

    fun getMessage(): String? = message

    override fun toString(): String = message ?: ""
}
