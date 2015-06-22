package gui.procDependencies.gui;

import javax.swing.table.*;
import exportedAPI.opcatAPIx.*;
import gui.opmEntities.*;
import java.util.*;


public class ProcessDependenciesModel extends DefaultTableModel{
		private ArrayList<Predecessor> preds;
		private Object[] procNames;
		private IXSystem myOPMSystem;
		public static final int PROCESSES_INDEX = 0;
	    public static final int TYPE_INDEX = 1;
		protected static String[] tasksHeader = {"Process Name","Type"};
			
   	    public String getColumnName(int column) {
		        return tasksHeader[column];
		}

		public boolean isCellEditable(int row, int column) {
		   return true;
		}
		
		public int getColumnCount(){return tasksHeader.length;}
		public int getRowCount(){return preds==null?0:preds.size();}
		
		/**
		 * Building a RolesListModel using an existing collection.
		 */
		public ProcessDependenciesModel(ArrayList<Predecessor> preds,
				IXSystem myOPMSystem, Object[] procNames) {
			this.myOPMSystem = myOPMSystem;
			this.procNames = procNames;
			if (preds == null) {
				preds = new ArrayList<Predecessor>();
			}
			else {
				this.preds = (ArrayList<Predecessor>)preds.clone();
				this.fireTableDataChanged();
			}
		}
		
		public void setValueAt(Object value, int row, int col) {
			 if(col<0||col>=this.getColumnCount()){return;}
			    if(row<0||row>=preds.size()){
			      row = insert(row);
			    }    
			Predecessor pred = (Predecessor)preds.get(row);
	        String str;
			switch (col) {
	            case PROCESSES_INDEX:	
	            	str = (String)value;
	            	if(str.equals(""))break;
	            	str = str.substring(str.lastIndexOf("#")+1);
	            	pred.setProcessID(new Long(str).longValue());
	            	break;
	            case TYPE_INDEX:
	            	str = (String)value;
	            	pred.setType(str);
	               break;
	           default:
	               System.out.println("invalid index");
	        }
	    }
		
		public Object getValueAt(int row, int column) {
			if(row<0||row>=preds.size())return "";
			Predecessor record = this.preds.get(row);
			if(record == null)return "";
	        switch (column) {
	        	case PROCESSES_INDEX:{
	        		return getPredecessorName(record);
	        	}
	        	case TYPE_INDEX:
	        		return record.getType2String();
	            default:
	               return "";
	        }
	    }
		
		 public int insert(int row){
			    row = preds.size();
			    Predecessor tmp = new Predecessor();
			    tmp.setProcessID(0);
			    tmp.setType(Predecessor.getTypes()[Predecessor.FS]);
			    preds.add(row,tmp);
			    return row;
			  }
		 
		 public boolean delete(int row){
			    if(row < 0 || row>=preds.size())
			      return false;
			    preds.remove(row);
			    return true;
			  }

		private String getPredecessorName(Predecessor pred){
			   if(pred.getProcessID() == -1||pred.getProcessID()==0)return "";
	           Enumeration en = this.myOPMSystem.getIXSystemStructure().getElements();	
               while(en.hasMoreElements()){
            	   IXEntry ent = (IXEntry)en.nextElement();
            	   if(ent instanceof IXProcessEntry){
            		   if(ent.getId()==pred.getProcessID()){
            			   return ent.getName() + " #"+ent.getId();
            		   }
            	   }
               }
               return "";
		}

		public int getSize(){
			if (this.preds == null) {
				return 0;
			}
			return this.preds.size();
		}

		/**
		 * Return the predecessors as an ArrayList model.
		 */
		public ArrayList<Predecessor> getDependencies(){
			return this.preds;
		}
	}
