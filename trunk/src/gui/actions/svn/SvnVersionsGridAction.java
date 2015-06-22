package gui.actions.svn;

import com.qarks.util.files.diff.Diff;
import com.qarks.util.files.diff.FileDiffResult;
import com.qarks.util.files.diff.ui.DiffPanel;
import com.sciapp.renderers.BooleanRenderer;
import com.sciapp.table.styles.Style;
import com.sciapp.treetable.TreeTableModelAdapter;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.dataProject.DataCreatorType;
import gui.metaLibraries.logic.MetaLibrary;
import gui.projectStructure.Entry;
import gui.util.opcatGrid.GridPanel;
import modelControl.OpcatMCManager;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.wc.SVNRevision;
import util.Configuration;
import util.OpcatLogger;
import versions.GUI.OPXDiffDialog;
import versions.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by raanan.
 * Date: Jun 10, 2010
 * Time: 11:34:01 AM
 */
public class SvnVersionsGridAction extends SvnAction {


    /**
     *
     */
    private static final long serialVersionUID = -5578558288175000261L;

    boolean useName = false;
    boolean addSourceAsTemplate = false;
    boolean addTargetAsTemplate = false;
    OPXID.OPX_ID_TYPE opxIDType = OPXID.OPX_ID_TYPE.UUID;
    File source;
    File target;
    long sourceRevision = -1;
    long targetRevision = -1;
    OPXModel targetModel;
    OPXModel sourceModel;
    ResourceBundle rb;

    public SvnVersionsGridAction(String name, File source, Icon icon) {
        super(name, icon);
        this.source = source;
    }

    private String getResource(String key) {
        try {
            return (rb.getString(key) != null ? rb.getString(key) : key);
        } catch (Exception ex) {
            return key;
        }
    }

