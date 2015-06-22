package extensionTools.Documents.Doc;

/**
 * <P>Relations is a class that contains all the relation types in OPM
 * and information regarding the selection of them. The class is a
 * part of the DataDictionary.
 * @author		Olga Tavrovsky
 * @author		Anna Levit
 */


public class Relations {

  public Relations() {
  }
  /**
   * Counts the number of fields that contains TRUE.
   */
private int Counter = 0;//counts the selected fields
private boolean AgPar;
private boolean FeChar;
private boolean GenSpec;
private boolean ClassInst;
private boolean UniDir;
private boolean BiDir;

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
 * Sets the value of AgPar field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setAgPar(boolean value){
  AgPar=value;
  if (AgPar)
    Counter++;
  return;
}
/**
 * Sets the value of FeChar field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setFeChar(boolean value){
  FeChar=value;
  if (FeChar)
    Counter++;
  return;
}
/**
 * Sets the value of GenSpec field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setGenSpec(boolean value){
  GenSpec=value;
  if (GenSpec)
    Counter++;
  return;
}
/**
 * Sets the value of ClassInst field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setClassInst(boolean value){
  ClassInst=value;
  if (ClassInst)
    Counter++;
  return;
}
/**
 * Sets the value of UniDir field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setUniDir(boolean value){
  UniDir=value;
  if (UniDir)
    Counter++;
  return;
}
/**
 * Sets the value of BiDir field to be 'value'
 * @param value of type boolean - the value of the field
 */
public void setBiDir(boolean value){
  BiDir=value;
  if (BiDir)
    Counter++;
  return;
}

//Get Functions
/**
 * Returns the value of AgPar field
 * @return value of AgPar field
 */
public boolean getAgPar(){
  return AgPar;
}
/**
 * Returns the value of FeChar field
 * @return value of FeChar field
 */
public boolean getFeChar(){
  return FeChar;
}
/**
 * Returns the value of GenSpec field
 * @return value of GenSpec field
 */
public boolean getGenSpec(){
  return GenSpec;
}
/**
 * Returns the value of ClassInst field
 * @return value of ClassInst field
 */
public boolean getClassInst(){
  return ClassInst;
}
/**
 * Returns the value of UniDir field
 * @return value of UniDir field
 */
public boolean getUniDir(){
  return UniDir;
}
/**
 * Returns the value of BiDir field
 * @return value of BiDir field
 */
public boolean getBiDir(){
  return BiDir;
}

/**
 * Puts all the values in the Relation from the array.
 * The order of fields in the array: AgPar, ClassInst, FeChar, GenSpec, BiDir,
 * UniDir.
 * @param array of booleans - the values of the fields
 */
public void RelInit(boolean [] array){
  setAgPar(array[0]);
  setClassInst(array[1]);
  setFeChar(array[2]);
  setGenSpec(array[3]);
  setBiDir(array[4]);
  setUniDir(array[5]);
}

}