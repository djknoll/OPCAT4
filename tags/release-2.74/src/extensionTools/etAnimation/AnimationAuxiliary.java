package extensionTools.etAnimation;

import java.util.Random;
import java.util.Vector;

import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXLinkEntry;

/*
  This class contains various utilities used by other animation classes
 */

class AnimationAuxiliary extends Object {

  private static boolean checkClass(IXEntry entry, String className)  {
    return (entry.getClass().getName().indexOf(className) >= 0);
  }

  public static boolean isObject(IXEntry entry) {
    return checkClass(entry, "Object");
  }

  public static boolean isState(IXEntry entry) {
    return checkClass(entry, "State");
  }

  public static boolean isProcess(IXEntry entry) {
    return checkClass(entry, "Process");
  }

  public static boolean isThing(IXEntry entry) {
    return (isObject(entry) || isProcess(entry));
  }

  public static boolean isLink(IXEntry entry) {
    return checkClass(entry, "Link");
  }

  public static boolean isEvent(IXEntry entry) {
    if (isLink(entry))  {
      int linkType = ((IXLinkEntry)entry).getLinkType();
      return isEvent(linkType);
    }
    return false;
  }

  public static boolean isEvent(int linkType) {
    if ((linkType == exportedAPI.OpcatConstants.AGENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.EXCEPTION_LINK) ||
        (linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.INVOCATION_LINK)) {
      return true;
    }
    return false;
  }

  public static int getRandomNumber(int max)  {
    Random r = new Random();
    return r.nextInt(max);
  }

  // adds all elements of v2 to v1 without duplication
  public static void mergeVectors(Vector v1, Vector v2) {
    v1.removeAll(v2); // remove duplications
    v1.addAll(v2);
  }
  
  public static boolean isConditionLink(IXEntry entry){
  	if(entry.getName().indexOf("ondition")>-1)
  		return true;
  	return false;
  }

  public static void debug(Object x) {
  	//System.out.println(x);
  }
}