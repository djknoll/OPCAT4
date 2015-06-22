package simulation;

import simulation.creator.CreationConfig;

/**
 * <p>Title: Simulation Module</p>
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
public class SimulationConfig {
  private static SimulationConfig instance = new SimulationConfig();
  private CreationConfig creationConfig;
  public static final class DEFAULTS{
    public static final int ATOMICS_IN_STEP = 4;
    public static final int ATOMIC_STEP_DURATION = 250;
    public static boolean SHOW_LIFESPAN = true;
    public static int MOVE_BETWEEN_OPDS = AUTO_OPD_MOVE_TYPE.SINGLE_OPD_MOVE; 
  }
  
  public final static class AUTO_OPD_MOVE_TYPE{
  	public final static int DONT_MOVE = 0;
  	public final static int SINGLE_OPD_MOVE = 1;
  	public final static int PARALLEL_OPD_MOVE = 2;    	
  }


  public int atomicsInStep;
  public int atomicStepDuration;
  public int moveBetweenOPDs;
  public boolean showLifespan;

  private SimulationConfig() {
    restoreDefaults();
    creationConfig = new CreationConfig();
  }

  public void restoreDefaults(){
	atomicsInStep = DEFAULTS.ATOMICS_IN_STEP;
    atomicStepDuration = DEFAULTS.ATOMIC_STEP_DURATION;
    moveBetweenOPDs = AUTO_OPD_MOVE_TYPE.SINGLE_OPD_MOVE;
    showLifespan = DEFAULTS.SHOW_LIFESPAN;
  }
  
  public CreationConfig getCreationConfig(){
	  return creationConfig;
  }

  public static SimulationConfig getInstance(){
    return instance;
  }
}
