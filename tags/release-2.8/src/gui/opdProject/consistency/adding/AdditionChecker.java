package gui.opdProject.consistency.adding;

import java.util.Vector;

import gui.opdProject.Opd;
import gui.opdProject.consistency.ConsistencyAbstractChecker;
import gui.opdProject.consistency.ConsistencyCheckerInterface;
import gui.opdProject.consistency.ConsistencyOptions;
import gui.opdProject.consistency.ConsistencyResult;
import gui.opdProject.consistency.adding.rules.InZommedInsideAddition;
import gui.opdProject.consistency.adding.rules.InZommedOutsideAddition;
import gui.opdProject.consistency.adding.rules.SDAdding;

public class AdditionChecker extends ConsistencyAbstractChecker implements ConsistencyCheckerInterface{
	
	private Vector checkers = new Vector() ;  
	private boolean active = true ; 
	boolean cancled = false ; 
	
	public AdditionChecker (ConsistencyOptions options, ConsistencyResult results) {
		super(options, results) ; 
	}

	public void check() { 
		
		ConsistencyCheckerInterface checker = new InZommedOutsideAddition(getMyOptions(), getResults()) ; 
		checker.check() ;
		checkers.add(checker) ;

		checker = new SDAdding(getMyOptions(), getResults()) ; 
		checker.check();
		checkers.add(checker) ;
		
		checker = new InZommedInsideAddition(getMyOptions(), getResults()) ; 
		checker.check();
		checkers.add(checker) ;
	}

	public void deploy(ConsistencyResult checkResult) {
		Opd opd = getMyOptions().getProject().getCurrentOpd() ; 
		for (int i = 0 ; i < checkers.size(); i++) {
			ConsistencyCheckerInterface checker = (ConsistencyCheckerInterface) checkers.get(i) ; 
			if(checker.isDeploy()) {
				checker.deploy(checkResult) ;
			} 
		}
		getMyOptions().getProject().showOPD(opd.getOpdId()); 
		
	}

	public boolean isActive() {
		// TODO Auto-generated method stub
		return active;
	}

	public boolean isDeploy() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setActive(boolean active) {
		this.active = active ; 
		
	}

	public void setDeploy(boolean deploy) {
		// TODO Auto-generated method stub
		
	}

}
