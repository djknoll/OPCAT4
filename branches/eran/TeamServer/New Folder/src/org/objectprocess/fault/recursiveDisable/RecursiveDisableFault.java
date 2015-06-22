package org.objectprocess.fault.recursiveDisable;

import java.io.Serializable;

/**
 * Father exception for all recursive disable exceptions.
 * @author Lior Galanti
 */
public class RecursiveDisableFault extends Exception implements Serializable {
	public RecursiveDisableFault() {}

    public RecursiveDisableFault(String message){ 
    	super(message);
    }

}
