package simulation.tasks;

import simulation.Logger;
import exportedAPI.opcatAPIx.*;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;
import java.util.HashMap;
import simulation.reader.*;


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
public abstract class SimulationTask implements Comparable<SimulationTask>{

  public final static class XML_TAGS{
    public final static String TASK_JAVA_CLASS = "javaClass";
    public final static String START_TIME = "startTime";
    public final static String DURATION = "duration";
    public final static String ENTITY_ID = "entityId";
    public final static String IS_EXTERNALLY_ACTIVATED = "isExternallyActivated";
    public final static String DESCRIPTION = "description";
    public final static String UID = "taskUID";
  }

  private int startTime;
  private int duration;
  protected Logger logger = Logger.getInstance();
  private boolean isExternallyActivated;
  private String uid;


  public SimulationTask(int startTime, int duration) {
    this.startTime = startTime;
    this.duration = duration;
    isExternallyActivated = false;
    uid = buildUID();
  }

  public SimulationTask(NamedNodeMap attributes, IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
    startTime = Integer.parseInt(attributes.getNamedItem(XML_TAGS.START_TIME).getNodeValue());
    duration = Integer.parseInt(attributes.getNamedItem(XML_TAGS.DURATION).getNodeValue());
//    long entityId = Long.parseLong(attributes.getNamedItem(XML_TAGS.ENTITY_ID).getNodeValue());
    isExternallyActivated = Boolean.parseBoolean(attributes.getNamedItem(XML_TAGS.IS_EXTERNALLY_ACTIVATED).getNodeValue());
//    opmEntity = opmSystem.getIXSystemStructure().getIXEntry(entityId);
    uid = attributes.getNamedItem(XML_TAGS.UID).getNodeValue();
  }

  public void fillXML(Element task){
    task.setAttribute(XML_TAGS.TASK_JAVA_CLASS, this.getClass().getCanonicalName());
    task.setAttribute(XML_TAGS.START_TIME, Integer.toString(startTime));
    task.setAttribute(XML_TAGS.DURATION, Integer.toString(duration));
//    task.setAttribute(XML_TAGS.ENTITY_ID, Long.toString(opmEntity.getId()));
    task.setAttribute(XML_TAGS.IS_EXTERNALLY_ACTIVATED, Boolean.toString(isExternallyActivated));
    task.setAttribute(XML_TAGS.DESCRIPTION, toString());
    task.setAttribute(XML_TAGS.UID, uid);
  }

  public String getUID(){
    return uid;
  }

  private String buildUID(){
    return /*opmEntity.getId()+"."+*/startTime+"."+duration+"."+(int)(Math.random()*10000);
  }

  public int getStartTime(){
    return startTime;
  }

  public int getDuration(){
    return duration;
  }

  public boolean isExternallyActivated(){
    return isExternallyActivated;
  }

  public void setExternallyActivated(boolean isExternallyActivated){
    this.isExternallyActivated = isExternallyActivated;
  }


  public int compareTo(SimulationTask anotherTask){
    int anotherStartTime = anotherTask.startTime;
    return (startTime<anotherStartTime ? -1 : (startTime == anotherStartTime ? 0 : 1));
  }

  public abstract void executeForward(final int stepDuration, double executionSpeed, RuntimeInfoTable table);
  public abstract void executeBackward(final int stepDuration, double executionSpeed, RuntimeInfoTable table);

}
