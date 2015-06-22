package extensionTools.search;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXSystem;
import gui.util.opcatGrid.GridPanel;


/** 
 * the Search Extension tools Grid Panel. 
 * this panel is shown in the extension tools pane. inherits from the 
 * OpcatGrid Class, it manages the dblClick action
 * @author raanan
 *
 */
public class SearchGridPanel extends GridPanel  {
	public static String PanelName = "";  
	private IXSystem mySys ; 
	private IXInstance lastIns = null ;
	private Color lastColor = Color.black  ;  	 
	
	
	public SearchGridPanel(int ColumnsNumber , Vector ColumnsNames, IXSystem opcatSystem) {
		super(ColumnsNumber, ColumnsNames) ;
	    this.getGrid().addMouseListener(new MouseListner(this));
	    mySys = opcatSystem; 
	    this.GetGridData().setDuplicateRows(true);	    
	}
	
	public void AddRow(Object[] row, Object[] rowTag ) {
		this.GetGridData().addRow(row, rowTag);  
	}
	
	public  void RemoveFromExtensionToolsPanel(String tabName) {
		super.RemoveFromExtensionToolsPanel(tabName) ; 
		RestoreThingOrigColor() ; 
	}
	
	public void RestoreThingOrigColor() {
		if (lastIns != null )lastIns.setBorderColor(lastColor) ; 
	}
	
	public void dblClickEvent() {
  	  Object[] tag = new Object[2] ; 
	  IXInstance ins ; 
	  tag =  this.GetGridData().GetTag(this.getGrid().getSelectedRow())  ;
	  OpdKey key = (OpdKey)  tag[0] ;
	  Long entityID =(Long)tag[1] ;		    	  
	  ins = mySys.getIXSystemStructure().getIXEntry(entityID.longValue()).getIXInstance(key) ;
	  
	  if ((lastIns != null) && (lastIns != ins )) {
		  lastIns.setBorderColor(lastColor) ; 
		  lastIns.update() ; 
	  }
	  if (lastIns != ins ) {
		  lastIns = ins ;
		  lastColor = lastIns.getBorderColor() ; 
		  ins.setBorderColor(Color.red);
		  ins.update(); 
	  } 
	  mySys.showOPD(key.getOpdId()) ;		
		
	}
	
	
	public class MouseListner extends MouseAdapter {
		private SearchGridPanel panel  ;  
		public MouseListner(SearchGridPanel Panel) {
			panel = Panel ; 
		}
	     public void mouseClicked(MouseEvent e){
		      if (e.getClickCount() == 2){ 
		    	  panel.dblClickEvent() ; 
		       }
		   }
	}
}
