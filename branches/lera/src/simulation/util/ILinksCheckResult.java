package simulation.util;

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
public interface ILinksCheckResult {
  public boolean isCondititionSatisfied();
  public String getDataPath();
  public List<IXLinkEntry> getSatistyingLinks();
}
