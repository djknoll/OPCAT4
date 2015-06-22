package simulation.creator.creationInfo;
import java.util.*;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;
import simulation.creator.*;
import simulation.creator.rules.ObjectTerminationRule;
import simulation.creator.rules.Rule;
import simulation.creator.rules.StateActivationRule;
import simulation.plugin.ILogicalTask;
import simulation.plugin.impl.lifespan.RunHistoryEntry;
import simulation.plugin.impl.lifespan.RunHistoryEvent;

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
public class CreationInfoTable implements ICreationConfigListener{
  private Hashtable<Long, CreationInfoEntry> infoTable;
  private IXSystem opmSystem;
  private CreationConfig config;

  public CreationInfoTable(IXSystem system, CreationConfig config) {
    opmSystem = system;
    this.config = config;
    infoTable = new Hashtable<Long, CreationInfoEntry>();
    Enumeration opmEntries = opmSystem.getIXSystemStructure().getElements();
    //Persistent elements are created at the first phase
    while (opmEntries.hasMoreElements()){
      IXEntry currEntry = (IXEntry)opmEntries.nextElement();
      if (currEntry instanceof IXProcessEntry){
        infoTable.put(currEntry.getId(), new ProcessInfoEntry((IXProcessEntry)currEntry, system));
        continue;
      }

      if (currEntry instanceof IXObjectEntry){
        if (((IXObjectEntry)currEntry).isInstance()){
        	continue;
        }

        ObjectInfoEntry objectEntry =  new ObjectInfoEntry((IXObjectEntry)currEntry, system);
        infoTable.put(currEntry.getId(), objectEntry);
    
      	Enumeration states = ((IXObjectEntry)currEntry).getStates();
      	while (states.hasMoreElements()){
      		IXStateEntry currState = (IXStateEntry)states.nextElement();
      		infoTable.put(currState.getId(), new StateInfoEntry(currState, opmSystem));
      	}
      	
        Enumeration instances = ((IXObjectEntry)currEntry).getRelationBySource();
        while (instances.hasMoreElements()){
        	IXRelationEntry currRelation = (IXRelationEntry)instances.nextElement();
        	if (currRelation.getRelationType() == OpcatConstants.INSTANTINATION_RELATION){
        		IXObjectEntry childObject = (IXObjectEntry)system.getIXSystemStructure().getIXEntry(currRelation.getDestinationId());
        		InstanceInfoEntry instanceEntry =  new InstanceInfoEntry(system, childObject ,objectEntry);
        		addInstanceEntry(instanceEntry);
        		
        		Enumeration childStates = childObject.getStates();
        		while (childStates.hasMoreElements()){
        			IXStateEntry childState = (IXStateEntry)childStates.nextElement();
        			IXStateEntry parentState = objectEntry.getState(childState.getName());
        			StateInfoEntry parentStateInfo = (StateInfoEntry)getInfoEntry(parentState.getId());
        			TransientStateEntry childStateInfo = new TransientStateEntry(system, parentStateInfo); 
        			instanceEntry.addState(childStateInfo);
          		infoTable.put(childState.getId(), childStateInfo);        			
        		}
        		instanceEntry.setActiveRandomState(getConfig().isStatesRandomelyActivated());
        	}
        }
      	
        continue;
      }
    }
    config.addChangeListener(this);
  }
  
  public IXSystem getSystem(){
  	return opmSystem;
  }

  public void configChanged(){
    Iterator<CreationInfoEntry> iter = infoTable.values().iterator();
    while (iter.hasNext()){
      CreationInfoEntry entry = iter.next();
      if (entry instanceof ObjectInfoEntry){
        ObjectInfoEntry objectEntry = (ObjectInfoEntry)entry;
        objectEntry.invalidateLinksCachedInfo();
      }
    }
  }
  
  public void addInstanceEntry(InstanceInfoEntry entry){
  	ObjectInfoEntry parentEntry = (ObjectInfoEntry)entry.getParent();
  	if (parentEntry == null){
  		throw new IllegalArgumentException("Parent entry of passed instance is not exist in creation table");
  	}
  	
  	parentEntry.addInstance(entry);
  	infoTable.put(entry.getId(), entry);
  }
  
  public void removeInstanceEntry(InstanceInfoEntry entry){
  	ObjectInfoEntry parentEntry = (ObjectInfoEntry) entry.getParent();
		if (parentEntry == null) {
			throw new IllegalArgumentException("Parent entry of passed instance is not exist in creation table");
		}

		parentEntry.removeInstance(entry);
		infoTable.remove(entry.getId());
	}
  

  public CreationInfoEntry getInfoEntry(long entityId){
    return infoTable.get(entityId);
  }

  public Iterator<CreationInfoEntry> iterator(){
    return infoTable.values().iterator();
  }

  public CreationConfig getConfig(){
    return config;
  }
}
