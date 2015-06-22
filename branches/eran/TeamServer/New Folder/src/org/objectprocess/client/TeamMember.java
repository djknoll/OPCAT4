/*
 * Created on 19/11/2003
 */
package org.objectprocess.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.objectprocess.SoapClient.CollaborativeSessionAccessLocal;
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.CollaborativeSessionServiceLocator;
import org.objectprocess.SoapClient.CollaborativeSessionValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EditableOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EditableOpmModelValue;
import org.objectprocess.SoapClient.EditableRevisionValue;
import org.objectprocess.SoapClient.EditableWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.EditableWorkgroupValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedOpmModelValue;
import org.objectprocess.SoapClient.EnhancedUserValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupValue;
import org.objectprocess.SoapClient.FullRevisionValue;
import org.objectprocess.SoapClient.MetaRevisionValue;
import org.objectprocess.SoapClient.OpmModelAccessLocal;
import org.objectprocess.SoapClient.OpmModelPermissionsPK;
import org.objectprocess.SoapClient.OpmModelPermissionsValue;
import org.objectprocess.SoapClient.OpmModelServiceLocator;
import org.objectprocess.SoapClient.OpmModelValue;
import org.objectprocess.SoapClient.UpdatableCollaborativeSessionValue;
import org.objectprocess.SoapClient.UpdatableOpmModelValue;
import org.objectprocess.SoapClient.UpdatableUserValue;
import org.objectprocess.SoapClient.UpdatableWorkgroupValue;
import org.objectprocess.SoapClient.UserAccessLocal;
import org.objectprocess.SoapClient.UserServiceLocator;
import org.objectprocess.SoapClient.UserValue;
import org.objectprocess.SoapClient.WorkgroupAccessLocal;
import org.objectprocess.SoapClient.WorkgroupPermissionsPK;
import org.objectprocess.SoapClient.WorkgroupPermissionsValue;
import org.objectprocess.SoapClient.WorkgroupServiceLocator;
import org.objectprocess.SoapClient.WorkgroupValue;
import org.objectprocess.config.PermissionFlags;
/**
 * @author moonwatcher
 */
public class TeamMember {
	private String loginName = null;
	private String password= null;
	private String userID = null;
	private EnhancedUserValue localEnhancedUser= null;
    private ArrayList localEnhancedWorkgroupsPermissions = new ArrayList();
	private ArrayList localEnhancedOpmModelsPermissions = new ArrayList();
	private ArrayList localEnhancedCollaborativeSessionsPermissions = new ArrayList();
    //the next variable holds the users that belong to te active session.
    private EnhancedCollaborativeSessionValue enhancedCollaborativeSessionValue = null;


	private UserServiceLocator userService;
	private UserAccessLocal userPort;
	private WorkgroupServiceLocator workgroupService;
	private WorkgroupAccessLocal workgroupPort;
	private OpmModelServiceLocator opmModelService;
	private OpmModelAccessLocal opmModelPort;
	private CollaborativeSessionServiceLocator collaborativeSessionService;
	private CollaborativeSessionAccessLocal collaborativeSessionPort;

	public final static String minorUserServiceUrl = "UserService";
	public final static String minorWorkgroupServiceUrl = "WorkgroupService";
	public final static String minorOpmModelServiceUrl = "OpmModelService";
	public final static String minorCollaborativeSessionServiceUrl = "CollaborativeSessionService";
	private String serverAddr;
	
	public TeamMember(){}
	
	public void setAllServices(String serverAddr,String serverPort) 
		throws Exception
	{
		URL tempURL;
        String majorURLString="http://"+serverAddr+":"+serverPort+"/axis/services/";

		tempURL = createURL( majorURLString, minorUserServiceUrl );
		//Make a UserService
		setUserService(new UserServiceLocator());
		setUserPort((UserAccessLocal) userService.getUserService(tempURL));

		tempURL = createURL( majorURLString, minorWorkgroupServiceUrl );
		//Make a WorkgroupService
		setWorkgroupService(new WorkgroupServiceLocator());
		setWorkgroupPort((WorkgroupAccessLocal) workgroupService.getWorkgroupService(tempURL));

		tempURL = createURL( majorURLString, minorOpmModelServiceUrl );
		//Make a OpmModelService
		setOpmModelService(new OpmModelServiceLocator());
		opmModelPort = (OpmModelAccessLocal) opmModelService.getOpmModelService(tempURL);

		tempURL = createURL( majorURLString, minorCollaborativeSessionServiceUrl );
		//Make a CollaborativeSessionService
		setCollaborativeSessionService(new CollaborativeSessionServiceLocator());
		setCollaborativeSessionPort((CollaborativeSessionAccessLocal) collaborativeSessionService.getCollaborativeSessionService(tempURL));

		setServerAddr(serverAddr);
	}

