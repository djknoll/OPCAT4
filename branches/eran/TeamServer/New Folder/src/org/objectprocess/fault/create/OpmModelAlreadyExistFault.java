package org.objectprocess.fault.create;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the OPM model name requested for the new OPM model already exists.
 * @author Lior Galanti
 */
public class OpmModelAlreadyExistFault extends CreationFault {
	public OpmModelAlreadyExistFault(String opmModel){
		super(FaultCode.OpmModelAlreadyExistCode + "OPM model " + opmModel + " already exists.");
	}
}
