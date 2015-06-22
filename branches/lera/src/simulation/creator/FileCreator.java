package simulation.creator;

import java.util.*;
import java.lang.reflect.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.io.File;
import java.io.IOException;


import simulation.tasks.SimulationTask;
import org.w3c.dom.NodeList;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXInstance;
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
public class FileCreator implements IQueueCreator {
  public final static class XML_TAGS{
    public final static String NUM_OF_TASKS = "numOfTasks";
    public final static String TASK_PREFIX = "task";
    public final static String MAX_ENTITY_ID = "maxEntityId";
  }
  private final static int INITIAL_SIZE = 5000;

  private Document document;
  private int numOfTasks;
  private IXSystem opmSystem;

  private ArrayList<SimulationTask> tasks = new ArrayList<SimulationTask>(INITIAL_SIZE);
  private ArrayList<SimulationTask> creatingTasks = new ArrayList<SimulationTask>(INITIAL_SIZE);
  private int lastGeneratedTS;
  private int lastTaskIndex;
  private int previousLastTaskIndex;
  private File tasksFile;

  public FileCreator(File tasksFile, IXSystem system) {
    this.tasksFile = tasksFile;
    opmSystem = system;    
    reset();
  }
  
  public void reset(){
    try {
    	tasks.clear();
    	creatingTasks.clear();
      lastGeneratedTS = 0;
      lastTaskIndex = 0;
      previousLastTaskIndex = 0;
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      document = builder.parse(tasksFile);
      Node tasksNum = document.getChildNodes().item(0).getAttributes().getNamedItem(XML_TAGS.NUM_OF_TASKS);
      numOfTasks = Integer.parseInt(tasksNum.getNodeValue());
      Node reservedEntitiesNum = document.getChildNodes().item(0).getAttributes().getNamedItem(XML_TAGS.MAX_ENTITY_ID);
      long numOfReservedEntities = Long.parseLong(reservedEntitiesNum.getNodeValue());
      if (numOfReservedEntities > opmSystem.getEntityMaxEntry()){
      	opmSystem.setEntityMaxEntry(numOfReservedEntities);
      }
      
      HashMap<String, SimulationTask> tasksMap = new HashMap<String,SimulationTask>();
      for (int i = 0; i < numOfTasks; i++){
        Node taskNode = document.getElementsByTagName(XML_TAGS.TASK_PREFIX+i).item(0);
        SimulationTask nextTask = getTask(taskNode, tasksMap);
        tasks.add(nextTask);
        tasksMap.put(nextTask.getUID(), nextTask);
      }
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    catch (SAXException ex) {
      ex.printStackTrace();
    }
    catch (ParserConfigurationException ex) {
      ex.printStackTrace();
    }
    catch (FactoryConfigurationError ex) {
      ex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  	
  }

  public String getFileName(){
    return tasksFile.getName();
  }

  private SimulationTask getTask(Node taskNode, HashMap<String, SimulationTask> tasksMap) throws Exception{
    NamedNodeMap attributes = taskNode.getAttributes();
    String taskClassName = attributes.getNamedItem(SimulationTask.XML_TAGS.TASK_JAVA_CLASS).getNodeValue();
    Class taskJavaClass = Class.forName(taskClassName);
    Constructor taskConstructor = taskJavaClass.getConstructor(new Class[]{NamedNodeMap.class, IXSystem.class, HashMap.class});
    Object params[] = new Object[]{attributes, opmSystem, tasksMap};
    SimulationTask task = (SimulationTask)taskConstructor.newInstance(params);
    return task;
  }

  /**
   * getNextTasks
   *
   * @param time int
   * @return List<SimulationTask>
   */
  public List<SimulationTask> getNextTasks(int time) {
    creatingTasks.clear();
    if (lastTaskIndex >= numOfTasks){
      return creatingTasks;
    }

    previousLastTaskIndex = lastTaskIndex;
    lastGeneratedTS += time;

    SimulationTask currTask = tasks.get(lastTaskIndex);
    while (currTask.getStartTime() < lastGeneratedTS){
      creatingTasks.add(currTask);
      lastTaskIndex++;
      if (lastTaskIndex >= numOfTasks){
        break;
      }
      currTask = tasks.get(lastTaskIndex);
    }
    return creatingTasks;
  }

  /**
   * recreateTasks
   *
   * @param newUserTask SimulationTask
   * @return List<SimulationTask>
   */
  public List<SimulationTask> recreateTasks(ExternalInput input, int inputTime, int time2create){
    creatingTasks.clear();
    if (lastTaskIndex >= numOfTasks){
      return creatingTasks;
    }

    lastTaskIndex = previousLastTaskIndex;

    SimulationTask currTask = tasks.get(lastTaskIndex);
    while (currTask.getStartTime() < lastGeneratedTS){
      creatingTasks.add(currTask);
      lastTaskIndex++;
      if (lastTaskIndex >= numOfTasks){
        break;
      }
      currTask = tasks.get(lastTaskIndex);
    }
    return creatingTasks;

  }
}
