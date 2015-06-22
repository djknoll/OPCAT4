package gui.actions.svn;

import expose.OpcatExposeKey;
import expose.OpcatExposeManager;
import expose.OpcatExposeUser;
import gui.Opcat2;
import gui.controls.GuiControl;
import gui.util.OpcatFile;
import modelControl.OpcatMCDirEntry;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class SvnLocalDeleteAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatFile file;

    public SvnLocalDeleteAction(OpcatFile file, String name, Icon icon) {
        super(name, icon);
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.startWait();
        try {
            //OpcatMCDirEntry entry = OpcatMCManager.getInstance().getEntry(file);
            SVNURL url = OpcatMCManager.getInstance(true).getFileURL(file);

            if (url == null) {
                JOptionPane.showMessageDialog(Opcat2.getFrame(),
                        "Not a MC file");
                Opcat2.endWait();
                return;
            }

            ArrayList<OpcatExposeUser> users = new ArrayList<OpcatExposeUser>();
            if (url != null) {
                users = OpcatExposeManager.getExposeUsage(url
                        .getURIEncodedPath());
            }

            if (users.size() > 0) {
                JOptionPane.showMessageDialog(Opcat2.getFrame(),
                        "File has used exports, delete canceled");
                Opcat2.endWait();
                return;
            } else {
                // remove expose as there is no users
                ArrayList<OpcatExposeKey> keys = OpcatExposeManager
                        .getExposeKey(url.getURIEncodedPath());
                for (OpcatExposeKey key : keys) {
                    OpcatExposeManager.removeExposeFromDB(key);
                }

                // remove usage
                //SVNURL url = OpcatMCManager.getInstance().getFileURL(file);
                OpcatExposeManager.removeModelUsage(url.getPath());

            }

            int ret = JOptionPane.showOptionDialog(Opcat2.getFrame(),
                    "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (ret == JOptionPane.YES_OPTION) {
                OpcatMCManager.getInstance().doLocalDelete(file, true);
                GuiControl.getInstance().getRepository().getModelsView()
                        .repaintKeepOpen();

            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }

        GuiControl.getInstance().getRepository().getModelsView().rebuildTree(
                null, null);
        Opcat2.endWait();

    }
}
