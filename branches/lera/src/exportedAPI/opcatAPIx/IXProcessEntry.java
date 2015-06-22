package exportedAPI.opcatAPIx;

import java.util.ArrayList;

import gui.opmEntities.OpmProcess;
import gui.opmEntities.Predecessor;

/**
 * IXProcessEntry is an interface to logical representation of Process in OPM meaning
 */

public interface IXProcessEntry extends IXThingEntry
{

	/**
	 * Returns process body of this IXProcessEntry
	 * @return process body of this IXProcessEntry
	 */
	public String getProcessBody();

	/**
	* Sets process body of this OpmProcess
	* @param processBody - string contains body of of the OPM process
	*/
	public void setProcessBody(String processBody);

	/**
	* Returns String representing maximum activation time
	* of this Process. This String contains non-negative integer X 7
	* (msec, sec, min, hours, days, months, years) with semi-colons
	* separation.
	* @return maximum activation time in string representation
	*/
	public String getMaxTimeActivation();

	/**
	* Sets  maximum activation time
	* of this Process. This field contains non-negative integer X 7
	* (msec, sec, min, hours, days, months, years) with semi-colons
	* separation.<br>
	* <strong>Important</strong>: Legality of given string is not checked when using this method.
	* @param time - maximum activation time in string representation
	*/
	public void setMaxTimeActivation(String time);

	/**
	* Returns String representing minimum activation time
	* of this Process. This String contains non-negative integer X 7
	* (msec, sec, min, hours, days, months, years) with semi-colons
	* separation.
	* @return minimum activation time in string representation
	*/
	public String getMinTimeActivation();


	/**
	* Sets  minimum activation time
	* of this Process. This field contains non-negative integer X 7
	* (msec, sec, min, hours, days, months, years) with semi-colons
	* separation.<br>
	* <strong>Importent</strong>: Legality of given string is not checked when using this method
	* @param time - minimum activation time in string representation
	*/
	public void setMinTimeActivation(String time);
	
	/**
	* Checks if process is marked as breakpoint
	* @return true if process is marked as breakpoint, false otherwise
	*/
	
	public boolean isMarkedAsBreakpoint();

/**
	* Sets  breakpoint property for this Process.

	* @param isBreakpoint - determines whether this process is marked as breakpoint
	*/

	public void setBreakpoint(boolean isBreakpoint);	
	
	/**
	 * Adds process predecessor 
	 * @param predecessorID - predecessor process entity ID
	 * @param type - the dependency type code(FF = 0,FS, SS, SF)
	 * */
	public void addPredecessor(long predecessorID, int type);
	
	/**
	 * Removes process predecessor 
	 * @param predecessorID - predecessor process entity ID
	 * */
	public void removePredecessor(long predecessorID);
	public ArrayList<Predecessor> getPredecessors();
	
	/**
	 * @param type - one of the possible types{SS,FF,SF,FS} defines in Predecessor 
	 * class
	 * */
	public java.util.Vector<IXProcessEntry> getProcessPredecessorsByType(int type);
		
	
	public boolean isCritical();

	public void setCritical(boolean isCritical);

}