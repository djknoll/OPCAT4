package gui.metaLibraries.logic;

import exportedAPI.opcatAPI.ISystem;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXObjectInstance;
import gui.Opcat2;
import gui.metaDataProject.MetaDataAbstractItem;
import gui.metaDataProject.MetaDataProject;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmState;
import gui.projectStructure.ConnectionEdgeEntry;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatLogger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * The class represents a a knowledge about a domain - captured as an OPM model.
 * Meta-libraries can be used to define the structure of OPM models by importing
 * the meta-libraries into the OPM model and assigning things from the
 * meta-libraries as roles of things from the OPM models. Roles are represtned
 * using the {@link Role} class. The OPM model can be validated against the
 * meta-library, insuring that the structure of the meta-library is reflected as
 * the relations between roles in the model.
 * <p>
 * The meta-library class is used to reference and contain an OPM model project.
 * It allows access to the elements of the model, and exposes methods that
 * retrieve the possible roles of the ontology.
 * <p>
 * Each meta-libraryis is identified by a reference ID (<code>mlibID</code>)
 * which identifies the ontology in the context of the current project.
 * 
 * @see Role
 * @creator Eran Toch
 */
public class MetaLibrary implements Comparable {

    private boolean policyLibrary;

    /**
         * A constant indicating a reference to a local file.
         */
    public static final int TYPE_FILE = 0;

    /**
         * A constant indicating a reference to a URL.
         */
    public static final int TYPE_URL = 1;

    // ************** Possible states for the library *************
    /**
         * State initialized is a default state, which does not carry a special
         * operation.
         */
    public static final int STATE_INIT = 0;

    /**
         * The meta-library was not loaded and serves as a reference. A path and
         * type variables should have been set. A call to
         * <code>MetaManager.refresh()</code> should load all the libraries
         * with this state. Note that the meta-library is in a transient state,
         * and cannot serve for roles.
         */
    public static final int STATE_REFERENCE = 1;

    /**
         * The meta-library was loaded successfully, and can serve as a basis
         * for roles.
         */
    public static final int STATE_LOADED = 2;

    /**
         * The meta-library had failed to load, and cannot serve as a basis for
         * roles.
         */
    public static final int STATE_LOAD_FAILED = 3;

    /**
         * The meta-library should be removed by the next
         * <code>MetaManager.refresh()</code>. Note that the meta-library is
         * in a transient state, and cannot serve for roles.
         */
    public static final int STATE_REMOVED = 4;

    // /**
    // * The meta-library was edited and should be removed (after the
    // assigned roles removed)
    // * by the next <code>MetaManager.refresh()</code> call.
    // * Note that the meta-library is in a transient state, and cannot
    // serve for roles.
    // */
    // public static final int STATE_EDITED = 5;

    /**
         * A reference to the project that contains the meta-library's OPM
         * model.
         */
    private DataHolder holder = null;

    /**
         * An ID that identifies the library within the context of the project
         * that imports the library. Note that the same OPM model that serves as
         * a meta-library might have different <code>mlibID</code> when
         * imported by different OPM models and in other runs of the same model.
         */
    private long mlibID = 0;

    /**
         * Stores the state of the library (<code>STATE_REFERENCE</code>,
         * <code>STATE_LOADED</code> etc.
         */
    private int state = STATE_INIT;

    /**
         * The string that represent the path to the library.
         */
    private String path = "";

    /**
         * The path type (File or URL).
         */
    private int referenceType;

    /**
         * A global identifier for the library. The ID is issued uniquely by
         * Opcat for each project.
         */
    private String globalID = "";

    /**
         * The ontology data type
         */
    private int mlDataType = 0;

    /**
         * The ontology recieves a projet holder and an internal ontology ID as
         * input.
         * 
         */
    public MetaLibrary(long _referenceID, String _path, int _refType,
	    String _globalID) {
	this(_referenceID, _path, _refType);
	this.globalID = _globalID;
    }

