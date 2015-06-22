package gui.util.opcatGrid;

import javax.swing.table.AbstractTableModel;
import com.sciapp.table.ListTableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class GridData extends AbstractTableModel implements ListTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	
	public static final int UPDATE_TYPE_DELETE = 1 ; 
	public static final int UPDATE_TYPE_UPDATE = 1 ;
	public static final int UPDATE_TYPE_INSERT = 1 ;

	private int columnCount = 0;

	private Vector columnNames;

	private ArrayList gridData; 

	private ArrayList tags;

	private HashMap tagsHash = new HashMap();

	private boolean duplicateRows = true;

	private boolean[] keyFields;

	public Vector GetColumnsNames() {
		return this.columnNames;
	}

	public GridData(int ColumnCount, Vector ColumnNames) {
		super();
		this.columnCount = ColumnCount;
		this.gridData = new ArrayList();
		this.tags = new ArrayList();
		this.keyFields = new boolean[this.columnCount];
		for (int i = 0; i < this.keyFields.length; i++) {
			this.keyFields[i] = true;
		}
		this.columnNames = ColumnNames;
	}

	private String GetKeyFromRowandTag(Object[] row, Object[] rowTag) {
		if (rowTag == null)
			return "no tag";
		String str = "";
		for (int i = 0; i < rowTag.length; i++) {
			str = str + rowTag[i].toString();
		}
		for (int i = 0; i < this.getColumnCount(); i++) {
			if (row[i] != null) {
				if (this.keyFields[i]) {
					str = str + row[i].toString();
				}
			}
		}
		return str;
	}

	private void AddRowandTagstoHash(Object[] row, Object[] tags) {
		String str = this.GetKeyFromRowandTag(row, tags);
		this.tagsHash.put(str, str);
	}

	public int addRow(Object[] row, Object[] rowTag) {
		if (this.isDuplicateRows()
				|| !this.tagsHash.containsKey(this.GetKeyFromRowandTag(row,
						rowTag))) {
			this.gridData.add(row);
			this.tags.add(rowTag);
			this.AddRowandTagstoHash(row, rowTag);
		}
		return this.getRowCount();
	}

	protected Object[] GetTag(int rowNumber) {
		return (Object[]) this.tags.get(rowNumber);
	}

	public int getRowCount() {
		if (!(this.gridData == null)) {
			return this.gridData.size();
		} else {
			return 0;
		}
	}

	public String getColumnName(int col) {
		return this.columnNames.elementAt(col).toString();
	}

	public int getColumnCount() {
		return this.columnCount;
	}

	public Object getValueAt(int row, int column) {
		if ((row < this.gridData.size()) && (column < this.columnCount)) {
			Object[] oRow = (Object[]) this.gridData.get(row);
			return oRow[column];
		} else {
			return null;
		}
	}

	/**
	 * @return Returns the duplicateRows.
	 */
	public boolean isDuplicateRows() {
		return this.duplicateRows;
	}

	/**
	 * @param duplicateRows
	 *            The duplicateRows to set.
	 */
	public void setDuplicateRows(boolean duplicateRows) {
		this.duplicateRows = duplicateRows;
	}

	public void setKeyFieldsOff(int field) {
		this.keyFields[field] = false;
	}

	public void addRow(Object row) {
		// TODO Auto-generated method stub
		this.gridData.add(row);

	}

	public void addRows(List rows) {
		// TODO Auto-generated method stub
		gridData.addAll(rows) ; 

	}

	public void clear() {
		// TODO Auto-generated method stub
		int count = getRowCount() ; 
		this.tags.clear() ;
		this.tagsHash.clear();		
		this.gridData.clear();
		fireTableRowsDeleted(0, count) ;
	}

	public Object getCellValue(Object row, int index) {
		// TODO Auto-generated method stub
		return ((Object[]) row)[index];
	}

	public List getRows() {
		// TODO Auto-generated method stub
		return gridData;
	}

	public void removeRow(int index) {
		// TODO Auto-generated method stub
		this.gridData.remove(index);
	}

	public void removeRows(int[] indexes) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < indexes.length ; i++) {
			removeRow(i) ; 
		}
	}
}

