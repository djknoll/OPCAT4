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

import org.objectprocess.cmp.EditableWorkgroupPermissionsValue;
import org.objectprocess.cmp.EditableWorkgroupValue;
import org.objectprocess.cmp.EnhancedWorkgroupPermissionsValue;
import org.objectprocess.cmp.EnhancedWorkgroupValue;
import org.objectprocess.cmp.OpmModelLocal;
import org.objectprocess.cmp.UpdatableWorkgroupValue;
import org.objectprocess.cmp.UserLocalHome;
import org.objectprocess.cmp.UserUtil;
import org.objectprocess.cmp.WorkgroupLocal;
import org.objectprocess.cmp.WorkgroupLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsLocal;
import org.objectprocess.cmp.WorkgroupPermissionsLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsPK;
import org.objectprocess.cmp.WorkgroupPermissionsUtil;
import org.objectprocess.cmp.WorkgroupUtil;
import org.objectprocess.cmp.WorkgroupValue;
import org.objectprocess.config.PermissionFlags;
import org.objectprocess.fault.authenticate.AuthenticationFailedFault;
import org.objectprocess.fault.create.CreationFault;
import org.objectprocess.fault.create.WorkgroupAlreadyExistFault;
import org.objectprocess.fault.lookup.LookupFault;
import org.objectprocess.fault.lookup.NoOpmModelsFoundFault;
import org.objectprocess.fault.lookup.NoSuchWorkgroupFault;
import org.objectprocess.fault.lookup.NoWorkgroupsFoundFault;
import org.objectprocess.fault.lookup.PermissionLookupFault;
import org.objectprocess.fault.lookup.WorkgroupLookupFault;
import org.objectprocess.fault.recursiveDisable.ActiveOpmModelFoundFault;
import org.objectprocess.fault.recursiveDisable.RecursiveDisableFault;
import org.objectprocess.security.SecurityManager;

/**
 * Main implementation of the <code>WorkgroupAccess</code> session ejb.
 * xDoclet tags generate all required interfaces and deploymet decriptors.
 * <code>WorkgroupAccess</code> encapsulates all workgroup related methods invoked by clients.
 * 
 * @ejb.bean 
 *	name="WorkgroupAccess"
 *	jndi-name="WorkgroupAccessBean"
 *	type="Stateless" 
 *
 * @ejb.ejb-ref 
 *	ejb-name="Workgroup"
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
 */

public abstract class WorkgroupAccessBean implements SessionBean {
	
	protected SessionContext ctx;
	private UserLocalHome userLocalHome;
	private WorkgroupLocalHome workgroupLocalHome;
	private WorkgroupPermissionsLocalHome workgroupPermissionsLocalHome;
	private SecurityManager securityManager;
	

	/**
	 * WorkgroupAccessBean <code>ejbCreate</code> method.
	 * @throws CreateException if ejb creation fails. 
	 * 
	 * @ejb.create-method
	 */
	public void ejbCreate() throws CreateException {
		try {
			userLocalHome = UserUtil.getLocalHome();
			workgroupLocalHome = WorkgroupUtil.getLocalHome();
			workgroupPermissionsLocalHome = WorkgroupPermissionsUtil.getLocalHome();
			securityManager = new SecurityManager();
		} catch (NamingException nE) { nE.printStackTrace(); }
		//TODO handle NamingException for ejbCreate
	}
	
	/**
	 * Sets the session context 
	 * @param ctx the new context value
	 * 
	 * @ejb.method 
	 */   
	public void setSessionContext(javax.ejb.SessionContext ctx)  {this.ctx = ctx;}    

	/**
	 * Unsets the session context 
	 * 
	 * @ejb.method
	 */
	public void unsetSessionContext()  {this.ctx = null;}
	
