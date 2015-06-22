package simulation.creator.rules;

import java.util.*;

import exportedAPI.opcatAPIx.*;
import exportedAPI.*;
import simulation.creator.*;
import simulation.tasks.animation.*;
import simulation.tasks.logic.DebugInfoEntry;
import simulation.tasks.logic.DebugInfoTask;
import simulation.tasks.logic.DefaultLogicalTask;
import simulation.tasks.*;
import simulation.util.*;
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
public class ProcessActivationRule  extends Rule {
  private LinkActivationChecker linkChecker = new LinkActivationChecker();
  private boolean shouldBeSkipped;
  private ILinksCheckResult lastLinksCheckResult;
  private ArrayList<InstanceInfoEntry> consumedInstances;
  private ArrayList<InstanceInfoEntry> affectedInstances;
  private ArrayList<InstanceInfoEntry> readInstances;
  private String executionId;
  private String parentExecutionId;
  private boolean wasTriggerredByEvent;
  

  public ProcessActivationRule(int time, Rule parentRule,/* String parentExecutionId,*/
                                         IXProcessEntry entity, 
                                         CreationInfoTable creationTable, boolean wasTriggerredByEvent) {
    super(time, parentRule, entity, creationTable);
    myLogicalTask = new DefaultLogicalTask(getTime(), creationTable.getSystem(),
    		opmEntity, LogicalTask.TYPE.ACTIVATION, parentLogicalTask);
    consumedInstances = new ArrayList<InstanceInfoEntry>();
    affectedInstances = new ArrayList<InstanceInfoEntry>();
    readInstances = new ArrayList<InstanceInfoEntry>();
    this.executionId = (int)Math.random()*100 + "."+time;
    this.parentExecutionId = parentExecutionId;
    this.wasTriggerredByEvent = wasTriggerredByEvent;
  }

  public ProcessActivationRule(int time, Rule parentRule,/* String parentExecutionId,*/
          IXProcessEntry entity, 
          CreationInfoTable creationTable) {
	  this(time, parentRule, entity, creationTable, false);
  }

