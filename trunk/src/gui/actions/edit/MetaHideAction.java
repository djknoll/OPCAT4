package gui.actions.edit;

import gui.Opcat2;
import gui.dataProject.MetaDataItem;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import org.w3c.dom.events.EventException;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class MetaHideAction extends EditAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Object metaData;

    MetaLibrary metalib;

    boolean isShowAction = false;

    public MetaHideAction(String name, MetaLibrary meta, boolean isShowAction, Icon icon) {
        super(name, icon);
        this.metalib = meta;
        if (meta != null) {
            this.metaData = meta.getProjectHolder();
        }

        this.isShowAction = isShowAction;
    }

    public MetaHideAction(String name, MetaLibrary meta, boolean isShowAction, boolean showCutMessage, Icon icon) {
        super(name, icon, showCutMessage);
        this.metalib = meta;
        if (meta != null) {
            this.metaData = meta.getProjectHolder();
        }

        this.isShowAction = isShowAction;
    }


    public void actionPerformed(ActionEvent arg0) {
        try {
            super.actionPerformed(arg0);
        } catch (EventException e) {
            JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage()
                    .toString(), "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Opcat2.startWait();


            if (metalib == null) {
                returnToDefault();
            } else {
                returnToDefault();
                if (isShowAction) {
                    hideOthers(metalib);

                } else {
                    hide(metalib);
                }
            }
            if ((edit.getCurrentProject().getCurrentOpd() != null)
                    && (edit.getCurrentProject().getCurrentOpd().getDrawingArea() != null))
                edit.getCurrentProject().getCurrentOpd().getDrawingArea().repaint();

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        } finally {
            Opcat2.endWait();
        }
    }

    private void returnToDefault() {
        Iterator iter = Collections.list(
                edit.getCurrentProject().getSystemStructure().getElements())
                .iterator();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            // if ((ent instanceof ObjectEntry) || (ent instanceof
            // ProcessEntry)) {
            Iterator insIter = Collections.list(ent.getInstances(false)).iterator();
            while (insIter.hasNext()) {
                Instance ins = (Instance) insIter.next();

                if (ins.isMetaHide()) {
                    ins.setVisible(true);
                    ins.setMetaHide(false);
                }

            }
            // }
        }
    }

    private void hideOthers(MetaLibrary meta) {
        Iterator iter = Collections.list(
                edit.getCurrentProject().getSystemStructure().getElements())
                .iterator();
        HashMap<Long, Long> colored = new HashMap<Long, Long>();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            // if ((ent instanceof ObjectEntry) || (ent instanceof
            // ProcessEntry)) {
            int level = -1;
            try {
                Iterator roleIter = ((OpmEntity) ent.getLogicalEntity())
                        .getRolesManager().getRolesVector(meta.getType(),
                                meta.getID()).iterator();
                while (roleIter.hasNext()) {
                    Role role = (Role) roleIter.next();
                    if (role.getThing() instanceof MetaDataItem) {
                        MetaDataItem metaItem = (MetaDataItem) role.getThing();
                        //if (level < metaItem.getColoringLevel()) {
                        //    level = metaItem.getColoringLevel();
                        //}
                    } else {
                        Long id = new Long(role.getThingId());
                        if (colored.containsKey(id)) {
                            level = ((Long) colored.get(id)).intValue();
                        } else {
                            level = colored.values().size() + 1;
                            colored.put(id, new Long(level));
                        }
                    }
                }
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            }
            if (level == -1) {
                if (ent instanceof Entry) {
                    Iterator insIter = Collections.list(ent.getInstances(false))
                            .iterator();
                    while (insIter.hasNext()) {
                        Instance ins = (Instance) insIter.next();
                        ins.setMetaHide(ins.isVisible());
                        ins.setVisible(false);
                    }
                }
            }
            // }
        }

    }

    private void hide(MetaLibrary meta) {
        Iterator iter = Collections.list(
                edit.getCurrentProject().getSystemStructure().getElements())
                .iterator();
        HashMap<Long, Long> colored = new HashMap<Long, Long>();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            // if ((ent instanceof ObjectEntry) || (ent instanceof
            // ProcessEntry)) {
            int level = -1;
            try {
                Iterator roleIter = ((OpmEntity) ent.getLogicalEntity())
                        .getRolesManager().getRolesVector(meta.getType(),
                                meta.getID()).iterator();
                while (roleIter.hasNext()) {
                    Role role = (Role) roleIter.next();
                    if (role.getThing() instanceof MetaDataItem) {
                        MetaDataItem metaItem = (MetaDataItem) role.getThing();
                        //if (level < metaItem.getColoringLevel()) {
                        //    level = metaItem.getColoringLevel();
                        //}
                    } else {
                        Long id = new Long(role.getThingId());
                        if (colored.containsKey(id)) {
                            level = ((Long) colored.get(id)).intValue();
                        } else {
                            level = colored.values().size() + 1;
                            colored.put(id, new Long(level));
                        }
                    }
                }
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            }
            if (level > -1) {
                if (ent instanceof Entry) {
                    Iterator insIter = Collections.list(ent.getInstances(false))
                            .iterator();
                    while (insIter.hasNext()) {
                        Instance ins = (Instance) insIter.next();
                        ins.setMetaHide(ins.isVisible());
                        ins.setVisible(false);
                    }
                }
            }
            // }
        }
    }

}
