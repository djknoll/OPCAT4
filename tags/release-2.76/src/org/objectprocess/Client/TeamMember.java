/*
 * Created on 19/11/2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.objectprocess.Client;

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
import org.objectprocess.SoapClient.EditableUserValue;
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
import org.objectprocess.SoapClient.UserAccessLocal;
import org.objectprocess.SoapClient.UserServiceLocator;
import org.objectprocess.SoapClient.UserValue;
import org.objectprocess.SoapClient.WorkgroupAccessLocal;
import org.objectprocess.SoapClient.WorkgroupPermissionsPK;
import org.objectprocess.SoapClient.WorkgroupPermissionsValue;
import org.objectprocess.SoapClient.WorkgroupServiceLocator;
import org.objectprocess.SoapClient.WorkgroupValue;
/**
 * @author moonwatcher
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TeamMember {
	private String loginName = null;
	private String password= null;
	private int userID = 0;
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

	public final String minorUserServiceUrl = "UserService";
	public final String minorWorkgroupServiceUrl = "WorkgroupService";
	public final String minorOpmModelServiceUrl = "OpmModelService";
	public final String minorCollaborativeSessionServiceUrl = "CollaborativeSessionService";
	private String serverAddr;


	public void setAllServices(String serverAddr,String serverPort) throws Exception
	{
		try {
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

		} catch (Exception e) {throw e;};
              }

	private URL createURL(String majorURLString, String manorURLString) throws Exception{
	   try {
		 String finalURL = majorURLString + manorURLString;
		 URL url = new URL(finalURL);
		 return url;
               }
	   catch (MalformedURLException eurl) {
		 // exception handler code here
		 throw eurl;
               }
             }

        public boolean isConnected() {
          return !(getUserID() == 0);
        }

        public TeamMember(){}

//Creating the editables class - it is pre create action.
	public EditableWorkgroupPermissionsValue createEditableWorkgroupPermissionsValue(
             Integer userID,
             Integer workgroupID,
             Integer AccessControl)
           {
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
             Integer userID,
             Integer opmModelID,
             Integer AccessControl)
        {
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
             Integer userID,
             Integer collaborativeSessionID,
             Integer AccessControl)
        {
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
	public EnhancedWorkgroupPermissionsValue
            createWorkgroup(EditableWorkgroupValue editableWorkgroupValue) throws Exception {
          EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue = null;
          try {
            enhancedWorkgroupPermissionsValue = workgroupPort.createWorkgroup(
                           getLocalEnhancedUser().getUserID().intValue(),
                           getLocalEnhancedUser().getPassword(),
                           editableWorkgroupValue);
            getLocalEnhancedWorkgroupsPermissions().add(enhancedWorkgroupPermissionsValue);
          }catch(Exception e) { throw e; }

          return enhancedWorkgroupPermissionsValue;
        }

	public EnhancedOpmModelPermissionsValue createOpmModel(EditableOpmModelValue editableOpmModelValue) throws Exception{
          EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue = null;
          try {
            enhancedOpmModelPermissionsValue = opmModelPort.createOpmModel(
                   getLocalEnhancedUser().getUserID().intValue(),
                   getLocalEnhancedUser().getPassword(),
                   editableOpmModelValue);
            getLocalEnhancedOpmModelsPermissions().add(enhancedOpmModelPermissionsValue);
          } catch(Exception e) { throw e; }

          return enhancedOpmModelPermissionsValue;
        }

        public EnhancedCollaborativeSessionPermissionsValue createCollaborativeSession
            (EditableCollaborativeSessionValue editableCollaborativeSessionValue) throws Exception{
          EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue = null;
          try {
            enhancedCollaborativeSessionPermissionsValue = collaborativeSessionPort.createCollaborativeSession(
               getLocalEnhancedUser().getUserID().intValue(),
               getLocalEnhancedUser().getPassword(),
               editableCollaborativeSessionValue);
            getLocalEnhancedCollaborativeSessionsPermissions().add(enhancedCollaborativeSessionPermissionsValue);
          } catch(Exception e) { throw e; }

          return enhancedCollaborativeSessionPermissionsValue;
        }

//Permissions getter functions
        public WorkgroupPermissionsValue fetchWorkgroupPermissionsForUser
            (WorkgroupPermissionsPK workgroupPermissionsPK)throws Exception{

          WorkgroupPermissionsValue workgroupPermissionsValue=null;
          try {
            workgroupPermissionsValue=
                userPort.getWorkgroupPermissionsForUser(
                     getLocalEnhancedUser().getUserID().intValue(),
                     getLocalEnhancedUser().getPassword(),
                     workgroupPermissionsPK);
          } catch(Exception e) { throw e; }
          return workgroupPermissionsValue;
        }

        public OpmModelPermissionsValue fetchOpmModelPermissionsForUser
            (OpmModelPermissionsPK opmModelPermissionsPK)throws Exception{

          OpmModelPermissionsValue opmModelPermissionsValue=null;
          try {
            opmModelPermissionsValue=
                userPort.getOpmModelPermissionsForUser(
                     getLocalEnhancedUser().getUserID().intValue(),
                     getLocalEnhancedUser().getPassword(),
                     opmModelPermissionsPK);
          } catch(Exception e) { throw e; }
          return opmModelPermissionsValue;
        }

        public CollaborativeSessionPermissionsValue fetchCollaborativeSessionPermissionsForUser
            (CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK)throws Exception{

          CollaborativeSessionPermissionsValue collaborativeSessionPermissionsValue=null;
          try {
            collaborativeSessionPermissionsValue=
                userPort.getCollaborativeSessionPermissionsForUser(
                     getLocalEnhancedUser().getUserID().intValue(),
                     getLocalEnhancedUser().getPassword(),
                     collaborativeSessionPermissionsPK);
          } catch(Exception e) { throw e; }
          return  collaborativeSessionPermissionsValue;
        }

//Permissions update functions
	public void updateWorkgroupPermissions
            (EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue)throws Exception{
          try {
            workgroupPort.setWorkgroupPermissions(
              getLocalEnhancedUser().getUserID().intValue(),
              getLocalEnhancedUser().getPassword(),
              editableWorkgroupPermissionsValue);
          } catch(Exception e) { throw e; }
        }

	public void updateOpmModelPermissions
            (EditableOpmModelPermissionsValue editableOpmModelPermissionsValue)throws Exception{
          try {
            opmModelPort.setOpmModelPermissions(
               getLocalEnhancedUser().getUserID().intValue(),
               getLocalEnhancedUser().getPassword(),
               editableOpmModelPermissionsValue);
          } catch(Exception e) { throw e; }
        }

	public void updateCollaborativeSessionPermissions
            (EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue)throws Exception{
          try {
            collaborativeSessionPort.setCollaborativeSessionPermissions(
                getLocalEnhancedUser().getUserID().intValue(),
                getLocalEnhancedUser().getPassword(),
                editableCollaborativeSessionPermissionsValue);
          } catch(Exception e) { throw e; }
        }


//Local DataStructure Update methods , including handing the disable/enable flags.
      private void updateLocalWorkgroup(WorkgroupValue newWorkgroupValue){
        Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue)iterate.next();
          if( newWorkgroupValue.getWorkgroupID().equals(currentWorkgroup.getWorkgroupID())){
            currentWorkgroup.setWorkgroup(newWorkgroupValue);
            getLocalEnhancedWorkgroupsPermissions().set(
                 (getLocalEnhancedWorkgroupsPermissions().indexOf(currentWorkgroup)),
                  currentWorkgroup);
            return;
          }
        }
      }

      private void disableLocalWorkgroup(int workgroupID){
        Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
          if( workgroupID == currentWorkgroup.getWorkgroupID().intValue()){
            currentWorkgroup.getWorkgroup().setEnabled(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalWorkgroup(int workgroupID){
        Iterator iterate = getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
          if( workgroupID == currentWorkgroup.getWorkgroupID().intValue()){
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
            getLocalEnhancedOpmModelsPermissions().set(
                getLocalEnhancedOpmModelsPermissions().indexOf(currentOpmModel),
                currentOpmModel);
            return;
          }
        }
      }

      private void disableLocalOpmModel(int opmModelID){
        Iterator iterate = getLocalEnhancedOpmModelsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
          if( opmModelID == currentOpmModel.getOpmModelID().intValue()){
            currentOpmModel.getOpmModel().setEnabled(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalOpmModel(int opmModelID){
        Iterator iterate = getLocalEnhancedOpmModelsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
          if( opmModelID == currentOpmModel.getOpmModelID().intValue()){
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

      private void disableLocalCollaborativeSession(int collaborativeSessionID){
        Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( collaborativeSessionID == currentCollaborativeSession.getCollaborativeSessionID().intValue()){
            currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalCollaborativeSession(int collaborativeSessionID){
        Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( collaborativeSessionID == currentCollaborativeSession.getCollaborativeSessionID().intValue()){
            currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(true));
            return;
          }
        }
      }

      public void updateLocalCollaborativeSessionToken(int collaborativeSessionID,int tokenHolderID){
        Iterator iterate = getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( collaborativeSessionID==currentCollaborativeSession.getCollaborativeSessionID().intValue()){
            currentCollaborativeSession.getCollaborativeSession().setTokenHolderID(new Integer(tokenHolderID));
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
	public void loginUser(String loginName, String password)throws Exception {
          try {
            setUserID(getUserPort().loginUser(loginName,password));
          } catch(Exception e) {throw e;}

          setLoginName(loginName);
          setPassword(password);
        }

        public void logoutUser()throws Exception {
          try {
            getUserPort().logoutUser(getUserID());
            setUserID(0);
            setLoginName(null);
            setPassword(null);
            setLocalEnhancedUser(null);
            setLocalEnhancedWorkgroupsPermissions(new ArrayList());
            setLocalEnhancedOpmModelsPermissions(new ArrayList());
            setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());

          } catch(Exception e) { throw e;}
        }


	/**
	 * Initialize enhancedUserValue.
	 */
        public void initializeEnhancedUserValue()throws Exception {
          //	Check that initialize has not been called before successful login
          if(getUserID() == 0)
            return;

          setLocalEnhancedUser(null);
          setLocalEnhancedWorkgroupsPermissions(new ArrayList());
          setLocalEnhancedOpmModelsPermissions(new ArrayList());
          setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());

          try {
            setLocalEnhancedUser(getUserPort().getEnhancedUserByPK(
                 getUserID(),
                 getPassword(),
                 getUserID()));
            // Initialize local Data Structure References
            int i=0;
            for (i=0; i<getLocalEnhancedUser().getEnhancedWorkgroupsPermissions().length; i++){
              getLocalEnhancedWorkgroupsPermissions().add(
                   getLocalEnhancedUser().getEnhancedWorkgroupsPermissions()[i]);}

            for (i=0; i<getLocalEnhancedUser().getEnhancedOpmModelsPermissions().length; i++){
              getLocalEnhancedOpmModelsPermissions().add(
		getLocalEnhancedUser().getEnhancedOpmModelsPermissions()[i]);}

            for (i=0; i<getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions().length; i++){
              getLocalEnhancedCollaborativeSessionsPermissions().add(
		getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions()[i]);}

              } catch(Exception e) {throw e;}
            }

	public void updateUser(EditableUserValue editableUserValue)throws Exception{
          try {
            EditableUserValue newEditableUserValue = getUserPort().setUser(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 editableUserValue);

            //Update EditableUserValue fields on LOCAL EnhancedUserValue
            updateEnhancedUserValueWithEditableFields(newEditableUserValue);
            setPassword(getLocalEnhancedUser().getPassword());
            setLoginName(getLocalEnhancedUser().getLoginName());
          } catch (Exception e){throw e;};
        }

	public UserValue getUserByLoginName(String loginName)throws Exception{
          UserValue userValue = null;
          try {
            userValue = getUserPort().getUserByLoginName(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 loginName);
          } catch(Exception e) { throw e; }
          return userValue;
        }

	public UserValue getUserByPK(int userID) throws Exception{
          UserValue userValue = null;
          try {
            userValue = getUserPort().getUserByPK(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  userID);
          } catch(Exception e) { throw e; }
          return userValue;
        }

