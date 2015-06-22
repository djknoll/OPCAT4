package org.objectprocess.fault.authenticate;

import java.io.Serializable;

/**
 * Father exception for all authentication related exceptions.
 * @author Lior Galanti
 */
public class AuthenticationFailedFault extends Exception implements Serializable {
    public AuthenticationFailedFault() {
    	super();
    }
    public AuthenticationFailedFault(String message) {
        super(message);
    }
}
