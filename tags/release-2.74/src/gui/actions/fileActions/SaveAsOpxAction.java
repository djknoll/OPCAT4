package gui.actions.fileActions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

import org.objectprocess.Client.TeamMember;

/**
 * Perfoms a save-as operation, prompting the user for a file location. 
 * @author Eran Toch
 * @see		gui.actions.controls.FileControl#_saveAs()
 */
public class SaveAsOpxAction extends FileAction {

	private TeamMember teamMember = null;
	
	public SaveAsOpxAction(String name, Icon icon)	{
		super(name, icon);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (!handleWhetherOpenProject())	{
		    return;
		}
		Thread runner = new Thread() {
			public void run() {
				fileControl._saveAs();
			}
		};

		runner.start();

	}


}

