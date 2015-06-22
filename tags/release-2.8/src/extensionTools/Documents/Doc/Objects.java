package extensionTools.Documents.Doc;

/**
 * <P>Objects is a class that contains all the fields describing an object in OPM
 * and information regarding the selection of those fields.
 * The class is a part of the DataDictionary.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */


public class Objects {

  public Objects() {
  }
  /**
   * Counts the number of fields that contains TRUE.
   */
private int Counter = 0;
private boolean Type;
private boolean Desc;
private boolean InValue;
private boolean Essence;
private boolean Origin;
private boolean Scope;
private boolean Index;
private boolean States;
private boolean StateDesc;
private boolean StateInitial;
private boolean StateTime;

/**
 * Is at least one of the fields in this part selected
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
 * Sets the value of Type field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setType(boolean value){
  Type=value;
  if (Type)
    Counter++;
  return;
}
/**
 * Sets the value of Desc field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setDesc(boolean value){
  Desc=value;
  if (Desc)
    Counter++;
  return;
}
/**
 * Sets the value of InValue field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setInValue(boolean value){
  InValue=value;
  if (InValue)
    Counter++;
  return;
}
/**
 * Sets the value of Essence field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setEssence(boolean value){
  Essence=value;
  if (Essence)
    Counter++;
  return;
}
/**
 * Sets the value of Origin field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setOrigin(boolean value){
  Origin=value;
  if (Origin)
    Counter++;
  return;
}
/**
 * Sets the value of Scope field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setScope(boolean value){
  Scope=value;
  if (Scope)
    Counter++;
  return;
}
/**
 * Sets the value of Index field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setIndex(boolean value){
  Index=value;
  if (Index)
    Counter++;
  return;
}
/**
 * Sets the value of States field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setStates(boolean value){
  States=value;
  if (States)
    Counter++;
  return;
}
/**
 * Sets the value of StateDesc field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setStateDesc(boolean value){
  StateDesc=value;
  if (StateDesc)
    Counter++;
  return;
}
/**
 * Sets the value of StateInitial field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setStateInitial(boolean value){
  StateInitial=value;
  if (StateInitial)
    Counter++;
  return;
}
/**
 * Sets the value of StateTime field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setStateTime(boolean value){
  StateTime=value;
  if (StateTime)
    Counter++;
  return;
}
//Get Functions
/**
 * Returns the value of Type field
 * @return value of Type field
 */
public boolean getType(){
  return Type;
}
/**
 * Returns the value of Desc field
 * @return value of Desc field
 */
public boolean getDesc(){
  return Desc;
}
/**
 * Returns the value of InValue field
 * @return value of InValue field
 */
public boolean getInValue(){
  return InValue;
}
/**
 * Returns the value of Essence field
 * @return value of Essence field
 */
public boolean getEssence(){
  return Essence;
}
/**
 * Returns the value of Origin field
 * @return value of Origin field
 */
public boolean getOrigin(){
  return Origin;
}
/**
 * Returns the value of Scope field
 * @return value of Scope field
 */
public boolean getScope(){
  return Scope;
}
/**
 * Returns the value of Index field
 * @return value of Index field
 */
public boolean getIndex(){
  return Index;
}
/**
 * Returns the value of States field
 * @return value of States field
 */
public boolean getStates(){
  return States;
}
/**
 * Returns the value of StateDesc field
 * @return value of StateDesc field
 */
public boolean getStateDesc(){
  return StateDesc;
}
/**
 * Returns the value of StateInitial field
 * @return value of StateInitial field
 */
public boolean getStateInitial(){
  return StateInitial;
}
/**
 * Returns the value of StateTime field
 * @return value of StateTime field
 */
public boolean getStateTime(){
  return StateTime;
}

/**
 * Puts all the values in the object from the array
 * The order of fields in the array: Type, Desc, InValue, Essence, Index, Scope,
 * Origin, States, StateDesc, StateInitial, StateTime
 * @param array of booleans - the values of the fields
 */
 public void ObjInit(boolean [] array){
  setType(array[0]);
  setDesc(array[1]);
  setInValue(array[2]);
  setEssence(array[3]);
  setIndex(array[4]);
  setScope(array[5]);
  setOrigin(array[6]);
  setStates(array[7]);
  setStateDesc(array[8]);
  setStateInitial(array[9]);
  setStateTime(array[10]);
}
}