package gui.actions.messages;

import gui.Opcat2;
import gui.actions.svn.SvnAction;
import gui.controls.GuiControl;
import gui.util.opcatGrid.GridPanel;
import messages.OpcatMessagesFilter;
import messages.OpcatMessagesManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpcatMessagesConsoleRefreshAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private GridPanel myPanel;

    public OpcatMessagesConsoleRefreshAction(String name, Icon icon,
                                             GridPanel myPanel) {
        super(name, icon);
        this.myPanel = myPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Opcat2.getGlassPane().setVisible(true);
        Opcat2.getGlassPane().start();
        myPanel.setEnabled(false);
        try {

            myPanel.ClearData();
            myPanel.RemoveFromExtensionToolsPanel();
            myPanel = null;

            // GridPanel.RemovePanel(OpcatMessages.getMessagesGridName());
            if (GuiControl.getInstance().isShowMessagesConsole()) {
                OpcatMessagesFilter filter = OpcatMessagesManager.getInstance()
                        .getLastFilter();
                OpcatMessagesManager.getInstance().getMessages(filter)
                        .AddToExtensionToolsPanel(true);
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

        GuiControl.getInstance().getRepository().getModelsView()
                .repaintKeepOpen();
        Opcat2.getGlassPane().setVisible(false);
        Opcat2.getGlassPane().stop();
    }
}
