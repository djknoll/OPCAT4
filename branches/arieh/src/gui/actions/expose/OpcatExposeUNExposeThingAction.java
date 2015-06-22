package gui.actions.expose;

import expose.OpcatExposeConstants.OPCAT_EXPOSE_OP;
import expose.OpcatExposeList;
import gui.Opcat2;
import gui.actions.OpcatAction;
import gui.controls.FileControl;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Instance;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OpcatExposeUNExposeThingAction extends OpcatAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    OpmEntity myEntity;
    Instance myInstance;
    boolean isPublicUnExpose;

    public OpcatExposeUNExposeThingAction(String name, Icon icon,
                                          Instance instance, boolean isPublicUnExpose) {
        super(name, icon);
        this.myEntity = instance.getEntry().getLogicalEntity();
        this.myInstance = instance;
        this.isPublicUnExpose = isPublicUnExpose;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            super.actionPerformed(e);

            OpdProject prj = myInstance.getEntry().getMyProject();

            if (!isPublicUnExpose && myEntity.isPrivateExposed()) {
                int ret = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                        "Are you sure?\nThis Action can not be un-done",
                        "Opcat II", JOptionPane.YES_NO_OPTION);
                if (ret == JOptionPane.YES_OPTION) {
                    prj.getExposeManager().addPrivateExposeChange(myInstance,
                            OPCAT_EXPOSE_OP.DELETE);
                }

            } else if (isPublicUnExpose
                    && myInstance.getEntry().getLogicalEntity()
                    .isPublicExposed()) {
                int ret = JOptionPane.showConfirmDialog(Opcat2.getFrame(),
                        "Are you sure?\nThis Action can not be un-done",
                        "Opcat II", JOptionPane.YES_NO_OPTION);
                if (ret == JOptionPane.YES_OPTION) {
                    prj.getExposeManager().addPublicExposeChange(myInstance,
                            OPCAT_EXPOSE_OP.DELETE);
                }

            }

            OpcatExposeList.getInstance().refresh(
                    FileControl.getInstance().getCurrentProject()
                            .getExposeManager().getExposedList(null, true));

            FileControl.getInstance().getCurrentProject().setCanClose(false);

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }
}
