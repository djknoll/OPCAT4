package simulation.creator.creationInfo;

import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.*;
import exportedAPI.opcatAPIx.IXLinkEntry;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;


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
public class StateInfoEntry extends ConnectionEdgeInfoEntry{

  public StateInfoEntry(IXStateEntry entry, IXSystem system) {
    super(entry, system);
  }

  protected List<IXLinkEntry> getDirectConnectedRelevantLinks(IXConnectionEdgeEntry state, boolean isSource){
    ArrayList<IXLinkEntry> list = new ArrayList<IXLinkEntry>();
    Enumeration links;
    if (isSource){
      links = state.getLinksBySource();
    }else{
      links = state.getLinksByDestination();
    }

    if (links != null){
      while (links.hasMoreElements()){
        IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
        int type = currLink.getLinkType();
        if (type == OpcatConstants.CONSUMPTION_EVENT_LINK ||
            type == OpcatConstants.INSTRUMENT_EVENT_LINK){
          list.add(currLink);
        }
      }
    }

    return list;
  }


}
