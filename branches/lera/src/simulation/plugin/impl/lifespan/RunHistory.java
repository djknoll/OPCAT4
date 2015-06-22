package simulation.plugin.impl.lifespan;

import java.util.*;

import simulation.plugin.ILogicalTask;
import simulation.plugin.impl.lifespan.LifespanTableModel.ChildRow;
import simulation.plugin.impl.lifespan.LifespanTableModel.ParentRow;
import simulation.util.ObjectUtils;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;

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
public class RunHistory implements IHistoryListener{
  private Hashtable<Long, RunHistoryEntry> historyTable = new Hashtable<Long, RunHistoryEntry>();
  private Hashtable<Long, Long> childParentMap = new Hashtable<Long, Long>();
  private ArrayList<IHistoryListener> listeners;
  private int currSystemTime;
  private IXSystem system;

  public RunHistory(IXSystem opmSystem){
	system = opmSystem;  
    IXSystemStructure structure = opmSystem.getIXSystemStructure();
    Enumeration entries = structure.getElements();
    while (entries.hasMoreElements()){
      IXEntry nextEntry = (IXEntry)entries.nextElement();
      if (nextEntry instanceof IXProcessEntry){
        historyTable.put(nextEntry.getId(), new RunHistoryEntry(nextEntry));
        continue;
      }
      
      if (nextEntry instanceof IXObjectEntry){
    	  IXObjectEntry object = (IXObjectEntry)nextEntry;
    	  if (!object.isInstance()){
    		ParentObjectHistoryEntry parent = new ParentObjectHistoryEntry(object);
            historyTable.put(nextEntry.getId(), parent);
            Enumeration instances = object.getRelationBySource();
            while (instances.hasMoreElements()){
            	IXRelationEntry currRelation = (IXRelationEntry)instances.nextElement();
            	if (currRelation.getRelationType() == OpcatConstants.INSTANTINATION_RELATION){
            		IXObjectEntry childObject = (IXObjectEntry)structure.getIXEntry(currRelation.getDestinationId());
            		parent.addChild(new ChildObjectHistoryEntry(childObject));
            		childParentMap.put(childObject.getId(), parent.getOpmEntity().getId());
            		parent.addEvent(new RunHistoryEvent(childObject, 0, ILogicalTask.TYPE.CREATION));
            	}
            }
            
            parent.addHistoryListener(this);
            
            continue;
          }
      }
    }

    listeners = new ArrayList<IHistoryListener>();
  }
  
  public void entryChanged(final long id){}

  public void timeChanged(final int time){}
  
  public void childAdded(long parentId, long childId){
	  childParentMap.put(childId, parentId);
	  notifyListeners4Child(parentId, childId);
  }
  

  private boolean isChild(IXEntry entity){
	  if (entity instanceof IXObjectEntry){
		  return ((IXObjectEntry)entity).isInstance();
	  }
	  return false;
  }
  
  private long getParentId(IXEntry entity){
	  if (entity instanceof IXObjectEntry){
		  IXObjectEntry parent = ObjectUtils.getParent((IXObjectEntry)entity, system);
		  if (parent != null){
			  return parent.getId(); 
		  }
	  }
	  return -1;
  }
  
  public void addHistoryEvent(ILogicalTask task, RunHistoryEvent anEvent){
	long entityId;
	IXEntry entity = task.getEntity();
	if (entity instanceof IXStateEntry){
		entity = ((IXStateEntry)entity).getParentIXObjectEntry();
	}
	
	if (childParentMap.containsKey(entity.getId())){ // If the entity was already deleted we can't fetch its parent from OPM system !!!
		entityId = childParentMap.get(entity.getId());
	}else{
		if (!isChild(entity)){  
			entityId = entity.getId();
		}else{
			entityId = getParentId(entity);
		}
	}
	
    RunHistoryEntry entry = historyTable.get(entityId);
    entry.addEvent(anEvent);
    notifyListeners(entityId);
  }

  public void removeHistoryEvent(ILogicalTask task, RunHistoryEvent anEvent){
	  long entityId;
	  IXEntry entity = task.getEntity();
	  if (entity instanceof IXStateEntry){
		  entity = ((IXStateEntry)entity).getParentIXObjectEntry();
	  }
		
	  if (childParentMap.containsKey(entity.getId())){ // If the entity was already deleted we can't fetch its parent from OPM system !!!
		  entityId = childParentMap.get(entity.getId());
	  }else{
		  if (!isChild(entity)){  
			  entityId = entity.getId();
		  }else{
			  entityId = getParentId(entity);
		  }
	  }
	
    RunHistoryEntry entry = historyTable.get(entityId);
    entry.removeEvent(anEvent);
    notifyListeners(entityId);
  }
  
  public IXSystem getSystem(){
	  return  system;
  }
  
  public Iterator<RunHistoryEntry> getEntries(){
    return historyTable.values().iterator();
  }

  public int size(){
    return historyTable.size();
  }

  public void setSystemTime(int time){
    currSystemTime = time;
    notifyListeners4Time(time);
  }

  public int getSystemTime(){
    return currSystemTime;
  }
  
  public RunHistoryEntry getHistoryEntry(long id){
	  return historyTable.get(id);
  }

  private void notifyListeners(long changedEntryId){
    synchronized (listeners){
      for (int i = 0; i < listeners.size(); i++) {
        listeners.get(i).entryChanged(changedEntryId);
      }
    }
  }

  private void notifyListeners4Time(int time){
    synchronized (listeners){
      for (int i = 0; i < listeners.size(); i++) {
        listeners.get(i).timeChanged(time);
      }
    }
  }

  private void notifyListeners4Child(long parentId, long childId){
	  synchronized (listeners){
		  for (int i = 0; i < listeners.size(); i++) {
			  listeners.get(i).childAdded(parentId, childId);
		  }
	  }
  }
  

  public void addHistoryListener(IHistoryListener listener){
    synchronized (listeners){
      listeners.add(listener);
    }
  }

  public void removeHistoryListener(IHistoryListener listener){
    synchronized (listeners){
      listeners.remove(listener);
    }
  }
}
