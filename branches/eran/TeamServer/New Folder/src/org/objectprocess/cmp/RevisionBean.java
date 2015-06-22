package org.objectprocess.cmp;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
/**
 * <code>Revision</code> entity implementation.
 * A revision entity holds an OPM model revision in the repository.
 * The revision numbering template is: <code>major.minor.build</code>, 
 * where the build number is constantly incremented, regardless of major and minor updates.
 * <code>Revision</code> is mapped to the <code>RevisionSchema</code> in the <code>Schema.dbxmi</code> file,
 * and uses the <code>REVISION</code> SQL table for persistence. Revision entities have no updatable value object, they are created once and are never changed.
 * The revision entity holds the following attributes:
 * 
 * <ul>
 * <li><code>revisionID</code> - the revision's primary key code.
 * <li><code>opmModelID</code> - the primary key code for the revision's OPM model entity. 
 * <li><code>opmModelFile</code> - the revision's OPX file.
 * <li><code>enabled</code> - enabled flag for the revision.
 * <li><code>major</code> - the major revision number.
 * <li><code>minor</code> - the minor revision number.
 * <li><code>build</code> - the build number.
 * <li><code>creationTime</code> - time of revision creation.
 * <li><code>comitterID</code> - the primary key code for the user entity who committed the session.
 * <li><code>description</code> - the revision's description.
 * </ul>
 * The following value objects are defined for the <code>Revision</code> ejb: 
 * <ul>
 * 	<li><code>MetaRevisionValue</code> - containing all CMP attributes but the OPX file.
 * 	<li><code>FullRevisionValue</code> - contains all attributes, including the OPX file and a value object of the source OPM model.
 * 	<li><code>EditableRevisionValue</code> - contains all attributes that can be initialized on object creation, only used as an argument for c'tor.
 * </ul>
 *  
 * @author Lior Galanti
 * 
 * @ejb.bean 
 *	name="Revision"
 *	jndi-name="RevisionBean"
 *	type="CMP"
 *  primkey-field="revisionID"
 *  schema="RevisionSchema" 
 *  cmp-version="2.x"
 * @ejb.persistence 
 *	table-name="revision" 
 * 
 * @ejb.value-object
 *	name="MetaRevision"
 *	match="meta"
 * @ejb.value-object
 *	name="FullRevision"
 *	match="full"
 * @ejb.value-object
 *	name="EditableRevision"
 *	match="edit"
 *
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM RevisionSchema as a"  
 *	signature="java.util.Collection findAll()"
 * 
 * @ejb.finder
 *	query="SELECT OBJECT(a) FROM RevisionSchema a where (a.build > ?1 AND a.opmModelID = ?2)"  
 *	signature="java.util.Collection findByCurrentRevision(java.lang.Integer build, java.lang.String opmModelID)"
 * 
 * @ejb.finder
 *	query="SELECT OBJECT(a) FROM RevisionSchema a where ( a.majorRevision = ?1 AND a.minorRevision = ?2 AND a.opmModelID = ?3) OR (a.opmModelID = ?3 AND a.majorRevision > ?1)"  
 *	signature="java.util.Collection findByRevisionNumber(java.lang.Integer majorRevision, java.lang.Integer minorRevision, java.lang.String opmModelID)"
 *
 * @ejb.finder
 *	query="SELECT OBJECT(a) FROM RevisionSchema as a where a.opmModelID = ?1"  
 *	signature="java.util.Collection findByOpmModelID(java.lang.String opmModelID)"
 *
 **/
public abstract class RevisionBean implements EntityBean {
	
	protected EntityContext eContext;
	public void setEntityContext(EntityContext context){ eContext = context; } 
	public void unsetEntityContext(){ eContext = null; }
	
	/**
	 * Revision <code>ejbCreate</code> method.
	 * @param editableRevisionValue contains attributed necessary to initilize a new Revision ejb.
	 * @throws CreateException if ejb creation fails.
	 * 
	 * @ejb.create-method 
	 */
	public String ejbCreate(EditableRevisionValue editableRevisionValue)
		throws CreateException {
		//TODO what the fuck?
		setRevisionID(editableRevisionValue.getRevisionID());
		setEditableRevisionValue(editableRevisionValue);
		setRevisionID(editableRevisionValue.getRevisionID());
		setEnabled(new Boolean(true));
		setCreationTime (new Date());
		return null;
	}
	/**
	 * The container invokes this method immediately after it calls <code>ejbCreate</code>.
	 * 
	 * @throws CreateException if ejb creation fails.
	 */
	public void ejbPostCreate() throws CreateException {
	}
	
	//	CMR fields
	/**
	 * Returns an <code>OpmModelLocal</code> the revision is based on.
	 * @return the <code>OpmModelLocal</code>.
	 * 
	 * @ejb.interface-method
	 *    view-type="local"
	 * 
	 * @ejb.relation
	 *	name="Revisions-OpmModel"
	 *	role-name="Revisions-for-OpmModel"
	 *	cascade-delete="yes"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.OpmModelValue"
	 * 	aggregate-name="OpmModel"
	 * 	members="org.objectprocess.cmp.OpmModelLocal"
	 * 	members-name="OpmModelValue"
	 * 	relation="external"
	 * 	match="full"
	 * 
	 * @jboss.relation
	 *	related-pk-field="opmModelID"
	 *	fk-column="OPMMODELID"
	 */
	public abstract OpmModelLocal getOpmModel();   
	/**
	 * @ejb.interface-method
	 *    view-type="local"
	 */
	public abstract void setOpmModel(OpmModelLocal OpmModel);
	
