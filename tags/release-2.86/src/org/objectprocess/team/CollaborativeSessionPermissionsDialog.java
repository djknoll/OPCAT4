package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsPK;
import org.objectprocess.SoapClient.CollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class CollaborativeSessionPermissionsDialog extends JDialog {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	TeamMember myTeamMember;

	EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

	Integer myCollaborativeSessionID;

	Integer myAccessControl;

	Integer myUserId;

	int myAccessControlIntValue; // this is the int valuse of the access
									// control after disabling the logged in
									// flag.

	UserValue userValue = null;

	JTabbedPane tabs = new JTabbedPane();

	JPanel collaborativeSessionMyPermissions = new JPanel();

	JPanel collaborativeSessionGivePermissions = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JButton updateButton = new JButton();

	JButton cancelButton = new JButton();

	JCheckBox creator = new JCheckBox();

	JCheckBox administrator = new JCheckBox();

	JCheckBox sessionEditor = new JCheckBox();

	JCheckBox sessionViewer = new JCheckBox();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JCheckBox createSessionEditor = new JCheckBox();

	JCheckBox createSessionViewer = new JCheckBox();

	JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);

	JCheckBox sessionCommiter = new JCheckBox();

	JCheckBox createSessionCommiter = new JCheckBox();

	JTextField jTextField2 = new JTextField();

	JLabel jLabel4 = new JLabel();

	JCheckBox createAdministrator = new JCheckBox();

	public CollaborativeSessionPermissionsDialog(
			TeamMember teamMember,
			EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;
		this.myCollaborativeSessionID = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSessionID();
		this.myAccessControl = this.myEnhancedCollaborativeSessionPermissionsValue
				.getAccessControl();
		this.myUserId = this.myEnhancedCollaborativeSessionPermissionsValue.getUserID();
		this.myAccessControlIntValue = this.myAccessControl.intValue();
		if (this.myAccessControlIntValue >= PermissionFlags.LOGGEDIN.intValue()) {
			this.myAccessControlIntValue -= PermissionFlags.LOGGEDIN.intValue();
		}

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Session's Permissions Handling");
		this.collaborativeSessionMyPermissions.setLayout(null);
		// collaborativeSessionGivePermissions.setBounds(new Rectangle(-2, 0,
		// 451, 360));
		this.collaborativeSessionGivePermissions.setLayout(null);
		this.tabs.setBounds(new Rectangle(-4, 7, 435, 268));

		this.logoLabel.setBounds(new Rectangle(369, 21, 50, 50));

		this.updateButton.setText("Get Permission");
		this.updateButton
				.addActionListener(new CollaborativeSessionAccessDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(22, 212, 145, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new CollaborativeSessionAccessDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(335, 285, 96, 28));

		this.creator.setEnabled(false);
		this.creator.setText("Creator");
		this.creator.setBounds(new Rectangle(8, 58, 97, 27));
		this.administrator.setEnabled(false);
		this.administrator.setToolTipText("");
		this.administrator.setText("Administrator");
		this.administrator.setBounds(new Rectangle(182, 58, 119, 27));
		this.sessionEditor.setEnabled(false);
		this.sessionEditor.setText("Sessions\'s Editor");
		this.sessionEditor.setBounds(new Rectangle(182, 137, 158, 27));
		this.sessionViewer.setEnabled(false);
		this.sessionViewer.setText("Session\'s Viewer");
		this.sessionViewer.setBounds(new Rectangle(182, 181, 169, 27));
		this.sessionCommiter.setEnabled(false);
		this.sessionCommiter.setText("Session\'s Commiter");
		this.sessionCommiter.setBounds(new Rectangle(182, 101, 216, 23));

		if (this.myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) {
			this.creator.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
			this.administrator.setSelected(true);
		}
		// if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())
		// moderator.setSelected(true);
		if (this.myAccessControlIntValue >= PermissionFlags.COMMITTER.intValue()) {
			this.sessionCommiter.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.EDITOR.intValue()) {
			this.sessionEditor.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.VIEWER.intValue()) {
			this.sessionViewer.setSelected(true);
		}

		this.jLabel3.setText("Member Login Name:");
		this.jLabel3.setBounds(new Rectangle(22, 30, 145, 18));
		this.jTextField1.setText("");
		this.jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

		this.createAdministrator.setText("Administrator");
		this.createAdministrator.setBounds(new Rectangle(165, 67, 120, 23));
		this.createAdministrator.setEnabled(false);
		this.createSessionEditor.setText("Session\'s Editor");
		this.createSessionEditor.setBounds(new Rectangle(165, 134, 150, 27));
		this.createSessionEditor.setEnabled(false);
		this.createSessionViewer.setText("Session\'s Viewer");
		this.createSessionViewer.setBounds(new Rectangle(165, 173, 146, 27));
		this.createSessionViewer.setEnabled(false);
		this.logoLabel1.setBounds(new Rectangle(361, 27, 50, 50));
		this.createSessionCommiter.setText("Session\'s Commiter");
		this.createSessionCommiter.setBounds(new Rectangle(165, 100, 182, 23));
		this.createSessionCommiter.setEnabled(false);

		Calendar calendar = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSession().getLastUpdate();
		this.jTextField2.setText(calendar.getTime().toString());
		this.jTextField2.setBounds(new Rectangle(79, 21, 205, 24));
		this.jTextField2.setEditable(false);
		this.jLabel4.setText("Join Time:");
		this.jLabel4.setBounds(new Rectangle(8, 24, 71, 18));
		this.collaborativeSessionMyPermissions.add(this.creator, null);
		this.collaborativeSessionMyPermissions.add(this.logoLabel, null);
		this.collaborativeSessionMyPermissions.add(this.administrator, null);
		this.collaborativeSessionMyPermissions.add(this.jTextField2, null);
		this.collaborativeSessionMyPermissions.add(this.jLabel4, null);
		this.collaborativeSessionMyPermissions.add(this.sessionCommiter, null);
		this.collaborativeSessionMyPermissions.add(this.sessionEditor, null);
		this.collaborativeSessionMyPermissions.add(this.sessionViewer, null);
		this.collaborativeSessionGivePermissions.add(this.jLabel3, null);
		this.collaborativeSessionGivePermissions.add(this.jTextField1, null);
		this.collaborativeSessionGivePermissions.add(this.logoLabel1, null);
		this.collaborativeSessionGivePermissions.add(this.createAdministrator, null);
		this.collaborativeSessionGivePermissions.add(this.updateButton, null);
		this.collaborativeSessionGivePermissions.add(this.createSessionViewer, null);
		this.collaborativeSessionGivePermissions.add(this.createSessionCommiter, null);
		this.collaborativeSessionGivePermissions.add(this.createSessionEditor, null);
		this.tabs.add(this.collaborativeSessionMyPermissions, "My Permissions");

		this.getContentPane().add(this.cancelButton, null);

		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
			this.tabs.add(this.collaborativeSessionGivePermissions,
					"Update Member\'s Permission ");
		}

		this.getContentPane().add(this.tabs, null);

		this.setBounds(0, 0, 460, 360);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void updateButton_actionPerformed(ActionEvent e) {

		if (this.updateButton.getText().equals("Get Permission")) {

			if (this.jTextField1.getText().equals("")) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"Member Login Name is missing", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// request from server the userID of the given member login name
			try {
				this.userValue = this.myTeamMember.getUserByLoginName(this.jTextField1
						.getText());
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
				if (this.userValue == null) {
					this.dispose();
				}
			}

			// if the userValue is null and no exception happen,
			// that means that the user does not exit - in such case, dispose
			// the window
			// after an error message
			if (this.userValue == null) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(), "User "
						+ this.jTextField1.getText() + " does not exist", "Error",
						JOptionPane.ERROR_MESSAGE);
				this.dispose();
				return;
			}

			// first check that the user that we change his permission is not
			// me.
			if (this.myUserId.equals(this.userValue.getUserID())) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"You can not changed your personal permissions",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			// fetch from server the current access control flag for this user
			CollaborativeSessionPermissionsPK collaborativeSessionPermissionsPK = new CollaborativeSessionPermissionsPK();
			collaborativeSessionPermissionsPK.setUserID(this.userValue.getUserID());
			collaborativeSessionPermissionsPK
					.setCollaborativeSessionID(this.myEnhancedCollaborativeSessionPermissionsValue
							.getCollaborativeSessionID());
			CollaborativeSessionPermissionsValue collaborativeSessionPermissionsValue = null;
			try {
				collaborativeSessionPermissionsValue = this.myTeamMember
						.fetchCollaborativeSessionPermissionsForUser(collaborativeSessionPermissionsPK);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}
			// if the user is "new in the session" then
			// collaborativeSessionPermissionsValue will be null.
			int currentMemberAccessControl;
			if (collaborativeSessionPermissionsValue == null) {
				currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS
						.intValue();
			} else {
				currentMemberAccessControl = collaborativeSessionPermissionsValue
						.getAccessControl().intValue();
			}
			// ignore loggedIn bit.
			if (currentMemberAccessControl >= PermissionFlags.LOGGEDIN
					.intValue()) {
				currentMemberAccessControl -= PermissionFlags.LOGGEDIN
						.intValue();
			}
			// clean the area from the previos request
			// createModerator.setEnabled(true);
			this.createSessionCommiter.setEnabled(true);
			this.createSessionEditor.setEnabled(true);
			this.createSessionViewer.setEnabled(true);
			this.createAdministrator.setSelected(false);
			// createModerator.setSelected(false);
			this.createSessionViewer.setSelected(false);
			this.createSessionCommiter.setSelected(false);
			this.createSessionEditor.setSelected(false);

			if (currentMemberAccessControl >= PermissionFlags.ADMINISTRATOR
					.intValue()) {
				this.createAdministrator.setSelected(true);
			}
			// if (currentMemberAccessControl >=
			// PermissionFlags.MODERATOR.intValue())
			// createModerator.setSelected(true);
			if (currentMemberAccessControl >= PermissionFlags.COMMITTER
					.intValue()) {
				this.createSessionCommiter.setSelected(true);
			}
			if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue()) {
				this.createSessionEditor.setSelected(true);
			}
			if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue()) {
				this.createSessionViewer.setSelected(true);
			}

			// if the member is administrator, we are not allowed to change its
			// perimssions
			if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR
					.intValue()) {
				// createModerator.setEnabled(true);
				this.createSessionEditor.setEnabled(true);
				this.createSessionViewer.setEnabled(true);
				this.createSessionCommiter.setEnabled(true);
				this.jTextField1.setEnabled(false);
				this.updateButton.setText("Update");
			}
			return;
		}

		else { // "update" state
			Integer newAccessControlForMember;
			// first find out what the user choose to do.
			// if (createAdministrator.isSelected()) myAccessControl = new
			// Integer(1364);
			// if (createModerator.isSelected()) newAccessControlForMember =
			// PermissionFlags.MODERATOR; else
			if (this.createSessionCommiter.isSelected()) {
				newAccessControlForMember = PermissionFlags.COMMITTER;
			} else if (this.createSessionEditor.isSelected()) {
				newAccessControlForMember = PermissionFlags.EDITOR;
			} else if (this.createSessionViewer.isSelected()) {
				newAccessControlForMember = PermissionFlags.VIEWER;
			} else {
				newAccessControlForMember = PermissionFlags.NULL_PERMISSIONS;
			}

			this.dispose();

			// build the editable class and send the reques to the server
			EditableCollaborativeSessionPermissionsValue editableCollaborativeSessionPermissionsValue = this.myTeamMember
					.createEditableCollaborativeSessionPermissionsValue(
							this.userValue.getUserID(), this.myCollaborativeSessionID,
							newAccessControlForMember);

			// transmit request ot server
			try {
				this.myTeamMember
						.updateCollaborativeSessionPermissions(editableCollaborativeSessionPermissionsValue);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}
		}
	}
} // end of class CollaborativeSessionAccessDialog

class CollaborativeSessionAccessDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	CollaborativeSessionPermissionsDialog adaptee;

	CollaborativeSessionAccessDialog_updateButton_actionAdapter(
			CollaborativeSessionPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class CollaborativeSessionAccessDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	CollaborativeSessionPermissionsDialog adaptee;

	CollaborativeSessionAccessDialog_cancelButton_actionAdapter(
			CollaborativeSessionPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

