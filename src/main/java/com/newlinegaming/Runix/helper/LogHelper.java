package com.newlinegaming.Runix.helper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.newlinegaming.Runix.lib.LibInfo;
import org.jetbrains.annotations.NotNull;

public class LogHelper {

    private static final Logger RLog = LogManager.getLogger(LibInfo.MOD_ID.toUpperCase());

    private static void log(Level logLevel, @NotNull Object obj) {
        RLog.log(logLevel, obj.toString());
    }

    public static void debug(@NotNull Object obj) {
        log(Level.DEBUG, obj.toString());
    }

    public static void warn(@NotNull Object obj) {
        log(Level.WARN, obj.toString());
    }

    public static void info(@NotNull Object obj) {
        log(Level.INFO, obj.toString());
    }

    public static void fatal(@NotNull Object obj) {
        log(Level.FATAL, obj.toString());
    }
}
