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
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;

// class for the session PopUp menu
public class CollaborativeSessionPopupMenu extends JPopupMenu {
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

	protected Integer myUserID;

	protected EnhancedCollaborativeSessionPermissionsValue myEnhancedCollaborativeSessionPermissionsValue;

	protected ActiveCollaborativeSession activeCollaborativeSession;

	public CollaborativeSessionPopupMenu(
			DefaultMutableTreeNode node,
			TeamMember teamMember,
			EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue,
			Opcat2 opcat2) {
		super();
		this.myNode = node;
		this.myTeamMember = teamMember;
		this.myOpcat2 = opcat2;
		this.myEnhancedCollaborativeSessionPermissionsValue = enhancedCollaborativeSessionPermissionsValue;
		this.myUserID = this.myEnhancedCollaborativeSessionPermissionsValue
				.getUserID();
		this.activeCollaborativeSession = this.myOpcat2
				.getActiveCollaborativeSession();

		// check if the session is the cuurect active session if yes, present
		// the extend menu,
		// else present the minimal menu.
		Integer currentSessionID = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSessionID();

		this.add(this.loginSessionAction);
		this.add(this.saveSessionAction);
		this.add(this.commitSessionAction);
		this.add(new JSeparator());
		this.add(this.getTokenAction);
		this.add(this.returnTokenAction);
		this.add(new JSeparator());
		this.add(this.showUsersListAction);
		this.add(this.accessControlAction);
		this.add(this.collaborativeSessionDetailsAction);
		this.add(this.disableCollaborativeSessionAction);
		this.add(new JSeparator());
		this.add(this.logoutSessionAaction);
		this.add(this.uploadSessionAction);

		// first disable part of the option according to the user's permissions
		// flag.
		int accessControlIntValue = this.myEnhancedCollaborativeSessionPermissionsValue
				.getAccessControl().intValue();
		// ignore lgggedin bit in the flag.
		if (accessControlIntValue >= PermissionFlags.LOGGEDIN.intValue()) {
			accessControlIntValue -= PermissionFlags.LOGGEDIN.intValue();
		}

		if (accessControlIntValue < PermissionFlags.ADMINISTRATOR.intValue()) {
			this.disableCollaborativeSessionAction.setEnabled(false);
		}
		if (accessControlIntValue < PermissionFlags.COMMITTER.intValue()) {
			this.commitSessionAction.setEnabled(false);
		}
		if (accessControlIntValue < PermissionFlags.EDITOR.intValue()) {
			this.saveSessionAction.setEnabled(false);
			this.uploadSessionAction.setEnabled(false);
			this.getTokenAction.setEnabled(false);
			this.returnTokenAction.setEnabled(false);
		}

		// second, disable part of the options according to the session status.
		// if no session is currently opened- disable few of the options
		if (this.activeCollaborativeSession == null) {
			this.saveSessionAction.setEnabled(false);
			this.uploadSessionAction.setEnabled(false);
			this.commitSessionAction.setEnabled(false);
			this.getTokenAction.setEnabled(false);
			this.returnTokenAction.setEnabled(false);
			this.logoutSessionAaction.setEnabled(false);
			return;
		}

		// if the current sesion is the open session
		if (currentSessionID.equals(this.activeCollaborativeSession
				.getSessionID())) {
			this.loginSessionAction.setEnabled(false);
			this.disableCollaborativeSessionAction.setEnabled(false);
			if (!(this.isUserTokenHolder(this.myUserID))) {
				this.saveSessionAction.setEnabled(false);
				this.uploadSessionAction.setEnabled(false);
				this.commitSessionAction.setEnabled(false);
				this.returnTokenAction.setEnabled(false);
			} else {
				this.getTokenAction.setEnabled(false);
			}
			return;
		}

		// if the curreent session is not the opened session
		if (!(currentSessionID.equals(this.activeCollaborativeSession
				.getSessionID()))) {
			this.loginSessionAction.setEnabled(false);
			this.saveSessionAction.setEnabled(false);
			this.uploadSessionAction.setEnabled(false);
			this.commitSessionAction.setEnabled(false);
			this.getTokenAction.setEnabled(false);
			this.returnTokenAction.setEnabled(false);
			this.logoutSessionAaction.setEnabled(false);
			return;
		}
	}

	Action getTokenAction = new AbstractAction("Get Token",
			OPCATeamImages.GETTOKEN) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */

