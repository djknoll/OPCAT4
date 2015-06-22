package gui.procDependencies;

import exportedAPI.opcatAPIx.IXProcessEntry;
import gui.opmEntities.Predecessor;
import java.util.*;

public class QuickTopologicSorter {
	private static QuickTopologicSorter sorter = null;
	
	private QuickTopologicSorter(){sorter = this;}
	
	public static QuickTopologicSorter getInstance(){
		if(sorter == null)
			new QuickTopologicSorter();
		return sorter;
	}
	
	public IXProcessEntry[] order(ArrayList<IXProcessEntry>procs, Condition cond)throws ExistCirclesException{
		int n = procs.size();
		int[] sort = new int[n]; 
		int[] inDeg = new int[n]; 
		IXProcessEntry[] orderedProcs = new IXProcessEntry[procs.size()];
		for (int i=0; i<n; i++) inDeg[i] = cond.getProcessInDegree(procs.get(i)); 
		int cnt = -1; 
		boolean progress = true;  
		while (progress){ 
		     progress = false; 
		     for (int v=0; v<n; v++) { 
		        if (inDeg[v] == 0){ 
		          sort[v] = ++cnt; 
		          progress = true; 
		          inDeg[v] = -1; 		  
		          java.util.Vector<IXProcessEntry> nbrs = cond.getProcessSucessors(procs.get(v)); 
		          for (int k=0; k<nbrs.size(); k++){ 
		             int u = procs.indexOf(nbrs.elementAt(k)); 
		             inDeg[u] = inDeg[u] - 1; 
		          } 
		        } 
		     }  
		  }
		String errorLog = "";
		for(int i=0; i<n;i++){
			if(inDeg[i] !=-1 ){	
				errorLog = errorLog + "#" + procs.get(i).getId();
			}
		}
		if(!errorLog.equals(""))throw new ExistCirclesException(errorLog);
		for(int i=0;i<n;i++){
			orderedProcs[sort[i]] = procs.get(i);
		}
		return orderedProcs;
	}
	
	public interface Condition{
		public int getProcessInDegree(IXProcessEntry proc);
		public java.util.Vector<IXProcessEntry> getProcessSucessors(IXProcessEntry proc);
	};
}

