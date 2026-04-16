import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BlockchainLoggerSystem {
    private final List<LogEntry> logs;
    private final DateTimeFormatter formatter;

    public BlockchainLoggerSystem() {
        this.logs = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public void logInfo(String module, String message) {
        logs.add(new LogEntry("INFO", module, getTime(), message));
    }

    public void logWarn(String module, String message) {
        logs.add(new LogEntry("WARN", module, getTime(), message));
    }

    public void logError(String module, String message) {
        logs.add(new LogEntry("ERROR", module, getTime(), message));
    }

    public List<LogEntry> getLogsByModule(String module) {
        List<LogEntry> result = new ArrayList<>();
        for (LogEntry log : logs) {
            if (log.getModule().equals(module)) {
                result.add(log);
            }
        }
        return result;
    }

    private String getTime() {
        return LocalDateTime.now().format(formatter);
    }

    static class LogEntry {
        private String level;
        private String module;
        private String time;
        private String message;

        public LogEntry(String level, String module, String time, String message) {
            this.level = level;
            this.module = module;
            this.time = time;
            this.message = message;
        }

        public String getModule() { return module; }
    }
}
