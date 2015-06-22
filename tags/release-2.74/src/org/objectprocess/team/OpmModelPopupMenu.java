package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.standard.StandardImages;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.tree.DefaultMutableTreeNode;

import org.objectprocess.Client.PermissionFlags;
import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.EnhancedOpmModelPermissionsValue;


// class for the OpmModel PopUp menu
public class OpmModelPopupMenu extends JPopupMenu {
  protected DefaultMutableTreeNode myNode;
  protected TeamMember myTeamMember;
  protected Opcat2 myOpcat2;
  protected EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

    public OpmModelPopupMenu(DefaultMutableTreeNode node,
                             TeamMember teamMember,
                             EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue,
                             Opcat2 opcat2) {
      super();
      myNode = node;
      myTeamMember = teamMember;
      myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;
      myOpcat2 = opcat2;

      this.add(addCollaborativeSessionAction);
      this.add(  new JSeparator());
      this.add(accessControlAction);
      this.add(showUsersListAction);
      this.add(showRevisionsListAction);
      this.add(opmModelDetailsAction);
      this.add(  new JSeparator());
      this.add(disableOpmModelAction);

      Integer accessControl = myEnhancedOpmModelPermissionsValue.getAccessControl();
      if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue())
        disableOpmModelAction.setEnabled(false);
      if (accessControl.intValue() < PermissionFlags.EDITOR.intValue())
        addCollaborativeSessionAction.setEnabled(false);


    }

    Action opmModelDetailsAction = new AbstractAction("Show/Edit Details", OPCATeamImages.DOCUMENT){
      public void actionPerformed(ActionEvent e){
        OpmModelValueDialog OpmModelDataDialog = new OpmModelValueDialog(myTeamMember,
                                                                         myEnhancedOpmModelPermissionsValue);
        }
    };

    Action disableOpmModelAction = new AbstractAction("Disable OPM Model", StandardImages.DELETE){
      public void actionPerformed(ActionEvent e) {
        int retValue;
        //
        retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                           "Are you sure you want to disable the model?",
                                           "Disable OPM Model",
                                           JOptionPane.YES_NO_OPTION,
                                           JOptionPane.QUESTION_MESSAGE);
        switch (retValue) {
          case JOptionPane.YES_OPTION:
            try {
              myTeamMember.disableOpmModel( (myEnhancedOpmModelPermissionsValue.
                                             getOpmModelID()).intValue());
              myOpcat2.getRepository().getAdmin().removeNodeFromControlPanelTree(myNode);
            }catch (Exception exception) {
              ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
            }
            break;

          case JOptionPane.NO_OPTION:
            return;
        }
      }
    };

    Action accessControlAction = new AbstractAction("Permissions Handling", OPCATeamImages.PERMITHANDLE){
          public void actionPerformed(ActionEvent e){
            OpmModelPermissionsDialog  opmModelPermissionsDialog = new OpmModelPermissionsDialog(
                myTeamMember,
                myEnhancedOpmModelPermissionsValue);
                }
            };

    Action addCollaborativeSessionAction = new AbstractAction("New  Collaborative Session", StandardImages.NEW){
      public void actionPerformed(ActionEvent e){
        if (myOpcat2.isProjectOpened()) {
          JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                        "You should close the current model before performing new creation.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
          return;
        }

        AddCollaborativeSessionDialog addCollaborativeSessionDialog =
            new AddCollaborativeSessionDialog(myTeamMember,
                                              myEnhancedOpmModelPermissionsValue,
                                              myOpcat2);
      }
    };

    Action showUsersListAction = new AbstractAction("List Of Users", OPCATeamImages.MEMBERSTREE){
      public void actionPerformed(ActionEvent e){
        OpmModelUserListDialog opmModelUserListDialog = new OpmModelUserListDialog(myTeamMember,
            myEnhancedOpmModelPermissionsValue.getOpmModelID());
      }
    };

    Action showRevisionsListAction = new AbstractAction("List Of Revisions", StandardImages.EMPTY){
      public void actionPerformed(ActionEvent e){
        OpmModelRevisionListDialog opmModelRevisionListDialog = new OpmModelRevisionListDialog(myTeamMember,
            myEnhancedOpmModelPermissionsValue.getOpmModelID());
      }
    };

};//end of class OpmModelPopupMenu
