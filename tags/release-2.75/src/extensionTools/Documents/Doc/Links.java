package extensionTools.Documents.Doc;

/**
 * <P>Links is a class that contains all the link types, fields
 * describing a link in OPM and information regarding the selection of them.
 * The class is a part of the DataDictionary.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */

public class Links {

  public Links() {
  }
  /**
   * Counts the number of fields that contains TRUE.
   */
private int Counter = 0;
private boolean Agent;
private boolean Instrument;
private boolean ResCons;
private boolean Effect;
private boolean Event;
private boolean InstEvent;
private boolean Condition;
private boolean Exception;
private boolean Invocation;
private boolean Cond;
private boolean Path;
private boolean ReactTime;
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
 * Sets the value of Agent field to be 'value'
 * @param value of type boolean - the value of the field
 */

public void setAgent(boolean value){
  Agent=value;
  if (Agent)
    Counter++;
  return;
}
/**
 * Sets the value of Instrument field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setInstrument(boolean value){
  Instrument=value;
  if (Instrument)
    Counter++;
  return;
}
/**
 * Sets the value of ResCons field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setResCons(boolean value){
  ResCons=value;
  if (ResCons)
    Counter++;
  return;
}
/**
 * Sets the value of Effect field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setEffect(boolean value){
  Effect=value;
  if (Effect)
    Counter++;
  return;
}
/**
 * Sets the value of Event field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setEvent(boolean value){
  Event=value;
  if (Event)
    Counter++;
  return;
}
/**
 * Sets the value of InstEvent field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setInstEvent(boolean value){
  InstEvent=value;
  if (InstEvent)
    Counter++;
  return;
}
/**
 * Sets the value of Condition field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setCondition(boolean value){
  Condition=value;
  if (Condition)
    Counter++;
  return;
}
/**
 * Sets the value of Exception field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setException(boolean value){
  Exception=value;
  if (Exception)
    Counter++;
  return;
}
/**
 * Sets the value of Invocation field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setInvocation(boolean value){
  Invocation=value;
  if (Invocation)
    Counter++;
  return;
}
/**
 * Sets the value of Cond field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setCond(boolean value){
  Cond=value;
  if (Cond)
    Counter++;
  return;
}
/**
 * Sets the value of Path field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setPath(boolean value){
  Path=value;
  if (Path)
    Counter++;
  return;
}
/**
 * Sets the value of ReactTime field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setReactTime(boolean value){
  ReactTime=value;
  if (ReactTime)
    Counter++;
  return;
}


//Get Functions
/**
 * Returns the value of Agent field
 * @return value of Agent field
 */
public boolean getAgent(){
  return Agent;
}
/**
 * Returns the value of Instrument field
 * @return value of Instrument field
 */
public boolean getInstrument(){
  return Instrument;
}
/**
 * Returns the value of ResCons field
 * @return value of ResCons field
 */
public boolean getResCons(){
  return ResCons;
}
/**
 * Returns the value of Effect field
 * @return value of Effect field
 */
public boolean getEffect(){
  return Effect;
}
/**
 * Returns the value of Event field
 * @return value of Event field
 */
public boolean getEvent(){
  return Event;
}
/**
 * Returns the value of InstEvent field
 * @return value of InstEvent field
 */
public boolean getInstEvent(){
  return InstEvent;
}
/**
 * Returns the value of Condition field
 * @return value of Condition field
 */
public boolean getCondition(){
  return Condition;
}
/**
 * Returns the value of Exception field
 * @return value of Exception field
 */
public boolean getException(){
  return Exception;
}
/**
 * Returns the value of Invocation field
 * @return value of Invocation field
 */
public boolean getInvocation(){
  return Invocation;
}
/**
 * Returns the value of Cond field
 * @return value of Cond field
 */
public boolean getCond(){
  return Cond;
}
/**
 * Returns the value of Path field
 * @return value of Path field
 */
public boolean getPath(){
  return Path;
}
/**
 * Returns the value of ReactTime field
 * @return value of ReactTime field
 */
public boolean getReactTime(){
  return ReactTime;
}
/**
 * Puts all the values in the Link from the array.
 * The order of fields in the array: Agent, Condition, Effect, Event, Exception,
 * InstEvent, Invocation, ResCons, Instrument, ResCons, Instrument, Cond, Path,
 * ReactTime.
 * @param array of booleans - the values of the fields
 */

public void LinkInit(boolean [] array){
  setAgent(array[0]);
  setCondition(array[1]);
  setEffect(array[2]);
  setEvent(array[3]);
  setException(array[4]);
  setInstEvent(array[5]);
  setInvocation(array[6]);
  setResCons(array[7]);
  setInstrument(array[8]);
  setCond(array[9]);
  setPath(array[10]);
  setReactTime(array[11]);
}

}
