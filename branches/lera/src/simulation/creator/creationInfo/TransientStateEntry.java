package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;

public class TransientStateEntry extends TransientInfoEntry {

	public TransientStateEntry(IXSystem system, StateInfoEntry parent) {
		super(system, NOT_DEFINED_ID, parent);
		// TODO Auto-generated constructor stub
	}
	
	public String getName(){
		return ((IXStateEntry)getParentEntity()).getName();
	}

}
