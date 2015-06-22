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
import javax.swing.ScrollPaneConstants;

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
	  

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	 
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

    this.myTeamMember= teamMember;
    this.myOpcat2 = opcat2;

          try {
            this.jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Create New Workgroup");
    this.WorkGroup.setBounds(new Rectangle(0, 0, 381, 266));

    this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel1.setText("Workgroup Name:");
    this.jLabel1.setBounds(new Rectangle(86, 18, 119, 21));
    this.jLabel2.setEnabled(true);
    this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel2.setText("Creator:");
    this.jLabel2.setBounds(new Rectangle(86, 54, 97, 21));
    this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel4.setText("Description:");
    this.jLabel4.setBounds(new Rectangle(86, 140, 97, 21));
    this.jLabel3.setEnabled(true);
    this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    this.jLabel3.setText("Creation Date:");
    this.jLabel3.setBounds(new Rectangle(86, 93, 97, 21));

    this.WorkGroup.setLayout(null);

    this.logoLabel.setBounds(new Rectangle(16, 18, 50, 50));

    this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    this.jTextField1.setText("");
    this.jTextField1.setBounds(new Rectangle(207, 18, 149, 27));
    this.jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
    this.jTextField2.setText(this.myTeamMember.getLocalEnhancedUser().getFirstName()+" "
                        +this.myTeamMember.getLocalEnhancedUser().getLastName());
    this.jTextField2.setBounds(new Rectangle(207, 54, 149, 27));
    this.jTextField2.setEditable(false);
    this.jTextField3.setBounds(new Rectangle(207, 93, 149, 29));
    this.jTextField3.setText(this.creationDate.toString());
    this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    this.jTextField3.setEditable(false);


    this.createButton.setText("Create");
    this.createButton.addActionListener(new AddWorkgroupDialog_createButton_actionAdapter(this));
    this.createButton.setBounds(new Rectangle(16, 220, 96, 28));

    this.cancelButton.setText("Cancel");
    this.cancelButton.addActionListener(new AddWorkgroupDialog_cancelButton_actionAdapter(this));
    this.cancelButton.setBounds(new Rectangle(260, 220, 96, 28));

    this.jScrollPane1.setBounds(new Rectangle(207, 140, 149, 55));
    this.jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                      HORIZONTAL_SCROLLBAR_NEVER);
    this.jTextArea1.setLineWrap(true);
    this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    this.jTextArea1.setText("");
    this.WorkGroup.add(this.logoLabel, null);
    this.WorkGroup.add(this.createButton, null);
    this.WorkGroup.add(this.jTextField3, null);
    this.WorkGroup.add(this.jLabel1, null);
    this.WorkGroup.add(this.jLabel2, null);
    this.WorkGroup.add(this.jLabel3, null);
    this.WorkGroup.add(this.jLabel4, null);
    this.WorkGroup.add(this.jTextField1, null);
    this.WorkGroup.add(this.jTextField2, null);
    this.WorkGroup.add(this.jScrollPane1, null);
    this.WorkGroup.add(this.cancelButton, null);
    this.jScrollPane1.getViewport().add(this.jTextArea1, null);
    this.getContentPane().add(this.WorkGroup, null);

    this.setBounds(0, 0, 381, 296);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }
    void createButton_actionPerformed(ActionEvent e) {

      //first check that all feilds are filled with information.
      if ( (this.jTextField1.getText().equals("")) )
      {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                      "Data is missing in one of the fields",
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
        return;
      }

      this.dispose();

      EditableWorkgroupValue editableWorkgroupValue = new EditableWorkgroupValue();
      editableWorkgroupValue.setDescription(this.jTextArea1.getText());
      editableWorkgroupValue.setWorkgroupName(this.jTextField1.getText());
      EnhancedWorkgroupPermissionsValue ewpv=null;

      try {
          ewpv=this.myTeamMember.createWorkgroup(editableWorkgroupValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
        exceptionHandler.logError(exception) ;
      }
      if (ewpv != null) {
        WorkgroupTreeNode wg = new WorkgroupTreeNode(ewpv);
        this.myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(wg);
      }
    }

} //end of class WorkGroupDialog

class AddWorkgroupDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  AddWorkgroupDialog adaptee;

  AddWorkgroupDialog_cancelButton_actionAdapter(AddWorkgroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    this.adaptee.cancelButton_actionPerformed(e);
  }
}

class AddWorkgroupDialog_createButton_actionAdapter implements java.awt.event.ActionListener {
  AddWorkgroupDialog adaptee;

  AddWorkgroupDialog_createButton_actionAdapter(AddWorkgroupDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    this.adaptee.createButton_actionPerformed(e);
  }
}


