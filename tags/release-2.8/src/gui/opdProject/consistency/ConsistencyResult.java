package gui.opdProject.consistency;

import exportedAPI.OpdKey;
import gui.projectStructure.Instance;

import java.util.Hashtable;
import java.util.Vector; 

public class ConsistencyResult {
	
	private String message = "" ;
	private Vector actions = new Vector() ; 
	private Hashtable deployed = new Hashtable(); 
	
	public ConsistencyResult () {
		message = ""; 
	}
	
	public boolean isConsistent() {
		return (actions.size() == 0 );
	}
	
	public String getMessage() {
		return message;
	}

	public Vector getActions() {
		return actions;
	}

	public void setAction(ConsistencyAction action) {
		//check if action is alrady in collection.		
		boolean found = false ; 
		for(int i = 0 ; i < actions.size() ; i++) {
			if (action.getID().equalsIgnoreCase( ((ConsistencyAction) actions.get(i)).getID())) {
				found = true; 
				break ; 
			}
		}
		
		if (!found) actions.add(action);
	}

	public Hashtable getDeployed() {
		return deployed;
	}

	public void setDeployed(Instance ins) {
		deployed.put(ins.getKey().toString(), ins); 
	}
	
	
	public boolean isDeployed(Instance ins) {
		return deployed.containsKey(ins.getKey().toString()); 
	}	
	
	public boolean isDeployed(OpdKey key) {
		return deployed.containsKey(key.toString()); 
	}	

}
