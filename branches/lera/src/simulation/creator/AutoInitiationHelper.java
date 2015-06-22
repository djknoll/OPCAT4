package simulation.creator;

import exportedAPI.opcatAPIx.*;
import exportedAPI.OpcatConstants;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.RelationEntry;
import simulation.creator.rules.*;

import java.util.Enumeration;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import simulation.util.*;
import java.util.*;
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
public class AutoInitiationHelper {
  private CreationInfoTable creationTable;

  public AutoInitiationHelper(CreationInfoTable table) {
    creationTable = table;
  }
 
  //Automatically generated first rules (first instances for objects, 
  //only for objects that system doesn't create via processes)
  public List<Rule> getAutoInitiatedRules(){
    ArrayList<Rule> autoInititiatedRules = new ArrayList<Rule>();
    autoInititiatedRules.addAll(getAutoInitiatedObjectRules());
    autoInititiatedRules.addAll(getAutoInitiatedProcessRules());
    return autoInititiatedRules;
  }
  
  public List<Rule> getAutoInitiatedProcessRules(){
    ArrayList<Rule> autoInititiatedRules = new ArrayList<Rule>();
    //root OPD = 1
    Enumeration things = creationTable.getSystem().getIXSystemStructure().getThingsInOpd(1);
    while (things.hasMoreElements()){
    	Object currEntity = things.nextElement();
    	if (currEntity instanceof IXProcessInstance){
    		IXProcessInstance process = (IXProcessInstance)currEntity;
    		if (!isTrigerredByEvent(process)){
    			autoInititiatedRules.add(new ProcessActivationRule(500, null, 
    					(IXProcessEntry)process.getIXEntry(), creationTable));
    		}
    	}
    }
    return autoInititiatedRules;
  }
  
  private static boolean isTrigerredByEvent(IXProcessInstance process){
		Enumeration links = process.getRelatedInstances();
		while (links.hasMoreElements()){
			Object currInstance = links.nextElement();
			if (currInstance instanceof IXLinkInstance){
				IXLinkInstance currLink = (IXLinkInstance)currInstance;
				int linkType = ((IXLinkEntry)currLink.getIXEntry()).getLinkType();
				if (linkType == OpcatConstants.EXCEPTION_LINK ||
						linkType == OpcatConstants.CONSUMPTION_EVENT_LINK ||
						linkType == OpcatConstants.INSTRUMENT_EVENT_LINK ||
						linkType == OpcatConstants.INVOCATION_LINK){
					return true;
				}
			}
		}
  	return false;
  }
  
  public List<Rule> getAutoInitiatedObjectRules(){
    ArrayList<Rule> autoInititiatedRules = new ArrayList<Rule>();
    Iterator<CreationInfoEntry> iter = creationTable.iterator();
    while (iter.hasNext()){
      CreationInfoEntry currEntry = iter.next();
      if (currEntry instanceof ObjectInfoEntry){
      	ObjectInfoEntry objectEntry = (ObjectInfoEntry)currEntry;
        IXObjectEntry currObject = (IXObjectEntry)objectEntry.getEntity();
        if (ProcessUtils.getParent(currObject) != null){
          continue;
        }

        Rule newRule = getRule4Object(currObject, 0);
        if (newRule != null){
          autoInititiatedRules.add(newRule);
        }
      }
    }

    for (int i = 0; i < autoInititiatedRules.size(); i++){
      autoInititiatedRules.get(i).setExternallyActivated(true);
    }

    return autoInititiatedRules;
  }

  private Rule getRule4Object(IXObjectEntry object, int creationTime){
    if (object.isInstance() || isResultedObject(object)){
    	return null;
    }

    return new ObjectActivationRule(creationTime, null , object, creationTable);
  }
  
