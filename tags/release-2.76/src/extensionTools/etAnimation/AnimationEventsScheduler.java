package extensionTools.etAnimation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/*
  AnimationEventsScheduler class is a table of events scheduled for each step.
  It also contains various function to add, remove and search for a specific event
 */

class AnimationEventsScheduler extends Hashtable {
  // This hashtable holds scheduled animation events
  // Key is a step
  // Value is a list of events scheduled to this step
  public AnimationEventsScheduler() {
  }

  public void scheduleEvent(AnimationEvent aEvent)  {
    AnimationAuxiliary.debug(this,"scheduleEvent getSchedulingStep = " + aEvent.getSchedulingStep());
    Long key = new Long(aEvent.getStep());
    Vector value = null;
    if (this.containsKey(key)) {
      value = (Vector) this.get(key);
    }
    else  {
      value = new Vector();
    }
    value.add(aEvent);    // append to the end - FIFO
    this.put(key, value);
  }

  public void scheduleEvent(long currentStep, long eventStep)  {
    AnimationEvent aEvent = new AnimationEvent();
    aEvent.setStep(eventStep);
    aEvent.setSchedulingStep(currentStep);
    aEvent.setExecutionId();
    this.scheduleEvent(aEvent);
  }

  public Enumeration getScheduledEvents(long step) {
    Long key = new Long(step);
    if (this.containsKey(key))  {
      Vector value = (Vector) this.get(key);
      return value.elements();  // check that event is not handled and mark as handled
    }
    return (new Vector()).elements();   // empty enumeration
  }

  public void removeScheduledEvent(long step)  {
    Long key = new Long(step);
    Vector value = null;
    if (this.containsKey(key)) {
      value = (Vector) this.get(key);
      value.remove(value.firstElement());   // remove first - FIFO
      // if there are no more events scheduled to this step - remove the key
      // from the hash table
      if (value.isEmpty())  {
        this.remove(key);
      }
    }
  }

  // removes all events scheduled after the given step
  // used for undo with execution id = 0
  // used aslo to remove events scheduled for certain execution
  public void removeAllScheduledEvents(long schedulingStep, long executionId)  {
    AnimationAuxiliary.debug(this,"Starting removeAllScheduledEvents");
    Enumeration en = this.elements();
    for(; en.hasMoreElements();) {
      Vector v = (Vector)en.nextElement();
      Long key = null;
      // go through the vector in backward order and remove the events
      // that were scheduled later than schedulingStep
      for(int i=v.size()-1; i>=0;i--) {
        AnimationEvent aEvent = (AnimationEvent)v.get(i);
        key = new Long(aEvent.getStep());
        // remove the events that were scheduled later than schedulingStep
        if (aEvent.getSchedulingStep() > schedulingStep)  {
          if ((executionId == 0)  || executionId == aEvent.getExecutionId())  {
            AnimationAuxiliary.debug(this,"remove event for step "  + aEvent.getStep());
            AnimationEvent aRemovedEvent = (AnimationEvent)v.remove(i);
            // prevent rescheduling of the removed event if it already
            // was taken by the nextStep function
            if (aRemovedEvent.getRecurringInd())  {
              aRemovedEvent.setRecurringInd(false);
            }
          }
        }

        // restore the events that were cancelled later than schedulingStep
        if (aEvent.getCancelStep() > schedulingStep)  {
          if ((executionId == 0)  || executionId == aEvent.getExecutionId())  {
            AnimationAuxiliary.debug(this,"restore cancelled event for step "  + aEvent.getStep());
            aEvent.setCancelStep(0);  // the event is not cancelled
          }
        }
      }
      // remove the vector if it is empty
      if (v.isEmpty())  {
        this.remove(key);
      }
    }
    AnimationAuxiliary.debug(this,"After removeAllScheduledEvents");
    this.print();
  }

  // this function prints the contents of the scheduler
  // it is intended for debugging
  public void print()  {
    Enumeration en = this.elements();
    for(; en.hasMoreElements();) {
      Vector v = (Vector)en.nextElement();
      if (!v.isEmpty()) {
        AnimationAuxiliary.debug(this,"Step: " + ((AnimationEvent)v.firstElement()).getStep());
      }
      // print the events stored inside teh vector
      for(int i=0; i<v.size();i++) {
        AnimationEvent aEvent = (AnimationEvent)v.get(i);
        if (aEvent.getEventType()==AnimationSettings.ACTIVATION_EVENT)  {
          AnimationAuxiliary.debug(this,"#" + i + ": Activation event");
        }
        else  {
          AnimationAuxiliary.debug(this,"#" + i + ": Termination event");
        }
        AnimationAuxiliary.debug(this," was issued on " + aEvent.getSchedulingStep());
      }
    }
  }

  // this function checks whether there are events
  // scheduled on or after given step
  public AnimationEvent futureEventsScheduled(long currentStep, long executionId)  {
    Enumeration en = this.keys();
    for(; en.hasMoreElements();) {
      Long key = (Long)en.nextElement();
      if (key.longValue() >= currentStep)  {
        Vector v = (Vector)this.get(key);
        for(int i=0; i<v.size();i++) {
          AnimationEvent aEvent = (AnimationEvent)v.get(i);
          if ((aEvent.getExecutionId() == executionId) &&
              (aEvent.getEventType() != AnimationSettings.TERMINATION_EVENT))  {
            AnimationAuxiliary.debug(this,"found future event, step " + aEvent.getStep());
            return aEvent;
          }
        }
      }
    }
    return null;
  }
}