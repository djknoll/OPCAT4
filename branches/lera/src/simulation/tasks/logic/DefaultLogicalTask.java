package simulation.tasks.logic;

import java.util.HashMap;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXSystem;
import simulation.plugin.ILogicalTask.TYPE;
import simulation.reader.RuntimeInfoEntry;
import simulation.reader.RuntimeInfoTable;
import simulation.tasks.LogicalTask;
import simulation.tasks.SimulationTask;
import simulation.tasks.AnimationTask.XML_TAGS;

public class DefaultLogicalTask extends LogicalTask {
	private IXEntry opmEntity;
	
	public DefaultLogicalTask(int startTime, IXSystem system, IXEntry opmEntity, int type,
			LogicalTask parentTask) {
		super(startTime, system, type, parentTask);
		this.opmEntity = opmEntity;
	}

  public IXEntry getEntity(){
    return opmEntity;
  }
  
	public DefaultLogicalTask(NamedNodeMap attributes, IXSystem opmSystem,
			HashMap<String, SimulationTask> previouselyCreatedTasks) {
		super(attributes, opmSystem, previouselyCreatedTasks);
    long entityId = Long.parseLong(attributes.getNamedItem(SimulationTask.XML_TAGS.ENTITY_ID).getNodeValue());
    opmEntity = opmSystem.getIXSystemStructure().getIXEntry(entityId);
	}

  public void fillXML(Element task){
  	super.fillXML(task);
    task.setAttribute(SimulationTask.XML_TAGS.ENTITY_ID, Long.toString(opmEntity.getId()));
  }

  /**
   * executeBackward
   *
   * @param stepDuration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  public void executeBackward(int stepDuration, double executionSpeed, RuntimeInfoTable table) {
    RuntimeInfoEntry entry = table.getInfoEntry(opmEntity.getId());
    switch (getType()){
      case TYPE.ACTIVATION:{
        entry.setActivated(false);
        break;
      }
      case TYPE.TERMINATION:{
        entry.setActivated(true);
        break;
      }
    }
  }

  /**
   * executeForward
   *
   * @param stepDuration int
   * @todo Implement this simulation.tasks.SimulationTask method
   */
  public void executeForward(int stepDuration, double executionSpeed, RuntimeInfoTable table) {
    RuntimeInfoEntry entry = table.getInfoEntry(opmEntity.getId());
    switch (getType()){
      case TYPE.ACTIVATION:{
        entry.setActivated(true);
        break;
      }
      case TYPE.TERMINATION:{
        entry.setActivated(false);
        break;
      }
    }
  }
  
	
}
