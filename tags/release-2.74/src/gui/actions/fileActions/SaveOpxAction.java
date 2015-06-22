package gui.actions.fileActions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.JOptionPane;
import org.objectprocess.Client.TeamMember;

/**
 * Perfoms a save operation, saving the file in the last location which it
 * was saved. Before that, it performs an OpcatTeam operation. 
 * @author Eran Toch
 * @see		gui.actions.controls.FileControl#_save()
 */
public class SaveOpxAction extends FileAction {

	private TeamMember teamMember = null;
	
	public SaveOpxAction(String name, Icon icon)	{
		super(name, icon);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (!handleWhetherOpenProject())	{
		    return;
		}
	    teamMember = fileControl.getTeamMember();
		//OPCATEAM
		//check if the open project is an OPCATeam project- if yes, use another save procedure
		if (teamMember != null)
			if (teamMember.isConnected()) {
				Object[] saveOption = new Object[2];
				saveOption[0] = new String("Save Localy");
				saveOption[1] = new String("Cancel");
				Icon icon = null;
				int retValue;
				retValue =
					JOptionPane.showOptionDialog(
						myGuiControl.getFrame(),
						"The action you chose will perform Local Saving."
							+ '\n'
							+ "In order to save the Model on the Server you should use the OPCATeam Panel."
							+ '\n',
						"Save",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,
						icon,
						saveOption,
						saveOption[1]);

				switch (retValue) {
					case JOptionPane.YES_OPTION :
						{
							fileControl._save();
							break;
						}
					case JOptionPane.NO_OPTION :
						{
							break;
						}
				}

				return;
			}
		//OPCATeam - end

		Thread runner = new Thread() {
			public void run() {
				fileControl._save();
			}
		};

		runner.start();

	}

}

