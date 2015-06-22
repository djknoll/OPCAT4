package simulation.creator.creationInfo;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;
import simulation.util.*;
import java.util.*;


public class ProcessInfoEntry extends ConnectionEdgeInfoEntry{
  LevelsRelation levels = null;
  ArrayList runningProcesses = new ArrayList();
  
  public ProcessInfoEntry(IXProcessEntry entry, IXSystem system) {
    super(entry, system);
  }

  public void add2RunningProcesses(String id){
  	if (!isRunning(id)){
  		runningProcesses.add(id);
  	}
  }
  
  public void removeFromRunningProcesses(String id){
  	runningProcesses.remove(id);
  }
  
  public boolean isRunning(String id){
  	return (runningProcesses.indexOf(id) != -1);
  }
  
  public LevelsRelation getLevelsRelation(){
    if (levels == null){
      levels = new LevelsRelation((IXProcessEntry)opmEntry);
    }

    return levels;
  }

  protected List<IXLinkEntry> getDirectConnectedRelevantLinks(IXConnectionEdgeEntry process, boolean isSource){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    Enumeration links;
    if (isSource){
      links = process.getLinksBySource();
    }else{
      links = process.getLinksByDestination();
    }

    if (links != null){
      while (links.hasMoreElements()){
        IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
        if (currLink.getLinkType() != OpcatConstants.CONSUMPTION_EVENT_LINK){
        	list.add(currLink);
        }
      }
    }

    return list;
  }

}
