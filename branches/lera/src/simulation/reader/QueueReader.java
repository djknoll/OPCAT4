package simulation.reader;
import simulation.SimulationConfig;
import simulation.creator.CreationException;
import simulation.creator.IQueueCreator;
import simulation.Logger;
import simulation.queue.TasksQueue;
import simulation.tasks.SimulationTask;
import java.io.File;
import java.util.*;
import simulation.*;
import simulation.plugin.*;
import exportedAPI.opcatAPIx.*;

/**
 * <p>Title: Simulation Module</p>
 *
 * <p>Description: The main reader, entry point!
 * Accepts creator, from whom he gets events.
 * Holds here TsksQueue.
 * Tasks are logical or animation, any logic task has time 1 (atomic)
 * Animation - may long more than one tick.
 * Do/undo goes in the queue - forward we go according to the task start time, 
 * backward by finish time.
 * Has thread - daemon - constant quanta of time, if in the middle user stops,
 * roll backs are more easy. (step may has many qaunts).
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public class QueueReader implements IControlCommandHandler, IActivationHandler{
  public static final int TASKS_CHUNK_SIZE = 1000;
  private static final int CURRENT_TASKS_LIST_SIZE = 200;

  private IXSystem opmSystem;
  private SimulationConfig config = SimulationConfig.getInstance();
  private IQueueCreator queueCreator;
  private TasksQueue queue;
  private QueueReadingThread processingThread;
  private TasksRunner tasksRunner;
  private RuntimeInfoTable infoTable;
  private VisualStateUpdater visualStateUpdater;
  private PluginsRunner pluginsRunner;
  private Object stateLock = new Object();
  private int numOfRequestedOperations;
  private boolean isContinousPlaying;
  private boolean isPlayingForward;
  private boolean isPaused;
  private boolean isStarted;
  ArrayList<SimulationTask> executingTasks = new ArrayList<SimulationTask>(CURRENT_TASKS_LIST_SIZE);
  //what second of qaunta is now
  private int timeLine;
  private ExternalInput externalInput = null;
  private Object externalInputLock = new Object(); 

  private Logger logger;
  private QueueReaderStatus status;
  private ArrayList<IQueueReaderStatusListener> listeners;
  private double playingSpeed;
  private boolean isQuickRunMode;



  public QueueReader(IXSystem opmSystem) {
    this.opmSystem = opmSystem;
    isContinousPlaying = false;
    isPlayingForward = true;
    timeLine = 0;
    isStarted = false;
    playingSpeed = 1.0;
    isQuickRunMode = false;
    queue = new TasksQueue();
    logger = Logger.getInstance();
    status = new QueueReaderStatus();
    listeners = new ArrayList<IQueueReaderStatusListener>();
  }

  public IQueueReaderStatus getStatus(){
    return status;
  }

  private void notifyListeners(){
    status.isStarted = isStarted;
    status.isPaused = isPaused;
    status.isContinuous = isContinousPlaying;
    status.isQuickRunMode = isQuickRunMode;
    status.timeline = timeLine;
    status.isPlayingForward = isPlayingForward;
    status.playingSpeed = playingSpeed;

    synchronized (listeners){
      for (int i = 0; i < listeners.size(); i++) {
        listeners.get(i).statusChanged(status);
      }
    }
  }

  public void addStatusListener(IQueueReaderStatusListener listener){
    synchronized (listeners){
      listeners.add(listener);
    }
  }

  public void removeStatusListener(IQueueReaderStatusListener listener){
    synchronized (listeners){
      listeners.remove(listener);
    }
  }


  public void saveAsXML(File targetFile, IXSystem ownerSystem){
    queue.saveAsXML(targetFile, ownerSystem);
  }


  public void setQueueCreator(IQueueCreator newCreator){
    queueCreator = newCreator;
    timeLine = 0;
  }

  public void setContinousPlay(boolean isContinous){
    isContinousPlaying = isContinous;
    notifyListeners();
  }
  
  public void setQuickRunMode(boolean isQuickRun){
	  isQuickRunMode = isQuickRun;
	  notifyListeners();
  }
  

  public void setPlayingSpeed(double speed){
    if (speed < 0.2){
      throw new IllegalArgumentException("Playing speed should be greater that 0.2");
    }

    playingSpeed = speed;
    notifyListeners();
  }


  public void start(List<ISimulationPlugin> plugins){
    if (isStarted){
      return;
    }
    isStarted = true;
    numOfRequestedOperations = 0;

    infoTable = new RuntimeInfoTable(opmSystem);
    pluginsRunner = new PluginsRunner(plugins);
    pluginsRunner.start();
    tasksRunner = new TasksRunner(status, infoTable, pluginsRunner);
    addStatusListener(tasksRunner);

    visualStateUpdater = new VisualStateUpdater(infoTable);
    visualStateUpdater.update();
    addStatusListener(pluginsRunner);
    tasksRunner.start();
    queue.clear();
    try {
		queue.put(queueCreator.getNextTasks(TASKS_CHUNK_SIZE));
	} catch (CreationException e) {}
	
    processingThread = new QueueReadingThread();
    processingThread.start();
    notifyListeners();
  }

  public void stop(){
    if (!isStarted){
      return;
    }
    isStarted = false;
    removeStatusListener(tasksRunner);
    removeStatusListener(pluginsRunner);
    // Todo - implement nicer stop solution
    if (processingThread != null){
      processingThread.stop();
      processingThread = null;
    }
    
    if (tasksRunner != null){
    	tasksRunner.stop();
    	tasksRunner = null;
    }

    if (pluginsRunner != null){
    	pluginsRunner.stop();
    	pluginsRunner = null;
    }
    
    notifyListeners();
  }

  private void requestStep(){
  	synchronized (stateLock){
  		if (numOfRequestedOperations < 2){
  			numOfRequestedOperations++;
  		}
  		stateLock.notify();
  	}
  }

  public void playForward(){
    synchronized (stateLock){
      isPlayingForward = true;
      isPaused = false;
      requestStep();
    }

    notifyListeners();
  }

  public void playBackward(){
    synchronized (stateLock){
      isPlayingForward = false;
      isPaused = false;
      requestStep();
    }

    notifyListeners();
  }

  public void pause(){
    synchronized (stateLock){
    	numOfRequestedOperations = 0;
    	isPaused = true;
    }

    notifyListeners();
  }

  public void resume(){
    synchronized (stateLock){
      isPaused = false;
      requestStep();
    }
    notifyListeners();
  }

  public boolean activate(ExternalInput input){
    synchronized (externalInputLock) {
      externalInput = input;
    }

    synchronized (stateLock){
      if (!isContinousPlaying){
        isPlayingForward = true;
        requestStep();
      }
    }

    return true;
  }


  private void removeFinishedTasks(int lastTime2Execute, boolean isForward){
    Iterator<SimulationTask> iter = executingTasks.iterator();
    while (iter.hasNext()) {
      SimulationTask currTask = iter.next();
      if (isForward){
        if (currTask.getStartTime() + currTask.getDuration() - 1 < lastTime2Execute){
                  logger.print("removing task "+currTask,4);
          iter.remove();
        }
      }else{
        if (currTask.getStartTime() >= lastTime2Execute){
                  logger.print("removing task "+currTask,4);
          iter.remove();
        }
      }
    }
  }
 
  //in quick mode, not sleep
  //in this mode timeline gets unrealistic values!
  private void sleepAccording2State(long time2sleep){
	  if (isQuickRunMode){
		  return;
	  }

	  long realTime2Sleep = Math.round((double)time2sleep / playingSpeed);
	  try{
		  if (realTime2Sleep > 0) {
			  Thread.sleep(realTime2Sleep);
		  }
	  }catch (InterruptedException ex) {}
  }

  private void stepForward(int stepDuration){
    logger.print("QueueReader:stepForward - 'timeLine = " +timeLine +
                 "','stepDuration = "+stepDuration+"'",2);
    
    if (stepDuration <=0 ){
      return;
    }
    int lastTime2Execute = timeLine + stepDuration;

    if (queue.getLastCreatedTS() < lastTime2Execute){
    	try {
    		queue.put(queueCreator.getNextTasks(Math.max(TASKS_CHUNK_SIZE, stepDuration)));
    	} catch (CreationException e) {
    		pause();
    	}
    }

    //for debugging
    printExecutingTasks();
    Iterator<SimulationTask> iter = executingTasks.iterator();

    while (iter.hasNext()) {
      tasksRunner.run(iter.next(), timeLine, lastTime2Execute, true);
    }

    List<SimulationTask> tasks =  queue.getStartOrderedTasks(timeLine, lastTime2Execute);
    iter = tasks.iterator();

    while (iter.hasNext()) {
      SimulationTask currTask = iter.next();
  	  int time2NextTask = currTask.getStartTime() - timeLine;
  	  sleepAccording2State(time2NextTask);
      timeLine += time2NextTask;
      tasksRunner.run(currTask, timeLine, lastTime2Execute, true);
      executingTasks.add(currTask);
    }

    sleepAccording2State(lastTime2Execute - timeLine);
    timeLine = lastTime2Execute;
    removeFinishedTasks(lastTime2Execute, true);
  }


  private void stepBackward(int stepDuration){
    if (timeLine <= 0){
      if (isContinousPlaying){
        try {
          Thread.sleep(100); // release CPU
        }
        catch (InterruptedException ex) {
        }
      }
      return;
    }

    logger.print("QueueReader:stepBackward - 'timeLine = " +timeLine +
                 "' ,'stepDuration = "+stepDuration+"'",5);
    int lastTime2Execute = Math.max(timeLine - stepDuration, 0);

    printExecutingTasks();
    Iterator<SimulationTask> iter = executingTasks.iterator();
    while (iter.hasNext()) {
      tasksRunner.run(iter.next(), timeLine, lastTime2Execute, false);
    }

    List<SimulationTask> tasks =  queue.getEndOrderedTasks(timeLine, lastTime2Execute);

    iter = tasks.iterator();

    while (iter.hasNext()) {
      SimulationTask currTask = iter.next();
      int time2NextTask =  timeLine - (currTask.getStartTime() + currTask.getDuration());
      sleepAccording2State(time2NextTask);
      timeLine -= time2NextTask;
      logger.print("QueueReader:stepBackward - ' new time timeLine = " +timeLine + " time2NextTask "+
             time2NextTask,0);

      tasksRunner.run(currTask, timeLine , lastTime2Execute, false);
      executingTasks.add(currTask);
    }

    sleepAccording2State(timeLine - lastTime2Execute);
    timeLine = lastTime2Execute;
    removeFinishedTasks(lastTime2Execute, false);
  }

  private void printExecutingTasks(){
    logger.print("Executing tasks -",2);
    printTasks(executingTasks);
  }

  private void printTasks(List<SimulationTask> tasks2print){
    ListIterator<SimulationTask> iter = tasks2print.listIterator();
    while (iter.hasNext()){
      logger.print(iter.next().toString(), 2);
    }
  }


  class QueueReadingThread extends Thread{
	private int remainedSteps = 0;
	
    public void run(){
      while (true){
        synchronized (stateLock){
          if (isPaused || (!isContinousPlaying && remainedSteps <=0)) {
            try {
              logger.print("QueueReader:QueueReadingThread - waiting 'isPaused = "+
                           isPaused +"', 'isContinousPlaying = "+isContinousPlaying, 2);
              if (numOfRequestedOperations <= 0){
            	if (isQuickRunMode){
            	  visualStateUpdater.update();
            	}
              	stateLock.wait();
              }
              numOfRequestedOperations--;
              remainedSteps = SimulationConfig.getInstance().atomicsInStep;
            }
            catch (InterruptedException ex) {
            }
          }
        }

        synchronized (externalInputLock){
          if (externalInput != null){
        	  queue.invalidateTasks(timeLine - 1);
        	  try{
        		  queue.putRecreated(queueCreator.recreateTasks(externalInput, timeLine, TASKS_CHUNK_SIZE));
        	  } catch (CreationException e) {
        		  pause();
        	  }
        	  externalInput = null;
          }
        }
        
        if (isPlayingForward){
          stepForward(SimulationConfig.DEFAULTS.ATOMIC_STEP_DURATION);
        }else{
          stepBackward(SimulationConfig.DEFAULTS.ATOMIC_STEP_DURATION);
        }

        remainedSteps--;
        notifyListeners();
      }
    }
  }


  class QueueReaderStatus implements IQueueReaderStatus{
    boolean isStarted;
    boolean isPaused;
    boolean isContinuous;
    int timeline;
    boolean isPlayingForward;
    double playingSpeed;
    boolean isQuickRunMode;

    public QueueReaderStatus() {
      isStarted = false;
      isPaused = false;
      isContinuous = false;
      timeline = 0;
      isPlayingForward = true;
      playingSpeed = 1.0;
    }

    public boolean isStarted(){
      return this.isStarted;
    }

    public boolean isPaused(){
      return this.isPaused;
    }

    public boolean isContinuous(){
      return this.isContinuous;
    }
    
    public boolean isQuickRunMode(){
    	return this.isQuickRunMode;
    }

    public int getTimeline(){
      return timeline;
    }

    public boolean isPlayingForward(){
      return isPlayingForward;
    }

    public double getPlayingSpeed(){
      return playingSpeed;
    }
  }

}
