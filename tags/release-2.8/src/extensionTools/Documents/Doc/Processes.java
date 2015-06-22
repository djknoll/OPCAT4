package extensionTools.Documents.Doc;

/**
 * <P>Processes is a class that contains all the fields describing a process in OPM
 * and information regarding the selection of those fields. The class is a
 * part of the DataDictionary.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */


public class Processes {

  public Processes() {
  }

  /**
   * Counts the number of fields that contains TRUE.
   */
private int Counter = 0;//counts the selected fields
private boolean Desc;
private boolean Essence;
private boolean Origin;
private boolean Scope;
private boolean ActTime;
private boolean Body;

/**
 * Is at least one of the fields in this part selected
 * @return TRUE if the part is selected
*/
 boolean isSelected (){
  if (Counter>0)
    return true;
  else
    return false;
}

//Set Functions
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
 * Sets the value of ActTime field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setActTime(boolean value){
  ActTime=value;
  if (ActTime)
    Counter++;
  return;
}
/**
 * Sets the value of Body field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setBody(boolean value){
  Body=value;
  if (Body)
    Counter++;
  return;
}

//Get Functions
/**
 * Returns the value of Desc field
 * @return value of Desc field
 */
public boolean getDesc(){
  return Desc;
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
 * Returns the value of ActTime field
 * @return value of ActTime field
 */
public boolean getActTime(){
  return ActTime;
}
/**
 * Returns the value of Body field
 * @return value of Body field
 */
public boolean getBody(){
  return Body;
}

/**
 * Puts all the values in the process from the array.
 * The order of fields in the array: Desc, Essence, Origin, Scope, Body, ActTime.
 * @param array of booleans - the values of the fields
 */

public void ProcInit(boolean [] array){
  setDesc(array[0]);
  setEssence(array[1]);
  setOrigin(array[2]);
  setScope(array[3]);
  setBody(array[4]);
  setActTime(array[5]);
 }


}