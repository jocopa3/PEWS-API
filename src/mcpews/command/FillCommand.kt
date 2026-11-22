package mcpews.command

import mcpews.mcenum.BlockPos
import mcpews.mcenum.BlockType
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class FillCommand(input: FillCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.FILL.getName())
        setVersion(1)
    }

    enum class OldBlockHandling {
        destroy,
        hollow,
        keep,
        outline,
        replace
    }

    open class FillCommandInput : CommandInput {

        private var from: BlockPos? = null
        private var to: BlockPos? = null
        private var tileName: String? = null
        private var tileData: Int? = null
        private var oldBlockHandling: String? = null
        private var replaceTileName: String? = null
        private var replaceDataValue: Int? = null

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?) : super() {
            overloadName = "byName"
            this.from = from
            this.to = to
            this.tileName = tileName
        }

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?) : super() {
            overloadName = "byName"
            this.from = from
            this.to = to
            this.tileName = tileName
            this.tileData = tileData
        }

        constructor(from: BlockPos?, to: BlockPos?, block: BlockType?) :
            this(from, to, block!!.getName(), block!!.getData())

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?, oldBlockHandling: String?) :
            this(from, to, tileName, tileData) {
            this.oldBlockHandling = oldBlockHandling
        }

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?, oldBlockHandling: OldBlockHandling) :
            this(from, to, tileName, tileData, oldBlockHandling.name)

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?, oldBlockHandling: OldBlockHandling, replaceTileName: String?) :
            this(from, to, tileName, tileData, oldBlockHandling.name) {
            this.replaceTileName = replaceTileName
        }

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?, oldBlockHandling: String?, replaceTileName: String?) :
            this(from, to, tileName, tileData, oldBlockHandling) {
            this.replaceTileName = replaceTileName
        }

        constructor(from: BlockPos?, to: BlockPos?, tileName: String?, tileData: Int?, oldBlockHandling: OldBlockHandling, replaceTileName: String?, replaceDataValue: Int) :
            this(from, to, tileName, tileData, oldBlockHandling.name, replaceTileName) {
            this.replaceDataValue = replaceDataValue
        }
    }
}
