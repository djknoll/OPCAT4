package gui.actions.svn;

import gui.Opcat2;
import gui.controls.GuiControl;
import gui.util.OpcatFile;

import java.awt.event.ActionEvent;

import javax.swing.*;

import modelControl.OpcatMCManager;

public class SvnUpdateAction extends SvnAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private OpcatFile file;

    public SvnUpdateAction(OpcatFile file, String name, Icon icon) {
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
                    OpcatMCManager.getInstance().doUpdate(file);
                } catch (Exception ex) {

                }

                GuiControl.getInstance().getRepository().getModelsView()
                        .repaintKeepOpen();

                Opcat2.endWait();
            }
        });

    }

}
