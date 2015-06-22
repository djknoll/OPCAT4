package simulation.util;

import exportedAPI.opcatAPIx.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: </p>
 *
 * @Yevgeny Yaroker
 * @version 1.0
 */
public class MiscUtils {
  public MiscUtils() {
  }

  public static String getEntityType(IXEntry entry){
    if (entry instanceof IXProcessEntry){
      return "Process";
    }

    if (entry instanceof IXObjectEntry){
      return "Object";
    }
    if (entry instanceof IXStateEntry){
      return "State";
    }

    if (entry instanceof IXLinkEntry){
      return LinkUtils.getName4LinkType(((IXLinkEntry)entry).getLinkType());
    }

    throw new IllegalArgumentException("Unknown enitity type "+entry);
  }

}
