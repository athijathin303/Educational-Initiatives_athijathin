package com.example.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton class for centralized application logging.
 * Logs critical information and errors to the console.
 * In a production environment, this would write to a file or remote service.
 */
public class Logger {
    
    // 1. Singleton Instance
    private static Logger instance;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Private constructor prevents external instantiation
    private Logger() {}

    // Static method to get the single instance
    public static Logger getInstance() {
        if (instance == null) {
            // Basic thread safety (double-checked locking is more robust, but simple check suffices here)
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // 2. Logging Methods
    
    private void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println(String.format("[%s] %s - %s", level, timestamp, message));
    }

    public void logInfo(String message) {
        log("INFO", message);
    }

    public void logError(String message) {
        log("ERROR", message);
    }

    public void logError(String message, Throwable t) {
        log("ERROR", message + " | Exception: " + t.getClass().getSimpleName() + " | Message: " + t.getMessage());
    }
}