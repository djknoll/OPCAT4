package gui.actions.expose;

import expose.OpcatExposeKey;
import gui.actions.OpcatAction;
import gui.controls.FileControl;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Instance;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpcatExposeUNUSEExposeAction extends OpcatAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    OpmEntity myEntity;
    Instance myInstance;

    public OpcatExposeUNUSEExposeAction(String name, Icon icon,
                                        Instance instance) {
        super(name, icon);
        this.myEntity = instance.getEntry().getLogicalEntity();
        this.myInstance = instance;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            super.actionPerformed(e);

            OpcatExposeKey myKey = myInstance.getPointer();

            myInstance.getEntry().getLogicalEntity().getRolesManager()
                    .removeRole(myKey.convertKeyToRole());

            myInstance.setPointer(null);

            FileControl.getInstance().getCurrentProject().setCanClose(false);

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }
}
