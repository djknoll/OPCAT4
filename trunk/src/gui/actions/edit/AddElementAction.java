package gui.actions.edit;

import gui.Opcat2;
import gui.opdProject.StateMachine;
import org.w3c.dom.events.EventException;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Eran Toch
 */
public class AddElementAction extends EditAction {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */


    private int state = 0;

    private int elementType = 0;

    /**
     * @param name
     * @param icon
     */
    public AddElementAction(String name, Icon icon, int state, int elementType) {
        super(name, icon);
        this.state = state;
        this.elementType = elementType;
    }

    /**
     * Performs the
     */
    public void actionPerformed(ActionEvent arg0) {
        try {
            super.actionPerformed(arg0);
        } catch (EventException e) {
            JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
                    .toString(), "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            StateMachine.go(this.state, this.elementType);
        } catch (Exception e1) {
            OpcatLogger.error(e1);
        }

        Opcat2.getCurrentProject().getCurrentOpd().setViewZoomIn(false);

    }
}
