package simulation.creator.creationInfo;
import exportedAPI.opcatAPIx.*;

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
public abstract class CreationInfoEntry {
  protected IXSystem opmSystem;
  private boolean isActivated;
  private String dataPath;
  private int threadID;


  public CreationInfoEntry(IXSystem system) {
    opmSystem = system;
    isActivated = false;
    dataPath = null;
    threadID = -1;
  }

  public void setActivated(boolean isActivated){
    this.isActivated = isActivated;
  }

  public boolean isActivated(){
    return isActivated;
  }

  public void setDataPath(String dataPath){
    this.dataPath = dataPath;
  }

  public String getDataPath(){
    return dataPath;
  }

  public void setThreadID(int threadID){
    this.threadID = threadID;
  }

  public int getThreadID(){
    return threadID;
  }
  
  public abstract long getId();
  
  public boolean equals(Object obj){
  	if (this == obj){
  		return true;
  	}
  	
  	if (!(obj instanceof CreationInfoEntry)){
  		return false;
  	}
  	
  	return ((CreationInfoEntry)obj).getId() == getId();
  }
}
