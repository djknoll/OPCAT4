package simulation.plugin;

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
 * @author Yevgeny Yaroker
 * @version 1.0
 */
public interface ILogicalTask {
  public static final class TYPE {
    public final static int ACTIVATION = 0;
    public final static int TERMINATION = 1;
    public final static int CREATION = 2;
    public final static int DELETION = 3;
    public final static int INVOCATION = 4;
    public final static int DEBUG_INFO = 5;
  }

  public int getTime();
  public int getType();
  public IXEntry getEntity();
  public ILogicalTask getParentTask();
}
