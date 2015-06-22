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
import org.objectprocess.SoapClient.EditableOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.OpmModelPermissionsPK;
import org.objectprocess.SoapClient.OpmModelPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class OpmModelPermissionsDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;
  Integer myOpmModelID;
  Integer myAccessControl;
  int myAccessControlIntValue;
  Integer myUserId;
  UserValue userValue = null;

  JTabbedPane tabs = new JTabbedPane();

  JPanel opmModelMyPermissions = new JPanel();
  JPanel opmModelGivePermissions = new JPanel();

  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

  JLabel jLabel1 = new JLabel();

  JButton cancelButton = new JButton();
  JCheckBox creator = new JCheckBox();
  JCheckBox administrator = new JCheckBox();
  JCheckBox sessionCreator = new JCheckBox();
  JCheckBox sessionViewer = new JCheckBox();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JTextField jTextField1 = new JTextField();
  JCheckBox createSessionCreator = new JCheckBox();
  JCheckBox createSessionViewer = new JCheckBox();
  JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel4 = new JLabel();
  JTextField jTextField2 = new JTextField();
  JButton updateButton = new JButton();
  JCheckBox createAdministrator = new JCheckBox();


public OpmModelPermissionsDialog(TeamMember teamMember,
                                 EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myEnhancedOpmModelPermissionsValue=enhancedOpmModelPermissionsValue;
    myOpmModelID=myEnhancedOpmModelPermissionsValue.getOpmModelID();
    myAccessControl=myEnhancedOpmModelPermissionsValue.getAccessControl();
    myAccessControlIntValue =myAccessControl.intValue();
    myUserId = myEnhancedOpmModelPermissionsValue.getUserID();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("OPM Model's Permissions Handling");
    opmModelMyPermissions.setLayout(null);
    opmModelGivePermissions.setBounds(new Rectangle(-2, 0, 451, 360));
    opmModelGivePermissions.setLayout(null);
    tabs.setBounds(new Rectangle(-4, 7, 435, 268));

    logoLabel.setBounds(new Rectangle(362, 27, 50, 50));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new OpmModelAccessDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(333, 281, 96, 28));

    creator.setEnabled(false);
    creator.setRequestFocusEnabled(true);
    creator.setText("Creator");
    creator.setBounds(new Rectangle(16, 72, 97, 27));
    administrator.setEnabled(false);
    administrator.setToolTipText("");
    administrator.setText("Administrator");
    administrator.setBounds(new Rectangle(192, 72, 119, 27));
    sessionCreator.setEnabled(false);
    sessionCreator.setDoubleBuffered(false);
    sessionCreator.setText("Sessions\'s Creator");
    sessionCreator.setBounds(new Rectangle(192, 113, 158, 27));
    sessionViewer.setEnabled(false);
    sessionViewer.setText("Session\'s Viewer");
    sessionViewer.setBounds(new Rectangle(192, 160, 169, 27));

    if (myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) creator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) administrator.setSelected(true);