    public void actionPerformed(ActionEvent e) {

        OPXDiffDialog dialog = new OPXDiffDialog();
        dialog.setRun(false);
        dialog.setSource(source);
        dialog.setTarget(source);
        dialog.pack();
        dialog.setModal(true);
        dialog.setSize(new Dimension(Opcat2.getFrame().getWidth() / 2, Opcat2.getFrame().getHeight() / 2));
        dialog.setLocation(Opcat2.getFrame().getX() + (Opcat2.getFrame().getWidth() / 4), Opcat2.getFrame().getY() + (Opcat2.getFrame().getHeight() / 4));
        dialog.setVisible(true);


        if (!dialog.isRun()) return;

        sourceRevision = dialog.getSourceRevision();
        targetRevision = dialog.getTargetRevision();
        useName = dialog.isUseName();
        addTargetAsTemplate = dialog.isAddTargetAsTemplate();
        addSourceAsTemplate = dialog.isAddSourceAsTemplate();
        opxIDType = (useName ? OPXID.OPX_ID_TYPE.NAME : OPXID.OPX_ID_TYPE.UUID);

        this.source = dialog.getSource();
        this.target = dialog.getTarget();

        Thread runner = new Thread() {
            public void run() {

                JFrame frame = new JFrame("Diff Loading");
                try {

                    Opcat2.startWait();


                    //create prograss bar
                    JProgressBar pb;
                    pb = new JProgressBar(0, 6);
                    int pbCounter = -1;
                    pb.setValue(pbCounter++);
                    pb.setStringPainted(true);
                    JLabel label = new JLabel("Loading Files");
                    JPanel panel = new JPanel();
                    panel.add(pb);
                    JPanel panel1 = new JPanel();
                    panel1.setLayout(new BorderLayout());
                    panel1.add(panel, BorderLayout.NORTH);
                    panel1.add(label, BorderLayout.CENTER);
                    panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    frame.setContentPane(panel1);
                    frame.pack();
                    frame.setLocation((Opcat2.getFrame().getX() + (Opcat2.getFrame().getWidth() / 2)) - frame.getWidth() / 2, Opcat2.getFrame().getY() + (Opcat2.getFrame().getHeight() / 4));
                    frame.setVisible(true);
                    //frame.setDefaultCloseOperation(JFrame.);
                    //end creation


                    rb = ResourceBundle.getBundle("Versions", Locale.getDefault());


                    String targetOldName = target.getName();
                    String sourceOldName = source.getName();


                    pb.setValue(pbCounter++);
                    label.setText("Getting source File " + source.getName() + (sourceRevision >= 0 ? " Revision : " + sourceRevision : " local copy"));
                    frame.pack();
                    if (sourceRevision >= 0) {
                        File temp = File.createTempFile("opcat-version-" + sourceRevision + "-", ".opx");
                        long export = OpcatMCManager.getInstance().doExport(OpcatMCManager.getInstance().getFileURL(source).getURIEncodedPath(), SVNRevision.create(sourceRevision), temp);
                        if (export != sourceRevision) {
                            JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("error.message.export.fail.source"), getResource("error.message.export.title"), JOptionPane.ERROR_MESSAGE);
                            Opcat2.endWait();

                            frame.setVisible(false);
                            return;
                        }
                        source = new File(temp.getPath());
                    }

                    pb.setValue(pbCounter++);
                    label.setText("Loading source File " + sourceOldName + (sourceRevision >= 0 ? " Revision : " + sourceRevision : " local copy"));
                    frame.pack();
                    sourceModel = new OPXModel(source, opxIDType);
                    sourceModel.loadOPX();
                    if (!sourceModel.isLoaded()) {
                        JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("error.message.loading.fail.source"), getResource("error.message.loading.title"), JOptionPane.ERROR_MESSAGE);
                        Opcat2.endWait();

                        frame.setVisible(false);
                        return;
                    }

                    pb.setValue(pbCounter++);
                    label.setText("Getting target File " + target.getName() + (targetRevision >= 0 ? " Revision : " + targetRevision : " local copy"));
                    frame.pack();
                    if (targetRevision >= 0) {
                        File temp = File.createTempFile("opcat-version-" + targetRevision + "-", ".opx");
                        long export = OpcatMCManager.getInstance().doExport(OpcatMCManager.getInstance().getFileURL(target).getURIEncodedPath(), SVNRevision.create(targetRevision), temp);
                        if (export != targetRevision) {
                            JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("error.message.export.fail.target"), getResource("error.message.export.title"), JOptionPane.ERROR_MESSAGE);
                            Opcat2.endWait();

                            frame.setVisible(false);
                            return;
                        }
                        target = new File(temp.getPath());
                    }


                    pb.setValue(pbCounter++);
                    label.setText("Loading target File " + targetOldName + (targetRevision >= 0 ? " Revision : " + targetRevision : " local copy"));
                    frame.pack();
                    targetModel = new OPXModel(target, opxIDType);
                    targetModel.loadOPX();
                    if (!targetModel.isLoaded()) {
                        JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("error.message.loading.fail.target"), getResource("error.message.loading.title"), JOptionPane.ERROR_MESSAGE);
                        Opcat2.endWait();

                        frame.setVisible(false);
                        return;
                    }

                    //OPXEntity test = new OPXEntity(null);
                    //OPXEntity.setID_TYPE(OPXID.OPX_ID_TYPE.NAME) ;
                    pb.setValue(pbCounter++);
                    label.setText("Calculating models diff");
                    frame.pack();
                    OPXDiffResults results = OPXDiff.diff(sourceModel, targetModel);
                    if (addSourceAsTemplate) {
                        //add target as template to current project

                        String path = source.getAbsolutePath();
                        try {
                            SVNURL url = OpcatMCManager.getInstance(true).getFileURL(
                                    new File(path));
                            if (url != null) {
                                if (Opcat2.getCurrentProject() != null) {
                                    MetaLibrary newLib = Opcat2.getCurrentProject()
                                            .getMetaManager()
                                            .createNewMetaLibraryReference(
                                                    MetaLibrary.TYPE_POLICY,
                                                    path,
                                                    DataCreatorType.DATA_TYPE_OPCAT_LIBRARAY,
                                                    DataCreatorType.REFERENCE_TYPE_TEMPLATE_POLICY_FILE);
                                    newLib.load();
                                    Opcat2.getCurrentProject().getMetaManager().addMetaLibrary(newLib);
                                    Opcat2.getCurrentProject().setCanClose(false);
                                } else {
                                    OpcatLogger.error("Error adding source as template : No Current model", true);

                                }
                            } else {
                                OpcatLogger.error("Error adding source as template : source is not in MC", true);
                            }
                        } catch (Exception ex) {
                            OpcatLogger.error(ex);
                        }

                    }

