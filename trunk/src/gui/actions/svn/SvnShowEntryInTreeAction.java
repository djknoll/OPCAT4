package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCDirEntry;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import util.OpcatLogger;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Enumeration;

public class SvnShowEntryInTreeAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private GridPanel panel;

    public SvnShowEntryInTreeAction(String name, Icon icon, GridPanel panel) {
        super(name, icon);
        this.panel = panel;
    }

    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        Opcat2.startWait();

        try {

            GridPanel.RemovePanel(OpcatMCManager.getAdminGridName());
            if (GuiControl.getInstance().isShowAdminConsole()) {
                Object[] tag = panel.getSelectedTag();

                OpcatMCDirEntry entry = (OpcatMCDirEntry) tag[0];

                DefaultMutableTreeNode root = (DefaultMutableTreeNode) GuiControl
                        .getInstance().getRepository().getSVNView().getModel()
                        .getRoot();

                for (DefaultMutableTreeNode node : Collections
                        .list((Enumeration<DefaultMutableTreeNode>) root
                                .depthFirstEnumeration())) {
                    SVNURL url;
                    if (node.getUserObject() instanceof OpcatMCDirEntry) {
                        url = ((OpcatMCDirEntry) node.getUserObject())
                                .getURL();

                    } else {
                        url = (SVNURL) node.getUserObject();
                    }

                    /**
                     * check if url is parent or sub path of entry
                     */
                    // SVNPathUtil.isAncestor(parentPath, ancestorPath)
                }
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        GuiControl.getInstance().getRepository().getModelsView()
                .repaintKeepOpen();
        Opcat2.endWait();
    }
}