//    if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())  moderator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.EDITOR.intValue())   sessionCreator.setSelected(true);
    if (myAccessControlIntValue >= PermissionFlags.VIEWER.intValue())    sessionViewer.setSelected(true);

    jLabel3.setText("Member Login Name:");
    jLabel3.setBounds(new Rectangle(22, 30, 142, 18));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

    createAdministrator.setEnabled(false);
    createAdministrator.setText("Administrator");
    createAdministrator.setBounds(new Rectangle(165, 70, 118, 23));
    createSessionCreator.setText("Session\'s Creator");
    createSessionCreator.setBounds(new Rectangle(165, 110, 169, 27));
    createSessionCreator.setEnabled(false);
    createSessionViewer.setText("Session\'s Viewer");
    createSessionViewer.setBounds(new Rectangle(165, 148, 157, 27));
    createSessionViewer.setEnabled(false);

    logoLabel1.setBounds(new Rectangle(367, 25, 50, 50));

    jLabel4.setText("Join Time:");
    jLabel4.setBounds(new Rectangle(16, 30, 82, 18));
    Calendar calendar = myEnhancedOpmModelPermissionsValue.getOpmModel().getCreationTime();
    jTextField2.setText(calendar.getTime().toString());
    jTextField2.setBounds(new Rectangle(85, 27, 212, 24));
    jTextField2.setEditable(false);
    updateButton.setText("Get Permission");
    updateButton.addActionListener(new OpmModelAccessDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(22, 207, 160, 28));
    opmModelMyPermissions.add(administrator, null);
    opmModelMyPermissions.add(jLabel4, null);
    opmModelMyPermissions.add(jTextField2, null);
    opmModelMyPermissions.add(creator, null);
    opmModelMyPermissions.add(logoLabel, null);
    opmModelMyPermissions.add(sessionViewer, null);
    opmModelMyPermissions.add(sessionCreator, null);
    opmModelGivePermissions.add(jLabel3, null);
    opmModelGivePermissions.add(jTextField1, null);
    opmModelGivePermissions.add(logoLabel1, null);
    opmModelGivePermissions.add(updateButton, null);
    opmModelGivePermissions.add(createAdministrator, null);
    opmModelGivePermissions.add(createSessionCreator, null);
    opmModelGivePermissions.add(createSessionViewer, null);

    this.getContentPane().add(cancelButton, null);

    tabs.add(opmModelMyPermissions, "My Permissions");

    if (myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()){
      tabs.add(opmModelGivePermissions,   " Update Member\'s Permission ");
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
      }
      catch (Exception exception) {
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
      OpmModelPermissionsPK opmModelPermissionsPK = new OpmModelPermissionsPK();
      opmModelPermissionsPK.setUserID(userValue.getUserID());
      opmModelPermissionsPK.setOpmModelID(myEnhancedOpmModelPermissionsValue.
                                           getOpmModelID());
      OpmModelPermissionsValue opmModelPermissionsValue = null;
      try {
        opmModelPermissionsValue =
            myTeamMember.fetchOpmModelPermissionsForUser(opmModelPermissionsPK);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
      int currentMemberAccessControl;
      //if the user is "new" then opmModelPermissionsValue will be null.
      if (opmModelPermissionsValue==null) currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS.intValue();
      else currentMemberAccessControl = opmModelPermissionsValue.
          getAccessControl().intValue();
      //ignore loggedIn bit.
      if (currentMemberAccessControl >= PermissionFlags.LOGGEDIN.intValue())
        currentMemberAccessControl -= PermissionFlags.LOGGEDIN.intValue();
        //clean the area from the previos request
//      createModerator.setEnabled(true);
      createSessionCreator.setEnabled(true);
      createSessionViewer.setEnabled(true);
      createAdministrator.setSelected(false);
//      createModerator.setSelected(false);
      createSessionCreator.setSelected(false);
      createSessionViewer.setSelected(false);

      if (currentMemberAccessControl >=
          PermissionFlags.ADMINISTRATOR.intValue())
        createAdministrator.setSelected(true);
//      if (currentMemberAccessControl >= PermissionFlags.MODERATOR.intValue())
//        createModerator.setSelected(true);
      if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue())
        createSessionCreator.setSelected(true);
      if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue())
        createSessionViewer.setSelected(true);

        //if the member is administrator, we are not allowed to change its perimssions
      if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR.intValue()) {
//        createModerator.setEnabled(true);
        createSessionCreator.setEnabled(true);
        createSessionViewer.setEnabled(true);
        jTextField1.setEnabled(false);
        updateButton.setText("Update");
      }
      return;
    }

    else { //"update" state

      //first find out what the user choose to do.
//      if (createAdministrator.isSelected()) myAccessControl = new Integer(1364);
//      if (createModerator.isSelected()) myAccessControl = PermissionFlags.MODERATOR;
      if (createSessionCreator.isSelected())myAccessControl = PermissionFlags.EDITOR;
      else if (createSessionViewer.isSelected()) myAccessControl = PermissionFlags.VIEWER;
      else myAccessControl = PermissionFlags.NULL_PERMISSIONS;

      this.dispose();

      //build the editable class and send the reques to the server
      EditableOpmModelPermissionsValue editableOpmModelPermissionsValue =
          myTeamMember.createEditableOpmModelPermissionsValue(
             userValue.getUserID(),
             myOpmModelID,
             myAccessControl);

      //transmit request ot server
      try {
        myTeamMember.updateOpmModelPermissions(editableOpmModelPermissionsValue);
      }
      catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }
  }

} //end of class OpmModelAccessDialog //end of class

class OpmModelAccessDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      OpmModelPermissionsDialog adaptee;

      OpmModelAccessDialog_cancelButton_actionAdapter(OpmModelPermissionsDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class

class OpmModelAccessDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
  OpmModelPermissionsDialog adaptee;

  OpmModelAccessDialog_updateButton_actionAdapter(OpmModelPermissionsDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.updateButton_actionPerformed(e);
  }
}






