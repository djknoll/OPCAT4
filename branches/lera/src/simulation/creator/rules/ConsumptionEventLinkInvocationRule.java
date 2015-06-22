package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.tasks.animation.*;
import simulation.util.ProcessUtils;
import simulation.creator.creationInfo.*;

public class ConsumptionEventLinkInvocationRule extends Rule {
  private InstanceInfoEntry usedInstance;
  

  public ConsumptionEventLinkInvocationRule(int time, Rule parentRule, IXLinkEntry entity,
  		InstanceInfoEntry usedInstance, CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.usedInstance = usedInstance;
  }
  
  public InstanceInfoEntry getUsedInstance(){
	  return usedInstance;
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>();
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
	PersistentInfoEntry destInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getDestinationId());
    consequenceRules.add(new ProcessActivationRule(getTime() + 1, this,
            (IXProcessEntry)destInfo.getEntity() , creationTable, true));
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
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
  }
}
