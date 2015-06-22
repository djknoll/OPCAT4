package gui.actions;

import gui.controls.EditControl;
import org.w3c.dom.events.EventException;

import javax.swing.*;
import java.awt.event.ActionEvent;

public abstract class OpcatAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public OpcatAction(String name, Icon icon) {
        super(name, icon);
    }

    public OpcatAction() {
        super();
    }

    public OpcatAction(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent e) {
        if (!EditControl.getInstance().isProjectOpened()) {
            short code = 1;
            throw new EventException(code,
                    "This Action can not be executed if there is no open model");
        }
    }

}
