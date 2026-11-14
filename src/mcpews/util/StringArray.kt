package mcpews.util

class StringArray(vararg strings: String) {
    private val arr: Array<out String> = strings

    override fun toString(): String = arr.joinToString(" ")
}
