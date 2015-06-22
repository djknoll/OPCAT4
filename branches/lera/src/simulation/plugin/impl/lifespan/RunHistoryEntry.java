package simulation.plugin.impl.lifespan;

import java.util.*;

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
public class RunHistoryEntry {
  private ArrayList<RunHistoryEvent> events = new ArrayList<RunHistoryEvent>();
  protected IXEntry myEntry;

  public RunHistoryEntry(IXEntry entry){
    myEntry = entry;
  }

  public IXEntry getOpmEntity(){
    return myEntry;
  }
  
  public void addEvent(RunHistoryEvent anEvent){
    if (!events.isEmpty() &&
        events.get(events.size() - 1).getTime() > anEvent.getTime()){
      throw new IllegalArgumentException("New event's time cannot be smaller than last events in history");
    }

    events.add(anEvent);
  }

  public void removeEvent(RunHistoryEvent anEvent){
    if (events.isEmpty() || !events.get(events.size() - 1).equals(anEvent)) {
      throw new IllegalArgumentException("Removed  event should be equal to the last event in history");
    }

    events.remove(events.size() - 1);
  }

  public Iterator<RunHistoryEvent> getEvents(){
    return events.iterator();
  }
  
  public int getNumOfEvents(){
	  return events.size();
  }
  
  public String toString(){
	  return myEntry.getName();
  }

}
