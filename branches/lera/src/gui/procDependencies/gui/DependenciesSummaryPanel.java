package gui.procDependencies.gui;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLabel;
import gui.opdProject.OpdProject;
import gui.opmEntities.*;


import com.sciapp.filter.CustomPopupFilterHeaderModel;
import com.sciapp.filter.FilterHeaderModel;
import com.sciapp.filter.FilterTableHeader;
import com.sciapp.table.FilterTableModel;
import com.sciapp.table.SortTableModel;
import com.sciapp.table.styles.Style;
import com.sciapp.treetable.*;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;

import gui.opdProject.OpdProject;
import gui.util.OpcatLogger;
import gui.util.opcatGrid.*;
import exportedAPI.opcatAPIx.*;

public class DependenciesSummaryPanel extends GridPanel{

	int numOfColumns = 3;
	IXSystem opcat;
	java.util.Vector columnNames = new java.util.Vector();
	
	public DependenciesSummaryPanel(int cols, Vector names, IXSystem opcat){
		super(cols, names);
		this.opcat = opcat;
		//TableColumnModel model = this.GetColumnsModel();
		this.setInstanceTag(opcat) ; 
		this.getGrid().setDuplicateRows(true);		
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
		this.getButtonPane().add(new JLabel(""));
		this.setTabName("Dependencies"); 
		this.doLayout(); 
		GridAddRows();
		this.AddToExtensionToolsPanel();
	}

	private void GridAddRows() {
		//Entry entry = (Entry) ent ; 
		java.util.ArrayList<IXProcessEntry>procs = opcat.getIXSystemStructure().getProcesses();
		for (int i=0; i<procs.size();i++) {
				IXProcessEntry entry = procs.get(i);
				IXProcessInstance ins = null;
				OpdKey key = null;
				Object[] tag = new Object[2];
				Object[] row = new Object[this.getGrid()
						.getColumnCount()];
				String sourceName;

				ins = (IXProcessInstance)procs.get(i).getInstances().nextElement();
				if (ins.getKey().getOpdId() != OpdProject.CLIPBOARD_ID) {
					key = ins.getKey();
					
					//row[0] = ins.getIXEntry().getName();
					sourceName = this.opcat.getIXSystemStructure().getIXOpd(
							key.getOpdId()).getName().replaceAll("\n", " ");
					//predecessors!!!
					java.util.ArrayList<Predecessor>preds = entry.getPredecessors();
					for(int j=0; j<preds.size();j++){					
						row = new Object[this.getGrid()
											.getColumnCount()];
						tag = new Object[2];
						//row[0]= sourceName;
						row[0] = entry.getName().replaceAll("\n", " ");
						row[1] = opcat.getIXSystemStructure().getIXEntry(preds.get(j).getProcessID()).getName().replaceAll("\n", " ");
						row[2] = preds.get(j).getType2String();
						tag[0] = key;
						tag[1] = new Long(procs.get(i).getId());
						this.getGrid().addRow(row,
							tag);
					}
				}
		}
	}

}
