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
 * @ejb.bean name="CollaborativeSessionPermissions"
 *	jndi-name="CollaborativeSessionPermissionsBean"
 *	type="CMP"
  *  schema="CollaborativeSessionPermissionsSchema" 
 *  cmp-version="2.x"
 * 
 * @ejb.value-object
 *	name="EnhancedCollaborativeSessionPermissions"
 *	match="enhanced"
 *
 * @ejb.value-object
 *	name="Enhanced2CollaborativeSessionPermissions"
 *	match="enhanced2"
 *
 * @ejb.value-object
 *	name="CollaborativeSessionPermissions"
 *	match="flat"
 *
 * @ejb.value-object
 *	name="EditableCollaborativeSessionPermissions"
 *	match="edit"
 *
 * @ejb.persistence 
 *   table-name="session_permissions" 
 * 
 * @ejb.finder 
 *    query="SELECT OBJECT(a) FROM CollaborativeSessionPermissionsSchema as a"  
 *    signature="java.util.Collection findAll()"
 * 
 **/

public abstract class CollaborativeSessionPermissionsBean implements EntityBean {

	/**
	 * The ejbCreate method.
	 * @ejb.create-method
	 */
	 public CollaborativeSessionPermissionsPK ejbCreate(
	 	EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue) 
	 	throws CreateException 
	 {		
		 setUserID(editableCollaborativeSessionPermissionsValue.getUserID());
		 setCollaborativeSessionID(editableCollaborativeSessionPermissionsValue.getCollaborativeSessionID());
		 setAccessControl(editableCollaborativeSessionPermissionsValue.getAccessControl());
		 setJoinTime(new Date());
		 CollaborativeSessionPermissionsPK pk = new CollaborativeSessionPermissionsPK(
		 	editableCollaborativeSessionPermissionsValue.getCollaborativeSessionID(),
		 	editableCollaborativeSessionPermissionsValue.getUserID());
		 return pk;
	 }

   /**
	* The container invokes this method immediately after it calls ejbCreate.
	*/
	public void ejbPostCreate() throws CreateException {}
	 	
// CMR field for CollaborativeSession	
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="CollaborativeSessionPermissions-CollaborativeSession"
	*	role-name="CollaborativeSession-for-CollaborativeSessionPermissions"
	* 
	* @ejb.value-object
	* 	aggregate="org.objectprocess.cmp.CollaborativeSessionValue"
	* 	aggregate-name="CollaborativeSession"
	* 	members="org.objectprocess.cmp.CollaborativeSessionLocal"
	* 	members-name="CollaborativeSessionValue"
	* 	relation="external"
	* 	match="enhanced"
	* 
	* @jboss.relation
	*	related-pk-field="collaborativeSessionID"
	*	fk-column="SESSIONID"
	*/
	public abstract CollaborativeSessionLocal getCollaborativeSession();
	   
   /**
	* @ejb.interface-method
	*    view-type="local"
	*/
	public abstract void setCollaborativeSession(CollaborativeSessionLocal CollaborativeSession);

//	CMR field for User		
   /**
	* @ejb.interface-method
	*    view-type="local"
	* 
	* @ejb.relation
	*	name="CollaborativeSessionPermissions-User"
	*	role-name="User-for-CollaborativeSessionPermissions"
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

//	Flat data members for CollaborativeSessionPermissions Object
   /**
	* Returns the collaborativeSessionID
	* @return the collaborativeSessionID
	* 
	* @ejb.value-object match="enhanced"
	* @ejb.value-object match="enhanced2"
	* @ejb.value-object match="flat"
	* @ejb.value-object match="edit"
	* 
	* @ejb.persistent-field 
	* @ejb.persistence
	*    column-name="SESSIONID"
	*     sql-type="VARCHAR"
	* @ejb.pk-field 
	* @ejb.interface-method
	*/
	public abstract String getCollaborativeSessionID();

   /**
	* Sets the collaborativeSessionID
	* 
	* @param java.lang.Integer the new collaborativeSessionID value
	* 
	* @ejb.interface-method
	*/
	public abstract void setCollaborativeSessionID(String collaborativeSessionID);

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
	*     sql-type="INTEGER"
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
	*     sql-type="DATETIME"
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
	*     sql-type="INTEGER"
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

//	Setter functions for Editable CollaborativeSessionPermissions Values
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public void setCollaborativeSessionPermissions(
	 	EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue){ 
	 		setAccessControl(editableCollaborativeSessionPermissionsValue.getAccessControl()); 
	 }

//	Getter functions for Value Objects 	
   /**
	* @ejb.interface-method
	*	view-type="local" 
	*/
	public EnhancedCollaborativeSessionPermissionsValue getEnhancedCollaborativeSessionPermissionsValue(){ 
		return new EnhancedCollaborativeSessionPermissionsValue( 	
					getCollaborativeSessionID(),
					getUserID(),
					getJoinTime(),
					getAccessControl()); 
	}
	
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public Enhanced2CollaborativeSessionPermissionsValue getEnhanced2CollaborativeSessionPermissionsValue(){ 
		 return new Enhanced2CollaborativeSessionPermissionsValue( 	
					 getCollaborativeSessionID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }

	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public CollaborativeSessionPermissionsValue getCollaborativeSessionPermissionsValue(){ 
		 return new CollaborativeSessionPermissionsValue( 	
					 getCollaborativeSessionID(),
					 getUserID(),
					 getJoinTime(),
					 getAccessControl()); 
	 }
	 
	/**
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	 public EditableCollaborativeSessionPermissionsValue getEditableCollaborativeSessionPermissionsValue(){ 
		 return new EditableCollaborativeSessionPermissionsValue( 	
					 getCollaborativeSessionID(),
					 getUserID(),
					 getAccessControl()); 
	 }

}
