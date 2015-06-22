package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.SimulationTask;
import simulation.creator.creationInfo.*;

public class ResultLinkTerminationRule extends Rule {
	private InstanceInfoEntry affectedInstance;
	ProcessActivationRule.StateValuePair result;
	
  public ResultLinkTerminationRule(int time, Rule parentRule, IXLinkEntry entity,
                                     CreationInfoTable creationTable, ProcessActivationRule.StateValuePair result_) {
    this(time, parentRule, entity, null, creationTable, result_);
  }
  
  public ResultLinkTerminationRule(int time, Rule parentRule, IXLinkEntry entity,
      InstanceInfoEntry affectedInstance, CreationInfoTable creationTable, ProcessActivationRule.StateValuePair result_) {
  	super(time, parentRule, entity, creationTable);
  	this.affectedInstance = affectedInstance;
  	this.result = result_;
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
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>(1);
    PersistentInfoEntry destInfo = (PersistentInfoEntry)creationTable.getInfoEntry(((IXLinkEntry)opmEntity).getDestinationId());
    
    Rule newRule = null;
    if (destInfo.getEntity() instanceof IXObjectEntry){
    	if(result!=null && result.hasState()){
    	newRule = new ObjectActivationRule(getTime() + 1, this,
          (IXObjectEntry)destInfo.getEntity(), creationTable, result.getState().getName());}
    	else{
    		newRule = new ObjectActivationRule(getTime() + 1, this,
    		          (IXObjectEntry)destInfo.getEntity(), creationTable);
    	}
    }else if (destInfo.getEntity() instanceof IXStateEntry){
      if (affectedInstance != null){
      	newRule = new StateActivationRule(getTime() + 1, this,
      			(IXStateEntry)destInfo.getEntity(), affectedInstance, creationTable);
      }else{
      	IXObjectEntry parentObject = ((IXStateEntry)destInfo.getEntity()).getParentIXObjectEntry();
      	newRule = new ObjectActivationRule(getTime() + 1, this, parentObject,
      		  creationTable, destInfo.getEntity().getName());
      }
    }
    
  	newRule.setAnimationTreatedByOther(true);
  	consequenceRules.add(newRule);
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
//    myInfo.setActivated(true);
  }

  /**
   * updateCreationTable
   *
   */
  public void updateCreationTable() {
//    myInfo.setActivated(false);
  }
}
