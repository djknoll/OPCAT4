/**
 * 
 */
package simulation.creator;

/**
 * @author Yevgeny Yaroker
 *
 */
public class NoPossibleTasksException extends CreationException {

	/**
	 * 
	 */
	public NoPossibleTasksException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public NoPossibleTasksException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public NoPossibleTasksException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public NoPossibleTasksException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
