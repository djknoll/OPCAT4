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
 * @ejb.bean name="OpmModelPermissions"
 *	jndi-name="OpmModelPermissionsBean"
 *	type="CMP"
  *  schema="OpmModelPermissionsSchema" 
 *  cmp-version="2.x"
 * 
 * @ejb.value-object
 *	name="EnhancedOpmModelPermissions"
 *	match="enhanced"
 * 
 * @ejb.value-object
 *	name="Enhanced2OpmModelPermissions"
 *	match="enhanced2"
 * 
 * @ejb.value-object
 *	name="OpmModelPermissions"
 *	match="flat"
 *
 * @ejb.value-object
 *	name="EditableOpmModelPermissions"
 *	match="edit"
 *
 *  @ejb.persistence 
 *   table-name="opmmodel_permissions" 
 * 
 * @ejb.finder 
 *    query="SELECT OBJECT(a) FROM OpmModelPermissionsSchema as a"  
 *    signature="java.util.Collection findAll()"  
 *  
 **/

public abstract class OpmModelPermissionsBean implements EntityBean {

   /**
	* The  ejbCreate method.
	* @ejb.create-method 
	*/
	public OpmModelPermissionsPK ejbCreate(EditableOpmModelPermissionsValue editableOpmModelPermissionsValue) 
	 	throws CreateException {

		setUserID(editableOpmModelPermissionsValue.getUserID());
		setOpmModelID(editableOpmModelPermissionsValue.getOpmModelID());
		setAccessControl(editableOpmModelPermissionsValue.getAccessControl());
		setJoinTime(new Date());
		OpmModelPermissionsPK pk = new OpmModelPermissionsPK(
			editableOpmModelPermissionsValue.getOpmModelID(),
			editableOpmModelPermissionsValue.getUserID());
		return pk;
	}
	
   /**
	* The container invokes this method immediately after it calls ejbCreate.
	*/
	public void ejbPostCreate() throws javax.ejb.CreateException {}

//	CMR field for OpmModel
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="OpmModelPermissions-OpmModel"
	*	role-name="OpmModel-for-OpmModelPermissions"
	*	cascade-delete="yes"
	* 
	* @ejb.value-object
	* 	aggregate="org.objectprocess.cmp.OpmModelValue"
	* 	aggregate-name="OpmModel"
	* 	members="org.objectprocess.cmp.OpmModelLocal"
	* 	members-name="OpmModelValue"
	* 	relation="external"
	* 	match="enhanced"
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

//	CMR field for User		
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="OpmModelPermissions-User"
	*	role-name="User-for-OpmModelPermissions"
	*	cascade-delete="yes"
	* 
	* @ejb.value-object
	* 	aggregate="org.objectprocess.cmp.UserValue"
	* 	aggregate-name="User"
	* 	members="org.objectprocess.cmp.UserLocal"
	* 	members-name="UserValue"
	* 	relation="external"
	* 	match="enhanced2"
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
	
//	Flat data members for OpmModelPermissions Object

   /**
	* Returns the opmModelID
	* @return the opmModelID
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* @ejb.value-object match="edit"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*    column-name="OPMMODELID"
	*     sql-type="VARCHAR"
	* 
	* @ejb.pk-field 
	* @ejb.interface-method
	*/
	public abstract String getOpmModelID();

   /**
	* Sets the opmModelID
	* 
	* @param java.lang.Integer the new opmModelID value
	* 
	* @ejb.interface-method
	*/
	public abstract void setOpmModelID(String opmModelID);

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
	*    column-name="USERID"
	*    sql-type="VARCHAR"
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

//	Setter functions for EditableOpmModel Permissions Values
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public void setOpmModelPermissions(
	 	EditableOpmModelPermissionsValue editableOpmModelPermissionsValue){ 
			setAccessControl(editableOpmModelPermissionsValue.getAccessControl()); 
	 }
	 
//	Getter functions for Value objects	
	
   /**
	* @ejb.interface-method
	*	view-type="local" 
	*/
	public EnhancedOpmModelPermissionsValue getEnhancedOpmModelPermissionsValue(){ 
		return new EnhancedOpmModelPermissionsValue( 	
					getOpmModelID(),
					getUserID(),
					getJoinTime(),
					getAccessControl()); 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public Enhanced2OpmModelPermissionsValue getEnhanced2OpmModelPermissionsValue(){ 
		 return new Enhanced2OpmModelPermissionsValue( 	
					 getOpmModelID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }
	 
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public OpmModelPermissionsValue getOpmModelPermissionsValue(){ 
		 return new OpmModelPermissionsValue( 	
					 getOpmModelID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }

	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public EditableOpmModelPermissionsValue getEditableOpmModelPermissionsValue(){ 
		 return new EditableOpmModelPermissionsValue( 	
					 getOpmModelID(),
					 getUserID(),
					 getAccessControl()); 
	 }
}
