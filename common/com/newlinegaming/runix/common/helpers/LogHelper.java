package com.newlinegaming.runix.common.helpers;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.newlinegaming.runix.common.lib.LibRef;

public class LogHelper {
	
	private static final Logger RLog = LogManager.getLogger(LibRef.MOD_ID);
	
	public static void log(Level logLevel, Object obj) {
		RLog.log(logLevel, obj.toString());
	}
	
	public static void debug(Object obj){
		log(Level.DEBUG, obj.toString());
	}
	

}
