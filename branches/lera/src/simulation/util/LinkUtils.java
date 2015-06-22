package simulation.util;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;
import java.util.*;

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
public class LinkUtils {

  public static String getName4LinkType(int linkType){
    switch (linkType) {
      case OpcatConstants.CONSUMPTION_LINK:
        return "Consumption link";
      case OpcatConstants.EFFECT_LINK:
        return "Effect link";
      case OpcatConstants.INSTRUMENT_LINK:
        return "Instrument link";
      case OpcatConstants.CONDITION_LINK:
        return "Condition link";
      case OpcatConstants.AGENT_LINK:
        return "Agent link";
      case OpcatConstants.RESULT_LINK:
        return "Result Link";
      case OpcatConstants.INVOCATION_LINK:
        return "Invocation link";
      case OpcatConstants.INSTRUMENT_EVENT_LINK:
        return "Instrument event link";
      case OpcatConstants.EXCEPTION_LINK:
        return "Exception link";
      case OpcatConstants.CONSUMPTION_EVENT_LINK:
        return "Consumption Event link";
    }

    return "Unknown link type "+linkType;
  }

  public static boolean hasIdenticalLink(IXLinkEntry link, IXSystem system, boolean isInzoomedProcessActsAsSource){
    IXConnectionEdgeEntry sourceEntry = (IXConnectionEdgeEntry)system.getIXSystemStructure().getIXEntry(link.getSourceId());
    IXConnectionEdgeEntry destEntry = (IXConnectionEdgeEntry)system.getIXSystemStructure().getIXEntry(link.getDestinationId());

    IXProcessEntry processEntry;
    IXConnectionEdgeEntry checkedEntry;


    if (isInzoomedProcessActsAsSource){
      processEntry = (IXProcessEntry)sourceEntry;
      checkedEntry = destEntry;
    }else{
      processEntry = (IXProcessEntry)destEntry;
      checkedEntry = sourceEntry;
    }

    if (!ProcessUtils.isInzoomed(processEntry)){
      return false;
    }

    IXProcessInstance processInstance = (IXProcessInstance)processEntry.getZoomedInIXOpd().getMainIXInstance();
    Enumeration children = processInstance.getChildThings();
    while (children.hasMoreElements()){
      Object nextElem = children.nextElement();
      if (!(nextElem instanceof IXProcessInstance)){
        continue;
      }

      IXProcessInstance currInstance = (IXProcessInstance)nextElem;
      IXProcessEntry childEntry = (IXProcessEntry)currInstance.getIXEntry();
      Enumeration childLinks;
      if (isInzoomedProcessActsAsSource){
        childLinks = childEntry.getLinksBySource();
      }else{
        childLinks = childEntry.getLinksByDestination();
      }

      while (childLinks.hasMoreElements()){
        IXLinkEntry childLink = (IXLinkEntry)childLinks.nextElement();
        if (childLink.getLinkType() == link.getLinkType()){
          IXConnectionEdgeEntry childCheckedEntry;
          if (isInzoomedProcessActsAsSource){
            childCheckedEntry = (IXConnectionEdgeEntry) system.getIXSystemStructure().
                getIXEntry(childLink.getDestinationId());
          }else{
            childCheckedEntry = (IXConnectionEdgeEntry) system.getIXSystemStructure().
                getIXEntry(childLink.getSourceId());
          }
          if (areIdenticalAsLinkEnds(checkedEntry, childCheckedEntry, system)){
            return true;
          }
        }
      }
    }

    return false;
  }

  private static boolean areIdenticalAsLinkEnds(IXConnectionEdgeEntry parentEntry, IXConnectionEdgeEntry childEntry, IXSystem system){
    if (parentEntry.getId() == childEntry.getId()){
      return true;
    }

    if (ConnectionEdgeUtils.isAncestor(childEntry, parentEntry, OpcatConstants.SPECIALIZATION_RELATION, system)){
      return true;
    }

    if (childEntry instanceof IXStateEntry){
      IXObjectEntry childObject = ((IXStateEntry)childEntry).getParentIXObjectEntry();
      if (childObject.getId() == parentEntry.getId()){
        return true;
      }

      if (ConnectionEdgeUtils.isAncestor(childObject, parentEntry, OpcatConstants.SPECIALIZATION_RELATION, system)){
        return true;
      }

    }

    if ((parentEntry instanceof IXProcessEntry) && (childEntry instanceof IXProcessEntry)){
      if (ProcessUtils.isChild( (IXProcessEntry) childEntry, (IXProcessEntry) parentEntry)) {
        return true;
      }
    }
    return false;
  }


  public static LinkLogicalConnection getLinkLogicalConnection(List<IXLinkEntry>  links, boolean isSource, IXSystem system) {
    LinkLogicalConnection connection = new LinkLogicalConnection();

    ArrayList<IXLinkEntry> participators = new ArrayList<IXLinkEntry>(links);
    while (participators.size() > 0) {
      IXLinkEntry currLink = participators.get(0);
      if (connection.isLinkInConnection(currLink)){
        continue;
      }

      LinkLogicalConnection orChildConnection = getChildConnection4Link(currLink, participators, isSource, true, system);
      LinkLogicalConnection xorChildConnection = getChildConnection4Link(currLink, participators, isSource, false, system);

      if (orChildConnection.isAtomic() && xorChildConnection.isAtomic()){
        connection.addChildConnection(orChildConnection);
        removeLinkFromList(participators, orChildConnection.getAllParticipatingLinks());
      }else{
        if (!orChildConnection.isAtomic()){
          connection.addChildConnection(orChildConnection);
          removeLinkFromList(participators, orChildConnection.getAllParticipatingLinks());
        }

        if (!xorChildConnection.isAtomic()){
          connection.addChildConnection(xorChildConnection);
          removeLinkFromList(participators, xorChildConnection.getAllParticipatingLinks());
        }

      }
    }

    return connection;
  }

