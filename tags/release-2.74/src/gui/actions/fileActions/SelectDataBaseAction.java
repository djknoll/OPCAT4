package gui.actions.fileActions;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Opens an Access database, in ordre to provide backwards compatability 
 * for legacy project representations. 
 * @author Eran Toch
 * @see		gui.actions.controls.FileControl#selectDataBase()
 */
public class SelectDataBaseAction extends FileAction {

    public SelectDataBaseAction(String name, Icon icon) {
        super(name, icon);
    }


    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        fileControl.selectDataBase();

    }

}
