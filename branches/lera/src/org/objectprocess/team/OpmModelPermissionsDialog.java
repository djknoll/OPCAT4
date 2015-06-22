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
import org.objectprocess.SoapClient.EditableOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.OpmModelPermissionsPK;
import org.objectprocess.SoapClient.OpmModelPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class OpmModelPermissionsDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	TeamMember myTeamMember;

	EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

	Integer myOpmModelID;

	Integer myAccessControl;

	int myAccessControlIntValue;

	Integer myUserId;

	UserValue userValue = null;

	JTabbedPane tabs = new JTabbedPane();

	JPanel opmModelMyPermissions = new JPanel();

	JPanel opmModelGivePermissions = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JButton cancelButton = new JButton();

	JCheckBox creator = new JCheckBox();

	JCheckBox administrator = new JCheckBox();

	JCheckBox sessionCreator = new JCheckBox();

	JCheckBox sessionViewer = new JCheckBox();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JCheckBox createSessionCreator = new JCheckBox();

	JCheckBox createSessionViewer = new JCheckBox();

	JLabel logoLabel1 = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel4 = new JLabel();

	JTextField jTextField2 = new JTextField();

	JButton updateButton = new JButton();

	JCheckBox createAdministrator = new JCheckBox();

	public OpmModelPermissionsDialog(TeamMember teamMember,
			EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;
		this.myOpmModelID = this.myEnhancedOpmModelPermissionsValue
				.getOpmModelID();
		this.myAccessControl = this.myEnhancedOpmModelPermissionsValue
				.getAccessControl();
		this.myAccessControlIntValue = this.myAccessControl.intValue();
		this.myUserId = this.myEnhancedOpmModelPermissionsValue.getUserID();
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("OPM Model's Permissions Handling");
		this.opmModelMyPermissions.setLayout(null);
		this.opmModelGivePermissions.setBounds(new Rectangle(-2, 0, 451, 360));
		this.opmModelGivePermissions.setLayout(null);
		this.tabs.setBounds(new Rectangle(-4, 7, 435, 268));

		this.logoLabel.setBounds(new Rectangle(362, 27, 50, 50));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new OpmModelAccessDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(333, 281, 96, 28));

		this.creator.setEnabled(false);
		this.creator.setRequestFocusEnabled(true);
		this.creator.setText("Creator");
		this.creator.setBounds(new Rectangle(16, 72, 97, 27));
		this.administrator.setEnabled(false);
		this.administrator.setToolTipText("");
		this.administrator.setText("Administrator");
		this.administrator.setBounds(new Rectangle(192, 72, 119, 27));
		this.sessionCreator.setEnabled(false);
		this.sessionCreator.setDoubleBuffered(false);
		this.sessionCreator.setText("Sessions\'s Creator");
		this.sessionCreator.setBounds(new Rectangle(192, 113, 158, 27));
		this.sessionViewer.setEnabled(false);
		this.sessionViewer.setText("Session\'s Viewer");
		this.sessionViewer.setBounds(new Rectangle(192, 160, 169, 27));

		if (this.myAccessControlIntValue >= PermissionFlags.CREATOR.intValue()) {
			this.creator.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR
				.intValue()) {
			this.administrator.setSelected(true);
		}
		// if (myAccessControlIntValue >= PermissionFlags.MODERATOR.intValue())
		// moderator.setSelected(true);
		if (this.myAccessControlIntValue >= PermissionFlags.EDITOR.intValue()) {
			this.sessionCreator.setSelected(true);
		}
		if (this.myAccessControlIntValue >= PermissionFlags.VIEWER.intValue()) {
			this.sessionViewer.setSelected(true);
		}

		this.jLabel3.setText("Member Login Name:");
		this.jLabel3.setBounds(new Rectangle(22, 30, 142, 18));
		this.jTextField1.setText("");
		this.jTextField1.setBounds(new Rectangle(165, 27, 138, 24));

		this.createAdministrator.setEnabled(false);
		this.createAdministrator.setText("Administrator");
		this.createAdministrator.setBounds(new Rectangle(165, 70, 118, 23));
		this.createSessionCreator.setText("Session\'s Creator");
		this.createSessionCreator.setBounds(new Rectangle(165, 110, 169, 27));
		this.createSessionCreator.setEnabled(false);
		this.createSessionViewer.setText("Session\'s Viewer");
		this.createSessionViewer.setBounds(new Rectangle(165, 148, 157, 27));
		this.createSessionViewer.setEnabled(false);

		this.logoLabel1.setBounds(new Rectangle(367, 25, 50, 50));

		this.jLabel4.setText("Join Time:");
		this.jLabel4.setBounds(new Rectangle(16, 30, 82, 18));
		Calendar calendar = this.myEnhancedOpmModelPermissionsValue
				.getOpmModel().getCreationTime();
		this.jTextField2.setText(calendar.getTime().toString());
		this.jTextField2.setBounds(new Rectangle(85, 27, 212, 24));
		this.jTextField2.setEditable(false);
		this.updateButton.setText("Get Permission");
		this.updateButton
				.addActionListener(new OpmModelAccessDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(22, 207, 160, 28));
		this.opmModelMyPermissions.add(this.administrator, null);
		this.opmModelMyPermissions.add(this.jLabel4, null);
		this.opmModelMyPermissions.add(this.jTextField2, null);
		this.opmModelMyPermissions.add(this.creator, null);
		this.opmModelMyPermissions.add(this.logoLabel, null);
		this.opmModelMyPermissions.add(this.sessionViewer, null);
		this.opmModelMyPermissions.add(this.sessionCreator, null);
		this.opmModelGivePermissions.add(this.jLabel3, null);
		this.opmModelGivePermissions.add(this.jTextField1, null);
		this.opmModelGivePermissions.add(this.logoLabel1, null);
		this.opmModelGivePermissions.add(this.updateButton, null);
		this.opmModelGivePermissions.add(this.createAdministrator, null);
		this.opmModelGivePermissions.add(this.createSessionCreator, null);
		this.opmModelGivePermissions.add(this.createSessionViewer, null);

		this.getContentPane().add(this.cancelButton, null);

		this.tabs.add(this.opmModelMyPermissions, "My Permissions");

		if (this.myAccessControlIntValue >= PermissionFlags.ADMINISTRATOR
				.intValue()) {
			this.tabs.add(this.opmModelGivePermissions,
					" Update Member\'s Permission ");
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
				this.userValue = this.myTeamMember
						.getUserByLoginName(this.jTextField1.getText());
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
						+ this.jTextField1.getText() + " does not exist",
						"Error", JOptionPane.ERROR_MESSAGE);
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
			OpmModelPermissionsPK opmModelPermissionsPK = new OpmModelPermissionsPK();
			opmModelPermissionsPK.setUserID(this.userValue.getUserID());
			opmModelPermissionsPK
					.setOpmModelID(this.myEnhancedOpmModelPermissionsValue
							.getOpmModelID());
			OpmModelPermissionsValue opmModelPermissionsValue = null;
			try {
				opmModelPermissionsValue = this.myTeamMember
						.fetchOpmModelPermissionsForUser(opmModelPermissionsPK);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}
			int currentMemberAccessControl;
			// if the user is "new" then opmModelPermissionsValue will be null.
			if (opmModelPermissionsValue == null) {
				currentMemberAccessControl = PermissionFlags.NULL_PERMISSIONS
						.intValue();
			} else {
				currentMemberAccessControl = opmModelPermissionsValue
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
			this.createSessionCreator.setEnabled(true);
			this.createSessionViewer.setEnabled(true);
			this.createAdministrator.setSelected(false);
			// createModerator.setSelected(false);
			this.createSessionCreator.setSelected(false);
			this.createSessionViewer.setSelected(false);

			if (currentMemberAccessControl >= PermissionFlags.ADMINISTRATOR
					.intValue()) {
				this.createAdministrator.setSelected(true);
			}
			// if (currentMemberAccessControl >=
			// PermissionFlags.MODERATOR.intValue())
			// createModerator.setSelected(true);
			if (currentMemberAccessControl >= PermissionFlags.EDITOR.intValue()) {
				this.createSessionCreator.setSelected(true);
			}
			if (currentMemberAccessControl >= PermissionFlags.VIEWER.intValue()) {
				this.createSessionViewer.setSelected(true);
			}

			// if the member is administrator, we are not allowed to change its
			// perimssions
			if (currentMemberAccessControl < PermissionFlags.ADMINISTRATOR
					.intValue()) {
				// createModerator.setEnabled(true);
				this.createSessionCreator.setEnabled(true);
				this.createSessionViewer.setEnabled(true);
				this.jTextField1.setEnabled(false);
				this.updateButton.setText("Update");
			}
			return;
		}

		else { // "update" state

			// first find out what the user choose to do.
			// if (createAdministrator.isSelected()) myAccessControl = new
			// Integer(1364);
			// if (createModerator.isSelected()) myAccessControl =
			// PermissionFlags.MODERATOR;
			if (this.createSessionCreator.isSelected()) {
				this.myAccessControl = PermissionFlags.EDITOR;
			} else if (this.createSessionViewer.isSelected()) {
				this.myAccessControl = PermissionFlags.VIEWER;
			} else {
				this.myAccessControl = PermissionFlags.NULL_PERMISSIONS;
			}

			this.dispose();

			// build the editable class and send the reques to the server
			EditableOpmModelPermissionsValue editableOpmModelPermissionsValue = this.myTeamMember
					.createEditableOpmModelPermissionsValue(this.userValue
							.getUserID(), this.myOpmModelID,
							this.myAccessControl);

			// transmit request ot server
			try {
				this.myTeamMember
						.updateOpmModelPermissions(editableOpmModelPermissionsValue);
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}
		}
	}

} // end of class OpmModelAccessDialog //end of class

class OpmModelAccessDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	OpmModelPermissionsDialog adaptee;

	OpmModelAccessDialog_cancelButton_actionAdapter(
			OpmModelPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

class OpmModelAccessDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	OpmModelPermissionsDialog adaptee;

	OpmModelAccessDialog_updateButton_actionAdapter(
			OpmModelPermissionsDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
}
