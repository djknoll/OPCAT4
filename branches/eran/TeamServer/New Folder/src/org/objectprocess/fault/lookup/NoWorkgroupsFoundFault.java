package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when no workgroups could be found in the repository.
 * @author Lior Galanti
 */
public class NoWorkgroupsFoundFault extends LookupFault {
	public NoWorkgroupsFoundFault(){
		super(FaultCode.NoWorkgroupsFoundCode + "No Worgroups found.");
	}
}