	private URL createURL(String majorURLString, String manorURLString) 
		throws MalformedURLException 
	{
		String finalURL = majorURLString + manorURLString;
		URL url = new URL(finalURL);
		return url;
	}

	public boolean isConnected() {
   	return !(userID==null);
   }	

	//Creating the editables class - it is pre create action.
	public EditableWorkgroupPermissionsValue createEditableWorkgroupPermissionsValue(
			String userID,
			String workgroupID,
			Integer AccessControl) {
		
			EditableWorkgroupPermissionsValue e = new EditableWorkgroupPermissionsValue();
			WorkgroupPermissionsPK pk = new WorkgroupPermissionsPK();
			pk.setUserID(userID);
			pk.setWorkgroupID(workgroupID);
		
			e.setPrimaryKey(pk);
			e.setUserID(userID);
			e.setWorkgroupID(workgroupID);
			e.setAccessControl(AccessControl);

			return e;
	}	

	public EditableOpmModelPermissionsValue createEditableOpmModelPermissionsValue(
			String userID,
			String opmModelID,
			Integer AccessControl){
		
			EditableOpmModelPermissionsValue e = new EditableOpmModelPermissionsValue();
			OpmModelPermissionsPK pk = new OpmModelPermissionsPK();
			pk.setUserID(userID);
			pk.setOpmModelID(opmModelID);

			e.setPrimaryKey(pk);
			e.setUserID(userID);
			e.setOpmModelID(opmModelID);
			e.setAccessControl(AccessControl);

			return e;
	}				

	public EditableCollaborativeSessionPermissionsValue createEditableCollaborativeSessionPermissionsValue(
			String userID,
			String collaborativeSessionID,
			Integer AccessControl) {
		
			EditableCollaborativeSessionPermissionsValue e = new EditableCollaborativeSessionPermissionsValue();
			CollaborativeSessionPermissionsPK pk = new CollaborativeSessionPermissionsPK();
			pk.setUserID(userID);
			pk.setCollaborativeSessionID(collaborativeSessionID);

			e.setPrimaryKey(pk);
			e.setUserID(userID);
			e.setCollaborativeSessionID(collaborativeSessionID);
			e.setAccessControl(AccessControl);

			return e;
	}

	//Create functions
	public EnhancedWorkgroupPermissionsValue createWorkgroup(EditableWorkgroupValue editableWorkgroupValue) 
		throws Exception 
	{      
		EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue = null;
				String workgroupID = workgroupPort.createWorkgroup(
						getLocalEnhancedUser().getUserID(),
						getLocalEnhancedUser().getPassword(),
						editableWorkgroupValue);
				WorkgroupPermissionsPK wpk = new WorkgroupPermissionsPK();
				wpk.setUserID(getLocalEnhancedUser().getUserID());
				wpk.setWorkgroupID(workgroupID);
				enhancedWorkgroupPermissionsValue = workgroupPort.getEnhancedWorkgroupPermissionsByPK(
						getLocalEnhancedUser().getUserID(),
						getLocalEnhancedUser().getPassword(),
						wpk);
				getLocalEnhancedWorkgroupsPermissions().add(enhancedWorkgroupPermissionsValue);
		return enhancedWorkgroupPermissionsValue;
	}

	public EnhancedOpmModelPermissionsValue createOpmModel(EditableOpmModelValue editableOpmModelValue)  
		throws Exception
	{
		EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue = null;
		String opmModelID = opmModelPort.createOpmModel(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				editableOpmModelValue);
		getLocalEnhancedOpmModelsPermissions().add(enhancedOpmModelPermissionsValue);
		OpmModelPermissionsPK ompk = new OpmModelPermissionsPK();
		ompk.setUserID(getLocalEnhancedUser().getUserID());
		ompk.setOpmModelID(opmModelID);
		enhancedOpmModelPermissionsValue = opmModelPort.getEnhancedOpmModelPermissionsByPK(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				ompk);
		getLocalEnhancedOpmModelsPermissions().add(enhancedOpmModelPermissionsValue);
		return enhancedOpmModelPermissionsValue;
	}

