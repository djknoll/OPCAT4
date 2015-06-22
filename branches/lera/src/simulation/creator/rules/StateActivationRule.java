package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.logic.StateActivationTask;
import simulation.tasks.*;
import exportedAPI.OpcatConstants;
import simulation.util.ProcessUtils;
import simulation.util.LinkLogicalConnection;
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
public class StateActivationRule extends Rule {
	private InstanceInfoEntry affectedInstance;
	
  public StateActivationRule(int time, Rule parentRule,
                                          IXStateEntry entity, InstanceInfoEntry affectedInstance,
                                          CreationInfoTable creationTable) {
    super(time, parentRule, entity, creationTable);
    this.affectedInstance = affectedInstance;
    myLogicalTask = new StateActivationTask(getTime(), creationTable.getSystem(), affectedInstance.getId(),
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
      newTasks.add(new ConnectionEdgeActivationAnimation(getTime(), 1,
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
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>();
    scheduleParentActivationRule(consequenceRules);
    scheduleLinkRules(consequenceRules);
    return consequenceRules;
  }


  private void scheduleParentActivationRule(ArrayList<Rule> consequenceRules){
//    CreationInfoEntry parentInfo = creationTable.getInfoEntry(opmEntity.getId());
//    if (parentInfo.isActivated()){
//      return;
//    }
//
//    IXObjectEntry parent = ((IXStateEntry)opmEntity).getParentIXObjectEntry();
//    consequenceRules.add(new ObjectActivationRule(getTime()+1, this,
//                         parent, creationTable));
//
  }

  private void scheduleLinkRules(ArrayList<Rule> consequenceRules){
    LinkLogicalConnection linkConnection = ((StateInfoEntry)myInfo).getLinkLogicalConnection(true);

    Iterator<IXLinkEntry> iter = linkConnection.getLinksByRelationLogic("").iterator();
    while (iter.hasNext()){
      IXLinkEntry currLink = iter.next();
      switch (currLink.getLinkType()){
        case OpcatConstants.INSTRUMENT_EVENT_LINK : {
          consequenceRules.add(new InstrumentEventLinkInvocationRule(getTime() + 1, this,
              currLink, creationTable));
          break;
        }
        case OpcatConstants.CONSUMPTION_EVENT_LINK : {
          consequenceRules.add(new ConsumptionEventLinkInvocationRule(getTime() + 1, this, currLink,
        		  affectedInstance, creationTable));
          
          break;
        }
      }
    }
  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
//    IXObjectEntry parent = ((IXStateEntry)opmEntity).getParentIXObjectEntry();
//    PersistentInfoEntry parentInfo = (PersistentInfoEntry)creationTable.getInfoEntry(parent.getId());
//    if (parentInfo.isActivated()){
//      return true;
//    }
//
//    IXProcessEntry inzoomedGrandParent = ProcessUtils.getParent((IXObjectEntry)parentInfo.getEntity());
//    if (inzoomedGrandParent != null){
//      if (!creationTable.getInfoEntry(inzoomedGrandParent.getId()).isActivated()){
//        return false;
//      }
//    }
//
//    Enumeration links = ((IXObjectEntry)parentInfo.getEntity()).getLinksByDestination();
//    while (links.hasMoreElements()){
//     if (((IXLinkEntry)links.nextElement()).getLinkType() == OpcatConstants.RESULT_LINK){
//       return false;
//     }
//    }
    return !affectedInstance.getStateInfo(opmEntity.getName()).isActivated();
  }

  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
  	affectedInstance.getStateInfo(opmEntity.getName()).setActivated(false);
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
  	affectedInstance.getStateInfo(opmEntity.getName()).setActivated(true);
  }
}
