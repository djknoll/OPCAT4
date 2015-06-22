package extensionTools.opl2.alg;

import java.util.Comparator;

/**
 * <p>Title: Extension Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author OPCAT team
 * @version 1.0
 */

public class OPLGeneralComparator implements Comparator {

  public OPLGeneralComparator() {
  }
  public int compare(Object o1, Object o2) {
    /**@todo Implement this java.util.Comparator method*/
    throw new java.lang.UnsupportedOperationException("Method compare() not yet implemented.");
  }
  public boolean equals(Object obj) {
     if(this.compare(this,obj)==0) {
		return true;
	}
     return false;
  }
}