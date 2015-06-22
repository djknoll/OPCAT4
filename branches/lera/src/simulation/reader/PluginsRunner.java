package simulation.reader;

import simulation.Logger;
import simulation.reader.TasksRunner.TasksRunningThread;
import simulation.tasks.SimulationTask;
import simulation.plugin.*;
import java.util.*;

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
public class PluginsRunner implements IQueueReaderStatusListener{
  private List<ISimulationPlugin> pluginsList;
  private LinkedList<TaskInfo> tasks;
  private PluginsRunningThread pluginsRunningThread;
  private boolean isActive;
  private Logger logger = Logger.getInstance();

  public PluginsRunner(List<ISimulationPlugin> plugins){
    this.pluginsList = plugins;
    tasks = new LinkedList<TaskInfo> ();
    pluginsRunningThread = new PluginsRunningThread();
  }

  public void setPlugins(List<ISimulationPlugin> plugins){
    this.pluginsList = plugins;
  }

  public void start() {
		logger.print("PluginsRunner:start - started", 2);
		isActive = true;
		pluginsRunningThread.start();
	}

	public void stop() {
		logger.print("PluginsRunner:stop - stopped", 2);
		isActive = false;
	}

	public void run(ILogicalTask task, boolean isForwardExecuted) {
		if (pluginsList == null) {
			return;
		}

		Iterator<ISimulationPlugin> iter = pluginsList.iterator();
		while (iter.hasNext()) {
			ISimulationPlugin plugin = iter.next();
			if (plugin instanceof IObservingPlugin) {
				if (!plugin.isSynchronious()) {
					execute(task, isForwardExecuted, plugin);
				} else {
					synchronized (tasks) {
						tasks.addLast(new TaskInfo(task, isForwardExecuted, plugin));
						tasks.notify();
					}
				}
			}
		}

	}



  private void execute(ILogicalTask task, boolean isForwardExecuted,
			ISimulationPlugin plugin) {
		if (plugin instanceof IObservingPlugin) {
			try {
				if (isForwardExecuted) {
					((IObservingPlugin) plugin).processTaskForward(task);
				} else {
					((IObservingPlugin) plugin).processTaskBackward(task);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

  public void statusChanged(IQueueReaderStatus status) {
    Iterator<ISimulationPlugin> iter = pluginsList.iterator();
    while (iter.hasNext()){
      ISimulationPlugin plugin = iter.next();
      try {
				plugin.readerStatusChanged(status);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
  }
  
  class PluginsRunningThread extends Thread {
	  public void run() {
		  while (isActive) {
			  TaskInfo currTask;
			  synchronized (tasks) {
				  if (tasks.isEmpty()){
					  try {
						  if (isActive){
							  logger.print("PluginsRunner:PluginsRunningThread - waiting for new tasks",4);
							  tasks.wait();
						  }else{
							  return;
						  }
					  }
					  catch (InterruptedException ex) {}
				  }
				  currTask = tasks.poll();
			  }
			  
			  execute(currTask.task, currTask.isForwardExecuted, currTask.executingPlugin);
		  }
	  }
  }
	

  class TaskInfo{
	  public ILogicalTask task;
	  public boolean isForwardExecuted;
	  public ISimulationPlugin executingPlugin;
	  
	  public TaskInfo(ILogicalTask task, boolean isForwardExecuted, ISimulationPlugin executingPlugin){
	    this.task = task;
	    this.isForwardExecuted = isForwardExecuted;
	    this.executingPlugin = executingPlugin;
	  }
  }
}
