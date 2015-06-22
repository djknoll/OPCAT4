package org.objectprocess.cmp;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

/**
 * Main implementation of the <code>OpmModel</code> entity ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>OpmModel</code> is mapped to the <code>OpmModelSchema</code> 
 * table schema and uses the OPMMODEL SQL table for persistence.
 * The following value objects are defined for the <code>OpmModel</code> ejb: 
 * <ul>
 * 	<li><code>EnhancedOpmModelValue</code> - contains all attributes, 
 * 		as well as a <code>Collection</code> of
 * 		<code>Enhanced2OpmModelPermission</code> objects the OPM Model has a coresponding permission for.
 * 	<li><code>OpmModelValue</code> - contains all attributes.
 * 	<li><code>EditableOpmModelValue</code> - contains all attributes 
 * 		that can be initialized when the entity is created,
 * 		should be used as an argument for the <code>ejbCreate</code> method.
 * 	<li><code>UpdatableOpmModelValue</code> - contains all attributes that can be updated.
 *  <li><code>OpmModelRevisionValue</code> - contains all attributes as well as a <code>Collection</code> 
 * 		of <code>MetaRevisionValue</code> objects assosiated with the OPM model.
 * </ul>
 * 
 * @author Lior Galanti
 * 
 * @ejb.bean name="OpmModel"
 *	jndi-name="OpmModelBean"
 *	type="CMP"
 *  primkey-field="opmModelID"
 *  schema="OpmModelSchema" 
 *  cmp-version="2.x"
 * @ejb.persistence 
 *	table-name="opmmodel" 
 *
 * @ejb.value-object
 *	name="EnhancedOpmModel"
 *	match="enhanced"
 * @ejb.value-object
 *	name="OpmModel"
 *	match="flat"
 * @ejb.value-object
 *	name="EditableOpmModel"
 *	match="edit"
 * @ejb.value-object
 *	name="UpdatableOpmModel"
 *	match="update"
 * @ejb.value-object
 *	name="OpmModelRevisions"
 *	match="revision"
 *
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM OpmModelSchema as a"  
 *	signature="java.util.Collection findAll()" 
 * 
 * @ejb.finder 
 * 	query="SELECT OBJECT(a) FROM OpmModelSchema a where a.opmModelName = ?1"  
 * 	signature="java.util.Collection findByOpmModelName(java.lang.String opmModelName)" 
 */
public abstract class OpmModelBean implements EntityBean {
	
	protected EntityContext eContext;
	public void setEntityContext(EntityContext context){ eContext = context; } 
	public void unsetEntityContext(){ eContext = null; }
	
	/**
	 * OpmModel <code>ejbCreate</code> method.
	 * @param editableOpmModelValue attributes required to create a new <code>OpmModel</code> ejb.
	 * @throws javax.ejb.CreateException if ejb creation failed.
	 * 
	 * @ejb.create-method
	 */
	public String ejbCreate(EditableOpmModelValue editableOpmModelValue) 
			 throws CreateException {
		setEditableOpmModelValue(editableOpmModelValue);	 	
		setEnabled(new Boolean(true));
		setCreationTime(new Date());
		setTotalCollaborativeTime(new Integer(0));
		return null;
	}

	/**
	 * The container invokes this method immediately after it calls <code>ejbCreate</code>.
	 * 
	 * @throws CreateException if ejb creation failed.
	 */
	public void ejbPostCreate() throws CreateException {}
	
	//	CMR field
	/**
	 * Returns a <code>Collection</code> of <code>RevisionLocal</code> related to the OPM Model.
	 * @return a <code>Collection</code> of related <code>RevisionLocal</code>.
	 * 
	 * @ejb.interface-method
	 *    view-type="local"
	 * 
	 * @ejb.value-object
	 *	aggregate="org.objectprocess.cmp.MetaRevisionValue"
	 *	aggregate-name="OpmModelRevision"
	 * 	members="org.objectprocess.cmp.RevisionLocal"
	 * 	members-name="MetaRevisionValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="revision"
	 * 
	 * @ejb.relation
	 *	name="Revisions-OpmModel"
	 *	role-name="OpmModel-has-Revisions"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="opmModelID"
	 * 	fk-column="OPMMODELID"
	 */
	public abstract Collection getOpmModelRevisions();	
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setOpmModelRevisions(Collection opmModelRevisions);
	
