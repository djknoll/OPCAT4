package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class CollaborativeSessionPermissionsDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;
  Integer myCollaborativeSessionID;
  Integer myAccessControl;
  Integer myUserId;
  int myAccessControlIntValue;  //this is the int valuse of the access control after disabling the logged in flag.
  UserValue userValue = null;


  JTabbedPane tabs = new JTabbedPane();

  JPanel collaborativeSessionMyPermissions = new JPanel();
  JPanel collaborativeSessionGivePermissions = new JPanel();

  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

  JLabel jLabel1 = new JLabel();

  JButton updateButton = new JButton();
  JButton cancelButton = new JButton();
  JCheckBox creator = new JCheckBox();
  JCheckBox administrator = new JCheckBox();
  JCheckBox sessionEditor = new JCheckBox();
  JCheckBox sessionViewer = new JCheckBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JCheckBox createSessionEditor = new JCheckBox();
  JCheckBox createSessionViewer = new JCheckBox();
  JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);
  JCheckBox sessionCommiter = new JCheckBox();
  JCheckBox createSessionCommiter = new JCheckBox();
  JTextField jTextField2 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JCheckBox createAdministrator = new JCheckBox();


public CollaborativeSessionPermissionsDialog(TeamMember teamMember,
                                             EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember= teamMember;
    myEnhancedCollaborativeSessionPermissionsValue=enhancedCollaborativeSessionPermissionsValue;
    myCollaborativeSessionID=myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID();
    myAccessControl=myEnhancedCollaborativeSessionPermissionsValue.getAccessControl();
    myUserId = myEnhancedCollaborativeSessionPermissionsValue.getUserID();
    myAccessControlIntValue = myAccessControl.intValue();
    if (myAccessControlIntValue >= PermissionFlags.LOGGEDIN.intValue())
      myAccessControlIntValue-=PermissionFlags.LOGGEDIN.intValue();

          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Session's Permissions Handling");
    collaborativeSessionMyPermissions.setLayout(null);
//    collaborativeSessionGivePermissions.setBounds(new Rectangle(-2, 0, 451, 360));
    collaborativeSessionGivePermissions.setLayout(null);
    tabs.setBounds(new Rectangle(-4, 7, 435, 268));

    logoLabel.setBounds(new Rectangle(369, 21, 50, 50));


    updateButton.setText("Get Permission");
    updateButton.addActionListener(new CollaborativeSessionAccessDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(22, 212, 145, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new CollaborativeSessionAccessDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(335, 285, 96, 28));


    creator.setEnabled(false);
    creator.setText("Creator");
    creator.setBounds(new Rectangle(8, 58, 97, 27));
    administrator.setEnabled(false);
    administrator.setToolTipText("");
    administrator.setText("Administrator");
    administrator.setBounds(new Rectangle(182, 58, 119, 27));
    sessionEditor.setEnabled(false);
    sessionEditor.setText("Sessions\'s Editor");
    sessionEditor.setBounds(new Rectangle(182, 137, 158, 27));
    sessionViewer.setEnabled(false);
    sessionViewer.setText("Session\'s Viewer");
    sessionViewer.setBounds(new Rectangle(182, 181, 169, 27));
    sessionCommiter.setEnabled(false);
    sessionCommiter.setText("Session\'s Commiter");
    sessionCommiter.setBounds(new Rectangle(182, 101, 216, 23));


    if (myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) creator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) administrator.setSelected(true);
//    if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())  moderator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.COMMITTER.intValue())   sessionCommiter.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.EDITOR.intValue())   sessionEditor.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.VIEWER.intValue())    sessionViewer.setSelected(true);

    jLabel3.setText("Member Login Name:");
    jLabel3.setBounds(new Rectangle(22, 30, 145, 18));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

    createAdministrator.setText("Administrator");
    createAdministrator.setBounds(new Rectangle(165, 67, 120, 23));
    createAdministrator.setEnabled(false);
    createSessionEditor.setText("Session\'s Editor");
    createSessionEditor.setBounds(new Rectangle(165, 134, 150, 27));
    createSessionEditor.setEnabled(false);
    createSessionViewer.setText("Session\'s Viewer");
    createSessionViewer.setBounds(new Rectangle(165, 173, 146, 27));
    createSessionViewer.setEnabled(false);
    logoLabel1.setBounds(new Rectangle(361, 27, 50, 50));
    createSessionCommiter.setText("Session\'s Commiter");
    createSessionCommiter.setBounds(new Rectangle(165, 100, 182, 23));
    createSessionCommiter.setEnabled(false);

    Calendar calendar = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getLastUpdate();
    jTextField2.setText(calendar.getTime().toString());
    jTextField2.setBounds(new Rectangle(79, 21, 205, 24));
    jTextField2.setEditable(false);
    jLabel4.setText("Join Time:");
    jLabel4.setBounds(new Rectangle(8, 24, 71, 18));
    collaborativeSessionMyPermissions.add(creator, null);
    collaborativeSessionMyPermissions.add(logoLabel, null);
    collaborativeSessionMyPermissions.add(administrator, null);
    collaborativeSessionMyPermissions.add(jTextField2, null);
    collaborativeSessionMyPermissions.add(jLabel4, null);
    collaborativeSessionMyPermissions.add(sessionCommiter, null);
    collaborativeSessionMyPermissions.add(sessionEditor, null);
    collaborativeSessionMyPermissions.add(sessionViewer, null);
    collaborativeSessionGivePermissions.add(jLabel3, null);
    collaborativeSessionGivePermissions.add(jTextField1, null);
    collaborativeSessionGivePermissions.add(logoLabel1, null);
    collaborativeSessionGivePermissions.add(createAdministrator, null);
    collaborativeSessionGivePermissions.add(updateButton, null);
    collaborativeSessionGivePermissions.add(createSessionViewer, null);
    collaborativeSessionGivePermissions.add(createSessionCommiter, null);
    collaborativeSessionGivePermissions.add(createSessionEditor, null);
    tabs.add(collaborativeSessionMyPermissions, "My Permissions");

    this.getContentPane().add(cancelButton, null);

    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
      tabs.add(collaborativeSessionGivePermissions,   "Update Member\'s Permission ");
   }


    this.getContentPane().add(tabs, null);

    this.setBounds(0, 0, 460, 360);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }
  void updateButton_actionPerformed(ActionEvent e) {

    if (updateButton.getText().equals("Get Permission")) {

      if (jTextField1.getText().equals("")) {
        JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Member Login Name is missing",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      //request from server the userID of the given member login name
      try {
        userValue = myTeamMember.getUserByLoginName(jTextField1.getText());
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
        if (userValue == null) this.dispose();
      }

      //if the userValue is null and no exception happen,
      //that means that the user does not exit - in such case, dispose the window
      //after an error message
      if (userValue == null) {
        JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "User "+jTextField1.getText()+" does not exist",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        this.dispose();
        return;
      }

      //first check that the user that we change his permission is not me.
      if (myUserId.equals(userValue.getUserID())) {
        JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "You can not changed your personal permissions",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      //fetch from server the current access control flag for this user
      CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK = new CollaborativeSessionPermissionsPK();
      collaborativeSessionPermissionsPK.setUserID(userValue.getUserID());
      collaborativeSessionPermissionsPK.setCollaborativeSessionID(myEnhancedCollaborativeSessionPermissionsValue.
                                           getCollaborativeSessionID());
      CollaborativeSessionPermissionsValue collaborativeSessionPermissionsValue = null;
      try {
        collaborativeSessionPermissionsValue =
            myTeamMember.fetchCollaborativeSessionPermissionsForUser(collaborativeSessionPermissionsPK);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
        //if the user is "new in the session" then collaborativeSessionPermissionsValue will be null.
        int currentMemberAccessControl;
        if (collaborativeSessionPermissionsValue == null)
          currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS.intValue();
        else currentMemberAccessControl = collaborativeSessionPermissionsValue.
            getAccessControl().intValue();
        //ignore loggedIn bit.
        if (currentMemberAccessControl >= PermissionFlags.LOGGEDIN.intValue())
          currentMemberAccessControl -= PermissionFlags.LOGGEDIN.intValue();
          //clean the area from the previos request
//        createModerator.setEnabled(true);
        createSessionCommiter.setEnabled(true);
        createSessionEditor.setEnabled(true);
        createSessionViewer.setEnabled(true);
        createAdministrator.setSelected(false);
//        createModerator.setSelected(false);
        createSessionViewer.setSelected(false);
        createSessionCommiter.setSelected(false);
        createSessionEditor.setSelected(false);

        if (currentMemberAccessControl >=
            PermissionFlags.ADMINISTRATOR.intValue())
          createAdministrator.setSelected(true);
//        if (currentMemberAccessControl >= PermissionFlags.MODERATOR.intValue())
//          createModerator.setSelected(true);
        if (currentMemberAccessControl >= PermissionFlags.COMMITTER.intValue())
          createSessionCommiter.setSelected(true);
        if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue())
          createSessionEditor.setSelected(true);
        if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue())
          createSessionViewer.setSelected(true);

          //if the member is administrator, we are not allowed to change its perimssions
        if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR.intValue()) {
//          createModerator.setEnabled(true);
          createSessionEditor.setEnabled(true);
          createSessionViewer.setEnabled(true);
          createSessionCommiter.setEnabled(true);
          jTextField1.setEnabled(false);
          updateButton.setText("Update");
        }
        return;
      }

    else { //"update" state
      Integer newAccessControlForMember;
      //first find out what the user choose to do.
//      if (createAdministrator.isSelected()) myAccessControl = new Integer(1364);
//        if (createModerator.isSelected()) newAccessControlForMember = PermissionFlags.MODERATOR; else
          if (createSessionCommiter.isSelected()) newAccessControlForMember= PermissionFlags.COMMITTER; else
            if (createSessionEditor.isSelected()) newAccessControlForMember= PermissionFlags.EDITOR; else
              if (createSessionViewer.isSelected()) newAccessControlForMember= PermissionFlags.VIEWER; else
                newAccessControlForMember=PermissionFlags.NULL_PERMISSIONS;

      this.dispose();

      //build the editable class and send the reques to the server
      EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue =
          myTeamMember.createEditableCollaborativeSessionPermissionsValue(userValue.getUserID(),
                                                                          myCollaborativeSessionID,
                                                                          newAccessControlForMember);

      //transmit request ot server
      try {
        myTeamMember.updateCollaborativeSessionPermissions(editableCollaborativeSessionPermissionsValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }
  }
} //end of class CollaborativeSessionAccessDialog


class CollaborativeSessionAccessDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      CollaborativeSessionPermissionsDialog adaptee;

      CollaborativeSessionAccessDialog_updateButton_actionAdapter(CollaborativeSessionPermissionsDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class CollaborativeSessionAccessDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      CollaborativeSessionPermissionsDialog adaptee;

      CollaborativeSessionAccessDialog_cancelButton_actionAdapter(CollaborativeSessionPermissionsDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class






