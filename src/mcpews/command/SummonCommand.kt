package mcpews.command

import mcpews.mcenum.BlockPos
import mcpews.mcenum.EntityType
import mcpews.message.MCCommand

/**
 *
 * @author Jocopa3
 */
open class SummonCommand(input: SummonCommandInput) : MCCommand() {

    init {
        setInput(input)
        setOrigin(BasicOrigin("player"))
        setName(CommandType.SUMMON.getName())
        setVersion(1)
    }

    open class SummonCommandInput : CommandInput {

        private var entityType: String? = null
        private var spawnPos: BlockPos? = null

        constructor(entity: String?) : super() {
            entityType = entity
        }

        constructor(entity: String?, pos: BlockPos?) : super() {
            entityType = entity
            spawnPos = pos
        }

        constructor(entity: EntityType?) : this(entity!!.getName())

        constructor(entity: EntityType?, pos: BlockPos?) : this(entity!!.getName(), pos)
    }
}
