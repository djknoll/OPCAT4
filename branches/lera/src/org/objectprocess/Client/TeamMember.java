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

			tempURL = this.createURL( majorURLString, this.minorUserServiceUrl );
			//Make a UserService
			this.setUserService(new UserServiceLocator());
			this.setUserPort(this.userService.getUserService(tempURL));

			tempURL = this.createURL( majorURLString, this.minorWorkgroupServiceUrl );
			//Make a WorkgroupService
			this.setWorkgroupService(new WorkgroupServiceLocator());
			this.setWorkgroupPort(this.workgroupService.getWorkgroupService(tempURL));

			tempURL = this.createURL( majorURLString, this.minorOpmModelServiceUrl );
			//Make a OpmModelService
			this.setOpmModelService(new OpmModelServiceLocator());
			this.opmModelPort = this.opmModelService.getOpmModelService(tempURL);

			tempURL = this.createURL( majorURLString, this.minorCollaborativeSessionServiceUrl );
			//Make a CollaborativeSessionService
			this.setCollaborativeSessionService(new CollaborativeSessionServiceLocator());
			this.setCollaborativeSessionPort(this.collaborativeSessionService.getCollaborativeSessionService(tempURL));

			this.setServerAddr(serverAddr);

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
          return !(this.getUserID() == 0);
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
            enhancedWorkgroupPermissionsValue = this.workgroupPort.createWorkgroup(
                           this.getLocalEnhancedUser().getUserID().intValue(),
                           this.getLocalEnhancedUser().getPassword(),
                           editableWorkgroupValue);
            this.getLocalEnhancedWorkgroupsPermissions().add(enhancedWorkgroupPermissionsValue);
          }catch(Exception e) { throw e; }

          return enhancedWorkgroupPermissionsValue;
        }

	public EnhancedOpmModelPermissionsValue createOpmModel(EditableOpmModelValue editableOpmModelValue) throws Exception{
          EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue = null;
          try {
            enhancedOpmModelPermissionsValue = this.opmModelPort.createOpmModel(
                   this.getLocalEnhancedUser().getUserID().intValue(),
                   this.getLocalEnhancedUser().getPassword(),
                   editableOpmModelValue);
            this.getLocalEnhancedOpmModelsPermissions().add(enhancedOpmModelPermissionsValue);
          } catch(Exception e) { throw e; }

          return enhancedOpmModelPermissionsValue;
        }

        public EnhancedCollaborativeSessionPermissionsValue createCollaborativeSession
            (EditableCollaborativeSessionValue editableCollaborativeSessionValue) throws Exception{
          EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue = null;
          try {
            enhancedCollaborativeSessionPermissionsValue = this.collaborativeSessionPort.createCollaborativeSession(
               this.getLocalEnhancedUser().getUserID().intValue(),
               this.getLocalEnhancedUser().getPassword(),
               editableCollaborativeSessionValue);
            this.getLocalEnhancedCollaborativeSessionsPermissions().add(enhancedCollaborativeSessionPermissionsValue);
          } catch(Exception e) { throw e; }

          return enhancedCollaborativeSessionPermissionsValue;
        }

//Permissions getter functions
        public WorkgroupPermissionsValue fetchWorkgroupPermissionsForUser
            (WorkgroupPermissionsPK workgroupPermissionsPK)throws Exception{

          WorkgroupPermissionsValue workgroupPermissionsValue=null;
          try {
            workgroupPermissionsValue=
                this.userPort.getWorkgroupPermissionsForUser(
                     this.getLocalEnhancedUser().getUserID().intValue(),
                     this.getLocalEnhancedUser().getPassword(),
                     workgroupPermissionsPK);
          } catch(Exception e) { throw e; }
          return workgroupPermissionsValue;
        }

        public OpmModelPermissionsValue fetchOpmModelPermissionsForUser
            (OpmModelPermissionsPK opmModelPermissionsPK)throws Exception{

          OpmModelPermissionsValue opmModelPermissionsValue=null;
          try {
            opmModelPermissionsValue=
                this.userPort.getOpmModelPermissionsForUser(
                     this.getLocalEnhancedUser().getUserID().intValue(),
                     this.getLocalEnhancedUser().getPassword(),
                     opmModelPermissionsPK);
          } catch(Exception e) { throw e; }
          return opmModelPermissionsValue;
        }

        public CollaborativeSessionPermissionsValue fetchCollaborativeSessionPermissionsForUser
            (CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK)throws Exception{

          CollaborativeSessionPermissionsValue collaborativeSessionPermissionsValue=null;
          try {
            collaborativeSessionPermissionsValue=
                this.userPort.getCollaborativeSessionPermissionsForUser(
                     this.getLocalEnhancedUser().getUserID().intValue(),
                     this.getLocalEnhancedUser().getPassword(),
                     collaborativeSessionPermissionsPK);
          } catch(Exception e) { throw e; }
          return  collaborativeSessionPermissionsValue;
        }