  private boolean isResultedEdge(IXConnectionEdgeEntry edge){
    Enumeration destLink = edge.getLinksByDestination();    
    if (destLink != null){
      while (destLink.hasMoreElements()){
        if (((IXLinkEntry)destLink.nextElement()).getLinkType() == OpcatConstants.RESULT_LINK){
          return true;
        }
      }
    }
    
    return false;
  }
  
  private boolean isConsumedEdge(IXConnectionEdgeEntry edge){
    Enumeration destLink = edge.getLinksBySource();    
    if (destLink != null){
      while (destLink.hasMoreElements()){
        if (((IXLinkEntry)destLink.nextElement()).getLinkType() == OpcatConstants.CONSUMPTION_LINK){
          return true;
        }
      }
    }
    
    return false;
  }
  
  private boolean isResultedObject(IXObjectEntry object){
  	if (isResultedEdge(object)){
  		return true;
  	}
    
    Enumeration states = object.getStates();
    boolean thereIsResultedStates = false;
    boolean thereIsConsumedStates = false;
    
    if (states != null){
    	while (states.hasMoreElements()){
    		IXStateEntry currState = (IXStateEntry)states.nextElement(); 
    		if (isResultedEdge(currState)){
    			thereIsResultedStates =  true;
    		}
    		
    		if (isConsumedEdge(currState)){
    			thereIsConsumedStates =  true;
    		}
    	}
    }

    
    return (thereIsResultedStates && !thereIsConsumedStates);
  }


  public List<Rule> getAutoInitiatedChildRules(IXProcessEntry process, int processTime){
    ArrayList<Rule> autoInititiatedRules = new ArrayList<Rule>();
    IXOpd parentOpd = process.getZoomedInIXOpd();
    if (parentOpd == null){
      return autoInititiatedRules;
    }

    Enumeration children = parentOpd.getMainIXInstance().getChildThings();
    while (children.hasMoreElements()){
      IXThingInstance currChild = (IXThingInstance)children.nextElement();
      if (currChild instanceof IXObjectInstance){
        Rule newRule = getRule4Object((IXObjectEntry)currChild.getIXEntry(), processTime);
        if (newRule != null){
          autoInititiatedRules.add(newRule);
        }

      }
    }

    for (int i = 0; i < autoInititiatedRules.size(); i++){
      autoInititiatedRules.get(i).setExternallyActivated(false);
    }

    return autoInititiatedRules;
  }
  
  public List<Rule> getAutoTerminatedChildRules(IXProcessEntry process, int terminationTime){
    ArrayList<Rule> autoInititiatedRules = new ArrayList<Rule>();
    IXOpd parentOpd = process.getZoomedInIXOpd();
    if (parentOpd == null){
      return autoInititiatedRules;
    }

    Enumeration children = parentOpd.getMainIXInstance().getChildThings();
    while (children.hasMoreElements()){
      IXThingInstance currChild = (IXThingInstance)children.nextElement();
      if (currChild instanceof IXObjectInstance){
      	List<Rule> newRules = getTerminationRules4Object((IXObjectEntry)currChild.getIXEntry(), terminationTime);
      	autoInititiatedRules.addAll(newRules);
      }
    }

    for (int i = 0; i < autoInititiatedRules.size(); i++){
      autoInititiatedRules.get(i).setExternallyActivated(false);
    }

    return autoInititiatedRules;
  }
  
  private List<Rule> getTerminationRules4Object(IXObjectEntry object, int terminationTime){
    ArrayList<Rule> autoRules = new ArrayList<Rule>();
    
  	if (object.isInstance()){
    	return autoRules;
    }
    
		ObjectInfoEntry objectEntry = (ObjectInfoEntry)creationTable.getInfoEntry(object.getId());
		Iterator<InstanceInfoEntry> instances = objectEntry.getInstances();
		
		while (instances.hasNext()){
			InstanceInfoEntry currInstance = instances.next();
				autoRules.add(new ObjectTerminationRule(terminationTime, null, object, currInstance, creationTable));
		}
    
    
    return autoRules;
  	
  }
  
}
