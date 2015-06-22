package gui.actions.file;

import java.awt.event.ActionEvent;
import gui.actions.edit.EditAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * Exits from Opcat, after asking the user to save the current project and 
 * performing other cleaning tasks. 
 * @author Eran Toch
 */
public class ExitAction extends FileAction {
    public ExitAction(String name, Icon icon)	{
		super(name, icon);
	}
    public void actionPerformed(ActionEvent e) {
   		fileControl._exit();
	}
}
