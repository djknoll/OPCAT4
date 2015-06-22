package org.objectprocess.fault.token;

import org.objectprocess.config.FaultCode;

/**
 * @author Lior Galanti
 */
public class NotTheTokenHolderFault extends TokenFault {
	public NotTheTokenHolderFault(String user){
		super(FaultCode.NotTheTokenHolderCode + "User  " + user + " is not the token holder.");
	}
}
