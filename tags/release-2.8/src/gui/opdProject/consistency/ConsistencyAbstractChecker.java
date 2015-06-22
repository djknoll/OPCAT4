package gui.opdProject.consistency;


public abstract class ConsistencyAbstractChecker implements ConsistencyCheckerInterface {
	
	private ConsistencyResult results = new ConsistencyResult() ; 
	private ConsistencyOptions myOptions = null ; 
	private boolean deploy = false; 
	
	public ConsistencyAbstractChecker (ConsistencyOptions options, ConsistencyResult results) {
		myOptions = options ; 
		this.results = results ; 
	}	
	
	public  void check() {
		//empty stub
	} 
	
	public void deploy(ConsistencyResult checkResult) {
		//empty stub
	}
	
	public boolean isDeploy() {
		return deploy ;
	}
	

	public ConsistencyResult getResults() {
		return results;
	}

	public ConsistencyOptions getMyOptions() {
		return myOptions;
	}
	
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();		
	}

	
}