//Enhanced Getter functions
	public EnhancedWorkgroupValue fatchEnhancedWorkgroup(int workgroupID)throws Exception{
          EnhancedWorkgroupValue enhancedWorkgroupValue = null;
          try {
            enhancedWorkgroupValue = workgroupPort.getEnhancedWorkgroupByPK(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  workgroupID);
          } catch(Exception e) { throw e; }
          return enhancedWorkgroupValue;
        }

	public EnhancedOpmModelValue fatchEnhancedOpmModel(int opmModelID)throws Exception{
          EnhancedOpmModelValue enhancedOpmModelValue = null;
          try {
            enhancedOpmModelValue = opmModelPort.getEnhancedOpmModelByPK(
                   getLocalEnhancedUser().getUserID().intValue(),
                   getLocalEnhancedUser().getPassword(),
                   opmModelID);
          } catch(Exception e) { throw e; }
          return enhancedOpmModelValue;
        }


//The next functions takes care of getting the session's member list, and update it if necessary

//fetch the information from the server and keep it in the coresponding variable
	public EnhancedCollaborativeSessionValue fatchEnhancedCollaborativeSession
            (int collaborativeSessionID)throws Exception{
          enhancedCollaborativeSessionValue = null;
          try {
            enhancedCollaborativeSessionValue = collaborativeSessionPort.getEnhancedCollaborativeSessionByPK(
                    getLocalEnhancedUser().getUserID().intValue(),
                    getLocalEnhancedUser().getPassword(),
                    collaborativeSessionID);
          } catch(Exception e) { throw e; }
          return enhancedCollaborativeSessionValue;
        }

