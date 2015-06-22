package org.objectprocess.fault.token;

import org.objectprocess.config.FaultCode;

/**
 * Exception raised when a user atempts to take a session token that is not available.
 * @author Lior Galanti
 */
public class TokenNotAvailableFault extends TokenFault {
	public TokenNotAvailableFault(String session){
		super(FaultCode.TokenNotAvailableCode + "Token for Collaborative session  " + session + " is not available.");
	}
}
