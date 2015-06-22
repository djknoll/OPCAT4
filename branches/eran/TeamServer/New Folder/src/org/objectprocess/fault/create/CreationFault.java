package org.objectprocess.fault.create;

import java.io.Serializable;

/**
 * Father Exception for all ejb creation related exceptions.
 * @author Lior Galanti
 */
public class CreationFault extends Exception implements Serializable {
	public CreationFault() {
    }

    public CreationFault(String message) {
        super(message);
    }
}
