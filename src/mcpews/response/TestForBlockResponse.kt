package mcpews.response

import mcpews.mcenum.BlockPos
import mcpews.message.MCResponse

class TestForBlockResponse : MCResponse() {
    @JvmField var position: BlockPos? = null
    @JvmField var matches: Boolean = false

    fun getPosition(): BlockPos? = position
    fun matches(): Boolean = matches
}
