package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.creator.creationInfo.*;

public class AgentLinkInvocationRule extends Rule {
  public AgentLinkInvocationRule(int time, Rule parentRule, IXLinkEntry entity,
                                     CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
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
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>();
    ProcessInfoEntry processInfo = (ProcessInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getDestinationId());
    consequenceRules.add(new ProcessActivationRule(getTime() + 1, this
                                                   ,(IXProcessEntry)processInfo.getEntity(), creationTable));
    return consequenceRules;
  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
    CreationInfoEntry sourceInfo = creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getSourceId());
    return (sourceInfo.isActivated());
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
