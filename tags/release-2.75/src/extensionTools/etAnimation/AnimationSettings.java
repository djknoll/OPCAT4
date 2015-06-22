package extensionTools.etAnimation;


/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class AnimationSettings extends Object {

  public static long STEP_DURATION = 1000;
  public static boolean FIXED_PROCESS_DURATION = true;
  public static int PROCESS_DURATION = 1; // default process duration is 1 step
  public static int PROCESS_DURATION_RANGE_START = 5;
  public static int PROCESS_DURATION_RANGE_END = 15;
  public static boolean FIXED_REACTION_TIME = true;
  public static int REACTION_TIME = 1;
  public static int REACTION_TIME_RANGE_START = 5;
  public static int REACTION_TIME_RANGE_END = 15;
  public static boolean ONE_OBJECT_INSTANCE = true;
  public static int MAX_OBJECT_INSTANCES = 9999;
  public static boolean AUTOMATIC_INITIATION = true;
  public static boolean MOVE_BETWEEN_OPD = true;
  public static boolean STEP_BY_STEP_MODE = true;
  public static boolean RANDOM_STATE_SELECTION = true;
  public static final int ACTIVATION_EVENT = 1;
  public static final int TERMINATION_EVENT = 0;
  public static long LAST_EXECUTION_ID = 0;

  public static void loadDefaultSettings()  {
    STEP_DURATION = 1000;
    FIXED_PROCESS_DURATION = true;
    PROCESS_DURATION = 1; // default process duration is 1 step
    PROCESS_DURATION_RANGE_START = 5;
    PROCESS_DURATION_RANGE_END = 15;
    FIXED_REACTION_TIME = true;
    REACTION_TIME = 0;
    REACTION_TIME_RANGE_START = 0;
    REACTION_TIME_RANGE_END = 5;
    ONE_OBJECT_INSTANCE = true;
    AUTOMATIC_INITIATION = true;
    MOVE_BETWEEN_OPD = true;
    STEP_BY_STEP_MODE = true;
    RANDOM_STATE_SELECTION = true;
  }

  public static void loadSettings(AnimationSettings newSettings)  {
    STEP_DURATION = AnimationSettings.STEP_DURATION;
    FIXED_PROCESS_DURATION = AnimationSettings.FIXED_PROCESS_DURATION;
    PROCESS_DURATION = AnimationSettings.PROCESS_DURATION;
    PROCESS_DURATION_RANGE_START = AnimationSettings.PROCESS_DURATION_RANGE_START;
    PROCESS_DURATION_RANGE_END = AnimationSettings.PROCESS_DURATION_RANGE_END;
    FIXED_REACTION_TIME = AnimationSettings.FIXED_REACTION_TIME;
    REACTION_TIME = AnimationSettings.REACTION_TIME;
    REACTION_TIME_RANGE_START = AnimationSettings.REACTION_TIME_RANGE_START;
    REACTION_TIME_RANGE_END = AnimationSettings.REACTION_TIME_RANGE_END;
    ONE_OBJECT_INSTANCE = AnimationSettings.ONE_OBJECT_INSTANCE;
    AUTOMATIC_INITIATION = AnimationSettings.AUTOMATIC_INITIATION;
    MOVE_BETWEEN_OPD = AnimationSettings.MOVE_BETWEEN_OPD;
    STEP_BY_STEP_MODE = AnimationSettings.STEP_BY_STEP_MODE;
    RANDOM_STATE_SELECTION = AnimationSettings.RANDOM_STATE_SELECTION;
  }
}