package gui.procDependencies.dependenciesInit;
import exportedAPI.opcatAPIx.*;
import gui.opmEntities.Predecessor;
import simulation.util.LevelsRelation;
import exportedAPI.opcatAPIx.IXSystem;

/**
 * This class complements the original dependencies of the OPM processes
 * This is done according to the following rules:
 * <li>If process a has FS relation to be, also b will have relation SF to a
 * <li>If process a holds processes b, c as its first level sub-processes,
 * a will have SS dependency to b and to c and verse viseIf process a holds processes b, c as its first level sub-processes,
 * <li>If process a holds processes b, c as its last level sub-processes,
 * a will have FF dependency to b and to c and verse vice
 * Put attention! 
 * <li>The same process can't hold contradicting relations, so the second relation
 * is not added:<br> 
 * Suppose we try to add as the first operation to process a the relation SF to the process b
 * and then as the second operation to a FS relation to the same process b.
 * The second operation will have no effect, as it is a direct violation of the dependencies rules
 * <li>If original processes have circles or the processes OPDs are constructed in such a way that 
 * contradicts the original process dependencies - the algorithm will cause circles!!!
 * <li>After using this algorithm dependencies check operation is recommended!
 * */
public class DependenciesInitializer {
private IXSystem opcatSystem;
	public DependenciesInitializer(IXSystem opcatSystem){
		this.opcatSystem = opcatSystem;
	}
	
	public void completeDependencies(){
		java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();
		for(int l=0; l<procs.size();l++){
			IXProcessEntry currProc = procs.get(l);
			if(currProc.getZoomedInIXOpd()!=null){
				LevelsRelation rel = new LevelsRelation(currProc);
				int size = rel.getNumOfLevels();
				addFirstInZoomedChilds(currProc, rel);
				addLastInZoomedChilds(currProc, rel);
				for(int i=1; i<size; i++){
					addBrothersDependencies(rel, i-1,i);
				}
			}
			completeNonInZoomDependencies(currProc);
		}
		//TODO - fix this initialization
		//ssffInitializer.fix_SS_FF_Dependencies();
	}
	
	public void completeSF_FSDependencies(){
		java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();
		for(int l=0; l<procs.size();l++){
			IXProcessEntry currProc = procs.get(l);
			completeNonInZoomDependencies(currProc);
		}
	}
	
	private void addBrothersDependencies(LevelsRelation rel, int level1, int level2){
		java.util.List<IXProcessEntry>procs1 = rel.getProcessesInLevel(level1);
		java.util.List<IXProcessEntry>procs2 = rel.getProcessesInLevel(level2);
		if(procs1==null || procs1.isEmpty()||procs2==null || procs2.isEmpty())return;
		for(int i=0;i<procs2.size();i++){
			IXProcessEntry proc = procs2.get(i);
			for(int j=0;j<procs1.size();j++){
				proc.addPredecessor(procs1.get(j).getId(), Predecessor.FS);
				procs1.get(j).addPredecessor(proc.getId(), Predecessor.SF);
			}
		}
	}
	
	private void addFirstInZoomedChilds(IXProcessEntry proc, LevelsRelation rel){
		addInZoomedChilds(proc, rel, 0, Predecessor.SS);
	}
	
	private void addLastInZoomedChilds(IXProcessEntry proc, LevelsRelation rel){
		addInZoomedChilds(proc, rel, rel.getNumOfLevels()-1, Predecessor.FF);
	}
	
	private void addInZoomedChilds(IXProcessEntry proc, LevelsRelation rel, int level,
			int type){
		java.util.List<IXProcessEntry>procs = rel.getProcessesInLevel(level);
		if(procs==null || procs.isEmpty())return;
		for(int i=0; i<procs.size();i++){
			proc.addPredecessor(procs.get(i).getId(), type);
		}
	}
	
	private void completeNonInZoomDependencies(IXProcessEntry proc){
		java.util.ArrayList<Predecessor>preds = proc.getPredecessors();
		for(int i=0;i<preds.size();i++){
			Predecessor pred = preds.get(i);
			int type = pred.getType();
			int childType;
			IXProcessEntry child = (IXProcessEntry)this.opcatSystem.getIXSystemStructure().getIXEntry(pred.getProcessID());
			if(type == Predecessor.FF || type == Predecessor.SS)
				childType = type;
			else{
				childType = (type==Predecessor.FS)?Predecessor.SF:Predecessor.FS;
			}
			child.addPredecessor(proc.getId(), childType);
		}
	}
}