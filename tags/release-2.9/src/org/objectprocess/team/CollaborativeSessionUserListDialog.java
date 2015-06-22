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
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionValue;

public class CollaborativeSessionUserListDialog extends JDialog {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	 
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

    this.myTeamMember=teamMember;
    this.myCollaborativeSessionID = collaborativeSessionID;

          try {
            this.jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("List Of Collaborative Session members");
    this.CollaborativeSession.setBounds(new Rectangle(0, 0, 280, 320));
    this.CollaborativeSession.setLayout(null);

    this.logoLabel.setBounds(new Rectangle(27, 28, 50, 50));

    this.cancelButton.setText("Cancel");
    this.cancelButton.addActionListener(new CollaborativeSessionUserListDialog_cancelButton_actionAdapter(this));
    this.cancelButton.setBounds(new Rectangle(149, 271, 96, 28));

    this.jScrollPane1.setBounds(new Rectangle(122, 28, 124, 128));
    this.jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                          HORIZONTAL_SCROLLBAR_NEVER);


    this.genList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    //Listen for when the selection changes.
    this.genList.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        UserInList userInList = (UserInList) CollaborativeSessionUserListDialog.this.genList.getSelectedValue();

    if (userInList == null) {
		return;
	}
    String userLoggedInStatus;
    if (userInList.myUserValue.getLoggedIn().booleanValue()) {
		userLoggedInStatus = "Logged In";
	}
    userLoggedInStatus = "Logged Out";

    CollaborativeSessionUserListDialog.this.jTextArea1.setText("");
    CollaborativeSessionUserListDialog.this.jTextArea1.setText(
            "Login Name: "+ userInList.myUserValue.getLoginName() +"\n"+
            "Description: "+ userInList.myUserValue.getDescription() +"\n"+
            "Email: " + userInList.myUserValue.getEmail()+"\n"+
            "Current Status: " + userLoggedInStatus);
   }
});


    //fetch the list of users from server
    EnhancedCollaborativeSessionValue enhancedCollaborativeSessionValue=this.myTeamMember.fatchEnhancedCollaborativeSession(this.myCollaborativeSessionID.intValue());

    if (enhancedCollaborativeSessionValue == null) {
		return;
	}

    int len = enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions().length;
    UserInList[] usersInList = new UserInList[len];

    for (int i=0; i<len; i++) {
      usersInList[i] = new UserInList(enhancedCollaborativeSessionValue.getCollaborativeSessionsPermissions()[i].getUser());
    }

    this.genList.setListData(usersInList);
    this.genList.setToolTipText("");
//    genList.setSelectedIndex(0);

    this.jScrollPane2.setBounds(new Rectangle(25, 180, 220, 69));
    this.jTextArea1.setBackground(Color.lightGray);
    this.jTextArea1.setEditable(false);
    this.CollaborativeSession.add(this.logoLabel, null);
    this.CollaborativeSession.add(this.jScrollPane1, null);
    this.CollaborativeSession.add(this.jScrollPane2, null);
    this.CollaborativeSession.add(this.cancelButton, null);
    this.jScrollPane2.getViewport().add(this.jTextArea1, null);
    this.jScrollPane1.getViewport().add(this.genList, null);
    this.getContentPane().add(this.CollaborativeSession, null);


    this.setBounds(0, 0, 280, 350);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);

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
    this.adaptee.cancelButton_actionPerformed(e);
  }
}




