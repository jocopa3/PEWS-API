package mcpews.mcenum

import java.util.HashMap

/**
 *
 * @author Jocopa3
 */
enum class FeatureType(
    private val nameValue: String,
    private val id: Int
) {
    ENDCITY("endcity", 0),
    FORTRESS("fortress", 1),
    MINESHAFT("mineshaft", 2),
    MONUMENT("monument", 3),
    STRONGHOLD("stronghold", 4),
    TEMPLE("temple", 5),
    VILLAGE("village", 6);

    fun getName(): String = nameValue

    fun getId(): Int = id

    companion object {
        private val fromString: HashMap<String, FeatureType> = HashMap()

        init {
            for (feature in values()) {
                fromString[feature.getName()] = feature
            }
        }

        @JvmStatic
        fun fromString(name: String): FeatureType? = fromString[name]
    }
}
