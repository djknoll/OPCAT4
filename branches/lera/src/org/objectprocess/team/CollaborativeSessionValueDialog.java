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
import javax.swing.ScrollPaneConstants;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.UserValue;

public class CollaborativeSessionValueDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	TeamMember myTeamMember;

	EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

	JPanel collaborativeSession = new JPanel();

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

	JLabel jLabel6 = new JLabel();

	JLabel jLabel7 = new JLabel();

	JLabel jLabel8 = new JLabel();

	JTextField jTextField5 = new JTextField();

	JTextField jTextField6 = new JTextField();

	JTextField jTextField7 = new JTextField();

	JTextArea jTextArea1 = new JTextArea();

	JLabel updateMessage = new JLabel();

	public CollaborativeSessionValueDialog(
			TeamMember teamMember,
			EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		// get the user name of the Token Holder from the server
		UserValue tokenHolderValue = null;
		tokenHolderValue = this.myTeamMember
				.getUserByPK(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getTokenHolderID()
						.intValue());

		String tokenHolderName;
		if (tokenHolderValue == null) {
			tokenHolderName = new String("");
		} else {
			tokenHolderName = new String(tokenHolderValue.getFirstName() + " "
					+ tokenHolderValue.getLastName());
		}

		this.getContentPane().setLayout(null);
		this.setTitle("Collaborative Session Details");
		this.collaborativeSession.setLayout(null);
		this.collaborativeSession.setBounds(new Rectangle(0, 0, 381, 422));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("Session Name:");
		this.jLabel1.setBounds(new Rectangle(101, 23, 107, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Description:");
		this.jLabel4.setBounds(new Rectangle(101, 284, 97, 21));
		this.jLabel3.setEnabled(true);
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Creation Date:");
		this.jLabel3.setBounds(new Rectangle(101, 107, 97, 21));
		this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel5.setText("VC Revision:");
		this.jLabel5.setBounds(new Rectangle(101, 64, 108, 23));
		this.jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel6.setText("Last Update:");
		this.jLabel6.setBounds(new Rectangle(101, 150, 97, 21));
		this.jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel7.setText("Token Holder:");
		this.jLabel7.setBounds(new Rectangle(101, 193, 97, 21));
		this.jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel8.setText("Token Timeout:");
		this.jLabel8.setBounds(new Rectangle(101, 237, 124, 21));

		this.logoLabel.setBounds(new Rectangle(22, 23, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1
				.setText(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession()
						.getCollaborativeSessionName());
		this.jTextField1.setBounds(new Rectangle(208, 23, 149, 29));
		this.jTextField3.setBounds(new Rectangle(208, 107, 149, 29));
		Calendar calendar = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSession().getCreationTime();
		this.jTextField3.setText(calendar.getTime().toString());
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField3.setEditable(false);
		this.jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField4.setEditable(false);
		this.jTextField4.setBounds(new Rectangle(208, 64, 149, 29));
		this.jTextField5.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField5.setEditable(false);

		calendar = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSession().getLastUpdate();
		this.jTextField4
				.setText(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getMajorRevision()
						+ "."
						+ this.myEnhancedCollaborativeSessionPermissionsValue
								.getCollaborativeSession().getMinorRevision());
		this.jTextField5.setText(calendar.getTime().toString());
		this.jTextField5.setBounds(new Rectangle(208, 150, 149, 29));
		this.jTextField6.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField6.setEditable(false);
		this.jTextField6.setText(tokenHolderName);
		this.jTextField6.setBounds(new Rectangle(208, 193, 149, 29));
		this.jTextField7.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField7
				.setText(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getTokenTimeout().toString());
		this.jTextField7.setBounds(new Rectangle(209, 237, 149, 29));
		this.jTextArea1
				.setText(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getDescription());

		this.updateButton.setText("Update");
		this.updateButton
				.addActionListener(new CollaborativeSessionValueDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(22, 370, 96, 28));
		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new CollaborativeSessionValueDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(263, 370, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(209, 284, 149, 59));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.updateMessage
				.setText("Only the administrator can perform update action.");
		this.updateMessage.setBounds(new Rectangle(22, 355, 318, 15));
		this.collaborativeSession.add(this.logoLabel, null);
		this.collaborativeSession.add(this.jLabel4, null);
		this.collaborativeSession.add(this.jLabel1, null);
		this.collaborativeSession.add(this.jLabel5, null);
		this.collaborativeSession.add(this.jLabel3, null);
		this.collaborativeSession.add(this.jLabel6, null);
		this.collaborativeSession.add(this.jLabel7, null);
		this.collaborativeSession.add(this.jLabel8, null);
		this.collaborativeSession.add(this.jTextField1, null);
		this.collaborativeSession.add(this.jTextField4, null);
		this.collaborativeSession.add(this.jTextField3, null);
		this.collaborativeSession.add(this.jTextField5, null);
		this.collaborativeSession.add(this.jTextField6, null);
		this.collaborativeSession.add(this.jScrollPane1, null);
		this.collaborativeSession.add(this.jTextField7, null);
		this.collaborativeSession.add(this.cancelButton, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.collaborativeSession, null);

		// diable this button if the user is not creator or admin .
		Integer accessControl = this.myEnhancedCollaborativeSessionPermissionsValue
				.getAccessControl();
		if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
			this.jTextField1.setEditable(false);
			this.jTextField7.setEditable(false);
			this.jTextArea1.setEditable(false);
			this.collaborativeSession.add(this.updateMessage, null);

		} else {
			this.collaborativeSession.add(this.updateButton, null);
		}

		this.setBounds(0, 0, 381, 452);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void updateButton_actionPerformed(ActionEvent e) {
		// first check that all feilds are filled with information.
		if ((this.jTextField1.getText().equals(""))
				|| (this.jTextField7.getText().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Data is missing in one of the fields", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.dispose();

		EditableCollaborativeSessionValue editableCollaborativeSessionValue = new EditableCollaborativeSessionValue();
		editableCollaborativeSessionValue
				.setCollaborativeSessionName(this.jTextField1.getText());
		editableCollaborativeSessionValue.setDescription(this.jTextArea1
				.getText());
		editableCollaborativeSessionValue.setTokenTimeout(new Integer(
				this.jTextField7.getText()));
		editableCollaborativeSessionValue.setUserTimeout(new Integer(0));
		editableCollaborativeSessionValue
				.setCollaborativeSessionID(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSessionID());
		editableCollaborativeSessionValue
				.setPrimaryKey(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSessionID());
		editableCollaborativeSessionValue
				.setOpmModelID(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getOpmModelID());
		editableCollaborativeSessionValue
				.setMajorRevision(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getMajorRevision());
		editableCollaborativeSessionValue
				.setMinorRevision(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getMinorRevision());
		editableCollaborativeSessionValue
				.setRevisionID(this.myEnhancedCollaborativeSessionPermissionsValue
						.getCollaborativeSession().getRevisionID());

		try {
			this.myTeamMember
					.updateCollaborativeSession(editableCollaborativeSessionValue);
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

} // end of class CollaborativeSessionDialog

class CollaborativeSessionValueDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	CollaborativeSessionValueDialog adaptee;

	CollaborativeSessionValueDialog_updateButton_actionAdapter(
			CollaborativeSessionValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class CollaborativeSessionValueDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	CollaborativeSessionValueDialog adaptee;

	CollaborativeSessionValueDialog_cancelButton_actionAdapter(
			CollaborativeSessionValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

