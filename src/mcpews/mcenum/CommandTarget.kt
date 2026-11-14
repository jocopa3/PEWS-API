@file:Suppress("DEPRECATION")

package mcpews.mcenum

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.util.ArrayList
import java.util.HashMap
import java.util.regex.Pattern

/**
 *
 * @author Jocopa3
 */
class CommandTarget {

    enum class TargetSelector(
        private val type: String,
        private val selectorCode: Char
    ) {
        ALL_ENTITIES("allEntities", 'e'),
        ALL_PLAYERS("allPlayers", 'a'),
        NEAREST_PLAYER("nearestPlayer", 'p'),
        RANDOM_PLAYER("randomPlayer", 'r');

        fun getTypeString(): String = type

        fun getTypeCode(): Char = selectorCode

        companion object {
            private val fromCode: HashMap<Char, String> = HashMap()

            init {
                for (selector in values()) {
                    fromCode[selector.selectorCode] = selector.type
                }
            }

            @JvmStatic
            fun fromCode(code: Char): String? = fromCode[code]

            @JvmStatic
            fun fromCode(s: String): String? {
                if (s.startsWith("@")) {
                    return fromCode(s[1])
                }

                return null
            }
        }
    }

    enum class RuleType(
        @Transient internal val description: String
    ) {
        NAME("Name"), // Entity/Player name
        type("Type"), // Entity type
        r("Max Radius"), // Max Radius
        rm("Min Radius"), // Min Radius
        m("Game Mode"), // Gamemode
        x("X-Pos"), // X-pos
        y("Y-Pos"), // Y-pos
        z("Z-Pos"); // Z-pos

        // Keep the original string form for the renamed NAME constant.
        override fun toString(): String {
            return if (this === NAME) "name" else this.name
        }

        companion object {
            // Java enums may have a constant literally named "name"; Kotlin enums cannot
            // (it conflicts with Enum.name). Keep the Java-visible RuleType.name constant
            // as a static alias for the renamed NAME entry.
            @JvmField
            val name: RuleType = NAME

            private val fromString: HashMap<String, RuleType> = HashMap()

            init {
                fromString["name"] = NAME
                for (rule in values()) {
                    if (rule !== NAME) {
                        fromString[rule.name] = rule
                    }
                }
            }

            @JvmStatic
            fun fromString(type: String): RuleType? = fromString[type]
        }
    }

    inner class TargetRule(
        private val name: String,
        private val value: String
    ) {

        private var inverted: Boolean = false

        constructor(name: String, value: String, inverted: Boolean) : this(name, value) {
            this.inverted = inverted
        }

        fun getRule(): RuleType? {
            return RuleType.fromString(name)
        }

        override fun toString(): String {
            val rule = getRule()!!
            var text = rule.description + (if (inverted) " != " else " = ")

            when (rule) {
                RuleType.m -> {
                    text += when (value) {
                        "0", "s" -> "Survival"
                        "1", "c" -> "Creative"
                        "2", "a" -> "Adventure"
                        else -> "Invalid"
                    }
                }
                else -> text += value
            }

            return text
        }
    }

    private var selector: String
    private var rules: ArrayList<TargetRule>? = null

    constructor(targetString: String) {

        val selected = TargetSelector.fromCode(targetString)

        if (selected == null) {
            selector = TargetSelector.ALL_PLAYERS.getTypeString()
            addRule(RuleType.name, targetString)
        } else {
            selector = selected
            val targetSpecifiers = targetString.replaceFirst(Regex("@[aeprAEPR]"), "")

            if (targetSpecifiers.isNotEmpty()) {
                val specifiers = innerQuotes.matcher(outerQuotes.matcher(targetSpecifiers).replaceAll("\""))
                    .replaceAll("\":\"")
                    .replace("[", "{")
                    .replace("]", "}")

                try {
                    val ele: JsonObject = parser.parse(specifiers).asJsonObject

                    for (entry in ele.entrySet()) {
                        val value = entry.value.asString
                        if (value.startsWith("!")) {
                            addRule(entry.key, value.substring(value.indexOf('!') + 1), true)
                        } else {
                            addRule(entry.key, value)
                        }
                    }
                } catch (e: Exception) {
                    rules = null
                }
            } else {
                rules = null
            }
        }
    }

    constructor(selector: TargetSelector, vararg rules: TargetRule) {
        this.selector = selector.getTypeString()

        for (rule in rules) {
            addRule(rule)
        }
    }

    fun addRule(rule: TargetRule) {
        if (rules == null) {
            rules = ArrayList()
        }

        rules!!.add(rule)
    }

    fun addRule(name: String, value: String) {
        addRule(TargetRule(name, value))
    }

    fun addRule(name: String, value: String, inverted: Boolean) {
        addRule(TargetRule(name, value, inverted))
    }

    fun addRule(type: RuleType, value: String) {
        addRule(TargetRule(ruleKey(type), value))
    }

    fun addRule(type: RuleType, value: String, inverted: Boolean) {
        addRule(TargetRule(ruleKey(type), value, inverted))
    }

    fun getSelector(): String {
        return selector
    }

    fun getRules(): Array<TargetRule> {
        return rules!!.toTypedArray()
    }

    override fun toString(): String {
        val b = StringBuilder()
        b.append("Selector: ").append(selector).append("; Rules: ")
        val rules = rules!!
        var i = 0
        while (i < rules.size - 1) {
            b.append(rules[0].toString()).append(", ")
            i++
        }

        b.append(rules[i].toString())

        return b.toString()
    }

    companion object {
        private val parser = JsonParser()
        private val outerQuotes = Pattern.compile("(((?<=[\\[,])\\s*)|(?<=[a-zA-Z0-9_])\\s*(?=[,\\]]))")
        private val innerQuotes = Pattern.compile("\\s*=\\s*")

        // RuleType.NAME stands in for the Java constant originally named "name".
        private fun ruleKey(type: RuleType): String {
            return if (type === RuleType.NAME) "name" else type.name
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val target1 = "@e[type=!sheep,r=10]"
            val target2 = "@p[m=s]"

            val tar1 = CommandTarget(target1)
            val tar2 = CommandTarget(target2)

            println("Target 1:\n" + tar1)
            println("Target 2:\n" + tar2)
        }
    }
}
