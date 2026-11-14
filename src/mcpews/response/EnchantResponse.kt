package mcpews.response

import mcpews.message.MCResponse

class EnchantResponse : MCResponse() {
    private var result: String? = null

    fun result(): String? = result

    override fun toString(): String = statusMessage ?: ""
}
