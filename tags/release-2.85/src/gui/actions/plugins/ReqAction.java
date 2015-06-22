package gui.actions.plugins;

import gui.controls.FileControl;
import gui.controls.GuiControl;
import com.sciapp.tree.TreeTableModel;
import com.sciapp.tree.TreeTableRow;

import gui.metaLibraries.logic.DataInstance;
import gui.metaLibraries.logic.MetaException;
import gui.metaLibraries.logic.MetaLibrary;
import gui.metaLibraries.logic.Role;
import gui.metaLibraries.logic.Types;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessEntry;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.ThingEntry;
import gui.reqProject.Req;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReqAction extends AbstractAction {

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

	GridPanel gridPanel;

	public ReqAction(String name, Icon icon) {
		super(name, icon);

		// gridPanel.AllignButtons() ;
		Vector colNames = new Vector();
		colNames.add("Level");
		colNames.add("Covered?");
		colNames.add("Things");
		colNames.add("Name");
		colNames.add("Description");
		colNames.add("Risk");
		colNames.add("ID");
		
		this.gridPanel = new GridPanel(7, colNames);
		
		this.gridPanel.getGrid().getSortedModel().setComparator(0,
				new ReqComparator());
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ConnectButton(this));
		this.gridPanel.getButtonPane().add(btnConnect);

		this.gridPanel.getGrid().addMouseListener(new MouseListner());

		JButton btnDisconnect = new JButton("Disconnect");
		this.gridPanel.getButtonPane().add(btnDisconnect);
		this.gridPanel.getButtonPane().add(new JLabel(""));
		btnDisconnect.addActionListener(new DisconnectButton(this));
		JButton btnUnconnected = new JButton("Not Assigned");
		this.gridPanel.getButtonPane().add(btnUnconnected);
		btnUnconnected.addActionListener(new UnconnectedButton(this));
		this.gridPanel.getButtonPane().add(new JLabel(""));
		// gridPanel.getGrid().getColumnModel().getColumn(6)
		//this.gridPanel.getGrid().getColumnModel().removeColumn(
		//		this.gridPanel.getGrid().getColumnModel().getColumn(6));

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

		if (this.gridPanel.isShowing()) {
			this.gridPanel.ClearData();
		}

		//
		Thread runner = new Thread() {
			public void run() {
				ReqAction.this.gui.startWaitingMode("Loading Requirments...",
						true);
				try {
					ReqAction.this.doLoadReq();
				} catch (Exception e) {
					ReqAction.this.gui.stopWaitingMode();
					OpcatLogger.logError(e);
					FileControl file = FileControl.getInstance();
					JOptionPane.showMessageDialog(file.getCurrentProject()
							.getMainFrame(),
							"Loading had failed becasue of the following error:\n"
									+ e.getMessage(), "Message",
							JOptionPane.ERROR_MESSAGE);
				} finally {
					ReqAction.this.gui.stopWaitingMode();
				}
			}

		};
		runner.start();
		//		

	}

	private void doLoadReq() {
		// set a style for header and footer rows

		// file.getCurrentProject().getMetaManager().loadDummyReqProject();

		Vector allRoles = new Vector();
		Enumeration ontologies = this.file.getCurrentProject().getMetaManager()
				.getMetaLibraries(Types.RequirementsData);
		try {
			while (ontologies.hasMoreElements()) {
				MetaLibrary ont = (MetaLibrary) ontologies.nextElement();
				allRoles.addAll(ont.getRolesCollection());
			}
		} catch (MetaException E) {
			// JOptionPane.showMessageDialog(this, E.getMessage(), "Message",
			// JOptionPane.ERROR_MESSAGE);
			// OpcatLogger.logError(E);
		}
		// get a hash map of (req, thingsVector connected to it)

		Iterator iter = allRoles.iterator();
		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			DataInstance dataIns = role.getThing();
			Req req = (Req) dataIns.getDataInstance();

			// get connected thing.
			String things = this.connectedThingstoString(req);
			// first we need to add an aggragator row for the thig coloumn
			Object[] row = new Object[7];
			Object[] rowTag = new Object[2];
			if (things.equalsIgnoreCase("")) {
				row[1] = "No";
			} else {
				row[1] = "Yes";
			}
			row[0] = req.getReqIndentNumber();
			row[2] = things;
			row[3] = req.getName();
			row[4] = req.getDescription();
			row[5] = req.getRisk();
			row[6] = req.getExtId();
			rowTag[0] = role; // set to thing real tag if it exists
			rowTag[1] = req; // //set to thing real tag if it exists
			this.gridPanel.getGrid().addRow(row, rowTag);

		}

		gridPanel.AddToExtensionToolsPanel("Requirments");
		//gridPanel.getGrid().setTableState("0:0,1:0,2:0,3:0,4:0,5:0") ;		
		// gridPanel.getGrid().getTreeModel().fireTableDataChanged();

	}

	private void connect() {

		Grid table = this.gridPanel.getGrid();
		// table is the TreeTable model
		int[] selectedRows = table.getSelectedRows();
		TreeTableModel ttm = (TreeTableModel) table.getModel();
		List treeList = ttm.getRows();

		for (int row = 0; row < selectedRows.length; row++) {
			TreeTableRow treeRow = (TreeTableRow) treeList
					.get(selectedRows[row]);

			List thingsList = Collections.list(this.file.getCurrentProject()
					.getCurrentOpd().getSelectedItems());

			Iterator selectedIter = thingsList.iterator();

			Object[] tag = table.GetTag(selectedRows[row]);
			Role role = (Role) tag[0];

			while (selectedIter.hasNext()) {
				Object obj = selectedIter.next();
				if ((obj instanceof ObjectInstance)
						|| (obj instanceof ProcessInstance)) {
					OpmThing theThing = (OpmThing) ((Instance) obj).getEntry()
							.getLogicalEntity();
					theThing.getRolesManager().addRole(role);
					for (int i = 0; i < selectedRows.length; i++) {

					}
				}
			}
			if (thingsList.size() > 0) {
				Req req = (Req) tag[1];
				Object[] data = (Object[]) treeRow.getUserObject();
				data[1] = "Yes";
				data[2] = this.connectedThingstoString(req);
			}
		}
		this.gridPanel.getGrid().getSortedModel().fireTableDataChanged();
	}

	private void disconnect() {

		Grid table = this.gridPanel.getGrid();
		// table is the TreeTable model
		int[] selectedRows = table.getSelectedRows();
		TreeTableModel ttm = (TreeTableModel) table.getModel();
		List treeList = ttm.getRows();

		for (int row = 0; row < selectedRows.length; row++) {
			TreeTableRow treeRow = (TreeTableRow) treeList
					.get(selectedRows[row]);
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
				Req req = (Req) tag[1];
				Object[] data = (Object[]) treeRow.getUserObject();
				String str = this.connectedThingstoString(req);
				data[2] = str;
				if (str.equalsIgnoreCase("")) {
					data[1] = "No";
				}
			}
		}
		this.gridPanel.getGrid().getSortedModel().fireTableDataChanged();
	}

	private String connectedThingstoString(Req req) {
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
	private Vector connectedThings(Req req) {

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
						theThing.getRolesManager().getRolesVector(
								Types.RequirementsData).elements()).iterator();
				while (rolesIter.hasNext()) {
					Role role = (Role) rolesIter.next();
					Req tempReq = (Req) role.getThing().getDataInstance();
					if (tempReq.getExtId().equals(req.getExtId())) {
						things.add(theThing);
					}
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
				if (!ent.getRoles(Types.RequirementsData).hasMoreElements()) {
					things.add(ent.getLogicalEntity());
				}
			}
		}

		ThingEntry.ShowInstances(things);
	}

	private void showThings() {

		Grid table = this.gridPanel.getGrid();
		Vector things = new Vector();
		// table is the TreeTable model
		int[] selectedRows = table.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			Object[] tag = table.GetTag(selectedRows[i]);
			Req req = (Req) tag[1];
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
		private ReqAction panel;

		public ConnectButton(ReqAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.connect();
		}
	}

	class DisconnectButton implements java.awt.event.ActionListener {
		private ReqAction panel;

		public DisconnectButton(ReqAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.disconnect();
		}
	}

	class UnconnectedButton implements java.awt.event.ActionListener {
		private ReqAction panel;

		public UnconnectedButton(ReqAction action) {
			this.panel = action;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.unconnected();
		}
	}

	class MouseListner extends MouseAdapter {

		public MouseListner() {
			super() ; 
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				showThings();
			}
			// if (e.getButton() == MouseEvent.BUTTON3 ) {
			// DelimitedExportManager exportMan = new DelimitedExportManager() ;
			// exportMan.write(gridPanel.getGrid().getModel(), System.out) ;
			// }
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
