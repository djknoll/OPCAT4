package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.logic.StateTerminationTask;
import simulation.tasks.*;
import simulation.util.*;
import exportedAPI.*;
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
public class StateTerminationRule extends Rule {
	private InstanceInfoEntry affectedInstance;
	
	public StateTerminationRule(int time, Rule parentRule,
                                          IXStateEntry entity, InstanceInfoEntry affectedInstance,
                                          CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.affectedInstance = affectedInstance;
    myLogicalTask = new StateTerminationTask(getTime(), creationTable.getSystem(), affectedInstance.getId(),
    		entity.getName(), parentLogicalTask);
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
      newTasks.add(new ConnectionEdgeTerminationAnimation(getTime(), 1,
          (IXStateEntry) opmEntity));
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
    return null;
  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
    return affectedInstance.getStateInfo(opmEntity.getName()).isActivated();
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
  	affectedInstance.getStateInfo(opmEntity.getName()).setActivated(true);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
  	affectedInstance.getStateInfo(opmEntity.getName()).setActivated(false);
  }
}
