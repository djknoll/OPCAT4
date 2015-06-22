package extensionTools.etAnimation;

import java.util.TimerTask;


/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

class AnimationTask extends TimerTask {
  AnimationSystem aSys;

  public AnimationTask() {
  }

  public AnimationTask(AnimationSystem aSys) {
    this.aSys = aSys;
  }

  public void run() {
    if (aSys.currentStep == 0)  {
      aSys.firstStep();
      // set pause to enter into step by step mode
      if (AnimationSettings.STEP_BY_STEP_MODE)  {
        aSys.animationPause();
        // schedule pause after 1 step
        /*
        aSys.animationTimer.schedule(new TimerTask() {public void run() {
                                    aSys.animationPause();}}, AnimationSettings.STEP_DURATION);
        */
      }
    }
    else  {
      aSys.nextStep();
    }

  }
}