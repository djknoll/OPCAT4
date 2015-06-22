package gui.opmEntities;

/**
 * This class represents Object in OPM. For better understanding of this class
 * you should be familiar with OPM.
 * 
 * @version 2.0
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 */

public class OpmObject extends OpmThing {
	// ---------------------------------------------------------------------------
	// The private attributes/members are located here
	private boolean persistent;

	private String type;

	private long typeOriginId;

	private boolean key;

	private String indexName;

	private int indexOrder;

	private String initialValue;

	/**
	 * Creates an OpmObject with specified id and name. Id of created OpmObject
	 * must be unique in OPCAT system. Other data members of OpmObject get
	 * default values.
	 * 
	 * @param id
	 *            OpmObject id
	 * @param name
	 *            OpmObject name
	 */

	public OpmObject(long objectId, String objectName) {
		super(objectId, objectName);
		this.persistent = true;
		this.type = "";
		this.key = false;
		this.indexName = "";
		this.indexOrder = 0;
		this.initialValue = "";
		this.typeOriginId = -1;
	}

	public void copyPropsFrom(OpmObject origin) {
		super.copyPropsFrom(origin);
		this.persistent = origin.isPersistent();
		this.type = origin.getType();
		this.typeOriginId = origin.getTypeOriginId();
		this.key = origin.isKey();
		this.indexName = origin.getIndexName();
		this.indexOrder = origin.getIndexOrder();
		this.initialValue = origin.getInitialValue();

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
	 * 
	 */

	public boolean isPersistent() {
		return this.persistent;
	}

	/**
	 * Sets the persistent property of this OpmObject.
	 * 
	 */

	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}

	/**
	 * Returns String representing type of this OpmObject
	 * 
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Sets type of this OpmObject.
	 * 
	 */

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns true if this OpmObject is key.
	 * 
	 */

	public boolean isKey() {
		return this.key;
	}

	/**
	 * Sets key property of this OpmObject.
	 * 
	 */

	public void setKey(boolean key) {
		this.key = key;
	}

	/**
	 * Returns index name of this OpmObject.
	 * 
	 */
	public String getIndexName() {
		return this.indexName;
	}

	/**
	 * Sets index name of if this OpmObject.
	 * 
	 */

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * Returns index order of if this OpmObject.
	 * 
	 */

	public int getIndexOrder() {
		return this.indexOrder;
	}

	/**
	 * Sets index order of if this OpmObject.
	 * 
	 */
	public void setIndexOrder(int indexOrder) {
		this.indexOrder = indexOrder;
	}

	/**
	 * Returns initial value of if this OpmObject.
	 * 
	 */
	public String getInitialValue() {
		return this.initialValue;
	}

	/**
	 * Sets initial value of if this OpmObject.
	 * 
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

}