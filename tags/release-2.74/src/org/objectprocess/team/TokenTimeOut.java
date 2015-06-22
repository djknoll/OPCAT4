package org.objectprocess.team;

import gui.Opcat2;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class TokenTimeOut {
  Opcat2 myOpcat2;
  Timer timer;

  public TokenTimeOut(Opcat2 opcat2) {
    myOpcat2 = opcat2;
  }

  //start the timer
  public void startTimer(int seconds) {
    timer = new Timer();
    timer.scheduleAtFixedRate(new RemindTask(),seconds*1000,seconds*1000);
  }

  //stop and kill the timer
  public void stopTimer() {
    timer.cancel(); //Terminate the timer thread
  }

  class RemindTask extends TimerTask {
    public void run() {
      if (myOpcat2.getActiveCollaborativeSession()!=null) {
        myOpcat2.getActiveCollaborativeSession().returnToken();
        JOptionPane.showMessageDialog(Opcat2.getFrame(),
                              "Token TimeOut occured, The token was returned to the server.",
                              "Message",
                              JOptionPane.INFORMATION_MESSAGE);

      }
    }
  }
}


