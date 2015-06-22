package gui.metaLibraries.logic;

import exportedAPI.opcatAPI.IRole;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPIx.IXRole;
import exportedAPI.opcatAPIx.IXThingEntry;
import gui.metaDataProject.MetaDataItem;
import gui.metaDataProject.MetaDataProject;
import gui.opdProject.OpdProject;
import gui.projectStructure.ThingEntry;
import gui.opmEntities.OpmThing;
import gui.util.OpcatLogger;

/**
 * The role represents an ontological thing which can act as the role of system
 * elements. Roles must be included in a single ontology and point to a single
 * thing.
 */
public class Role implements IXRole, IRole, Comparable {
	/**
	 * A reference to the thing which is the actual role.
	 */
	private DataInstance theThing = null;

	/**
	 * The ontology which the thing belongs to.
	 */
	private MetaLibrary theOntology = null;

	private int state = 0;

	private long thingID = 0;

	private long libID = 0;
	
	
	//the next are to keep track of the roles which where in the library 
	//but are not there any more. so i save those into the opcat file 
	//and if the roles can not be loaded (ID gone from library)
	//i can report them to the user. 
	//private String ontologyName = "" ;  
	
	//private String thingName = "" ; 
	/////
	
	public static final int STATE_INIT = 0;

	public static final int UNLOADED = 1;

	public static final int LOADED = 2;

	/**
	 * A constructor method that sets the thing.
	 * 
	 * @param thing
	 *            The ontology thing which the role represents.
	 */
	public Role(Object thing, MetaLibrary onto) {
		this.load(thing, onto);
	}

	public Role(long _thingID, long _libID) {
		this.define(_thingID, _libID);
	}

	/**
	 * A constructor that takes a thing ID and a meta-library and loads the role
	 * from the meta library.
	 * 
	 * @param _thingID
	 * @param onto
	 * @throws MetaException
	 *             If the MetaLibrary was passed null
	 * @throws MetaException
	 *             Whatever the <code>load()</code> method throws.
	 */
	public Role(long _thingID, MetaLibrary onto) throws MetaException {
		if (onto == null) {
			throw new MetaException("Meta library was passed null to Role", "");
		}
		this.define(_thingID, onto.getReferenceID());
		try {
			this.load(onto);
		} catch (MetaException ex) {
			throw ex;
		}
	}

	public void define(long _thingID, long _libID) {
		this.thingID = _thingID;
		this.libID = _libID;
		this.state = UNLOADED;
	}

	public void load(MetaLibrary onto) throws MetaException,
			NullPointerException {
		// If the library was not loaded, then the role cannot be loaded
		if (onto.getState() != MetaLibrary.STATE_LOADED) {
			return;
		}
		if (onto == null) {
			throw new MetaException(
					"Meta-Library is passed to null for Role loading", "");
		}
		if (onto != null) {
			if ((onto.getType() == LibraryTypes.MetaLibrary)) {
				ISystemStructure metaStruct = null;
				try {
					OpdProject pr = (OpdProject) onto.getProjectHolder();
					metaStruct = pr.getISystemStructure();
				} catch (NullPointerException npe) {
					OpcatLogger
							.logError("Meta-Library strcture is passed to null for Role loading");
					OpcatLogger.logError(npe);
				}
				ThingEntry entry = (ThingEntry) metaStruct
						.getIEntry(this.getThingId());
				if (entry != null) {
					OpmThing thing = (OpmThing) entry.getLogicalEntity();
					this.load(thing, onto);
					this.getThing().setAdditionalData(entry);
				} 
			}

			if ((onto.getType() == LibraryTypes.MetaData)) {
				MetaDataProject reqProject = (MetaDataProject) onto
						.getProjectHolder();
				MetaDataItem req = reqProject.getMetaDataItem(this.getThingId());
				if (req != null) {
					this.load(req, onto);
				} 
			}
		}
	}

