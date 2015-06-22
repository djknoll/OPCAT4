package extensionTools.etAnimation;

/*
  This class implements the functions of Animation Entry, which is an extension
  of IXEntry. The class holds animation functions, such as animated and
  stopAnimation. It also stores the events scheduled for the entry for future
  execution and keeps the log of the steps that were executed
*/

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXLinkEntry;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXProcessInstance;
import exportedAPI.opcatAPIx.IXRelationEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;

class AnimationEntry extends Object {

  private IXEntry iEntry = null;

  // current event stores differents setting that external functions
  // pass to each other.
  private AnimationEvent currentEvent = null;

  private int defaultNumberOfInstances;
  private String taxtualInfo = "" ; 
  private boolean isEventLinkActivated = false ; 

  // activation events scheduler
  // it keeps a list of events scheduled for each step
  AnimationEventsScheduler actvScheduler;

  // termination events scheduler
  AnimationEventsScheduler termScheduler;

  // Log table
  AnimationEventsScheduler log;

  // current events list
  // usually animation entry has one current event
  // list of current events is required when more than one execution runs
  Hashtable currentEvents;  // key execution Id, value AnimationEvent


  // constructor
  public AnimationEntry (IXEntry iEntry)  {
    this.iEntry = iEntry;
    currentEvent = new AnimationEvent();
    currentEvent.setNumberOfInstances(0);
    taxtualInfo = ""; 
    // set default number of instances
    if (AnimationAuxiliary.isThing(iEntry))  {
      IXThingEntry iThingEntry = (IXThingEntry) iEntry;
      defaultNumberOfInstances = iThingEntry.getNumberOfMASInstances();
    }
    else  {
      defaultNumberOfInstances = 0;
    }
    if (AnimationAuxiliary.isLink(iEntry))  {
      this.currentEvent.setPath(((IXLinkEntry) iEntry).getPath());
    }
    else  {
      this.currentEvent.setPath(new String());
    }
    actvScheduler = new AnimationEventsScheduler();
    termScheduler = new AnimationEventsScheduler();
    log = new AnimationEventsScheduler();
    currentEvents = new Hashtable();
  }

  public IXEntry getIXEntry() {
    return iEntry;
  }

  // this function activates the entry - increases the number of instances
  // by one and applies animation
  public void activate(AnimationEvent aEvent)  {
    int insNum = this.getNumberOfInstances();
    // generate new executionId if it was not passed
    if (aEvent.getExecutionId() == 0) {
      aEvent.setExecutionId();
    }
    // store the path
    if (!currentEvent.getPath().equals("")) {
      AnimationAuxiliary.debug(this,"Set path " + currentEvent.getPath() + " for " + iEntry.getName() + " execId = " + aEvent.getExecutionId());
      aEvent.setPath(currentEvent.getPath());
    }

    // store step
    this.currentEvent.setStep(aEvent.getStep());

    AnimationAuxiliary.debug(this,"Activate " + iEntry.getName() + ", exec ID = " + aEvent.getExecutionId());
    AnimationAuxiliary.debug(this,", number of instances = " + aEvent.getNumberOfInstances());

    // store current event
    this.addCurrentEvent(new AnimationEvent(aEvent));

    this.currentEvent.setParentProcess(aEvent.getParentProcess());
    AnimationAuxiliary.debug(this,"setParentProcess " + aEvent.getParentProcess());
    // remove all future events scheduled for it for this execution
    if (aEvent.getExecutionId() != 0) {
      AnimationAuxiliary.debug(this,"Calling removeAllScheduledEvents from step " + (aEvent.getStep() - 1));
      this.actvScheduler.removeAllScheduledEvents(aEvent.getStep() - 1, aEvent.getExecutionId());
    }
    // write the activation to the log
    // set scheduling step to before writing to the log
    AnimationEvent logEvent = new AnimationEvent(aEvent);
    logEvent.setEventType(AnimationSettings.ACTIVATION_EVENT);
    logEvent.setSchedulingStep(aEvent.getStep());
    log.scheduleEvent(logEvent);

    setNumberOfInstances(insNum + aEvent.getNumberOfInstances());
    if ((insNum > 0) ||
         (insNum + aEvent.getNumberOfInstances() == 0))  {
      return;
    }
    this.animate(aEvent);
  }

  public int getNumberOfInstances() {
    return this.currentEvent.getNumberOfInstances();
  }


