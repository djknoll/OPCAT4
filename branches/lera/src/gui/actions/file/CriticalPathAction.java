package gui.actions.file;
import gui.Opcat2;
import gui.actions.edit.EditAction;
import gui.opx.LoadException;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class CriticalPathAction extends FileAction{
	
	public CriticalPathAction(String name, Icon icon){
		super(name, icon);
	}
	public void actionPerformed(ActionEvent arg0) {
		if (!this.handleWhetherOpenProject()) {
			return;
		}
		runFindCriticalPath() ; 
	}
	
	private void runFindCriticalPath() {
		Thread runner = new Thread() {
			public void run() {

				if (Opcat2.getCurrentProject() != null) Opcat2.getCurrentProject().setCanClose(true); 
				CriticalPathAction.this.myGuiControl.startWaitingMode(
						"Findig Critical Path...", true);
				try {
					CriticalPathAction.this.fileControl.findCriticalPath(new JProgressBar());
				}catch (LoadException e) {
					CriticalPathAction.this.myGuiControl.stopWaitingMode();
					OpcatLogger.logError(e);
					JOptionPane.showMessageDialog(
							CriticalPathAction.this.fileControl
									.getCurrentProject().getMainFrame(),
							"Critical Path can't be detected\n"
									+ e.getMessage(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} catch ( gui.procDependencies.ExistCirclesException e) {
					CriticalPathAction.this.myGuiControl.stopWaitingMode();
					OpcatLogger.logError(e);
					JOptionPane.showMessageDialog(
							CriticalPathAction.this.fileControl
									.getCurrentProject().getMainFrame(),
							"CP method had failed becasue of the following error:\n"
									+ e.getMsg(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					CriticalPathAction.this.myGuiControl.stopWaitingMode();
				}
				Opcat2.getCurrentProject().setCanClose(false); 
			}

		};
		runner.start();		
	}
}
