package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * @author Lior Galanti
 */
public class PermissionLookupFault extends LookupFault {
	public PermissionLookupFault(String user){
		super(FaultCode.PermissionLookupCode + "Permissions for user " + user + " not found.");
	}
}
