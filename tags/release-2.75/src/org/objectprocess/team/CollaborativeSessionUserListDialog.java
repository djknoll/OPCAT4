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
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue;

public class CollaborativeSessionUserListDialog extends JDialog {

  TeamMember myTeamMember;
  Integer myCollaborativeSessionID;

  JPanel CollaborativeSession = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);


  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  private JList genList = new JList();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();


public CollaborativeSessionUserListDialog(TeamMember teamMember, Integer collaborativeSessionID) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myCollaborativeSessionID = collaborativeSessionID;

          try {
            jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("List Of Collaborative Session members");
    CollaborativeSession.setBounds(new Rectangle(0, 0, 280, 320));
    CollaborativeSession.setLayout(null);

    logoLabel.setBounds(new Rectangle(27, 28, 50, 50));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new CollaborativeSessionUserListDialog_cancelButton_actionAdapter(this));
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
    EnhancedCollaborativeSessionValue enhancedCollaborativeSessionValue=myTeamMember.fatchEnhancedCollaborativeSession(myCollaborativeSessionID.intValue());

    if (enhancedCollaborativeSessionValue == null)
      return;

    int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
    UserInList[] usersInList = new UserInList[len];

    for (int i=0; i<len; i++) {
      usersInList[i] = new UserInList(enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser());
    }

    genList.setListData(usersInList);
    genList.setToolTipText("");
//    genList.setSelectedIndex(0);

    jScrollPane2.setBounds(new Rectangle(25, 180, 220, 69));
    jTextArea1.setBackground(Color.lightGray);
    jTextArea1.setEditable(false);
    CollaborativeSession.add(logoLabel, null);
    CollaborativeSession.add(jScrollPane1, null);
    CollaborativeSession.add(jScrollPane2, null);
    CollaborativeSession.add(cancelButton, null);
    jScrollPane2.getViewport().add(jTextArea1, null);
    jScrollPane1.getViewport().add(genList, null);
    this.getContentPane().add(CollaborativeSession, null);


    this.setBounds(0, 0, 280, 350);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }


} //end of class CollaborativeSessionUserList Dialog

class CollaborativeSessionUserListDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  CollaborativeSessionUserListDialog adaptee;

  CollaborativeSessionUserListDialog_cancelButton_actionAdapter(CollaborativeSessionUserListDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}




