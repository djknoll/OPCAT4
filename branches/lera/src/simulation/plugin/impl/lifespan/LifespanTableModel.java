
package simulation.plugin.impl.lifespan;

import javax.swing.table.*;
import javax.swing.tree.MutableTreeNode;

import java.util.*;
import javax.swing.event.*;
import javax.swing.*;

import com.sciapp.table.DefaultListTableModel;
import com.sciapp.table.ListTableModel;
import com.sciapp.treetable.AggregateRow;
import com.sciapp.treetable.DataRow;
import com.sciapp.treetable.DefaultMutableTreeTableModel;
import com.sciapp.treetable.DefaultSortTreeTableModel;
import com.sciapp.treetable.DynamicTreeTableModel;

import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXStateEntry;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @Yevgeny Yaroker
 * @version 1.0
 */
public class LifespanTableModel extends DefaultMutableTreeTableModel implements IHistoryListener{
  public final static String PROCESS_TYPE = "Process";	
  public final static String OBJECT_TYPE = "Object";
  public final static String STATE_TYPE = "State";
	
  HashMap<Long, ParentRow> entityId2RowMap;
  RunHistory history;

  public LifespanTableModel(RunHistory history) {
	super(new String[]{" Name", " Type", " History"});
    this.history = history;
    Iterator<RunHistoryEntry> iter = history.getEntries();
    entityId2RowMap = new HashMap<Long, ParentRow>();
    while (iter.hasNext()){
      RunHistoryEntry currEntry = iter.next();
      ParentRow row = new ParentRow(currEntry);
      insertNodeInto(row,(MutableTreeNode)getRoot(), 0);
      entityId2RowMap.put(currEntry.getOpmEntity().getId(), row);
      
      if (currEntry instanceof ParentObjectHistoryEntry){
    	  ParentObjectHistoryEntry parent = (ParentObjectHistoryEntry)currEntry;
    	  Iterator<ChildObjectHistoryEntry> childIter = parent.childrenIterator();
    	  while (childIter.hasNext()){
    		  insertNodeInto(new ChildRow(childIter.next()) ,row, row.getChildCount());
    	  }
      }
    }

    history.addHistoryListener(this);
  }

  public void entryChanged(final long id){
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
////        fireTableCellUpdated(entityId2RowMap.get(id), 1);
//      }
//    });
  }

  public void timeChanged(final int time){
//    SwingUtilities.invokeLater(new Runnable() {
//      public void run() {
//        fireTableDataChanged();
//      }
//    });
  }
  
  public void childAdded(long parentId, long childId){
	  ParentObjectHistoryEntry parent = (ParentObjectHistoryEntry)history.getHistoryEntry(parentId);
	  ChildObjectHistoryEntry child = parent.getChild(childId);
	  ParentRow parentRow = entityId2RowMap.get(parentId);
	  ParentRow childRow = new ParentRow(child); 
	  insertNodeInto(childRow ,parentRow, parentRow.getChildCount());
	  Iterator<RunHistoryEntry> states = child.stateIterator();
	  while (states.hasNext()){
		  insertNodeInto(new ChildRow(states.next()), childRow , childRow.getChildCount());
	  }
  }

  public void refreshModel(){
	 // fireTreeTableStructureChanged();
  }
  
	private String getType(RunHistoryEntry entry){
		if (entry.getOpmEntity() instanceof IXProcessEntry){
			return PROCESS_TYPE;
		}
		
		if (entry.getOpmEntity() instanceof IXObjectEntry){
			return OBJECT_TYPE;
		}
		
		if (entry.getOpmEntity() instanceof IXStateEntry){
			return STATE_TYPE;
		}
		
		return "";
		
	}

  
  class ParentRow extends AggregateRow{
	  public ParentRow(RunHistoryEntry entry){
		  super(entry, 1);
		  setAggregateValues(new Object[]{entry, getType(entry), entry});
	  }
	  
	  public boolean isHeader(){
		  return true;
	  }
	  
	  public boolean isFooter(){
		  return false;
	  }
	  
  }
  
  class ChildRow extends DataRow{
	  public ChildRow(RunHistoryEntry entry){
		  super(entry ,1);
		  setAggregateValues(new Object[]{entry, getType(entry), entry});
	  }
  }
}
