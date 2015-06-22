package gui.actions.expose;

import expose.OpcatExposeKey;
import gui.actions.OpcatAction;
import gui.controls.FileControl;
import modelControl.OpcatMCManager;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class OpcatExposeOpenExposeModel extends OpcatAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    OpcatExposeKey key;

    public OpcatExposeOpenExposeModel(String name, Icon icon, OpcatExposeKey key) {
        super(name, icon);
        this.key = key;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            super.actionPerformed(e);

            File model = OpcatMCManager.getInstance().doExportToTempDir(
                    key.getModelEncodedURI());

            FileControl.getInstance().runNewOPCAT(model, key.getOpmEntityID());

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }
}
