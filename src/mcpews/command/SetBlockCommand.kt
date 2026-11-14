package mcpews.command

import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class SetBlockCommand(input: SetBlockInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.SETBLOCK.getName())
        setVersion(1)
    }

    enum class OldBlockHandling {
        destroy,
        keep,
        replace
    }

    open class SetBlockInput : CommandInput {

        private var position: BlockPos? = null
        private var tileName: String? = null
        private var tileData: Int = 0
        private var oldBlockHandling: String? = null

        constructor(pos: BlockPos?, block: BlockType?) : super() {
            position = pos
            val blockValue = block!!
            tileName = blockValue.getName()
            tileData = blockValue.getData()
        }

        constructor(pos: BlockPos?, block: BlockType?, blockHandling: OldBlockHandling) :
            this(pos, block, blockHandling.name)

        constructor(pos: BlockPos?, block: BlockType?, blockHandling: String?) : super() {
            position = pos
            val blockValue = block!!
            tileName = blockValue.getName()
            tileData = blockValue.getData()
            oldBlockHandling = blockHandling
        }

        constructor(pos: BlockPos?, blockId: String) : this(pos, BlockType.fromString(blockId))

        constructor(pos: BlockPos?, blockId: String, data: Int?) :
            this(pos, BlockType.fromString(blockId, data!!))

        constructor(pos: BlockPos?, blockId: String, handling: OldBlockHandling) :
            this(pos, BlockType.fromString(blockId), handling)

        constructor(pos: BlockPos?, blockId: String, data: Int?, handling: String?) :
            this(pos, BlockType.fromString(blockId, data!!), handling)
    }
}