//Permissions update functions
	public void updateWorkgroupPermissions
            (EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue)throws Exception{
          try {
            this.workgroupPort.setWorkgroupPermissions(
              this.getLocalEnhancedUser().getUserID().intValue(),
              this.getLocalEnhancedUser().getPassword(),
              editableWorkgroupPermissionsValue);
          } catch(Exception e) { throw e; }
        }

	public void updateOpmModelPermissions
            (EditableOpmModelPermissionsValue editableOpmModelPermissionsValue)throws Exception{
          try {
            this.opmModelPort.setOpmModelPermissions(
               this.getLocalEnhancedUser().getUserID().intValue(),
               this.getLocalEnhancedUser().getPassword(),
               editableOpmModelPermissionsValue);
          } catch(Exception e) { throw e; }
        }

	public void updateCollaborativeSessionPermissions
            (EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue)throws Exception{
          try {
            this.collaborativeSessionPort.setCollaborativeSessionPermissions(
                this.getLocalEnhancedUser().getUserID().intValue(),
                this.getLocalEnhancedUser().getPassword(),
                editableCollaborativeSessionPermissionsValue);
          } catch(Exception e) { throw e; }
        }


//Local DataStructure Update methods , including handing the disable/enable flags.
      private void updateLocalWorkgroup(WorkgroupValue newWorkgroupValue){
        Iterator iterate = this.getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue)iterate.next();
          if( newWorkgroupValue.getWorkgroupID().equals(currentWorkgroup.getWorkgroupID())){
            currentWorkgroup.setWorkgroup(newWorkgroupValue);
            this.getLocalEnhancedWorkgroupsPermissions().set(
                 (this.getLocalEnhancedWorkgroupsPermissions().indexOf(currentWorkgroup)),
                  currentWorkgroup);
            return;
          }
        }
      }

      private void disableLocalWorkgroup(int workgroupID){
        Iterator iterate = this.getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
          if( workgroupID == currentWorkgroup.getWorkgroupID().intValue()){
            currentWorkgroup.getWorkgroup().setEnabled(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalWorkgroup(int workgroupID){
        Iterator iterate = this.getLocalEnhancedWorkgroupsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedWorkgroupPermissionsValue currentWorkgroup = (EnhancedWorkgroupPermissionsValue) iterate.next();
          if( workgroupID == currentWorkgroup.getWorkgroupID().intValue()){
            currentWorkgroup.getWorkgroup().setEnabled(new Boolean(true));
            return;
          }
        }
      }

      private void updateLocalOpmModel(OpmModelValue newOpmModelValue){
        Iterator iterate = this.getLocalEnhancedOpmModelsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
          if( newOpmModelValue.getOpmModelID().equals(currentOpmModel.getOpmModelID())){
            currentOpmModel.setOpmModel(newOpmModelValue);
            this.getLocalEnhancedOpmModelsPermissions().set(
                this.getLocalEnhancedOpmModelsPermissions().indexOf(currentOpmModel),
                currentOpmModel);
            return;
          }
        }
      }

      private void disableLocalOpmModel(int opmModelID){
        Iterator iterate = this.getLocalEnhancedOpmModelsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
          if( opmModelID == currentOpmModel.getOpmModelID().intValue()){
            currentOpmModel.getOpmModel().setEnabled(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalOpmModel(int opmModelID){
        Iterator iterate = this.getLocalEnhancedOpmModelsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedOpmModelPermissionsValue currentOpmModel = (EnhancedOpmModelPermissionsValue) iterate.next();
          if( opmModelID == currentOpmModel.getOpmModelID().intValue()){
            currentOpmModel.getOpmModel().setEnabled(new Boolean(true));
            return;
          }
        }
      }

      public void updateLocalCollaborativeSession(CollaborativeSessionValue newCollaborativeSessionValue){
        Iterator iterate = this.getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( newCollaborativeSessionValue.getCollaborativeSessionID().equals(currentCollaborativeSession.getCollaborativeSessionID())){
            currentCollaborativeSession.setCollaborativeSession(newCollaborativeSessionValue);
                this.getLocalEnhancedCollaborativeSessionsPermissions().set(
                this.getLocalEnhancedCollaborativeSessionsPermissions().indexOf(currentCollaborativeSession),
                currentCollaborativeSession);
            return;
          }
        }
      }

      private void disableLocalCollaborativeSession(int collaborativeSessionID){
        Iterator iterate = this.getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( collaborativeSessionID == currentCollaborativeSession.getCollaborativeSessionID().intValue()){
            currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(false));
            return;
          }
        }
      }

      private void enableLocalCollaborativeSession(int collaborativeSessionID){
        Iterator iterate = this.getLocalEnhancedCollaborativeSessionsPermissions().iterator();
        while(iterate.hasNext()) {
          EnhancedCollaborativeSessionPermissionsValue currentCollaborativeSession = (EnhancedCollaborativeSessionPermissionsValue) iterate.next();
          if( collaborativeSessionID == currentCollaborativeSession.getCollaborativeSessionID().intValue()){
            currentCollaborativeSession.getCollaborativeSession().setTerminated(new Boolean(true));
            return;
          }
        }
      }

      public void updateLocalCollaborativeSessionToken(int collaborativeSessionID,int tokenHolderID){
        Iterator iterate = this.getLocalEnhancedCollaborativeSessionsPermissions().iterator();
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
            this.setUserID(this.getUserPort().loginUser(loginName,password));
          } catch(Exception e) {throw e;}

          this.setLoginName(loginName);
          this.setPassword(password);
        }

        public void logoutUser()throws Exception {
          try {
            this.getUserPort().logoutUser(this.getUserID());
            this.setUserID(0);
            this.setLoginName(null);
            this.setPassword(null);
            this.setLocalEnhancedUser(null);
            this.setLocalEnhancedWorkgroupsPermissions(new ArrayList());
            this.setLocalEnhancedOpmModelsPermissions(new ArrayList());
            this.setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());

          } catch(Exception e) { throw e;}
        }


	/**
	 * Initialize enhancedUserValue.
	 */
        public void initializeEnhancedUserValue()throws Exception {
          //	Check that initialize has not been called before successful login
          if(this.getUserID() == 0) {
			return;
		}

          this.setLocalEnhancedUser(null);
          this.setLocalEnhancedWorkgroupsPermissions(new ArrayList());
          this.setLocalEnhancedOpmModelsPermissions(new ArrayList());
          this.setLocalEnhancedCollaborativeSessionsPermissions(new ArrayList());

          try {
            this.setLocalEnhancedUser(this.getUserPort().getEnhancedUserByPK(
                 this.getUserID(),
                 this.getPassword(),
                 this.getUserID()));
            // Initialize local Data Structure References
            int i=0;
            for (i=0; i<this.getLocalEnhancedUser().getEnhancedWorkgroupsPermissions().length; i++){
              this.getLocalEnhancedWorkgroupsPermissions().add(
                   this.getLocalEnhancedUser().getEnhancedWorkgroupsPermissions()[i]);}

            for (i=0; i<this.getLocalEnhancedUser().getEnhancedOpmModelsPermissions().length; i++){
              this.getLocalEnhancedOpmModelsPermissions().add(
		this.getLocalEnhancedUser().getEnhancedOpmModelsPermissions()[i]);}

            for (i=0; i<this.getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions().length; i++){
              this.getLocalEnhancedCollaborativeSessionsPermissions().add(
		this.getLocalEnhancedUser().getEnhancedCollaborativeSessionsPermissions()[i]);}

              } catch(Exception e) {throw e;}
            }

	public void updateUser(EditableUserValue editableUserValue)throws Exception{
          try {
            EditableUserValue newEditableUserValue = this.getUserPort().setUser(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 editableUserValue);

            //Update EditableUserValue fields on LOCAL EnhancedUserValue
            this.updateEnhancedUserValueWithEditableFields(newEditableUserValue);
            this.setPassword(this.getLocalEnhancedUser().getPassword());
            this.setLoginName(this.getLocalEnhancedUser().getLoginName());
          } catch (Exception e){throw e;};
        }

	public UserValue getUserByLoginName(String loginName)throws Exception{
          UserValue userValue = null;
          try {
            userValue = this.getUserPort().getUserByLoginName(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 loginName);
          } catch(Exception e) { throw e; }
          return userValue;
        }

	public UserValue getUserByPK(int userID) throws Exception{
          UserValue userValue = null;
          try {
            userValue = this.getUserPort().getUserByPK(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  userID);
          } catch(Exception e) { throw e; }
          return userValue;
        }

