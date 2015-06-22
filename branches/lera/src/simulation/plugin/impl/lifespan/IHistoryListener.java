package simulation.plugin.impl.lifespan;

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
public interface IHistoryListener{
  public void entryChanged(long id);
  public void timeChanged(int time);
  public void childAdded(long parentId, long childId);
}
