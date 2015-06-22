package gui.opmEntities;

import exportedAPI.OpcatConstants;
import gui.metaLibraries.logic.RolesManager;

/**
 * This class represents Thing in OPM
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public abstract class OpmThing extends OpmConnectionEdge {

    public static final String DEFAULT_ROLE = "";
    public static final boolean DEFAULT_PHYSICAL = false;
    public static final String DEFAULT_SCOPE = OpcatConstants.PUBLIC;
    public static final int DEFAULT_NUMBER_OF_INSTANCES = 1;


    // ---------------------------------------------------------------------------
    // The private attributes/members are located here

    private String scope; // can be 0 - public,1 - protected ,2 - private

    private int numberOfInstances;

    /**
     * Creates an OpmThing with specified id and name. Id of created OpmThing
     * must be unique in OPCAT system
     *
     * @param thingId   OpmThing id
     * @param thingName OpmThing name
     */

    public OpmThing(long thingId, String thingName) {
        super(thingId, thingName);
        this.setPhysical(DEFAULT_PHYSICAL);
        this.scope = DEFAULT_SCOPE;
        this.numberOfInstances = DEFAULT_NUMBER_OF_INSTANCES;
        this.role = DEFAULT_ROLE;
    }

    protected void copyPropsFrom(OpmEntity origin) {
        super.copyPropsFrom(origin);
        this.setPhysical(origin.isPhysical());
        this.scope = ((OpmThing) origin).getScope();
        this.role = origin.getFreeTextRole();
        this.numberOfInstances = ((OpmThing) origin).getNumberOfInstances();

        // Copying the roles - using the roles manager
        // By Eran Toch
        this.rolesManager = (RolesManager) origin.getRolesManager().clone();

    }

    protected boolean hasSameProps(OpmThing pThing) {
        return (super.hasSameProps(pThing)
                && (this.isPhysical() == pThing.isPhysical())
                && this.scope.equals(pThing.getScope())
                && this.role.equals(pThing.getRole())
                && (this.numberOfInstances == pThing.getNumberOfInstances()) && (this
                .getRolesManager().equals(pThing.getRolesManager())));
    }


    /**
     * Sets the scope of OpmThing.
     *
     * @param scope a NAME specifying the scope of the Thing
     */

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Returns the scope of OpmThing.
     *
     * @return a NAME containing thing's scope
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


}