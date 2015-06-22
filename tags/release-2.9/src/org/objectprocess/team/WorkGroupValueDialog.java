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
import org.objectprocess.SoapClient.EditableWorkgroupValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

public class WorkGroupValueDialog extends JDialog {
	  

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	TeamMember myTeamMember;

	EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

	JPanel WorkGroup = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JLabel jLabel4 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JButton updateButton = new JButton();

	JButton cancelButton = new JButton();

	JTextField jTextField3 = new JTextField();

	JScrollPane jScrollPane1 = new JScrollPane();

	JTextArea jTextArea1 = new JTextArea();

	JLabel updateMessage = new JLabel();

	public WorkGroupValueDialog(TeamMember teamMember,
			EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Workgroup Details");
		this.WorkGroup.setBounds(new Rectangle(0, 0, 381, 252));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("Workgroup Name:");
		this.jLabel1.setBounds(new Rectangle(84, 32, 118, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Description:");
		this.jLabel4.setBounds(new Rectangle(84, 116, 97, 21));
		this.jLabel3.setEnabled(true);
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Creation Date:");
		this.jLabel3.setBounds(new Rectangle(84, 75, 97, 21));

		this.WorkGroup.setLayout(null);

		this.logoLabel.setBounds(new Rectangle(17, 32, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1.setText(this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getWorkgroupName());
		this.jTextField1.setBounds(new Rectangle(211, 32, 149, 29));
		this.jTextField3.setBounds(new Rectangle(211, 75, 149, 29));
		Calendar calendar = this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getCreationTime();
		this.jTextField3.setText(calendar.getTime().toString());
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField3.setEditable(false);

		this.updateButton.setText("Update");
		this.updateButton
				.addActionListener(new WorkGroupValueDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(17, 203, 96, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new WorkGroupValueDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(264, 203, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(211, 116, 153, 63));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextArea1.setText(this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getDescription());
		this.updateMessage
				.setText("Only the administrator can perform update action!");
		this.updateMessage.setBounds(new Rectangle(17, 202, 367, 15));
		this.WorkGroup.add(this.logoLabel, null);
		this.WorkGroup.add(this.jLabel4, null);
		this.WorkGroup.add(this.jLabel1, null);
		this.WorkGroup.add(this.jLabel3, null);
		this.WorkGroup.add(this.jTextField3, null);
		this.WorkGroup.add(this.jTextField1, null);
		this.WorkGroup.add(this.jScrollPane1, null);
		this.WorkGroup.add(this.cancelButton, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.WorkGroup, null);

		// diable this button if the user is not creator or admin .
		Integer accessControl = this.myEnhancedWorkgroupPermissionsValue
				.getAccessControl();
		if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
			this.jTextArea1.setEditable(false);
			this.jTextField1.setEditable(false);
			this.WorkGroup.add(this.updateMessage, null);
		} else {
			this.WorkGroup.add(this.updateButton, null);
		}

		this.setBounds(0, 0, 381, 282);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void updateButton_actionPerformed(ActionEvent e) {

		if ((this.jTextField1.getText().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Data is missing in one of the fields", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.dispose();

		EditableWorkgroupValue editableWorkgroupValue = new EditableWorkgroupValue();
		editableWorkgroupValue.setDescription(this.jTextArea1.getText());
		editableWorkgroupValue.setWorkgroupName(this.jTextField1.getText());
		editableWorkgroupValue
				.setWorkgroupID(this.myEnhancedWorkgroupPermissionsValue
						.getWorkgroupID());
		editableWorkgroupValue
				.setPrimaryKey(this.myEnhancedWorkgroupPermissionsValue
						.getWorkgroupID());
		try {
			this.myTeamMember.updateWorkgroup(editableWorkgroupValue);
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

} // end of class WorkGroupDialog

class WorkGroupValueDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	WorkGroupValueDialog adaptee;

	WorkGroupValueDialog_updateButton_actionAdapter(WorkGroupValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class WorkGroupValueDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	WorkGroupValueDialog adaptee;

	WorkGroupValueDialog_cancelButton_actionAdapter(WorkGroupValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

