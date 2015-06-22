package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when no collaborative sessions could be found in the repository.
 * @author Lior Galanti
 */
public class NoSessionsFoundFault extends LookupFault {
	public NoSessionsFoundFault(){
		super(FaultCode.NoSessionsFoundCode + "No collaborative sessions found.");
	}
}
