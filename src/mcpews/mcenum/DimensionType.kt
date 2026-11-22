package mcpews.mcenum

import java.util.HashMap

/**
 *
 * @author Jocopa3
 */
enum class DimensionType(
    private val nameValue: String,
    private val id: Int
) {
    OVERWORLD("overworld", 0),
    NETHER("nether", 1),
    END("end", 2);

    fun getName(): String = nameValue

    fun getId(): Int = id

    companion object {
        private val fromString: HashMap<String, DimensionType> = HashMap()

        init {
            for (dimension in values()) {
                fromString[dimension.getName()] = dimension
            }
        }

        @JvmStatic
        fun fromString(name: String): DimensionType? = fromString[name]
    }
}
