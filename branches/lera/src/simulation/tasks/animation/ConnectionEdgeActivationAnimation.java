package simulation.tasks.animation;

import simulation.tasks.*;
import exportedAPI.opcatAPIx.*;
import org.w3c.dom.NamedNodeMap;
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
public class ConnectionEdgeActivationAnimation extends AnimationTask {

  IXConnectionEdgeEntry connectionEdge;
  public ConnectionEdgeActivationAnimation(int startTime, int duration,
                                      IXConnectionEdgeEntry entity) {
    super(startTime, duration, entity);
    connectionEdge = entity;
  }

  public ConnectionEdgeActivationAnimation(NamedNodeMap attributes,
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
      ((IXConnectionEdgeInstance)instances.nextElement()).animate(duration);
    }
  }

  /**
   * animateBackward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateBackward(int duration) {
    logger.print(toString()+" activated animated backward "+duration, 0);
    Enumeration instances = connectionEdge.getInstances();
    while (instances.hasMoreElements()){
     IXConnectionEdgeInstance inst = ((IXConnectionEdgeInstance)instances.nextElement());
     logger.print("isAnimated " +inst.isAnimated(),0);
     inst.stopTesting(duration);
//      ((IXConnectionEdgeInstance)instances.nextElement()).stopTesting(duration);
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
    String returnedString = "Animation activated for ";

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

    return "Can't get the description for animation activation entity '" +connectionEdge.getName()+"' id" + connectionEdge.getName();
  }

}
