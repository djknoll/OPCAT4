package extensionTools.etAnimation;
import javax.swing.JLabel;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AnimationStatusBar extends JLabel {

  public AnimationStatusBar() {
  }

  public void showAnimationStatus(int mode, long currentStep) {
    // mode: 0 application is not running
    //       1 application is running
    //       2 pause
    if (mode == 1)  {
      this.setText("Animation status: Running, Current Step: " + currentStep);
      return;
    }
    if (mode == 0) {
      this.setText("Animation status: Not running");
      return;
    }
    if (mode == 2)  {
      this.setText("Animation status: Pause, Current Step: " + currentStep);
      return;
    }
  }
}