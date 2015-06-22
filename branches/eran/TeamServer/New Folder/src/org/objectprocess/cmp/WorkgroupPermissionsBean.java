/*
 * Created on 10/11/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.cmp;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;

/**
 * @ejb.bean name="WorkgroupPermissions"
 *	jndi-name="WorkgroupPermissionsBean"
 *	type="CMP"
 *  schema="WorkgroupPermissionsSchema" 
 *  cmp-version="2.x"
 * 
 * @ejb.persistence 
 *   table-name="workgroup_permissions" 
 * 
 * @ejb.value-object
 *	name="EnhancedWorkgroupPermissions"
 *	match="enhanced"
 *
 * @ejb.value-object
 *	name="Enhanced2WorkgroupPermissions"
 *	match="enhanced2"
 *
 * @ejb.value-object
 *	name="WorkgroupPermissions"
 *	match="flat"
 *
 * @ejb.value-object
 *	name="EditableWorkgroupPermissions"
 *	match="edit"
 * 
 * @ejb.finder 
 *    query="SELECT OBJECT(a) FROM WorkgroupPermissionsSchema as a"  
 *    signature="java.util.Collection findAll()" 
 *  
 */
public abstract class WorkgroupPermissionsBean implements EntityBean {
	
   /**
	* The  ejbCreate method. 
	* @ejb.create-method
	*/
	public WorkgroupPermissionsPK ejbCreate(EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue) 
		throws CreateException { 
		setUserID(editableWorkgroupPermissionsValue.getUserID());
		setWorkgroupID(editableWorkgroupPermissionsValue.getWorkgroupID());		
		setAccessControl(editableWorkgroupPermissionsValue.getAccessControl());
		setJoinTime(new Date());		 
		WorkgroupPermissionsPK pk = new WorkgroupPermissionsPK(
			editableWorkgroupPermissionsValue.getWorkgroupID(),
			editableWorkgroupPermissionsValue.getUserID());
		return pk;
	}

   /**
	* The container invokes this method immediately after it calls ejbCreate.
	*/
	public void ejbPostCreate() throws CreateException {}

//	CMR field for Workgroup		
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="WorkgroupPermissions-Workgroup"
	*	role-name="Workgroup-for-WorkgroupPermissions"
	*	cascade-delete="yes"
	* 
	* @ejb.value-object
	* 	aggregate="org.objectprocess.cmp.WorkgroupValue"
	* 	aggregate-name="Workgroup"
	* 	members="org.objectprocess.cmp.WorkgroupLocal"
	* 	members-name="Workgroup"
	* 	relation="external"
	* 	match = "enhanced"
	* 
	* @jboss.relation
	*	related-pk-field="workgroupID"
	*	fk-column="WORKGROUPID"
	*/
	public abstract WorkgroupLocal getWorkgroup();
	   
   /**
	* @ejb.interface-method
	*    view-type="local"
	*/
	public abstract void setWorkgroup(WorkgroupLocal workgroup);

//	CMR field for User		
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="WorkgroupPermissions-User"
	*	role-name="User-for-WorkgroupPermissions"
	*	cascade-delete="yes"
	* 
	* @ejb.value-object
	* 	aggregate="org.objectprocess.cmp.UserValue"
	* 	aggregate-name="User"
	* 	members="org.objectprocess.cmp.UserLocal"
	* 	members-name="UserValue"
	* 	relation="external"
	* 	match = "enhanced2"
	* 
	* @jboss.relation
	*	related-pk-field="userID"
	*	fk-column="USERID"
	*/
	public abstract UserLocal getUser();
	  
   /**
	* @ejb.interface-method
	*    view-type="local"
	*/
	public abstract void setUser(UserLocal user);
	
//	Flat data members for WorkgroupPermissions Object
   /**
	* Returns the workgroupID
	* @return the workgroupID
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* @ejb.value-object match="edit"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*    column-name="WORKGROUPID"
	*     sql-type="VARCHAR"
	* @ejb.pk-field
	* @ejb.interface-method
	*/
	public abstract String getWorkgroupID();

   /**
	* Sets the workgroupID
	* 
	* @param java.lang.Integer the new workgroupID value
	* 
	* @ejb.interface-method
	*/
	public abstract void setWorkgroupID(String workgroupID);

   /**
	* Returns the userID
	* @return the userID
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* @ejb.value-object match="edit"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*	column-name="USERID"
	*	sql-type="VARCHAR"
	*
	* @ejb.pk-field
	* @ejb.interface-method
	*/
	public abstract String getUserID();

   /**
	* Sets the userID
	* 
	* @param java.lang.Integer the new userID value
	* 
	* @ejb.interface-method
	*/
	public abstract void setUserID(String userID);

   /**
	* Returns the joinTime
	* @return the joinTime
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*    column-name="JOINTIME"
	*    sql-type="DATETIME"
	*  
	* @ejb.interface-method
	*/
	public abstract Date getJoinTime();

   /**
	* Sets the joinTime
	* 
	* @param java.util.Date the new joinTime value
	* 
	* @ejb.interface-method
	*/
	public abstract void setJoinTime(Date joinTime);

   /**
	* Returns the accessControl
	* @return the accessControl
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* @ejb.value-object match="edit"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*    column-name="ACCESSCONTROL"
	*    sql-type="INTEGER"
	*  
	* @ejb.interface-method
	*/
	public abstract Integer getAccessControl();

   /**
	* Sets the accessControl
	* 
	* @param java.lang.Integer the new accessControl value
	* 
	* @ejb.interface-method
	*/
	public abstract void setAccessControl(Integer accessControl);

//	Setter functions for Editable WorkgroupPermissions Values
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public void setWorkgroupPermissions(
	 	EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue){ 
			setAccessControl(editableWorkgroupPermissionsValue.getAccessControl()); 
	 }
	 
//	Getter functions for Value Objects	 
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsValue(){ 
		 return new EnhancedWorkgroupPermissionsValue( 	
					 getWorkgroupID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }

	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public Enhanced2WorkgroupPermissionsValue getEnhanced2WorkgroupPermissionsValue(){ 
		 return new Enhanced2WorkgroupPermissionsValue( 	
					 getWorkgroupID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }
	 
   /**
	* @ejb.interface-method
	*	view-type="local" 
	*/
	public WorkgroupPermissionsValue getWorkgroupPermissionsValue(){ 
		return new WorkgroupPermissionsValue( 	
					getWorkgroupID(),
					getUserID(),
					getJoinTime(),
					getAccessControl()); 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public EditableWorkgroupPermissionsValue getEditableWorkgroupPermissionsValue(){ 
		 return new EditableWorkgroupPermissionsValue( 	
					 getWorkgroupID(),
					 getUserID(),
					 getAccessControl()); 
	 }

}
