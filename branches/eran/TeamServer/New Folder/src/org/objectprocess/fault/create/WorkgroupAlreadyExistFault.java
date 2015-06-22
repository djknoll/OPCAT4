package org.objectprocess.fault.create;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the workgroup name requested for the new workgroup already exists.
 * @author Lior Galanti
 */
public class WorkgroupAlreadyExistFault extends CreationFault {
	public WorkgroupAlreadyExistFault(String workgroup){
		super(FaultCode.WorkgroupAlreadyExistCode + "Workgroup " + workgroup + " already exists.");
	}
}
