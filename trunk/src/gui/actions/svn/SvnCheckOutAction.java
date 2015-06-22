package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import modelControl.OpcatMCDirEntry;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnCheckOutAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatMCDirEntry entry;
    private boolean lock = true;

    public SvnCheckOutAction(OpcatMCDirEntry entry, boolean lock, String name,
                             Icon icon) {
        super(name, icon);
        this.entry = entry;
        this.lock = lock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);


        Opcat2.startWait();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    OpcatMCManager svn = OpcatMCManager.getInstance();
                    svn.doCheckOut(entry, SVNRevision.HEAD, lock);

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                }


                GuiControl.getInstance().getRepository().getModelsView()
                        .repaintKeepOpen();

                Opcat2.endWait();


            }
        });
    }

}
