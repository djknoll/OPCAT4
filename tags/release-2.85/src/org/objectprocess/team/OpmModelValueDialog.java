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
import org.objectprocess.SoapClient.EditableOpmModelValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.MetaRevisionValue;

public class OpmModelValueDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	TeamMember myTeamMember;

	EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

	Integer majorRevision = new Integer(0);

	Integer minorRevision = new Integer(0);

	JPanel OpmModel = new JPanel();

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

	JTextArea jTextArea1 = new JTextArea();

	JLabel updateMessage = new JLabel();

	public OpmModelValueDialog(TeamMember teamMember,
			EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue)
			throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;

		try {
			Object[] metaRevisionsList = this.myTeamMember
					.fatchOpmModelAllRevisions(this.myEnhancedOpmModelPermissionsValue
							.getOpmModelID().intValue());

			int index = teamMember.findHigestRevision(metaRevisionsList);
			if (index != -1) {
				this.majorRevision = ((MetaRevisionValue) (metaRevisionsList[index]))
						.getMajorRevision();
				this.minorRevision = ((MetaRevisionValue) (metaRevisionsList[index]))
						.getMinorRevision();
			}

			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("OPM Model Details");
		this.OpmModel.setLayout(null);
		this.OpmModel.setBounds(new Rectangle(0, 0, 369, 310));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("OpmModel Name:");
		this.jLabel1.setBounds(new Rectangle(84, 31, 121, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Description:");
		this.jLabel4.setBounds(new Rectangle(84, 165, 97, 21));
		this.jLabel3.setEnabled(true);
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Creation Date:");
		this.jLabel3.setBounds(new Rectangle(84, 118, 97, 21));
		this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel5.setText("VC Last Revision:");
		this.jLabel5.setBounds(new Rectangle(84, 73, 108, 23));

		this.logoLabel.setBounds(new Rectangle(19, 31, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1.setText(this.myEnhancedOpmModelPermissionsValue
				.getOpmModel().getOpmModelName());
		this.jTextField1.setBounds(new Rectangle(195, 31, 149, 29));
		this.jTextField3.setBounds(new Rectangle(195, 118, 149, 29));
		Calendar calendar = this.myEnhancedOpmModelPermissionsValue
				.getOpmModel().getCreationTime();
		this.jTextField3.setText(calendar.getTime().toString());
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField3.setEditable(false);
		this.jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField4.setEditable(false);
		this.jTextField4.setText(this.majorRevision + "." + this.minorRevision);
		this.jTextField4.setBounds(new Rectangle(195, 73, 149, 29));

		this.updateButton.setText("Update");
		this.updateButton
				.addActionListener(new OpmModelValueDialog_updateButton_actionAdapter(
						this));
		this.updateButton.setBounds(new Rectangle(19, 260, 96, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new OpmModelValueDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(248, 260, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(194, 165, 149, 67));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jTextArea1.setLineWrap(true);
		this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextArea1.setText(this.myEnhancedOpmModelPermissionsValue
				.getOpmModel().getDescription());
		this.updateMessage
				.setText("Only the administrator can perform update action!");
		this.updateMessage.setBounds(new Rectangle(19, 244, 343, 15));
		this.OpmModel.add(this.logoLabel, null);
		this.OpmModel.add(this.jLabel4, null);
		this.OpmModel.add(this.jLabel1, null);
		this.OpmModel.add(this.jLabel5, null);
		this.OpmModel.add(this.jLabel3, null);
		this.OpmModel.add(this.jTextField1, null);
		this.OpmModel.add(this.jTextField4, null);
		this.OpmModel.add(this.cancelButton, null);
		this.OpmModel.add(this.jTextField3, null);
		this.OpmModel.add(this.jScrollPane1, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.OpmModel, null);

		// diable this button if the user is not creator or admin .
		Integer accessControl = this.myEnhancedOpmModelPermissionsValue
				.getAccessControl();
		if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
			this.jTextField1.setEditable(false);
			this.jTextArea1.setEditable(false);
			this.OpmModel.add(this.updateMessage, null);
		} else {
			this.OpmModel.add(this.updateButton, null);
		}

		this.setBounds(0, 0, 369, 340);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void updateButton_actionPerformed(ActionEvent e) {

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
				.setOpmModelID(this.myEnhancedOpmModelPermissionsValue
						.getOpmModelID());
		editableOpmModelValue
				.setPrimaryKey(this.myEnhancedOpmModelPermissionsValue
						.getOpmModelID());
		editableOpmModelValue
				.setWorkgroupID(this.myEnhancedOpmModelPermissionsValue
						.getOpmModel().getWorkgroupID());

		try {
			this.myTeamMember.updateOpmModel(editableOpmModelValue);
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

} // end of class OpmModelDialog

class OpmModelValueDialog_updateButton_actionAdapter implements
		java.awt.event.ActionListener {
	OpmModelValueDialog adaptee;

	OpmModelValueDialog_updateButton_actionAdapter(OpmModelValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.updateButton_actionPerformed(e);
	}
} // end of class

class OpmModelValueDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	OpmModelValueDialog adaptee;

	OpmModelValueDialog_cancelButton_actionAdapter(OpmModelValueDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

