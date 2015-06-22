/*
 * Created on 04/06/2004
 */
package extensionTools.validator.finder;

import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IRelationEntry;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPI.IThingEntry;

/**
 * Finds the neighbors of all the generalizations of a given thing. 
 * @author Eran Toch
 * Created: 04/06/2004
 */
public class GeneralizedFinder extends Finder implements NeighborFinder {
	public GeneralizedFinder()	{
	}
	
	public Vector findNeighbors(IThingEntry thing, ISystemStructure struct)	{
		this.structure = struct;
		Vector vec = new Vector();
		vec.addAll(this.getGeneralizationNeighbors(thing));
		return vec;
	}

	/**
	 * Returns all the constrains that are connected to things which are generalization
	 * of the current thing (super-things). The method calls <code>findConstraints</code>
	 * in order to find all connected constraints to it's super-things. Note that
	 * it may results in recursive calls to this method.
	 * @param thing	The current thing.
	 */	
	public Vector getGeneralizationNeighbors(IThingEntry thing)	{
		Vector vec = new Vector();
		Enumeration connections = thing.getRelationByDestination();
		while (connections.hasMoreElements()) {
			IRelationEntry rel = (IRelationEntry) connections.nextElement();
			if (rel.getRelationType() == OpcatConstants.SPECIALIZATION_RELATION) {
			
			IThingEntry destinationThing =
				(IThingEntry) this.structure.getIEntry(rel.getSourceId());
				vec.addAll(super.findNeighbors(destinationThing, this.structure));
				this.getGeneralizationNeighbors(destinationThing);
			}
		}
		return vec;
	}

}
