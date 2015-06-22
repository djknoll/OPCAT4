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
	 
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	 
DefaultMutableTreeNode myNode;
  TeamMember myTeamMember;
  Opcat2 myOpcat2;
  EnhancedWorkgroupPermissionsValue myEnhancedWorkgroupPermissionsValue;

    public WorkgroupPopupMenu(DefaultMutableTreeNode node,
                              TeamMember teamMember,
                              EnhancedWorkgroupPermissionsValue enhancedWorkgroupPermissionsValue,
                              Opcat2 opcat2) {
      super();
      this.myNode = node;
      this.myTeamMember=teamMember;
      this.myEnhancedWorkgroupPermissionsValue = enhancedWorkgroupPermissionsValue;
      this.myOpcat2 = opcat2;

      this.add(this.addOPMmodelAction);
      this.add( new JSeparator());
      this.add(this.showUsersListAction);
      this.add(this.accessControlAction);
      this.add(this.workgroupDetailsAction);
      this.add(  new JSeparator());
      this.add(this.disableWorkgroupAction);

      Integer accessControl = this.myEnhancedWorkgroupPermissionsValue.getAccessControl();
      if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
		this.disableWorkgroupAction.setEnabled(false);
	}
      if (accessControl.intValue() < PermissionFlags.EDITOR.intValue()) {
		this.addOPMmodelAction.setEnabled(false);
	}

    }

    Action workgroupDetailsAction = new AbstractAction("Show/Edit Details", OPCATeamImages.DOCUMENT){
    	 
      /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	/**
		 * 
		 */
		 

	public void actionPerformed(ActionEvent e){
        WorkGroupValueDialog workgroupDialog = new WorkGroupValueDialog(WorkgroupPopupMenu.this.myTeamMember,
                                                                        WorkgroupPopupMenu.this.myEnhancedWorkgroupPermissionsValue);
        workgroupDialog.setVisible(true) ; 
        }
    };

    Action disableWorkgroupAction = new AbstractAction("Disable Workgroup", StandardImages.DELETE){
    	 
      /**
		 * 
		 */
		private static final long serialVersionUID = 1855984435746416737L;

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
                WorkgroupPopupMenu.this.myTeamMember.disableWorkgroup( (WorkgroupPopupMenu.this.myEnhancedWorkgroupPermissionsValue.
                                                getWorkgroupID()).intValue());
                WorkgroupPopupMenu.this.myOpcat2.getRepository().getAdmin().removeNodeFromControlPanelTree(WorkgroupPopupMenu.this.myNode);
              }catch (Exception exception) {
                ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
                exceptionHandler.logError(exception) ; 
              }
              break;

            case JOptionPane.NO_OPTION:
               return;
           }
         }
       };

    Action accessControlAction = new AbstractAction("Permissions Handling", OPCATeamImages.PERMITHANDLE){
    	 
          /**
		 * 
		 */
		private static final long serialVersionUID = -9176961556950330404L;

		public void actionPerformed(ActionEvent e){
            WorkgroupPermissionsDialog  workgroupPermissionsDialog = new WorkgroupPermissionsDialog(WorkgroupPopupMenu.this.myTeamMember,
                                                                                                    WorkgroupPopupMenu.this.myEnhancedWorkgroupPermissionsValue);
            workgroupPermissionsDialog.setVisible(true) ; 
                                       }
       };

    Action addOPMmodelAction = new AbstractAction("New OPM model", StandardImages.NEW) {
    	 
             /**
		 * 
		 */
		private static final long serialVersionUID = -6598387688206856540L;

			public void actionPerformed(ActionEvent e){
               if (WorkgroupPopupMenu.this.myOpcat2.isProjectOpened()) {
                 JOptionPane.showMessageDialog(Opcat2.getFrame(),
                                               "You should close the current model before performing new creation.",
                                               "Error",
                                               JOptionPane.ERROR_MESSAGE);
                 return;
               }
               AddOpmModelDialog addOpmModelDialog = new AddOpmModelDialog(WorkgroupPopupMenu.this.myTeamMember,
                                                                           WorkgroupPopupMenu.this.myEnhancedWorkgroupPermissionsValue,
                                                                           WorkgroupPopupMenu.this.myOpcat2);
               addOpmModelDialog.setVisible(true) ;                

               }
          };

    Action showUsersListAction = new AbstractAction("List Of Users", OPCATeamImages.MEMBERSTREE){
    	 
            /**
		 * 
		 */
		private static final long serialVersionUID = 8145300391535704269L;

			public void actionPerformed(ActionEvent e){
              WorkgroupUserListDialog workgroupUserListDialog = new WorkgroupUserListDialog(WorkgroupPopupMenu.this.myTeamMember,
                                                                                            WorkgroupPopupMenu.this.myEnhancedWorkgroupPermissionsValue.getWorkgroupID());
              workgroupUserListDialog.setVisible(true) ; 
              }
          };


};//end of class WorkgroupPopupMenu