    /**
         * The ontology recieves a projet holder and an internal ontology ID as
         * input.
         * 
         */
    public MetaLibrary(long _referenceID, String _path, int _refType) {
	this.mlibID = _referenceID;
	this.path = _path;
	this.referenceType = _refType;
	this.mlDataType = this.CheckDataTypeByFileType(_path);
	this.setState(STATE_REFERENCE);
    }

    public MetaLibrary(MetaDataProject metadata) {
	this.mlibID = metadata.getName().hashCode();
	this.path = metadata.getPath();
	this.referenceType = MetaLibrary.TYPE_FILE;
	setType(LibraryTypes.MetaLibrary);
	this.mlDataType = LibraryTypes.MetaData;
	this.globalID = metadata.getGlobalID();
	this.setModelProject(metadata);
	this.setState(STATE_LOADED);
    }

    private int CheckDataTypeByFileType(String path) {

	if (path.endsWith(".opz") || path.endsWith(".opx")) {
	    return LibraryTypes.MetaLibrary;
	}
	if (path.endsWith(".csv") || path.endsWith(".txt")
		|| path.endsWith("req")) {
	    return LibraryTypes.MetaData;
	}
	return LibraryTypes.MetaLibrary;
    }

    // Commented by Eran 1/5/04
    // /**
    // * The ontology recieves a projet holder and an internal ontology ID
    // as input.
    // *
    // */
    // public MetaLibrary(long _referenceID, String _path, int _refType,
    // OpdProject projectHolder) {
    // this(_referenceID, _path, _refType);
    // setModelProject(projectHolder);
    // }

    /**
         * Sets the project (the referenced OPM model) for this meta-library.
         * 
         * @param projectHolder
         *                An opened OpdProject object.
         */
    public void setModelProject(Object project) throws NullPointerException {

	if (project instanceof OpdProject) {
	    this.holder = new DataHolder(project, LibraryTypes.MetaLibrary);
	}

	if (project instanceof MetaDataProject) {
	    this.holder = new DataHolder(project, LibraryTypes.MetaData);
	}

	if (this.holder.getData() == null) {
	    throw new NullPointerException(
		    "Project was passed null to the MetaLibrary");
	}
	this.globalID = this.holder.getGlobalID();
	this.setState(STATE_LOADED);
    }

    /**
         * Sets a new state for this library.
         * 
         * @param newState
         *                The new state. Must be one of the constant states
         *                listed in the class reference.
         */
    protected void setState(int newState) {
	this.state = newState;
    }

    /**
         * Retrieves all the Objects of the ontology as Role elements.
         * 
         * @return An Enumeration object containing Role objects.
         */
    public Enumeration getAllObjectRoles() {
	return null;
    }

    /**
         * Retrieves all the Process of the ontology as Role elements.
         * 
         * @return An Enumeration object containing Role objects.
         */
    public Enumeration getAllProcessRoles() {
	return null;
    }

    /**
         * Returns the ISystemStructure interface of the project holder.
         */
    public ISystemStructure getStructure() {
	return this.holder.getISystemStructure();
    }

    /**
         * Returns a collection of the roles.
         */
    public Collection<Role> getRolesCollection() throws MetaException {
	if (this.holder == null) {
	    throw new MetaException("Library is not loaded", this.getPath());
	}

	Collection<Role> things = new ArrayList<Role>();
	// Enumeration locenum =
	// Collections.enumeration(this.holder.getDataComponents()) ;

	// /**
	// * TODO fix this to not count on type
	// */
	// if(this.getType() == LibraryTypes.MetaLibrary) {
	// Entry entry;
	// OpmEntity thing;
	// Role role;
	// while (locenum.hasMoreElements()) {
	// entry = (Entry)locenum.nextElement();
	// OpmEntity entity = entry.getLogicalEntity();
	// //if (entity instanceof OpmThing) {
	// thing = (OpmEntity)entity;
	// role = new Role(thing, this);
	// role.getThing().setAdditionalData(entry) ;
	// things.add(role);
	// //}
	// }
	// }

	// if(this.getType() == LibraryTypes.MetaData) {
	Iterator iter = this.holder.getDataComponents().iterator();
	while (iter.hasNext()) {
	    MetaDataAbstractItem req = (MetaDataAbstractItem) iter.next();
	    things.add(new Role(req, this));
	}
	// }

	return things;
    }

