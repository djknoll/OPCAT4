package org.objectprocess.cmp;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

/**
 * Main implementation of the <code>User</code> entity ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>User</code> is mapped to the <code>UserSchema</code> 
 * table schema and uses the USER SQL table for persistence.
 * The following value objects are defined for the <code>User</code> ejb: 
 * <ul>
 * 	<li><code>EnhancedUserValue</code> - contains all attributes, 
 * 		as well as a <code>Collection</code> of
 * 		<code>Workgroup</code>, <code>OpmModel</code> and <code>CollaborativeSession</code>
 * 		objects the user has a coresponding permission for.
 * 	<li><code>UserValue</code> - contains all attributes.
 * 	<li><code>EditableUserValue</code> - contains all attributes that 
 * 		can be initialized when the entity is created.
 * 		should be used as an argument for the <code>ejbCreate</code> method.
 * 	<li><code>UpdatableUserValue</code> - contains all attributes that can be updated.
 * </ul>
 *  
 * @author Lior Galanti
 *
 * @ejb.bean 
 *	name="User"
 *	jndi-name="UserBean"
 *	type="CMP"
 *	primkey-field="userID"
 *	schema="UserSchema" 
 *	cmp-version="2.x"
 * @ejb.persistence 
 *	table-name="user" 
 *
 * @ejb.value-object
 *	name="EnhancedUser"
 *	match="enhanced"
 * @ejb.value-object
 *	name="User"
 *	match="flat"
 * @ejb.value-object
 *	name="EditableUser"
 *	match="edit"
 * @ejb.value-object
 *	name="UpdatableUser"
 *	match="update"
 *  
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM UserSchema as a"  
 *	signature="java.util.Collection findAll()"
 * 
 * @ejb.finder 
 *	query="SELECT OBJECT(a) FROM UserSchema a where a.loginName = ?1"  
 *	signature="java.util.Collection findByLoginName(java.lang.String loginName)"  
 */
public abstract class UserBean implements EntityBean {

	protected EntityContext eContext;
	public void setEntityContext(EntityContext context){ eContext = context; } 
	public void unsetEntityContext(){ eContext = null; }
	
