package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * @author Lior Galanti
 */
public class WorkgroupLookupFault extends LookupFault {
	public WorkgroupLookupFault(String workgroup){
		super(FaultCode.WorkgroupLookupCode + "Workgroup " + workgroup + " was not found.");
	}
}
