/*
 * Created on 13/11/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.objectprocess.cmp.CollaborativeSessionFileValue;
import org.objectprocess.cmp.CollaborativeSessionLocal;
import org.objectprocess.cmp.CollaborativeSessionLocalHome;
import org.objectprocess.cmp.CollaborativeSessionPermissionsLocal;
import org.objectprocess.cmp.CollaborativeSessionPermissionsLocalHome;
import org.objectprocess.cmp.CollaborativeSessionPermissionsPK;
import org.objectprocess.cmp.CollaborativeSessionPermissionsUtil;
import org.objectprocess.cmp.CollaborativeSessionUtil;
import org.objectprocess.cmp.CollaborativeSessionValue;
import org.objectprocess.cmp.EditableCollaborativeSessionPermissionsValue;
import org.objectprocess.cmp.EditableCollaborativeSessionValue;
import org.objectprocess.cmp.EditableRevisionValue;
import org.objectprocess.cmp.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.cmp.EnhancedCollaborativeSessionValue;
import org.objectprocess.cmp.OpmModelLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsUtil;
import org.objectprocess.cmp.OpmModelUtil;
import org.objectprocess.cmp.RevisionLocal;
import org.objectprocess.cmp.RevisionLocalHome;
import org.objectprocess.cmp.RevisionUtil;
import org.objectprocess.cmp.UpdatableCollaborativeSessionValue;
import org.objectprocess.cmp.UserLocalHome;
import org.objectprocess.cmp.UserUtil;
import org.objectprocess.cmp.WorkgroupLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsUtil;
import org.objectprocess.cmp.WorkgroupUtil;
import org.objectprocess.config.PermissionFlags;
import org.objectprocess.config.SystemMessages;
import org.objectprocess.fault.authenticate.AuthenticationFailedFault;
import org.objectprocess.fault.authenticate.UserAlreadyLoggedInFault;
import org.objectprocess.fault.authenticate.UserNotLoggedInFault;
import org.objectprocess.fault.create.CreationFault;
import org.objectprocess.fault.create.SessionAlreadyExistFault;
import org.objectprocess.fault.lookup.LookupFault;
import org.objectprocess.fault.lookup.NoSessionsFoundFault;
import org.objectprocess.fault.lookup.NoSuchSessionFault;
import org.objectprocess.fault.lookup.PermissionLookupFault;
import org.objectprocess.fault.recursiveDisable.RecursiveDisableFault;
import org.objectprocess.fault.recursiveDisable.UsersStillLoggedInFault;
import org.objectprocess.fault.token.NotTheTokenHolderFault;
import org.objectprocess.fault.token.TokenFault;
import org.objectprocess.fault.token.TokenNotAvailableFault;
import org.objectprocess.security.SecurityManager;


/**
 * Main implementation of the <code>CollaborativeSessionAccess</code> session ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>CollaborativeSessionAccess</code> encapsulates all Collaborative Session related methods invoked by clients.
 * 
 * @ejb.bean 
 * 	name="CollaborativeSessionAccess"
 *	jndi-name="CollaborativeSessionAccessBean"
 *	type="Stateless" 
 * @ejb.resource-ref 
 *	res-ref-name="jdbc/DefaultDS"
 *	res-type="javax.sql.Datasource"
 *	res-auth="Container"
 *
 * @ejb.ejb-ref 
 * 	ejb-name="CollaborativeSession"
 * 	view-type="local"
 * 	ref-name="CollaborativeSessionLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="CollaborativeSessionPermissions"
 * 	view-type="local"
 * 	ref-name="CollaborativeSessionPermissionsLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="OpmModel"
 * 	view-type="local"
 * 	ref-name="OpmModelLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="OpmModelPermissions"
 * 	view-type="local"
 * 	ref-name="OpmModelPermissionsLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="Workgroup"
 * 	view-type="local"
 * 	ref-name="WorkgroupLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="WorkgroupPermissions"
 * 	view-type="local"
 * 	ref-name="WorkgroupPermissionsLocal"
 * @ejb.ejb-ref 
 * 	ejb-name="User"
 * 	view-type="local"
 * 	ref-name="UserLocal"
 *
 * @jboss.resource-ref 
 * 	res-ref-name="jdbc/DefaultDS" 
 * 	jndi-name="java:/DefaultDS"
**/

