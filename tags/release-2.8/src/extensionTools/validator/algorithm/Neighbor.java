package extensionTools.validator.algorithm;

import exportedAPI.opcatAPI.IConnectionEdgeEntry;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IStateEntry;
import exportedAPI.opcatAPI.IThingEntry;

/**
 * Represents a conencted node on the OPM things graph. A connection might be a
 * link, a relation or another OPM attribute, such as zoom-in.
 * @author Eran Toch
 * Created: 04/06/2004
 */
public class Neighbor {
	private IThingEntry connectedThing;
	private int connectionType;
	private int direction;
	private IEntry connectionObject;

	/**
	 * Builds a neighbor object from the connected thing and the connection type.
	 * The method has a special behavior for conenctions with states. If the 
	 * _connectionEdge is found to be an instance of <code>IStateEntry</code>, then
	 * the method changes the <code>connectedThing</code> to the parent object
	 * of the state (and leaves the conenction type as is).
	 * @param _connectionEdge	The object on the other end of the edge (connection). 
	 * @param _connectionType	The type of the connection (from OpcatConstants, or from 
	 * an inner classification of the validation process)
	 * @param _direction	The direction of the connection (Finder.DESTINATION_DIRECTION
	 * or Finder.SOURCE_DIRECTION).
	 * @param	_connectionObject	The connection object (<code>IRelationEntry</code> or
	 * <code>ILinkEntry</code>). Can be <code>null</code>.
	 */
	public Neighbor(IConnectionEdgeEntry _connectionEdge, int _connectionType, IEntry _connectionObject, int _direction)	{
		if (_connectionEdge instanceof IThingEntry)	{
			connectedThing = (IThingEntry)_connectionEdge;
		}
		else if	(_connectionEdge instanceof IStateEntry)	{
			IStateEntry state = (IStateEntry)_connectionEdge;
			connectedThing = state.getParentIObjectEntry();
		}
		connectionType = _connectionType;
		connectionObject = _connectionObject;
		direction = _direction;
	}
	
	
	/**
	 * @return
	 */
	public IThingEntry getConnectedThing() {
		return connectedThing;
	}

	/**
	 * @return	The type of the connection, specified by <code>gui.opmEntities.Constants</code>, 
	 * or by specialized types used by the Finder graph.
	 */
	public int getConnectionType() {
		return connectionType;
	}

	/**
	 * @return	The direction of the connection (which can be <code>Finder.SOURCE_DIRECTION</code>
	 * or <code>Finder.DESTINATION_DIRECTION</code>.
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * @param entry
	 */
	public void setConnectedThing(IThingEntry entry) {
		connectedThing = entry;
	}


	/**
	 * @param i
	 */
	public void setConnectionType(int i) {
		connectionType = i;
	}

	/**
	 * @param i
	 */
	public void setDirection(int i) {
		direction = i;
	}

	/**
	 * @return
	 */
	public IEntry getConnectionObject() {
		return connectionObject;
	}

	/**
	 * @param entry
	 */
	public void setConnectionObject(IEntry entry) {
		connectionObject = entry;
	}

}
