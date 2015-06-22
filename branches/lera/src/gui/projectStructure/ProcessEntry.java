package gui.projectStructure;

import java.util.ArrayList;
import java.util.Enumeration;

import exportedAPI.opcatAPI.IProcessEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.Predecessor;

/**
 * <p>
 * This class represents entry of OPM process.
 * 
 * @version 2.0
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * 
 */

public class ProcessEntry extends ThingEntry implements IXProcessEntry,
		IProcessEntry {

	private boolean isBreakpoint = false;
	private boolean isCritical = false;
	/**
	 * Creates ProcessEntry that holds all information about specified pProcess.
	 * 
	 * @param pProcess
	 *            object of OpmProcess class.
	 */

	public ProcessEntry(OpmProcess pProcess, OpdProject project) {
		super(pProcess, project);
	}

	public ProcessEntry(OpmProcess pProcess, ThingEntry parentThing,
			OpdProject project) {
		super(pProcess, parentThing, project);
	}

	/**
	 * Returns process body of this OpmProcess
	 * 
	 */
	public String getProcessBody() {
		return ((OpmProcess) this.logicalEntity).getProcessBody();
	}

	/**
	 * Sets process body of this OpmProcess
	 * 
	 */
	public void setProcessBody(String processBody) {
		((OpmProcess) this.logicalEntity).setProcessBody(processBody);
	}

	public boolean isCritical() {
		return isCritical;
	}

	public void setCritical(boolean isCritical) {
		this.isCritical = isCritical;
	}

	/**
	 * Returns String representing maximum activation time of this OpmProcess.
	 * This String contains non-negative integer X 7 (msec, sec, min, hours,
	 * days, months, years) with semi-colons separation.
	 * 
	 */

	public String getMaxTimeActivation() {
		return ((OpmProcess) this.logicalEntity).getMaxTimeActivation();
	}

	/**
	 * Sets maximum activation time of this OpmProcess. This field contains
	 * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
	 * with semi-colons separation.
	 * 
	 */

	public void setMaxTimeActivation(String time) {
		((OpmProcess) this.logicalEntity).setMaxTimeActivation(time);
	}

	/**
	 * Returns String representing minimum activation time of this OpmProcess.
	 * This String contains non-negative integer X 7 (msec, sec, min, hours,
	 * days, months, years) with semi-colons separation.
	 * 
	 */

	public String getMinTimeActivation() {
		return ((OpmProcess) this.logicalEntity).getMinTimeActivation();
	}

	/**
	 * Sets minimum activation time of this OpmProcess. This field contains
	 * non-negative integer X 7 (msec, sec, min, hours, days, months, years)
	 * with semi-colons separation.
	 * 
	 */

	public void setMinTimeActivation(String time) {
		((OpmProcess) this.logicalEntity).setMinTimeActivation(time);
	}
	
	public void addPredecessor(long predecessorID, int type){
		((OpmProcess) this.logicalEntity).addPredecessor(predecessorID, type);
	}
	public void addPredecessor(Predecessor pred){
		((OpmProcess) this.logicalEntity).addPredecessor(pred);	
	}
	
	public void removePredecessor(long processID){
		((OpmProcess) this.logicalEntity).removePredecessor(processID);
	}
	
	public ArrayList<Predecessor> getPredecessors(){
		return ((OpmProcess) this.logicalEntity).getPredecessors();
	}
	
	public java.util.Vector<IXProcessEntry> getProcessPredecessorsByType(int type){
		return ((OpmProcess) this.logicalEntity).getProcessPredecessorsByType(type);
	}
	
	public void setBreakpoint(boolean isBreakpoint){
		if (this.isBreakpoint == isBreakpoint){
			return;
		}
		this.isBreakpoint = isBreakpoint;
		for (Enumeration e = this.getInstances() ; e.hasMoreElements();)
		{	
			ProcessInstance currInstance = (ProcessInstance)e.nextElement();
			currInstance.setBreakPoint(isBreakpoint);
			currInstance.update();
		}		

	}
	
	public boolean isMarkedAsBreakpoint(){
		return isBreakpoint;
	}

	public String toString() {
		return this.getName();
	}

}
