package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 *  * Exception raised when no revisions could be found in the repository.
 * @author Lior Galanti
 */
public class NoRevisionsFoundFault extends LookupFault {
	public NoRevisionsFoundFault(String opmModelID){
		super(FaultCode.NoRevisionsFoundCode + "No revisions found for OPM model " + opmModelID + ".");
	}
}
