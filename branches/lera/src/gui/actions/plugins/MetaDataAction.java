package gui.actions.plugins;

import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;

import com.sciapp.renderers.BooleanRenderer;
import com.sciapp.renderers.ProgressBarRenderer;
import com.sciapp.treetable.*;
import gui.metaDataProject.MetaDataItem;
import gui.metaDataProject.MetaDataItemAnalisysPanel;
import gui.metaDataProject.MetaDataProject;
import gui.metaLibraries.logic.DataInstance;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.LibraryTypes;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;

public class MetaDataAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * 
	 */

	private FileControl file = FileControl.getInstance();

	private GuiControl gui = GuiControl.getInstance();

	HashMap gridPanels = new HashMap();

	public MetaDataAction(String name, Icon icon) {
		super(name, icon);
	}

	private GridPanel InitGrid(MetaDataProject prj) {

		if ((gridPanels != null) && gridPanels.containsKey(prj.getType())) {
			return (GridPanel) gridPanels.get(prj.getType());
		}

		GridPanel gridPanel;

		// gridPanel.AllignButtons() ;
		Vector colNames = new Vector();
		for (int i = 0; i < prj.getHeaders().size(); i++) {
			colNames.add(prj.getHeaders().elementAt(i));
		}
		colNames.add("Connected?");
		colNames.add("Things");

		gridPanel = new GridPanel(colNames.size(), colNames);

		gridPanel.getGrid().getSortedModel().setComparator(0,
				new ReqComparator());
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ConnectButton(this));
		gridPanel.getButtonPane().add(btnConnect);

		gridPanel.getGrid().addMouseListener(new MouseListner(gridPanel));

		JButton btnDisconnect = new JButton("Disconnect");
		gridPanel.getButtonPane().add(btnDisconnect);
		gridPanel.getButtonPane().add(new JLabel(""));
		btnDisconnect.addActionListener(new DisconnectButton(this));
		JButton btnUnconnected = new JButton("Not Assigned");
		gridPanel.getButtonPane().add(btnUnconnected);
		btnUnconnected.addActionListener(new UnconnectedButton(this));

		JButton btnMissing = new JButton("Missing");
		gridPanel.getButtonPane().add(btnMissing);
		btnMissing.addActionListener(new MissingButton(this));

		gridPanel.setTabName(prj.getType());

		gridPanels.put(prj.getType(), gridPanel);
		return gridPanel;

	}

	public void actionPerformed(ActionEvent action) {

		// TODO Auto-generated method stub
		if (!this.file.isProjectOpened()) {
			JOptionPane
					.showMessageDialog(this.gui.getFrame(),
							"No system is opened", "Message",
							JOptionPane.ERROR_MESSAGE);
			return;
		}
		doLoadReq();

	}

	private void doLoadReq() {
		// set a style for header and footer rows

		// file.getCurrentProject().getMetaManager().loadDummyReqProject();

		Enumeration ontologies = this.file.getCurrentProject().getMetaManager()
				.getMetaLibraries(LibraryTypes.MetaData);
		try {
			while (ontologies.hasMoreElements()) {
				MetaLibrary ont = (MetaLibrary) ontologies.nextElement();
				GridPanel gridPanel = InitGrid((MetaDataProject) ont
						.getProjectHolder());
				gridPanel.ClearData();
				gridPanel.AddToExtensionToolsPanel();

				Vector allRoles = new Vector();
				allRoles.addAll(ont.getRolesCollection());

				int levelIndex = ((MetaDataProject) ont.getProjectHolder())
						.getLevelIndex();
				if (levelIndex >= 0) {
					JProgressBar bar = new JProgressBar(0,
							((MetaDataProject) ont.getProjectHolder())
									.getMaxLevelValue());
					ProgressBarRenderer cellRenderer = new ProgressBarRenderer(
							bar);
					gridPanel.getGrid().getColumnModel().getColumn(levelIndex)
							.setCellRenderer(cellRenderer);
				}

				int connectedIndex = ((MetaDataProject) ont.getProjectHolder())
						.getHeaders().size();
				JCheckBox chk = new JCheckBox();
				BooleanRenderer cellRenderer = new BooleanRenderer(chk);
				gridPanel.getGrid().getColumnModel().getColumn(connectedIndex)
						.setCellRenderer(cellRenderer);

				// get a hash map of (req, thingsVector connected to it)

				Iterator iter = allRoles.iterator();
				while (iter.hasNext()) {
					Role role = (Role) iter.next();
					DataInstance dataIns = role.getThing();
					MetaDataItem req = (MetaDataItem) dataIns.getDataInstance();

					// get connected thing.
					String things = this.connectedThingstoString(req);
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
		} catch (MetaException E) {
			// JOptionPane.showMessageDialog(this, E.getMessage(), "Message",
			// JOptionPane.ERROR_MESSAGE);
			// OpcatLogger.logError(E);
		}

	}

	private Grid getCurrentTable() {

		gui = GuiControl.getInstance();
		Object panel = gui.getExtensionToolsPane().getSelectedComponent();
		if (panel instanceof GridPanel) {
			Grid table = ((GridPanel) panel).getGrid();
			return table;
		} else {
			return null;
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
						.list(this.file.getCurrentProject().getCurrentOpd()
								.getSelectedItems());

				Iterator selectedIter = thingsList.iterator();

				Object[] tag = table.GetTag(selectedRows[row]);
				Role role = (Role) tag[0];

				while (selectedIter.hasNext()) {
					Object obj = selectedIter.next();
					if ((obj instanceof ObjectInstance)
							|| (obj instanceof ProcessInstance)) {
						OpmThing theThing = (OpmThing) ((Instance) obj)
								.getEntry().getLogicalEntity();
						theThing.getRolesManager().addRole(role);
						for (int i = 0; i < selectedRows.length; i++) {

						}
					}
				}
				if (thingsList.size() > 0) {
					MetaDataItem req = (MetaDataItem) tag[1];
					Object[] data = (Object[]) treeRow.getUserObject();
					data[data.length - 2] = new Boolean(true);
					data[data.length - 1] = this.connectedThingstoString(req);
				}
			}
		}
		getCurrentTable().getSortedModel().fireTableDataChanged();
		Opcat2.getCurrentProject().setCanClose(false);
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

			List selectedThings = Collections.list(this.file
					.getCurrentProject().getCurrentOpd().getSelectedItems());
			Iterator selectedIter = selectedThings.iterator();
			while (selectedIter.hasNext()) {
				Object obj = selectedIter.next();
				if ((obj instanceof ObjectInstance)
						|| (obj instanceof ProcessInstance)) {
					OpmThing theThing = (OpmThing) ((Instance) obj).getEntry()
							.getLogicalEntity();
					theThing.getRolesManager().removeRole(role);
				}
			}
			if (selectedThings.size() > 0) {
				MetaDataItem req = (MetaDataItem) tag[1];
				Object[] data = (Object[]) treeRow.getUserObject();
				String str = this.connectedThingstoString(req);
				data[data.length - 1] = str;
				if (str.equalsIgnoreCase("")) {
					data[data.length - 2] = new Boolean(false);
				}
			}
		}
		getCurrentTable().getSortedModel().fireTableDataChanged();
		Opcat2.getCurrentProject().setCanClose(false);
	}

	private String connectedThingstoString(MetaDataItem req) {
		Vector connectedThings = this.connectedThings(req);
		String names = "";
		for (int j = 0; j < connectedThings.size(); j++) {
			names = names + ((OpmThing) connectedThings.elementAt(j)).getName();
			if (j < connectedThings.size() - 1) {
				names = names + ",";
			}
		}
		return names;
	}

	/**
	 * returns a map of (req, Conected Things)
	 * 
	 * @param reqExtID
	 * @return
	 */
	private Vector connectedThings(MetaDataItem req) {

		Vector things = new Vector();

		List allTthings = Collections.list(this.file.getCurrentProject()
				.getComponentsStructure().getElements());
		Iterator iter = allTthings.iterator();
		while (iter.hasNext()) {
			Object entry = iter.next();
			if ((entry instanceof ProcessEntry)
					|| (entry instanceof ObjectEntry)) {
				OpmThing theThing = (OpmThing) ((Entry) entry)
						.getLogicalEntity();
				Iterator rolesIter = Collections.list(
						theThing.getRolesManager().getLoadedRolesVector(
								LibraryTypes.MetaData).elements()).iterator();
				while (rolesIter.hasNext()) {
					Role role = (Role) rolesIter.next();
					// if (role.getThing().getDataInstance() != null) {
					MetaDataItem tempReq = (MetaDataItem) role.getThing()
							.getDataInstance();
					if (tempReq.getId() == req.getId()) {
						things.add(theThing);
					}
					// } else {
					// theThing.getRolesManager().removeRole(role) ;
					// }
				}

			}

		}

		return things;
	}

	private void unconnected() {

		Vector things = new Vector();

		Iterator elementsIter = Collections.list(
				this.file.getCurrentProject().getSystemStructure()
						.getElements()).iterator();

		while (elementsIter.hasNext()) {
			Object obj = elementsIter.next();
			if ((obj instanceof ProcessEntry) || (obj instanceof ObjectEntry)) {
				ThingEntry ent = (ThingEntry) obj;
				if (!ent.getRoles(LibraryTypes.MetaData).hasMoreElements()) {
					things.add(ent.getLogicalEntity());
				}
			}
		}

		ThingEntry.ShowInstances(things);
	}

	private void missing() {
		Vector things = new Vector();

		Iterator elementsIter = Collections.list(
				this.file.getCurrentProject().getSystemStructure()
						.getElements()).iterator();

		while (elementsIter.hasNext()) {
			Object obj = elementsIter.next();
			if ((obj instanceof ProcessEntry) || (obj instanceof ObjectEntry)) {
				ThingEntry ent = (ThingEntry) obj;
				OpmThing opm = (OpmThing) ent.getLogicalEntity();
				if (opm.getRolesManager().getNotLoadedRolesVector(
						LibraryTypes.MetaData).size() > 0) {
					things.add(ent.getLogicalEntity());
				}
			}
		}

		ThingEntry.ShowInstances(things);

	}

	private void rightClickEvent(GridPanel panel, MouseEvent e) {
		JPopupMenu metaDataPopupMenu = panel.getRMenu();

		if (panel.getGrid().getSelectedRow() >= 0) {
			Object[] tag = new Object[2];
			tag = panel.getGrid().GetTag(panel.getGrid().getSelectedRow());
			Role role = (Role) tag[0];
			MetaDataItem req = (MetaDataItem) tag[1];
			JMenuItem analyze = new JMenuItem("Analyze : " + req.getName());
			metaDataPopupMenu.add(analyze);
			Vector cols = new Vector();
			cols.add("Thing");
			cols.add("Connected Thing");
			cols.add("Is Connected");
			cols.add("Connection Type");
			analyze.addActionListener(new MetaDataItemAnalisysPanel(cols, req,
					connectedThings(req), role));
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
			MetaDataItem req = (MetaDataItem) tag[1];
			Vector connectedThings = this.connectedThings(req);
			Iterator thingsIter = connectedThings.iterator();
			while (thingsIter.hasNext()) {
				OpmThing theThing = (OpmThing) thingsIter.next();
				things.add(theThing);
			}

		}
		ThingEntry.ShowInstances(things);
	}

	class ConnectButton implements java.awt.event.ActionListener {
		private MetaDataAction panel;

		public ConnectButton(MetaDataAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.connect();
		}
	}

	class DisconnectButton implements java.awt.event.ActionListener {
		private MetaDataAction panel;

		public DisconnectButton(MetaDataAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.disconnect();
		}
	}

	class UnconnectedButton implements java.awt.event.ActionListener {
		private MetaDataAction panel;

		public UnconnectedButton(MetaDataAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.unconnected();
		}
	}

	class MissingButton implements java.awt.event.ActionListener {
		private MetaDataAction panel;

		public MissingButton(MetaDataAction action) {
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
}

class ReqComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		// first decompose the indentations.
		String[] s1 = ((String) o1).split("[.]");
		String[] s2 = ((String) o2).split("[.]");

		int min = s2.length;
		if (s1.length < s2.length) {
			min = s1.length;
		}

		int i = 0;
		while ((i < min)
				&& (s1[i].toUpperCase().compareTo(s2[i].toUpperCase()) == 0)) {
			i++;
		}
		if (i == min) {
			if (s1.length < s2.length) {
				return -1;
			}
			if (s1.length > s2.length) {
				return 1;
			}
			return 0;
		} else {

			Long l1;
			try {
				l1 = new Long(s1[i]);
			} catch (Exception e) {
				l1 = new Long(-1);
			}

			Long l2;
			try {
				l2 = new Long(s2[i]);
			} catch (Exception e) {
				l2 = new Long(-1);
			}

			if (l1.longValue() > l2.longValue()) {
				return 1;
			}
			if (l1.longValue() < l2.longValue()) {
				return -1;
			}
			return 0;
		}
	}
}
