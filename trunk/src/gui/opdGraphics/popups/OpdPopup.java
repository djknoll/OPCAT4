package gui.opdGraphics.popups;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.IXCheckResult;
import gui.Opcat2;
import gui.actions.edit.*;
import gui.checkModule.CheckModule;
import gui.checkModule.CheckResult;
import gui.controls.GuiControl;
import gui.dataProject.DataAbstractItem;
import gui.dataProject.DataProject;
import gui.images.opm.OPMImages;
import gui.images.repository.RepositoryImages;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.ProjectPropertiesDialog;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProcess;
import gui.util.VerticalGridLayout;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;

public class OpdPopup extends DefaultPopup {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    CompleteLinksinOPD completeLinksInOPDWithMissing = null;
    CompleteLinksinOPD completeLinksInOPDWithOutMissing = null;


    Opd my = null;

    public OpdPopup(OpdProject prj, int x, int y) {
        super(prj, null);

        if ((Opcat2.getCurrentProject() != null) && (Opcat2.getCurrentProject().getCurrentOpd() != null))
            my = Opcat2.getCurrentProject().getCurrentOpd();

        this.add(this.expand);
        add(new JSeparator());
        completeLinksInOPDWithMissing = new CompleteLinksinOPD(
                "Add Missing Things", StandardImages.COMPLETE_LINKS, true);

        completeLinksInOPDWithOutMissing = new CompleteLinksinOPD(
                "Do Not Add Missing Things",
                StandardImages.COMPLETE_LINKS_NO_THING, false);

        this.add(this.pasteAction);
        this.add(this.deleteAction);

        add(new JSeparator());
        JMenu completeLinksRelations = new JMenu("Complete Links/Relations");
        JMenu completeLinks = new JMenu("Complete Links");
        completeLinks.setIcon(StandardImages.COMPLETE_LINKS);
        completeLinks.add(completeLinksInOPDWithOutMissing);
        completeLinks.add(completeLinksInOPDWithMissing);
        completeLinksRelations.add(completeLinks);

        JMenu completeRelations = new JMenu("Complete Relations");
        JMenu completeRelationsAdd = new JMenu("Add Missing Things");
        JMenu completeRelationsNoAdd = new JMenu("Do Not Add Missing Things");

        completeRelations.add(completeRelationsAdd);
        completeRelations.add(completeRelationsNoAdd);

        completeRelationsAdd.add(new CompleteRElationsinOPD("Add Uni Directional", OPMImages.UNI_DIRECTIONAL, true, OpcatConstants.UNI_DIRECTIONAL_RELATION));
        completeRelationsAdd.add(new CompleteRElationsinOPD("Add Bi Directional", OPMImages.BI_DIRECTIONAL, true, OpcatConstants.BI_DIRECTIONAL_RELATION));
        completeRelationsAdd.add(new CompleteRElationsinOPD("Add SPECIALIZATION", OPMImages.GENERALIZATION, true, OpcatConstants.SPECIALIZATION_RELATION));
        completeRelationsAdd.add(new CompleteRElationsinOPD("Add AGGREGATION", OPMImages.AGGREGATION, true, OpcatConstants.AGGREGATION_RELATION));
        completeRelationsAdd.add(new CompleteRElationsinOPD("Add EXHIBITION", OPMImages.EXHIBITION, true, OpcatConstants.EXHIBITION_RELATION));

        completeRelationsNoAdd.add(new CompleteRElationsinOPD("Add Uni Directional", OPMImages.UNI_DIRECTIONAL, false, OpcatConstants.UNI_DIRECTIONAL_RELATION));
        completeRelationsNoAdd.add(new CompleteRElationsinOPD("Add Bi Directional", OPMImages.BI_DIRECTIONAL, false, OpcatConstants.BI_DIRECTIONAL_RELATION));
        completeRelationsNoAdd.add(new CompleteRElationsinOPD("Add SPECIALIZATION", OPMImages.GENERALIZATION, false, OpcatConstants.SPECIALIZATION_RELATION));
        completeRelationsNoAdd.add(new CompleteRElationsinOPD("Add AGGREGATION", OPMImages.AGGREGATION, false, OpcatConstants.AGGREGATION_RELATION));
        completeRelationsNoAdd.add(new CompleteRElationsinOPD("Add EXHIBITION", OPMImages.EXHIBITION, false, OpcatConstants.EXHIBITION_RELATION));

        completeLinksRelations.add(completeRelations);

        this.add(completeLinksRelations);
        add(new JSeparator());

        JMenu levelColor = new JMenu("Meta Coloring");
        levelColor.setIcon(StandardImages.META_COLOR);
        levelColor
                .setToolTipText("Color things according to Meta Data associations");
        add(levelColor);


        JMenu showData = new JMenu("Meta Data");
        showData.setIcon(StandardImages.META_COLOR);
        showData
                .setToolTipText("Show data according to Meta Data associations");
        add(showData);

        /**
         * TODO: move this to saved settings when we do that
         */
        int menuItemInAColomn = 30;
        /**
         *
         */
        levelColor.getPopupMenu().setLayout(
                new VerticalGridLayout(menuItemInAColomn, 0));

        JMenu implemMenu = new JMenu("Insert Tagged Things");
        implemMenu
                .setToolTipText("Insert Things connected to an External Thing");
        implemMenu.getPopupMenu().setLayout(
                new VerticalGridLayout(menuItemInAColomn, 0));
        implemMenu.setIcon(StandardImages.META_INSERT);
        //JMenu hideMenu = new JMenu("Meta Hide");
        //hideMenu.setIcon(StandardImages.META_HIDE);
        //hideMenu.setToolTipText("Hide Things connected to External Data");
        //hideMenu.getPopupMenu().setLayout(
        //        new VerticalGridLayout(menuItemInAColomn, 0));
        //add(hideMenu);

        //JMenu showMenu = new JMenu("Meta Show");
        //showMenu.setIcon(StandardImages.META_SHOW);
        //showMenu.setToolTipText("Show Things connected to External Data");
        //showMenu.getPopupMenu().setLayout(
        //        new VerticalGridLayout(menuItemInAColomn, 0));
        //add(showMenu);

        JMenuItem colorItem = new JMenuItem(new MetaColoringAction("Default",
                null, StandardImages.META_COLOR_DEF));
        levelColor.add(colorItem);

        //JMenuItem showItem = new JMenuItem(new MetaHideAction("Show All", null,
        //        true, null));
        //showMenu.add(showItem);

        //JMenuItem hideItem = new JMenuItem(new MetaHideAction("Unhide All",
        //        null, false, null));
        //hideMenu.add(hideItem);

        ArrayList<MetaLibrary> metas = new ArrayList<MetaLibrary>();
        metas.addAll(Collections.list(myProject.getMetaLibraries()));
        metas.addAll(Collections.list(myProject.getOpdMetaDataManager()
                .getMetaLibraries()));
        Iterator iter = metas.iterator();
        while (iter.hasNext()) {
            MetaLibrary meta = (MetaLibrary) iter.next();
            if (!meta.isPolicy()) {
                if (meta.getState() == MetaLibrary.STATE_LOADED) {
                    if (meta.getProjectHolder() instanceof DataProject) {
                        DataProject proj = (DataProject) meta
                                .getProjectHolder();
                        if (proj.hasColoringData()) {
                            //hideItem = new JMenuItem(new MetaHideAction(proj
                            //        .getIDType(), meta, false,
                            //        StandardImages.System_Icon));
                            //hideMenu.add(hideItem);

                            //showItem = new JMenuItem(new MetaHideAction(proj
                            //        .getIDType(), meta, true,
                            //        StandardImages.System_Icon));
                            //showMenu.add(showItem);

                            colorItem = new JMenuItem(new MetaColoringAction(
                                    proj.getType(), meta,
                                    StandardImages.System_Icon));
                            levelColor.add(colorItem);
                        }
                    } else {
                        OpdProject proj = (OpdProject) meta.getProjectHolder();
                        //hideItem = new JMenuItem(new MetaHideAction(proj
                        //        .getName(), meta, false,
                        //        StandardImages.System_Icon));
                        //hideMenu.add(hideItem);

                        //showItem = new JMenuItem(new MetaHideAction(proj
                        //        .getName(), meta, true,
                        //        StandardImages.System_Icon));
                        //showMenu.add(showItem);

                        colorItem = new JMenuItem(new MetaColoringAction(proj
                                .getName(), meta, StandardImages.System_Icon));
                        levelColor.add(colorItem);
                    }
                }
            }
        }


        JMenuItem showDataItem = new JMenuItem(new MetaShowData("Default",
                null, StandardImages.META_COLOR_DEF));
        showData.add(showDataItem);

        metas = new ArrayList<MetaLibrary>();
        metas.addAll(Collections.list(myProject.getMetaLibraries()));
        metas.addAll(Collections.list(myProject.getOpdMetaDataManager()
                .getMetaLibraries()));
        iter = metas.iterator();
        while (iter.hasNext()) {
            MetaLibrary meta = (MetaLibrary) iter.next();
            if (!meta.isPolicy()) {
                if (meta.getState() == MetaLibrary.STATE_LOADED) {
                    if (meta.getProjectHolder() instanceof DataProject) {
                        DataProject proj = (DataProject) meta
                                .getProjectHolder();
                        showDataItem = new JMenuItem(new MetaShowData(
                                proj.getType(), meta,
                                StandardImages.System_Icon));
                        showData.add(showDataItem);
                    } else {
                        OpdProject proj = (OpdProject) meta.getProjectHolder();
                        showDataItem = new JMenuItem(new MetaShowData(proj
                                .getName(), meta, StandardImages.System_Icon));
                        showData.add(showDataItem);
                    }
                }
            }
        }


        iter = Collections.list(
                myProject.getMetaLibraries(MetaLibrary.TYPE_CLASSIFICATION)).iterator();

        while (iter.hasNext()) {
            MetaLibrary meta = (MetaLibrary) iter.next();
            if (!meta.isPolicy()) {
                if (meta.getState() == MetaLibrary.STATE_LOADED) {
                    // check for connected roles
                    try {
                        ArrayList<Role> insertRolesThings = new ArrayList<Role>();
                        Iterator rolesIter = meta.getRolesCollection()
                                .iterator();
                        JMenu metaMenu = new JMenu(meta.getName());
                        metaMenu.setIcon(StandardImages.System_Icon);
                        metaMenu.getPopupMenu().setLayout(
                                new VerticalGridLayout(menuItemInAColomn, 0));
                        while (rolesIter.hasNext()) {
                            Role role = (Role) rolesIter.next();
                            if (role.getThing() instanceof DataAbstractItem) {
                                //if( (role.connectedThings(Opcat2.getCurrentProject()) != null) &&  (role.connectedThings(Opcat2.getCurrentProject()).size() > 0  ))
                                insertRolesThings.add(role);
                            }
                        }
                        Collections.sort(insertRolesThings);
                        for (int i = 0; i < insertRolesThings.size(); i++) {
                            Icon icon;
                            if (insertRolesThings.get(i).getThing() instanceof OpmProcess) {
                                icon = RepositoryImages.PROCESS;

                            } else if (insertRolesThings.get(i).getThing() instanceof OpmObject) {
                                icon = RepositoryImages.OBJECT;
                            } else {
                                icon = null;
                            }
                            JMenuItem roleItem = new JMenuItem(
                                    new MetaInsertAction(insertRolesThings
                                            .get(i), icon));
                            metaMenu.add(roleItem);
                        }
                        implemMenu.add(metaMenu);
                    } catch (Exception ex) {
                        OpcatLogger.error(ex);
                    }
                }
            }
        }
        // this.add(new JSeparator());
        add(implemMenu);
        // add(processAction);
        // add(new JSeparator());
        // generateOPLAction.setEnabled(false);
        // add(generateOPLAction);
        this.add(new JSeparator());
        /**
         * too long running time about 15 min to arrange a sd on the ABS
         */
        // this.add(new AutoArrangeAction(prj.getCurrentOpd()));
        // this.add(new JSeparator());
        /**
         *
         */
        this.add(this.propertiesAction);

        if (prj.getCurrentOpd().isViewZoomIn()) {
            this.add(new JSeparator());
            this.add(this.zoomOutAction);
        }
        this.add(new JSeparator());
        this.add(this.BackgroundColorAction);
        this.add(this.RestoreBackgroundColorAction);


        if (my != null) {
            this.add(new JSeparator());
            this.add(this.ShowSimplifiedOPM);
        }


    }

