package extensionTools.Documents.Doc;

/**
 * <P>GenInfo is a class that contains all the information regarding
 * the fields that should be inserted in the general info part. The class is a
 * part of the Info.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */


public class GenInfo {

  public GenInfo() {
  }
  /**
   * Counts the number of fields that contains TRUE.
   */
  private int Counter;//how many fields are selected
  /**
   * Counts the number of fields that contains TRUE.
   */
  private boolean Client;
  private boolean Overview;
  private boolean Current;
  private boolean Goals;
  private boolean Business;
  private boolean Future;
  private boolean Hard;
  private boolean Inputs;
  private boolean Issues;
  private boolean Problems;
  private boolean Users;
  private boolean Oper;

  /**
   * Is at least one of the fields in this part selected.
   * @return TRUE if the part is selected
   */
public boolean isSelected (){
  if (Counter>0)
    return true;
  else
    return false;
}

//Set Functions
/**
 * Sets the value of Client field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setClient(boolean value){
  Client=value;
  if (Client)
    Counter++;
  return;
}
/**
 * Sets the value of Overview field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setOverview(boolean value){
  Overview=value;
  if (Overview)
    Counter++;
  return;
}
/**
 * Sets the value of Goals field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setGoals(boolean value){
  Goals=value;
  if (Goals)
    Counter++;
  return;
}
/**
 * Sets the value of Users field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setUsers(boolean value){
  Users=value;
  if (Users)
    Counter++;
  return;
}
/**
 * Sets the value of Hardware field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setHard(boolean value){
  Hard=value;
  if (Hard)
    Counter++;
  return;
}
/**
 * Sets the value of Inputs field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setInputs(boolean value){
  Inputs=value;
  if (Inputs)
    Counter++;
  return;
}
/**
 * Sets the value of Current state field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setCurrent(boolean value){
  Current=value;
  if (Current)
    Counter++;
  return;
}
/**
 * Sets the value of Business field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setBusiness(boolean value){
  Business=value;
  if (Business)
    Counter++;
  return;
}
/**
 * Sets the value of Future field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setFuture(boolean value){
  Future=value;
  if (Future)
    Counter++;
  return;
}
/**
 * Sets the value of Issues field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setIssues(boolean value){
  Issues=value;
  if (Issues)
    Counter++;
  return;
}
/**
 * Sets the value of Problems field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setProblems(boolean value){
  Problems=value;
  if (Problems)
    Counter++;
  return;
}
/**
 * Sets the value of Oper field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setOper(boolean value){
  Oper=value;
  if (Oper)
    Counter++;
  return;
}
//Get Functions
/**
 * Returns the value of Client field
 * @return value of client field
 */
public boolean getClient(){
  return Client;
}
/**
 * Returns the value of Overview field
 * @return value of Overview field
 */
public boolean getOverview(){
  return Overview;
}
/**
 * Returns the value of Goals field
 * @return value of Goals field
 */
public boolean getGoals(){
  return Goals;
}
/**
 * Returns the value of Users field
 * @return value of Users field
 */
public boolean getUsers(){
  return Users;
}
/**
 * Returns the value of Hard field
 * @return value of Hard field
 */
public boolean getHard(){
  return Hard;
}
/**
 * Returns the value of Inputs field
 * @return value of Inputs field
 */
public boolean getInputs(){
  return Inputs;
}
/**
 * Returns the value of Current field
 * @return value of Current field
 */
public boolean getCurrent(){
  return Current;
}
/**
 * Returns the value of Business field
 * @return value of Business field
 */
public boolean getBusiness(){
  return Business;
}
/**
 * Returns the value of Future field
 * @return value of Future field
 */
public boolean getFuture(){
  return Future;
}
/**
 * Returns the value of Problems field
 * @return value of Problems field
 */
public boolean getProblems(){
  return Problems;
}
/**
 * Returns the value of Oper field
 * @return value of Oper field
 */
public boolean getOper(){
  return Oper;
}
/**
 * Returns the value of Issues field
 * @return value of Issues field
 */
public boolean getIssues(){
  return Issues;
}
/**
 * Puts all the values in the General Info from the array.
 * The order of fields in the array: Client, Overview, Current, Goals,
 * Business, Future, Hard, Inputs, Issues, Problems, Users, Oper
 * @param array of booleans - the values of the fields
 */
public void GIInit(boolean [] array){
  setClient(array[0]);
  setOverview(array[1]);
  setCurrent(array[2]);
  setGoals(array[3]);
  setBusiness(array[4]);
  setFuture(array[5]);
  setHard(array[6]);
  setInputs(array[7]);
  setIssues(array[8]);
  setProblems(array[9]);
  setUsers(array[10]);
  setOper(array[11]);
  }
}