  // this function sets the number of instances and updated the related
  // entries
  private void setNumberOfInstances(int num) {
//    if (AnimationAuxiliary.isThing(iEntry) && !this.isFeature())  {
    if (AnimationAuxiliary.isThing(iEntry))  {
      IXThingEntry iThingEntry = (IXThingEntry) iEntry;
      if ((num + getNumberOfSpecializations()) > 0)  {
        iThingEntry.setNumberOfMASInstances(num + getNumberOfSpecializations());
      }
      else  {
        iThingEntry.setNumberOfMASInstances(1);
      }
    }
    iEntry.updateInstances();
    // 0 is invalid number of instances for OPCAT, therefore
    // I don't set MAS instances to 0, I store it in local variable
    // currentEvent.numberOfInstances
    this.currentEvent.setNumberOfInstances(num);
  }

  // this function restores the number of instances to the default value
  // it is used when animation terminates to restore the number of MAS instances
  private void setDefaultNumberOfInstances() {
//    if (AnimationAuxiliary.isThing(iEntry) && !this.isFeature())  {
    if (AnimationAuxiliary.isThing(iEntry))  {
      IXThingEntry iThingEntry = (IXThingEntry) iEntry;
      if (defaultNumberOfInstances > 0)  {
        iThingEntry.setNumberOfMASInstances(defaultNumberOfInstances);
      }
      else  {
        iThingEntry.setNumberOfMASInstances(1);
      }
    }
    iEntry.updateInstances();
  }

  public int getDefaultNumberOfInstances() {
    return defaultNumberOfInstances;
  }

  // this function deactivates the entry - decreases number of instances
  // and stops the animation
  public void deactivate(AnimationEvent aEvent)  {
    AnimationAuxiliary.debug(this,"Starting deactivate for " + iEntry.getName());

    if (aEvent == null) {
      aEvent = new AnimationEvent();
      aEvent.setEventType(AnimationSettings.TERMINATION_EVENT);
    }

    // restore path
    this.currentEvent.setPath(this.getPath(aEvent.getExecutionId()));

    // store step
    Long execId = new Long(aEvent.getExecutionId());
    if (execId.longValue() != 0)  {
      try {
        this.currentEvent.setStep(((AnimationEvent)currentEvents.get(execId)).getStep());
      }
      catch (Exception e) {
    	  AnimationAuxiliary.debug(this,e.getMessage()) ; 
      }
    }

    // write the deactivation to the log
    AnimationEvent logEvent = new AnimationEvent(aEvent);
    logEvent.setEventType(AnimationSettings.TERMINATION_EVENT);
    logEvent.setSchedulingStep(aEvent.getStep());
    log.scheduleEvent(logEvent);

    // remove current event
    this.removeCurrentEvent(aEvent);

    int insNum = getNumberOfInstances(),
         numberOfInstances = aEvent.getNumberOfInstances();
    AnimationAuxiliary.debug(this,"deactivate for " + iEntry.getName());
    AnimationAuxiliary.debug(this,"exec id = " + aEvent.getExecutionId());
    AnimationAuxiliary.debug(this,"getNumberOfInstances() " + getNumberOfInstances());
    AnimationAuxiliary.debug(this,"aEvent.getNumberOfInstances() " + aEvent.getNumberOfInstances());
    AnimationAuxiliary.debug(this,"numberOfSpecializations " + this.currentEvent.getNumberOfSpecializations());

    if (insNum >= numberOfInstances)  {
      insNum -= numberOfInstances;
    }
    else  {
      insNum = 0;
    }
    setNumberOfInstances(insNum);
    //??
    if (insNum + this.currentEvent.getNumberOfSpecializations() != 0)  {
      return;
    }
    this.stopAnimation(aEvent);
  }

  // check if the entry is a feature of another entry
  public boolean isFeature()  {
    return getRelationByDestionation(exportedAPI.OpcatConstants.EXHIBITION_RELATION).elements().hasMoreElements();
  }

  public Vector getRelationByDestionation(int relationType)  {
    Vector v = new Vector();
    if (AnimationAuxiliary.isThing(iEntry) || AnimationAuxiliary.isState(iEntry)) {
      IXConnectionEdgeEntry iEdge = (IXThingEntry)iEntry;
      Enumeration en = iEdge.getRelationByDestination();
      for (;en.hasMoreElements();)  {
        IXRelationEntry relation = (IXRelationEntry)en.nextElement();
        if (relation.getRelationType() == relationType) {
          v.add(relation);
        }
      }
    }
    return v;
  }

