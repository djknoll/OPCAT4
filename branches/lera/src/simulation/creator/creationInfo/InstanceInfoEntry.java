package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.*;
import exportedAPI.opcatAPIx.IXRelationEntry;
import exportedAPI.opcatAPIx.IXLinkEntry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

import simulation.creator.rules.StateActivationRule;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;


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
public class InstanceInfoEntry extends TransientInfoEntry{
	private HashMap<String, TransientStateEntry> states;
	
  public InstanceInfoEntry(IXSystem system, ObjectInfoEntry parent, long id) {
    super(system, id,  parent);
    states = new HashMap<String, TransientStateEntry>();
  }

  public InstanceInfoEntry(IXSystem system, IXObjectEntry instance, ObjectInfoEntry parent) {
    this(system, parent , instance.getId());
  }
  
  public void addState(StateInfoEntry parentState){
  		states.put(parentState.getEntity().getName(),
  				new TransientStateEntry(opmSystem, parentState));
  }

  public void addState(TransientStateEntry stateEntry){
		states.put(stateEntry.getName(), stateEntry);
  }

  public TransientStateEntry getStateInfo(String name){
  	return states.get(name);
  }

  public boolean isThereActiveStates(){
    Iterator<TransientStateEntry> iter = states.values().iterator();
    while (iter.hasNext()){
      if (iter.next().isActivated()){
        return true;
      }
    }

    return false;
  }
  
  //if param = false, activates only initial state or doesn't do anything
  public void setActiveRandomState(boolean isStatesRandomelyActivated){
	  	Iterator<TransientStateEntry> iter = states.values().iterator();
	    int numOfStates = states.size();
	    if(numOfStates == 0)return;
	    while (iter.hasNext()){
	    	TransientStateEntry currState = iter.next(); 
	        if(((IXStateEntry)currState.getParentEntity()).isInitial()){ 
	    	  currState.setActivated(true);
	    	  return;
	      }
	    }
	    if(!isStatesRandomelyActivated)return;
	    int randomState = (int)(Math.random() * numOfStates);
	    Object[] keys = states.keySet().toArray();
	    TransientStateEntry currState = states.get(keys[randomState]);
	    currState.setActivated(true);
	    return;
	  }
  
  public Iterator<TransientStateEntry> getActiveStates(){
  	ArrayList<TransientStateEntry> activeStates = new ArrayList<TransientStateEntry>();
    Iterator<TransientStateEntry> iter = states.values().iterator();
    while (iter.hasNext()){
    	TransientStateEntry currState = iter.next(); 
      if (currState.isActivated()){
        activeStates.add(currState);
      }
    }
  	
  	return activeStates.iterator();
  	
  }
  
}
