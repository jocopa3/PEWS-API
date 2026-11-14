package mcpews.mcenum

import java.util.HashMap

/**
 *
 * @author Jocopa3
 */
enum class GameRuleType(
    private val nameValue: String
) {
    FALL_DAMAGE("falldamage"),
    PVP("pvp"),
    DROWNING_DAMAGE("drowningdamage"),
    FIRE_DAMAGE("firedamage"),
    IMMUTABLE_WORLD("immutableworld");

    fun getName(): String = nameValue

    companion object {
        private val fromString: HashMap<String, GameRuleType> = HashMap()

        init {
            for (gameRule in values()) {
                fromString[gameRule.getName()] = gameRule
            }
        }

        @JvmStatic
        fun fromString(name: String): GameRuleType? = fromString[name]
    }
}
