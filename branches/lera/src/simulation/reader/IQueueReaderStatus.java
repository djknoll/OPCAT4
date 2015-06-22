package simulation.reader;

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
public interface IQueueReaderStatus {
  public boolean isStarted();
  public boolean isPaused();
  public boolean isContinuous();
  public boolean isPlayingForward();
  public boolean isQuickRunMode();
  public int getTimeline();
  public double getPlayingSpeed();
}
