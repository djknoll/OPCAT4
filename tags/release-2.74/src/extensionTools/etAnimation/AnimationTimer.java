package extensionTools.etAnimation;

import java.util.Timer;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

class AnimationTimer extends Timer {

  private java.lang.Boolean pauseInd = new Boolean(false);
  AnimationSystem aSys = null;

  public AnimationTimer() {
  }

  public AnimationTimer(AnimationSystem aSys) {
    this.aSys = aSys;
    // schedule next step
    this.schedule(new AnimationTask(aSys), 0, AnimationSettings.STEP_DURATION);
  }

  public void animationPause() {

    synchronized (pauseInd) {
      aSys.guiPanel.showAnimationStatus(2,aSys.currentStep);
      pauseInd = new Boolean(true);
    }
  }

  public void animationContinue()  {
    synchronized (pauseInd) {
      if (isPause())  {
        aSys.guiPanel.showAnimationStatus(1,aSys.currentStep);
        pauseInd = new Boolean(false);
      }
    }
  }

  public boolean isPause() {
    return pauseInd.booleanValue();
  }

}