package gui.util.opcatGrid;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import gui.controls.GuiControl;
import gui.images.grid.GridImages;
import gui.util.debug.Debug;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import com.sciapp.table.AdvancedJScrollPane;
import com.sciapp.tree.GroupingPanel;
import com.sciapp.tree.TreeTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class GridPanel extends JPanel {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	private Grid myGrid;

	private String panelName = "";

	private boolean defualtTag = false;

	private IXSystem mySys = null;

	private IXInstance lastIns = null;

	private Color lastColor = Color.black;

	private JPanel buttonPane;

	public JPanel getButtonPane() {
		return this.buttonPane;
	}

	public GridPanel(int ColumnsNumber, Vector ColumnsNames) {
		super();

		// myGrid = new Grid(myGridData);
		this.myGrid = new Grid(ColumnsNumber, ColumnsNames);

		JPanel groupingPanel = new GroupingPanel((TreeTableModel) this.myGrid
				.getModel());

		AdvancedJScrollPane scrollPane = new AdvancedJScrollPane(
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(this.myGrid);
		this.myGrid.setShowRowHeader(true);
		// JScrollPane scrollPane = new JScrollPane(myGrid);
		// myGrid.setPreferredScrollableViewportSize(new Dimension(500, 70));

		this.setLayout(new BorderLayout());
		this.add(groupingPanel, BorderLayout.SOUTH);
		this.add(scrollPane, BorderLayout.CENTER);

		JButton clearButton = new JButton("   Clear   ");

		JButton closeButton = new JButton("   Close   ");

		this.buttonPane = new JPanel();
		this.buttonPane.setLayout(new GridLayout(0, 1, 5, 2)); // buttonPane,
															// BoxLayout.PAGE_AXIS
		this.buttonPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		this.buttonPane.add(clearButton);
		this.buttonPane.add(closeButton);
		this.buttonPane.add(new JLabel(" "));

		this.add(this.buttonPane, BorderLayout.LINE_END);
		clearButton.addActionListener(new ClearButton(this.myGrid));
		closeButton.addActionListener(new CloseButton(this));

		this.getGrid().addMouseListener(new MouseListner(this));

		JTree tree = this.getGrid().getTree();
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		renderer.setClosedIcon(GridImages.FOLDER);
		renderer.setOpenIcon(GridImages.FOLDEROPEN);
		renderer.setLeafIcon(GridImages.LEAF);
	}

	// public GridData GetGridData() {
	// return myGrid.getGridData();
	// }

	public void ClearData() {
		this.myGrid.ClearData();
	}

	public TableColumnModel GetColumnsModel() {
		return this.myGrid.getColumnModel();
	}

	class ClearButton implements java.awt.event.ActionListener {
		private Grid myGrid;

		public ClearButton(Grid grid) {
			this.myGrid = grid;
		}

		public void actionPerformed(ActionEvent e) {
			this.myGrid.ClearData();
			this.myGrid.getListModel().fireTableDataChanged();
		}
	}

	class CloseButton implements java.awt.event.ActionListener {
		private GridPanel panel;

		public CloseButton(GridPanel pane) {
			this.panel = pane;
		}

		public void actionPerformed(ActionEvent e) {
			this.panel.ClearData();
			this.panel.RemoveFromExtensionToolsPanel(this.panel.getPanelName());
		}
	}

	public void RemoveFromExtensionToolsPanel(String tabName) {

		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();

		int indexOfValidation = tabbedPane.indexOfTab(tabName);
		if (indexOfValidation > 0) {
			try {
				tabbedPane.remove(indexOfValidation);
			} catch (Exception ex) {
				Debug debug = Debug.getInstance();
				debug.Print(this, ex.getMessage(), "debug");
			}

		}
	}

	public void AddToExtensionToolsPanel(String tabName) {
		GuiControl gui = GuiControl.getInstance();
		JTabbedPane tabbedPane = gui.getExtensionToolsPane();

		for (int i = 0; i < tabbedPane.getTabCount(); i++) {

			if (tabbedPane.getTitleAt(i).equals(tabName)) {
				tabbedPane.setSelectedIndex(i);
				return;
			}

		}

		if (this != null) {
			this.panelName = tabName;
			tabbedPane.add(tabName, this);
			tabbedPane.setSelectedComponent(this);
		}
	}

	public int GetSelectedRowNumber() {
		return this.myGrid.getSelectedRow();
	}

	public Grid getGrid() {
		return this.myGrid;
	}

	/**
	 * @return Returns the panelName.
	 */
	public String getPanelName() {
		return this.panelName;
	}

	public boolean isDefualtTag() {
		return this.defualtTag;
	}

	public void setDefualtTag(boolean defualtTag, IXSystem sys) {
		this.defualtTag = defualtTag;

		if (defualtTag == true) {
			this.mySys = sys;
		} else {
			this.mySys = null;
		}
	}

	public void dblClickEvent() {

		if (this.mySys == null) {
			return;
		}

		Object[] tag = new Object[2];
		IXInstance ins;
		tag = this.getGrid().GetTag(this.getGrid().getSelectedRow());
		OpdKey key = (OpdKey) tag[0];
		Long entityID = (Long) tag[1];
		ins = this.mySys.getIXSystemStructure().getIXEntry(entityID.longValue())
				.getIXInstance(key);

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

	public void RestoreThingOrigColor() {
		if (this.lastIns != null) {
			this.lastIns.setBorderColor(this.lastColor);
		}
	}

	// public void AllignButtons() {
	// int maxLength = 0 ;
	//		
	// for(int i = 0 ; i < getButtonPane().getComponentCount() ; i ++ ) {
	// if(getButtonPane().getComponent(i) instanceof JButton) {
	// JButton curButton = (JButton) getButtonPane().getComponent(i) ;
	// if(maxLength < curButton.getWidth()) maxLength = curButton.getWidth();
	// }
	// }
	//		
	// for(int i = 0 ; i < getButtonPane().getComponentCount() ; i ++ ) {
	// if(getButtonPane().getComponent(i) instanceof JButton) {
	// JButton curButton = (JButton) getButtonPane().getComponent(i) ;
	// curButton.setSize(new Dimension(maxLength, curButton.getWidth())) ;
	// }
	// }
	// }

	public class MouseListner extends MouseAdapter {
		private GridPanel panel;

		public MouseListner(GridPanel Panel) {
			this.panel = Panel;
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				this.panel.dblClickEvent();
			}
		}
	}

}
