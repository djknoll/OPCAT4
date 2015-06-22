package org.objectprocess.team;

import gui.Opcat2;

import java.io.File;
import java.io.FileWriter;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EditableRevisionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.FullRevisionValue;
import org.objectprocess.SoapClient.MetaRevisionValue;

public class ActiveCollaborativeSession extends Object {

  protected EnhancedCollaborativeSessionPermissionsValue myECSPV;
  protected Integer sessionID=null;
  protected String sessionName=null;
  protected Integer tokenHolderID=null;
  protected Integer opmModelID = null;
  protected Integer revisionID=null;
  protected TeamMember myTeamMember=null;
  protected Opcat2 myOpcat2=null;
  protected TokenTimer tokenTimer = null;

  private final static String fileSeparator = System.getProperty("file.separator");
  private String clientFileName = "OPCATeam" + fileSeparator + "Client.opx";
  private String serverFileName = "OPCATeam" + fileSeparator + "Server.opx";

//constractor
  public ActiveCollaborativeSession (TeamMember teamMember,
                                     EnhancedCollaborativeSessionPermissionsValue
                                     enhancedCollaborativeSessionPermissionsValue,
                                     Opcat2 opcat2) {

    myECSPV = enhancedCollaborativeSessionPermissionsValue;
    sessionID = myECSPV.getCollaborativeSessionID();
    sessionName = myECSPV.getCollaborativeSession().getCollaborativeSessionName();
    tokenHolderID = myECSPV.getCollaborativeSession().getTokenHolderID();
    opmModelID = myECSPV.getCollaborativeSession().getOpmModelID();
    revisionID = myECSPV.getCollaborativeSession().getRevisionID();
    myTeamMember = teamMember;
    myOpcat2 = opcat2;
    tokenTimer = new TokenTimer(myOpcat2);

  }


