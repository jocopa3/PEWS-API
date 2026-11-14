package mcpews.response

import mcpews.message.MCResponse

class ListDetailResponse : MCResponse() {
    private var details: String? = null
    private var currentPlayerCount: Int = 0
    private var maxPlayerCount: Int = 0
    private var players: String? = null

    fun details(): String? = details
    fun currentPlayerCount(): Int = currentPlayerCount
    fun maxPlayerCount(): Int = maxPlayerCount
    fun players(): String? = players

    override fun toString(): String = statusMessage ?: ""
}
