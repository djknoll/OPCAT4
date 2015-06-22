package exportedAPI.opcatAPI;


import java.util.Enumeration;

/**
 * IThingEntry is an interface to logical representation of OPM Thing
 */

 public interface IThingEntry extends IConnectionEdgeEntry
{

	/**
	 * Returns the number of animated (graphically) instances. The meaning 
	 * of instance for object is number of objects related by instantiation relation,
	 * for process - number of current parallel executions for this process.
	 * @return int number of animated (graphically) instances.
	 */

	public int getNumberOfAnimatedInstances();
	
	/**
	* Checks if thing is physical
	* @return true if thing is physical, false if informational
	*/
	public boolean isPhysical();

	/**
    * Returns the scope of OpmThing. The scope can be one of constant values
    * specified in interface OpcatConstants.
	*
	* @return  a String containing thing's scope
	*/
	public String getScope();

	/**
	 * Returns the role of the thing
	 * @return String representing the role of the OPM Thing
	 */
	public String getRole();
	
	/**
	 * Returns the free text role string, without the meta-libraries roles.
	 * @return	String representing the role.
	 */
	public String getFreeTextRole();
	
	/**
	 * Returns an enumeration of ontology roles objects. Each object represent
	 * a role from an imported ontology.
	 * @return Enumeration Contains Role objects.
	 * @author Eran Toch
	 */
	public Enumeration getRoles();
	
	public Enumeration getRoles(int type);
	
	public String getRolesString(int type);	

	/**
	 * Returns the number of MAS instances of the thing
	 * @return int specifying number of MAS instances of the OPM Thing
	 */
	public int getNumberOfMASInstances();

	  /**
	   * Returns the OPD that zooming in this ThingEntry. null returned if there is no
	   * such OPD.
	   *
	   */
	  public IOpd getZoomedInIOpd();
	  

	    /**
	     * returns the Thing URL
	     * @return
	     */
	    public String getUrl() ;	  
}
