package gui.controls;

import exportedAPI.opcatAPI.ISystem;
import gui.Opcat2;
import gui.actions.edit.MetaColoringAction;
import gui.actions.edit.MetaHideAction;
import gui.dataBase.DataBase;
import gui.dataBase.Versions;
import gui.images.standard.StandardImages;
import gui.metaLibraries.dialogs.LibrariesLoadingWindow;
import gui.metaLibraries.logic.MetaManager;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.opdProject.StateMachine;
import gui.opx.LoadException;
import gui.server.OpcatDB;
import gui.testingScenarios.Scenario;
import gui.util.Configuration;
import gui.util.CustomFileFilter;
import gui.util.LastFileEntry;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.GridPanel;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.undo.UndoManager;

import org.jboss.net.axis.SetClassLoaderHandler;
import org.objectprocess.Client.ChatMessageHandler;
import org.objectprocess.Client.CollaborativeSessionMessageHandler;
import org.objectprocess.Client.TeamMember;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Enable access to file-related operations of Opcat2. The class is a singleton,
 * which means that in order to invoke it and use it's methods, it is sufficient
 * to:
 * <p>
 * <code>
 * FileContorl.getInstance().doSomething();
 * </code><br>
 * 
 * @author Eran Toch
 */
public class FileControl {

    /**
         * The singleton instance.
         */
    private static FileControl instance = null;

    /**
         * A reference to the Opcat instance.
         */
    private Opcat2 myOpcat = null;

    protected GuiControl myGuiControl = GuiControl.getInstance();

    protected EditControl edit = EditControl.getInstance();

    protected boolean duringFileAction = false;

    public final static String FILE_ACTION_PENDING_MSG = "File Operation in Progress";

    /**
         * A reference to the current project.
         */
    private OpdProject currentProject = null;

    /**
         * A string to the path of the last directory in which files were saved
         * or loaded.
         */
    private String lastDirectory = "";

    public final static String fileSeparator = System
	    .getProperty("file.separator");

    /**
         * A private constructor, exist to defy instansiation.
         * 
         */
    private FileControl() {

    }

    /**
         * Returns the single instance of this class.
         * 
         * @return
         */
    public static FileControl getInstance() {
	if (instance == null) {
	    instance = new FileControl();
	}
	return instance;
    }

    /**
         * Sets a reference for Opcat. It is necessary to perform this method
         * <b>before </b> using any other method.
         * 
         * @param opcat
         *                An instance of Opcat2.
         */
    public void setOpcat(Opcat2 opcat) {
	this.myOpcat = opcat;
	this.currentProject = Opcat2.getCurrentProject();
    }

    /**
         * Handles situations when the system was already opened, and a new
         * system should be opened. The method closes the old system, if a
         * system is opened.
         * 
         * @return <code>false</code> if the user cancelled the operation.
         *         <code>true</code> otherwise.
         */
    public boolean handleOpenedSystem() {
	if (this.myOpcat.isProjectOpened()) {
	    return this.closeSystem();
	}

	return true;
    }

    /**
         * Closes a system, allowing the user to save it if there are any
         * changes since it was opnened. The changes are checked by calling
         * <code>myUndoManager.canUndo()</code>. If a certain action does not
         * have a reference in the undo manager, and no other references exist,
         * and the system is closed, then data may be lost.
         * 
         * @return <code>false</code> if the user cancelled the closing
         *         operation.
         * @see #closeCleanUp
         */
    public boolean closeSystem() {
	if (!this.myOpcat.isProjectOpened()) {
	    JOptionPane.showMessageDialog(Opcat2.getFrame(),
		    "No system was opened", "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return true;
	}

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

	// TODO: remember to uncomment this section
	// //***start********HIOTeam*********************
	// if (drawingAreaOnMouseDragAction == "draw") {
	// moveOnDragButton.setSelected(false);
	// selectOnDragButton.setSelected(true);
	// drawOnDragButton.setSelected(false);
	// drawing = null;
	// }
	// //***end********HIOTeam*********************
	//
	// //OPCATEAM
	// //check if the open project is an OPCATeam project- if yes, use
	// another
	// // close procedure
	// if (teamMember != null)
	// if ((teamMember.isConnected())
	// && (getActiveCollaborativeSession() != null)) {
	// JOptionPane.showMessageDialog(
	// myFrame,
	// "Use the close option from the OPCATeam Panel in order to close the
	// session.",
	// "Message",
	// JOptionPane.ERROR_MESSAGE);
	//
	// return true;
	// }
	// //OPCATeam - end

	int retValue;
	// System.out.println("x="+
	// currentProject.getSystemChangeProbability());
	if (!this.canExitWithoutSave()) {
	    retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
		    "Do you want to save the system?", "Close System",
		    JOptionPane.YES_NO_CANCEL_OPTION);

	    switch (retValue) {
	    case JOptionPane.YES_OPTION: {
		this._save(true, false);
		break;
	    }
	    case JOptionPane.CANCEL_OPTION: {
		return false;
	    }
	    }
	}
	// Performs cleaning operations when the project is closed down.
	this.myOpcat.closeCleanUp();

	// Removes the extension tools
	for (int i = 1; i < this.myOpcat.getExtensionToolsPane().getTabCount(); i++) {
	    this.myOpcat.getExtensionToolsPane().remove(i);
	}
	return true;
    }

