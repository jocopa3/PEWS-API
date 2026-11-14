package mcpews.response

import mcpews.message.MCResponse

open class FillResponse : MCResponse() {

    private var blockName: String? = null
    private var fillCount: Int = 0

    open fun getBlockName(): String? {
        return blockName
    }

    open fun getFillCount(): Int {
        return fillCount
    }

    override fun toString(): String {
        return statusMessage ?: ""
    }
}
