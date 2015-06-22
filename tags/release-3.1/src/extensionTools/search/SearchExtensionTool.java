package extensionTools.search;

import javax.swing.JDialog;
import javax.swing.JPanel;
import exportedAPI.OpcatExtensionToolX;
import exportedAPI.opcatAPIx.IXSystem;


public class SearchExtensionTool implements OpcatExtensionToolX {
	static JDialog searchDialog = null ; 
	
	public JPanel execute(IXSystem opcatSystem) {
		searchDialog = SearchDialog.getInstance(opcatSystem) ; 
		searchDialog.setVisible(true) ; 
		return null ;
	}

	public JPanel getAboutBox() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHelpURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return "Search";
	}
    
	
}
