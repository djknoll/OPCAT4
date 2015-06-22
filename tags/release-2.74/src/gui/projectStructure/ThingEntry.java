package gui.projectStructure;

import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXRole;
import exportedAPI.opcatAPIx.IXThingEntry;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;


/**
 * <p>This class represents entry of OPM thing. In this class we additionally hold
 * information about OPD which zooms in this thing.
 *
 * @version	2.0
 * @author		Stanislav Obydionnov
 * @author		Yevgeny   Yaroker
 *
 */

public class ThingEntry
    extends ConnectionEdgeEntry
    implements IXThingEntry, IThingEntry {
  private Opd zoomedInOpd;
  private ThingEntry parentThing;

  /**
   * Creates ThingEntry that holds all information about specified pThing.
   *
   * @param pEdge object of OpmThing class.
   */
  public ThingEntry(OpmThing pThing, OpdProject project) {
    super(pThing, project);
    zoomedInOpd = null;
    parentThing = null;
  }

  public ThingEntry(OpmThing pThing, ThingEntry parentThing, OpdProject project) {
    super(pThing, project);
    zoomedInOpd = null;
    this.parentThing = parentThing;
  }

  public ThingEntry getParentThing() {
    return parentThing;
  }

  /**
   * Returns the OPD that zooming in this ThingEntry. null returned if there is no
   * such OPD.
   *
   */
  public Opd getZoomedInOpd() {
    return zoomedInOpd;
  }
  
  /**
   * Returns the IOPD that zooming in this ThingEntry. null returned if there is no
   * such OPD.
   */
  public IOpd getZoomedInIOpd() {
    return (IOpd)zoomedInOpd;
  }
  
  /**
   * Returns the IXOPD that zooming in this ThingEntry. null returned if there is no
   * such OPD.
   */
  public IXOpd getZoomedInIXOpd() {
    return (IXOpd)zoomedInOpd;
  }


  /**
   * Sets pZoomedInOpd to be zooming in for this ThingEntry.
   *
   */

  public void setZoomedInOpd(Opd pZoomedInOpd) {
    zoomedInOpd = pZoomedInOpd;
    updateInstances();
  }

  /**
   * Removes ZoomedInOpd from this ThingEntry.
   *
   */
  public void removeZoomedInOpd() {
    zoomedInOpd = null;
    updateInstances();
  }

  public void updateInstances() {
    super.updateInstances();

    if (zoomedInOpd != null) {
      StringTokenizer st = new StringTokenizer(zoomedInOpd.getName(), " ");
      zoomedInOpd.setName(st.nextToken() + " - " +
                          getLogicalEntity().getName().replace('\n', ' ') +
                          " in-zoomed");
    }
  }

  public ThingEntry getAncesctor() {
    ThingEntry retValue = null;
    ThingEntry curEntry = this;

    while (curEntry.getParentThing() != null) {
      curEntry = curEntry.getParentThing();
      retValue = curEntry;
    }

    return retValue;
  }

  public Enumeration getAllAncestors() {
    Hashtable ancestors = new Hashtable();

    ThingEntry curEntry = this;

    while (curEntry != null) {
      ancestors.put(curEntry, curEntry);
      curEntry = curEntry.getParentThing();
    }

    return ancestors.elements();
  }

  /**
   * Sets the physical/informational property of OpmThing. If value of
   * physical is true it's a physical thing, otherwise informational
   *
   */

  public void setPhysical(boolean physical) {
    ( (OpmThing) logicalEntity).setPhysical(physical);
  }

  /**
   * Returns true if this OpmThing is physical. If it's informational
   * returns false
   *
   */

  public boolean isPhysical() {
    return ( (OpmThing) logicalEntity).isPhysical();
  }

  /**
   * Sets the scope of OpmThing.
   *
   * @param scope a String specifying the scope of the Thing
   */

  public void setScope(String scope) {
    ( (OpmThing) logicalEntity).setScope(scope);
  }

  /**
   * Returns the scope of OpmThing.
   *
   * @return  a String containing thing's scope
   */

  public String getScope() {
    return ( (OpmThing) logicalEntity).getScope();
  }

  /**
   * Returns the number of MAS instances of the thing
   * @return int specifying number of MAS instances of the OPM Thing
   */
  public int getNumberOfMASInstances() {
    return ( (OpmThing) logicalEntity).getNumberOfInstances();
  }

  /**
   * Sets the number of MAS instances of the thing
   * @param int specifying number of MAS instances of the OPM Thing
   */
  public void setNumberOfMASInstances(int numOfInstances) {
    ( (OpmThing) logicalEntity).setNumberOfInstances(numOfInstances);
  }

  public String getRole() {
    return ( (OpmThing) logicalEntity).getRole();
  }
  
  public String getFreeTextRole()	{
	return ( (OpmThing) logicalEntity).getFreeTextRole();
  }

  public void setRole(String role) {
    ( (OpmThing) logicalEntity).setRole(role);
  }
  
  /**
   * Returns the roles of the thing - which reflect its notation in an imported
   * ontology.
   * @return Enumeration  Contains the roles as Role objects.
   * @author  Eran Toch
   */
  public Enumeration getRoles() {
	Enumeration roles;
  	try	{
  	 	roles = ((OpmThing)logicalEntity).getRolesManager().getRoles();
  	}
  	catch (Exception E)	{
  		roles = new Vector().elements();
  	}
	return roles;
  }
  
  /**
   * Returns the {@link RoleEntry} objects of the thing - which reflect its notation in an imported
   * ontology.
   * @return Enumeration  Contains the roles as <code>RoleEntry</code> objects.
   * @author  Eran Toch
   */
  public Enumeration getRolesEntries() {
	Enumeration roles;
	try	{
		roles = ((OpmThing)logicalEntity).getRolesManager().getRoleEntries();
	}
	catch (Exception E)	{
		roles = new Vector().elements();
	}
	return roles;
  }
  
  public void addRole(IXRole role)	{
	((OpmThing)logicalEntity).getRolesManager().addRole(role);
  }

  public void removeRole(IXRole role)	{
	((OpmThing)logicalEntity).getRolesManager().removeRole(role);
  }
}