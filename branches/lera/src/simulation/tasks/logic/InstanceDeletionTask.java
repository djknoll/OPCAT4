package simulation.tasks.logic;

import java.util.HashMap;

import javax.swing.SwingUtilities;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;

import org.jdom.Element;
import org.w3c.dom.NamedNodeMap;

import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXSystem;
import simulation.reader.RuntimeInfoTable;
import simulation.tasks.LogicalTask;
import simulation.tasks.SimulationTask;
import simulation.tasks.logic.InstanceCreationTask.XML_TAGS;

public class InstanceDeletionTask extends LogicalTask {
	public final static class XML_TAGS{
		public final static String DELETED_INSTANCE_ID = "newInstanceId";
	}
	
	private long deletedInstanceId;
	private AbstractUndoableEdit undoInfo;
	private IXObjectEntry deletedInstanceEntry = null;

	public InstanceDeletionTask(int startTime, IXSystem system, long deletedInstanceId,
			LogicalTask parentTask) {
		super(startTime, system, TYPE.DELETION, parentTask);
		this.deletedInstanceId = deletedInstanceId;
		undoInfo = null;
	}

	public InstanceDeletionTask(NamedNodeMap attributes, IXSystem opmSystem,
			HashMap<String, SimulationTask> previouselyCreatedTasks) {
		super(attributes, opmSystem, previouselyCreatedTasks);
		deletedInstanceId = Long.parseLong(attributes.getNamedItem(XML_TAGS.DELETED_INSTANCE_ID).getNodeValue());	    
	}
	

  public void fillXML(Element task){
  	super.fillXML(task);
    task.setAttribute(XML_TAGS.DELETED_INSTANCE_ID, Long.toString(deletedInstanceId));    
  }

	@Override
	public void executeBackward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
		runAndWaitWithinUIThread(new Runnable(){
			public void run(){
				undoInfo.undo();			}
		});
	}

	@Override
	public void executeForward(int stepDuration, double executionSpeed,
			RuntimeInfoTable table) {
		runAndWaitWithinUIThread(new Runnable(){
			public void run(){
				if (undoInfo == null){
					deletedInstanceEntry = (IXObjectEntry)opmSystem.getIXSystemStructure().getIXEntry(deletedInstanceId);
					undoInfo = opmSystem.deleteEntry(deletedInstanceEntry);
				}else{
					undoInfo.redo();
				}
			}
		});
	}

	public IXEntry getEntity() {
		return deletedInstanceEntry;
	}

}