  // check if the entry is a root of a relation with the given type
  public Vector getRelationBySource(int relationType)  {
    Vector v = new Vector();
    if (AnimationAuxiliary.isThing(iEntry) || AnimationAuxiliary.isState(iEntry)) {
      IXConnectionEdgeEntry iEdge = (IXThingEntry)iEntry;
      Enumeration en = iEdge.getRelationBySource();
      for (;en.hasMoreElements();)  {
        IXRelationEntry relation = (IXRelationEntry)en.nextElement();
        if (relation.getRelationType() == relationType) {
          v.add(relation);
        }
      }
    }
    return v;
  }

  public boolean isActive() {
    AnimationAuxiliary.debug(this,iEntry.getName() + " is active = " +  this.currentEvent.getNumberOfInstances());
    return this.currentEvent.getNumberOfInstances() > 0;
  }

  // this function checks whether the entry is active
  // in the given execution context
  public boolean isActive(long executionId) {
    AnimationAuxiliary.debug(this,"isActive for " + iEntry.getName() + ", executionId = " +  executionId);
    AnimationAuxiliary.debug(this,"return " + this.currentEvents.containsKey(new Long(executionId)));
    return this.currentEvents.containsKey(new Long(executionId));
  }

  public void setPath(String path)  {
    this.currentEvent.setPath(path);
  }

  public void setPath(String path, long execId)  {
    AnimationEvent aEvent = (AnimationEvent)this.currentEvents.get(new Long(execId));
    if (aEvent != null) {
      aEvent.setPath(path);
    }
  }

  public String getPath() {
    return this.currentEvent.getPath();
  }

  public String getPath(long execId) {
    AnimationEvent aEvent = (AnimationEvent)this.currentEvents.get(new Long(execId));
    if (aEvent != null) {
      return aEvent.getPath();
    }
    AnimationAuxiliary.debug(this,"getPath: could not find AnimationEvent for execId " + execId);
    return "";
  }

  public void setNumberOfSpecializations(int numberOfSpecialization)  {
    AnimationAuxiliary.debug(this,"setNumberOfSpecializations for " + iEntry.getName());
    int oldIns = this.getNumberOfInstances() + this.getNumberOfSpecializations();
    if (numberOfSpecialization < 0) {
      this.currentEvent.setNumberOfSpecializations(0);
    }
    else  {
      this.currentEvent.setNumberOfSpecializations(numberOfSpecialization);
    }
    try {
      IXThingEntry iThingEntry = (IXThingEntry)iEntry;
      int oldNumOfMas = iThingEntry.getNumberOfMASInstances();
      if ((this.getNumberOfInstances() + this.currentEvent.getNumberOfSpecializations()) > 0) {
        iThingEntry.setNumberOfMASInstances(this.getNumberOfInstances() +
                                            this.currentEvent.getNumberOfSpecializations());
      }
      else {
        iThingEntry.setNumberOfMASInstances(1);
      }
      if ((oldIns == 0) && (numberOfSpecialization > 0))  {
        this.animate(new AnimationEvent());
      }
      if ((oldIns > 0) &&
         ((this.getNumberOfInstances() + this.getNumberOfSpecializations()) == 0)) {
        this.stopAnimation(new AnimationEvent());
      }
      iEntry.updateInstances();
    }
    catch (Exception e) {}
  }

  public int getNumberOfSpecializations()  {
    return this.currentEvent.getNumberOfSpecializations();
  }

  // get any execution id
  public long getExecutionId()  {
    Enumeration en = this.currentEvents.elements();
    if (en.hasMoreElements()) {
      return ((AnimationEvent)en.nextElement()).getExecutionId();
    }
    return 0;
  }

  public AnimationEvent getCurrentEvent() {
    return this.currentEvent;
  }

  public AnimationEvent getCurrentEvent(long executionId) {
    AnimationAuxiliary.debug(this,"getCurrentEvent for " + iEntry.getName() + ", executionId = " + executionId);
    AnimationAuxiliary.debug(this,"return " + (AnimationEvent)currentEvents.get(new Long(executionId)));
    return (AnimationEvent)currentEvents.get(new Long(executionId));
  }

