package extensionTools.search;

import java.util.Enumeration;
import java.util.Vector;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;

public class AlgoExectMatch implements AlgoInterface{
	private OptionsExectMatch options ; 
	
	public AlgoExectMatch (OptionsExectMatch searchOptions ) {
		this.options = searchOptions ; 
	}
	
	public Vector PreformSearch(IXSystem opcat) {
		Vector out = new Vector(); 
		boolean found = false ; 
		// TODO Auto-generated method stub
		//APILogger.SetDebug(true); 
		//APILogger.Print(this, options.searchText ); 
		Enumeration  entEnum = opcat.getIXSystemStructure().getElements();
		while(entEnum.hasMoreElements()) {
			IXEntry ent = (IXEntry) entEnum.nextElement() ; 
			found = false ; 
			
			if (ent.getName().equals(this.options.getSearchText())  && this.options.inName) {
				if ((ent instanceof IXProcessEntry) && this.options.forProcess) {
					found = true ; 
				}
				
				if ((ent instanceof IXStateEntry) && this.options.forStates) {
					found = true ;
				}
				
				if ((ent instanceof IXObjectEntry) && this.options.forObjects) {
					found = true ;
				}					
				
			}	
			
			if (found) {
				out.add(ent) ; 
			}
		}
		return out ; 
	}
	

}
