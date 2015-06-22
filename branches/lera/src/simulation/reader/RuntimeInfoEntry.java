package simulation.reader;

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
public class RuntimeInfoEntry {
  protected IXSystem opmSystem;
  protected IXEntry opmEntry;
  private boolean isActivated;

  public RuntimeInfoEntry(IXEntry entry, IXSystem system) {
    opmEntry = entry;
    opmSystem = system;
    isActivated = false;
  }

  public IXEntry getInstance(){
    return opmEntry;
  }

  public void setActivated(boolean isActivated){
    this.isActivated = isActivated;
  }

  public boolean isActivated(){
    return isActivated;
  }

}

