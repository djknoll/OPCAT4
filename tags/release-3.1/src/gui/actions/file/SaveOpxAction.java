package gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Perfoms a save operation, saving the file in the last location which it was
 * saved. Before that, it performs an OpcatTeam operation.
 * 
 * @author Eran Toch
 * @see gui.controls.FileControl#_save()
 */
public class SaveOpxAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	public SaveOpxAction(String name, Icon icon) {
		super(name, icon);		
	}

	public void actionPerformed(ActionEvent e) {
		if (!this.handleWhetherOpenProject()) {
			return;
		}

		Thread runner = new Thread() {
			public void run() {
				SaveOpxAction.this.fileControl._save(true, false);
			}
		};

		runner.start();

	}

}
