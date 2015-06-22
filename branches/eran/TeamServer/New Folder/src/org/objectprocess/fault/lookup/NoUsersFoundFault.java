package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when no users could be found in the repository.
 * @author Lior Galanti
 */
public class NoUsersFoundFault extends LookupFault {
	public NoUsersFoundFault(){
		super(FaultCode.NoUsersFoundCode + "No users found.");
	}
}
