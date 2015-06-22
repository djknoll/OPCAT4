package simulation.queue;
import simulation.tasks.SimulationTask;
import simulation.Logger;
import java.util.*;
import java.lang.*;
import java.io.File;

import exportedAPI.opcatAPIx.IXSystem;

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
public class TasksQueue {
  private final static int INITIAL_SIZE = 5000;
  private static EndTimeComparator  endTimeComparator = new EndTimeComparator();

  private QueueList<SimulationTask> startTimeOrder = new QueueList<SimulationTask>(INITIAL_SIZE);
  private LinkedList<SimulationTask> endTimeOrder = new LinkedList<SimulationTask>();

  private QueueSerializer serializer;
  private Logger logger = Logger.getInstance();
  private int startOrderIndex;
  private ListIterator<SimulationTask> endOrderIter;
  private int lastCreatedTS;

  public TasksQueue() {
    serializer = new QueueSerializer(startTimeOrder);
    clear();
  }

  public synchronized void clear(){
    startTimeOrder.clear();
    endTimeOrder.clear();
    lastCreatedTS = 0;
    startOrderIndex = 0;
    endOrderIter = endTimeOrder.listIterator(0);

  }

  public synchronized boolean isEmpty(){
    return startTimeOrder.isEmpty();
  }


  /**
   * Returns index to the first task with task.startTime >= startTime
   * Returns null if no such element was found
   * @param startTime int
   * @return int
   */

  private synchronized int findStartIndex(int startTime){
    int currIndex = startOrderIndex;
    while (currIndex > 0){
      if (startTimeOrder.get(currIndex).getStartTime() >= startTime){
        currIndex--;
      }else{
        break;
      }
    }

    while (currIndex < startTimeOrder.size()){
      if (startTimeOrder.get(currIndex).getStartTime() < startTime){
        currIndex++;
        if (currIndex >= startTimeOrder.size()){
          return -1;
        }
      }else{
        return currIndex;
      }
    }

    return -1;
  }

  public synchronized List<SimulationTask> getStartOrderedTasks(int startTime, int endTime){
    logger.print("TasksQueue:getStartOrderedTasks - Got request for startTime "+startTime+" endTime "+endTime, 2);
    ArrayList<SimulationTask> returnedTasks = new ArrayList<SimulationTask>();

    int currIndex = findStartIndex(startTime);
    if (currIndex == -1){
      return returnedTasks;
    }

    while (currIndex < startTimeOrder.size()){
      SimulationTask currTask = startTimeOrder.get(currIndex);
      if (currTask.getStartTime() < endTime){
        returnedTasks.add(currTask);
        currIndex++;
      }else{
        break;
      }
    }

    startOrderIndex = Math.min(currIndex, startTimeOrder.size() - 1);
    logger.print("TasksQueue:getStartOrderedTasks: returning tasks - ", 2);
    for (int i = 0;i < returnedTasks.size(); i++){
      logger.print(returnedTasks.get(i).toString(),1);
    }

    return returnedTasks;
  }

  /**
   * Returns iterator to the first element (in reverse order)
   * such as its .previous() method returns task with
   *  task.endTime < startTime
   * @param endTime int
   * @return ListIterator
   */

  private synchronized ListIterator<SimulationTask> findEndIndex(int startTime, ListIterator<SimulationTask> iter){

    while (iter.hasNext()){
      SimulationTask currTask = iter.next();
      int taskEndTime = currTask.getStartTime() + currTask.getDuration() - 1;
      if (taskEndTime >= startTime){
        break;
      }
    }


    while (iter.hasPrevious()){
      SimulationTask currTask = iter.previous();
      int taskEndTime = currTask.getStartTime() + currTask.getDuration() - 1;

      if (taskEndTime < startTime){
        iter.next();
        return iter;
      }
    }

    return null;
  }

  public synchronized List<SimulationTask> getEndOrderedTasks(int startTime, int endTime){
    logger.print("TasksQueue:getEndOrderedTasks Got request for startTime "+startTime+" endTime "+endTime, 4);
    ArrayList<SimulationTask> returnedTasks = new ArrayList<SimulationTask>();

    ListIterator<SimulationTask> currIter = findEndIndex(startTime, endOrderIter);
    if (currIter == null){
      return returnedTasks;
    }


    while (currIter.hasPrevious()){
      SimulationTask currTask = currIter.previous();
      int taskEndTime = currTask.getStartTime() + currTask.getDuration() - 1;
      if (taskEndTime >= endTime){
        returnedTasks.add(currTask);
      }else{
        break;
      }
    }

    endOrderIter = currIter;
    logger.print("TasksQueue:getEndOrderedTasks: returning tasks - ", 2);
    for (int i = 0;i < returnedTasks.size(); i++){
      logger.print(returnedTasks.get(i).toString(),1);
    }

    return returnedTasks;
  }


