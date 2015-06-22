/*
 * Created on 13/11/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.session;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

import org.objectprocess.cmp.CollaborativeSessionPermissionsLocal;
import org.objectprocess.cmp.CollaborativeSessionPermissionsLocalHome;
import org.objectprocess.cmp.CollaborativeSessionPermissionsPK;
import org.objectprocess.cmp.CollaborativeSessionPermissionsUtil;
import org.objectprocess.cmp.CollaborativeSessionPermissionsValue;
import org.objectprocess.cmp.EditableUserValue;
import org.objectprocess.cmp.EnhancedUserValue;
import org.objectprocess.cmp.OpmModelPermissionsLocal;
import org.objectprocess.cmp.OpmModelPermissionsLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsPK;
import org.objectprocess.cmp.OpmModelPermissionsUtil;
import org.objectprocess.cmp.OpmModelPermissionsValue;
import org.objectprocess.cmp.UpdatableUserValue;
import org.objectprocess.cmp.UserLocal;
import org.objectprocess.cmp.UserLocalHome;
import org.objectprocess.cmp.UserUtil;
import org.objectprocess.cmp.UserValue;
import org.objectprocess.cmp.WorkgroupPermissionsLocal;
import org.objectprocess.cmp.WorkgroupPermissionsLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsPK;
import org.objectprocess.cmp.WorkgroupPermissionsUtil;
import org.objectprocess.cmp.WorkgroupPermissionsValue;
import org.objectprocess.fault.authenticate.AuthenticationFailedFault;
import org.objectprocess.fault.authenticate.NoSuchUserFault;
import org.objectprocess.fault.create.CreationFault;
import org.objectprocess.fault.create.UserAlreadyExistFault;
import org.objectprocess.fault.lookup.LookupFault;
import org.objectprocess.fault.lookup.NoUsersFoundFault;
import org.objectprocess.fault.lookup.PermissionLookupFault;
import org.objectprocess.fault.lookup.UserLookupFault;
import org.objectprocess.security.SecurityManager;

/**
 * Main implementation of the <code>UserAccess</code> session ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>UserAccess</code> encapsulates all user related methods invoked by clients.
 * 
 * @author Lior Galanti
 * 
 * @ejb.bean 
 *	name="UserAccess"
 *	jndi-name="UserAccessBean"
 *	type="Stateless" 
 *
 * @ejb.ejb-ref 
 * 	ejb-name="User"
 * 	view-type="local"
 * 	ref-name="UserLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="WorkgroupPermissions"
 * 	view-type="local"
 * 	ref-name="WorkgroupPermissionsLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="OpmModelPermissions"
 * 	view-type="local"
 * 	ref-name="OpmModelPermissionsLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="CollaborativeSessionPermissions"
 * 	view-type="local"
 * 	ref-name="CollaborativeSessionPermissionsLocal"
 * @ejb.resource-ref 
 *	res-ref-name="jdbc/DefaultDS"
 *	res-type="javax.sql.Datasource"
 *	res-auth="Container"
 *
 * @jboss.resource-ref 
 * 	res-ref-name="jdbc/DefaultDS" 
 * 	jndi-name="java:/DefaultDS"
 */

public abstract class UserAccessBean implements SessionBean {
	
	protected SessionContext ctx;
	private SecurityManager securityManager;
	private UserLocalHome userLocalHome;
	private WorkgroupPermissionsLocalHome workgroupPermissionsLocalHome;
	private OpmModelPermissionsLocalHome opmModelPermissionsLocalHome;
	private CollaborativeSessionPermissionsLocalHome collaborativeSessionPermissionsLocalHome;
	
	/**
	 * UserAccessBean <code>ejbCreate</code> method.
	 * @throws CreateException if ejb creation fails. 
	 * 
	 * @ejb.create-method
	**/
	public void ejbCreate() throws CreateException 
	{
		try {
			userLocalHome = UserUtil.getLocalHome();
			workgroupPermissionsLocalHome = WorkgroupPermissionsUtil.getLocalHome();
			opmModelPermissionsLocalHome = OpmModelPermissionsUtil.getLocalHome();
			collaborativeSessionPermissionsLocalHome = CollaborativeSessionPermissionsUtil.getLocalHome();
			securityManager = new SecurityManager();
		} catch (NamingException nE) {nE.printStackTrace();}
		//TODO handle NamingException for ejbCreate
	}
	
	/**
	 * Sets the session context 
	 * @param ctx the new context value
	 * 
	 * @ejb.method 
	 */    
	public void setSessionContext( SessionContext ctx )  {this.ctx = ctx;}    

