package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.axis.AxisFault;
import org.objectprocess.Client.TeamMember;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2001
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class Connect extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	TeamMember myTeamMember;

	private final static String fileSeparator = System
			.getProperty("file.separator");

	JPanel jPanel = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JLabel jLabel4 = new JLabel();

	JTextField loginName = new JTextField();

	JPasswordField password = new JPasswordField();

	JTextField serverAddr = new JTextField();

	JTextField serverPort = new JTextField();

	JButton ConnectButton = new JButton();

	JButton CancelButton = new JButton();

	JCheckBox saveProperties = new JCheckBox();

	public Connect(TeamMember teamMember) throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;

		// read preference file - temporary file
		try {
			this.ReadPreferenceFile();
		} catch (Exception e) {
		}

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Connect");
		this.jPanel.setBounds(new Rectangle(0, 0, 355, 296));

		this.jPanel.setLayout(null);

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("Login Name:");
		this.jLabel1.setBounds(new Rectangle(98, 26, 98, 31));
		this.jLabel1.setToolTipText("Insert your login name");
		this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel2.setText("Password:");
		this.jLabel2.setBounds(new Rectangle(98, 69, 98, 31));
		this.jLabel2.setToolTipText("Insert your password");
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Server Address:");
		this.jLabel3.setBounds(new Rectangle(98, 116, 109, 31));
		this.jLabel3
				.setToolTipText("Insert a full description of the server's URL");
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Server Port:");
		this.jLabel4.setBounds(new Rectangle(98, 172, 110, 15));

		this.loginName.setBounds(new Rectangle(219, 28, 111, 26));
		this.loginName.setFont(new java.awt.Font("SansSerif", 0, 12));

		this.password.setBounds(new Rectangle(219, 71, 111, 26));
		this.password.setFont(new java.awt.Font("SansSerif", 0, 12));

		this.serverAddr.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.serverAddr.setBounds(new Rectangle(219, 118, 111, 26));

		this.serverPort.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.serverPort.setBounds(new Rectangle(219, 171, 111, 26));

		this.ConnectButton.setBounds(new Rectangle(18, 249, 88, 25));
		this.ConnectButton.setText("Connect");
		this.ConnectButton
				.addActionListener(new Connect_ConnectButton_actionAdapter(this));
		this.CancelButton.setBounds(new Rectangle(242, 249, 88, 25));
		this.CancelButton.setText("Cancel");
		this.CancelButton
				.addActionListener(new Connect_CancelButton_actionAdapter(this));

		this.logoLabel.setBounds(new Rectangle(20, 28, 50, 50));

		this.saveProperties.setEnabled(true);
		this.saveProperties.setFont(new java.awt.Font("Dialog", 0, 12));
		this.saveProperties.setSelected(false);
		this.saveProperties.setText("Save Properties");
		this.saveProperties.setBounds(new Rectangle(100, 208, 116, 23));
		this.saveProperties
				.addActionListener(new Connect_saveProperties_actionAdapter(
						this));
		this.saveProperties
				.addActionListener(new Connect_saveProperties_actionAdapter(
						this));
		this.jPanel.add(this.logoLabel, null);
		this.jPanel.add(this.jLabel1, null);
		this.jPanel.add(this.loginName, null);
		this.jPanel.add(this.jLabel2, null);
		this.jPanel.add(this.password, null);
		this.jPanel.add(this.jLabel3, null);
		this.jPanel.add(this.serverAddr, null);
		this.jPanel.add(this.jLabel4, null);
		this.jPanel.add(this.saveProperties, null);
		this.jPanel.add(this.CancelButton, null);
		this.jPanel.add(this.ConnectButton, null);
		this.jPanel.add(this.serverPort, null);
		this.getContentPane().add(this.jPanel, null);

		// catch the "enter" and perform connect to server.
		this.loginName.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.loginName.getActionMap().put("pressed", this.enterKeyPressed);
		this.password.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.password.getActionMap().put("pressed", this.enterKeyPressed);
		this.serverAddr.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.serverAddr.getActionMap().put("pressed", this.enterKeyPressed);
		this.serverPort.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.serverPort.getActionMap().put("pressed", this.enterKeyPressed);

		this.jPanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),
				"pressed");
		this.jPanel.getActionMap().put("pressed", this.enterKeyPressed);

		this.setBounds(0, 0, 355, 326);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
		// password.setFocusable(true);
		// password.requestFocus();
	}// end of JBinit

	void ConnectButton_actionPerformed() {

		// check that loginName and password were given by the user.
		if ((this.loginName.getText().equals(""))
				|| (this.password.getPassword().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Login Name or Password is missing", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));

		// first, initialize all web service, if it is the first time.
		if (!this.myTeamMember.isConnected()) {
			try {
				this.myTeamMember.setAllServices(this.serverAddr.getText(),
						this.serverPort.getText());
			} catch (Exception exception) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(
						exception);
				exceptionHandler.logError(exception);
			}
		}

		// try to login into server, and if succesful, get all user data.
		// first- try to connect to server, using loginname and apss- if OK
		// return wuth userID.
		try {
			this.myTeamMember.loginUser(this.loginName.getText(), new String(
					this.password.getPassword()));
		} catch (AxisFault axisException) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.dispose();
			ExceptionHandler exceptionHandler = new ExceptionHandler(
					axisException);
			exceptionHandler.logError(axisException);
			return;
		} catch (Exception eLogin) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.dispose();
			System.err.println("Exception caught: " + eLogin);
			return;
		}

		// get from the server all the user data.
		try {
			this.myTeamMember.initializeEnhancedUserValue();
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			this.dispose();
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"User successfully logged to server", "Message",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} catch (AxisFault axisException) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(
					axisException);
			exceptionHandler.logError(axisException);
		} catch (Exception eInitUser) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"User Interface Initialization failed.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		try {
			this.myTeamMember.logoutUser();
		} catch (Exception eLogout) {
		}
		;
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		this.dispose();
	}

	void CancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void saveProperties_actionPerformed(ActionEvent e) {
		if (this.saveProperties.isSelected()) {
			Thread runner = new Thread() {
				public void run() {

					String fileName = "OPCATeam" + fileSeparator
							+ "OPCATeam.txt";
					File outputFile = new File(fileName);
					try {
						FileWriter out = new FileWriter(outputFile);
						out
								.write(Connect.this.loginName.getText() + '\r' + '\n');
						out
								.write(Connect.this.serverAddr.getText() + '\r' + '\n');
						out
								.write(Connect.this.serverPort.getText() + '\r' + '\n');
						out.close();
					} catch (Exception eFile) {
						System.out.print(eFile.toString());
					}
				}
			};
			runner.start();
		}
	}

	public void ReadPreferenceFile() throws Exception {

		String fileName = "OPCATeam" + fileSeparator + "OPCATeam.txt";
		BufferedReader in = new BufferedReader(new FileReader(fileName));

		String line = null;
		this.loginName.setText("");
		this.serverAddr.setText("");
		this.serverPort.setText("");

		try {
			line = in.readLine();
			this.loginName.setText(line);
			line = in.readLine();
			this.serverAddr.setText(line);
			line = in.readLine();
			this.serverPort.setText(line);
		} catch (Exception e) {
		}
	}

	Action enterKeyPressed = new AbstractAction() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			Connect.this.ConnectButton_actionPerformed();
		}
	};

} // end of class Connect

class Connect_ConnectButton_actionAdapter implements
		java.awt.event.ActionListener {
	Connect adaptee;

	Connect_ConnectButton_actionAdapter(Connect adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.ConnectButton_actionPerformed();
	}
}

class Connect_CancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	Connect adaptee;

	Connect_CancelButton_actionAdapter(Connect adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.CancelButton_actionPerformed(e);
	}
}

class Connect_saveProperties_actionAdapter implements
		java.awt.event.ActionListener {
	Connect adaptee;

	Connect_saveProperties_actionAdapter(Connect adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.saveProperties_actionPerformed(e);
	}
}