                    if (addTargetAsTemplate) {
                        //add target as template to current project

                        String path = target.getAbsolutePath();
                        try {
                            SVNURL url = OpcatMCManager.getInstance(true).getFileURL(
                                    new File(path));
                            if (url != null) {
                                if (Opcat2.getCurrentProject() != null) {
                                    MetaLibrary newLib = Opcat2.getCurrentProject()
                                            .getMetaManager()
                                            .createNewMetaLibraryReference(
                                                    MetaLibrary.TYPE_POLICY,
                                                    path,
                                                    DataCreatorType.DATA_TYPE_OPCAT_LIBRARAY,
                                                    DataCreatorType.REFERENCE_TYPE_TEMPLATE_POLICY_FILE);
                                    newLib.load();
                                    Opcat2.getCurrentProject().getMetaManager().addMetaLibrary(newLib);
                                    Opcat2.getCurrentProject().setCanClose(false);
                                } else {
                                    OpcatLogger.error("Error adding source as template : No Current model", true);

                                }
                            } else {
                                OpcatLogger.error("Error adding source as template : source is not in MC", true);
                            }
                        } catch (Exception ex) {
                            OpcatLogger.error(ex);
                        }

                    }

                    pb.setValue(pbCounter++);
                    label.setText("Creating Grid");
                    ArrayList<String> cols = new ArrayList<String>();
                    cols.add(getResource("col.name"));
                    cols.add(getResource("col.type"));
                    cols.add(getResource("col.new"));
                    cols.add(getResource("col.deleted"));
                    cols.add(getResource("col.change"));
                    cols.add(getResource("col.included"));
                    cols.add(getResource("col.instance"));


                    GridPanel grid = new GridPanel(cols);
                    grid.setTabName(getResource("grid.name") + " " + sourceOldName + (sourceRevision >= 0 ? getResource("grid.name.revision") + sourceRevision : "") + " <> " + targetOldName + (targetRevision >= 0 ? getResource("grid.name.revision") + targetRevision : ""));
                    grid.getGrid().getColumnModel().getColumn(2).setCellRenderer(
                            new BooleanRenderer());
                    grid.getGrid().getColumnModel().getColumn(3).setCellRenderer(
                            new BooleanRenderer());
                    grid.getGrid().getColumnModel().getColumn(4).setCellRenderer(
                            new BooleanRenderer());
                    grid.getGrid().getColumnModel().getColumn(5).setCellRenderer(
                            new BooleanRenderer());
                    grid.getGrid().getColumnModel().getColumn(6).setCellRenderer(
                            new BooleanRenderer());


                    grid.getGrid().getStyleModel().clearStyles();
                    grid.getGrid().getStyleModel().addStyle(new VesrsionsGridStyle(grid));
                    grid.getGrid().addMouseListener(new VersionsMouseListner(grid));