	//	CMP fields
	/**
	 * Returns the revision's id.
	 * @return the revision's id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="REVISIONID"
	 *	sql-type="VARCHAR"
	 * @ejb.pk-field 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getRevisionID();
	/**
	 * Sets the revision's id.
	 * @param revisionID the new revision id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setRevisionID(String revisionID);
	
	/**
	 * Returns the id for the OPM model the revision is based on.
	 * @return the OPM model's id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELID"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getOpmModelID();
	/**
	 * Sets the id of the OPM model the revision is based on.
	 * @param opmModelID the new OPM model id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setOpmModelID(String opmModelID);
	
	//TODO implement transport of a compressed OPX file.
	/**
	 * Returns the OPX file of the revision.
	 * @return the OPX file.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELFILE"
	 *	sql-type="BLOB"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getOpmModelFile();
	/**
	 * Sets the OPX file for the revision.
	 * @param opmModelFile the new OPX file value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setOpmModelFile(String opmModelFile);
	
	/**
	 * Returns the revision's <code>enabled</code> flag value.
	 * @return the enabled flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="ENABLED"
	 *	sql-type="TINYINT"  
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 */
	public abstract Boolean getEnabled();
	/**
	 * Sets the revision's <code>enabled</code> flag value.
	 * @param enabled the new <code>enabled</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	
	public abstract void setEnabled(Boolean enabled);
	/**
	 * Returns the revision's major revision number.
	 * @return the major revision number.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="MAJORREVISION"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract Integer getMajorRevision();
	/**
	 * Sets the revision's major revision number.
	 * @param revision the new major revision number value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setMajorRevision(Integer revision);
	
	/**
	 * Returns the revision's minor revision number.
	 * @return the minor revision number.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="MINORREVISION"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract Integer getMinorRevision();
	/**
	 * Sets the revision's minor revision number.
	 * @param revision the new minor revision value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setMinorRevision(Integer revision);

	/**
	 * Returns the revision's build number.
	 * @return the build number.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="BUILD"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract Integer getBuild();
	/**
	 * Sets the revision's build number,
	 * @param build the new build number value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setBuild(Integer build);
	
	/**
	 * Returns the creationTime
	 * @return the creationTime
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="CREATIONTIME"
	 *	sql-type="DATETIME"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 */
	public abstract Date getCreationTime();
	/**
	 * Sets the creationTime
	 * @param creationTime the new creationTime value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCreationTime(Date creationTime);
	
	/**
	 * Returns the comitterID
	 * @return the comitterID
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="COMITTERID"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getComitterID();
	/**
	 * Sets the comitterID
	 * @param comitterID the new comitterID value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setComitterID(String comitterID);
	
	/**
	 * Returns the description
	 * @return the description
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="DESCRIPTION"
	 *	sql-type="BLOB"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="full"
	 * @ejb.value-object match="meta"
	 * @ejb.value-object match="edit"
	 */
	public abstract String getDescription();
	/**
	 * Sets the description
	 * @param description the new description value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setDescription(String description);
 
	/**
	 * Update the object with the Editable Value Object
	 * @param editableRevisionValue the new Editable Revision fields
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */	
	public void setEditableRevisionValue(EditableRevisionValue editableRevisionValue){
		setRevisionID(editableRevisionValue.getRevisionID());
		setOpmModelID(editableRevisionValue.getOpmModelID());
		setOpmModelFile(editableRevisionValue.getOpmModelFile());
		setMajorRevision(editableRevisionValue.getMajorRevision());
		setMinorRevision(editableRevisionValue.getMinorRevision());
		setComitterID(editableRevisionValue.getComitterID());
		setDescription(editableRevisionValue.getDescription());	
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public FullRevisionValue getFullRevisionValue(){
		return new FullRevisionValue(
			getRevisionID(),
			getOpmModelID(),
			getOpmModelFile(),
			getEnabled(),
			getMajorRevision(),
			getMinorRevision(),
			getBuild(),
			getCreationTime(),
			getComitterID(),
			getDescription());
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public EditableRevisionValue getEditableRevisionValue(){
		return new EditableRevisionValue(
			getRevisionID(),
			getOpmModelID(),
			getOpmModelFile(),
			getMajorRevision(),
			getMinorRevision(),
			getBuild(),
			getComitterID(),
			getDescription());
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="both" 
	 */	
	public MetaRevisionValue getMetaRevisionValue(){
		return new MetaRevisionValue(
			getRevisionID(),
			getOpmModelID(),
			getEnabled(),
			getMajorRevision(),
			getMinorRevision(),
			getBuild(),
			getCreationTime(),
			getComitterID(),
			getDescription());
	}
	
}