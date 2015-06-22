package simulation.util;
import exportedAPI.opcatAPIx.*;
import java.util.*;
import simulation.Logger;

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
public class ConnectionEdgeUtils {
  public ConnectionEdgeUtils() {
    super();
  }


/*
  public static Iterator<Long> getConnectedElementsID(IXConnectionEdgeEntry connectionEdge, int linkType, boolean isSource){
    ArrayList<Long> connectedElements = new ArrayList<Long>(4);

    Enumeration allLinks;

    if (isSource){
      allLinks = connectionEdge.getLinksBySource();
    }else{
      allLinks = connectionEdge.getLinksByDestination();
    }

    if (allLinks != null){
      while (allLinks.hasMoreElements()){
        IXLinkEntry currLink = (IXLinkEntry)allLinks.nextElement();
        if (currLink.getLinkType() == linkType){
          if (isSource){
            connectedElements.add(currLink.getDestinationId());
          }else{
            connectedElements.add(currLink.getSourceId());
          }
        }
      }
    }

    return connectedElements.iterator();

  }
*/

  public static boolean isAncestor(IXConnectionEdgeEntry checkedEntity, IXConnectionEdgeEntry possibleAncestor, int relationType, IXSystem system){
    Enumeration links = checkedEntity.getRelationByDestination();
    while (links.hasMoreElements()){
      IXRelationEntry currRelation = (IXRelationEntry)links.nextElement();
      if (currRelation.getRelationType() != relationType){
        continue;
      }

      IXConnectionEdgeEntry parent = (IXConnectionEdgeEntry)system.getIXSystemStructure().getIXEntry(currRelation.getSourceId());
      if (parent.getId() == possibleAncestor.getId()){
        return true;
      }

      if (isAncestor(parent, possibleAncestor, relationType, system)){
        return true;
      }
    }

    return false;
  }

}
