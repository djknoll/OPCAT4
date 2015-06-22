package org.objectprocess.fault.recursiveDisable;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when attempting a NON recursive disable of a collaborative session that has logged in users.
 * @author Lior Galanti
 */
public class UsersStillLoggedInFault extends RecursiveDisableFault {
	public UsersStillLoggedInFault(String session){
		super(FaultCode.UsersStillLoggedInCode + "Users are still logged into " + session + ".");
	}
}
