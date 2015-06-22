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
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.FullRevisionValue;
import org.objectprocess.SoapClient.MetaRevisionValue;

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

public class AddCollaborativeSessionDialog extends JDialog {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	protected TeamMember myTeamMember;

	protected EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

	protected EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

	protected Opcat2 myOpcat2;

	protected Integer majorRevision = new Integer(0);

	protected Integer minorRevision = new Integer(0);

	protected Integer revisionID = null;

	protected Date creationDate = (new GregorianCalendar()).getTime();

	private final static String fileSeparator = System
			.getProperty("file.separator");

	JPanel CollaborativeSession = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JLabel jLabel1 = new JLabel();

	JLabel jLabel4 = new JLabel();

	JLabel jLabel3 = new JLabel();

	JLabel jLabel5 = new JLabel();

	JLabel jLabel6 = new JLabel();

	JTextField jTextField1 = new JTextField();

	JTextField jTextField3 = new JTextField();

	JTextField jTextField4 = new JTextField();

	JButton createButton = new JButton();

	JButton cancelButton = new JButton();

	JScrollPane jScrollPane1 = new JScrollPane();

	JTextField jTextField5 = new JTextField();

	JLabel jLabel2 = new JLabel();

	JTextField jTextField2 = new JTextField();

	JTextArea jTextArea1 = new JTextArea();

	public AddCollaborativeSessionDialog(TeamMember teamMember,
			EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue,
			Opcat2 opcat2) throws HeadlessException {
		super(Opcat2.getFrame(), true);

		this.myTeamMember = teamMember;
		this.myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;
		this.myOpcat2 = opcat2;
		this.myEnhancedCollaborativeSessionPermissionsValue = null;

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
				this.revisionID = ((MetaRevisionValue) (metaRevisionsList[index]))
						.getRevisionID();
			}

			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Create New Collaborative Session");
		this.CollaborativeSession.setLayout(null);
		this.CollaborativeSession.setBounds(new Rectangle(0, 0, 395, 345));

		this.jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel1.setText("Session Name:");
		this.jLabel1.setBounds(new Rectangle(87, 32, 107, 21));
		this.jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel4.setText("Creation Date:");
		this.jLabel4.setBounds(new Rectangle(87, 148, 123, 21));
		this.jLabel3.setEnabled(true);
		this.jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel3.setText("Description:");
		this.jLabel3.setBounds(new Rectangle(87, 232, 107, 21));
		this.jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel5.setText("Opm Model Name:");
		this.jLabel5.setBounds(new Rectangle(87, 72, 127, 21));
		this.jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel6.setText("VC Revision:");
		this.jLabel6.setBounds(new Rectangle(87, 110, 124, 21));

		this.logoLabel.setBounds(new Rectangle(17, 32, 50, 50));

		this.jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField1.setText("");
		this.jTextField1.setBounds(new Rectangle(211, 32, 149, 25));
		this.jTextField3.setBounds(new Rectangle(211, 148, 149, 25));
		this.jTextField3.setText(this.creationDate.toString());
		this.jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.jTextField3.setEditable(false);
		this.jTextField4.setText(this.myEnhancedOpmModelPermissionsValue.getOpmModel()
				.getOpmModelName());
		this.jTextField4.setBounds(new Rectangle(211, 72, 149, 25));
		this.jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField4.setEditable(false);
		this.jTextField5.setBounds(new Rectangle(211, 110, 149, 25));
		this.jTextField5.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextField5.setEditable(false);
		this.jTextField5.setText(this.majorRevision + "." + this.minorRevision);

		this.createButton.setText("Create");
		this.createButton
				.addActionListener(new AddCollaborativeSessionDialog_createButton_actionAdapter(
						this));
		this.createButton.setBounds(new Rectangle(17, 302, 96, 28));

		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new AddCollaborativeSessionDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(264, 302, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(211, 232, 152, 45));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
		this.jLabel2.setText("Token Timeout:");
		this.jLabel2.setBounds(new Rectangle(87, 186, 127, 15));
		this.jTextField2.setFont(new java.awt.Font("SansSerif", 0, 14));
		this.jTextField2.setText("600");
		this.jTextField2.setBounds(new Rectangle(211, 186, 149, 25));
		this.jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
		this.jTextArea1.setText("");
		this.CollaborativeSession.add(this.jLabel4, null);
		this.CollaborativeSession.add(this.jLabel2, null);
		this.CollaborativeSession.add(this.jLabel6, null);
		this.CollaborativeSession.add(this.jLabel5, null);
		this.CollaborativeSession.add(this.jLabel1, null);
		this.CollaborativeSession.add(this.jTextField2, null);
		this.CollaborativeSession.add(this.jTextField3, null);
		this.CollaborativeSession.add(this.jTextField5, null);
		this.CollaborativeSession.add(this.jTextField4, null);
		this.CollaborativeSession.add(this.jTextField1, null);
		this.CollaborativeSession.add(this.logoLabel, null);
		this.CollaborativeSession.add(this.jLabel3, null);
		this.CollaborativeSession.add(this.jScrollPane1, null);
		this.CollaborativeSession.add(this.cancelButton, null);
		this.CollaborativeSession.add(this.createButton, null);
		this.jScrollPane1.getViewport().add(this.jTextArea1, null);
		this.getContentPane().add(this.CollaborativeSession, null);

