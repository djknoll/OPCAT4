package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnShowFileConsoleAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SvnShowFileConsoleAction(String name, Icon icon) {
        super(name, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Opcat2.startWait();

        try {

            GridPanel.RemovePanel(OpcatMCManager.getFileGridName());
            if (GuiControl.getInstance().isShowFileConsole()) {
                OpcatMCManager.getInstance(true).getFileGrid().show(true);
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        GuiControl.getInstance().getRepository().getModelsView()
                .repaintKeepOpen();
        Opcat2.endWait();
    }
}
