package org.objectprocess.fault.authenticate;

import org.objectprocess.config.FaultCode;

/**
 * Excpetion raised when a logout is preformed by a user that is not logged in.
 * @author Lior Galanti
 */
public class UserNotLoggedInFault extends AuthenticationFailedFault {
	public UserNotLoggedInFault(String user){
		super(FaultCode.UserNotLoggedInCode + "User " + user + " is not logged in.");
	}
}
