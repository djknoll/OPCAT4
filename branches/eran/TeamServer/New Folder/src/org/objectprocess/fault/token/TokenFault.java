package org.objectprocess.fault.token;

import java.io.Serializable;

/**
 * Father exception for all token related exceptions.
 * @author Lior Galanti
 */
public class TokenFault extends Exception implements Serializable {
    public TokenFault() {
    }

    public TokenFault(String message) {
        super(message);
    }
}
