package mcpews.logger;

import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleLogManager {

    public static final Logger logger = Logger.getLogger("PEWS-MessageLog");

    public static void init() {
        ConsoleLogFormatter clf = new ConsoleLogFormatter();
        logger.setUseParentHandlers(false);
        
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(clf);
        
        logger.addHandler(consolehandler);
        logger.setLevel(Level.FINEST);
        
        consolehandler.setLevel(Level.FINEST);

        /*
        try {
            FileHandler filehandler = new FileHandler("server.log", true);
            filehandler.setFormatter(clf);
            logger.addHandler(filehandler);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Failed to log to server.log", e);
        }
        */
    }
}
