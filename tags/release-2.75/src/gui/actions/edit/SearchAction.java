package gui.actions.edit;

import gui.Search.SearchDialog;

import java.awt.event.ActionEvent;
import javax.swing.Icon;

/**
 * Open the search dialog 
 * @author Raanan Manor
 */
public class SearchAction extends EditAction {
	
	private SearchDialog diag ; 

   public SearchAction(String name, Icon icon) {
        super(name, icon);
    }
   
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0); 
        	
        if (edit.isProjectOpened()) {
        	diag = new SearchDialog(edit.getCurrentProject()); 
        	diag.ShowDialog(); 
        }

    }
}

