package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * @author Lior Galanti
 */
public class RevisionLookupFault extends LookupFault {
	public RevisionLookupFault(String revision){
		super(FaultCode.RevisionLookupCode + "OPM model revision " + revision + " was not found.");
	}
}
