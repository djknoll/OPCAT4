package gui.opmEntities;

import gui.dataProject.DataAbstractItem;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.RolesManager;
import gui.projectStructure.OpcatProperties;

import java.awt.*;
import java.util.Vector;

//-----------------------------------------------------------------

/**
 * The base class for all OPM logical entities. This class represents
 * logicaly(not graphically) all entities which exist in OPM methodology, and
 * its (and its subclasses) purpose is only to store all general information
 * about this entity. For better understanding of this class you should be
 * familiar with OPM.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */
// -----------------------------------------------------------------
public abstract class OpmEntity extends DataAbstractItem {

    /**
     * A roles manager that manage {@link Role} objects for the current
     * <code>OpmThing</code>. The manager is set to <code>null</code> and is
     * initialized only if called by the <code>getRolesManager</code> method.
     */
    protected RolesManager rolesManager = null;

    /**
     * the freetext role
     */
    protected String role;

    private OpcatProperties myProps;

    private boolean exposed = false;

    private boolean privateExposed = false;

    private boolean exposedChanged = false;

    public boolean isExposed() {
        return exposed || privateExposed;
    }

    public boolean isPublicExposed() {
        return exposed;
    }

    public boolean isExposedChanged() {
        return exposedChanged;
    }

    public void setExposedChanged(boolean changed) {
        this.exposedChanged = changed;
    }

    /**
     * Makes this {@link OpmEntity} exposed, thus making it visible to other
     * projects for reuse. the exposed entity could be used only after
     * committing the project to the server.
     *
     * @param exposed <code>true</code> if entity is exposed
     */
    public void setPublicExposed(boolean exposed) {
        this.exposed = exposed;
    }

    // ---------------------------------------------------------------------------
    // The private attributes/members are located here
    private final long entityId;

    /**
     * Creates an OpmEntity with specified id and name. Id of created OpmEntity
     * must be unique in OPCAT system
     *
     * @param id   entity id
     * @param name entity name
     */

    public OpmEntity(long id, String name) {
        super(name, id);
        this.entityId = id;
        this.setAdditionalData(new Long(id));
        myProps = new OpcatProperties();
        getMyProps().put(OpcatProperties.opmEnvKey, "false");
        getMyProps().put(OpcatProperties.opmPhyKey, "false");
        getMyProps().put(OpcatProperties.opmNameKey, name);

    }

    protected void copyPropsFrom(OpmEntity origin) {
        getMyProps().put(OpcatProperties.opmNameKey, origin.getName());
        getMyProps().copyFrom(origin.getMyProps());
    }

    protected boolean hasSameProps(OpmEntity pEntity) {
        return (getName().equals(pEntity.getName())
                && (isEnviromental() == pEntity
                .isEnviromental()));
    }

    // ---------------------------------------------------------------------------
    // The public methods are located here

    // --------------------------------------------------------------------------

    /**
     * Returns the id of the entity.
     *
     * @return a long number represents id of the entity.
     */

    public long getId() {
        return this.entityId;
    }

    /***********************************************************************
     * No setter function for entityId - you set the ID in the constructor * and
     * you can't change it *
     **********************************************************************/

    // --------------------------------------------------------------------------

    /**
     * Returns the name of the entity.
     *
     * @return a NAME represnts entity name
     */

    public String getName() {
        return myProps.getProperty(OpcatProperties.opmNameKey);
    }

    // --------------------------------------------------------------------------

    /**
     * Sets the string to be entity name.
     *
     * @param name entity name
     */

    public void setName(String name) {
        getMyProps().put(OpcatProperties.opmNameKey, name);
    }

    // --------------------------------------------------------------------------

    /**
     * Sets the enviromental/system property of OpmEntity. If value of
     * enviromental is true it's a enviromental thing, otherwise system
     *
     * @param enviromental <code > true</code> if entity is enviromental
     */

