package gui.actions.file;

import extensionTools.reuse.ImportDialog;
import extensionTools.reuse.ReuseCommand;
import gui.Opcat2;
import gui.opdProject.OpdProject;
import gui.opx.LoadException;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Opens the import dialog, which imports a model into the existing model, using
 * the <code>reuse</code> package.
 * 
 * @author Eran Toch
 * @see extensionTools.reuse.ImportDialog
 */
public class ReuseImportAction extends FileAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

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
		if (!this.handleWhetherOpenProject()) {
			return;
		}

		ImportDialog dialog = new ImportDialog(this.fileControl
				.getCurrentProject(), this.myGuiControl.getDesktop());
		dialog.setVisible(true);
		if (!dialog.isOperated()) {
			return;
		}
		this.typeOfReuse = dialog.getTypeOfReuse();
		this.importFileName = dialog.getImportFileName();	
		runImport() ; 
	}
	
	
	private void runImport() {
		Thread runner = new Thread() {
			public void run() {

				if (Opcat2.getCurrentProject() != null) Opcat2.getCurrentProject().setCanClose(true); 
				ReuseImportAction.this.fileControl._save(false);
				ReuseImportAction.this.myGuiControl.startWaitingMode(
						"Importing Model...", true);
				try {
					ReuseImportAction.this.doImport();
					ReuseImportAction.this.fileControl.reloadProject(true);
				} catch (OpcatException e) {
					ReuseImportAction.this.myGuiControl.stopWaitingMode();
					OpcatLogger.logError(e);
					JOptionPane.showMessageDialog(
							ReuseImportAction.this.fileControl
									.getCurrentProject().getMainFrame(),
							"Import had failed becasue of the following error:\n"
									+ e.getMessage(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					ReuseImportAction.this.myGuiControl.stopWaitingMode();
				}
				Opcat2.getCurrentProject().setCanClose(false); 
			}

		};
		runner.start();		
	}

	public void doImport(String fileName) throws OpcatException {
		importFileName = fileName;
		runImport() ; 
	}

	public void doImport() throws OpcatException {
		OpdProject reusedProject = null;
		try {
			reusedProject = this.fileControl.loadOpxFileIntoProject(
					this.importFileName, null);
		} catch (LoadException e) {
			throw new OpcatException("Imported project was not loaded\n"
					+ e.getMessage());
		}
		if (reusedProject == null) {
			throw new OpcatException(
					"Imported project is null. Check the file name and try again\n"
							+ this.importFileName);
		}
		OpdProject curr = this.fileControl.getCurrentProject();
		int reuseType = ReuseCommand.NO_REUSE;
		if (this.typeOfReuse == 0) {
			reuseType = ReuseCommand.SIMPLE_WHITE;
		}

		if (this.typeOfReuse == 1) {
			reuseType = ReuseCommand.SIMPLE_BLACK;
		}
		if (this.typeOfReuse == 2) {
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
			curr.addopenResuedOpdToList(curr.getCurrentOpd(),
					this.importFileName);
			curr.getCurrentOpd().setOpenReused(true);
			curr.getCurrentOpd().setReusedSystemPath(this.importFileName);
		}

		if ((this.typeOfReuse > 2) || (reusedProject == null)) {
			throw new OpcatException("Reuse type was not determined");
		}
		ReuseCommand command = new ReuseCommand(curr, reusedProject, reuseType);
		try {
			command.commitReuse();
		} catch (OpcatException e1) {
			throw e1;
		} finally {
			reusedProject.close();
		}
		return;
	}

}