  public void loginToSession() throws Exception {
    try {
      //try to login to server
      myTeamMember.sessionLogin(sessionID.intValue());
      //open connection on the JMS flatfrom with the session ID.
      if (myOpcat2.getCollaborativeSessionMessageHandler() != null) {
        myOpcat2.getCollaborativeSessionMessageHandler().openConnection(sessionID);
        myOpcat2.getChatMessageHandler().openConnection(sessionID);
        myOpcat2.addChatRoom();
      }
      //load the corresponding file
      loadSessionFile();
    }
    catch (Exception exception) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      throw exception;
    }
  }

  private void loadSessionFile() {

    Thread runner = new Thread() {
      public void run() {
      try {
        CollaborativeSessionFileValue collaborativeSessionFileValue = null;
        collaborativeSessionFileValue = myTeamMember.fatchCollaborativeSessionFile(sessionID.intValue());

        if (collaborativeSessionFileValue == null)
          return;

        File outputFile = new File(clientFileName);
        FileWriter out = new FileWriter(outputFile);
        out.write(collaborativeSessionFileValue.getOpmModelFile());
        out.close();
        //load the file to OPCAT
        myOpcat2.loadFileForOPCATeam(clientFileName,
                                   sessionName,
                                   false);

        //set the variable sessionOPenID to the currebt active session ID.
        setSessionID(sessionID);
        //set the name fo the active session on the panel
        myOpcat2.getRepository().getAdmin().updateActiveSessionOnPanel(
            sessionName);
        //create the session's members tree on the panel.
        myOpcat2.getRepository().getAdmin().createMembersTree(sessionID);
        //set the name of the token holder on the panel
        myOpcat2.getRepository().getAdmin().updateTokenHolderOnPanel(
            tokenHolderID.intValue());
        }
        catch (Exception exception) {
          ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
        }
      }
    };
    runner.start();
  }

  public void logoutFromSession() {
    try {
    //logout session
    myTeamMember.sessionLogout(sessionID.intValue());
    //close connection to JMS
    if (myOpcat2.getCollaborativeSessionMessageHandler()!= null) {
      myOpcat2.getCollaborativeSessionMessageHandler().closeConnection();
      myOpcat2.getChatMessageHandler().closeConnection();
      myOpcat2.removeChatRoom();
    }

    myOpcat2.getRepository().getAdmin().updateActiveSessionOnPanel(null);
    myOpcat2.getRepository().getAdmin().updateTokenHolderOnPanel("");
    myOpcat2.getRepository().getAdmin().removeMembersTree();
    myOpcat2.closeFileForOPCATeam();

  }catch (Exception exception) {
    ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
  }
}



  public void getToken(){
    try {
      if (myTeamMember.requestToken(sessionID.intValue())) {
        //The user got the token
        if (tokenTimer != null) tokenTimer.startTimer(
              myECSPV.getCollaborativeSession().getTokenTimeout().intValue());
      }

    }catch (Exception exception) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
    }
  }

  public void returnToken() {
    try {
      myTeamMember.returnToken(sessionID.intValue());
      if (tokenTimer != null) tokenTimer.stopTimer();
    }catch (Exception exception) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
    }
  }

  public void commitActiveCollaborativeSession(String commitDescription,boolean IcreaseMajor)
  {
    //first, check that all members are logged out- this can be done through the
    //myTeamMember interface which mantains online the list of session members for the active sesion.
    if (myTeamMember.numOfLoggedInMembers() > 1)  {
      //the session has more then one users logged into - ask the user if he wish to continue.
      int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                               "Active members are logged in the session." + "\n" +
                                               "Proceed with the commit process?",
                                               "Commit Session",
                                               JOptionPane.OK_CANCEL_OPTION,
                                               JOptionPane.QUESTION_MESSAGE);
      switch (retValue) {
        case JOptionPane.OK_OPTION:
          break;

        case JOptionPane.CANCEL_OPTION:
          return;
      }
    }

    try {
      //execute preCommit request to the server to see if there is a conflict problem.
      Object[] metaRevisionsList = null;
      if (revisionID != null)
        metaRevisionsList = myTeamMember.preCommitCollaborativeSession(revisionID.intValue());

      //if the resposne is null-> no conflict occurs.in such case the
      //session file has to be transferred to the server, and a new revision has to be created.
      if ((revisionID == null) || ((metaRevisionsList == null) || (metaRevisionsList.length == 0))) {
        EditableRevisionValue editableRevisionValue = new EditableRevisionValue();
        editableRevisionValue.setComitterID(tokenHolderID);
        editableRevisionValue.setDescription(commitDescription);
        editableRevisionValue.setMajorRevision(null);
        editableRevisionValue.setMinorRevision(null);
        editableRevisionValue.setOpmModelID(opmModelID);
        //convert the file into string
        myOpcat2.saveFileForOPCATeam();
        FileConvertor fileConvertor = new FileConvertor();
        String finalString = fileConvertor.convertFileToString(clientFileName);
        editableRevisionValue.setOpmModelFile(finalString);
        //finally, send the commit requesto the server
        EditableRevisionValue returnedEditableRevisionValue =
            myTeamMember.commitCollaborativeSession(sessionID.intValue(),
                                                  editableRevisionValue,
                                                  IcreaseMajor);

        if (returnedEditableRevisionValue != null) {
          //the commit action terminated succesfully
          JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                        "The commit action successfully terminated." + "\n" +
                                        "A new revision was created on the server. " + "\n" +
                                        "The new revision number is: " +
                                        returnedEditableRevisionValue.getMajorRevision() + "." +
                                        returnedEditableRevisionValue.getMinorRevision() + "\n" +
                                        "The session will be closed now.",
                                        "Message",
                                        JOptionPane.INFORMATION_MESSAGE);
          this.logoutFromSession();
          myOpcat2.getRepository().refreshOPCATeamControlPanel();
          myOpcat2.setActiveCollaborativeSession(null);
        }
      }
      //the next section deals with conflicts.
      else {

        Object[] conflictOption = new Object[3];
        conflictOption[0] = new String("Merge");
        conflictOption[1] = new String("Ignore");
        conflictOption[2] = new String("Cancel");
        Icon icon = null;
        int retValue;
        retValue = JOptionPane.showOptionDialog(Opcat2.getFrame(),
                                                "The server detected a conflict." + "\n" +
                                                "Choose one of the following options:" + "\n" +
                                                "Merge with last revision, Ignore and commit the model, or Cancel." + "\n",
                                                "Conflict detected by the Server",
                                                JOptionPane.YES_NO_OPTION,
                                                JOptionPane.QUESTION_MESSAGE,
                                                icon,
                                                conflictOption,
                                                conflictOption[0]);

        switch (retValue) {
          //merge option
          case 0:
            //find out what is the highest revision number
            int index = myTeamMember.findHigestRevision(metaRevisionsList);
            if (index == -1) break;

            Integer majorRevision = ( (MetaRevisionValue) (metaRevisionsList[index])).getMajorRevision();
            Integer minorRevision = ( (MetaRevisionValue) (metaRevisionsList[index])).getMinorRevision();
            revisionID = ( (MetaRevisionValue) (metaRevisionsList[index])).getRevisionID();

            if (revisionID == null) break;

            //bring the coresponding revision for the merge operation
            FullRevisionValue fullRevisionValue = myTeamMember.fatchOpmModelFile(revisionID.intValue());

            if (fullRevisionValue == null) break;

            //convert the string that came from the server to server.opx file
            File outputFile = new File(serverFileName);
            FileWriter out = new FileWriter(outputFile);
            out.write(fullRevisionValue.getOpmModelFile());
            out.close();

            //before calling merger - save the client project.
            myOpcat2.saveFileForOPCATeam();
            //call the merger
            (new XmlMerger(myOpcat2)).mergeProject(myOpcat2.getCurrentProject(),
                                           serverFileName);

            //ask the user to decide if he wants to save the new session or rollback

            Object[] saveOrRollBackOption = new Object[2];
            conflictOption[0] = new String("Save");
            conflictOption[1] = new String("Roll Back");

            retValue = JOptionPane.showOptionDialog(Opcat2.getFrame(),
                                                    "Merge operation successfuly terminated." + "\n" +
                                                    "Choose one of the following options:" + "\n" +
                                                    "Save Merged session on the server, or Roll Back." + "\n",
                                                    "Merge or Rollback",
                                                    JOptionPane.YES_NO_OPTION,
                                                    JOptionPane.QUESTION_MESSAGE,
                                                    icon,
                                                    conflictOption,
                                                    conflictOption[0]);

            switch (retValue) {
              //save option
              case 0:
                //update the new session details on the server
                EditableCollaborativeSessionValue editableCollaborativeSessionValue =
                    new EditableCollaborativeSessionValue();
                editableCollaborativeSessionValue.setCollaborativeSessionName(myECSPV.getCollaborativeSession().getCollaborativeSessionName());
                editableCollaborativeSessionValue.setDescription(myECSPV.getCollaborativeSession().getDescription());
                editableCollaborativeSessionValue.setTokenTimeout(myECSPV.getCollaborativeSession().getTokenTimeout());
                editableCollaborativeSessionValue.setUserTimeout(myECSPV.getCollaborativeSession().getUserTimeout());
                editableCollaborativeSessionValue.setCollaborativeSessionID(myECSPV.getCollaborativeSessionID());
                editableCollaborativeSessionValue.setPrimaryKey(myECSPV.getCollaborativeSessionID());
                editableCollaborativeSessionValue.setOpmModelID(myECSPV.getCollaborativeSession().getOpmModelID());
                editableCollaborativeSessionValue.setMajorRevision(fullRevisionValue.getMajorRevision());
                editableCollaborativeSessionValue.setMinorRevision(fullRevisionValue.getMinorRevision());
                editableCollaborativeSessionValue.setRevisionID(fullRevisionValue.getRevisionID());

                try {
                  myTeamMember.updateCollaborativeSession(editableCollaborativeSessionValue);
                  saveSession();
                }catch (Exception exception) {
                  ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
                }
                return;

                case 1:
                //rollback
                myOpcat2.closeFileForOPCATeam();
                myOpcat2.loadFileForOPCATeam(clientFileName,
                                           sessionName,
                                           false);
                  return;
              }


          case 1:
            //ignore option-> commit the current model without any merging action.
            EditableRevisionValue editableRevisionValue = new EditableRevisionValue();
            editableRevisionValue.setComitterID(tokenHolderID);
            editableRevisionValue.setDescription(commitDescription);
            editableRevisionValue.setMajorRevision(null);
            editableRevisionValue.setMinorRevision(null);
            editableRevisionValue.setOpmModelID(opmModelID);
            //convert the file into string
            myOpcat2.saveFileForOPCATeam();
            FileConvertor fileConvertor = new FileConvertor();
            String finalString = fileConvertor.convertFileToString(
                clientFileName);
            editableRevisionValue.setOpmModelFile(finalString);
           //finally, send the commit requesto the server
            EditableRevisionValue returnedEditableRevisionValue =
                myTeamMember.commitCollaborativeSession(sessionID.intValue(),
                editableRevisionValue,
                IcreaseMajor);

            if (returnedEditableRevisionValue != null) {
              //the commit action terminated succesfully
              JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                            "The commit action successfully terminated." + "\n" +
                                            "A new revision was created on the server. " + "\n" +
                                            "The new revision number is: " +
                                            returnedEditableRevisionValue.getMajorRevision() + "." +
                                            returnedEditableRevisionValue.getMinorRevision() + "\n" +
                                            "The session will be closed now.",
                                            "Message",
                                            JOptionPane.INFORMATION_MESSAGE);
              this.logoutFromSession();
              myOpcat2.getRepository().refreshOPCATeamControlPanel();
              myOpcat2.setActiveCollaborativeSession(null);
            }
              return;

          case 2:
            //the user changed his mind, and canceled the operation.
            return;
        }
        //
      }
    }catch (Exception exception) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
    }
  }

  public void saveSession() {

    Integer currentSessionID = myECSPV.getCollaborativeSessionID();

    //check that the save command is used for the open session
    if (! (currentSessionID.equals(getSessionID()))) {
      JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                    "You can perform save action only on the open Session",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    //all checks are OK -> covert the file to string and send to the server
    CollaborativeSessionFileValue collaborativeSessionFileValue = new CollaborativeSessionFileValue();
    collaborativeSessionFileValue.setCollaborativeSessionID(currentSessionID);
    collaborativeSessionFileValue.setPrimaryKey(currentSessionID);

    try {
      myOpcat2.saveFileForOPCATeam();
      FileConvertor fileConvertor = new FileConvertor();
      String finalString = fileConvertor.convertFileToString(clientFileName);
      collaborativeSessionFileValue.setOpmModelFile(finalString);
      myTeamMember.updateCollaborativeSessionFile(collaborativeSessionFileValue);
    }catch (Exception e) {
      ExceptionHandler exceptionHandler = new ExceptionHandler(e);
    }
  }


  public void setSessionID(Integer ID){
    sessionID = ID;
  }
  public Integer getSessionID(){
    return sessionID;
  }
  public void setTokenHolderID(Integer ID){
    tokenHolderID = ID;
  }
  public Integer getTokenHolderID(){
    return tokenHolderID;
  }
public TokenTimer getTokenTimer() {
    return tokenTimer;
  }
}
