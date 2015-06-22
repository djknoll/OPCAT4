package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.*;
import exportedAPI.opcatAPIx.IXLinkEntry;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class ObjectInfoEntry extends ConnectionEdgeInfoEntry{
  private ArrayList<InstanceInfoEntry> instances;

  public ObjectInfoEntry(IXObjectEntry entry, IXSystem system) {
    super(entry, system);
    instances = new ArrayList<InstanceInfoEntry>();
  }

  void addInstance(InstanceInfoEntry instance){
  	instances.add(instance);
  }
  
  void removeInstance(InstanceInfoEntry instance){
  	
  	instances.remove(instance);
  }
  
  public boolean hasInstances(){
  	return (instances.size() > 0);
  }
  
  public InstanceInfoEntry getInstanceAtState(String stateName){
  	for (int i = 0; i < instances.size(); i++){
  		TransientStateEntry state = instances.get(i).getStateInfo(stateName);
  		if (state != null && state.isActivated()){
  			return instances.get(i);
  		}
  	}
  	
  	return null;
  }
  
  public IXStateEntry getState(String stateName){
  	Enumeration states = ((IXObjectEntry)opmEntry).getStates();
  	while (states.hasMoreElements()){
  		IXStateEntry currState = (IXStateEntry)states.nextElement();
  		if (currState.getName().equals(stateName)){
  			return currState;
  		}
  	}
  	return null;
  }
  
  public boolean hasInstanceAtState(String stateName){
  	if (getInstanceAtState(stateName) == null){
  		return false;
  	}else{
  		return true;
  	}
  }

  public InstanceInfoEntry getRandomInstance(){
  	return instances.get(0);
  }
  
  public Iterator<InstanceInfoEntry> getInstances(){
  	return instances.iterator();
  }
  
  protected List<IXLinkEntry> getDirectConnectedRelevantLinks(IXConnectionEdgeEntry object, boolean isSource){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    Enumeration links;
    if (isSource){
      links = object.getLinksBySource();
    }else{
      links = object.getLinksByDestination();
    }


    if (links != null){
      while (links.hasMoreElements()){
        IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
        int type = currLink.getLinkType();
        if (type == OpcatConstants.CONSUMPTION_EVENT_LINK){
          list.add(currLink);
        }
      }
    }

    return list;
  }


}
