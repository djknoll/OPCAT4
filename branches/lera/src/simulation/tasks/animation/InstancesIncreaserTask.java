package simulation.tasks.animation;

import java.util.Enumeration;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;

import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXObjectInstance;
import exportedAPI.opcatAPIx.IXSystem;
import simulation.reader.RuntimeInfoTable;
import simulation.tasks.AnimationTask;
import simulation.tasks.SimulationTask;

public class InstancesIncreaserTask extends ConnectionEdgeActivationAnimation {
  private int previosNumOfInstances;
  private boolean shouldTreatAnimation;
  IXObjectEntry myObject;
  
  public InstancesIncreaserTask(int startTime, int duration, IXObjectEntry object) {
  	super(startTime, duration, object);
  	myObject = object;
  }
	
  public InstancesIncreaserTask(NamedNodeMap attributes,
		  IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
  	super(attributes, opmSystem, previouselyCreatedTasks);
  	myObject = (IXObjectEntry)getEntity();
  }

  private boolean isAnimated(){
  	Enumeration instances = connectionEdge.getInstances();
  	if (instances.hasMoreElements()){
  		IXObjectInstance firstInstance = (IXObjectInstance)instances.nextElement();
  		return firstInstance.isAnimated();
  	}
	
  	return false;
  }

  /**
   * animateForward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void animateForward(int duration) {
  	shouldTreatAnimation = !isAnimated();
	
  	if (shouldTreatAnimation){
  		super.animateForward(duration);	
  	}
  }

  /**
   * animateBackward
   *
   * @param duration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  
  protected void animateBackward(int duration) {
  	synchronized (myObject){
  		myObject.setNumberOfAnimatedInstances(previosNumOfInstances);
  		myObject.updateInstances();
  		shouldTreatAnimation = myObject.getNumberOfAnimatedInstances() <= 0;
  	}
	  
  	if (shouldTreatAnimation){
  		super.animateBackward(duration);	
  	}
  }

  /**
   * continueAnimation
   *
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  protected void continueAnimation() {
  	if (shouldTreatAnimation){
  		super.continueAnimation();	
  	}
  }

  protected void pauseAnimation() {
  	if (shouldTreatAnimation){
  		super.pauseAnimation();	
  	}
  }

  protected void finishForwardAnimation(){
  	synchronized (myObject){
  		previosNumOfInstances = myObject.getNumberOfAnimatedInstances();
  		myObject.setNumberOfAnimatedInstances(previosNumOfInstances + 1);
  		myObject.updateInstances();
  	}	
  }
}