                    for (OPXDiffResult res : results.getResults()) {
                        try {
                            Object[] row = new Object[7];
                            Object[] tag = new Object[2];

                            OPXEntity entity = (sourceModel.getEntity(res.getEntity()) == null ? targetModel.getEntity(res.getEntity()) : sourceModel.getEntity(res.getEntity()));
                            //boolean targetEntity = (sourceModel.getEntity(res.getEntity()) == null);

                            if ((entity.getEntityType() == OPXEntity.OPX_ENTITY_TYPE.LINK) || (entity.getEntityType() == OPXEntity.OPX_ENTITY_TYPE.RELEATION)) {
                                OPXEdgeEntry edge = (OPXEdgeEntry) entity;
                                row[0] = (sourceModel.getNode(edge.getSource()) != null ? sourceModel.getNode(edge.getSource()) : targetModel.getNode(edge.getSource())).getName()
                                        + " -> "
                                        + (sourceModel.getNode(edge.getTarget()) != null ? sourceModel.getNode(edge.getTarget()) : targetModel.getNode(edge.getTarget())).getName();
                            } else {
                                row[0] = entity.getName();
                            }
                            if (entity instanceof OPXEdgeEntry) {
                                OPXEdgeEntry edge = (OPXEdgeEntry) entity;
                                row[1] = getResource(String.valueOf(edge.getEdgeType()));
                            } else {
                                row[1] = getResource(String.valueOf(entity.getEntityType()));
                            }


                            //if (!targetEntity) {
                            //int i = 0;
                            for (OPXDiffResult.OPX_ENTITY_CHANGES_TYPES type : res.getEntityChanges()) {
                                //NAME ret = null;
                                switch (type) {
                                    case EDGE_NO_CHANGE:
                                        break;
                                    case EDGE_CHANGED_TYPE:
                                        row[4] = true;
                                        break;
                                    case EDGE_NOT_EXISTS:
                                        break;
                                    case EDGE_CHANGED:
                                        row[4] = true;
                                        break;
                                    case NODE_CHANGED_PROPERTIES:
                                        row[4] = true;
                                        break;
                                    case NODE_CHANGED_APPEARANCES:
                                        row[6] = true;
                                        break;
                                    case NODE_CHANGED_INCLUDED:
                                        row[5] = true;
                                        break;
                                    case IN_SOURCE_NOT_IN_TARGET:
                                        row[2] = true;
                                        break;
                                    case IN_TARGET_NOT_IN_SOURCE:
                                        row[3] = true;
                                        break;
                                }
                            }

                            Object[] s = new Object[2];
                            s[0] = entity;
                            s[1] = res;

                            tag[0] = s;
                            OPXID id = entity.getID();
                            if ((entity.getEntityType() == OPXEntity.OPX_ENTITY_TYPE.LINK) || (entity.getEntityType() == OPXEntity.OPX_ENTITY_TYPE.RELEATION)) {
                                OPXEdgeEntry edge = (OPXEdgeEntry) entity;
                                Object tag1 = (targetModel.getEdge(edge.getSource(), edge.getTarget()) == null ? "" : targetModel.getEdge(edge.getSource(), edge.getTarget()));
                                tag[1] = (targetModel.getEntity(id) == null ? tag1 : targetModel.getEntity(id));
                            } else {
                                tag[1] = (targetModel.getEntity(id) == null ? "" : targetModel.getEntity(id));
                            }

                            grid.getGrid().addRow(row, tag);
                        } catch (Exception ex) {
                            OpcatLogger.error(ex);
                            Opcat2.endWait();

                            frame.setVisible(false);
                            return;
                        }
                    }
                    grid.AddToExtensionToolsPanel(true);

                } catch (Exception ex) {
                    OpcatLogger.error(ex);
                    Opcat2.endWait();

                    frame.setVisible(false);
                } finally {
                    Opcat2.endWait();

                    frame.setVisible(false);
                }
            }
        };
        runner.start();

    }

    private void rightClickEvent(GridPanel panel, MouseEvent e) {
        JPopupMenu popup = panel.getRMenu();
        boolean showMenu = false;
        if (panel.getGrid().getSelectedRow() >= 0) {
            Object[] s = (Object[]) panel.getSelectedTag()[0];
            OPXEntity entity = (OPXEntity) s[0];
            OPXDiffResult result = (OPXDiffResult) s[1];
            Object target = panel.getSelectedTag()[1];

            JMenu actions = new JMenu(getResource("menu.name"));
            if (!result.getEntityChanges().contains(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET)) {
                JMenuItem showComparedModel = new JMenuItem(getResource("menu.open.other.model"));
                showComparedModel.addActionListener(new VersionsShowModel(entity.getID(), true));
                actions.add(showComparedModel);
                showMenu = true;
            }

            if (result.getEntityChanges().contains(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET)) {
                JMenuItem showComparedModel = new JMenuItem(getResource("menu.open.source.model"));
                showComparedModel.addActionListener(new VersionsShowModel(entity.getID(), false));
                actions.add(showComparedModel);
                showMenu = true;
            }


            if ((target != null) && !(target instanceof String) && (target instanceof OPXEntity)) {
                JMenuItem showComparedModel = new JMenuItem(getResource("menu.show.diff.pane"));
                showComparedModel.addActionListener(new VersionsDiffWindow(entity, (OPXEntity) target, result, sourceModel, targetModel));
                actions.add(showComparedModel);
                showMenu = true;
            }

            if (showMenu)
                popup.add(actions);


        }

        popup.show(e.getComponent(), e.getX(), e.getY());

    }


    class VersionsMouseListner extends MouseAdapter {
        GridPanel grid;

        VersionsMouseListner(GridPanel grid) {
            this.grid = grid;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                //get entity of uuid from current project
                Object[] tag = grid.getSelectedTag();
                OPXID uuid = ((OPXEntity) ((Object[]) tag[0])[0]).getID();
                if (FileControl.getInstance().getCurrentProject() != null) {
                    Entry entry = FileControl.getInstance().getCurrentProject().getEntry(uuid);
                    if (entry != null) {
                        entry.ShowInstances();
                    } else {
                        JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("message.entry.not.in.project"));
                    }
                } else {
                    JOptionPane.showMessageDialog(Opcat2.getFrame(), getResource("message.entry.no.open.project"));
                }
            }

            if (e.getButton() == MouseEvent.BUTTON3) {
                rightClickEvent(grid, e);
            }
        }

    }

    class VesrsionsGridStyle implements Style {
        GridPanel grid;

        VesrsionsGridStyle(GridPanel grid) {
            this.grid = grid;

        }

        public void apply(Component component, JTable jTable, int i, int i1) {

            TreeTableModelAdapter model = (TreeTableModelAdapter) grid.getGrid().getModel();
            if (model != null && (model.isHeader(i) || model.isFooter(i))) return;

            Object[] tag = grid.getGrid().GetTag(i);
            if (tag != null) {
                Object[] s = (Object[]) tag[0];
                //OPXEntity entity = (OPXEntity) s[0];
                OPXDiffResult res = (OPXDiffResult) s[1];

                if (res.getEntityChanges().contains(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET)) {
                    component.setBackground(Configuration.getInstance().getColorFromProperty("versions.grid.color.new"));
                    return;
                }

                if (res.getEntityChanges().contains(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE)) {
                    component.setBackground(Configuration.getInstance().getColorFromProperty("versions.grid.color.deleted"));
                    return;
                }


                component.setBackground(Configuration.getInstance().getColorFromProperty("versions.grid.color.change"));

            }


        }
    }


    class VersionsDiffWindow extends SvnAction {
        /**
         *
         */
        private static final long serialVersionUID = 5998404909085391136L;
        OPXEntity source;
        OPXEntity target;
        OPXDiffResult result;
        OPXModel sourceModel;
        OPXModel targetModel;


        VersionsDiffWindow(OPXEntity source, OPXEntity target, OPXDiffResult result, OPXModel sourceModel, OPXModel targetModel) {
            super();
            this.source = source;
            this.target = target;
            this.result = result;
            this.sourceModel = sourceModel;
            this.targetModel = targetModel;
        }

        private String[] getPropertiesRightLeft(OPXEntity locSource, OPXEntity locTarget) {
            String[] ret = new String[2];

            HashMap<Object, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES> entityDiff = locSource.compareProperties(locTarget);

            ret[0] = "";
            ret[1] = "";

            if (result.getOldType() != null) {
                ret[1] = ret[1] + getResource("changed.type") + "\n" + getResource(result.getOldType().toString());
                if (locSource instanceof OPXEdgeEntry) {
                    OPXEdgeEntry edge = (OPXEdgeEntry) locSource;
                    ret[0] = ret[0] + getResource("changed.type") + "\n" + getResource(String.valueOf(edge.getEdgeType()));
                } else {
                    ret[0] = ret[0] + getResource("changed.type") + "\n" + getResource(String.valueOf(locSource.getEntityType()));
                }
                return ret;
            }

            for (Object key : entityDiff.keySet()) {

                ret[0] = ret[0] + getResource(key.toString()) + " \n";
                String leftInsert = "";
                if (entityDiff.get(key) != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE) {
                    leftInsert = locSource.get(key).toString();
                }
                leftInsert = leftInsert + " \n";
                int leftLines = leftInsert.split("\n").length;
                ret[0] = ret[0] + leftInsert;


                ret[1] = ret[1] + getResource(key.toString()) + " \n";
                String rightInsert = "";
                if (entityDiff.get(key) != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET) {
                    rightInsert = locTarget.get(key).toString();
                }
                rightInsert = rightInsert + " \n";
                int rightLines = rightInsert.split("\n").length;
                ret[1] = ret[1] + rightInsert;

                int padd = leftLines - rightLines;
                if (padd > 0) {
                    for (int i = 0; i < padd; i++) {
                        ret[1] = ret[1] + "\n";
                    }
                } else if (padd < 0) {
                    for (int i = 0; i < (-1 * padd); i++) {
                        ret[0] = ret[0] + "\n";
                    }
                }


            }


            return ret;
        }

        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);

            Thread runner = new Thread() {
                public void run() {


                    JTabbedPane tabbedPane = new JTabbedPane();

                    DiffPanel propertiesDiffEditor;
                    propertiesDiffEditor = new DiffPanel("");
                    propertiesDiffEditor.setBorder(new EmptyBorder(10, 10, 10, 10));
                    tabbedPane.add(propertiesDiffEditor, getResource("diff.panel.properties.name"));

                    DiffPanel appearancesDiffEditor;
                    appearancesDiffEditor = new DiffPanel("");
                    appearancesDiffEditor.setBorder(new EmptyBorder(10, 10, 10, 10));
                    tabbedPane.add(appearancesDiffEditor, getResource("diff.panel.apearances.name"));

                    DiffPanel includedDiffEditor;
                    includedDiffEditor = new DiffPanel("");
                    includedDiffEditor.setBorder(new EmptyBorder(10, 10, 10, 10));
                    tabbedPane.add(includedDiffEditor, getResource("diff.panel.in-zoom.name"));


                    FileDiffResult fileDiffResult = Diff.quickDiff(getPropertiesRightLeft(VersionsDiffWindow.this.source, VersionsDiffWindow.this.target)[0], getPropertiesRightLeft(VersionsDiffWindow.this.source, VersionsDiffWindow.this.target)[1], true);
                    propertiesDiffEditor.setContent(fileDiffResult);

                    HashMap<OPXID, OPXDiffResult.OPX_APEARANCES_CHANGES_TYPES> apearancesChanges = result.getAooearanceChanges();
                    String left = "";
                    String right = "";
                    for (OPXID apearance : apearancesChanges.keySet()) {
                        OPXDiffResult.OPX_APEARANCES_CHANGES_TYPES type = apearancesChanges.get(apearance);
                        switch (type) {
                            case IN_TARGET_NOT_IN_SOURCE:
                                left = left + "\n";
                                //left = left + target.getAppearance(apearance).getName() + "\n";
                                //left = left + getResource("appearance.panel.deleted") + "\n";
                                right = right + VersionsDiffWindow.this.target.getAppearance(apearance).getName() + getResource("appearance.panel.not.in.source") + "\n";
                                //right = right + getResource("appearance.panel.not.in.source") + "\n";
                                break;
                            case IN_SOURCE_NOT_IN_TARGET:
                                left = left + VersionsDiffWindow.this.source.getAppearance(apearance).getName() + getResource("appearance.panel.new") + "\n";
                                //left = left + getResource("appearance.panel.new") + "\n";
                                right = right + "\n";
                                //right = right + source.getAppearance(apearance).getName() + "\n";
                                //right = right + getResource("appearance.panel.not.in.target") + "\n";
                                break;
                        }
                    }
                    fileDiffResult = Diff.quickDiff(left, right, true);
                    appearancesDiffEditor.setContent(fileDiffResult);

                    left = "";
                    right = "";
                    HashMap<OPXID, OPXDiffResult.OPX_INCLUDED_CHANGES_TYPES> includedChanges = result.getIncludedChangesMap();
                    for (OPXID included : includedChanges.keySet()) {
                        OPXDiffResult.OPX_INCLUDED_CHANGES_TYPES type = includedChanges.get(included);
                        OPXEntity t = (targetModel.getEntityAppearance(included) != null ? targetModel.getEntityAppearance(included) : targetModel.getNode(included));
                        OPXEntity s = (sourceModel.getEntityAppearance(included) != null ? sourceModel.getEntityAppearance(included) : sourceModel.getNode(included));
                        switch (type) {
                            case IN_TARGET_NOT_IN_SOURCE:
                                left = left + "\n";
                                //left = left + targetModel.getEntityAppearance(included).getName() + "\n";
                                //left = left + getResource("included.panel.deleted") + "\n";
                                right = right + t.getEntityType() + " " + t.getName() + getResource("included.panel.not.in.source") + "\n";
                                //right = right + getResource("included.panel.not.in.source") + "\n";
                                break;
                            case IN_SOURCE_NOT_IN_TARGET:
                                left = left + s.getEntityType() + " " + s.getName() + getResource("included.panel.new") + "\n";
                                //left = left + getResource("included.panel.new") + "\n";
                                right = right + "\n";
                                //right = right + sourceModel.getEntityAppearance(included).getName() + "\n";
                                //right = right + getResource("included.panel.not.in.target") + "\n";
                                break;

                            case ORDER_CHANGED:
                                left = left + s.getEntityType() + " " + s.getName() + " " + getResource("included.panel.order") + sourceModel.getEntityAppearance(included).getOrder() + "\n";
                                right = right + t.getEntityType() + " " + t.getName() + " " + getResource("included.panel.order") + targetModel.getEntityAppearance(included).getOrder() + "\n";
                                break;
                        }

                    }
                    fileDiffResult = Diff.quickDiff(left, right, true);
                    includedDiffEditor.setContent(fileDiffResult);


                    JDialog diff = new JDialog(Opcat2.getFrame(), true);
                    diff.setSize(new Dimension(Opcat2.getFrame().getWidth() / 2, Opcat2.getFrame().getHeight() / 2));
                    diff.setLocation(Opcat2.getFrame().getX() + (Opcat2.getFrame().getWidth() / 4), Opcat2.getFrame().getY() + (Opcat2.getFrame().getHeight() / 4));
                    diff.setTitle(getResource("diff.windows.name"));
                    diff.add(tabbedPane);
                    diff.setVisible(true);
                }
            };
            runner.start();
        }


    }

    class VersionsShowModel extends SvnAction {

        /**
         *
         */
        private static final long serialVersionUID = 5706005312977959603L;
        private OPXID uuid;
        private boolean isTarget = true;

        VersionsShowModel(OPXID uuid, boolean openTarget) {
            super();
            this.uuid = uuid;
            this.isTarget = openTarget;
        }

        public void actionPerformed(ActionEvent e) {
            super.actionPerformed(e);
            if (isTarget) {
                if (uuid.getIDType() == OPXID.OPX_ID_TYPE.UUID) {
                    FileControl.getInstance().runNewOPCAT(target, "uuid" + uuid.toString());
                } else {
                    FileControl.getInstance().runNewOPCAT(target, "name" + uuid.toString());
                }
            } else {
                if (uuid.getIDType() == OPXID.OPX_ID_TYPE.UUID) {
                    FileControl.getInstance().runNewOPCAT(source, "uuid" + uuid.toString());
                } else {
                    FileControl.getInstance().runNewOPCAT(source, "name" + uuid.toString());

                }
            }
        }

    }


}



