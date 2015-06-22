package simulation.util;
import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPIx.*;
import java.util.*;

/**
 * <p>Title: </p>
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
public class ProcessUtils {
  public static boolean isInzoomed(IXProcessEntry process){
    return (process.getZoomedInIXOpd() != null);
  }

  public static boolean isChild(IXThingEntry checkedThing, IXProcessEntry possibleParent){
    IXOpd opd = possibleParent.getZoomedInIXOpd();
    if (opd == null){
      return false;
    }

    Enumeration children = opd.getMainIXInstance().getChildThings();
    while (children.hasMoreElements()){
      IXThingInstance currThing = (IXThingInstance)children.nextElement();
      if (currThing.getLogicalId() == checkedThing.getId()){
        return true;
      }
    }
    return false;
  }

  public static IXProcessEntry getParent(IXThingEntry checkedThing){
    Enumeration instances = checkedThing.getInstances();
    while (instances.hasMoreElements()){
      IXThingInstance currInstance = (IXThingInstance)instances.nextElement();
      IXThingInstance parentInstance = currInstance.getParentIXThingInstance();
      if ((parentInstance != null) && (parentInstance instanceof IXProcessInstance)){
        IXProcessInstance parent = (IXProcessInstance)parentInstance;
        return (IXProcessEntry)parent.getIXEntry();
      }
    }
    return null;
  }
  
  public static boolean isObjectAffectedByProcess(IXProcessEntry process, IXObjectEntry object, IXSystem system){
  	return (isObjectResultedByProcess(process, object, system) &&
  			isObjectConsumedByProcess(process, object, system));
  }
  
  public static boolean isObjectResultedByProcess(IXProcessEntry process, IXObjectEntry object, IXSystem system){
  	Enumeration links = process.getLinksBySource();
  	while (links.hasMoreElements()){
  		IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
  		if (currLink.getLinkType() == OpcatConstants.RESULT_LINK){
  			IXEntry destination = system.getIXSystemStructure().getIXEntry(currLink.getDestinationId());
  			if (destination instanceof IXObjectEntry){
  				if (destination.getId() == object.getId()){
  					return true;
  				}
  			}
  			
  			if (destination instanceof IXStateEntry){
  				if (((IXStateEntry)destination).getParentIXObjectEntry().getId() == object.getId()){
  					return true;
  				}
  			}
  		}
  	}
  	
  	return false;
  }
  
  public static boolean isObjectConsumedByProcess(IXProcessEntry process, IXObjectEntry object, IXSystem system){
  	Enumeration links = process.getLinksByDestination();
  	while (links.hasMoreElements()){
  		IXLinkEntry currLink = (IXLinkEntry)links.nextElement();
  		if (currLink.getLinkType() == OpcatConstants.CONSUMPTION_LINK ||
  				currLink.getLinkType() == OpcatConstants.CONSUMPTION_EVENT_LINK){
  			IXEntry source = system.getIXSystemStructure().getIXEntry(currLink.getSourceId());
  			if (source instanceof IXObjectEntry){
  				if (source.getId() == object.getId()){
  					return true;
  				}
  			}
  			
  			if (source instanceof IXStateEntry){
  				if (((IXStateEntry)source).getParentIXObjectEntry().getId() == object.getId()){
  					return true;
  				}
  			}
  		}
  	}
  	
  	return false;
  }
  
  public static int getMaxTimeoutInMSec(IXProcessEntry process){
  	return getTimeoutInMSec(process.getMaxTimeActivation());
  }
  
  private static int getTimeoutInMSec(String timeout){
  	StringTokenizer tokenizer = new StringTokenizer(timeout, ";");
  	int numOfTokens = tokenizer.countTokens();
  	if ( numOfTokens != 7){
  		throw new IllegalArgumentException("The timeout string is not well formed");
  	}
  	
  	int[] args = new int[7];
  	for (int i = 0; i < 7; i++){
  		args[i] = Integer.parseInt(tokenizer.nextToken()); 
  	}
  	
  	return args[0] + args[1] * 1000 + args[2] * (1000 * 60) + args[3] * (1000 * 60 * 60) +
  		args[4] * (1000 * 60 * 60 * 24) + args[5] * (1000 * 60 * 60 * 24 * 30) +
  		args[6] * (1000 * 60 * 60 * 24 * 30 * 12);
//  	msec, sec, min, hours, days, months, years?
  }
  
}
