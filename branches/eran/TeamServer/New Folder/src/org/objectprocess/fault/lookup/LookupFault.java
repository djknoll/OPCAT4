package org.objectprocess.fault.lookup;

import java.io.Serializable;

/**
 * Father exception for all lookup related exceptions. a lookup exception is raised when the requested element could not be located.
 * @author Lior Galanti
 */
public class LookupFault extends Exception implements Serializable {
    public LookupFault() {
    }

    public LookupFault(String message) {
        super(message);
    }
}