//Enhanced Getter functions
	public EnhancedWorkgroupValue fatchEnhancedWorkgroup(int workgroupID)throws Exception{
          EnhancedWorkgroupValue enhancedWorkgroupValue = null;
          try {
            enhancedWorkgroupValue = this.workgroupPort.getEnhancedWorkgroupByPK(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  workgroupID);
          } catch(Exception e) { throw e; }
          return enhancedWorkgroupValue;
        }

	public EnhancedOpmModelValue fatchEnhancedOpmModel(int opmModelID)throws Exception{
          EnhancedOpmModelValue enhancedOpmModelValue = null;
          try {
            enhancedOpmModelValue = this.opmModelPort.getEnhancedOpmModelByPK(
                   this.getLocalEnhancedUser().getUserID().intValue(),
                   this.getLocalEnhancedUser().getPassword(),
                   opmModelID);
          } catch(Exception e) { throw e; }
          return enhancedOpmModelValue;
        }


//The next functions takes care of getting the session's member list, and update it if necessary

//fetch the information from the server and keep it in the coresponding variable
	public EnhancedCollaborativeSessionValue fatchEnhancedCollaborativeSession
            (int collaborativeSessionID)throws Exception{
          this.enhancedCollaborativeSessionValue = null;
          try {
            this.enhancedCollaborativeSessionValue = this.collaborativeSessionPort.getEnhancedCollaborativeSessionByPK(
                    this.getLocalEnhancedUser().getUserID().intValue(),
                    this.getLocalEnhancedUser().getPassword(),
                    collaborativeSessionID);
          } catch(Exception e) { throw e; }
          return this.enhancedCollaborativeSessionValue;
        }

