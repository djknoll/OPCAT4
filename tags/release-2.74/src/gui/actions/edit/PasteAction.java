package gui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Carries out a copy operation. 
 * @author Eran Toch
 */
public class PasteAction extends EditAction {

   public PasteAction(String name, Icon icon) {
        super(name, icon);
    }
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0);
        edit.getCurrentProject().paste(100, 50, edit.getCurrentProject().getCurrentOpd()
                .getDrawingArea());
        edit.updateLogicalStructureChange();
    }
}

