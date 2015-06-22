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
import org.objectprocess.SoapClient.EditableWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.UserValue;
import org.objectprocess.SoapClient.WorkgroupPermissionsPK;
import org.objectprocess.SoapClient.WorkgroupPermissionsValue;

public class WorkgroupPermissionsDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;
  Integer myWorkgroupID;
  Integer myAccessControl;
  int myAccessControlIntValue;
  Integer myUserId;
  UserValue userValue = null;

  JTabbedPane tabs = new JTabbedPane();

  JPanel workgroupMyPermissions = new JPanel();
  JPanel workgroupGivePermissions = new JPanel();

  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

  JLabel jLabel1 = new JLabel();

  JButton updateButton = new JButton();
  JButton cancelButton = new JButton();
  JCheckBox creator = new JCheckBox();
  JCheckBox administrator = new JCheckBox();
  JCheckBox modelCreator = new JCheckBox();
  JCheckBox modelViewer = new JCheckBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JCheckBox createModelCreator = new JCheckBox();
  JCheckBox createModelViewer = new JCheckBox();
  JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);
  JTextField jTextField2 = new JTextField();
  JLabel jLabel4 = new JLabel();
  JCheckBox createAdminisrator = new JCheckBox();


public WorkgroupPermissionsDialog(TeamMember teamMember,
                                  EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember = teamMember;
    myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
    myWorkgroupID = myEnhancedWorkgroupPermissionsValue.getWorkgroupID();
    myAccessControl = myEnhancedWorkgroupPermissionsValue.getAccessControl();
    myAccessControlIntValue =myAccessControl.intValue();
    myUserId = myEnhancedWorkgroupPermissionsValue.getUserID();

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Workgroup's Permissions Handling");
    tabs.setBounds(new Rectangle(-4, 7, 435, 258));

    workgroupMyPermissions.setBounds(new Rectangle(-2, 0, 451, 360));
    workgroupMyPermissions.setLayout(null);
    workgroupGivePermissions.setBounds(new Rectangle(-2, 0, 451, 360));
    workgroupGivePermissions.setLayout(null);

    logoLabel.setBounds(new Rectangle(363, 26, 50, 50));


    updateButton.setText("Get Permission");
    updateButton.addActionListener(new WorkgroupAccessDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(26, 197, 153, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new WorkgroupAccessDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(335, 280, 96, 28));


    creator.setEnabled(false);
    creator.setText("Creator");
    creator.setBounds(new Rectangle(14, 65, 97, 27));
    administrator.setEnabled(false);
    administrator.setToolTipText("");
    administrator.setText("Administrator");
    administrator.setBounds(new Rectangle(168, 65, 119, 27));
    modelCreator.setEnabled(false);
    modelCreator.setText("OPM Model\'s Creator");
    modelCreator.setBounds(new Rectangle(168, 113, 158, 27));
    modelViewer.setEnabled(false);
    modelViewer.setText("OPM Model\'s Viewer");
    modelViewer.setBounds(new Rectangle(168, 161, 169, 27));

    if (myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) creator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) administrator.setSelected(true);
//    if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())  moderator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.EDITOR.intValue())   modelCreator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.VIEWER.intValue())    modelViewer.setSelected(true);

    jLabel3.setText("Member Login Name:");
    jLabel3.setBounds(new Rectangle(26, 30, 140, 18));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

    createAdminisrator.setText("Administrator");
    createAdminisrator.setBounds(new Rectangle(165, 79, 125, 23));
    createAdminisrator.setEnabled(false);
    createModelCreator.setText("OPM Model\'s Creator");
    createModelCreator.setBounds(new Rectangle(165, 112, 169, 27));
    createModelCreator.setEnabled(false);
    createModelViewer.setText("OPM Model\'s Viewer");
    createModelViewer.setBounds(new Rectangle(165, 147, 157, 27));
    createModelViewer.setEnabled(false);

    logoLabel1.setBounds(new Rectangle(366, 27, 50, 50));

    Calendar calendar = myEnhancedWorkgroupPermissionsValue.getWorkgroup().getCreationTime();
    jTextField2.setText(calendar.getTime().toString());
    jTextField2.setBounds(new Rectangle(87, 26, 207, 24));
    jTextField2.setEditable(false);
    jLabel4.setText("Join Time:");
    jLabel4.setBounds(new Rectangle(14, 29, 86, 18));
    workgroupMyPermissions.add(logoLabel, null);
    workgroupMyPermissions.add(creator, null);
    workgroupMyPermissions.add(administrator, null);
    workgroupMyPermissions.add(jLabel4, null);
    workgroupMyPermissions.add(jTextField2, null);
    workgroupMyPermissions.add(modelCreator, null);
    workgroupMyPermissions.add(modelViewer, null);
    workgroupGivePermissions.add(jTextField1, null);
    workgroupGivePermissions.add(logoLabel1, null);
    workgroupGivePermissions.add(jLabel3, null);
    workgroupGivePermissions.add(updateButton, null);
    workgroupGivePermissions.add(createAdminisrator, null);
    workgroupGivePermissions.add(createModelCreator, null);
    workgroupGivePermissions.add(createModelViewer, null);

    this.getContentPane().add(cancelButton, null);

    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
      tabs.add(workgroupGivePermissions,   " Update Member\'s Permission ");
    }

    tabs.add(workgroupMyPermissions, " My permissions ");

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
      WorkgroupPermissionsPK workgroupPermissionsPK = new WorkgroupPermissionsPK();
      workgroupPermissionsPK.setUserID(userValue.getUserID());
      workgroupPermissionsPK.setWorkgroupID(myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
      WorkgroupPermissionsValue workgroupPermissionsValue=null;
      try {
        workgroupPermissionsValue =
            myTeamMember.fetchWorkgroupPermissionsForUser(workgroupPermissionsPK);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }

        int currentMemberAccessControl;
        if (workgroupPermissionsValue==null) currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS.intValue();
        else currentMemberAccessControl = workgroupPermissionsValue.
            getAccessControl().intValue();
        //ignore loggedIn bit.
        if (currentMemberAccessControl >= PermissionFlags.LOGGEDIN.intValue())
          currentMemberAccessControl-=PermissionFlags.LOGGEDIN.intValue();
        //clean the area from the previos request
//        createModerator.setEnabled(false);
        createModelCreator.setEnabled(false);
        createModelViewer.setEnabled(false);
        createAdminisrator.setSelected(false);
//        createModerator.setSelected(false);
        createModelCreator.setSelected(false);
        createModelViewer.setSelected(false);

        if (currentMemberAccessControl >= PermissionFlags.ADMINISTRATOR.intValue()) createAdminisrator.setSelected(true);
//        if (currentMemberAccessControl >= PermissionFlags.MODERATOR.intValue()) createModerator.setSelected(true);
        if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue())    createModelCreator.setSelected(true);
        if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue())    createModelViewer.setSelected(true);

        //if the member is administrator, we are not allowed to change its perimssions
        if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR.intValue()) {
//           createModerator.setEnabled(true);
           createModelCreator.setEnabled(true);
           createModelViewer.setEnabled(true);
           jTextField1.setEnabled(false);
           updateButton.setText("Update");
         }
         return;
    }
    else { //"update" state

      Integer newAccessControlForMember;
      //first find out what the user choose to do.
 //     if (createModerator.isSelected()) newAccessControlForMember = PermissionFlags.MODERATOR; else
      if (createModelCreator.isSelected()) newAccessControlForMember= PermissionFlags.EDITOR; else
      if (createModelViewer.isSelected()) newAccessControlForMember= PermissionFlags.VIEWER; else
        newAccessControlForMember=PermissionFlags.NULL_PERMISSIONS;

      this.dispose();

      //build the editable class and send the reques to the server
      EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue =
          myTeamMember.createEditableWorkgroupPermissionsValue(userValue.getUserID(),
                                                               myWorkgroupID,
                                                               newAccessControlForMember);

      //transmit the request to the server
      try {
        myTeamMember.updateWorkgroupPermissions(editableWorkgroupPermissionsValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }
  }

} //end of class WorkgroupAccessDialog


class WorkgroupAccessDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      WorkgroupPermissionsDialog adaptee;

      WorkgroupAccessDialog_updateButton_actionAdapter(WorkgroupPermissionsDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class WorkgroupAccessDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      WorkgroupPermissionsDialog adaptee;

      WorkgroupAccessDialog_cancelButton_actionAdapter(WorkgroupPermissionsDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class