	public EnhancedCollaborativeSessionPermissionsValue createCollaborativeSession (
			EditableCollaborativeSessionValue editableCollaborativeSessionValue ) 
	throws Exception {
		EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue = null;
		String sessionID = collaborativeSessionPort.createCollaborativeSession(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				editableCollaborativeSessionValue);
		CollaborativeSessionPermissionsPK cspk = new CollaborativeSessionPermissionsPK();
		cspk.setUserID(getLocalEnhancedUser().getUserID());
		cspk.setCollaborativeSessionID(sessionID);
		enhancedCollaborativeSessionPermissionsValue = collaborativeSessionPort.getEnhancedCollaborativeSessionPermissionsByPK(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				cspk);
		getLocalEnhancedCollaborativeSessionsPermissions().add(enhancedCollaborativeSessionPermissionsValue);
		return enhancedCollaborativeSessionPermissionsValue;
	}

	//Permissions getter functions
	public WorkgroupPermissionsValue fetchWorkgroupPermissionsForUser (
		WorkgroupPermissionsPK workgroupPermissionsPK) 
		throws Exception
	{
		WorkgroupPermissionsValue workgroupPermissionsValue=null;
		workgroupPermissionsValue= userPort.getWorkgroupPermissionsForUser(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			workgroupPermissionsPK);
		return workgroupPermissionsValue;
	}

	public OpmModelPermissionsValue fetchOpmModelPermissionsForUser (
		OpmModelPermissionsPK opmModelPermissionsPK)
		throws Exception
	{		
		OpmModelPermissionsValue opmModelPermissionsValue=null;
		opmModelPermissionsValue = userPort.getOpmModelPermissionsForUser(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			opmModelPermissionsPK);
		return opmModelPermissionsValue;
	}

	public CollaborativeSessionPermissionsValue fetchCollaborativeSessionPermissionsForUser (
		CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK)
		throws Exception
	{
		CollaborativeSessionPermissionsValue collaborativeSessionPermissionsValue=null;
		collaborativeSessionPermissionsValue = userPort.getCollaborativeSessionPermissionsForUser(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			collaborativeSessionPermissionsPK);
		return  collaborativeSessionPermissionsValue;
	}

//Permissions update functions
	public void updateWorkgroupPermissions (EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue)
		throws Exception
	{
		workgroupPort.setWorkgroupPermissions(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				editableWorkgroupPermissionsValue);
	}

	public void updateOpmModelPermissions (EditableOpmModelPermissionsValue editableOpmModelPermissionsValue)
		throws Exception
	{
		opmModelPort.setOpmModelPermissions(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				editableOpmModelPermissionsValue);
	}

