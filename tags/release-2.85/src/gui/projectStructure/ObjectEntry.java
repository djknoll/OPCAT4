package gui.projectStructure;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmState;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;



/**
* <p>This class represents entry of OPM object. In this class we additionally hold
* information about all states belong to this object.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class ObjectEntry extends ThingEntry implements IObjectEntry, IXObjectEntry
{
	private Hashtable stateHash;

/**
* Creates ObjectEntry that holds all information about specified pObject.
*
* @param pObject object of OpmObject class.
*/

	public ObjectEntry(OpmObject pObject, OpdProject project)
	{
		super(pObject, project);
		this.stateHash = new Hashtable();
	}

    public ObjectEntry(OpmObject pObject, ThingEntry parentThing, OpdProject project)
	{
		super(pObject, parentThing, project);
		this.stateHash = new Hashtable();
	}



/**
* Returns Hashtable which contains all states belonging to this object.
*
*/

	public Hashtable getStateHash()
	{
		return this.stateHash;
	}

        public boolean hasStates()
        {
          return !this.stateHash.isEmpty();
        }

/**
* Returns Enumeration which contains all states belonging to this object.
* Use the Enumeration methods on the returned object to fetch the
* OpmState sequentially
*/
	public Enumeration getStateEnum()
	{
		return this.stateHash.elements();
	}

/**
* Returns Enumeration of StateEntry which contains all states belonging to this object.
* Use the Enumeration methods on the returned object to fetch the
* StateEntry sequentially
*/

    public Enumeration getStates()
    {
        Vector tempVector = new Vector();
        MainStructure pStructure = this.myProject.getComponentsStructure();

        for (Enumeration e = this.getStateEnum(); e.hasMoreElements();)
        {
            OpmState tState = (OpmState)e.nextElement();
            tempVector.add(pStructure.getEntry(tState.getId()));
        }

        return tempVector.elements();
    }

/**
* Adds newState to data structure containing all states belonging to this OPM object
*
*/
	public void addState(OpmState newState)
	{
		this.stateHash.put(new Long(newState.getId()), newState);
	}

/**
* Removes OpmState with specified stateID from
* data structure containing all states belonging to this OPM object
*
*/
	public void removeState(long stateID)
	{
		this.stateHash.remove(new Long(stateID));
	}

/**
* Removes state from
* data structure containing all states belonging to this OPM object
*
*/
	public void removeState(OpmState state)
	{
		this.stateHash.remove(new Long(state.getId()));
	}

/**
* Returns OpmState with specified stateID from
* data structure containing all states belonging to this OPM object.
* null is returned if there is no such state
*/
	public OpmState getState(long stateID)
	{
		return (OpmState)(this.stateHash.get(new Long(stateID)));
	}





/**
* Returns true if this OpmObject is persistent.
*
*/

	public boolean isPersistent()
    {
        return ((OpmObject)this.logicalEntity).isPersistent();
    }

/**
* Sets the persistent property of this OpmObject.
*
*/

	public void setPersistent(boolean persistent)
    {
        ((OpmObject)this.logicalEntity).setPersistent(persistent);
    }

/**
* Returns String representing type  of this OpmObject
*
*/
	public String getType()
    {
        return ((OpmObject)this.logicalEntity).getType();
    }

/**
* Sets type of this OpmObject.
*
*/

	public void setType(String type)
    {
        ((OpmObject)this.logicalEntity).setType(type);
    }

/**
* Returns true if this OpmObject is key.
*
*/

	public boolean isKey()
    {
        return ((OpmObject)this.logicalEntity).isKey();
    }


/**
* Sets key property of this OpmObject.
*
*/

	public void setKey(boolean key)
    {
        ((OpmObject)this.logicalEntity).setKey(key);
    }

/**
* Returns index name of this OpmObject.
*
*/
	public String getIndexName()
    {
        return ((OpmObject)this.logicalEntity).getIndexName();
    }


/**
* Sets index name of if this OpmObject.
*
*/

	public void setIndexName(String indexName)
    {
        ((OpmObject)this.logicalEntity).setIndexName(indexName);
    }

/**
* Returns index order of if this OpmObject.
*
*/

	public int getIndexOrder()
    {
        return ((OpmObject)this.logicalEntity).getIndexOrder();
    }

/**
* Sets index order of if this OpmObject.
*
*/
	public void setIndexOrder(int indexOrder)
    {
        ((OpmObject)this.logicalEntity).setIndexOrder(indexOrder);
    }

/**
* Returns initial value of if this OpmObject.
*
*/
	public String getInitialValue()
    {
        return ((OpmObject)this.logicalEntity).getInitialValue();
    }

/**
* Sets initial value of if this OpmObject.
*
*/

	public void setInitialValue(String initialValue)
    {
        ((OpmObject)this.logicalEntity).setInitialValue(initialValue);
    }

    public void setTypeOriginId(long typeOriginId)
    {
        ((OpmObject)this.logicalEntity).setTypeOriginId(typeOriginId);
    }

    public long getTypeOriginId()
    {
        return ((OpmObject)this.logicalEntity).getTypeOriginId();
    }
    
    public String toString() {
    	return this.getName() ; 
    }

}

