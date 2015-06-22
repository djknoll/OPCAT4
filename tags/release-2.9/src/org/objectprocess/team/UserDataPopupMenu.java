package org.objectprocess.team;

import gui.Opcat2;
import gui.images.opcaTeam.OPCATeamImages;
import gui.images.standard.StandardImages;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import org.objectprocess.Client.TeamMember;

//Class for User Data Popup Menu
public class UserDataPopupMenu extends JPopupMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	protected TeamMember myTeamMember;

	protected Opcat2 myOpcat2;

	public UserDataPopupMenu(TeamMember teamMember, Opcat2 opcat2) {
		super();
		this.myTeamMember = teamMember;
		this.myOpcat2 = opcat2;

		this.add(this.addWorkgroupAction);
		this.add(new JSeparator());
		this.add(this.userDetailsAction);
		this.add(new JSeparator());
		this.add(this.refreshAction);

	}

	Action addWorkgroupAction = new AbstractAction("New Workgroup",
			StandardImages.NEW) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

		/**
				 * 
				 */
				 

		public void actionPerformed(ActionEvent e) {
			AddWorkgroupDialog addWorkgroupDialog = new AddWorkgroupDialog(
					UserDataPopupMenu.this.myTeamMember, UserDataPopupMenu.this.myOpcat2);
			addWorkgroupDialog.setVisible(true);
		}
	};

	Action userDetailsAction = new AbstractAction("Show/Edit Details",
			OPCATeamImages.DOCUMENT) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = -1573234013528399386L;

		public void actionPerformed(ActionEvent e) {
			UserValueDialog userDataDialog = new UserValueDialog(UserDataPopupMenu.this.myTeamMember);
			userDataDialog.setVisible(true);
		}
	};

	Action refreshAction = new AbstractAction("Refresh Control Panel",
			OPCATeamImages.CONTROL_PANEL) {
		 

		/**
				 * 
				 */
				private static final long serialVersionUID = 690948959809436540L;

		public void actionPerformed(ActionEvent e) {
			UserDataPopupMenu.this.myOpcat2.getRepository().refreshOPCATeamControlPanel();
		}
	};

} // end of class UserDataPopUpMenu
