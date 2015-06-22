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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	protected DefaultMutableTreeNode myNode;

	protected TeamMember myTeamMember;

	protected Opcat2 myOpcat2;

	protected EnhancedOpmModelPermissionsValue myEnhancedOpmModelPermissionsValue;

	public OpmModelPopupMenu(DefaultMutableTreeNode node,
			TeamMember teamMember,
			EnhancedOpmModelPermissionsValue enhancedOpmModelPermissionsValue,
			Opcat2 opcat2) {
		super();
		this.myNode = node;
		this.myTeamMember = teamMember;
		this.myEnhancedOpmModelPermissionsValue = enhancedOpmModelPermissionsValue;
		this.myOpcat2 = opcat2;

		this.add(this.addCollaborativeSessionAction);
		this.add(new JSeparator());
		this.add(this.accessControlAction);
		this.add(this.showUsersListAction);
		this.add(this.showRevisionsListAction);
		this.add(this.opmModelDetailsAction);
		this.add(new JSeparator());
		this.add(this.disableOpmModelAction);

		Integer accessControl = this.myEnhancedOpmModelPermissionsValue
				.getAccessControl();
		if (accessControl.intValue() < PermissionFlags.ADMINISTRATOR.intValue()) {
			this.disableOpmModelAction.setEnabled(false);
		}
		if (accessControl.intValue() < PermissionFlags.EDITOR.intValue()) {
			this.addCollaborativeSessionAction.setEnabled(false);
		}

	}

	Action opmModelDetailsAction = new AbstractAction("Show/Edit Details",
			OPCATeamImages.DOCUMENT) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			OpmModelValueDialog OpmModelDataDialog = new OpmModelValueDialog(
					OpmModelPopupMenu.this.myTeamMember,
					OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue);
			OpmModelDataDialog.setVisible(true);
		}
	};

	Action disableOpmModelAction = new AbstractAction("Disable OPM Model",
			StandardImages.DELETE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4339870154144988919L;

		public void actionPerformed(ActionEvent e) {
			int retValue;
			//
			retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"Are you sure you want to disable the model?",
					"Disable OPM Model", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			switch (retValue) {
			case JOptionPane.YES_OPTION:
				try {
					OpmModelPopupMenu.this.myTeamMember
							.disableOpmModel((OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue
									.getOpmModelID()).intValue());
					OpmModelPopupMenu.this.myOpcat2.getRepository().getAdmin()
							.removeNodeFromControlPanelTree(
									OpmModelPopupMenu.this.myNode);
				} catch (Exception exception) {
					ExceptionHandler exceptionHandler = new ExceptionHandler(
							exception);
					exceptionHandler.logError(exception);
				}
				break;

			case JOptionPane.NO_OPTION:
				return;
			}
		}
	};

	Action accessControlAction = new AbstractAction("Permissions Handling",
			OPCATeamImages.PERMITHANDLE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4313883338529999663L;

		public void actionPerformed(ActionEvent e) {
			OpmModelPermissionsDialog opmModelPermissionsDialog = new OpmModelPermissionsDialog(
					OpmModelPopupMenu.this.myTeamMember,
					OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue);
			opmModelPermissionsDialog.setVisible(true);
		}
	};

	Action addCollaborativeSessionAction = new AbstractAction(
			"New  Collaborative Session", StandardImages.NEW) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 534651388018198146L;

		public void actionPerformed(ActionEvent e) {
			if (OpmModelPopupMenu.this.myOpcat2.isProjectOpened()) {
				JOptionPane
						.showMessageDialog(
								Opcat2.getFrame(),
								"You should close the current model before performing new creation.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			AddCollaborativeSessionDialog addCollaborativeSessionDialog = new AddCollaborativeSessionDialog(
					OpmModelPopupMenu.this.myTeamMember,
					OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue,
					OpmModelPopupMenu.this.myOpcat2);
			addCollaborativeSessionDialog.setVisible(true);
		}
	};

	Action showUsersListAction = new AbstractAction("List Of Users",
			OPCATeamImages.MEMBERSTREE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 6559657337375233997L;

		public void actionPerformed(ActionEvent e) {
			OpmModelUserListDialog opmModelUserListDialog = new OpmModelUserListDialog(
					OpmModelPopupMenu.this.myTeamMember,
					OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue
							.getOpmModelID());
			opmModelUserListDialog.setVisible(true);
		}
	};

	Action showRevisionsListAction = new AbstractAction("List Of Revisions",
			StandardImages.EMPTY) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 616379190694441767L;

		public void actionPerformed(ActionEvent e) {
			OpmModelRevisionListDialog opmModelRevisionListDialog = new OpmModelRevisionListDialog(
					OpmModelPopupMenu.this.myTeamMember,
					OpmModelPopupMenu.this.myEnhancedOpmModelPermissionsValue
							.getOpmModelID());
			opmModelRevisionListDialog.setVisible(true);
		}
	};

};// end of class OpmModelPopupMenu
