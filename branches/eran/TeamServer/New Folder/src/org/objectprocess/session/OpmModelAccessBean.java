/*
 * Created on 13/11/2003
 */
package org.objectprocess.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.naming.NamingException;

import org.objectprocess.cmp.CollaborativeSessionLocal;
import org.objectprocess.cmp.EditableOpmModelPermissionsValue;
import org.objectprocess.cmp.EditableOpmModelValue;
import org.objectprocess.cmp.EnhancedOpmModelPermissionsValue;
import org.objectprocess.cmp.EnhancedOpmModelValue;
import org.objectprocess.cmp.FullRevisionValue;
import org.objectprocess.cmp.MetaRevisionValue;
import org.objectprocess.cmp.OpmModelLocal;
import org.objectprocess.cmp.OpmModelLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsLocal;
import org.objectprocess.cmp.OpmModelPermissionsLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsPK;
import org.objectprocess.cmp.OpmModelPermissionsUtil;
import org.objectprocess.cmp.OpmModelRevisionsValue;
import org.objectprocess.cmp.OpmModelUtil;
import org.objectprocess.cmp.OpmModelValue;
import org.objectprocess.cmp.RevisionLocal;
import org.objectprocess.cmp.RevisionLocalHome;
import org.objectprocess.cmp.RevisionUtil;
import org.objectprocess.cmp.UpdatableOpmModelValue;
import org.objectprocess.cmp.UserLocalHome;
import org.objectprocess.cmp.UserUtil;
import org.objectprocess.cmp.WorkgroupLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsUtil;
import org.objectprocess.cmp.WorkgroupUtil;
import org.objectprocess.config.PermissionFlags;
import org.objectprocess.fault.authenticate.AuthenticationFailedFault;
import org.objectprocess.fault.create.CreationFault;
import org.objectprocess.fault.create.OpmModelAlreadyExistFault;
import org.objectprocess.fault.lookup.LookupFault;
import org.objectprocess.fault.lookup.NoOpmModelsFoundFault;
import org.objectprocess.fault.lookup.NoRevisionsFoundFault;
import org.objectprocess.fault.lookup.NoSessionsFoundFault;
import org.objectprocess.fault.lookup.NoSuchOpmModelFault;
import org.objectprocess.fault.lookup.OpmModelLookupFault;
import org.objectprocess.fault.lookup.PermissionLookupFault;
import org.objectprocess.fault.lookup.RevisionLookupFault;
import org.objectprocess.fault.recursiveDisable.ActiveSessionFoundFault;
import org.objectprocess.fault.recursiveDisable.RecursiveDisableFault;
import org.objectprocess.security.SecurityManager;

/**
 * Main implementation of the <code>OpmModelAccess</code> session ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>OpmModelAccess</code> encapsulates all OPM Model related methods invoked by clients.
 * 
 * @ejb.bean 
 *	name="OpmModelAccess"
 *	jndi-name="OpmModelAccessBean"
 *	type="Stateless" 
 * 
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
 * @ejb.resource-ref 
 *	res-ref-name="jdbc/DefaultDS"
 *	res-type="javax.sql.Datasource"
 *	res-auth="Container"
 * 
 * @jboss.resource-ref 
 * 	res-ref-name="jdbc/DefaultDS" 
 * 	jndi-name="java:/DefaultDS"
**/

public abstract class OpmModelAccessBean implements SessionBean {
	
	
	protected SessionContext ctx;
	private UserLocalHome userLocalHome;
	private OpmModelLocalHome opmModelLocalHome;
	private OpmModelPermissionsLocalHome opmModelPermissionsLocalHome;
	private WorkgroupLocalHome workgroupLocalHome;
	private WorkgroupPermissionsLocalHome workgroupPermissionsLocalHome;
	private RevisionLocalHome revisionLocalHome;
	private SecurityManager securityManager;
	
