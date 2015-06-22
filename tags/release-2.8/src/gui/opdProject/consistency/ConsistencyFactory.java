package gui.opdProject.consistency;

import gui.opdProject.OpdProject;
import gui.opdProject.consistency.adding.AdditionChecker;
import gui.opdProject.consistency.deleting.DeletionChecker;

import org.apache.commons.collections.Factory;

public class ConsistencyFactory implements Factory {
	
	private OpdProject myProject = null;
	private int myOP ; 
	private ConsistencyResult results ; 

	public ConsistencyFactory(OpdProject project, int OP, ConsistencyResult results) {
		myProject = project ; 
		myOP = OP ; 
		this.results = results ; 
	}
	
	public Object create() {
		
		ConsistencyOptions options = new ConsistencyOptions(myProject, myProject.getCurrentOpd().getSelectedItemsHash(), myOP); 
		
		if(myOP == ConsistencyAction.DELETION ) {
			return new DeletionChecker(options, results) ; 
		}
		
		if(myOP == ConsistencyAction.ADDITION ) {
			return new AdditionChecker(options, results) ; 
		}
		
		return null;
	}

}
