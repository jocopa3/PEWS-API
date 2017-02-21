/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcpews.logger;

import java.util.logging.Level;

/**
 *
 * @author Jocopa3
 */
public class LogLevel extends Level {

    public static final LogLevel CHAT = new LogLevel("CHAT", Level.INFO.intValue() + 5);
    public static final LogLevel DEBUG = new LogLevel("DEBUG", Level.INFO.intValue() + 1);
    public static final LogLevel DINFO = new LogLevel("DINFO", Level.INFO.intValue() + 2);
    public static final LogLevel DWARNING = new LogLevel("DWARNING", Level.INFO.intValue() + 3);
    public static final LogLevel DSEVERE = new LogLevel("DSEVERE", Level.INFO.intValue() + 4);

    public LogLevel(String name, int level) {
        super(name, level);
    }
}
