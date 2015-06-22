package simulation.creator;

import java.util.*;

public class CreationConfig implements Cloneable{
  private boolean isAutoInitiationRequired;
  private boolean isStopOnAgents;
  private boolean isStatesRandomelyActivated;
  private int defaultProcessDuration;
  ArrayList<ICreationConfigListener> listeners = new ArrayList<ICreationConfigListener>();

  public CreationConfig() {
    isAutoInitiationRequired = true;
    isStopOnAgents = false;
    isStatesRandomelyActivated = true;
    defaultProcessDuration = 1500;
  }

  private void notifyListeners(){
    synchronized (listeners){
      for (int i = 0; i < listeners.size(); i++) {
        listeners.get(i).configChanged();
      }
    }
  }

  public void addChangeListener(ICreationConfigListener listener){
    synchronized (listeners){
      listeners.add(listener);
    }
  }

  public void removeChangeListener(ICreationConfigListener listener){
    synchronized (listeners){
      listeners.remove(listener);
    }
  }

  public int getDefaultProcessDuration(){
	  return this.defaultProcessDuration;
  }
	  
  public void setDefaultProcessDuration(int defaultProcessDuration){
	  this.defaultProcessDuration = defaultProcessDuration;
  }


  public boolean isStatesRandomelyActivated(){
  	return this.isStatesRandomelyActivated;
  }
  
  public void setStatesRandomelyActivated(boolean isActivated){
  	this.isStatesRandomelyActivated = isActivated;
  }
  
  public boolean isAutoInitiated(){
    return isAutoInitiationRequired;
  }

  public void setAutoInitiated(boolean isAutoInitiated){
    if (isAutoInitiationRequired == isAutoInitiated){
      return;
    }
    isAutoInitiationRequired = isAutoInitiated;
    notifyListeners();
  }

  public boolean isStopOnAgents(){
    return isStopOnAgents;
  }

  public void setStopOnAgents(boolean isStopOnAgents){
    if (isStopOnAgents == this.isStopOnAgents){
      return;
    }
    this.isStopOnAgents = isStopOnAgents;
    notifyListeners();
  }


  public Object clone(){
    CreationConfig clonedObject = new CreationConfig();
    clonedObject.isAutoInitiationRequired = this.isAutoInitiationRequired;
    clonedObject.isStopOnAgents = this.isStopOnAgents;
    clonedObject.defaultProcessDuration = this.defaultProcessDuration;
    return clonedObject;
  }
}