	public void load(Object thing, MetaLibrary onto) {
		this.theThing = new DataInstance(thing);
		this.theOntology = onto;
		this.state = LOADED;
		this.thingID = this.theThing.getId();
	}

	/**true
	 * Returns the thing which is represented by the ontology.
	 * 
	 * @return the OpmThing that the role refer to.
	 */
	public DataInstance getThing() { 
		return this.theThing;
	}

	/**
	 * Sets the thing.
	 * 
	 * @param aThing
	 */
	public void setThing(DataInstance aThing) {
		this.theThing = new DataInstance(aThing.getDataInstance());
		this.thingID = this.theThing.getId();
	}

	/**
	 * Returns the ontology that the
	 */
	public MetaLibrary getOntology() {
		return this.theOntology;
	}

	/**
	 * Sets the meta-library.
	 * 
	 * @param lib
	 */
	public void setOntology(MetaLibrary lib) {
		this.theOntology = lib;
		this.libID = lib.getReferenceID();
	}

	/**
	 * Returns the name of the thing.
	 * 
	 * @return The name of the text.
	 */
	public String getThingName() { 
		if (this.theThing != null) {
			return this.theThing.getName();
		} else {
			return "";
		}
	}

	/**
	 * Returns the name of the ontology.
	 * 
	 * @return The name of the text.
	 */
	public String getLibraryName() {
		if (this.theOntology != null) {
			return this.theOntology.getName();
		} else {
			return "";
		}
	}

	/**
	 * Strings the role. Used by the roles choosing tree.
	 */
	public String toString() {
		return this.getThingName();
	}

	/**
	 * Checks if another role is identical to the current one. The method
	 * returns <code>true</code> iff both the ontology and the thing are
	 * identical.
	 */
	public boolean equals(Object obj) {
		Role otherRole = (Role) obj;
		if ((this.getThingId() == otherRole.getThingId())
				&& (this.getLibraryId() == otherRole.getLibraryId())) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the {@link ThingEntry} representation of the Thing in the
	 * meta-library that the role is based on.
	 * 
	 * @return
	 */
	public IThingEntry getThingEntry() {
		ThingEntry entry = new ThingEntry((OpmThing) this.theThing
				.getDataInstance(), (OpdProject) this.theOntology
				.getProjectHolder());
		return entry;
	}

	/**
	 * Returns the {@link IXThingEntry} representation of the Thing in the
	 * meta-library that the role is based on.
	 * 
	 * @return
	 */
	public IXThingEntry getXThingEntry() {
		ThingEntry entry = new ThingEntry((OpmThing) this.theThing
				.getDataInstance(), (OpdProject) this.theOntology
				.getProjectHolder());
		return entry;
	}

	/**
	 * Returns the ID of thing in the ontology.
	 */
	public long getThingId() {
		if (this.state == UNLOADED) {
			return this.thingID;
		}
		return this.theThing.getId();
	}

	/**
	 * Returns the ID of the ontology.
	 */
	public long getLibraryId() {
		if (this.state == UNLOADED) {
			return this.libID;
		}
		return this.theOntology.getReferenceID();
	}

	/**
	 * @return The state of the role (<code>UNLOADED</code>,
	 *         <code>LOADED</code>).
	 */
	public int getState() {
		return this.state;
	}

	/**
	 * Checks if the role is loaded.
	 * 
	 * @return <code>true</code> if it is loaded, <code>false</code>
	 *         otherwise.
	 */
	public boolean isLoaded() {
		if (this.state == LOADED) {
			return true;
		}
		return false;
	}

	/**
	 * Creates a copy of the <code>Role</code> object.
	 */
	public Object clone() {
		Role copy = new Role(this.thingID, this.libID);
		copy.setState(this.getState());
		if (this.isLoaded()) {
			copy.setThing(this.getThing());
			copy.setOntology(this.getOntology());
		}
		return copy;
	}

	/**
	 * @param i
	 */
	public void setState(int i) {
		this.state = i;
	}

	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return this.toString().compareTo(arg0.toString());
	}


}
