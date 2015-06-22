package extensionTools.opltoopd;


/**
 * Represents a Process sentence
 */
public class ProcessStruct {

  private boolean Environmental;
  private boolean IsPhysical;
  private String MinTimeActivation;
  private String MaxTimeActivation;
  private boolean IsParallel;
  private String ProcessName;
  private String Scope;
  /**
   * If the Process is destination, it is his cardinality
   */
  public String DestinationCardinality;

  public ProcessStruct() { }

  /**
  * Creates a new Process
  * @param environmental is true if Process is environmental
  * @param isPhysical is true if Process is physical
  * @param minTimeActivation the Process min time activation
  * @param maxTimeActivation the Process max time activation
  * @param isParallel is true if Process is parallel
  * @param processName the Process  name
  * @param scope the Process scope
 */
  public ProcessStruct(boolean environmental, boolean isPhysical,
                       String minTimeActivation, String maxTimeActivation,
                       boolean isParallel, String processName,String scope) {

    Environmental = environmental;
    IsPhysical = isPhysical;
    MinTimeActivation = minTimeActivation;
    MaxTimeActivation = maxTimeActivation;
    IsParallel = isParallel;
    ProcessName = processName;
    Scope = scope;

  }

  /**
  * Returns true if the Process is Environmental
   * @return isEnvironmental
  */
  public boolean getEnvironmental(){
   return Environmental;
  }
  /**
   * Returns true if the Process is Physical
   * @return isPhysical
   */
  public boolean getIsPhysical(){
   return IsPhysical;
  }
  /**
   * Returns true if the Process is Parallel
   * @return isParallel
   */
  public boolean getIsParallel(){
    return IsParallel;
  }

  /**
   * Returns the min time activation of the Process
   * @return MinTimeActivation or null
   */
  public String getMinTimeActivation(){
   return MinTimeActivation;
  }

  /**
   * Returns the max time activation of the Process
   * @return MaxTimeActivation or null
   */
  public String getMaxTimeActivation(){
   return MaxTimeActivation;
  }

  /**
   * Returns the Process name
   * @return Process name
   */
  public String getProcessName(){
   return ProcessName;
  }

  /**
   * Returns the scope of the Process
   * @return scope or null
   */
  public String getScope(){
   return Scope;
  }

/**
 * Sets the environmental parameter of the Process
 * @param environmental
 */
  public void setEnvironmental(boolean environmental){
    Environmental = environmental;
  }

  /**
   * Sets the physical parameter of the Process
   * @param isPhysical
   */
  public void setIsPhysical(boolean isPhysical){
    IsPhysical = isPhysical;
  }

  /**
   * Sets the parallell parameter of the Process
   * @param isParallel
   */
  public void setIsParallel(boolean isParallel){
    IsParallel = isParallel;
  }

  /**
   * Sets the min time activation of the Process
   * @param minTimeActivation the min time activation of the Process
   *                     or null if there is no min time activation
   */
  public void setMinTimeActivation(String minTimeActivation){
    MinTimeActivation = minTimeActivation;
  }

  /**
   * Sets the max time activation of the Process
   * @param maxTimeActivation the max time activation of the Process
   *                     or null if there is no max time activation
   */
  public void setMaxTimeActivation(String maxTimeActivation){
    MaxTimeActivation = maxTimeActivation;
  }

  /**
   * Sets the Process name
   * @param processName the name of the Process
   */
  public void setProcessName(String processName){
    ProcessName = processName;
  }

  /**
   * Sets the Process scope
   * @param scope the scope of the Process or null if there is no scope
   */
  public void setScope(String scope){
    Scope = scope;
  }


}