//in case new user is logged in - update its status
        public void updateLocalMemberLoggedInStatus(int memberID){
          int len = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          for (i=0; i<len; i++) {
            if (this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
              Integer myAccessControl = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
              myAccessControl = new Integer(myAccessControl.intValue()+PermissionFlags.LOGGEDIN.intValue());
              this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
              return;
            }
          }
        }
//in case user is logged out- update its status
        public void updateLocalMemberLoggedOutStatus(int memberID){
          int len = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          for (i=0; i<len; i++) {
            if (this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
              Integer myAccessControl = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
              myAccessControl = new Integer(myAccessControl.intValue()-PermissionFlags.LOGGEDIN.intValue());
              this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].setAccessControl(myAccessControl);
              return;
            }
          }
        }
//return the name of the user that his ID is given as a parameter
        public String getLocalMemberName(int memberID){
          //first check the the Data structure is not null;
          if (this.enhancedCollaborativeSessionValue == null) {
			return null;
		}

          int len = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          String memberName=null;
          for (i=0; i<len; i++) {
            if (this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUserID().intValue() == memberID) {
              memberName = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser().getFirstName()+ " " +
                  this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser().getLastName();
              return memberName;
            }
          }
          return memberName;
        }
//this function returns the numbers of loggedIn users in the active session, includung the user itself.
        public int numOfLoggedInMembers(){
          //first check the the Data structure is not null;
          if (this.enhancedCollaborativeSessionValue == null) {
			return 0;
		}

          int len = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
          int i;
          int num=0;
          for (i=0; i<len; i++) {
            Integer accessControl = this.enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getAccessControl();
            if (((accessControl.intValue()) & (PermissionFlags.LOGGEDIN.intValue())) == PermissionFlags.LOGGEDIN.intValue()) {
				num++;
			}
          }
          return num;
        }


