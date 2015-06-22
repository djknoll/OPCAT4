package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.*;
import simulation.util.*;
import java.util.*;
import simulation.util.LinkUtils;


public abstract class ConnectionEdgeInfoEntry extends PersistentInfoEntry{
  private LinkLogicalConnection sourceConnection = null;
  private LinkLogicalConnection destinationConnection = null;

  public ConnectionEdgeInfoEntry(IXConnectionEdgeEntry entry, IXSystem system) {
    super(entry, system);
  }

  public synchronized LinkLogicalConnection getLinkLogicalConnection(boolean isSource){
    if (isSource){
      if (sourceConnection == null){
        sourceConnection = LinkUtils.getLinkLogicalConnection(getLinks(isSource), isSource, opmSystem);
      }
      return sourceConnection;
    }else{
      if (destinationConnection == null){
        destinationConnection = LinkUtils.getLinkLogicalConnection(getLinks(isSource), isSource, opmSystem);
      }
      return destinationConnection;
    }
  }

  public synchronized void invalidateLinksCachedInfo(){
    sourceConnection = null;
    destinationConnection = null;
  }

  private List<IXLinkEntry> getLinks(boolean isSource){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    IXConnectionEdgeEntry connectionEdge = (IXConnectionEdgeEntry)opmEntry;
    if (!(connectionEdge instanceof IXProcessEntry)){
      list.addAll(getOwnLinks(connectionEdge, isSource));
      return list;
    }

    IXProcessEntry process = (IXProcessEntry)connectionEdge;
    while (process != null){
      list.addAll(getOwnLinks(process, isSource));
      process = ProcessUtils.getParent(process);
    }

    return list;
  }


  private List<IXLinkEntry> getOwnLinks( IXConnectionEdgeEntry connectionEdge, boolean isSource){
    List<IXLinkEntry> directConnectedLinks = getDirectConnectedRelevantLinks(connectionEdge, isSource);

    if (connectionEdge instanceof IXProcessEntry){
      if (!ProcessUtils.isInzoomed((IXProcessEntry)connectionEdge)){
        return directConnectedLinks;
      }
    }

    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    Iterator<IXLinkEntry> iter = directConnectedLinks.iterator();
    while (iter.hasNext()){
      IXLinkEntry link = iter.next();
      IXProcessEntry process;
      boolean isCheckedProcessSource;
      if (connectionEdge instanceof IXProcessEntry){
        process = (IXProcessEntry)connectionEdge;
        isCheckedProcessSource = isSource;
      }else{
        IXConnectionEdgeEntry sourceEntry = (IXConnectionEdgeEntry)opmSystem.getIXSystemStructure().getIXEntry(link.getSourceId());
        IXConnectionEdgeEntry destEntry = (IXConnectionEdgeEntry)opmSystem.getIXSystemStructure().getIXEntry(link.getDestinationId());
        if (isSource){
          process = (IXProcessEntry)destEntry;
        }else{
          process = (IXProcessEntry)sourceEntry;
        }
        isCheckedProcessSource = !isSource;
      }

      if (!ProcessUtils.isInzoomed(process) || !LinkUtils.hasIdenticalLink(link, opmSystem, isCheckedProcessSource)){
        list.add(link);
      }
    }

    return list;
  }

  protected abstract List<IXLinkEntry> getDirectConnectedRelevantLinks(IXConnectionEdgeEntry connectionEdge, boolean isSource);
/*
  protected List<IXLinkEntry> getDirectConnectedLinks(IXConnectionEdgeEntry connectionEdge, boolean isSource){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    Enumeration links;
    if (isSource){
      links = connectionEdge.getLinksBySource();
    }else{
      links = connectionEdge.getLinksByDestination();
    }

    if (links != null){
      while (links.hasMoreElements()){
        IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
        list.add(currLink);
      }
    }

    return list;
  }
*/
}
