package gui.metaDataProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JCheckBox;

import com.sciapp.renderers.BooleanRenderer;

import gui.metaLibraries.logic.LibraryTypes;
import gui.metaLibraries.logic.Role;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ThingEntry;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;

public class MetaDataItemAnalisysPanel extends GridPanel implements
		ActionListener {
	/**
	 * 
	 */

	// cols.add("Thing");
	// cols.add("Connected Thing");
	// cols.add("Is Connected");
	// cols.add("Connection Type");
	private static final long serialVersionUID = 1L;

	Vector things;

	MetaDataItem item;

	Vector headers;

	Role role;

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Iterator iter = things.iterator();
		HashMap connctedMap = new HashMap();
		this.ClearData();
		this.RemoveFromExtensionToolsPanel();

		while (iter.hasNext()) {
			OpmThing opmThing = (OpmThing) iter.next();
			ThingEntry thing = ThingEntry.getEntryFromOpmThing(opmThing);

			connctedMap = thing.getConnectedThings();
			Iterator connectedIter = connctedMap.values().iterator();

			while (connectedIter.hasNext()) {
				Object[] array = (Object[]) connectedIter.next() ; 
				ThingEntry connected = (ThingEntry) ( array[0]) ;
				OpmProceduralLink link =(OpmProceduralLink) ( array[1]) ; 

				Object row[] = new Object[headers.size()];
				Object rowTag[] = new Object[2];

				row[0] = thing.getName();

				row[1] = connected.getName();

				boolean isConnected = false ; 
				Iterator roleIter = Collections.list(
						connected.getRoles(LibraryTypes.MetaData)).iterator();
				while (roleIter.hasNext()) {
					Role tempRole = (Role) roleIter.next();
					MetaDataItem tempItem = (MetaDataItem) tempRole.getThing()
							.getDataInstance();
					if ((role.getLibraryId() == tempRole.getLibraryId())
							&& (item.getId() == tempItem.getId())) {
						isConnected = true ; 
						break ; 
					}
				}

				JCheckBox chk = new JCheckBox();
				BooleanRenderer cellRenderer = new BooleanRenderer(chk);
				getGrid().getColumnModel().getColumn(2).setCellRenderer(
						cellRenderer);
				row[2] = new Boolean(isConnected);
				row[3] = link.getName().substring(0, link.getName().indexOf(" "));

				rowTag[0] = connected.getLogicalEntity();
				rowTag[1] = " ";
				this.getGrid().addRow(row, rowTag);
			}
		}
		AddToExtensionToolsPanel();

	}

	public MetaDataItemAnalisysPanel(Vector cols, MetaDataItem req,
			Vector connctedThings, Role role) {
		super(cols.size(), cols);
		this.RemoveFromExtensionToolsPanel();
		this.ClearData();
		setTabName("Analisys : " + req.getName() + "(" + req.getExtID() + ")");
		item = req;
		things = connctedThings;
		headers = cols;
		this.role = role;
		getGrid().addMouseListener(new MouseListner(this));
	}

	private void showThings() {
		Grid table = getGrid();
		if (table == null)
			return;
		Vector things = new Vector();
		// table is the TreeTable model
		int[] selectedRows = table.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {
			Object[] tag = table.GetTag(selectedRows[i]);
			OpmThing theThing = (OpmThing) tag[0];
			things.add(theThing);
		}
		ThingEntry.ShowInstances(things);
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
		}
	}
}
