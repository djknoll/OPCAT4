package org.objectprocess.fault.authenticate;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when a login is attempted by a disabled user.
 * @author Lior Galanti
 */
public class UserDisabledFault extends AuthenticationFailedFault{
    public UserDisabledFault(String user){
		super(FaultCode.UserDisabledCode + "User " + user + " is disabled.");
	}
}
