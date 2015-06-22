package gui.opdGraphics.popups;

import database.OpcatDatabaseConnection;
import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.ISystem;
import expose.OpcatExposeConstants.OPCAT_EXOPSE_ADVISOR_TYPE;
import expose.gui.OpcatExposeAdvisorGUI;
import expose.gui.OpcatExposeShowModelUsersAction;
import expose.gui.OpcatExposeShowUsersAction;
import extensionTools.uml.umlDiagrams.DiagramsCreator;
import extensionTools.uml.userInterface.UserDesicion;
import gui.Opcat2;
import gui.actions.edit.CompleteLinksForConnectionEdgeEntry;
import gui.actions.edit.CompleteRelationsForConnectionEdgeEntry;
import gui.actions.edit.InsertPropertyAction;
import gui.actions.expose.*;
import gui.controls.FileControl;
import gui.images.opm.OPMImages;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpcatLayoutManager;
import gui.opdProject.OpdProject;
import gui.opdProject.consistency.ConsistencyAbstractChecker;
import gui.opdProject.consistency.ConsistencyAction;
import gui.opdProject.consistency.ConsistencyFactory;
import gui.opdProject.consistency.ConsistencyResult;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmThing;
import gui.opmEntities.OpmUniDirectionalRelation;
import gui.projectStructure.*;
import gui.scenarios.Scenario;
import gui.scenarios.ScenarioUtils;
import gui.scenarios.TestingScenariosPanel;
import gui.util.CustomFileFilter;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import util.OpcatLogger;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ThingPopup extends DefaultPopup {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private ThingInstance ti;
    CompleteLinksForConnectionEdgeEntry completeLinksActionWithOutAddMissing = null;
    CompleteLinksForConnectionEdgeEntry completeLinksActionWithAddMissing = null;

    public ThingPopup(OpdProject prj, Instance inst, int x, int y) {
        super(prj, inst);
        this.ti = (ThingInstance) inst;// (ThingInstance)myProject.getCurrentOpd().getSelectedItem();

        boolean env = ti.getEntry().getLogicalEntity().isEnviromental();
        boolean hasRoles = (ti.getEntry().getLogicalEntity().getRolesManager().getLoadedPoliciesRolesVector().size() > 0);
        boolean ext = (env && hasRoles) || (ti.isPointerInstance());


        completeLinksActionWithAddMissing = new CompleteLinksForConnectionEdgeEntry(
                "Add Missing Things", StandardImages.COMPLETE_LINKS,
                (ConnectionEdgeInstance) inst, true);
        completeLinksActionWithOutAddMissing = new CompleteLinksForConnectionEdgeEntry(
                "Do Not Add Missing Things",
                StandardImages.COMPLETE_LINKS_NO_THING,
                (ConnectionEdgeInstance) inst, false);

        ThingEntry myEntry = (ThingEntry) ti.getEntry();


        this.add(this.expand);
        this.add(new JSeparator());


        if (myEntry.getZoomedInOpd() == null) {
            this.add(this.zoomInAction);
        } else {
            this.add(this.gotoZoominAction);
        }

        if (myEntry.getUnfoldingOpd() == null) {

            this.add(this.unfoldingAction);
        } else {
            this.add(this.gotoUnfoldAction);
        }

        this.add(this.createViewAction);

        if (ext) {
            unfoldingAction.setEnabled(false);
        }
        if (ext) {
            gotoUnfoldAction.setEnabled(false);
        }
        if (ext) {
            zoomInAction.setEnabled(false);
        }
        if (ext) {
            gotoZoominAction.setEnabled(false);
        }
        if (ext) {
            createViewAction.setEnabled(false);
        }

        // if (OpcatMCManager.isOnline() && OpcatMCManager.isConnectedToerver())
        // {
        this.add(new JSeparator());

        JMenuItem item;

        // OpcatExposeManager.getInstance();

        JMenu exposeMenu = new JMenu("Expose");

        // boolean removedExpose = (myEntry.getMyProject().getExposeManager()
        // .getExposeLastChange(myEntry.getId()) == OPCAT_EXPOSE_OP.DELETE);

        // boolean addedExpose = (myEntry.getMyProject().getExposeManager()
        // .getExposeLastChange(myEntry.getId()) == OPCAT_EXPOSE_OP.ADD);

        boolean isPointer = myInstance.isPointerInstance();

        boolean privateExposed = myInstance.getEntry().getLogicalEntity().isPrivateExposed();

        boolean showPublicExposedUpdate = (myEntry.getLogicalEntity().isPublicExposed());

        // public expose menu.
        exposeMenu.add(new JSeparator());
        if (showPublicExposedUpdate) {
            item = new JMenuItem(new OpcatExposeUpdateExposedThingAction(
                    "Update Public Exposure Information", null, myEntry.getLogicalEntity()));
            exposeMenu.add(item);
            item.setEnabled(OpcatMCManager.isOnline() && OpcatDatabaseConnection.isOnLine());

            exposeMenu.add(new OpcatExposeUNExposeThingAction("Public Un-expose",
                    StandardImages.CLONE, myInstance, true));

        } else if (!myEntry.getLogicalEntity().isPublicExposed()) {
            if ((FileControl.getInstance().getCurrentProject() != null) && (FileControl.getInstance().getCurrentProject().getPath() != null)) {
                if (FileControl.getInstance().getCurrentProject().getMcURL() != null) {
                    exposeMenu.add(new OpcatExposeThingAction("Public Expose",
                            StandardImages.CLONE, this.myInstance));
                } else {
                    item = exposeMenu.add("Public Expose - not MC file");
                    exposeMenu.add(item);
                    item.setEnabled(false);
                }
            } else {
                item = exposeMenu.add("Public Expose - new File");
                exposeMenu.add(item);
                item.setEnabled(false);
            }

        }

        // private expose menu
        exposeMenu.add(new JSeparator());
        if (privateExposed) {
            exposeMenu.add(new OpcatExposeUNExposeThingAction(
                    "Private Un-expose", StandardImages.CLONE, myInstance,
                    false));
        } else {
            if ((FileControl.getInstance().getCurrentProject() != null) && (FileControl.getInstance().getCurrentProject().getPath() != null)) {
                SVNURL url = myProject.getMcURL();
                if (url != null) {
                    exposeMenu.add(new JSeparator());
                    exposeMenu.add(new OpcatPrivateExposeThingAction(
                            "Private Expose", StandardImages.CLONE,
                            this.myInstance));
                } else {
                    item = exposeMenu.add("Private Expose - not in MC");
                    exposeMenu.add(item);
                    item.setEnabled(false);
                }
            } else {
                item = exposeMenu.add("Private Expose - new File");
                exposeMenu.add(item);
                item.setEnabled(false);
            }
        }

        // expose report


        if ((FileControl.getInstance().getCurrentProject() != null) && (FileControl.getInstance().getCurrentProject().getPath() != null)) {
            SVNURL url = myProject.getMcURL();

            if (url != null) {
                if (myEntry.getLogicalEntity().isExposed()) {
                    try {
                        item = exposeMenu.add(new OpcatExposeShowUsersAction(
                                "Show Successors", null, myEntry));
                        item.setEnabled(OpcatMCManager.isOnline() && OpcatDatabaseConnection.isOnLine());

                    } catch (Exception e) {
                    }
                }

                // used menus
                exposeMenu.add(new JSeparator());
                if (isPointer) {
                    item = exposeMenu.add(new OpcatExposeShowModelUsersAction(
                            "Show Local Successors", null, myProject, myInstance.getPointer()));

                    if (!myInstance.getPointer().isPrivate()) {
                        item = exposeMenu.add(new OpcatExposeOpenExposeModel(
                                "Open Parent model", null, myInstance.getPointer()));
                        item.setEnabled(OpcatMCManager.isOnline());

                    } else {
                        Entry entry = myProject.getSystemStructure().getEntry(
                                myInstance.getPointer().getOpmEntityID());
                        if (entry != null) {
                            Instance ins = entry.getInstances(false).nextElement();
                            exposeMenu.add(new OpcatExposeShowEntity(
                                    "Show Parent Thing", null, ins));
                        }
                    }

                    exposeMenu.add(new JSeparator());
                    if (!myInstance.getPointer().isPrivate()) {
                        item = exposeMenu.add(new OpcatExposeUNUSEExposeAction(
                                "Release Public Exposed", null, myInstance));
                        item.setEnabled(OpcatMCManager.isOnline() && OpcatDatabaseConnection.isOnLine());
                    } else {
                        item = exposeMenu.add(new OpcatExposeUNUSEExposeAction(
                                "Release Private Exposed", null, myInstance));
                    }

                }

                this.add(exposeMenu);
                // if (ti.getEntry().getMyProject().isCanClose()) {
                if (ti.isPointerInstance() || (ti.getEntry().getLogicalEntity().getRolesManager().getRolesCollection().size() > 0)) {

                    OpcatExposeAdvisorGUI local_inter = new OpcatExposeAdvisorGUI(ti,
                            OPCAT_EXOPSE_ADVISOR_TYPE.INTERFACE_LOCAL);
                    item = this.add(local_inter.init());

                    // OpcatExposeAdvisorGUI root_inter = new OpcatExposeAdvisorGUI(ti,
                    // OPCAT_EXOPSE_ADVISOR_TYPE.INTERFACE_ROOT);
                    // item = this.add(root_inter.init());

                    OpcatExposeAdvisorGUI recomender = new OpcatExposeAdvisorGUI(ti,
                            OPCAT_EXOPSE_ADVISOR_TYPE.PROPERTIES_FROM);
                    item = this.add(recomender.init());

                    OpcatExposeAdvisorGUI to = new OpcatExposeAdvisorGUI(ti,
                            OPCAT_EXOPSE_ADVISOR_TYPE.PROERTIES_TO);
                    item = this.add(to.init());

                } else {
                    item = new JMenuItem("Advisor - not a used thing");
                    item.setEnabled(false);
                    this.add(item);
                }
            }
        }
        // } else {
        // item = new JMenuItem("Advisor - model not saved");
        // item.setEnabled(false);
        // this.add(item);
        // }

        // if (ti.getEntry().getMyProject().isCanClose()) {
        // if (!ti.isPointerInstance()
        // && (ti.getEntry().getLogicalEntity().getRolesManager()
        // .getRolesCollection().size() > 0)) {
        //
        // Recommender recommender = new Recommender(ti, myProject);
        // this.add(recommender.init());
        // } else {
        // item = new JMenuItem("Template Advisor - not template");
        // item.setEnabled(false);
        // this.add(item);
        // }
        // } else {
        // item = new JMenuItem("Template Advisor - model not saved");
        // item.setEnabled(false);
        // this.add(item);
        // }

        this.add(new JSeparator());

        this.add(this.showAction);

        // properties menu
        JMenu sonsMenu = new JMenu("Insert property ");
        sonsMenu.setIcon(StandardImages.EMPTY);

        // add direct sons
        JMenu direct = new JMenu("Direct ");
        sonsMenu.add(direct);
        direct.addMenuListener(this.directAction);
        if (ext) {
            direct.setEnabled(false);
        }

        // add inharated ons
        JMenu inharated = new JMenu("Inherited ");
        sonsMenu.add(inharated);
        inharated.addMenuListener(this.inharatedAction);
        if (ext) {
            inharated.setEnabled(false);
        }

        JMenu meta = new JMenu("From Template ");
        // sonsMenu.add(meta);
        // meta direct
        JMenu metadirect = new JMenu("Direct ");
        metadirect.addMenuListener(this.metadirectAction);
        meta.add(metadirect);

        // meta inherited
        JMenu metainharated = new JMenu("Inherited ");
        metainharated.addMenuListener(this.metainharatedAction);
        meta.add(metainharated);

        JMenu rolesinherited = new JMenu("From Inherited Roles ");
        rolesinherited.addMenuListener(this.rolesinharatedAction);
        meta.add(rolesinherited);

        this.add(sonsMenu);
        this.add(new JSeparator());


        JMenu completeLinksRelations = new JMenu("Complete Links/Relations ");
        JMenu completeLinks = new JMenu("Complete Links");
        completeLinks.setIcon(StandardImages.COMPLETE_LINKS);
        completeLinks.add(completeLinksActionWithOutAddMissing);
        completeLinks.add(completeLinksActionWithAddMissing);
        completeLinksRelations.add(completeLinks);

        JMenu completeRelations = new JMenu("Complete Relations");
        JMenu completeRelationsAdd = new JMenu("Add Missing Things");
        JMenu completeRelationsNoAdd = new JMenu("Do Not Add Missing Things");

        completeRelations.add(completeRelationsAdd);
        completeRelations.add(completeRelationsNoAdd);

        completeRelationsAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add Uni Directional", OPMImages.UNI_DIRECTIONAL, (ConnectionEdgeInstance) myInstance, true, OpcatConstants.UNI_DIRECTIONAL_RELATION));
        completeRelationsAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add Bi Directional", OPMImages.BI_DIRECTIONAL, (ConnectionEdgeInstance) myInstance, true, OpcatConstants.BI_DIRECTIONAL_RELATION));
        completeRelationsAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add SPECIALIZATION", OPMImages.GENERALIZATION, (ConnectionEdgeInstance) myInstance, true, OpcatConstants.SPECIALIZATION_RELATION));
        completeRelationsAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add AGGREGATION", OPMImages.AGGREGATION, (ConnectionEdgeInstance) myInstance, true, OpcatConstants.AGGREGATION_RELATION));
        completeRelationsAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add EXHIBITION", OPMImages.EXHIBITION, (ConnectionEdgeInstance) myInstance, true, OpcatConstants.EXHIBITION_RELATION));

        completeRelationsNoAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add Uni Directional", OPMImages.UNI_DIRECTIONAL, (ConnectionEdgeInstance) myInstance, false, OpcatConstants.UNI_DIRECTIONAL_RELATION));
        completeRelationsNoAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add Bi Directional", OPMImages.BI_DIRECTIONAL, (ConnectionEdgeInstance) myInstance, false, OpcatConstants.BI_DIRECTIONAL_RELATION));
        completeRelationsNoAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add SPECIALIZATION", OPMImages.GENERALIZATION, (ConnectionEdgeInstance) myInstance, false, OpcatConstants.SPECIALIZATION_RELATION));
        completeRelationsNoAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add AGGREGATION", OPMImages.AGGREGATION, (ConnectionEdgeInstance) myInstance, false, OpcatConstants.AGGREGATION_RELATION));
        completeRelationsNoAdd.add(new CompleteRelationsForConnectionEdgeEntry("Add EXHIBITION", OPMImages.EXHIBITION, (ConnectionEdgeInstance) myInstance, false, OpcatConstants.EXHIBITION_RELATION));

        completeLinksRelations.add(completeRelations);

        this.add(completeLinksRelations);

//
//        JMenu completeLinks = new JMenu("Complete Links ");
//        completeLinks.setIcon(StandardImages.COMPLETE_LINKS);
//        completeLinks.add(completeLinksActionWithOutAddMissing);
//        completeLinks.add(completeLinksActionWithAddMissing);
//        this.add(completeLinks);


        this.add(new JSeparator());
        this.add(this.copyAction);
        this.add(this.copyToParentAction);
        this.copyToParentAction.setToOpd(ti.getOpd().getParentOpd());

        this.add(this.cutAction);
        // System.err.println(ti);

        if (!(this.ti.getThing().isZoomedIn())) {
            this.pasteAction.setEnabled(false);
        }
        // add(pasteAction);
        this.add(this.pasteAction);

        this.add(this.deleteAction);
        this.add(new JSeparator());

        this.add(this.bringToFrontAction);
        this.add(this.sendToBackAction);
        this.add(this.fitToContent);

        JMenu alingMenu = new JMenu("Align");
        alingMenu.setIcon(StandardImages.EMPTY);
        alingMenu.add(this.alignLeftAction);
        alingMenu.add(this.alignRightAction);
        alingMenu.add(this.alignTopAction);
        alingMenu.add(this.alignBottomAction);
        alingMenu.add(this.alignCenterVerticallyAction);
        alingMenu.add(this.alignCenterHorizontallyAction);
        alingMenu.add(new JSeparator());
        alingMenu.add(this.alignAsTreeAction);

        this.add(alingMenu);
        if ((this.myProject.getCurrentOpd() == null) || (this.myProject.getCurrentOpd().getSelectedItemsHash().size() < 2)) {
            alingMenu.setEnabled(false);
        }

        this.add(new JSeparator());
        this.add(this.ConsistencyHelper);
        this.add(this.TestingCenter);
        this.add(new JSeparator());
        this.add(this.propertiesAction);
        this.add(this.parametersAction);
        if (myEntry instanceof ObjectEntry) {
            this.add(new JSeparator());
            this.add(this.showMeasurmentUnit);
            if (!(((OpmObject) myEntry.getLogicalEntity()).isMesurementUnitExists())) {
                showMeasurmentUnit.setEnabled(false);
            }
        }
        this.add(new JSeparator());

        if ((this.myInstance instanceof ObjectInstance) && (((ObjectInstance) this.myInstance).getUnfoldingOpd() != null)) {
            this.add(this.GenerateClassDiagram);
            // this.GenerateClassDiagram.setEnabled(false);
        }

        // if(this.myInstance instanceof ProcessInstance) {
        // this.add(this.GenerateUseCaseDiagram);
        // }

        // ArrayList<Role> roles = ti.getEntry().getLogicalEntity()
        // .getRolesManager().getRolesCollectionIncludingInerated();

        // HashMap<Role, ArrayList<Entry>> show = OpcatExposeObjectAdvisor
        // .getAdvices((ThingEntry) ti.getEntry());
        // for (Role i : show.keySet()) {
        // System.out.println("Start " + i.toString());
        // for (Entry entry : show.get(i)) {
        // System.out.println("Son " + entry.getName());
        // }
        // System.out.println("End " + i.toString());
        //
        // }

    }

    Action showAction = new AbstractAction("Show all appearances",
            StandardImages.SHOW_APPR) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         *
         */
        public void actionPerformed(ActionEvent e) {

            ThingPopup.this.ti.getEntry().ShowInstances();

        }
    };

    private JMenu fillPropertiesMenu(HashMap hmap, JMenu menu, boolean metalib,
                                     Role role) {
        Iterator sonsIter = hmap.keySet().iterator();

        while (sonsIter.hasNext()) {
            ThingEntry thing = (ThingEntry) sonsIter.next();
            FundamentalRelationEntry relation = (FundamentalRelationEntry) hmap.get(thing);
            // only show Thing if it is not connected in this OPD
            boolean isThingInOPD = (myProject.getSystemStructure().getInstanceInOpd(ti.getOpd(), thing.getId()) != null);
            boolean showSon = true;

            // now check if son is alrady connected so we don't have to show
            // it
            if (isThingInOPD && !metalib) {
                // see if ti is connected to Son.
                Iterator iter = Collections.list(
                        ti.getRelatedSourceFundamentalRelation()).iterator();
                while (iter.hasNext()) {
                    FundamentalRelationInstance relIns = (FundamentalRelationInstance) iter.next();
                    if (relIns.getDestinationInstance().getEntry().getId() == thing.getId()) {
                        showSon = false;
                        break;
                    }
                }

            }
            // boolean isRelationINOpd = (myProject.getSystemStructure()
            // .getInstanceInOpd(ti.getOpd(), relation.getId()) != null);

            if (showSon && !metalib) {
                JMenuItem son = new JMenuItem(thing.getName());
                ActionListener sonsActionListner = new InsertPropertyAction(ti,
                        thing, relation, false, null);
                son.addActionListener(sonsActionListner);
                menu.add(son);
            }
            if (metalib) {
                JMenuItem son = new JMenuItem(thing.getName());
                ActionListener sonsActionListner = new InsertPropertyAction(ti,
                        thing, relation, true, role);
                son.addActionListener(sonsActionListner);
                menu.add(son);
            }
        }

        return menu;
    }

    MenuListener directAction = new MenuListener() {

        private static final long serialVersionUID = 1L;

        public void menuCanceled(MenuEvent e) {
        }

        public void menuDeselected(MenuEvent e) {
        }


        public void menuSelected(MenuEvent e) {
            JMenu direct = (JMenu) e.getSource(); // getValue("Menu") ;
            direct.removeAll();


            ArrayList<String> exclodes = new ArrayList<String>();
            exclodes.add(OpmUniDirectionalRelation.class.getCanonicalName());
            exclodes.add(OpmBiDirectionalRelation.class.getCanonicalName());

            HashMap sons = ((ThingEntry) ti.getEntry()).getAllFundemantulRelationSons(exclodes);
            fillPropertiesMenu(sons, direct, false, null);

        }
    };
    MenuListener inharatedAction = new MenuListener() {

        private static final long serialVersionUID = 1L;

        public void menuCanceled(MenuEvent e) {
        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuSelected(MenuEvent e) {
            ((JMenu) e.getSource()).removeAll();
            HashMap sons = ((ThingEntry) ti.getEntry()).getAllInharatedProperties();
            fillPropertiesMenu(sons, (JMenu) e.getSource(), false, null);

        }
    };
    MenuListener metainharatedAction = new MenuListener() {

        private static final long serialVersionUID = 1L;

        public void menuCanceled(MenuEvent e) {
        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuSelected(MenuEvent e) {
            ((JMenu) e.getSource()).removeAll();
            Iterator rolesIter = ti.getEntry().getLogicalEntity().getRolesManager().getLoadedRolesVector(
                    MetaLibrary.TYPE_POLICY).iterator();
            while (rolesIter.hasNext()) {
                Role role = (Role) rolesIter.next();
                OpdProject rolesProject = (OpdProject) role.getOntology().getProjectHolder();
                ThingEntry entry = (ThingEntry) rolesProject.getSystemStructure().getEntry(
                        ((OpmThing) role.getThing()).getId());
                if (entry != null) {
                    HashMap sons = entry.getAllInharatedProperties();
                    fillPropertiesMenu(sons, (JMenu) e.getSource(), true, role);
                }
            }
        }
    };
    MenuListener rolesinharatedAction = new MenuListener() {

        private static final long serialVersionUID = 1L;

        public void menuCanceled(MenuEvent e) {
        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuSelected(MenuEvent e) {
            ((JMenu) e.getSource()).removeAll();
            HashMap rolesMap = ((ThingEntry) ti.getEntry()).getAllIneratenceRoles();
            Iterator rolesIter = rolesMap.keySet().iterator();
            while (rolesIter.hasNext()) {
                Role role = (Role) rolesIter.next();
                OpdProject rolesProject = (OpdProject) role.getOntology().getProjectHolder();
                ThingEntry entry = (ThingEntry) rolesProject.getSystemStructure().getEntry(
                        ((OpmThing) role.getThing()).getId());
                if (entry != null) {
                    JMenu roleMenu = new JMenu(entry.getName());
                    JMenu direct = new JMenu("Direct");
                    JMenu inherited = new JMenu("Inherited");
                    roleMenu.add(direct);
                    roleMenu.add(inherited);
                    HashMap sons = entry.getAllInharatedProperties();
                    fillPropertiesMenu(sons, inherited, true, role);
                    sons = entry.getAllFundemantulRelationSons();
                    fillPropertiesMenu(sons, direct, true, role);
                    ((JMenu) e.getSource()).add(roleMenu);
                }
            }
        }
    };
    MenuListener metadirectAction = new MenuListener() {

        private static final long serialVersionUID = 1L;

        public void menuCanceled(MenuEvent e) {
        }

        public void menuDeselected(MenuEvent e) {
        }

        public void menuSelected(MenuEvent e) {
            ((JMenu) e.getSource()).removeAll();
            Iterator rolesIter = ti.getEntry().getLogicalEntity().getRolesManager().getLoadedRolesVector(
                    MetaLibrary.TYPE_POLICY).iterator();
            while (rolesIter.hasNext()) {
                Role role = (Role) rolesIter.next();
                OpdProject rolesProject = (OpdProject) role.getOntology().getProjectHolder();
                ThingEntry entry = (ThingEntry) rolesProject.getSystemStructure().getEntry(
                        ((OpmThing) role.getThing()).getId());
                if (entry != null) {
                    HashMap sons = entry.getAllFundemantulRelationSons();
                    fillPropertiesMenu(sons, (JMenu) e.getSource(), true, role);
                }
            }
            // HashMap sons = ((ThingEntry) ti.getEntry())
            // .getAllInharatedProperties();
            // fillPropertiesMenu(sons, (JMenu) e.getSource());

        }
    };
    Action zoomInAction = new AbstractAction("In-Zoom", StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 7921410206025464287L;

        public void actionPerformed(ActionEvent e) {

            myProject.getExposeManager().startCompundChange();
            try {
                Opcat2.startWait();


                ThingPopup.this.myProject.zoomIn();

            } catch (Exception e1) {
                OpcatLogger.error(e1);
            } finally {
                myProject.getExposeManager().endCompundChange();
                Opcat2.endWait();
            }
        }
    };
    Action unfoldingAction = new AbstractAction("Unfold", StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -667563739626134447L;

        public void actionPerformed(ActionEvent e) {
            myProject.getExposeManager().startCompundChange();

            try {

                Opcat2.startWait();


                ThingPopup.this.myProject.unfolding();


            } catch (Exception e1) {
                OpcatLogger.error(e1);
            } finally {
                myProject.getExposeManager().endCompundChange();
                Opcat2.endWait();
            }
        }
    };
    Action createViewAction = new AbstractAction("Create View",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -667563739626134447L;

        public void actionPerformed(ActionEvent e) {

            myProject.getExposeManager().startCompundChange();

            try {

                Opcat2.startWait();


                ThingPopup.this.myProject.createView();


            } catch (Exception e1) {
                OpcatLogger.error(e1);
            } finally {
                myProject.getExposeManager().endCompundChange();
                Opcat2.endWait();
            }
        }
    };
    Action gotoUnfoldAction = new AbstractAction("Show Unfolded OPD",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -667563739626134447L;

        public void actionPerformed(ActionEvent e) {
            try {
                Opcat2.getCurrentProject().showOPD(
                        ((ThingEntry) ti.getEntry()).getUnfoldingOpd().getOpdId(), false);
            } catch (Exception e1) {
                OpcatLogger.error(e1);
            }
        }
    };
    Action gotoZoominAction = new AbstractAction("Show In-zoomed OPD",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -667563739626134447L;

        public void actionPerformed(ActionEvent e) {
            try {
                Opcat2.getCurrentProject().showOPD(
                        ((ThingEntry) ti.getEntry()).getZoomedInOpd().getOpdId(), false);
            } catch (Exception e1) {
                OpcatLogger.error(e1);
            }
        }
    };
    Action alignLeftAction = new AbstractAction("Left", StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -5483421693041278633L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2Left();
        }
    };
    Action alignRightAction = new AbstractAction("Right", StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 239503285663982807L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2Right();
        }
    };
    Action alignTopAction = new AbstractAction("Top",
            StandardImages.ALIGN_VERTICAL_TOP) {

        /**
         *
         */
        private static final long serialVersionUID = 2305020403623966419L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2Top();
        }
    };
    Action alignBottomAction = new AbstractAction("Bottom",
            StandardImages.ALIGN_VERTICAL_BOTTOM) {

        /**
         *
         */
        private static final long serialVersionUID = 7814303177070480931L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2Bottom();
        }
    };
    Action alignCenterVerticallyAction = new AbstractAction(
            "Center Vertically", StandardImages.ALIGN_VERTICAL_CENTER) {

        /**
         *
         */
        private static final long serialVersionUID = 1316613334680687140L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2CenterVertically();
        }
    };
    Action alignCenterHorizontallyAction = new AbstractAction(
            "Center Horizontally", StandardImages.ALIGN_HORIZONTAL_CENTER) {

        /**
         *
         */
        private static final long serialVersionUID = 6662070932673168893L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().align2CenterHorizontally();
        }
    };
    Action alignAsTreeAction = new AbstractAction("As Tree",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -5193633177133700618L;

        public void actionPerformed(ActionEvent e) {
            ThingPopup.this.myProject.getLayoutManager().alignAsTree();
        }
    };
    Action alignVertically = new AbstractAction("Vertically",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -5193633177133700618L;

        public void actionPerformed(ActionEvent e) {

            ((ObjectInstance) ti).setStatesAutoArranged(false);

            myProject.removeSelection();
            ti.getOpd().getSelection().resetSelection();
            ti.getOpd().getSelection().removeSelection();

            ti.getOpd().addSelection(ti, false);

            OpcatLayoutManager myLayoutManager = myProject.getLayoutManager();
            for (Enumeration enumSelected = ((ObjectInstance) ti).getStateInstances(); enumSelected.hasMoreElements(); ) {
                Object currItem = enumSelected.nextElement();
                ConnectionEdgeInstance currInstance = (ConnectionEdgeInstance) currItem;
                currInstance.getOpd().addSelection(currInstance, false);
            }

            int x = ti.getX();
            int y = ti.getY();

            myLayoutManager.evenSpaceVertically();

            ti.setLocation(x, y);

            myProject.removeSelection();
            ti.getOpd().getSelection().resetSelection();
            ti.getOpd().getSelection().removeSelection();

        }
    };
    Action parametersAction = new AbstractAction("Parameters",
            StandardImages.PROPERTIES) {

        /**
         *
         */
        private static final long serialVersionUID = 1764163162957061291L;

        public void actionPerformed(ActionEvent e) {
            ArrayList<OpcatProperties> list = new ArrayList<OpcatProperties>();
            ti.getMyProps().put(OpcatProperties.instanceOrderKey, String.valueOf(ti.getOrder()));
            list.add(ti.getMyProps());
            list.add(ti.getEntry().getMyProps());
            list.add(ti.getEntry().getLogicalEntity().getMyProps());
            GridPanel panel = ti.getMyProps().getPropertiesGRIDPanel(list, OpcatProperties.OpcatPropertiesGroups.ALL, ti.getEntry().getName());
            panel.AddToExtensionToolsPanel(true);

            final ThingEntry entry = (ThingEntry) ti.getEntry();
            if (entry.getMyProps().hasMCURL()) {
                JButton urlButton = new JButton("Show Referenced");
                ActionListener urlListner = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        entry.getMyProps().ShowURL((entry.getProperty("object.rpg.ReferencedField.Field") != null ? entry.getProperty("object.rpg.ReferencedField.Field") : entry.getName()));
                    }
                };
                urlButton.addActionListener(urlListner);
                panel.getButtonPane().add(urlButton);
            }
        }
    };
    Action propertiesAction = new AbstractAction("Properties",
            StandardImages.PROPERTIES) {

        /**
         *
         */
        private static final long serialVersionUID = 1764163162957061291L;

        public void actionPerformed(ActionEvent e) {
            (ti).getThing().callPropertiesDialog(
                    BaseGraphicComponent.SHOW_ALL_TABS,
                    BaseGraphicComponent.SHOW_ALL_BUTTONS);
            ThingPopup.this.myProject.getCurrentOpd().getDrawingArea().repaint();

        }
    };
    Action ConsistencyHelper = new AbstractAction("Consistency Helper",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 1764163162957061291L;

        public void actionPerformed(ActionEvent e) {

            ConsistencyResult checkResult = new ConsistencyResult();
            ConsistencyAbstractChecker checker = (ConsistencyAbstractChecker) (new ConsistencyFactory(
                    ti, myProject, ConsistencyAction.GLOBAL, checkResult)).create();
            checker.check();
            if (!checkResult.isConsistent()) {
                checker.deploy(checkResult);
            }
        }
    };
    Action TestingCenter = new AbstractAction("Test Scenario",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 1764163162957061291L;

        public void actionPerformed(ActionEvent e) {

            TestingScenariosPanel panel = ScenarioUtils.getScenaioPanel(
                    new Scenario(ti.getEntry().getName()),
                    (ConnectionEdgeEntry) ti.getEntry());

            panel.AddToExtensionToolsPanel(true);
        }
    };
    Action showMeasurmentUnit = new AbstractAction("Measurement Unit",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 1764163162957061291L;

        public void actionPerformed(ActionEvent e) {

            ((ObjectInstance) myInstance).showMeasurementUnit();
        }
    };
    Action GenerateUseCaseDiagram = new AbstractAction("Generate Use-Case",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         * performs the XMI generation task. The action will be performed only
         * to objects that have an unfolding OPD.
         *
         * @author Raanan (modified by Eran)
         */
        public void actionPerformed(ActionEvent e) {
            // if ((myInstance instanceof ObjectInstance)
            // && (((ObjectInstance) myInstance).getUnfoldingOpd() != null))
            // {

            Opcat2.startWait();


            DiagramsCreator xmiCreator = new DiagramsCreator();
            CustomFileFilter filter = new CustomFileFilter("xmi",
                    "UML XMI Files");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(filter);
            int ret = fileChooser.showSaveDialog(Opcat2.getFrame());

            if (ret == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getPath();
                StringTokenizer st = new StringTokenizer(filename, ".", false);
                filename = st.nextToken() + ".xmi";

                UserDesicion desicion = new UserDesicion();
                desicion.UClassYes = false;
                desicion.UUAll = false;
                desicion.UUNone = false;
                desicion.UURoot = true;
                desicion.UUUntil = true;
                desicion.UUUntilText = "1";

                try {
                    Vector vec = new Vector();
                    ProcessEntry process = (ProcessEntry) ti.getEntry();
                    vec.addAll(process.getConnectedThings().values());
                    vec.add(process);

                    xmiCreator.setBaseEnum(vec.elements());
                    xmiCreator.generateXmiFile((ISystem) Opcat2.getCurrentProject(), filename, desicion);

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                }
            }

            Opcat2.endWait();
        }
        // }
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

            Dimension old = ((ThingInstance) myInstance).getThing().getSize();
            int rel = 2;
            ((ThingInstance) myInstance).getThing().setSize(new Dimension(rel * ((ThingInstance) myInstance).getThing().getWidth(), rel * ((ThingInstance) myInstance).getThing().getHeight()));
            Entry entry = myInstance.getEntry();
            Enumeration<Instance> instances = myProject.getSystemStructure().getInstancesInOpd(myInstance.getOpd().getOpdId());
            while (instances.hasMoreElements()) {
                Instance ins = instances.nextElement();
                if (ins instanceof ThingInstance) {
                    if (ins.getParent() != null) {
                        if (ins.getParent().getInstance().getKey().equals(myInstance.getKey())) {
                            ThingInstance thing = (ThingInstance) ins;
                            ((ThingInstance) ins).setLocation(thing.getX(), thing.getY());
                        }
                    }
                }
            }
            ((ThingInstance) myInstance).getThing().repaint();
            myInstance.getOpd().refit();
        }
    };
    Action GenerateClassDiagram = new AbstractAction("Generate UML",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        /**
         * performs the XMI generation task. The action will be performed only
         * to objects that have an unfolding OPD.
         *
         * @author Raanan (modified by Eran)
         */
        public void actionPerformed(ActionEvent e) {
            // if ((myInstance instanceof ObjectInstance)
            // && (((ObjectInstance) myInstance).getUnfoldingOpd() != null))
            // {

            Opcat2.startWait();
            ;

            ObjectInstance objIns = (ObjectInstance) myInstance;
            DiagramsCreator xmiCreator = new DiagramsCreator();

            CustomFileFilter filter = new CustomFileFilter("xmi",
                    "UML XMI Files");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(filter);
            int ret = fileChooser.showSaveDialog(Opcat2.getFrame());

            if (ret == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooser.getSelectedFile().getPath();
                StringTokenizer st = new StringTokenizer(filename, ".", false);
                filename = st.nextToken() + ".xmi";

                UserDesicion desicion = new UserDesicion();
                desicion.UClassYes = true;

                try {
                    Vector vec = new Vector();
                    myProject.removeSelection();
                    objIns.getUnfoldingOpd().selectAll();
                    Enumeration myEnum = objIns.getUnfoldingOpd().getSelectedItems();
                    while (myEnum.hasMoreElements()) {
                        Object locObj = myEnum.nextElement();
                        if (locObj instanceof Instance) {
                            vec.add(((Instance) locObj).getEntry());
                        }
                    }
                    xmiCreator.setBaseEnum(vec.elements());
                    xmiCreator.generateXmiFile((ISystem) Opcat2.getCurrentProject(), filename, desicion);

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                }
            }

            Opcat2.endWait();
        }
        // }
    };
    Action bringToFrontAction = new AbstractAction("Bring To Front",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -7919648834938175001L;

        public void actionPerformed(ActionEvent e) {
            OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
            obc.setSentToBAck(false);
        }
    };
    Action sendToBackAction = new AbstractAction("Send To Back",
            StandardImages.EMPTY) {

        /**
         *
         */
        private static final long serialVersionUID = -381895123736075568L;

        public void actionPerformed(ActionEvent e) {
            OpdBaseComponent obc = ThingPopup.this.ti.getConnectionEdge();
            obc.setSentToBAck(true);
        }
    };
    Action fitToContent = new AbstractAction("Fit To Content",
            StandardImages.FIT_TO_SIZE) {

        /**
         *
         */
        private static final long serialVersionUID = 879338001685397770L;

        public void actionPerformed(ActionEvent e) {
            OpdConnectionEdge oce = ThingPopup.this.ti.getConnectionEdge();
            oce.fitToContent();

        }
    };
}
