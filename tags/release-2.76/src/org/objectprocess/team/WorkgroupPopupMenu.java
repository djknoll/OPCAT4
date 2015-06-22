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
import org.objectprocess.SoapClient.EnhancedWorkgroupPermissionsValue;


// class for the Workgroup PopUp menu
public class WorkgroupPopupMenu extends JPopupMenu {
  DefaultMutableTreeNode myNode;
  TeamMember myTeamMember;
  Opcat2 myOpcat2;
  EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

    public WorkgroupPopupMenu(DefaultMutableTreeNode node,
                              TeamMember teamMember,
                              EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue,
                              Opcat2 opcat2) {
      super();
      myNode = node;
      myTeamMember=teamMember;
      myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
      myOpcat2 = opcat2;

      this.add(addOPMmodelAction);
      this.add( new JSeparator());
      this.add(showUsersListAction);
      this.add(accessControlAction);
      this.add(workgroupDetailsAction);
      this.add(  new JSeparator());
      this.add(disableWorkgroupAction);

      Integer accessControl = myEnhancedWorkgroupPermissionsValue.getAccessControl();
      if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue())
        disableWorkgroupAction.setEnabled(false);
      if (accessControl.intValue() < PermissionFlags.EDITOR.intValue())
        addOPMmodelAction.setEnabled(false);

    }

    Action workgroupDetailsAction = new AbstractAction("Show/Edit Details", OPCATeamImages.DOCUMENT){
      public void actionPerformed(ActionEvent e){
        WorkGroupValueDialog workgroupDialog = new WorkGroupValueDialog(myTeamMember,
                                                                        myEnhancedWorkgroupPermissionsValue);
        }
    };

    Action disableWorkgroupAction = new AbstractAction("Disable Workgroup", StandardImages.DELETE){
      public void actionPerformed(ActionEvent e) {
        int retValue;
        //
        retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                                                   "Are you sure you want ot disable the wrokgroup?",
                                                   "Disable Workgroup",
                                                   JOptionPane.YES_NO_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
          switch (retValue) {
            case JOptionPane.YES_OPTION:
              try {
                myTeamMember.disableWorkgroup( (myEnhancedWorkgroupPermissionsValue.
                                                getWorkgroupID()).intValue());
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
            WorkgroupPermissionsDialog  workgroupPermissionsDialog = new WorkgroupPermissionsDialog(myTeamMember,
                                                                                                    myEnhancedWorkgroupPermissionsValue);
                                       }
       };

    Action addOPMmodelAction = new AbstractAction("New OPM model", StandardImages.NEW) {
             public void actionPerformed(ActionEvent e){
               if (myOpcat2.isProjectOpened()) {
                 JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                               "You should close the current model before performing new creation.",
                                               "Error",
                                               JOptionPane.ERROR_MESSAGE);
                 return;
               }
               AddOpmModelDialog addOpmModelDialog = new AddOpmModelDialog(myTeamMember,
                                                                           myEnhancedWorkgroupPermissionsValue,
                                                                           myOpcat2);

               }
          };

    Action showUsersListAction = new AbstractAction("List Of Users", OPCATeamImages.MEMBERSTREE){
            public void actionPerformed(ActionEvent e){
              WorkgroupUserListDialog workgroupUserListDialog = new WorkgroupUserListDialog(myTeamMember,
                                                                                            myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
              }
          };


};//end of class WorkgroupPopupMenu
