package simulation.tasks;

import org.w3c.dom.*;
import exportedAPI.opcatAPIx.*;
import simulation.plugin.*;
import org.jdom.Element;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import simulation.reader.*;

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
public abstract class LogicalTask  extends SimulationTask implements ILogicalTask{
  public final static class XML_TAGS{
    public final static String TYPE = "type";
    public final static String PARENT_UID = "parentUID";
    public final static String PARENT_OBJECT_ID = "parentObjectId"; 
  }

  public static final int LOGICAL_TASK_DURATION = 1;
  private int type;
  private LogicalTask parentTask;
  protected IXSystem opmSystem; 

  public LogicalTask(int startTime, IXSystem system, int type, LogicalTask parentTask) {
    super(startTime, LOGICAL_TASK_DURATION);
    opmSystem = system;
    this.type = type;
    this.parentTask = parentTask;
  }

  public LogicalTask(NamedNodeMap attributes, IXSystem opmSystem, HashMap<String, SimulationTask> previouselyCreatedTasks) {
    super(attributes, opmSystem, previouselyCreatedTasks);
    this.opmSystem = opmSystem;
    type = Integer.parseInt(attributes.getNamedItem(XML_TAGS.TYPE).getNodeValue());
    String parentUID = attributes.getNamedItem(XML_TAGS.TYPE).getNodeValue();
    if (parentUID.equals("none")){
      parentTask = null;
    }else{
      parentTask = (LogicalTask)previouselyCreatedTasks.get(parentUID);
    }
  }

  public LogicalTask getParentTask(){
    return parentTask;
  }

  public void fillXML(Element task){
    super.fillXML(task);
    task.setAttribute(XML_TAGS.TYPE, Integer.toString(type));
    String parentUID;
    if (parentTask == null){
      parentUID = "none";
    }else{
      parentUID = parentTask.getUID();
    }
    task.setAttribute(XML_TAGS.PARENT_UID, parentUID);
  }


  public int getType(){
    return type;
  }

  public int getTime(){
    return getStartTime();
  }

  protected void runAndWaitWithinUIThread(Runnable doRun){
	 if (SwingUtilities.isEventDispatchThread()){
		 doRun.run();
	 }else{
		 try {
			SwingUtilities.invokeAndWait(doRun);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
  }

//  public String toString(){
//    return opmEntity.getName() + " "+opmEntity.getId();
//  }
}
