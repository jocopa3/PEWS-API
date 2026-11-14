package mcpews.response

import mcpews.message.MCResponse

class ListResponse : MCResponse() {
    private var currentPlayerCount: Int = 0
    private var maxPlayerCount: Int = 0
    private var players: String? = null

    fun currentPlayerCount(): Int = currentPlayerCount
    fun maxPlayerCount(): Int = maxPlayerCount
    fun players(): String? = players

    override fun toString(): String = statusMessage ?: ""
}
