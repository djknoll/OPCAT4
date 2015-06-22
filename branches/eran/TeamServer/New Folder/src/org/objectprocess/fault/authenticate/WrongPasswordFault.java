package org.objectprocess.fault.authenticate;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised if the passowrd provided for user login is incorrect.
 * @author Lior Galanti
 */
public class WrongPasswordFault extends AuthenticationFailedFault {
	public WrongPasswordFault(String user, String password){
		super(FaultCode.WrongPasswordCode + "Password " + password + " for user " + user + " is incorrect.");
	}
}
