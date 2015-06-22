package extensionTools.etAnimation;

import java.util.Enumeration;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Vector;

import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXInstance;
import exportedAPI.opcatAPIx.IXLinkEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import exportedAPI.opcatAPIx.IXObjectInstance;
import exportedAPI.opcatAPIx.IXProcessEntry;
import exportedAPI.opcatAPIx.IXProcessInstance;
import exportedAPI.opcatAPIx.IXRelationEntry;
import exportedAPI.opcatAPIx.IXStateEntry;
import exportedAPI.opcatAPIx.IXStateInstance;
import exportedAPI.opcatAPIx.IXSystem;
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;

public class AnimationSystem extends Object implements Animation  {
  private IXSystem  mySys;
  private IXSystem  bkpSys;
  private Vector animationElements = new Vector();
  public long currentStep = 0;
  AnimationTimer animationTimer;
  private boolean stopInd = false;
  private long contextSequence = 0;
  AnimationStatusBar guiPanel = null;
  Stack opdStack = new Stack();
  //There is a bug in original program I could not discovered that 
  //required double starting
  //of animation before actualy activating. Therefore I added 
  //doublestrartfixer to double start it.
  //Also after stopping,since "isAnimationPoused()" 
  //checked through timer there was the same problem,
  //so I use it to avoid it too.
  private static boolean doublestrartfixer = true;

  //////////////////// initiation ////////////////////////

  public AnimationSystem(IXSystem mySys, AnimationStatusBar guiPanel) {
    this.mySys = mySys;
    this.bkpSys = mySys;
    this.guiPanel = guiPanel;
    guiPanel.showAnimationStatus(0,0);
  }

  private void createAnimationElements()  {
    Enumeration en;

    if (!animationElements.isEmpty()) {
      animationElements.clear();
    }
    en = mySys.getIXSystemStructure().getElements();
    for(; en.hasMoreElements();)  {
      IXEntry iEntry  = (IXEntry)en.nextElement();
      // create only things, states and links (but not relations)
      // since relations are not relevant for animation
      if (AnimationAuxiliary.isThing(iEntry) ||
          AnimationAuxiliary.isState(iEntry) ||
          AnimationAuxiliary.isLink(iEntry))  {
        animationElements.addElement(new AnimationEntry(iEntry));
      }
    }
  }

  //////////////////// public functions ////////////////////////

  public void animationSettings(AnimationSettings newSettings) {
    AnimationSettings.loadSettings(newSettings);
  }

  public boolean isAnimationPaused()  {
    try {/*
      return animationTimer.isPause();*/
    	return !(doublestrartfixer);
    }
    catch (NullPointerException e)  {
      return false;
    }
  }
  public boolean canFarward() {
    return isAnimationPaused();
  }

  public boolean canBackward()  {
    return (isAnimationPaused() && (currentStep > 0));
  }

  public void animationStart()  {
    currentStep = 0;
    createAnimationElements();
    if(animationTimer!=null){
    try {
      animationTimer.cancel();
      this.animationStop();
      doublestrartfixer = false;
    }
    catch (NullPointerException e)  {
    }}
    animationTimer = new AnimationTimer(this);
    if(doublestrartfixer){
    	doublestrartfixer = false;
    	this.animationStart();
    }    
  }

  public void animationStop()  {
    if (animationTimer != null) {
      animationTimer.cancel();
    }
    deactivate();
    this.guiPanel.showAnimationStatus(0,0);
    stopInd = true;
    doublestrartfixer = true;
    this.mySys = this.bkpSys ;
  }

  public void animationPause()  {
    animationTimer.animationPause();
    // pause all animation effects
    Enumeration en = this.animationElements.elements();
    for(; en.hasMoreElements();)  {
      ((AnimationEntry)en.nextElement()).pauseAnimation();
    }
  }

  public void animationContinue()  {
    animationTimer.animationContinue();
    // continue all animation effects
    Enumeration en = this.animationElements.elements();
    for(; en.hasMoreElements();)  {
      ((AnimationEntry)en.nextElement()).continueAnimation();
    }
  }

  public void animationActivate() {
    Enumeration en = mySys.getCurrentIXOpd().getSelectedItems();
    for(; en.hasMoreElements();)  {
      // activate selected items
      IXEntry iEntry = ((IXInstance) en.nextElement()).getIXEntry();
      AnimationEntry aEntry = findEntryById(iEntry.getId());
      // objects can be activated without preconditions
      // other entries should be checked before the activation
      if (this.checkBeforeActivate(aEntry, null) ||
          AnimationAuxiliary.isObject(aEntry.getIXEntry()) ||
          AnimationAuxiliary.isState(aEntry.getIXEntry()))  {
        activate(aEntry);
      }
    }
    // continue one step forward after manual activate
    if (AnimationSettings.STEP_BY_STEP_MODE)  {
      this.animationForward(1);
    }
  }

  public void animationDeactivate() {
    Enumeration en = mySys.getCurrentIXOpd().getSelectedItems();
    for(; en.hasMoreElements();)  {
      // deactivate selected items
      IXEntry iEntry = ((IXInstance) en.nextElement()).getIXEntry();
      deactivate(findEntryById(iEntry.getId()));
    }
  }

  public void animationForward(long numberOfSteps)  {
    // if application is not running - return
    if (animationTimer == null) {
      return;
    }
    // exit pause mode
    this.animationContinue();

    // schedule pause after numberOfSteps steps
    animationTimer.schedule(new TimerTask() {public void run() {
                                    animationPause();}}, numberOfSteps*AnimationSettings.STEP_DURATION);
  }

  public void animationBackward(long numberOfSteps) {
    // validate number of steps
    if (numberOfSteps >= currentStep)  {
      // return to the starting point
      numberOfSteps = currentStep;
      deactivate();
    }
    else  {
      Enumeration en = animationElements.elements();
      for(; en.hasMoreElements();)  {
        AnimationEntry aEntry = (AnimationEntry)en.nextElement();
        int numOfIns = aEntry.getNumberOfInstances();
        // restore the number of instances and the animation status of the entry itself
        aEntry.restore(currentStep, currentStep - numberOfSteps);
        // update the generalization
        // check how many instances were added/removed from this entry
        if (AnimationAuxiliary.isThing(aEntry.getIXEntry())) {
          numOfIns = numOfIns - aEntry.getNumberOfInstances();
          if (numOfIns != 0)  {
            AnimationEvent aEventTemp = new AnimationEvent();
            if (numOfIns > 0) {
              aEventTemp.setEventType(AnimationSettings.TERMINATION_EVENT);
            }
            else  {
              aEventTemp.setEventType(AnimationSettings.ACTIVATION_EVENT);
            }
            if (numOfIns < 0) {
              numOfIns = (-numOfIns);
            }
            aEventTemp.setNumberOfInstances(numOfIns);
            treatGeneralization((IXThingEntry)aEntry.getIXEntry(), aEventTemp );
          }
        }
        // if the restored entry is a process and it is active after the restore
        // animate its consumption and result links.
        if (AnimationAuxiliary.isProcess(aEntry.getIXEntry()) && (aEntry.getNumberOfInstances() > 0))  {
        }
      }
    }
    retreatStep(numberOfSteps);
  }

//////////////////// activate ////////////////////////

  public void activate (AnimationEntry aEntry) {
    this.activate(aEntry, null);
  }

  public void activate (AnimationEntry aEntry, AnimationEvent aEvent) {
    // activate
    aEvent = activateWithoutConsequences(aEntry, aEvent);
    // treat consequences
    this.treatActivationConsequences(aEntry, aEvent);
  }

  // this function just activates the the entry
  // without treating consequences
  // this function is used when consequences should not be treated
  // immediately: in activateLevel function, where we first activate
  // all processes on certain level and then treat consequences of each one.
  private AnimationEvent activateWithoutConsequences(AnimationEntry aEntry, AnimationEvent aEvent)  {
    if (aEntry != null)  {
      if (aEvent == null) {
        aEvent = new AnimationEvent();
      }
      // set scheduling step and step (this is requried for undo)
      aEvent.setSchedulingStep(currentStep);
      aEvent.setStep(currentStep);
      aEvent.setEventType(AnimationSettings.ACTIVATION_EVENT);
      // for children processes: set parent and executionId, if it still empty
      setProcessContext(aEntry, aEvent);
      // activate
      aEntry.activate(aEvent);
    }
    return aEvent;
  }

  // this function activates child processes located on the same level
  // inside a zoomed in process. It implements the following flow:
  // For each process on the given level:
  // 1. check whether process can be activated
  // 2. if it can not be activated due to missing condition - continue
  // 3. if it can not be activated due to another reason -
  //    schedule another activation to the next step (the system will try to activate
  //    this process until it will succeed.
  // 4. if the process passed the validations - activate it.
  // If no activations were performed or scheduled (this can happen if
  // all the processes on this level could not be activated due to condition),
  // then the function recursively calls to itself to activate for the next level
  // the function returns true if it activates at least one child process
  // and false otherwise
  private boolean activateLevel(Enumeration enChildProcesses, AnimationEvent aInputChildEvent) {
    AnimationAuxiliary.debug("Starting activateLevel");
    boolean proceedToNextLevel = true;
    AnimationEntry aChildProcess = null;
    Vector activatedProcesses = new Vector();
    Vector activatedEvents = new Vector();
    for(; enChildProcesses.hasMoreElements();) {
      AnimationEvent aChildEvent = new AnimationEvent(aInputChildEvent);
      IXProcessInstance childProcessInstance = (IXProcessInstance) enChildProcesses.nextElement();
      aChildProcess = findEntryById(childProcessInstance.getIXEntry().getId());
      //    this was added to debug condition link bug
      /*String s = ((IXEntry)(aChildProcess.getParents()).nextElement()).getName().toString();
      int i=0;
      if(s.lastIndexOf("Transaction\nProcessing")>-1||s.lastIndexOf("Transaction\nExecuting")>-1){
      	i++;
      }*/
      // ignore instantion/feature/specialization/particulation processes
      if (isRelatedProcess(aChildProcess, aChildEvent.getParentProcess())) {
        continue;
      }
      AnimationAuxiliary.debug("Before checkProcessBeforeActivate for " + aChildProcess.getIXEntry().getName());
      // set the indication that the process is to be activated in parents context
      AnimationEntry oldParent = aChildProcess.getCurrentEvent().getParentProcess();
      aChildProcess.getCurrentEvent().setParentProcess(aChildEvent.getParentProcess());
      AnimationAuxiliary.debug("activate level: set parent " + aChildEvent.getParentProcess().getIXEntry().getName());
      AnimationAuxiliary.debug(" for child process " + aChildProcess.getIXEntry().getName());

      // check general validations before process activation
      // child process can be activated automatically
      // only if there is no events linked to it
      boolean eventsFound = this.findEvents(aChildProcess, aChildEvent.getParentProcess());
      aChildProcess.getCurrentEvent().setWaitForEvent(true);
      /*Vector linkTypes = new Vector();
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
      */
      
      if (!eventsFound  && this.checkProcessBeforeActivate(aChildProcess, null)) {
        aChildProcess.getCurrentEvent().setWaitForEvent(false);
        AnimationAuxiliary.debug("After (success) checkProcessBeforeActivate for " + aChildProcess.getIXEntry().getName());
        // activate the process if it is valid
        activateWithoutConsequences(aChildProcess, aChildEvent);
        activatedProcesses.add(aChildProcess);
        activatedEvents.add(aChildEvent);
        proceedToNextLevel = false;
      }
      /*else if((this.getLinkByType((IXProcessEntry)aChildProcess.getIXEntry(),linkTypes,false)).size() == 0 && checkLinksBeforeActivate(aChildProcess, null)){
      	if (!this.findUnavailableCondition(aChildProcess)){
      		AnimationEvent aChildEventTemp = new AnimationEvent(aChildEvent);
            aChildEventTemp.setRecurringInd(false);
            aChildEventTemp.setStep(currentStep);
            aChildEventTemp.setSchedulingStep(currentStep);
            aChildProcess.currentEvents.clear();
            aChildProcess.scheduleActivation(aChildEventTemp);
      		activateWithoutConsequences(aChildProcess, aChildEventTemp);
      		activatedProcesses.add(aChildProcess);
      		activatedEvents.add(aChildEventTemp);
      		proceedToNextLevel = false;
      		aChildProcess.getCurrentEvent().setWaitForEvent(false);
      	}
      }*/
      else  {
        aChildProcess.getCurrentEvent().setWaitForEvent(false);
        AnimationAuxiliary.debug("After (failure) checkProcessBeforeActivate for " + aChildProcess.getIXEntry().getName());
        // if there is an unavalaible condition link
        // other processes should not wait to this process
        // otherwise schedule future activation until first successful activation
        if (!this.findUnavailableCondition(aChildProcess))  {
          // the process should be activated by event, not when handling the
          // future request, therefore number of instances is 0
          // future event is scheduled only to indicate that parent
          // process can not terminate yet
          if (eventsFound)  {
            aChildEvent.setNumberOfInstances(0);
            //schedule future activation to current step (without recurring ind)
            // this is necessary if event will activate the process already
            // on current step
            AnimationEvent aChildEventTemp = new AnimationEvent(aChildEvent);
            aChildEventTemp.setRecurringInd(false);
            aChildEventTemp.setStep(currentStep);
            aChildEventTemp.setSchedulingStep(currentStep);
            aChildProcess.scheduleActivation(aChildEventTemp);
          }
          // schedule future activation to next step with recurring ind
          AnimationAuxiliary.debug("After findUnavailableCondition for " + aChildProcess.getIXEntry().getName());
          AnimationAuxiliary.debug("Schedule recuring event: setStep " + currentStep + ", setSchedulingStep " + currentStep);
          aChildEvent.setRecurringInd(true);
          aChildEvent.setStep(currentStep + 1);
          aChildEvent.setSchedulingStep(currentStep);
          //this was added to solve conditions links problem.
          //aChildEvent.setNumberOfInstances(aChildProcess.getNumberOfInstances());
          aChildProcess.scheduleActivation(aChildEvent);
          proceedToNextLevel = false;
        }
        // if process can not be activated restore its parent process
        aChildProcess.getCurrentEvent().setParentProcess(oldParent);
      }
    }   // end for

    // go through the list of activated processes and treatActivationConsequences
    // for each one. The separation between activate and treat consequences
    // here is required to ensure that all processes activated. Otherwise,
    // it can happen that one of activated process terminates immediately (if
    // neither of its children can be executed due to unavailable condition)
    // and then parent might be cancelled before it tries to activate other
    // children.
    for (int i=0;i<activatedProcesses.size(); i++)  {
      this.treatProcessActivationConsequences((AnimationEntry)activatedProcesses.get(i),
                                               (AnimationEvent)activatedEvents.get(i));
    }

    // if nothing was activated on this level, and no future activations
    // were scheduled, although the level is not empty -
    // proceed immediately to the next level
    if ((proceedToNextLevel) && (aChildProcess != null))  {
      AnimationAuxiliary.debug("proceedToNextLevel");
      treatChildProcessTerminationConsequences(aChildProcess, new AnimationEvent(aInputChildEvent));
      return true;
    }

    // if there are no more children - the process is the last child process
    // terminate the parent
    if (aChildProcess == null)  {
      return false;
    }

    return true;
  }

