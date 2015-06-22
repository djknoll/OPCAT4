package gui.util.opcatGrid;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JTable;

import com.sciapp.filter.CustomPopupFilterHeaderModel;
import com.sciapp.filter.FilterHeaderModel;
import com.sciapp.filter.FilterTableHeader;
import com.sciapp.table.FilterTableModel;
import com.sciapp.table.ListTableModel;
import com.sciapp.table.SortTableModel;
import com.sciapp.table.span.CellSpan;
import com.sciapp.table.span.DefaultSpanModel;
import com.sciapp.table.span.SpanDrawer;
import com.sciapp.table.styles.Style;
import com.sciapp.tree.*;

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

	private ListTableModel myListModel;

	private SortTableModel mySortedModel;

	private FilterTableModel myFilterModel;

	private TreeTableModel myTreeModel;

	private GridData myGridData;

	// constructor to create a table with given number of ros and columns
	public Grid(int ColumnsNumber, Vector ColumnsNames) {
		// we make colsNumber + 1 cols in order to keep tag data in the last
		// col.
		this.myGridData = new GridData(ColumnsNumber, ColumnsNames);
		this.myListModel = this.myGridData;
		this.myFilterModel = new FilterTableModel(this.myListModel);
		this.mySortedModel = new SortTableModel(this.myFilterModel);
		this.myTreeModel = new TreeTableModel(this.mySortedModel);

		this.setModel(this.myTreeModel);

		this.mySortedModel.setHeader(this.getTableHeader());

		FilterHeaderModel fhm = new CustomPopupFilterHeaderModel();
		fhm.setTableHeader((FilterTableHeader) this.getTableHeader());
		// attach FilterHeaderModel to the table
		fhm.attachToTable(this);

		Color c = new Color(220, 220, 220);
		this.setOddColor(c);
		this.setShowDummyColumn(false);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		SpanDrawer drawer = this.getSpanDrawer();
		DefaultSpanModel dsm = (DefaultSpanModel) drawer.getSpanModel();
		CellSpan cellSpan = new CellSpan(0, 0, 0, CellSpan.ALL_COLUMNS);
		dsm.addCellSpan(cellSpan);

		Style s = new AggregateStyle(this);
		this.getStyleModel().addStyle(s);
		
		getTableAssistant().setShowMore(true) ; 
		setAutoResizeMode(AUTO_RESIZE_ALL_COLUMNS); 

	}

	public Object[] GetTag(int rowNumber) {

		// table is the TreeTable model
		int origIndex = -1;

		// int[] selectedRows = this.getSelectedRows();
		//		
		// for (int i = 0; i < selectedRows.length; i++) {
		//			
		// origIndex = myTreeModel.getDataRow(selectedRows[i]);
		// //.getDataRow(selectedRows[i]);
		// }
		//
		origIndex =  getFilterModel().getFilteredIndexes()[this.mySortedModel.getSortedIndexes()[rowNumber]];
		// origIndex = myTreeModel.getDataRow(rowNumber);
		return this.myGridData.GetTag(origIndex);
	}

	public int addRow(Object[] row, Object[] rowTag) {
		int insertedRow = this.myGridData.addRow(row, rowTag);
		this.Update(0, insertedRow);
		return insertedRow;
	}

	public void setDuplicateRows(boolean duplicateRows) {
		this.myGridData.setDuplicateRows(duplicateRows);
	}

	protected void ClearData() {
		this.myGridData.ClearData();
	}

	public void Update(int from, int to) {
		this.myGridData.Update(from, to);
	}

	public int getColumnCount() {
		return this.myGridData.getColumnCount();
	}

	public ListTableModel getListModel() {
		return this.myListModel;
	}

	public void setListModel(ListTableModel myListModel) {
		this.myListModel = myListModel;
	}

	public SortTableModel getSortedModel() {
		return this.mySortedModel;
	}

	public void setSortedModel(SortTableModel mySortedModel) {
		this.mySortedModel = mySortedModel;
	}

	public TreeTableModel getTreeModel() {
		return this.myTreeModel;
	}

	public void setTreeModel(TreeTableModel myTreeModel) {
		this.myTreeModel = myTreeModel;
	}

	public FilterTableModel getFilterModel() {
		return myFilterModel;
	}

}// end of MyTable class

