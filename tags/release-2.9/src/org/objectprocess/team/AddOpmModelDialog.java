package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EditableOpmModelValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;

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

public class AddOpmModelDialog extends JDialog {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	protected TeamMember myTeamMember;

	protected EditableOpmModelValue myEditableOpmModelValue;

	protected EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

	protected Opcat2 myOpcat2;

	protected Date creationDate = (new GregorianCalendar()).getTime();

	JPanel OpmModel = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JLabel jLabel2 = new JLabel();

	JLabel jLabel4 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JLabel jLabel5 = new JLabel();

	JLabel jLabel6 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JTextField jTextField2 = new JTextField();

	JTextField jTextField3 = new JTextField();

	JTextField jTextField4 = new JTextField();

	JButton createButton = new JButton();

	JButton cancelButton = new JButton();

	JScrollPane jScrollPane1 = new JScrollPane();

	JTextField jTextField5 = new JTextField();

	JTextArea jTextArea1 = new JTextArea();

	public AddOpmModelDialog(
			TeamMember teamMember,
			EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue,
			Opcat2 opcat2) throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
		this.myOpcat2 = opcat2;
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Create New OPM Model");
		this.OpmModel.setLayout(null);
		this.OpmModel.setBounds(new Rectangle(0, 0, 388, 330));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("OPM Model Name:");
		this.jLabel1.setBounds(new Rectangle(84, 32, 126, 21));
		this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel2.setText("Creator:");
		this.jLabel2.setBounds(new Rectangle(84, 141, 107, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Creation Date:");
		this.jLabel4.setBounds(new Rectangle(84, 176, 107, 21));
		this.jLabel3.setEnabled(true);
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Description:");
		this.jLabel3.setBounds(new Rectangle(84, 212, 107, 21));
		this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel5.setText("Workgroup Name:");
		this.jLabel5.setBounds(new Rectangle(84, 70, 130, 21));
		this.jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel6.setText("VC Revision:");
		this.jLabel6.setBounds(new Rectangle(84, 105, 107, 21));

		this.logoLabel.setBounds(new Rectangle(15, 32, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1.setText("");
		this.jTextField1.setBounds(new Rectangle(216, 32, 149, 25));
		this.jTextField2.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField2.setText(this.myTeamMember.getLocalEnhancedUser().getFirstName()
				+ " " + this.myTeamMember.getLocalEnhancedUser().getLastName());
		this.jTextField2.setBounds(new Rectangle(216, 141, 149, 25));
		this.jTextField2.setEditable(false);
		this.jTextField3.setBounds(new Rectangle(216, 176, 149, 25));
		this.jTextField3.setText(this.creationDate.toString());
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField3.setEditable(false);
		this.jTextField4.setText(this.myEnhancedWorkgroupPermissionsValue.getWorkgroup()
				.getWorkgroupName());
		this.jTextField4.setBounds(new Rectangle(216, 70, 149, 25));
		this.jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField4.setEditable(false);
		this.jTextField5.setBounds(new Rectangle(216, 105, 149, 25));
		this.jTextField5.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField5.setEditable(false);
		this.jTextField5.setText("0.0");

		this.createButton.setText("Create");
		this.createButton
				.addActionListener(new AddOpmModelDialog_createButton_actionAdapter(
						this));
		this.createButton.setBounds(new Rectangle(15, 284, 96, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new AddOpmModelDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(269, 284, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(216, 212, 152, 45));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextArea1.setText("");
		this.OpmModel.add(this.createButton, null);
		this.OpmModel.add(this.cancelButton, null);
		this.OpmModel.add(this.logoLabel, null);
		this.OpmModel.add(this.jLabel3, null);
		this.OpmModel.add(this.jLabel1, null);
		this.OpmModel.add(this.jLabel5, null);
		this.OpmModel.add(this.jLabel6, null);
		this.OpmModel.add(this.jLabel2, null);
		this.OpmModel.add(this.jLabel4, null);
		this.OpmModel.add(this.jTextField3, null);
		this.OpmModel.add(this.jTextField1, null);
		this.OpmModel.add(this.jTextField4, null);
		this.OpmModel.add(this.jTextField5, null);
		this.OpmModel.add(this.jTextField2, null);
		this.OpmModel.add(this.jScrollPane1, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.OpmModel, null);

		this.setBounds(0, 0, 388, 360);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void createButton_actionPerformed(ActionEvent e) {

		// first check that all feilds are filled with information.
		if ((this.jTextField1.getText().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Data is missing in one of the fields", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.dispose();

		EditableOpmModelValue editableOpmModelValue = new EditableOpmModelValue();
		editableOpmModelValue.setDescription(this.jTextArea1.getText());
		editableOpmModelValue.setOpmModelName(this.jTextField1.getText());
		editableOpmModelValue
				.setWorkgroupID(this.myEnhancedWorkgroupPermissionsValue
						.getWorkgroupID());
		EnhancedOpmModelPermissionsValue eompv = null;

		try {
			eompv = this.myTeamMember.createOpmModel(editableOpmModelValue);
		} catch (Exception ex) {
			// ExceptionHandler exceptionHandler = new ExceptionHandler(ex);
		}
		if (eompv != null) {
			// add the new OPM model to the tree
			OpmModelTreeNode om = new OpmModelTreeNode(eompv);
			this.myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(om);

		}
	}
} // end of class AddOpmModelDialog

class AddOpmModelDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	AddOpmModelDialog adaptee;

	AddOpmModelDialog_cancelButton_actionAdapter(AddOpmModelDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
}

class AddOpmModelDialog_createButton_actionAdapter implements
		java.awt.event.ActionListener {
	AddOpmModelDialog adaptee;

	AddOpmModelDialog_createButton_actionAdapter(AddOpmModelDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.createButton_actionPerformed(e);
	}
}
