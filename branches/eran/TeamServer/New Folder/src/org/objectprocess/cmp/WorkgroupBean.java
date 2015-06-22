package org.objectprocess.cmp;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

/**
 * Main implementation of the <code>Workgroup</code> entity ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>Workgroup</code> is mapped to the <code>WorkgroupSchema</code> 
 * table schema and uses the WORKGROUP SQL table for persistence.
 * The following value objects are defined for the <code>Workgroup</code> ejb: 
 * <ul>
 * 	<li><code>EnhancedWorkgroupValue</code> - contains all attributes, 
 * 		as well as a <code>Collection</code> of
 * 		<code>Enhanced2WorkgroupPermission</code> objects the workgroup has a coresponding permission for.
 * 	<li><code>WorkgroupValue</code> - contains all attributes.
 * 	<li><code>EditableWorkgroupValue</code> - contains all attributes 
 * 		that can be initialized when the entity is created,
 * 		should be used as an argument for the <code>ejbCreate</code> method.
 * 	<li><code>UpdatableWorkgroupValue</code> - contains all attributes that can be updated.
 * </ul>
 * 
 * @author Lior Galanti
 * 
 * @ejb.bean name="Workgroup"
 *	name="Workgroup"
 *	jndi-name="WorkgroupBean"
 *	type="CMP"
 *  primkey-field="workgroupID"
 *  schema="WorkgroupSchema" 
 *  cmp-version="2.x"
 * @ejb.persistence 
 *	table-name="workgroup" 
 * 
 * @ejb.value-object
 *	name="EnhancedWorkgroup"
 *	match="enhanced"
 * @ejb.value-object
 *	name="Workgroup"
 *	match="flat"
 * @ejb.value-object
 *	name="EditableWorkgroup"
 *	match="edit"
 * @ejb.value-object
 *	name="UpdatableWorkgroup"
 *	match="update"
 * 
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM WorkgroupSchema as a"  
 *	signature="java.util.Collection findAll()"
 * 
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM WorkgroupSchema a where a.workgroupName = ?1"  
 * 	signature="java.util.Collection findByWorkgroupName(java.lang.String workgroupName)"  	  
 */
public abstract class WorkgroupBean implements EntityBean {
	
	protected EntityContext eContext;
	public void setEntityContext(EntityContext context){ eContext = context; } 
	public void unsetEntityContext(){ eContext = null; }
	
   /**
	 * Workgroup <code>ejbCreate</code> method.
	 * @param editableWorkgroupValue attributes required to create a new <code>Workgroup</code> ejb.
	 * @throws CreateException if ejb creation failed.
	 * 
	 * @ejb.create-method 
	 */
	public String ejbCreate(EditableWorkgroupValue editableWorkgroupValue) 
		throws CreateException {
		setEditableWorkgroupValue(editableWorkgroupValue);
		setEnabled(new Boolean(true));
		setCreationTime(new Date()); 
		return null;
	}
	
	/**
	 * The container invokes this method immediately after it calls <code>ejbCreate</code>.
	 * 
	 * @throws CreateException if ejb creation failed.
	 */
	public void ejbPostCreate() throws CreateException {}

	//	CMR fields
	/**
	 * Returns a <code>Collection</code> of <code>WorkgroupPermissionsLocal</code> related to the workgroup.
	 * @return a <code>Collection</code> of related <code>WorkgroupPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.Enhanced2WorkgroupPermissionsValue"
	 * 	aggregate-name="WorkgroupsPermission"
	 * 	members="org.objectprocess.cmp.WorkgroupPermissionsLocal"
	 * 	members-name="EnhancedWorkgroupPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced"
	 * 
	 * @ejb.relation
	 *	name="WorkgroupPermissions-Workgroup"
	 *	role-name="Workgroup-has-WorkgroupPermissions"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="workgroupID"
	 *	fk-column="WORKGROUPID"
	 */
	public abstract Collection getWorkgroupPermissions();   
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setWorkgroupPermissions(Collection workgroupPermissions);