  public void addCurrentEvent(AnimationEvent aEvent)  {
    // store current event
    Long execId = new Long(aEvent.getExecutionId());
    AnimationEvent aEventTemp = null;
    try {
      aEventTemp = (AnimationEvent)this.currentEvents.get(execId);
      aEvent.setNumberOfInstances(aEventTemp.getNumberOfInstances() + aEvent.getNumberOfInstances());
      currentEvents.put(execId,aEvent);
      AnimationAuxiliary.debug(this,"ExecId: " + execId.longValue() + ", numb of ins = " +  aEvent.getNumberOfInstances());
    }
    catch (NullPointerException ex) {
      this.currentEvents.put(execId, aEvent);
    }
  }

  public void removeCurrentEvent(AnimationEvent aEvent)  {
    // remove current event
    AnimationEvent aEventTemp = null;
    Long execId = new Long(aEvent.getExecutionId());
    try {
      aEventTemp = (AnimationEvent)this.currentEvents.get(execId);
      AnimationAuxiliary.debug(this,"removeCurrentEvent for " + iEntry.getName());
      if (AnimationAuxiliary.isProcess(iEntry) && aEventTemp.getNumberOfInstances() > 1) {
        aEventTemp.setNumberOfInstances(aEventTemp.getNumberOfInstances() - 1);
        currentEvents.put(execId, aEventTemp);
        aEvent.setNumberOfInstances(1);
        AnimationAuxiliary.debug(this,"reduce number of instanced for exec id" + execId.longValue());
        AnimationAuxiliary.debug(this,"new number of instances is " + aEventTemp.getNumberOfInstances());
      }
      else  {
        AnimationAuxiliary.debug(this,"remove execId " + execId.longValue());
        this.currentEvents.remove(execId);
      }
    }
    catch (NullPointerException ex) {}
  }


  public void scheduleActivation(AnimationEvent actvEvent)  {
    if (actvEvent.getExecutionId() == 0)  {
      actvEvent.setExecutionId();
    }
    actvScheduler.scheduleEvent(actvEvent);
  }

  public void scheduleActivation(long currentStep, long eventStep)  {
    actvScheduler.scheduleEvent(currentStep, eventStep);
  }

  public void scheduleTermination(AnimationEvent termEvent)  {
    termScheduler.scheduleEvent(termEvent);
  }

  public void scheduleTermination(long currentStep, long eventStep)  {
  termScheduler.scheduleEvent(currentStep, eventStep);
  }

  public Enumeration getScheduledActivations(long step) {
    return actvScheduler.getScheduledEvents(step);
  }

  public Enumeration getScheduledTerminations(long step) {
    return termScheduler.getScheduledEvents(step);
  }

  public void removeScheduledActivation(long step)  {
    actvScheduler.removeScheduledEvent(step);
  }

  // this function checks whether there are activations
  // scheduled after given step
  public boolean futureActivationScheduled(long currentStep, long executionId)  {
    AnimationAuxiliary.debug(this,"futureActivationScheduled for " + iEntry.getName() + ", executionId " + executionId);
    AnimationAuxiliary.debug(this," returns " + actvScheduler.futureEventsScheduled(currentStep, executionId));
    return (actvScheduler.futureEventsScheduled(currentStep, executionId) != null);
  }

  public long getDuration() {
    // temporary function - it should be part of OPCAT interface
    if (AnimationAuxiliary.isProcess(iEntry)) {
      if (AnimationSettings.FIXED_PROCESS_DURATION) {
        return AnimationSettings.PROCESS_DURATION*AnimationSettings.STEP_DURATION;
      }
      else  {
        //Random r = new Random();
        //long temp = AnimationSettings.PROCESS_DURATION_RANGE_START + r.nextInt(AnimationSettings.PROCESS_DURATION_RANGE_END - AnimationSettings.PROCESS_DURATION_RANGE_START);
        long temp = AnimationSettings.PROCESS_DURATION_RANGE_START +
                    AnimationAuxiliary.getRandomNumber(AnimationSettings.PROCESS_DURATION_RANGE_END -
                                                       AnimationSettings.PROCESS_DURATION_RANGE_START);
        temp = temp*AnimationSettings.STEP_DURATION;
        //AnimationAuxiliary.debug(this,"random int value " + i + " for " + this.getIXEntry().getName());
        return temp;
      }
    }

    if (AnimationAuxiliary.isEvent(iEntry)) {
      IXLinkEntry iLink = (IXLinkEntry)this.getIXEntry();
      /*
      if ((iLink.getLinkType() == exportedAPI.OpcatConstants.AGENT_LINK) ||
          (iLink.getLinkType() == exportedAPI.OpcatConstants.EXCEPTION_LINK))  {
        return 0;
      }*/

      if (AnimationSettings.FIXED_REACTION_TIME)  {
        return AnimationSettings.REACTION_TIME*AnimationSettings.STEP_DURATION;
      }
      else  {
        //Random r = new Random();
        //long temp = AnimationSettings.REACTION_TIME_RANGE_START + r.nextInt(AnimationSettings.REACTION_TIME_RANGE_END - AnimationSettings.REACTION_TIME_RANGE_START);
        long temp = AnimationSettings.REACTION_TIME_RANGE_START +
                    AnimationAuxiliary.getRandomNumber(AnimationSettings.REACTION_TIME_RANGE_END -
                                                       AnimationSettings.REACTION_TIME_RANGE_START);
        temp = temp*AnimationSettings.STEP_DURATION;
        return temp;
      }
    }
    return 0;
  }

