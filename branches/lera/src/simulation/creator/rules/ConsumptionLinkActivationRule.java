package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.tasks.animation.*;
import simulation.creator.creationInfo.*;

public class ConsumptionLinkActivationRule extends Rule {
  private int duration;
  private boolean isConsuming;
  private InstanceInfoEntry usedObject; 
  
  public ConsumptionLinkActivationRule(int time, Rule parentRule, IXLinkEntry entity, InstanceInfoEntry usedObject, boolean isConsuming,
                                     CreationInfoTable creationTable, int duration) {
    super(time, parentRule, entity, creationTable);
    this.duration = duration;
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
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>();
    PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getSourceId());
    if (sourceInfo instanceof ObjectInfoEntry){
      newTasks.add(new InstancesDecreaserTask(getTime(), duration, (IXObjectEntry)sourceInfo.getEntity()));    	
    }else{
      newTasks.add(new ConnectionEdgeTerminationAnimation(getTime(), duration, (IXConnectionEdgeEntry)sourceInfo.getEntity()));
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
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>(3);
    consequenceRules.add(new ConsumptionLinkTerminationRule(getTime() + duration, this,
        (IXLinkEntry)opmEntity, usedObject, isConsuming, creationTable));

    PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getSourceId());
    if (sourceInfo.getEntity() instanceof IXObjectEntry){
      ObjectTerminationRule objectRule = new ObjectTerminationRule(getTime() + 1, this,
          (IXObjectEntry)sourceInfo.getEntity(), usedObject, creationTable);
      objectRule.setAnimationTreatedByOther(true);
      consequenceRules.add(objectRule);
    }else if (sourceInfo.getEntity() instanceof IXStateEntry){
      StateTerminationRule stateRule = new StateTerminationRule(getTime() + 1, this,
          (IXStateEntry)sourceInfo.getEntity(), usedObject, creationTable);
      stateRule.setAnimationTreatedByOther(true);
      consequenceRules.add(stateRule);
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
//    CreationInfoEntry sourceInfo = creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getSourceId());
//    return sourceInfo.isActivated();
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
