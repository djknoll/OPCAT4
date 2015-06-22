package gui.actions.file;

import gui.Opcat2;
import gui.controls.GuiControl;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Perfoms a save operation, saving the file in the last location which it was
 * saved. Before that, it performs an OpcatTeam operation.
 *
 * @author Eran Toch
 */
public class SaveOpxAction extends FileAction {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */

    public SaveOpxAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        try {
            Opcat2.startWait();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        SaveOpxAction.this.file._save(true);
                        GuiControl.getInstance().getRepository().getModelsView().repaintKeepOpen();
                    } catch (Exception ex) {
                        OpcatLogger.error(ex, true);

                    } finally {
                        Opcat2.endWait();
                    }
                }
            });
        } catch (Exception ex) {
            OpcatLogger.error(ex, true);
            Opcat2.endWait();
        }

    }

}
