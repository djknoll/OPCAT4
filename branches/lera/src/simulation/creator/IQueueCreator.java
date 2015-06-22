package simulation.creator;

import simulation.tasks.SimulationTask;
import exportedAPI.opcatAPIx.IXInstance;
import java.util.List;
import simulation.*;

/**
 * <p>Title: Simulation Module</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public interface IQueueCreator {
  List<SimulationTask> getNextTasks(int time) throws CreationException;
  List<SimulationTask> recreateTasks(ExternalInput input, int inputTime, int time2create) throws CreationException;
  void reset();
}
