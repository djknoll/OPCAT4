package gui.procDependencies.gui;

import exportedAPI.opcatAPIx.IXEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Enumeration;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;


public class DependenciesProcessPropertiesTab extends JPanel{
	private static final long serialVersionUID = 1L;
	private JScrollPane panel;
	private JPanel p;
	private OpmThing theThing;
	private OpdProject myProject;
	private JTable dependTable;
	private ProcessDependenciesModel dependModel;
	private BorderLayout layout = new BorderLayout();
	private Object[] procNames;

	public DependenciesProcessPropertiesTab(OpmThing theThing, OpdProject project) {
		super();
		try {
			this.theThing = theThing;
			this.myProject = project;
			this.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void build(){
		p = new JPanel();
		int columnNumber =2;
		procNames = this.createProcessIndexes();
	    dependModel = new ProcessDependenciesModel(((OpmProcess)theThing).getPredecessors(),this.myProject, procNames);
	    this.setLayout(layout);
	    panel = new JScrollPane();
	    panel.setPreferredSize(new Dimension(185,270));
	    dependTable = new JTable();
	    dependTable.setAutoCreateColumnsFromModel(false);
	    dependTable.setModel(dependModel);
	    TableColumn[] column = new TableColumn[columnNumber];
	    TableCellEditor[] editor = new TableCellEditor[columnNumber];
	    DefaultTableCellRenderer[] textRenderer = new DefaultTableCellRenderer[columnNumber];
	    for(int k=0;k<columnNumber;k++){
	      textRenderer[k] = new DefaultTableCellRenderer();
	      textRenderer[k].setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
	      JComboBox b;
	      switch(k){
	      case ProcessDependenciesModel.PROCESSES_INDEX:
	    	b = new JComboBox(this.createProcessIndexes());
	    	editor[k] = new DefaultCellEditor(b);
	    	break;
	      case ProcessDependenciesModel.TYPE_INDEX:
	        b = new JComboBox(Predecessor.getTypes());
	        editor[k] = new DefaultCellEditor(b);
	        break;
	    }
	     column[k]= new TableColumn(k, 70, textRenderer[k], editor[k]);
	     dependTable.addColumn(column[k]);
	    }

	    JTableHeader header = dependTable.getTableHeader();
	    header.setUpdateTableInRealTime(true);
	    dependTable.getTableHeader().setReorderingAllowed(false);
	    dependTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    panel.getViewport().add(dependTable);
	    this.add(panel, BorderLayout.CENTER);

	    p.setLayout(new BoxLayout(p,BoxLayout.X_AXIS));
	    p.add(Box.createHorizontalGlue());
	    this.add(p,BorderLayout.SOUTH);
	    p.add(new javax.swing.JLabel(" "));

	    JButton bt = new JButton("Insert");
	    bt.setToolTipText("Insert row at the end of the list");
	    bt.setMnemonic('a');
	    bt.setAlignmentY(0.5f);
	    ActionListener lst = new ActionListener(){
	    public void actionPerformed( ActionEvent e){
	      int row = dependTable.getSelectedRow();
	       dependModel.insert(row+1);
	       dependTable.tableChanged(new TableModelEvent(dependModel, row+1, row+1, TableModelEvent.ALL_COLUMNS,
	           TableModelEvent.INSERT));
	       dependTable.clearSelection();
	       dependTable.repaint();
	     }
	    };
	    bt.addActionListener(lst);
	    p.add(bt);
	    p.add(new javax.swing.JLabel("  "));

	    bt = new JButton("Delete");
	    bt.setToolTipText(" Delete row ");
	    bt.setMnemonic('d');
	    bt.setAlignmentY(0.5f);
	    lst = new ActionListener(){
	   
	    	public void actionPerformed( ActionEvent e){
	    		int row = dependTable.getSelectedRow();
	    		if (dependTable.isEditing()) {
	    			dependTable.getCellEditor().cancelCellEditing();
	    		}
	    		if (dependModel.delete(row)) {
	    			dependTable.tableChanged(new TableModelEvent(dependModel, row,
	    					row, TableModelEvent.ALL_COLUMNS,
	    					TableModelEvent.INSERT));
	    			dependTable.clearSelection();
	    			dependTable.repaint();
	    		}
	     }
	    };

	
	    bt.addActionListener(lst);
	    p.add(bt);
	    p.setVisible(true);
	    dependTable.setVisible(true);
	    dependTable.setRowHeight(20);

	}

	private Object[] createProcessIndexes(){
	Enumeration en = myProject.getComponentsStructure().getElements();
	java.util.ArrayList<String>strs = new java.util.ArrayList<String>();
	IXEntry entry;
	while(en.hasMoreElements()){
		entry = (IXEntry)en.nextElement();
		strs.add(entry.getName() + " #"+entry.getId());
	}
	return strs.toArray();
	}
	
	public java.util.ArrayList<Predecessor> getPredecessors(){
		return this.dependModel.getDependencies();
	}
}
