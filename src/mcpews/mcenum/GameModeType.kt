package mcpews.mcenum

import java.util.HashMap

/**
 *
 * @author Jocopa3
 */
enum class GameModeType(
    private val nameValue: String,
    private val id: Int
) {
    ALL("a", -1),
    SURVIVAL("s", 0),
    CREATIVE("c", 1);

    fun getName(): String = nameValue

    fun getId(): Int = id

    companion object {
        private val fromString: HashMap<String, GameModeType> = HashMap()

        init {
            for (mode in values()) {
                fromString[mode.getName()] = mode
            }
        }

        @JvmStatic
        fun fromString(name: String): GameModeType? = fromString[name]
    }
}