public abstract class CollaborativeSessionAccessBean implements SessionBean {
	
	protected SessionContext ctx;
	private UserLocalHome userLocalHome;
	private OpmModelLocalHome opmModelLocalHome;
	private OpmModelPermissionsLocalHome opmModelPermissionsLocalHome;
	private WorkgroupLocalHome workgroupLocalHome;
	private WorkgroupPermissionsLocalHome workgroupPermissionsLocalHome;
	private CollaborativeSessionLocalHome collaborativeSessionLocalHome;
	private CollaborativeSessionPermissionsLocalHome collaborativeSessionPermissionsLocalHome;
	private RevisionLocalHome revisionLocalHome;
	private SecurityManager securityManager;
	
	private TopicConnection topicConnection = null;
	private Topic collaborativeSessionTopic = null;
	
	/**
	 * CollaborativeSessionAccessBean <code>ejbCreate</code> method.
	 * @throws CreateException if ejb creation fails. 
	 * 
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		try {
			userLocalHome = UserUtil.getLocalHome();
			opmModelLocalHome = OpmModelUtil.getLocalHome();
			opmModelPermissionsLocalHome = OpmModelPermissionsUtil.getLocalHome();
			workgroupLocalHome = WorkgroupUtil.getLocalHome();
			workgroupPermissionsLocalHome = WorkgroupPermissionsUtil.getLocalHome();
			collaborativeSessionLocalHome = CollaborativeSessionUtil.getLocalHome();
			collaborativeSessionPermissionsLocalHome = CollaborativeSessionPermissionsUtil.getLocalHome();
			revisionLocalHome = RevisionUtil.getLocalHome();
			securityManager = new SecurityManager();
			
			Context context = new InitialContext();
			TopicConnectionFactory topicConnectionFactory = null;
			collaborativeSessionTopic = (Topic) context.lookup("topic/collaborativeSessionTopic");
			topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
			topicConnection = topicConnectionFactory.createTopicConnection();
			
		} catch (NamingException nE) { nE.printStackTrace(); 
		} catch (JMSException jE) {jE.printStackTrace();}
		//TODO handle NamingException and JMSException for ejbCreate
	}
	
	/**
	 * Sets the session context 
	 * @param ctx the new context value
	 * 
	 * @ejb.method setSessionContext
	 */   
	public void setSessionContext(SessionContext ctx)  {this.ctx = ctx;}    
	
	/**
	 * Unsets the session context 
	 * 
	 * @ejb.method unsetSessionContext
	 */
	public void unsetSessionContext()  {this.ctx = null;}
		
