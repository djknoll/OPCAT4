package simulation.tasks.logic;

import exportedAPI.opcatAPIx.*;
import simulation.util.*;

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
public class DebugInfoEntry {
  public final static class REASON{
    public final static int LINK_NOT_SATISFIED = 0;
    public final static int USER_INPUT_REQUIRED = 1;
    public final static int ALREADY_ACTIVATED = 2;
    public final static int SHOULD_BE_MANUALLY_ACTIVATED = 3;    
  }

  private IXEntry nonsatisfiedEntry;
  private int reason;
  private IXEntry causeOfNonsatisfiedEntry;

  public DebugInfoEntry(IXEntry nonsatisfiedEntry, int reason) {
    this.nonsatisfiedEntry = nonsatisfiedEntry;
    this.reason = reason;
    causeOfNonsatisfiedEntry = null;
  }

  public IXEntry getNonsatisfiedEntry(){
    return nonsatisfiedEntry;
  }

  public void addCauseOfNonsatisfiedEntry(IXEntry causeOfNonsatisfiedEntry){
    this.causeOfNonsatisfiedEntry = causeOfNonsatisfiedEntry;
  }

  public IXEntry getCauseOfNonsatisfiedEntry(){
    return causeOfNonsatisfiedEntry;
  }

  public int getReason(){
    return reason;
  }

  public String toString(){
    String entityType = MiscUtils.getEntityType(nonsatisfiedEntry);

    switch (reason){
      case REASON.USER_INPUT_REQUIRED : {
        return entityType+" "+nonsatisfiedEntry.getName()+ " requires user input.";
      }
      case REASON.LINK_NOT_SATISFIED : {
        String causeType = MiscUtils.getEntityType(causeOfNonsatisfiedEntry);
        return entityType+" is not satisfied because "+causeType + " " +
            causeOfNonsatisfiedEntry.getName()+ " is not active.";
      }
      case REASON.ALREADY_ACTIVATED : {
        String causeType = MiscUtils.getEntityType(causeOfNonsatisfiedEntry);
        return entityType+" is not satisfied because "+causeType + " " +
            causeOfNonsatisfiedEntry.getName()+ " is already activated.";
      }

      case REASON.SHOULD_BE_MANUALLY_ACTIVATED : {
        String causeType = MiscUtils.getEntityType(causeOfNonsatisfiedEntry);
        return entityType+" is not satisfied because "+causeType + " " +
            causeOfNonsatisfiedEntry.getName()+ " should be manually activated.";
      }
      
      default : throw new IllegalStateException("Reason of type "+reason+" is unknown ");
    }
  }
}