	/**
	 * Returns a <code>Collection</code> of <code>OpmModelPermissionsLocal</code> related to the OPM Model.
	 * @return a <code>Collection</code> of related <code>OpmModelPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 *	aggregate="org.objectprocess.cmp.Enhanced2OpmModelPermissionsValue"
	 *	aggregate-name="OpmModelsPermission"
	 * 	members="org.objectprocess.cmp.OpmModelPermissionsLocal"
	 * 	members-name="EnhancedOpmModelPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced"
	 * 
	 * @ejb.relation
	 *	name="OpmModelPermissions-OpmModel"
	 *	role-name="OpmModel-has-OpmModelPermissions"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="opmModelID"
	 * 	fk-column="OPMMODELID"
	 */
	public abstract Collection getOpmModelPermissions();
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setOpmModelPermissions(Collection opmModelPermissions);

	/**
	 * Returns a <code>Collection</code> of <code>CollaborativeSessionLocal</code> related to the OPM Model.
	 * @return a <code>Collection</code> of related <code>CollaborativeSessionLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.CollaborativeSessionValue"
	 * 	aggregate-name="CollaborativeSession"
	 * 	members="org.objectprocess.cmp.CollaborativeSessionLocal"
	 * 	members-name="CollaborativeSessionValue"
	 * 	relation="external"
	 *	type="java.util.Collection"
	 * 
	 * @ejb.relation
	 *	name="OpmModel-CollaborativeSession"
	 *	role-name="OpmModel-has-CollaborativeSession"
	 *
	 * @jboss.target-relation
	 *	related-pk-field="opmModelID"
	 * 	fk-column="OPMMODELID"
	 */
	public abstract Collection getCollaborativeSessions();	   
	/**
	 * @ejb.interface-method
	 *    view-type="local"
	 */
	public abstract void setCollaborativeSessions(Collection CollaborativeSession);
	
	/**
	 * Returns a <code>WorkgroupLocal</code> containing the OPM Model.
	 * @return a <code>WorkgroupLocal</code> containing the OPM Model.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.relation
	 *	name="Workgroup-OpmModel"
	 *	role-name="OpmModel-in-Workgroup"
	 *	cascade-delete="yes"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.WorkgroupValue"
	 * 	aggregate-name="Workgroup"
	 * 	members="org.objectprocess.cmp.WorkgroupLocal"
	 * 	members-name="WorkgroupValue"
	 * 	relation="external"
	 *
	 * @jboss.relation
	 *	related-pk-field="workgroupID"
	 *	fk-column="WORKGROUPID"
	 */
	public abstract WorkgroupLocal getWorkgroup();	   
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setWorkgroup(WorkgroupLocal Workgroup);

