package org.objectprocess.fault.lookup;

import org.objectprocess.config.FaultCode;

/**
 * @author Lior Galanti
 */
public class UserLookupFault extends LookupFault {
	public UserLookupFault(String user){
		super(FaultCode.UserLookupCode + "User " + user + " was not found.");
	}
}
