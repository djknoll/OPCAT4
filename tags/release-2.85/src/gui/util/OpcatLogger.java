package gui.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import exportedAPI.util.OpcatApiException;
import gui.util.debug.Debug;

/**
 * Logs messages into a designated log file.
 * @author Eran Toch
 *
 */
public class OpcatLogger {
	static private boolean debug = true;
	
	/**
	 * Logs an error message to the system.err output.
	 */
	public static void logError(String message)	{
		Debug debug = Debug.getInstance() ; 
		debug.Print(message, message, "1") ; 
		
		System.err.println(getFormattedTime());
		System.err.println(message);
		System.err.println("---");
	}
	
	/**
	 * Logs a string message to the standard output. 
	 */
	public static void debug(String message)	{
		System.out.println(message);
	}
	
	private static String getFormattedTime()	{
		GregorianCalendar rightNow = new GregorianCalendar();
	    Date d = rightNow.getTime();
	    DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	    String thetime = df.format(d);
	    return thetime;
	}
	
	/**
	 * Logs an error by printing it's message and the stack trace to an "error.log"
	 * file and to the standard error output.
	 */
	public static void logError(Exception ex)	{
		
		Debug debugGrid = Debug.getInstance() ; 
		debugGrid.Print(ex, ex.getLocalizedMessage(), "1") ; 
		
		try	{
			FileOutputStream errorLog =
				new FileOutputStream("error.log" ,true);
			PrintStream ps = new PrintStream(errorLog);
			ps.println(getFormattedTime());
			ps.println(ex);
			ex.printStackTrace(ps);
			ps.println("---");
			ps.flush();
			ps.close();
		} catch (IOException ioe) {}
		if (debug)	{
			System.err.println(ex);
			ex.printStackTrace(System.err);
		}
	}
	
	/**
	 * Logs an OPM error by printing it's message and the stack trace to an "error.log"
	 * file and to the standard error output.
	 */
	public static void logError(OpcatApiException ex)	{
		logError((Exception)ex);
	}
	
	/**
	 * Logs a general message.
	 */
	public static void logMessage(String message)	{
		System.out.println(message);
	}
	/**
	 * @return Returns the debug.
	 */
	public static boolean isDebug() {
		return debug;
	}
	/**
	 * @param debug The debug to set.
	 */
	public static void setDebug(boolean debug) {
		OpcatLogger.debug = debug;
	}
}
