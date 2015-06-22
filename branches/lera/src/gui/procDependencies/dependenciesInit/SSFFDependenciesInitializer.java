package gui.procDependencies.dependenciesInit;

import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXSystem;
import gui.opmEntities.Predecessor;
import gui.procDependencies.ExistCirclesException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

public class SSFFDependenciesInitializer {
	IXSystem opcatSystem;
	public SSFFDependenciesInitializer(IXSystem opcatSystem){
		this.opcatSystem = opcatSystem;
	}

	void fix_SS_FF_Dependencies()throws ExistCirclesException{
		ArrayList<HashSet<IXProcessEntry>>ssgroups = new ArrayList<HashSet<IXProcessEntry>>();
		ArrayList<HashSet<IXProcessEntry>>ffgroups = new ArrayList<HashSet<IXProcessEntry>>();
		//Construct FF, SS clusters
		constructGroup(ssgroups, Predecessor.SS);
		constructGroup(ffgroups, Predecessor.FF);
		//In each group add all the FS to all the SS procs
		for(int i=0; i<ssgroups.size();i++){
			addMyPredecessors(ssgroups.get(i), Predecessor.SF);
		}
		//In each group add all the SF to all the FF procs
		for(int i=0; i<ffgroups.size();i++){
			addMyPredecessors(ffgroups.get(i), Predecessor.FS);
		}
	}
	
	private void constructGroup(ArrayList<HashSet<IXProcessEntry>>ssgroups, int type){
		java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();;
		for(int l=0; l<procs.size();l++){
			IXProcessEntry proc = procs.get(l);
			Vector<IXProcessEntry>preds = proc.getProcessPredecessorsByType(type);
			if(preds==null || preds.size()==0)continue;
			HashSet<IXProcessEntry> ssprocs = getGroup(ssgroups, proc);
			if(ssprocs == null){
				ssprocs = new HashSet<IXProcessEntry>();
				ssprocs.addAll(preds);
				ssprocs.add(proc);
				ssgroups.add(ssprocs);
			}else{
				ssprocs.addAll(preds);
			}
		}
	}
	
	private HashSet<IXProcessEntry> getGroup(ArrayList<HashSet<IXProcessEntry>>groups, IXProcessEntry proc){
		if(groups.isEmpty())return null;
		for(int i=0; i<groups.size();i++){
			HashSet<IXProcessEntry>group = groups.get(i);
			if(group.contains(proc))return group;
		}
		return null;
	}
	
	private void addMyPredecessors(HashSet<IXProcessEntry> ssprocs, int type){
		if(ssprocs.isEmpty())return;
		int verseType = (type==Predecessor.FS)?Predecessor.SF:Predecessor.FS;
		Vector<IXProcessEntry>preds = new Vector<IXProcessEntry>();
		Iterator<IXProcessEntry> procsIter = ssprocs.iterator();
		while(procsIter.hasNext()){
			IXProcessEntry ent = procsIter.next();
			Vector<IXProcessEntry> childs = ent.getProcessPredecessorsByType(type);
			for(int j=0; j<childs.size();j++){
				if(!preds.contains(childs.elementAt(j))) preds.add(childs.elementAt(j));
			}
		}
		procsIter = ssprocs.iterator();
		while(procsIter.hasNext()){
			IXProcessEntry ent = procsIter.next();
			for(int j=0; j<preds.size();j++){
				IXProcessEntry pred = preds.get(j);
				ent.addPredecessor(pred.getId(), type);
				pred.addPredecessor(ent.getId(), verseType);
			}
		}
	}
}
