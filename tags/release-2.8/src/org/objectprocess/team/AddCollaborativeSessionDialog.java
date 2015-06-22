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

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;
import org.objectprocess.SoapClient.FullRevisionValue;
import org.objectprocess.SoapClient.MetaRevisionValue;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AddCollaborativeSessionDialog extends JDialog {

  protected TeamMember myTeamMember;
  protected EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;
  protected EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;
  protected Opcat2 myOpcat2;
  protected Integer majorRevision = new Integer(0);
  protected Integer minorRevision = new Integer(0);
  protected Integer revisionID = null;
  protected Date creationDate = (new GregorianCalendar()) .getTime();
  private final static String fileSeparator = System.getProperty("file.separator");

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
    super(Opcat2.getFrame(),true);

    myTeamMember=teamMember;
    myEnhancedOpmModelPermissionsValue=enhancedOpmModelPermissionsValue;
    myOpcat2 = opcat2;
    myEnhancedCollaborativeSessionPermissionsValue=null;

    try {
      Object[] metaRevisionsList = myTeamMember.fatchOpmModelAllRevisions(
          myEnhancedOpmModelPermissionsValue.getOpmModelID().intValue());

      int index = teamMember.findHigestRevision(metaRevisionsList);
      if (index != -1) {
        majorRevision = ((MetaRevisionValue)(metaRevisionsList[index])).getMajorRevision();
        minorRevision = ((MetaRevisionValue)(metaRevisionsList[index])).getMinorRevision();
        revisionID = ((MetaRevisionValue)(metaRevisionsList[index])).getRevisionID();
      }

      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

private void jbInit() throws Exception {

    this.getContentPane().setLayout(null);
    this.setTitle("Create New Collaborative Session");
    CollaborativeSession.setLayout(null);
    CollaborativeSession.setBounds(new Rectangle(0, 0, 395, 345));

    jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel1.setText("Session Name:");
    jLabel1.setBounds(new Rectangle(87, 32, 107, 21));
    jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel4.setText("Creation Date:");
    jLabel4.setBounds(new Rectangle(87, 148, 123, 21));
    jLabel3.setEnabled(true);
    jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel3.setText("Description:");
    jLabel3.setBounds(new Rectangle(87, 232, 107, 21));
    jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel5.setText("Opm Model Name:");
    jLabel5.setBounds(new Rectangle(87, 72, 127, 21));
    jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel6.setText("VC Revision:");
    jLabel6.setBounds(new Rectangle(87, 110, 124, 21));

    logoLabel.setBounds(new Rectangle(17, 32, 50, 50));

    jTextField1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField1.setText("");
    jTextField1.setBounds(new Rectangle(211, 32, 149, 25));
    jTextField3.setBounds(new Rectangle(211, 148, 149, 25));
    jTextField3.setText(creationDate.toString());
    jTextField3.setFont(new java.awt.Font("SansSerif", 0, 11));
    jTextField3.setEditable(false);
    jTextField4.setText(myEnhancedOpmModelPermissionsValue.getOpmModel().getOpmModelName());
    jTextField4.setBounds(new Rectangle(211, 72, 149, 25));
    jTextField4.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField4.setEditable(false);
    jTextField5.setBounds(new Rectangle(211, 110, 149, 25));
    jTextField5.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextField5.setEditable(false);
    jTextField5.setText(majorRevision +"."+ minorRevision);

    createButton.setText("Create");
    createButton.addActionListener(new AddCollaborativeSessionDialog_createButton_actionAdapter(this));
    createButton.setBounds(new Rectangle(17, 302, 96, 28));

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new AddCollaborativeSessionDialog_cancelButton_actionAdapter(this));
    cancelButton.setBounds(new Rectangle(264, 302, 96, 28));

    jScrollPane1.setBounds(new Rectangle(211, 232, 152, 45));
    jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.
                                  HORIZONTAL_SCROLLBAR_NEVER);

    jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12));
    jLabel2.setText("Token Timeout:");
    jLabel2.setBounds(new Rectangle(87, 186, 127, 15));
    jTextField2.setFont(new java.awt.Font("SansSerif", 0, 14));
    jTextField2.setText("600");
    jTextField2.setBounds(new Rectangle(211, 186, 149, 25));
    jTextArea1.setFont(new java.awt.Font("SansSerif", 0, 12));
    jTextArea1.setText("");
    CollaborativeSession.add(jLabel4, null);
    CollaborativeSession.add(jLabel2, null);
    CollaborativeSession.add(jLabel6, null);
    CollaborativeSession.add(jLabel5, null);
    CollaborativeSession.add(jLabel1, null);
    CollaborativeSession.add(jTextField2, null);
    CollaborativeSession.add(jTextField3, null);
    CollaborativeSession.add(jTextField5, null);
    CollaborativeSession.add(jTextField4, null);
    CollaborativeSession.add(jTextField1, null);
    CollaborativeSession.add(logoLabel, null);
    CollaborativeSession.add(jLabel3, null);
    CollaborativeSession.add(jScrollPane1, null);
    CollaborativeSession.add(cancelButton, null);
    CollaborativeSession.add(createButton, null);
    jScrollPane1.getViewport().add(jTextArea1, null);
    this.getContentPane().add(CollaborativeSession, null);


    this.setBounds(0, 0, 395, 375);
    this.setLocationRelativeTo(this.getParent());
    this.setResizable(false);
    this.setModal(true);
    this.setVisible(true);

} //end of JBinit

    void cancelButton_actionPerformed(ActionEvent e) {
      this.dispose();
      }

    void createButton_actionPerformed(ActionEvent e) {
      //first check that all feilds are filled with information.
      if ( (jTextField1.getText().equals("")) ||
           (jTextField2.getText().equals(""))  )
         {  JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                         "Data is missing in one of the fields",
                                         "Error",
                                         JOptionPane.ERROR_MESSAGE);
           return;
         }

      this.dispose();

      EditableCollaborativeSessionValue editableCollaborativeSessionValue = new EditableCollaborativeSessionValue();
      editableCollaborativeSessionValue.setCollaborativeSessionName(jTextField1.getText());
      editableCollaborativeSessionValue.setDescription(jTextArea1.getText());
      editableCollaborativeSessionValue.setOpmModelID(myEnhancedOpmModelPermissionsValue.getOpmModelID());
      editableCollaborativeSessionValue.setRevisionID(revisionID);
      editableCollaborativeSessionValue.setMajorRevision(majorRevision);
      editableCollaborativeSessionValue.setMinorRevision(minorRevision);
      editableCollaborativeSessionValue.setTokenTimeout(new Integer(jTextField2.getText()));
      editableCollaborativeSessionValue.setUserTimeout(new Integer(0));

      try {
        myEnhancedCollaborativeSessionPermissionsValue =
            myTeamMember.createCollaborativeSession(editableCollaborativeSessionValue);
      }catch (Exception exception) {
        ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
      }
      if (myEnhancedCollaborativeSessionPermissionsValue != null) {
        try {
          //add the new collaborative session to the tree
          CollaborativeSessionTreeNode cs = new CollaborativeSessionTreeNode(myEnhancedCollaborativeSessionPermissionsValue);
          myOpcat2.getRepository().getAdmin().addNodeToControlPanelTree(cs);

          //fetch the selected OPM model revision file for the session.
          fetchModelRevisionToSession(revisionID);

          //create activeCollaborativeSession instance
          ActiveCollaborativeSession activeCollaborativeSession = new
              ActiveCollaborativeSession(myTeamMember,
                                         myEnhancedCollaborativeSessionPermissionsValue,
                                         myOpcat2);
          myOpcat2.setActiveCollaborativeSession(activeCollaborativeSession);
          activeCollaborativeSession.loginToSession();

        } catch (Exception ex) {
          ExceptionHandler exceptionHandler = new ExceptionHandler(ex);}
      }
    }

    private void fetchModelRevisionToSession(Integer revisionID) throws Exception {

       FullRevisionValue fullRevisionValue = null;
       CollaborativeSessionFileValue collaborativeSessionFileValue = new
           CollaborativeSessionFileValue();

       try {
         //if revision ID is null-> empty model is requested
         if (revisionID != null) {
           fullRevisionValue = myTeamMember.fatchOpmModelFile(revisionID.intValue());
           if (fullRevisionValue == null) return;
           collaborativeSessionFileValue.setOpmModelFile(fullRevisionValue.getOpmModelFile());
         }
         else {
           FileConvertor fileConvertor = new FileConvertor();
           String fileName = "OPCATeam" + fileSeparator + "EmptyModel.opx";

           String finalString = fileConvertor.convertFileToString(fileName);
           collaborativeSessionFileValue.setOpmModelFile(finalString);

         }
        //send the file from the opm model to the server
         collaborativeSessionFileValue.setCollaborativeSessionID(
             myEnhancedCollaborativeSessionPermissionsValue.
             getCollaborativeSessionID());
         collaborativeSessionFileValue.setPrimaryKey(
            myEnhancedCollaborativeSessionPermissionsValue.
            getCollaborativeSessionID());

         myTeamMember.updateCollaborativeSessionFile(collaborativeSessionFileValue);
       }
       catch (Exception e) {
         ExceptionHandler exceptionHandler = new ExceptionHandler(e);
         throw e;
       }
     }


    class AddCollaborativeSessionDialog_cancelButton_actionAdapter implements java.awt.event.ActionListener {
    	  AddCollaborativeSessionDialog adaptee;

    	  AddCollaborativeSessionDialog_cancelButton_actionAdapter(AddCollaborativeSessionDialog adaptee) {
    	    this.adaptee = adaptee;
    	  }
    	  public void actionPerformed(ActionEvent e) {
    	    adaptee.cancelButton_actionPerformed(e);
    	  }
    	}

    	class AddCollaborativeSessionDialog_createButton_actionAdapter implements java.awt.event.ActionListener {
    	  AddCollaborativeSessionDialog adaptee;

    	  AddCollaborativeSessionDialog_createButton_actionAdapter(AddCollaborativeSessionDialog adaptee) {
    	    this.adaptee = adaptee;
    	  }
    	  public void actionPerformed(ActionEvent e) {
    	    adaptee.createButton_actionPerformed(e);
    	  }
    	}


} //end of class AddCollaborativeSessionDialog




