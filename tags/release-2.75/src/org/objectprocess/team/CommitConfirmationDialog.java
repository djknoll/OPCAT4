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


public class CommitConfirmationDialog extends JDialog {

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
    super(Opcat2.getFrame(),true);
    myOpcat2 = opcat2;
    activeCollaborativeSession = myOpcat2.getActiveCollaborativeSession();

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Commit Confirmation Details");
    commitConfirmation.setBounds(new Rectangle(0, 0, 433, 334));

    commitConfirmation.setLayout(null);

    logoLabel.setBounds(new Rectangle(17, 23, 50, 50));

    commitButton.setText("Commit");
    commitButton.addActionListener(new CommitConfirmationDialog_commitButton_actionAdapter(this));
    commitButton.setBounds(new Rectangle(17, 291, 96, 28));
    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new CommitConfirmationDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(319, 293, 96, 28));

    jScrollPane1.setBounds(new Rectangle(17, 148, 398, 123));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                          HORIZONTAL_SCROLLBAR_NEVER);

    jLabel1.setText("Please insert a short description to summarizes the reasons");
    jLabel1.setBounds(new Rectangle(18, 92, 373, 15));
    jLabel2.setText("for this Commit Action.");
    jLabel2.setBounds(new Rectangle(18, 111, 306, 15));
    increaseMajor.setFont(new java.awt.Font("SansSerif", 0, 11));
    increaseMajor.setText("Increase  major revision number");
    increaseMajor.setBounds(new Rectangle(100, 37, 233, 23));
    commitConfirmation.add(jScrollPane1, null);
    commitConfirmation.add(commitButton, null);
    commitConfirmation.add(cancelButton, null);
    commitConfirmation.add(increaseMajor, null);
    commitConfirmation.add(logoLabel, null);
    commitConfirmation.add(jLabel2, null);
    commitConfirmation.add(jLabel1, null);
    jScrollPane1.getViewport().add(commitDescription, null);
    this.getContentPane().add(commitConfirmation, null);

    this.setBounds(0, 0, 433, 364);

    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

  } //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }

    void commitButton_actionPerformed(ActionEvent e) {

      if (commitDescription.getText().equals(""))
        {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                        "Commit Description is missing",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }
        this.dispose();
        activeCollaborativeSession.commitActiveCollaborativeSession
            (commitDescription.getText(),increaseMajor.isSelected());
      }

} //end of class UserDataDialog


class CommitConfirmationDialog_commitButton_actionAdapter implements java.awt.event.ActionListener {
      CommitConfirmationDialog adaptee;

      CommitConfirmationDialog_commitButton_actionAdapter(CommitConfirmationDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.commitButton_actionPerformed(e);
      }
} //end of class

class CommitConfirmationDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
      CommitConfirmationDialog adaptee;

      CommitConfirmationDialog_cancelButton_actionAdapter(CommitConfirmationDialog adaptee) {
        this.adaptee = adaptee;
      }
      public void actionPerformed(ActionEvent e) {
        adaptee.cancelButton_actionPerformed(e);
      }
} //end of class



