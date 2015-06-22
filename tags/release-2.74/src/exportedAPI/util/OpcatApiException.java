package exportedAPI.util;

import gui.util.OpcatException;

/**
 * A general exception used by Opcat core and extension tools. All exceptions thrown
 * by Opcat components should extend the <code>OPMException</code>.
 * @author Eran Toch
 */
public class OpcatApiException extends OpcatException {
    public OpcatApiException(String msg)	{
		super(msg);
	}
}
