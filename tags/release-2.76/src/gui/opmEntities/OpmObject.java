package gui.opmEntities;


/**
 * This class represents Object in OPM.
 * For better understanding of this class you should be familiar with OPM.
 *
 * @version	2.0
 * @author		Stanislav Obydionnov
 * @author		Yevgeny   Yaroker
 */

public class OpmObject
    extends OpmThing {
//---------------------------------------------------------------------------
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
   * must be unique in OPCAT system. Other data members of OpmObject get default values.
   *
   * @param id OpmObject id
   * @param name OpmObject name
   */

  public OpmObject(long objectId, String objectName) {
    super(objectId, objectName);
    persistent = true;
    type = "";
    key = false;
    indexName = "";
    indexOrder = 0;
    initialValue = "";
    typeOriginId = -1;
  }

  public void copyPropsFrom(OpmObject origin) {
    super.copyPropsFrom(origin);
    persistent = origin.isPersistent();
    type = origin.getType();
    typeOriginId = origin.getTypeOriginId();
    key = origin.isKey();
    indexName = origin.getIndexName();
    indexOrder = origin.getIndexOrder();
    initialValue = origin.getInitialValue();
    
    //Added by Eran Toch
//    rolesManager = origin.getRolesManager();
  }

  public boolean hasSameProps(OpmObject pObject) {
    return (super.hasSameProps(pObject) &&
            (persistent == pObject.isPersistent()) &&
            (type.equals(pObject.getType())) &&
            (typeOriginId == pObject.getTypeOriginId()) &&
            (key == pObject.isKey()) &&
            (indexName.equals(pObject.getIndexName())) &&
            (indexOrder == pObject.getIndexOrder()) &&
            (initialValue.equals(pObject.getInitialValue())));
  }

  /**
   * Returns true if this OpmObject is persistent.
   *
   */

  public boolean isPersistent() {
    return persistent;
  }

  /**
   * Sets the persistent property of this OpmObject.
   *
   */

  public void setPersistent(boolean persistent) {
    this.persistent = persistent;
  }

  /**
   * Returns String representing type  of this OpmObject
   *
   */
  public String getType() {
    return type;
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
    return key;
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
    return indexName;
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
    return indexOrder;
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
    return initialValue;
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
    return typeOriginId;
  }

}