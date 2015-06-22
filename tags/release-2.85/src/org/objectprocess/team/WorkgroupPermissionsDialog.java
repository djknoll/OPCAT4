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
import org.objectprocess.SoapClient.EditableWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;
import org.objectprocess.SoapClient.UserValue;
import org.objectprocess.SoapClient.WorkgroupPermissionsPK;
import org.objectprocess.SoapClient.WorkgroupPermissionsValue;

public class WorkgroupPermissionsDialog extends JDialog {

	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	TeamMember myTeamMember;

	EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

	Integer myWorkgroupID;

	Integer myAccessControl;

	int myAccessControlIntValue;

	Integer myUserId;

	UserValue userValue = null;

	JTabbedPane tabs = new JTabbedPane();

	JPanel workgroupMyPermissions = new JPanel();

	JPanel workgroupGivePermissions = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JButton updateButton = new JButton();

	JButton cancelButton = new JButton();

	JCheckBox creator = new JCheckBox();

	JCheckBox administrator = new JCheckBox();

	JCheckBox modelCreator = new JCheckBox();

	JCheckBox modelViewer = new JCheckBox();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JCheckBox createModelCreator = new JCheckBox();

	JCheckBox createModelViewer = new JCheckBox();

	JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);

	JTextField jTextField2 = new JTextField();

	JLabel jLabel4 = new JLabel();

	JCheckBox createAdminisrator = new JCheckBox();

	public WorkgroupPermissionsDialog(TeamMember teamMember,
			EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
		this.myWorkgroupID = this.myEnhancedWorkgroupPermissionsValue.getWorkgroupID();
		this.myAccessControl = this.myEnhancedWorkgroupPermissionsValue
				.getAccessControl();
		this.myAccessControlIntValue = this.myAccessControl.intValue();
		this.myUserId = this.myEnhancedWorkgroupPermissionsValue.getUserID();

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Workgroup's Permissions Handling");
		this.tabs.setBounds(new Rectangle(-4, 7, 435, 258));

		this.workgroupMyPermissions.setBounds(new Rectangle(-2, 0, 451, 360));
		this.workgroupMyPermissions.setLayout(null);
		this.workgroupGivePermissions.setBounds(new Rectangle(-2, 0, 451, 360));
		this.workgroupGivePermissions.setLayout(null);

		this.logoLabel.setBounds(new Rectangle(363, 26, 50, 50));

		this.updateButton.setText("Get Permission");
		this.updateButton
				.addActionListener(new WorkgroupAccessDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(26, 197, 153, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new WorkgroupAccessDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(335, 280, 96, 28));

		this.creator.setEnabled(false);
		this.creator.setText("Creator");
		this.creator.setBounds(new Rectangle(14, 65, 97, 27));
		this.administrator.setEnabled(false);
		this.administrator.setToolTipText("");
		this.administrator.setText("Administrator");
		this.administrator.setBounds(new Rectangle(168, 65, 119, 27));
		this.modelCreator.setEnabled(false);
		this.modelCreator.setText("OPM Model\'s Creator");
		this.modelCreator.setBounds(new Rectangle(168, 113, 158, 27));
		this.modelViewer.setEnabled(false);
		this.modelViewer.setText("OPM Model\'s Viewer");
		this.modelViewer.setBounds(new Rectangle(168, 161, 169, 27));

		if (this.myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) {
			this.creator.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
			this.administrator.setSelected(true);
		}
		// if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())
		// moderator.setSelected(true);
		if (this.myAccessControlIntValue >= PermissionFlags.EDITOR.intValue()) {
			this.modelCreator.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.VIEWER.intValue()) {
			this.modelViewer.setSelected(true);
		}

		this.jLabel3.setText("Member Login Name:");
		this.jLabel3.setBounds(new Rectangle(26, 30, 140, 18));
		this.jTextField1.setText("");
		this.jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

		this.createAdminisrator.setText("Administrator");
		this.createAdminisrator.setBounds(new Rectangle(165, 79, 125, 23));
		this.createAdminisrator.setEnabled(false);
		this.createModelCreator.setText("OPM Model\'s Creator");
		this.createModelCreator.setBounds(new Rectangle(165, 112, 169, 27));
		this.createModelCreator.setEnabled(false);
		this.createModelViewer.setText("OPM Model\'s Viewer");
		this.createModelViewer.setBounds(new Rectangle(165, 147, 157, 27));
		this.createModelViewer.setEnabled(false);

		this.logoLabel1.setBounds(new Rectangle(366, 27, 50, 50));

		Calendar calendar = this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getCreationTime();
		this.jTextField2.setText(calendar.getTime().toString());
		this.jTextField2.setBounds(new Rectangle(87, 26, 207, 24));
		this.jTextField2.setEditable(false);
		this.jLabel4.setText("Join Time:");
		this.jLabel4.setBounds(new Rectangle(14, 29, 86, 18));
		this.workgroupMyPermissions.add(this.logoLabel, null);
		this.workgroupMyPermissions.add(this.creator, null);
		this.workgroupMyPermissions.add(this.administrator, null);
		this.workgroupMyPermissions.add(this.jLabel4, null);
		this.workgroupMyPermissions.add(this.jTextField2, null);
		this.workgroupMyPermissions.add(this.modelCreator, null);
		this.workgroupMyPermissions.add(this.modelViewer, null);
		this.workgroupGivePermissions.add(this.jTextField1, null);
		this.workgroupGivePermissions.add(this.logoLabel1, null);
		this.workgroupGivePermissions.add(this.jLabel3, null);
		this.workgroupGivePermissions.add(this.updateButton, null);
		this.workgroupGivePermissions.add(this.createAdminisrator, null);
		this.workgroupGivePermissions.add(this.createModelCreator, null);
		this.workgroupGivePermissions.add(this.createModelViewer, null);

		this.getContentPane().add(this.cancelButton, null);

		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR.intValue()) {
			this.tabs.add(this.workgroupGivePermissions, " Update Member\'s Permission ");
		}

		this.tabs.add(this.workgroupMyPermissions, " My permissions ");

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
			WorkgroupPermissionsPK workgroupPermissionsPK = new WorkgroupPermissionsPK();
			workgroupPermissionsPK.setUserID(this.userValue.getUserID());
			workgroupPermissionsPK
					.setWorkgroupID(this.myEnhancedWorkgroupPermissionsValue
							.getWorkgroupID());
			WorkgroupPermissionsValue workgroupPermissionsValue = null;
			try {
				workgroupPermissionsValue = this.myTeamMember
						.fetchWorkgroupPermissionsForUser(workgroupPermissionsPK);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}

			int currentMemberAccessControl;
			if (workgroupPermissionsValue == null) {
				currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS
						.intValue();
			} else {
				currentMemberAccessControl = workgroupPermissionsValue
						.getAccessControl().intValue();
			}
			// ignore loggedIn bit.
			if (currentMemberAccessControl >= PermissionFlags.LOGGEDIN
					.intValue()) {
				currentMemberAccessControl -= PermissionFlags.LOGGEDIN
						.intValue();
			}
			// clean the area from the previos request
			// createModerator.setEnabled(false);
			this.createModelCreator.setEnabled(false);
			this.createModelViewer.setEnabled(false);
			this.createAdminisrator.setSelected(false);
			// createModerator.setSelected(false);
			this.createModelCreator.setSelected(false);
			this.createModelViewer.setSelected(false);

			if (currentMemberAccessControl >= PermissionFlags.ADMINISTRATOR
					.intValue()) {
				this.createAdminisrator.setSelected(true);
			}
			// if (currentMemberAccessControl >=
			// PermissionFlags.MODERATOR.intValue())
			// createModerator.setSelected(true);
			if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue()) {
				this.createModelCreator.setSelected(true);
			}
			if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue()) {
				this.createModelViewer.setSelected(true);
			}

			// if the member is administrator, we are not allowed to change its
			// perimssions
			if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR
					.intValue()) {
				// createModerator.setEnabled(true);
				this.createModelCreator.setEnabled(true);
				this.createModelViewer.setEnabled(true);
				this.jTextField1.setEnabled(false);
				this.updateButton.setText("Update");
			}
			return;
		} else { // "update" state

			Integer newAccessControlForMember;
			// first find out what the user choose to do.
			// if (createModerator.isSelected()) newAccessControlForMember =
			// PermissionFlags.MODERATOR; else
			if (this.createModelCreator.isSelected()) {
				newAccessControlForMember = PermissionFlags.EDITOR;
			} else if (this.createModelViewer.isSelected()) {
				newAccessControlForMember = PermissionFlags.VIEWER;
			} else {
				newAccessControlForMember = PermissionFlags.NULL_PERMISSIONS;
			}

			this.dispose();

			// build the editable class and send the reques to the server
			EditableWorkgroupPermissionsValue editableWorkgroupPermissionsValue = this.myTeamMember
					.createEditableWorkgroupPermissionsValue(this.userValue
							.getUserID(), this.myWorkgroupID,
							newAccessControlForMember);

			// transmit the request to the server
			try {
				this.myTeamMember
						.updateWorkgroupPermissions(editableWorkgroupPermissionsValue);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception) ; 
			}
		}
	}

} // end of class WorkgroupAccessDialog

class WorkgroupAccessDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	WorkgroupPermissionsDialog adaptee;

	WorkgroupAccessDialog_updateButton_actionAdapter(
			WorkgroupPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class WorkgroupAccessDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	WorkgroupPermissionsDialog adaptee;

	WorkgroupAccessDialog_cancelButton_actionAdapter(
			WorkgroupPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

