package simulation.tasks.animation;

import simulation.tasks.*;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXLinkEntry;
import org.w3c.dom.NamedNodeMap;
import exportedAPI.opcatAPIx.IXSystem;
import simulation.util.LinkUtils;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public class LinkActivationAnimation extends AnimationTask {

  IXLinkEntry link;
  public LinkActivationAnimation(int startTime, int duration,
                                      IXLinkEntry entity) {
    super(startTime, duration, entity);
    link = entity;
  }

  public LinkActivationAnimation(NamedNodeMap attributes,
                                      IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
    super(attributes, opmSystem, previouselyCreatedTasks);
    link = (IXLinkEntry)getEntity();
  }


  /**
   * animateForward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateForward(int duration) {
    Enumeration instances = link.getInstances();
    while (instances.hasMoreElements()){
      ((IXLinkInstance)instances.nextElement()).animate(duration, true);
    }

  }

  /**
   * animateBackward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateBackward(int duration) {
    Enumeration instances = link.getInstances();
    while (instances.hasMoreElements()){
        ((IXLinkInstance)instances.nextElement()).animate(duration, false);
    }
  }

  /**
   * continueAnimation
   *
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void continueAnimation() {
    Enumeration instances = link.getInstances();
    while (instances.hasMoreElements()){
      ((IXLinkInstance)instances.nextElement()).continueTesting();
    }
  }

  protected void pauseAnimation() {
    Enumeration instances = link.getInstances();
    while (instances.hasMoreElements()){
      ((IXLinkInstance)instances.nextElement()).pauseTesting();
    }

  }

  protected void finishForwardAnimation(){}
  protected void finishBackwardAnimation(){}

  public String toString(){
    return "Animation activated for "+LinkUtils.getName4LinkType(link.getLinkType());
  }

}
