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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableUserValue;
import org.objectprocess.SoapClient.EnhancedUserValue;

public class UserValueDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	TeamMember myTeamMember;

	EnhancedUserValue myEnhancedUserValue;

	JPanel userData = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JLabel jLabel4 = new JLabel();

	JLabel jLabel5 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JTextField jTextField2 = new JTextField();

	JTextField jTextField3 = new JTextField();

	JTextField jTextField5 = new JTextField();

	JScrollPane jScrollPane1 = new JScrollPane();

	JButton updateButton = new JButton();

	JButton cancelButton = new JButton();

	JTextArea jTextArea1 = new JTextArea();

	JLabel jLabel6 = new JLabel();

	JLabel jLabel7 = new JLabel();

	JTextField jTextField4 = new JTextField();

	JPasswordField jPasswordField1 = new JPasswordField();

	public UserValueDialog(TeamMember teamMember) throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedUserValue = this.myTeamMember.getLocalEnhancedUser();
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("User Details");
		this.userData.setBounds(new Rectangle(0, 0, 377, 410));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("First Name:");
		this.jLabel1.setBounds(new Rectangle(92, 107, 97, 21));
		this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel2.setText("Last Name:");
		this.jLabel2.setBounds(new Rectangle(92, 151, 97, 21));
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Email Address:");
		this.jLabel3.setBounds(new Rectangle(92, 196, 121, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Description:");
		this.jLabel4.setBounds(new Rectangle(92, 282, 97, 21));
		this.jLabel5.setEnabled(true);
		this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel5.setText("Last Login Time:");
		this.jLabel5.setBounds(new Rectangle(92, 236, 122, 21));
		this.jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel6.setText("Password:");
		this.jLabel6.setBounds(new Rectangle(92, 62, 85, 18));
		this.jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel7.setText("Login Name:");
		this.jLabel7.setBounds(new Rectangle(92, 18, 92, 18));

		this.userData.setLayout(null);

		this.logoLabel.setBounds(new Rectangle(20, 18, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1.setText(this.myEnhancedUserValue.getFirstName());
		this.jTextField1.setBounds(new Rectangle(204, 107, 149, 29));
		this.jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField2.setText(this.myEnhancedUserValue.getLastName());
		this.jTextField2.setBounds(new Rectangle(204, 151, 149, 29));
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField3.setText(this.myEnhancedUserValue.getEmail());
		this.jTextField3.setBounds(new Rectangle(204, 196, 149, 29));
		this.jTextField5.setEnabled(true);
		this.jTextField5.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField5.setEditable(false);
		Calendar calendar = this.myEnhancedUserValue.getLastLoginTime();
		this.jTextField5.setText(calendar.getTime().toString());
		this.jTextField5.setBounds(new Rectangle(204, 236, 149, 29));
		this.jTextField4.setText(this.myEnhancedUserValue.getLoginName());
		this.jTextField4.setBounds(new Rectangle(204, 18, 149, 29));
		this.jPasswordField1.setBounds(new Rectangle(204, 62, 149, 29));
		this.jPasswordField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jPasswordField1.setText(this.myEnhancedUserValue.getPassword());

		this.updateButton.setText("Update");
		this.updateButton
				.addActionListener(new UserValueDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(20, 362, 96, 28));
		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new UserValueDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(257, 362, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(203, 282, 149, 59));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jTextArea1.setLineWrap(true);
		this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextArea1.setText(this.myEnhancedUserValue.getDescription());
		this.userData.add(this.logoLabel, null);
		this.userData.add(this.updateButton, null);
		this.userData.add(this.jLabel2, null);
		this.userData.add(this.jLabel7, null);
		this.userData.add(this.jLabel6, null);
		this.userData.add(this.jLabel1, null);
		this.userData.add(this.jLabel3, null);
		this.userData.add(this.jLabel4, null);
		this.userData.add(this.jTextField3, null);
		this.userData.add(this.jTextField5, null);
		this.userData.add(this.jTextField4, null);
		this.userData.add(this.jPasswordField1, null);
		this.userData.add(this.jTextField1, null);
		this.userData.add(this.jTextField2, null);
		this.userData.add(this.jLabel5, null);
		this.userData.add(this.jScrollPane1, null);
		this.userData.add(this.cancelButton, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.userData, null);

		this.setBounds(0, 0, 377, 440);

		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void updateButton_actionPerformed(ActionEvent e) {

		if ((this.jTextField1.getText().equals(""))
				|| (this.jTextField2.getText().equals(""))
				|| (this.jTextField3.getText().equals(""))
				|| (this.jTextField4.getText().equals(""))
				|| (this.jPasswordField1.getPassword().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Data is missing in one of the fields", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		EditableUserValue editableUserValue = new EditableUserValue();
		editableUserValue.setDescription(this.jTextArea1.getText());
		editableUserValue.setEmail(this.jTextField3.getText());
		editableUserValue.setFirstName(this.jTextField1.getText());
		editableUserValue.setLastName(this.jTextField2.getText());
		editableUserValue.setLoginName(this.jTextField4.getText());
		editableUserValue.setPassword(new String(this.jPasswordField1
				.getPassword()));
		editableUserValue.setUserID(this.myTeamMember.getLocalEnhancedUser()
				.getUserID());
		editableUserValue.setAdministrator(this.myTeamMember
				.getLocalEnhancedUser().getAdministrator());
		editableUserValue.setPrimaryKey(this.myTeamMember
				.getLocalEnhancedUser().getUserID());
		try {
			this.myTeamMember.updateUser(editableUserValue);
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}

		this.dispose();
	}

} // end of class UserDataDialog

class UserValueDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	UserValueDialog adaptee;

	UserValueDialog_updateButton_actionAdapter(UserValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class UserValueDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	UserValueDialog adaptee;

	UserValueDialog_cancelButton_actionAdapter(UserValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

