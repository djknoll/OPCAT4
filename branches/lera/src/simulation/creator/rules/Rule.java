package simulation.creator.rules;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXEntry;
import java.util.List;
import simulation.Logger;
import simulation.tasks.*;
import simulation.tasks.logic.DebugInfoTask;
import simulation.creator.*;
import simulation.creator.creationInfo.*;

/**
 * <p>Title: Simulation Module</p>
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
public abstract class Rule {
  private int time;
  private int parentTime;
  private boolean isEvaluated;
  private boolean isExternallyActivated;
  private boolean isAnimationTreatedByOther;
  protected IXEntry opmEntity;
  protected CreationInfoTable creationTable;
  protected Logger logger;
  protected CreationInfoEntry myInfo;
  protected int simulationTaskDuration;
  protected Rule parentRule;
  protected LogicalTask myLogicalTask;
  protected LogicalTask parentLogicalTask;
  protected DebugInfoTask debugInfo;

  public Rule(int time, Rule parentRule, IXEntry entity, CreationInfoTable creationTable) {
    this.parentRule = parentRule;
    if (parentRule == null){
      parentTime = time;
      parentLogicalTask = null;
    }else{
      parentTime = parentRule.getTime();
      parentLogicalTask = parentRule.getLogicalTask();
    }
    if (time < parentTime){
      throw new IllegalArgumentException("Rule constructor - rule's time cannot be"+
                                         " less than parent's time");
    }
    this.time = time;
    this.parentTime = parentTime;
    opmEntity = entity;
    this.creationTable = creationTable;
    myInfo = creationTable.getInfoEntry(opmEntity.getId());
    isEvaluated = false;
    isExternallyActivated = false;
    isAnimationTreatedByOther = false;
    logger = Logger.getInstance();
    debugInfo = null;
  }

  public int getTime(){
    return time;
  }

  public LogicalTask getLogicalTask(){
    return myLogicalTask;
  }

  public DebugInfoTask getDebugInfo(){
    return debugInfo;
  }

  public int getParentTime(){
    return parentTime;
  }

  public boolean isEvaluated(){
    return isEvaluated;
  }

  public void setEvaluated(boolean isEvaluated){
    this.isEvaluated = isEvaluated;
  }

  public boolean isExternallyActivated(){
    return isExternallyActivated;
  }

  public void setExternallyActivated(boolean isExternallyActivated){
    this.isExternallyActivated = isExternallyActivated;
  }

  public boolean isAnimationTreatedByOther(){
    return isAnimationTreatedByOther;
  }

  public void setAnimationTreatedByOther(boolean isAnimationTreatedByOther){
    this.isAnimationTreatedByOther = isAnimationTreatedByOther;
  }


  public void setSimulationTaskDuration(int taskDuration){
    simulationTaskDuration = taskDuration;
  }

  public String toString(){
    return "Rule - Entity name '"+opmEntity.getName();
  }


  public abstract boolean isConditionSatisfied();
  public abstract List<Rule> getConsequenceRules();
  public abstract List<SimulationTask> getConsequenceTasks();
  public abstract void updateCreationTable();
  public abstract void undoUpdateCreationTable();
}