	public void updateCollaborativeSessionPermissions (EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue)
		throws Exception
	{
		collaborativeSessionPort.setCollaborativeSessionPermissions(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				editableCollaborativeSessionPermissionsValue);
	}

//Local DataStructure Update methods , including handing the disable/enable flags.
	private void updateLocalWorkgroup(WorkgroupValue newWorkgroupValue){
		Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue)iterate.next();
			if( newWorkgroupValue.getWorkgroupID().equals(currentWorkgroup.getWorkgroupID())){
				currentWorkgroup.setWorkgroup(newWorkgroupValue);
				getLocalEnhancedWorkgroupsPermissions().set(
						(getLocalEnhancedWorkgroupsPermissions().indexOf(currentWorkgroup)),currentWorkgroup);
				return;
			}
		}
	}

	private void disableLocalWorkgroup(String workgroupID){
		Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
			if( workgroupID.equals(currentWorkgroup.getWorkgroupID())){
				currentWorkgroup.getWorkgroup().setEnabled(new Boolean(false));
				return;
			}
		}
	}

	private void enableLocalWorkgroup(String workgroupID){
		Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
			if( workgroupID.equals(currentWorkgroup.getWorkgroupID())){
				currentWorkgroup.getWorkgroup().setEnabled(new Boolean(true));
				return;
			}
		}
	}

	private void updateLocalOpmModel(OpmModelValue newOpmModelValue){
		Iterator iterate = getLocalEnhancedOpmModelsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
			if( newOpmModelValue.getOpmModelID().equals(currentOpmModel.getOpmModelID())){
				currentOpmModel.setOpmModel(newOpmModelValue);
				getLocalEnhancedOpmModelsPermissions().set(getLocalEnhancedOpmModelsPermissions().indexOf(currentOpmModel),
						currentOpmModel);
				return;
			}
		}
	}

	private void disableLocalOpmModel(String opmModelID){
		Iterator iterate = getLocalEnhancedOpmModelsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
			if( opmModelID.equals(currentOpmModel.getOpmModelID())){
				currentOpmModel.getOpmModel().setEnabled(new Boolean(false));
				return;
			}
		}
	}

	private void enableLocalOpmModel(String opmModelID){
		Iterator iterate = getLocalEnhancedOpmModelsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
			if( opmModelID.equals(currentOpmModel.getOpmModelID())){
				currentOpmModel.getOpmModel().setEnabled(new Boolean(true));
				return;
			}
		}
	}

	public void updateLocalCollaborativeSession(CollaborativeSessionValue newCollaborativeSessionValue){
		Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
			if( newCollaborativeSessionValue.getCollaborativeSessionID().equals(currentCollaborativeSession.getCollaborativeSessionID())){
				currentCollaborativeSession.setCollaborativeSession(newCollaborativeSessionValue);
				getLocalEnhancedCollaborativeSessionsPermissions().set(
						getLocalEnhancedCollaborativeSessionsPermissions().indexOf(currentCollaborativeSession),
						currentCollaborativeSession);
				return;
			}
		}
	}

	private void disableLocalCollaborativeSession(String collaborativeSessionID){
		Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
		while(iterate.hasNext()) {
			EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
			if( collaborativeSessionID.equals(currentCollaborativeSession.getCollaborativeSessionID())){
				currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(false));
				return;
			}
		}
	}

     private void enableLocalCollaborativeSession(String collaborativeSessionID){
     	Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
     	while(iterate.hasNext()) {
     		EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
     		if( collaborativeSessionID.equals(currentCollaborativeSession.getCollaborativeSessionID())){
     			currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(true));
     			return;
     		}
     	}
     }

     public void updateLocalCollaborativeSessionToken(String collaborativeSessionID,String tokenHolderID){
     	Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
     	while(iterate.hasNext()) {
     		EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
     		if( collaborativeSessionID.equals(currentCollaborativeSession.getCollaborativeSessionID())){
     			currentCollaborativeSession.getCollaborativeSession().setTokenHolderID(tokenHolderID);
     			return;
     		}
     	}
     }

     //User functions
	/**
	 * @param username the user name
	 * @param password the Password
	 *
	 * Initialize the userName and password data members.
	 * Call the loginUser webservice and get userID.
	 */
	public void loginUser(String loginName, String password)
		throws Exception 
	{
		setUserID(getUserPort().loginUser(loginName,password));
		setLoginName(loginName);
		setPassword(password);
	}

    public void logoutUser()
    	throws Exception
	{
		getUserPort().logoutUser(getUserID(),getPassword());
		setUserID(null);
		setLoginName(null);
		setPassword(null);
		setLocalEnhancedUser(null);
		setLocalEnhancedWorkgroupsPermissions(new ArrayList());
		setLocalEnhancedOpmModelsPermissions(new ArrayList());
		setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());
	}

	/**
	 * Initialize enhancedUserValue.
	 */
    public void initializeEnhancedUserValue()throws Exception 
	{
    	//	Check that initialize has not been called before successful login
    	if(getUserID().equals(null))
    		return;

    	setLocalEnhancedUser(null);
    	setLocalEnhancedWorkgroupsPermissions(new ArrayList());
    	setLocalEnhancedOpmModelsPermissions(new ArrayList());
    	setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());

        setLocalEnhancedUser(getUserPort().getEnhancedUserByPK( getUserID(), getPassword()));
        // Initialize local Data Structure References
        int i=0;
        for (i=0; i<getLocalEnhancedUser().getEnhancedWorkgroupsPermissions().length; i++){
          getLocalEnhancedWorkgroupsPermissions().add(getLocalEnhancedUser().getEnhancedWorkgroupsPermissions()[i]);}
        for (i=0; i<getLocalEnhancedUser().getEnhancedOpmModelsPermissions().length; i++){
          getLocalEnhancedOpmModelsPermissions().add(getLocalEnhancedUser().getEnhancedOpmModelsPermissions()[i]);}
        for (i=0; i<getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions().length; i++){
          getLocalEnhancedCollaborativeSessionsPermissions().add(getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions()[i]);}
	}

	public void updateUser(UpdatableUserValue updatableUserValue)
		throws Exception
	{
        getUserPort().updateUser( 
        	getLocalEnhancedUser().getUserID(),
            getLocalEnhancedUser().getPassword(),
            updatableUserValue);

        //Update UpdatableUserValue fields on LOCAL EnhancedUserValue
        updateEnhancedUserValueWithUpdatableFields(updatableUserValue);
        setPassword(getLocalEnhancedUser().getPassword());
        setLoginName(getLocalEnhancedUser().getLoginName());
    }

	public UserValue getUserByLoginName(String loginName)
		throws Exception
	{
		UserValue userValue = null;
		userValue = getUserPort().getUserByLoginName(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			loginName);
		return userValue;
	}

	public UserValue getUserByPK(String userID) 
		throws Exception
	{
		UserValue userValue = null;
		userValue = getUserPort().getUserByPK(
		      getLocalEnhancedUser().getUserID(),
		      getLocalEnhancedUser().getPassword(),
		      userID);
		return userValue;
    }

