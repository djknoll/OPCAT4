package simulation.plugin;

/**
 * <p>Title: </p>
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
public interface IObservingPlugin extends ISimulationPlugin {
  public void init(IControlCommandHandler commandHandler);
  public void processTaskForward(ILogicalTask task);
  public void processTaskBackward(ILogicalTask task);
}
