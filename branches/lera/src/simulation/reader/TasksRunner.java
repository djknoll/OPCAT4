package simulation.reader;
import simulation.Logger;
import simulation.plugin.ILogicalTask;
import simulation.tasks.SimulationTask;
import java.util.LinkedList;

/**
 * <p>Title: Simulation Module</p>
 *
 * <p>Description: 
 * QueueReader sends to it tasks to execute, and by plug in runner sends logical
 * tasks.</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public class TasksRunner implements IQueueReaderStatusListener{
  private LinkedList<TaskInfo> tasks;
  private TasksRunningThread tasksRunningThread;
  private boolean isActive;
  private Logger logger = Logger.getInstance();
  private double playingSpeed;
  private boolean isQuickRunMode;
  private RuntimeInfoTable table;
  private PluginsRunner pluginsRunner;

  public TasksRunner(IQueueReaderStatus status, RuntimeInfoTable table, PluginsRunner pluginsRunner) {
	this.pluginsRunner = pluginsRunner;  
    tasks = new LinkedList<TaskInfo> ();
    tasksRunningThread = new TasksRunningThread();
    this.table = table;
    playingSpeed = status.getPlayingSpeed();
    isQuickRunMode = status.isQuickRunMode();
  }

  public void statusChanged(IQueueReaderStatus status){
    playingSpeed = status.getPlayingSpeed();
    isQuickRunMode = status.isQuickRunMode();
  }

  public void start(){
    logger.print("TasksRunner:start - started",2);
    isActive = true;
    tasksRunningThread.start();
  }

  public void stop(){
    logger.print("TasksRunner:stop - stopped",2);
    isActive = false;
  }

  public void run(SimulationTask task, int startTime, int endTime, boolean isForwardExecuted) {
    logger.print("TasksRunner:run - adding next task to queue start time "+startTime
                +" endTime "+endTime+"  isForward "+isForwardExecuted+" "+task.toString() ,0);
    if (!(task instanceof ILogicalTask) && isQuickRunMode){
    	return;
    }

    int time = Math.abs(endTime - startTime);
    int taskEndTime = task.getStartTime() + task.getDuration();
    if (isForwardExecuted){
      if (task.getStartTime() > startTime){
        time -= (task.getStartTime() - startTime);
      }

      if (taskEndTime < endTime){
        time -= (endTime - taskEndTime);
      }
    }else{
      if (task.getStartTime() > endTime){
        time -= (task.getStartTime() - endTime);
      }

      if (taskEndTime < startTime){
        time -= (startTime - taskEndTime);
      }

    }


    synchronized (tasks) {
      tasks.addLast(new TaskInfo(task, time, isForwardExecuted));
      tasks.notify();
    }
  }

  class TasksRunningThread extends Thread {
    public void run() {
      while (isActive) {
        TaskInfo currTask;
        synchronized (tasks) {
          if (tasks.isEmpty()){
            try {
              if (isActive){
                logger.print("TasksRunner:TasksRunningThread - waiting for new tasks",4);
                tasks.wait();
              }else{
                return;
              }
            }
            catch (InterruptedException ex) {
            }
          }
          currTask = tasks.poll();
        }

        logger.print("TasksRunner:TasksRunningThread - trying to execute next task",4);
        if (currTask.isForwardExecuted){
          currTask.task.executeForward(currTask.time, playingSpeed, table);
        }else{
          currTask.task.executeBackward(currTask.time, playingSpeed, table);
        }
        
        if (currTask.task instanceof ILogicalTask){
            pluginsRunner.run((ILogicalTask)currTask.task, currTask.isForwardExecuted);
        }
      }
    }
  }
}

class TaskInfo{
  public SimulationTask task;
  public int time;
  public boolean isForwardExecuted;
  public TaskInfo(SimulationTask task, int time, boolean isForwardExecuted){
    this.task = task;
    this.time = time;
    this.isForwardExecuted = isForwardExecuted;
  }
}


