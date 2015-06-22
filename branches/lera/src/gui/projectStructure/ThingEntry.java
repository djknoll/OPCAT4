package gui.projectStructure;

import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXRole;
import exportedAPI.opcatAPIx.IXThingEntry;
import extensionTools.search.OptionsBase;
import extensionTools.search.OptionsExectMatch;
import extensionTools.search.SearchAction;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.metaLibraries.logic.Role;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmThing;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * <p>
 * This class represents entry of OPM thing. In this class we additionally hold
 * information about OPD which zooms in this thing.
 * 
 * @version 2.0
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * 
 */

public class ThingEntry extends ConnectionEdgeEntry implements IXThingEntry,
		IThingEntry {
	private Opd zoomedInOpd;

	private ThingEntry parentThing;

	public ThingEntry(OpmThing pThing, OpdProject project) {
		this(pThing, project, true);
	}
	/**
	 * Creates ThingEntry that holds all information about specified pThing.
	 * 
	 * @param pEdge
	 *            object of OpmThing class.
	 */
	public ThingEntry(OpmThing pThing, OpdProject project, boolean instancesVisible) {
		super(pThing, project, instancesVisible);
		this.zoomedInOpd = null;
		this.parentThing = null;
	}

	public ThingEntry(OpmThing pThing, ThingEntry parentThing,
			OpdProject project) {
		this(pThing, project, true);
	}

	
	public ThingEntry(OpmThing pThing, ThingEntry parentThing,
			OpdProject project, boolean instancesVisible) {
		super(pThing, project, instancesVisible);
		this.zoomedInOpd = null;
		this.parentThing = parentThing;
	}

	public ThingEntry getParentThing() {
		return this.parentThing;
	}

	/**
	 * Returns the OPD that zooming in this ThingEntry. null returned if there
	 * is no such OPD.
	 * 
	 */
	public Opd getZoomedInOpd() {
		return this.zoomedInOpd;
	}

	public Opd getUnfoldingOpd() {
		Iterator iter = Collections.list(getInstances()).iterator();
		while (iter.hasNext()) {
			Instance ins = (Instance) iter.next();
			if (ins instanceof ThingInstance) {
				if (((ThingInstance) ins)._getUnfoldingOpd() != null) {
					return ((ThingInstance) ins)._getUnfoldingOpd();
				}
			}
		}
		return null;
	}

	/**
	 * Returns the IOPD that zooming in this ThingEntry. null returned if there
	 * is no such OPD.
	 */
	public IOpd getZoomedInIOpd() {
		return this.zoomedInOpd;
	}

	/**
	 * Returns the IXOPD that zooming in this ThingEntry. null returned if there
	 * is no such OPD.
	 */
	public IXOpd getZoomedInIXOpd() {
		return this.zoomedInOpd;
	}

	/**
	 * Sets pZoomedInOpd to be zooming in for this ThingEntry.
	 * 
	 */

	public void setZoomedInOpd(Opd pZoomedInOpd) {
		this.zoomedInOpd = pZoomedInOpd;
		this.updateInstances();
	}

	/**
	 * Removes ZoomedInOpd from this ThingEntry.
	 * 
	 */
	public void removeZoomedInOpd() {
		this.zoomedInOpd = null;
		this.updateInstances();
	}

	public void updateInstances() {
		super.updateInstances();

		if (this.zoomedInOpd != null) {
			StringTokenizer st = new StringTokenizer(
					this.zoomedInOpd.getName(), " ");
			this.zoomedInOpd.setName(st.nextToken() + " - "
					+ this.getLogicalEntity().getName().replace('\n', ' ')
					+ " in-zoomed");
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
     * Returns the number of animated (graphically) instances. The meaning 
	 * of instance for object is number of objects related by instantiation relation,
	 * for process - number of current parallel executions for this process.
	 * @return int number of animated (graphically) instances.
	 */
	public int getNumberOfAnimatedInstances() {
		return ((OpmThing) this.logicalEntity).getNumberOfAnimatedInstances();
	}

    /**
     * Sets the number of animated (graphically) instances. The meaning 
	 * of instance for object is number of objects related by instantiation relation,
	 * for process - number of current parallel executions for this process.
	 */
	public void setNumberOfAnimatedInstances(int nOfIns) {
		((OpmThing) this.logicalEntity).setNumberOfAnimatedInstances(nOfIns);
	}


	/**
	 * Sets the physical/informational property of OpmThing. If value of
	 * physical is true it's a physical thing, otherwise informational
	 * 
	 */

	public void setPhysical(boolean physical) {
		((OpmThing) this.logicalEntity).setPhysical(physical);
	}

	/**
	 * Returns true if this OpmThing is physical. If it's informational returns
	 * false
	 * 
	 */

	public boolean isPhysical() {
		return ((OpmThing) this.logicalEntity).isPhysical();
	}

	/**
	 * Sets the scope of OpmThing.
	 * 
	 * @param scope
	 *            a String specifying the scope of the Thing
	 */

	public void setScope(String scope) {
		((OpmThing) this.logicalEntity).setScope(scope);
	}

	/**
	 * Returns the scope of OpmThing.
	 * 
	 * @return a String containing thing's scope
	 */

	public String getScope() {
		return ((OpmThing) this.logicalEntity).getScope();
	}

	/**
	 * Returns the number of MAS instances of the thing
	 * 
	 * @return int specifying number of MAS instances of the OPM Thing
	 */
	public int getNumberOfMASInstances() {
		return ((OpmThing) this.logicalEntity).getNumberOfInstances();
	}

	/**
	 * Sets the number of MAS instances of the thing
	 * 
	 * @param int
	 *            specifying number of MAS instances of the OPM Thing
	 */
	public void setNumberOfMASInstances(int numOfInstances) {
		((OpmThing) this.logicalEntity).setNumberOfInstances(numOfInstances);
	}

	public String getRole() {
		return ((OpmThing) this.logicalEntity).getRole();
	}

	public String getFreeTextRole() {
		return ((OpmThing) this.logicalEntity).getFreeTextRole();
	}

	public void setRole(String role) {
		((OpmThing) this.logicalEntity).setRole(role);
	}

	/**
	 * Returns the roles of the thing - which reflect its notation in an
	 * imported ontology.
	 * 
	 * @return Enumeration Contains the roles as Role objects.
	 * @author Eran Toch
	 */
	public Enumeration getRoles() {
		Enumeration roles;
		try {
			roles = ((OpmThing) this.logicalEntity).getRolesManager()
					.getRoles();
		} catch (Exception E) {
			roles = new Vector().elements();
		}
		return roles;
	}

	public Enumeration getRoles(int type) {
		Enumeration roles;
		try {
			roles = ((OpmThing) this.logicalEntity).getRolesManager()
					.getRolesVector(type).elements();
		} catch (Exception E) {
			roles = new Vector().elements();
		}
		return roles;
	}

	public String getRolesString(int type) {
		String ret = null;
		Iterator iter = Collections.list(this.getRoles(type)).iterator();
		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			ret = ret + role.getThingName();
			ret = ret + ",";
		}
		if (ret != null) {
			return ret.substring(0, ret.length() - 1);
		} else {
			return ret;
		}

	}

	/**
	 * Returns the {@link RoleEntry} objects of the thing - which reflect its
	 * notation in an imported ontology.
	 * 
	 * @return Enumeration Contains the roles as <code>RoleEntry</code>
	 *         objects.
	 * @author Eran Toch
	 */
	public Enumeration getRolesEntries() {
		Enumeration roles;
		try {
			roles = ((OpmThing) this.logicalEntity).getRolesManager()
					.getRoleEntries();
		} catch (Exception E) {
			roles = new Vector().elements();
		}
		return roles;
	}

	public void addRole(IXRole role) {
		((OpmThing) this.logicalEntity).getRolesManager().addRole(role);
	}

	public void removeRole(IXRole role) {
		((OpmThing) this.logicalEntity).getRolesManager().removeRole(role);
	}

	public void ShowInstances() {

		OptionsBase searchOptions = new OptionsExectMatch();

		if (this instanceof ProcessEntry) {
			searchOptions.setForProcess(true);
			searchOptions.setForObjects(false);
		} else {
			searchOptions.setForProcess(false);
			searchOptions.setForObjects(true);
		}
		searchOptions.setForStates(false);
		searchOptions.setInDescription(false);
		searchOptions.setInName(true);
		searchOptions.setSearchText(this.getLogicalEntity().getName());
		SearchAction search = new SearchAction(this.myProject, searchOptions,
				"Instances");
		search.actionPerformed(null);

	}

	public static void ShowInstances(Vector opmThings) {

		OptionsBase[] searchOptions = new OptionsExectMatch[opmThings.size()];

		FileControl file = FileControl.getInstance();

		for (int i = 0; i < opmThings.size(); i++) {
			OpmThing opmEnt = (OpmThing) opmThings.get(i);
			ThingEntry ent = ThingEntry.getEntryFromOpmThing(opmEnt);

			searchOptions[i] = new OptionsExectMatch();

			if (ent instanceof ProcessEntry) {
				searchOptions[i].setForProcess(true);
				searchOptions[i].setForObjects(false);
			} else {
				searchOptions[i].setForProcess(false);
				searchOptions[i].setForObjects(true);
			}
			searchOptions[i].setForStates(false);
			searchOptions[i].setInDescription(false);
			searchOptions[i].setInName(true);
			searchOptions[i].setSearchText(ent.getLogicalEntity().getName());
		}

		SearchAction search = new SearchAction(file.getCurrentProject(),
				searchOptions, "Instances");
		search.actionPerformed(null);

	}

	public static ThingEntry getEntryFromOpmThing(OpmThing myOpmThing) {
		long myID = myOpmThing.getId();
		Enumeration systemEnum = Opcat2.getCurrentProject()
				.getIXSystemStructure().getElements();
		while (systemEnum.hasMoreElements()) {
			Object ins = systemEnum.nextElement();
			if (ins instanceof ThingEntry) {
				ThingEntry thingIns = (ThingEntry) ins;
				if (thingIns.getLogicalEntity().getId() == myID) {
					return thingIns;
				}
			}
		}
		return null;
	}
	
	public HashMap getConnectedThings() {

		HashMap entries = new HashMap();
		HashMap ret = new HashMap();

		ThingEntry thingEnt = this;
		entries.put(new Long(thingEnt.getId()), thingEnt);
		if (thingEnt instanceof ObjectEntry) {
			ObjectEntry obj = (ObjectEntry) thingEnt;
			Iterator statesIter = Collections.list(obj.getStates()).iterator();
			while (statesIter.hasNext()) {
				StateEntry state = (StateEntry) statesIter.next();
				entries.put(new Long(state.getId()), state);
			}
		}

		if (thingEnt.getZoomedInOpd() != null) {
			Vector includedIns = GetInZoomedIncludedInstances(thingEnt
					.getZoomedInOpd());
			Iterator iter = includedIns.iterator();
			while (iter.hasNext()) {
				Instance ins = (Instance) iter.next();
				if (!entries.containsKey(new Long(ins.getEntry().getId()))) {
					entries.put(new Long(ins.getEntry().getId()), ins
							.getEntry());
					if (ins instanceof ObjectInstance) {
						ObjectEntry obj = (ObjectEntry) ins.getEntry();
						Iterator statesIter = Collections.list(obj.getStates())
								.iterator();
						while (statesIter.hasNext()) {
							StateEntry state = (StateEntry) statesIter.next();
							entries.put(new Long(state.getId()), state);
						}
					}
				}
			}
		}

		Iterator entriesIter = entries.values().iterator();
		while (entriesIter.hasNext()) {

			Entry entry = (Entry) entriesIter.next();
			Iterator destIter = null;
			Iterator srcIter = null;

			if (entry instanceof StateEntry) {
				destIter = Collections.list(
						((StateEntry) entry).getDestinationLinks()).iterator();
				srcIter = Collections.list(
						((StateEntry) entry).getSourceLinks()).iterator();

			} else {
				destIter = Collections.list(
						((ThingEntry) entry).getDestinationLinks()).iterator();
				srcIter = Collections.list(
						((ThingEntry) entry).getSourceLinks()).iterator();
			}

			while (destIter.hasNext()) {
				Object obj = destIter.next();
				if (obj instanceof OpmProceduralLink) {
					OpmProceduralLink link = (OpmProceduralLink) obj;
					Entry ent = (Entry) Opcat2.getCurrentProject()
							.getSystemStructure().getEntry(link.getSourceId());
					if (ent instanceof ProcessEntry) {
						ProcessEntry process = (ProcessEntry) ent;
						if (!ret.containsKey(new Long(process.getId()))) {
							Object[]  array = new Object[2] ; 
							array[0] = process; 
							array[1] = link ; 
							ret.put(new Long(process.getId()), array);
						}
					}
					
					if (ent instanceof ObjectEntry) {
						ObjectEntry object = (ObjectEntry) ent;
						if (!ret.containsKey(new Long(object.getId()))) {
							Object[]  array = new Object[2] ; 
							array[0] = object; 
							array[1] = link ; 						
							ret.put(new Long(object.getId()), array);
						}
					}					
				}
			}

			while (srcIter.hasNext()) {
				Object obj = srcIter.next();
				if (obj instanceof OpmProceduralLink) {
					OpmProceduralLink link = (OpmProceduralLink) obj;
					Entry ent = (Entry) Opcat2.getCurrentProject()
							.getSystemStructure().getEntry(
									link.getDestinationId());
					if (ent instanceof ProcessEntry) {
						ProcessEntry process = (ProcessEntry) ent;
						if (!ret.containsKey(new Long(process.getId()))) {
							Object[]  array = new Object[2] ; 
							array[0] = process; 
							array[1] = link ;							
							ret.put(new Long(process.getId()), array);
						}
					}
					
					if (ent instanceof ObjectEntry) {
						ObjectEntry object = (ObjectEntry) ent;
						if (!ret.containsKey(new Long(object.getId()))) {
							Object[]  array = new Object[2] ; 
							array[0] = object; 
							array[1] = link ;						
							ret.put(new Long(object.getId()), array);
						}
					}					
				}
			}
		}

		return ret;
	}	
}