//Enhanced Getter functions
	public EnhancedWorkgroupValue fatchEnhancedWorkgroup(String workgroupID)
		throws Exception
	{
		EnhancedWorkgroupValue enhancedWorkgroupValue = null;
		enhancedWorkgroupValue = workgroupPort.getEnhancedWorkgroupByPK(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				workgroupID);
		return enhancedWorkgroupValue;
	}

	public EnhancedOpmModelValue fatchEnhancedOpmModel(String opmModelID)
		throws Exception
	{
		EnhancedOpmModelValue enhancedOpmModelValue = null;
		enhancedOpmModelValue = opmModelPort.getEnhancedOpmModelByPK(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			opmModelID);
		return enhancedOpmModelValue;
	}


//The next functions takes care of getting the session's member list, and update it if necessary

//fetch the information from the server and keep it in the coresponding variable
	public EnhancedCollaborativeSessionValue fatchEnhancedCollaborativeSession(String collaborativeSessionID)
		throws Exception
	{
		enhancedCollaborativeSessionValue = null;
        enhancedCollaborativeSessionValue = collaborativeSessionPort.getEnhancedCollaborativeSessionByPK(
            getLocalEnhancedUser().getUserID(),
            getLocalEnhancedUser().getPassword(),
            collaborativeSessionID);
        return enhancedCollaborativeSessionValue;
	}

//in case new user is logged in - update its status
	public void updateLocalMemberLoggedInStatus(String memberID){
      int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
      int i;
      for (i=0; i<len; i++) {
        if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().equals(memberID)) {
          Integer myAccessControl = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
          myAccessControl = new Integer(myAccessControl.intValue()+PermissionFlags.LOGGEDIN.intValue());
          enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
          return;
        }
      }
	}
//in case user is logged out- update its status
	public void updateLocalMemberLoggedOutStatus(String memberID){
      int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
      int i;
      for (i=0; i<len; i++) {
        if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().equals(memberID)) {
          Integer myAccessControl = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
          myAccessControl = new Integer(myAccessControl.intValue()-PermissionFlags.LOGGEDIN.intValue());
          enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
          return;
        }
      }
	}
//return the name of the user that his ID is given as a parameter
	public String getLocalMemberName(String memberID){
      //first check the the Data structure is not null;
      if (enhancedCollaborativeSessionValue == null) return null;

      int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
      int i;
      String memberName=null;
      for (i=0; i<len; i++) {
        if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().equals(memberID)) {
          memberName = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser().getFirstName()+ " " +
              enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser().getLastName();
          return memberName;
        }
      }
      return memberName;
	}
//this function returns the numbers of loggedIn users in the active session, includung the user itself.
	public int numOfLoggedInMembers(){
      //first check the the Data structure is not null;
      if (enhancedCollaborativeSessionValue == null) return 0;

      int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
      int i;
      int num=0;
      for (i=0; i<len; i++) {
        Integer accessControl = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
        if (((accessControl.intValue()) & (PermissionFlags.LOGGEDIN.intValue())) == PermissionFlags.LOGGEDIN.intValue())
          num++;
      }
      return num;
	}

//Getter functions
	public WorkgroupValue fatchWorkgroup(String workgroupID)throws Exception{
          WorkgroupValue workgroupValue = null;
          try {
            workgroupValue = workgroupPort.getWorkgroupByPK(
                    getLocalEnhancedUser().getUserID(),
                    getLocalEnhancedUser().getPassword(),
                    workgroupID);
          } catch(Exception e) { throw e; }
          return workgroupValue;
	}

	public OpmModelValue fatchOpmModel(String opmModelID) throws Exception{
          OpmModelValue opmModelValue = null;
          try {
            opmModelValue = opmModelPort.getOpmModelByPK(
                 getLocalEnhancedUser().getUserID(),
                 getLocalEnhancedUser().getPassword(),
                 opmModelID);
          } catch(Exception e) { throw e; }
          return opmModelValue;
	}

	public CollaborativeSessionValue fatchCollaborativeSession
            (String collaborativeSessionID) throws Exception{
          CollaborativeSessionValue collaborativeSessionValue = null;
          try {
            collaborativeSessionValue = collaborativeSessionPort.getCollaborativeSessionByPK(
                 getLocalEnhancedUser().getUserID(),
                 getLocalEnhancedUser().getPassword(),
                 collaborativeSessionID);
          } catch(Exception e) { throw e; }
          return collaborativeSessionValue;
	}

