package gui.opdGraphics.dialogs;

import gui.opdProject.OpdProject;
import gui.projectStructure.*;
import com.sciapp.table.*;
import com.sciapp.comparators.*;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*; 


public class InstancesPanel extends JPanel {
	ObjectEntry myEntry;
	OpdProject project;
	AdvancedJTable myTable;
	InstancesModel myModel;
	GridBagLayout gridBag1 = new GridBagLayout();
	GridBagLayout gridBag2 = new GridBagLayout();
	AdvancedJScrollPane tableScroll;
	JPanel buttonsPanel = new JPanel();
	JButton addInstanceButton = new JButton(" Add ");
	JButton removeInstanceButton = new JButton(" Remove ");
  	
	public InstancesPanel(ObjectEntry entry, OpdProject project){
		this.myEntry = entry;
		this.project = project;
		reloadModel();
		myTable = new AdvancedJTable(myModel);
		setTablePrefs();
		tableScroll = new AdvancedJScrollPane(myTable);
		
		initUI();
	}
	
	private void setTablePrefs(){
		myModel.setHeader(myTable.getTableHeader());
		myModel.setComparator(1, new BooleanComparator());
		myModel.setComparator(0, new EntryNameComparator());
		myTable.getColumnModel().getColumn(1).setCellRenderer(new VisiblityRenderer());
		JCheckBox box = new JCheckBox("   ");
		box.setHorizontalTextPosition(SwingConstants.LEFT);
		myTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(box));
		myTable.setRowHeight(20);
		myTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		myTable.getColumnModel().getColumn(1).setMaxWidth(80);
		myTable.setOddColor(new Color(220, 220, 220));
		myTable.setShowDummyColumn(false);
	}
	
	private void reloadModel(){
		DefaultListTableModel tempModel = new DefaultListTableModel(new String[]{" Instance Name", " isVisible"},0);
		Enumeration relations = myEntry.getRelationBySource();
		while (relations.hasMoreElements()){
			RelationEntry relEntry = (RelationEntry)relations.nextElement();
			if (relEntry.getRelationType() == OpcatConstants.INSTANTINATION_RELATION){
				ConnectionEdgeEntry destEntry = (ConnectionEdgeEntry)project.getSystemStructure().getEntry(relEntry.getDestinationId());
				tempModel.addRow(new Object[]{destEntry, destEntry.isInstancesVisible()});
			}
		}
		
		myModel = new InstancesModel(tempModel);
		myModel.addTableModelListener(new ModelListener());
		
		if (myTable != null){
			myTable.setModel(myModel);
			myModel.setHeader(myTable.getTableHeader());
			setTablePrefs();
		}
	}
	
	private void initUI(){
		setLayout(gridBag1);
		this.add(tableScroll, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.1
                               , GridBagConstraints.NORTHWEST,
                               GridBagConstraints.BOTH,
                               new Insets(10, 10, 10, 5), 0, 0));
		
		buttonsPanel.setLayout(gridBag2);
		buttonsPanel.add(addInstanceButton, new GridBagConstraints(0, 0, 1, 1, 0.1, 0.0
            , GridBagConstraints.NORTHEAST,
            GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
		buttonsPanel.add(removeInstanceButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            , GridBagConstraints.NORTHEAST,
	            GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));
		
    
		this.add(buttonsPanel,new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(10, 10, 10, 5), 0, 0));
    
		addInstanceButton.addActionListener(new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent arg0){
				addNewInstance();
			}
		});
		
		removeInstanceButton.addActionListener(new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent arg0){
				removeInstance();
			}
		});
		
	}
	
	private void addNewInstance(){
		project.addInstance4Object(myEntry, false);
		reloadModel();
	}
	
	private void removeInstance(){
		int[] selectedRows = myTable.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++){
			project.deleteEntry((ObjectEntry)myModel.getValueAt(selectedRows[i], 0));
		}
		reloadModel();
	}
	
	
	class InstancesModel extends SortTableModel{
		public InstancesModel(DefaultListTableModel model){
			super(model);
		}
		
		public boolean isCellEditable(int row, int column){
			if (column == 0){
				return false;
			}
			return true;
		}
	}
	class VisiblityRenderer implements	TableCellRenderer{
		 public java.awt.Component getTableCellRendererComponent(JTable table, 
				 Object value, boolean isSelected, boolean hasFocus, int row, int column){
				JCheckBox box = new JCheckBox("   ", (Boolean)value);
				box.setHorizontalTextPosition(SwingConstants.LEFT);
			 return box;
		 }
	}
	
	class ModelListener implements TableModelListener{
		public void tableChanged(TableModelEvent event){
			if (event.getType() ==  TableModelEvent.UPDATE){
				if (event.getColumn() == -1 || event.getFirstRow() == -1){
					return;
				}
				ObjectEntry entry = (ObjectEntry)myModel.getValueAt(event.getFirstRow(), 0);
				boolean isVisible = (Boolean)myModel.getValueAt(event.getFirstRow(), 1);
				entry.setInstancesVisible(isVisible);
			}
		}
	}
	
	class EntryNameComparator implements Comparator{
		StringComparator strCompartor = new StringComparator();
		
		public int compare(Object o1, Object o2) {
			if (!(o1 instanceof Entry) || !(o2 instanceof Entry)){
				return 0;
			}
			
			return strCompartor.compare(((Entry)o1).getName(), ((Entry)o2).getName());
		}
	}

}