//in case new user is logged in - update its status
        public void updateLocalMemberLoggedInStatus(int memberID){
          int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          for (i=0; i<len; i++) {
            if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
              Integer myAccessControl = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
              myAccessControl = new Integer(myAccessControl.intValue()+PermissionFlags.LOGGEDIN.intValue());
              enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
              return;
            }
          }
        }
//in case user is logged out- update its status
        public void updateLocalMemberLoggedOutStatus(int memberID){
          int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          for (i=0; i<len; i++) {
            if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
              Integer myAccessControl = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
              myAccessControl = new Integer(myAccessControl.intValue()-PermissionFlags.LOGGEDIN.intValue());
              enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
              return;
            }
          }
        }
//return the name of the user that his ID is given as a parameter
        public String getLocalMemberName(int memberID){
          //first check the the Data structure is not null;
          if (enhancedCollaborativeSessionValue == null) return null;

          int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          String memberName=null;
          for (i=0; i<len; i++) {
            if (enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
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
	public WorkgroupValue fatchWorkgroup(int workgroupID)throws Exception{
          WorkgroupValue workgroupValue = null;
          try {
            workgroupValue = workgroupPort.getWorkgroupByPK(
                    getLocalEnhancedUser().getUserID().intValue(),
                    getLocalEnhancedUser().getPassword(),
                    workgroupID);
          } catch(Exception e) { throw e; }
          return workgroupValue;
        }

	public OpmModelValue fatchOpmModel(int opmModelID) throws Exception{
          OpmModelValue opmModelValue = null;
          try {
            opmModelValue = opmModelPort.getOpmModelByPK(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 opmModelID);
          } catch(Exception e) { throw e; }
          return opmModelValue;
        }

	public CollaborativeSessionValue fatchCollaborativeSession
            (int collaborativeSessionID) throws Exception{
          CollaborativeSessionValue collaborativeSessionValue = null;
          try {
            collaborativeSessionValue = collaborativeSessionPort.getCollaborativeSessionByPK(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 collaborativeSessionID);
          } catch(Exception e) { throw e; }
          return collaborativeSessionValue;
        }

//Update functions
	public void updateWorkgroup(EditableWorkgroupValue editableWorkgroupValue) throws Exception{
          WorkgroupValue newWorkgroupValue = null;
          try{
            newWorkgroupValue = workgroupPort.setWorkgroup(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 editableWorkgroupValue);
            updateLocalWorkgroup(newWorkgroupValue);
          } catch(Exception e) { throw e; }
        }

        public void updateOpmModel(EditableOpmModelValue editableOpmModelValue) throws Exception{
          OpmModelValue newOpmModelValue = null;
          try{
            newOpmModelValue = opmModelPort.setOpmModel(
                     getLocalEnhancedUser().getUserID().intValue(),
                     getLocalEnhancedUser().getPassword(),
                     editableOpmModelValue);
            updateLocalOpmModel(newOpmModelValue);
          }catch(Exception e) { throw e; }
        }

        public void updateCollaborativeSession
            (EditableCollaborativeSessionValue editableCollaborativeSessionValue) throws Exception{
		CollaborativeSessionValue newCollaborativeSessionValue = null;
                try{
                  newCollaborativeSessionValue = collaborativeSessionPort.setCollaborativeSession(
                      getLocalEnhancedUser().getUserID().intValue(),
                      getLocalEnhancedUser().getPassword(),
                      editableCollaborativeSessionValue);
                  updateLocalCollaborativeSession(newCollaborativeSessionValue);
                }catch(Exception e) {throw e; }
              }

// OpmModel File Functions
	public CollaborativeSessionFileValue fatchCollaborativeSessionFile(int collaborativeSessionID) throws Exception{

          CollaborativeSessionFileValue collaborativeSessionFileValue =null;
          try{
            collaborativeSessionFileValue =collaborativeSessionPort.getCollaborativeSessionFileByPK(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  collaborativeSessionID);
          }catch (Exception e){throw e;}
          return collaborativeSessionFileValue;
        }

        public FullRevisionValue fatchOpmModelFile(int revisionID) throws Exception{
          FullRevisionValue fullRevisionValue = null;
          try{
            fullRevisionValue =opmModelPort.getRevisionByPK(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  revisionID);
          }catch (Exception e){throw e;}
          return fullRevisionValue;
        }

        public Object[] fatchOpmModelAllRevisions(int opmModelID) throws Exception{
          Object[] metaRevisionsList=null;
          Object meta=null;
          try{
            meta=opmModelPort.getAllRevisions(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  opmModelID);
          }catch (Exception e){throw e;}
          metaRevisionsList =(Object[]) meta;
          return metaRevisionsList;
        }

        public void updateCollaborativeSessionFile
            (CollaborativeSessionFileValue collaborativeSessionFileValue) throws Exception{
          try{
            collaborativeSessionPort.setCollaborativeSessionFileByPK(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 collaborativeSessionFileValue);
          }catch (Exception e){throw e;}
        }

        public int findHigestRevision(Object[] metaRevisionsList) {
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
        public void sessionLogin(int collaborativeSessionID) throws Exception{
          try{
            collaborativeSessionPort.sessionLoginUser(
                                getLocalEnhancedUser().getUserID().intValue(),
                                getLocalEnhancedUser().getPassword(),
                                collaborativeSessionID);
          } catch (Exception e) {throw e;}
        }

        public void sessionLogout(int collaborativeSessionID) throws Exception {
          try{
            collaborativeSessionPort.sessionLogoutUser(
                                getLocalEnhancedUser().getUserID().intValue(),
                                getLocalEnhancedUser().getPassword(),
                                collaborativeSessionID);
          } catch (Exception e) {throw e;}
        }


// Token function
        public boolean requestToken(int collaborativeSessionID) throws Exception{
          boolean result = false;
          try{
            result = collaborativeSessionPort.requestToken(
                             getLocalEnhancedUser().getUserID().intValue(),
                             getLocalEnhancedUser().getPassword(),
                             collaborativeSessionID);
            if (result == true)
              updateLocalCollaborativeSessionToken(collaborativeSessionID,getUserID());
            } catch (Exception e) {throw e;}
          return result;
          }

     public void returnToken(int collaborativeSessionID) throws Exception{
         try{
           collaborativeSessionPort.returnToken(
               getLocalEnhancedUser().getUserID().intValue(),
               getLocalEnhancedUser().getPassword(),
               collaborativeSessionID);
           updateLocalCollaborativeSessionToken(collaborativeSessionID,PermissionFlags.SERVER_USERID.intValue());
           } catch(Exception e){throw e;}
         }


//CVS Functions
         public Object[] preCommitCollaborativeSession(int revisionID) throws Exception{
           Object[] metaRevisionsList=null;
           Object meta=null;
           try{
           meta=opmModelPort.getPreCommit(
                getLocalEnhancedUser().getUserID().intValue(),
	        getLocalEnhancedUser().getPassword(),
		revisionID);
           } catch(Exception e){throw e;}
           metaRevisionsList =(Object[]) meta;
           return metaRevisionsList;
         }



	public  EditableRevisionValue commitCollaborativeSession(
             int sessionID, EditableRevisionValue editableRevisionValue,boolean increaseMajor) throws Exception{
          EditableRevisionValue returnedEditableRevisionValue=null;
          try{
           returnedEditableRevisionValue = collaborativeSessionPort.commitCollaborativeSession(
		getLocalEnhancedUser().getUserID().intValue(),
		getLocalEnhancedUser().getPassword(),
		sessionID,
                editableRevisionValue,
                increaseMajor);
          } catch(Exception e){throw e;}
          return returnedEditableRevisionValue;
        }


//Disable and Enable Functions
	 public void disableOpmModel(int opmModelID) throws Exception{
           try{
             opmModelPort.disableOpmModel(
                    getLocalEnhancedUser().getUserID().intValue(),
                    getLocalEnhancedUser().getPassword(),
                    opmModelID);
             disableLocalOpmModel(opmModelID);
           } catch(Exception e){throw e;}
         }

	 public void enableOpmModel(int opmModelID) throws Exception{
           try{
             opmModelPort.enableOpmModel(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  opmModelID);
             enableLocalOpmModel(opmModelID);
           } catch(Exception e){throw e;}
         }

	 public void disableWorkgroup(int workgroupID)throws Exception{
           try{
             workgroupPort.disableWorkgroup(
                  getLocalEnhancedUser().getUserID().intValue(),
                  getLocalEnhancedUser().getPassword(),
                  workgroupID);
             disableLocalWorkgroup(workgroupID);
           } catch(Exception e){throw e;}
         }

	 public void enableWorkgroup(int workgroupID)throws Exception{
           try{
             workgroupPort.enableWorkgroup(
                   getLocalEnhancedUser().getUserID().intValue(),
                   getLocalEnhancedUser().getPassword(),
                   workgroupID);
             enableLocalWorkgroup(workgroupID);
           } catch(Exception e){throw e;}
         }

         public void disableCollaborativeSession(int collaborativeSessionID) throws Exception{
           try{
             collaborativeSessionPort.disableCollaborativeSession(
                     getLocalEnhancedUser().getUserID().intValue(),
                     getLocalEnhancedUser().getPassword(),
                     collaborativeSessionID);
             disableLocalCollaborativeSession(collaborativeSessionID);
           } catch(Exception e){throw e;}
         }

         public void enablecollaborativeSession(int collaborativeSessionID) throws Exception{
           try{
             collaborativeSessionPort.enableCollaborativeSession(
                 getLocalEnhancedUser().getUserID().intValue(),
                 getLocalEnhancedUser().getPassword(),
                 collaborativeSessionID);
             enableLocalCollaborativeSession(collaborativeSessionID);
           } catch(Exception e){throw e;}
         }

//	Private class functions
	private void updateEnhancedUserValueWithEditableFields(EditableUserValue editableUserValue){
          getLocalEnhancedUser().setLoginName(editableUserValue.getLoginName());
          getLocalEnhancedUser().setFirstName(editableUserValue.getFirstName());
          getLocalEnhancedUser().setLastName(editableUserValue.getLastName());
          getLocalEnhancedUser().setPassword(editableUserValue.getPassword());
          getLocalEnhancedUser().setEmail(editableUserValue.getEmail());
          getLocalEnhancedUser().setDescription(editableUserValue.getDescription());
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
	private int getUserID() {
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
	private void setUserID(int i) {
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
