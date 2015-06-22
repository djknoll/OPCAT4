package gui.actions.svn;

import expose.OpcatExposeKey;
import expose.OpcatExposeManager;
import expose.OpcatExposeUser;
import gui.Opcat2;
import gui.controls.GuiControl;
import gui.repository.BaseView.View_RefreshState;
import modelControl.OpcatMCDirEntry;
import modelControl.OpcatMCManager;
import user.OpcatUser;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SvnRepoDeleteAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatMCDirEntry entry;

    public SvnRepoDeleteAction(OpcatMCDirEntry entry, String name, Icon icon) {
        super(name, icon);
        this.entry = entry;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.startWait();

        try {

            ArrayList<OpcatExposeUser> users = OpcatExposeManager
                    .getExposeUsage(entry.getURL().getURIEncodedPath());

            if (users.size() > 0) {
                JOptionPane.showMessageDialog(Opcat2.getFrame(),
                        "File has used exports, delete canceled");
                Opcat2.endWait();
                return;
            } else {
                // remove expose as there is no users
                ArrayList<OpcatExposeKey> keys = OpcatExposeManager
                        .getExposeKey(entry.getURL().getURIEncodedPath());
                for (OpcatExposeKey key : keys) {
                    OpcatExposeManager.removeExposeFromDB(key);
                }

            }

            boolean force = Boolean.valueOf(Configuration.getInstance().getProperty("admin.opcat.forcemcdelete"));
            int ret = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                    (force ? "Forcing " : "") + "Deletion of repository Directory, Are you sure ?", "Delete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ret != JOptionPane.OK_OPTION) {
                Opcat2.endWait();
                return;
            }


            boolean done = false;
            while (!done) {
                String msg = (String) JOptionPane.showInputDialog(Opcat2
                        .getFrame(), "Enter delete note", "Delete",
                        JOptionPane.PLAIN_MESSAGE, null, null, "Deleted by "
                                + OpcatUser.getCurrentUser().getName());

                if ((msg != null) && (msg.length() > 0)) {
                    OpcatMCManager.getInstance().doRemoteDelete(entry, msg);
                    GuiControl.getInstance().getRepository().getSVNView()
                            .repaintKeepOpen(View_RefreshState.KEEP_OPEN);
                }

                done = true;
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        } finally {
            Opcat2.endWait();
        }


    }
}
