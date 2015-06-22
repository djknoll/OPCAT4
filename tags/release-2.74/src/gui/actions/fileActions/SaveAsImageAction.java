package gui.actions.fileActions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Saves the current OPD as a JPG image. 
 * @author Eran Toch
 * @see		gui.actions.controls.FileControl#saveAsImage()
 */
public class SaveAsImageAction extends FileAction {

    public SaveAsImageAction(String name, Icon icon) {
        super(name, icon);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        if (!handleWhetherOpenProject())	{
		    return;
		}
        fileControl.saveAsImage();

    }

}
