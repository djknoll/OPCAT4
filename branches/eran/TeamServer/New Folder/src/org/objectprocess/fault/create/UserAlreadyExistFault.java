package org.objectprocess.fault.create;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when the user name requested for the new user already exists.
 * @author Lior Galanti
 */
public class UserAlreadyExistFault extends CreationFault {
	public UserAlreadyExistFault(String user){
		super(FaultCode.UserAlreadyExistCode + "User " + user + " already exists.");
	}
}