    public void setEnviromental(boolean enviromental) {
        getMyProps().put(OpcatProperties.opmEnvKey, String.valueOf(enviromental));
    }

    /**
     * Returns true if this OpmEntity is enviromental. If it's system returns
     * false
     *
     * @return true if entity is enviromental
     */

    public boolean isEnviromental() {
        return Boolean.valueOf(getMyProps().getProperty(OpcatProperties.opmEnvKey));
    }

    // --------------------------------------------------------------------------

    /**
     * Returns a string representation of the entity.
     *
     * @return a string representation of the entity
     */

    public String toString() {
        return myProps.getProperty(OpcatProperties.opmNameKey).replace('\n', ' ');
    }

    public boolean equals(Object obj) {

        OpmEntity tempEntity;
        if (!(obj instanceof OpmEntity)) {
            return false;
        }

        tempEntity = (OpmEntity) obj;

        return tempEntity.getId() == this.getId();
    }


    /**
     * Returns the {@link RolesManager}, which manages roles for this
     * <code>OpmThing</code>. The method creates a new <code>RolesManager</code>
     * if it was <code>null</code>. Each <code>Thing</code> has one
     * <code>RolesManager</code>.
     *
     * @return The <code>RolesManager</code> of this Thing.
     */
    public RolesManager getRolesManager() {
        if (this.rolesManager == null) {
            this.rolesManager = new RolesManager(this);
        }
        return this.rolesManager;
    }

// --Commented out by Inspection START (4/22/09 11:53 AM):
//	/**
//	 * Returns a a <code>Vector</code> object containing pairs of Thing name /
//	 * Library Name.
//	 *
//	 * @return A <code>Vector</code> object holding <code>Property</code>
//	 *         object, in which the thing name has the key of
//	 *         <code>RolesManager.THING_NAME</code> and the meta-library name is
//	 *         keyed under <code>RolesManager.LIBRARY_NAME</code>.
//	 *         <p>
//	 *         The following code demonstrates how to print the role reference:
//	 *         <p>
//	 *         <code>
//	 * Vector allRoles = theThing.getRolesRepresentation();<br>
//	 * if (allRoles.size() > 0)	{<br>
//	 * 		Properties aRole = (Properties)allRoles.get(0);<br>
//	 * 		NAME roleThingName = aRole.getProperty(RolesManager.THING_NAME);<br>
//	 * 		NAME roleLibraryName = aRole.getProperty(RolesManager.LIBRARY_NAME);<br>
//	 * 		System.out.println(roleThingName + ":" + roleLibraryName);<br>
//	 * }<br>
//     * </code>
//	 *
//	 */
//	public Vector getRolesRepresentation() {
//		return  this.getRolesManager().getOPLRepresentation();
//	}
// --Commented out by Inspection STOP (4/22/09 11:53 AM)

    /**
     * Returns the full graphical representation of the roles, including free
     * text <code>NAME</code> roles and meta-libraries based roles.
     *
     * @return manual entered role <code>NAME</code>
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
                        MetaLibrary.TYPE_POLICY);
            }
        }
        return output;
    }

    /**
     * Returns the full graphical representation of the roles which library is
     * of a given type (policy, not policy) , including free text
     * <code>NAME</code> roles and meta-libraries based roles.
     *
     * @return String representing the roles
     */
    public String getPoliciesRole() {
        String output = "";
        if ((this.role != null) && !this.role.equals("")) {
            output += this.role;
        }
        if (this.rolesManager != null) {
            if (this.getRolesManager().hasRoles()) {
                if (!output.equals("")) {
                    output += ", ";
                }
                output += this.getRolesManager().getPoliciesRolesText();
            }
        }
        return output;
    }

