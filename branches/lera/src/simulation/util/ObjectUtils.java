package simulation.util;

import java.util.Enumeration;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXRelationEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;

public class ObjectUtils {
	
	public static IXObjectEntry getParent(IXObjectEntry object, IXSystem system){
	    Enumeration instances = object.getRelationByDestination();
	    while (instances.hasMoreElements()){
	    	IXRelationEntry currRelation = (IXRelationEntry)instances.nextElement();
	    	if (currRelation.getRelationType() == OpcatConstants.INSTANTINATION_RELATION){
	    		return (IXObjectEntry)system.getIXSystemStructure().getIXEntry(currRelation.getSourceId());
	    	}
	    }
		
		return null;
	}
	
	public static IXStateEntry getState(IXObjectEntry object, String name){
		Enumeration states = object.getStates();
		while (states.hasMoreElements()){
			IXStateEntry currState = (IXStateEntry)states.nextElement();
			if (currState.getName().equals(name)){
				return currState;
			}
		}
		return null;
	}
	

}