//Getter functions
	public WorkgroupValue fatchWorkgroup(int workgroupID)throws Exception{
          WorkgroupValue workgroupValue = null;
          try {
            workgroupValue = this.workgroupPort.getWorkgroupByPK(
                    this.getLocalEnhancedUser().getUserID().intValue(),
                    this.getLocalEnhancedUser().getPassword(),
                    workgroupID);
          } catch(Exception e) { throw e; }
          return workgroupValue;
        }

	public OpmModelValue fatchOpmModel(int opmModelID) throws Exception{
          OpmModelValue opmModelValue = null;
          try {
            opmModelValue = this.opmModelPort.getOpmModelByPK(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 opmModelID);
          } catch(Exception e) { throw e; }
          return opmModelValue;
        }

	public CollaborativeSessionValue fatchCollaborativeSession
            (int collaborativeSessionID) throws Exception{
          CollaborativeSessionValue collaborativeSessionValue = null;
          try {
            collaborativeSessionValue = this.collaborativeSessionPort.getCollaborativeSessionByPK(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 collaborativeSessionID);
          } catch(Exception e) { throw e; }
          return collaborativeSessionValue;
        }

//Update functions
	public void updateWorkgroup(EditableWorkgroupValue editableWorkgroupValue) throws Exception{
          WorkgroupValue newWorkgroupValue = null;
          try{
            newWorkgroupValue = this.workgroupPort.setWorkgroup(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 editableWorkgroupValue);
            this.updateLocalWorkgroup(newWorkgroupValue);
          } catch(Exception e) { throw e; }
        }

        public void updateOpmModel(EditableOpmModelValue editableOpmModelValue) throws Exception{
          OpmModelValue newOpmModelValue = null;
          try{
            newOpmModelValue = this.opmModelPort.setOpmModel(
                     this.getLocalEnhancedUser().getUserID().intValue(),
                     this.getLocalEnhancedUser().getPassword(),
                     editableOpmModelValue);
            this.updateLocalOpmModel(newOpmModelValue);
          }catch(Exception e) { throw e; }
        }

        public void updateCollaborativeSession
            (EditableCollaborativeSessionValue editableCollaborativeSessionValue) throws Exception{
		CollaborativeSessionValue newCollaborativeSessionValue = null;
                try{
                  newCollaborativeSessionValue = this.collaborativeSessionPort.setCollaborativeSession(
                      this.getLocalEnhancedUser().getUserID().intValue(),
                      this.getLocalEnhancedUser().getPassword(),
                      editableCollaborativeSessionValue);
                  this.updateLocalCollaborativeSession(newCollaborativeSessionValue);
                }catch(Exception e) {throw e; }
              }

// OpmModel File Functions
	public CollaborativeSessionFileValue fatchCollaborativeSessionFile(int collaborativeSessionID) throws Exception{

          CollaborativeSessionFileValue collaborativeSessionFileValue =null;
          try{
            collaborativeSessionFileValue =this.collaborativeSessionPort.getCollaborativeSessionFileByPK(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  collaborativeSessionID);
          }catch (Exception e){throw e;}
          return collaborativeSessionFileValue;
        }

        public FullRevisionValue fatchOpmModelFile(int revisionID) throws Exception{
          FullRevisionValue fullRevisionValue = null;
          try{
            fullRevisionValue =this.opmModelPort.getRevisionByPK(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  revisionID);
          }catch (Exception e){throw e;}
          return fullRevisionValue;
        }

        public Object[] fatchOpmModelAllRevisions(int opmModelID) throws Exception{
          Object[] metaRevisionsList=null;
          Object meta=null;
          try{
            meta=this.opmModelPort.getAllRevisions(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  opmModelID);
          }catch (Exception e){throw e;}
          metaRevisionsList =(Object[]) meta;
          return metaRevisionsList;
        }

        public void updateCollaborativeSessionFile
            (CollaborativeSessionFileValue collaborativeSessionFileValue) throws Exception{
          try{
            this.collaborativeSessionPort.setCollaborativeSessionFileByPK(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 collaborativeSessionFileValue);
          }catch (Exception e){throw e;}
        }

        public int findHigestRevision(Object[] metaRevisionsList) {
               if (metaRevisionsList==null) {
				return -1;
			}

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
            this.collaborativeSessionPort.sessionLoginUser(
                                this.getLocalEnhancedUser().getUserID().intValue(),
                                this.getLocalEnhancedUser().getPassword(),
                                collaborativeSessionID);
          } catch (Exception e) {throw e;}
        }

        public void sessionLogout(int collaborativeSessionID) throws Exception {
          try{
            this.collaborativeSessionPort.sessionLogoutUser(
                                this.getLocalEnhancedUser().getUserID().intValue(),
                                this.getLocalEnhancedUser().getPassword(),
                                collaborativeSessionID);
          } catch (Exception e) {throw e;}
        }


