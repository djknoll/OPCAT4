package gui.actions.edit;

import gui.controls.FileControl;
import gui.metaLibraries.logic.MetaLibrary;
import gui.projectStructure.*;
import org.w3c.dom.events.EventException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Iterator;

public class MetaColoringAction extends EditAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Object metaData;
    MetaLibrary meta;

    public MetaColoringAction(String name, MetaLibrary meta, Icon icon) {
        super(name, icon);
        this.meta = meta;
        if (meta != null) {
            this.metaData = meta.getProjectHolder();
        }
    }

    public MetaColoringAction(String name, MetaLibrary meta, boolean showCutMessage, Icon icon) {
        super(name, icon, showCutMessage);
        this.meta = meta;
        if (meta != null) {
            this.metaData = meta.getProjectHolder();
        }
    }

    public void actionPerformed(ActionEvent arg0) {
        try {
            super.actionPerformed(arg0);
        } catch (EventException e) {
            JOptionPane.showMessageDialog(this.gui.getFrame(), e.getMessage().toString(), "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        FileControl.getInstance().getCurrentProject().getMetaManager().refreshRoles(FileControl.getInstance().getCurrentProject());

        if (meta == null) {
            returnToDefault();
        } else {
            returnToDefault();
            colorToLevel();
        }
        if ((edit.getCurrentProject().getCurrentOpd() != null) && (edit.getCurrentProject().getCurrentOpd().getDrawingArea() != null)) {
            edit.getCurrentProject().getCurrentOpd().getDrawingArea().repaint();
        }
    }

    private void returnToDefault() {
        Iterator iter = Collections.list(
                edit.getCurrentProject().getSystemStructure().getElements()).iterator();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            if (ent instanceof FundamentalRelationEntry) continue;
            if (ent instanceof LinkEntry) continue;
            // if ((ent instanceof ObjectEntry) || (ent instanceof
            // ProcessEntry)) {
            Iterator insIter = Collections.list(ent.getInstances(false)).iterator();
            while (insIter.hasNext()) {
                Instance ins = (Instance) insIter.next();

                // if (ins instanceof ConnectionEdgeInstance) {
                // ConnectionEdgeInstance obj = (ConnectionEdgeInstance) ins;
                // obj.getConnectionEdge().restoreFromMetaColor();
                // }


                if (ins.getGraphicalRepresentation() != null) {
                    ins.getGraphicalRepresentation().restoreFromMetaColor();
                }

                if (ins instanceof LinkInstance) {
                    LinkInstance link = (LinkInstance) ins;
                    link.getLine().restoreFromMetaColor();
                }

                if (ins instanceof GeneralRelationInstance) {
                    GeneralRelationInstance link = (GeneralRelationInstance) ins;
                    link.getLine().restoreFromMetaColor();
                }

                if (ins instanceof FundamentalRelationInstance) {
                    FundamentalRelationInstance link = (FundamentalRelationInstance) ins;
                    for (int i = 0; i < link.getLines().length; i++) {
                        link.getLines()[i].restoreFromMetaColor();
                    }
                }

            }
            // }
        }
    }

    private void colorToLevel() {
        Iterator iter = Collections.list(
                edit.getCurrentProject().getSystemStructure().getElements()).iterator();

        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            // if ((ent instanceof ObjectEntry) || (ent instanceof
            // ProcessEntry)) {
            Color color = ent.getMetaColor(meta);

            //if (level > -1) {
            if (ent instanceof Entry) {
                if (ent instanceof FundamentalRelationEntry) continue;
                if (ent instanceof LinkEntry) continue;
                Iterator insIter = Collections.list(ent.getInstances(false)).iterator();
                while (insIter.hasNext()) {
                    Instance ins = (Instance) insIter.next();


//						int maxLevel = 0;
//						if (metaData instanceof MetaDataProject) {
//							maxLevel = ((MetaDataProject) metaData)
//									.getMaxColoringLevelValue();
//						} else {
//							maxLevel = ((OpdProject) metaData)
//									.getNumOfEntries();
//						}
                    if (ins.getGraphicalRepresentation() != null) {
                        ins.getGraphicalRepresentation().changeToMetaColor(color);
                    }

                    if (ins instanceof LinkInstance) {
                        LinkInstance link = (LinkInstance) ins;
                        link.getLine().changeToMetaColor(color);
                    }

                    if (ins instanceof GeneralRelationInstance) {
                        GeneralRelationInstance link = (GeneralRelationInstance) ins;
                        link.getLine().changeToMetaColor(color);
                    }

                    if (ins instanceof FundamentalRelationInstance) {
                        FundamentalRelationInstance link = (FundamentalRelationInstance) ins;
                        for (int i = 0; i < link.getLines().length; i++) {
                            link.getLines()[i].changeToMetaColor(
                                    color);
                        }
                    }

                }
                //}
            }
        }
    }
}
