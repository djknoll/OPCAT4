package gui.procDependencies.criticalPath;
import exportedAPI.opcatAPIx.*;
import gui.opmEntities.Predecessor;
import gui.procDependencies.ExistCirclesException;
import gui.procDependencies.QuickTopologicSorter;
import gui.procDependencies.QuickTopologicSorter.Condition;
import gui.procDependencies.dependenciesInit.DependenciesInitializer;
import simulation.util.LevelsRelation;

import java.awt.Color;
import java.util.*;

public class CriticalPathConstructor {

	private Activity[] activities;
	private Hashtable<Long, Activity>activs;
	private IXSystem opcatSystem;
	
	public CriticalPathConstructor(IXSystem opcatSystem)throws ExistCirclesException{
		this.opcatSystem = opcatSystem; 
		activs = new Hashtable<Long, Activity>();
		DependenciesInitializer dep = new DependenciesInitializer(opcatSystem);
		dep.completeDependencies();
		this.constructActivitiesTopologicList();
		//TODO - in-zooming processes should be separated to process_start and process_end
		//TODO - then each SS group should get the same FS children
		//TODO - and each FF group should get common SF children
		//TODO - check getDif, getSum usage
		this.WalkListAhead();
		this.WalkListAback();
		this.setCriticalPath();
		this.drawCriticalPath();
	}
	
	public void printTasks(){
		for(int i=0;i<this.activities.length;i++){
			System.out.println("The next task ID"+activities[i].procID);
			System.out.println("eet:"+activities[i].eet);
			System.out.println("let:"+activities[i].let);
			System.out.println("est:"+activities[i].est);
			System.out.println("lst:"+activities[i].lst);
		}
	}
	
	public void drawCriticalPath(){
		IXProcessEntry proc;
		java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();
		for(int i=0;i<procs.size();i++){
			IXEntry currElem = procs.get(i);
			proc = (IXProcessEntry)currElem;
			Color cl;
			if(proc.isCritical()) cl = Color.ORANGE;
			else cl = (Color)((gui.opdProject.OpdProject)opcatSystem).getConfiguration().getProperty("ProcessColor");
			Enumeration<IXInstance> en = proc.getInstances();
			while(en.hasMoreElements()){
				en.nextElement().setBorderColor(cl);
			}		
		}	
	}

	//construct topologically ordered activities list
	public void constructActivitiesTopologicList()throws ExistCirclesException{
		java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();
		QuickTopologicSorter sorter = QuickTopologicSorter.getInstance();
		if(procs == null)return;
		IXProcessEntry[] orderedProcs = sorter.order(procs, new CriticalPathConstructor.ActivitiesCondition());
		int n = procs.size();
		this.activities = new Activity[n];
		activs = new Hashtable<Long, Activity>();
		for(int i=0;i<n;i++){
			//Activity act = new Activity(procs.get(i));
			this.activities[i] = new Activity(orderedProcs[i], orderedProcs[i].getMaxTimeActivation());
			//System.out.println("The order is:"+activities[sort[i]-1].procID);
			this.activs.put(new Long(orderedProcs[i].getId()), activities[i]);
		}
	}
	
	private void WalkListAhead(){
		  activities[0].est = "0;0;0;0;0;0;0";
		  this.activities[0].eet = Activity.getSum(activities[0].est, activities[0].duration);
		  Activity activity = null;
		  IXProcessEntry proc = null;
		  for(int i = 1; i < activities.length; i++)
		  {
			java.util.ArrayList<Predecessor>predecessors = ((IXProcessEntry)this.opcatSystem.getIXSystemStructure().getIXEntry(activities[i].procID)).getPredecessors();
		    for(int j = 0; j<predecessors.size();j++){
		    	if(predecessors.get(j).getType() == Predecessor.FS){
		    		activity = activs.get(new Long(predecessors.get(j).getProcessID()));
		    		if(activity.isSmaller(activities[i].est, activity.eet))
				       activities[i].est = activity.eet;
		    	}
		    }
		   if(activities[i].est == null)activities[i].est = "0;0;0;0;0;0;0";
		   activities[i].eet = Activity.getSum(activities[i].est, activities[i].duration);
		 }
   }

	private void WalkListAback(){
		//TODO - from all the nodes having no successors find maximal est + duration ???
		//find maximal eet for all the nodes having no sucessors,
		//then for all of those nodes set let to be this maximum
		// and their lst = let-duration!!!
		int na = this.activities.length;
		setMaximalEET2ActivitiesWithoutSucessors();
		if(activities[na - 1].let == null){
			activities[na - 1].let = activities[na - 1].eet;
			activities[na - 1].lst = Activity.getDif(activities[na - 1].let, activities[na - 1].duration);
		}
		Activity activity = null;  
		for(int i = na-2; i >=0; i--){
			java.util.ArrayList<Predecessor>predecessors = ((IXProcessEntry)this.opcatSystem.getIXSystemStructure().getIXEntry(activities[i].procID)).getPredecessors();
		    
			for(int j = 0; j<predecessors.size();j++){
		    	if(predecessors.get(j).getType() == Predecessor.SF){
		    		activity = activs.get(new Long(predecessors.get(j).getProcessID()));
		    		if(activities[i].let == null)
		    			activities[i].let = activity.lst;
		    		else if(!Activity.isSmaller(activities[i].let, activity.lst))
		    				activities[i].let = activity.lst;
		    		}
		    }
		   if(activities[i].let!=null)
		      activities[i].lst = Activity.getDif(activities[i].let, activities[i].duration);
		 }  
	}
	
	private void setMaximalEET2ActivitiesWithoutSucessors(){
		String maximum ="0;0;0;0;0;0;0";
		java.util.ArrayList<Activity>activs = new ArrayList<Activity>();
		for(int i=0; i<activities.length; i++){
			java.util.ArrayList<Predecessor>predecessors = ((IXProcessEntry)this.opcatSystem.getIXSystemStructure().getIXEntry(activities[i].procID)).getPredecessors();
		    int j;
			for(j=0; j<predecessors.size();j++){
		    	if(predecessors.get(j).getType() == Predecessor.SF){
		    		j=predecessors.size()+1;
		    	}
		    }
			//No sucessors!!
			if(j != (predecessors.size()+1)){
				activs.add(activities[i]);
				if(Activity.isSmaller(maximum, activities[i].eet)){
					maximum = activities[i].eet;
				}
			}
		}
		for(int i=0; i<activs.size();i++){
			activs.get(i).let = maximum;
			activs.get(i).lst = Activity.getDif(maximum, activs.get(i).duration);
		}
	}
	
	private void setCriticalPath(){
		for(int i=0;i<this.activities.length;i++){
			IXProcessEntry proc =(IXProcessEntry)this.opcatSystem.getIXSystemStructure().getIXEntry(activities[i].procID);
			proc.setCritical(activities[i].isCritical());
		}
	}
	public class ActivitiesCondition implements QuickTopologicSorter.Condition{
		public ActivitiesCondition(){}
		public int getProcessInDegree(IXProcessEntry proc){
			int sum = 0;
			java.util.ArrayList<Predecessor>preds = proc.getPredecessors();
			for(int i=0; i<preds.size(); i++){
				if(preds.get(i).getType() == Predecessor.FS)sum++;
			}
			return sum;
		}
		public java.util.Vector<IXProcessEntry> getProcessSucessors(IXProcessEntry proc){
				return proc.getProcessPredecessorsByType(Predecessor.SF);
		}
	};
		 

}
