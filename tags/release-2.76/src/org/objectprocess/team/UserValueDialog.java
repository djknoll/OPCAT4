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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableUserValue;
import org.objectprocess.SoapClient.EnhancedUserValue;

public class UserValueDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedUserValue myEnhancedUserValue;

  JPanel userData = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();

  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField5 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();

  JButton updateButton = new JButton();
  JButton cancelButton = new JButton();
  JTextArea jTextArea1 = new JTextArea();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JTextField jTextField4 = new JTextField();
  JPasswordField jPasswordField1 = new JPasswordField();


public UserValueDialog(TeamMember teamMember) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember = teamMember;
    myEnhancedUserValue=myTeamMember.getLocalEnhancedUser();
          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("User Details");
    userData.setBounds(new Rectangle(0, 0, 377, 410));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("First Name:");
    jLabel1.setBounds(new Rectangle(92, 107, 97, 21));
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Last Name:");
    jLabel2.setBounds(new Rectangle(92, 151, 97, 21));
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Email Address:");
    jLabel3.setBounds(new Rectangle(92, 196, 121, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Description:");
    jLabel4.setBounds(new Rectangle(92, 282, 97, 21));
    jLabel5.setEnabled(true);
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("Last Login Time:");
    jLabel5.setBounds(new Rectangle(92, 236, 122, 21));
    jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel6.setText("Password:");
    jLabel6.setBounds(new Rectangle(92, 62, 85, 18));
    jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel7.setText("Login Name:");
    jLabel7.setBounds(new Rectangle(92, 18, 92, 18));

    userData.setLayout(null);

    logoLabel.setBounds(new Rectangle(20, 18, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText(myEnhancedUserValue.getFirstName());
    jTextField1.setBounds(new Rectangle(204, 107, 149, 29));
    jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField2.setText(myEnhancedUserValue.getLastName());
    jTextField2.setBounds(new Rectangle(204, 151, 149, 29));
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField3.setText(myEnhancedUserValue.getEmail());
    jTextField3.setBounds(new Rectangle(204, 196, 149, 29));
    jTextField5.setEnabled(true);
    jTextField5.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField5.setEditable(false);
    Calendar calendar = myEnhancedUserValue.getLastLoginTime();
    jTextField5.setText(calendar.getTime().toString());
    jTextField5.setBounds(new Rectangle(204, 236, 149, 29));
    jTextField4.setText(myEnhancedUserValue.getLoginName());
    jTextField4.setBounds(new Rectangle(204, 18, 149, 29));
    jPasswordField1.setBounds(new Rectangle(204, 62, 149, 29));
    jPasswordField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jPasswordField1.setText(myEnhancedUserValue.getPassword());

    updateButton.setText("Update");
    updateButton.addActionListener(new UserValueDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(20, 362, 96, 28));
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new UserValueDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(257, 362, 96, 28));

    jScrollPane1.setBounds(new Rectangle(203, 282, 149, 59));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);

    jTextArea1.setLineWrap(true);
    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText(myEnhancedUserValue.getDescription());
    userData.add(logoLabel, null);
    userData.add(updateButton, null);
    userData.add(jLabel2, null);
    userData.add(jLabel7, null);
    userData.add(jLabel6, null);
    userData.add(jLabel1, null);
    userData.add(jLabel3, null);
    userData.add(jLabel4, null);
    userData.add(jTextField3, null);
    userData.add(jTextField5, null);
    userData.add(jTextField4, null);
    userData.add(jPasswordField1, null);
    userData.add(jTextField1, null);
    userData.add(jTextField2, null);
    userData.add(jLabel5, null);
    userData.add(jScrollPane1, null);
    userData.add(cancelButton, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(userData, null);


    this.setBounds(0, 0, 377, 440);

    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }

    void updateButton_actionPerformed(ActionEvent e) {

      if ( (jTextField1.getText().equals("")) ||
           (jTextField2.getText().equals("")) ||
           (jTextField3.getText().equals("")) ||
           (jTextField4.getText().equals("")) ||
           (jPasswordField1.getText().equals("")) )
      {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Data is missing in one of the fields",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
         return;
      }

      EditableUserValue editableUserValue = new EditableUserValue();
      editableUserValue.setDescription(jTextArea1.getText());
      editableUserValue.setEmail(jTextField3.getText());
      editableUserValue.setFirstName(jTextField1.getText());
      editableUserValue.setLastName(jTextField2.getText());
      editableUserValue.setLoginName(jTextField4.getText());
      editableUserValue.setPassword(jPasswordField1.getText());
      editableUserValue.setUserID(myTeamMember.getLocalEnhancedUser().getUserID());
      editableUserValue.setAdministrator(myTeamMember.getLocalEnhancedUser().getAdministrator());
      editableUserValue.setPrimaryKey(myTeamMember.getLocalEnhancedUser().getUserID());
      try{
        myTeamMember.updateUser(editableUserValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }

      this.dispose();
    }

} //end of class UserDataDialog


class UserValueDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      UserValueDialog adaptee;

      UserValueDialog_updateButton_actionAdapter(UserValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class UserValueDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      UserValueDialog adaptee;

      UserValueDialog_cancelButton_actionAdapter(UserValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class



