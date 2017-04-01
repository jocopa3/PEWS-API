package mcpews.util;

public enum BiomeType {
    NEUTRAL(ChatFormatCode.WHITE_STRING),
    SNOWY(ChatFormatCode.BLUE_STRING),
    COLD(ChatFormatCode.GREEN_STRING),
    TEMPERATE(ChatFormatCode.GOLD_STRING),
    HOT(ChatFormatCode.RED_STRING);

    private String chatCode;

    BiomeType(String code) {
        this.chatCode = code;
    }

    public String getChatColor() {
        return chatCode;
    }
}