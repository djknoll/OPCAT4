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
import org.objectprocess.SoapClient.EditableWorkgroupValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AddWorkgroupDialog extends JDialog {

  protected TeamMember myTeamMember;
  protected Date creationDate = (new GregorianCalendar()).getTime();
  protected Opcat2 myOpcat2;

  JPanel WorkGroup = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel3 = new JLabel();

  JTextField jTextField1 = new JTextField();
  JTextField jTextField2 = new JTextField();

  JButton createButton = new JButton();
  JButton cancelButton = new JButton();
  JTextField jTextField3 = new JTextField();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();


public AddWorkgroupDialog(TeamMember teamMember,Opcat2 opcat2) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember= teamMember;
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
    this.setTitle("Create New Workgroup");
    WorkGroup.setBounds(new Rectangle(0, 0, 381, 266));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Workgroup Name:");
    jLabel1.setBounds(new Rectangle(86, 18, 119, 21));
    jLabel2.setEnabled(true);
    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Creator:");
    jLabel2.setBounds(new Rectangle(86, 54, 97, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Description:");
    jLabel4.setBounds(new Rectangle(86, 140, 97, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Creation Date:");
    jLabel3.setBounds(new Rectangle(86, 93, 97, 21));

    WorkGroup.setLayout(null);

    logoLabel.setBounds(new Rectangle(16, 18, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(207, 18, 149, 27));
    jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField2.setText(myTeamMember.getLocalEnhancedUser().getFirstName()+" "
                        +myTeamMember.getLocalEnhancedUser().getLastName());
    jTextField2.setBounds(new Rectangle(207, 54, 149, 27));
    jTextField2.setEditable(false);
    jTextField3.setBounds(new Rectangle(207, 93, 149, 29));
    jTextField3.setText(creationDate.toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);


    createButton.setText("Create");
    createButton.addActionListener(new AddWorkgroupDialog_createButton_actionAdapter(this));
    createButton.setBounds(new Rectangle(16, 220, 96, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new AddWorkgroupDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(260, 220, 96, 28));

    jScrollPane1.setBounds(new Rectangle(207, 140, 149, 55));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                      HORIZONTAL_SCROLLBAR_NEVER);
    jTextArea1.setLineWrap(true);
    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText("");
    WorkGroup.add(logoLabel, null);
    WorkGroup.add(createButton, null);
    WorkGroup.add(jTextField3, null);
    WorkGroup.add(jLabel1, null);
    WorkGroup.add(jLabel2, null);
    WorkGroup.add(jLabel3, null);
    WorkGroup.add(jLabel4, null);
    WorkGroup.add(jTextField1, null);
    WorkGroup.add(jTextField2, null);
    WorkGroup.add(jScrollPane1, null);
    WorkGroup.add(cancelButton, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(WorkGroup, null);

    this.setBounds(0, 0, 381, 296);
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

      EditableWorkgroupValue editableWorkgroupValue = new EditableWorkgroupValue();
      editableWorkgroupValue.setDescription(jTextArea1.getText());
      editableWorkgroupValue.setWorkgroupName(jTextField1.getText());
      EnhancedWorkgroupPermissionsValue ewpv=null;

      try {
          ewpv=myTeamMember.createWorkgroup(editableWorkgroupValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
      if (ewpv != null) {
        WorkgroupTreeNode wg = new WorkgroupTreeNode(ewpv);
        myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(wg);
      }
    }

} //end of class WorkGroupDialog

class AddWorkgroupDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  AddWorkgroupDialog adaptee;

  AddWorkgroupDialog_cancelButton_actionAdapter(AddWorkgroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class AddWorkgroupDialog_createButton_actionAdapter implements java.awt.event.ActionListener {
  AddWorkgroupDialog adaptee;

  AddWorkgroupDialog_createButton_actionAdapter(AddWorkgroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.createButton_actionPerformed(e);
  }
}


