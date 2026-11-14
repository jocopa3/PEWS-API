package mcpews.util

class ChatFormatCode private constructor() {
    companion object {
        const val ESCAPE_CHAR: Char = '\u00a7'
        @JvmField
        val ESCAPE_STRING: String = ESCAPE_CHAR.toString()

        const val BLACK_CHAR: Char = '0'
        const val DARK_BLUE_CHAR: Char = '1'
        const val DARK_GREEN_CHAR: Char = '2'
        const val DARK_AQUA_CHAR: Char = '3'
        const val DARK_RED_CHAR: Char = '4'
        const val DARK_PURPLE_CHAR: Char = '5'
        const val GOLD_CHAR: Char = '6'
        const val GRAY_CHAR: Char = '7'
        const val DARK_GRAY_CHAR: Char = '8'
        const val BLUE_CHAR: Char = '9'
        const val GREEN_CHAR: Char = 'a'
        const val AQUA_CHAR: Char = 'b'
        const val RED_CHAR: Char = 'c'
        const val LIGHT_PURPLE_CHAR: Char = 'd'
        const val YELLOW_CHAR: Char = 'e'
        const val WHITE_CHAR: Char = 'f'

        const val OBFUSCATED_CHAR: Char = 'k'
        const val BOLD_CHAR: Char = 'l'
        const val STRIKETHROUGH_CHAR: Char = 'm'
        const val UNDERLINE_CHAR: Char = 'n'
        const val ITALIC_CHAR: Char = 'o'
        const val RESET_CHAR: Char = 'r'

        @JvmField val BLACK_STRING = ESCAPE_STRING + "0"
        @JvmField val DARK_BLUE_STRING = ESCAPE_STRING + "1"
        @JvmField val DARK_GREEN_STRING = ESCAPE_STRING + "2"
        @JvmField val DARK_AQUA_STRING = ESCAPE_STRING + "3"
        @JvmField val DARK_RED_STRING = ESCAPE_STRING + "4"
        @JvmField val DARK_PURPLE_STRING = ESCAPE_STRING + "5"
        @JvmField val GOLD_STRING = ESCAPE_STRING + "6"
        @JvmField val GRAY_STRING = ESCAPE_STRING + "7"
        @JvmField val DARK_GRAY_STRING = ESCAPE_STRING + "8"
        @JvmField val BLUE_STRING = ESCAPE_STRING + "9"
        @JvmField val GREEN_STRING = ESCAPE_STRING + "a"
        @JvmField val AQUA_STRING = ESCAPE_STRING + "b"
        @JvmField val RED_STRING = ESCAPE_STRING + "c"
        @JvmField val LIGHT_PURPLE_STRING = ESCAPE_STRING + "d"
        @JvmField val YELLOW_STRING = ESCAPE_STRING + "e"
        @JvmField val WHITE_STRING = ESCAPE_STRING + "f"

        @JvmField val OBFUSCATED_STRING = ESCAPE_STRING + "k"
        @JvmField val BOLD_STRING = ESCAPE_STRING + "l"
        @JvmField val STRIKETHROUGH_STRING = ESCAPE_STRING + "m"
        @JvmField val UNDERLINE_STRING = ESCAPE_STRING + "n"
        @JvmField val ITALIC_STRING = ESCAPE_STRING + "o"
        @JvmField val RESET_STRING = ESCAPE_STRING + "r"

        @JvmStatic
        fun formatString(format: String, s: String): String = s.replace(format.toRegex(), ESCAPE_STRING)
    }
}
