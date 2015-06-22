package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.*;
import simulation.util.*;
import exportedAPI.OpcatConstants;
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
public class ProcessTerminationRule extends Rule {
	private String executionId;
	
  public ProcessTerminationRule(int time, Rule parentRule, String executionId,
                                          IXProcessEntry entity,
                                          CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.executionId = executionId;
    myLogicalTask = new DefaultLogicalTask(getTime(), creationTable.getSystem(), opmEntity,
                                    LogicalTask.TYPE.TERMINATION, parentLogicalTask);
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List<SimulationTask> getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>(1);
    newTasks.add(myLogicalTask);
    newTasks.add(new ConnectionEdgeTerminationAnimation(getTime(), 1, (IXProcessEntry)opmEntity));
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
    scheduleLinkRules(consequenceRules);
    //+ flag not to scheduleNextLevelRules
    scheduleNextLevelRules(consequenceRules);
    scheduleChildTerminationRules(consequenceRules);
    return consequenceRules;
  }

  
  protected void scheduleChildTerminationRules(ArrayList<Rule> consequenceRules){
  	if (!ProcessUtils.isInzoomed((IXProcessEntry)opmEntity)){
  		return;
  	}
  	//clean inner objects 
    AutoInitiationHelper helper = new AutoInitiationHelper(creationTable);
    consequenceRules.addAll(helper.getAutoTerminatedChildRules((IXProcessEntry)opmEntity, getTime() + 1));
  }
  
  protected void scheduleNextLevelRules(ArrayList<Rule> consequenceRules){
    IXProcessEntry parentEntry = ProcessUtils.getParent((IXProcessEntry)opmEntity);
    if (parentEntry == null){
      return;
    }

    ProcessInfoEntry processInfo = (ProcessInfoEntry)creationTable.getInfoEntry(parentEntry.getId());
    LevelsRelation parentLevels = processInfo.getLevelsRelation();
    parentLevels.setProcessFinished(opmEntity.getId());
    if (!parentLevels.isProcessesLevelFinished(opmEntity.getId())){
      return;
    }

    int myProcessLevel = parentLevels.getProcessLevel(opmEntity.getId());
    if ((parentLevels.getNumOfLevels() - 1) == myProcessLevel){
      consequenceRules.add(new ProcessTerminationRule(getTime() + 5, this,
      		executionId, parentEntry, creationTable));

    }else{
      Iterator<IXProcessEntry> iter = parentLevels.getProcessesInLevel(myProcessLevel + 1).iterator();
      while (iter.hasNext()){
        consequenceRules.add(new ProcessActivationRule(getTime() + 5, this,
            iter.next(), creationTable));
      }

    }
  }

  private void scheduleLinkRules(ArrayList<Rule> consequenceRules){
    LinkLogicalConnection linkConnection = ((ProcessInfoEntry)myInfo).getLinkLogicalConnection(true);
    Iterator<IXLinkEntry> iter = linkConnection.getLinksByRelationLogic("").iterator();
    while (iter.hasNext()){
      IXLinkEntry currLink = iter.next();
      if (currLink.getLinkType() == OpcatConstants.INVOCATION_LINK){
        consequenceRules.add(new InvocationLinkInvocationRule(getTime() + 1, this,
            currLink, creationTable));
      }
      //if invocation is to parent, levels relation should be reset and manually clean inner objects
      // time + 1
      //only then Invocation rule with time + 2..3 (maybe special rule) 
    }

  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
//  	return ((ProcessInfoEntry)myInfo).isRunning(executionId);
  	return true;
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
  	((ProcessInfoEntry)myInfo).add2RunningProcesses(executionId);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
  	((ProcessInfoEntry)myInfo).removeFromRunningProcesses(executionId);
  }
}
