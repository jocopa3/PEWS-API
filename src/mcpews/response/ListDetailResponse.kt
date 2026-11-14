package mcpews.response

import mcpews.message.MCResponse

open class ListDetailResponse : MCResponse() {

    private var details: String? = null
    private var currentPlayerCount: Int = 0
    private var maxPlayerCount: Int = 0
    private var players: String? = null

    open fun details(): String? {
        return details
    }

    open fun currentPlayerCount(): Int {
        return currentPlayerCount
    }

    open fun maxPlayerCount(): Int {
        return maxPlayerCount
    }

    open fun players(): String? {
        return players
    }

    override fun toString(): String {
        return returnAsJava(statusMessage)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> returnAsJava(value: Any?): T {
        return value as T
    }
}
