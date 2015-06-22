package simulation.util;

import exportedAPI.opcatAPIx.IXLinkEntry;

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
public interface ILinkCondition {
  public boolean isConditionSatisfied(IXLinkEntry link);
}
