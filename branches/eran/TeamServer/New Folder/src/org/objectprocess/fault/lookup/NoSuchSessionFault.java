package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the requested collaborative session could not be located.
 * @author Lior Galanti
 */
public class NoSuchSessionFault extends LookupFault {
	public NoSuchSessionFault(String sessionID){
		super(FaultCode.NoSuchSessionCode + "Collaborative session  " + sessionID + " does not exist.");
	}
}