	//	CMP fields
	/**
	 * Returns the OPM model's id
	 * @return the OPM model's id
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELID"
	 *	sql-type="VARCHAR"
	 * @ejb.pk-field 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 * @ejb.value-object match="revision"
	 */
	public abstract String getOpmModelID();
	/**
	 * Sets the OPM model's id
	 * @param opmModelID the new OPM model's id value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setOpmModelID(String opmModelID);

	/**
	 * Returns the OPM model's name.
	 * @return the OPM model's name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="OPMMODELNAME"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 * @ejb.value-object match="revision"
	 */
	public abstract String getOpmModelName();
	/**
	 * Sets the OPM model's name
	 * @param opmModelName the new OPM model's name value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setOpmModelName(String opmModelName);

	/**
	 * Returns the OPM model's <code>enabled</code> flag value.
	 * @return the OPM model's <code>enabled</code> flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="ENABLED"
	 *	sql-type="TINYINT"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="update"
	 * @ejb.value-object match="revision"
	 */
	public abstract java.lang.Boolean getEnabled();
	/**
	 * Sets the OPM model's <code>enabled</code> flag value.
	 * @param enabled the new <code>enabled</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setEnabled(Boolean enabled);

	/**
	 * Returns the time the OPM model was created.
	 * @return the time the OPM model was created.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="CREATIONTIME"
	 *	sql-type="DATETIME"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="revision"
	 */
	public abstract Date getCreationTime();
	/**
	 * Sets the time for OPM model creation. Initialized once, on <code>ejbCreate</code>.
	 * @param CreationTime the new timestamp value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCreationTime(Date creationTime);

	/**
	 * Returns the OPM model's description string.
	 * @return the OPM model's description.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="DESCRIPTION"
	 *	sql-type="BLOB"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 * @ejb.value-object match="revision"
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
	 * Returns the id for the workgroup containing the OPM model.
	 * @return the id for the workgroup containing the OPM model.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="WORKGROUPID"
	 *	sql-type="VARCHAR" 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="revision"
	 */
	public abstract String getWorkgroupID();
	/**
	 * Sets the id for the workgroup containing the OPM model.
	 * @param workgroupID the new workgroup id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setWorkgroupID(String workgroupID);

	/**
	 * Returns the total accumulated time of collaborative work on the OPM model.
	 * @return the total accumulated time.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="TOTALCOLLABORATIVETIME"
	 *	sql-type="INTEGER"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="revision"
	 */
	public abstract Integer getTotalCollaborativeTime();
	/**
	 * Sets the total accumulated time of collaborative work on the OPM model.
	 * @param totalCollaborativeTime the new total accumulated time value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setTotalCollaborativeTime(Integer totalCollaborativeTime);
	
	//	 Getter functions for value objects
	/**
	 * Returns an <code>EnhancedOpmModelValue</code> object.
	 * Contains all the cmp fields for the OPM model and a Collection 
	 * of <code>Enhanced2OpmModelPermissionsValue</code> objects,
	 * satisfying the entity bean's primary key.
	 * Enhanced2OpmModelPermissionsValue objects contain 
	 * all flat OpmModelPermission fields and a flat UserValue object.
	 * @return the enhanced OPM model value object. 
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public EnhancedOpmModelValue getEnhancedOpmModelValue(){
		return new EnhancedOpmModelValue( 
				getOpmModelID(),
				getOpmModelName(),
				getEnabled(),
				getCreationTime(),
				getDescription(),
				getWorkgroupID(),
				getTotalCollaborativeTime());  
	}
	
	/**
	 * Returns an <code>OpmModelRevisionValue</code> object. 
	 * Contains all the cmp fields for the OPM model, 
	 * as well as a <code>Collection</code> of <code>MetaRevisionValue</code> objects
	 * containing meta data for all known revisions of the OPM model.
	 * @return the OPM model revision value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public OpmModelRevisionsValue getOpmModelRevisionsValue(){
		return new OpmModelRevisionsValue( 
				getOpmModelID(),
				getOpmModelName(),
				getEnabled(),
				getCreationTime(),
				getDescription(),
				getWorkgroupID(),
				getTotalCollaborativeTime());  
	}
	
	/**
	 * Returns an <code>OpmModelValue</code> object, 
	 * contains all the cmp fields for the workgroup.
	 * @return the OPM model value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public OpmModelValue getOpmModelValue(){
		return new OpmModelValue( 
				getOpmModelID(),
				getOpmModelName(),
				getEnabled(),
				getCreationTime(),
				getDescription(),
				getWorkgroupID(),
				getTotalCollaborativeTime());  
	}
	
	/**
	 * Returns an <code>UpdatableOpmModelValue</code> object, 
	 * contains all cmp fields that can be updated. 
	 * @return the updatable OPM model value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public UpdatableOpmModelValue getUpdatableOpmModelValue(){
		return new UpdatableOpmModelValue(
				getOpmModelID(), 
				getOpmModelName(),
				getEnabled(),
				getDescription());  
	}
	
	/**
	 * Returns an <code>EditableOpmModelValue</code> object, 
	 * contains all cmp fields that can be initialized when creating the bean.
	 * This object should only be used as an argument for ejbCreate.  
	 * @return the editable OPM model value object.
	 * @deprecated
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public EditableOpmModelValue getEditableOpmModelValue(){
		return new EditableOpmModelValue(
				getOpmModelID(), 
				getOpmModelName(),
				getDescription(),
				getWorkgroupID());  
	}

	// Setter function for value objects
	
	/**
	 * Sets the updatable OPM model cmp fields.
	 * @param updatableOpmModelValue the updated OPM model cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public void setUpdatableOpmModelValue(UpdatableOpmModelValue updatableOpmModelValue){
	    if (updatableOpmModelValue.opmModelNameHasBeenSet())	setOpmModelName(updatableOpmModelValue.getOpmModelName());
	    if (updatableOpmModelValue.descriptionHasBeenSet())		setDescription(updatableOpmModelValue.getDescription());
	    if (updatableOpmModelValue.enabledHasBeenSet())			setEnabled(updatableOpmModelValue.getEnabled());	
	}	

	/**
	 * Sets the editable OPM model cmp fields, used only in ejbCreate.
	 * @param editableOpmModelValue the new editable OPM model cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	private void setEditableOpmModelValue(EditableOpmModelValue editableOpmModelValue){
	    setOpmModelID(editableOpmModelValue.getOpmModelID());
	    setOpmModelName(editableOpmModelValue.getOpmModelName());
		setDescription(editableOpmModelValue.getDescription());
		setWorkgroupID(editableOpmModelValue.getWorkgroupID());   		
	}
}
