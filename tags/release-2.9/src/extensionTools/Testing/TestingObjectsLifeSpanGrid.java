package extensionTools.Testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JTabbedPane;
import javax.swing.table.TableCellRenderer;
import com.sciapp.table.AdvancedJTable;
import com.sciapp.table.span.CellSpan;
import com.sciapp.table.span.DefaultSpanModel;
import com.sciapp.table.span.SpanModel;
import com.sciapp.table.styles.Style;
import exportedAPI.opcatAPIx.IXSystem;
import gui.controls.GuiControl;
import gui.projectStructure.Entry;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ThingEntry;
import gui.util.opcatGrid.GridData;
import gui.util.opcatGrid.GridPanel;

public class TestingObjectsLifeSpanGrid {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GridPanel panel = null;

	private ArrayList entries = new ArrayList();

	private ArrayList cols = new ArrayList();

	public TestingObjectsLifeSpanGrid(IXSystem sys) {

		ArrayList tempEntries = new ArrayList();
		Iterator iter = Collections.list(
				sys.getIXSystemStructure().getElements()).iterator();

		cols.add("Name");
		cols.add("Type");
		while (iter.hasNext()) {
			Entry ent = (Entry) iter.next();
			if (ent instanceof ThingEntry) {
				tempEntries.add(ent);
			}
		}

		for (int i = tempEntries.size(); i > 0; i--) {
			entries.add(tempEntries.get(i - 1));
		}

		panel = new GridPanel(cols);

		for (int i = 0; i < entries.size(); i++) {
			Object[] row = new Object[cols.size()];
			row[0] = ((ThingEntry) entries.get(i)).getName();
			if (entries.get(i) instanceof ObjectEntry) {
				row[1] = "Object";
			} else {
				row[1] = "Process";
			}
			Object[] rowTag = new Object[2];
			rowTag[0] = entries.get(i);
			rowTag[1] = " ";
			panel.getGrid().addRow(row, rowTag);
		}

		panel.setTabName("Life-Span Diagram");
		panel.RemoveFromExtensionToolsPanel();
		panel.setEntryTag();
		// panel.AddToExtensionToolsPanel();
	}

	public void NextStep(IXSystem sys, long stepNumber) {
		// here we need to get all the objects and there testing status.
		// need to do this in the object Entry.

		// // if (stepNumber > 1) {
		// TreeTableModelAdapter adapter = (TreeTableModelAdapter)
		// panel.getGrid()
		// .getModel();
		// if (adapter != null) {
		// for (int i = 0; i < entries.size(); i++) {
		// TreeTableRow row = (TreeTableRow) adapter.nodeForRow(i);
		// Object[] myRow = (Object[]) row.getUserObject();
		// oldGrid.add(myRow);
		// }
		// } else {
		// OpcatLogger.logError("Adapter is null");
		// return;
		// }

		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();
		synchronized (tabbedPane) {

			GridData data = (GridData) panel.getGrid().getMyGridData();
			ArrayList oldGrid = new ArrayList();

			if (data.getColumnCount() - 2 >= stepNumber) {
				for (int i = 0; i < entries.size(); i++) {
					Object[] row = new Object[2];
					for (int j = 0; j < 2; j++) {
						row[j] = data.getValueAt(i, j);
					}
					oldGrid.add(row);
				}
			} else {
				oldGrid.addAll(data.getRows());
			}

			panel.RemoveFromExtensionToolsPanel();
			panel.ClearData();
			cols = new ArrayList();
			cols.add("Name");
			cols.add("Type");
			for (int i = 0; i < stepNumber; i++) {
				cols.add(new Long(i + 1));
			}
			panel = new GridPanel(cols);

			TableCellRenderer defRend = panel.getGrid().getDefaultRenderer(
					Object.class);
			TableCellRenderer rend = new TestingLifeSpanCellRenderer(defRend);

			panel.getGrid().setDefaultRenderer(Object.class, rend);

			Style[] style = panel.getGrid().getStyleModel().getStyles();
			for (int i = 0; i < style.length; i++) {
				panel.getGrid().getStyleModel().removeStyle(style[i]);
			}

			panel.getGrid().getSpanDrawer().setUseSpan(true);

			SpanModel sm = ((AdvancedJTable) panel.getGrid()).getSpanDrawer()
					.getSpanModel();

			for (int i = 0; i < entries.size(); i++) {
				Object[] row = new Object[cols.size()];

				// if (stepNumber > 1) {
				Object[] oldRow = (Object[]) oldGrid.get(i);
				for (int j = 0; j < oldRow.length; j++) {
					row[j] = oldRow[j];
				}

				TestingColor testColor = new TestingColor((ThingEntry) entries
						.get(i));
				
				
				//System.out.println(((ThingEntry) entries.get(i)).getName()
				//		+ ", " + testColor.getColor().getBlue() + ", "
				//		+ testColor.getColor().getGreen() + ", " + testColor.getColor().getRed());
				
				
				row[cols.size() - 1] = testColor;
				Object[] rowTag = new Object[2];
				rowTag[0] = entries.get(i);
				rowTag[1] = " ";
				panel.getGrid().addRow(row, rowTag);

				//if (entries.get(i) instanceof ObjectEntry) {
					int lastCol = 2;
					for (int j = 2; j < row.length - 1; j++) {
						if (!row[j].toString().equalsIgnoreCase(
								row[j + 1].toString())) {
							CellSpan cellSpan = new CellSpan(i, lastCol, 0, j
									- lastCol );
							((DefaultSpanModel) sm).addCellSpan(cellSpan);
							lastCol = j + 1;
						}
					}
					CellSpan cellSpan = new CellSpan(i, lastCol, 0, row.length
							- lastCol );
					((DefaultSpanModel) sm).addCellSpan(cellSpan);
				//}

			}

			panel.setTabName("Life-Span Diagram");
			panel.setEntryTag();

			// cellSpan = new CellSpan(0,0,0,CellSpan.ALL_ROWS) ;
			// dsm.addCellSpan(cellSpan) ;

			panel.AddToExtensionToolsPanel();
		}
	}

	public void RemoveFromExtensionToolsPanel() {
		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();
		synchronized (tabbedPane) {
			panel.RemoveFromExtensionToolsPanel();
		}
	}

	public void AddToExtensionToolsPanel() {
		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();
		synchronized (tabbedPane) {
			panel.AddToExtensionToolsPanel();
		}
	}

	public void ClearData() {
		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();
		synchronized (tabbedPane) {
			panel.ClearData();
		}
	}

	public GridPanel getPanel() {
		return panel;
	}
}
