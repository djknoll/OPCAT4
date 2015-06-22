package simulation.tasks.logic;

import java.util.HashMap;

import javax.swing.SwingUtilities;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.UndoInfo;
import simulation.reader.RuntimeInfoTable;
import simulation.tasks.LogicalTask;
import simulation.tasks.SimulationTask;

public class InstanceCreationTask extends LogicalTask {
	public final static class XML_TAGS{
		public final static String NEW_INSTANCE_ID = "newInstanceId";
	}
	
	private UndoInfo undoInfo;
	private IXObjectEntry parentObject;
	private long newInstanceId;
	private IXObjectEntry newInstanceEntry;

	public InstanceCreationTask(int startTime, IXSystem system, IXObjectEntry parentObject,
			long newInstanceId,LogicalTask parentTask) {
		super(startTime, system, TYPE.CREATION, parentTask);
		this.parentObject = parentObject;
		this.newInstanceId = newInstanceId;
		undoInfo = null; 
	}

	public InstanceCreationTask(NamedNodeMap attributes, IXSystem opmSystem,
			HashMap<String, SimulationTask> previouselyCreatedTasks) {
		super(attributes, opmSystem, previouselyCreatedTasks);
		long parentId = Long.parseLong(attributes.getNamedItem(
				LogicalTask.XML_TAGS.PARENT_OBJECT_ID).getNodeValue());
		parentObject = (IXObjectEntry) opmSystem.getIXSystemStructure().getIXEntry(
				parentId);
		newInstanceId = Long.parseLong(attributes.getNamedItem(
				XML_TAGS.NEW_INSTANCE_ID).getNodeValue());	    
	}
	

  public void fillXML(Element task){
  	super.fillXML(task);
    task.setAttribute(LogicalTask.XML_TAGS.PARENT_OBJECT_ID, Long.toString(parentObject.getId()));
    task.setAttribute(XML_TAGS.NEW_INSTANCE_ID, Long.toString(newInstanceId));    
  }
	

	@Override
	public void executeBackward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
		runAndWaitWithinUIThread(new Runnable(){
			public void run(){
				undoInfo.getUndoObject().undo();			}
		});
	}

	@Override
	public void executeForward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
		runAndWaitWithinUIThread(new Runnable(){
			public void run(){
				if (undoInfo == null){
					undoInfo = opmSystem.addInstance4Object(parentObject, newInstanceId, false);
					newInstanceEntry = (IXObjectEntry)undoInfo.getEntry();
				}else{
					undoInfo.getUndoObject().redo();
				}
			}
		});
	}

	public IXEntry getEntity() {
		return newInstanceEntry;
	}

}
