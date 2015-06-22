package gui.actions.file;

import gui.Opcat2;
import gui.controls.GuiControl;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Perfoms a save-as operation, prompting the user for a file location.
 *
 * @author Eran Toch
 */
public class SaveAsOpxAction extends FileAction {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */


    public SaveAsOpxAction(String name, Icon icon) {
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

                        SaveAsOpxAction.this.file._saveAs();
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
        } finally {
            Opcat2.endWait();
        }

    }

}
