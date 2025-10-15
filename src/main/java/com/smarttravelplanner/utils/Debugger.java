package com.smarttravelplanner.utils;

/**
 * Simple debugging utility class for logging and debugging
 */
public class Debugger {
    private static boolean debugMode = true;
    
    /**
     * Enable or disable debug mode
     * @param enabled true to enable debug mode, false to disable
     */
    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }
    
    /**
     * Print a debug message if debug mode is enabled
     * @param message the message to print
     */
    public static void log(String message) {
        if (debugMode) {
            System.out.println("[DEBUG] " + message);
        }
    }
    
    /**
     * Print a debug message with a value if debug mode is enabled
     * @param message the message to print
     * @param value the value to print
     */
    public static void log(String message, Object value) {
        if (debugMode) {
            System.out.println("[DEBUG] " + message + ": " + value);
        }
    }
    
    /**
     * Print an error message
     * @param message the error message to print
     */
    public static void error(String message) {
        System.err.println("[ERROR] " + message);
    }
    
    /**
     * Print an error message with an exception
     * @param message the error message to print
     * @param e the exception to print
     */
    public static void error(String message, Exception e) {
        System.err.println("[ERROR] " + message + ": " + e.getMessage());
        if (debugMode) {
            e.printStackTrace();
        }
    }
}