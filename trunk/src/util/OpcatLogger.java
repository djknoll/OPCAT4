package util;

import gui.Opcat2;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

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
        try {
            logger.error(message);
            showLoggerGUI(guiHeader, message, java.util.logging.Level.SEVERE);
        } catch (Exception ex) {

        }
    }

    public static void error(Exception ex) {
        try {
            logger.error(getGUIHeader(ex), ex);
            boolean show = true;
            if (!Boolean.valueOf(Configuration.getInstance().getProperty("opcat.show.detailederrors")) && (ex instanceof NullPointerException)) {
                show = false;
            }
            if (show) showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                    java.util.logging.Level.SEVERE);
        } catch (Exception e) {

        }
    }

    public static void error(Exception ex, boolean showDialog) {
        try {
            logger.error(getGUIHeader(ex), ex);
            if (showDialog) {
                boolean show = true;
                if (Boolean.valueOf(Configuration.getInstance().getProperty("opcat.show.detailederrors")) && (ex instanceof NullPointerException)) {
                    show = false;
                }
                if (show) showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                        java.util.logging.Level.SEVERE);
            }

        } catch (Exception e) {

        }
    }

    public static void info(String message, boolean showGUI) {
        try {
            logger.info(message);
            if (showGUI) {
                showLoggerGUI(guiHeader, message, java.util.logging.Level.INFO);
            }
        } catch (Exception ex) {

        }
    }

    public static void error(String message, boolean showGUI) {
        try {
            logger.error(message);
            if (showGUI) {
                showLoggerGUI(guiHeader, message, java.util.logging.Level.SEVERE);
            }
        } catch (Exception ex) {

        }
    }

    public static void warning(String message, boolean showGUI) {
        try {
            logger.warn(message);
            if (showGUI) {
                showLoggerGUI(guiHeader, message, java.util.logging.Level.WARNING);
            }
        } catch (Exception ex) {

        }
    }

    public static void info(Exception ex, boolean showGUI) {
        try {
            logger.info(getGUIHeader(ex), ex);
            if (showGUI) {
                showLoggerGUI(getGUIHeader(ex), getGUIErrorBody(ex),
                        java.util.logging.Level.INFO);
            }
        } catch (Exception e) {

        }
    }

    private static void showLoggerGUI(String msg, String errorMessage,
                                      java.util.logging.Level level) {
        try {

            JXErrorPane error = new JXErrorPane();
            ErrorInfo info = new ErrorInfo("OPCAT II", msg, errorMessage, "Error",
                    null, level, null);
            error.setErrorInfo(info);
            try {
                JFrame frame = Opcat2.getFrame();
                if (frame == null) {
                    System.out.println(errorMessage);
                } else {
                    JXErrorPane.createDialog(frame, error).setVisible(true);
                }
            } catch (Exception ex) {
                System.out.println(errorMessage);
            }
        } catch (Exception ex) {

        }

    }

    private static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();

        try {
            PrintWriter pw = new PrintWriter(sw, true);
            t.printStackTrace(pw);
            pw.flush();
            sw.flush();
        } catch (Exception ex) {

        }
        return sw.toString();

    }

    private static String getGUIHeader(Exception ex) {
        String errorMessage = ex.toString();
        try {
            int place = ex.toString().indexOf(":");
            if (place > 0) {
                errorMessage = errorMessage.substring(place + 1, errorMessage.length());
            }
        } catch (Exception e) {

        }
        return "An error occured:\n\n" + errorMessage;
    }

    private static String getGUIErrorBody(Exception ex) {
        return "<pre>" + getStackTrace(ex) + "</pre>";
    }
}
