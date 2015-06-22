package org.objectprocess.team;

import gui.Opcat2;

import java.io.IOException;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.swing.JOptionPane;

import org.apache.axis.AxisFault;

public class ExceptionHandler {

public ExceptionHandler(Exception exception) {

    if (exception instanceof AxisFault)
    {
      String str1 = ((AxisFault)exception).getMessage();
      int index1 = str1.indexOf(':')+2;
      int index2 = str1.indexOf('-');

      if (index2 != -1) {
        //that means that the message is not internal to OPCATeam server-
        //in such case - just present the message to the user, dont try tp parse it.

        String str2 = str1.substring(index1, index2);
        switch (findExceptionNumber(str2)) {
          case 1008:
            return;
        }
      }

      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    str1.substring(index1),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (exception instanceof JMSException)
    {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    exception.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

      System.err.println("Exception caught: " + exception);
      return;
    }

    if (exception instanceof NamingException) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    exception.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

      System.err.println("Exception caught: " + exception);
      return;
    }

    if (exception instanceof gui.opx.LoadException) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    exception.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

      System.err.println("Exception caught: " + exception);
      return;
    }

    if (exception instanceof IOException) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    exception.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);

      System.err.println("Exception caught: " + exception);
      return;
    }


    //all the rest
    System.err.println("Exception caught: " + exception);
  }


  private int findExceptionNumber(String stringNum) {
//Users
    String USER_ALREADY_EXIST ="01001"; //Username already exists.
    String USER_DOES_NOT_EXIST ="01002"; //User does not exist.
    String USER_IS_NOT_LOGGED_IN ="01003"; //User is not logged in
    String USER_IS_ALREADY_LOGGED_IN ="01004"; //User is already logged
    String PASSWORD_INCORRECT ="01005"; //Password rejected for user.
    String USER_DISABLED ="01006"; //User is disabled.
    String INTERNAL_DB_ERROR1 ="01007"; //Internal Database Error.
    String PERMISSION_DOES_NOT_EXIST ="01008"; //The requested permission entry does not exist
//Workgroups
    String INTERNAL_DB_ERROR2 ="02001"; //Internal Database Error.
    String WORKGROUP_ALREADY_EXIST ="02002"; //Workgroup already exists.
    String WORKGROUP_DOES_NOT_EXIST ="02003"; //Workgroup does not exist.
    String ENABLED_OPMMODELS_FOUND ="02004"; //Active OPM models found. Disable all OPM models before proceeding.
//OPMmodels
    String INTERNAL_DB_ERROR3 ="03001"; //Internal Database Error.
    String OPMMODEL_ALREADY_EXIST ="03002"; //OpmModel already exists.
    String OPMMODEL_DOES_NOT_EXIST ="03003"; //OpmModel does not .
    String ENABLED_SESSIONS_FOUND ="03004"; //Enabled collaborative sessions found. Disable all collaborative sessions before proceeding.
//Sessions
    String INTERNAL_DB_ERROR4 ="04001"; //Internal Database Error.
    String COLLABORATIVESESSION_ALREADY_EXIST =	"04002"; //CollaborativeSession already exists.
    String COLLABORATIVESESSION_DOES_NOT_EXIST ="04003"; //CollaborativeSession does not exist.
    String COLLABORATIVESESSION_TOKEN_TAKEN ="04004"; //The token is not available.
    String USER_IS_NOT_TOKEN_HOLDER ="04005"; //You are not the token holder.
    String USER_ALREADY_LOGGED_INTO_SESSION ="04006"; //You are already logged into the session
    String USER_NOT_LOGGED_INTO_SESSION ="04007"; //You are not logged into the session
    String SESSION_HAS_LOGGEDIN_USERS ="04008"; //Users are still logged into the session. Active sessions cannot be disabled
    String JMS_EXCEPTION ="04009"; //JMSException Cought
//revisions
    String NO_REVISIONS_FOUND ="05001";
    String REVISIONS_DOES_NOT_EXIST ="05002";
    String DUPLICATE_ENTRY ="05003";

    if (stringNum.equals(PERMISSION_DOES_NOT_EXIST))
      return 1008;
    return 0;

  }
}