package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.repository.BaseView.View_RefreshState;
import gui.util.OpcatFile;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnDeleteLocalNoRepoDirectoryAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatFile file;

    public SvnDeleteLocalNoRepoDirectoryAction(OpcatFile file, String name,
                                               Icon icon) {
        super(name, icon);
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.getGlassPane().setVisible(true);
        Opcat2.getGlassPane().start();
        try {
            int ret = JOptionPane.showOptionDialog(Opcat2.getFrame(),
                    "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, null, null);
            if (ret == JOptionPane.YES_OPTION) {

                OpcatMCManager.deleteDirectory(file);

            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
        GuiControl.getInstance().getRepository().getModelsView()
                .setRefrashState(View_RefreshState.ALL);
        Opcat2.getGlassPane().setVisible(false);
        Opcat2.getGlassPane().stop();
        GuiControl.getInstance().getRepository().getModelsView().repaintKeepOpen();

    }
}
