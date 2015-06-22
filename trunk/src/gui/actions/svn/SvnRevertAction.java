package gui.actions.svn;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.util.OpcatFile;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class SvnRevertAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatFile file;

    public SvnRevertAction(OpcatFile file, String name, Icon icon) {
        super(name, icon);
        this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);

        Opcat2.startWait();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {

                    int ret = 0;
                    if (file.isDirectory()) {
                        ret = JOptionPane
                                .showConfirmDialog(
                                        Opcat2.getFrame(),
                                        "Are you sure?\nAll local copy fils are going to be overridden",
                                        "Opcat II", JOptionPane.YES_NO_OPTION);
                    } else {
                        ret = JOptionPane
                                .showConfirmDialog(
                                        Opcat2.getFrame(),
                                        "Are you sure?\nYour local copy is going to be overridden",
                                        "Opcat II", JOptionPane.YES_NO_OPTION);
                    }
                    if (ret != JOptionPane.YES_OPTION) {
                        return;
                    }
                    OpcatMCManager.getInstance().doRevert(file);

                    GuiControl.getInstance().getRepository().getModelsView()
                            .repaintKeepOpen();

                    GridPanel.RemoveALLPanels();

                    if (file.getName().endsWith(".opx")
                            || file.getName().endsWith(".opz"))
                        FileControl.getInstance().loadFileWithOutFilesPrompt(
                                file.getPath());

                    if (Opcat2.getCurrentProject() != null)
                        Opcat2.getCurrentProject().setCanClose(false);

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                } finally {
                    Opcat2.endWait();
                    GuiControl.getInstance().getRepository().getModelsView()
                            .repaintKeepOpen();
                }

                GuiControl.getInstance().getRepository().getModelsView()
                        .repaintKeepOpen();
            }
        });


    }
}
