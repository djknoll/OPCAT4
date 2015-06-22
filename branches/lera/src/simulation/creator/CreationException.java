/**
 * 
 */
package simulation.creator;

/**
 * @author Yevgeny Yaroker
 *
 */
public class CreationException extends Exception {

	/**
	 * 
	 */
	public CreationException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public CreationException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public CreationException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public CreationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