	/**
	 * OpmModelAccessBean <code>ejbCreate</code> method.
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
			revisionLocalHome = RevisionUtil.getLocalHome();
			securityManager = new SecurityManager();
		} catch (NamingException nE) { nE.printStackTrace(); }
		//TODO handle NamingException for ejbCreate
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
	 * returns an OPM model value object for the given OPM model id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelID primary key for the requested OPM model.
	 * @return the OPM model value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws OpmModelLookupFault if the requested OPM model could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public OpmModelValue getOpmModelByPK( String sUserID, String sPassword, String opmModelID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		OpmModelValue omv = null;
		   
		try {
			omv  =   (opmModelLocalHome.findByPrimaryKey(opmModelID)).getOpmModelValue();											
		} catch (FinderException fE) {
			throw new OpmModelLookupFault(opmModelID);
		} return omv; 
	}
	
	/**
	 * returns an enhanced OPM model value object for the given OPM model id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelID primary key for the requested OPM model.
	 * @return the enhanced OPM model value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws OpmModelLookupFault if the requested OPM model could not be found.
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedOpmModelValue getEnhancedOpmModelByPK( String sUserID, String sPassword, String opmModelID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		EnhancedOpmModelValue eomv = null;
		   
		try {
			eomv = (opmModelLocalHome.findByPrimaryKey(opmModelID)).getEnhancedOpmModelValue();
		} catch (FinderException fE) {
			throw new OpmModelLookupFault(opmModelID);
		} return eomv; 
	}
	
	/**
	 * returns an OPM model revision value object for the given OPM model id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelID primary key for the requested OPM model.
	 * @return the OPM model revision value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws OpmModelLookupFault if the requested OPM model could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public OpmModelRevisionsValue getOpmModelRevisionsByPK( String sUserID, String sPassword, String opmModelID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		OpmModelRevisionsValue omrv = null;
		   
		try {
			omrv = (opmModelLocalHome.findByPrimaryKey(opmModelID)).getOpmModelRevisionsValue();											
		} catch (FinderException fE) {
			throw new OpmModelLookupFault(opmModelID);
		} return omrv; 
	}
	
	/**
	 * returns an array list of <code>MetaRevisionValue</code> objects OPM model.
	 * note: method is replaced by <code>getOpmModelRevisionsByPK</code>.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all OPM models.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoRevisionsFoundFault if no revisions were found.
	 * @deprecated
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getAllRevisions(String sUserID, String sPassword, String opmModelID) 
		throws AuthenticationFailedFault, LookupFault
	{	
	    //TODO remove this method. ask Dizza.
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList revisionsList = new ArrayList();
		try {
			Collection revisions = revisionLocalHome.findByOpmModelID(opmModelID);
			Iterator iterate = revisions.iterator();								
			while(iterate.hasNext()) {				   	
				revisionsList.add(((RevisionLocal) iterate.next()).getMetaRevisionValue());
			}			
		} catch (FinderException fE){
			throw new NoRevisionsFoundFault(opmModelID);
		} return revisionsList;
	}
	
	/**
	 * returns an array list of OPM moodel value objects for all OPM models in the system.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all OPM models.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoOpmModelsFoundFault if no OPM models were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getAllOpmModels( String sUserID, String sPassword ) 
		throws AuthenticationFailedFault, LookupFault
	{	
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList omList = new ArrayList();
		try {
			Collection oms = opmModelLocalHome.findAll();
			Iterator iterate = oms.iterator();								
			while(iterate.hasNext()) {				   	
				omList.add(((OpmModelLocal) iterate.next()).getOpmModelValue());
			}	
		} catch (FinderException fE){
				throw new NoOpmModelsFoundFault();
		} return omList;
	}

	/**
	 * returns a meta revision value object for the given revision id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param revisionID primary key for the requested revision.
	 * @return the meta revision value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws RevisionLookupFault if the requested revision could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public MetaRevisionValue getMetaRevisionByPK(String sUserID, String sPassword, String revisionID) 
		throws AuthenticationFailedFault, LookupFault
	{		
		securityManager.Authenticate(sUserID, sPassword);
		MetaRevisionValue mrv = null;
		
		try {
			mrv = (revisionLocalHome.findByPrimaryKey(revisionID)).getMetaRevisionValue();  	
		} catch (FinderException fE) {
			throw new RevisionLookupFault(revisionID);
		} return mrv; 
	}	
	/**
	 * returns a full revision value object for the given revision id.
	 * Full revision value object encapsulates the OPM model revision document and its meta information.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param revisionID primary key for the requested revision.
	 * @return the full revision value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws RevisionLookupFault if the requested revision could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public FullRevisionValue getRevisionByPK(String sUserID, String sPassword, String revisionID) 
	throws AuthenticationFailedFault, LookupFault
	{		
		securityManager.Authenticate(sUserID, sPassword);
		FullRevisionValue frv = null;
		
		try {
			frv = (revisionLocalHome.findByPrimaryKey(revisionID)).getFullRevisionValue();  	
		} catch (FinderException fE) {
			throw new RevisionLookupFault(revisionID);
		} return frv; 
	}	
	
	/**
	 * returns an array list of collaborative session value objects for the specified OPM model.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all collaborative sessions.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSessionsFoundFault if no collaborative sessions were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getCollaborativeSessionsForOpmModel( String sUserID, String sPassword, String opmModelID ) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList sessionList = new ArrayList();
		try {
			OpmModelLocal oml = opmModelLocalHome.findByPrimaryKey(opmModelID);
			Collection sessions = oml.getCollaborativeSessions();
			Iterator iterate = sessions.iterator();								
			while(iterate.hasNext()) {
			    sessionList.add(((CollaborativeSessionLocal) iterate.next()).getCollaborativeSessionValue());
			}	
		} catch (FinderException fE){
			throw new NoSessionsFoundFault();
		} return sessionList;
	}
	
	/**
	 * Returns an array list of meta revision value objects for all revisions with build numbers greater then the given revision.
	 * The method <code>getPreCommit</code> should be used in conjunction with 
	 * <code>CollaborativeSessionAccessBean.commitCollaborativeSession</code> and is used to attain 
	 * meta information for all revisions that were committed after the collaborative session's base revision 
	 * was committed. clients can then choose to merge with any of those revisions before they commit the new revision.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param revisionID primary key for the revision.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getPreCommit( String sUserID, String sPassword, String revisionID) 
		throws AuthenticationFailedFault, LookupFault
	{		
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList revisionsList = new ArrayList();

		try {
			RevisionLocal cr = revisionLocalHome.findByPrimaryKey(revisionID);
			Collection revisions = revisionLocalHome.findByCurrentRevision(cr.getBuild(),cr.getOpmModelID());
			Iterator iterate = revisions.iterator();								
			while(iterate.hasNext()) {				   	
				MetaRevisionValue mrv = ((RevisionLocal) iterate.next()).getMetaRevisionValue();
				revisionsList.add(mrv);
			}
		} catch (FinderException fE){
			throw new RevisionLookupFault(revisionID);
		} return revisionsList;
	}

	/**
	 * returns an enhanced OPM model permissions value object for the given OPM model permissions primary key.
	 * An enhanced OPM model permissions value object include a corresponding OPM model value object.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelPermissionsPK primary key for the requested OPM model permissions.
	 * @return the enhanced OPM model permissions value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested OPM model permissions could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedOpmModelPermissionsValue getEnhancedOpmModelPermissionsByPK(
			String sUserID, 
			String sPassword, 
			OpmModelPermissionsPK opmModelPermissionsPK) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		EnhancedOpmModelPermissionsValue eopv = null;

		try {
			eopv = (opmModelPermissionsLocalHome.findByPrimaryKey(opmModelPermissionsPK)).getEnhancedOpmModelPermissionsValue();											
		} catch (FinderException fE) {
				throw new PermissionLookupFault(opmModelPermissionsPK.getUserID());
		} return eopv; 
	}

	/**
	 * sets the OPM model permissions according to the editable OPM model permissions value object.
	 * if the permissions already exist they will be updated, otherwise new permissions will be created.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableOpmModelPermissionsValue an editable OPM model permissions value object containing the new permissions.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	**/
	public void setOpmModelPermissions(
		String sUserID, 
		String sPassword, 
		EditableOpmModelPermissionsValue editableOpmModelPermissionsValue) 
		throws AuthenticationFailedFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		OpmModelPermissionsLocal ompl = null;
					
