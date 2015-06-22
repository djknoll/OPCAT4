package simulation.plugin;
import java.util.List;
import simulation.reader.IQueueReaderStatus;

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
public interface ISimulationPlugin {
  public String getName();
  public void readerStatusChanged(IQueueReaderStatus status);
  public void destroy();
  public boolean isSynchronious();
}
