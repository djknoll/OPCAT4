package simulation.plugin.impl.lifespan;

import exportedAPI.opcatAPIx.IXEntry;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class RunHistoryEvent {
  private int time;
  private int eventType;
  private IXEntry entity;
  
  public RunHistoryEvent(IXEntry entity, int time, int eventType){
	this.entity = entity;  
    this.time = time;
    this.eventType = eventType;
  }

  public IXEntry getEntity(){
	return entity;
  }

  
  public int getTime(){
    return time;
  }

  public int getEventType(){
    return eventType;
  }

  public boolean equals(Object anotherObject){
    if (anotherObject instanceof RunHistoryEvent){
      return (time == ((RunHistoryEvent)anotherObject).time &&
              eventType == ((RunHistoryEvent)anotherObject).eventType);
    }else{
      return false;
    }
  }
}