	/**
	 * returns a workgroup value object for the given workgroup id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupID primary key for the requested workgroup.
	 * @return the workgroup value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws WorkgroupLookupFault if the requested workgroup could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public WorkgroupValue getWorkgroupByPK( String sUserID, String sPassword, String workgroupID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		WorkgroupValue wv = null;
					   
		try {
			wv  =   (workgroupLocalHome.findByPrimaryKey(workgroupID)).getWorkgroupValue();											
		} catch (FinderException fE) {
				throw new WorkgroupLookupFault(workgroupID);
		} return wv; 
	}

	/**
	 * returns an enhanced workgroup value object for the given workgroup id.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupID primary key for the requested workgroup.
	 * @return the enhanced workgroup value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws WorkgroupLookupFault if the requested workgroup could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedWorkgroupValue getEnhancedWorkgroupByPK( String sUserID, String sPassword, String workgroupID) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		EnhancedWorkgroupValue ewv = null;

		try {
			ewv = (workgroupLocalHome.findByPrimaryKey(workgroupID)).getEnhancedWorkgroupValue();											
		} catch (FinderException fE) {
				throw new WorkgroupLookupFault(workgroupID);
		} return ewv; 
	}

	/**
	 * returns an enhanced workgroup permissions value object for the given workgroup permissions primary key.
	 * An enhanced workgroup permissions value object include a corresponding workgroup value object.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupPermissionsPK primary key for the requested workgroup permissions.
	 * @return the enhanced workgroup permissions value object.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws PermissionLookupFault if the requested workgroup permissions could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public EnhancedWorkgroupPermissionsValue getEnhancedWorkgroupPermissionsByPK(
			String sUserID, 
			String sPassword, 
			WorkgroupPermissionsPK workgroupPermissionsPK) 
		throws AuthenticationFailedFault, LookupFault
	{ 
		securityManager.Authenticate(sUserID, sPassword);
		EnhancedWorkgroupPermissionsValue ewpv = null;

		try {
			ewpv = (workgroupPermissionsLocalHome.findByPrimaryKey(workgroupPermissionsPK)).getEnhancedWorkgroupPermissionsValue();											
		} catch (FinderException fE) {
				throw new PermissionLookupFault(workgroupPermissionsPK.getUserID());
		} return ewpv; 
	}
	
	/**
	 * returns an array list of workgroup value objects for all workgroups in the system.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all workgroups.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoWorkgroupsFoundFault if no workgroups were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getAllWorkgroups( String sUserID, String sPassword ) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList workgroupsList = new ArrayList();
		try {
			Collection workgroups = workgroupLocalHome.findAll();
			Iterator iterate = workgroups.iterator();								
			while(iterate.hasNext()) {
				workgroupsList.add(((WorkgroupLocal) iterate.next()).getWorkgroupValue());
			}	
		} catch (FinderException fE){
			throw new NoWorkgroupsFoundFault();
		} return workgroupsList;
	}
	
	/**
	 * returns an array list of OPM model value objects for all OPM models of the given workgroup.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @return the list of all OPM models.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoOpmModelsFoundFault if no OPM models were found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public ArrayList getOpmModelsForWorkgroup( String sUserID, String sPassword, String workgroupID ) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		ArrayList opmModelList = new ArrayList();
		try {
			WorkgroupLocal wl = workgroupLocalHome.findByPrimaryKey(workgroupID);
			Collection opmModels = wl.getOpmModels();
			Iterator iterate = opmModels.iterator();								
			while(iterate.hasNext()) {
				opmModelList.add(((OpmModelLocal) iterate.next()).getOpmModelValue());
			}	
		} catch (FinderException fE){
			throw new NoOpmModelsFoundFault();
		} return opmModelList;
	}
	
	/**
	 * sets the workgroup permissions according to the editable workgroup permissions value object.
	 * if the permissions already exist they will be updated, otherwise new permissions will be created.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableWorkgroupPermissionsValue an editable workgroup permissions value object containing the new permissions.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	**/
	public void setWorkgroupPermissions( 
		String sUserID, 
		String sPassword, 
		EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue) 
		throws AuthenticationFailedFault
	{
		securityManager.Authenticate(sUserID, sPassword);		
		try {
			WorkgroupPermissionsLocal workgroupPermissionsLocal = 
				workgroupPermissionsLocalHome.findByPrimaryKey(editableWorkgroupPermissionsValue.getPrimaryKey());
			workgroupPermissionsLocal.setWorkgroupPermissions(editableWorkgroupPermissionsValue);
		} catch (FinderException fE) {
			try {
				workgroupPermissionsLocalHome.create(editableWorkgroupPermissionsValue);
			} catch (CreateException cE) { 
				//TODO What if the if a forign key constraint fails?
			}
		}
	}
	
