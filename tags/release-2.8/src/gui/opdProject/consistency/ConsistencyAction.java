package gui.opdProject.consistency;

import gui.opdProject.Opd; 
import gui.projectStructure.Instance;

public class ConsistencyAction {
	
	public static final int DELETION = 1 ;
	public static final int THING_INZOOMED_ADDITION = 2 ; 	
	public static final int CHANGE = 3 ;
	public static final int LINK_ADDITION = 4 ;
	public static final int THING_SD_ADDITION = 5 ;
	public static final int ADDITION = 6 ;
	public static final int THING_INZOMMED_INSIDE_ADDITION = 7 ;
	public static final int NO_ACTION = 0 ;
	
	private Opd myOpd = null ; 
	private int myType = ConsistencyAction.NO_ACTION;
	private Instance mySourceInstance = null ; 
	private Instance[] myEffectedInstances = null; 
	private String ID ; 
	
	public ConsistencyAction (int type, Opd opd, Instance sourceInstance, Instance[] effectedInstances ) {
		myType = type ; 
		myOpd = opd ;  
		mySourceInstance = sourceInstance ; 
		myEffectedInstances = effectedInstances ; 
		
		ID = sourceInstance.getKey().toString();  
		for(int i = 0 ; i < effectedInstances.length ; i++) {
			ID = ID + effectedInstances[i].getKey().toString(); 
		}
	}

	
	public Opd getMyOpd() {
		return myOpd;
	}

	public int getMyType() {
		return myType;
	}

	public Instance getMySourceInstance() {
		return mySourceInstance;
	}


	public String getID() {
		return ID;
	}


	public Instance[] getMyEffectedInstances() {
		return myEffectedInstances;
	}

	public String toString() {
		return this.getID() ; 
	}
	
}
