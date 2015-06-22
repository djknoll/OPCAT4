package simulation;

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
public class Logger {
  private static Logger instance = new Logger();
  private int currVerbosityLevel = 0;
  private Logger() {
  }

  public static Logger getInstance(){
    return instance;
  }

  public void print(String message, int verbosityLevel){
    if (verbosityLevel < currVerbosityLevel){
      System.out.println(message);
    }
  }

  public void setVerbosity(int verbosity){
    currVerbosityLevel = verbosity;
  }


}
