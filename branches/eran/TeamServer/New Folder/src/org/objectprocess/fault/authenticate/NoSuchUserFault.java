package org.objectprocess.fault.authenticate;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the user could not be found in the repository.
 * @author Lior Galanti
 */
public class NoSuchUserFault extends AuthenticationFailedFault{
	public NoSuchUserFault(String user){
		super(FaultCode.NoSuchUserCode + "User " + user + " does not exist.");
	}
}