    /**
         * Checks if another ontology is the same one as this one. The
         * comparison is carried out according to the name of the ontology. This
         * method is not correct, of course. Any ideas?
         * 
         * @param otherOnto
         *                the MetaLibrary to be compared.
         * @return <code>true</code> if they are equals, <code>false</code>
         *         otherwise.
         */
    public boolean equals(MetaLibrary otherOnto) {
	if (otherOnto.getName() == this.getName()) {
	    return true;
	}
	return false;
    }

    /**
         * Returns the name of the ontology - the name of the project holder.
         */
    public String getName() {
	if (this.holder == null) {
	    return "null-ontology";
	}
	return this.holder.getName();
    }

    /**
         * Returns the project holder (the OPM model of the ontology) as an
         * <code>OpdProject</code>.
         */
    public Object getProjectHolder() {
	if (this.holder == null) {
	    return null;
	}
	return this.holder.getData();
    }

    /**
         * Returns the project holder (the OPM model of the ontology) as an
         * <code>ISystem</code>.
         */
    public ISystem getISystem() {
	return this.holder.getISystem();
    }

    /**
         * Returns the ID of the ontology, in the context of the current
         * project.
         * 
         * @return The ID of the ontology.
         */
    public long getReferenceID() {
	return this.mlibID;
    }

    /**
         * Returns the project global ID. //TODO: add global ID to the exported
         * API
         */
    public String getGlobalID() {
	return this.globalID;
    }

    /**
         * Sets the ontology ID.
         * 
         * @param _referenceID
         */
    public void setReferenceID(long _referenceID) {
	this.mlibID = _referenceID;
    }

    /**
         * Gets the reference type.
         * 
         * @return int The type of the reference (TYPE_FILE or TYPE_URL).
         * @see #TYPE_FILE
         * @see #TYPE_URL
         */
    public int getReferenceType() {
	return this.referenceType;
    }

    /**
         * Sets the path.
         * 
         * @param path
         *                String
         */
    public void setPath(String path) {
	this.path = path;
    }

    /**
         * Sets the reference type.
         * 
         * @param referenceType
         *                int
         */
    public void setReferenceType(int referenceType) {
	this.referenceType = referenceType;
    }

    /**
         * Gets the path of the meta-library reference.
         * 
         * @return String
         */
    public String getPath() {
	return this.path;
    }

    /**
         * Returns the state of the Meta-Library.
         * 
         * @return One of the states of Meta-libraries.
         */
    public int getState() {
	return this.state;
    }

    /**
         * Finalizing a <code>MetaLibrary</code>, nulls the attached
         * <code>OpdProject</code>.
         */
    public void finalize() {
	this.holder = null;
    }

    /**
         * Checks whether the project that represent the library is
         * <code>null</code>.
         * 
         * @return
         */
    public boolean projectIsNull() {
	if (this.holder == null) {
	    return true;
	}
	return false;
    }

    public int getType() {
	return this.mlDataType;
    }

    public void setType(int dataType) {
	this.mlDataType = dataType;
    }

    public String toString() {
	return this.getName();
    }