		this.setBounds(0, 0, 395, 375);
		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void createButton_actionPerformed(ActionEvent e) {
		// first check that all feilds are filled with information.
		if ((this.jTextField1.getText().equals(""))
				|| (this.jTextField2.getText().equals(""))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Data is missing in one of the fields", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.dispose();

		EditableCollaborativeSessionValue editableCollaborativeSessionValue = new EditableCollaborativeSessionValue();
		editableCollaborativeSessionValue
				.setCollaborativeSessionName(this.jTextField1.getText());
		editableCollaborativeSessionValue.setDescription(this.jTextArea1.getText());
		editableCollaborativeSessionValue
				.setOpmModelID(this.myEnhancedOpmModelPermissionsValue
						.getOpmModelID());
		editableCollaborativeSessionValue.setRevisionID(this.revisionID);
		editableCollaborativeSessionValue.setMajorRevision(this.majorRevision);
		editableCollaborativeSessionValue.setMinorRevision(this.minorRevision);
		editableCollaborativeSessionValue.setTokenTimeout(new Integer(
				this.jTextField2.getText()));
		editableCollaborativeSessionValue.setUserTimeout(new Integer(0));

		try {
			this.myEnhancedCollaborativeSessionPermissionsValue = this.myTeamMember
					.createCollaborativeSession(editableCollaborativeSessionValue);
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
		if (this.myEnhancedCollaborativeSessionPermissionsValue != null) {
			try {
				// add the new collaborative session to the tree
				CollaborativeSessionTreeNode cs = new CollaborativeSessionTreeNode(
						this.myEnhancedCollaborativeSessionPermissionsValue);
				this.myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(
						cs);

				// fetch the selected OPM model revision file for the session.
				this.fetchModelRevisionToSession(this.revisionID);

				// create activeCollaborativeSession instance
				ActiveCollaborativeSession activeCollaborativeSession = new ActiveCollaborativeSession(
						this.myTeamMember,
						this.myEnhancedCollaborativeSessionPermissionsValue,
						this.myOpcat2);
				this.myOpcat2
						.setActiveCollaborativeSession(activeCollaborativeSession);
				activeCollaborativeSession.loginToSession();

			} catch (Exception ex) {
				ExceptionHandler exceptionHandler = new ExceptionHandler(ex);
				exceptionHandler.logError(ex);
			}
		}
	}

	private void fetchModelRevisionToSession(Integer revisionID)
			throws Exception {

		FullRevisionValue fullRevisionValue = null;
		CollaborativeSessionFileValue collaborativeSessionFileValue = new CollaborativeSessionFileValue();

		try {
			// if revision ID is null-> empty model is requested
			if (revisionID != null) {
				fullRevisionValue = this.myTeamMember.fatchOpmModelFile(revisionID
						.intValue());
				if (fullRevisionValue == null) {
					return;
				}
				collaborativeSessionFileValue.setOpmModelFile(fullRevisionValue
						.getOpmModelFile());
			} else {
				FileConvertor fileConvertor = new FileConvertor();
				String fileName = "OPCATeam" + fileSeparator + "EmptyModel.opx";

				String finalString = fileConvertor
						.convertFileToString(fileName);
				collaborativeSessionFileValue.setOpmModelFile(finalString);

			}
			// send the file from the opm model to the server
			collaborativeSessionFileValue
					.setCollaborativeSessionID(this.myEnhancedCollaborativeSessionPermissionsValue
							.getCollaborativeSessionID());
			collaborativeSessionFileValue
					.setPrimaryKey(this.myEnhancedCollaborativeSessionPermissionsValue
							.getCollaborativeSessionID());

			this.myTeamMember
					.updateCollaborativeSessionFile(collaborativeSessionFileValue);
		} catch (Exception e) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(e);
			exceptionHandler.logError(e);
			throw e;
		}
	}

	class AddCollaborativeSessionDialog_cancelButton_actionAdapter implements
			java.awt.event.ActionListener {
		AddCollaborativeSessionDialog adaptee;

		AddCollaborativeSessionDialog_cancelButton_actionAdapter(
				AddCollaborativeSessionDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			this.adaptee.cancelButton_actionPerformed(e);
		}
	}

	class AddCollaborativeSessionDialog_createButton_actionAdapter implements
			java.awt.event.ActionListener {
		AddCollaborativeSessionDialog adaptee;

		AddCollaborativeSessionDialog_createButton_actionAdapter(
				AddCollaborativeSessionDialog adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			this.adaptee.createButton_actionPerformed(e);
		}
	}

} // end of class AddCollaborativeSessionDialog

