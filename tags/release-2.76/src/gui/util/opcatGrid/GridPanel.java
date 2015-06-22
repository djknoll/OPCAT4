package gui.util.opcatGrid;

import gui.controls.GuiControl;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.table.TableColumnModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Vector;

public class GridPanel extends JPanel{

	private Grid myGrid ; 
	private GridData myGridData ; 
	private String panelName = "" ; 
	
	public GridPanel(int ColumnsNumber , Vector ColumnsNames) {
		super();
		
		//we make colsNumber + 1 cols in order to keep tag data in the last col.
        myGridData = new GridData(ColumnsNumber, ColumnsNames) ; 
        myGrid = new Grid(myGridData); 
    
		JScrollPane scrollPane = new JScrollPane(myGrid);
		myGrid.setPreferredScrollableViewportSize(new Dimension(500, 70));
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);			
		//this.add(myGrid);   
		
		JButton clearButton = new JButton();
		clearButton.setMinimumSize(new Dimension(71, 20));
		clearButton.setText("Clear");
		
		JButton closeButton = new JButton();
		closeButton.setMinimumSize(new Dimension(71, 20));
		closeButton.setText("Close");		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		buttonPane.add(clearButton);
		buttonPane.add(closeButton);
		
		this.add(buttonPane, BorderLayout.LINE_END);
		clearButton.addActionListener(new ClearButton(myGridData));
		closeButton.addActionListener(new CloseButton(this));		
		
		
	}
	
	public GridData GetGridData() {
		return myGridData ; 
	}
	
	//public Grid GetGrid() {
	//	return myGrid ; 
	//}	
	
	public void ClearData() {
		myGridData.ClearData(); 
	}
	
	public TableColumnModel GetColumnsModel() {
		return myGrid.getColumnModel() ; 
	}
	class ClearButton implements java.awt.event.ActionListener {
		private GridData myGridData ; 
		public ClearButton(GridData gridData ) {
			myGridData = gridData ; 
		}
		public void actionPerformed(ActionEvent e) {
			myGridData.ClearData(); 
		}
	}	
	
	class CloseButton implements java.awt.event.ActionListener { 
		private GridPanel panel ; 
		public CloseButton( GridPanel pane ) {
			panel = pane ; 
		}
		public void actionPerformed(ActionEvent e) {
			panel.ClearData(); 
			panel.RemoveFromExtensionToolsPanel(panel.getPanelName()); 
		}
	}		
	public  void RemoveFromExtensionToolsPanel(String tabName) {
		
		GuiControl gui = GuiControl.getInstance() ; 
		JTabbedPane tabbedPane = gui.getExtensionToolsPane() ; 
		
		int indexOfValidation = tabbedPane.indexOfTab(tabName);
		if (indexOfValidation > 0) {
		    try {
		    	tabbedPane.remove(indexOfValidation);
		    } catch (Exception ex) {
		    }
		}		
	}
	
	public void AddToExtensionToolsPanel(String tabName) {
		GuiControl gui = GuiControl.getInstance() ; 
		JTabbedPane tabbedPane = gui.getExtensionToolsPane() ; 
		
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {

            if (tabbedPane.getTitleAt(i).equals(tabName)) {
            	tabbedPane.setSelectedIndex(i);
                return;
            }
            
        }
        
        if (this != null) {           
        	panelName = tabName ; 
        	tabbedPane.add(tabName, this);
        	tabbedPane.setSelectedComponent(this);
        }        
	}
	
	public int GetSelectedRowNumber() {
		return myGrid.getSelectedRow() ;  
	}
	
	protected Grid getGrid() {
		return myGrid ;
	}


	
	/**
	 * @return Returns the panelName.
	 */
	public String getPanelName() {
		return panelName;
	}
	
}


