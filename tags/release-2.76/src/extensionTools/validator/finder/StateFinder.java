package extensionTools.validator.finder;

import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingEntry;
import extensionTools.validator.algorithm.Neighbor;

/**
 * Finds all the states that a given object has. The realtion is typed as
	 * <code>STATE_OF_RELATION</code>.
 * @author Eran Toch
 *
 */
public class StateFinder implements NeighborFinder {

	/**
	 * Retrieves all the states for a given object. The realtion is typed as
	 * <code>STATE_OF_RELATION</code>.
	 */
	public Vector findNeighbors(IThingEntry thing, ISystemStructure struct) {
		Vector vec = new Vector();
		if (!(thing instanceof IObjectEntry))	{
			return vec;
		}
		IObjectEntry object = (IObjectEntry)thing;
		Enumeration states = object.getStates();
		while (states.hasMoreElements())	{
			IStateEntry state = (IStateEntry)states.nextElement();
			Neighbor neighbor = new Neighbor(state, Finder.STATE_OF_RELATION, null, Finder.DESTINATION_DIRECTION);
			vec.add(neighbor);
		}
		return vec;
	}
	
	/* (non-Javadoc)
	 * @see extensionTools.validator.finder.NeighborFinder#setExsitingFinding(java.util.Vector)
	 */
	public void setExsitingFinding(Vector findings) {}

}
