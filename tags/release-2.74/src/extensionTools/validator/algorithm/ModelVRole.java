package extensionTools.validator.algorithm;

import exportedAPI.opcatAPI.IThingEntry;

/**
 * Represent a role of a thing in the model.
 * @author Eran Toch
 * Created: 27/05/2004
 */
public class ModelVRole extends VRole {
	
	protected IThingEntry originalEntry;
	
	/**
	 * The constructor allow setting the original <code>IThingEntry</code>,
	 * used for presentation issues.
	 * @param _metaLibID	The ID of the meta-library
	 * @param _thingID		The ID of the thing (in the meta-library)
	 * @param _original	The original IThingEntry that the role belongs to.
	 */
	public ModelVRole(long _metaLibID, long _thingID, IThingEntry _original)	{
		super(_metaLibID, _thingID);
		originalEntry = _original;
	}
	
	/**
	 * Returns the name of the original thing that the role is set to.
	 */
	public String getThingName()	{
		if (originalEntry != null)	{
			return originalEntry.getName();
		}
		return "";
	}
	
	public IThingEntry getOriginalThing()	{
		return originalEntry;
	}
	
	
}
