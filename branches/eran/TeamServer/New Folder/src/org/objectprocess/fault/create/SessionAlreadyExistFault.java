package org.objectprocess.fault.create;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the collaborative session name requested for the new collaborative session already exists.
 * @author Lior Galanti
 */
public class SessionAlreadyExistFault extends CreationFault {
	public SessionAlreadyExistFault(String session){
		super(FaultCode.SessionAlreadyExistCode + "Collaborative session " + session + " already exists.");
	}
}
