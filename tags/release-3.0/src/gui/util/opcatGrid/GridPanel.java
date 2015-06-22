package gui.util.opcatGrid;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import gui.Opcat2;
import gui.controls.FileControl;
import gui.controls.GuiControl;
import gui.images.grid.GridImages;
import gui.images.standard.StandardImages;
import gui.projectStructure.Entry;
import gui.projectStructure.ThingEntry;
import gui.util.CustomFileFilter;
import gui.util.OpcatLogger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.sciapp.table.AdvancedJScrollPane;
import com.sciapp.table.io.DelimitedExportManager;
import com.sciapp.table.io.XMLExportManager;
import com.sciapp.treetable.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class GridPanel extends JPanel {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */

    private JPopupMenu RMenu = new JPopupMenu(" ");

    private Grid myGrid;

    private String tabName = "";

    private boolean instanceTag = false;

    private boolean entryTag = false;

    private IXSystem mySys = null;

    private static IXInstance lastIns = null;

    private static Color lastColor = Color.black;

    private JPanel buttonPane;

    private boolean onExtensionToolsPane = false;

    public JPanel getButtonPane() {
	return this.buttonPane;
    }

    public GridPanel(ArrayList columnsNames) {
	super();

	// myGrid = new Grid(myGridData);
	this.myGrid = new Grid(columnsNames);

	JPanel groupingPanel = new GroupingPanel(this.getGrid().getTreeModel());

	AdvancedJScrollPane scrollPane = new AdvancedJScrollPane(
		ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	//AdvancedJScrollPane scrollPane = new AdvancedJScrollPane(myGrid);

	//scrollPane.add(myGrid) ; 
	scrollPane.setViewportView(this.myGrid);

	this.setLayout(new BorderLayout());
	this.add(scrollPane, BorderLayout.CENTER);
	this.add(groupingPanel, BorderLayout.SOUTH);

	JButton clearButton = new JButton("      Clear      ");
	clearButton.setIcon(StandardImages.GRID_PANEL_CLEAR);

	JButton closeButton = new JButton("      Close      ");
	closeButton.setIcon(StandardImages.GRID_PANEL_CLOSE);

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

	myGrid.setMinimumSize(new Dimension(0, 0));
	this.setMinimumSize(new Dimension(0, 0));
	this.buttonPane.setMinimumSize(new Dimension(0, 0));
	scrollPane.setMinimumSize(new Dimension(0, 0));

	myGrid.addFocusListener(new GridFocusListner(this));
	this.tabName = "No Tab Name Set";

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
	    updateColor(null);
	}
    }

    class CloseButton implements java.awt.event.ActionListener {
	private GridPanel panel;

	public CloseButton(GridPanel pane) {
	    this.panel = pane;
	}

	public void actionPerformed(ActionEvent e) {
	    this.panel.ClearData();
	    this.panel.RemoveFromExtensionToolsPanel();
	}
    }

    public static void RemoveALLPanels() {
	GuiControl gui = GuiControl.getInstance();
	JTabbedPane tabbedPane = gui.getExtensionToolsPane();

	int tabCount = tabbedPane.getTabCount();
	for (int i = tabCount - 1; i > 0; i--)
	    tabbedPane.remove(i);

    }

    public static void RemovePanel(String name) {
	GuiControl gui = GuiControl.getInstance();
	JTabbedPane tabbedPane = gui.getExtensionToolsPane();

	int indexOfValidation = tabbedPane.indexOfTab(name);
	if (indexOfValidation > 0) {
	    try {
		tabbedPane.remove(indexOfValidation);
	    } catch (Exception ex) {
		OpcatLogger.logError(ex);
	    }
	}

    }

    public void RemoveFromExtensionToolsPanel() {

	GuiControl gui = GuiControl.getInstance();
	JTabbedPane tabbedPane = gui.getExtensionToolsPane();

	int indexOfValidation = tabbedPane.indexOfTab(this.getTabName());
	if (indexOfValidation > 0) {
	    try {
		tabbedPane.remove(indexOfValidation);
	    } catch (Exception ex) {
		OpcatLogger.logError(ex);
	    }

	}
	GridPanel.updateColor(null);
	onExtensionToolsPane = false;
    }

    public void AddToExtensionToolsPanel() {
	String tabName = getTabName();
	GuiControl gui = GuiControl.getInstance();
	JTabbedPane tabbedPane = gui.getExtensionToolsPane();

	for (int i = 0; i < tabbedPane.getTabCount(); i++) {

	    if (tabbedPane.getTitleAt(i).equals(tabName)) {
		tabbedPane.setSelectedIndex(i);
		return;
	    }

	}

	if (this != null) {
	    this.tabName = tabName;
	    tabbedPane.add(tabName, this);
	    tabbedPane.setSelectedComponent(this);
	    onExtensionToolsPane = true;
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
    public String getTabName() {
	return this.tabName;
    }

    /**
         * sets the dblClick event to color the clicked on instance when
         * dblclicking on the grid. only one from EntryTag or InstanceTag could
         * be active at any given time.
         * 
         * for this to work the tag foe every row should be -
         * <code> OpdKey key = (OpdKey)tag[0]; 
     * Long entityID = (Long) tag[1];
     * </code>
         * 
         * @param instanceTag
         * @param sys
         */
    public void setInstanceTag(IXSystem sys) {
	this.instanceTag = true;
	this.entryTag = false;

	if (instanceTag == true) {
	    this.mySys = sys;
	} else {
	    this.mySys = null;
	}
    }

    public void dblClickEvent(MouseEvent e) {

	if (this.getGrid().getSelectedRow() < 0) {
	    return;
	}
	Object[] tag = new Object[2];
	tag = this.getGrid().GetTag(this.getGrid().getSelectedRow());

	if (instanceTag && this.mySys != null) {
	    IXInstance ins;
	    OpdKey key = (OpdKey) tag[0];
	    Long entityID = (Long) tag[1];
	    ins = this.mySys.getIXSystemStructure().getIXEntry(
		    entityID.longValue()).getIXInstance(key);

	    this.mySys.showOPD(key.getOpdId());
	    updateColor(ins);
	}

	if (entryTag) {
	    Entry ent = (Entry) tag[0];
	    if (ent instanceof ThingEntry) {
		((ThingEntry) ent).ShowInstances();
	    }

	}

    }

    public static void updateColor(IXInstance newSelected) {
	if (lastIns != newSelected) {
	    if (lastIns != null) {
		lastIns.setBorderColor(lastColor);
		lastIns.update();
	    }
	    if (newSelected != null) {
		lastIns = newSelected;
		lastColor = lastIns.getBorderColor();
		newSelected.setBorderColor(Color.red);
		newSelected.update();
	    }
	}
	if ((Opcat2.getCurrentProject() != null)
		&& (Opcat2.getCurrentProject().getCurrentOpd() != null)
		&& (Opcat2.getCurrentProject().getCurrentOpd().getDrawingArea() != null))
	    Opcat2.getCurrentProject().getCurrentOpd().getDrawingArea()
		    .repaint();
    }

    public void rightClickEvent(MouseEvent e) {

	getRMenu().show(e.getComponent(), e.getX(), e.getY());

    }

    public JPopupMenu getRMenu() {

	RMenu = new JPopupMenu(" ");
	JMenuItem save = RMenu.add("Export to CSV");
	JMenuItem saveXML = RMenu.add("Export to XML");
	save.addActionListener(new RAction(this));
	saveXML.addActionListener(new RActionXML(this));
	RMenu.add(new JSeparator());

	return RMenu;
    }

    public void setRMenu(JPopupMenu menu) {
	RMenu = menu;
    }

    public void setTabName(String panelName) {
	this.tabName = panelName;
    }

    /**
         * Activate the Show instances of an Entry when dblClick on the grid for
         * this to work <code>tag[0] = ThingEntry</code>
         * 
         * @param entryTag
         */
    public void setEntryTag() {
	this.entryTag = true;
	this.instanceTag = false;
    }

    public boolean isOnExtensionToolsPane() {
	return onExtensionToolsPane;
    }

}

class RAction extends AbstractAction {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    GridPanel panel;

    public RAction(GridPanel panel) {
	this.panel = panel;
    }

    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub

	String fileChooserTitle = "Save File";

	JFileChooser myFileChooser = new JFileChooser();
	myFileChooser.setSelectedFile(new File(Opcat2.getCurrentProject()
		.getName()
		+ ".csv"));
	myFileChooser.resetChoosableFileFilters();
	String[] csv = { "csv", "txt" };
	CustomFileFilter myFilter = new CustomFileFilter(csv, "MetaData File");
	myFileChooser.addChoosableFileFilter(myFilter);

	String ld = FileControl.getInstance().getLastDirectory();
	if (!ld.equals("")) {
	    myFileChooser.setCurrentDirectory(new File(ld));
	}
	int retVal = myFileChooser.showDialog(panel, fileChooserTitle);

	if (retVal != JFileChooser.APPROVE_OPTION) {
	    return;
	}
	File file = myFileChooser.getSelectedFile();
	try {
	    int ret = JOptionPane.OK_OPTION;
	    if (!file.exists()) {
		file.createNewFile();
	    } else {
		ret = JOptionPane.showOptionDialog(panel,
			"File Exists, Save ?", "OPCAT Warning",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.ERROR_MESSAGE, null, null, null);
	    }
	    if (ret == JOptionPane.OK_OPTION) {
		OutputStream outStream = new FileOutputStream(file);
		DelimitedExportManager exporter = new DelimitedExportManager(
			",");
		exporter.setWriteHeader(true);
		exporter.write(panel.getGrid().getModel(), outStream);
		// FileWriter out = new FileWriter(file) ;
		// TableModel model = panel.getGrid().getModel() ;
		// for (int i = 0 ; i < model.getColumnCount(); i++) {
		// out.write(model.getColumnName(i)) ;
		// if(i != model.getColumnCount() -1 ) out.write(",") ;
		// }
		// out.write("\n") ;
		//				
		// for(int i = 0 ; i < model.getRowCount(); i++) {
		// for (int j = 0 ; j < model.getColumnCount(); j++) {
		// out.write( "\"" + ((String) model.getValueAt(i,
		// j)).replaceAll("\n", " ") + "\"") ;
		// if(j != model.getColumnCount() -1 ) out.write(",") ;
		// }
		// out.write("\n") ;
		// }
		// out.close() ;

	    }
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

    }

}

class RActionXML extends AbstractAction {

    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    GridPanel panel;

    public RActionXML(GridPanel panel) {
	this.panel = panel;
    }

    public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	XMLExportManager XMLExporter = new XMLExportManager("..");
	String fileChooserTitle = "Save File";

	JFileChooser myFileChooser = new JFileChooser();
	myFileChooser.setSelectedFile(new File(Opcat2.getCurrentProject()
		.getName()
		+ ".xml"));
	myFileChooser.resetChoosableFileFilters();
	String[] csv = { "xml" };
	CustomFileFilter myFilter = new CustomFileFilter(csv, "MetaData File");
	myFileChooser.addChoosableFileFilter(myFilter);

	String ld = FileControl.getInstance().getLastDirectory();
	if (!ld.equals("")) {
	    myFileChooser.setCurrentDirectory(new File(ld));
	}
	int retVal = myFileChooser.showDialog(panel, fileChooserTitle);

	if (retVal != JFileChooser.APPROVE_OPTION) {
	    return;
	}
	File file = myFileChooser.getSelectedFile();
	try {
	    int ret = JOptionPane.OK_OPTION;
	    if (!file.exists()) {
		file.createNewFile();
	    } else {
		ret = JOptionPane.showOptionDialog(panel,
			"File Exists, Save ?", "OPCAT Warning",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.ERROR_MESSAGE, null, null, null);
	    }
	    if (ret == JOptionPane.OK_OPTION) {
		OutputStream os = new FileOutputStream(file);
		XMLExporter.write(panel.getGrid().getModel(), os);
	    }
	} catch (Exception ex) {
	    OpcatLogger.logError(ex);
	}

    }
}

class MouseListner extends MouseAdapter {
    private GridPanel panel;

    public MouseListner(GridPanel Panel) {
	this.panel = Panel;
    }

    public void mouseClicked(MouseEvent e) {
	if (e.getClickCount() == 2) {
	    this.panel.dblClickEvent(e);
	    return;
	}

	if (e.getButton() == MouseEvent.BUTTON3) {
	    // if (this.panel.getGrid().getSelectedRow() != -1)
	    this.panel.rightClickEvent(e);
	    return;
	}
	super.mouseClicked(e);
    }
}

class GridFocusListner implements FocusListener {

    GridPanel panel;

    public GridFocusListner(GridPanel panel) {
	this.panel = panel;
    }

    public void focusGained(FocusEvent e) {
	// TODO Auto-generated method stub
	GridPanel.updateColor(null);
    }

    public void focusLost(FocusEvent e) {
	// TODO Auto-generated method stub
    }

}