  // an auxiliary function that ascribes a child process activated by
  // event to some execution of its parent.
  private void setProcessContext(AnimationEntry aEntry, AnimationEvent aEvent)  {
    if (AnimationAuxiliary.isProcess(aEntry.getIXEntry()) &&
        aEntry.getParents().hasMoreElements() &&
        (aEvent.getExecutionId() == 0)) {
      aEvent.setParentProcess(aEntry.getCurrentEvent().getParentProcess());
      aEvent.setExecutionId(aEntry.getCurrentEvent().getExecutionId());
      //aEntry.getCurrentEvent(aEntry.getCurrentEvent().getExecutionId())
    }
  }

//////////////////// deactivate ////////////////////////

  // this function deactivates all animation entries
  public void deactivate()  {
    Enumeration en = animationElements.elements();
    for(; en.hasMoreElements();)
    {
      AnimationEntry aEntry = (AnimationEntry)en.nextElement();
      AnimationEvent aEvent = new AnimationEvent();
      aEvent.setNumberOfInstances(aEntry.getNumberOfInstances());
      aEvent.setStep(currentStep);
      aEvent.setEventType(AnimationSettings.TERMINATION_EVENT);
      aEvent.setExecutionId(0);
      AnimationAuxiliary.debug("deactivate " + aEntry.getIXEntry().getName());
      aEntry.deactivate(aEvent);
      aEntry.clear();
    }
    opdStack.clear();
    AnimationSettings.LAST_EXECUTION_ID = 0;
  }

  // this function deactivates specific animation entry
  public void deactivate (AnimationEntry aEntry) {
    if (aEntry.isActive())  {
      deactivate(aEntry, new AnimationEvent());
    }
  }

  // this function deactivates specific animation entry
  // according to the settings passed in AnimationEvent object
  public void deactivate (AnimationEntry aEntry, AnimationEvent aEvent) {
    if (aEntry != null)  {
      if (aEvent == null) {
        aEvent = new AnimationEvent();
      }
      // set scheduling step and step (this is requried for undo)
      if (aEvent.getSchedulingStep() == 0) {
        aEvent.setSchedulingStep(currentStep);
      }
      if (aEvent.getStep() == 0)  {
        aEvent.setStep(currentStep);
      }
      // deactivate
      aEntry.deactivate(aEvent);
      // treat consequences
      this.treatTerminationConsequences(aEntry, aEvent);

    }
  }

  //////////////////// find ////////////////////////

  // this function searches for a entry with the given id
   private AnimationEntry findEntryById (long entryId)
  {
    Enumeration en = animationElements.elements();
    AnimationEntry abstractEntry;
    long id;
    for(; en.hasMoreElements();)
    {
      abstractEntry = (AnimationEntry)en.nextElement();
      id = abstractEntry.getIXEntry().getId();
      if (id == entryId)
        return abstractEntry;
    }
    return null;
  }

  private void advanceStep()  {
    currentStep++;
    this.guiPanel.showAnimationStatus(1, currentStep);
  }

  private void retreatStep(long numberOfSteps)  {
    currentStep-=numberOfSteps;
    this.guiPanel.showAnimationStatus(2, currentStep);
  }

  //////////////////// first step ////////////////////////
  // this function is called when the animation starts running
  // it initiates the objects according to object initiation rules
  public void firstStep ()
  {
    // return if pause mode
    if (animationTimer.isPause()) {
      return;
    }
    Enumeration en = animationElements.elements();
    AnimationEntry aEntry;
    advanceStep();
    for(; en.hasMoreElements();)
    {
      aEntry = (AnimationEntry)en.nextElement();
      // initially objects and states are activated without any trigger
      if (AnimationSettings.AUTOMATIC_INITIATION) {
        if (AnimationAuxiliary.isObject(aEntry.getIXEntry()))  {
          // activate the object only if it is not inside a zoomed in process
          if (!aEntry.getParents().hasMoreElements()) {
            initiateObject(aEntry);
          }
        }
      }
    }
  }

  // this function checks whether the object can be activated
  // according to the initial activation rules
  // and activates it if it passes the validation
  private void initiateObject(AnimationEntry aObject) {
    if (checkObjectBeforeActivate(aObject)) {
      // object is initiated with the default number of instances
      // which is specified in MAS folder
      int insNum = aObject.getDefaultNumberOfInstances();
      if (insNum <= 1) {
        if (AnimationSettings.ONE_OBJECT_INSTANCE)  {
          insNum = 1;   // replace with default
        }
        else  {
          insNum = AnimationSettings.MAX_OBJECT_INSTANCES;   // replace with default
        }
      }
      AnimationEvent actvEvent = new AnimationEvent();
      actvEvent.setStep(currentStep);
      actvEvent.setNumberOfInstances(insNum);
      activate(aObject, actvEvent);
    }
  }

  //////////////////// next step ////////////////////////
  // this function is called whenever animation performes a recomputation
  // consistent with the configured step duration
  // it goes through the list of terminations scheduled to the current step
  // and performs the events that pass the validations
  // then it treats the activations scheduled to the current step
  public void nextStep ()
  {
    // if animation is paused - do nothing
    if (animationTimer.isPause()) {
      return;
    }
    Enumeration en = animationElements.elements();
    AnimationEntry aEntry;
    advanceStep();
    // go through the animation elements
    // and retrieve a list of scheduled events (first terminations and then
    // activations for each element
    for(; en.hasMoreElements();)
    {
      aEntry = (AnimationEntry)en.nextElement();
      //this was added to debug condition link bug
      if(AnimationAuxiliary.isProcess(aEntry.getIXEntry())){
      //String s = ((IXStateEntry)aEntry.getIXEntry()).getParentIXObjectEntry().getName().toString();
      	String s = aEntry.getIXEntry().getName().toString();
      int i=0;
      if(s.lastIndexOf("Transaction\nProcessing")>-1){
    		i++;
    		}
      }
      // get scheduled terminations
      Enumeration termEvents = aEntry.getScheduledTerminations(currentStep);
      for(; termEvents.hasMoreElements();)  {
        // treat termination event
        AnimationEvent currentEvent = (AnimationEvent)termEvents.nextElement();
        // deactivate the entry if the event is not cancelled and the entry
        // passes termination conditions
        if ((currentEvent.getCancelStep() == 0) &&
             this.checkBeforeTerminate(aEntry, currentEvent)) {
          AnimationAuxiliary.debug("next Step: deactivate entry " + aEntry.getIXEntry().getName());
          deactivate(aEntry, currentEvent);
        }
      }
      // get scheduled activations
      Enumeration actvEvents = aEntry.getScheduledActivations(currentStep);
      for(; actvEvents.hasMoreElements();)  {
        AnimationEvent currentEvent = (AnimationEvent)actvEvents.nextElement();
        synchronized (currentEvent) {
          // set the indication that the process is to be activated in parents context
          AnimationEntry oldParent = aEntry.getCurrentEvent().getParentProcess();
          aEntry.getCurrentEvent().setParentProcess(currentEvent.getParentProcess());
          // activate the entry if the event is not cancelled and the entry
          // passes activation conditions
          // number of instances 0 indicates waiting for event (child process
          // inside a zoomed in OPD can be activated only by event, therefore
          // next step ignores it).
          Vector linkTypes = new Vector();
          linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
          linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
          linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
          linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
          
          if ((currentEvent.getCancelStep() == 0) &&(currentEvent.getNumberOfInstances() > 0)&&
              this.checkBeforeActivate(aEntry, currentEvent)) {
            AnimationAuxiliary.debug("next Step: activate entry " + aEntry.getIXEntry().getName());
            currentEvent.setWaitForEvent(false);
            activate(aEntry, currentEvent);
          }/*
          else if(AnimationAuxiliary.isProcess(aEntry.getIXEntry())){
          	if((this.getLinkByType((IXProcessEntry)aEntry.getIXEntry(),linkTypes,false)).size() == 0 && checkLinksBeforeActivate(aEntry, null)){
          	if(aEntry.getParents().nextElement()!=null){
          		if(((AnimationEntry)aEntry.getIXEntry()).isActive())
          		if (!this.findUnavailableCondition(aEntry)){
          		AnimationEvent aChildEventTemp = new AnimationEvent(currentEvent);
                aChildEventTemp.setRecurringInd(true);
                aChildEventTemp.setStep(currentStep+1);
                aChildEventTemp.setSchedulingStep(currentStep);
                aEntry.currentEvents.clear();
                aEntry.scheduleActivation(aChildEventTemp);
          		aEntry.getCurrentEvent().setWaitForEvent(false);
          		aChildEventTemp.setNumberOfInstances(aEntry.getNumberOfInstances());
          	}}}}*/
          else  {
            // if the entry hasn't pass the validations restore parent process
            aEntry.getCurrentEvent().setParentProcess(oldParent);
            // reschedule to the next step if the recurring ind is checked
            if (currentEvent.getRecurringInd()) {
              AnimationAuxiliary.debug("nextStep: reschedule activation for " + aEntry.getIXEntry().getName() + " to step " +  (currentStep+1));
              AnimationEvent aEvent = new AnimationEvent(currentEvent);
              aEvent.setSchedulingStep(currentStep);
              aEvent.setStep(currentStep + 1);
              aEntry.scheduleActivation(aEvent);
            }
          }
        } // end synchronized
      }
    }
  }


  //////////////////// validations before activation ////////////////////////
  private boolean checkBeforeActivate(AnimationEntry aEntry, AnimationEvent aEvent) {
    if (AnimationAuxiliary.isObject(aEntry.getIXEntry()))  {
      return checkObjectBeforeActivate(aEntry);
    }

    if (AnimationAuxiliary.isProcess(aEntry.getIXEntry())) {
      return checkProcessBeforeActivate(aEntry, aEvent);
    }

    if (AnimationAuxiliary.isLink(aEntry.getIXEntry())) {
      return checkLinkBeforeActivate((IXLinkEntry)aEntry.getIXEntry());
    }
    return false;
  }

  // this function checks whether object is valid to be activated
  // it is called only by initiation procedure
  // An object can be activated if it is not a result
  // and if it is not a feature of another object
  private boolean checkObjectBeforeActivate(AnimationEntry aEntry) {
    // result can't be active unless it is activated as a consequence
    IXObjectEntry iObject = (IXObjectEntry)aEntry.getIXEntry();
    if (isResult(iObject))  {
      return false;
    }
    // if the object is a feature of another object it can not be activated
    // unless as a consequence of root object activation
    if (aEntry.isFeature()) {
      return false;
    }
    return true;
  }

  // this function checks whether the given object is result
  // of some process. Object O is result if the following is true:
  // 1. There is a result link between  a process P to O or to some state of O
  //    and there is no consumption link from O or one of its states to P
  // 2. A generalization of O is a result.
  private boolean isResult(IXObjectEntry iObject)  {
    // initialize
    Vector allResultLinks = new Vector();
    Vector allConsumptionLinks = new Vector();
    Vector linkTypesResults = new Vector();
    linkTypesResults.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
    Vector linkTypesConsumption = new Vector();
    linkTypesConsumption.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
    linkTypesConsumption.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
    boolean isResult = true;

    // get own incoming result links
    allResultLinks.addAll(this.getLinkByType(this.getLinksByDestination(iObject), linkTypesResults));

    // get own outgoing consumption links
    allConsumptionLinks.addAll(getLinkByType(getLinksBySource(iObject), linkTypesConsumption));

    // get incoming result links and outgoing consumption links of all states
    Enumeration enStates = iObject.getStates();
    for(; enStates.hasMoreElements();)  {
      IXStateEntry iState = (IXStateEntry) enStates.nextElement();
      // get list of result links incoming to the state
      allResultLinks.addAll(getLinkByType(getLinksByDestination(iState), linkTypesResults));
      allConsumptionLinks.addAll(getLinkByType(getLinksBySource(iState), linkTypesConsumption));
    }

    // go through both link lists
    for (int i=0;i<allResultLinks.size();i++) {
      IXLinkEntry iResultLink = (IXLinkEntry)allResultLinks.get(i);
      isResult = true;
      // for each result link find corresponding consumption link
      // if not found return true - the object is result
      for (int j=0;j<allConsumptionLinks.size() && isResult;j++) {
        IXLinkEntry iConsumptionLink = (IXLinkEntry)allConsumptionLinks.get(j);
        if (iResultLink.getSourceId() == iConsumptionLink.getDestinationId()) {
          // found pair of consumption and result
          isResult = false;
        }
      }
      // found result link without corresponding consumption link - return true
      // the object is a result
      if (isResult) {
        return true;
      }
    }

    // check if object's generalization is a product
    Enumeration en = iObject.getRelationByDestination();
    for (;en.hasMoreElements();) {
      IXRelationEntry relation = (IXRelationEntry)en.nextElement();
      if (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION) {
        // check if relations source is result, by this recursive call
        if (isResult((IXObjectEntry)findEntryById(relation.getSourceId()).getIXEntry())) {
          return true;
        }
      }
    }
    return false;
  }

