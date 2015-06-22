package gui.actions.controls;

import exportedAPI.opcatAPI.ISystem;
import gui.Opcat2;
import gui.metaLibraries.dialogs.LibrariesLoadingWindow;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.opdProject.StateMachine;
import gui.opx.LoadException;
import gui.server.OpcatDB;
import gui.util.Configuration;
import gui.util.CustomFileFilter;
import gui.util.LastFileEntry;
import gui.util.OpcatLogger;

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
import java.util.Vector;
import java.util.zip.GZIPInputStream;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.undo.UndoManager;

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

    /**
     * A reference to the current project.
     */
    private OpdProject currentProject = null;

    /**
     * A string to the path of the last directory in which files were saved or
     * loaded.
     */
    private String lastDirectory = "";

    private final static String fileSeparator = System
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
     *            An instance of Opcat2.
     */
    public void setOpcat(Opcat2 opcat) {
        this.myOpcat = opcat;
        currentProject = myOpcat.getCurrentProject();
    }

    /**
     * Handles situations when the system was already opened, and a new system
     * should be opened. The method closes the old system, if a system is
     * opened.
     * 
     * @return <code>false</code> if the user cancelled the operation.
     *         <code>true</code> otherwise.
     */
    public boolean handleOpenedSystem() {
        if (myOpcat.isProjectOpened()) {
            return closeSystem();
        }
        return true;
    }

    /**
     * Closes a system, allowing the user to save it if there are any changes
     * since it was opnened. The changes are checked by calling
     * <code>myUndoManager.canUndo()</code>. If a certain action does not
     * have a reference in the undo manager, and no other references exist, and
     * the system is closed, then data may be lost.
     * 
     * @return <code>false</code> if the user cancelled the closing operation.
     * @see #closeCleanUp
     */
    public boolean closeSystem() {
        if (!myOpcat.isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(Opcat2.getFrame(), "No system was opened", "Message", JOptionPane.ERROR_MESSAGE);
            return true;
        }

        //TODO: remember to uncomment this section
        //        //***start********HIOTeam*********************
        //		if (drawingAreaOnMouseDragAction == "draw") {
        //			moveOnDragButton.setSelected(false);
        //			selectOnDragButton.setSelected(true);
        //			drawOnDragButton.setSelected(false);
        //			drawing = null;
        //		}
        //		//***end********HIOTeam*********************
        //
        //		//OPCATEAM
        //		//check if the open project is an OPCATeam project- if yes, use
        // another
        //		// close procedure
        //		if (teamMember != null)
        //			if ((teamMember.isConnected())
        //				&& (getActiveCollaborativeSession() != null)) {
        //				JOptionPane.showMessageDialog(
        //					myFrame,
        //					"Use the close option from the OPCATeam Panel in order to close the
        // session.",
        //					"Message",
        //					JOptionPane.ERROR_MESSAGE);
        //
        //				return true;
        //			}
        //		//OPCATeam - end

        int retValue;
        //	  System.out.println("x="+
        // currentProject.getSystemChangeProbability());
        if (!canExitWithoutSave()) {
            retValue = JOptionPane
                    .showConfirmDialog(Opcat2.getFrame(), "Do you want to save the system?", "Close System", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (retValue) {
            case JOptionPane.YES_OPTION: {
                _save();
                break;
            }
            case JOptionPane.CANCEL_OPTION: {
                return false;
            }
            }
        }
        //Performs cleaning operations when the project is closed down.
        myOpcat.closeCleanUp();

        //Removes the extension tools
        for (int i = 1; i < myOpcat.getExtensionToolsPane().getTabCount(); i++) {
            myOpcat.getExtensionToolsPane().remove(i);
        }
        return true;
    }

    /**
     * loads an OPX file and initiates the current project. The method presents
     * The method presents error messages to the user, in case the file was
     * not found or the loading action failed. The method
     * updates the last used file list. 
     * @param fileName	The full path to the file
     * @param	showProgressBar	A flag indicating whether to show the progress 
     * bar while loading the file.  
     * @see #_loadProject
     */
    public void loadOpxFile(String fileName, boolean showProgressBar) {
        InputStream is;
        try {
            JProgressBar pBar = null;
            if (showProgressBar)	{
	            pBar = new JProgressBar();
	            myGuiControl.showWaitMessage("Loading System...", pBar);
            }
            currentProject = loadOpxFileIntoProject(fileName, pBar);
            currentProject.setFileName(fileName);

            myOpcat.setProjectWasOpened(true);
            myOpcat.setCurrentProject(currentProject);
            StateMachine.reset();
            Opcat2.getFrame()
                    .setTitle("Opcat II - " + currentProject.getName());

            GenericTable config = currentProject.getConfiguration();
            int currentSize = ((Integer) config.getProperty("CurrentSize"))
                    .intValue();
            int normalSize = ((Integer) config.getProperty("NormalSize"))
                    .intValue();

            myOpcat._enableZoomIn(true);
            myOpcat._enableZoomOut(true);

            if (currentSize == normalSize + 2) {
                myOpcat._enableZoomIn(false);
            }

            if (currentSize == normalSize - 2) {
                myOpcat._enableZoomOut(false);
            }

            //Loading ontology by their references
            //By Eran Toch
            refreshMetaLibraries(currentProject.getMetaManager()
                    .getVectorClone());

            myOpcat.getRepository().setProject(currentProject);
            Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

            //Update last used files list.
            updateLastUsedFile();

        } catch (gui.opx.LoadException le) {
            OpcatLogger.logError(le);
            JOptionPane.showMessageDialog(myGuiControl.getFrame(), le
                    .getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }  finally {
            if (showProgressBar)	{
                myGuiControl.hideWaitMessage();
            }
            StateMachine.reset();
            StateMachine.setWaiting(false);
            myGuiControl.setCursor4All(Cursor.DEFAULT_CURSOR);

        }
    }

    /**
     * Loading the project from a given input stream, using the <code>opx</code>
     * library. 
     * @param is	An input stream of the OPM model. 
     * @return	The OpdProject, initiated from the model
     * @throws gui.opx.LoadException	If the loading process failed.
     * @see	gui.opx.Loader#load
     */
    public OpdProject _loadProject(InputStream is, JProgressBar pBar)
            throws gui.opx.LoadException {
        gui.opx.Loader ld = new gui.opx.Loader();
        OpdProject tProject = null;
        try {
            tProject = ld.load(myOpcat.getDesktop(), is, pBar);
        } catch (gui.opx.LoadException ex) {
            throw ex;
        } finally {
            
        }
        return tProject;
    }
    
    /**
     * Loads an external file into a project. 
     * @param fileName		The name of the file
     * @return				An OPD project. 
     * @throws LoadException	If the file was not found, if there was an I/O problem
     * or if the load failed.
     */
    public OpdProject loadOpxFileIntoProject(String fileName, JProgressBar pBar) throws LoadException {
        
        try {
            File f = new File(fileName);
            InputStream is;
            if (fileName.endsWith(".opz")) {
                is = new GZIPInputStream(new FileInputStream(f), 4096);
            } else {
                is = new BufferedInputStream(new FileInputStream(f), 4096);
            }
            
            OpdProject project = _loadProject(is, pBar);
            is.close();
            return project;
        } catch (FileNotFoundException e) {
            throw new LoadException("The file was not found:"+ fileName +"\n Please check the name and try again");
        } catch (IOException e) {
            throw new LoadException("The file could not be loaded due to an I/O problem:"+ fileName +"\n Please check the name and try again");
        } catch (LoadException e) {
            throw e;
        }
    }

    /**
     * Handles the meta-libraries assigned to this project. The method laods the
     * libraries and handles the changes done to the list in the
     * <code>ProjectPropertiesDialog</code>.
     * 
     * @param metaLibraries
     *            A Vector containing <code>MetaLibrary</code> objects. If
     *            null, the method will return without carrying out any
     *            operation.
     */
    public void refreshMetaLibraries(Vector metaLibraries) {
        if (metaLibraries == null) {
            return;
        }
        currentProject.getMetaManager().setLibrariesVector(metaLibraries);
        JProgressBar pBar = new JProgressBar();
        myGuiControl.showMetaWaitMessage("Loading meta-libraries...", pBar);
        try {
            currentProject.getMetaManager().refresh(currentProject, pBar);
        } finally {
            myGuiControl.hideMetaWaitMessage();
        }
        if (myOpcat.getCurrentProject().getMetaManager().containFailed()) {
            LibrariesLoadingWindow libsLoad = new LibrariesLoadingWindow(
                    currentProject.getMetaManager(), currentProject, Opcat2
                            .getFrame());
            if (libsLoad.showDialog()) {
                refreshMetaLibraries(libsLoad.getUpdatedLibs());
            }
            /**
             * JOptionPane.showMessageDialog( Opcat2.getFrame(),
             * currentProject.getMetaManager().getFailedMessage(), "Opcat2 -
             * Error", JOptionPane.INFORMATION_MESSAGE);
             */
        }
    }

    /**
     * Performs a saving operation. If the file was already saved, or loaded 
     * from an existing file, then the method saves the file to it's last
     * location. Otherwise, the user is prompted for a file location. The method
     * updates the last used file list. 
     *
     */
    public void _save() {

        if (currentProject.getFileName() == null) {
            _saveAs();
            return;
        }

        StateMachine.setWaiting(true);
        myGuiControl.setCursor4All(Cursor.WAIT_CURSOR);

        //start save
        gui.opx.Saver sv = new gui.opx.Saver();
        sv.save(currentProject, new JProgressBar(), Opcat2.getFrame());
        //end save
        updateLastUsedFile();

        StateMachine.setWaiting(false);
        myGuiControl.setCursor4All(Cursor.DEFAULT_CURSOR);
        myOpcat.getOpcatUndoManager().dataSaved();

    }

    /**
     * The method prompts the user for a file location, and then saves the
     * file to that location using the {@link _save} method.
     *
     */
    public void _saveAs() {
        JFileChooser myFileChooser = new JFileChooser();
        myFileChooser.setSelectedFile(new File(currentProject.getName()));
        myFileChooser.resetChoosableFileFilters();
        CustomFileFilter opxChooser = new CustomFileFilter("opx",
                "Opcat2 XML Files");
        CustomFileFilter opzChooser = new CustomFileFilter("opz",
                "Opcat2 XML zipped Files");
        if (!getLastDirectory().equals("")) {
            myFileChooser.setCurrentDirectory(new File(getLastDirectory()));
        }
        myFileChooser.addChoosableFileFilter(opxChooser);
        myFileChooser.setFileFilter(opzChooser);
        int retVal = myFileChooser.showDialog(Opcat2.getFrame(), "Save System");

        if (retVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String fileName = myFileChooser.getSelectedFile().getPath();
        //Checks if the file already exists
        if (!canSaveFile(fileName)) {
            return;
        }
        setLastDirectory(myFileChooser.getSelectedFile().getParent());
        if (myFileChooser.getFileFilter().getDescription().equals(opzChooser
                .getDescription())
                || fileName.endsWith(".opz")) {
            if (!fileName.endsWith(".opz")) {
                fileName += ".opz";
            }
        } else {
            if (!fileName.endsWith(".opx")) {
                fileName += ".opx";
            }

        }

        currentProject.setFileName(fileName);
        _save();
    }

    /**
     * Checks if a file with a given path can be saved to the file system. The
     * method checks if the file exists, and if so, asks the user to decide if
     * to overwrite the file or not.
     * 
     * @param fileName
     *            The path to the file.
     * @return <code>true</code> if a file with the fiven name does not exists
     *         or if the user decided to overwrite the file.
     */
    private boolean canSaveFile(String fileName) {
        File checkFile = new File(fileName);
        if (checkFile.exists()) {
            int retVal = JOptionPane
                    .showConfirmDialog(Opcat2.getFrame(), fileName
                            + " already exist.\nOverwrite existing file?", "File Exist", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (retVal == JOptionPane.NO_OPTION) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks wheather there is a need to save the system before closing it. The
     * method cheks if the undo manager has any changes or the
     * <code>systemChangeProbability</code> variable is set to
     * <code>true</code>.
     * 
     * @return <code>true</code> if it's possible to exit without changes.
     *         <code>false</code> otherwise.
     * @see UndoManager
     * @see #getSystemChangeProbability
     */
    public boolean canExitWithoutSave() {
        return myOpcat.getOpcatUndoManager().canCloseProject();
    }

    /**
     * @return Whether a project is currently open. Uses {@link	Opcat2#isProjectWasOpened()}
     * in order to retrieve an answer.
     */
    public boolean isProjectOpened() {
        return myOpcat.isProjectWasOpened();
    }

    /**
     * Exits gracefully from Opcat, saving unclosed files and performing other
     * cleaning tasks. These tasks include: closing opcaTeam connection, stopping
     * the state machine. 
     *
     */
    public void _exit() {
        OpcatDB dataBase = myOpcat.getDataBase();
        TeamMember teamMember = myOpcat.getTeamMember();
        CollaborativeSessionMessageHandler csMessageHandler = myOpcat
                .getCollaborativeSessionMessageHandler();
        ChatMessageHandler chatMessageHandler = myOpcat.getChatMessageHandler();
        if (!isProjectOpened()) {
            dataBase.closeConnection();
            //OPCATeam - close connection with JMS, logout from server
            if (teamMember != null)
                if (teamMember.isConnected())
                    try {
                        teamMember.logoutUser();
                    } catch (Exception e) {
                    } finally {
                        if (csMessageHandler != null)
                            csMessageHandler.closeConnection();
                        if (chatMessageHandler != null)
                            chatMessageHandler.closeConnection();
                        System.exit(0);
                    }
            System.exit(0);
        }

        if (StateMachine.isAnimated()) {
            myOpcat.closeAnimation.actionPerformed(new ActionEvent(myGuiControl
                    .getFrame(), 0, ""));
            return;
        }

        //		OPCATEAM
        //check if the open project is an OPCATeam project- if yes, use another
        // save procedure
        if (teamMember != null) {
            if (teamMember.isConnected()) {
                // if a session was opened -> close the file and logout from the
                // session
                if (myOpcat.getActiveCollaborativeSession() != null) {
                    Object[] saveOption = new Object[3];
                    saveOption[0] = new String("Save Locally and exit ");
                    saveOption[1] = new String("Don't save and Exit");
                    saveOption[2] = new String("Cancel");
                    Icon icon = null;
                    int retValue;
                    retValue = JOptionPane
                            .showOptionDialog(myGuiControl.getFrame(), "If you choose to exit without saving the Model on the Server, your last updates will be lost."
                                    + '\n'
                                    + "In order to save the Model on the Server you should use the OPCATeam Panel."
                                    + '\n', "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon, saveOption, saveOption[2]);

                    switch (retValue) {
                    case JOptionPane.YES_OPTION:
                        _save();
                        break;

                    case JOptionPane.NO_OPTION:
                        break;

                    case JOptionPane.CANCEL_OPTION:
                        return;
                    }

                    //OPCATeam - close session, logout server.
                    myOpcat.getActiveCollaborativeSession().logoutFromSession();
                    myOpcat.setActiveCollaborativeSession(null);

                    //now the sesion is closed , close connection with JMS .
                    dataBase.closeConnection();
                    if (csMessageHandler != null)
                        csMessageHandler.closeConnection();
                    if (chatMessageHandler != null)
                        chatMessageHandler.closeConnection();
                    System.exit(0);
                }
            }
        } //OPCATeam - end

        if (!canExitWithoutSave()) {
            int retValue;
            retValue = JOptionPane
                    .showConfirmDialog(myGuiControl.getFrame(), "Do you want to save the system?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);

            switch (retValue) {
            case JOptionPane.YES_OPTION: {
                _save();
                //                dataBase.save(currentProject);
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
     * Returns the OpcaTeam team member class, which is used for the OpcaTeam
     * server edition. 
     * @return
     */
    public TeamMember getTeamMember() {
        return myOpcat.getTeamMember();
    }

    /**
     * Creates a new model project, including displaying a project properties
     * dialog. 
     *
     */
    public void createNewProject() {
        OpdProject tmpProject = new OpdProject(myGuiControl.getDesktop(), 1);
        ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(tmpProject,
                myGuiControl.getFrame(), "New System Properties");
        boolean isOK = ppd.showDialog();
        if (isOK) {

            currentProject = new OpdProject(myGuiControl.getDesktop(), -1 /* dataBase.getFreeProjectEntry() */
            );
            currentProject.setName(tmpProject.getName());
            currentProject.setCreator(tmpProject.getCreator());
            currentProject.setCreationDate(tmpProject.getCreationDate());

            //Copying the general infromation for the project from the
            // temporary project
            currentProject.getGeneralInfo().setTable(tmpProject
                    .getGeneralInfo().getTable());
            myOpcat.setCurrentProject(currentProject);
            myOpcat.setProjectWasOpened(true);

            myOpcat._enableZoomIn(true);
            myOpcat._enableZoomOut(true);

            //Setting the Opcat license for the project

            currentProject.showRootOpd();
            myOpcat.getRepository().setProject(currentProject);

            // Handling Meta-librares (ontologies) import
            // by Eran Toch
            refreshMetaLibraries(ppd.getMetaLibraries());
        }
        return;
    }


    public void loadExample(String actionCommand) {
        String fileName = "examples" + fileSeparator + actionCommand + ".opz";

        loadOpxFile(fileName, true);

    }

    /**
     * Opens an OPM project from a legacy Access data base file. The method
     * allows the user to select the database, allow her to choose the prject
     * from the database and loads the project. 
     *
     */
    public void selectDataBase() {
        if (currentProject != null) {
            if (!handleOpenedSystem()) {
                return;
            }
        }
        JFileChooser myFileChooser = new JFileChooser();
        myFileChooser.setSelectedFile(new File(""));
        myFileChooser.resetChoosableFileFilters();
        myFileChooser.setFileFilter(new CustomFileFilter("mdb",
                "MS Access Database Files"));
        int returnVal = myFileChooser
                .showDialog(Opcat2.getFrame(), "Select Database File");

        if (returnVal == JFileChooser.CANCEL_OPTION) {
            return;
        }
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String prevDB = myOpcat.getDataBase().getCurrentDB();
            boolean valid = myOpcat.getDataBase().setCurrentDB(myFileChooser
                    .getSelectedFile().getPath());

            Opcat2.getFrame().setCursor(Cursor
                    .getPredefinedCursor(Cursor.WAIT_CURSOR));
            valid = (valid && myOpcat.getDataBase().validateCurrentDb());
            if (!valid) {
                myOpcat.getDataBase().setCurrentDB(prevDB);
                Opcat2.getFrame().setCursor(Cursor.getDefaultCursor());
                JOptionPane
                        .showMessageDialog(Opcat2.getFrame(), "Illegal database format.\nNot an Opcat2 database.", "Opcat2 - Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Table.setCurrentDB(myFileChooser.getSelectedFile().getPath());
            Opcat2.getFrame().setCursor(Cursor.getDefaultCursor());
        }
        if (!handleOpenedSystem()) {
            return;
        }
        currentProject = myOpcat.getDataBase().load(myGuiControl.getDesktop());
        if (currentProject != null) {
            myOpcat.setProjectWasOpened(true);
            StateMachine.reset();
            Opcat2.getFrame()
                    .setTitle("Opcat II - " + currentProject.getName());
            //setProjectActionEnable(true);
            myOpcat.getRepository().setProject(currentProject);
            Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);
        }

        return;
    }

    /**
     * Saves the current OPD as an JPG image, to a location choosen by the 
     * user. The method uses <code>JPEGEncodeParam</code> in order to perform
     * the actual JPG encoding task. The image is retrieved from 
     * <code>currentProject.getCurrentOpd().getImageRepresentation()</code>
     *
     */
    public void saveAsImage() {
        Opd currOpd = currentProject.getCurrentOpd();

        if (!isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(myGuiControl.getFrame(), "No OPD is opened", "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JFileChooser myFileChooser = new JFileChooser();
        myFileChooser.resetChoosableFileFilters();
        myFileChooser.setFileFilter(new CustomFileFilter("jpg", "JPEG Files"));
		
        //Setting the last directory
		String ld = getLastDirectory();
		if (!ld.equals("")) {
			myFileChooser.setCurrentDirectory(new File(
			        getLastDirectory()));
		}
        int returnVal = myFileChooser.showSaveDialog(myGuiControl.getFrame());

        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            String file = myFileChooser.getSelectedFile().getAbsolutePath();
            if (!file.trim().toLowerCase().endsWith(".jpg")) {
                file = file.trim() + ".jpg";
            }
            if (!canSaveFile(file)) {
                return;
            }
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream(file));
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);

            BufferedImage bi = currentProject.getCurrentOpd()
                    .getImageRepresentation();
            
            JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bi);
            int quality = 100;
            quality = Math.max(0, Math.min(quality, 100));
            param.setQuality((float) quality / 100.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(bi);
            out.close();
        } catch (Exception e23) {
        }

    }

    /**
     * Returns the API read-only representation of the current project. 
     * @return
     */
    public ISystem getCurrentISystem() {
        return myOpcat.getCurrentProject();
    }

    /**
     * Returns the current project, after checking if myOpcat is not null.
     * @return
     */
    public OpdProject getCurrentProject() {
        if (myOpcat == null) {
            OpcatLogger.logError("FileControl was not initiated by Opcat2");
        }
        return myOpcat.getCurrentProject();
    }

    /**
     * @return Returns the isWholeProjectCompiled.
     */
    public boolean isWholeProjectCompiled() {
        return myOpcat.isWholeProjectCompiled();
    }

    /**
     * @param isWholeProjectCompiled The isWholeProjectCompiled to set.
     */
    public void setWholeProjectCompiled(boolean isWholeProjectCompiled) {
        myOpcat.setWholeProjectCompiled(isWholeProjectCompiled);
    }

    /**
     * Adds the current project to the list of the last used files. The data
     * is stored in a configuration file, and can be used afterwards. 
     */
    public void updateLastUsedFile() {
        Configuration conf = Configuration.getInstance();
        LastFileEntry entry = new LastFileEntry(currentProject.getName(),
                currentProject.getFileName());
        conf.addFileToLastUsed(entry);
        myOpcat.refreshMenuBar();
    }

    /**
     * @return Returns the lastDirectory - the last directory that an opcat file 
     * was opened from or saved to. 
     */
    public String getLastDirectory() {
        return lastDirectory;
    }

    /**
     * @param lastDirectory The lastDirectory to set.
     */
    public void setLastDirectory(String lastDirectory) {
        this.lastDirectory = lastDirectory;
    }

 /**
  * Reloads the project, by saving and loading it. If the project was not saved, then
  * the user is prompted to save the file. The method returns the user to the original
  * OPD that was used by the user before the reload.
  */   
    public void reloadProject()	{
        long currentOpdID = getCurrentProject().getCurrentOpd().getOpdId();
        _save();
        String filePath = new String(getCurrentProject().getFileName());
        closeSystem();
        loadOpxFile(filePath, false);
        Opd originalOpd = getCurrentProject().getOpdByID(currentOpdID);
        getCurrentProject().setCurrentOpd(originalOpd);
        getCurrentProject().showOPD(currentOpdID);
    }
}