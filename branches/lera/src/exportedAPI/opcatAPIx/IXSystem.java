package exportedAPI.opcatAPIx;
import java.awt.Dimension;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.undo.AbstractUndoableEdit;

import exportedAPI.OpdLayout;

/**
 * IXSystem is an interface to Opcat2 system. This is the 'enterance' to internal structure.
 * Through this class one can get access to IXSystemStructure and all other info. See example how to get
 * into IXSystem.
 */

public interface IXSystem
{
		
	/**
	 * Updates all changes in the IXSystem. This method automatically rebuilds
	 * repository trees and the OPL according to changes made in this IXSystem
	 */
		
	public void updateChanges();
    
	public void arrangeOPDs(OpdLayout layout); 

	/**
	* Returns an interface to current OPD (user is currently working with this OPD)
	* @return current IXOpd
	*/
	public IXOpd getCurrentIXOpd();

    /**
    * Returns the main OPCAT frame.
    * @return JFrame main OPCAT frame.
    */
    public JFrame getMainFrame();
    public Dimension getUserAreaSize();

	/**
	* Returns an interface to IXSystemStructure - data structure which holds all inforation about this project
	* @return Opcat's IXSystemStructure
	* @see IXSystemStructure
	*/
	public IXSystemStructure getIXSystemStructure();


	/**
	* Returns project's name
	* @return project name string
	*/
	public String getName();

	/**
	* Returns name of project's creator
	* @return author string
	*/
	public String getCreator();

	/**
	* Returns project's creaion date
	* @return project creaion date
	*/
	public Date getCreationDate();

	/**
	* Sets project name
	* @param new project name
	*/
	public void setName(String name);

	/**
	* Sets project creator name
	* @param new project creator name
	*/
	public void setCreator(String creator);


    public String getInfoValue(String key);
    public void setInfoValue(String key, String value);

	/**
	 * Returns String that holds OPL for OPD given by opdNum
	 * @param isHTML - if true String returned is HTML formated string, if false plain text OPL
	 * @param opdNum - the ID of OPD to generate OPL for.
	 * @return String that holds OPL for OPD given by opdNum
	 */
	public String getOPL(boolean isHTML, long opdNum);

	public void showOPD(long opdNum);

	/**
	 * Returns String that holds OPL for System.
	 * @param isHTML - if true String returned is HTML formated string, if false plain text OPL
	 * @return String that holds OPL for System
	 */
	public String getOPL(boolean isHTML);

	/**
	 * Adds an IXObjectEntry named <code>name</code>. Creates instance (IXObjectInstance)
	 * for it at (<code>x</code>, <code>y</code>) coordinates
	 * to OPD specified by <code>opdID</code> and returns it.
	 * @param name - name for IXObjectEntry
	 * @param x - x coordinate for IXObjectInstance
	 * @param y - y coordinate for IXObjectInstance
	 * @param opdID - ID of IXOpd to add newly created IXObjectInstance to
	 * @return newly created IXObjectInstance
	 */
	public IXObjectInstance addObject(String name, int x, int y, long opdId);

	/**
	 * Adds an IXObjectEntry named <code>name</code>. Creates instance (IXObjectInstance)
	 * for it at (<code>x</code>, <code>y</code>) coordinates
	 * into IXThingInstance specified by parentInstance.
	 * @param name - name for IXObjectEntry
	 * @param x - x coordinate for IXObjectInstance
	 * @param y - y coordinate for IXObjectInstance
	 * @param parentInstance - an IXThingInstance to add newly created IXObjectEntry into.
	 * @return newly created IXObjectInstance
	 */
	public IXObjectInstance addObject(String name, int x, int y, IXThingInstance parentInstance);

	
	/**
	 * Returns maximal Entity Entry (for save purposes)
	 */
	public long getEntityMaxEntry();
	public void setEntityMaxEntry(long pEntityMaxEntry);
	public long reserveId();
	
	/**
	 * Adds an new IXObjectEntry with specified id and connects it  to the parent by 
	 * instantiation relation. This method should be used only for special cases when id should be reserved and d
	 * used before actual creation. 
	 * 
	 * @param parent - an IXObjectEntry to add newly created IXObjectEntry into.
	 * @param instanceId - id of new "instance object". This Id should be first by reserveId method.
	 * @param isVisible - determines whether new IXObjectEntry is visible
	 * @return newly created IXObjectEntry
	 */

	
	public UndoInfo addInstance4Object(IXObjectEntry parent, long instanceId, boolean isVisible);

	
	/**
	 * Adds an new IXObjectEntry and connects it  to the parent by 
	 * instantiation relation.
	 * 
	 * @param parent - an IXObjectEntry to add newly created IXObjectEntry into.
	 * @param isVisible - determines whether new IXObjectEntry is visible
	 * @return newly created IXObjectEntry
	 */

	public UndoInfo addInstance4Object(IXObjectEntry parent, boolean isVisible);

