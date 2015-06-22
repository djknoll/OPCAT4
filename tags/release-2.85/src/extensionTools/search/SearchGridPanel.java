package extensionTools.search;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import gui.util.opcatGrid.Grid;
import gui.util.opcatGrid.GridPanel;

/**
 * the Search Extension tools Grid Panel. this panel is shown in the extension
 * tools pane. inherits from the OpcatGrid Class, it manages the dblClick action
 * 
 * @author raanan
 * 
 */
public class SearchGridPanel extends GridPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public static String PanelName = "";

	private IXSystem mySys;

	private IXInstance lastIns = null;

	private Color lastColor = Color.black;

	public SearchGridPanel(int ColumnsNumber, Vector ColumnsNames,
			IXSystem opcatSystem) {
		super(ColumnsNumber, ColumnsNames);
		this.getGrid().addMouseListener(new MouseListner(this));
		this.mySys = opcatSystem;
		this.getGrid().setDuplicateRows(true);
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
	}

	public void AddRow(Object[] row, Object[] rowTag) {
		this.getGrid().addRow(row, rowTag);
	}

	public void RemoveFromExtensionToolsPanel(String tabName) {
		super.RemoveFromExtensionToolsPanel(tabName);
		this.RestoreThingOrigColor();
	}

	public void RestoreThingOrigColor() {
		if (this.lastIns != null) {
			this.lastIns.setBorderColor(this.lastColor);
		}
	}

	public void dblClickEvent() {
		Grid table = this.getGrid();

		Object[] tag = table.GetTag(table.getSelectedRow());
		
		IXInstance ins;
		tag = this.getGrid().GetTag(this.getGrid().getSelectedRow());
		OpdKey key = (OpdKey) tag[0];
		Long entityID = (Long) tag[1];
		ins = this.mySys.getIXSystemStructure()
				.getIXEntry(entityID.longValue()).getIXInstance(key);

		if ((this.lastIns != null) && (this.lastIns != ins)) {
			this.lastIns.setBorderColor(this.lastColor);
			this.lastIns.update();
		}
		if (this.lastIns != ins) {
			this.lastIns = ins;
			this.lastColor = this.lastIns.getBorderColor();
			ins.setBorderColor(Color.red);
			ins.update();
		}
		this.mySys.showOPD(key.getOpdId());

	}

	public class MouseListner extends MouseAdapter {
		private SearchGridPanel panel;

		public MouseListner(SearchGridPanel Panel) {
			this.panel = Panel;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				this.panel.dblClickEvent();
			}
		}
	}
}
