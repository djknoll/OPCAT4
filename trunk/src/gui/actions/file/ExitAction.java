package gui.actions.file;

import gui.Opcat2;
import gui.controls.GuiControl;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Exits from Opcat, after asking the user to save the current project and
 * performing other cleaning tasks.
 *
 * @author Eran Toch
 */
public class ExitAction extends FileAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */

    public ExitAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            GuiControl gui = GuiControl.getInstance();
            Opcat2.startWait();

            this.file._exit();
        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        } finally {
            Opcat2.endWait();
        }
    }
}
