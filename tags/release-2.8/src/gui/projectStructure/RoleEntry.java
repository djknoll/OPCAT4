/*
 * Created on 14/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gui.projectStructure;

import exportedAPI.opcatAPI.IRole;
import exportedAPI.opcatAPIx.IXRole;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;

/**
 * A Role of a thing, according to an imported {@link MetaLibrary}.
 * @author Eran Toch
 */
public class RoleEntry implements IXRole, IRole {
	private Role theRole;
	
	public RoleEntry(long _thingID, long _libID)	{
		define(_thingID, _libID);
	}
	
	public RoleEntry(Role _role)	{
		theRole = _role;
	}
	
	/**
	 * Defines a role using a given thing ID and meta-library ID.
	 */
	public void define(long _thingID, long _libID)	{
		Role role = new Role(_thingID, _libID);
		theRole = role;
	}
	
	public Role getLogicalRole()	{
		return theRole;
	}
		
	public String getThingName()	{
		return theRole.getThingName();
	}
	
	public String getLibraryName()	{
		return theRole.getLibraryName();
	}
	
	public long getThingId()	{
		return theRole.getThingId();
	}
	
	public long getLibraryId()	{
		return theRole.getLibraryId();
	}
}
