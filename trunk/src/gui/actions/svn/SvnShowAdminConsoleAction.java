package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnShowAdminConsoleAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SvnShowAdminConsoleAction(String name, Icon icon) {
        super(name, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Opcat2.startWait();

        try {

            GridPanel.RemovePanel(OpcatMCManager.getAdminGridName());
            if (GuiControl.getInstance().isShowAdminConsole()) {
                OpcatMCManager.getInstance(true).getAdminGrid().show(true);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        GuiControl.getInstance().getRepository().getModelsView()
                .repaintKeepOpen();
        Opcat2.endWait();
    }
}
