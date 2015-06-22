package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.logic.InstanceCreationTask;
import simulation.tasks.*;
import exportedAPI.OpcatConstants;
import simulation.util.LinkLogicalConnection;
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
public class ObjectActivationRule extends Rule {
	private long newInstanceId;
	private InstanceInfoEntry instanceEntry;
	private String initialState;
	
	
  public ObjectActivationRule(int time, Rule parentRule, IXObjectEntry parent,
		  CreationInfoTable creationTable, String initialState) {
    super(time, parentRule, parent, creationTable);
    instanceEntry = null;
    this.initialState = initialState;
  }
  
  public ObjectActivationRule(int time, Rule parentRule, IXObjectEntry parent,
		  CreationInfoTable creationTable) {
    this(time, parentRule, parent, creationTable, null);
  }
  
  
  private synchronized void createNewInstanceInfo(){
    if (instanceEntry == null){
    	ObjectInfoEntry parentEntry = (ObjectInfoEntry)creationTable.getInfoEntry(opmEntity.getId());
    	newInstanceId = creationTable.getSystem().reserveId();
    	
    	instanceEntry = new InstanceInfoEntry(creationTable.getSystem(), parentEntry, newInstanceId);
    	Enumeration states = ((IXObjectEntry)opmEntity).getStates();
    	while (states.hasMoreElements()){
    		IXStateEntry currState = (IXStateEntry)states.nextElement();
    		StateInfoEntry stateInfo = (StateInfoEntry)creationTable.getInfoEntry(currState.getId());
    		instanceEntry.addState(stateInfo);
    	}
    }
  	
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List<SimulationTask> getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask> (2);
    createNewInstanceInfo();
    myLogicalTask = new InstanceCreationTask(getTime(), creationTable.getSystem(),
    		(IXObjectEntry)opmEntity, newInstanceId, parentLogicalTask);

    newTasks.add(myLogicalTask);
    if (!isAnimationTreatedByOther()){
      newTasks.add(new InstancesIncreaserTask(getTime(), 1,
          (IXObjectEntry) opmEntity));
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
    scheduleInitialStatesRules(consequenceRules);
    scheduleLinkRules(consequenceRules);
    return consequenceRules;

  }

  private void scheduleLinkRules(ArrayList<Rule> consequenceRules){
    LinkLogicalConnection linkConnection = ((ObjectInfoEntry)myInfo).getLinkLogicalConnection(true);

    Iterator<IXLinkEntry> iter = linkConnection.getLinksByRelationLogic("").iterator();
    while (iter.hasNext()){
      IXLinkEntry currLink = iter.next();
      switch (currLink.getLinkType()){
//        case OpcatConstants.AGENT_LINK : {
//          consequenceRules.add(new AgentLinkInvocationRule(getTime() + 1, this,
//              currLink, creationTable));
//          break;
//        }
        case OpcatConstants.CONSUMPTION_EVENT_LINK : {
          consequenceRules.add(new ConsumptionEventLinkInvocationRule(getTime() + 1, this, currLink,
        		  instanceEntry, creationTable));
          break;
        }
        
      }
    }
  }


  private void scheduleInitialStatesRules(ArrayList<Rule> consequenceRules){
    if (instanceEntry.isThereActiveStates()){
      return;
    }
    
    if (initialState == null){
    	scheduleRandomStateRules(consequenceRules);
    }else{
      Enumeration states = ((IXObjectEntry)opmEntity).getStates();
      while (states.hasMoreElements()){
        IXStateEntry currState = (IXStateEntry)states.nextElement();
        if (currState.getName().equals(initialState)){
          consequenceRules.add(new StateActivationRule(getTime()+1, this,
                               currState, instanceEntry, creationTable));
        }
      }
    	
    }
  }
  
 //randomly activates created instance states
  private void scheduleRandomStateRules(ArrayList<Rule> consequenceRules){
    Enumeration states = ((IXObjectEntry)opmEntity).getStates();
    int numOfStates = 0;
    boolean statesWasActivated = false;
    while (states.hasMoreElements()){
      IXStateEntry currState = (IXStateEntry)states.nextElement();
      if (currState.isInitial()){
        consequenceRules.add(new StateActivationRule(getTime()+1, this,
                             currState, instanceEntry, creationTable));
        statesWasActivated = true;
      }
      
      numOfStates++;
    }
    
    if (statesWasActivated || !creationTable.getConfig().isStatesRandomelyActivated()){
    	return;
    }

    int randomState = (int)(Math.random() * numOfStates);
    states = ((IXObjectEntry)opmEntity).getStates();
    for (int i = 0;states.hasMoreElements(); i++){
      IXStateEntry currState = (IXStateEntry)states.nextElement();
      if (i == randomState){
        consequenceRules.add(new StateActivationRule(getTime()+1, this,
                             currState, instanceEntry, creationTable));
      }
    }    
  }


  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
//  	if (instanceEntry.getParentEntity().getName().contains("Speed Delta")){
//  		System.err.println("Speed Delta is sta"+myInfo.isActivated());
//  	}
//    if (myInfo.isActivated())
//    {
//      return false;
//    }
//
    return true;
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
	  creationTable.removeInstanceEntry(instanceEntry);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
	  creationTable.addInstanceEntry(instanceEntry);
  }
}
