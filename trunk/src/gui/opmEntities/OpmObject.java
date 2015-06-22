package gui.opmEntities;

/**
 * This class represents Object in OPM. For better understanding of this class
 * you should be familiar with OPM.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class OpmObject extends OpmThing {
    // ---------------------------------------------------------------------------
    // The private attributes/members are located here

    public static final boolean DEFAULT_PERSISTENT = true;
    public static final String DEFAULT_TYPE = "";
    public static final boolean DEFAULT_IS_KEY = false;
    public static final String DEFAULT_INDEX_NAME = "";
    public static final int DEFAULT_INDEX_ORDER = 0;
    public static final String DEFAULT_INITIAL_VALUE = "";
    public static final int DEFAULT_TYPE_ORIGIN_ID = -1;
    public static final String DEFAULT_MESUREMENT_UNIT = " ";
    public static final double DEFAULT_MESUREMENT_UNIT_INITIAL_VALUE = 0.0;
    public static final double DEFAULT_MESUREMENT_UNIT_MIN_VALUE = 0.0;
    public static final double DEFAULT_MESUREMENT_UNIT_MAX_VALUE = 0.0;

    private boolean persistent;

    private String type;

    private long typeOriginId;

    private boolean key;

    private String indexName;

    private int indexOrder;

    private String initialValue;

    private String mesurementUnit;

    private double mesurementUnitInitialValue;

    private double mesurementUnitCurrentValue;


    private double mesurementUnitMinValue;

    private double mesurementUnitMaxValue;

    /**
     * Creates an OpmObject with specified id and name. Id of created OpmObject
     * must be unique in OPCAT system. Other data members of OpmObject get
     * default values.
     *
     * @param objectId   OpmObject id
     * @param objectName OpmObject name
     */

    public OpmObject(long objectId, String objectName) {
        super(objectId, objectName);

        this.persistent = DEFAULT_PERSISTENT;
        this.type = DEFAULT_TYPE;
        this.key = DEFAULT_IS_KEY;
        this.indexName = DEFAULT_INDEX_NAME;
        this.indexOrder = DEFAULT_INDEX_ORDER;
        this.initialValue = DEFAULT_INITIAL_VALUE;
        this.typeOriginId = DEFAULT_TYPE_ORIGIN_ID;
        this.mesurementUnit = DEFAULT_MESUREMENT_UNIT;
        this.mesurementUnitInitialValue = DEFAULT_MESUREMENT_UNIT_INITIAL_VALUE;
        mesurementUnitCurrentValue = DEFAULT_MESUREMENT_UNIT_INITIAL_VALUE;
        this.mesurementUnitMinValue = DEFAULT_MESUREMENT_UNIT_MIN_VALUE;
        this.mesurementUnitMaxValue = DEFAULT_MESUREMENT_UNIT_MAX_VALUE;
    }

    public double getMesurementUnitCurrentValue() {
        return mesurementUnitCurrentValue;
    }

    public void setMesurementUnitCurrentValue(double mesurementUnitCurrentValue) {
        this.mesurementUnitCurrentValue = mesurementUnitCurrentValue;
    }

    public void copyPropsFrom(OpmEntity origin) {
        super.copyPropsFrom(origin);
        this.persistent = ((OpmObject) origin).isPersistent();
        this.type = ((OpmObject) origin).getType();
        this.typeOriginId = ((OpmObject) origin).getTypeOriginId();
        this.key = ((OpmObject) origin).isKey();
        this.indexName = ((OpmObject) origin).getIndexName();
        this.indexOrder = ((OpmObject) origin).getIndexOrder();
        this.initialValue = ((OpmObject) origin).getInitialValue();
        this.mesurementUnit = ((OpmObject) origin).getMesurementUnit();
        this.mesurementUnitInitialValue = ((OpmObject) origin)
                .getMesurementUnitInitialValue();
        this.mesurementUnitMaxValue = ((OpmObject) origin).getMesurementUnitMaxValue();
        this.mesurementUnitMinValue = ((OpmObject) origin).getMesurementUnitMinValue();
        // this.setEnviromental(origin.isEnviromental()) ;
        this.setPhysical(origin.isPhysical());

        // Added by Eran Toch
        // rolesManager = origin.getRolesManager();
    }

    public boolean hasSameProps(OpmObject pObject) {
        return (super.hasSameProps(pObject)
                && (this.persistent == pObject.isPersistent())
                && (this.type.equals(pObject.getType()))
                && (this.typeOriginId == pObject.getTypeOriginId())
                && (this.key == pObject.isKey())
                && (this.indexName.equals(pObject.getIndexName()))
                && (this.indexOrder == pObject.getIndexOrder()) && (this.initialValue
                .equals(pObject.getInitialValue())));
    }

    /**
     * Returns true if this OpmObject is persistent.
     */

    public boolean isPersistent() {
        return this.persistent;
    }

    /**
     * Sets the persistent property of this OpmObject.
     */

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * Returns NAME representing type of this OpmObject
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets type of this OpmObject.
     */

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns true if this OpmObject is key.
     */

    public boolean isKey() {
        return this.key;
    }

    /**
     * Sets key property of this OpmObject.
     */

    public void setKey(boolean key) {
        this.key = key;
    }

    /**
     * Returns index name of this OpmObject.
     */
    public String getIndexName() {
        return this.indexName;
    }

    /**
     * Sets index name of if this OpmObject.
     */

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    /**
     * Returns index order of if this OpmObject.
     */

    public int getIndexOrder() {
        return this.indexOrder;
    }

    /**
     * Sets index order of if this OpmObject.
     */
    public void setIndexOrder(int indexOrder) {
        this.indexOrder = indexOrder;
    }

    /**
     * Returns initial value of if this OpmObject.
     */
    public String getInitialValue() {
        return this.initialValue;
    }

    /**
     * Sets initial value of if this OpmObject.
     */

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    public void setTypeOriginId(long typeOriginId) {
        this.typeOriginId = typeOriginId;
    }

    public long getTypeOriginId() {
        return this.typeOriginId;
    }

    public boolean isMesurementUnitExists() {
        if (mesurementUnit.trim().equalsIgnoreCase("")) {
            return false;
        }
        return true;
    }

    public String getMesurementUnit() {
        if (mesurementUnit.trim().equalsIgnoreCase("")) {
            return "";
        }
        return mesurementUnit;
    }

    public void setMesurementUnit(String mesurementUnit) {
        this.mesurementUnit = mesurementUnit;
    }

    public double getMesurementUnitInitialValue() {
        return mesurementUnitInitialValue;
    }

    public void setMesurementUnitInitialValue(double mesurementUnitInitialValue) {
        this.mesurementUnitInitialValue = mesurementUnitInitialValue;
    }

    public double getMesurementUnitMaxValue() {
        if (mesurementUnit.trim().equalsIgnoreCase("")) {
            return 999999999;
        } else {
            return mesurementUnitMaxValue;
        }
    }

    public void setMesurementUnitMaxValue(double mesurementUnitMaxValue) {
        this.mesurementUnitMaxValue = mesurementUnitMaxValue;
    }

    public double getMesurementUnitMinValue() {
        if (mesurementUnit.trim().equalsIgnoreCase("")) {
            return -999999999;
        } else {
            return mesurementUnitMinValue;
        }
    }

    public void setMesurementUnitMinValue(double mesurementUnitMinValue) {
        this.mesurementUnitMinValue = mesurementUnitMinValue;
    }

}