//Update functions
	public void updateWorkgroup(UpdatableWorkgroupValue updatableWorkgroupValue) 
		throws Exception
	{
		workgroupPort.updateWorkgroup(
		     getLocalEnhancedUser().getUserID(),
		     getLocalEnhancedUser().getPassword(),
			 updatableWorkgroupValue);
		WorkgroupValue wv = workgroupPort.getWorkgroupByPK(
			     getLocalEnhancedUser().getUserID(),
			     getLocalEnhancedUser().getPassword(),
				 updatableWorkgroupValue.getWorkgroupID());
		updateLocalWorkgroup(wv);
	}

    public void updateOpmModel(UpdatableOpmModelValue updatableOpmModelValue) 
    	throws Exception
	{
      opmModelPort.updateOpmModel(
                 getLocalEnhancedUser().getUserID(),
                 getLocalEnhancedUser().getPassword(),
                 updatableOpmModelValue);
      OpmModelValue omv = opmModelPort.getOpmModelByPK(
		     getLocalEnhancedUser().getUserID(),
		     getLocalEnhancedUser().getPassword(),
			 updatableOpmModelValue.getOpmModelID());
      updateLocalOpmModel(omv);
    }

    public void updateCollaborativeSession (UpdatableCollaborativeSessionValue updatableCollaborativeSessionValue) 
    	throws Exception
	{
    	collaborativeSessionPort.updateCollaborativeSession(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              updatableCollaborativeSessionValue);
    	CollaborativeSessionValue csv = collaborativeSessionPort.getCollaborativeSessionByPK(
   		     getLocalEnhancedUser().getUserID(),
		     getLocalEnhancedUser().getPassword(),
			 updatableCollaborativeSessionValue.getCollaborativeSessionID());
    	updateLocalCollaborativeSession(csv);
    }

