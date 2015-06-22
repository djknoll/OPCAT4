package gui.actions.edit;

import gui.Opcat2;

import java.awt.event.ActionEvent;

import javax.swing.Icon;
import javax.swing.undo.CannotRedoException;

/**
 * Carries out a copy operation. 
 * @author Eran Toch
 */
public class UndoAction extends EditAction {

   public UndoAction(String name, Icon icon) {
        super(name, icon);
    }
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        edit.undo();
    }
}
