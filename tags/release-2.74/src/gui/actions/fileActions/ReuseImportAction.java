package gui.actions.fileActions;

import extensionTools.reuse.ImportDialog;
import extensionTools.reuse.ReuseCommand;
import gui.opdProject.OpdProject;
import gui.opx.LoadException;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Opens the import dialog, which imports a model into the existing model, using
 * the <code>reuse</code> package.
 * 
 * @author Eran Toch
 * @see extensionTools.reuse.ImportDialog
 */
public class ReuseImportAction extends FileAction {

    private int typeOfReuse = ReuseCommand.NO_REUSE;

    private String importFileName = "";

    public ReuseImportAction(String name, Icon icon) {
        super(name, icon);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (!handleWhetherOpenProject()) {
            return;
        }

        ImportDialog dialog = new ImportDialog(fileControl.getCurrentProject(),
                myGuiControl.getDesktop());
        dialog.setVisible(true);
        if (!dialog.isOperated()) {
            return;
        }
        typeOfReuse = dialog.getTypeOfReuse();
        importFileName = dialog.getImportFileName();

        Thread runner = new Thread() {
            public void run() {

                fileControl._save();
                myGuiControl.startWaitingMode("Importing Model...", true);
                try {
                    doImport();
                    fileControl.reloadProject();
                } catch (OpcatException e) {
                    myGuiControl.stopWaitingMode();
                    OpcatLogger.logError(e);
                    JOptionPane
                            .showMessageDialog(fileControl.getCurrentProject()
                                    .getMainFrame(), "Import had failed becasue of the following error:\n"
                                    + e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
                } finally {
                    myGuiControl.stopWaitingMode();
                }
            }

        };
        runner.start();

    }

    public void doImport() throws OpcatException {
        InputStream is;
        OpdProject reusedProject = null;
        try {
            reusedProject = fileControl
                    .loadOpxFileIntoProject(importFileName, null);
        } catch (LoadException e) {
            throw new OpcatException("Imported project was not loaded\n"
                    + e.getMessage());
        }
        if (reusedProject == null) {
            throw new OpcatException(
                    "Imported project is null. Check the file name and try again\n"
                            + importFileName);
        }
        OpdProject curr = fileControl.getCurrentProject();
        int reuseType = ReuseCommand.NO_REUSE;
        if (typeOfReuse == 0)
            reuseType = ReuseCommand.SIMPLE_WHITE;

        if (typeOfReuse == 1)
            reuseType = ReuseCommand.SIMPLE_BLACK;
        if (typeOfReuse == 2) {
            // check if the current OPD is already open reused
            // if so don't allow open reuse
            if (curr.getCurrentOpd().isOpenReused()) {
                throw new OpcatException(
                        "The current Opd is already open reused");
            }
            // check if the current opd is already open reused
            // in the case of open reuse we need to
            // mark the system as open reused
            // mark the Current Opd as open reused and specify the path of hte
            // file
            reuseType = ReuseCommand.OPEN;

            curr.setProjectType(OpdProject.OPEN_REUSED_SYSTEM);
            curr.addopenResuedOpdToList(curr.getCurrentOpd(), importFileName);
            curr.getCurrentOpd().setOpenReused(true);
            curr.getCurrentOpd().setReusedSystemPath(importFileName);
        }

        if ((typeOfReuse > 2) || (reusedProject == null)) {
            throw new OpcatException("Reuse type was not determined");
        }
        ReuseCommand command = new ReuseCommand(curr, reusedProject, reuseType);
        try {
            command.commitReuse();
        } catch (OpcatException e1) {
            throw e1;
        }
        finally	{
            reusedProject.close();
        }
        return;
    }

}