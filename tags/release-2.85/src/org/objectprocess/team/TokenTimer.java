package org.objectprocess.team;

import gui.Opcat2;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;
/**
 * The TokenTimer is a class that implements the timeout functionality which is defined
 * for each collaborative session, by its creator (who is administrator too).
 * Every time the user login into a session, he can ask for the token if he is authorized.
 * At the point of login into session an instance of the class is created.
 * When the user gets the token, the timer starts. The default value for the token timeout is 10 minutes.
 * Once the timer is started, after the defined timeout is terminated,  the following actions will be taken:
 * 1. If a saved action occurred in the last defined period, the flag that indicates it is reset.
 * 2. If no save action occurred, but the user is alone in the session ( this can be checked through the teamMember class) , no action is taken.
 * 3. If no save action occurred, and the user is not alone in the session, the token returns to the server, the timer is canceled, and the user gets a message about it on the screen.
 *
 *
 * @author  Dizza Beimel
 * @version 1.0, May 2004
 * @since   1.0
 */

public class TokenTimer {
  protected Opcat2 myOpcat2;
  protected Timer timer;
  protected boolean modelSavedFlag;

/**
   * Constractor
   *
   * @param Opcat2 needed for few functions activation, which are reachable from Opcat2.
   */
  public TokenTimer(Opcat2 opcat2) {
    this.myOpcat2 = opcat2;
    this.modelSavedFlag = false;
  }

  /**
   * starts the timer, schedule the timer to start after the defined period,
   * and to restart again after the same defined period.
   * Reset the flag that indicates if saved action occured in the last defined period.
   *
   * @param seconds indicates the requested period
   */
  public void startTimer(int seconds) {
    this.timer = new Timer();
    this.timer.scheduleAtFixedRate(new RemindTask(),seconds*1000,seconds*1000);
    this.modelSavedFlag = false;
  }

  /**
   * Stop the timer (acually terminates the timer thread), this will happen when the the token is returned to the server.
   */
  public void stopTimer() {
    this.timer.cancel();
  }
  /**
   * returns the status (boolean) of the flag that indicates if saved action occured in the last defined period.
   */
  public boolean getModelSavedFlag() {
    return this.modelSavedFlag;
  }
  /**
   * set the status (boolean) of the flag that indicates if saved action occured in the last defined period.
   *
   * @param flag indicates the requested status.
   */
  public void setModelSavedFlag(boolean flag) {
    this.modelSavedFlag = flag;
  }
  /**
   * the thread that runs after a defined period is over. Obay the rules that were described in
   * the class doc.
   */
  class RemindTask extends TimerTask {
    public void run() {
      if (TokenTimer.this.myOpcat2.getActiveCollaborativeSession()!=null) {
        //if save action acours in the last timer period - reset the flag and exit
        if (TokenTimer.this.modelSavedFlag == true) {
          TokenTimer.this.modelSavedFlag = false;
          return;
        }
        //check that user is not alone in the session - if not alone- return the token,
        //else, leave the token.
        if (TokenTimer.this.myOpcat2.getTeamMember().numOfLoggedInMembers() <= 1) {
			return;
		}

      //the session has more then one users logged into return the token.
        TokenTimer.this.myOpcat2.getActiveCollaborativeSession().returnToken();
        JOptionPane.showMessageDialog(Opcat2.getFrame(),
                              "Token TimeOut occured, The token was returned to the server.",
                              "Message",
                              JOptionPane.INFORMATION_MESSAGE);
      }
    }
  }
}


