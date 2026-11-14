package mcpews.command

import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class TestForBlockCommand(input: TestForBlockInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.TESTFORBLOCK.getName())
        setVersion(1)
    }

    open class TestForBlockInput : CommandInput {

        private var position: BlockPos? = null
        private var tileName: String? = null
        private var dataValue: Int = 0

        constructor(pos: BlockPos?, block: String?) : this(pos, block, 0)

        constructor(pos: BlockPos?, block: String?, data: Int?) : super() {
            position = pos
            tileName = block
            dataValue = data!!
        }

        constructor(pos: BlockPos?, block: BlockType?) : this(pos, block!!.getName(), block!!.getData())
    }
}
