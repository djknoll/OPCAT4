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
import org.objectprocess.SoapClient.EditableOpmModelValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.MetaRevisionValue;


public class OpmModelValueDialog extends JDialog {

  TeamMember myTeamMember;
  EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;
  Integer majorRevision = new Integer(0);
  Integer minorRevision = new Integer(0);


  JPanel OpmModel = new JPanel();
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
  JTextArea jTextArea1 = new JTextArea();
  JLabel updateMessage = new JLabel();


public OpmModelValueDialog(TeamMember teamMember,
                           EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue) throws HeadlessException {
  super(Opcat2.getFrame(),true);

  myTeamMember=teamMember;
  myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;

  try {
    Object[] metaRevisionsList = myTeamMember.fatchOpmModelAllRevisions(
          myEnhancedOpmModelPermissionsValue.getOpmModelID().intValue());

    int index = teamMember.findHigestRevision(metaRevisionsList);
    if (index != -1) {
      majorRevision = ((MetaRevisionValue)(metaRevisionsList[index])).getMajorRevision();
      minorRevision = ((MetaRevisionValue)(metaRevisionsList[index])).getMinorRevision();
    }

    jbInit();
  }
  catch(Exception e) {
    e.printStackTrace();
  }
}

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("OPM Model Details");
    OpmModel.setLayout(null);
    OpmModel.setBounds(new Rectangle(0, 0, 369, 310));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("OpmModel Name:");
    jLabel1.setBounds(new Rectangle(84, 31, 121, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Description:");
    jLabel4.setBounds(new Rectangle(84, 165, 97, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Creation Date:");
    jLabel3.setBounds(new Rectangle(84, 118, 97, 21));
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("VC Last Revision:");
    jLabel5.setBounds(new Rectangle(84, 73, 108, 23));


    logoLabel.setBounds(new Rectangle(19, 31, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText(myEnhancedOpmModelPermissionsValue.getOpmModel().getOpmModelName());
    jTextField1.setBounds(new Rectangle(195, 31, 149, 29));
    jTextField3.setBounds(new Rectangle(195, 118, 149, 29));
    Calendar calendar = myEnhancedOpmModelPermissionsValue.getOpmModel().getCreationTime();
    jTextField3.setText(calendar.getTime().toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);
    jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField4.setEditable(false);
    jTextField4.setText(majorRevision +"."+ minorRevision);
    jTextField4.setBounds(new Rectangle(195, 73, 149, 29));

    updateButton.setText("Update");
    updateButton.addActionListener(new OpmModelValueDialog_updateButton_actionAdapter(this));
    updateButton.setBounds(new Rectangle(19, 260, 96, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new OpmModelValueDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(248, 260, 96, 28));


    jScrollPane1.setBounds(new Rectangle(194, 165, 149, 67));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);

    jTextArea1.setLineWrap(true);
    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText(myEnhancedOpmModelPermissionsValue.getOpmModel().getDescription());
    updateMessage.setText("Only the administrator can perform update action!");
    updateMessage.setBounds(new Rectangle(19, 244, 343, 15));
    OpmModel.add(logoLabel, null);
    OpmModel.add(jLabel4, null);
    OpmModel.add(jLabel1, null);
    OpmModel.add(jLabel5, null);
    OpmModel.add(jLabel3, null);
    OpmModel.add(jTextField1, null);
    OpmModel.add(jTextField4, null);
    OpmModel.add(cancelButton, null);
    OpmModel.add(jTextField3, null);
    OpmModel.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(OpmModel, null);

    //diable this button if the user is not creator or admin .
    Integer accessControl = myEnhancedOpmModelPermissionsValue.getAccessControl();
    if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()){
      jTextField1.setEditable(false);
      jTextArea1.setEditable(false);
      OpmModel.add(updateMessage, null);
    }
    else
      OpmModel.add(updateButton, null);


    this.setBounds(0, 0, 369, 340);
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
      if ( (jTextField1.getText().equals("")) )
         {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                          "Data is missing in one of the fields",
                                          "Error",
                                          JOptionPane.ERROR_MESSAGE);
            return;
          }

      this.dispose();

      EditableOpmModelValue editableOpmModelValue = new EditableOpmModelValue();
      editableOpmModelValue.setDescription(jTextArea1.getText());
      editableOpmModelValue.setOpmModelName(jTextField1.getText());
      editableOpmModelValue.setOpmModelID(myEnhancedOpmModelPermissionsValue.getOpmModelID());
      editableOpmModelValue.setPrimaryKey(myEnhancedOpmModelPermissionsValue.getOpmModelID());
      editableOpmModelValue.setWorkgroupID(myEnhancedOpmModelPermissionsValue.getOpmModel().getWorkgroupID());

      try{
        myTeamMember.updateOpmModel(editableOpmModelValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
    }


} //end of class OpmModelDialog


class OpmModelValueDialog_updateButton_actionAdapter implements java.awt.event.ActionListener {
      OpmModelValueDialog adaptee;

      OpmModelValueDialog_updateButton_actionAdapter(OpmModelValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.updateButton_actionPerformed(e);
      }
} //end of class

class OpmModelValueDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      OpmModelValueDialog adaptee;

      OpmModelValueDialog_cancelButton_actionAdapter(OpmModelValueDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class



