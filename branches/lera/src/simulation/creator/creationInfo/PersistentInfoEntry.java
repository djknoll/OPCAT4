package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXSystem;

public abstract class PersistentInfoEntry extends CreationInfoEntry {
  protected IXEntry opmEntry;
	
  public PersistentInfoEntry(IXEntry entry, IXSystem system) {
  	super(system);
    opmEntry = entry;
  }
  
  public IXEntry getEntity(){
    return opmEntry;
  }
  
  public long getId(){
  	return opmEntry.getId();
  }
  

}