	/**
	 * returns a collaborative session value object for the given collaborative session id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for the requested collaborative session.
	 * @return the collaborative session value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public CollaborativeSessionValue getCollaborativeSessionByPK(String sUserID, String sPassword, String collaborativeSessionID)
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		
		CollaborativeSessionValue csv = null;			   
		try {
			csv = (collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID)).getCollaborativeSessionValue();;								
		} catch (FinderException fE) {  
			throw new NoSuchSessionFault(collaborativeSessionID);
		} return csv; 
	}
		
	/**
	 * returns an enhanced collaborative session value object for the given collaborative session id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for the requested collaborative session.
	 * @return the enhanced collaborative session value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedCollaborativeSessionValue getEnhancedCollaborativeSessionByPK( 
		String sUserID, 
		String sPassword,
		String collaborativeSessionID)
	throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		
		EnhancedCollaborativeSessionValue ecsv = null;			   
		try {
			ecsv = (collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID)).getEnhancedCollaborativeSessionValue();;								
		} catch (FinderException fE) {  
			throw new NoSuchSessionFault(collaborativeSessionID);
		} return ecsv; 
	}
	/**
	 * returns a collaborative session file value object for the given collaborative session id.
	 * A <code>CollaborativeSessionFileValue</code> encapsulates collaborative session attributes and the OPM model file.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for the requested collaborative session.
	 * @return the collaborative session file value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	 public CollaborativeSessionFileValue getCollaborativeSessionFileByPK(
		 String sUserID,
		 String sPassword, 
		 String collaborativeSessionID)
		throws AuthenticationFailedFault, LookupFault
		{ 
			securityManager.Authenticate(sUserID, sPassword);
			
			CollaborativeSessionFileValue csfv = null;			   
			try {
				csfv = (collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID)).getCollaborativeSessionFileValue();;								
			} catch (FinderException fE) {  
				throw new NoSuchSessionFault(collaborativeSessionID);
			} return csfv; 
	 }	
	/**
	 * returns an enhanced collaborative session permissions value object for the given collaborative session permissions primary key.
	 * An enhanced collaborative session permissions value object encapsulates a corresponding collaborative session value object.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionPermissionsPK primary key for the requested collaborative session permissions.
	 * @return the enhanced collaborative session permissions value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested collaborative session permissions could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedCollaborativeSessionPermissionsValue getEnhancedCollaborativeSessionPermissionsByPK(
			String sUserID, 
			String sPassword, 
			CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		EnhancedCollaborativeSessionPermissionsValue ecpv = null;

		try {
			ecpv = (collaborativeSessionPermissionsLocalHome.findByPrimaryKey(collaborativeSessionPermissionsPK)).getEnhancedCollaborativeSessionPermissionsValue();											
		} catch (FinderException fE) {
				throw new PermissionLookupFault(collaborativeSessionPermissionsPK.getUserID());
		} return ecpv; 
	}

	/**
	 * sets the collaborative session permissions according to the editable collaborative session permissions value object.
	 * if the permissions already exist they will be updated, otherwise new permissions will be created.
	 * the method sends an UPDATE_USER_COLLABORATIVE_SESSION_PERMISSIONS message to the CollaborativeSessionTopic
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableCollaborativeSessionPermissionsValue an editable collaborative session permissions value object 
	 * 	encapsulating the new permissions.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 *  
	 * @ejb.interface-method
	 *	view-type="both"
	**/
	public void setCollaborativeSessionPermissions(
		String sUserID,
		String sPassword, 
		EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue)
	throws AuthenticationFailedFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		CollaborativeSessionPermissionsLocal cspl = null;
		try {
			cspl = collaborativeSessionPermissionsLocalHome.findByPrimaryKey(editableCollaborativeSessionPermissionsValue.getPrimaryKey());
			cspl.setCollaborativeSessionPermissions(editableCollaborativeSessionPermissionsValue);
		} catch (FinderException fE){
			try {
				cspl = collaborativeSessionPermissionsLocalHome.create(editableCollaborativeSessionPermissionsValue);
			} catch (CreateException cE) { 
				//TODO What if the if a forign key constraint fails?
			}
		} finally {
		    try {
		        //TODO why is the editable permission object not sent?
		        publishMessageToTopic(
		                sUserID, 
		                editableCollaborativeSessionPermissionsValue.getCollaborativeSessionID(),
		                SystemMessages.UPDATE_USER_COLLABORATIVE_SESSION_PERMISSIONS, null);
		    } catch (LookupFault lookupFault) {
		        //This should never have happned, a forign key constrain should have failed for the permissions
		        lookupFault.printStackTrace();
		    }
		}
	}
	
	/**
	 * Marks the user as token holder for the collaborative session.
	 * Sends a USER_IS_TOKENHOLDER message to the CollaborativeSessionTopic.
	 * @param sUserID workgroup id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for the collaborative session whos token was requested.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws TokenNotAvailableFault if the token is held by a diffrent user.
	 * @throws NoSuchSessionFault if the collaborative session who's token was requested could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/	
	public void requestToken( String sUserID, String sPassword, String collaborativeSessionID)
		throws AuthenticationFailedFault, LookupFault, TokenFault
	{
		securityManager.Authenticate(sUserID, sPassword);	
		try {
			CollaborativeSessionLocal csl = collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID);
			if ( csl.getTokenHolderID().equals(PermissionFlags.SERVER_USERID)){
				csl.setTokenHolderID(sUserID);
				//send meesage to session participants about the new token holder.
				publishMessageToTopic(sUserID,collaborativeSessionID,SystemMessages.USER_IS_TOKENHOLDER, null);
			} else {
				throw new TokenNotAvailableFault(collaborativeSessionID);
			}
		} catch (FinderException fE) { 
				throw new NoSuchSessionFault(collaborativeSessionID);
		} 
	}

	
	 /**
	  * Resets the token holder attribute for the collaborative session to <code>null_user</code>, 
	  * representing the fact that the token is now available.
	  * Sends a USER_RETURNED_TOKEN message to the CollaborativeSessionTopic.
	  * @param sUserID workgroup id for the user performing the action.
	  * @param sPassword password for the user performing the action.
	  * @param collaborativeSessionID primary key for the collaborative session whos token was returned.
	  * @throws AuthenticationFailedFault if user authentication failed.
	  * @throws NotTheTokenHolderFault if the user was not the collaborative session's token holder.
	  * @throws NoSuchSessionFault if the collaborative session who's token was returned could not be found.
	  * 
	  * @ejb.interface-method
	  *	view-type="both" 
	 **/	
	 public void returnToken( String sUserID, String sPassword, String collaborativeSessionID)
	 	throws AuthenticationFailedFault, LookupFault, TokenFault
	{
	 	securityManager.Authenticate(sUserID, sPassword);
		 try {
			 CollaborativeSessionLocal csl = collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID);
			 if ( csl.getTokenHolderID().equals(sUserID) ){
				 csl.setTokenHolderID(PermissionFlags.SERVER_USERID);
				//send meesage to session participants about the token new status.
				publishMessageToTopic(sUserID,collaborativeSessionID,SystemMessages.USER_RETURNED_TOKEN, null);		
			 } else {
			 	throw new NotTheTokenHolderFault(sUserID);
			 }
		 } catch (FinderException fE) { 
		 	throw new NoSuchSessionFault(collaborativeSessionID);
		 }
	 }

	/**
	 * updates the collaborative session entry with the given updatable collaborative session value object.
	 * the method sends an UPDATE_COLLABORATIVE_SESSION_DETAILS message to the CollaborativeSessionTopic 
	 * and updates the <code>lastUpdate</code> attribute for the collaborative session.
	 * @param sUserID workgroup id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param updatableCollaborativeSessionValue updatable collaborative session value encapsulating the information to be updated.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/	
	public void updateCollaborativeSession(
		String sUserID,
		String sPassword,		
		UpdatableCollaborativeSessionValue updatableCollaborativeSessionValue)
	throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);		
		try {
			CollaborativeSessionLocal csl = collaborativeSessionLocalHome.findByPrimaryKey(updatableCollaborativeSessionValue.getCollaborativeSessionID());								
			csl.setUpdatableCollaborativeSessionValue(updatableCollaborativeSessionValue);
			 //	Update timestamp for last entry change
			csl.setLastUpdate(new Date());	
			publishMessageToTopic(
				   sUserID, 
				   updatableCollaborativeSessionValue.getCollaborativeSessionID(), 
				   SystemMessages.UPDATE_COLLABORATIVE_SESSION_DETAILS, null);
		} catch (FinderException fE) {
				throw new NoSuchSessionFault(updatableCollaborativeSessionValue.getCollaborativeSessionID());
		}
	}
	
	 /**
	  * Updates the collaborative session entry with the given CollaborativeSessionFileValue object.
	  * Similar to the <code>updateCollaborativeSession</code> method but allows updating the OPM model file of the seesion.
	  * the method sends a PUBLISH_UPDATED_FILE message to the CollaborativeSessionTopic 
	  * and updates the <code>lastUpdate</code> attribute for the collaborative session.
	  * @param sUserID workgroup id for the user performing the action.
	  * @param sPassword password for the user performing the action.
	  * @param collaborativeSessionFileValue collaborative session file value encapsulating the information to be updated.
	  * @throws AuthenticationFailedFault if user authentication failed.
	  * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	  * 
	  * @ejb.interface-method
	  *	view-type="both" 
	 **/
	 public void updateCollaborativeSessionFile(
		 String sUserID,
		 String sPassword, 
		 CollaborativeSessionFileValue collaborativeSessionFileValue)
	throws AuthenticationFailedFault, LookupFault
	 {
	 	securityManager.Authenticate(sUserID, sPassword);
		 try {
		 	CollaborativeSessionLocal csl = 
		 		collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionFileValue.getCollaborativeSessionID());
		 	csl.setCollaborativeSessionFileValue(collaborativeSessionFileValue);
			//	Update timestamp for last entry change
			csl.setLastUpdate(new Date());	
		 	publishMessageToTopic(
		 		sUserID, 
			 	collaborativeSessionFileValue.getCollaborativeSessionID(), 
			 	SystemMessages.PUBLISH_UPDATED_FILE,
			 	collaborativeSessionFileValue );
		 } catch (FinderException fE) {
			throw new NoSuchSessionFault(collaborativeSessionFileValue.getCollaborativeSessionID());
		 }
	 }
	 
	/**
	 * marks the requested collaborative session as disabled.
	 * If <code>recursive</code> is set to <code>true</code> all logged in session participants 
	 * will be logged out of the session. If set to <code>false</code> the procedure will check
	 * that no participants are logged into the collaborative session before disabling it. 
	 * if logged in participants are are found, the session will not be disabled 
	 * and a <code>UsersStillLoggedInFault</code> will be raised.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for collaborative session to be disabled.
	 * @param recursive a boolean denoting if simple or a recursive disable should be preformed.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * @throws UsersStillLoggedInFault if <code>recursive</code> is set to true 
	 * 	and the collaborative sessions has logged in participants.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void disableCollaborativeSession( String sUserID, String sPassword, String collaborativeSessionID, boolean recursive)
		throws AuthenticationFailedFault, LookupFault, RecursiveDisableFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		try {
			CollaborativeSessionLocal csl = collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID);
			Collection cspls = csl.getCollaborativeSessionPermissions();
			Iterator iterate = cspls.iterator();								
			while(iterate.hasNext()) {				   	
				CollaborativeSessionPermissionsLocal cspl = (CollaborativeSessionPermissionsLocal) iterate.next();
				if ( ( cspl.getAccessControl().intValue() & PermissionFlags.LOGGEDIN.intValue() ) == PermissionFlags.LOGGEDIN.intValue() ) {
					if (recursive){
						//TODO implement recursive logout for users
					} else {
						throw new UsersStillLoggedInFault(collaborativeSessionID);
					}
				}
			}
			csl.setEnabled(new Boolean(false));
		} catch (FinderException fE) { 
			throw new NoSuchSessionFault(collaborativeSessionID);
		}
	}
	
	/**
	 * marks the requested collaborative session as enabled.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for collaborative session to be disabled.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void enableCollaborativeSession( String sUserID, String sPassword, String collaborativeSessionID)
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		try {
			(collaborativeSessionLocalHome.findByPrimaryKey(collaborativeSessionID)).setEnabled(new Boolean(true));
		} catch (FinderException fE) {
			throw new NoSuchSessionFault(collaborativeSessionID);
		}
	}
	
	/**
	 * logges the client into the session.
	 * This is a diffrent loggin then the one for system login, 
	 * the flag is stored in the CollaborativeSessionPermissions entry for the user.
	 * the method sends a USER_LOGGED_INTO_SESSION message to the CollaborativeSessionTopic.
	 * note: in future tokenless session implementations the session login flag will not be presistent. 
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for collaborative session to be logged into.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found. 
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public void sessionLoginUser ( String sUserID, String sPassword, String collaborativeSessionID) 
		throws AuthenticationFailedFault, LookupFault
	 { 
		securityManager.Authenticate(sUserID, sPassword);
		CollaborativeSessionPermissionsPK pk = new CollaborativeSessionPermissionsPK(collaborativeSessionID, sUserID); 
		try {
		    //TODO check session is not Terminated
			CollaborativeSessionPermissionsLocal cspl = collaborativeSessionPermissionsLocalHome.findByPrimaryKey(pk);
			Integer accessControl = cspl.getAccessControl();
			if ( 0 == (PermissionFlags.LOGGEDIN.intValue() & accessControl.intValue())) {
			    //means he is not logged in, otherwise throw exception
			    cspl.setAccessControl(new Integer(accessControl.intValue() + PermissionFlags.LOGGEDIN.intValue()));
			    //send meesage to session participants about the new user.
			    publishMessageToTopic(sUserID,collaborativeSessionID,SystemMessages.USER_LOGGED_INTO_SESSION, null);
			} else {
				throw new UserAlreadyLoggedInFault(sUserID);
			}
		 } catch (FinderException fE) {
				 throw new NoSuchSessionFault(collaborativeSessionID);
		 }
	 } 
	
	/**
	 * logges the client out of the session.
	 * the method sends a USER_LOGGED_OUT_OF_SESSION message to the CollaborativeSessionTopic.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for collaborative session to be logged into.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found. 
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public void sessionLogoutUser( String sUserID, String sPassword, String collaborativeSessionID) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		CollaborativeSessionPermissionsPK pk = new CollaborativeSessionPermissionsPK(collaborativeSessionID, sUserID);
		try {
			CollaborativeSessionPermissionsLocal cspl = collaborativeSessionPermissionsLocalHome.findByPrimaryKey(pk);
			Integer accessControl = cspl.getAccessControl();
			if (0 != (PermissionFlags.LOGGEDIN.intValue() & accessControl.intValue())) {
				//means he is logged in otherwise throw exception
				cspl.setAccessControl(new Integer(accessControl.intValue() - PermissionFlags.LOGGEDIN.intValue()));
				//send message to session participants about the user's logout.
				publishMessageToTopic(sUserID,collaborativeSessionID,SystemMessages.USER_LOGGED_OUT_OF_SESSION, null);
			} else {
				throw new UserNotLoggedInFault(sUserID);	
			}
		} catch (FinderException fE) {
				throw new NoSuchSessionFault(collaborativeSessionID);
		}
	}

	/**
	 * Commits the collaborative session.
	 * After commiting, the collaborative session is terminated and the session's <code>Terminated</code> flag 
	 * is set to <code>true</code>. Commiting will seeks the latest revision , suppose it's version number 
	 * is <code>[x].[y].[z]</code> (mask: [major revision].[minor revision].[build]) and generate a new revision. 
	 * If <code>increaseMajor</code> is <code>true</code> then new revision number will be: <code>[x+1].0.[z+1]</code>, 
	 * If <code>increaseMajor</code> is <code>false</code> then the new revision number will be: <code>[x].[y+1].[z+1]</code>.
	 * if now revisions are currently available for the OPM model the new revision's number will be: <code>1.0.1</code>.
	 * the method sends a USER_COMMITED_SESSION message to the CollaborativeSessionTopic.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param collaborativeSessionID primary key for collaborative session to be commited.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchSessionFault if the requested collaborative session could not be found. 
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	 */
	public EditableRevisionValue commitCollaborativeSession(
		String sUserID,
		String sPassword,
		String sessionID,
		EditableRevisionValue editableRevisionValue,
		Boolean increaseMajor) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		//TODO check who is logged in?
		try {
			Collection revisions = revisionLocalHome.findByOpmModelID(editableRevisionValue.getOpmModelID());		
			if (revisions.isEmpty()) {
				throw new FinderException();
			}
			//TODO recode revision search to use build numbers
			Iterator iterate = revisions.iterator();								
			RevisionLocal maxrev = (RevisionLocal) iterate.next();
			while(iterate.hasNext()) {				   	
				RevisionLocal rl = (RevisionLocal) iterate.next();
				if(rl.getMajorRevision().intValue() > maxrev.getMajorRevision().intValue()){
					maxrev = rl;
				} else {
					if (	( rl.getMajorRevision().intValue() == maxrev.getMajorRevision().intValue() ) 
						&&	( rl.getMinorRevision().intValue() >  maxrev.getMinorRevision().intValue() ) 
					) maxrev = rl;
				}
			}					
			if (increaseMajor.booleanValue() == true) {	
				editableRevisionValue.setMajorRevision(new Integer(maxrev.getMajorRevision().intValue()+1));
				editableRevisionValue.setMinorRevision(new Integer(0));
				editableRevisionValue.setBuild(new Integer(maxrev.getBuild().intValue() + 1 )); 
			} else {
				editableRevisionValue.setMajorRevision(maxrev.getMajorRevision());
				editableRevisionValue.setMinorRevision(new Integer(maxrev.getMinorRevision().intValue()+1));
				editableRevisionValue.setBuild(new Integer(maxrev.getBuild().intValue() + 1 )); 
			}
		} catch (FinderException fE){ // First Commit
			editableRevisionValue.setMajorRevision(new Integer(1));
			editableRevisionValue.setMinorRevision(new Integer(0));
			editableRevisionValue.setBuild(new Integer(1));
		}
		
		try {
			(collaborativeSessionLocalHome.findByPrimaryKey(sessionID)).setTerminated(new Boolean(true));
			editableRevisionValue.setRevisionID(RevisionUtil.generateGUID(editableRevisionValue));
			revisionLocalHome.create(editableRevisionValue);
			//send meesage to session participants about the commit action.
			publishMessageToTopic(sUserID,sessionID,SystemMessages.USER_COMMITED_SESSION, null);
		} catch (CreateException cE){
			//TODO this should never happen
		} catch (FinderException fE){
			throw new NoSuchSessionFault(sessionID);
		} return editableRevisionValue;	
	}

	/**
	 * returns an array list of CollaborativeSessionValue objects for all collaborative sessions in the system.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return a list of all collaborative sessions.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSessionsFoundFault if no collaborative sessions were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getAllCollaborativeSessions( String sUserID, String sPassword ) 
		throws AuthenticationFailedFault, LookupFault
	{	
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList sessionsList = new ArrayList();
		try {
			Collection sessions = collaborativeSessionLocalHome.findAll();
			Iterator iterate = sessions.iterator();								
			while(iterate.hasNext()) {				   	
				sessionsList.add(((CollaborativeSessionLocal) iterate.next()).getCollaborativeSessionValue());
			}	
		} catch (FinderException fE){
				throw new NoSessionsFoundFault();
		} return sessionsList;
	}
	
	/**
	 * Creates a new collaborative session entry based on the information in the EditableCollaborativeSessionValue object.
	 * An EditableCollaborativeSessionValue object contains all collaborative session attributes that can be initialized during creation.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableCollaborativeSessionValue the editable collaborative session value object containing the editable collaborative session attributes.
	 * @return GUID primary key for the new OPM model.
	 * @throws SessionAlreadyExistFault if a collaborative session with the requested name already exists.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public String createCollaborativeSession(
		String sUserID, 
		String sPassword, 
		EditableCollaborativeSessionValue editableCollaborativeSessionValue)
	throws AuthenticationFailedFault, CreationFault
	{
		securityManager.Authenticate(sUserID, sPassword);

		try {
			editableCollaborativeSessionValue.setCollaborativeSessionID(
				CollaborativeSessionUtil.generateGUID(editableCollaborativeSessionValue)
			);
			collaborativeSessionLocalHome.create(editableCollaborativeSessionValue);
			EditableCollaborativeSessionPermissionsValue ecspv = new EditableCollaborativeSessionPermissionsValue(
				editableCollaborativeSessionValue.getCollaborativeSessionID(),
				sUserID,
				PermissionFlags.CREATOR
			);
			collaborativeSessionPermissionsLocalHome.create(ecspv);
			//	TODO Create permissions for all father elements
		} catch (CreateException cE) {
			throw new SessionAlreadyExistFault(editableCollaborativeSessionValue.getCollaborativeSessionName());
		} return editableCollaborativeSessionValue.getCollaborativeSessionID();
		
	}
	
	/**
	 * Privat method encapsulating the message delivery procedure.
	 * @param userID primary key for the user who's action published the message.
	 * @param sessionID primary key for session the message is sent to.
	 * @param systemMessageCode integer code for the message being sent, <code>org.objectprocess.config.SystemMessages</code>
	 * contains the mappging for the message codes.
	 * @param messageObject an <code>object</code> to attache to the message, can be <code>null<code>. 
	 * @throws NoSessionsFoundFault if no collaborative sessions were found.
	 */
	private void publishMessageToTopic(
		String userID, 
		String sessionID,
		int systemMessageCode,
		Serializable messageObject) 
		throws LookupFault
	{	TopicSession ts = null;	
		try {
			CollaborativeSessionLocal csl  =   collaborativeSessionLocalHome.findByPrimaryKey(sessionID);								
				
			ts = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
			TopicPublisher tp = ts.createPublisher(collaborativeSessionTopic);
			
			ObjectMessage message = ts.createObjectMessage();
			message.setIntProperty("SystemMessageType", systemMessageCode);
			message.setStringProperty("UserID", userID);
			message.setStringProperty("SessionID",sessionID);
			message.setObject(messageObject);				
			tp.publish(message);
		} catch (JMSException jmsE) { 
			//TODO Handle JMSExceptions individualy
			jmsE.printStackTrace();
		} catch (FinderException fE) {
			throw new NoSuchSessionFault(sessionID);
		} finally {
			if (ts != null) {
				try { ts.close();} 
					catch (JMSException jmsE) {jmsE.printStackTrace();
				}
			}
		}
	}
}

