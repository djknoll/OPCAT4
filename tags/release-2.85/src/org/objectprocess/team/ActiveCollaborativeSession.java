package org.objectprocess.team;

import gui.Opcat2;

import java.io.File;
import java.io.FileWriter;

import javax.swing.Icon;
import javax.swing.JOptionPane;

import org.objectprocess.Client.TeamMember;
import org.objectprocess.SoapClient.CollaborativeSessionFileValue;
import org.objectprocess.SoapClient.EditableCollaborativeSessionValue;
import org.objectprocess.SoapClient.EditableRevisionValue;
import org.objectprocess.SoapClient.EnhancedCollaborativeSessionPermissionsValue;
import org.objectprocess.SoapClient.FullRevisionValue;
import org.objectprocess.SoapClient.MetaRevisionValue;

public class ActiveCollaborativeSession extends Object {

	protected EnhancedCollaborativeSessionPermissionsValue myECSPV;

	protected Integer sessionID = null;

	protected String sessionName = null;

	protected Integer tokenHolderID = null;

	protected Integer opmModelID = null;

	protected Integer revisionID = null;

	protected TeamMember myTeamMember = null;

	protected Opcat2 myOpcat2 = null;

	protected TokenTimer tokenTimer = null;

	private final static String fileSeparator = System
			.getProperty("file.separator");

	private String clientFileName = "OPCATeam" + fileSeparator + "Client.opx";

	private String serverFileName = "OPCATeam" + fileSeparator + "Server.opx";

	// constractor
	public ActiveCollaborativeSession(
			TeamMember teamMember,
			EnhancedCollaborativeSessionPermissionsValue enhancedCollaborativeSessionPermissionsValue,
			Opcat2 opcat2) {

		this.myECSPV = enhancedCollaborativeSessionPermissionsValue;
		this.sessionID = this.myECSPV.getCollaborativeSessionID();
		this.sessionName = this.myECSPV.getCollaborativeSession()
				.getCollaborativeSessionName();
		this.tokenHolderID = this.myECSPV.getCollaborativeSession().getTokenHolderID();
		this.opmModelID = this.myECSPV.getCollaborativeSession().getOpmModelID();
		this.revisionID = this.myECSPV.getCollaborativeSession().getRevisionID();
		this.myTeamMember = teamMember;
		this.myOpcat2 = opcat2;
		this.tokenTimer = new TokenTimer(this.myOpcat2);

	}

	public void loginToSession() throws Exception {
		try {
			// try to login to server
			this.myTeamMember.sessionLogin(this.sessionID.intValue());
			// open connection on the JMS flatfrom with the session ID.
			if (this.myOpcat2.getCollaborativeSessionMessageHandler() != null) {
				this.myOpcat2.getCollaborativeSessionMessageHandler()
						.openConnection(this.sessionID);
				this.myOpcat2.getChatMessageHandler().openConnection(this.sessionID);
				this.myOpcat2.addChatRoom();
			}
			// load the corresponding file
			this.loadSessionFile();
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
			throw exception;
		}
	}

	private void loadSessionFile() {

		Thread runner = new Thread() {
			public void run() {
				try {
					CollaborativeSessionFileValue collaborativeSessionFileValue = null;
					collaborativeSessionFileValue = ActiveCollaborativeSession.this.myTeamMember
							.fatchCollaborativeSessionFile(ActiveCollaborativeSession.this.sessionID.intValue());

					if (collaborativeSessionFileValue == null) {
						return;
					}

					File outputFile = new File(ActiveCollaborativeSession.this.clientFileName);
					FileWriter out = new FileWriter(outputFile);
					out.write(collaborativeSessionFileValue.getOpmModelFile());
					out.close();
					// load the file to OPCAT
					ActiveCollaborativeSession.this.myOpcat2.loadFileForOPCATeam(ActiveCollaborativeSession.this.clientFileName, ActiveCollaborativeSession.this.sessionName,
							false);

					// set the variable sessionOPenID to the currebt active
					// session ID.
					ActiveCollaborativeSession.this.setSessionID(ActiveCollaborativeSession.this.sessionID);
					// set the name fo the active session on the panel
					ActiveCollaborativeSession.this.myOpcat2.getRepository().getAdmin()
							.updateActiveSessionOnPanel(ActiveCollaborativeSession.this.sessionName);
					// create the session's members tree on the panel.
					ActiveCollaborativeSession.this.myOpcat2.getRepository().getAdmin().createMembersTree(
							ActiveCollaborativeSession.this.sessionID);
					// set the name of the token holder on the panel
					ActiveCollaborativeSession.this.myOpcat2.getRepository().getAdmin()
							.updateTokenHolderOnPanel(ActiveCollaborativeSession.this.tokenHolderID.intValue());
				} catch (Exception exception) {
					ExceptionHandler exceptionHandler = new ExceptionHandler(
							exception);
					exceptionHandler.logError(exception);
				}
			}
		};
		runner.start();
	}

