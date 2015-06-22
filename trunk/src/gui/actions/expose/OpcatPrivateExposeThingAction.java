package gui.actions.expose;

import expose.OpcatExposeConstants.OPCAT_EXPOSE_OP;
import expose.OpcatExposeList;
import gui.actions.OpcatAction;
import gui.controls.FileControl;
import gui.projectStructure.Instance;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpcatPrivateExposeThingAction extends OpcatAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Instance myEntity;

    public OpcatPrivateExposeThingAction(String name, Icon icon,
                                         Instance opmEntity) {
        super(name, icon);
        this.myEntity = opmEntity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            super.actionPerformed(e);

            myEntity.getEntry().getMyProject().getExposeManager()
                    .addPrivateExposeChange(myEntity, OPCAT_EXPOSE_OP.ADD);

            OpcatExposeList.getInstance().refresh(
                    FileControl.getInstance().getCurrentProject()
                            .getExposeManager().getExposedList(null, true));

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }
}
