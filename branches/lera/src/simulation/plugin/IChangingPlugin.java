package simulation.plugin;

import simulation.*;

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
public interface IChangingPlugin extends ISimulationPlugin {
  public void init(IControlCommandHandler commandHandler, IActivationHandler activationHandler);
  public void processTaskForward(ILogicalTask task);
 }
