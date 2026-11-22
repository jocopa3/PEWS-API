package mcpews.mcenum

import java.util.HashMap

/**
 *
 * @author Jocopa3
 */
enum class EnchantType(
    private val nameValue: String,
    private val id: Int
) {
    PROTECTION("protection", 0),
    FIRE_PROTECTION("fire_protection", 1),
    FEATHER_FALLING("feather_falling", 2),
    BLAST_PROTECTION("blast_protection", 3),
    PROJECTILE_PROTECTION("projectile_protection", 4),
    THORNS("thorns", 5),
    RESPIRATION("respiration", 6),
    DEPTH_STRIDER("depth_strider", 7),
    AQUA_AFFINITY("aqua_affinity", 8),
    SHARPNESS("sharpness", 9),
    SMITE("smite", 10),
    BANE_OF_ARTHROPODS("bane_of_arthropods", 11),
    KNOCKBACK("knockback", 12),
    FIRE_ASPECT("fire_aspect", 13),
    LOOTING("looting", 14),
    EFFICIENCY("efficiency", 15),
    SILK_TOUCH("silk_touch", 16),
    DURABILITY("durability", 17),
    FORTUNE("fortune", 18),
    POWER("power", 19),
    PUNCH("punch", 20),
    FLAME("flame", 21),
    INFINITY("infinity", 22),
    LUCK_OF_THE_SEA("luck_of_the_sea", 23),
    LURE("lure", 24);

    fun getName(): String = nameValue

    fun getId(): Int = id

    companion object {
        private val fromString: HashMap<String, EnchantType> = HashMap()

        init {
            for (enchant in values()) {
                fromString[enchant.getName()] = enchant
            }
        }

        @JvmStatic
        fun fromString(name: String): EnchantType? = fromString[name]
    }
}
