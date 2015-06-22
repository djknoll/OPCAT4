package simulation.plugin.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import simulation.SimulationConfig;
import simulation.plugin.*;
import exportedAPI.OpdLayout;
import exportedAPI.opcatAPIx.*;
import simulation.util.*;
import javax.swing.*;

import simulation.reader.IQueueReaderStatus;

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
public class AdvancedOpdMover implements IObservingPlugin {
  private IXSystem opmSystem;
  private OpdLayout layout;
  private final static int PARENT_ROW = 0;  
  private final static int CHILD_ROW = 1;
  private Hashtable<Long, OpdInfo> opdInfoTable;
  private SimulationConfig config = SimulationConfig.getInstance(); 
  private int layoutType;
  
  public AdvancedOpdMover(IXSystem opmSystem){
    this.opmSystem = opmSystem;
    layout = new OpdLayout();
    opdInfoTable = new Hashtable<Long, OpdInfo>();
    layoutType = config.moveBetweenOPDs;
  }
  /**
   * getName
   *
   * @return String
   * @todo Implement this simulation.plugin.ISimulationPlugin method
   */
  public String getName() {
    return "OpdMover";
  }
  
  public void setLayoutType(int layoutType){
	  if (this.layoutType != layoutType){
		  this.layoutType = layoutType;
		  arrangeOpds();
	  }
  }

  public void init(IControlCommandHandler commandHandler){
	  opdInfoTable.clear();
	  OpdInfo rootInfo = new OpdInfo(opmSystem.getIXSystemStructure().getIXOpd(1), 1);
	  rootInfo.isActivated = true;
	  opdInfoTable.put(rootInfo.opd.getOpdId(), rootInfo);
	  arrangeOpds();
  }
  
  public void readerStatusChanged(IQueueReaderStatus status){}
  public void destroy(){}
  public boolean isSynchronious(){
  	return true;
  }

  private List<OpdInfo> getDeepestActivatedOpds(){
	ArrayList<OpdInfo> deepestOpds = new ArrayList<OpdInfo>();
	int currentDepth = 0;
	Iterator<OpdInfo> iter = opdInfoTable.values().iterator();
	while (iter.hasNext()){
		OpdInfo currInfo = iter.next();
		if (currInfo.depth < currentDepth || !currInfo.isActivated){
			continue;
		}

		if (currInfo.depth > currentDepth){
			deepestOpds.clear();
		}
		
		currentDepth = currInfo.depth;
		deepestOpds.add(currInfo);
	}
	
	return deepestOpds;
  }
  
  private List<OpdInfo> getParents(List<OpdInfo> children){
	  ArrayList<OpdInfo> parents = new ArrayList<OpdInfo>();
	  Iterator<OpdInfo> childIter = children.iterator();
	  while (childIter.hasNext()){
		  IXOpd parentOpd = childIter.next().opd.getParentIXOpd();
		  if (parentOpd == null){
			  continue;
		  }
		  
		  OpdInfo parentInfo = opdInfoTable.get(parentOpd.getOpdId());
		  if (!(parents.contains(parentInfo))){
			  parents.add(parentInfo);
		  }
	  }
	  return parents; 
  }
  
  private void arrangeOpds(){
	  if (config.moveBetweenOPDs == SimulationConfig.AUTO_OPD_MOVE_TYPE.DONT_MOVE){
		  return;
	  }
	  
	  final List<OpdInfo> children = getDeepestActivatedOpds();
	  if (layoutType == SimulationConfig.AUTO_OPD_MOVE_TYPE.SINGLE_OPD_MOVE){
		  SwingUtilities.invokeLater(new Runnable() {
			  public void run() {
	            opmSystem.showOPD(children.get(0).opd.getOpdId());
	          }
	        });
		  
		  return;
	  }
	  
	  List<OpdInfo> parents = getParents(children);
	  layout.clear();
	  Iterator<OpdInfo> parentIter = parents.iterator();
	  while (parentIter.hasNext()){
		  layout.addOpd(parentIter.next().opd.getOpdId(), PARENT_ROW);
	  }
	  
	  Iterator<OpdInfo> childtIter = children.iterator();
	  while (childtIter.hasNext()){
		  layout.addOpd(childtIter.next().opd.getOpdId(), CHILD_ROW);
	  }
	  
      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            opmSystem.arrangeOPDs(layout);
          }
        });
  }

  private void handleTask(ILogicalTask task, boolean isForwardExecuted){
	  if (!(task.getEntity() instanceof IXProcessEntry)){
		  return;
	  }

	  final IXProcessEntry process = (IXProcessEntry)task.getEntity();
	  if (!ProcessUtils.isInzoomed(process)){
		  return;
	  }

	  IXOpd processOpd = opmSystem.getIXSystemStructure().getIXOpd(
			  process.getZoomedInIXOpd().getOpdId());
    
	  OpdInfo processOpdInfo = opdInfoTable.get(processOpd.getOpdId());
	  if (processOpdInfo == null){
		  OpdInfo parentInfo = opdInfoTable.get(processOpd.getParentIXOpd().getOpdId());
		  processOpdInfo = new OpdInfo(processOpd, parentInfo.depth + 1);
		  opdInfoTable.put(processOpdInfo.opd.getOpdId(), processOpdInfo);
	  }
    
	  if (task.getType() == ILogicalTask.TYPE.ACTIVATION){
		  processOpdInfo.isActivated = isForwardExecuted;
	  }
    
	  if (task.getType() == ILogicalTask.TYPE.TERMINATION){
		  processOpdInfo.isActivated = !isForwardExecuted;
	  }
    
	  arrangeOpds();
  }
  
  /**
   * processTaskForward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskForward(ILogicalTask task) {
	  handleTask(task, true);
  }


  /**
   * processTaskBackward
   *
   * @param task ILogicalTask
   * @todo Implement this simulation.plugin.IObservingPlugin method
   */
  public void processTaskBackward(ILogicalTask task) {
	  handleTask(task, false);
  }
  
  public class OpdInfo{
	  public int depth;
	  public IXOpd opd;
	  public boolean isActivated;
	  
	  public OpdInfo(IXOpd opd, int depth){
		  this.opd = opd;
		  this.depth = depth;
	  }
	  
	  public boolean equals(Object obj){
		  if (!(obj instanceof OpdInfo))
		  {
			  return false;
		  }
		  
		  return (opd.getOpdId() == ((OpdInfo)obj).opd.getOpdId());
	  }
  }
}