    /**
     * Returns the text of the free text <code>NAME</code> role (without
     * meta-libraries roles).
     *
     * @return the NAME representation of the manual Role text
     */
    public String getFreeTextRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * return the type of an entity as a NAME. Object Process State etc...
     *
     * @return get OpmEntity type as NAME
     */
    public String getTypeString() {

        if (this instanceof OpmObject) {
            return "Object";
        } else if (this instanceof OpmProcess) {
            return "Process";
        } else if (this instanceof OpmState) {
            return "State";
        } else if (this instanceof OpmAgent) {
            return "Agent Link";
        } else if (this instanceof OpmConditionLink) {
            return "Condition Link";
        } else if (this instanceof OpmSpecialization) {
            return "Specialization";
        } else if (this instanceof OpmResultLink) {
            return "Result Link";
        } else if (this instanceof OpmUniDirectionalRelation) {
            return "UniDirectional Link";
        } else if (this instanceof OpmAggregation) {
            return "Aggregation Link";
        } else if (this instanceof OpmBiDirectionalRelation) {
            return "BiDirectional Link";
        } else if (this instanceof OpmConsumptionEventLink) {
            return "Consumption Event";
        } else if (this instanceof OpmConsumptionLink) {
            return "Consumption Link";
        } else if (this instanceof OpmEffectLink) {
            return "Effect Link";
        } else if (this instanceof OpmExceptionLink) {
            return "Exception Link";
        } else if (this instanceof OpmExhibition) {
            return "Exibition";
        } else if (this instanceof OpmInstantination) {
            return "Instantination";
        } else if (this instanceof OpmInstrumentEventLink) {
            return "Instrument Event";
        } else if (this instanceof OpmInstrument) {
            return "Instrument Link";
        } else {
            return "Link";
        }

    }

    public Vector getAllData() {
        Vector vec = new Vector();
        vec.add(this.getId());

        vec.add(getTypeString());
        vec.add(this.getName().replaceAll("\n", " "));

        if (this instanceof OpmGeneralRelation) {
            if (this.getName().equalsIgnoreCase("")) {
                vec.add(" ");
            } else {
                String forword = ((OpmGeneralRelation) this)
                        .getForwardRelationMeaning();
                String back = ((OpmGeneralRelation) this)
                        .getBackwardRelationMeaning();
                String str;
                if (forword.equalsIgnoreCase("")) {
                    str = back;
                } else {
                    str = forword;
                    if (!back.equalsIgnoreCase("")) {
                        str = str + "--" + back;
                    }
                }
                vec.add(str);
            }
        } else {
            vec.add(this.getName());
        }
        return vec;
    }

    public Color getColor(MetaLibrary meta) {

        return new Color(getId(), getId(), getId());
        //
        // Iterator roleIter = getRolesManager().getRolesVector(meta.getIDType(),
        // meta.getGlobalID()).iterator();
        //
        // HashMap colored = new HashMap();
        //
        // int level = -1 ;
        // while (roleIter.hasNext()) {
        // Role role = (Role) roleIter.next();
        // Long id = new Long(role.getThingId());
        // if (!colored.containsKey(id)) {
        // colored.put(id, new Long(level));
        // level++;
        // }
        //
        // }
        // return level;
    }

    /**
     * Returns true if this OpmThing is physical. If it's informational returns
     * false
     *
     * @return phisical state of this entity
     */

    public boolean isPhysical() {
        return Boolean.valueOf(getMyProps().getProperty(OpcatProperties.opmPhyKey));
    }

    /**
     * Sets the physical/informational property of OpmThing. If value of
     * physical is true it's a physical thing, otherwise informational
     *
     * @param physical <code>true</code> to set entity as physical
     */
    public void setPhysical(boolean physical) {
        getMyProps().put(OpcatProperties.opmPhyKey, String.valueOf(physical));
    }

    public void setPrivateExposed(boolean privateExposed) {
        this.privateExposed = privateExposed;
    }

    public boolean isPrivateExposed() {
        return privateExposed;
    }

    public OpcatProperties getMyProps() {
        return myProps;
    }
}
