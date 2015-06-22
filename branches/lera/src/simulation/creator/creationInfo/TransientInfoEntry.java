package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXSystem;

public class TransientInfoEntry extends CreationInfoEntry {
	protected static final long NOT_DEFINED_ID = -2;
	private PersistentInfoEntry parent;
	private long entryId;
	
	public TransientInfoEntry(IXSystem system, long id, PersistentInfoEntry parent) {
		super(system);
		this.parent = parent;
		entryId = id;
	}
	
	public PersistentInfoEntry getParent(){
		return parent;
	}
	
	public IXEntry getParentEntity(){
		return parent.getEntity();
	}

	@Override
	public long getId() {
		if (entryId == NOT_DEFINED_ID){
			throw new IllegalStateException(" The id for this info entry is not defined");
		}

		return entryId;
	}

}
