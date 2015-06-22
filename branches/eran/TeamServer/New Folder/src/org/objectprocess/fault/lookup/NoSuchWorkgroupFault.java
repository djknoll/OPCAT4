package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the requested workgroup could not be located.
 * @author Lior Galanti
 */
public class NoSuchWorkgroupFault extends LookupFault {
	public NoSuchWorkgroupFault(String workgroupID){
		super(FaultCode.NoSuchWorkgroupCode + "Workgroup " + workgroupID + " does not exist.");
	}
}
