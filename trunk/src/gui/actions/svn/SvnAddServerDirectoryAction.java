package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.repository.BaseView.View_RefreshState;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnAddServerDirectoryAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private SVNURL url;

    public SvnAddServerDirectoryAction(SVNURL url, String name, Icon icon) {
        super(name, icon);
        this.url = url;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        try {
            Opcat2.startWait();

            String msg = "Enter Directory Name";
            String title = "Create MC Directory";
            msg = JOptionPane.showInputDialog(Opcat2.getFrame(), title, msg);

            if (msg != null) {
                OpcatMCManager.getInstance().doMakeDirectory(url, msg);

                GuiControl.getInstance().getRepository().getSVNView()
                        .repaintKeepOpen(View_RefreshState.KEEP_OPEN);

            }
        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        } finally {

            Opcat2.endWait();
        }

    }

}
