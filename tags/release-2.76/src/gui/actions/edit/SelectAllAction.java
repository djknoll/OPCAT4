package gui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Select all the elements in the current OPD. 
 * @author Eran Toch
 */
public class SelectAllAction extends EditAction {

   public SelectAllAction(String name, Icon icon) {
        super(name, icon);
    }
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0);
        edit.getCurrentProject().selectAll();
    }
}
