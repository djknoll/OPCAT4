package gui.opmEntities;

import exportedAPI.OpcatConstants;
/**
 * ontology import
 * @author Eran Toch
 */

import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.RolesManager;
import gui.metaLibraries.logic.LibraryTypes;
import gui.projectStructure.ThingEntry;
import gui.util.OpcatLogger;

import java.net.URL;
import java.util.Vector;

/**
 * This class represents Thing in OPM
 * 
 * @version 2.0
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 */

public abstract class OpmThing extends OpmConnectionEdge {
	// ---------------------------------------------------------------------------
	// The private attributes/members are located here
	private boolean physical; // true if physical, false if informational

	private String scope; // can be 0 - public,1 - protected ,2 - private

	private int numberOfInstances;

	private String role;

	/**
	 * A roles manager that manage {@link Role} objects for the current
	 * <code>OpmThing</code>. The manager is set to <code>null</code> and
	 * is initialized only if called by the <code>getRolesManager</code>
	 * method.
	 * 
	 * @author Eran Toch
	 */
	protected RolesManager rolesManager = null;

	/**
	 * Creates an OpmThing with specified id and name. Id of created OpmThing
	 * must be unique in OPCAT system
	 * 
	 * @param id
	 *            OpmThing id
	 * @param name
	 *            OpmThing name
	 */

	public OpmThing(long thingId, String thingName) {
		super(thingId, thingName);
		this.physical = false;
		this.scope = OpcatConstants.PUBLIC;
		this.numberOfInstances = 1;
		this.role = "";
	}

	protected void copyPropsFrom(OpmThing origin) {
		super.copyPropsFrom(origin);
		this.physical = origin.isPhysical();
		this.scope = origin.getScope();
		this.role = origin.getFreeTextRole();
		this.numberOfInstances = origin.getNumberOfInstances();

		// Copying the roles - using the roles manager
		// By Eran Toch
		this.rolesManager = (RolesManager) origin.getRolesManager().clone();

	}

	protected boolean hasSameProps(OpmThing pThing) {
		return (super.hasSameProps(pThing)
				&& (this.physical == pThing.isPhysical())
				&& this.scope.equals(pThing.getScope())
				&& this.role.equals(pThing.getRole())
				&& (this.numberOfInstances == pThing.getNumberOfInstances()) && (this
				.getRolesManager().equals(pThing.getRolesManager())));
	}

	/**
	 * Sets the physical/informational property of OpmThing. If value of
	 * physical is true it's a physical thing, otherwise informational
	 * 
	 */

	public void setPhysical(boolean physical) {
		this.physical = physical;
	}

	/**
	 * Returns true if this OpmThing is physical. If it's informational returns
	 * false
	 * 
	 */

	public boolean isPhysical() {
		return this.physical;
	}

	/**
	 * Sets the scope of OpmThing.
	 * 
	 * @param scope
	 *            a String specifying the scope of the Thing
	 */

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * Returns the scope of OpmThing.
	 * 
	 * @return a String containing thing's scope
	 */

	public String getScope() {
		return this.scope;
	}

	public int getNumberOfInstances() {
		return this.numberOfInstances;
	}

	public void setNumberOfInstances(int nOfIns) {
		this.numberOfInstances = nOfIns;
	}

	/**
	 * Returns the full graphical representation of the roles, including free
	 * text <code>String</code> roles and meta-libraries based roles.
	 * 
	 * @author Eran Toch
	 * @return
	 */
	public String getRole() {
		String output = "";
		if ((this.role != null) && !this.role.equals("")) {
			output += this.role;
		}
		if (this.rolesManager != null) {
			if (this.getRolesManager().hasRoles()) {
				if (!output.equals("")) {
					output += ", ";
				}
				output += this.getRolesManager().getRolesText(
						LibraryTypes.MetaLibrary);
			}
		}
		return output;
	}

	/**
	 * Returns the text of the free text <code>String</code> role (without
	 * meta-libraries roles).
	 * 
	 * @author Eran Toch
	 */
	public String getFreeTextRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Returns the {@link RolesManager}, which manages roles for this
	 * <code>OpmThing</code>. The method creates a new
	 * <code>RolesManager</code> if it was <code>null</code>. Each
	 * <code>Thing</code> has one <code>RolesManager</code>.
	 * 
	 * @return The <code>RolesManager</code> of this Thing.
	 * @author Eran Toch
	 */
	public RolesManager getRolesManager() {
		if (this.rolesManager == null) {
			this.rolesManager = new RolesManager();
		}
		return this.rolesManager;
	}

	/**
	 * Returns a a <code>Vector</code> object containing pairs of Thing name /
	 * Library Name.
	 * 
	 * @return A <code>Vector</code> object holding <code>Property</code>
	 *         object, in which the thing name has the key of
	 *         <code>RolesManager.THING_NAME</code> and the meta-library name
	 *         is keyed under <code>RolesManager.LIBRARY_NAME</code>.
	 *         <p>
	 *         The following code demonstrates how to print the role reference:
	 *         <p>
	 *         <code>
	 * Vector allRoles = theThing.getRolesRepresentation();<br>
	 * if (allRoles.size() > 0)	{<br>
	 * 		Properties aRole = (Properties)allRoles.get(0);<br>
	 * 		String roleThingName = aRole.getProperty(RolesManager.THING_NAME);<br>
	 * 		String roleLibraryName = aRole.getProperty(RolesManager.LIBRARY_NAME);<br>
	 * 		System.out.println(roleThingName + ":" + roleLibraryName);<br>
	 * }<br>
	 * </code>
	 * @author Eran Toch
	 */
	public Vector getRolesRepresentation() {
		Vector vec = this.getRolesManager().getOPLRepresentation();
		return vec;
	}

	public void updateDefaultURL() {

		// if thing is Env AND has Roles AND does not have a URL, then
		// we put the first Role meta path has the URL.
		if (canBeGeneric()
				&& this.isEnviromental()
				&& this.getRolesManager().getLoadedRoles().hasMoreElements()
				&& (this.getUrl().equalsIgnoreCase("none") || (this.getUrl() == null))) {
			MetaLibrary myMeta = ((Role) this.getRolesManager()
					.getLoadedRoles().nextElement()).getOntology();
			try {
				URL url = new URL("file", "", myMeta.getPath());
				this.setUrl(url.getProtocol() + "://" + url.getPath());
			} catch (Exception ex) {
				OpcatLogger.logError(ex);
			}

		}
	}

	public boolean canBeGeneric() {
		if (ThingEntry.getEntryFromOpmThing(this) != null) {
			return ThingEntry.getEntryFromOpmThing(this).getZoomedInOpd() == null;
		} else {
			return true;
		}
	}

}