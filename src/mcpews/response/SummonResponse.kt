package mcpews.response

import java.util.Locale
import mcpews.mcenum.BlockPos
import mcpews.mcenum.EntityType
import mcpews.message.MCResponse

open class SummonResponse : MCResponse() {

    private var entityType: String? = null
    private var spawnPos: BlockPos? = null
    private var wasSpawned: Boolean = false

    open fun getEntityType(): EntityType? {
        return entityType?.let { EntityType.fromString(it) }
    }

    open fun getSpawnPos(): BlockPos? {
        return spawnPos
    }

    open fun wasSpawned(): Boolean {
        return wasSpawned
    }

    override fun toString(): String {
        if (statusCode != 0) {
            return returnAsJava(statusMessage)
        }

        return statusMessage!!.lowercase(Locale.getDefault()).replace("object", entityType!!)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> returnAsJava(value: Any?): T {
        return value as T
    }
}
