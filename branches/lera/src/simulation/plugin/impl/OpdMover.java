package simulation.plugin.impl;

import simulation.plugin.*;
import exportedAPI.opcatAPIx.*;
import simulation.util.*;
import javax.swing.*;
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
public class OpdMover implements IObservingPlugin {
  private IXSystem opmSystem;

  public OpdMover(IXSystem opmSystem){
    this.opmSystem = opmSystem;
  }
  /**
   * getName
   *
   * @return String
   * @todo Implement this simulation.plugin.ISimulationPlugin method
   */
  public String getName() {
    return "OpdMover";
  }
  
  public boolean isSynchronious(){
  	return true;
  }
  

  public void init(IControlCommandHandler commandHandler){
  	opmSystem.showOPD(1); // Root Opd
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

    final IXProcessEntry process = (IXProcessEntry)task.getEntity();
    if (!ProcessUtils.isInzoomed(process)){
      return;
    }

    final IXOpd processOpd = opmSystem.getIXSystemStructure().getIXOpd(
      process.getZoomedInIXOpd().getOpdId());

    if (task.getType() == ILogicalTask.TYPE.ACTIVATION){
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          opmSystem.showOPD(processOpd.getOpdId());
        }
      });
      return;
    }

    if (task.getType() == ILogicalTask.TYPE.TERMINATION){
      final IXOpd parentOpd = processOpd.getParentIXOpd();

      if (parentOpd != null){
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            opmSystem.showOPD(parentOpd.getOpdId());
          }
        });
      }
      return;
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

    final IXProcessEntry process = (IXProcessEntry)task.getEntity();
    if (!ProcessUtils.isInzoomed(process)){
      return;
    }

    final IXOpd processOpd = opmSystem.getIXSystemStructure().getIXOpd(
      process.getZoomedInIXOpd().getOpdId());

    if (task.getType() == ILogicalTask.TYPE.TERMINATION){
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          opmSystem.showOPD(processOpd.getOpdId());
        }
      });
      return;
    }

    if (task.getType() == ILogicalTask.TYPE.ACTIVATION){
      final IXOpd parentOpd = processOpd.getParentIXOpd();

      if (parentOpd != null){
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            opmSystem.showOPD(parentOpd.getOpdId());
          }
        });
      }
      return;
    }
  }
}