	/**
	 * updates the workgroup entry with the given updatable workgroup value object.
	 * @param sUserID workgroup id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param updatableWorkgroupValue updatable workgroup value encapsulating the information to be updated.
	 * @throws AuthenticationFailedFault if workgroup authentication failed.
	 * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.

	 * @ejb.interface-method
	 *	view-type="both"
	 */	
	public void updateWorkgroup(
		String sUserID, 
		String sPassword, 
		UpdatableWorkgroupValue updatableWorkgroupValue) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);					
		
		try {
			WorkgroupLocal wl = workgroupLocalHome.findByPrimaryKey(updatableWorkgroupValue.getWorkgroupID());								
			wl.setUpdatableWorkgroupValue(updatableWorkgroupValue);		
		} catch (FinderException fE) {
				throw new NoSuchWorkgroupFault(updatableWorkgroupValue.getWorkgroupID());
		}
	}

	/**
	 * marks the requested workgroup as disabled.
	 * If <code>recursive</code> is set to <code>true</code> all OPM models in the workgroup
	 * as well as all collaborative sessions for those OPM models will recursively be disabled. 
	 * If set to <code>false</code> the procedure will check that all OPM models in the workgroup 
	 * are disabled before disabling the workgroup. if enabled OPM models are found 
	 * the workgroup will not be disabled and a <code>RecursiveDisableFault</code> will be raised.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupID primary key for workgroup to be disabled.
	 * @param recursive a boolean denoting if simple or a recursive disable should be preformed.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.
	 * @throws RecursiveDisableFault if <code>recursive</code> is set to true and enabled OPM models are found in the workgroup.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void disableWorkgroup( String sUserID, String sPassword, String workgroupID, boolean recursive) 
		throws AuthenticationFailedFault, LookupFault, RecursiveDisableFault
	{
		securityManager.Authenticate(sUserID, sPassword);	
		
		try {
			WorkgroupLocal wl = workgroupLocalHome.findByPrimaryKey(workgroupID);
			Collection opls = wl.getOpmModels();
			Iterator iterate = opls.iterator();								
			while(iterate.hasNext()) {			   	
				OpmModelLocal opl = (OpmModelLocal) iterate.next();
				if ( opl.getEnabled().booleanValue() ){
					throw new ActiveOpmModelFoundFault(opl.getOpmModelID());
					//TODO Implement recursive disable
				}
			}
			wl.setEnabled(new Boolean(false));
		} catch (FinderException fE) {
			throw new NoSuchWorkgroupFault(workgroupID);
		}	
	}
	
	/**
	 * marks the requested workgroup as enabled.
	 * If <code>recursive</code> is set to <code>true</code> all OPM models in the workgroup
	 * as well as all collaborative sessions for those OPM models will recursively be enabled. 
	 * If set to <code>false</code> only the requested workgroup will be enabled.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param workgroupID primary key for workgroup to be disabled.
	 * @param recursive a boolean denoting if simple or a recursive enable should be preformed.
	 * @throws AuthenticationFailedFault if user authentication failed.
	 * @throws NoSuchWorkgroupFault if the requested workgroup could not be found.
	 * 
	 * @ejb.interface-method
	 *	view-type="both" 
	**/
	public void enableWorkgroup( String sUserID, String sPassword, String workgroupID, boolean recursive) 
		throws AuthenticationFailedFault, LookupFault
	{
		securityManager.Authenticate(sUserID, sPassword);
		
		try {
			(workgroupLocalHome.findByPrimaryKey(workgroupID)).setEnabled(new Boolean(true));
		} catch (FinderException fE) {
			throw new NoSuchWorkgroupFault(workgroupID);
		}
	}
	
	/**
	 * creates a new workgroup entry based on the information in the editable workgroup value object.
	 * an editable workgroup value object contains all workgroup attributes that can be initialized during creation.
	 * @param sUserID user id for the user performing the action.
	 * @param sPassword password for the user performing the action.
	 * @param editableWorkgroupValue the editable workgroup value object containing the editable workgroup attributes.
	 * @return GUID primary key for the new workgroup.
	 * @throws WorkgroupAlreadyExistFault if a workgroup with the requested name already exists.
	 * 
	 * @ejb.interface-method
	 *	view-type="both"
	 */
	public String createWorkgroup(
		String sUserID, 
		String sPassword, 
		EditableWorkgroupValue editableWorkgroupValue) 
		throws AuthenticationFailedFault, CreationFault
	{
		securityManager.Authenticate(sUserID, sPassword);
						
		try {
			editableWorkgroupValue.setWorkgroupID(WorkgroupUtil.generateGUID(editableWorkgroupValue));
			workgroupLocalHome.create(editableWorkgroupValue);
			EditableWorkgroupPermissionsValue ewpv = new EditableWorkgroupPermissionsValue(
					editableWorkgroupValue.getWorkgroupID(),
					sUserID,
					PermissionFlags.CREATOR);
			workgroupPermissionsLocalHome.create(ewpv);
		} catch (CreateException cE) { 
			throw new WorkgroupAlreadyExistFault(editableWorkgroupValue.getWorkgroupName());
		} return editableWorkgroupValue.getWorkgroupID();		
	}
}