  private static void removeLinkFromList(ArrayList<IXLinkEntry> list,
                                  List<IXLinkEntry> links2Remove){
    for (int i = 0; i < list.size();){
      if (isMemberOfList(list.get(i), links2Remove)){
        list.remove(i);
      }else{
        i++;
      }
    }
  }

  private static List<IXLinkEntry> getOrXorNeighbors(IXLinkEntry link, boolean isSource, boolean isOr,
      List<IXLinkEntry> participators, IXSystem system){
    ArrayList<IXLinkEntry> neighbors = new ArrayList<IXLinkEntry>();
    Enumeration linkInstances = link.getInstances();
    Enumeration graphicalNeigbors;

    while (linkInstances.hasMoreElements()){
      IXLinkInstance currLinkInstance = (IXLinkInstance) linkInstances.nextElement();
      if (isSource) {
        graphicalNeigbors = currLinkInstance.getOrXorSourceNeighbours(!isOr);
      }
      else {
        graphicalNeigbors = currLinkInstance.getOrXorDestinationNeighbours(!isOr);
      }

      if (graphicalNeigbors != null) {
        while (graphicalNeigbors.hasMoreElements()) {
          IXLinkInstance currNeighbor = (IXLinkInstance)graphicalNeigbors.nextElement();
          IXLinkEntry currNeighborEntry = (IXLinkEntry)currNeighbor.getIXEntry();

          if (!isMemberOfList(currNeighborEntry, neighbors) &&
              isMemberOfList(currNeighborEntry, participators)){
            neighbors.add(currNeighborEntry);
          }

        }
      }
    }

    if (isOr || link.getPath() == null || link.getPath().trim().equals("")){
      return neighbors;
    }

    IXConnectionEdgeEntry edge;
    if (isSource){
      edge = (IXConnectionEdgeEntry)system.getIXSystemStructure().getIXEntry(link.getSourceId());
    }else{
      edge = (IXConnectionEdgeEntry)system.getIXSystemStructure().getIXEntry(link.getDestinationId());
    }

    Enumeration logicalLinks;
    if (isSource){
      logicalLinks = edge.getLinksBySource();
    }else{
      logicalLinks = edge.getLinksByDestination();
    }

    ArrayList<String> xoredDataPaths =  new ArrayList<String>();
    xoredDataPaths.add(link.getPath().trim());
    while (logicalLinks.hasMoreElements()){
      IXLinkEntry currLink = (IXLinkEntry)logicalLinks.nextElement();
      if (currLink.getPath() == null || currLink.getPath().trim().equals("")){
        continue;
      }
      if (!xoredDataPaths.contains(currLink.getPath().trim()) &&
          !isMemberOfList(currLink, neighbors)){
        if (!isMemberOfList(currLink, neighbors) &&
            isMemberOfList(currLink, participators)){
          neighbors.add(currLink);
          xoredDataPaths.add(currLink.getPath().trim());
        }
      }
    }

    return neighbors;
  }


  private static LinkLogicalConnection getChildConnection4Link(IXLinkEntry link, List<IXLinkEntry> participators, boolean isSource, boolean isOr, IXSystem system){
    LinkLogicalConnection childConnection = new LinkLogicalConnection();
    if (isOr){
      childConnection.setType(LinkLogicalConnection.CONNECTION_TYPE.OR);
    }else{
      childConnection.setType(LinkLogicalConnection.CONNECTION_TYPE.XOR);
    }

    childConnection.setAtomicEntry(link);


    List<IXLinkEntry> neighbors = getOrXorNeighbors(link, isSource, isOr, participators, system);

    if (neighbors != null){
      Iterator<IXLinkEntry> neighborsIter = neighbors.iterator();
      while (neighborsIter.hasNext()){
        IXLinkEntry currNeighborEntry = neighborsIter.next();

        if (!childConnection.isLinkInConnection(currNeighborEntry)){
          LinkLogicalConnection atomicConnection = new LinkLogicalConnection();
          atomicConnection.setAtomicEntry(currNeighborEntry);
          childConnection.addChildConnection(atomicConnection);
        }
      }
    }


    if (childConnection.getNumOfChilds() > 0){
      LinkLogicalConnection atomicConnection = new LinkLogicalConnection();
      atomicConnection.setAtomicEntry(link);
      childConnection.addChildConnection(atomicConnection);
    }

    return childConnection;
  }

  public static boolean isMemberOfList(IXLinkEntry entry, List<IXLinkEntry> list){
    Iterator<IXLinkEntry> iter = list.iterator();
    while (iter.hasNext()){
      if (iter.next().getId() == entry.getId()){
        return true;
      }
    }

    return false;
  }


}
