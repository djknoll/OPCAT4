package exportedAPI.opcatAPI;
import java.util.Enumeration;

/**
 * IObjectEntry is an interface to logical representation of Object in OPM meaning
 */

public interface IObjectEntry extends IThingEntry
{

	/**
	* Checks if this object is persistent.
	* @return true if object is persistent.
	*/
	public boolean isPersistent();

	/**
	* Returns String representing type property of this OPM object.
	* @retrun String represening type of the OPM object
	*/
	public String getType();

	/**
	* Checks if OPM object is a key.
	* @return true if the OPM object is key, false otherwise
	*/
	public boolean isKey();

	/**
	* Returns index name of this OPM Object.
	* @return index name of this OPM Object.
	*/
	public String getIndexName();


	/**
	* Returns index order of this OPM Object.
	* @return index order of this OPM Object.
	*/
	public int getIndexOrder();


	/**
	* Returns initial value of this OPM Object.
	* @return initial value of this OPM Object.
	*/
	public String getInitialValue();
	
	

	/**
	 * Returns the IObjectEntry's type origin.<br>
	 * If IObjectEntry is an instance of some non-basic type this method
	 * returns the ID of IObjectEntry that represents type definition.
	 * @return the ID of IObjectEntry that represents type definition.
	 */
	public long getTypeOriginId();

    /**
    * Returns Enumeration of IStateEntry which contains all states belonging to this object.
    * Use the Enumeration methods on the returned object to fetch the
    * IStateEntry sequentially
    */

    public Enumeration getStates();

  	/**
  	* Returns number of instances (objects related by instantiation relation).
  	* @return initial value of this OPM Object.
  	*/
  	public int getNumberOfInstances();

  	/**
  	* Determines whether the given IObjectEntry is instance (objects related by instantiation relation).
  	* @return true if IObjectEntry is instance.
  	*/

  	public boolean isInstance();  	
    
}