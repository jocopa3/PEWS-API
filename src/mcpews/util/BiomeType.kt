package mcpews.util

enum class BiomeType(private val chatCode: String) {
    NEUTRAL(ChatFormatCode.WHITE_STRING),
    SNOWY(ChatFormatCode.BLUE_STRING),
    COLD(ChatFormatCode.GREEN_STRING),
    TEMPERATE(ChatFormatCode.GOLD_STRING),
    HOT(ChatFormatCode.RED_STRING);

    fun getChatColor(): String = chatCode
}
