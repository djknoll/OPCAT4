package gui.metaLibraries.logic;

import exportedAPI.opcatAPI.IRole;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPIx.IXRole;
import exportedAPI.opcatAPIx.IXThingEntry;
import gui.opdProject.OpdProject;
import gui.projectStructure.ThingEntry;
import gui.opmEntities.OpmThing;
import gui.util.OpcatLogger;

/**
 * The role represents an ontological thing which can act as the role of system
 * elements. Roles must be included in a single ontology and point to a single
 * thing.
 */
public class Role implements IXRole, IRole
{
	/**
	* A reference to the thing which is the actual role.
	*/
	private OpmThing theThing = null;

	/**
	 * The ontology which the thing belongs to.
	 */
	private MetaLibrary theOntology = null;
	
	private int state = 0;
	
	private long thingID = 0;
	private long libID = 0;

	public static final int STATE_INIT = 0;
	public static final int UNLOADED = 1;
	public static final int LOADED = 2;
	
	/**
	 * A constructor method that sets the thing.
	 * @param thing The ontology thing which the role represents.
	 */
	public Role(OpmThing thing, MetaLibrary onto) {
		load(thing, onto);		
	}

	public Role(long _thingID, long _libID) {
		define(_thingID, _libID);
	}
	
	/**
	 * A constructor that takes a thing ID and a meta-library and loads the 
	 * role from the meta library.
	 * @param _thingID
	 * @param onto
	 * @throws MetaException	If the MetaLibrary was passed null
	 * @throws MetaException	Whatever the <code>load()</code> method throws.
	 */
	public Role(long _thingID, MetaLibrary onto) throws MetaException	{
		if (onto == null)	{
			throw new MetaException("Meta library was passed null to Role", "");	
		}		
		define(_thingID, onto.getReferenceID());
		try	{
			load(onto);
		}
		catch (MetaException ex)	{
			throw ex;	
		}
	}

	public void define(long _thingID, long _libID)	{
		thingID = _thingID;
		libID = _libID;
		state = UNLOADED;
	}
	
	public void load(MetaLibrary onto)	throws MetaException, NullPointerException	{
		//If the library was not loaded, then the role cannot be loaded
		if (onto.getState() != MetaLibrary.STATE_LOADED)	{
			return;
		}
		if (onto == null)	{
			throw new MetaException("Meta-Library is passed to null for Role loading", "");
		}
		if (onto != null)	{
			ISystemStructure metaStruct = null;
			try	{
				OpdProject pr = onto.getProjectHolder();
				metaStruct = pr.getISystemStructure();
			}
			catch (NullPointerException npe)	{
				OpcatLogger.logError("Meta-Library strcture is passed to null for Role loading");
				OpcatLogger.logError(npe);
			}
			ThingEntry entry = (ThingEntry)metaStruct.getIEntry(thingID);
			if (entry != null)	{
				OpmThing thing = (OpmThing)entry.getLogicalEntity();
				load(thing, onto);
			}
		}
	}
	
	public void load(OpmThing thing, MetaLibrary onto)	{
		theThing = thing;
		theOntology = onto;
		state = LOADED;
	}

	/**
	 * Returns the thing which is represented by the ontology.
	 * @return the OpmThing that the role refer to.
	 */
	public OpmThing getThing() {
		return theThing;
	}

	/**
	 * Sets the thing.
	 * @param aThing
	 */
	public void setThing(OpmThing aThing)	{
		theThing = aThing;
		thingID = aThing.getId();
	}
	
	/**
	 * Returns the ontology that the
	 */
	public MetaLibrary getOntology() {
		return theOntology;
	}

	/**
	 * Sets the meta-library.
	 * @param lib
	 */
	public void setOntology(MetaLibrary lib)	{
		theOntology = lib;
		libID = lib.getReferenceID();
	}
	
	/**
	 * Returns the name of the thing.
	 * @return The name of the text.
	 */
	public String getThingName() {
		if (theThing != null) {
			return theThing.getName();
		}
		else {
			return "";
		}
	}

	/**
	 * Returns the name of the ontology.
	 * @return The name of the text.
	 */
	public String getLibraryName() {
		if (theOntology != null) {
			return theOntology.getName();
		}
		else {
			return "";
		}
	}

	/**
	 * Strings the role. Used by the roles choosing tree.
	 */
	public String toString() {
		return getThingName();
	}

	/**
	 * Checks if another role is identical to the current one. The method returns
	 * <code>true</code> iff both the ontology and the thing are identical.
	 */
	public boolean equals(Role otherRole) {
		if (this.getThingId() == otherRole.getThingId() && this.getLibraryId() == otherRole.getLibraryId())	{
			return true;
		}
		return false;
	}
	/**
	 * Returns the {@link ThingEntry} representation of the Thing in the meta-library
	 * that the role is based on.
	 * @return
	 */
	public IThingEntry getThingEntry() {
		ThingEntry entry = new ThingEntry(theThing, theOntology.getProjectHolder());
		return entry;
	}

	/**
	 * Returns the {@link IXThingEntry} representation of the Thing in the meta-library
	 * that the role is based on.
	 * @return
	 */
	public IXThingEntry getXThingEntry() {
	ThingEntry entry = new ThingEntry(theThing, theOntology.getProjectHolder());
	return entry;
}


	/**
	 * Returns the ID of thing in the ontology.
	 */
	public long getThingId() {
		if (state == UNLOADED)	{
			return thingID;
		}
		return theThing.getId();
	}
	
	/**
	 * Returns the ID of the ontology.
	 */
	public long getLibraryId()	{
		if (state == UNLOADED)	{
			return libID;
		}
		return theOntology.getReferenceID();
	}
	/**
	 * @return	The state of the role (<code>UNLOADED</code>, <code>LOADED</code>).
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Checks if the role is loaded.
	 * @return	<code>true</code> if it is loaded, <code>false</code> otherwise.
	 */
	public boolean isLoaded()	{
		if (state == LOADED)	{
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a copy of the <code>Role</code> object.
	 */
	public Object clone()	{
		Role copy = new Role(thingID, libID);
		copy.setState(this.getState());
		if (this.isLoaded())	{
			copy.setThing(this.getThing());
			copy.setOntology(this.getOntology());
		}
		return copy;
	}

	/**
	 * @param i
	 */
	public void setState(int i) {
		state = i;
	}

}
