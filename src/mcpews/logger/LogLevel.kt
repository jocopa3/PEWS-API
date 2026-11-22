package mcpews.logger

import java.util.logging.Level

open class LogLevel(name: String, level: Int) : Level(name, level) {
    companion object {
        @JvmField
        val CHAT = LogLevel("CHAT", Level.INFO.intValue() + 5)

        @JvmField
        val DEBUG = LogLevel("DEBUG", Level.INFO.intValue() + 1)

        @JvmField
        val DINFO = LogLevel("DINFO", Level.INFO.intValue() + 2)

        @JvmField
        val DWARNING = LogLevel("DWARNING", Level.INFO.intValue() + 3)

        @JvmField
        val DSEVERE = LogLevel("DSEVERE", Level.INFO.intValue() + 4)
    }
}
