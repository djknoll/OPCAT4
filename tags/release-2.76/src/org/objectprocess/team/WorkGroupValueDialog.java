package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableWorkgroupValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

public class WorkGroupValueDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

  JPanel WorkGroup = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();

  JTextField jTextField1 = new JTextField();

  JButton updateButton = new JButton();
  JButton cancelButton = new JButton();
  JTextField jTextField3 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();
  JLabel updateMessage = new JLabel();


public WorkGroupValueDialog(TeamMember teamMember,
                            EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myEnhancedWorkgroupPermissionsValue=enhancedWorkgroupPermissionsValue;

          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Workgroup Details");
    WorkGroup.setBounds(new Rectangle(0, 0, 381, 252));


    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Workgroup Name:");
    jLabel1.setBounds(new Rectangle(84, 32, 118, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Description:");
    jLabel4.setBounds(new Rectangle(84, 116, 97, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Creation Date:");
    jLabel3.setBounds(new Rectangle(84, 75, 97, 21));

    WorkGroup.setLayout(null);

    logoLabel.setBounds(new Rectangle(17, 32, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText(myEnhancedWorkgroupPermissionsValue.getWorkgroup().getWorkgroupName());
    jTextField1.setBounds(new Rectangle(211, 32, 149, 29));
    jTextField3.setBounds(new Rectangle(211, 75, 149, 29));
    Calendar calendar = myEnhancedWorkgroupPermissionsValue.getWorkgroup().getCreationTime();
    jTextField3.setText(calendar.getTime().toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);

    updateButton.setText("Update");
    updateButton.addActionListener(new WorkGroupValueDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(17, 203, 96, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new WorkGroupValueDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(264, 203, 96, 28));

    jScrollPane1.setBounds(new Rectangle(211, 116, 153, 63));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);

    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText(myEnhancedWorkgroupPermissionsValue.getWorkgroup().getDescription());
    updateMessage.setText("Only the administrator can perform update action!");
    updateMessage.setBounds(new Rectangle(17, 202, 367, 15));
    WorkGroup.add(logoLabel, null);
    WorkGroup.add(jLabel4, null);
    WorkGroup.add(jLabel1, null);
    WorkGroup.add(jLabel3, null);
    WorkGroup.add(jTextField3, null);
    WorkGroup.add(jTextField1, null);
    WorkGroup.add(jScrollPane1, null);
    WorkGroup.add(cancelButton, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(WorkGroup, null);

    //diable this button if the user is not creator or admin .
    Integer accessControl = myEnhancedWorkgroupPermissionsValue.getAccessControl();
    if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
      jTextArea1.setEditable(false);
      jTextField1.setEditable(false);
      WorkGroup.add(updateMessage, null);
    }
    else
      WorkGroup.add(updateButton, null);


    this.setBounds(0, 0, 381,282);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }
    void updateButton_actionPerformed(ActionEvent e) {

      if ( (jTextField1.getText().equals("")) )
      {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Data is missing in one of the fields",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      this.dispose();

      EditableWorkgroupValue editableWorkgroupValue = new EditableWorkgroupValue();
      editableWorkgroupValue.setDescription(jTextArea1.getText());
      editableWorkgroupValue.setWorkgroupName(jTextField1.getText());
      editableWorkgroupValue.setWorkgroupID(myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
      editableWorkgroupValue.setPrimaryKey(myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
      try {
        myTeamMember.updateWorkgroup(editableWorkgroupValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }

} //end of class WorkGroupDialog


class WorkGroupValueDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      WorkGroupValueDialog adaptee;

      WorkGroupValueDialog_updateButton_actionAdapter(WorkGroupValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class WorkGroupValueDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      WorkGroupValueDialog adaptee;

      WorkGroupValueDialog_cancelButton_actionAdapter(WorkGroupValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class



