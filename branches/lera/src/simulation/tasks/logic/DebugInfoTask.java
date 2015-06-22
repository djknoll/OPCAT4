package simulation.tasks.logic;

import java.util.*;

import org.jdom.Element;
import org.w3c.dom.*;

import exportedAPI.opcatAPIx.*;
import simulation.reader.*;
import simulation.tasks.SimulationTask;
import simulation.tasks.logic.InstanceDeletionTask.XML_TAGS;
import simulation.util.*;
import simulation.plugin.IDebugInfo;

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
public class DebugInfoTask extends DefaultLogicalTask implements IDebugInfo{
  private ArrayList<DebugInfoEntry> debugEntries;
  private IXEntry failed2RunEntity;

  public DebugInfoTask(int startTime, IXSystem system, IXEntry failed2RunEntity) {
    super(startTime, system, failed2RunEntity, TYPE.DEBUG_INFO, null);
    this.failed2RunEntity = failed2RunEntity;
    debugEntries = new ArrayList<DebugInfoEntry>();
  }

  public DebugInfoTask(NamedNodeMap attributes, IXSystem opmSystem,
                       HashMap previouselyCreatedTasks) {
    super(attributes, opmSystem, previouselyCreatedTasks);
//	deletedInstanceId = Long.parseLong(attributes.getNamedItem(XML_TAGS.DELETED_INSTANCE_ID).getNodeValue());    
  }

  public void fillXML(Element task){
  	super.fillXML(task);
//    task.setAttribute(XML_TAGS.DELETED_INSTANCE_ID, Long.toString(deletedInstanceId));    
  }

  
  public void addDebugEntry(DebugInfoEntry debugEntry){
    debugEntries.add(debugEntry);
  }

  public Iterator<DebugInfoEntry> getDebugInfo(){
    return debugEntries.iterator();
  }

  public void executeBackward(int stepDuration, double executionSpeed,
                              RuntimeInfoTable table) {}

  public void executeForward(int stepDuration, double executionSpeed,
                             RuntimeInfoTable table) {}

  public String toString(){
    String description = MiscUtils.getEntityType(failed2RunEntity)+
        " '"+failed2RunEntity.getName()+"' failed to run (time = "+this.getTime()+
       ") for following reasons :"+
        System.getProperty("line.separator");

    for (int i = 0; i < debugEntries.size(); i++){
      description += "    " + debugEntries.get(i).toString()+System.getProperty("line.separator");
    }
    return description;
  }
}
