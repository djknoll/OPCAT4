package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.ConnectionEdgeActivationAnimation;
import simulation.tasks.animation.InstancesIncreaserTask;
import simulation.tasks.animation.LinkActivationAnimation;
import simulation.tasks.*;
import simulation.creator.creationInfo.*;

public class ResultLinkActivationRule extends Rule {
  private int duration;
	private InstanceInfoEntry affectedInstance; // if null we should create new one
	ProcessActivationRule.StateValuePair result;

	public ResultLinkActivationRule(int time, Rule parentRule, IXLinkEntry entity, InstanceInfoEntry affectedInstance,
																		CreationInfoTable creationTable, int duration, ProcessActivationRule.StateValuePair result) {
    super(time, parentRule, entity, creationTable);
    this.affectedInstance = affectedInstance;
    this.duration = duration;
    this.result = result;
  }
	
	public ResultLinkActivationRule(int time, Rule parentRule, IXLinkEntry entity, 
																		CreationInfoTable creationTable, int duration, ProcessActivationRule.StateValuePair result) {
			this(time, parentRule, entity, null, creationTable, duration, result);
	}
	

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>();
    PersistentInfoEntry destInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getDestinationId());
    if (destInfo instanceof ObjectInfoEntry){
      newTasks.add(new InstancesIncreaserTask(getTime(), duration, (IXObjectEntry)destInfo.getEntity()));
    }else{
    	if (affectedInstance == null){ // new instance is created
    		IXObjectEntry parentObject = ((IXStateEntry)destInfo.getEntity()).getParentIXObjectEntry();
        newTasks.add(new InstancesIncreaserTask(getTime(), duration, parentObject));    		
    	}
      newTasks.add(new ConnectionEdgeActivationAnimation(getTime(), duration, (IXConnectionEdgeEntry)destInfo.getEntity()));    	
    }
    newTasks.add(new LinkActivationAnimation(getTime(), duration, (IXLinkEntry)opmEntity));

    return newTasks;
  }

  /**
   * getConsequenceRules
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceRules() {
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>(1);
    consequenceRules.add(new ResultLinkTerminationRule(getTime() + duration, this,
                            (IXLinkEntry)opmEntity, affectedInstance, creationTable, result));
    return consequenceRules;
  }

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
   */
  public void undoUpdateCreationTable() {
//    myInfo.setActivated(false);
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
//    myInfo.setActivated(true);
  }
}