    Action propertiesAction = new AbstractAction("Properties",
            StandardImages.PROPERTIES) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */

        public void actionPerformed(ActionEvent e) {
            ProjectPropertiesDialog ppd = new ProjectPropertiesDialog(
                    OpdPopup.this.myProject, Opcat2.getFrame(),
                    "Project Properties");
            ppd.showDialog();
            OpdPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();
            GuiControl gui = GuiControl.getInstance();
            gui.getRepository().rebuildTrees(true);
        }
    };
    Action zoomOutAction = new AbstractAction("Normal Size",
            StandardImages.ZOOM_OUT) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */

        public void actionPerformed(ActionEvent e) {
            OpdPopup.this.myProject.getCurrentOpd().setViewZoomIn(false);
        }
    };


    Action BackgroundColorAction = new AbstractAction("Background Color",
            null) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */

        public void actionPerformed(ActionEvent e) {
            Color newColor = JColorChooser.showDialog(OpdPopup.this.myProject.getCurrentOpd().getOpdContainer(), "Choose Background Color",
                    OpdPopup.this.myProject.getCurrentOpd().getOpdContainer().getBackground());
            if (newColor != null) {
                OpdPopup.this.myProject.getCurrentOpd().getDrawingArea().setBackground(newColor);
            }

        }
    };


    Action ShowSimplifiedOPM = new AbstractAction("Show Simplified OPM", null) {

        @Override
        public void actionPerformed(ActionEvent e) {
            my.showSimplifiedView();
        }
    };


    Action RestoreBackgroundColorAction = new AbstractAction("Restore default Color",
            null) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */

        public void actionPerformed(ActionEvent e) {
            int ans = JOptionPane.showOptionDialog(Opcat2.getFrame(), "Choose level", "Restore Color", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"model", "OPD", "Cancel"}, new Object[]{"Cancel"});
            if (ans == JOptionPane.YES_OPTION) {
                Enumeration<Opd> opds = OpdPopup.this.myProject.getSystemStructure().getOpds();
                while (opds.hasMoreElements()) {
                    Opd opd = opds.nextElement();
                    opd.getDrawingArea().setBackground(Configuration.getInstance().getColorFromProperty("graphics.default.OPDBackgroundColor"));
                }
                OpdPopup.this.myProject.setCanClose(false);
            }
            if (ans == JOptionPane.NO_OPTION) {
                OpdPopup.this.myProject.getCurrentOpd().getDrawingArea().setBackground(Configuration.getInstance().getColorFromProperty("graphics.default.OPDBackgroundColor"));
                OpdPopup.this.myProject.setCanClose(false);
            }

        }
    };

    Action expand = new AbstractAction("Expand",
            StandardImages.ZOOM_OUT) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */

        public void actionPerformed(ActionEvent e) {
            Opd opd = myProject.getCurrentOpd();
            opd.getDrawingArea().revalidate();
            opd.getDrawingArea().setPreferredSize(new Dimension(2 * opd.getDrawingArea().getWidth(), 2 * opd.getDrawingArea().getHeight()));
            opd.getDrawingArea().repaint();
        }
    };

    Action deleteAction = new AbstractAction("Delete OPD",
            StandardImages.DELETE) {

        /**
         *
         */
        private static final long serialVersionUID = -1284971987046026420L;

        public void actionPerformed(ActionEvent e) {
            // JOptionPane.showMessageDialog(Opcat2.getFrame(), "This option
            // not
            // impemented yet", "Opcat2 - tmp message",
            // JOptionPane.INFORMATION_MESSAGE);

            try {

                Opcat2.startWait();

                CheckResult cr = CheckModule.checkOpdDeletion(
                        OpdPopup.this.myProject.getCurrentOpd(),
                        OpdPopup.this.myProject);

                if (cr.getResult() == IXCheckResult.WRONG) {
                    JOptionPane.showMessageDialog(Opcat2.getFrame(), cr
                            .getMessage(), "Opcat2 - Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (cr.getResult() == IXCheckResult.WARNING) {
                    int retValue = JOptionPane.showConfirmDialog(Opcat2
                            .getFrame(), cr.getMessage()
                            + "\n Do you want to continue?",
                            "Opcat2 - Warning", JOptionPane.YES_NO_OPTION);

                    if (retValue == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                myProject.getExposeManager().startCompundChange();
                OpdPopup.this.myProject.deleteOpd(OpdPopup.this.myProject
                        .getCurrentOpd());
                myProject.getExposeManager().endCompundChange();

                Opcat2.endWait();
                // myProject.getCurrentOpd().getDrawingArea().repaint();
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            } finally {
                myProject.getExposeManager().endCompundChange();
                Opcat2.endWait();
            }
        }

    };

}