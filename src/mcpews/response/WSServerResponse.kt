package mcpews.response

import mcpews.message.MCResponse

open class WSServerResponse : MCResponse() {

    private var requestedUri: String? = null

    open fun getRequestedUri(): String? {
        return requestedUri
    }
}