	/**
	 * Unsets the session context 
	 * 
	 * @ejb.method 
	 */
	public void unsetSessionContext()  {this.ctx = null;}
	
	/**
	 * returns the user's permissions for the given workgroup.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupPermissionsPK primary key for the workgroup.
	 * @return a workgroup permission value object for the requested permission.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested permission could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public WorkgroupPermissionsValue getWorkgroupPermissionsForUser(
		String sUserID, 
		String sPassword, 
		WorkgroupPermissionsPK workgroupPermissionsPK)
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		WorkgroupPermissionsLocal wpl = null;
		
		try {
			wpl = workgroupPermissionsLocalHome.findByPrimaryKey(workgroupPermissionsPK);		
		} catch (FinderException fE){
			throw new PermissionLookupFault(workgroupPermissionsPK.getUserID());
		}
		return wpl.getWorkgroupPermissionsValue();		
	}
	
	/**
	 * returns the user's permissions for the given OPM model.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelPermissionsPK primary key for the the OPM model.
	 * @return a workgroup permission value object for the requested permission.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested permission could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public OpmModelPermissionsValue getOpmModelPermissionsForUser(
		String sUserID, 
		String sPassword, 
		OpmModelPermissionsPK opmModelPermissionsPK)
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		OpmModelPermissionsLocal opl = null;
		
		try {			
			opl = opmModelPermissionsLocalHome.findByPrimaryKey(opmModelPermissionsPK);			
		} catch (FinderException fE){
			throw new PermissionLookupFault(opmModelPermissionsPK.getUserID());
		}
		return opl.getOpmModelPermissionsValue();		
	}
	
	/**
	 * returns the user's permissions for the given collaborative session.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionPermissionsPK primary key for the the collaborative session.
	 * @return a workgroup permission value object for the requested permission.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested permission could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public CollaborativeSessionPermissionsValue getCollaborativeSessionPermissionsForUser(
		String sUserID, 
		String sPassword, 
		CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK)
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		CollaborativeSessionPermissionsLocal cpl = null;
		try {
			cpl = collaborativeSessionPermissionsLocalHome.findByPrimaryKey(collaborativeSessionPermissionsPK);	
		} catch (FinderException fE){
			throw new PermissionLookupFault(collaborativeSessionPermissionsPK.getUserID());
		}
		return cpl.getCollaborativeSessionPermissionsValue();
		
	}	
	
	/**
	 * autheticates the user and logges the client into the system.
	 * @param loginName login name for the user being logged in.
	 * @param password password for the user being logged in.
	 * @return the user's primary key guid value.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public String loginUser( String loginName, String password ) 
		throws AuthenticationFailedFault
	{ 	   	   
		String userID = null;
		try {
			Collection users = userLocalHome.findByLoginName(loginName);
			Iterator iterate = users.iterator();								
			if(iterate.hasNext()) {				   	
				UserLocal userLocal = (UserLocal) iterate.next();
				userID = userLocal.getUserID();
				securityManager.Authenticate(userID, password);
				//TODO check if already logged in and throw exception
				userLocal.setLoggedIn(new Boolean(true));
				userLocal.setLastLoginTime(new Date());
			}
		} catch(FinderException fE) {
		    throw new NoSuchUserFault(loginName);
		}
		return userID;
	}

	/**
	 * autheticates the user for logging into the administration portal.
	 * @param loginName login name for the user being logged in.
	 * @param password password for the user being logged in.
	 * @return the user's primary key guid value.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public String webLoginUser( String loginName, String password ) 
		throws AuthenticationFailedFault
	{ 	 
		String userID = null;
		try {
			Collection users = userLocalHome.findByLoginName(loginName);
			Iterator iterate = users.iterator();								
			if(iterate.hasNext()) {				   	
				UserLocal userLocal = (UserLocal) iterate.next();
				userID = userLocal.getUserID();
				if (!userLocal.getAdministrator().booleanValue())
				//TODO Check for Administrator premissions and throw exception if not	
				securityManager.Authenticate(userID, password);
			}
		} catch(FinderException fE) {throw new NoSuchUserFault(loginName);}
		return userID;
	}		

	/**
	 * logges the client out of the system.
	 * @param loginName login name for the user being logged out.
	 * @param password password for the user being logged out.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public void logoutUser(String userID, String password )
		throws AuthenticationFailedFault
	{
		UserLocal userLocal = securityManager.Authenticate(userID, password);
		userLocal.setLoggedIn(new Boolean(false));
	}
	
	/**
	 * returns an enhanced user value object for the requested user.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the enhanced user value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public EnhancedUserValue getEnhancedUserByPK( String sUserID, String sPassword ) 
		throws AuthenticationFailedFault
	{ 
		UserLocal userLocal = securityManager.Authenticate(sUserID, sPassword);
		return userLocal.getEnhancedUserValue();
	}	
	
	/**
	 * returns a user value object for the given user id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param userID primary key for the requested user.
	 * @return the user value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws UserLookupFault if the requested user could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public UserValue getUserByPK( String sUserID, String sPassword, String userID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		UserValue userValue = null;
		try {
			UserLocal userLocal = userLocalHome.findByPrimaryKey(userID);								
			userValue = userLocal.getUserValue();				
		} catch (FinderException fE){
				throw new UserLookupFault(userID);
		}		
		return userValue; 
	}
	/**
	 * returns an array list of user value objects for all users in the system.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all users.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoUsersFoundFault if no users were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public ArrayList getAllUsers( String sUserID, String sPassword ) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList usersList = new ArrayList();
		
		try {
			Collection users = userLocalHome.findAll();
			Iterator iterate = users.iterator();								
			while(iterate.hasNext()) {
				usersList.add(((UserLocal) iterate.next()).getUserValue()); }	
		} catch (FinderException fE){
			throw new NoUsersFoundFault();
		} return usersList;
	}

	/**
	 * returns a user value object for the requested user.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param loginName the login name for the requested user.
	 * @return the user value object for the requested user.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws UserLookupFault if the requested user could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public UserValue getUserByLoginName( String sUserID, String sPassword, String loginName) 
		throws AuthenticationFailedFault, LookupFault
	{		
		securityManager.Authenticate(sUserID, sPassword);
		UserValue userValue = null;
		
		try {
			Collection users = userLocalHome.findByLoginName(loginName);				   
			Iterator iterate = users.iterator();								
			if(iterate.hasNext()) {				   	
				userValue = ((UserLocal) iterate.next()).getUserValue();
			}
		} catch (FinderException fE){
			throw new UserLookupFault(loginName);
		} return userValue;
	}
	
	/**
	 * updates the user entry with the given updatable user value object.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param updatableUserValue updatable user value packing the information to be updated.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws UserLookupFault if the requested user could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */	
	public void updateUser( String sUserID, String sPassword, UpdatableUserValue updatableUserValue) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		UserLocal user = null;
		
