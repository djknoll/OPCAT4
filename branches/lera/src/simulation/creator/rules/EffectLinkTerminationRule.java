package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.creator.creationInfo.*;

public class EffectLinkTerminationRule extends Rule {
  public EffectLinkTerminationRule(int time, Rule parentRule, IXLinkEntry entity,
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
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>(1);
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
    myInfo.setActivated(true);
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
    myInfo.setActivated(false);
  }
}
