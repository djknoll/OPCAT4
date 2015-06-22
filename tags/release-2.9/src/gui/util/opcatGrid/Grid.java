package gui.util.opcatGrid;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTree;
import com.sciapp.filter.CustomPopupFilterHeaderModel;
import com.sciapp.filter.FilterHeaderModel;
import com.sciapp.filter.FilterTableHeader;
import com.sciapp.table.FilterTableModel;
import com.sciapp.table.SortTableModel;
import com.sciapp.table.styles.Style;
import com.sciapp.treetable.*;;

//public class Grid extends JTable{
//	
//	
//	public Grid (GridData data) {	  
//		this.setModel(data) ;
//	}	 
//
//}

public class Grid extends TreeTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	int colSelect;

	boolean select;

	//private ListTableModel myListModel;

	private SortTableModel mySortedModel;

	private FilterTableModel myFilterModel;

	private DynamicTreeTableModel myTreeModel;
	
	private TreeTableModelAdapter myTreeTableAdpter ;
	
	FilterHeaderModel filterHeaderModel  ;	
	

	private GridData myGridData;

	// constructor to create a table with given number of ros and columns
	public Grid(ArrayList columnsNames) {
		// we make colsNumber + 1 cols in order to keep tag data in the last
		// col.
		this.myGridData = new GridData(columnsNames);

		this.myFilterModel = new FilterTableModel(myGridData);
		this.mySortedModel = new SortTableModel(this.myFilterModel);
		this.myTreeModel = new DynamicTreeTableModel(this.mySortedModel);
		this.myTreeTableAdpter = new TreeTableModelAdapter(this.myTreeModel, new JTree()) ; 
		this.setModel(myTreeTableAdpter);

		this.mySortedModel.setHeader(this.getTableHeader());

		filterHeaderModel  = new CustomPopupFilterHeaderModel();
		filterHeaderModel.setTableHeader((FilterTableHeader)  this.getTableHeader());
		filterHeaderModel.attachToTable(this, myFilterModel);

		Color c = new Color(220, 220, 220);
		this.setOddColor(c);
		this.setShowDummyColumn(false);

		//SpanDrawer drawer = this.getSpanDrawer();
		//DefaultSpanModel dsm = (DefaultSpanModel) drawer.getSpanModel();
		//CellSpan cellSpan = new CellSpan(0, 0, 0, CellSpan.ALL_COLUMNS);
		//dsm.addCellSpan(cellSpan);

		Style s = new AggregateStyle(this);
		this.getStyleModel().addStyle(s);

		getTableAssistant().setShowMore(true);
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS);

	}

	public Object[] GetTag(int rowNumber) {

		// table is the TreeTable model
		int origIndex = -1;

		TreeTableRow node = (TreeTableRow)  myTreeTableAdpter.nodeForRow(rowNumber) ;
		
		if (!node.isLeaf() && !node.isFooter()) {
			node = (TreeTableRow) node.getChildAt(0) ;			
		}
		
		if (node.isFooter()) {
			node = (TreeTableRow) node.getParent().getChildAt(0) ;			
		}		

		origIndex =  node.getModelIndex() ;
		origIndex = getFilterModel().getFilteredIndexes()[this.mySortedModel
		                                  				.getSortedIndexes()[origIndex]];
		return this.myGridData.GetTag(origIndex);
	}
	
	

	public int getCurrentRealRowIndex() {
		int rowNumber = this.getSelectedRow() ; 
		
		return getFilterModel().getFilteredIndexes()[this.mySortedModel
				.getSortedIndexes()[rowNumber]];
	}

	public int addRow(Object[] row, Object[] rowTag) {
		int insertedRow = this.myGridData.addRow(row, rowTag);
		myGridData.fireTableRowsInserted(insertedRow, insertedRow) ; 
		return insertedRow;
	}

	public void setDuplicateRows(boolean duplicateRows) {
		this.myGridData.setDuplicateRows(duplicateRows);
	}

	protected void ClearData() {
		this.myGridData.clear() ;  
		this.repaint() ; 
	}

//	public int getColumnCount() {
//		return this.myGridData.getColumnCount();
//	}

	public SortTableModel getSortedModel() {
		return this.mySortedModel;
	}

	public void setSortedModel(SortTableModel mySortedModel) {
		this.mySortedModel = mySortedModel;
	}

	public DynamicTreeTableModel getTreeModel() {
		return this.myTreeModel;
	}

	public void setTreeModel(DynamicTreeTableModel myTreeModel) {
		this.myTreeModel = myTreeModel;
	}

	public FilterTableModel getFilterModel() {
		return myFilterModel;
	}

	public void AddDefaultAggragator(boolean footer, boolean header) {
		myTreeModel.setFooter(new MyFooter());
		myTreeModel.setDefaultAggregator(new FooterCounter(myTreeModel, footer , header ));

	}

	public FilterHeaderModel getFilterHeaderModel() {
		return filterHeaderModel;
	}

	public GridData getMyGridData() {
		return myGridData;
	}

}// end of MyTable class

class MyFooter implements Footer {
	public int getFooterSize(TreeTableRow row) {
		if (row.getLevel() == 0)
			return 0;
		return 1;
	}
}

// create the aggregator
class FooterCounter extends DefaultCellAggregator {

	public Object getAggregateValue(AggregateRow row, int colIndex) {

		TreeTableRow parent = (TreeTableRow) row.getParent();
		if (doFooter && colIndex == 0 &&  row.isFooter()) {
			int[] totalRows = model.getModelIndexesUnderRow(parent, false);
			int sum = totalRows.length;
			return "Total Cases - " + new Integer(sum);
		}	
		
		if (doHeader && row.isHeader() && colIndex != 0 ) {
			TreeTableRow child ; 
			if(model.getChildCount(row ) > 0 ) {
				child = (TreeTableRow) model.getChild(row, 0) ;
			} else {
				child = row ; 
			}
			Object val = (Object) ((Object[]) child.getUserObject())[colIndex] ; 
			return val;
		}				
		
		return super.getAggregateValue(row, colIndex);
	}

	DynamicTreeTableModel dtt;
	boolean doFooter = false ; 
	boolean doHeader = false ; 

	public FooterCounter(DynamicTreeTableModel model, boolean footer , boolean header) {
		super(model);
		dtt = model;
		doFooter = footer ; 
		doHeader = header ; 
	}
}