  // temporary function - it should be part of OPCAT interface
  public long getMinTime() {
    if (AnimationAuxiliary.isProcess(iEntry)) {
      IXProcessEntry iEntry = (IXProcessEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMinTimeActivation());
    }
    if (AnimationAuxiliary.isLink(iEntry))  {
      IXLinkEntry iEntry = (IXLinkEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMinReactionTime());
    }

    if (AnimationAuxiliary.isState(iEntry)) {
      IXStateEntry iEntry = (IXStateEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMinTime());
    }
    return 0;
  }

  public long getMaxTime() {
    if (AnimationAuxiliary.isProcess(iEntry)) {
      IXProcessEntry iEntry = (IXProcessEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMaxTimeActivation());
    }
    if (AnimationAuxiliary.isLink(iEntry))  {
      IXLinkEntry iEntry = (IXLinkEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMaxReactionTime());
    }

    if (AnimationAuxiliary.isState(iEntry)) {
      IXStateEntry iEntry = (IXStateEntry)this.getIXEntry();
      return this.translateTime(iEntry.getMaxTime());
    }
    return java.lang.Long.MAX_VALUE;
  }

  private long translateTime(String timeString) {
    if (timeString.equals("infinity"))  {
      return java.lang.Long.MAX_VALUE;
    }
    long l[] = {1, 1000, 60000, 3600000, 86400000};
    int i = 0;
    long time = 0;
    StringTokenizer st = new  StringTokenizer(timeString, ";");
    for(i=0; st.hasMoreTokens();i++)  {
      try {
        String temp = st.nextToken();
        long timeToken;
        timeToken = new Long(temp).longValue();
        if (i < 5)  {
          time += timeToken*l[i];
        }
        else  {
          time += timeToken*java.lang.Long.MAX_VALUE;
        }
      }
      catch (Exception e)  {
        // invalid MaxTimeActivation or MinTimeActivation defined for the process
        e.printStackTrace();
      }
    }
    return time;
  }

  public void clear() {
    this.actvScheduler.clear();
    this.termScheduler.clear();
    this.log.clear();
    this.currentEvent.setNumberOfInstances(0);
    this.setDefaultNumberOfInstances();
    this.currentEvents.clear();
    this.currentEvent.setNumberOfSpecializations(0);
    this.stopAnimation(new AnimationEvent());
  }

  public void animate(AnimationEvent aEvent) {
      //some debug vars
      String d1 = new Boolean(extensionTools.etAnimation.AnimationAuxiliary.isState(iEntry)).toString()+iEntry.getName()  ;
      String d2 = new Boolean(extensionTools.etAnimation.AnimationAuxiliary.isState(iEntry)).toString()+iEntry.getName()  ;
      //end debug	  	  
    Enumeration eTemp = iEntry.getInstances();
    for(; eTemp.hasMoreElements();) {
      IXInstance i = (IXInstance) eTemp.nextElement();
      if (AnimationAuxiliary.isLink(iEntry))  {
        IXLinkInstance iLinkInstance = (IXLinkInstance)i;
        if (AnimationAuxiliary.isEvent(iEntry)) {
          //AnimationAuxiliary.debug(this,"Event: before animateAsFlash");
          iLinkInstance.animateAsFlash();
          //AnimationAuxiliary.debug(this,"Event: after animateAsFlash");
        }
        else  {
          // link, but not event
          if (aEvent.getDuration() > 0) {
            //AnimationAuxiliary.debug(this,"Link: before animate");
            iLinkInstance.animate(aEvent.getDuration());
            //AnimationAuxiliary.debug(this,"Link: after animate");
          }
        }
      }
      else  {
        // object, process or state
        if (AnimationAuxiliary.isState(iEntry) || AnimationAuxiliary.isThing(iEntry)) {
          // object or process
          IXConnectionEdgeInstance  iEdge = (IXConnectionEdgeInstance )i;
          //AnimationAuxiliary.debug(this,"getBackgroundColor " + iThingInstance.getBackgroundColor());
          //AnimationAuxiliary.debug(this,"defaultColor " + defaultColor);
          AnimationAuxiliary.debug(this,"animate thing " + iEntry.getName());
          //AnimationAuxiliary.debug(this,"duration " + aEvent.getDuration());
          //AnimationAuxiliary.debug(this,"Current time " + System.currentTimeMillis());
          iEdge.animate(aEvent.getDuration());
          
          /**
           * TODO: here ask the conf if to show th eurl or not?
           */
          iEdge.animateUrl(aEvent.getDuration()) ; 
        }
      }
    }
  }

