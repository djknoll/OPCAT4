package org.objectprocess.fault.recursiveDisable;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when attempting to preform a NON recursive disable of a workgroup and active OPM models are found in the workgroup.
 * @author Lior Galanti
 */
public class ActiveOpmModelFoundFault extends RecursiveDisableFault {
	public ActiveOpmModelFoundFault(String opmModelID){
		super(FaultCode.ActiveOpmModelFoundCode + "OpmModel " + opmModelID + " is active.");
	}
}
