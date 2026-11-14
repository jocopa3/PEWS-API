package mcpews.response

import mcpews.message.MCResponse

class FillResponse : MCResponse() {
    private var blockName: String? = null
    private var fillCount: Int = 0

    fun getBlockName(): String? = blockName
    fun getFillCount(): Int = fillCount

    override fun toString(): String = statusMessage ?: ""
}
