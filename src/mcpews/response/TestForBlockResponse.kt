package mcpews.response

import mcpews.mcenum.BlockPos
import mcpews.message.MCResponse

open class TestForBlockResponse : MCResponse() {

    @JvmField
    internal var position: BlockPos? = null

    @JvmField
    internal var matches: Boolean = false

    open fun getPosition(): BlockPos? {
        return position
    }

    open fun matches(): Boolean {
        return matches
    }
}
