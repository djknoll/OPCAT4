package gui.actions.fileActions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Closes the current project.
 * @see	gui.actions.controls.FileControl#closeSystem()
 * @author Eran Toch
 */
public class CloseAction extends FileAction {
	
	public CloseAction(String name, Icon icon)	{
		super(name, icon);
	}
	
	public void actionPerformed(ActionEvent arg0) {
	    fileControl.closeSystem();
	}


}