    /**
         * loads an OPX file and initiates the current project. The method
         * presents The method presents error messages to the user, in case the
         * file was not found or the loading action failed. The method updates
         * the last used file list.
         * 
         * @param fileName
         *                The full path to the file
         * @param showProgressBar
         *                A flag indicating whether to show the progress bar
         *                while loading the file.
         * @see #_loadProject
         */

    public void loadOpxFile(String fileName, boolean showProgressBar,
	    boolean updateLastUsedFiles) {
	loadOpxFile(fileName, showProgressBar);
	if (updateLastUsedFiles) {
	    // Update last used files list.
	    this.updateLastUsedFile();
	}
    }

    public void loadOpxFile(String fileName, boolean showProgressBar) {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	GuiControl gui = GuiControl.getInstance();
	try {
	    gui.getGlassPane().setVisible(true);
	    gui.getGlassPane().start();

	    JProgressBar pBar = null;
	    if (showProgressBar) {
		pBar = new JProgressBar();
		this.myGuiControl.showWaitMessage("Loading System...", pBar);
	    }
	    this.currentProject = this.loadOpxFileIntoProject(fileName, pBar);
	    this.currentProject.setFileName(fileName);

	    this.myOpcat.setProjectWasOpened(true);
	    this.myOpcat.setCurrentProject(this.currentProject);
	    StateMachine.reset();
	    if (currentProject.getPath() != null) {
		Opcat2.getFrame().setTitle(
			"Opcat II - " + currentProject.getName() + " : "
				+ currentProject.getPath());
	    } else {
		Opcat2.getFrame().setTitle(
			"Opcat II - " + currentProject.getName() + " : "
				+ "warning - not saved yet");
	    }

	    GenericTable config = this.currentProject.getConfiguration();
	    int currentSize = ((Integer) config.getProperty("CurrentSize"))
		    .intValue();
	    int normalSize = ((Integer) config.getProperty("NormalSize"))
		    .intValue();

	    this.myOpcat._enableZoomIn(true);
	    this.myOpcat._enableZoomOut(true);

	    if (currentSize == normalSize + 15) {
		this.myOpcat._enableZoomIn(false);
	    }

	    if (currentSize == normalSize - 4) {
		this.myOpcat._enableZoomOut(false);
	    }

	    // Loading ontology by their references
	    // By Eran Toch
	    this.refreshMetaLibraries(this.currentProject.getMetaManager()
		    .getVectorClone());

	    for (Iterator i = currentProject.getScen().values().iterator(); i
		    .hasNext();) {
		Scenario scen = (Scenario) i.next();
		refreshMetaLibraries(scen.getSettings(), scen.getSettings()
			.getVectorClone());
	    }

	    // myOpcat.getMetaWaitScreen().dispose() ;

	    this.myOpcat.getRepository().setProject(this.currentProject);
	    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE_TREE);

	    // clean any empty entries.
	    Opcat2.getCurrentProject().deleteEmptyEntries();
	    Opcat2.getCurrentProject().getCurrentOpd().refit();

	} catch (gui.opx.LoadException le) {
	    OpcatLogger.logError(le);
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(), le
		    .getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	} finally {
	    if (showProgressBar) {
		this.myGuiControl.hideWaitMessage();
	    }
	    StateMachine.reset();
	    StateMachine.setWaiting(false);
	    this.myGuiControl.setCursor4All(Cursor.DEFAULT_CURSOR);
	    gui.getGlassPane().stop();

	}
    }

    /**
         * Loading the project from a given input stream, using the
         * <code>opx</code> library.
         * 
         * @param is
         *                An input stream of the OPM model.
         * @return The OpdProject, initiated from the model
         * @throws gui.opx.LoadException
         *                 If the loading process failed.
         * @see gui.opx.Loader#load
         */
    public OpdProject _loadProject(InputStream is, JProgressBar pBar,
	    String fileName) throws gui.opx.LoadException {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    throw new gui.opx.LoadException(EditControl.CUT_PENDING_MSG);
	}

	gui.opx.Loader ld = new gui.opx.Loader(fileName);
	OpdProject tProject = null;
	try {
	    tProject = ld.load(this.myOpcat.getDesktop(), is, pBar);
	} catch (gui.opx.LoadException ex) {
	    throw ex;
	} finally {
	    duringFileAction = false;
	}
	return tProject;
    }

    /**
         * Loads an external file into a project.
         * 
         * @param fileName
         *                The name of the file
         * @return An OPD project.
         * @throws LoadException
         *                 If the file was not found, if there was an I/O
         *                 problem or if the load failed.
         */
    public OpdProject loadOpxFileIntoProject(String fileName, JProgressBar pBar)
	    throws LoadException {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    throw new gui.opx.LoadException(EditControl.CUT_PENDING_MSG);
	}

	try {
	    File f = new File(fileName);
	    InputStream is;
	    if (fileName.endsWith(".opz")) {
		is = new GZIPInputStream(new FileInputStream(f), 4096);
	    } else {
		is = new BufferedInputStream(new FileInputStream(f), 4096);
	    }
	    OpdProject project = this._loadProject(is, pBar, fileName);
	    is.close();
	    return project;
	} catch (FileNotFoundException e) {
	    throw new LoadException("The file was not found:" + fileName
		    + "\n Please check the name and try again");
	} catch (IOException e) {
	    throw new LoadException(
		    "The file could not be loaded due to an I/O problem:"
			    + fileName
			    + "\n Please check the name and try again");
	} catch (LoadException e) {
	    throw e;
	}
    }

    /**
         * Handles the meta-libraries assigned to this project. The method laods
         * the libraries and handles the changes done to the list in the
         * <code>ProjectPropertiesDialog</code>.
         * 
         * @param metaLibraries
         *                A Vector containing <code>MetaLibrary</code>
         *                objects. If null, the method will return without
         *                carrying out any operation.
         */
    public void refreshMetaLibraries(Vector metaLibraries) {
	if (metaLibraries == null) {
	    return;
	}
	this.currentProject.getMetaManager().setLibrariesVector(metaLibraries);
	JProgressBar pBar = new JProgressBar();
	this.myGuiControl
		.showMetaWaitMessage("Loading meta-libraries...", pBar);
	try {
	    this.currentProject.getMetaManager().refresh(this.currentProject,
		    pBar);
	} finally {
	    this.myGuiControl.hideMetaWaitMessage();
	}
	if (Opcat2.getCurrentProject().getMetaManager().containFailed()) {
	    LibrariesLoadingWindow libsLoad = new LibrariesLoadingWindow(
		    this.currentProject.getMetaManager(), this.currentProject,
		    Opcat2.getFrame());
	    if (libsLoad.showDialog()) {
		this.refreshMetaLibraries(libsLoad.getUpdatedLibs());
	    }
	    /**
                 * JOptionPane.showMessageDialog( Opcat2.getFrame(),
                 * currentProject.getMetaManager().getFailedMessage(), "Opcat2 -
                 * Error", JOptionPane.INFORMATION_MESSAGE);
                 */
	}
    }

    public void refreshMetaLibraries(MetaManager man, Vector metaLibraries) {
	if (metaLibraries == null) {
	    return;
	}
	man.setLibrariesVector(metaLibraries);

	JProgressBar pBar;
	if (myGuiControl.getMetaWaitMessage() != null) {
	    pBar = null;

	} else {
	    pBar = new JProgressBar();
	}

	this.myGuiControl
		.showMetaWaitMessage("Loading meta-libraries...", pBar);
	try {
	    man.refresh(this.currentProject, pBar);
	} finally {
	    this.myGuiControl.hideMetaWaitMessage();
	}

	if (man.containFailed()) {
	    LibrariesLoadingWindow libsLoad = new LibrariesLoadingWindow(man,
		    this.currentProject, Opcat2.getFrame());
	    if (libsLoad.showDialog()) {
		this.refreshMetaLibraries(man, libsLoad.getUpdatedLibs());
	    }

	}

    }

    /**
         * Performs a saving operation. If the file was already saved, or loaded
         * from an existing file, then the method saves the file to it's last
         * location. Otherwise, the user is prompted for a file location. The
         * method updates the last used file list.
         * 
         * @param updateLastUsedFiles -
         *                If true the save is not recorded in the saved file
         *                list and the data is not considered saved by the undo
         *                manager
         * 
         */
    private void _save(boolean updateLastUsedFiles) {

	GridPanel.updateColor(null);
	MetaColoringAction colorLevel = new MetaColoringAction("dummy", null,
		StandardImages.META_COLOR_DEF);
	colorLevel.actionPerformed(null);

	MetaHideAction hide = new MetaHideAction("dummy", null, false,
		StandardImages.System_Icon);
	hide.actionPerformed(null);

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	// clean any empty entries.
	Opcat2.getCurrentProject().deleteEmptyEntries();

	if (this.currentProject.getFileName() == null) {
	    this._saveAs();
	    return;
	}

	StateMachine.setWaiting(true);
	this.myGuiControl.setCursor4All(Cursor.WAIT_CURSOR);

	// start save
	gui.opx.Saver sv = new gui.opx.Saver();
	sv.save(this.currentProject, new JProgressBar(), Opcat2.getFrame());

	//
	// end save

	if (updateLastUsedFiles) {
	    this.updateLastUsedFile();
	    if (Opcat2.getCurrentProject() != null)
		Opcat2.getCurrentProject().setCanClose(true);
	}

	StateMachine.setWaiting(false);
	this.myGuiControl.setCursor4All(Cursor.DEFAULT_CURSOR);
	if (updateLastUsedFiles) {
	    this.myOpcat.getOpcatUndoManager().dataSaved();
	}

	if (currentProject.getPath() != null) {
	    Opcat2.getFrame().setTitle(
		    "Opcat II - " + currentProject.getName() + " : "
			    + currentProject.getPath());
	} else {
	    Opcat2.getFrame().setTitle(
		    "Opcat II - " + currentProject.getName() + " : "
			    + "warning - not saved yet");
	}
    }

    /**
         * Performs a saving operation. If the file was already saved, or loaded
         * from an existing file, then the method saves the file to it's last
         * location. Otherwise, the user is prompted for a file location. The
         * method updates the last used file list.
         * 
         * @param updateLastUsedFiles -
         *                If true the save is not recorded in the saved file
         *                list and the data is not considered saved by the undo
         *                manager
         * @param addNewVersionNumber -
         *                saves a new Version in the OPCAT DB.
         * 
         */
    public void _save(boolean updateLastUsedFiles, boolean addNewVersionNumber) {
	_save(updateLastUsedFiles);
	if (addNewVersionNumber) {
	    Versions.saveNewVersion(this.currentProject);
	}
    }

    /**
         * The method prompts the user for a file location, and then saves the
         * file to that location using the {@link _save} method.
         * 
         */
    public void _saveAs() {
	GridPanel.updateColor(null);
	MetaColoringAction colorLevel = new MetaColoringAction("dummy", null,
		StandardImages.META_COLOR_DEF);
	colorLevel.actionPerformed(null);

	MetaHideAction hide = new MetaHideAction("dummy", null, false,
		StandardImages.System_Icon);
	hide.actionPerformed(null);

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}
	JFileChooser myFileChooser = new JFileChooser();
	myFileChooser.setSelectedFile(new File(this.currentProject.getName()));
	myFileChooser.resetChoosableFileFilters();
	CustomFileFilter opxChooser = new CustomFileFilter("opx",
		"Opcat2 XML Files");
	CustomFileFilter opzChooser = new CustomFileFilter("opz",
		"Opcat2 XML zipped Files");
	if (!this.getLastDirectory().equals("")) {
	    myFileChooser
		    .setCurrentDirectory(new File(this.getLastDirectory()));
	}
	myFileChooser.addChoosableFileFilter(opxChooser);
	myFileChooser.setFileFilter(opzChooser);
	int retVal = myFileChooser.showDialog(Opcat2.getFrame(), "Save System");

	if (retVal != JFileChooser.APPROVE_OPTION) {
	    return;
	}

	String fileName = myFileChooser.getSelectedFile().getPath();
	// Checks if the file already exists
	if (!this.canSaveFile(fileName)) {
	    return;
	}
	this.setLastDirectory(myFileChooser.getSelectedFile().getParent());
	if (myFileChooser.getFileFilter().getDescription().equals(
		opzChooser.getDescription())
		|| fileName.endsWith(".opz")) {
	    if (!fileName.endsWith(".opz")) {
		fileName += ".opz";
	    }
	} else {
	    if (!fileName.endsWith(".opx")) {
		fileName += ".opx";
	    }

	}

	this.currentProject.setFileName(fileName);
	this.currentProject.setPath(fileName);
	this._save(true, false);
	if (currentProject.getPath() != null) {
	    Opcat2.getFrame().setTitle(
		    "Opcat II - " + currentProject.getName() + " : "
			    + currentProject.getPath());
	} else {
	    Opcat2.getFrame().setTitle(
		    "Opcat II - " + currentProject.getName() + " : "
			    + "warning - not saved yet");
	}

    }

    /**
         * Checks if a file with a given path can be saved to the file system.
         * The method checks if the file exists, and if so, asks the user to
         * decide if to overwrite the file or not.
         * 
         * @param fileName
         *                The path to the file.
         * @return <code>true</code> if a file with the fiven name does not
         *         exists or if the user decided to overwrite the file.
         */
    private boolean canSaveFile(String fileName) {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    return false;
	}

	File checkFile = new File(fileName);
	if (checkFile.exists()) {
	    int retVal = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
		    fileName + " already exist.\nOverwrite existing file?",
		    "File Exist", JOptionPane.YES_NO_OPTION,
		    JOptionPane.QUESTION_MESSAGE);
	    if (retVal == JOptionPane.NO_OPTION) {
		return false;
	    }
	}
	return true;
    }

    /**
         * Checks wheather there is a need to save the system before closing it.
         * The method cheks if the undo manager has any changes or the
         * <code>systemChangeProbability</code> variable is set to
         * <code>true</code>.
         * 
         * @return <code>true</code> if it's possible to exit without changes.
         *         <code>false</code> otherwise.
         * @see UndoManager
         * @see #getSystemChangeProbability
         */
    public boolean canExitWithoutSave() {
	if (Opcat2.getCurrentProject() == null)
	    return true;
	return this.myOpcat.getOpcatUndoManager().canCloseProject()
		&& Opcat2.getCurrentProject().isCanClose();
    }

    /**
         * @return Whether a project is currently open. Uses
         *         {@link	Opcat2#isProjectWasOpened()} in order to retrieve an
         *         answer.
         */
    public boolean isProjectOpened() {
	return this.myOpcat.isProjectWasOpened();
    }

    /**
         * Exits gracefully from Opcat, saving unclosed files and performing
         * other cleaning tasks. These tasks include: closing opcaTeam
         * connection, stopping the state machine.
         * 
         */
    public void _exit() {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}
	OpcatDB dataBase = this.myOpcat.getDataBase();
	TeamMember teamMember = this.myOpcat.getTeamMember();
	CollaborativeSessionMessageHandler csMessageHandler = this.myOpcat
		.getCollaborativeSessionMessageHandler();
	ChatMessageHandler chatMessageHandler = this.myOpcat
		.getChatMessageHandler();
	if (!this.isProjectOpened()) {
	    dataBase.closeConnection();
	    // OPCATeam - close connection with JMS, logout from server
	    if (teamMember != null) {
		if (teamMember.isConnected()) {
		    try {
			teamMember.logoutUser();
		    } catch (Exception e) {
		    } finally {
			if (csMessageHandler != null) {
			    csMessageHandler.closeConnection();
			}
			if (chatMessageHandler != null) {
			    chatMessageHandler.closeConnection();
			}
			System.exit(0);
		    }
		}
	    }
	    System.exit(0);
	}

	if (StateMachine.isAnimated()) {
	    this.myOpcat.closeTesting.actionPerformed(new ActionEvent(
		    this.myGuiControl.getFrame(), 0, ""));
	    return;
	}

	// OPCATEAM
	// check if the open project is an OPCATeam project- if yes, use another
	// save procedure
	if (teamMember != null) {
	    if (teamMember.isConnected()) {
		// if a session was opened -> close the file and logout from the
		// session
		if (this.myOpcat.getActiveCollaborativeSession() != null) {
		    Object[] saveOption = new Object[3];
		    saveOption[0] = new String("Save Locally and exit ");
		    saveOption[1] = new String("Don't save and Exit");
		    saveOption[2] = new String("Cancel");
		    Icon icon = null;
		    int retValue;
		    retValue = JOptionPane
			    .showOptionDialog(
				    this.myGuiControl.getFrame(),
				    "If you choose to exit without saving the Model on the Server, your last updates will be lost."
					    + '\n'
					    + "In order to save the Model on the Server you should use the OPCATeam Panel."
					    + '\n', "Exit",
				    JOptionPane.YES_NO_OPTION,
				    JOptionPane.WARNING_MESSAGE, icon,
				    saveOption, saveOption[2]);

		    switch (retValue) {
		    case JOptionPane.YES_OPTION:
			this._save(true, false);
			break;

		    case JOptionPane.NO_OPTION:
			break;

		    case JOptionPane.CANCEL_OPTION:
			return;
		    }

		    // OPCATeam - close session, logout server.
		    this.myOpcat.getActiveCollaborativeSession()
			    .logoutFromSession();
		    this.myOpcat.setActiveCollaborativeSession(null);

		    // now the sesion is closed , close connection with JMS
		    // .
		    dataBase.closeConnection();
		    if (csMessageHandler != null) {
			csMessageHandler.closeConnection();
		    }
		    if (chatMessageHandler != null) {
			chatMessageHandler.closeConnection();
		    }
		    System.exit(0);
		}
	    }
	} // OPCATeam - end

	if (!this.canExitWithoutSave()) {
	    int retValue;
	    retValue = JOptionPane.showConfirmDialog(this.myGuiControl
		    .getFrame(), "Do you want to save the system?", "Exit",
		    JOptionPane.YES_NO_CANCEL_OPTION);

	    switch (retValue) {
	    case JOptionPane.YES_OPTION: {
		this._save(true, false);
		// dataBase.save(currentProject);
		dataBase.closeConnection();
		System.exit(0);
		break;
	    }
	    case JOptionPane.NO_OPTION: {
		dataBase.closeConnection();
		System.exit(0);
		break;
	    }
	    case JOptionPane.CANCEL_OPTION: {
		return;
	    }
	    }
	} else {
	    dataBase.closeConnection();
	    System.exit(0);
	}
    }

    /**
         * Returns the OpcaTeam team member class, which is used for the
         * OpcaTeam server edition.
         * 
         * @return
         */
    public TeamMember getTeamMember() {
	return this.myOpcat.getTeamMember();
    }

    /**
         * Creates a new model project, including displaying a project
         * properties dialog.
         * 
         */
    public void createNewProject() {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	OpdProject tmpProject = new OpdProject(this.myGuiControl.getDesktop(),
		1);
	ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(tmpProject,
		this.myGuiControl.getFrame(), "New System Properties");
	boolean isOK = ppd.showDialog();
	if (isOK) {

	    this.currentProject = new OpdProject(
		    this.myGuiControl.getDesktop(), -1 /* dataBase.getFreeProjectEntry() */
	    );
	    this.currentProject.setName(tmpProject.getName());
	    this.currentProject.setCreator(tmpProject.getCreator());
	    this.currentProject.setCreationDate(tmpProject.getCreationDate());

	    // Copying the general infromation for the project from the
	    // temporary project
	    this.currentProject.getGeneralInfo().setTable(
		    tmpProject.getGeneralInfo().getTable());
	    this.myOpcat.setCurrentProject(this.currentProject);
	    this.myOpcat.setProjectWasOpened(true);

	    this.myOpcat._enableZoomIn(true);
	    this.myOpcat._enableZoomOut(true);

	    // Setting the Opcat license for the project

	    this.currentProject.showRootOpd();
	    this.myOpcat.getRepository().setProject(this.currentProject);

	    // Handling Meta-librares (ontologies) import
	    // by Eran Toch
	    this.refreshMetaLibraries(ppd.getMetaLibraries());
	}
	return;
    }

    public void loadExample(String actionCommand) {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	int retValue;
	if (!this.canExitWithoutSave()) {
	    retValue = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
		    "Do you want to save the system?", "Close System",
		    JOptionPane.YES_NO_CANCEL_OPTION);

	    switch (retValue) {
	    case JOptionPane.YES_OPTION: {
		this._save(true, false);
		break;
	    }
	    case JOptionPane.CANCEL_OPTION: {
		return;
	    }
	    }
	}

	String fileName = getOPCATDirectory() + fileSeparator + "Examples"
		+ fileSeparator + actionCommand + ".opz";

	// Opcat2.setLoadedfilename(System.get)
	this.loadOpxFile(fileName, true);

    }

    /**
         * Opens an OPM project from a legacy Access data base file. The method
         * allows the user to select the database, allow her to choose the
         * prject from the database and loads the project.
         * 
         */
    public void selectDataBase() {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}
	if (this.currentProject != null) {
	    if (!this.handleOpenedSystem()) {
		return;
	    }
	}
	JFileChooser myFileChooser = new JFileChooser();
	myFileChooser.setSelectedFile(new File(""));
	myFileChooser.resetChoosableFileFilters();
	myFileChooser.setFileFilter(new CustomFileFilter("mdb",
		"MS Access Database Files"));
	int returnVal = myFileChooser.showDialog(Opcat2.getFrame(),
		"Select Database File");

	if (returnVal == JFileChooser.CANCEL_OPTION) {
	    return;
	}
	if (returnVal == JFileChooser.APPROVE_OPTION) {
	    String prevDB = this.myOpcat.getDataBase().getCurrentDB();
	    boolean valid = this.myOpcat.getDataBase().setCurrentDB(
		    myFileChooser.getSelectedFile().getPath());

	    Opcat2.getFrame().setCursor(
		    Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    valid = (valid && this.myOpcat.getDataBase().validateCurrentDb());
	    if (!valid) {
		this.myOpcat.getDataBase().setCurrentDB(prevDB);
		Opcat2.getFrame().setCursor(Cursor.getDefaultCursor());
		JOptionPane.showMessageDialog(Opcat2.getFrame(),
			"Illegal database format.\nNot an Opcat2 database.",
			"Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
		return;
	    }

	    // Table.setCurrentDB(myFileChooser.getSelectedFile().getPath());
	    Opcat2.getFrame().setCursor(Cursor.getDefaultCursor());
	}
	if (!this.handleOpenedSystem()) {
	    return;
	}
	this.currentProject = this.myOpcat.getDataBase().load(
		this.myGuiControl.getDesktop());
	if (this.currentProject != null) {
	    this.myOpcat.setProjectWasOpened(true);
	    StateMachine.reset();
	    if (currentProject.getPath() != null) {
		Opcat2.getFrame().setTitle(
			"Opcat II - " + currentProject.getName() + " : "
				+ currentProject.getPath());
	    } else {
		Opcat2.getFrame().setTitle(
			"Opcat II - " + currentProject.getName() + " : "
				+ "warning - not saved yet");
	    }
	    // setProjectActionEnable(true);
	    this.myOpcat.getRepository().setProject(this.currentProject);
	    Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE_TREE);
	}

	return;
    }

    /**
         * Saves the current OPD as an JPG image, to a location choosen by the
         * user. The method uses <code>JPEGEncodeParam</code> in order to
         * perform the actual JPG encoding task. The image is retrieved from
         * <code>currentProject.getCurrentOpd().getImageRepresentation()</code>
         * 
         */
    public void saveAsImage() {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (!this.isProjectOpened()) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    "No OPD is opened", "Message", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	JFileChooser myFileChooser = new JFileChooser();
	myFileChooser.resetChoosableFileFilters();
	myFileChooser.setFileFilter(new CustomFileFilter("jpg", "JPEG Files"));

	// Setting the last directory
	String ld = this.getLastDirectory();
	if (!ld.equals("")) {
	    myFileChooser
		    .setCurrentDirectory(new File(this.getLastDirectory()));
	}
	int returnVal = myFileChooser.showSaveDialog(this.myGuiControl
		.getFrame());

	if (returnVal != JFileChooser.APPROVE_OPTION) {
	    return;
	}

	try {
	    String file = myFileChooser.getSelectedFile().getAbsolutePath();
	    if (!file.trim().toLowerCase().endsWith(".jpg")) {
		file = file.trim() + ".jpg";
	    }
	    if (!this.canSaveFile(file)) {
		return;
	    }
	    BufferedOutputStream out = new BufferedOutputStream(
		    new FileOutputStream(file));
	    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

	    BufferedImage bi = this.currentProject.getCurrentOpd()
		    .getImageRepresentation();

	    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
	    int quality = 100;
	    quality = Math.max(0, Math.min(quality, 100));
	    param.setQuality(quality / 100.0f, false);
	    encoder.setJPEGEncodeParam(param);
	    encoder.encode(bi);
	    out.close();
	} catch (Exception e23) {
	}

    }

    /**
         * Returns the API read-only representation of the current project.
         * 
         * @return
         */
    public ISystem getCurrentISystem() {
	return Opcat2.getCurrentProject();
    }

    /**
         * Returns the current project, after checking if myOpcat is not null.
         * 
         * @return
         */
    public OpdProject getCurrentProject() {
	if (this.myOpcat == null) {
	    OpcatLogger.logError("FileControl was not initiated by Opcat2");
	}
	return Opcat2.getCurrentProject();
    }

    /**
         * @return Returns the isWholeProjectCompiled.
         */
    public boolean isWholeProjectCompiled() {
	return this.myOpcat.isWholeProjectCompiled();
    }

    /**
         * @param isWholeProjectCompiled
         *                The isWholeProjectCompiled to set.
         */
    public void setWholeProjectCompiled(boolean isWholeProjectCompiled) {
	this.myOpcat.setWholeProjectCompiled(isWholeProjectCompiled);
    }

    /**
         * Adds the current project to the list of the last used files. The data
         * is stored in a configuration file, and can be used afterwards.
         */
    public void updateLastUsedFile() {
	Configuration conf = Configuration.getInstance();
	LastFileEntry entry = new LastFileEntry(this.currentProject.getName(),
		this.currentProject.getFileName());
	conf.addFileToLastUsed(entry);
	this.myOpcat.refreshMenuBar();
    }

    /**
         * @return Returns the lastDirectory - the last directory that an opcat
         *         file was opened from or saved to.
         */
    public String getLastDirectory() {
	if (lastDirectory.equalsIgnoreCase("")) {
	    return getOPCATProjectsHOMEDirectory();
	} else {
	    return this.lastDirectory;
	}
    }

    /**
         * @param lastDirectory
         *                The lastDirectory to set.
         */
    public void setLastDirectory(String lastDirectory) {
	this.lastDirectory = lastDirectory;
    }

    /**
         * Reloads the project, by saving it into a temp file and loading it.
         * This method is used only by the import s a patch to solve a graphics
         * bug. The method returns the user to the original OPD that was used by
         * the user before the reload. it will not update the lastused file list
         */

    public void reloadProject() {

	boolean cutPending = this.edit.IsCutPending();
	if (cutPending) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    EditControl.CUT_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}

	if (duringFileAction) {
	    JOptionPane.showMessageDialog(this.myGuiControl.getFrame(),
		    FILE_ACTION_PENDING_MSG, "Message",
		    JOptionPane.ERROR_MESSAGE);
	    return;
	}
	try {
	    File file = File.createTempFile("temp", "opz");
	    String oldFileName = this.currentProject.getFileName();
	    String oldPath = currentProject.getPath();

	    this.currentProject.setFileName(file.getPath());

	    long currentOpdID = this.getCurrentProject().getCurrentOpd()
		    .getOpdId();
	    this._save(false, false);
	    String filePath = new String(this.getCurrentProject().getFileName());
	    this.closeSystem();
	    Opcat2.setLoadedfilename(filePath);
	    this.loadOpxFile(filePath, false, false);
	    this.currentProject.setFileName(oldFileName);
	    currentProject.setPath(oldPath);
	    // this._save(false, false);
	    // if (currentProject.getPath() != null) {
	    // Opcat2.getFrame().setTitle(
	    // "Opcat II - " + currentProject.getName() + " : "
	    // + currentProject.getPath());
	    // } else {
	    Opcat2.getFrame().setTitle(
		    "Opcat II - " + currentProject.getName() + " : "
			    + "warning - not saved after import");
	    // }

	    currentProject.setCanClose(false) ; 
	    Opd originalOpd = this.getCurrentProject().getOpdByID(currentOpdID);
	    this.getCurrentProject().setCurrentOpd(originalOpd);
	    this.getCurrentProject().showOPD(currentOpdID);
	} catch (Exception e) {
	    OpcatLogger.logError(e);
	}
    }

    public String getOPCATBackupDirectory() {
	return System.getenv().get("OPCAT_BACKUP_HOME");
    }

    public String getOPCATDirectory() {
	return System.getenv().get("OPCAT_HOME");
    }

    public String getOPCATColorsDirectory() {
	return System.getenv().get("OPCAT_COLORS_HOME");
    }

    public String getOPCATProjectsHOMEDirectory() {
	return System.getenv().get("OPCAT_PROJECTS_HOME");
    }

    public boolean isDuringFileAction() {
	return duringFileAction;
    }

    public void setDuringFileAction(boolean duringFileAction) {
	this.duringFileAction = duringFileAction;
    }

    public void dbDemo() {

	ResultSet rs = DataBase.getInstance()
		.testDB_getInstances(getCurrentProject(),
			Versions.getVersionID(getCurrentProject()));
	showRS("Instances", rs);

	rs = DataBase.getInstance().testDB_Counting(getCurrentProject(),
		Versions.getVersionID(getCurrentProject()));
	showRS("Counting", rs);

	rs = DataBase.getInstance().testDB_Select(getCurrentProject(),
		Versions.getVersionID(getCurrentProject()));
	showRS("Agent", rs);

    }

    public void showRS(String name, ResultSet rs) {
	GuiControl gui = GuiControl.getInstance();

	gui.getGlassPane().setVisible(true);
	gui.getGlassPane().start();

	if (rs != null) {
	    ArrayList<String> cols = new ArrayList<String>();

	    try {
		for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
		    cols.add(rs.getMetaData().getColumnLabel(i + 1));
		}

		// setup the grid
		GridPanel.RemovePanel(name);
		GridPanel grid = new GridPanel(cols);
		grid.getButtonPane().add(new JLabel(""));
		grid.getButtonPane().add(new JLabel(""));
		grid.getButtonPane().add(new JLabel(""));
		grid.getButtonPane().add(new JLabel(""));
		// /

		Object[] rowTag = null;

		rs.first();
		rs.last();
		if (rs.getRow() > 0) {
		    rs.first();
		    while (!rs.isAfterLast()) {
			Object[] row = new Object[cols.size()];
			for (int i = 0; i < cols.size(); i++) {
			    row[i] = rs.getString(i + 1);
			}
			grid.getGrid().addRow(row, rowTag);
			rs.next();
		    }

		    grid.setTabName(name);
		    grid.AddToExtensionToolsPanel();
		}
	    } catch (Exception ex) {
		OpcatLogger.logError(ex);
	    }
	}
	gui.getGlassPane().stop();
    }
}