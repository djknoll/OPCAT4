package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.creator.creationInfo.*;

public class ConditionLinkActivationRule extends Rule {
  public ConditionLinkActivationRule(int time, Rule parentRule, IXLinkEntry entity,
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
    return null;
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
    return false;
  }

  /**
   * undoUpdateCreationTable
   *
   */
  public void undoUpdateCreationTable() {
    myInfo.setActivated(false);
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
    myInfo.setActivated(true);
  }
}