  public void stopAnimation(AnimationEvent aEvent) {
    Enumeration eTemp = iEntry.getInstances();
    for(; eTemp.hasMoreElements();) {

      if (AnimationAuxiliary.isThing(iEntry) || AnimationAuxiliary.isState(iEntry))  {
        IXConnectionEdgeInstance i = (IXConnectionEdgeInstance) eTemp.nextElement();
        //AnimationAuxiliary.debug(this,"stopAnimation for " + iEntry.getName());
        //AnimationAuxiliary.debug(this,"duration =  " + aEvent.getDuration());
        i.stopAnimation(aEvent.getDuration());
      }
      else  {
        if (AnimationAuxiliary.isLink(iEntry))  {
          //stopAnimation for link was not supplied.
          IXLinkInstance i = (IXLinkInstance) eTemp.nextElement();
          //AnimationAuxiliary.debug(this,"stopAnimation for link " + iEntry.getName());
          //AnimationAuxiliary.debug(this,"duration " + aEvent.getDuration());
          //i.stopAnimation(aEvent.getDuration());
          i.stopAnimation();
        }
      }
    }
  }

    public void pauseAnimation() {
    Enumeration en = iEntry.getInstances();
    for(; en.hasMoreElements();) {
      if (AnimationAuxiliary.isLink(iEntry))  {
        IXLinkInstance i = (IXLinkInstance) en.nextElement();
        i.pauseAnimation();
      }
      else  {
        if (AnimationAuxiliary.isState(iEntry) || AnimationAuxiliary.isThing(iEntry)) {
          IXConnectionEdgeInstance i = (IXConnectionEdgeInstance)en.nextElement();
          // temporary remarked
          i.pauseAnimation();
        }
      }
    }
  }

  public void continueAnimation() {
    Enumeration en = iEntry.getInstances();
    for(; en.hasMoreElements();) {
      if (AnimationAuxiliary.isLink(iEntry))  {
        IXLinkInstance i = (IXLinkInstance) en.nextElement();
        i.continueAnimation();
      }
      else  {
        if (AnimationAuxiliary.isState(iEntry) || AnimationAuxiliary.isThing(iEntry)) {
          IXConnectionEdgeInstance i = (IXConnectionEdgeInstance)en.nextElement();
          i.continueAnimation();
        }
      }
    }
  }

  ////////////////////////////
  // CHILD-PARENT functions//
  ////////////////////////////

  // this function returns a list of parents processes (not objects)
  public Enumeration getParents() {
    Enumeration en = iEntry.getInstances();
    Vector parents = new Vector();
    if (!AnimationAuxiliary.isThing(iEntry))  {
      // return empty enumeration
      return parents.elements();
    }
    for(; en.hasMoreElements();) {
      IXThingInstance i = (IXThingInstance) en.nextElement();
      IXThingInstance parentInstance = i.getParentIXThingInstance();
      if (parentInstance != null) {
        if (AnimationAuxiliary.isProcess(parentInstance.getIXEntry()))  {
          IXEntry parentEntry = parentInstance.getIXEntry();
          if (!parents.contains(parentEntry)) {
            parents.add(parentEntry);
          }
        }
      }
    }
    return parents.elements();
  }

