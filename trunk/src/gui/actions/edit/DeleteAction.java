package gui.actions.edit;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.repository.Repository;
import org.w3c.dom.events.EventException;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Carries out a delete operation.
 *
 * @author Eran Toch
 */
public class DeleteAction extends EditAction {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */


    public DeleteAction(String name, Icon icon) {
        super(name, icon);
    }

    /*
      * (non-Javadoc)
      *
      * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
      */
    public void actionPerformed(ActionEvent arg0) {
        try {
            super.actionPerformed(arg0);
        } catch (EventException e) {
            JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
                    .toString(), "Message", JOptionPane.ERROR_MESSAGE);

            return;
        }

        this.edit.getCurrentProject().delete();
        Opcat2.updateStructureChange(Opcat2.LOGICAL_CHANGE);

        GuiControl gui = GuiControl.getInstance();
        if (gui.getRepository().getCurrentView() == Repository.OPDThingsVIEW)
            gui.getRepository().rebuildTrees(true);


    }
}
