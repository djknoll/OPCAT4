package gui.actions.file;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import extensionTools.MsProject.ProjectLoader;
import gui.Opcat2;
import gui.opx.LoadException;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

public class MsProjectImportAction extends FileAction{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private java.io.File importFile = null;

	public MsProjectImportAction(String name, Icon icon) {
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

		extensionTools.MsProject.MsProjectImportDialog dialog = new extensionTools.MsProject.MsProjectImportDialog();
		dialog.init();
		//this.typeOfReuse = dialog.getTypeOfReuse();
		this.importFile = dialog.getImportFile();	
		runImport() ; 
	}
	
	
	private void runImport() {
		Thread runner = new Thread() {
			public void run() {

				if (Opcat2.getCurrentProject() != null) Opcat2.getCurrentProject().setCanClose(true); 
				MsProjectImportAction.this.myGuiControl.startWaitingMode(
						"Importing MsProject...", true);
				try {
					//MsProjectImportAction.this.doImport();
					MsProjectImportAction.this.fileControl.loadMsProject(MsProjectImportAction.this.importFile, new JProgressBar());
				}catch (LoadException e) {
					MsProjectImportAction.this.myGuiControl.stopWaitingMode();
					OpcatLogger.logError(e);
					JOptionPane.showMessageDialog(
							MsProjectImportAction.this.fileControl
									.getCurrentProject().getMainFrame(),
							"Import was not loaded\n"
									+ e.getMessage(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} catch (OpcatException e) {
					MsProjectImportAction.this.myGuiControl.stopWaitingMode();
					OpcatLogger.logError(e);
					JOptionPane.showMessageDialog(
							MsProjectImportAction.this.fileControl
									.getCurrentProject().getMainFrame(),
							"Import had failed becasue of the following error:\n"
									+ e.getMessage(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					MsProjectImportAction.this.myGuiControl.stopWaitingMode();
				}
				Opcat2.getCurrentProject().setCanClose(false); 
			}

		};
		runner.start();		
	}
}
