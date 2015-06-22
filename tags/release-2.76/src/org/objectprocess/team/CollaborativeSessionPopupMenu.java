package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.standard.StandardImages;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;

// class for the session PopUp menu
public class CollaborativeSessionPopupMenu extends JPopupMenu {
      protected DefaultMutableTreeNode myNode;
      protected TeamMember myTeamMember;
      protected Opcat2 myOpcat2;

      protected Integer myUserID;
      protected EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;
      protected ActiveCollaborativeSession activeCollaborativeSession;

      public CollaborativeSessionPopupMenu(DefaultMutableTreeNode node,
                                           TeamMember teamMember,
                                           EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue,
                                           Opcat2 opcat2) {
          super();
          myNode = node;
          myTeamMember = teamMember;
          myOpcat2 = opcat2;
          myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;
          myUserID = myEnhancedCollaborativeSessionPermissionsValue.getUserID();
          activeCollaborativeSession = myOpcat2.getActiveCollaborativeSession();


          //check if the session is the cuurect active session if yes, present the extend menu,
          //else present the minimal menu.
          Integer currentSessionID = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID();

              this.add(loginSessionAction);
              this.add(saveSessionAction);
              this.add(commitSessionAction);
              this.add(  new JSeparator());
              this.add(getTokenAction);
              this.add(returnTokenAction);
              this.add(  new JSeparator());
              this.add(showUsersListAction);
              this.add(accessControlAction);
              this.add(collaborativeSessionDetailsAction);
              this.add(disableCollaborativeSessionAction);
              this.add(  new JSeparator());
              this.add(logoutSessionAaction);
              this.add(uploadSessionAction);

              //first disable part of the option according to the user's permissions flag.
              int accessControlIntValue = myEnhancedCollaborativeSessionPermissionsValue.getAccessControl().intValue();
              //ignore lgggedin bit in the flag.
              if (accessControlIntValue >= PermissionFlags.LOGGEDIN.intValue())
                accessControlIntValue-=PermissionFlags.LOGGEDIN.intValue();

              if (accessControlIntValue < PermissionFlags.ADMINISTRATOR.intValue())
                disableCollaborativeSessionAction.setEnabled(false);
              if (accessControlIntValue < PermissionFlags.COMMITTER.intValue())
                commitSessionAction.setEnabled(false);
              if (accessControlIntValue < PermissionFlags.EDITOR.intValue()) {
                saveSessionAction.setEnabled(false);
                uploadSessionAction.setEnabled(false);
                getTokenAction.setEnabled(false);
                returnTokenAction.setEnabled(false);
              }

              //second, disable part of the options according to the session status.
              //if no session is currently opened- disable few of the options
              if (activeCollaborativeSession ==null) {
                saveSessionAction.setEnabled(false);
                uploadSessionAction.setEnabled(false);
                commitSessionAction.setEnabled(false);
                getTokenAction.setEnabled(false);
                returnTokenAction.setEnabled(false);
                logoutSessionAaction.setEnabled(false);
                return;
              }

              //if the current sesion is the open session
              if (currentSessionID.equals(activeCollaborativeSession.getSessionID())) {
                loginSessionAction.setEnabled(false);
                disableCollaborativeSessionAction.setEnabled(false);
                if (!(isUserTokenHolder(myUserID))) {
                  saveSessionAction.setEnabled(false);
                  uploadSessionAction.setEnabled(false);
                  commitSessionAction.setEnabled(false);
                  returnTokenAction.setEnabled(false);
                }
                else {
                  getTokenAction.setEnabled(false);
                }
                return;
              }

              //if the curreent session is not the opened session
              if (!(currentSessionID.equals(activeCollaborativeSession.getSessionID()))) {
                loginSessionAction.setEnabled(false);
                saveSessionAction.setEnabled(false);
                uploadSessionAction.setEnabled(false);
                commitSessionAction.setEnabled(false);
                getTokenAction.setEnabled(false);
                returnTokenAction.setEnabled(false);
                logoutSessionAaction.setEnabled(false);
                return;
                }
            }

