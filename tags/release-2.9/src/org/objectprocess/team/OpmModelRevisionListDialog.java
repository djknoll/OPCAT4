package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.MetaRevisionValue;
import org.objectprocess.SoapClient.UserValue;

public class OpmModelRevisionListDialog extends JDialog {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	 
TeamMember myTeamMember;
  Integer myOpmModelID;

  JPanel OpmModel = new JPanel();
  JLabel logoLabel = new JLabel(OPCATeamImages.PEOPLE);


  JButton cancelButton = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  private JList genList = new JList();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea jTextArea1 = new JTextArea();


public OpmModelRevisionListDialog(TeamMember teamMember, Integer opmModelID) throws HeadlessException {
    super(Opcat2.getFrame(),true);

    this.myTeamMember=teamMember;
    this.myOpmModelID = opmModelID;

          try {
            this.jbInit();
            }
            catch(Exception e) {
              e.printStackTrace();
              }
   }

private void jbInit() throws Exception {

     this.getContentPane().setLayout(null);
     this.setTitle("List Of Opm Model Revesions");
     this.OpmModel.setBounds(new Rectangle(0, 0, 280, 320));
     this.OpmModel.setLayout(null);

     this.logoLabel.setBounds(new Rectangle(27, 28, 50, 50));

     this.cancelButton.setText("Cancel");
     this.cancelButton.addActionListener(new
         OpmModelRevisionListDialog_cancelButton_actionAdapter(this));
     this.cancelButton.setBounds(new Rectangle(149, 271, 96, 28));

     this.jScrollPane1.setBounds(new Rectangle(122, 28, 124, 128));
     this.jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.
                                               HORIZONTAL_SCROLLBAR_NEVER);

     this.genList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     //Listen for when the selection changes.
     this.genList.addListSelectionListener(new ListSelectionListener() {
       public void valueChanged(ListSelectionEvent e) {
         RevisionInList revisionInList = (RevisionInList) OpmModelRevisionListDialog.this.genList.
             getSelectedValue();

         if (revisionInList == null) {
			return;
		}

         UserValue userValue = null;
         String commiterLoginName= null;
         Integer commiterID = revisionInList.myMetaRevisionValue.getComitterID();
         try {
           userValue = OpmModelRevisionListDialog.this.myTeamMember.getUserByPK(commiterID.intValue());
           commiterLoginName = userValue.getFirstName()+" " + userValue.getLastName();
         }catch (Exception exception) {}

         Calendar calendar = revisionInList.myMetaRevisionValue.getCreationTime();

         OpmModelRevisionListDialog.this.jTextArea1.setText("");
         OpmModelRevisionListDialog.this.jTextArea1.setText(
             "Creation Time: " + calendar.getTime().toString() + "\n" +
             "Description: " + revisionInList.myMetaRevisionValue.getDescription() + "\n" +
             "Commiter: " + commiterLoginName);
       }
     });

     //fetch the list of revisions from server
     Object[] metaRevisionsList = this.myTeamMember.fatchOpmModelAllRevisions(this.myOpmModelID.intValue());

     RevisionInList[] revisionsInList = new RevisionInList[metaRevisionsList.length];

     for (int i=0; i<metaRevisionsList.length; i++) {
		revisionsInList[i] = new RevisionInList((MetaRevisionValue)metaRevisionsList[i]);
	}

    this.genList.setListData(revisionsInList);
    this.genList.setToolTipText("");
//    genList.setSelectedIndex(0);

    this.jScrollPane2.setBounds(new Rectangle(25, 180, 220, 69));
    this.jTextArea1.setBackground(Color.lightGray);
    this.jTextArea1.setEditable(false);
    this.OpmModel.add(this.logoLabel, null);
    this.OpmModel.add(this.jScrollPane1, null);
    this.OpmModel.add(this.jScrollPane2, null);
    this.OpmModel.add(this.cancelButton, null);
    this.jScrollPane2.getViewport().add(this.jTextArea1, null);
    this.jScrollPane1.getViewport().add(this.genList, null);
    this.getContentPane().add(this.OpmModel, null);


    this.setBounds(0, 0, 280, 350);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }


} //end of class OpmModelUserList Dialog

class OpmModelRevisionListDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
  OpmModelRevisionListDialog adaptee;

  OpmModelRevisionListDialog_cancelButton_actionAdapter(OpmModelRevisionListDialog adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    this.adaptee.cancelButton_actionPerformed(e);
  }
}



