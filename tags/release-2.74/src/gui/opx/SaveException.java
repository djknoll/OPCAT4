package gui.opx;

/**
 * A general exception class for exceptions that occur during OPX save process.
 * @author Eran Toch
 */
public class SaveException extends Exception
{
  public SaveException()
  {
    super();
  }

  public SaveException(String message)
  {
    super(message);
  }

}
