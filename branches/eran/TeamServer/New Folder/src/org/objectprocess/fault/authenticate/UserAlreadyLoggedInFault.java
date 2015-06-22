package org.objectprocess.fault.authenticate;
import org.objectprocess.config.FaultCode;

/**
 * Exception raised when a login is preformed by a user that is already logged in.
 * @author Lior Galanti
 */
public class UserAlreadyLoggedInFault extends AuthenticationFailedFault {
	public UserAlreadyLoggedInFault(String user){
		super(FaultCode.UserAlreadyLoggedInCode + "User " + user + " is already logged in.");
	}
}
