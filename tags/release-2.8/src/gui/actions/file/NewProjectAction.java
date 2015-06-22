package gui.actions.file;

import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 * Creates a new project, and prompts the user for properties. 
 * @author Eran Toch
 * @see		gui.controls.FileControl#createNewProject()
 */
public class NewProjectAction extends FileAction {

    public NewProjectAction(String name, Icon icon) {
        super(name, icon);
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
		if (!fileControl.handleOpenedSystem()) {
			return;
		}
		fileControl.createNewProject();
	}
}
