package org.objectprocess.security;

import java.util.Collection;
import java.util.Iterator;

import javax.ejb.FinderException;
import javax.ejb.ObjectNotFoundException;
import javax.naming.NamingException;

import org.objectprocess.cmp.OpmModelLocal;
import org.objectprocess.cmp.OpmModelLocalHome;
import org.objectprocess.cmp.OpmModelPermissionsLocal;
import org.objectprocess.cmp.OpmModelUtil;
import org.objectprocess.cmp.UserLocal;
import org.objectprocess.cmp.UserLocalHome;
import org.objectprocess.cmp.UserUtil;
import org.objectprocess.cmp.WorkgroupLocal;
import org.objectprocess.cmp.WorkgroupLocalHome;
import org.objectprocess.cmp.WorkgroupPermissionsLocal;
import org.objectprocess.cmp.WorkgroupUtil;
import org.objectprocess.config.PermissionFlags;
import org.objectprocess.fault.authenticate.NoSuchUserFault;
import org.objectprocess.fault.authenticate.UserDisabledFault;
import org.objectprocess.fault.authenticate.WrongPasswordFault;
import org.objectprocess.fault.lookup.NoSuchOpmModelFault;

/**
 * The security manager encapsulates all security related functionality such as user authentication and permission handling.
 * @author Lior Galanti
 */
public class SecurityManager {	
	private UserLocalHome userLocalHome;
	private WorkgroupLocalHome workgroupLocalHome;
	private OpmModelLocalHome opmModelLocalHome;
	public SecurityManager(){
		try{
			userLocalHome = UserUtil.getLocalHome();
			workgroupLocalHome = WorkgroupUtil.getLocalHome();
			opmModelLocalHome = OpmModelUtil.getLocalHome();
		} catch (NamingException nE) {nE.printStackTrace();}
	}
	
	/**
	 * @param userID user id for the user performing the action.
	 * @param password password for the user performing the action.
	 * @return Local Interface for the user being authenitcated.
	 * @throws UserDisabledFault if the user being authenticated is disabled.
	 * @throws WrongPasswordFault if the password provided is incorrect.
	 * @throws NoSuchUserFault if the user being authenticated does not exist in the repository.
	 */
	public UserLocal Authenticate( String userID, String password) 
		throws UserDisabledFault, WrongPasswordFault, NoSuchUserFault
	{
		UserLocal userLocal = null;
		try {
			userLocal = userLocalHome.findByPrimaryKey(userID);
			if(!userLocal.getEnabled().booleanValue())
				throw new UserDisabledFault(userID);
			
			if (0 != userLocal.getPassword().compareTo(password)){
				throw new WrongPasswordFault(userID, password);
			}
			
		} catch (FinderException fE){
			if (fE instanceof ObjectNotFoundException)
				throw new NoSuchUserFault(userID);
		} 
		return userLocal;
	}	
	
	/**
	 * Locates the creator of the workgroup containing the OPM model.
	 * @param opmModelID primary key for the OPM model.
	 * @return local interface for the workgroup permission of the workgroup's creator.
	 * @throws NoSuchOpmModelFault if the OPM model could not be located.
	 */
	public WorkgroupPermissionsLocal getWorkgroupCreator( String opmModelID) 
		throws NoSuchOpmModelFault
	{
		OpmModelLocal oml = null;
		try {
			oml = opmModelLocalHome.findByPrimaryKey(opmModelID);
		} catch (FinderException fE) {
			throw new NoSuchOpmModelFault(opmModelID);				
		}
		try {
			WorkgroupLocal wl = workgroupLocalHome.findByPrimaryKey(oml.getWorkgroupID());
			Collection wpls = wl.getWorkgroupPermissions();
			Iterator iterate = wpls.iterator();								
			while(iterate.hasNext()) {				   	
				WorkgroupPermissionsLocal wpl = (WorkgroupPermissionsLocal) iterate.next();
				if ((wpl.getAccessControl().intValue() & PermissionFlags.CREATOR.intValue()) == PermissionFlags.CREATOR.intValue())
					return wpl;
			}
		} catch (FinderException fE) {
			//How can we have an OPM model with no containing workgroup?	
		    //throw new NoSuchWorkgroupFault(oml.getWorkgroupID());
		    fE.printStackTrace();
		} return null; //TODO no creator?
	}	
	
	/**
	 * Locates the creator of the OPM model.
	 * @param opmModelID primary key for th OPM model.
	 * @return local interface for the OPM model permission of the OPM model's creator. 
	 * @throws NoSuchOpmModelFault if the OPM model could not be located.
	 */
	public OpmModelPermissionsLocal getOpmModelCreator (String opmModelID) 
		throws NoSuchOpmModelFault
	{
		try {
			OpmModelLocal oml = opmModelLocalHome.findByPrimaryKey(opmModelID);
			Collection ompls = oml.getOpmModelPermissions();
			Iterator iterate = ompls.iterator();								
			while(iterate.hasNext()) {				   	
				OpmModelPermissionsLocal ompl = (OpmModelPermissionsLocal) iterate.next();
				if ((ompl.getAccessControl().intValue() & PermissionFlags.CREATOR.intValue()) == PermissionFlags.CREATOR.intValue())
					return ompl;
			}
		} catch (FinderException fE) {
				throw new NoSuchOpmModelFault(opmModelID);
		}
		return null;
	}
	
	public boolean isViewer(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.VIEWER.intValue()) == PermissionFlags.VIEWER.intValue() )
		    return true;
		else
		    return false;
	}	
	public boolean isEditor(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.EDITOR.intValue()) == PermissionFlags.EDITOR.intValue() )
		    return true;
		else
		    return false;
	}	
	public boolean isCommiter(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.COMMITTER.intValue()) == PermissionFlags.COMMITTER.intValue() )
		    return true;
		else
		    return false;
	}
	public boolean isModerator(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.MODERATOR.intValue()) == PermissionFlags.MODERATOR.intValue() )
		    return true;
		else
		    return false;
	}
	public boolean isAdministrator(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.ADMINISTRATOR.intValue()) == PermissionFlags.ADMINISTRATOR.intValue() )
		    return true;
		else
		    return false;    
	}
	public boolean isCreator(Integer accessControl){
		if ( (accessControl.intValue() & PermissionFlags.CREATOR.intValue()) == PermissionFlags.CREATOR.intValue() )
		    return true;
		else
		    return false;    
	}
}
