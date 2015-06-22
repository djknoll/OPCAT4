package simulation.plugin.impl.lifespan;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import simulation.plugin.ILogicalTask;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXStateEntry;

public class ChildObjectHistoryEntry extends RunHistoryEntry {
	private HashMap<String, RunHistoryEntry> states = null;
	
	public ChildObjectHistoryEntry(IXObjectEntry entry) {
		super(entry);
		states = new HashMap<String, RunHistoryEntry>();
		Enumeration stateEnum = entry.getStates();
		while (stateEnum.hasMoreElements()){
			IXStateEntry currState = (IXStateEntry)stateEnum.nextElement();
			states.put(currState.getName(), new RunHistoryEntry(currState));
		}
	}
	
	public void addEvent(RunHistoryEvent anEvent){
		if (anEvent.getEntity() instanceof IXStateEntry){
			states.get(anEvent.getEntity().getName()).addEvent(anEvent);
		}else{
			super.addEvent(anEvent);
		}
	}
	
	public void removeEvent(RunHistoryEvent anEvent){
		if (anEvent.getEntity() instanceof IXStateEntry){
			states.get(anEvent.getEntity().getName()).removeEvent(anEvent);
		}else{
			super.removeEvent(anEvent);
		}
	}
	
	
	 public Iterator<RunHistoryEntry> stateIterator() {
		return states.values().iterator();
	}


	public RunHistoryEntry getState(String name) {
		return states.get(name);
	}

}
