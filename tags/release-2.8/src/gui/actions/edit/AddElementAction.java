package gui.actions.edit;

import gui.opdProject.StateMachine;
import gui.util.OpcatLogger;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * 
 * @author Eran Toch
 */
public class AddElementAction extends EditAction {

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
        // TODO Auto-generated method stub
        super.actionPerformed(arg0);
        if (!edit.isProjectOpened()) {
            return;
        }
        try {
            StateMachine.go(state, elementType);
        } catch (Exception e1) {
            OpcatLogger.logError(e1);
        }
    }
}
