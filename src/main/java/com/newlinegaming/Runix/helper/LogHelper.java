package com.newlinegaming.Runix.helper;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.newlinegaming.Runix.lib.LibInfo;

public class LogHelper {

    private static final Logger RLog = LogManager.getLogger(LibInfo.MOD_ID);

    public static void log(Level logLevel, Object obj) {
        RLog.log(logLevel, obj.toString());
    }

    public static void debug(Object obj) {
        log(Level.DEBUG, obj.toString());
    }

    public static void warn(Object obj) {
        log(Level.WARN, obj.toString());
    }

    public static void info(Object obj) {
        log(Level.INFO, obj.toString());
    }

    public static void fatal(Object obj) {
        log(Level.INFO, obj.toString());
    }
}
