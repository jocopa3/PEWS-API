package mcpews.response

import mcpews.mcenum.BlockPos
import mcpews.mcenum.EntityType
import mcpews.message.MCResponse

class SummonResponse : MCResponse() {
    private var entityType: String? = null
    private var spawnPos: BlockPos? = null
    private var wasSpawned: Boolean = false

    fun getEntityType(): EntityType? = EntityType.fromString(entityType)
    fun getSpawnPos(): BlockPos? = spawnPos
    fun wasSpawned(): Boolean = wasSpawned

    override fun toString(): String {
        if (statusCode != 0) return statusMessage ?: ""
        return (statusMessage ?: "").lowercase().replace("object", entityType ?: "")
    }
}
