package gui.actions.file;

import gui.Opcat2;
import gui.opx.LoadException;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class DependenciesInitializerAction extends FileAction{
		
		public DependenciesInitializerAction(String name, Icon icon){
			super(name, icon);
		}
		public void actionPerformed(ActionEvent arg0) {
			if (!this.handleWhetherOpenProject()) {
				return;
			}
			runInitialize() ; 
		}
		
		private void runInitialize() {
			Thread runner = new Thread() {
				public void run() {

					if (Opcat2.getCurrentProject() != null) 
						Opcat2.getCurrentProject().setCanClose(true); 
					DependenciesInitializerAction.this.myGuiControl.startWaitingMode(
							"Initialize Process Dependencies...", true);
				
					DependenciesInitializerAction.this.fileControl.initializeDependencies(new JProgressBar());
						DependenciesInitializerAction.this.myGuiControl.stopWaitingMode();
					Opcat2.getCurrentProject().setCanClose(false); 
				}
			};
			runner.start();		
		}
	}
