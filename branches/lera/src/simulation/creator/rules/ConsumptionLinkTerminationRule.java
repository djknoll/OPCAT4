package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.creator.creationInfo.*;

public class ConsumptionLinkTerminationRule extends Rule {
  private InstanceInfoEntry usedObject;
  private boolean isConsuming;
  
  public ConsumptionLinkTerminationRule(int time, Rule parentRule, IXLinkEntry entity,
  																				InstanceInfoEntry usedObject, boolean isConsuming, CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.usedObject = usedObject;
    this.isConsuming = isConsuming;
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>(1);
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
    
    if (isConsuming){
    	PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getSourceId());
    	if (sourceInfo.getEntity() instanceof IXStateEntry){
    		IXStateEntry sourceState = (IXStateEntry)sourceInfo.getEntity();
    		ObjectTerminationRule objectRule = new ObjectTerminationRule(getTime() + 1, this,
    				sourceState.getParentIXObjectEntry(), usedObject, creationTable);
      
    		consequenceRules.add(objectRule);
    	}
    }

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
//    myInfo.setActivated(true);
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
//    myInfo.setActivated(false);
  }
}
