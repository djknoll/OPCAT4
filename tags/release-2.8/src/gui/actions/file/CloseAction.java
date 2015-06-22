package gui.actions.file;

import gui.actions.edit.EditAction;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Closes the current project.
 * @see	gui.controls.FileControl#closeSystem()
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

