package simulation.creator;

import java.util.*;

import simulation.Logger;
import simulation.tasks.*;
import simulation.tasks.animation.*;
import exportedAPI.opcatAPIx.*;
import simulation.ExternalInput;

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
public class TestCreator implements IQueueCreator {
  private final static int INITIAL_QUEUES_SIZE = 5000;
  private IXSystem opmSystem;
  private ArrayList<SimulationTask> futureTasks = new ArrayList<SimulationTask>(INITIAL_QUEUES_SIZE);
  private ArrayList<SimulationTask> futureTasksFromPreviousIteration = new ArrayList<SimulationTask>(INITIAL_QUEUES_SIZE);
  private ArrayList<SimulationTask> creatingTasks = new ArrayList<SimulationTask>(INITIAL_QUEUES_SIZE);

  private boolean isFirstCreation;
  private int lastGeneratedTS;
  private Logger logger;

  public TestCreator(IXSystem system) {
    opmSystem = system;
    isFirstCreation = true;
    lastGeneratedTS = 0;
    logger = Logger.getInstance();
  }

  public void reset(){
  	
  }
  /**
   * getNextTasks
   *
   * @param time int
   * @return List<SimulationTask>
   */
  public List<SimulationTask> getNextTasks(int time) {
    logger.print("TestCreator:getNextTasks - 'time = "+time+"'",0);
    if (isFirstCreation){
      int currTime = 0;
      IXSystemStructure structure = opmSystem.getIXSystemStructure();
      Enumeration allEntries = structure.getElements();
      while (allEntries.hasMoreElements()){
        IXEntry entry = (IXEntry)allEntries.nextElement();
        if (entry instanceof IXProcessEntry){
            futureTasks.add(new ConnectionEdgeActivationAnimation(currTime, (int)(Math.random()*10000 ), (IXProcessEntry)entry));
            currTime += 1000;
        }
      }
      isFirstCreation = false;
    }

    lastGeneratedTS += time;
    futureTasksFromPreviousIteration = (ArrayList<SimulationTask>)futureTasks.clone();
    creatingTasks.clear();

    Iterator<SimulationTask> iter = futureTasks.iterator();

    while (iter.hasNext()) {
      SimulationTask task = iter.next();
      if (task.getStartTime() < lastGeneratedTS){
        creatingTasks.add(task);
        iter.remove();
      }
    }

    logger.print("TestCreator:getNextTasks - created '"+creatingTasks.size() +"' tasks",2);
    return creatingTasks;
  }

  /**
   * recreateTasks
   *
   * @param newUserTask SimulationTask
   * @param time int
   * @return List<SimulationTask>
   */
  public List<SimulationTask> recreateTasks(ExternalInput input, int inputTime, int time2create){
    futureTasks  = (ArrayList<SimulationTask>)futureTasksFromPreviousIteration.clone();
//    futureTasks.add(newUserTask);
    Iterator<SimulationTask> iter = futureTasks.iterator();

    while (iter.hasNext()) {
      SimulationTask task = iter.next();
      if (task.getStartTime() < lastGeneratedTS){
        creatingTasks.add(task);
        iter.remove();
      }
    }

    return creatingTasks;
  }
}
