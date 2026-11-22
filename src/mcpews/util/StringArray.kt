package mcpews.util

open class StringArray(vararg strings: String) {
    @JvmField
    internal val arr: Array<out String> = strings

    override fun toString(): String = arr.joinToString(" ")
}
