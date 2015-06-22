package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.tasks.animation.ConnectionEdgeTerminationAnimation;
import simulation.creator.creationInfo.*;

public class TimeoutLinkInvocationRule extends Rule {
	private ProcessInfoEntry timeoutedProcessInfo; 
	private String executionId;
	
  public TimeoutLinkInvocationRule(int time, Rule parentRule, String executionId, 
  		IXLinkEntry entity, CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.executionId = executionId;
    timeoutedProcessInfo = (ProcessInfoEntry)creationTable.getInfoEntry(entity.getSourceId());
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask> (2);
//    newTasks.add(myLogicalTask);
//    if (!isAnimationTreatedByOther()){
//      newTasks.add(new ConnectionEdgeTerminationAnimation(getTime(), 1,
//          (IXConnectionEdgeEntry) opmEntity));
//    }
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
  	return timeoutedProcessInfo.isRunning(executionId);
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
  	timeoutedProcessInfo.add2RunningProcesses(executionId);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
  	timeoutedProcessInfo.removeFromRunningProcesses(executionId);
  }
}
