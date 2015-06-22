package gui.util.opcatGrid;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Vector; 

public class GridData extends AbstractTableModel {

	private int columnCount = 0 ; 
	private Vector columnNames  ;
	private Vector data ; 
	private Vector filteredData ; 
	private int filterColumn = -1 ;
	private Object filterValue = null ; 
	private boolean filterUpToDate = true ; 
	private Vector tags; 
	private HashMap tagsHash = new HashMap() ; 
	private boolean duplicateRows = true ; 
	private boolean[] keyFields ; 
	
	
	
	public Vector GetColumnsNames() {
		return columnNames ; 
	}
	
	public GridData (int ColumnCount, Vector ColumnNames) {
		super();
		columnCount = ColumnCount ; 
		data = new Vector();
		filteredData = new Vector();		
		tags = new Vector(); 
		keyFields = new boolean[columnCount] ; 
		for (int i = 0 ; i < keyFields.length; i++) {
			keyFields[i] = true ; 
		}
		columnNames  = ColumnNames ; 
	}
	private String GetKeyFromRowandTag(Object[] row, Object[] rowTag){
		String str = "" ;  
		for(int i = 0 ; i < rowTag.length; i++) {
			str = str + rowTag[i].toString() ; 
		}	
		for(int i = 0 ; i < getColumnCount() ; i++) {
			if (row[i] != null ) {
				if(keyFields[i]) str = str + row[i].toString() ;
			}
		}		
		return str ; 
	}
	
	private void AddRowandTagstoHash (Object[] row , Object[] tags) {
		String str = GetKeyFromRowandTag(row, tags); 
		tagsHash.put(str, str); 
	}
	
	public  int addRow(Object[] row, Object[] rowTag) 
	{	
		if (isDuplicateRows()  || !tagsHash.containsKey(GetKeyFromRowandTag(row, rowTag)) ) {
			data.add(row);
			filteredData.add(row) ; 
			tags.add(rowTag); 
			AddRowandTagstoHash(row, rowTag) ;    
		} 	    
		return getRowCount() ; 
	}
	
	public void Update(int startRow , int endRow) {
		fireTableRowsInserted(startRow - 1 , endRow -1 ) ;		
	}
	
	public Object[] GetTag(int rowNumber) {
		return (Object []) tags.elementAt(rowNumber); 
	}
	
	  public int getRowCount() {
		  if (!(filteredData == null)) {
			  return filteredData.size();
		  } else {
			  return 0 ; 
		  }
	  }
	  
	    public String getColumnName(int col) {
	        return columnNames.elementAt(col).toString();
	    }	  
	  
	  public int getColumnCount() {
		  return columnCount ;
	  }
	  
	  public Object getValueAt( int row, int column ) {
		  if (!filterUpToDate ) updateFilter() ; 
		  if (row < filteredData.size() && column < columnCount) 
		  {
			  Object [] oRow = (Object []) filteredData.elementAt(row);
			  return oRow[column];
		  } 
		  else 
		  {
			  return null;
		  }	    
	  }

//	public void _setValueAt(Object aValue, int rowIndex, int columnIndex) 
//	{
//		data.setElementAt(aValue, rowIndex) ;
//		fireTableCellUpdated(rowIndex, columnIndex);
//	}	
//	
	public void ClearData() {
		data = new Vector();
		filteredData = new Vector() ; 
		tags = new Vector(); 
		tagsHash.clear() ; 
		Update(0, getRowCount()) ; 
	}

	/**
	 * @return Returns the duplicateRows.
	 */
	public boolean isDuplicateRows() {
		return duplicateRows;
	}

	/**
	 * @param duplicateRows The duplicateRows to set.
	 */
	public void setDuplicateRows(boolean duplicateRows) {
		this.duplicateRows = duplicateRows;
	}
	
	public void setKeyFieldsOff(int field) {
		keyFields[field] = false ; 
	}

	/**
	 * @param filterColumn The filterColumn to set.
	 */
	public void setFilterColumn(int filterColumn) {
		this.filterColumn = filterColumn;
	}

	/**
	 * @return Returns the filterValue.
	 */
	public Object getFilterValue() {
		return filterValue;
	}

	/**
	 * @param filterValue The filterValue to set.
	 */
	public void setFilterValue(Object filterValue) {
		this.filterValue = filterValue;
	}
	
	private void updateFilter() {
		
	}
}