// Token function
        public boolean requestToken(int collaborativeSessionID) throws Exception{
          boolean result = false;
          try{
            result = this.collaborativeSessionPort.requestToken(
                             this.getLocalEnhancedUser().getUserID().intValue(),
                             this.getLocalEnhancedUser().getPassword(),
                             collaborativeSessionID);
            if (result == true) {
				this.updateLocalCollaborativeSessionToken(collaborativeSessionID,this.getUserID());
			}
            } catch (Exception e) {throw e;}
          return result;
          }

     public void returnToken(int collaborativeSessionID) throws Exception{
         try{
           this.collaborativeSessionPort.returnToken(
               this.getLocalEnhancedUser().getUserID().intValue(),
               this.getLocalEnhancedUser().getPassword(),
               collaborativeSessionID);
           this.updateLocalCollaborativeSessionToken(collaborativeSessionID,PermissionFlags.SERVER_USERID.intValue());
           } catch(Exception e){throw e;}
         }


//CVS Functions
         public Object[] preCommitCollaborativeSession(int revisionID) throws Exception{
           Object[] metaRevisionsList=null;
           Object meta=null;
           try{
           meta=this.opmModelPort.getPreCommit(
                this.getLocalEnhancedUser().getUserID().intValue(),
	        this.getLocalEnhancedUser().getPassword(),
		revisionID);
           } catch(Exception e){throw e;}
           metaRevisionsList =(Object[]) meta;
           return metaRevisionsList;
         }



	public  EditableRevisionValue commitCollaborativeSession(
             int sessionID, EditableRevisionValue editableRevisionValue,boolean increaseMajor) throws Exception{
          EditableRevisionValue returnedEditableRevisionValue=null;
          try{
           returnedEditableRevisionValue = this.collaborativeSessionPort.commitCollaborativeSession(
		this.getLocalEnhancedUser().getUserID().intValue(),
		this.getLocalEnhancedUser().getPassword(),
		sessionID,
                editableRevisionValue,
                increaseMajor);
          } catch(Exception e){throw e;}
          return returnedEditableRevisionValue;
        }


//Disable and Enable Functions
	 public void disableOpmModel(int opmModelID) throws Exception{
           try{
             this.opmModelPort.disableOpmModel(
                    this.getLocalEnhancedUser().getUserID().intValue(),
                    this.getLocalEnhancedUser().getPassword(),
                    opmModelID);
             this.disableLocalOpmModel(opmModelID);
           } catch(Exception e){throw e;}
         }

	 public void enableOpmModel(int opmModelID) throws Exception{
           try{
             this.opmModelPort.enableOpmModel(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  opmModelID);
             this.enableLocalOpmModel(opmModelID);
           } catch(Exception e){throw e;}
         }

	 public void disableWorkgroup(int workgroupID)throws Exception{
           try{
             this.workgroupPort.disableWorkgroup(
                  this.getLocalEnhancedUser().getUserID().intValue(),
                  this.getLocalEnhancedUser().getPassword(),
                  workgroupID);
             this.disableLocalWorkgroup(workgroupID);
           } catch(Exception e){throw e;}
         }

	 public void enableWorkgroup(int workgroupID)throws Exception{
           try{
             this.workgroupPort.enableWorkgroup(
                   this.getLocalEnhancedUser().getUserID().intValue(),
                   this.getLocalEnhancedUser().getPassword(),
                   workgroupID);
             this.enableLocalWorkgroup(workgroupID);
           } catch(Exception e){throw e;}
         }

         public void disableCollaborativeSession(int collaborativeSessionID) throws Exception{
           try{
             this.collaborativeSessionPort.disableCollaborativeSession(
                     this.getLocalEnhancedUser().getUserID().intValue(),
                     this.getLocalEnhancedUser().getPassword(),
                     collaborativeSessionID);
             this.disableLocalCollaborativeSession(collaborativeSessionID);
           } catch(Exception e){throw e;}
         }

         public void enablecollaborativeSession(int collaborativeSessionID) throws Exception{
           try{
             this.collaborativeSessionPort.enableCollaborativeSession(
                 this.getLocalEnhancedUser().getUserID().intValue(),
                 this.getLocalEnhancedUser().getPassword(),
                 collaborativeSessionID);
             this.enableLocalCollaborativeSession(collaborativeSessionID);
           } catch(Exception e){throw e;}
         }

