package exportedAPI.util;

import gui.util.OpcatLogger;

/**
 * Enables logging utilities for extension tools. The class enable logging for error 
 * handling and debugging. Opcat logging might be blocked in production versions, 
 * but will always work in debug versions.
 * @author Eran Toch
 */
public class APILogger {

	/**
	 * Logs a text message to the system.err output.  
	 * @param message The error message to be displayed.
	 */
	public static void logError(String message)	{
		OpcatLogger.logError(message);
	}
	
	/**
	 * Logs an OPM error by printing it's message and the stack trace to an "error.log"
	 * file and to the standard error output.
	 */
	public static void logError(OpcatApiException oex)	{
		OpcatLogger.logError(oex);
	}

}
