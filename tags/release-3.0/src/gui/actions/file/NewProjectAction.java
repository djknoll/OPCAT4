package gui.actions.file;

import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 * Creates a new project, and prompts the user for properties.
 * 
 * @author Eran Toch
 * @see gui.controls.FileControl#createNewProject()
 */
public class NewProjectAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public NewProjectAction(String name, Icon icon) {
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (!this.fileControl.handleOpenedSystem()) {
			return;
		}
		this.fileControl.createNewProject();
	}
}
