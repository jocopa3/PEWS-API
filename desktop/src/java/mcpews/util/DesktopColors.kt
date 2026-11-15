package mcpews.util

import java.awt.Color

/** 桌面 Swing 日志着色用的调色板；Android 构建不包含这个源集。 */
class DesktopColors private constructor() {
    companion object {
        @JvmField
        val BLACK: Color = Color(0, 0, 0)

        @JvmField
        val DARK_BLUE: Color = Color(0, 0, 170)

        @JvmField
        val DARK_GREEN: Color = Color(0, 170, 0)

        @JvmField
        val DARK_AQUA: Color = Color(0, 170, 170)

        @JvmField
        val DARK_RED: Color = Color(170, 0, 0)

        @JvmField
        val DARK_PURPLE: Color = Color(170, 0, 170)

        @JvmField
        val GOLD: Color = Color(255, 170, 0)

        @JvmField
        val GRAY: Color = Color(170, 170, 170)

        @JvmField
        val DARK_GRAY: Color = Color(85, 85, 85)

        @JvmField
        val BLUE: Color = Color(85, 85, 255)

        @JvmField
        val GREEN: Color = Color(85, 255, 85)

        @JvmField
        val AQUA: Color = Color(85, 255, 255)

        @JvmField
        val RED: Color = Color(255, 85, 85)

        @JvmField
        val LIGHT_PURPLE: Color = Color(255, 85, 255)

        @JvmField
        val YELLOW: Color = Color(255, 255, 85)

        @JvmField
        val WHITE: Color = Color(255, 255, 255)
    }
}
