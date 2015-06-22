package gui.actions.file;

import gui.Opcat2;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class DependenciesCheckAction extends FileAction{
		
		public DependenciesCheckAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent arg0) {
			if (!this.handleWhetherOpenProject()) {
				return;
			}
			runTest() ; 
		}
		
		private void runTest() {
			Thread runner = new Thread() {
				public void run() {

					if (Opcat2.getCurrentProject() != null) Opcat2.getCurrentProject().setCanClose(true); 
					DependenciesCheckAction.this.myGuiControl.startWaitingMode(
							"Dependencies checking...", true);
					try {
						DependenciesCheckAction.this.fileControl.checkDependenciesForCircles(new JProgressBar());
					} catch ( gui.procDependencies.ExistCirclesException e) {
						DependenciesCheckAction.this.myGuiControl.stopWaitingMode();
						OpcatLogger.logError(e);
						JOptionPane.showMessageDialog(
								DependenciesCheckAction.this.fileControl
										.getCurrentProject().getMainFrame(),
								"The dependencies test failed because of the following error:\n"
										+ e.getMsg(), "Message",
								JOptionPane.ERROR_MESSAGE);
					} finally {
						DependenciesCheckAction.this.myGuiControl.stopWaitingMode();
					}
					Opcat2.getCurrentProject().setCanClose(false); 
				}
			};
			runner.start();		
		}
	}
