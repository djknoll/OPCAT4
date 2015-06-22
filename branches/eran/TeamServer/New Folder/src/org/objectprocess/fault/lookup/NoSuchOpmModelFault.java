package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the requested OPM model could not be located.
 * @author Lior Galanti
 */
public class NoSuchOpmModelFault extends LookupFault {
	public NoSuchOpmModelFault(String opmModelID){
		super(FaultCode.NoSuchOpmModelCode + "OPM model " + opmModelID + " does not exist.");
	}
}
