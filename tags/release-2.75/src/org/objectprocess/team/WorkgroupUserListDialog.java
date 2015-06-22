package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EnhancedWorkgroupValue;

public class WorkgroupUserListDialog extends JDialog {

  TeamMember myTeamMember;
  Integer myWorkgroupID;

  JPanel Workgroup = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);


  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  private JList genList = new JList();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();


public WorkgroupUserListDialog(TeamMember teamMember, Integer WorkgroupID) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myWorkgroupID = WorkgroupID;

          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle(" List Of Workgroup members");
    Workgroup.setBounds(new Rectangle(0, 0, 280, 320));
    Workgroup.setLayout(null);

    logoLabel.setBounds(new Rectangle(27, 28, 50, 50));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new WorkgroupUserListDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(149, 271, 96, 28));

    jScrollPane1.setBounds(new Rectangle(122, 28, 124, 128));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);


    genList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //Listen for when the selection changes.
    genList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        UserInList userInList = (UserInList) genList.getSelectedValue();

    if (userInList == null) return;
    String userLoggedInStatus;
    if (userInList.myUserValue.getLoggedIn().booleanValue()) userLoggedInStatus = "Logged In";
    userLoggedInStatus = "Logged Out";

    jTextArea1.setText("");
    jTextArea1.setText(
            "Login Name: "+ userInList.myUserValue.getLoginName() +"\n"+
            "Description: "+ userInList.myUserValue.getDescription() +"\n"+
            "Email: " + userInList.myUserValue.getEmail()+"\n"+
            "Current Status: " + userLoggedInStatus);
   }
});


    //fetch the list of users from server
    EnhancedWorkgroupValue enhancedWorkgroupValue=myTeamMember.fatchEnhancedWorkgroup(myWorkgroupID.intValue());

    if (enhancedWorkgroupValue == null)
      return;

    int len = enhancedWorkgroupValue.getWorkgroupsPermissions().length;
    UserInList[] usersInList = new UserInList[len];

    for (int i=0; i<len; i++) {
      usersInList[i] = new UserInList(enhancedWorkgroupValue.getWorkgroupsPermissions()[i].getUser());
    }

    genList.setListData(usersInList);
    genList.setToolTipText("");
//    genList.setSelectedIndex(0);

    jScrollPane2.setBounds(new Rectangle(25, 180, 220, 69));
    jTextArea1.setBackground(Color.lightGray);
    jTextArea1.setEditable(false);
    Workgroup.add(logoLabel, null);
    Workgroup.add(jScrollPane1, null);
    Workgroup.add(jScrollPane2, null);
    Workgroup.add(cancelButton, null);
    jScrollPane2.getViewport().add(jTextArea1, null);
    jScrollPane1.getViewport().add(genList, null);
    this.getContentPane().add(Workgroup, null);


    this.setBounds(0, 0, 280, 350);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }


} //end of class WorkgroupUserList Dialog

class WorkgroupUserListDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  WorkgroupUserListDialog adaptee;

  WorkgroupUserListDialog_cancelButton_actionAdapter(WorkgroupUserListDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}



