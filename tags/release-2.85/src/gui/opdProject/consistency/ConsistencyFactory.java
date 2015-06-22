package gui.opdProject.consistency;

import java.util.Hashtable;

import gui.opdProject.OpdProject;
import gui.opdProject.consistency.adding.AdditionChecker;
import gui.opdProject.consistency.deleting.DeletionChecker;

public class ConsistencyFactory  {
	
	private OpdProject myProject = null;
	private int myOP ; 
	private ConsistencyResult results ; 

	public ConsistencyFactory(OpdProject project, int OP, ConsistencyResult results) {
		this.myProject = project ; 
		this.myOP = OP ; 
		this.results = results ; 
	}
	
	public Object create() {
		
		ConsistencyOptions options = new ConsistencyOptions(this.myProject, (Hashtable) this.myProject.getCurrentOpd().getSelectedItemsHash().clone(), this.myOP); 
		
		if(this.myOP == ConsistencyAction.DELETION ) {
			return new DeletionChecker(options, this.results) ; 
		}
		
		if(this.myOP == ConsistencyAction.ADDITION ) {
			return new AdditionChecker(options, this.results) ; 
		}
		
		return null;
	}

}
