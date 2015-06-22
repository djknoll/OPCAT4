package util;

import gui.Opcat2;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JFrame;

/**
 * Logs messages into a designated log file.
 *
 * @author Eran Toch
 */
public class OpcatLogger {

    /**
     * Logs an error message to the system.err output.
     */
    private static String loggerName = Configuration.getInstance().getProperty(
            "logger");
    private static Logger logger = Logger.getLogger(loggerName);
    private static String guiHeader = "An error occured" + "\n\n\"opcat-log.xml\" file was created in Opcat2 directory. Please send the error file to bugs@opcat.com ";

    public static void error(String message) {
        logger.error(message);
        showLoggerGUI(guiHeader, message, java.util.logging.Level.SEVERE);
    }

    public static void error(Exception ex) {
        logger.error(getGUIHeader(ex), ex);
        boolean show = true;
        if (!Boolean.valueOf(Configuration.getInstance().getProperty("opcat.show.detailederrors")) && (ex instanceof NullPointerException)) {
            show = false;
        }
        if (show) showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                java.util.logging.Level.SEVERE);
    }

    public static void error(Exception ex, boolean showDialog) {
        logger.error(getGUIHeader(ex), ex);
        if (showDialog) {
            boolean show = true;
            if (Boolean.valueOf(Configuration.getInstance().getProperty("opcat.show.detailederrors")) && (ex instanceof NullPointerException)) {
                show = false;
            }
            if (show) showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                    java.util.logging.Level.SEVERE);
        }

    }

    public static void info(String message, boolean showGUI) {
        logger.info(message);
        if (showGUI) {
            showLoggerGUI(guiHeader, message, java.util.logging.Level.INFO);
        }
    }

    public static void error(String message, boolean showGUI) {
        logger.error(message);
        if (showGUI) {
            showLoggerGUI(guiHeader, message, java.util.logging.Level.SEVERE);
        }
    }

    public static void warning(String message, boolean showGUI) {
        logger.warn(message);
        if (showGUI) {
            showLoggerGUI(guiHeader, message, java.util.logging.Level.WARNING);
        }
    }

    public static void info(Exception ex, boolean showGUI) {
        logger.info(getGUIHeader(ex), ex);
        if (showGUI) {
            showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                    java.util.logging.Level.INFO);
        }
    }

    private static void showLoggerGUI(String msg, String errorMessage,
                                      java.util.logging.Level level) {

        JXErrorPane error = new JXErrorPane();
        ErrorInfo info = new ErrorInfo("OPCAT II", msg, errorMessage, "Error",
                null, level, null);
        error.setErrorInfo(info);
        try {
            JFrame frame = Opcat2.getFrame();
            if (frame != null) {
                JXErrorPane.createDialog(frame, error).setVisible(true);
            }
        } catch (Exception ex) {
            //error(ex, false);
        }

    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush();
        return sw.toString();
    }

    private static String getGUIHeader(Exception ex) {
        String errorMessage = ex.toString();
        int place = ex.toString().indexOf(":");
        if (place > 0) {
            errorMessage = errorMessage.substring(place + 1, errorMessage.length());
        }
        return "An error occured:\n\n" + errorMessage;
    }

    private static String getGUIErrorBody(Exception ex) {
        return "<pre>" + getStackTrace(ex) + "</pre>";
    }
}
