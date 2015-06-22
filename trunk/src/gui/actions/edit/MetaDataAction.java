package gui.actions.edit;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.dataProject.DataProject;
import gui.dataProject.MetaDataGridPanels;
import gui.metaLibraries.logic.MetaLibrary;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MetaDataAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     *
     */

    private FileControl file = FileControl.getInstance();

    private GuiControl gui = GuiControl.getInstance();

    public MetaDataAction(String name, Icon icon) {
        super(name, icon);
    }

    public void actionPerformed(ActionEvent action) {

        if (!this.file.isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(this.gui.getFrame(),
                            "No system is opened", "Message",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }

        Opcat2.startWait();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MetaDataGridPanels.getInstance().showMetaDataProjects();
                } catch (Exception ex) {
                    OpcatLogger.error(ex, false);
                } finally {
                    Opcat2.endWait();
                }
            }
        });

    }

    public void showMeta(MetaLibrary ont, DataProject metadata) {

        if (!this.file.isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(this.gui.getFrame(),
                            "No system is opened", "Message",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        MetaDataGridPanels.getInstance().showMeta(ont, metadata);

    }

}
