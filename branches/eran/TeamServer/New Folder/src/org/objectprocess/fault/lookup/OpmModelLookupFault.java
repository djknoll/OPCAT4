package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * @author Liro Galanti
 */
public class OpmModelLookupFault extends LookupFault {
	public OpmModelLookupFault(String opmModel){
		super(FaultCode.OpmModelLookupCode + "OPM model " + opmModel + " was not found.");
	}
}