    public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return this.toString().compareTo(o.toString());
    }

    public boolean isPolicyLibrary() {
	return policyLibrary;
    }

    public void setPolicyLibrary(boolean policyLibrary) {
	this.policyLibrary = policyLibrary;
    }

    private static ProcessInstance insertProcessInstance(Role insertedRole,
	    boolean keepConnected, Opd targetOpd, int x, int y,
	    ConnectionEdgeInstance parent) {

	ProcessInstance newInstance = null;

	OpdProject metaProject = (OpdProject) insertedRole.getOntology()
		.getProjectHolder();

	ConnectionEdgeEntry roleThing = (ConnectionEdgeEntry) metaProject
		.getSystemStructure().getEntry(insertedRole.getThingId());

	if (parent != null) {
	    newInstance = (ProcessInstance) Opcat2.getCurrentProject()
		    .addProcess(roleThing.getName(), x, y,
			    (ThingInstance) parent);
	} else {
	    newInstance = (ProcessInstance) Opcat2
		    .getCurrentProject()
		    .addProcess(roleThing.getName(), x, y, targetOpd.getOpdId());
	}

	try {
	    newInstance.getEntry().addRole(
		    new Role(roleThing.getId(), insertedRole.getOntology()));
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	((OpmProcess) ((ProcessEntry) ((ProcessInstance) newInstance)
		.getEntry()).getLogicalEntity())
		.copyPropsFrom((OpmProcess) ((ProcessEntry) roleThing)
			.getLogicalEntity());

	return newInstance;
    }

    private static StateInstance insertStateInstance(Role insertedRole,
	    boolean keepConnected, Opd targetOpd, int x, int y,
	    ConnectionEdgeInstance parent) {

	StateInstance newInstance = null;

	OpdProject metaProject = (OpdProject) insertedRole.getOntology()
		.getProjectHolder();

	ConnectionEdgeEntry roleThing = (ConnectionEdgeEntry) metaProject
		.getSystemStructure().getEntry(insertedRole.getThingId());

	IXConnectionEdgeInstance newObjectInstance = null;

	ObjectEntry object = ((StateEntry) roleThing).getParentObject();

	/**
         * see if the object with this role is in the targetOPD
         */
	ArrayList<Instance> instancesofRoleInOPD = new ArrayList<Instance>();
	try {
	    instancesofRoleInOPD = targetOpd.getInstancesInOPDOfRole(new Role(
		    object.getId(), insertedRole.getOntology()));
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	boolean insertToExsitingObject = false;
	if (instancesofRoleInOPD.size() > 0) {
	    newObjectInstance = (IXObjectInstance) instancesofRoleInOPD.get(0);
	    insertToExsitingObject = true;

	} else {
	    if (parent != null) {
		newObjectInstance = Opcat2.getCurrentProject().addObject(x, y,
			parent.getGraphicalRepresentation(), -1, -1, true);
	    } else {
		newObjectInstance = Opcat2.getCurrentProject().addObject(x, y,
			targetOpd.getDrawingArea(), -1, -1, true);
	    }
	}

	/**
         * 
         */

	((OpmObject) ((ObjectInstance) newObjectInstance).getEntry()
		.getLogicalEntity()).copyPropsFrom((OpmObject) object
		.getLogicalEntity());
	Iterator iter = Collections.list(object.getStateEnum()).iterator();

	while (iter.hasNext()) {
	    OpmState state = (OpmState) iter.next();

	    boolean insert = false;
	    if (insertToExsitingObject) {
		insert = (state.getName() == ((StateEntry) roleThing).getName());
	    } else {
		insert = true;
	    }
	    StateEntry stateEntry = null;

	    if (insert) {
		stateEntry = (StateEntry) Opcat2.getCurrentProject().addState(
			state.getName(), ((ObjectInstance) newObjectInstance));
	    }

	    try {
		if (insert) {
		    stateEntry.addRole(new Role(state.getId(), insertedRole
			    .getOntology()));
		}
	    } catch (Exception ex) {
		OpcatLogger.logError(ex);
	    }
	}

	ObjectEntry locObject = (ObjectEntry) newObjectInstance.getIXEntry();
	try {
	    locObject.addRole(new Role(object.getId(), insertedRole
		    .getOntology()));
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	StateEntry mystate = locObject.getState(roleThing.getName());

	newInstance = (StateInstance) mystate.getInstanceInOPD(targetOpd);
	
	((ObjectInstance) newObjectInstance).getConnectionEdge().fitToContent() ; 

	return newInstance;
    }

    private static ObjectInstance insertObjectInstance(Role insertedRole,
	    boolean keepConnected, Opd targetOpd, int x, int y,
	    ConnectionEdgeInstance parent) {

	ObjectInstance newInstance = null;

	OpdProject metaProject = (OpdProject) insertedRole.getOntology()
		.getProjectHolder();

	ConnectionEdgeEntry roleThing = (ConnectionEdgeEntry) metaProject
		.getSystemStructure().getEntry(insertedRole.getThingId());

	/**
         * ugly but does the job
         */
	ObjectEntry object = (ObjectEntry) roleThing;
	/**
         * 
         */

	if (parent != null) {
	    newInstance = Opcat2.getCurrentProject().addObject(x, y,
		    parent.getGraphicalRepresentation(), -1, -1, true);
	} else {
	    newInstance = Opcat2.getCurrentProject().addObject(x, y,
		    targetOpd.getDrawingArea(), -1, -1, true);
	}

	((OpmObject) ((ObjectInstance) newInstance).getEntry()
		.getLogicalEntity()).copyPropsFrom((OpmObject) object
		.getLogicalEntity());
	Iterator iter = Collections.list(object.getStateEnum()).iterator();

	while (iter.hasNext()) {
	    OpmState state = (OpmState) iter.next();
	    StateEntry stateEntry = (StateEntry) Opcat2.getCurrentProject()
		    .addState(state.getName(), ((ObjectInstance) newInstance));

	    if (keepConnected) {
		try {
		    stateEntry.addRole(new Role(state.getId(), insertedRole
			    .getOntology()));
		} catch (Exception ex) {
		    OpcatLogger.logError(ex);
		}
	    }
	}

	return newInstance;
    }

    /**
         * Create a new ConnectionEgdeInstance in targetOpd from the Role. (i.e.
         * insert from metalib action) if keepConected is true the new object
         * will be assigned the insertedRole.
         * 
         * @param insertedRole
         * @param targetOpd
         * @param keepConnected
         * @param relativeInstance
         *                if adding a process the this is used also as the
         *                parent of the process the place of the new instance is
         *                calculated relative to this instance if null the 0, 0
         *                is taken
         * @return
         */
    public static ConnectionEdgeInstance insertConnectionEdge(
	    Role insertedRole, Opd targetOpd, boolean keepConnected,
	    ConnectionEdgeInstance relativeInstance,
	    ConnectionEdgeInstance parent) {

	int x = 0;
	int y = 0;

	if (relativeInstance == null) {
	    x = 100;
	    y = 100;
	} else {
	    x = relativeInstance.getX() + relativeInstance.getWidth() + 30;
	    y = relativeInstance.getY() + relativeInstance.getHeight() + 30;
	}
	ConnectionEdgeInstance newInstance = null;

	OpdProject metaProject = (OpdProject) insertedRole.getOntology()
		.getProjectHolder();

	ConnectionEdgeEntry roleThing = (ConnectionEdgeEntry) metaProject
		.getSystemStructure().getEntry(insertedRole.getThingId());

	if (roleThing instanceof ObjectEntry) {

	    newInstance = insertObjectInstance(insertedRole, keepConnected,
		    targetOpd, x, y, parent);

	} else if (roleThing instanceof StateEntry) {

	    newInstance = insertStateInstance(insertedRole, keepConnected,
		    targetOpd, x, y, parent);

	} else {

	    newInstance = insertProcessInstance(insertedRole, keepConnected,
		    targetOpd, x, y, parent);
	}

	try {
	    newInstance.getEntry().addRole(
		    new Role(roleThing.getId(), insertedRole.getOntology()));
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

	return newInstance;
    }
}