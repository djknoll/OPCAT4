package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class CommitConfirmationDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	protected ActiveCollaborativeSession activeCollaborativeSession;

	protected Opcat2 myOpcat2;

	JPanel commitConfirmation = new JPanel();

	JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);

	JScrollPane jScrollPane1 = new JScrollPane();

	JButton commitButton = new JButton();

	JButton cancelButton = new JButton();

	JTextArea commitDescription = new JTextArea();

	JLabel jLabel1 = new JLabel();

	JLabel jLabel2 = new JLabel();

	JCheckBox increaseMajor = new JCheckBox();

	public CommitConfirmationDialog(Opcat2 opcat2) throws HeadlessException {
		super(Opcat2.getFrame(), true);
		this.myOpcat2 = opcat2;
		this.activeCollaborativeSession = this.myOpcat2
				.getActiveCollaborativeSession();

		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setTitle("Commit Confirmation Details");
		this.commitConfirmation.setBounds(new Rectangle(0, 0, 433, 334));

		this.commitConfirmation.setLayout(null);

		this.logoLabel.setBounds(new Rectangle(17, 23, 50, 50));

		this.commitButton.setText("Commit");
		this.commitButton
				.addActionListener(new CommitConfirmationDialog_commitButton_actionAdapter(
						this));
		this.commitButton.setBounds(new Rectangle(17, 291, 96, 28));
		this.cancelButton.setText("Cancel");
		this.cancelButton
				.addActionListener(new CommitConfirmationDialog_cancelButton_actionAdapter(
						this));
		this.cancelButton.setBounds(new Rectangle(319, 293, 96, 28));

		this.jScrollPane1.setBounds(new Rectangle(17, 148, 398, 123));
		this.jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		this.jLabel1
				.setText("Please insert a short description to summarizes the reasons");
		this.jLabel1.setBounds(new Rectangle(18, 92, 373, 15));
		this.jLabel2.setText("for this Commit Action.");
		this.jLabel2.setBounds(new Rectangle(18, 111, 306, 15));
		this.increaseMajor.setFont(new java.awt.Font("SansSerif", 0, 11));
		this.increaseMajor.setText("Increase  major revision number");
		this.increaseMajor.setBounds(new Rectangle(100, 37, 233, 23));
		this.commitConfirmation.add(this.jScrollPane1, null);
		this.commitConfirmation.add(this.commitButton, null);
		this.commitConfirmation.add(this.cancelButton, null);
		this.commitConfirmation.add(this.increaseMajor, null);
		this.commitConfirmation.add(this.logoLabel, null);
		this.commitConfirmation.add(this.jLabel2, null);
		this.commitConfirmation.add(this.jLabel1, null);
		this.jScrollPane1.getViewport().add(this.commitDescription, null);
		this.getContentPane().add(this.commitConfirmation, null);

		this.setBounds(0, 0, 433, 364);

		this.setLocationRelativeTo(this.getParent());
		this.setResizable(false);
		this.setModal(true);

	} // end of JBinit

	void cancelButton_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	void commitButton_actionPerformed(ActionEvent e) {

		if (this.commitDescription.getText().equals("")) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"Commit Description is missing", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.dispose();
		this.activeCollaborativeSession.commitActiveCollaborativeSession(
				this.commitDescription.getText(), this.increaseMajor
						.isSelected());
	}

} // end of class UserDataDialog

class CommitConfirmationDialog_commitButton_actionAdapter implements
		java.awt.event.ActionListener {
	CommitConfirmationDialog adaptee;

	CommitConfirmationDialog_commitButton_actionAdapter(
			CommitConfirmationDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.commitButton_actionPerformed(e);
	}
} // end of class

class CommitConfirmationDialog_cancelButton_actionAdapter implements
		java.awt.event.ActionListener {
	CommitConfirmationDialog adaptee;

	CommitConfirmationDialog_cancelButton_actionAdapter(
			CommitConfirmationDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		this.adaptee.cancelButton_actionPerformed(e);
	}
} // end of class