	//	CMR field for OpmModels in workgroup 	
	/**
	 * Returns a <code>Collection</code> of assosiated <code>OpmModelLocal</code>.
	 * @return a <code>Collection</code> of assosiated <code>OpmModelLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.OpmModelValue"
	 * 	aggregate-name="OpmModel"
	 * 	members="org.objectprocess.cmp.OpmModelLocal"
	 * 	members-name="OpmModelValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 * 
	 * @ejb.relation
	 *	name="Workgroup-OpmModel"
	 *	role-name="Workgroup-has-OpmModel"
	 *
	 * @jboss.target-relation
	 *	related-pk-field="workgroupID"
	 * 	fk-column="WORKGROUPID"
	 */
	public abstract Collection getOpmModels();  
	/**
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setOpmModels(Collection opmModels);

	//	CMP fields
	/**
	 * Returns the workgroup's id.
	 * @return the workgroup's id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="WORKGROUPID"
	 *	sql-type="VARCHAR"
	 * @ejb.pk-field 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getWorkgroupID();
	/**
	 * Sets the workgroup's id.
	 * @param workgroupID the new workgroupID value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setWorkgroupID(String workgroupID);

	/**
	 * Returns the workgroup's name.
	 * @return the workgroup's name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="WORKGROUPNAME"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getWorkgroupName();
	/**
	 * Sets the workgroup's name
	 * @param workgroupName the new workgroup name value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setWorkgroupName(String workgroupName);

	/**
	 * Returns the workgroup's <code>enabled</code> flag value.
	 * @return the workgroup's <code>enabled</code> flag value.
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
	 */
	public abstract Boolean getEnabled();
	/**
	 * Sets the workgroup's <code>enabled</code> flag value.
	 * @param enabled the new <code>enabled</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setEnabled(Boolean enabled);

	/**
	 * Returns the time the workgroup was created.
	 * @return the time the workgroup was created.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="CREATIONTIME"
	 *	sql-type="DATETIME"  
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract Date getCreationTime();
	/**
	 * Sets the time for workgroup creation. Initialized once, on <code>ejbCreate</code>.
	 * @param CreationTime the new timestamp value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setCreationTime(Date creationTime);

	/**
	 * Returns the workgroup's description string.
	 * @return the workgroup's description.
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
	 */
	public abstract String getDescription();
	/**
	 * Sets the Description
	 * @param Description the new Description value
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setDescription(String description);
	
	//	 Getter functions for value objects	
	/**
	 * Returns an <code>EnhancedWorkgroupValue</code> object.
	 * Contains all the cmp fields for the workgroup and a Collection of <code>Enhanced2WorkgroupPermissionValue</code> objects,
	 * satisfying the entity bean's primary key.
	 * Enhanced2WorkgroupPermissionValue objects contain all flat WorkgroupPermission fields and a flat UserValue object.
	 * @return the enhanced workgroup value object. 
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public EnhancedWorkgroupValue getEnhancedWorkgroupValue(){ 
		return new EnhancedWorkgroupValue( 	
			getWorkgroupID(),
			getWorkgroupName(),
			getEnabled(),
			getCreationTime(),
			getDescription()); 
	}
	   
	/**
	 * Returns a <code>WorkgroupValue</code> object, 
	 * contains all the cmp fields for the workgroup.
	 * @return the workgroup value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public WorkgroupValue getWorkgroupValue(){ 
		return new WorkgroupValue( 	
			getWorkgroupID(),
			getWorkgroupName(),
			getEnabled(),
			getCreationTime(),
			getDescription()); 
	}
	
	/**
	 * Returns an <code>UpdatableWorkgroupValue</code> object, 
	 * contains all cmp fields that can be updated. 
	 * @return the updatable workgroup value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public UpdatableWorkgroupValue getUpdatableWorkgroupValue(){ 
		return new UpdatableWorkgroupValue( 	
			getWorkgroupID(),
			getWorkgroupName(),
			getEnabled(),
			getDescription()); 
	}
	
	/**
	 * Returns an <code>EditableWorkgroupValue</code> object, 
	 * contains all cmp fields that can be initialized when creating the bean.
	 * This object should only be used as an argument for ejbCreate.  
	 * @return the editable workgroup value object.
	 * @deprecated
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public EditableWorkgroupValue getEditableWorkgroupValue(){ 
		return new EditableWorkgroupValue( 	
			getWorkgroupID(),
			getWorkgroupName(),
			getDescription()); 
	}

	//	Setter functions for Value objects
	/**
	 * Sets the updatable workgroup cmp fields.
	 * @param updatableWorkgroupValue the updated workgroup cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public void setUpdatableWorkgroupValue(UpdatableWorkgroupValue updatableWorkgroupValue){	
	    if (updatableWorkgroupValue.workgroupNameHasBeenSet())	setWorkgroupName(updatableWorkgroupValue.getWorkgroupName());
	    if (updatableWorkgroupValue.enabledHasBeenSet())		setEnabled(updatableWorkgroupValue.getEnabled());
	    if (updatableWorkgroupValue.descriptionHasBeenSet()) 	setDescription(updatableWorkgroupValue.getDescription()); 
	}
	
	/**
	 * Sets the editable workgroup cmp fields, used only in ejbCreate.
	 * @param editableWorkgroupValue the new editable workgroup cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	private void setEditableWorkgroupValue(EditableWorkgroupValue editableWorkgroupValue){	
	    setWorkgroupID(editableWorkgroupValue.getWorkgroupID());
	    setWorkgroupName(editableWorkgroupValue.getWorkgroupName());
	  	setDescription(editableWorkgroupValue.getDescription()); 
	}

}
