package simulation.tasks.logic;

import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.UndoInfo;
import simulation.reader.RuntimeInfoTable;
import simulation.tasks.LogicalTask;
import simulation.tasks.SimulationTask;
import simulation.util.ObjectUtils;

public class StateActivationTask extends LogicalTask {
	public final static class XML_TAGS{
//		public final static String NEW_INSTANCE_ID = "newInstanceId";
	}
	
	private IXObjectEntry ownerInstance;
	private IXStateEntry state = null;
	private String stateName;
	private long ownerInstanceId;

	public StateActivationTask(int startTime, IXSystem system, long ownerInstanceId, String name, LogicalTask parentTask) {
		super(startTime, system, TYPE.ACTIVATION, parentTask);
		this.ownerInstanceId = ownerInstanceId;
		stateName = name;
	}

	public StateActivationTask(NamedNodeMap attributes, IXSystem opmSystem,
			HashMap<String, SimulationTask> previouselyCreatedTasks) {
		super(attributes, opmSystem, previouselyCreatedTasks);
		long ownerInstanceId = Long.parseLong(attributes.getNamedItem(
				LogicalTask.XML_TAGS.PARENT_OBJECT_ID).getNodeValue());
	}
	

  public void fillXML(Element task){
  	super.fillXML(task);
    task.setAttribute(LogicalTask.XML_TAGS.PARENT_OBJECT_ID, Long.toString(ownerInstanceId));
  }
	

	@Override
	public void executeBackward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
	}

	@Override
	public void executeForward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
		if (state == null){
			ownerInstance = (IXObjectEntry)opmSystem.getIXSystemStructure().getIXEntry(ownerInstanceId);
			state = ObjectUtils.getState(ownerInstance, stateName);
		}
	}

	public IXEntry getEntity() {
		return state;
	}

}