  // this function checks whether process can be activated
  // it incorporates the following validations
  // ***Validations relevant for a child process inside zoomed in OPD***
  // 1. Parent Process should be active
  // 2. If process is activated by an external event, it is allowed to be activated
  //    only if parent process waits for this event
  // ***Validations relevant for all processes***
  // 1. If the process was triggered by event (instrument event link or
  //    consumption event link) the source of this event should be active
  //    and if this event is marked with a path label, this path should be valid
  // 2. Incoming links (instrument, comnsumption, effect and condition) should
  //    constitute a valid combination consistent with defined paths and
  //    logical conditions (not supported yet)
  //LERA!!! - adding logical conditions is here!!!!!!!!
  private boolean checkProcessBeforeActivate(AnimationEntry aProcess, AnimationEvent aEvent)  {
    AnimationAuxiliary.debug("Starting checkProcessBeforeActivate for " + aProcess.getIXEntry().getName());
    // if the process is a child process inside a zoomed in process
    // it can be activated only if the parent is active
    Enumeration enParents = aProcess.getParents();
    AnimationEntry aParent = null;
    AnimationEntry oldParent = aProcess.getCurrentEvent().getParentProcess();
    AnimationAuxiliary.debug("oldParent = " + oldParent);
    // future enhancement: support multiple parents
    if(enParents.hasMoreElements())  {
      IXProcessEntry parent = (IXProcessEntry)enParents.nextElement();
      aParent = findEntryById(parent.getId());
      // if found a not active parent return false
      if (!aParent.isActive())  {
        return false;
      }

      // check if the process was triggered by internal event
      boolean isInternalEvent = isTriggeredByInternalEvent(aProcess, aParent, aEvent);

      // if the process activation was triggered from activateLevel function
      // or from nextStep function the waitForEvent ind will be switched on
      // if it is not switched on - the process was triggered by an external
      // event. In this case we should check whether the parent waits for this
      // event
      if (!aProcess.getCurrentEvent().getWaitForEvent() && !isInternalEvent)  {
//      if ((aEvent != null) && (aEvent.getEventLink() != null) && !isInternalEvent)  {
        AnimationAuxiliary.debug("wait for event " + aProcess.getCurrentEvent().getWaitForEvent());
        // process can be activated by event only if parent process waits for
        // this event, i.e. a future activation is scheduled
        Enumeration enEvents = aProcess.getScheduledActivations(currentStep);
        AnimationAuxiliary.debug("getScheduledActivations for step " + currentStep);
        boolean found = false;
        for (;enEvents.hasMoreElements();) {
          AnimationEvent aEventTemp = (AnimationEvent)enEvents.nextElement();
          AnimationAuxiliary.debug("aEventTemp.getExecutionId() = " + aEventTemp.getExecutionId());
          // find the active parent according to the exceution id
          for (Enumeration enP = aProcess.getParents();enP.hasMoreElements();)  {
            IXEntry iParent = (IXEntry)enP.nextElement();
            AnimationEntry aParentTemp = findEntryById(iParent.getId());
            if (aParentTemp.isActive(aEventTemp.getExecutionId()))  {
//            if (aEventTemp.getExecutionId() == aParent.getExecutionId())  {
              aParent = aParentTemp;
              found = true;
            }
          }
        }

        // parent process do not wait for this event - return false
        // unless the event is internal
        if (!found) {
          AnimationAuxiliary.debug("Could not find scheduled activation");
          return false;
        }

        // Parent process should be set here, because it is used further is this function
        // to obtain the links of the process, including those inherited from parent
        aProcess.getCurrentEvent().setParentProcess(aParent);
        aProcess.getCurrentEvent().setExecutionId(aParent.getExecutionId());
        AnimationAuxiliary.debug("setExecutionId " + aParent.getExecutionId());
      }

      // copy parent and execution id to current event
      // they are used further
      if (aEvent != null) {
        if ((aEvent.getEventLink() != null) && (aProcess.getCurrentEvent().getParentProcess() != null))  {
           // copy parent and execution id from the process
          aEvent.setParentProcess(aProcess.getCurrentEvent().getParentProcess());
          AnimationAuxiliary.debug("aEvent.setParentProcess " + aProcess.getCurrentEvent().getParentProcess());
          if (!isInternalEvent) {
            aEvent.setExecutionId(aProcess.getCurrentEvent().getExecutionId());
          }

        }
      }
    }

    // check the incoming links, a valid combination should exist
    // in accordance with path and logical relations (if defined)
    // if no path/logical relations are defined
    // all links should be valid.
    boolean pathExist = false;
    Vector linkTypes = new Vector();

    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));

    // check if the process was activated by event
    if (aEvent != null) {
      if (aEvent.getEventLink() != null)  {
        IXLinkEntry triggerEvent = (IXLinkEntry)aEvent.getEventLink().getIXEntry();
        int linkType = triggerEvent.getLinkType();
        // if the process was activated by consumption or instrument event link
        // check that the object on the second end of this link is active
        if ((linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK) ||
            (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK))  {
          linkTypes.add(new Integer(linkType));
        }
        // if the process was activated by event
        // and there is a path defined for this event - 
        // this path should be valid
        if (!triggerEvent.getPath().equals("")) {
          if (checkPath(triggerEvent.getPath(), getLinksByDestination((IXProcessEntry)aProcess.getIXEntry(), linkTypes)))  {
            AnimationAuxiliary.debug("After checkPathDestination for trigger event " + triggerEvent.getName());
            // store valid path on the process
            aProcess.setPath(triggerEvent.getPath());
            AnimationAuxiliary.debug("Set path " + triggerEvent.getPath());
            return  true;
          }
          else  {
            // return false if the path of the trigger is not valid
            return false;
          }
        }
      }
    }


    // check if incoming links allow process activation
    // if a path is defined for at least one of the links - this path should be valid
    // all links without path should be valid, unless there is a logical condition
    Vector v = this.getProcessLinks((IXProcessEntry)aProcess.getIXEntry(), linkTypes, false);
    for(Enumeration en=v.elements(); en.hasMoreElements();)
    {
      IXLinkEntry iLinkEntry = (IXLinkEntry)en.nextElement();
      if (!iLinkEntry.getPath().equals("")) {
        // this variable indicates if atleast one of the links has path
        pathExist = true;
      }
      AnimationAuxiliary.debug("Before checkLinkBeforeActivate for link " + iLinkEntry.getName());
      if (checkLinkBeforeActivate(iLinkEntry))  {
        AnimationAuxiliary.debug("After checkLinkBeforeActivate for link " + iLinkEntry.getName());
        // At least one path should be valid
        if (!iLinkEntry.getPath().equals("")) {
          AnimationAuxiliary.debug("Before checkPathDestination for link " + iLinkEntry.getName());
          if (checkPath(iLinkEntry.getPath(), v.elements()))  {
            AnimationAuxiliary.debug("After checkPathDestination for link " + iLinkEntry.getName());
            // store valid path on the process
            aProcess.setPath(iLinkEntry.getPath());
            AnimationAuxiliary.debug("Set path " + iLinkEntry.getPath());
            return  true;
          }
        }
      }
      else  {
        // if link's path is empty - the link is mandatory
        // return false if it is not available
        // if link has a path - don't return false meanwhile
        // because may be another path will be found valid
        if (iLinkEntry.getPath().equals("")) {
          // revert old parent and return
          aProcess.getCurrentEvent().setParentProcess(oldParent);
          return false ;
        }
         // check logical operators. May be there is another link
        // is available
        //return false;
      }
    }
    // if atleast one link has a path - the process can be activated
    // only if one of the paths is valid.
    // if the execution acheives this point - no valid path exist
    // therefore the process can not be activated
    if (pathExist)  {
      // revert ald parent and return
      aProcess.getCurrentEvent().setParentProcess(oldParent);
      return false;
    }

    // there is no path and all links without path are valid
    // store empty path
    aProcess.setPath("");
    return true;
  }

  private boolean checkLinksBeforeActivate(AnimationEntry aProcess, AnimationEvent aEvent)  {
    AnimationAuxiliary.debug("Starting checkProcessBeforeActivate for " + aProcess.getIXEntry().getName());
    // if the process is a child process inside a zoomed in process
    // it can be activated only if the parent is active
    Enumeration enParents = aProcess.getParents();
    AnimationEntry aParent = null;
    AnimationEntry oldParent = aProcess.getCurrentEvent().getParentProcess();
    AnimationAuxiliary.debug("oldParent = " + oldParent);
    // future enhancement: support multiple parents
    if(enParents.hasMoreElements())  {
      IXProcessEntry parent = (IXProcessEntry)enParents.nextElement();
      aParent = findEntryById(parent.getId());
      // if found a not active parent return false
      if (!aParent.isActive())  {
        return false;
      }

      // check if the process was triggered by internal event
      boolean isInternalEvent = isTriggeredByInternalEvent(aProcess, aParent, aEvent);

      // if the process activation was triggered from activateLevel function
      // or from nextStep function the waitForEvent ind will be switched on
      // if it is not switched on - the process was triggered by an external
      // event. In this case we should check whether the parent waits for this
      // event
      if (!isInternalEvent)  {
//      if ((aEvent != null) && (aEvent.getEventLink() != null) && !isInternalEvent)  {
        AnimationAuxiliary.debug("wait for event " + aProcess.getCurrentEvent().getWaitForEvent());
        // process can be activated by event only if parent process waits for
        // this event, i.e. a future activation is scheduled
        Enumeration enEvents = aProcess.getScheduledActivations(currentStep);
        AnimationAuxiliary.debug("getScheduledActivations for step " + currentStep);
        boolean found = false;
        for (;enEvents.hasMoreElements();) {
          AnimationEvent aEventTemp = (AnimationEvent)enEvents.nextElement();
          AnimationAuxiliary.debug("aEventTemp.getExecutionId() = " + aEventTemp.getExecutionId());
          // find the active parent according to the exceution id
          for (Enumeration enP = aProcess.getParents();enP.hasMoreElements();)  {
            IXEntry iParent = (IXEntry)enP.nextElement();
            AnimationEntry aParentTemp = findEntryById(iParent.getId());
            if (aParentTemp.isActive(aEventTemp.getExecutionId()))  {
//            if (aEventTemp.getExecutionId() == aParent.getExecutionId())  {
              aParent = aParentTemp;
              found = true;
            }
          }
        }

        // parent process do not wait for this event - return false
        // unless the event is internal
        if (!found) {
          AnimationAuxiliary.debug("Could not find scheduled activation");
          return false;
        }

        // Parent process should be set here, because it is used further is this function
        // to obtain the links of the process, including those inherited from parent
        aProcess.getCurrentEvent().setParentProcess(aParent);
        aProcess.getCurrentEvent().setExecutionId(aParent.getExecutionId());
        AnimationAuxiliary.debug("setExecutionId " + aParent.getExecutionId());
      }

      // copy parent and execution id to current event
      // they are used further
      if (aEvent != null) {
        if ((aEvent.getEventLink() != null) && (aProcess.getCurrentEvent().getParentProcess() != null))  {
           // copy parent and execution id from the process
          aEvent.setParentProcess(aProcess.getCurrentEvent().getParentProcess());
          AnimationAuxiliary.debug("aEvent.setParentProcess " + aProcess.getCurrentEvent().getParentProcess());
          if (!isInternalEvent) {
            aEvent.setExecutionId(aProcess.getCurrentEvent().getExecutionId());
          }

        }
      }
    }

    // check the incoming links, a valid combination should exist
    // in accordance with path and logical relations (if defined)
    // if no path/logical relations are defined
    // all links should be valid.
    boolean pathExist = false;
    Vector linkTypes = new Vector();

    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.AGENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));

    // check if the process was activated by event
    


    // check if incoming links allow process activation
    // if a path is defined for atleast one of the links - this path should be valid
    // all links without path should be valid, unless there is a logical condition
    Vector v = this.getProcessLinks((IXProcessEntry)aProcess.getIXEntry(), linkTypes, false);
    for(Enumeration en=v.elements(); en.hasMoreElements();)
    {
      IXLinkEntry iLinkEntry = (IXLinkEntry)en.nextElement();
      if (!iLinkEntry.getPath().equals("")) {
        // this variable indicates if atleast one of the links has path
        pathExist = true;
      }
      AnimationAuxiliary.debug("Before checkLinkBeforeActivate for link " + iLinkEntry.getName());
      if (checkLinkBeforeActivate(iLinkEntry))  {
        AnimationAuxiliary.debug("After checkLinkBeforeActivate for link " + iLinkEntry.getName());
        // At least one path should be valid
        if (!iLinkEntry.getPath().equals("")) {
          AnimationAuxiliary.debug("Before checkPathDestination for link " + iLinkEntry.getName());
          if (checkPath(iLinkEntry.getPath(), v.elements()))  {
            AnimationAuxiliary.debug("After checkPathDestination for link " + iLinkEntry.getName());
            // store valid path on the process
            aProcess.setPath(iLinkEntry.getPath());
            AnimationAuxiliary.debug("Set path " + iLinkEntry.getPath());
            return  true;
          }
        }
      }
      else  {
        // if link's path is empty - the link is mandatory
        // return false if it is not available
        // if link has a path - don't return false meanwhile
        // because may be another path will be found valid
        if (iLinkEntry.getPath().equals("")) {
          // revert old parent and return
          aProcess.getCurrentEvent().setParentProcess(oldParent);
          return false ;
        }
         // check logical operators. May be there is another link
        // is available
        //return false;
      }
    }
    // if atleast one link has a path - the process can be activated
    // only if one of the paths is valid.
    // if the execution acheives this point - no valid path exist
    // therefore the process can not be activated
    if (pathExist)  {
      // revert ald parent and return
      aProcess.getCurrentEvent().setParentProcess(oldParent);
      return false;
    }

    // there is no path and all links without path are valid
    // store empty path
    aProcess.setPath("");
    return true;
  }
  
  // This function checks whether link can be activated
  // It incorporates the following validations:
  // 1. Link source should be active
  // 2. If there is a path stored on the source of the link, then the link
  //    can be activated only if it has the same path
  private boolean checkLinkBeforeActivate(IXLinkEntry iLinkEntry)  {
    int linkType = iLinkEntry.getLinkType();  // link Type
    AnimationEntry aSrc= findEntryById(iLinkEntry.getSourceId()); // link source

    // if link is enabler its source should be active
    if ((linkType == exportedAPI.OpcatConstants.AGENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.CONDITION_LINK) ||
        (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.CONSUMPTION_LINK) ||
        (linkType == exportedAPI.OpcatConstants.EFFECT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK) ||
        (linkType == exportedAPI.OpcatConstants.INSTRUMENT_LINK)) {
        if (!aSrc.isActive()) {
          // return false if source is not active - link can't be activated
          AnimationAuxiliary.debug("checkLinkBeforeActivate: source is not active");
          return false;
        }
    }
    // link path should be same as source path (if source path is not empty)
    if ((aSrc.getPath().equals(""))  ||
      (iLinkEntry.getPath().equals(aSrc.getPath()))) {
    }
    else  {
      AnimationAuxiliary.debug("checkLinkBeforeActivate: source path isn ot empty");
      return false;
    }
    return true;
  }

  //////////////////// Treat activation Consequences ////////////////////////

  private void treatActivationConsequences(AnimationEntry activatedEntry, AnimationEvent aEvent) {
    if (AnimationAuxiliary.isEvent(activatedEntry.getIXEntry())) {
      treatEventActivationConsequences((IXLinkEntry)activatedEntry.getIXEntry(), aEvent);
      return;
    }
    if (AnimationAuxiliary.isProcess(activatedEntry.getIXEntry())) {
      treatProcessActivationConsequences(activatedEntry, aEvent);
      return;
    }
    if (AnimationAuxiliary.isObject(activatedEntry.getIXEntry())) {
      treatObjectActivationConsequences(activatedEntry);
      return;
    }
    if (AnimationAuxiliary.isState(activatedEntry.getIXEntry())) {
      treatStateActivationConsequences((IXStateEntry)activatedEntry.getIXEntry());
      return;
    }
    if (AnimationAuxiliary.isConditionLink(activatedEntry.getIXEntry())) {
    	treatConditionLinkConsequences((IXLinkEntry)activatedEntry.getIXEntry());
        return;
      }
  }
  
  /**
   * this function was added to try to solve a bug wiht conditions.
   * condition links where not functioning properly.(didnt help)
   * @param entry
   */
  private void treatConditionLinkConsequences(IXLinkEntry entry) {
  	/*AnimationEntry Destination = findEntryById(entry.getDestinationId());
  	AnimationEvent currentEvent = new AnimationEvent();
  	currentEvent.setStep(currentStep+1);
  	currentEvent.setSchedulingStep(currentStep);
  	Destination.futureActivationScheduled(currentStep,currentEvent.getExecutionId());*/
  }
  // this function handles consequences of event links activation
  // it goes through the list of animation elements and activates the processes
  // connected to the event that pass the validations.
  // if reaction time is defined for the event, it schedules the activation after
  // the reaction time
  // the function also handles the reaction time out exception, which are not
  // supported yet by OPCAT
  private void treatEventActivationConsequences(IXLinkEntry activatedEvent, AnimationEvent currentEvent) {
    if (currentEvent == null) {
      currentEvent = new AnimationEvent();
    }
    AnimationAuxiliary.debug("Starting treatEventActivationConsequences for " + activatedEvent.getName());
    AnimationAuxiliary.debug("current event exec id = " + currentEvent.getExecutionId());
    // try to activate all corresponding processes
    Enumeration en = animationElements.elements();
    AnimationEntry aProcess, aEventLink = findEntryById(activatedEvent.getId());
    IXLinkEntry link;
    long reactionTime = aEventLink.getDuration();
    for(; en.hasMoreElements();)
    {
      aProcess = (AnimationEntry)en.nextElement();
      if (AnimationAuxiliary.isProcess(aProcess.getIXEntry())) {
        if (activatedEvent.getDestinationId() == aProcess.getIXEntry().getId())  {
          // if the event is connected to one of the descendant  of the process
          // it should not activate the parent
          if  (isDescendantLink(activatedEvent, aProcess, false))  {
            continue;
          }

          // if process that triggers this process is its child
          // nullify the parent passed from child
          try {
            if (currentEvent.getParentProcess().equals(aProcess)) {
              currentEvent.setParentProcess(null);
              AnimationAuxiliary.debug("treatEventActivationConsequences: currentEvent.setParentProcess(null)");
            }
          }
          catch (NullPointerException ex) {}

          // if no reaction time is defined - try to activate the process
          // immediately
          if (reactionTime == 0)  {
            // store the link that tries to activate the process
            currentEvent.setEventLink(aEventLink);
            // activate the process if it passes the validations
            if (this.checkProcessBeforeActivate(aProcess, currentEvent))  {
            /*
              // copy parent and execution id from the process
              currentEvent.setParentProcess(aProcess.getCurrentEvent().getParentProcess());
//              if (currentEvent.getExecutionId() == 0) {
              if (!this.isTriggeredByInternalEvent(aProcess, currentEvent.getParentProcess(), currentEvent )) {
                currentEvent.setExecutionId(aProcess.getCurrentEvent().getExecutionId());
              } MOVED TO checkProcessBeforeActivate */
              // activate the process
              activate(aProcess, currentEvent);
            }
          }
          else  {
            // schedule future activation of the process after the reaction time
            // the system will try to activate the process even if the reaction
            // time constraints do not hold.
            //AnimationEvent actvEvent = new AnimationEvent((currentStep + this.computeStep(reactionTime)), aEventLink.getPath(), 0,1, currentStep);
            currentEvent.setStep(currentStep + this.computeStep(reactionTime));
            currentEvent.setPath(aEventLink.getPath());
            currentEvent.setSchedulingStep(currentStep);

            // store the link that tries to activate the process
            currentEvent.setEventLink(aEventLink);
            aProcess.scheduleActivation(currentEvent);

            // treat reaction time out exceptions if they are defined
            Enumeration exceptions = findTimeOutException(aEventLink);
            // if found exceptions compare reaction time to min and max limits
            // Note: reaction time out exceptions are not supported yet
            // therefore they have not been tested
            for(; exceptions.hasMoreElements();)  {
              IXLinkEntry iLink = (IXLinkEntry) exceptions.nextElement();
              AnimationEntry aExceptionLink = findEntryById(iLink.getId());
              // reaction time out exception link exists
              if (reactionTime < aEventLink.getMinTime())  {
                // if reaction time is less than minimum -
                // schedule exception activation after reaction time
                aExceptionLink.scheduleActivation(currentStep, this.computeStep(reactionTime));
              }
              if (reactionTime > aEventLink.getMaxTime()) {
                // if reaction time is greater than maximum
                // schedule exception activation after max time
                aExceptionLink.scheduleActivation(currentStep, this.computeStep(aEventLink.getMaxTime()));
              }
            }
          }
        }
      }
    }  //for

    // now deactivate the event
    findEntryById(activatedEvent.getId()).deactivate(new AnimationEvent());
  }

  // this function handles process activation consequences:
  // 1. schedules future termination of the process, unless the process is zoomed in
  // 2. deactivates consumed objects/states consistent with path rules
  // 3. animates result and effect links
  // 4. For zoomed in processes: activates the objects and first level processes
  //    inside zoomed in OPD.
  private void treatProcessActivationConsequences(AnimationEntry aProcess, AnimationEvent currentEvent) {
    AnimationAuxiliary.debug("Starting treatProcessActivationConsequences for " + aProcess.getIXEntry().getName());

    IXProcessEntry activatedProcess = (IXProcessEntry)aProcess.getIXEntry();
    long duration = aProcess.getDuration();
    AnimationEvent aEvent = new AnimationEvent();
    aEvent.setDuration(duration);
    aEvent.setExecutionId(currentEvent.getExecutionId());

    // schedule future termination
    // if process has child process it will be terminated upon
    // termination of the last child
    if (aProcess.isLowLevel())  {
      try {
        AnimationEvent aChildEvent = new AnimationEvent(currentEvent);
        aChildEvent.setSchedulingStep(currentStep);
        aChildEvent.setStep(currentStep + computeStep(duration));
        aProcess.scheduleTermination(aChildEvent);
      }
      catch (Exception e) {
      }
    }

    // deactivate consumed objects/states
    Vector linkTypes = new Vector();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
    DeactivateConsumedEntriesTask task1 = new DeactivateConsumedEntriesTask(aProcess, aEvent);
    AnimationAuxiliary.debug("Calling treatLinkBySource for DeactivateConsumedEntriesTask");
    this.validateAndTreatLinkByDestination(activatedProcess, linkTypes, task1);

    // animate results
    linkTypes.clear();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));
    AnimateResultsTask task2 = new AnimateResultsTask(aProcess, aEvent);
    AnimationAuxiliary.debug("Calling treatLinkBySource for AnimateResultsTask");
    this.treatLinkBySource(activatedProcess, linkTypes, task2);

    // animate effect links
    linkTypes.clear();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));
    this.treatLinkByDestination(activatedProcess, linkTypes, task2);

    // if the process is invoked by internal event deactivate the
    // objects inside it and then initiate them again
    if (isTriggeredByInternalEvent(aProcess, currentEvent.getParentProcess(), currentEvent)) {
      this.deactivateChildObjects(aProcess);
    }

    // activate objects (not results) inside zoomed in process
    Enumeration en = (aProcess.getChildrenObjects()).elements();
    for(; en.hasMoreElements();) {
      IXObjectEntry childObject = (IXObjectEntry) en.nextElement();
      initiateObject(findEntryById(childObject.getId()));
    }

    // activate first descendant process (if exists) inside zoomed in process
    AnimationEvent aChildEvent = new AnimationEvent();
    aChildEvent.setParentProcess(aProcess);
    aChildEvent.setExecutionId(currentEvent.getExecutionId());
    en = activatedProcess.getInstances();
    for(; en.hasMoreElements();) {
      IXProcessInstance activatedProcessInstance = (IXProcessInstance) en.nextElement();
      Enumeration firstLevelChildren = aProcess.getFirstLevelChildren(activatedProcessInstance);
      // show OPD that contains the child process
      if (AnimationSettings.MOVE_BETWEEN_OPD) {
        if (firstLevelChildren.hasMoreElements()) {
          long childOpdId = ((IXProcessInstance)firstLevelChildren.nextElement()).getKey().getOpdId();
          long currentOpd = mySys.getCurrentIXOpd().getOpdId();
          if (childOpdId != currentOpd) {
            mySys.showOPD(childOpdId);
            opdStack.push(new Long(currentOpd));
            AnimationAuxiliary.debug("treatProcessActivationConsequences for " + activatedProcess.getName() + ": push " + currentOpd);
          }
        }
      }
      activateLevel(aProcess.getFirstLevelChildren(activatedProcessInstance), aChildEvent);
    }

    // treat timeouts
    treatTimeoutsUponActivation(aProcess);

    // if activated process is a specialization of another process
    // activate its generalization as well
    AnimationEvent aEventTemp = new AnimationEvent(currentEvent);
    aEventTemp.setEventType(AnimationSettings.ACTIVATION_EVENT);
    treatGeneralization(activatedProcess, aEventTemp );
  }

  // this function treats object activation consequences:
  // 1. activates the initial state
  // 2. if no initial state is defined - activates any state randomly
  // 3. if the object has no states treates the instrument/consumption event links
  // 4. activates related objects generalization and exhibitions
  private void treatObjectActivationConsequences(AnimationEntry aObject) {
    IXObjectEntry activatedObject = (IXObjectEntry)aObject.getIXEntry();
    AnimationAuxiliary.debug("Starting treatObjectActivationConsequences for " + activatedObject.getName());
    IXObjectInstance objIns = (IXObjectInstance)(activatedObject.getInstances().nextElement());
    boolean hasActiveState = hasActiveState(activatedObject);
    boolean hasInitialState = false;
    Enumeration en = null;
    int statesCounter = 0;
    String s =aObject.getIXEntry().getName();
    int it=0;
    /*
     * Added to debug condition links problems
     */
    //if(s.lastIndexOf("Cash Card Is Valid?")>-1){
  	//	it++;
  	//	}

    // activate initial state if there is no another active state
    if (!hasActiveState) {
      en = objIns.getStateInstances();
      if (en.hasMoreElements()) {
        IXStateEntry state = null;
        for(; en.hasMoreElements();)  {
          state = (IXStateEntry)((IXStateInstance)en.nextElement()).getIXEntry();
          statesCounter++;
          if (state.isInitial())  {
            AnimationEntry aState = findEntryById(state.getId());
            activate(aState);
            hasInitialState = true;
          }
        }
        // activate some state randomly if no initial state is defined
        if (AnimationSettings.RANDOM_STATE_SELECTION) {
          if (!hasInitialState)  {
            int randomStateIndex = 0;
            if (statesCounter > 1) {
              randomStateIndex = AnimationAuxiliary.getRandomNumber(statesCounter);
            }
            AnimationAuxiliary.debug("States Counter = " + statesCounter + ", randomStateIndex = " + randomStateIndex);
            en = objIns.getStateInstances();
            for(int i=0; en.hasMoreElements();i++)  {
              state = (IXStateEntry)((IXStateInstance)en.nextElement()).getIXEntry();
              if (i==randomStateIndex)  {
                AnimationAuxiliary.debug("Activate State " + state.getName());
                activate(findEntryById(state.getId()));
              }
            }
          }
        }
      }
    }

    // if there are no states - treat event links (otherwise these is treated by
    // state activation consequences function
    if (!activatedObject.getStates().hasMoreElements()) {
      Vector linkTypes = new Vector();
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
      linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
      activateLinksBySource(activatedObject, linkTypes);
    }

    // if activated object is a specialization of another object
    // activate its generalization as well
    AnimationEvent aEventTemp = new AnimationEvent();
    aEventTemp.setEventType(AnimationSettings.ACTIVATION_EVENT);
    treatGeneralization(activatedObject, aEventTemp);

    // treat exhibition relations
    treatExhibitions(aObject, true);
  }

  // this function treats state activation consequences:
  // 1. Activates the parent object if it is not active yet
  // 2. schedules state timeout events
  // 3. treats consumption and instrument event links linked to the state
  // 4. treats consumption and instrument event links linked to the parent object
  //    (state change)
  private void treatStateActivationConsequences(IXStateEntry activatedState) {
    // activate parent object (if not active)
    IXObjectEntry parentObject = activatedState.getParentIXObjectEntry();
    AnimationEntry aParentObject = findEntryById(parentObject.getId());
    if (!aParentObject.isActive())  {
      this.activate(aParentObject);
    }
    /*
     * Added to debug condition links problems
     */
    //String s = activatedState.getParentIXObjectEntry().getName();
    //int i=0;
    //if(s.lastIndexOf("Cash Card Is Valid?")>-1){
  	//	i++;
  	//	}
    // schedule state time out exception
    AnimationEntry aState = findEntryById(activatedState.getId());
    treatTimeoutsUponActivation(aState);

    // Treat state-entrance events
    Vector linkTypes = new Vector();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
    //this one change to solve bug. condition links where not allways working.
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
    activateLinksBySource(activatedState, linkTypes);

    // treat state change events
    activateLinksBySource(parentObject, linkTypes);
  }

  // this function synchonizes activation of exhibition relation root and its features
  // when the root is activated/deactivated all its features are acticvated/deactivated
  // as well. Activation/deactivation of feature triggeres activation/deactivation
  // of the root.
  private void treatExhibitions(AnimationEntry aObject, boolean activateInd) {
    // if the object is a feature of another object activate/deactivate its parent
    // in this case WaitForEvent indicates that the object was not activated/deactivated
    // as a consequence of root process activation/deactivation
    if (!aObject.getCurrentEvent().getWaitForEvent()) {
      Vector relations = aObject.getRelationByDestionation(exportedAPI.OpcatConstants.EXHIBITION_RELATION);
      for (int i=0;i<relations.size();i++) {
        IXRelationEntry relation = (IXRelationEntry)relations.get(i);
        AnimationEntry aParent = findEntryById(relation.getSourceId());
        aObject.getCurrentEvent().setWaitForEvent(true);
        if (activateInd)  {
          activate(aParent);
        }
        else  {
          deactivate(aParent);
        }
        aObject.getCurrentEvent().setWaitForEvent(false);
      }
    }

    // if the object is a root of an exhibition relation -
    // activate/deactivate all its features
    Vector relations = aObject.getRelationBySource(exportedAPI.OpcatConstants.EXHIBITION_RELATION);
    for (int i=0;i<relations.size();i++) {
      IXRelationEntry relation = (IXRelationEntry)relations.get(i);
      AnimationEntry aFeature = findEntryById(relation.getDestinationId());
      if (!AnimationAuxiliary.isProcess(aFeature.getIXEntry())) {
        if (!aFeature.getCurrentEvent().getWaitForEvent())  {
          aFeature.getCurrentEvent().setWaitForEvent(true); // temporary indication
          if (activateInd)  {
            activate(aFeature);
          }
          else  {
            deactivate(aFeature);
          }
          aFeature.getCurrentEvent().setWaitForEvent(false); // temporary indication
        }
      }
    }
  }

  // This function sets the number of generalization instances upon activation/
  // deactivation of a specialization object. Generalization reflect the
  // total numebr of instances of all its specialization and of its own
   private void treatGeneralization(IXThingEntry iThing, AnimationEvent aEvent)  {
    Enumeration en = iThing.getRelationByDestination();
    for (;en.hasMoreElements();) {
      IXRelationEntry relation = (IXRelationEntry)en.nextElement();
      if (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION) {
        // activate relation's source - this is the generalization
        // don't check before activate in this case
        // activateGeneralization is invoked when a specialization is activated
        // in this case generalization should be valid
        AnimationEntry aGenEntry = findEntryById(relation.getSourceId());
        if (aEvent.getEventType() == AnimationSettings.ACTIVATION_EVENT)  {
          aGenEntry.setNumberOfSpecializations(aGenEntry.getNumberOfSpecializations() + aEvent.getNumberOfInstances());
        }
        else  {
          aGenEntry.setNumberOfSpecializations(aGenEntry.getNumberOfSpecializations() - aEvent.getNumberOfInstances());
        }
      }
    }
   }

   ///////////// Validations before termination //////////////

  private boolean checkBeforeTerminate(AnimationEntry aEntry, AnimationEvent aEvent) {
    // Validations before termination are relevant mean while only for process
    if (AnimationAuxiliary.isProcess(aEntry.getIXEntry())) {
      return checkProcessBeforeTerminate(aEntry, aEvent);
    }
    return true;
  }

  // process can be terminated only if all child processes have been terminated
  // if child process could not be started because of any reason, except
  // insufficient condition link (e.g. missing instrument), it will have
  // a future activation event that will try to activate the child on the next step.
  private boolean checkProcessBeforeTerminate(AnimationEntry aProcess, AnimationEvent aEvent)  {
    AnimationAuxiliary.debug("Starting checkProcessBeforeTerminate for " + aProcess.getIXEntry().getName());
    // check if any child is active or has future events
    IXProcessEntry iProcess = (IXProcessEntry)aProcess.getIXEntry();
    Enumeration children = aProcess.getChildrenProcesses().elements();
    for(; children.hasMoreElements();) {
      IXEntry childEntry = (IXEntry)children.nextElement();
      AnimationEntry aChildEntry = findEntryById(childEntry.getId());
      // can not terminate if there is active child
      // or there is a termination scheduled for child
      // or child waits for event
      if (aChildEntry.isActive(aEvent.getExecutionId()) ||
          aChildEntry.futureActivationScheduled(currentStep, aEvent.getExecutionId())) {
        return false;
      }
    }
    // check if the process itself waits for future activation by internal event
    // this is requried to support internal activation with reaction time
    AnimationEvent aEventTemp = aProcess.actvScheduler.futureEventsScheduled(currentStep, aEvent.getExecutionId());
    if (aEventTemp != null) {
      AnimationAuxiliary.debug("Larisa: found future activation");
      if (this.isTriggeredByInternalEvent(aProcess, aEventTemp.getParentProcess(), aEventTemp)) {
        AnimationAuxiliary.debug("Larisa: triggered by internal event");
        return false;
      }
    }

    return true;
  }

  //////////////////// Auxiliary Functions ////////////////////////

  // this function checks whether there is a link identical to the given link
  // linked to one of the direct descendants of the given process.
  // srcInd specifies whether source or destination links should be considered
  private boolean isDescendantLink(IXLinkEntry iLink, AnimationEntry aProcess, boolean srcInd)  {
    // get child processes
    //Vector descendants = getAllDescendantProcesses(aProcess);
    Vector descendants = aProcess.getChildrenProcesses();

    AnimationAuxiliary.debug("Printing list of descendants");
    AnimationAuxiliary.debug(descendants);

    // go through the list of child processes
    for(int j=0;j<descendants.size();j++) {
      IXProcessEntry iDescProcess = (IXProcessEntry)descendants.get(j);
      Enumeration enDescLinks = null;

      // get descendant's links
      Vector linkTypes = new Vector();
      linkTypes.add(new Integer(iLink.getLinkType()));
      enDescLinks = this.getLinkByType(iDescProcess, linkTypes, srcInd).elements();

      // look for a link identical to the process own link
      // among the links of descendant
      for (;enDescLinks.hasMoreElements();) {
        IXLinkEntry iDescLink = (IXLinkEntry)enDescLinks.nextElement();
        if (isIdentical(iLink, iDescLink, srcInd)) {
          return true;
        }
      }
    }
    return false;
  }

  // this function checks whether childThing is a specialization of parentThing
  // or of one of its specializations
  // it applies also for aggregation and instantiation relations
  private boolean isSpecialization (IXThingEntry parentThing, IXThingEntry childThing)  {
    AnimationAuxiliary.debug("Starting isSpecialization, parent " + parentThing.getName() + ", child " + childThing.getName());
    Enumeration enRelations = parentThing.getRelationBySource();
    for (;enRelations.hasMoreElements();) {
      IXRelationEntry relation = (IXRelationEntry)enRelations.nextElement();
      // if found that childThing is indeed a specialization of parentThing
      // return true
      if ((relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION) ||
          (relation.getRelationType() == exportedAPI.OpcatConstants.AGGREGATION_RELATION) ||
          (relation.getRelationType() == exportedAPI.OpcatConstants.INSTANTINATION_RELATION)) {
        if (relation.getDestinationId() == childThing.getId())  {
          AnimationAuxiliary.debug("isSpecialization returns true 1");
          AnimationAuxiliary.debug("for parent " + parentThing.getName() + ", child " + childThing.getName());
          return true;
        }

        //call the function recusively for relation's destination
        AnimationEntry temp = findEntryById(relation.getDestinationId());
        if (isSpecialization((IXThingEntry)temp.getIXEntry(), childThing))  {
          AnimationAuxiliary.debug("isSpecialization returns true 2");
          AnimationAuxiliary.debug("for parent " + parentThing.getName() + ", child " + childThing.getName());
          return true;
        }
      }
    }
    // return false if no specialization found
    AnimationAuxiliary.debug("isSpecialization returns false ");
    AnimationAuxiliary.debug("for parent " + parentThing.getName() + ", child " + childThing.getName());
    return false;
  }

  // this function checks whether two links are identical.
  // it gets two links, one of them is connected to a parent process
  // and second to its child. The srcInd indicates the connection type
  // (it is true when child/parent are links' source, false otherwise
  // it will return true if one of the following conditions hold
  // 1. second end of both links is same entry
  // 2. second end of child is a specialization of second end of parent
  // 3. second end of child's link is a state of an object on the second end  of
  //    parent's link
  // 4. second end of child is a child process of second end of parent
  // 5. second end of child is a state of an object, which is a specialization
  //    of second end of parent
  // 6. on high level there is an effect link between parent and object
  //    on low level there are result + consumption links between child and
  //    object's states
  private boolean isIdentical(IXLinkEntry parentLink, IXLinkEntry childLink, boolean srcInd)  {
    IXThingEntry parentThing = null, childThing = null;
    AnimationAuxiliary.debug("Starting isIdentical");
    AnimationAuxiliary.debug(", parentLink = " + parentLink.getName());
    AnimationAuxiliary.debug(", childLink = " + childLink.getName());
    AnimationAuxiliary.debug(", srcInd = " + srcInd);
    // if link type is different return false - identical links should have
    // link type
    if (parentLink.getLinkType() != childLink.getLinkType())  {
      AnimationAuxiliary.debug("isIdentical returns false (link type)");
      return false;
    }

    // if parent and child are linked to the same entry
    // with the same link type remove this link
    if ((parentLink.getDestinationId() == childLink.getDestinationId()) ||
         (parentLink.getSourceId() == childLink.getSourceId())) {
      AnimationAuxiliary.debug("isIdentical returns true (same entry)");
      return true;
    }

    // retrieve second end entries for child and parent
    try {
      IXEntry childEntry = null;
      if (srcInd) {
        parentThing = (IXThingEntry)findEntryById(parentLink.getDestinationId()).getIXEntry();
        childEntry = findEntryById(childLink.getDestinationId()).getIXEntry();
      }
      else{
        parentThing = (IXThingEntry)findEntryById(parentLink.getSourceId()).getIXEntry();
        childEntry = findEntryById(childLink.getSourceId()).getIXEntry();
      }
      // obtain the thing on child's second end
      if (AnimationAuxiliary.isThing(childEntry)) {
        childThing = (IXThingEntry) childEntry;
      }
      else  {
        // if child's second end is state - get the corresponding object
        if (AnimationAuxiliary.isState(childEntry)) {
          childThing = ((IXStateEntry)childEntry).getParentIXObjectEntry();
        }
      }
    }
    catch (Exception e) {
      AnimationAuxiliary.debug("isIdentical returns false (not a thing)");
      return false;
    }

    // after getting object for state:
    // if parent and child are linked to the same entry
    // with the same link type remove this link
    if (parentThing.getId() == childThing.getId())  {
      AnimationAuxiliary.debug("isIdentical returns true (same entry with state)");
      return true;
    }

    // if second end of child's link is specialization of second end
    // of parent's link (direct or inherited) - return true
    if ((childThing != null) && (parentThing != null))  {
      if (this.isSpecialization(parentThing, childThing)) {
        AnimationAuxiliary.debug("isIdentical returns true (Specialization)");
        return true;
      }
    }

    // if second end of child's link is a child process of second end
    // of parent's link - return true
    if ((AnimationAuxiliary.isProcess(childThing)) && (AnimationAuxiliary.isProcess(parentThing))) {
      IXProcessEntry childProcess = (IXProcessEntry)childThing;
      IXProcessEntry parentProcess = (IXProcessEntry)parentThing;
      AnimationAuxiliary.debug("childProcess " + childThing.getName() + " id = " + childThing.getId());
      AnimationAuxiliary.debug("parentProcess " + parentThing.getName());
      AnimationEntry aParentProcess = findEntryById(childProcess.getId());
      Enumeration en = aParentProcess.getChildrenProcesses().elements();
      for (;en.hasMoreElements();)  {
        IXProcessEntry childProcessTemp = (IXProcessEntry)en.nextElement();
        AnimationAuxiliary.debug("childProcessTemp  " + childProcessTemp.getName()  + " id = " + childProcessTemp.getId());
        if (childProcessTemp.getId() == parentProcess.getId()) {
          AnimationAuxiliary.debug("isIdentical returns true (child process)");
          return true;
        }
      }
    }

    // if neither of conditions holds - return false
    AnimationAuxiliary.debug("isIdentical returns false (end)");
    return false;
  }

  // this function checks whether the given process is linked with a fundamental
  // relation to another process under the same parent
  private boolean isRelatedProcess(AnimationEntry aProcess, AnimationEntry aParent) {
    IXProcessEntry iProcess = (IXProcessEntry)aProcess.getIXEntry();
    // get list of brothers
    Vector brothers = aParent.getChildrenProcesses();
    // go through the list of brothers
    for (int i=0;i<brothers.size();i++) {
      IXProcessEntry brother = (IXProcessEntry)brothers.get(i);
      long brotherId = brother.getId();
      // get relations of the current process
      Enumeration en = iProcess.getRelationByDestination();
      for (;en.hasMoreElements();)  {
        IXRelationEntry relation = (IXRelationEntry)en.nextElement();
        if ((relation.getRelationType() == exportedAPI.OpcatConstants.AGGREGATION_RELATION) ||
            (relation.getRelationType() == exportedAPI.OpcatConstants.EXHIBITION_RELATION) ||
            (relation.getRelationType() == exportedAPI.OpcatConstants.INSTANTINATION_RELATION) ||
            (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION)) {
          // check if the relation is linked to a brother
          if (relation.getSourceId() == brotherId) {
            return true;
          }
        }
      }
    }
    return false;
  }

  // This function checks whether there are incoming events
  // linked to the given process. The function considers all events
  // except internal events, i.e. events processes inside zoomed in process,
  // such as brother invokes brother, or child invokes parent
  private boolean findEvents(AnimationEntry aProcess, AnimationEntry aParentProcess)  {
    // get all incoming events
    Vector linkTypes = new Vector();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.AGENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EXCEPTION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
    Enumeration en = getLinksByDestination((IXProcessEntry)aProcess.getIXEntry(), linkTypes);
    // go through incoming events, return true if found none internal event
    for (;en.hasMoreElements();)  {
      IXLinkEntry iLink = (IXLinkEntry)en.nextElement();
      if (!isInternalEvent(aProcess, aParentProcess, iLink))  {
        return true;
      }
    }
    // return false - no events found
    return false;
  }

  // this function checks whether the given link is internal for the given process
  // internal link is a link inside a zoomed in process
  // such as brother invokes brother, or child invokes parent
  private boolean isInternalEvent(AnimationEntry aProcess,
                                  AnimationEntry aParentProcess, IXLinkEntry iLink) {
    AnimationAuxiliary.debug("Starting isInternalEvent");
    AnimationAuxiliary.debug("aProcess = " + aProcess.getIXEntry().getName());
    if (aParentProcess != null) {
      AnimationAuxiliary.debug("aParentProcess = " + aParentProcess.getIXEntry().getName());
    }
    AnimationAuxiliary.debug("iLink = " + iLink.getName());
    // internal link can be only of invocation or exception type
    if ((iLink.getLinkType() != exportedAPI.OpcatConstants.EXCEPTION_LINK) &&
        (iLink.getLinkType() != exportedAPI.OpcatConstants.INVOCATION_LINK))  {
      // the link is not internal
      return false;
    }

    // Get children processes
    Vector internalProcesses = aProcess.getChildrenProcesses();
    // Add brother processes
    if (aParentProcess != null) {
      internalProcesses.addAll(aParentProcess.getChildrenProcesses());
    }

    // check if the event source is a child or brother processes
    for (int i=0;i<internalProcesses.size();i++) {
      IXEntry iProcess = (IXEntry)internalProcesses.get(i);
      if (iProcess.getId() == iLink.getSourceId())  {
        // the link is internal
        return true;
      }
    }
    return false;
  }

  // this function checks whether the process has been triggered by internal
  // event, i.e by its child or brother.
  // it gets the process itself, its parent and the event that contains
  // activation parameters and calls isInternalEvent for the event link
  // that activated the process
  private boolean isTriggeredByInternalEvent(AnimationEntry aProcess,
                                             AnimationEntry aParent,
                                             AnimationEvent aEvent) {
    boolean isInternalEvent = false;  // initialize
    // obtain the event link that activated the process and check if it is internal
    try {
      IXLinkEntry iLink = (IXLinkEntry)aEvent.getEventLink().getIXEntry();
      isInternalEvent = isInternalEvent(aProcess, aParent, iLink);
    }
    catch (Exception e) {
      // the process was not activated by event - return false
      AnimationAuxiliary.debug("isInternalEvent returns false (exception)");
      return false;
    }
    AnimationAuxiliary.debug("isInternalEvent " + isInternalEvent);
    return isInternalEvent;
  }

  // this function checks whether there is an unavailable conditions, due to which
  // the process can not be activated. It is used for the child processes inside
  // zoomed in process
  private boolean findUnavailableCondition(AnimationEntry process)  {
    AnimationAuxiliary.debug("Starting findUnavailableCondition for " + process.getIXEntry().getName());
    // get all condition links linked to the process
    Vector linkTypes = new Vector();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
    Enumeration en = getLinksByDestination((IXProcessEntry)process.getIXEntry(), linkTypes);
    // go through condition links
    for(; en.hasMoreElements();)  {
      IXLinkEntry iLinkEntry = (IXLinkEntry)en.nextElement();
      // check whether condition is available
      if (!checkLinkBeforeActivate(iLinkEntry)) {
        // found unavailable condition
        AnimationAuxiliary.debug("Found unavailable condition link " + iLinkEntry.getName());
        return true;
      }
    }
    // all conditions are available
    return false;
  }

  // this is an auxiliary function that converts time value to steps
  private long computeStep(long timeOffset) {
    double time = (new Double(timeOffset)).doubleValue();
    double duration = (new Double(AnimationSettings.STEP_DURATION)).doubleValue();
    double doubleTemp = time/duration;
    long temp = java.lang.Math.round(java.lang.Math.ceil(doubleTemp));
    //AnimationAuxiliary.debug("doubleTemp = " + doubleTemp + " ceil = " + java.lang.Math.ceil(doubleTemp));
    //AnimationAuxiliary.debug("computeStep returns " + temp + " timeOffset = " +  timeOffset);
    return temp;
  }

  // this function searches time out exceptions linked to the given animation entry
  private Enumeration findTimeOutException(AnimationEntry aEntry)  {
    AnimationAuxiliary.debug("Starting findTimeOutException for " + aEntry.getIXEntry().getName());
    Vector linkTypes = new Vector();
    IXConnectionEdgeEntry iEntry;
    try {
      // currently it will not support reaction time outs
      // because link is not IXConnectionEdgeEntry
      iEntry = (IXConnectionEdgeEntry) aEntry.getIXEntry();
    }
    catch (java.lang.ClassCastException e)  {
      return linkTypes.elements();
    }
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.EXCEPTION_LINK));
    return this.getLinksBySource(iEntry, linkTypes);
  }

  // this function gets a path and a list of links
  // it checks whether the path is valid
  // if inside given list of links there is a none active
  // link marked with the given path, or with no path
  // the function returns false
  // otherwise it returns true
  private boolean checkPath(String path, Enumeration en)  {
    //Enumeration en = animationElements.elements();
    //String path = link.getPath();
    IXLinkEntry tempLink;
    //long destId = link.getDestinationId();

    for(; en.hasMoreElements();)  {
    /*
      AnimationEntry aEntry = (AnimationEntry)en.nextElement();
      if (!AnimationAuxiliary.isLink(aEntry.getIXEntry())) {
        continue;
      }
      tempLink = (IXLinkEntry)aEntry.getIXEntry(); */
      tempLink = (IXLinkEntry)en.nextElement();
      /*if ((tempLink.getDestinationId() == destId) &&
          (tempLink.getId() != link.getId()))  {*/
      String tempPath = tempLink.getPath();
      // if there is another link with same destination
      // marked with same path or not marked with any path
      // then it should be active as well
      if (tempPath.equals("") || tempPath.equals(path))  {
        if (!checkLinkBeforeActivate(tempLink)) {
          AnimationAuxiliary.debug("checkPathDestination fails on link " + tempLink.getName());
          return false;
        }
      }
    }
    // path is valid
    return true;
  }


  // this recursive function returns all descendant processes
  // of the given process
  public Vector getAllDescendantProcesses(AnimationEntry aProcess) {
    Vector v = new Vector();
    v.addAll(aProcess.getChildrenProcesses());
    Enumeration en = v.elements();
    for(;en.hasMoreElements();) {
      IXProcessEntry iChild = (IXProcessEntry)en.nextElement();
      AnimationEntry aChild = findEntryById(iChild.getId());
      AnimationAuxiliary.mergeVectors(v, aChild.getChildrenProcesses());
    }
    return v;
  }

  // this function checks whether the object has an active state
  private  boolean hasActiveState(IXObjectEntry ixObjectEntry)  {
    IXObjectInstance objIns = (IXObjectInstance)(ixObjectEntry.getInstances().nextElement());
    Enumeration en = objIns.getStateInstances();
    // check that there is no active state
    for(; en.hasMoreElements();)
    {
      IXStateEntry state = (IXStateEntry)((IXStateInstance)en.nextElement()).getIXEntry();
      AnimationEntry aState = findEntryById(state.getId());
      if (aState.isActive())  {
        return true;
      }
    }
    return false;
  }

  // this function deactivates objects inside a zoomed in process
  private void deactivateChildObjects(AnimationEntry aProcess)  {
    Enumeration childObjects = aProcess.getChildrenObjects().elements();
    for(; childObjects.hasMoreElements();) {
      IXObjectEntry childObject = (IXObjectEntry) childObjects.nextElement();
      AnimationEvent aEvent = new AnimationEvent();
      AnimationEntry aChildObject = findEntryById(childObject.getId());
      aEvent.setNumberOfInstances(aChildObject.getNumberOfInstances());
      deactivate(aChildObject, aEvent);
    }
  }

  //////////////////// Links Treatment Functions ////////////////////////

  // this function activates links with the given source and link type
  private void activateLinksBySource(IXConnectionEdgeEntry iEntry, Vector linkTypes) {
    Enumeration en = getLinksBySource(iEntry, linkTypes);
    IXLinkEntry link;
    for(; en.hasMoreElements();)
    {
      link = (IXLinkEntry)en.nextElement();
      this.activate(findEntryById(link.getId()));
    }
  }

  // this link applies the specified task to the links with specified source and link type
  private void treatLinkBySource(IXConnectionEdgeEntry iEntry, Vector linkTypes, Task task)  {
    Enumeration en = getLinksBySource(iEntry, linkTypes);
    treatLinks(en, task);
  }

  // this link applies the specified task to the links with specified destination and link type
  private void treatLinkByDestination(IXConnectionEdgeEntry iEntry, Vector linkTypes, Task task)  {
    Enumeration en = getLinksByDestination(iEntry, linkTypes);
    treatLinks(en, task);
  }

  // this link applies the validation and the treatment of the specified task to
  // the links with specified source and link type
  private void validateAndTreatLinkBySource(IXConnectionEdgeEntry iEntry, Vector linkTypes, Task task)  {
    Enumeration en = getLinksBySource(iEntry, linkTypes);
    validateAndTreatLinks(en, task);
  }

  // this link applies the validation and the treatment of the specified task to
  // the links with specified destination and link type
  private void validateAndTreatLinkByDestination(IXConnectionEdgeEntry iEntry, Vector linkTypes, Task task)  {
    Enumeration en = getLinksByDestination(iEntry, linkTypes);
    validateAndTreatLinks(en, task);
  }

  // this function applies the given task to the given enumeration of links
  private void treatLinks(Enumeration en, Task task)  {
    IXLinkEntry link;
    for(; en.hasMoreElements();)
    {
      link = (IXLinkEntry)en.nextElement();
      task.run(link);
    }
  }

  // this function applies the given task and validation to the given
  // enumeration of links
  private void validateAndTreatLinks(Enumeration en, Task task)  {
    Vector validLinks = new Vector();
    Vector allLinks = new Vector();

    for(; en.hasMoreElements();)  {
      allLinks.add(en.nextElement());
    }

    en = allLinks.elements();

    for(; en.hasMoreElements();)
    {
      IXLinkEntry link = (IXLinkEntry)en.nextElement();
      if (task.validate(link, allLinks.elements()))  {
        AnimationAuxiliary.debug("validateAndTreatLinks: Valid Link " + link.getName());
        validLinks.add(link);
      }
    }
    treatLinks(validLinks.elements(), task);
  }

  // temporary functions written instead of IXConnectionEdgeEntry.getLinksBySource,
  // which returns sometimes ancestors links
  private Enumeration getLinksBySource(IXConnectionEdgeEntry iEntry)  {
    return getLinks(iEntry, true);
    //return iEntry.getLinksBySource();
  }

  private Enumeration getLinksByDestination(IXConnectionEdgeEntry iEntry)  {
    return getLinks(iEntry, false);
    //return iEntry.getLinksByDestination();
  }

  private Enumeration getLinks(IXConnectionEdgeEntry iEntry, boolean srcInd)  {
    Vector v = new Vector();
    Enumeration en = this.animationElements.elements();
    for(; en.hasMoreElements();)  {
      AnimationEntry aEntry = (AnimationEntry)en.nextElement();
      if (AnimationAuxiliary.isLink(aEntry.getIXEntry()))  {
        IXLinkEntry iLink = (IXLinkEntry)aEntry.getIXEntry();
        if (srcInd) {
          if (iLink.getSourceId() == iEntry.getId())  {
            v.add(iLink);
          }
        }
        else  {
          if (iLink.getDestinationId() == iEntry.getId())  {
            v.add(iLink);
          }
        }
      }
    }
    AnimationAuxiliary.debug("getLinks returns " + v);
    return v.elements();
  }

  // this function returns list of links connected to the given entry (source)
  // and having specified link types
  private Enumeration getLinksBySource(IXConnectionEdgeEntry iEntry, Vector linkTypes) {
    if (AnimationAuxiliary.isProcess(iEntry)) {
      return this.getProcessLinks((IXProcessEntry)iEntry, linkTypes, true).elements();
    }
    else  {
      //return getLinkByType(iEntry.getLinksBySource(), linkTypes).elements();
      return getLinkByType(getLinksBySource(iEntry), linkTypes).elements();
    }
  }

  // this function returns list of links connected to the given entry (destination)
  // and having specified link types
  private Enumeration getLinksByDestination(IXConnectionEdgeEntry iEntry, Vector linkTypes) {
    if (AnimationAuxiliary.isProcess(iEntry)) {
      return this.getProcessLinks((IXProcessEntry)iEntry, linkTypes, false).elements();
    }
    else  {
      //return getLinkByType(iEntry.getLinksByDestination(), linkTypes).elements();
      return getLinkByType(getLinksByDestination(iEntry), linkTypes).elements();
    }
  }

  private Vector getLinkByType(IXProcessEntry process, Vector linkTypes, boolean srcInd) {
    if (srcInd) {
      return getLinkByType(getLinksBySource(process), linkTypes);
    }
    else  {
      return getLinkByType(getLinksByDestination(process), linkTypes);
    }
  }

  // this function derives links having given link types from the given
  // enumeration of links
  private Vector getLinkByType(Enumeration en, Vector linkTypes) {
    Vector temp = new Vector();
    IXLinkEntry link;
    for(; en.hasMoreElements();)
    {
      link = (IXLinkEntry)en.nextElement();
      if (linkTypes.contains(new Integer(link.getLinkType()))) {
        // mix links is some random order. This is necessary for
        // random selection when more than one link/path are available
        temp.add(AnimationAuxiliary.getRandomNumber(temp.size() + 1), link);
        //temp.add(link);
      }
    }
    return temp;
  }

  // this is a recursive function that returns a list of links
  // of the given object and all its ancestors
  // according to the given link type and source/destination
  private Vector getProcessOwnAndAncestorsLinks(IXProcessEntry process, Vector linkTypes, boolean srcInd) {
    AnimationAuxiliary.debug("Starting getProcessOwnAndAncestorsLinks for " + process.getName());
    Vector allLinks = new Vector();
    Vector newLinks = null;
    AnimationEntry aProcess = findEntryById(process.getId());

    // get own links
    newLinks = this.getLinkByType(process, linkTypes, srcInd);

     // add newLinks to allLinks
    AnimationAuxiliary.mergeVectors(allLinks, newLinks);

    // debug printings
    AnimationAuxiliary.debug("getProcessOwnAndAncestorsLinks for " + process.getName());
    AnimationAuxiliary.debug("printing own links");
    //AnimationAuxiliary.debug(allLinks);
    for (Enumeration en = newLinks.elements();en.hasMoreElements();) {
      AnimationAuxiliary.debug(((IXLinkEntry)en.nextElement()).getName());
    }
    AnimationAuxiliary.debug("finish printing own links");

    // if the process was not activated in parent's context return only
    // its own links (high level process)
    if (aProcess.getCurrentEvent().getParentProcess() == null)  {
      AnimationAuxiliary.debug("getParentProcess() == null  for " + process.getName());
      return allLinks;
    }

    // get ancestors' links by this recursive call
    IXProcessEntry parentProcess = (IXProcessEntry)aProcess.getCurrentEvent().getParentProcess().getIXEntry();
    Vector parentLinks = getProcessOwnAndAncestorsLinks(parentProcess, linkTypes, srcInd);

    // remove identical links from the list of parent's links
    for (int i=parentLinks.size()-1;i>=0;i--) {
      IXLinkEntry parentLink = (IXLinkEntry)parentLinks.get(i);
      // remove identical links
      for (int j=0;j<allLinks.size();j++) {
        IXLinkEntry childLink = (IXLinkEntry)allLinks.get(j);
        if (isIdentical(parentLink, childLink, srcInd)) {
          parentLinks.remove(i);
          j=allLinks.size();  // exit  from this loop after removal
        }
      }
    }

    // remove brothers' links
    Vector brothers = findEntryById(parentProcess.getId()).getChildrenProcesses();

    // go through the brothers
    for(int j=0;j<brothers.size();j++) {
      IXProcessEntry iBrotherProcess = (IXProcessEntry)brothers.get(j);

      // skip the process itself, it was already handled above
      if (iBrotherProcess.getId() == process.getId()) {
        continue;
      }

      Vector brothersLinks = null;

      // get brother's links
      brothersLinks = this.getLinkByType(iBrotherProcess, linkTypes, srcInd);

      // look among ancestors links for an identical link linked to a brother
      // process
      for(int i=parentLinks.size()-1;i>=0;i--) {
        IXLinkEntry iLink = (IXLinkEntry)parentLinks.get(i);
        AnimationAuxiliary.debug("Running for link " + iLink.getName() + ", src = " + iLink.getSourceId() + ", dest = " + iLink.getDestinationId());
        Enumeration enBrotherLinks = brothersLinks.elements();
        for (boolean stop = false;enBrotherLinks.hasMoreElements() && !stop;) {
          IXLinkEntry iBrotherLink = (IXLinkEntry)enBrotherLinks.nextElement();
          AnimationAuxiliary.debug("Brother link " + iBrotherLink.getName() + ", src = " + iBrotherLink.getSourceId() + ", dest = " + iBrotherLink.getDestinationId());
          if (isIdentical(iLink, iBrotherLink, srcInd)) {
            parentLinks.remove(i);
            stop = true; // exit from the loop after removal
          }
        }
      }
    }

    AnimationAuxiliary.mergeVectors(allLinks, parentLinks);
    AnimationAuxiliary.debug("Finish getProcessOwnAndAncestorsLinks for " + process.getName());
    AnimationAuxiliary.debug("printing output links");
    //AnimationAuxiliary.debug(allLinks);
    for (Enumeration en = allLinks.elements();en.hasMoreElements();) {
      AnimationAuxiliary.debug(((IXLinkEntry)en.nextElement()).getName());
    }
    AnimationAuxiliary.debug("finish printing output links");
    return allLinks;
  }

  // This functions returns the links that refer to the given entry
  // for objects and states it returns the list of incoming or outgoing links
  // in accordance with the given list of types
  // for processes it applies the following logic:
  // if the process is low level (i.e. hasn't child process in zoomed in OPD)
  // then it retrieves all the links linked directly to the process
  // and all none-event links linked to all ancestors of this process on all levels
  // except those that refer to process brothers or its ancestors' brothers
  // but not to the process itself
  // if the process is not low level then it will retrieve
  // only the events linked directly to the process
  // except those that are linked also to process descendant.
  private Vector getProcessLinks(IXProcessEntry process, Vector linkTypes, boolean srcInd) {
    AnimationAuxiliary.debug("Starting getProcessLinks for " + process.getName());
    AnimationEntry aProcess = findEntryById(process.getId());
    Vector allLinks = new Vector();

    // separate event links and none event links into two vectors
    Vector linkTypesNotEvents = new Vector();
    Vector linkTypesOnlyEvents = new Vector();
    for(int i=linkTypes.size()-1;i>=0;i--) {
      Integer linkType = (Integer)linkTypes.get(i);
      if (AnimationAuxiliary.isEvent(linkType.intValue())) {
        linkTypesOnlyEvents.add(linkType);
      }
      else  {
        linkTypesNotEvents.add(linkType);
      }
    }

    AnimationAuxiliary.debug("Printing linkTypesNotEvents " + linkTypesNotEvents);

    if (aProcess.isLowLevel())  {
      // if the process is low level
      // get all none event links of the process itself and its ancestors
      allLinks.addAll(getProcessOwnAndAncestorsLinks(process, linkTypesNotEvents, srcInd));
    }

    AnimationAuxiliary.debug("After getProcessOwnAndAncestorsLinks");
    AnimationAuxiliary.debug(allLinks);
    // now add the events linked directly to the current entry
    // linkTypes contains only events
    // none events are not required for zoomed in processes
    // and for low level process they already was collected above
    AnimationAuxiliary.mergeVectors(allLinks, getLinkByType(process, linkTypesOnlyEvents, srcInd));
    //this was added to  solve condition links problem.
    if(!aProcess.isLowLevel()){
    	AnimationAuxiliary.mergeVectors(allLinks, getLinkByType(process,  linkTypesNotEvents, srcInd));
    }
    // debug
    AnimationAuxiliary.debug("All Links before removal for " + aProcess.getIXEntry().getName());
    //AnimationAuxiliary.debug(allLinks);
    for (Enumeration en = allLinks.elements();en.hasMoreElements();) {
      AnimationAuxiliary.debug(((IXLinkEntry)en.nextElement()).getName());
    }
    AnimationAuxiliary.debug("finish printing All Links before removal");


    // if process's link has an identical link
    // link to one of descendant processes (if the process is zoomed in)
    // this link should be treated only for the descendant and therefore
    // should be removed from the list of process own links
    AnimationAuxiliary.debug("Starting removal of duplicate links");
    // add all descendants
    Vector descendants = getAllDescendantProcesses(aProcess);

    AnimationAuxiliary.debug("Printing list of descendants");
    AnimationAuxiliary.debug(descendants);
    for(int j=0;j<descendants.size();j++) {
      IXProcessEntry iDescProcess = (IXProcessEntry)descendants.get(j);
      Vector descendantLinks = new Vector();

      // get descendant's links
      descendantLinks = getLinkByType(iDescProcess, linkTypes, srcInd);

      // debug
      AnimationAuxiliary.debug("Printing links found for descendant process " + iDescProcess.getName());
      for (Enumeration en = descendantLinks.elements();en.hasMoreElements();) {
        AnimationAuxiliary.debug(((IXLinkEntry)en.nextElement()).getName());
      }
      AnimationAuxiliary.debug("finish printing descendant links");
      // end debug

      Enumeration enDescLinks = descendantLinks.elements();
      // look for a link identical to the process own link
      // among the links of descendant
      for(int i=allLinks.size()-1;i>=0;i--) {
        IXLinkEntry iLink = (IXLinkEntry)allLinks.get(i);
        for (boolean stop = false;enDescLinks.hasMoreElements() && !stop;) {
          IXLinkEntry iDescLink = (IXLinkEntry)enDescLinks.nextElement();
          if (isIdentical(iLink, iDescLink, srcInd))  {
            allLinks.remove(i);
            stop = true; // exit from the loop after removal.
          }
        }
      }
    }

    // return the resulting collection
    AnimationAuxiliary.debug("End of getProcessLinks for " + process.getName());
    AnimationAuxiliary.debug("Printing output links ");
    AnimationAuxiliary.debug(allLinks);
    return allLinks;
  }

  /////////////// TimeOut Treatment Functions ////////////////

  // this function applies time out treatment upon termination of an entry
  // it is called for states and processes
  // First it calculates the duration of process or being on state. Then
  // it goes through the list of time out exception links connected to the entry
  // if the duration is less that the minimum duration, it activates the exception
  // In any case it cancells the exception activation event scheduled max time after
  // entry activation (this scheduling is done in  treatTimeoutsUponActivation function).
  private void treatTimeoutsUponTermination(AnimationEntry aEntry, AnimationEvent currentEvent) {
    AnimationAuxiliary.debug("Starting treatTimeoutsUponTermination for " + aEntry.getIXEntry().getName());
    // treat time outs
    Enumeration en = findTimeOutException(aEntry);
    for(; en.hasMoreElements();)  {
      IXLinkEntry iLink = (IXLinkEntry)en.nextElement();
      AnimationEntry aExceptionLink = findEntryById(iLink.getId());

      // compute duration
      long activationStep = aEntry.getCurrentEvent().getStep();
      long duration = (currentStep - activationStep)*AnimationSettings.STEP_DURATION;
      AnimationAuxiliary.debug("duration = " + duration);

      // activate exception if entry is deactivated before minimum time
      if ((duration > 0) && (duration < aEntry.getMinTime())) {
        AnimationAuxiliary.debug("duration < aEntry.getMinTime(): Activate " + aExceptionLink);
        activate(aExceptionLink);
      }

      // cancel scheduled event - set cancel step = current step
      Enumeration enExAct = aExceptionLink.getScheduledActivations(activationStep + this.computeStep(aEntry.getMaxTime()));
      try {
        AnimationEvent aExceptionActvEvent = (AnimationEvent)enExAct.nextElement();
        AnimationAuxiliary.debug("Cancel activation scheduled to step " + (currentEvent.getSchedulingStep() + this.computeStep(aEntry.getMaxTime())));
        AnimationAuxiliary.debug("Current Step = " + currentStep);
        aExceptionActvEvent.setCancelStep(currentStep);
      }
      catch (Exception e) {}
    }
  }

  // this function treats time outs upon entry activation.
  private void treatTimeoutsUponActivation(AnimationEntry aEntry) {
    treatTimeoutsUponActivation(aEntry, currentStep);
  }

  // this function treats time outs upon entry activation
  // for each exception link connected to the entry it schedules future activation
  // after the max time.
  private void treatTimeoutsUponActivation(AnimationEntry aEntry, long step) {
    AnimationAuxiliary.debug("Starting treatTimeoutsUponActivation for " + aEntry.getIXEntry().getName());
    Enumeration en = findTimeOutException(aEntry);
    for(; en.hasMoreElements();)  {
      IXLinkEntry iLink = (IXLinkEntry)en.nextElement();
      AnimationEntry aLink = findEntryById(iLink.getId());
      long maxTime = aEntry.getMaxTime();
      AnimationAuxiliary.debug("Schedule exception activation " + iLink.getName() + " to step " + (step + computeStep(maxTime)));
      aLink.scheduleActivation(step, step + computeStep(maxTime));
    }
  }

  /////////////// Termination Consequences Treatment Functions ////////////////

  private void treatTerminationConsequences(AnimationEntry activatedEntry, AnimationEvent aEvent) {
    if (AnimationAuxiliary.isProcess(activatedEntry.getIXEntry())) {
      treatProcessTerminationConsequences(activatedEntry, aEvent);
      return;
    }
    if (AnimationAuxiliary.isObject(activatedEntry.getIXEntry())) {
      treatObjectTerminationConsequences(activatedEntry, aEvent);
      return;
    }

    if (AnimationAuxiliary.isState(activatedEntry.getIXEntry())) {
      treatStateTerminationConsequences(activatedEntry, aEvent);
      return;
    }
  }

  // this function treats the termination consequences of a process:
  // 1. activates results and invocations
  // 2. if process is a child process - call treatChildProcessTerminationConsequences function
  // 3. if this is a zoomed in process - terminates all objects inside it
  // 4. treats time outs
  private void treatProcessTerminationConsequences(AnimationEntry aProcess, AnimationEvent currentEvent) {
    AnimationAuxiliary.debug("Starting treatProcessTerminationConsequences for " + aProcess.getIXEntry().getName());
    AnimationAuxiliary.debug("currentEvent.getExecutionId() = " + currentEvent.getExecutionId());
    IXProcessEntry iProcess = (IXProcessEntry)aProcess.getIXEntry();
    

     // activate results
    Vector linkTypes = new Vector();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
    ActivateResultsTask task = new ActivateResultsTask(aProcess, currentEvent);
    this.treatLinkBySource(iProcess, linkTypes, task);

    // treat terminated states
    linkTypes.clear();
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
    linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
    Enumeration en = this.getLinksByDestination(iProcess, linkTypes);
    for (;en.hasMoreElements();)  {
      IXLinkEntry iLink = (IXLinkEntry)en.nextElement();
      AnimationEntry aSrc = findEntryById(iLink.getSourceId());
      if (AnimationAuxiliary.isState(aSrc.getIXEntry())) {
        IXObjectEntry object = ((IXStateEntry)aSrc.getIXEntry()).getParentIXObjectEntry();
        AnimationEntry aObj = findEntryById(object.getId());
        if (aObj.isActive() && !hasActiveState(object))  {
          // if after state termination, there is no more active states for this
          // object, the object should be deactivated
          deactivate(aObj, new AnimationEvent(currentEvent));
        }
      }
    }

    // if the process was triggered by internal event
    // treat child process termination consequences of the process that
    // triggered it (source of the event link that invoked the current process)
    if (isTriggeredByInternalEvent(aProcess, currentEvent.getParentProcess(), currentEvent))  {
      AnimationEvent aEventNext = new AnimationEvent(currentEvent);
      if (currentEvent.getParentProcess() == null)  {
        // this might be true only if child process invoked its parent
        // set current process as a parent
        aEventNext.setParentProcess(aProcess);
      }
      long srcId = ((IXLinkEntry)currentEvent.getEventLink().getIXEntry()).getSourceId();
      treatChildProcessTerminationConsequences(findEntryById(srcId), aEventNext);
    }
    else  {
      // if the terminated process is a child process
      // of a zoomed in process activated in parent process context -
      // activate next level children or deactivate the parent
    	/*
         * Added for debugging of condition links problems
         */
        String s =aProcess.getIXEntry().getName();
        int i=0;
         if(s.lastIndexOf("Password\nChecking")>-1 ){
      	 i++;
      	}
      if (currentEvent.getParentProcess() != null)  {   // parent process context
        treatChildProcessTerminationConsequences(aProcess, currentEvent);
      }
    }

    // if the terminated process is zoomed in
    // deactivate all objects inside it.
    if (aProcess.getNumberOfInstances() == 0) {
      this.deactivateChildObjects(aProcess);
    }

    // treat timeouts
    treatTimeoutsUponTermination(aProcess, currentEvent);

    // if terminated process is a specialization of another process
    // activate its generalization as well
    AnimationEvent aEventTemp = new AnimationEvent(currentEvent);
    aEventTemp.setEventType(AnimationSettings.TERMINATION_EVENT);
    treatGeneralization(iProcess, aEventTemp);
  }


  // this function treats the consequences of child process termination
  // child process is a process that has parent and was activated in parent's context
  // this function implements the following rule:
  // 1. when last child process on the active level terminates next level should be activated
  // 2. if current level is the last level, then parent process should be terminated
  private void treatChildProcessTerminationConsequences(AnimationEntry aProcess, AnimationEvent currentEvent) {
    AnimationAuxiliary.debug("Starting treatChildProcessTerminationConsequences for " + aProcess.getIXEntry().getName());
    IXProcessEntry iProcess = (IXProcessEntry)aProcess.getIXEntry();
    AnimationEvent aChildEvent = new AnimationEvent();
    aChildEvent.setParentProcess(currentEvent.getParentProcess());
    aChildEvent.setExecutionId(currentEvent.getExecutionId());
    if (aProcess.getParents().hasMoreElements())  {
      Enumeration en = iProcess.getInstances();
      for(; en.hasMoreElements();) {
        IXProcessInstance activatedProcessInstance = (IXProcessInstance) en.nextElement();
        IXThingInstance parentProcessInstance = activatedProcessInstance.getParentIXThingInstance();
        if (parentProcessInstance != null)  {
          AnimationEntry aParentProcess = findEntryById(parentProcessInstance.getIXEntry().getId());
          if (aParentProcess.isActive(currentEvent.getExecutionId()))  {
            if (this.checkProcessBeforeTerminate(aParentProcess, currentEvent)) {
              AnimationAuxiliary.debug("checkProcessBeforeTerminate succeeded for " + aParentProcess.getIXEntry().getName());
              // activate next level if all child processes already terminated
              Enumeration nextLevelChildren = aProcess.getNextLevelChildren(activatedProcessInstance);
              // check again checkProcessBeforeTerminate because it can happen that although
              // one of children has been activated, but it was terminated immediately
              if (!activateLevel(nextLevelChildren, aChildEvent) ||
                  checkProcessBeforeTerminate(aParentProcess, currentEvent)) {
                // if activateLevel returns false - there are no more children
                // processes to be activated - terminate the parent process
                AnimationAuxiliary.debug("Deactivate parent process " + aParentProcess.getIXEntry().getName());
                if (aParentProcess.getCurrentEvent(currentEvent.getExecutionId()) != null)  {
                  // return to parent's OPD
                  if (AnimationSettings.MOVE_BETWEEN_OPD) {
                    if (!opdStack.isEmpty())  {
                      long temp = ((Long)opdStack.pop()).longValue();
                      mySys.showOPD(temp);
                      AnimationAuxiliary.debug("treatChildProcessTerminationConsequences for " + iProcess.getName() + ": pop " + temp);
                    }
                  }
                  // deactivate parent
                  AnimationEvent termEvent = new AnimationEvent(aParentProcess.getCurrentEvent(currentEvent.getExecutionId()));
                  termEvent.setStep(currentStep);
                  termEvent.setSchedulingStep(currentStep);
                  termEvent.setNumberOfInstances(1);
                  deactivate(aParentProcess, termEvent);
                }
              }
            }
          }
        }
      }
    }
  }


  // this function treats object termination consequences
  // it deactivates all the states of the terminated object
  private void treatObjectTerminationConsequences(AnimationEntry aObj, AnimationEvent currentEvent) {
    // if there are no more active instances -> deactivate objects state
    IXObjectEntry terminatedObject = (IXObjectEntry)aObj.getIXEntry();
    if (aObj.isActive() == false) {
      IXObjectInstance objIns = (IXObjectInstance)(terminatedObject.getInstances().nextElement());
      Enumeration en = objIns.getStateInstances();
      for(; en.hasMoreElements();)
      {
        IXStateEntry state = (IXStateEntry)((IXStateInstance)en.nextElement()).getIXEntry();
        AnimationEntry aState = findEntryById(state.getId());
        if (aState.isActive())  {
          deactivate(aState);
        }
      }
    }
    // if terminated object is a specialization of another object
    // terminate its generalization as well
    AnimationEvent aEventTemp = new AnimationEvent();
    aEventTemp.setEventType(AnimationSettings.TERMINATION_EVENT);
    treatGeneralization((IXThingEntry)aObj.getIXEntry(), aEventTemp);

    // treat exhibition relations
    treatExhibitions(aObj,false);
  }

  private void treatStateTerminationConsequences(AnimationEntry terminatedState, AnimationEvent currentEvent) {
    // treat state time out exception
    this.treatTimeoutsUponTermination(terminatedState, currentEvent);
  }

  ////////////////////////////////
  //   Animation tasks classes  //
  ////////////////////////////////

  // generic task interface
  private interface Task {
    public void run(IXEntry iEntry);
    public boolean validate(IXEntry iEntry, Enumeration enAllEntries);
  }

  private class AnimateResultsTask implements Task {
    AnimationEntry aProcess;
    AnimationEvent aEvent;

    public AnimateResultsTask() {
    }

    public AnimateResultsTask(AnimationEntry aProcess, AnimationEvent aEvent) {
      this.aProcess = aProcess;
      this.aEvent = aEvent;
    }

    public boolean validate(IXEntry iEntry, Enumeration en) {
      return true;
    }

    public void run(IXEntry iEntry) {
      IXLinkEntry link = (IXLinkEntry) iEntry;
  //    AnimationAuxiliary.debug("Running AnimateResultsTask");
      if ((aProcess.getPath().equals(""))  ||
              (link.getPath().equals(aProcess.getPath())) ||
              (link.getPath().equals(""))) {
        // if process was activated without path,
        // and one of the links has path - set it to be the process path.
        if (aProcess.getPath().equals("")  &&
            !link.getPath().equals("")) {
          aProcess.setPath(link.getPath());
          aProcess.setPath(link.getPath(), aEvent.getExecutionId());
        }
        // animate
        findEntryById(link.getId()).animate(aEvent);
        findEntryById(link.getDestinationId()).animate(aEvent);
      }
    }
  }

  private class DeactivateConsumedEntriesTask implements Task {
    AnimationEntry aProcess;
    AnimationEvent aEvent;

    public DeactivateConsumedEntriesTask(AnimationEntry aProcess, AnimationEvent aEvent) {
      this.aProcess = aProcess;
      this.aEvent = aEvent;
    }

    public boolean validate(IXEntry iEntry, Enumeration enAllEntries ) {
      AnimationAuxiliary.debug("Validate consumed link " + iEntry.getName());
      IXLinkEntry link = (IXLinkEntry) iEntry;
      if (checkLinkBeforeActivate(link))  {
        AnimationAuxiliary.debug("After  checkLinkBeforeActivate for" + iEntry.getName());
        // check logical relation
        if (checkPath(link.getPath(), enAllEntries)) {
          AnimationAuxiliary.debug("After  checkPathDestination for" + iEntry.getName());
          return true;
        }
      }
      return false;
    }

    public void run(IXEntry iEntry) {
      IXLinkEntry link = (IXLinkEntry) iEntry;
      AnimationAuxiliary.debug("run deactivate consume entries for " + aProcess.getIXEntry().getName());
      if ((aProcess.getPath().equals("")) ||
          (aProcess.getPath().equals(link.getPath())) ||
          (link.getPath().equals(""))) {
        // animate
        //AnimationAuxiliary.debug("animate consumption link " + link.getName());
        //AnimationAuxiliary.debug("duration = " + aEvent.getDuration());
        //AnimationAuxiliary.debug("Current time " + System.currentTimeMillis());
        findEntryById(link.getId()).animate(aEvent);
        // deactivate the source object
        AnimationAuxiliary.debug("deactivate " + findEntryById(link.getSourceId()).getIXEntry().getName());
        deactivate(findEntryById(link.getSourceId()), aEvent);
      }
    }
  }

  private class ActivateResultsTask implements Task {
    AnimationEntry aProcess;
    AnimationEvent aEvent;

    public ActivateResultsTask() {
    }

    public ActivateResultsTask(AnimationEntry aProcess, AnimationEvent aEvent) {
      this.aProcess = aProcess;
      this.aEvent = aEvent;
    }

    public boolean validate(IXEntry iEntry, Enumeration en) {
      return true;
    }

    public void run(IXEntry iEntry) {
      IXLinkEntry link = (IXLinkEntry) iEntry;
      AnimationAuxiliary.debug("Running ActivateResultsTask");
       if ((aProcess.getPath().equals(""))  ||
        (link.getPath().equals(aProcess.getPath())) ||
        (link.getPath().equals(""))) {
        // activate the link if process is not marked with path
        // or if link's path is equal to the path stored on the process.
        AnimationEntry aLink = findEntryById(link.getId());
        if (AnimationAuxiliary.isEvent(link))  {
          activate(aLink, new AnimationEvent(aEvent));
        }
        else  {
          activate(findEntryById(link.getDestinationId()));
        }
      }
    }
  }
}