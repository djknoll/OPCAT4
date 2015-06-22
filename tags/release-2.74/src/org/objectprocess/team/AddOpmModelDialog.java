package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableOpmModelValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AddOpmModelDialog extends JDialog {

  protected TeamMember myTeamMember;
  protected EditableOpmModelValue myEditableOpmModelValue;
  protected EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;
  protected Opcat2 myOpcat2;
  protected Date creationDate = (new GregorianCalendar()).getTime();

  JPanel OpmModel = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();

  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();
  JTextField jTextField3 = new JTextField();
  JTextField jTextField4 = new JTextField();

  JButton createButton = new JButton();
  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextField jTextField5 = new JTextField();
  JTextArea jTextArea1 = new JTextArea();


public AddOpmModelDialog(TeamMember teamMember,
                         EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue,
                         Opcat2 opcat2) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myEnhancedWorkgroupPermissionsValue=enhancedWorkgroupPermissionsValue;
    myOpcat2 = opcat2;
          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Create New OPM Model");
    OpmModel.setLayout(null);
    OpmModel.setBounds(new Rectangle(0, 0, 388, 330));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("OPM Model Name:");
    jLabel1.setBounds(new Rectangle(84, 32, 126, 21));
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Creator:");
    jLabel2.setBounds(new Rectangle(84, 141, 107, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Creation Date:");
    jLabel4.setBounds(new Rectangle(84, 176, 107, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(84, 212, 107, 21));
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("Workgroup Name:");
    jLabel5.setBounds(new Rectangle(84, 70, 130, 21));
    jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel6.setText("VC Revision:");
    jLabel6.setBounds(new Rectangle(84, 105, 107, 21));

    logoLabel.setBounds(new Rectangle(15, 32, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(216, 32, 149, 25));
    jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField2.setText(myTeamMember.getLocalEnhancedUser().getFirstName()+" "
                        +myTeamMember.getLocalEnhancedUser().getLastName());
    jTextField2.setBounds(new Rectangle(216, 141, 149, 25));
    jTextField2.setEditable(false);
    jTextField3.setBounds(new Rectangle(216, 176, 149, 25));
    jTextField3.setText(creationDate.toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);
    jTextField4.setText(myEnhancedWorkgroupPermissionsValue.getWorkgroup().getWorkgroupName());
    jTextField4.setBounds(new Rectangle(216, 70, 149, 25));
    jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField4.setEditable(false);
    jTextField5.setBounds(new Rectangle(216, 105, 149, 25));
    jTextField5.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField5.setEditable(false);
    jTextField5.setText("0.0");

    createButton.setText("Create");
    createButton.addActionListener(new AddOpmModelDialog_createButton_actionAdapter(this));
    createButton.setBounds(new Rectangle(15, 284, 96, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new AddOpmModelDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(269, 284, 96, 28));

    jScrollPane1.setBounds(new Rectangle(216, 212, 152, 45));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                  HORIZONTAL_SCROLLBAR_NEVER);

    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText("");
    OpmModel.add(createButton, null);
    OpmModel.add(cancelButton, null);
    OpmModel.add(logoLabel, null);
    OpmModel.add(jLabel3, null);
    OpmModel.add(jLabel1, null);
    OpmModel.add(jLabel5, null);
    OpmModel.add(jLabel6, null);
    OpmModel.add(jLabel2, null);
    OpmModel.add(jLabel4, null);
    OpmModel.add(jTextField3, null);
    OpmModel.add(jTextField1, null);
    OpmModel.add(jTextField4, null);
    OpmModel.add(jTextField5, null);
    OpmModel.add(jTextField2, null);
    OpmModel.add(jScrollPane1, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(OpmModel, null);


    this.setBounds(0, 0, 388, 360);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }
    void createButton_actionPerformed(ActionEvent e) {

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
      editableOpmModelValue.setWorkgroupID(myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
      EnhancedOpmModelPermissionsValue eompv = null;

      try {
        eompv = myTeamMember.createOpmModel(editableOpmModelValue);
      }catch (Exception ex) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(ex);
      }
      if (eompv != null) {
        //add the new OPM model to the tree
        OpmModelTreeNode om = new OpmModelTreeNode(eompv);
        myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(om);

      }
    }
  } //end of class AddOpmModelDialog

class AddOpmModelDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  AddOpmModelDialog adaptee;

  AddOpmModelDialog_cancelButton_actionAdapter(AddOpmModelDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class AddOpmModelDialog_createButton_actionAdapter implements java.awt.event.ActionListener {
  AddOpmModelDialog adaptee;

  AddOpmModelDialog_createButton_actionAdapter(AddOpmModelDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.createButton_actionPerformed(e);
  }
}