		try {
			ompl = opmModelPermissionsLocalHome.findByPrimaryKey(editableOpmModelPermissionsValue.getPrimaryKey());
			ompl.setOpmModelPermissions(editableOpmModelPermissionsValue);
		} catch (FinderException fe) {
			try {
				ompl = opmModelPermissionsLocalHome.create(editableOpmModelPermissionsValue);
			} catch (CreateException cE) { 
				//TODO What if the if a forign key constraint fails?
			}
		}
	}
		
	/**
	 * updates the OPM model entry with the given updatable OPM model value object.
	 * @param sUserID workgroup id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param updatableOpmModelValue updatable OPM model value encapsulating the information to be updated.
	 * @throws AuthenticationFailedFault if OPM model authentication failed.
	 * @throws NoSuchOpmModelFault if the requested OPM model could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	**/	
	public void updateOpmModel(
		String sUserID, 
		String sPassword,
		UpdatableOpmModelValue updatableOpmModelValue)
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		try {
			OpmModelLocal oml = opmModelLocalHome.findByPrimaryKey(updatableOpmModelValue.getOpmModelID());								
			oml.setUpdatableOpmModelValue(updatableOpmModelValue);			
		} catch (FinderException fE) {
			throw new NoSuchOpmModelFault(updatableOpmModelValue.getOpmModelID());
		}
	}

	/**
	 * marks the requested OPM model as disabled.
	 * If <code>recursive</code> is set to <code>true</code> all collaborative sessions for the
	 * OPM model will recursively be disabled. If set to <code>false</code> the procedure will check
	 * that all collaborative sessions in the OPM model are disabled before disabling the OPM model. 
	 * if enabled collaborative sessions are found the OPM model will not be disabled 
	 * and a <code>RecursiveDisableFault</code> will be raised.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelID primary key for OPM model to be disabled.
	 * @param recursive a boolean denoting if simple or a recursive disable should be preformed.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchOpmModelFault if the requested OPM model could not be found.
	 * @throws RecursiveDisableFault if <code>recursive</code> is set to true 
	 * 	and enabled collaborative sessions are found for the OPM model.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void disableOpmModel( String sUserID, String sPassword, String opmModelID, boolean recursive) 
		throws AuthenticationFailedFault, LookupFault, RecursiveDisableFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		CollaborativeSessionLocal collaborativeSessionLocal = null;
		try {
			OpmModelLocal oml = opmModelLocalHome.findByPrimaryKey(opmModelID);
			Collection csls = oml.getCollaborativeSessions();
			Iterator iterate = csls.iterator();								
			while(iterate.hasNext()) {				   	
				CollaborativeSessionLocal csl = (CollaborativeSessionLocal) iterate.next();
				if ( csl.getEnabled().booleanValue() == true )
					throw new ActiveSessionFoundFault(csl.getCollaborativeSessionID());
					//TODO Implement recursive disable
			}
			oml.setEnabled(new Boolean(false));
		} catch (FinderException fE) {
			throw new NoSuchOpmModelFault(opmModelID);
		}
	}
	
	/**
	 * marks the requested OPM model as enabled.
	 * If <code>recursive</code> is set to <code>true</code> all collaborative sessions for the OPM model
	 * will recursively be enabled. If set to <code>false</code> only the requested OPM model will be enabled.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param opmModelID primary key for OPM model to be disabled.
	 * @param recursive a boolean denoting if simple or a recursive enable should be preformed.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchOpmModelFault if the requested OPM model could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void enableOpmModel(String sUserID, String sPassword, String opmModelID, boolean recursive) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		
		try {
			OpmModelLocal opl = opmModelLocalHome.findByPrimaryKey(opmModelID);
			opl.setEnabled(new Boolean(true));
		} catch (FinderException fE) {
			throw new NoSuchOpmModelFault(opmModelID);
		}
	}
	/**
	 * creates a new OPM model entry based on the information in the editable OPM model value object.
	 * an editable OPM model value object contains all OPM model attributes that can be initialized during creation.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableOpmModelValue the editable OPM model value object containing the editable OPM model attributes.
	 * @return GUID primary key for the new OPM model.
	 * @throws OpmModelAlreadyExistFault if an OPM model with the requested name already exists.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public String createOpmModel(
		String sUserID, 
		String sPassword, 
		EditableOpmModelValue editableOpmModelValue) 
		throws AuthenticationFailedFault, CreationFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		
		try {
			editableOpmModelValue.setOpmModelID(OpmModelUtil.generateGUID(editableOpmModelValue));
			opmModelLocalHome.create(editableOpmModelValue);
			EditableOpmModelPermissionsValue eopv = new EditableOpmModelPermissionsValue(
					editableOpmModelValue.getOpmModelID(),
					sUserID,
					PermissionFlags.CREATOR);
			opmModelPermissionsLocalHome.create(eopv);
			//TODO Create Permissions for Workgroup owner
		} catch (CreateException cE) {
			throw new OpmModelAlreadyExistFault(editableOpmModelValue.getOpmModelName());
		} return editableOpmModelValue.getOpmModelID();
	}
}

