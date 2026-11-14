package mcpews.response

import mcpews.message.MCResponse

class WSServerResponse : MCResponse() {
    private var requestedUri: String? = null

    fun getRequestedUri(): String? = requestedUri
}