  public long getNextLevelY(IXThingInstance parentInstance, long levelY)  {
    AnimationAuxiliary.debug(this,"Starting getNextLevelY, parent = " + parentInstance.getIXEntry().getName());
    AnimationAuxiliary.debug(this,", levelY = " + levelY);
    Enumeration en = parentInstance.getChildThings();
    Vector children = new Vector();
    long minY = java.lang.Long.MAX_VALUE;

    for(; en.hasMoreElements();) {
      IXThingInstance tempChildInstance = (IXThingInstance)en.nextElement();
      long tempChildY = tempChildInstance.getY();
      // check that this is a process
      try {
        IXProcessEntry temp = (IXProcessEntry) tempChildInstance.getIXEntry();
      }
      catch (ClassCastException e)  {
        // if not a process continue to a next child
        continue;
      }
      if  ((tempChildY <= minY) &&
          (tempChildY > (levelY + 7)))  {
        if (tempChildY < minY)  {
          //children.clear();
          minY = tempChildY;
        }
        //children.add(tempChildInstance);
      }
    }
    AnimationAuxiliary.debug(this,"after first loop, minY = " + minY);
    return minY;
  }

  public Enumeration getChildrenByLevel(IXThingInstance parentInstance, long levelY)  {
    AnimationAuxiliary.debug(this,"Starting getChildrenByLevel, parent = " + parentInstance.getIXEntry().getName());
    AnimationAuxiliary.debug(this,", levelY = " + levelY);
    Enumeration en = parentInstance.getChildThings();
    Vector children = new Vector();
    long minY = java.lang.Long.MAX_VALUE;

    // find the Y coordinate of the next level
    // this is the minimal Y that is greater than levelY
    minY = getNextLevelY(parentInstance, levelY);

    // obtain all processes from this level
    // this is a temporary solution, because it is not possible
    // to set several entries exactly on the same level
    en = parentInstance.getChildThings();
    for(; en.hasMoreElements();) {
      IXThingInstance tempChildInstance = (IXThingInstance)en.nextElement();
      long tempChildY = tempChildInstance.getY();
      // check that this is a process
      IXProcessEntry temp = null;
      try {
        temp = (IXProcessEntry) tempChildInstance.getIXEntry();
      }
      catch (ClassCastException e)  {
        // if not a process continue to a next child
        continue;
      }
      if ((tempChildY <= (minY + 3)) && (tempChildY >= minY)) {
        AnimationAuxiliary.debug(this,"Found process in the same level,  y = " + tempChildY + ",process = " + temp.getName());
        children.add(tempChildInstance);
      }
    }
    return children.elements();
  }

  // Returns all childern located on the next level after given child
  // on the zoomed in process
  public Enumeration getNextLevelChildren(IXThingInstance childInstance)  {
    return getChildrenByLevel(childInstance.getParentIXThingInstance(), childInstance.getY());
  }

  public Enumeration getFirstLevelChildren(IXThingInstance parentInstance)  {
    return getChildrenByLevel(parentInstance, java.lang.Long.MIN_VALUE);
  }

  // return true if the following conditions hold:
  // 1. process has no children processes
  //    (i.e. is not zoomed in, or zoomed in without children processes)
  // 2. process has parent and it was activated in parent's context
  public boolean isLowLevel() {
    if (AnimationAuxiliary.isProcess(iEntry)) {
    AnimationAuxiliary.debug(this,"isLowLevel for " + iEntry.getName());
    AnimationAuxiliary.debug(this,"this.getChildrenProcesses().isEmpty() = " + this.getChildrenProcesses().isEmpty());
    /*AnimationAuxiliary.debug(this,"this.getParents().hasMoreElements() = " + this.getParents().hasMoreElements());
    AnimationAuxiliary.debug(this,"currentEvent.getParentProcess() = " + currentEvent.getParentProcess());
    AnimationAuxiliary.debug(this,"isLowLevel returns " +
              (!this.getChildrenProcesses().hasMoreElements() &&
              (!this.getParents().hasMoreElements() ||
              currentEvent.getParentProcess() != null)));
      return (!this.getChildrenProcesses().hasMoreElements() &&
              (!this.getParents().hasMoreElements() ||
              currentEvent.getParentProcess() != null));*/

      return (this.getChildrenProcesses().isEmpty());
    }
    return true;
  }

  public Vector getChildrenProcesses() {
    return getChildrenByClass("gui.projectStructure.ProcessInstance");
  }

  public Vector getChildrenObjects() {
    return getChildrenByClass("gui.projectStructure.ObjectInstance");
  }

