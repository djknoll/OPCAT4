package extensionTools.search;

import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.AbstractAction;
import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;

/** 
 * This class is the Action of the search Button in the {@link  SearchDialog } 
 * It gets an OptionBase from the dialog and creates a Search algorithem by using the AlgorithemFactory. 
 * this search is then used to search the project. 
 * 
 * The search output is then added into the grid. a row for each instance. 
 * The Instance Key is added as a tag to the grid. this tag is latter used in {@link SearchAction} 
 * to move the focus to the selected line. 
 * 
 * singleton
 * 
 * @author raanan
 *
 */
public class SearchAction extends AbstractAction{
	 
	SearchGridPanel searchPanel = null ; 
	IXSystem opcat = null ; 
	
	public SearchAction(IXSystem system) {
		opcat = system ; 
		searchPanel =  InitSearchGridPanel() ;
	}
	
    public void actionPerformed(ActionEvent e) {    	
    	//Search here
    	Vector output  = new Vector();         	
    	/**
    	 * TODO: Add a options factory so i could use the 
    	 * algo factory without the dialog, 
    	 * it's input should be OptionBase class
    	 */
    	AlgoFactory factory = new AlgoFactory(SearchDialog.OptionsFactory()) ; 
    	AlgoInterface search = (AlgoInterface) factory.create(); 
    	output = search.PreformSearch(opcat) ;        
    	
    	
    	Enumeration entEnum = output.elements() ; 
		searchPanel.AddToExtensionToolsPanel("Search") ;
    	while (entEnum.hasMoreElements()) {
    		IXEntry ent = (IXEntry) entEnum.nextElement() ; 
    		SearchGridAddRow(ent) ; 
    	}    	
        
    }

	  
	  private SearchGridPanel InitSearchGridPanel () {
			Vector cols = new Vector() ; 
			cols.add("Thing Type"); 
			cols.add("OPD") ; 
			cols.add("Thing Name"); 
			SearchGridPanel locPanel ; 
			locPanel =  new SearchGridPanel(cols.size(), cols, opcat);
			locPanel.GetColumnsModel().getColumn(0).sizeWidthToFit(); 
			locPanel.GetColumnsModel().getColumn(1).sizeWidthToFit(); 
			locPanel.GetColumnsModel().getColumn(2).sizeWidthToFit();
			//locPanel.GetColumnsModel().getColumn(5).sizeWidthToFit(); 
			locPanel.doLayout(); 
			return locPanel ; 
	}	
	
  
  private void SearchGridAddRow(IXEntry entry) {
  	
	  Enumeration insEnum =  entry.getInstances() ;
	  for (;insEnum.hasMoreElements();) {		  
	  		IXInstance ins = null ; 
	  		OpdKey  key = null  ;	
	  		Object[] tag = new Object[2];
	  		Object[] row = new Object[searchPanel.GetGridData().getColumnCount()];
	  		
		  	ins= (IXInstance) insEnum.nextElement() ; 
		  	key = ins.getKey();
			String type = "None" ; 					
			if (entry instanceof IXProcessEntry) type = "Process" ;
			if (entry instanceof IXObjectEntry) type = "Object" ;
			if (entry instanceof IXStateEntry) type = "State" ;  				
		  	row[0] = type;	
		  	row[1] = opcat.getIXSystemStructure().getIXOpd(key.getOpdId()).getName(); 
		  	row[2] = entry.getName(); 		  	
		  	//System.out.println(key.toShtring()) ;
		  	tag[0] = key ; 
		  	tag[1] = new Long(entry.getId()) ;  		  	
		  	int numOfInsertedRow = searchPanel.GetGridData().addRow(row, tag) ;
		  	searchPanel.GetGridData().Update(numOfInsertedRow,numOfInsertedRow) ; 
	  }
}
}
