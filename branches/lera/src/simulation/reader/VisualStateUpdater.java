package simulation.reader;

import java.util.Enumeration;
import java.util.Iterator;

import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXProcessEntry;

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


public class VisualStateUpdater {
	RuntimeInfoTable runtimeTable;
	
	public VisualStateUpdater(RuntimeInfoTable runtimeTable){
		this.runtimeTable = runtimeTable;
	}
	
	public void update(){
		Iterator<RuntimeInfoEntry> iter = runtimeTable.iterator();
		while (iter.hasNext()){
			RuntimeInfoEntry currEntry = iter.next();
			IXEntry currEntity = currEntry.getInstance();
			if (currEntity instanceof IXObjectEntry){
				updateObject((IXObjectEntry)currEntity);
				continue;
			}
			
			if (currEntity instanceof IXProcessEntry){
				updateProcess(currEntry);
				continue;
			}
			
			
		}
	}
	
	private boolean isAnimated(IXConnectionEdgeEntry entry){
		Enumeration instances = entry.getInstances();
		while (instances.hasMoreElements()){
			IXConnectionEdgeInstance currInstance = (IXConnectionEdgeInstance)instances.nextElement();
			if (!currInstance.isAnimated()){
				return false;
			}
		}
		
		return true;
	}
	
	private void animate(IXConnectionEdgeEntry entry){
		Enumeration instances = entry.getInstances();
		while (instances.hasMoreElements()){
			IXConnectionEdgeInstance currInstance = (IXConnectionEdgeInstance)instances.nextElement();
			currInstance.animate(0);
		}
	}
	
	private void stopAnimation(IXConnectionEdgeEntry entry){
		Enumeration instances = entry.getInstances();
		while (instances.hasMoreElements()){
			IXConnectionEdgeInstance currInstance = (IXConnectionEdgeInstance)instances.nextElement();
			currInstance.stopTesting(0);
		}
	}
	
	private void updateProcess(RuntimeInfoEntry currEntry){
		if (currEntry.isActivated()){
			animate((IXProcessEntry)currEntry.getInstance());
		}else{
			stopAnimation((IXProcessEntry)currEntry.getInstance());
		}
	}
	
	
	private void updateObject(IXObjectEntry objectEntry){
		if (objectEntry.isInstance()){
			return;
		}
		
		int numOfInstances = objectEntry.getNumberOfInstances();
		if (numOfInstances > 0){
			if (numOfInstances == objectEntry.getNumberOfAnimatedInstances() &&
					isAnimated(objectEntry)){
				return;
			}
			
			objectEntry.setNumberOfAnimatedInstances(numOfInstances);
			animate(objectEntry);
		}else{
			if (objectEntry.getNumberOfAnimatedInstances() == 0 &&
					!isAnimated(objectEntry)){
				return;
			}
			
			objectEntry.setNumberOfAnimatedInstances(0);
			stopAnimation(objectEntry);
		}
	}
}
