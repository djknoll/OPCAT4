package gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Perfoms a save-as operation, prompting the user for a file location.
 * 
 * @author Eran Toch
 * @see gui.controls.FileControl#_saveAs()
 */
public class SaveAsOpxAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public SaveAsOpxAction(String name, Icon icon) {
		super(name, icon);
	}

	public void actionPerformed(ActionEvent e) {
		if (!this.handleWhetherOpenProject()) {
			return;
		}
		Thread runner = new Thread() {
			public void run() {
				SaveAsOpxAction.this.fileControl._saveAs();
			}
		};

		runner.start();

	}

}