	/**
	 * Adds an IXProcessEntry named <code>name</code>. Creates instance (IXProcessInstance)
	 * for it at (<code>x</code>, <code>y</code>) coordinates
	 * to OPD specified by <code>opdID</code> and returns it.
	 * @param name - name for IXProcessEntry
	 * @param x - x coordinate for IXProcessInstance
	 * @param y - y coordinate for IXProcessInstance
	 * @param opdID - ID of IXOpd to add newly created IXProcessInstance to
	 * @return newly created IXProcessInstance
	 */
	public IXProcessInstance addProcess(String name, int x, int y, long opdId);

	/**
	 * Adds an IXProcessEntry named <code>name</code>. Creates instance (IXProcessInstance)
	 * for it at (<code>x</code>, <code>y</code>) coordinates
	 * into IXThingInstance specified by parentInstance.
	 * @param name - name for IXProcessEntry
	 * @param x - x coordinate for IXProcessInstance
	 * @param y - y coordinate for IXProcessInstance
	 * @param parentInstance - an IXThingInstance to add newly created IXProcessInstance into.
	 * @return newly created IXObjectInstance
	 */
	public IXProcessInstance addProcess(String name, int x, int y, IXThingInstance parentInstance);


	/**
	 * Adds an IXRelationEntry of type relationType to System when IXThingEntry represented by IXThingInstance source
	 * is a source and IXThingEntry represented by IXThingInstance destination is destination. Creates an instance of new
	 * IXRelationEntry and connects it between IXThingInstance source and IXThingInstance destination and returns it.
	 * @param source - source IXThingInstance for new relation
	 * @param destination - destination IXThingInstance for new relation
	 * @param relationType - Type of relation to create as specified in exportedAPI.OpcatConstants
	 * @return - newly created IXRelationInstance
	 */
	public IXRelationInstance addRelation(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination, int relationType);

	/**
	 * Adds an IXLinkEntry of type lonkType to System when IXThingEntry represented by IXThingInstance source
	 * is a source and IXThingEntry represented by IXThingInstance destination is destination. Creates an instance of new
	 * IXLinkEntry and connects it between IXThingInstance source and IXThingInstance destination and returns it.
	 * @param source - source IXThingInstance for new link.
	 * @param destination - destination IXThingInstance for new link.
	 * @param relationType - Type of link to create as specified in exportedAPI.OpcatConstants
	 * @return - newly created IXLinkInstance
	 */
	public IXLinkInstance addLink(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination, int linkType);

	/**
	 * Adds State named <code>name</code> to IXObjectEntry that IXObjectInstance parentObject is one
	 * of its instances and newly created state IXStateEntry.
	 * @param name - name for new State
	 * @param parentObject - instance of OPM object to add new state to
	 * @return IXStateEntry of newly created State
	 */
	public IXStateEntry addState(String name, IXObjectInstance parentObject);

	/**
	 * Deletes the entry and its related links and relations.
	 * @param entry - entry to delete
	 */

	public AbstractUndoableEdit deleteEntry(IXConnectionEdgeEntry entry);
	
  public AbstractUndoableEdit delete(IXInstance delInstance);

    /**
     * Checks legality of deletion of given IXInstance.
     * Returns the result as IXCheckResult
     * @param insToCheck - checked IXInstance
     * @return IXCheckResult
     */

	public IXCheckResult checkDeletion(IXInstance insToCheck);

    /**
     * Checks legality of possible procedural link of linkType
     * addition between given source and destination.
     * Returns the result as IXCheckResult
     * @param source - source for possible link.
     * @param destination - destination for possible link.
     * @param linkType - type of possible link as specified in OpcatConstants interface.
     * @return IXCheckResult
     * @see exportedAPI.OpcatConstants
     */
	public IXCheckResult checkLinkAddition(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination, int linkType);

    /**
     * Checks legality of possible structural relation of relationType
     * addition between given source and destination.
     * Returns the result as IXCheckResult
     * @param source - source for possible relation.
     * @param destination - destination for possible relation.
     * @param relationType - type of possible relation as specified in OpcatConstants interface.
     * @return IXCheckResult
     * @see exportedAPI.OpcatConstants
     */
	public IXCheckResult checkRelationAddition(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination, int relationType);

    /**
     * Returns IXLinkInstance between given source and detination.
     * Returns null if no link connects given source and destination.
     * @param source - source for possible relation.
     * @param destination - destination for possible relation.
     * @return IXLinkInstance or null if no link connects given source and destination.
     */

    public IXLinkInstance getIXLinkBetweenInstances(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination);

    /**
     * Returns Enumeration of IXRelationInstance between given source and
     * detination. Returns empty enumeration if no relation connects
     * given source and destination.
     * @param source - source for possible relation.
     * @param destination - destination for possible relation.
     * @return Enumeration of IXRelationInstance.
     */
    public Enumeration getIXRelationsBetweenInstances(IXConnectionEdgeInstance source, IXConnectionEdgeInstance destination);

	/**
	 * Returns an Enumeration of all the <code>MetaLibrary</code> models imported
	 * by this system.
	 * @return	Enumeration containing <code>MetaLibrary</code> objects.
	 */
	public Enumeration getMetaLibraries();
	public Enumeration getMetaLibraries(int type);

}