package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.tasks.animation.LinkActivationAnimation;
import simulation.tasks.animation.*;
import simulation.creator.creationInfo.*;

public class EffectLinkActivationRule extends Rule {
  private int duration;
  public EffectLinkActivationRule(int time, Rule parentRule, IXLinkEntry entity,
                                     CreationInfoTable creationTable, int duration) {
    super(time, parentRule, entity, creationTable);
    this.duration = duration;
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>();
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
    consequenceRules.add(new EffectLinkTerminationRule(getTime() + duration, this,
        (IXLinkEntry)opmEntity, creationTable));

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
    return sourceInfo.isActivated();
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
