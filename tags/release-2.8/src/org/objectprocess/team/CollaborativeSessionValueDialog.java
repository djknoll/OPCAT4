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
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class CollaborativeSessionValueDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

  JPanel collaborativeSession = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel5 = new JLabel();

  JTextField jTextField1 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();

  JButton updateButton = new JButton();
  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JLabel jLabel8 = new JLabel();
  JTextField jTextField5 = new JTextField();
  JTextField jTextField6 = new JTextField();
  JTextField jTextField7 = new JTextField();
  JTextArea jTextArea1 = new JTextArea();
  JLabel updateMessage = new JLabel();


public CollaborativeSessionValueDialog(TeamMember teamMember,
                                       EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;

          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

     //get the user name of the Token Holder from the server
     UserValue tokenHolderValue= null;
     tokenHolderValue = myTeamMember.getUserByPK(myEnhancedCollaborativeSessionPermissionsValue.
                                                 getCollaborativeSession().getTokenHolderID().intValue());

     String tokenHolderName;
     if (tokenHolderValue ==null) tokenHolderName=new String("");
     else tokenHolderName=new String(tokenHolderValue.getFirstName()+" "+tokenHolderValue.getLastName());

    this.getContentPane().setLayout(null);
    this.setTitle("Collaborative Session Details");
    collaborativeSession.setLayout(null);
    collaborativeSession.setBounds(new Rectangle(0, 0, 381, 422));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Session Name:");
    jLabel1.setBounds(new Rectangle(101, 23, 107, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Description:");
    jLabel4.setBounds(new Rectangle(101, 284, 97, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Creation Date:");
    jLabel3.setBounds(new Rectangle(101, 107, 97, 21));
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("VC Revision:");
    jLabel5.setBounds(new Rectangle(101, 64, 108, 23));
    jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel6.setText("Last Update:");
    jLabel6.setBounds(new Rectangle(101, 150, 97, 21));
    jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel7.setText("Token Holder:");
    jLabel7.setBounds(new Rectangle(101, 193, 97, 21));
    jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel8.setText("Token Timeout:");
    jLabel8.setBounds(new Rectangle(101, 237, 124, 21));

    logoLabel.setBounds(new Rectangle(22, 23, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getCollaborativeSessionName());
    jTextField1.setBounds(new Rectangle(208, 23, 149, 29));
    jTextField3.setBounds(new Rectangle(208, 107, 149, 29));
    Calendar calendar = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getCreationTime();
    jTextField3.setText(calendar.getTime().toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);
    jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField4.setEditable(false);
    jTextField4.setBounds(new Rectangle(208, 64, 149, 29));
    jTextField5.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField5.setEditable(false);

    calendar = myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getLastUpdate();
    jTextField4.setText(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getMajorRevision()
                   +"."+myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getMinorRevision());
    jTextField5.setText(calendar.getTime().toString());
    jTextField5.setBounds(new Rectangle(208, 150, 149, 29));
    jTextField6.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField6.setEditable(false);
    jTextField6.setText(tokenHolderName);
    jTextField6.setBounds(new Rectangle(208, 193, 149, 29));
    jTextField7.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField7.setText(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getTokenTimeout().toString());
    jTextField7.setBounds(new Rectangle(209, 237, 149, 29));
    jTextArea1.setText(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getDescription());

    updateButton.setText("Update");
    updateButton.addActionListener(new CollaborativeSessionValueDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(22, 370, 96, 28));
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new CollaborativeSessionValueDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(263, 370, 96, 28));


    jScrollPane1.setBounds(new Rectangle(209, 284, 149, 59));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);

    updateMessage.setText("Only the administrator can perform update action.");
    updateMessage.setBounds(new Rectangle(22, 355, 318, 15));
    collaborativeSession.add(logoLabel, null);
    collaborativeSession.add(jLabel4, null);
    collaborativeSession.add(jLabel1, null);
    collaborativeSession.add(jLabel5, null);
    collaborativeSession.add(jLabel3, null);
    collaborativeSession.add(jLabel6, null);
    collaborativeSession.add(jLabel7, null);
    collaborativeSession.add(jLabel8, null);
    collaborativeSession.add(jTextField1, null);
    collaborativeSession.add(jTextField4, null);
    collaborativeSession.add(jTextField3, null);
    collaborativeSession.add(jTextField5, null);
    collaborativeSession.add(jTextField6, null);
    collaborativeSession.add(jScrollPane1, null);
    collaborativeSession.add(jTextField7, null);
    collaborativeSession.add(cancelButton, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(collaborativeSession, null);

    //diable this button if the user is not creator or admin .
    Integer accessControl = myEnhancedCollaborativeSessionPermissionsValue.getAccessControl();
    if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
      jTextField1.setEditable(false);
      jTextField7.setEditable(false);
      jTextArea1.setEditable(false);
      collaborativeSession.add(updateMessage, null);

    }
    else
    collaborativeSession.add(updateButton, null);

    this.setBounds(0, 0, 381, 452);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }

    void updateButton_actionPerformed(ActionEvent e) {
      //first check that all feilds are filled with information.
      if ( (jTextField1.getText().equals("")) ||
           (jTextField7.getText().equals(""))  )
         { JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                          "Data is missing in one of the fields",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
           return;
         }

      this.dispose();

      EditableCollaborativeSessionValue editableCollaborativeSessionValue =
          new EditableCollaborativeSessionValue();
      editableCollaborativeSessionValue.setCollaborativeSessionName(jTextField1.getText());
      editableCollaborativeSessionValue.setDescription(jTextArea1.getText());
      editableCollaborativeSessionValue.setTokenTimeout(new Integer(jTextField7.getText()));
      editableCollaborativeSessionValue.setUserTimeout(new Integer(0));
      editableCollaborativeSessionValue.setCollaborativeSessionID(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID());
      editableCollaborativeSessionValue.setPrimaryKey(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSessionID());
      editableCollaborativeSessionValue.setOpmModelID(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getOpmModelID());
      editableCollaborativeSessionValue.setMajorRevision(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getMajorRevision());
      editableCollaborativeSessionValue.setMinorRevision(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getMinorRevision());
      editableCollaborativeSessionValue.setRevisionID(myEnhancedCollaborativeSessionPermissionsValue.getCollaborativeSession().getRevisionID());


      try {
        myTeamMember.updateCollaborativeSession(editableCollaborativeSessionValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }

} //end of class CollaborativeSessionDialog


class CollaborativeSessionValueDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      CollaborativeSessionValueDialog adaptee;

      CollaborativeSessionValueDialog_updateButton_actionAdapter(CollaborativeSessionValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class CollaborativeSessionValueDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      CollaborativeSessionValueDialog adaptee;

      CollaborativeSessionValueDialog_cancelButton_actionAdapter(CollaborativeSessionValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class