		public void actionPerformed(ActionEvent e) {
			// submit request to server -> ask for the token.
			// if the retrun value is true , the user got the token ->update the
			// session details.
			if (CollaborativeSessionPopupMenu.this
					.isUserTokenHolder(CollaborativeSessionPopupMenu.this.myUserID)) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"You are the token holder!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				// if the server is the token holder - the user can request the
				// token,
				// if not - the user has to wait until the token is returned to
				// the server
				if (CollaborativeSessionPopupMenu.this
						.isUserTokenHolder(PermissionFlags.SERVER_USERID)) {
					CollaborativeSessionPopupMenu.this.activeCollaborativeSession
							.getToken();
				} else {
					JOptionPane
							.showMessageDialog(
									Opcat2.getFrame(),
									"You can request the token only when the server holds it.",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};

	Action returnTokenAction = new AbstractAction("Return Token",
			OPCATeamImages.RETURNTOKEN) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2447432784747946007L;

		public void actionPerformed(ActionEvent e) {
			int retValue;

			// The user requests to retrun the token -> check if the token is
			// here at all!
			// if the user is not the token holder ->
			if (!(CollaborativeSessionPopupMenu.this
					.isUserTokenHolder(CollaborativeSessionPopupMenu.this.myUserID))) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"You are not the Token Holder!", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
						"Do you want to save the model on the server?",
						"Exit Session", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				switch (retValue) {
				case JOptionPane.YES_OPTION:
					if (CollaborativeSessionPopupMenu.this.activeCollaborativeSession != null) {
						CollaborativeSessionPopupMenu.this.activeCollaborativeSession
								.saveSession();
					}
					break;

				case JOptionPane.CANCEL_OPTION:
					return;
				}
				// now, return the token back to server
				CollaborativeSessionPopupMenu.this.activeCollaborativeSession
						.returnToken();
			}
		}
	};

	Action saveSessionAction = new AbstractAction("Save Session On The Server",
			OPCATeamImages.SAVE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 3968047113218248101L;

		public void actionPerformed(ActionEvent e) {
			if (CollaborativeSessionPopupMenu.this.activeCollaborativeSession != null) {
				// reset token timer
				CollaborativeSessionPopupMenu.this.myOpcat2
						.getActiveCollaborativeSession().getTokenTimer()
						.setModelSavedFlag(true);
				CollaborativeSessionPopupMenu.this.activeCollaborativeSession
						.saveSession();
			}
		}
	};

	Action uploadSessionAction = new AbstractAction(
			"upload Session To The Server", StandardImages.SAVE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8020189666607703199L;

		public void actionPerformed(ActionEvent e) {
			if (CollaborativeSessionPopupMenu.this.myOpcat2.isProjectOpened()) {
				CollaborativeSessionPopupMenu.this.uploadSession();
			}
		}
	};

	Action loginSessionAction = new AbstractAction("Login Session",
			OPCATeamImages.LOGIN) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7845620003726821498L;

		public void actionPerformed(ActionEvent e) {
			if (CollaborativeSessionPopupMenu.this.myOpcat2.isProjectOpened()) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"Close the current model, then login to this session.",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (CollaborativeSessionPopupMenu.this.activeCollaborativeSession != null) {
				JOptionPane.showMessageDialog(Opcat2.getFrame(),
						"Close the current session,then log into this session",
						"Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			// create activeCollaborativeSession object
			CollaborativeSessionPopupMenu.this.activeCollaborativeSession = new ActiveCollaborativeSession(
					CollaborativeSessionPopupMenu.this.myTeamMember,
					CollaborativeSessionPopupMenu.this.myEnhancedCollaborativeSessionPermissionsValue,
					CollaborativeSessionPopupMenu.this.myOpcat2);
			CollaborativeSessionPopupMenu.this.myOpcat2
					.setActiveCollaborativeSession(CollaborativeSessionPopupMenu.this.activeCollaborativeSession);
			try {
				CollaborativeSessionPopupMenu.this.activeCollaborativeSession
						.loginToSession();
			} catch (Exception exception) {
				CollaborativeSessionPopupMenu.this.myOpcat2
						.setActiveCollaborativeSession(null);
			}
		}
	};

	Action commitSessionAction = new AbstractAction("Commit & Close Session",
			OPCATeamImages.COMMIT) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3819810545597015172L;

		public void actionPerformed(ActionEvent e) {
			CommitConfirmationDialog commitConfirmationDialg = new CommitConfirmationDialog(
					CollaborativeSessionPopupMenu.this.myOpcat2);
			commitConfirmationDialg.setVisible(true) ;
		}
	};

	Action collaborativeSessionDetailsAction = new AbstractAction(
			"Show/Edit Details", OPCATeamImages.DOCUMENT) {

		/**
		 * 
		 */
		private static final long serialVersionUID = -3451294324044283361L;

		public void actionPerformed(ActionEvent e) {
			CollaborativeSessionValueDialog collaborativeSessionValueDialog = new CollaborativeSessionValueDialog(
					CollaborativeSessionPopupMenu.this.myTeamMember,
					CollaborativeSessionPopupMenu.this.myEnhancedCollaborativeSessionPermissionsValue);
			collaborativeSessionValueDialog.setVisible(true);
		}
	};

