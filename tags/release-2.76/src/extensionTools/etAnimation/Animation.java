package extensionTools.etAnimation;


/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public interface Animation {
  public void animationStart();
  public void animationStop();
  public void animationActivate();
  public void animationDeactivate();
  public void animationForward(long numberOfSteps);
  public void animationBackward(long numberOfSteps);
  public void animationPause();
  public void animationContinue();
  public void animationSettings(AnimationSettings newSettings);
  public boolean isAnimationPaused();
  public boolean canFarward();
  public boolean canBackward();

}