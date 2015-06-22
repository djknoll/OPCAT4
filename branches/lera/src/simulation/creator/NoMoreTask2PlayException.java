package simulation.creator;

import simulation.tasks.logic.DebugInfoTask;

/**
 * @author Yevgeny Yaroker
 *
 */
public class NoMoreTask2PlayException extends CreationException {

	/**
	 * 
	 */
	public NoMoreTask2PlayException(int time) {
		super();
		//DebugInfoTask debugTask = new DebugInfoTask(time, null, DebugInfoTask.FAILURE_TYPE.NO_MORE_TASKS_2PLAY);
		//debugInfo.add(debugTask);
  }
}