// OpmModel File Functions
	public CollaborativeSessionFileValue fatchCollaborativeSessionFile(String collaborativeSessionID) 
		throws Exception
	{
          CollaborativeSessionFileValue csfv = collaborativeSessionPort.getCollaborativeSessionFileByPK(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              collaborativeSessionID);

          return csfv;
    }

    public FullRevisionValue fatchOpmModelFile(String revisionID) 
    	throws Exception
	{
      FullRevisionValue fullRevisionValue = opmModelPort.getRevisionByPK(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              revisionID);
      return fullRevisionValue;
    }

    public Object[] fatchOpmModelAllRevisions(String opmModelID) throws Exception{
    	//TODO check what is returning
      Object[] metaRevisionsList=null;
      Object meta=null;
      try{
        meta=opmModelPort.getAllRevisions(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              opmModelID);
      }catch (Exception e){throw e;}
      metaRevisionsList =(Object[]) meta;
      return metaRevisionsList;
    }

    public MetaRevisionValue fatchMetaRevision(String revisionID) 
    	throws Exception
	{
    	MetaRevisionValue metaRevisionValue=null;
      	metaRevisionValue=opmModelPort.getMetaRevisionByPK(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
			  revisionID);
            
      	return metaRevisionValue;
    }
    
    public void updateCollaborativeSessionFile (CollaborativeSessionFileValue collaborativeSessionFileValue) 
    	throws Exception
	{
        collaborativeSessionPort.updateCollaborativeSessionFile(
             getLocalEnhancedUser().getUserID(),
             getLocalEnhancedUser().getPassword(),
             collaborativeSessionFileValue);
    }

    public int findHigestRevision(Object[] metaRevisionsList) 
    {
       if (metaRevisionsList==null) return -1;

       int index=-1;
       int major=0;
       int minor=0;

       for (int i=0; i<metaRevisionsList.length; i++) {
         MetaRevisionValue meta = (MetaRevisionValue) (metaRevisionsList[i]);
         if (meta.getMajorRevision().intValue() >major) {
           major = meta.getMajorRevision().intValue();
           minor = meta.getMinorRevision().intValue();
           index = i;
         }
         else if ((meta.getMajorRevision().intValue() == major)  &&
                  (meta.getMinorRevision().intValue() > minor)) {
           minor = meta.getMinorRevision().intValue();
           index = i;
         }
       }
       return index;
    }

    //login, logout to session commands
    public void sessionLogin(String collaborativeSessionID)
    	throws Exception
	{
        collaborativeSessionPort.sessionLoginUser(
            getLocalEnhancedUser().getUserID(),
            getLocalEnhancedUser().getPassword(),
            collaborativeSessionID);
    }

    public void sessionLogout(String collaborativeSessionID) 
    	throws Exception
	{
        collaborativeSessionPort.sessionLogoutUser(
            getLocalEnhancedUser().getUserID(),
            getLocalEnhancedUser().getPassword(),
            collaborativeSessionID);
    }


    // Token function
    public boolean requestToken(String collaborativeSessionID)
    	throws Exception
	{
    	boolean result = false;
    	try{
    	    collaborativeSessionPort.requestToken(
    	            getLocalEnhancedUser().getUserID(),
    	            getLocalEnhancedUser().getPassword(),
    	            collaborativeSessionID);
            updateLocalCollaborativeSessionToken(collaborativeSessionID,getUserID());
            return true;

    	} catch (Exception fault){
    	    //TODO what is the boolean for?
    	    return false;
    	}

    }

	public void returnToken(String collaborativeSessionID) 
		throws Exception
	{
       collaborativeSessionPort.returnToken(
           getLocalEnhancedUser().getUserID(),
           getLocalEnhancedUser().getPassword(),
           collaborativeSessionID);
       updateLocalCollaborativeSessionToken(collaborativeSessionID,PermissionFlags.SERVER_USERID);
	}


	//VCS Functions
	public Object[] preCommitCollaborativeSession(String revisionID) throws Exception{
	   //TODO check what is returning (arrayList)
		Object[] metaRevisionsList=null;
		Object meta=null;
		meta=opmModelPort.getPreCommit(
				getLocalEnhancedUser().getUserID(),
				getLocalEnhancedUser().getPassword(),
				revisionID);
		metaRevisionsList =(Object[]) meta;
		return metaRevisionsList;
	}

	public EditableRevisionValue commitCollaborativeSession(
		String sessionID, EditableRevisionValue editableRevisionValue,boolean increaseMajor) 
		throws Exception
	{
		EditableRevisionValue returnedEditableRevisionValue = null;
		returnedEditableRevisionValue = collaborativeSessionPort.commitCollaborativeSession(
			getLocalEnhancedUser().getUserID(),
			getLocalEnhancedUser().getPassword(),
			sessionID,
			editableRevisionValue,
			increaseMajor);
		return returnedEditableRevisionValue;
	}

	//Disable and Enable Functions
	public void disableOpmModel(String opmModelID) throws Exception{
		//TODO implement recursive disable support  
		opmModelPort.disableOpmModel(
                getLocalEnhancedUser().getUserID(),
                getLocalEnhancedUser().getPassword(),
                opmModelID,
				false);
         disableLocalOpmModel(opmModelID);
	}

	public void enableOpmModel(String opmModelID) throws Exception{
         opmModelPort.enableOpmModel(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              opmModelID,
              false);
         enableLocalOpmModel(opmModelID);
	}

	public void disableWorkgroup(String workgroupID)throws Exception{
		//TODO implement recursive disable support  
		workgroupPort.disableWorkgroup(
              getLocalEnhancedUser().getUserID(),
              getLocalEnhancedUser().getPassword(),
              workgroupID,
			  false);
         disableLocalWorkgroup(workgroupID);
	}

	public void enableWorkgroup(String workgroupID)throws Exception{
         workgroupPort.enableWorkgroup(
               getLocalEnhancedUser().getUserID(),
               getLocalEnhancedUser().getPassword(),
               workgroupID,
               false);
         enableLocalWorkgroup(workgroupID);
	}

	public void disableCollaborativeSession(String collaborativeSessionID) throws Exception{
		//TODO implement recursive disable support 
		collaborativeSessionPort.disableCollaborativeSession(
		         getLocalEnhancedUser().getUserID(),
		         getLocalEnhancedUser().getPassword(),
		         collaborativeSessionID,
				 false);
		 disableLocalCollaborativeSession(collaborativeSessionID);
	}

     public void enablecollaborativeSession(String collaborativeSessionID) throws Exception{
         collaborativeSessionPort.enableCollaborativeSession(
             getLocalEnhancedUser().getUserID(),
             getLocalEnhancedUser().getPassword(),
             collaborativeSessionID);
         enableLocalCollaborativeSession(collaborativeSessionID);
     }

