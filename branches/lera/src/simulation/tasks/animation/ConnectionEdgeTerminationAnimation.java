package simulation.tasks.animation;

import simulation.tasks.*;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import org.w3c.dom.NamedNodeMap;
import exportedAPI.opcatAPIx.IXSystem;
import java.util.Enumeration;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
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
public class ConnectionEdgeTerminationAnimation extends AnimationTask {

  IXConnectionEdgeEntry connectionEdge;
  public ConnectionEdgeTerminationAnimation(int startTime, int duration,
                                      IXConnectionEdgeEntry entity) {
    super(startTime, duration, entity);
    connectionEdge = entity;
  }

  public ConnectionEdgeTerminationAnimation(NamedNodeMap attributes,
                                      IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
    super(attributes, opmSystem, previouselyCreatedTasks);
    connectionEdge = (IXConnectionEdgeEntry)getEntity();
  }


  /**
   * animateForward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateForward(int duration) {
    Enumeration instances = connectionEdge.getInstances();
    while (instances.hasMoreElements()){
      ((IXConnectionEdgeInstance)instances.nextElement()).stopTesting(duration);
    }
  }

  /**
   * animateBackward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateBackward(int duration) {
    Enumeration instances = connectionEdge.getInstances();
    while (instances.hasMoreElements()){
      ((IXConnectionEdgeInstance)instances.nextElement()).animate(duration);
    }
  }

  /**
   * continueAnimation
   *
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void continueAnimation() {
    Enumeration instances = connectionEdge.getInstances();
    while (instances.hasMoreElements()){
      ((IXConnectionEdgeInstance)instances.nextElement()).continueTesting();
    }

  }

  protected void pauseAnimation() {
    Enumeration instances = connectionEdge.getInstances();
    while (instances.hasMoreElements()){
      ((IXConnectionEdgeInstance)instances.nextElement()).pauseTesting();
    }

  }

  protected void finishForwardAnimation(){}
  protected void finishBackwardAnimation(){}

  public String toString(){
    String returnedString = "Animation terminated for ";

    if (connectionEdge instanceof IXProcessEntry){
      return returnedString + "process '"+connectionEdge.getName()+"'";
    }

    if (connectionEdge instanceof IXObjectEntry){
      return returnedString + " object '"+connectionEdge.getName()+"'";
    }

    if (connectionEdge instanceof IXStateEntry){
      returnedString += " state '"+connectionEdge.getName()+"' of ";
      IXObjectEntry parentObject = ((IXStateEntry)connectionEdge).getParentIXObjectEntry();
      return returnedString + " object '"+parentObject.getName()+"'";
    }

    return "Can't get the description for animation termination entity '" +connectionEdge.getName()+"' id" + connectionEdge.getName();
  }

}