  public Vector getChildrenEntries() {
    return getChildrenByClass("");
  }

  private Vector getChildrenByClass(String className)  {
    Vector results = new Vector();
    if (!AnimationAuxiliary.isProcess(iEntry))  {
      return results;
    }
    Enumeration en = iEntry.getInstances();
    for(; en.hasMoreElements();) {
      IXProcessInstance processInstance = (IXProcessInstance) en.nextElement();
      Enumeration children = processInstance.getChildThings();
      for(; children.hasMoreElements();) {
        IXThingInstance childInstance = (IXThingInstance)children.nextElement();
        if ((childInstance.getClass().getName().equals(className)) ||
            (className.equals(""))) {
          IXEntry childEntry = childInstance.getIXEntry();
          if (!results.contains(childEntry))  {
            results.add(childEntry);
          }
        }
      }
    }
      return results;
  }


  // this function is used for undo - it restores the entry from log
  // it updates the number of instances and the animation status
  public void restore(long fromStep, long toStep)  {
    AnimationAuxiliary.debug(this,"restore for " + iEntry.getName() + ", from " + fromStep + ", to " + toStep)  ;
    if (AnimationAuxiliary.isLink(iEntry))  {
      if (fromStep > toStep)  {
        // remove from the log all events that occured after toStep
        log.removeAllScheduledEvents(toStep, 0);
        // remove scheduled future events
        actvScheduler.removeAllScheduledEvents(toStep, 0);
        termScheduler.removeAllScheduledEvents(toStep, 0);
        return;
      }
    }
    // fromStep should be greater than toStep
    if (fromStep > toStep)  {
      // find all events that occured after the new step
      int numOfInstances = this.getNumberOfInstances();
      AnimationAuxiliary.debug(this,"Initial  numOfInstances " + numOfInstances);
      for(long step=fromStep; step>toStep;step--) {
        Enumeration enEvents = log.getScheduledEvents(step);
        for(; enEvents.hasMoreElements();) {
          AnimationEvent aEvent = (AnimationEvent)enEvents.nextElement();
          AnimationAuxiliary.debug(this,"Get event for step " + step);
          AnimationAuxiliary.debug(this,"aEvent.getEventType() " + aEvent.getEventType());
          // restore number of instances
          // if event is of activation type
          if (aEvent.getEventType() == AnimationSettings.ACTIVATION_EVENT)  {
            numOfInstances -= aEvent.getNumberOfInstances();
            AnimationAuxiliary.debug(this,"minus " + numOfInstances);
            // remove the corresponding entry from currentEvents
            //this.currentEvents.remove(new Long(aEvent.getExecutionId()));
            this.removeCurrentEvent(aEvent);
          }
          else  {
            numOfInstances += aEvent.getNumberOfInstances();
            AnimationAuxiliary.debug(this,"plus " + numOfInstances);
            // find the corresponding activation event and add it to
            // currentEvents list
            AnimationEvent actvEvent = log.futureEventsScheduled(0,aEvent.getExecutionId());
            if (actvEvent != null)  {
              AnimationAuxiliary.debug(this,"Step of restored activation event = " + actvEvent.getStep());
              //this.currentEvents.put(new Long(aEvent.getExecutionId()), new AnimationEvent(actvEvent));
              this.addCurrentEvent(new AnimationEvent(actvEvent));
            }
          }
        }
      }

      // restore the animation in accordance with number of instances
      AnimationAuxiliary.debug(this,"final " + numOfInstances);
      this.setNumberOfInstances(numOfInstances);
      if (numOfInstances > 0) {
        this.animate(new AnimationEvent());
      }
      else  {
        this.stopAnimation(new AnimationEvent());
      }
      // remove from the log all events that occured after toStep
      log.removeAllScheduledEvents(toStep, 0);
      // remove scheduled future events
      actvScheduler.removeAllScheduledEvents(toStep, 0);
      termScheduler.removeAllScheduledEvents(toStep, 0);
    }
  }

public String getTaxtualInfo() {
	return taxtualInfo;
}

public void setTaxtualInfo(String taxtualInfo) {
	this.taxtualInfo = taxtualInfo;
}

public boolean isEventLinkActivated() {
	return isEventLinkActivated;
}

public void setEventLinkActivated(boolean isEventLinkActivated) {
	this.isEventLinkActivated = isEventLinkActivated;
}
}