//	Private class functions
	private void updateEnhancedUserValueWithUpdatableFields(UpdatableUserValue updatableUserValue){
          getLocalEnhancedUser().setLoginName(updatableUserValue.getLoginName());
          getLocalEnhancedUser().setFirstName(updatableUserValue.getFirstName());
          getLocalEnhancedUser().setLastName(updatableUserValue.getLastName());
          getLocalEnhancedUser().setPassword(updatableUserValue.getPassword());
          getLocalEnhancedUser().setEmail(updatableUserValue.getEmail());
          getLocalEnhancedUser().setDescription(updatableUserValue.getDescription());
	}

	/**
	 * @return
	 */
	private CollaborativeSessionAccessLocal getCollaborativeSessionPort() {
          return collaborativeSessionPort;
	}

	/**
	 * @return
	 */
	private CollaborativeSessionServiceLocator getCollaborativeSessionService() {
          return collaborativeSessionService;
	}

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedCollaborativeSessionsPermissions() {
          return localEnhancedCollaborativeSessionsPermissions;
	}

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedOpmModelsPermissions() {
          return localEnhancedOpmModelsPermissions;
	}

	/**
	 * @return
	 */
	public EnhancedUserValue getLocalEnhancedUser() {
          return localEnhancedUser;
	}

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedWorkgroupsPermissions() {
          return localEnhancedWorkgroupsPermissions;
	}

	/**
	 * @return
	 */
	private String getLoginName() {
          return loginName;
	}

	/**
	 * @return
	 */
	private OpmModelAccessLocal getOpmModelPort() {
          return opmModelPort;
	}

	/**
	 * @return
	 */
	private OpmModelServiceLocator getOpmModelService() {
          return opmModelService;
	}

	/**
	 * @return
	 */
	private String getPassword() {
          return password;
	}

	/**
	 * @return
	 */
	private String getUserID() {
          return userID;
	}

	/**
	 * @return
	 */
	public UserAccessLocal getUserPort() {
          return userPort;
	}

	/**
	 * @return
	 */
	private UserServiceLocator getUserService() {
          return userService;
	}

	/**
	 * @return
	 */
	private WorkgroupAccessLocal getWorkgroupPort() {
          return workgroupPort;
	}

	/**
	 * @return
	 */
	private WorkgroupServiceLocator getWorkgroupService() {
          return workgroupService;
	}

	/**
	 * @param local
	 */
	private void setCollaborativeSessionPort(CollaborativeSessionAccessLocal local) {
          collaborativeSessionPort = local;
	}

	/**
	 * @param locator
	 */
	private void setCollaborativeSessionService(CollaborativeSessionServiceLocator locator) {
          collaborativeSessionService = locator;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedCollaborativeSessionsPermissions(ArrayList list) {
          localEnhancedCollaborativeSessionsPermissions = list;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedOpmModelsPermissions(ArrayList list) {
          localEnhancedOpmModelsPermissions = list;
	}

	/**
	 * @param value
	 */
        private void setLocalEnhancedUser(EnhancedUserValue value) {
          localEnhancedUser = value;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedWorkgroupsPermissions(ArrayList list) {
          localEnhancedWorkgroupsPermissions = list;
	}

	/**
	 * @param string
	 */
	private void setLoginName(String string) {
          loginName = string;
	}

	/**
	 * @param local
	 */
	private void setOpmModelPort(OpmModelAccessLocal local) {
          opmModelPort = local;
	}

	/**
	 * @param locator
	 */
	private void setOpmModelService(OpmModelServiceLocator locator) {
          opmModelService = locator;
	}

	/**
	 * @param string
	 */
	private void setPassword(String string) {
          password = string;
	}

	/**
	 * @param i
	 */
	private void setUserID(String i) {
          userID = i;
	}

	/**
	 * @param local
	 */
	private void setUserPort(UserAccessLocal local) {
          userPort = local;
	}

	/**
	 * @param locator
	 */
	private void setUserService(UserServiceLocator locator) {
          userService = locator;
	}

	/**
	 * @param local
	 */
	private void setWorkgroupPort(WorkgroupAccessLocal local) {
          workgroupPort = local;
	}

	/**
	 * @param locator
	 */
	private void setWorkgroupService(WorkgroupServiceLocator locator) {
          workgroupService = locator;
	}

	/**
	 * @return
	 */
	public String getServerAddr() {
          return serverAddr;
	}

	/**
	 * @param string
	 */
	public void setServerAddr(String string) {
          serverAddr = string;
	}



	public TeamMember(int debug)
	{
          //	Initialize the TeamMember with localhost
          try {
            //Make a UserService
            setUserService(new UserServiceLocator());
            setUserPort((UserAccessLocal) userService.getUserService());

            //Make a WorkgroupService
            setWorkgroupService(new WorkgroupServiceLocator());
            setWorkgroupPort((WorkgroupAccessLocal) workgroupService.getWorkgroupService());

            //Make a OpmModelService
            setOpmModelService(new OpmModelServiceLocator());
            opmModelPort = (OpmModelAccessLocal) opmModelService.getOpmModelService();

            //Make a CollaborativeSessionService
            setCollaborativeSessionService(new CollaborativeSessionServiceLocator());
            setCollaborativeSessionPort((CollaborativeSessionAccessLocal) collaborativeSessionService.getCollaborativeSessionService());

          } catch (Exception e) {e.printStackTrace();};
        }


}
