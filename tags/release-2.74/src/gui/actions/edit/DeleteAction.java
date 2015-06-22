package gui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Carries out a delete operation. 
 * @author Eran Toch
 */
public class DeleteAction extends EditAction {

   public DeleteAction(String name, Icon icon) {
        super(name, icon);
    }
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0);
        edit.getCurrentProject().delete();
    }
}
