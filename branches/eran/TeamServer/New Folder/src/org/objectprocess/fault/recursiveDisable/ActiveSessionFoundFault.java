package org.objectprocess.fault.recursiveDisable;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when attempting to preform a NON recursive disable of an OPM model that has active collaborative sessions.
 * @author Lior Galanti
 */
public class ActiveSessionFoundFault extends RecursiveDisableFault {
	public ActiveSessionFoundFault(String sessionID){
		super(FaultCode.ActiveSessionFoundCode + "CollaborativeSession " + sessionID + " is active.");
	}
}
