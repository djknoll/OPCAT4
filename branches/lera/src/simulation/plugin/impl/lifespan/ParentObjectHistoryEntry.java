package simulation.plugin.impl.lifespan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import simulation.plugin.ILogicalTask;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXStateEntry;

public class ParentObjectHistoryEntry extends RunHistoryEntry {
	private HashMap<Long, ChildObjectHistoryEntry> children = null;
	private ArrayList<IHistoryListener> listeners;
	private int numOfAliveInstances = 0;
	
	public ParentObjectHistoryEntry(IXObjectEntry entry) {
		super(entry);
	    listeners = new ArrayList<IHistoryListener>();
	}
	
	public void addEvent(RunHistoryEvent anEvent){
		if (anEvent.getEventType() == ILogicalTask.TYPE.CREATION){
			if (numOfAliveInstances == 0){
				super.addEvent(new RunHistoryEvent(myEntry, anEvent.getTime(), ILogicalTask.TYPE.ACTIVATION));
			}
			numOfAliveInstances++;
		}
		
		if (anEvent.getEventType() == ILogicalTask.TYPE.DELETION){
			numOfAliveInstances--;
			if (numOfAliveInstances == 0){
				super.addEvent(new RunHistoryEvent(myEntry, anEvent.getTime(), ILogicalTask.TYPE.TERMINATION));
			}
		}
		
		IXEntry entity = anEvent.getEntity();
		if (entity instanceof IXStateEntry){
			entity = ((IXStateEntry)entity).getParentIXObjectEntry();
		}
		
		ChildObjectHistoryEntry childEntry = getChildren().get(entity.getId());
		if (childEntry == null){
			childEntry = new ChildObjectHistoryEntry((IXObjectEntry)entity);
			addChild(childEntry);
		}
		childEntry.addEvent(anEvent);
		
	}
	
	public void removeEvent(RunHistoryEvent anEvent){
		if (anEvent.getEventType() == ILogicalTask.TYPE.CREATION){
			numOfAliveInstances--;
			if (numOfAliveInstances == 0){
				super.removeEvent(new RunHistoryEvent(myEntry, anEvent.getTime(), ILogicalTask.TYPE.ACTIVATION));
			}
		}
		
		if (anEvent.getEventType() == ILogicalTask.TYPE.DELETION){
			if (numOfAliveInstances == 0){
				super.removeEvent(new RunHistoryEvent(myEntry, anEvent.getTime(), ILogicalTask.TYPE.TERMINATION));
			}
			numOfAliveInstances++;
		}
		
		IXEntry entity = anEvent.getEntity();
		if (entity instanceof IXStateEntry){
			entity = ((IXStateEntry)entity).getParentIXObjectEntry();
		}
		
		ChildObjectHistoryEntry childEntry = getChildren().get(entity.getId());
		if (childEntry == null){
			childEntry = new ChildObjectHistoryEntry((IXObjectEntry)entity);
			addChild(childEntry);
		}
		childEntry.removeEvent(anEvent);
	}
	
	
	 public Iterator<ChildObjectHistoryEntry> childrenIterator() {
		return getChildren().values().iterator();
	}

	private HashMap<Long, ChildObjectHistoryEntry> getChildren() {
		if (children == null) {
			children = new HashMap<Long, ChildObjectHistoryEntry>();
		}
		return children;
	}

	public void addChild(ChildObjectHistoryEntry child) {
		getChildren().put(child.getOpmEntity().getId(), child);
		notifyListeners4Child(child.getOpmEntity().getId());
	}

	public ChildObjectHistoryEntry getChild(long childId) {
		return getChildren().get(childId);
	}

	private void notifyListeners4Child(long childId){
		synchronized (listeners){
			for (int i = 0; i < listeners.size(); i++) {
				listeners.get(i).childAdded(myEntry.getId(), childId);
			}	
		}
	}


	public void addHistoryListener(IHistoryListener listener) {
		synchronized (listeners) {
			listeners.add(listener);
		}
	}

	public void removeHistoryListener(IHistoryListener listener) {
		synchronized (listeners) {
			listeners.remove(listener);
		}
	}

}