	public void logoutFromSession() {
		try {
			// logout session
			this.myTeamMember.sessionLogout(this.sessionID.intValue());
			// close connection to JMS
			if (this.myOpcat2.getCollaborativeSessionMessageHandler() != null) {
				this.myOpcat2.getCollaborativeSessionMessageHandler()
						.closeConnection();
				this.myOpcat2.getChatMessageHandler().closeConnection();
				this.myOpcat2.removeChatRoom();
			}

			this.myOpcat2.getRepository().getAdmin()
					.updateActiveSessionOnPanel(null);
			this.myOpcat2.getRepository().getAdmin().updateTokenHolderOnPanel("");
			this.myOpcat2.getRepository().getAdmin().removeMembersTree();
			this.myOpcat2.closeFileForOPCATeam();

		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

	public void getToken() {
		try {
			if (this.myTeamMember.requestToken(this.sessionID.intValue())) {
				// The user got the token
				if (this.tokenTimer != null) {
					this.tokenTimer.startTimer(this.myECSPV.getCollaborativeSession()
							.getTokenTimeout().intValue());
				}
			}

		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

	public void returnToken() {
		try {
			this.myTeamMember.returnToken(this.sessionID.intValue());
			if (this.tokenTimer != null) {
				this.tokenTimer.stopTimer();
			}
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

	public void commitActiveCollaborativeSession(String commitDescription,
			boolean IcreaseMajor) {
		// first, check that all members are logged out- this can be done
		// through the
		// myTeamMember interface which mantains online the list of session
		// members for the active sesion.
		if (this.myTeamMember.numOfLoggedInMembers() > 1) {
			// the session has more then one users logged into - ask the user if
			// he wish to continue.
			int retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
					"Active members are logged in the session." + "\n"
							+ "Proceed with the commit process?",
					"Commit Session", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			switch (retValue) {
			case JOptionPane.OK_OPTION:
				break;

			case JOptionPane.CANCEL_OPTION:
				return;
			}
		}

		try {
			// execute preCommit request to the server to see if there is a
			// conflict problem.
			Object[] metaRevisionsList = null;
			if (this.revisionID != null) {
				metaRevisionsList = this.myTeamMember
						.preCommitCollaborativeSession(this.revisionID.intValue());
			}

			// if the resposne is null-> no conflict occurs.in such case the
			// session file has to be transferred to the server, and a new
			// revision has to be created.
			if ((this.revisionID == null)
					|| ((metaRevisionsList == null) || (metaRevisionsList.length == 0))) {
				EditableRevisionValue editableRevisionValue = new EditableRevisionValue();
				editableRevisionValue.setComitterID(this.tokenHolderID);
				editableRevisionValue.setDescription(commitDescription);
				editableRevisionValue.setMajorRevision(null);
				editableRevisionValue.setMinorRevision(null);
				editableRevisionValue.setOpmModelID(this.opmModelID);
				// convert the file into string
				this.myOpcat2.saveFileForOPCATeam();
				FileConvertor fileConvertor = new FileConvertor();
				String finalString = fileConvertor
						.convertFileToString(this.clientFileName);
				editableRevisionValue.setOpmModelFile(finalString);
				// finally, send the commit requesto the server
				EditableRevisionValue returnedEditableRevisionValue = this.myTeamMember
						.commitCollaborativeSession(this.sessionID.intValue(),
								editableRevisionValue, IcreaseMajor);

				if (returnedEditableRevisionValue != null) {
					// the commit action terminated succesfully
					JOptionPane
							.showMessageDialog(
									Opcat2.getFrame(),
									"The commit action successfully terminated."
											+ "\n"
											+ "A new revision was created on the server. "
											+ "\n"
											+ "The new revision number is: "
											+ returnedEditableRevisionValue
													.getMajorRevision()
											+ "."
											+ returnedEditableRevisionValue
													.getMinorRevision() + "\n"
											+ "The session will be closed now.",
									"Message", JOptionPane.INFORMATION_MESSAGE);
					this.logoutFromSession();
					this.myOpcat2.getRepository().refreshOPCATeamControlPanel();
					this.myOpcat2.setActiveCollaborativeSession(null);
				}
			}
			// the next section deals with conflicts.
			else {

				Object[] conflictOption = new Object[3];
				conflictOption[0] = new String("Merge");
				conflictOption[1] = new String("Ignore");
				conflictOption[2] = new String("Cancel");
				Icon icon = null;
				int retValue;
				retValue = JOptionPane
						.showOptionDialog(
								Opcat2.getFrame(),
								"The server detected a conflict."
										+ "\n"
										+ "Choose one of the following options:"
										+ "\n"
										+ "Merge with last revision, Ignore and commit the model, or Cancel."
										+ "\n",
								"Conflict detected by the Server",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, icon,
								conflictOption, conflictOption[0]);

				switch (retValue) {
				// merge option
				case 0:
					// find out what is the highest revision number
					int index = this.myTeamMember
							.findHigestRevision(metaRevisionsList);
					if (index == -1) {
						break;
					}

					// Integer majorRevision = ( (MetaRevisionValue)
					// (metaRevisionsList[index])).getMajorRevision();
					// Integer minorRevision = ( (MetaRevisionValue)
					// (metaRevisionsList[index])).getMinorRevision();
					this.revisionID = ((MetaRevisionValue) (metaRevisionsList[index]))
							.getRevisionID();

					if (this.revisionID == null) {
						break;
					}

					// bring the coresponding revision for the merge operation
					FullRevisionValue fullRevisionValue = this.myTeamMember
							.fatchOpmModelFile(this.revisionID.intValue());

					if (fullRevisionValue == null) {
						break;
					}

					// convert the string that came from the server to
					// server.opx file
					File outputFile = new File(this.serverFileName);
					FileWriter out = new FileWriter(outputFile);
					out.write(fullRevisionValue.getOpmModelFile());
					out.close();

					// before calling merger - save the client project.
					this.myOpcat2.saveFileForOPCATeam();
					// call the merger
					(new XmlMerger(this.myOpcat2)).mergeProject(Opcat2
							.getCurrentProject(), this.serverFileName);

					// ask the user to decide if he wants to save the new
					// session or rollback

					// Object[] saveOrRollBackOption = new Object[2];
					conflictOption[0] = new String("Save");
					conflictOption[1] = new String("Roll Back");

					retValue = JOptionPane
							.showOptionDialog(
									Opcat2.getFrame(),
									"Merge operation successfuly terminated."
											+ "\n"
											+ "Choose one of the following options:"
											+ "\n"
											+ "Save Merged session on the server, or Roll Back."
											+ "\n", "Merge or Rollback",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, icon,
									conflictOption, conflictOption[0]);

					switch (retValue) {
					// save option
					case 0:
						// update the new session details on the server
						EditableCollaborativeSessionValue editableCollaborativeSessionValue = new EditableCollaborativeSessionValue();
						editableCollaborativeSessionValue
								.setCollaborativeSessionName(this.myECSPV
										.getCollaborativeSession()
										.getCollaborativeSessionName());
						editableCollaborativeSessionValue
								.setDescription(this.myECSPV
										.getCollaborativeSession()
										.getDescription());
						editableCollaborativeSessionValue
								.setTokenTimeout(this.myECSPV
										.getCollaborativeSession()
										.getTokenTimeout());
						editableCollaborativeSessionValue
								.setUserTimeout(this.myECSPV
										.getCollaborativeSession()
										.getUserTimeout());
						editableCollaborativeSessionValue
								.setCollaborativeSessionID(this.myECSPV
										.getCollaborativeSessionID());
						editableCollaborativeSessionValue.setPrimaryKey(this.myECSPV
								.getCollaborativeSessionID());
						editableCollaborativeSessionValue.setOpmModelID(this.myECSPV
								.getCollaborativeSession().getOpmModelID());
						editableCollaborativeSessionValue
								.setMajorRevision(fullRevisionValue
										.getMajorRevision());
						editableCollaborativeSessionValue
								.setMinorRevision(fullRevisionValue
										.getMinorRevision());
						editableCollaborativeSessionValue
								.setRevisionID(fullRevisionValue
										.getRevisionID());

						try {
							this.myTeamMember
									.updateCollaborativeSession(editableCollaborativeSessionValue);
							this.saveSession();
						} catch (Exception exception) {
							ExceptionHandler exceptionHandler = new ExceptionHandler(
									exception);
							exceptionHandler.logError(exception);
						}
						return;

					case 1:
						// rollback
						this.myOpcat2.closeFileForOPCATeam();
						this.myOpcat2.loadFileForOPCATeam(this.clientFileName,
								this.sessionName, false);
						return;
					}

				case 1:
					// ignore option-> commit the current model without any
					// merging action.
					EditableRevisionValue editableRevisionValue = new EditableRevisionValue();
					editableRevisionValue.setComitterID(this.tokenHolderID);
					editableRevisionValue.setDescription(commitDescription);
					editableRevisionValue.setMajorRevision(null);
					editableRevisionValue.setMinorRevision(null);
					editableRevisionValue.setOpmModelID(this.opmModelID);
					// convert the file into string
					this.myOpcat2.saveFileForOPCATeam();
					FileConvertor fileConvertor = new FileConvertor();
					String finalString = fileConvertor
							.convertFileToString(this.clientFileName);
					editableRevisionValue.setOpmModelFile(finalString);
					// finally, send the commit requesto the server
					EditableRevisionValue returnedEditableRevisionValue = this.myTeamMember
							.commitCollaborativeSession(this.sessionID.intValue(),
									editableRevisionValue, IcreaseMajor);

					if (returnedEditableRevisionValue != null) {
						// the commit action terminated succesfully
						JOptionPane
								.showMessageDialog(
										Opcat2.getFrame(),
										"The commit action successfully terminated."
												+ "\n"
												+ "A new revision was created on the server. "
												+ "\n"
												+ "The new revision number is: "
												+ returnedEditableRevisionValue
														.getMajorRevision()
												+ "."
												+ returnedEditableRevisionValue
														.getMinorRevision()
												+ "\n"
												+ "The session will be closed now.",
										"Message",
										JOptionPane.INFORMATION_MESSAGE);
						this.logoutFromSession();
						this.myOpcat2.getRepository().refreshOPCATeamControlPanel();
						this.myOpcat2.setActiveCollaborativeSession(null);
					}
					return;

				case 2:
					// the user changed his mind, and canceled the operation.
					return;
				}
				//
			}
		} catch (Exception exception) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(exception);
			exceptionHandler.logError(exception);
		}
	}

	public void saveSession() {

		Integer currentSessionID = this.myECSPV.getCollaborativeSessionID();

		// check that the save command is used for the open session
		if (!(currentSessionID.equals(this.getSessionID()))) {
			JOptionPane.showMessageDialog(Opcat2.getFrame(),
					"You can perform save action only on the open Session",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// all checks are OK -> covert the file to string and send to the server
		CollaborativeSessionFileValue collaborativeSessionFileValue = new CollaborativeSessionFileValue();
		collaborativeSessionFileValue
				.setCollaborativeSessionID(currentSessionID);
		collaborativeSessionFileValue.setPrimaryKey(currentSessionID);

		try {
			this.myOpcat2.saveFileForOPCATeam();
			FileConvertor fileConvertor = new FileConvertor();
			String finalString = fileConvertor
					.convertFileToString(this.clientFileName);
			collaborativeSessionFileValue.setOpmModelFile(finalString);
			this.myTeamMember
					.updateCollaborativeSessionFile(collaborativeSessionFileValue);
		} catch (Exception e) {
			ExceptionHandler exceptionHandler = new ExceptionHandler(e);
			exceptionHandler.logError(e);
		}
	}

	public void setSessionID(Integer ID) {
		this.sessionID = ID;
	}

	public Integer getSessionID() {
		return this.sessionID;
	}

	public void setTokenHolderID(Integer ID) {
		this.tokenHolderID = ID;
	}

	public Integer getTokenHolderID() {
		return this.tokenHolderID;
	}

	public TokenTimer getTokenTimer() {
		return this.tokenTimer;
	}
}
