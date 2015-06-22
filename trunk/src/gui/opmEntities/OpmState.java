package gui.opmEntities;

/**
 * This class represents State in OPM. For better understanding of this class
 * you should be familiar with OPM.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class OpmState extends OpmConnectionEdge {
    // ---------------------------------------------------------------------------
    // The private attributes/members are located here
    private String minTime, maxTime;

    boolean initialState, finalState, defaultState;

    public final static String EXISTENT_STATE_NAME = "Exsistent";

    public final static String NONEXISTENT_STATE_NAME = "Non-Exsistent";

    public static final String DEFAULT_MAX_TIME = "infinity";
    public static final String DEFAULT_MIN_TIME = new String("0;0;0;0;0;0;0");

    public static final boolean DEFAULT_INITIAL_STATE = false;
    public static final boolean DEFAULT_FINAL_STATE = false;
    public static final boolean DEFAULT_DEFAULT_STATE = false;

    /**
     * Creates an OpmState with specified id and name. Id of created
     * OpmState must be unique in OPCAT system. Other data members of
     * OpmState get default values.
     *
     * @param stateId   OpmState id
     * @param stateName OpmState name
     */

    public OpmState(long stateId, String stateName) {
        super(stateId, stateName);
        this.minTime = DEFAULT_MIN_TIME;
        this.maxTime = DEFAULT_MAX_TIME;
        //this.setDescription("None");
        this.initialState = DEFAULT_INITIAL_STATE;
        this.finalState = DEFAULT_FINAL_STATE;
        this.defaultState = DEFAULT_DEFAULT_STATE;
    }

    public void copyPropsFrom(OpmEntity origin) {
        super.copyPropsFrom(origin);
        this.minTime = ((OpmState) origin).getMinTime();
        this.maxTime = ((OpmState) origin).getMaxTime();
        this.initialState = ((OpmState) origin).isInitial();
        this.finalState = ((OpmState) origin).isFinal();
        this.defaultState = ((OpmState) origin).isDefault();
    }

    // ---------------------------------------------------------------------------
    // The public methods are located here

    /**
     * Returns NAME representing minimum activation time of this OpmState.
     * This NAME contains non-negative integer X 7 (msec, sec, min, hours,
     * days, months, years) with semi-colons separation.
     */
    public String getMinTime() {
        return this.minTime;
    }

    /**
     * Sets minimum activation time of this OpmState. This field contains
     * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
     * with semi-colons separation.
     */
    public void setMinTime(String minTime) {
        this.minTime = minTime;
    }

    /**
     * Returns NAME representing maximum activation time of this OpmState.
     * This NAME contains non-negative integer X 7 (msec, sec, min, hours,
     * days, months, years) with semi-colons separation.
     */
    public String getMaxTime() {
        return this.maxTime;
    }

    /**
     * Sets maximum activation time of this OpmState. This field contains
     * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
     * with semi-colons separation.
     */
    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    /**
     * Sets the initialState property of OpmState.
     */
    public void setInitial(boolean initialState) {
        this.initialState = initialState;
    }

    /**
     * Returns true if this State is initial.
     */
    public boolean isInitial() {
        return this.initialState;
    }

    /**
     * Sets the finalState property of OpmState.
     */
    public void setFinal(boolean finalState) {
        this.finalState = finalState;
    }

    /**
     * Returns true if this State is final.
     */
    public boolean isFinal() {
        return this.finalState;
    }

    public boolean isDefault() {
        return this.defaultState;
    }

    public void setDefault(boolean defaultState) {
        this.defaultState = defaultState;
    }

}