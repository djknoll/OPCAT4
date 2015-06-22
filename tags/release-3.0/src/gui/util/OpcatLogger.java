package gui.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import exportedAPI.util.OpcatApiException;
import gui.controls.FileControl;
import gui.util.debug.Debug;

/**
 * Logs messages into a designated log file.
 * 
 * @author Eran Toch
 * 
 */
public class OpcatLogger {

	/**
	 * Logs an error message to the system.err output.
	 */
	public static void logError(String message) {
		if (Debug.getDebugLevel() > 0) {
			Debug debug = Debug.getInstance();
			debug.Print(message, message, "1");

		} else {
			try {
				FileControl file = FileControl.getInstance();
				FileOutputStream errorLog = new FileOutputStream(file
						.getOPCATDirectory()
						+ FileControl.fileSeparator + "error.log", true);
				PrintStream ps = new PrintStream(errorLog);
				ps.println("---------------------------------------------");
				ps.println(getFormattedTime());
				ps.println("Error :" + message);
				ps.println("----------------------------------------------");
				ps.flush();
				ps.close();
			} catch (IOException ioe) {
			}
		}

		System.err.println("---------------------------------------------");
		System.err.println(getFormattedTime());
		System.err.println("Error :" + message);
		System.err.println("---------------------------------------------");
	}

	private static String getFormattedTime() {
		GregorianCalendar rightNow = new GregorianCalendar();
		Date d = rightNow.getTime();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.MEDIUM);
		String thetime = df.format(d);
		return thetime;
	}

	/**
	 * Logs an error by printing it's message and the stack trace to an
	 * "error.log" file and to the standard error output.
	 */
	public static void logError(Exception ex) {

		if (Debug.getDebugLevel() > 0) {
			Debug debug = Debug.getInstance();
			debug.Print(ex, getFormattedTime() + "," + ex.getMessage(), "1");
			debug.Print(ex, getFormattedTime() + "," + ex.getCause(), "1");

			ex.printStackTrace();
		} else {

			try {
				FileControl file = FileControl.getInstance();
				FileOutputStream errorLog = new FileOutputStream(file
						.getOPCATDirectory()
						+ FileControl.fileSeparator + "error.log", true);
				PrintStream ps = new PrintStream(errorLog);
				ps.println("---------------------------------------------");
				ps.println(getFormattedTime());
				ps.println(ex);
				ex.printStackTrace(ps);
				ps.println("----------------------------------------------");
				ps.flush();
				ps.close();
			} catch (IOException ioe) {
			}
		}

		System.err.println("---------------------------------------------");
		System.err.println(getFormattedTime());
		System.err.println(ex);
		ex.printStackTrace(System.err);
		System.err.println("----------------------------------------------");
	}

	/**
	 * Logs an OPM error by printing it's message and the stack trace to an
	 * "error.log" file and to the standard error output.
	 */
	public static void logError(OpcatApiException ex) {
		logError((Exception) ex);
	}

	/**
	 * Logs a general message.
	 */
	public static void logMessage(String message) {
		if (Debug.getDebugLevel() > 0) {
			Debug debug = Debug.getInstance();
			debug.Print(message, message, "1");
		} 
		
		System.out.println("---------------------------------------------");
		System.out.println(getFormattedTime());
		System.out.println("Message :" + message);
		System.out.println("---------------------------------------------");
	}

}
