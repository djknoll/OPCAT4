package gui.dataProject;

import com.sciapp.renderers.BooleanRenderer;
import com.sciapp.table.SortTableModel;
import com.sciapp.treetable.TreeTableModelAdapter;
import com.sciapp.treetable.TreeTableRow;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.images.standard.StandardImages;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ThingEntry;
import gui.util.opcatGrid.ColorCellRenderer;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MetaDataGridPanels {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     *
     */

    private HashMap gridPanels = new HashMap();

    private static MetaDataGridPanels instance = new MetaDataGridPanels();

    private MetaDataGridPanels() {
    }

    private MetaDataGridPanel InitGrid(DataProject prj) {

        if ((gridPanels != null) && gridPanels.containsKey(prj.getName())) {
            return (MetaDataGridPanel) gridPanels.get(prj.getName());
        }

        MetaDataGridPanel gridPanel;

        // gridPanel.AllignButtons() ;
        ArrayList<String> colNames = new ArrayList<String>();
        for (int i = 0; i < prj.getHeaders().size(); i++) {
            colNames.add(prj.getHeaders().elementAt(i).toString());
        }
        colNames.add("Connected?");
        colNames.add("Things");

        gridPanel = new MetaDataGridPanel(colNames, prj);

        // gridPanel.getGrid().getSortedModel().setComparator(0,
        // new ReqComparator());
        JButton btnConnect = new JButton("   Connect   ");
        btnConnect.setIcon(StandardImages.META_CONNECT);
        btnConnect.addActionListener(new ConnectButton(this));
        gridPanel.getButtonPane().add(btnConnect);


        JButton btnConnectByName = new JButton("Connect By Name");
        btnConnectByName.setIcon(StandardImages.META_CONNECT);
        btnConnectByName.addActionListener(new ConnectByNameButton(this));
        gridPanel.getButtonPane().add(btnConnectByName);

        gridPanel.getGrid().addMouseListener(new MouseListner(gridPanel));

        JButton btnDisconnect = new JButton("Disconnect");
        btnDisconnect.setIcon(StandardImages.META_DISCONNECT);
        gridPanel.getButtonPane().add(btnDisconnect);
        gridPanel.getButtonPane().add(new JLabel(""));
        btnDisconnect.addActionListener(new DisconnectButton(this));

        JButton btnDisconnectAll = new JButton("Disconnect All");
        btnDisconnectAll.setIcon(StandardImages.META_DISCONNECT);
        gridPanel.getButtonPane().add(btnDisconnectAll);
        gridPanel.getButtonPane().add(new JLabel(""));
        btnDisconnectAll.addActionListener(new DisconnectAllButton(this));


        JButton btnUnconnected = new JButton("Not Assigned");
        gridPanel.getButtonPane().add(btnUnconnected);
        btnUnconnected.addActionListener(new UnconnectedButton(this));

        JButton btnMissing = new JButton("Missing");
        gridPanel.getButtonPane().add(btnMissing);
        btnMissing.addActionListener(new MissingButton(this));

        gridPanel.setTabName(prj.getName());

        gridPanels.put(prj.getName(), gridPanel);
        return gridPanel;

    }

    public void actionPerformed(ActionEvent action) {
        if (!FileControl.getInstance().isProjectOpened()) {
            JOptionPane
                    .showMessageDialog(GuiControl.getInstance().getFrame(),
                            "No system is opened", "Message",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        showMetaDataProjects();

    }

    public void showMeta(final MetaLibrary ont, final DataProject metadata) {

        try {

            //Opcat2.startWait();

            //SwingUtilities.invokeLater(new Runnable() {
            //    @Override
            //    public void run() {

            try {


                FileControl.getInstance().getCurrentProject().getMetaManager().refreshMetaRoles(FileControl.getInstance().getCurrentProject(), ont);


                MetaDataGridPanel gridPanel = InitGrid(metadata);

                //try {
                //    GridPanel.RemovePanel(gridPanel.getTabName());
                //} catch (Exception ex) {
                //
                //}


                gridPanel.ClearData();
                gridPanel.AddToExtensionToolsPanel(true);

                // try {
                Vector allRoles = new Vector();
                try {
                    allRoles.addAll(ont.getRolesCollection());
                } catch (MetaException ex) {
                    OpcatLogger.error(ex);
                }

                boolean coloring = (metadata).isColoring();
                int colorIdx = -1;
                if (coloring) {
                    colorIdx = (metadata).getColoringIndex();
                    ColorCellRenderer colorRenderer = new ColorCellRenderer();
                    gridPanel.getGrid().getColumnModel().getColumn(colorIdx).setCellRenderer(colorRenderer);

//            if (metadata.isShowColorValueAsPrograssBar()) {
//                JProgressBar bar = new JProgressBar(0, (metadata)
//                        .getMaxColoringLevelValue());
//                ProgressBarRenderer cellRenderer = new ProgressBarRenderer(bar);
//                gridPanel.getGrid().getColumnModel().getColumn(colorIdx)
//                        .setCellRenderer(cellRenderer);
//            }
                }

                int connectedIndex = (metadata).getHeaders().size();
                JCheckBox chk = new JCheckBox();
                BooleanRenderer cellRenderer = new BooleanRenderer(chk);
                gridPanel.getGrid().getColumnModel().getColumn(connectedIndex)
                        .setCellRenderer(cellRenderer);

                // get a hash map of (req, thingsVector connected to it)

                Iterator iter = allRoles.iterator();

                if (allRoles.size() > 0) {
                    //load the roles in current project .

                }
                while (iter.hasNext()) {
                    Role role = (Role) iter.next();
                    String things = role.connectedThingstoString(Opcat2.getCurrentProject());

                    DataAbstractItem req = role.getThing();
                    // MetaDataItem req;
                    // if (dataIns.getDataInstance() instanceof MetaDataItem) {
                    // req = (MetaDataItem) dataIns.getDataInstance();
                    // } else {
                    // OpmEntity opm = (OpmEntity) dataIns.getDataInstance();
                    // req = metadata.getMetaDataItem(opm.getId());
                    // }

                    if (req != null) {
                        // get connected thing.

                        // first we need to add an aggragator row for the thig
                        // coloumn
                        Object[] row = new Object[req.getAllData().size() + 2];
                        Object[] rowTag = new Object[2];
                        for (int i = 0; i < req.getAllData().size(); i++) {
                            row[i] = req.getAllData().get(i);
                        }

                        if (things.equalsIgnoreCase("")) {
                            row[req.getAllData().size()] = new Boolean(false);
                        } else {
                            row[req.getAllData().size()] = new Boolean(true);
                        }

                        row[req.getAllData().size() + 1] = things;
                        rowTag[0] = role; // set to thing real tag if it exists
                        rowTag[1] = req; // //set to thing real tag if it exists

                        gridPanel.getGrid().addRow(row, rowTag);
                    }

                }

                gridPanel.getGrid().getSortedModel().sort(0, SortTableModel.ADD_SORT);

                // } catch (MetaException E) {
                // JOptionPane.showMessageDialog(this, E.getMessage(), "Message",
                // JOptionPane.ERROR_MESSAGE);
                // OpcatLogger.logError(E);
                // }
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            } finally {
                //Opcat2.endWait();
            }
            //   }
            //});

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
            //Opcat2.endWait();
        }


    }

    public void showMetaDataProjects() {
        // set a style for header and footer rows

        // file.getCurrentProject().getMetaManager().loadDummyReqProject();

        Enumeration ontologies = FileControl.getInstance().getCurrentProject()
                .getMetaManager().getMetaLibraries();
        while (ontologies.hasMoreElements()) {

            MetaLibrary ont = (MetaLibrary) ontologies.nextElement();
            if (!ont.isPolicy()) {
                try {
                    showMeta(ont, ont.getDataProject());
                } catch (Exception ex) {
                    OpcatLogger.error(ex, false);
                }
            }

        }

    }

    private Grid getCurrentTable() {

        Object panel = GuiControl.getInstance().getExtensionToolsPane()
                .getSelectedComponent();
        if (panel instanceof GridPanel) {
            Grid table = ((GridPanel) panel).getGrid();
            return table;
        } else {
            return null;
        }

    }

    private void connectByName() {

        try {

            Opcat2.startWait();

            //SwingUtilities.invokeLater(new Runnable() {
            //@Override
            // public void run() {

            try {
                Grid table = getCurrentTable();
                if (table == null)
                    return;
                // table is the TreeTable model
                TreeTableModelAdapter ttm = (TreeTableModelAdapter) table.getModel();

                for (int row = 0; row < table.getRowCount(); row++) {

                    TreeTableRow treeRow = (TreeTableRow) ttm
                            .nodeForRow(row);

                    if (treeRow.isLeaf() && !treeRow.isFooter()) {
                        ArrayList<ThingEntry> things = FileControl.getInstance().getCurrentProject()
                                .getSystemStructure().getThingEntries();

                        Object[] tag = table.GetTag(row);
                        Role role = (Role) tag[0];
                        for (ThingEntry thing : things) {
                            if (role.getThingName().trim().equalsIgnoreCase(thing.getName().trim().replace("\n", " "))) {
                                OpmEntity theThing = thing.getLogicalEntity();
                                theThing.getRolesManager().addRole(role);
                                DataAbstractItem req = (DataAbstractItem) tag[1];
                                Object[] data = (Object[]) treeRow.getUserObject();
                                data[data.length - 2] = new Boolean(true);
                                data[data.length - 1] = role.connectedThingstoString(Opcat2.getCurrentProject());
                            }
                        }
                    }
                }

                getCurrentTable().getSortedModel().fireTableDataChanged();
                Opcat2.getCurrentProject().setCanClose(false);
                Opcat2.getFrame().repaint();

            } catch (Exception ex) {
                OpcatLogger.error(ex);
            } finally {
                Opcat2.endWait();
            }
            //}
            //});

        } catch (Exception ex) {
            OpcatLogger.error(ex, true);
            Opcat2.endWait();
        }


    }

    private void connect() {

        Grid table = getCurrentTable();
        if (table == null)
            return;
        // table is the TreeTable model
        int[] selectedRows = table.getSelectedRows();
        TreeTableModelAdapter ttm = (TreeTableModelAdapter) table.getModel();

        for (int row = 0; row < selectedRows.length; row++) {

            TreeTableRow treeRow = (TreeTableRow) ttm
                    .nodeForRow(selectedRows[row]);

            if (treeRow.isLeaf() && !treeRow.isFooter()) {
                List thingsList = Collections
                        .list(FileControl.getInstance().getCurrentProject()
                                .getCurrentOpd().getSelectedItems());

                Iterator selectedIter = thingsList.iterator();

                Object[] tag = table.GetTag(selectedRows[row]);
                Role role = (Role) tag[0];

                while (selectedIter.hasNext()) {
                    Object obj = selectedIter.next();
                    if ((obj instanceof Instance)) {
                        OpmEntity theThing = (OpmEntity) ((Instance) obj)
                                .getEntry().getLogicalEntity();
                        theThing.getRolesManager().addRole(role);
                    }
                }
                if (thingsList.size() > 0) {
                    DataAbstractItem req = (DataAbstractItem) tag[1];
                    Object[] data = (Object[]) treeRow.getUserObject();
                    data[data.length - 2] = new Boolean(true);
                    data[data.length - 1] = role.connectedThingstoString(Opcat2.getCurrentProject());
                }
            }
        }
        getCurrentTable().getSortedModel().fireTableDataChanged();
        Opcat2.getCurrentProject().setCanClose(false);
        Opcat2.getFrame().repaint();
    }

    private void disconnectAll() {

        try {

            Opcat2.startWait();

            //SwingUtilities.invokeLater(new Runnable() {
            //@Override
            // public void run() {
            try {
                Grid table = getCurrentTable();
                if (table == null)
                    return;
                // table is the TreeTable model
                TreeTableModelAdapter ttm = (TreeTableModelAdapter) table.getModel();

                for (int row = 0; row < table.getRowCount(); row++) {

                    TreeTableRow treeRow = (TreeTableRow) ttm
                            .nodeForRow(row);

                    if (treeRow.isLeaf() && !treeRow.isFooter()) {
                        ArrayList<ThingEntry> things = FileControl.getInstance().getCurrentProject()
                                .getSystemStructure().getThingEntries();
                        Object[] tag = table.GetTag(row);
                        Role role = (Role) tag[0];

                        for (ThingEntry thing : things) {
                            if (thing.getLogicalEntity().getRolesManager().getRolesCollection().size() > 0) {
                                OpmEntity theThing = thing.getLogicalEntity();
                                boolean contained = theThing.getRolesManager().removeRole(role);
                                if (contained) {
                                    Object[] data = (Object[]) treeRow.getUserObject();
                                    String str = role.connectedThingstoString(Opcat2.getCurrentProject());
                                    data[data.length - 1] = str;
                                    if (str.equalsIgnoreCase("")) {
                                        data[data.length - 2] = new Boolean(false);
                                    }
                                }
                            }
                        }
                    }
                }

                getCurrentTable().getSortedModel().fireTableDataChanged();
                Opcat2.getCurrentProject().setCanClose(false);
                Opcat2.getFrame().repaint();
            } catch (Exception ex) {
                OpcatLogger.error(ex);
            } finally {
                Opcat2.endWait();
            }
            // }
            //});

        } catch (Exception ex) {
            OpcatLogger.error(ex, true);
            Opcat2.endWait();
        }
    }

    private void disconnect() {

        Grid table = getCurrentTable();
        if (table == null)
            return;
        // table is the TreeTable model
        int[] selectedRows = table.getSelectedRows();
        TreeTableModelAdapter ttm = (TreeTableModelAdapter) table.getModel();
        // List treeList = ttm.getRows();

        for (int row = 0; row < selectedRows.length; row++) {
            TreeTableRow treeRow = (TreeTableRow) ttm
                    .nodeForRow(selectedRows[row]);
            Object[] tag = table.GetTag(selectedRows[row]);
            Role role = (Role) tag[0];
            try {
                role.load(role.getOntology());
            } catch (Exception e) {

            }

            List selectedThings = Collections.list(FileControl.getInstance()
                    .getCurrentProject().getCurrentOpd().getSelectedItems());
            Iterator selectedIter = selectedThings.iterator();
            while (selectedIter.hasNext()) {
                Object obj = selectedIter.next();
                if ((obj instanceof Instance)) {
                    OpmEntity theThing = (OpmEntity) ((Instance) obj)
                            .getEntry().getLogicalEntity();
                    theThing.getRolesManager().removeRole(role);
                }
            }
            if (selectedThings.size() > 0) {
                Object[] data = (Object[]) treeRow.getUserObject();
                String str = role.connectedThingstoString(Opcat2.getCurrentProject());
                data[data.length - 1] = str;
                if (str.equalsIgnoreCase("")) {
                    data[data.length - 2] = new Boolean(false);
                }
            }
        }
        getCurrentTable().getSortedModel().fireTableDataChanged();
        Opcat2.getCurrentProject().setCanClose(false);
        Opcat2.getFrame().repaint();
    }

    /**
     * returns a map of (req, Conected Things)
     *
     * @return
     */

    private void unconnected() {

        Vector things = new Vector();

        Iterator elementsIter = Collections.list(
                FileControl.getInstance().getCurrentProject()
                        .getSystemStructure().getElements()).iterator();

        Entry ent = null;
        while (elementsIter.hasNext()) {
            Object obj = elementsIter.next();
            if ((obj instanceof Entry)) {
                ent = (Entry) obj;
                if (!ent.getRoles(MetaLibrary.TYPE_CLASSIFICATION).hasMoreElements()) {
                    things.add(ent.getLogicalEntity());
                }
            }
        }

        if (ent != null)
            ent.ShowInstances(things);
    }

    private void missing() {
        Vector things = new Vector();

        Iterator elementsIter = Collections.list(
                FileControl.getInstance().getCurrentProject()
                        .getSystemStructure().getElements()).iterator();
        Entry ent = null;
        while (elementsIter.hasNext()) {
            Object obj = elementsIter.next();
            if ((obj instanceof Entry)) {
                ent = (Entry) obj;
                OpmEntity opm = (OpmEntity) ent.getLogicalEntity();
                if (opm.getRolesManager().getNotLoadedRolesVector(
                        MetaLibrary.TYPE_CLASSIFICATION).size() > 0) {
                    things.add(ent.getLogicalEntity());
                }
            }
        }

        if (ent != null)
            ent.ShowInstances(things);

    }

    private void rightClickEvent(GridPanel panel, MouseEvent e) {
        JPopupMenu metaDataPopupMenu = panel.getRMenu();

        if (panel.getGrid().getSelectedRow() >= 0) {
            Object[] tag = new Object[2];
            tag = panel.getGrid().GetTag(panel.getGrid().getSelectedRow());
            Role role = (Role) tag[0];
            DataAbstractItem req = (DataAbstractItem) tag[1];
            JMenuItem analyze = new JMenuItem("Analyze : " + req.getName());
            metaDataPopupMenu.add(analyze);
            ArrayList<String> cols = new ArrayList<String>();
            cols.add("Thing");
            cols.add("Name");
            cols.add("Connected To");
            cols.add("Is Connected");
            cols.add("Type");
            cols.add("Connected To");
            analyze
                    .addActionListener(new MetaDataItemAnalisysPanel(cols, req, role.connectedThings(Opcat2.getCurrentProject()), role));

            if (role.getOntology().getProjectHolder() instanceof OpdProject) {

                OpdProject meta = (OpdProject) role.getOntology()
                        .getProjectHolder();
                Long entryID = (Long) role.getThing().getAdditionalData();
                Entry entry = meta.getSystemStructure().getEntry(
                        entryID.longValue());

                JMenuItem menuItem = new JMenuItem("Show Appearances");
                menuItem.addActionListener(new ShowInstancesAction(entry));
                metaDataPopupMenu.add(new JSeparator());
                metaDataPopupMenu.add(menuItem);
            }
        }

        metaDataPopupMenu.show(e.getComponent(), e.getX(), e.getY());

    }

    private void showThings() {

        Grid table = getCurrentTable();
        if (table == null)
            return;
        Vector things = new Vector();
        // table is the TreeTable model
        int[] selectedRows = table.getSelectedRows();
        for (int i = 0; i < selectedRows.length; i++) {

            Object[] tag = table.GetTag(selectedRows[i]);
            DataAbstractItem req = (DataAbstractItem) tag[1];
            ArrayList connectedThings = ((Role) tag[0]).connectedThings(Opcat2.getCurrentProject());
            Iterator thingsIter = connectedThings.iterator();
            while (thingsIter.hasNext()) {
                DataAbstractItem theThing = (DataAbstractItem) thingsIter.next();
                things.add(theThing);
            }

        }
        // get an entry to show the instances.
        if (Opcat2.getCurrentProject().getSystemStructure() != null) {
            Entry entry = (Entry) Opcat2.getCurrentProject()
                    .getSystemStructure().getElements().nextElement();
            entry.ShowInstances(things);
        }
    }

    class ConnectButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public ConnectButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.connect();
        }
    }

    class ConnectByNameButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public ConnectByNameButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.connectByName();
        }
    }

    class DisconnectAllButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public DisconnectAllButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.disconnectAll();
        }
    }

    class DisconnectButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public DisconnectButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.disconnect();
        }
    }

    class UnconnectedButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public UnconnectedButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.unconnected();
        }
    }

    class MissingButton implements java.awt.event.ActionListener {
        private MetaDataGridPanels panel;

        public MissingButton(MetaDataGridPanels action) {
            this.panel = action;
        }

        public void actionPerformed(ActionEvent e) {
            this.panel.missing();
        }
    }

    class MouseListner extends MouseAdapter {
        GridPanel panel;

        public MouseListner(GridPanel panel) {
            super();
            this.panel = panel;
        }

        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                showThings();
            }
            if (e.getButton() == MouseEvent.BUTTON3) {
                rightClickEvent(panel, e);
            }
        }
    }

    public static MetaDataGridPanels getInstance() {
        return instance;
    }
}

class ShowInstancesAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    Entry entry;

    public ShowInstancesAction(Entry entry) {
        this.entry = entry;
    }

    public void actionPerformed(ActionEvent e) {
        entry.ShowInstances();
    }

}
