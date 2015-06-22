package gui.util.opcatGrid;


import gui.Opcat2;

import java.awt.Component;
import java.awt.FontMetrics;
import javax.swing.JTable;
import javax.swing.table.*;


//public class Grid extends JTable{
//	
//	
//	public Grid (GridData data) {	  
//		this.setModel(data) ;
//	}	 
//
//}

public class Grid extends JTable{
	int colSelect;
	boolean select;

	//default constructor
	public Grid(){
           super();
           showHorScroll(true);
           }

	public Grid (GridData data) {
		super() ; 
		super.setModel(data) ; 
        showHorScroll(true);		
	}

	//constructor to create a table with given number of ros and columns
        public Grid(int row, int col){
            super(row, col);
            showHorScroll(true);
            }


	 /**this method shows the horizontal scroll bar when required.
	   *make sure it is called for other methods like setHeaderWidth etc to work properly
       * Its being called in the two constructors provided here
       */
        public void showHorScroll(boolean show){
          if (show){
                 setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                 }
            else{
                 setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
			 }
        }


     /**this method should be called to set the column
	   *at pColumn index to a width of pWidth
	   */
		public void setColumnWidth(int pColumn, int pWidth){
		//get the column model
		TableColumnModel colModel = getColumnModel();
		//get the column at index pColumn, and set its preferred width
		colModel.getColumn(pColumn).setPreferredWidth(pWidth);
		}

	 /**this method would set pColumn resizable or not based on the flag: pResizable
		*/
		public void setResizable(int pColumn, boolean pIsResize){
		//get the column model
		TableColumnModel colModel = getColumnModel();
		//set resizable or not
		colModel.getColumn(pColumn).setResizable(pIsResize);
		}


	 /**sets the column at index col to wither be selected or deselected -based on the
      * value of select
     */
        public void setSelect(int col, boolean select){
	            colSelect = col;
	            this.select = select;
	     }


	  /**This method returns whether a particular cell is selected or not
	  */
	  public boolean isCellSelected(int row, int column) throws IllegalArgumentException{
	     //over ride the method for the column set in setSelect()
	     if (colSelect == column){
	        if (select) return true;
	       	  else return false;
	        }
	        else{
	           return super.isCellSelected(row, column);
	            }
	        }


		/**sets the header and column size as per the Header text
		*/
		public void setHeaderSize(int pColumn){
			//get the column name of the given column
			String value =  getColumnName(pColumn);
			//calculate the width required for the column
			FontMetrics metrics = Opcat2.getOplPane().getGraphics().getFontMetrics();
			int width = metrics.stringWidth(value) + (2*getColumnModel().getColumnMargin());
			//set the width
			setColumnWidth(pColumn, width);
		}
		
		//Sets the preferred width of the visible column specified by vColIndex. The column
	    // will be just wide enough to show the column head and the widest cell in the column.
	    // margin pixels are added to the left and right
	    // (resulting in an additional width of 2*margin pixels).
	    public void packColumn(int vColIndex) {
	    	
	    	int margin = getColumnModel().getColumnMargin() ; 
	        
	        DefaultTableColumnModel colModel = (DefaultTableColumnModel)getColumnModel();
	        TableColumn col = colModel.getColumn(vColIndex);
	        int width = 0;
	    
	        // Get width of column header
	        TableCellRenderer renderer = col.getHeaderRenderer();
	        if (renderer == null) {
	            renderer = getTableHeader().getDefaultRenderer();
	        }
	        Component comp = renderer.getTableCellRendererComponent(
	            this, col.getHeaderValue(), false, false, 0, 0);
	        width = comp.getPreferredSize().width;
	    
	        // Get maximum width of column data
	        for (int r=0; r<getRowCount(); r++) {
	            renderer = getCellRenderer(r, vColIndex);
	            comp = renderer.getTableCellRendererComponent(
	                this, this.getValueAt(r, vColIndex), false, false, r, vColIndex);
	            width = Math.max(width, comp.getPreferredSize().width);
	        }
	    
	        // Add margin
	        width += 2*margin;
	    
	        // Set the width
	        col.setPreferredWidth(width);
	    }		

    }// end of MyTable class

