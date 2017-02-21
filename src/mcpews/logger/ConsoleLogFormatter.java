package mcpews.logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

final class ConsoleLogFormatter extends Formatter {

    private SimpleDateFormat dateFormat;

    ConsoleLogFormatter() {
        dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
    }

    @Override
    public String format(LogRecord logrecord) {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(dateFormat.format(logrecord.getMillis()));

        Level level = logrecord.getLevel();
        stringbuilder.append(" [").append(level.getName().toUpperCase()).append("]: ");

        stringbuilder.append(logrecord.getMessage());
        stringbuilder.append('\n');

        Throwable throwable = logrecord.getThrown();
        if (throwable != null) {
            StringWriter stringwriter = new StringWriter();
            throwable.printStackTrace(new PrintWriter(stringwriter));
            stringbuilder.append(stringwriter.toString());
        }

        return stringbuilder.toString();
    }

}