	Action disableCollaborativeSessionAction = new AbstractAction(
			"Disable Session", StandardImages.DELETE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4653933570916279661L;

		public void actionPerformed(ActionEvent e) {
			int retValue;

			retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"Are you sure you want to disable the session?",
					"Disable Session", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			switch (retValue) {
			case JOptionPane.YES_OPTION:
				try {
					CollaborativeSessionPopupMenu.this.myTeamMember
							.disableCollaborativeSession((CollaborativeSessionPopupMenu.this.myEnhancedCollaborativeSessionPermissionsValue
									.getCollaborativeSessionID()).intValue());
					CollaborativeSessionPopupMenu.this.myOpcat2.getRepository()
							.getAdmin().removeNodeFromControlPanelTree(
									CollaborativeSessionPopupMenu.this.myNode);
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
		private static final long serialVersionUID = -2006295858512790722L;

		public void actionPerformed(ActionEvent e) {
			CollaborativeSessionPermissionsDialog collaborativeSessionPermissionsDialog = new CollaborativeSessionPermissionsDialog(
					CollaborativeSessionPopupMenu.this.myTeamMember,
					CollaborativeSessionPopupMenu.this.myEnhancedCollaborativeSessionPermissionsValue);
			collaborativeSessionPermissionsDialog.setVisible(true) ;
		}
	};

	Action showUsersListAction = new AbstractAction("List Of Users",
			OPCATeamImages.MEMBERSTREE) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7429053008831821797L;

		public void actionPerformed(ActionEvent e) {
			CollaborativeSessionUserListDialog collaborativeSessionUserListDialog = new CollaborativeSessionUserListDialog(
					CollaborativeSessionPopupMenu.this.myTeamMember,
					CollaborativeSessionPopupMenu.this.myEnhancedCollaborativeSessionPermissionsValue
							.getCollaborativeSessionID());
			collaborativeSessionUserListDialog.setVisible(true);
		}
	};

	Action logoutSessionAaction = new AbstractAction("Logout Session",
			OPCATeamImages.LOGOUT) {

		/**
		 * 
		 */
		private static final long serialVersionUID = 5901329129728789025L;

		public void actionPerformed(ActionEvent e) {
			int retValue;
			// if the user holds the token- the client ask him to save before
			// logout,
			// else, the question is unneeded
			if (CollaborativeSessionPopupMenu.this
					.isUserTokenHolder(CollaborativeSessionPopupMenu.this.myUserID)) {
				retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
						"Do you want to save the model on the Server?",
						"Logout Session", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				switch (retValue) {
				case JOptionPane.YES_OPTION:
					if (CollaborativeSessionPopupMenu.this.activeCollaborativeSession != null) {
						CollaborativeSessionPopupMenu.this.activeCollaborativeSession
								.saveSession();
					}
					break;

				case JOptionPane.CANCEL_OPTION:
					return;
				}
				// now, return the token back to server
				CollaborativeSessionPopupMenu.this.activeCollaborativeSession
						.returnToken();
			}

			CollaborativeSessionPopupMenu.this.activeCollaborativeSession
					.logoutFromSession();
			CollaborativeSessionPopupMenu.this.myOpcat2
					.setActiveCollaborativeSession(null);
		}
	};

	private boolean isUserTokenHolder(Integer userID) {
		return (userID.equals(this.activeCollaborativeSession
				.getTokenHolderID()));
	}

	private void uploadSession() {

		Integer currentSessionID = this.myEnhancedCollaborativeSessionPermissionsValue
				.getCollaborativeSessionID();

		// all checks are OK -> covert the file to string and send to the server
		CollaborativeSessionFileValue collaborativeSessionFileValue = new CollaborativeSessionFileValue();
		collaborativeSessionFileValue
				.setCollaborativeSessionID(currentSessionID);
		collaborativeSessionFileValue.setPrimaryKey(currentSessionID);

		try {
			FileConvertor fileConvertor = new FileConvertor();
			String fileSeparator = System.getProperty("file.separator");
			String uploadFileName = "OPCATeam" + fileSeparator + "Upload.opx";

			String finalString = fileConvertor
					.convertFileToString(uploadFileName);
			collaborativeSessionFileValue.setOpmModelFile(finalString);
			this.myTeamMember
					.updateCollaborativeSessionFile(collaborativeSessionFileValue);
		} catch (Exception e) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(e);
			exceptionHandler.logError(e);
		}
	}

}// end of class CollaborativeSessionPopupMenu
