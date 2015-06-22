package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when no OPM models could be found in the repository.
 * @author Lior Galanti
 */
public class NoOpmModelsFoundFault extends LookupFault {
	public NoOpmModelsFoundFault(){
		super(FaultCode.NoOpmModelsFoundCode + "No OPM models found.");
	}
}