        Action getTokenAction = new AbstractAction("Get Token", OPCATeamImages.GETTOKEN){
          public void actionPerformed(ActionEvent e){
            //submit request to server -> ask for the token.
            //if the retrun value is true , the user got the token ->update the session details.
            if (isUserTokenHolder(myUserID))
              JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                            "You are the token holder!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
            else {
              //if the server is the token holder - the user can request the token,
              //if not - the user has to wait until the token is returned to the server
              if (isUserTokenHolder(PermissionFlags.SERVER_USERID))
                activeCollaborativeSession.getToken();
              else
                JOptionPane.showMessageDialog(Opcat2.getFrame(),
                              "You can request the token only when the server holds it.",
                              "Error",
                              JOptionPane.ERROR_MESSAGE);
      }
    }
  };


        Action returnTokenAction = new AbstractAction("Return Token", OPCATeamImages.RETURNTOKEN){
          public void actionPerformed(ActionEvent e){
            int retValue;

            //The user requests to retrun the token -> check if the token is here at all!
            //if the user is not the token holder ->
            if (!(isUserTokenHolder(myUserID)))
              JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                            "You are not the Token Holder!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
            else {
              retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                                       "Do you want to save the model on the server?",
                                                       "Exit Session",
                                                       JOptionPane.YES_NO_CANCEL_OPTION,
                                                       JOptionPane.QUESTION_MESSAGE);
              switch (retValue) {
                case JOptionPane.YES_OPTION:
                  if (activeCollaborativeSession!=null)
                    activeCollaborativeSession.saveSession();
                  break;

                case JOptionPane.CANCEL_OPTION:
                  return;
              }
              //now, return the token back to server
              activeCollaborativeSession.returnToken();
            }
          }
        };


        Action saveSessionAction = new AbstractAction("Save Session On The Server", OPCATeamImages.SAVE){
          public void actionPerformed(ActionEvent e){
            if (activeCollaborativeSession!=null) {
              //reset token timer
              myOpcat2.getActiveCollaborativeSession().getTokenTimer().setModelSavedFlag(true);
              activeCollaborativeSession.saveSession();
            }
          }
        };


        Action uploadSessionAction = new AbstractAction("upload Session To The Server", StandardImages.SAVE){
          public void actionPerformed(ActionEvent e){
            if (myOpcat2.isProjectOpened()) {
              uploadSession();
            }
          }
          };


        Action loginSessionAction = new AbstractAction("Login Session", OPCATeamImages.LOGIN){
          public void actionPerformed(ActionEvent e){
            if (myOpcat2.isProjectOpened()) {
              JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                            "Close the current model, then login to this session.",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
              return;
            }

            if (activeCollaborativeSession != null) {
              JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                            "Close the current session,then log into this session",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
              return;
            }
            //create activeCollaborativeSession object
            activeCollaborativeSession = new
                ActiveCollaborativeSession(myTeamMember,
                                           myEnhancedCollaborativeSessionPermissionsValue,
                                           myOpcat2);
            myOpcat2.setActiveCollaborativeSession(activeCollaborativeSession);
            try {
              activeCollaborativeSession.loginToSession();
            }catch (Exception exception) {
              myOpcat2.setActiveCollaborativeSession(null);
            }
          }
        };

        Action commitSessionAction = new AbstractAction("Commit & Close Session", OPCATeamImages.COMMIT){
          public void actionPerformed(ActionEvent e){
            CommitConfirmationDialog commitConfirmationDialg = new CommitConfirmationDialog(myOpcat2);
          }
        };


        Action collaborativeSessionDetailsAction = new AbstractAction("Show/Edit Details", OPCATeamImages.DOCUMENT){
          public void actionPerformed(ActionEvent e){
            CollaborativeSessionValueDialog collaborativeSessionValueDialog =
                new CollaborativeSessionValueDialog(myTeamMember,
                                                    myEnhancedCollaborativeSessionPermissionsValue);
            }
        };

        Action disableCollaborativeSessionAction = new AbstractAction("Disable Session", StandardImages.DELETE){
          public void actionPerformed(ActionEvent e) {
            int retValue;

            retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                        "Are you sure you want to disable the session?",
                                        "Disable Session",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE);
            switch (retValue) {
              case JOptionPane.YES_OPTION:
                try {
                  myTeamMember.disableCollaborativeSession( (
                      myEnhancedCollaborativeSessionPermissionsValue.
                      getCollaborativeSessionID()).intValue());
                  myOpcat2.getRepository().getAdmin().removeNodeFromControlPanelTree(myNode);
                }catch (Exception exception) {
                  ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
                }
                break;

              case JOptionPane.NO_OPTION:
                return;
            }
          }
        };

        Action accessControlAction = new AbstractAction("Permissions Handling", OPCATeamImages.PERMITHANDLE){
          public void actionPerformed(ActionEvent e){
            CollaborativeSessionPermissionsDialog  collaborativeSessionPermissionsDialog =
                new CollaborativeSessionPermissionsDialog(myTeamMember,
                myEnhancedCollaborativeSessionPermissionsValue);
          }
        };

        Action showUsersListAction = new AbstractAction("List Of Users", OPCATeamImages.MEMBERSTREE){
          public void actionPerformed(ActionEvent e){
            CollaborativeSessionUserListDialog collaborativeSessionUserListDialog = new CollaborativeSessionUserListDialog(myTeamMember,
                myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID());
          }
        };

           Action logoutSessionAaction = new AbstractAction("Logout Session", OPCATeamImages.LOGOUT){
                 public void actionPerformed(ActionEvent e){
                   int retValue;
                   //if the user holds the token- the client ask him to save before logout,
                  // else, the question is unneeded
                   if (isUserTokenHolder(myUserID)) {
                     retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                                              "Do you want to save the model on the Server?",
                                                              "Logout Session",
                                                              JOptionPane.YES_NO_CANCEL_OPTION,
                                                              JOptionPane.QUESTION_MESSAGE);
                     switch (retValue) {
                       case JOptionPane.YES_OPTION:
                         if (activeCollaborativeSession!=null)
                         activeCollaborativeSession.saveSession();
                         break;

                       case JOptionPane.CANCEL_OPTION:
                         return;
                     }
                     //now, return the token back to server
                     activeCollaborativeSession.returnToken();
                   }

                   activeCollaborativeSession.logoutFromSession();
                   myOpcat2.setActiveCollaborativeSession(null);                 }
               };



     private boolean isUserTokenHolder(Integer userID) {
       return (userID.equals(activeCollaborativeSession.getTokenHolderID()));
     }

     private void uploadSession() {


       Integer currentSessionID = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID();

       //all checks are OK -> covert the file to string and send to the server
       CollaborativeSessionFileValue collaborativeSessionFileValue = new CollaborativeSessionFileValue();
       collaborativeSessionFileValue.setCollaborativeSessionID(currentSessionID);
       collaborativeSessionFileValue.setPrimaryKey(currentSessionID);

       try {
         FileConvertor fileConvertor = new FileConvertor();
         String fileSeparator = System.getProperty("file.separator");
         String uploadFileName = "OPCATeam" + fileSeparator + "Upload.opx";

         String finalString = fileConvertor.convertFileToString(uploadFileName);
         collaborativeSessionFileValue.setOpmModelFile(finalString);
         myTeamMember.updateCollaborativeSessionFile(collaborativeSessionFileValue);
       }catch (Exception e) {
         ExceptionHandler exceptionHandler = new ExceptionHandler(e);
       }
     }


}//end of class CollaborativeSessionPopupMenu
