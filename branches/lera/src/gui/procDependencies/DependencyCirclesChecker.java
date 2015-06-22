package gui.procDependencies;

	import exportedAPI.opcatAPIx.*;
	import gui.opmEntities.Predecessor;
	import gui.procDependencies.ExistCirclesException;
	import gui.procDependencies.QuickTopologicSorter;
	import gui.procDependencies.dependenciesInit.DependenciesInitializer;
	import javax.swing.JOptionPane;

	public class DependencyCirclesChecker {

		private IXSystem opcatSystem;
		
		public DependencyCirclesChecker(IXSystem opcatSystem)throws ExistCirclesException{
			this.opcatSystem = opcatSystem; 
			DependenciesInitializer dep = new DependenciesInitializer(opcatSystem);
			dep.completeSF_FSDependencies();
			this.constructTopologicList();
		}
		
		//construct topologic list
		private void constructTopologicList()throws ExistCirclesException{
			java.util.ArrayList<IXProcessEntry>procs = this.opcatSystem.getIXSystemStructure().getProcesses();
			QuickTopologicSorter sorter = QuickTopologicSorter.getInstance();
			if(procs == null)return;
			try{
			sorter.order(procs, new CheckerCondition());
			javax.swing.JOptionPane.showMessageDialog(null, "No cicles were found!", "Message", JOptionPane.INFORMATION_MESSAGE);
			}catch(ExistCirclesException e){
				throw e;
			}
		}
		
		public class CheckerCondition implements QuickTopologicSorter.Condition{
			public CheckerCondition(){}
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
