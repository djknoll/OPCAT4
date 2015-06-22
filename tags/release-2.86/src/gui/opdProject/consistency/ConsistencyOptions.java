package gui.opdProject.consistency;

import java.util.Enumeration;
import java.util.Hashtable;

import gui.opdProject.OpdProject;

public class ConsistencyOptions { 

	private Hashtable instences = null ;
	private OpdProject project = null ; 
	private int myOP = 0 ; 
	
	public ConsistencyOptions(OpdProject inProject, Hashtable inInstances, int op) {
		this.instences = inInstances ;
		this.project = inProject ;  
		this.myOP = op ; 
	}

	public Enumeration getInstences() {
		return this.instences.elements();
	}

	public OpdProject getProject() {
		return this.project;
	}

	public int getMyOP() {
		return this.myOP;
	}
}