  public synchronized void put(List<SimulationTask> tasks){
    logger.print("TasksQueue:put - '"+tasks.size()+"' new tasks added",2);
    if (tasks == null || tasks.size() == 0){
      return;
    }

    Collections.sort(tasks);
    if (startTimeOrder.size() > 0 && startTimeOrder.get(startTimeOrder.size()-1).getStartTime() > tasks.get(0).getStartTime()){
      throw new IllegalArgumentException("TasksQueue::put - not in order task");
    }
    startTimeOrder.addAll(tasks);
    if (!startTimeOrder.isEmpty()){
      lastCreatedTS = startTimeOrder.get(startTimeOrder.size() - 1).getStartTime();
    }

    Collections.sort(tasks, endTimeComparator);

    if (endTimeOrder.isEmpty()){
      endTimeOrder.addAll(tasks);
      endOrderIter = endTimeOrder.listIterator(0);
    }else{
      ListIterator<SimulationTask> lastIter = endTimeOrder.listIterator(endTimeOrder.size() - 1);
      SimulationTask firstEndOrderedTask =  tasks.get(0);
      int endTime = firstEndOrderedTask.getStartTime() + firstEndOrderedTask.getDuration();
      ListIterator<SimulationTask> queueIter = findEndIndex(endTime, lastIter);
      if (queueIter == null){
        queueIter = endTimeOrder.listIterator(0);
      }

      ListIterator<SimulationTask> tasks2InsertIter = tasks.listIterator();
      while (tasks2InsertIter.hasNext()){
        SimulationTask currTask = tasks2InsertIter.next();
        endTime = currTask.getStartTime() + currTask.getDuration();

        boolean currTaskWasInserted = false;
        while (queueIter.hasNext()){
          SimulationTask queueTask = queueIter.next();
          int queueElemEndTime = queueTask.getStartTime() + queueTask.getDuration();
          if (endTime < queueElemEndTime){
            queueIter.previous();
            queueIter.add(currTask);
            currTaskWasInserted = true;
            break;
          }
        }
        if (!currTaskWasInserted){
          queueIter.add(currTask);
        }
      }

      endOrderIter = endTimeOrder.listIterator(0);
    }

  }

  public synchronized int getLastCreatedTS(){
    return lastCreatedTS;
  }

  public synchronized void putRecreated(List<SimulationTask> tasks){
    if (tasks == null || tasks.size() == 0){
      return;
    }

    Collections.sort(tasks);

    if (startTimeOrder.isEmpty()){
      put(tasks);
      return;
    }

    ArrayList<SimulationTask> filteredTasks = new ArrayList<SimulationTask>();

    for (int i = 0; i < tasks.size(); i++){
      if (startTimeOrder.get(startTimeOrder.size() - 1).getStartTime() < tasks.get(i).getStartTime()){
        filteredTasks.add(tasks.get(i));
      }
    }

    put(filteredTasks);

  }

  public synchronized void invalidateTasks(int lastValidTS){
    logger.print("TasksQueue::invalidateTasks - "+ lastValidTS, 2);
    if (startTimeOrder.isEmpty()){
      return;
    }

    for (int i = 0; i < startTimeOrder.size(); i++){
      if (startTimeOrder.get(i).getStartTime() > lastValidTS){
        startTimeOrder.removeRange(i, startTimeOrder.size());
        break;
      }
    }

    ListIterator<SimulationTask> iter = endTimeOrder.listIterator(endTimeOrder.size());
    while (iter.hasPrevious()){
      SimulationTask currTask = iter.previous();

      if (currTask.getStartTime() > lastValidTS){
        iter.remove();
      }
    }

    startOrderIndex = Math.max(0, startTimeOrder.size() - 1);
    endOrderIter = endTimeOrder.listIterator(endTimeOrder.size());
  }

  public synchronized void saveAsXML(File targetFile, IXSystem ownerSystem){
    serializer.saveAsXML(targetFile, ownerSystem);
  }

}

class QueueList<E> extends ArrayList<E>{  // This class is added in order to get access to protected removeRange method
  public QueueList(int initialCapacity) {
    super(initialCapacity);
  }

  protected void removeRange(int fromIndex, int toIndex){
    super.removeRange(fromIndex,toIndex);
  }
}

class EndTimeComparator implements Comparator<SimulationTask>{
  public int compare(SimulationTask o1, SimulationTask o2){
    int endTime1 = o1.getStartTime() + o1.getDuration();
    int endTime2 = o2.getStartTime() + o2.getDuration();
    return (endTime1<endTime2 ? -1 : (endTime1 == endTime2 ? 0 : 1));

  }
}