		try {
			user = userLocalHome.findByPrimaryKey(updatableUserValue.getUserID());
			if (null == updatableUserValue.getPassword())
				updatableUserValue.setPassword(user.getPassword());
			user.setUpdatableUserValue(updatableUserValue);				
		} catch (FinderException fE){
			throw new UserLookupFault(updatableUserValue.getLoginName());
		} 
	}

	/**
	 * marks the requested user as disabled.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param userID primary key for user to be disabled.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws UserLookupFault if the requested user could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public void disableUser( String sUserID, String sPassword, String userID) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);		
		try {
			UserLocal userLocal = userLocalHome.findByPrimaryKey(userID);
			userLocal.setEnabled(new Boolean(false));
		} catch (FinderException fE){
			throw new UserLookupFault(userID);
		}	
	}
	
	/**
	 * marks the requested user as enabled.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param userID primary key for user to be enabled.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws UserLookupFault if the requested user could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public void enableUser( String sUserID, String sPassword, String userID) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);		
		try {
			UserLocal userLocal = userLocalHome.findByPrimaryKey(userID);
			userLocal.setEnabled(new Boolean(true));
		} catch (FinderException fE){
			throw new UserLookupFault(userID);
		}	
	}	

	/**
	 * creates a new user entry based on the information in the editable user value object.
	 * an editable user value object contains all user attributes that can be initialized during creation.
	 * @param editableUserValue the editable user value object containing the editable user attributes.
	 * @return GUID primary key for the new user.
	 * @throws UserAlreadyExistFault if a user with the requested name already exists.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
 	 */	
	public String createUser( EditableUserValue editableUserValue ) 
		throws CreationFault
	{	
		try {
		    //TODO check under what situations an exception is thrown
			editableUserValue.setUserID(UserUtil.generateGUID(editableUserValue));
			userLocalHome.create(editableUserValue);
		} catch (EJBException eE) {
			throw new UserAlreadyExistFault(editableUserValue.getLoginName());
		} catch (CreateException cE){
			throw new UserAlreadyExistFault(editableUserValue.getLoginName());
		} return editableUserValue.getUserID();
	}

}