  /**
   * getConsequenceTasks
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List<SimulationTask> getConsequenceTasks() {
    ArrayList<SimulationTask> newTasks = new ArrayList<SimulationTask>();
    if (shouldBeSkipped){
      return newTasks;
    }

    newTasks.add(myLogicalTask);
    newTasks.add(new ConnectionEdgeActivationAnimation(getTime(), 1, (IXProcessEntry)opmEntity));
    return newTasks;
  }


  /**
   * getConsequenceRules
   *
   * @return List
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public List<Rule> getConsequenceRules() {
    ArrayList<Rule> consequenceRules = new ArrayList<Rule>();
    if (shouldBeSkipped){
      consequenceRules.add(new ProcessSkippingRule(getTime() + 1, this,
                           executionId, (IXProcessEntry)opmEntity, creationTable));
      return consequenceRules;
    }
    scheduleTerminationRule(consequenceRules);
    scheduleInitialLevelRules(consequenceRules);
    scheduleLinkRules(consequenceRules);
    return consequenceRules;
  }

  private int getProcessDuration(){
    return creationTable.getConfig().getDefaultProcessDuration();
  }


  private void scheduleInitialLevelRules(ArrayList<Rule> consequenceRules){
    LevelsRelation levels = ((ProcessInfoEntry)myInfo).getLevelsRelation();
    if (levels.getNumOfLevels() == 0){
      return;
    }

    if (creationTable.getConfig().isAutoInitiated()){
      AutoInitiationHelper helper = new AutoInitiationHelper(creationTable);
      consequenceRules.addAll(helper.getAutoInitiatedChildRules((IXProcessEntry)opmEntity, getTime() + 1));
    }


    levels.resetFinished2All();
    List<IXProcessEntry> list = levels.getProcessesInLevel(0);
    Iterator<IXProcessEntry> iter = list.iterator();
    while (iter.hasNext()){
      consequenceRules.add(new ProcessActivationRule(getTime() + 5, this, /*exxecutionId,*/
                              iter.next(), creationTable));
    }

  }

  private void scheduleTerminationRule(ArrayList<Rule> consequenceRules){
    LevelsRelation levels = ((ProcessInfoEntry)myInfo).getLevelsRelation();
    if (levels.getNumOfLevels() > 0){
      levels.resetFinished2All();
      return;
    }

    consequenceRules.add(new ProcessTerminationRule(getTime() + getProcessDuration() + 1, this,
                            executionId, (IXProcessEntry)opmEntity, creationTable));
  }

  private InstanceInfoEntry getInstanceByParentId(ArrayList<InstanceInfoEntry> instances, long parentId){
  	for (int i = 0; i < instances.size(); i++){
  		if (instances.get(i).getParentEntity().getId() == parentId){
  			return instances.get(i);
  		}
  	}
  	
  	return null;
  }

  private void scheduleConsumptionEventLinkRules(ArrayList<Rule> consequenceRules){
	  if (wasTriggerredByEvent){
		  if (parentRule instanceof ConsumptionEventLinkInvocationRule){
			  IXLinkEntry consEventLink = (IXLinkEntry)parentRule.opmEntity;
		      long sourceId = consEventLink.getSourceId();
		      PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(sourceId);      
			  InstanceInfoEntry usedInstance = ((ConsumptionEventLinkInvocationRule)parentRule).getUsedInstance();
			  boolean isOnlyAffected = ProcessUtils.isObjectAffectedByProcess((IXProcessEntry)opmEntity, (IXObjectEntry)usedInstance.getParentEntity(),
					  creationTable.getSystem()); 
	            	
			  if (!isOnlyAffected){
				  consumedInstances.add(usedInstance);
			  }else{
				  affectedInstances.add(usedInstance);
			  }
			  
	          if (!((sourceInfo instanceof ObjectInfoEntry) && isOnlyAffected)){
	              consequenceRules.add(new ConsumptionLinkActivationRule(getTime() + 1, this,
	            		  consEventLink, usedInstance, !isOnlyAffected, creationTable, getProcessDuration()));
	            }
			  
		  }
	  	}
  }
  
  
  private void scheduleIncomingLinks(ArrayList<Rule> consequenceRules){
    LinkLogicalConnection destLinkConnection = ((ProcessInfoEntry)myInfo).getLinkLogicalConnection(false);
    Iterator<IXLinkEntry> destIter = lastLinksCheckResult.getSatistyingLinks().iterator();
    
    while (destIter.hasNext()){
      IXLinkEntry currLink = destIter.next();
      long sourceId = currLink.getSourceId();
      PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(sourceId);      
      InstanceInfoEntry usedInstance = null;
      if (sourceInfo instanceof ObjectInfoEntry){
      	usedInstance = ((ObjectInfoEntry)sourceInfo).getRandomInstance();
      }
      
      if (sourceInfo  instanceof StateInfoEntry){
    		IXStateEntry sourceState = (IXStateEntry)sourceInfo.getEntity();
    		long parentObjectId = sourceState.getParentIXObjectEntry().getId();
    		ObjectInfoEntry sourceObjectInfo = (ObjectInfoEntry)creationTable.getInfoEntry(parentObjectId);
    		usedInstance = sourceObjectInfo.getInstanceAtState(sourceState.getName());
      }

      switch (currLink.getLinkType()){
        case OpcatConstants.CONSUMPTION_LINK : {
          boolean isOnlyAffected = ProcessUtils.isObjectAffectedByProcess((IXProcessEntry)opmEntity, (IXObjectEntry)usedInstance.getParentEntity(),
          		creationTable.getSystem()); 
        	
          if (!isOnlyAffected){
          	consumedInstances.add(usedInstance);
          }else{
          	affectedInstances.add(usedInstance);
          }
          
          if (!((sourceInfo instanceof ObjectInfoEntry) && isOnlyAffected)){
            consequenceRules.add(new ConsumptionLinkActivationRule(getTime() + 1, this,
                currLink, usedInstance, !isOnlyAffected, creationTable, getProcessDuration()));
          }
          break;
        }
        case OpcatConstants.EFFECT_LINK : {
          consequenceRules.add(new EffectLinkActivationRule(getTime() + 1, this,
              currLink, creationTable, getProcessDuration()));
        	affectedInstances.add(usedInstance);
          break;
        }
      }

    }
	  
  }
  
  private void scheduleOutcomingLinks(ArrayList<Rule> consequenceRules){
	  LinkLogicalConnection sourceLinkConnection = ((ProcessInfoEntry)myInfo).getLinkLogicalConnection(true);
	  Iterator<IXLinkEntry> sourceIter = sourceLinkConnection.getLinksByRelationLogic(lastLinksCheckResult.getDataPath()).iterator();
	  while (sourceIter.hasNext()){
		  IXLinkEntry currLink = sourceIter.next();
		  IXEntry destEntry = ((PersistentInfoEntry)creationTable.getInfoEntry(currLink.getDestinationId())).getEntity();
	      
	      long destObjectId;
	      if (destEntry instanceof IXStateEntry){
	      	destObjectId = ((IXStateEntry)destEntry).getParentIXObjectEntry().getId();
	      }else{
	      	destObjectId = destEntry.getId(); 
	      }
	      
	      InstanceInfoEntry affectedInstance = getInstanceByParentId(affectedInstances, destObjectId );

//	      if (affectedInstance == null){ // not in list of affected we should create new instance
//	      	ObjectInfoEntry destObjInfo = (ObjectInfoEntry)creationTable.getInfoEntry(destObjectId);
//	      	affectedInstance = null;
//	      }
	      
	      switch (currLink.getLinkType()){
	      //effect link is done also in incoming links - should it be here too???
	        case OpcatConstants.EFFECT_LINK : {
	          consequenceRules.add(new EffectLinkActivationRule(getTime() + 1, this,
	                                  currLink, creationTable, getProcessDuration()));
	          break;
	        }
	        case OpcatConstants.RESULT_LINK : {
	        	 addResultLinkRule(consequenceRules, currLink, affectedInstance);
	        	 break;
	        }
	        
	        case OpcatConstants.EXCEPTION_LINK: {
	        	String maxDuration = ((IXProcessEntry)opmEntity).getMaxTimeActivation();
	        
	        	if (maxDuration != null){
	        		int timeoutDuration = ProcessUtils.getMaxTimeoutInMSec((IXProcessEntry)opmEntity);
	        		consequenceRules.add(new TimeoutLinkInvocationRule(getTime() + timeoutDuration, 
	        				this, executionId,	currLink, creationTable));
	        		break;
	        	}	
	        }
	      }

	  }
  }
  
  private void addResultLinkRule(ArrayList<Rule> consequenceRules, IXLinkEntry currLink, InstanceInfoEntry affectedInstance){
	//affectedInstance is not null if the instance already exists
      StateValuePair res = null;
      IXEntry destEntry = ((PersistentInfoEntry)creationTable.getInfoEntry(currLink.getDestinationId())).getEntity();
      System.err.println(opmEntity.getName());
      System.err.println(((IXObjectEntry)destEntry).getType());
      if(this.opmEntity.getName().equals("add") && (destEntry instanceof IXObjectEntry)
    		  && ((IXObjectEntry)destEntry).getType().equals("INT10")){
      int result = 0;
      //currently only for consumed instances
      for(int i = 0; i < this.consumedInstances.size(); i++){
    		InstanceInfoEntry entry = consumedInstances.get(i);
    		if(((IXObjectEntry)entry.getParent().getEntity()).getType().equals("INT10")){
    		if(entry.getActiveStates().hasNext()){
    			TransientStateEntry state = (TransientStateEntry)entry.getActiveStates().next();
    			IXStateEntry st = (IXStateEntry)state.getParentEntity();
    			if(st.getName().matches("[0-9]+")){
    				result += new Integer(st.getName()).intValue();
    			}
    		}else{
    			String initVal = ((IXObjectEntry)entry.getParent().getEntity()).getInitialValue();
    			if(initVal.matches("[0-9]+")){
    				result += new Integer(initVal).intValue();
    			}
    		}
    		}
    	}
    	res = new StateValuePair((new Integer(result)).toString(),(IXObjectEntry)destEntry);
    	res.setState2Value();
      }
      consequenceRules.add(new ResultLinkActivationRule(getTime() + 1, this,
          currLink, affectedInstance, creationTable, getProcessDuration(), res));
  }
  
  private void scheduleLinkRules(ArrayList<Rule> consequenceRules){
  	consumedInstances.clear();
  	affectedInstances.clear();
  	
  	scheduleConsumptionEventLinkRules(consequenceRules);
  	scheduleIncomingLinks(consequenceRules);
  	scheduleOutcomingLinks(consequenceRules);  	
  }

  /**
   * isConditionSatisfied
   *
   * @return boolean
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public boolean isConditionSatisfied() {
    debugInfo = new DebugInfoTask(getTime(), creationTable.getSystem(), opmEntity);
    if (myInfo.isActivated()){
      debugInfo.addDebugEntry(new DebugInfoEntry(opmEntity, DebugInfoEntry.REASON.ALREADY_ACTIVATED));
      return false;
    }

    return areLinksSatisfied();
  }

  private boolean areLinksSatisfied(){
    shouldBeSkipped = false;

    LinkLogicalConnection linkConnection = ((ProcessInfoEntry)myInfo).getLinkLogicalConnection(false);
    if (linkConnection == null){
      return true;
    }else{
      lastLinksCheckResult = linkConnection.isConditionSatisfied(linkChecker);
      return lastLinksCheckResult.isCondititionSatisfied();
    }
  }
  /**
   * undoUpdateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void undoUpdateCreationTable() {
    if (!shouldBeSkipped){
    	for (int i = 0; i < consumedInstances.size(); i++){
    	//	creationTable.addInstanceEntry(consumedInstances.get(i));    		
    	}
    	
      ((ProcessInfoEntry)myInfo).removeFromRunningProcesses(executionId);
    }
  }

  /**
   * updateCreationTable
   *
   * @todo Implement this simulation.creator.rules.Rule method
   */
  public void updateCreationTable() {
    if (!shouldBeSkipped){
    	for (int i = 0; i < consumedInstances.size(); i++){
    		//creationTable.removeInstanceEntry(consumedInstances.get(i));
    	}
    	
      ((ProcessInfoEntry)myInfo).add2RunningProcesses(executionId);
    }
  }

  class LinkActivationChecker  implements ILinkCondition{
    public boolean isConditionSatisfied(IXLinkEntry link){
      long sourceId = link.getSourceId();
      boolean stoppedOnManualActivation = false;
      PersistentInfoEntry sourceInfo = (PersistentInfoEntry)creationTable.getInfoEntry(sourceId);      
      switch (link.getLinkType()){
        case OpcatConstants.INSTRUMENT_LINK :
        case OpcatConstants.AGENT_LINK :
        case OpcatConstants.CONSUMPTION_LINK :
        case OpcatConstants.EFFECT_LINK :{
        	if (sourceInfo instanceof ObjectInfoEntry){
        		ObjectInfoEntry sourceObjectInfo = (ObjectInfoEntry)sourceInfo;
        		if (sourceObjectInfo.hasInstances()){
        			if (link.getLinkType() == OpcatConstants.AGENT_LINK){
        				if (creationTable.getConfig().isStopOnAgents() && !isExternallyActivated()){
        					stoppedOnManualActivation = true;
        				}
        				else{
        					return true;
        				}
        			}else{
        				return true;
        			}
        		}
        	}else if (sourceInfo instanceof StateInfoEntry){
        		IXStateEntry sourceState = (IXStateEntry)sourceInfo.getEntity();
        		long parentObjectId = sourceState.getParentIXObjectEntry().getId();
        		ObjectInfoEntry sourceObjectInfo = (ObjectInfoEntry)creationTable.getInfoEntry(parentObjectId);
        		if (sourceObjectInfo.hasInstanceAtState(sourceState.getName())){
        			return true;
        		}
        	}
        	
        	DebugInfoEntry debugEntry;
        	if (stoppedOnManualActivation){
        		debugEntry = new DebugInfoEntry(link,
          			DebugInfoEntry.REASON.SHOULD_BE_MANUALLY_ACTIVATED);
        	}else{
        		debugEntry = new DebugInfoEntry(link,
        			DebugInfoEntry.REASON.LINK_NOT_SATISFIED);
        	}
        	debugEntry.addCauseOfNonsatisfiedEntry(sourceInfo.getEntity());
        	debugInfo.addDebugEntry(debugEntry);
        	return false;
        		
        }

        case OpcatConstants.CONDITION_LINK : {
      		IXStateEntry sourceState = (IXStateEntry)sourceInfo.getEntity();
      		long parentObjectId = sourceState.getParentIXObjectEntry().getId();
      		ObjectInfoEntry sourceObjectInfo = (ObjectInfoEntry)creationTable.getInfoEntry(parentObjectId);
      		shouldBeSkipped = shouldBeSkipped || !sourceObjectInfo.hasInstanceAtState(sourceState.getName());
          return true;
        }
        //maybe to remove scheduling process termination, if the link is satisfied and 
        //is linked to the current process 
        //my next task!!!LERA
        case OpcatConstants.INVOCATION_LINK :
        	
        case OpcatConstants.CONSUMPTION_EVENT_LINK :
          return true;
      }

//      throw new RuntimeException("Unexpected link type "+link.getLinkType());
      return true;
    }
  }
  
  //maybe IXInstanceEntry and connecting link should be also
  //kept
  class StateValuePair{
	 private IXStateEntry state = null;
	 private String value = null;
	 private IXObjectEntry obj = null;
	 //object is needed to get all the existing states
	 //and other persistent data, like data type
	 StateValuePair(String value_,IXStateEntry state_, IXObjectEntry obj_){
		 state = state_;
		 value = value_;
		 obj = obj_;
	 }
	 StateValuePair(String value_, IXObjectEntry obj_){
		 this(value_, null, obj_);
	 }
	 void setState(IXStateEntry state_){
		 state = state_;
	 }
	 void setValue(String value_){
		 value = value_;
	 }
	 boolean hasValue(){
		 return value == null ? false : true;
	 }
	 boolean hasState(){
		 return state == null ? false : true;
	 }
	 String getValue(){return value;}
	 IXStateEntry getState(){return state;}
	 //sets matching to value state - not implemented
	 IXStateEntry setState2Value(){
		 if(value == null) return null;
		 Enumeration en = obj.getStates();
		 while(en.hasMoreElements()){
			 IXStateEntry st = (IXStateEntry)en.nextElement();
			 String name = st.getName();
			 if(name.equals(value)){
				 state = st;
				 return state;
			 }
		 }
		 return null;
	 }
  };

}