	/**
	 * User <code>ejbCreate</code> method.
	 * @param editableUserValue attributes required to create a new <code>User</code> ejb.
	 * @throws CreateException if ejb creation failed.
	 *	
	 * @ejb.create-method
	 */
	public String ejbCreate(EditableUserValue editableUserValue) 
			throws CreateException {
		setEditableUserValue(editableUserValue);
		setEnabled(new Boolean(true));
		setLoggedIn(new Boolean(false));
		setLastLoginTime(new Date());
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
	 * returns a <code>Collection</code> of <code>WorkgroupPermissionsLocal</code> related to the user.	
	 * @return a collection of related <code>WorkgroupPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 *
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue"
	 * 	aggregate-name="EnhancedWorkgroupsPermission"
	 * 	members="org.objectprocess.cmp.WorkgroupPermissionsLocal"
	 * 	members-name="EnhancedWorkgroupPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced"
	 * 
	 * @ejb.relation
	 *	name="WorkgroupPermissions-User"
	 *	role-name="user-has-WorkgroupPermissions"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="userID"
	 *	fk-column="USERID"
	 */
	public abstract Collection getWorkgroupPermissions();	   
	/**
	 * sets the workgroup permissions for the user.
	 * @param workgroupPermissions a collection of workgroup permission objects
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setWorkgroupPermissions(Collection workgroupPermissions);

	/**
	 * returns a <code>Collection</code> of <code>OpmModelPermissionsLocal</code> related to the user.
	 * @return a collection of related <code>OpmModelPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 * 	aggregate="org.objectprocess.cmp.EnhancedOpmModelPermissionsValue"
	 * 	aggregate-name="EnhancedOpmModelsPermission"
	 * 	members="org.objectprocess.cmp.OpmModelPermissionsLocal"
	 * 	members-name="EnhancedOpmModelPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced"
	 * 
	 * @ejb.relation
	 *	name="OpmModelPermissions-User"
	 *	role-name="user-has-OpmModelPermissions"
	 *
	 * @jboss.target-relation
	 *	related-pk-field="userID"
	 *	fk-column="USERID"
	 */
	public abstract java.util.Collection getOpmModelPermissions();
	/**
	 * sets the OPM model permissions for the user
	 * @param opmModelPermissions a collection of OPM model presmissions
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setOpmModelPermissions(Collection opmModelPermissions);
	
	/**
	 * returns a <code>Collection</code> of <code>CollaborativeSessionPermissionsLocal</code> related to the user.  
	 * @return a collection of related <code>CollaborativeSessionPermissionsLocal</code>.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 * 
	 * @ejb.value-object
	 *	aggregate="org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue"
	 * 	aggregate-name="EnhancedCollaborativeSessionsPermission"
	 * 	members="org.objectprocess.cmp.CollaborativeSessionPermissionsLocal"
	 * 	members-name="EnhancedCollaborativeSessionPermissionsValue"
	 * 	relation="external"
	 * 	type="java.util.Collection"
	 *	match="enhanced" 
	 * 
	 * @ejb.relation
	 *	name="CollaborativeSessionPermissions-User"
	 *	role-name="User-has-CollaborativeSessionPermissions"
	 * 
	 * @jboss.target-relation
	 *	related-pk-field="userID"
	 *	fk-column="USERID"
	 */
	public abstract Collection getCollaborativeSessionPermissions();	   
	/**
	 * sets the collaborative session permissions for the user
	 * @param collaborativeSessionPermissions a collection of collaborative session permissions
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public abstract void setCollaborativeSessionPermissions(Collection collaborativeSessionPermissions);

	//	CMP fields
	/**
	 * Returns the user's id.
	 * @return the user's id.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="USERID"
	 *	sql-type="VARCHAR"
	 * @ejb.pk-field 
	 * @ejb.interface-method 
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getUserID();
	/**
	 * Sets the user's id.
	 * @param userID The new user id value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setUserID(String userID);
	
	/**
	 * Returns the user's login name.
	 * @return the user's login name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="LOGINNAME"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getLoginName();	
	/**
	 * Sets the user's login name.
	 * @param loginName the new login name value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setLoginName(String loginName);
	
	/**
	 * Returns the user's first name.
	 * @return the user's first name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="FIRSTNAME"
	 *	sql-type="VARCHAR" 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getFirstName();	
	/**
	 * Sets the user's first name.
	 * @param firstName the new first name value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setFirstName(String firstName);
	
	/**
	 * Returns the user's last name.
	 * @return the user's last name.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="LASTNAME"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getLastName();
	/**
	 * Sets the user's last name.
	 * @param lastName the new last name value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setLastName(String lastName);
	
	/**
	 * Returns the user's password.
	 * @return the user's password.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="PASSWORD"
	 *	sql-type="VARCHAR" 
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getPassword();
	
	/**
	 * Sets the user's password.
	 * @param password the new password value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setPassword(String password);
	
	/**
	 * Returns the user's email.
	 * @return the user's email.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="EMAIL"
	 *	sql-type="VARCHAR"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 * @ejb.value-object match="update"
	 */
	public abstract String getEmail();
	/**
	 * Sets the user's email.
	 * @param email the new email value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setEmail(String email);
	
	/**
	 * Returns the user's <code>enabled</code> flag value, Setting to <code>false</code> will deny the user's login.
	 * @return the user's <code>enabled</code> flag value.
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
	 * Sets the user's <code>enabled</code> flag value.
	 * @param enabled the new <code>enabled</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setEnabled(Boolean enabled);
	
	/**
	 * Returns the user's <code>loggedIn</code> flag value. 
	 * @return the user's <code>loggedIn</code> flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="LOGGEDIN"
	 *	sql-type="TINYINT"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract Boolean getLoggedIn();
	/**
	 * Sets the user's <code>loggedIn</code> flag value.
	 * @param loggenIn the new <code>loggedIn</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setLoggedIn(Boolean loggenIn);
	
	/**
	 * Returns the last time the user has logged in, updates every login.
	 * @return the last time the user has logged in.
	 *  
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="LASTLOGINTIME"
	 *	sql-type="DATETIME"
	 * @ejb.interface-method
	 *
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 */
	public abstract Date getLastLoginTime();
	/**
	 * Sets the user's last login time stamp.
	 * @param lastLoginTime the new login time value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setLastLoginTime(Date lastLoginTime);
	
	/**
	 * Returns the user's description string.
	 * @return the user's description.
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
	 * Sets the user's description string.
	 * @param description the new description value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setDescription(String description);
	/**
	 * Returns the <code>administrator</code> flag value.
	 * @return the <code>administrator</code> flag value.
	 * 
	 * @ejb.persistent-field 
	 * @ejb.persistence
	 *	column-name="ADMINISTRATOR"
	 *	sql-type="TINYINT"
	 * @ejb.interface-method
	 * 
	 * @ejb.value-object match="enhanced"
	 * @ejb.value-object match="flat"
	 * @ejb.value-object match="edit"
	 */
	public abstract Boolean getAdministrator();
	/**
	 * Sets the <code>administrator</code> flag value.
	 * @param administrator the new the <code>administrator</code> flag value.
	 * 
	 * @ejb.interface-method
	 */
	public abstract void setAdministrator(Boolean  administrator);
	
	//	Getter functions for value objects	
	/**
	 * Returns an <code>EnhancedUserValue</code> object.
	 * Contains all the cmp fields for the user, a Collection of <code>EnhancedWorkgroupPermissionValue</code> objects, 
	 * a Collection of <code>EnhancedOpmModelPermissionValue</code> objects, 
	 * and a Collection <code>of EnhancedCollaborativeSessionPermissionValue</code> objects, 
	 * all satisfying the entity bean's primary key.
	 * each enhanced permission object contains the corresponding object.
	 * <p>
	 * for example: EnhancedWorkgroupPermissionValue objects contain a flat WorkgroupValue object.
	 * Useful for getting an entire tree of objects related to the user.
	 * @return the enhanced user value object.
	 * 
	 * @ejb.interface-method
	 * view-type="local" 
	 */
	public EnhancedUserValue getEnhancedUserValue(){ 
		return new EnhancedUserValue(
				getUserID(), 
				getLoginName(),
				getFirstName(),
				getLastName(),
				getPassword(),
				getEmail(),
				getEnabled(),
				getLoggedIn(),
				getLastLoginTime(),
				getDescription(),
				getAdministrator());
	}
	
	/**
	 * Returns a <code>UserValue</code> object.
	 * Contains all the cmp fields for the user but the password.
	 * @return the user value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public UserValue getUserValue(){ 
		return new UserValue(
				getUserID(), 
				getLoginName(),
				getFirstName(),
				getLastName(),
				getEmail(),
				getEnabled(),
				getLoggedIn(),
				getLastLoginTime(),
				getDescription(),
				getAdministrator()); 
	}
	
	/**
	 * Returns an <code>UpdatableUserValue</code> object.
	 * Contains all cmp fields that can be updated. 
	 * @return the updatable user value object.
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public UpdatableUserValue getUpdatableUserValue(){ 
		return new UpdatableUserValue(
				getUserID(), 
				getLoginName(),
				getFirstName(),
				getLastName(),
				getPassword(),
				getEmail(),
				getEnabled(),
				getDescription()); 
	}
	
	/**
	 * Returns an <code>EditableUserValue</code> object.
	 * Contains all cmp fields that can be initialized when creating the bean.
	 * This object should only be used as an argument for ejbCreate.  
	 * @return the editable user value object.
	 * @deprecated
	 * 
	 * @ejb.interface-method
	 *	view-type="local" 
	 */
	public EditableUserValue getEditableUserValue(){ 
		return new EditableUserValue(
				getUserID(), 
				getLoginName(),
				getFirstName(),
				getLastName(),
				getPassword(),
				getEmail(),
				getDescription(),
				getAdministrator()); 
	}
		
	//	Setter function for value objects
	/**
	 * Sets the updatable user cmp fields.
	 * @param updatableUserValue the updated user cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	public void setUpdatableUserValue(UpdatableUserValue updatableUserValue){ 

		if (updatableUserValue.loginNameHasBeenSet())	setLoginName(updatableUserValue.getLoginName());			
		if (updatableUserValue.firstNameHasBeenSet())	setFirstName(updatableUserValue.getFirstName());		
		if (updatableUserValue.lastNameHasBeenSet())	setLastName(updatableUserValue.getLastName());
		if (updatableUserValue.passwordHasBeenSet())	setPassword(updatableUserValue.getPassword());
		if (updatableUserValue.emailHasBeenSet())		setEmail(updatableUserValue.getEmail());
		if (updatableUserValue.descriptionHasBeenSet())	setDescription(updatableUserValue.getDescription());
	}	
	
	/**
	 * Sets the editable user cmp fields, used only in ejbCreate.
	 * @param editableUserValue the new editable user cmp fields.
	 * 
	 * @ejb.interface-method
	 *	view-type="local"
	 */
	private void setEditableUserValue(EditableUserValue editableUserValue){
	    setUserID(editableUserValue.getUserID());
	    setLoginName(editableUserValue.getLoginName());
		setFirstName(editableUserValue.getFirstName());		
		setLastName(editableUserValue.getLastName());
		setPassword(editableUserValue.getPassword());
		setEmail(editableUserValue.getEmail());
		setDescription(editableUserValue.getDescription());
		setAdministrator(editableUserValue.getAdministrator());
	}
}
