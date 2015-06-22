package simulation.plugin.impl;

import simulation.plugin.*;
import exportedAPI.opcatAPIx.*;
import simulation.reader.IQueueReaderStatus;

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
public class BreakpointsHandler implements IObservingPlugin {
  private IXSystem opmSystem;
  private IControlCommandHandler commandHandler;

  public BreakpointsHandler(IXSystem opmSystem){
    this.opmSystem = opmSystem;
  }
  /**
   * getName
   *
   * @return String
   * @todo Implement this simulation.plugin.ISimulationPlugin method
   */
  public String getName() {
    return "BreakpointHandler";
  }
  
  public boolean isSynchronious(){
  	return true;
  }
  


  public void init(IControlCommandHandler commandHandler){
    this.commandHandler = commandHandler;
  }

  public void readerStatusChanged(IQueueReaderStatus status){}
  public void destroy(){}
   /**
   * processTaskForward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskForward(ILogicalTask task) {
    if (!(task.getEntity() instanceof IXProcessEntry)){
      return;
    }

    if (task.getType() != ILogicalTask.TYPE.ACTIVATION){
      return;
    }

    if (((IXProcessEntry)task.getEntity()).isMarkedAsBreakpoint()){
      commandHandler.pause();
    }

  }


  /**
   * processTaskBackward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskBackward(ILogicalTask task) {
    if (!(task.getEntity() instanceof IXProcessEntry)){
      return;
    }

    if (task.getType() != ILogicalTask.TYPE.TERMINATION){
      return;
    }

    if (((IXProcessEntry)task.getEntity()).isMarkedAsBreakpoint()){
    	commandHandler.pause();
    }	

  }
}
