package extensionTools.opl2.alg;
import java.util.Comparator;

import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPI.IProcessEntry;
/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class GeneralComparatorByProcess implements Comparator {

  public GeneralComparatorByProcess() {
  }

  public int compare(Object ent1, Object ent2){
    return compareSystem((IEntry)ent1,(IEntry)ent2);
  }

  public int compareSystem(IEntry ent1, IEntry ent2){

    if((ent1 instanceof IObjectEntry)&&(ent2 instanceof IProcessEntry)){
      //System.err.println("ret val is -1");
      return -1;
    }
    if((ent1 instanceof IProcessEntry)&&(ent2 instanceof IObjectEntry)){
      //System.err.println("ret val is 1");
      return 1;
    }
    return 0;
  }

  public boolean equals(Object obj) {
   if(compare(this,obj)==0)
     return true;
   return false;
  }

}