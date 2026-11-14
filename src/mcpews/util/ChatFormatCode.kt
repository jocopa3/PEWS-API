package mcpews.util

open class ChatFormatCode {
    companion object {
        const val ESCAPE_CHAR: Char = '\u00a7'
        const val ESCAPE_STRING: String = "\u00a7"

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

        const val BLACK_STRING: String = ESCAPE_STRING + "0"
        const val DARK_BLUE_STRING: String = ESCAPE_STRING + "1"
        const val DARK_GREEN_STRING: String = ESCAPE_STRING + "2"
        const val DARK_AQUA_STRING: String = ESCAPE_STRING + "3"
        const val DARK_RED_STRING: String = ESCAPE_STRING + "4"
        const val DARK_PURPLE_STRING: String = ESCAPE_STRING + "5"
        const val GOLD_STRING: String = ESCAPE_STRING + "6"
        const val GRAY_STRING: String = ESCAPE_STRING + "7"
        const val DARK_GRAY_STRING: String = ESCAPE_STRING + "8"
        const val BLUE_STRING: String = ESCAPE_STRING + "9"
        const val GREEN_STRING: String = ESCAPE_STRING + "a"
        const val AQUA_STRING: String = ESCAPE_STRING + "b"
        const val RED_STRING: String = ESCAPE_STRING + "c"
        const val LIGHT_PURPLE_STRING: String = ESCAPE_STRING + "d"
        const val YELLOW_STRING: String = ESCAPE_STRING + "e"
        const val WHITE_STRING: String = ESCAPE_STRING + "f"

        const val OBFUSCATED_STRING: String = ESCAPE_STRING + "k"
        const val BOLD_STRING: String = ESCAPE_STRING + "l"
        const val STRIKETHROUGH_STRING: String = ESCAPE_STRING + "m"
        const val UNDERLINE_STRING: String = ESCAPE_STRING + "n"
        const val ITALIC_STRING: String = ESCAPE_STRING + "o"
        const val RESET_STRING: String = ESCAPE_STRING + "r"

        @JvmStatic
        fun formatString(format: String, s: String): String = s.replace(format.toRegex(), ESCAPE_STRING)
    }
}
