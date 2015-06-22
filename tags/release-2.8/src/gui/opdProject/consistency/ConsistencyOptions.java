package gui.opdProject.consistency;

import java.util.Enumeration;
import java.util.Hashtable;

import gui.opdProject.OpdProject;

public class ConsistencyOptions { 

	private Hashtable instences = null ;
	private OpdProject project = null ; 
	private int myOP = 0 ; 
	
	public ConsistencyOptions(OpdProject inProject, Hashtable inInstances, int op) {
		instences = inInstances ;
		this.project = inProject ;  
		myOP = op ; 
	}

	public Enumeration getInstences() {
		return instences.elements();
	}

	public OpdProject getProject() {
		return project;
	}

	public int getMyOP() {
		return myOP;
	}
}