//	Private class functions
	private void updateEnhancedUserValueWithEditableFields(EditableUserValue editableUserValue){
          this.getLocalEnhancedUser().setLoginName(editableUserValue.getLoginName());
          this.getLocalEnhancedUser().setFirstName(editableUserValue.getFirstName());
          this.getLocalEnhancedUser().setLastName(editableUserValue.getLastName());
          this.getLocalEnhancedUser().setPassword(editableUserValue.getPassword());
          this.getLocalEnhancedUser().setEmail(editableUserValue.getEmail());
          this.getLocalEnhancedUser().setDescription(editableUserValue.getDescription());
        }

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedCollaborativeSessionsPermissions() {
          return this.localEnhancedCollaborativeSessionsPermissions;
	}

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedOpmModelsPermissions() {
          return this.localEnhancedOpmModelsPermissions;
	}

	/**
	 * @return
	 */
	public EnhancedUserValue getLocalEnhancedUser() {
          return this.localEnhancedUser;
	}

	/**
	 * @return
	 */
	public ArrayList getLocalEnhancedWorkgroupsPermissions() {
          return this.localEnhancedWorkgroupsPermissions;
	}

	/**
	 * @return
	 */
	private String getPassword() {
          return this.password;
	}

	/**
	 * @return
	 */
	private int getUserID() {
          return this.userID;
	}

	/**
	 * @return
	 */
	public UserAccessLocal getUserPort() {
          return this.userPort;
	}

	/**
	 * @param local
	 */
	private void setCollaborativeSessionPort(CollaborativeSessionAccessLocal local) {
          this.collaborativeSessionPort = local;
	}

	/**
	 * @param locator
	 */
	private void setCollaborativeSessionService(CollaborativeSessionServiceLocator locator) {
          this.collaborativeSessionService = locator;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedCollaborativeSessionsPermissions(ArrayList list) {
          this.localEnhancedCollaborativeSessionsPermissions = list;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedOpmModelsPermissions(ArrayList list) {
          this.localEnhancedOpmModelsPermissions = list;
	}

	/**
	 * @param value
	 */
        private void setLocalEnhancedUser(EnhancedUserValue value) {
          this.localEnhancedUser = value;
	}

	/**
	 * @param list
	 */
	private void setLocalEnhancedWorkgroupsPermissions(ArrayList list) {
          this.localEnhancedWorkgroupsPermissions = list;
	}

	/**
	 * @param string
	 */
	private void setLoginName(String string) {
          this.loginName = string;
	}

	/**
	 * @param locator
	 */
	private void setOpmModelService(OpmModelServiceLocator locator) {
          this.opmModelService = locator;
	}

	/**
	 * @param string
	 */
	private void setPassword(String string) {
          this.password = string;
	}

	/**
	 * @param i
	 */
	private void setUserID(int i) {
          this.userID = i;
	}

	/**
	 * @param local
	 */
	private void setUserPort(UserAccessLocal local) {
          this.userPort = local;
	}

	/**
	 * @param locator
	 */
	private void setUserService(UserServiceLocator locator) {
          this.userService = locator;
	}

	/**
	 * @param local
	 */
	private void setWorkgroupPort(WorkgroupAccessLocal local) {
          this.workgroupPort = local;
	}

	/**
	 * @param locator
	 */
	private void setWorkgroupService(WorkgroupServiceLocator locator) {
          this.workgroupService = locator;
	}

	/**
	 * @return
	 */
	public String getServerAddr() {
          return this.serverAddr;
	}

	/**
	 * @param string
	 */
	public void setServerAddr(String string) {
          this.serverAddr = string;
	}



	public TeamMember(int debug)
	{
          //	Initialize the TeamMember with localhost
          try {
            //Make a UserService
            this.setUserService(new UserServiceLocator());
            this.setUserPort(this.userService.getUserService());

            //Make a WorkgroupService
            this.setWorkgroupService(new WorkgroupServiceLocator());
            this.setWorkgroupPort(this.workgroupService.getWorkgroupService());

            //Make a OpmModelService
            this.setOpmModelService(new OpmModelServiceLocator());
            this.opmModelPort = this.opmModelService.getOpmModelService();

            //Make a CollaborativeSessionService
            this.setCollaborativeSessionService(new CollaborativeSessionServiceLocator());
            this.setCollaborativeSessionPort(this.collaborativeSessionService.getCollaborativeSessionService());

          } catch (Exception e) {e.printStackTrace();};
        }

	public String getLoginName() {
		return loginName;
	}


}
