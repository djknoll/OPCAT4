package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.logic.InstanceDeletionTask;
import simulation.tasks.*;
import simulation.creator.creationInfo.*;

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
public class ObjectTerminationRule extends Rule {
	private InstanceInfoEntry deletedInstance;
	
  public ObjectTerminationRule(int time, Rule parentRule,
                                          IXObjectEntry parentObject, InstanceInfoEntry deletedInstance,
                                          CreationInfoTable creationTable) {
    super(time, parentRule, parentObject, creationTable);
    myLogicalTask = new InstanceDeletionTask(getTime(), creationTable.getSystem(),
    		deletedInstance.getId(), parentLogicalTask);
    this.deletedInstance = deletedInstance;
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List<SimulationTask> getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask> (2);
    newTasks.add(myLogicalTask);
    if (!isAnimationTreatedByOther()){
      newTasks.add(new InstancesDecreaserTask(getTime(), 1,
          (IXObjectEntry) opmEntity));
    }
    
    Iterator<TransientStateEntry> activeStates = deletedInstance.getActiveStates();
    while (activeStates.hasNext()){
    	newTasks.add(new ConnectionEdgeTerminationAnimation(getTime(), 1, 
    			(IXStateEntry)activeStates.next().getParentEntity()));
    }
    return newTasks;
  }

  /**
   * getConsequenceRules
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceRules() {
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>();
    //scheduleStateRules(consequenceRules); - we kill the instance no 
    return consequenceRules;

  }


//  private void scheduleStateRules(ArrayList<Rule> consequenceRules){
//    Enumeration states = ((IXObjectEntry)opmEntity).getStates();
//    while (states.hasMoreElements()){
//      IXStateEntry currState = (IXStateEntry)states.nextElement();
//      CreationInfoEntry stateEntry = creationTable.getInfoEntry(currState.getId());
//      if (stateEntry.isActivated()){
//        consequenceRules.add(new StateTerminationRule(getTime()+1, this,
//                             currState, creationTable));
//      }
//    }
//  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
    return true;
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
	  creationTable.addInstanceEntry(deletedInstance);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
	  creationTable.removeInstanceEntry(deletedInstance);
  }
}
