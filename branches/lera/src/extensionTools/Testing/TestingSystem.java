package extensionTools.Testing;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.TimerTask;
import java.util.Vector;
import exportedAPI.OpdKey;
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
import exportedAPI.util.APILogger;
import gui.Opcat2;
import gui.util.OpcatLogger;

public class TestingSystem extends Object implements Testing {
	private Hashtable debugEvents = new Hashtable();

	private IXSystem mySys;

	private IXSystem bkpSys;

	private Vector testingElements = new Vector();

	public long currentStep = 0;

	TestingTimer testingTimer;

	TestingStatusBar guiPanel = null;

	Stack opdStack = new Stack();
	
	private TestingObjectsLifeSpanGrid lifeSpanGrid = null ; 

	// There is a bug in original program I could not discovered that
	// required double starting
	// of testing before actualy activating. Therefore I added
	// doublestrartfixer to double start it.
	// Also after stopping,since "isTestingPoused()"
	// checked through timer there was the same problem,
	// so I use it to avoid it too.
	private static boolean doublestrartfixer = true;

	private TestingGridPanel testingPanel;

	private TestingObjectsScenrios objectsScenrios;

	static private boolean hasTerminations = false;

	static private boolean hasActivation = false;

	// ////////////////// initiation ////////////////////////

	public TestingSystem(IXSystem mySys, TestingStatusBar guiPanel) {
		this.mySys = mySys;
		this.bkpSys = mySys;
		this.guiPanel = guiPanel;
		guiPanel.showTestingStatus(0, 0);
		this.testingPanel = this.InitTestingGridPanel();
		testingPanel.setTabName(TestingAuxiliary.TestingPanelName);
		this.testingPanel.AddToExtensionToolsPanel();

		objectsScenrios = InitObjectScnerios();
		objectsScenrios.setTabName("Objects");
		this.objectsScenrios.AddToExtensionToolsPanel();
	}

	private void createTestingElements() {
		Enumeration en;

		if (!this.testingElements.isEmpty()) {
			this.testingElements.clear();
		}
		en = this.mySys.getIXSystemStructure().getElements();
		for (; en.hasMoreElements();) {
			IXEntry iEntry = (IXEntry) en.nextElement();
			// create only things, states and links (but not relations)
			// since relations are not relevant for testing
			if (TestingAuxiliary.isThing(iEntry)
					|| TestingAuxiliary.isState(iEntry)
					|| TestingAuxiliary.isLink(iEntry)) {
				this.testingElements.addElement(new TestingEntry(iEntry));
			}
		}
	}

	// ////////////////// public functions ////////////////////////

	public void testingSettings(TestingSettings newSettings) {
		TestingSettings.loadSettings(newSettings);
	}

	public boolean isTestingPaused() {
		try {/*
				 * return testingTimer.isPause();
				 */
			return !(doublestrartfixer);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean canFarward() {
		return this.isTestingPaused();
	}

	public boolean canBackward() {
		return (this.isTestingPaused() && (this.currentStep > 0));
	}

	public void testingStart() {
		lifeSpanGrid = new TestingObjectsLifeSpanGrid(mySys) ;
		
		this.currentStep = 0;
		this.createTestingElements();
		if (this.testingTimer != null) {
			try {
				this.testingTimer.cancel();
				this.testingStop();
				doublestrartfixer = false;
			} catch (NullPointerException e) {
			}
		}
		this.testingTimer = new TestingTimer(this);
		if (doublestrartfixer) {
			doublestrartfixer = false;
			this.testingStart();
		}
	}

	public void testingStop() {
		if (this.testingTimer != null) {
			this.testingTimer.cancel();
		}
		this.deactivate();
		this.guiPanel.showTestingStatus(0, 0);
		doublestrartfixer = true;
		this.mySys = this.bkpSys;
		this.testingPanel.RestoreThingOrigColor();

	}

	public void testingPause() {
		if (this.testingTimer == null) {
			return;
		}
		this.testingTimer.testingPause();
		// pause all testing effects
		Enumeration en = this.testingElements.elements();
		for (; en.hasMoreElements();) {
			((TestingEntry) en.nextElement()).pauseTesting();
		}
	}

	public void testingContinue() {		
		try {
			this.testingTimer.testingContinue();
			// continue all testing effects
			Enumeration en = this.testingElements.elements();
			for (; en.hasMoreElements();) {
				((TestingEntry) en.nextElement()).continueTesting();
			}			
		} catch (Exception ex) {
			OpcatLogger.logError(ex) ; 
		}
	}

	public void testingActivate() {
		
		if ((this.testingTimer == null) || this.testingTimer.isPause()) {
			// restart if we where in a pause
			this.testingContinue();
		}
		Enumeration en = this.mySys.getCurrentIXOpd().getSelectedItems();
		for (; en.hasMoreElements();) {
			// this.hasNotingToDO = false ;
			// activate selected items
			IXEntry iEntry = ((IXInstance) en.nextElement()).getIXEntry();
			TestingEntry aEntry = this.findEntryById(iEntry.getId());
			// objects can be activated without preconditions
			// other entries should be checked before the activation
			if (this.checkBeforeActivate(aEntry, null)
					|| TestingAuxiliary.isObject(aEntry.getIXEntry())
					|| TestingAuxiliary.isState(aEntry.getIXEntry())) {
				this.activate(aEntry);
				// this.hasNotingToDO = false ;
			}
		}
		// continue one step forward after manual activate
		if (TestingSettings.STEP_BY_STEP_MODE) {
			this.testingForward(1);
		}
	}

	public void testingDeactivate() {
		Enumeration en = this.mySys.getCurrentIXOpd().getSelectedItems();
		for (; en.hasMoreElements();) {
			// deactivate selected items
			IXEntry iEntry = ((IXInstance) en.nextElement()).getIXEntry();
			this.deactivate(this.findEntryById(iEntry.getId()));
		}
	}

	public void testingForward(long numberOfSteps) {
		// if application is not running - return
		if (this.testingTimer == null) {
			return;
		}
		// exit pause mode
		this.testingContinue();

		// schedule pause after numberOfSteps steps
		try {
			this.testingTimer.schedule(new TimerTask() {
				public void run() {
					TestingSystem.this.testingPause();
				}
			}, numberOfSteps * TestingSettings.STEP_DURATION);
		} catch (IllegalStateException e) {
			this.testingTimer = new TestingTimer(this);
		}
	}

	public void testingBackward(long numberOfSteps) {
		// validate number of steps
		if (numberOfSteps >= this.currentStep) {
			// return to the starting point
			numberOfSteps = this.currentStep;
			this.deactivate();
		} else {
			Enumeration en = this.testingElements.elements();
			for (; en.hasMoreElements();) {
				TestingEntry aEntry = (TestingEntry) en.nextElement();
				int numOfIns = aEntry.getNumberOfInstances();
				// restore the number of instances and the testing status of
				// the entry itself
				aEntry.restore(this.currentStep, this.currentStep
						- numberOfSteps);
				// update the generalization
				// check how many instances were added/removed from this entry
				if (TestingAuxiliary.isThing(aEntry.getIXEntry())) {
					numOfIns = numOfIns - aEntry.getNumberOfInstances();
					if (numOfIns != 0) {
						TestingEvent aEventTemp = new TestingEvent();
						if (numOfIns > 0) {
							aEventTemp
									.setEventType(TestingSettings.TERMINATION_EVENT);
						} else {
							aEventTemp
									.setEventType(TestingSettings.ACTIVATION_EVENT);
						}
						if (numOfIns < 0) {
							numOfIns = (-numOfIns);
						}
						aEventTemp.setNumberOfInstances(numOfIns);
						this.treatGeneralization((IXThingEntry) aEntry
								.getIXEntry(), aEventTemp);
					}
				}
				// if the restored entry is a process and it is active after the
				// restore
				// animate its consumption and result links.
				if (TestingAuxiliary.isProcess(aEntry.getIXEntry())
						&& (aEntry.getNumberOfInstances() > 0)) {
				}
			}
		}
		this.retreatStep(numberOfSteps); 
	}

	// ////////////////// activate ////////////////////////

	public void activate(TestingEntry aEntry) {
		this.activate(aEntry, null);
	}

	public void activate(TestingEntry aEntry, TestingEvent aEvent) {
		// activate
		aEvent = this.activateWithoutConsequences(aEntry, aEvent);
		// treat consequences
		this.treatActivationConsequences(aEntry, aEvent);
	}

	// this function just activates the the entry
	// without treating consequences
	// this function is used when consequences should not be treated
	// immediately: in activateLevel function, where we first activate
	// all processes on certain level and then treat consequences of each one.
	private TestingEvent activateWithoutConsequences(TestingEntry aEntry,
			TestingEvent aEvent) {
		if (aEntry != null) {
			if (aEvent == null) {
				aEvent = new TestingEvent();
			}
			// set scheduling step and step (this is requried for undo)
			aEvent.setSchedulingStep(this.currentStep);
			aEvent.setStep(this.currentStep);
			aEvent.setEventType(TestingSettings.ACTIVATION_EVENT);
			// for children processes: set parent and executionId, if it still
			// empty
			this.setProcessContext(aEntry, aEvent);
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
	// schedule another activation to the next step (the system will try to
	// activate
	// this process until it will succeed.
	// 4. if the process passed the validations - activate it.
	// If no activations were performed or scheduled (this can happen if
	// all the processes on this level could not be activated due to condition),
	// then the function recursively calls to itself to activate for the next
	// level
	// the function returns true if it activates at least one child process
	// and false otherwise
	private boolean activateLevel(Enumeration enChildProcesses,
			TestingEvent aInputChildEvent) {
		TestingAuxiliary.debug("Starting activateLevel");
		boolean proceedToNextLevel = true;
		TestingEntry aChildProcess = null;
		Vector activatedProcesses = new Vector();
		Vector activatedEvents = new Vector();
		for (; enChildProcesses.hasMoreElements();) {
			TestingEvent aChildEvent = new TestingEvent(aInputChildEvent);
			IXProcessInstance childProcessInstance = (IXProcessInstance) enChildProcesses
					.nextElement();
			aChildProcess = this.findEntryById(childProcessInstance
					.getIXEntry().getId());
			// ignore instantion/feature/specialization/particulation processes
			if (this.isRelatedProcess(aChildProcess, aChildEvent
					.getParentProcess())) {
				continue;
			}
			TestingAuxiliary.debug("Before checkProcessBeforeActivate for "
					+ aChildProcess.getIXEntry().getName());
			// set the indication that the process is to be activated in parents
			// context
			TestingEntry oldParent = aChildProcess.getCurrentEvent()
					.getParentProcess();
			aChildProcess.getCurrentEvent().setParentProcess(
					aChildEvent.getParentProcess());
			TestingAuxiliary.debug("activate level: set parent "
					+ aChildEvent.getParentProcess().getIXEntry().getName());
			TestingAuxiliary.debug(" for child process "
					+ aChildProcess.getIXEntry().getName());

			// check general validations before process activation
			// child process can be activated automatically
			// only if there is no events linked to it
			boolean eventsFound = this.findEvents(aChildProcess, aChildEvent
					.getParentProcess());
			aChildProcess.getCurrentEvent().setWaitForEvent(true);

			if (!eventsFound
					&& this.checkProcessBeforeActivate(aChildProcess, null)) {
				aChildProcess.getCurrentEvent().setWaitForEvent(false);
				TestingAuxiliary
						.debug("After (success) checkProcessBeforeActivate for "
								+ aChildProcess.getIXEntry().getName());
				// activate the process if it is valid
				this.activateWithoutConsequences(aChildProcess, aChildEvent);
				activatedProcesses.add(aChildProcess);
				activatedEvents.add(aChildEvent);
				proceedToNextLevel = false;
			} else {
				aChildProcess.getCurrentEvent().setWaitForEvent(false);
				TestingAuxiliary
						.debug("After (failure) checkProcessBeforeActivate for "
								+ aChildProcess.getIXEntry().getName());
				// if there is an unavalaible condition link
				// other processes should not wait to this process
				// otherwise schedule future activation until first successful
				// activation
				if (!this.findUnavailableCondition(aChildProcess)) {
					// the process should be activated by event, not when
					// handling the
					// future request, therefore number of instances is 0
					// future event is scheduled only to indicate that parent
					// process can not terminate yet
					if (eventsFound) {
						aChildEvent.setNumberOfInstances(0);
						// schedule future activation to current step (without
						// recurring ind)
						// this is necessary if event will activate the process
						// already
						// on current step
						TestingEvent aChildEventTemp = new TestingEvent(
								aChildEvent);
						aChildEventTemp.setRecurringInd(false);
						aChildEventTemp.setStep(this.currentStep);
						aChildEventTemp.setSchedulingStep(this.currentStep);
						aChildProcess.scheduleActivation(aChildEventTemp);
					}
					// schedule future activation to next step with recurring
					// ind
					TestingAuxiliary
							.debug("After findUnavailableCondition for "
									+ aChildProcess.getIXEntry().getName());
					TestingAuxiliary.debug("Schedule recuring event: setStep "
							+ this.currentStep + ", setSchedulingStep "
							+ this.currentStep);
					aChildEvent.setRecurringInd(true);
					aChildEvent.setStep(this.currentStep + 1);
					aChildEvent.setSchedulingStep(this.currentStep);
					// this was added to solve conditions links problem.
					// aChildEvent.setNumberOfInstances(aChildProcess.getNumberOfInstances());
					aChildProcess.scheduleActivation(aChildEvent);
					proceedToNextLevel = false;
				}
				// if process can not be activated restore its parent process
				aChildProcess.getCurrentEvent().setParentProcess(oldParent);
			}
		} // end for

		// go through the list of activated processes and
		// treatActivationConsequences
		// for each one. The separation between activate and treat consequences
		// here is required to ensure that all processes activated. Otherwise,
		// it can happen that one of activated process terminates immediately
		// (if
		// neither of its children can be executed due to unavailable condition)
		// and then parent might be cancelled before it tries to activate other
		// children.
		for (int i = 0; i < activatedProcesses.size(); i++) {
			this.treatProcessActivationConsequences(
					(TestingEntry) activatedProcesses.get(i),
					(TestingEvent) activatedEvents.get(i));
		}

		// if nothing was activated on this level, and no future activations
		// were scheduled, although the level is not empty -
		// proceed immediately to the next level
		if ((proceedToNextLevel) && (aChildProcess != null)) {
			TestingAuxiliary.debug("proceedToNextLevel");
			this.treatChildProcessTerminationConsequences(aChildProcess,
					new TestingEvent(aInputChildEvent));
			return true;
		}

		// if there are no more children - the process is the last child process
		// terminate the parent
		if (aChildProcess == null) {
			return false;
		}

		return true;
	}

	// an auxiliary function that ascribes a child process activated by
	// event to some execution of its parent.
	private void setProcessContext(TestingEntry aEntry, TestingEvent aEvent) {
		if (TestingAuxiliary.isProcess(aEntry.getIXEntry())
				&& aEntry.getParents().hasMoreElements()
				&& (aEvent.getExecutionId() == 0)) {
			aEvent
					.setParentProcess(aEntry.getCurrentEvent()
							.getParentProcess());
			aEvent.setExecutionId(aEntry.getCurrentEvent().getExecutionId());
			// aEntry.getCurrentEvent(aEntry.getCurrentEvent().getExecutionId())
		}
	}

	// ////////////////// deactivate ////////////////////////

	// this function deactivates all testing entries
	public void deactivate() {
		Enumeration en = this.testingElements.elements();
		for (; en.hasMoreElements();) {
			TestingEntry aEntry = (TestingEntry) en.nextElement();
			TestingEvent aEvent = new TestingEvent();
			aEvent.setNumberOfInstances(aEntry.getNumberOfInstances());
			aEvent.setStep(this.currentStep);
			aEvent.setEventType(TestingSettings.TERMINATION_EVENT);
			aEvent.setExecutionId(0);
			TestingAuxiliary.debug("deactivate "
					+ aEntry.getIXEntry().getName());
			aEntry.deactivate(aEvent);
			aEntry.clear();
		}
		this.opdStack.clear();
		TestingSettings.LAST_EXECUTION_ID = 0;
	}

	// this function deactivates specific testing entry
	public void deactivate(TestingEntry aEntry) {
		if (aEntry.isActive()) {
			this.deactivate(aEntry, new TestingEvent());
		}
	}

	// this function deactivates specific testing entry
	// according to the settings passed in TestingEvent object
	public void deactivate(TestingEntry aEntry, TestingEvent aEvent) {
		if (aEntry != null) {
			if (aEvent == null) {
				aEvent = new TestingEvent();
			}
			// set scheduling step and step (this is requried for undo)
			if (aEvent.getSchedulingStep() == 0) {
				aEvent.setSchedulingStep(this.currentStep);
			}
			if (aEvent.getStep() == 0) {
				aEvent.setStep(this.currentStep);
			}
			// deactivate
			aEntry.deactivate(aEvent);
			// treat consequences
			this.treatTerminationConsequences(aEntry, aEvent);

		}
	}

	// ////////////////// find ////////////////////////

	// this function searches for a entry with the given id
	private TestingEntry findEntryById(long entryId) {
		Enumeration en = this.testingElements.elements();
		TestingEntry abstractEntry;
		long id;
		for (; en.hasMoreElements();) {
			abstractEntry = (TestingEntry) en.nextElement();
			id = abstractEntry.getIXEntry().getId();
			if (id == entryId) {
				return abstractEntry;
			}
		}
		return null;
	}

	private void advanceStep() {
		this.currentStep++;
		this.guiPanel.showTestingStatus(1, this.currentStep);
	}

	private void retreatStep(long numberOfSteps) {
		this.currentStep -= numberOfSteps;
		this.guiPanel.showTestingStatus(2, this.currentStep);
	}

	// ////////////////// first step ////////////////////////
	// this function is called when the testing starts running
	// it initiates the objects according to object initiation rules
	public void firstStep() {
		// return if pause mode
		if ((this.testingTimer == null) || this.testingTimer.isPause()) {
			return;
		}
		this.debugEvents = new Hashtable();
		Enumeration en = this.testingElements.elements();
		TestingEntry aEntry;
		this.advanceStep();
		for (; en.hasMoreElements();) {
			aEntry = (TestingEntry) en.nextElement();
			// initially objects and states are activated without any trigger
			if (TestingSettings.AUTOMATIC_INITIATION) {
				if (TestingAuxiliary.isObject(aEntry.getIXEntry())) {
					// activate the object only if it is not inside a zoomed in
					// process
					if (!aEntry.getParents().hasMoreElements()) {
						this.initiateObject(aEntry);
					}
				}
			}
		}
		
		lifeSpanGrid.NextStep(mySys, 1); 		
		
	}

	// this function checks whether the object can be activated
	// according to the initial activation rules
	// and activates it if it passes the validation
	private void initiateObject(TestingEntry aObject) {
		if (this.checkObjectBeforeActivate(aObject)) {
			// object is initiated with the default number of instances
			// which is specified in MAS folder
			int insNum = aObject.getDefaultNumberOfInstances();
			if (insNum <= 1) {
				if (TestingSettings.ONE_OBJECT_INSTANCE) {
					insNum = 1; // replace with default
				} else {
					insNum = TestingSettings.MAX_OBJECT_INSTANCES; // replace
					// with
					// default
				}
			}
			TestingEvent actvEvent = new TestingEvent();
			actvEvent.setStep(this.currentStep);
			actvEvent.setNumberOfInstances(insNum);
			this.activate(aObject, actvEvent);
		}
	}

	private void testingChecking(TestingEvent tempEvent,
			TestingEntry tempEntry, String testingAction) {
		try {
			if ((this.debugEvents.size() > 0)
					&& (this.debugEvents.get(new Long(tempEvent
							.getExecutionId())) != null)) {
				this.TestingGridAddRow(testingAction, tempEntry, tempEvent);
			} else {
				this.debugEvents.put(new Long(tempEvent.getExecutionId()),
						tempEvent);
			}
		} catch (Exception e) {
			// APILogger.SetDebug(true) ;
			APILogger.Print(this, e.getMessage());
		}
	}

	// ////////////////// next step ////////////////////////
	// this function is called whenever testing performes a recomputation
	// consistent with the configured step duration
	// it goes through the list of terminations scheduled to the current step
	// and performs the events that pass the validations
	// then it treats the activations scheduled to the current step
	public void nextStep() {
		// if testing is paused - do nothing or there is nothing to do
		if (this.testingTimer.isPause()) {
			return;
		}

		Enumeration en = this.testingElements.elements();
		TestingEntry aEntry;
		this.advanceStep();
		hasTerminations = false;
		hasActivation = false;

		// go through the testing elements
		// and retrieve a list of scheduled events (first terminations and then
		// activations for each element
		for (; en.hasMoreElements();) {
			aEntry = (TestingEntry) en.nextElement();
			if (aEntry.getIXEntry() instanceof IXLinkEntry) {
				int linkType = ((IXLinkEntry) aEntry.getIXEntry())
						.getLinkType();
				if (linkType == exportedAPI.OpcatConstants.AGENT_LINK) {
					// TestingGridAddRow(TestingAuxiliary.ActionActivate,aEntry,
					// TestingAuxiliary.ActionResultUnknown, "Activate
					// Manualy") ;
				}
			}
			// this was added to debug condition link bug
			if (TestingAuxiliary.isProcess(aEntry.getIXEntry())) {
				// String s =
				// ((IXStateEntry)aEntry.getIXEntry()).getParentIXObjectEntry().getName().toString();
				String s = aEntry.getIXEntry().getName().toString();
				int i = 0;
				if (s.lastIndexOf("Transaction\nProcessing") > -1) {
					i++;
				}
			}
			// get scheduled terminations
			Enumeration termEvents = aEntry
					.getScheduledTerminations(this.currentStep);
			if (!hasTerminations) {
				hasTerminations = termEvents.hasMoreElements();
			}
			for (; termEvents.hasMoreElements();) {
				// treat termination event
				TestingEvent currentEvent = (TestingEvent) termEvents
						.nextElement();

				// deactivate the entry if the event is not cancelled and the
				// entry
				// passes termination conditions
				if ((currentEvent.getCancelStep() == 0)
						&& this.checkBeforeTerminate(aEntry, currentEvent)) {
					TestingAuxiliary.debug("next Step: deactivate entry "
							+ aEntry.getIXEntry().getName());

					// TestingGridAddRow(TestingAuxiliary.ActionStartingTask,aEntry,
					// TestingAuxiliary.ActionResultUnknown, "Trying to
					// deactivate " + aEntry.getIXEntry().getName()) ;
					this.deactivate(aEntry, currentEvent);
					// TestingGridAddRow(TestingAuxiliary.ActionFinishedTask,aEntry,
					// TestingAuxiliary.ActionResultUnknown, "Finished
					// deactivate of " + aEntry.getIXEntry().getName()) ;

				} else {
					// check event if need to be printed in the log
					if (currentEvent.getCancelStep() != 0) {
						// event cancled
						aEntry
								.setTaxtualInfo("Event Cancled by Time Limits in Step - "
										+ currentEvent.getCancelStep());
					}
					this.testingChecking(currentEvent, aEntry,
							TestingAuxiliary.ActionTerminate);
				}
			}
			// get scheduled activations
			Enumeration actvEvents = aEntry
					.getScheduledActivations(this.currentStep);
			if (!hasActivation) {
				hasActivation = actvEvents.hasMoreElements();
			}
			for (; actvEvents.hasMoreElements();) {
				TestingEvent currentEvent = (TestingEvent) actvEvents
						.nextElement();
				//        
				// if (currentEvent.getParentProcess() != null &&
				// TestingAuxiliary.isThing(currentEvent.getParentProcess().getIXEntry()))
				// {
				// try {
				// String url = ((IXThingEntry)
				// currentEvent.getParentProcess().getIXEntry()).getUrl() ;
				// if ((url != null || !url.equalsIgnoreCase("none")) &&
				// (url.endsWith("jpg") || url.endsWith("bmp") ||
				// url.endsWith("jpeg")))
				// BrowserLauncher.openURL(url) ;
				// } catch (IOException e ) {
				// APILogger.Print(this, e.getMessage()) ;
				// }
				// }

				synchronized (currentEvent) {
					// set the indication that the process is to be activated in
					// parents context
					TestingEntry oldParent = aEntry.getCurrentEvent()
							.getParentProcess();
					aEntry.getCurrentEvent().setParentProcess(
							currentEvent.getParentProcess());
					// activate the entry if the event is not cancelled and the
					// entry
					// passes activation conditions
					// number of instances 0 indicates waiting for event (child
					// process
					// inside a zoomed in OPD can be activated only by event,
					// therefore
					// next step ignores it).
					Vector linkTypes = new Vector();
					linkTypes.add(new Integer(
							exportedAPI.OpcatConstants.RESULT_LINK));
					linkTypes.add(new Integer(
							exportedAPI.OpcatConstants.INVOCATION_LINK));
					linkTypes.add(new Integer(
							exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
					linkTypes.add(new Integer(
							exportedAPI.OpcatConstants.CONSUMPTION_LINK));

					if ((currentEvent.getCancelStep() == 0)
							&& (currentEvent.getNumberOfInstances() > 0)
							&& this.checkBeforeActivate(aEntry, currentEvent)) {
						TestingAuxiliary.debug("next Step: activate entry "
								+ aEntry.getIXEntry().getName());
						currentEvent.setWaitForEvent(false);
						this.activate(aEntry, currentEvent);
					} else {
						if (currentEvent.getCancelStep() != 0) {
							// event cancled
							aEntry
									.setTaxtualInfo("Event Cancled by Time Limits in Step - "
											+ currentEvent.getCancelStep());
						}
						if (currentEvent.getNumberOfInstances() == 0) {
							// zero instances.
							if (TestingAuxiliary.isProcess(aEntry.getIXEntry())) {
								if (aEntry.getIXEntry().getId() != this.mySys
										.getCurrentIXOpd().getMainIXEntry()
										.getId()) {
									aEntry
											.setTaxtualInfo("Parent Process not consistent with upper levels ?");
								} else {
									aEntry
											.setTaxtualInfo("Process not consistent with upper levels ?");
								}
							} else {
								aEntry
										.setTaxtualInfo("Problem with Conditions");
							}
						}
						// if the entry hasn't pass the validations restore
						// parent process
						aEntry.getCurrentEvent().setParentProcess(oldParent);
						// reschedule to the next step if the recurring ind is
						// checked
						if (currentEvent.getRecurringInd()) {
							TestingAuxiliary
									.debug("nextStep: reschedule activation for "
											+ aEntry.getIXEntry().getName()
											+ " to step "
											+ (this.currentStep + 1));
							TestingEvent aEvent = new TestingEvent(currentEvent);
							aEvent.setSchedulingStep(this.currentStep);
							aEvent.setStep(this.currentStep + 1);
							aEntry.scheduleActivation(aEvent);
							aEntry.setTaxtualInfo(aEntry.getTaxtualInfo()
									+ ", Will try again in step - "
									+ (this.currentStep + 1));
						}
						this.testingChecking(currentEvent, aEntry,
								TestingAuxiliary.ActionActivate);
					}
				} // end synchronized
			}
		}
		if (!hasActivation && !hasTerminations) {
			en = this.testingElements.elements();
			for (; en.hasMoreElements();) {
				if (this.mySys.getCurrentIXOpd().getMainIXEntry() != null) {
					aEntry = (TestingEntry) en.nextElement();
					if (TestingAuxiliary.isProcess(aEntry.getIXEntry())) {
						if (aEntry.getIXEntry().getId() != this.mySys
								.getCurrentIXOpd().getMainIXEntry().getId()) {
							// aEntry.setTaxtualInfo("Inconsistent Model, Parent
							// Process does nothing Or not consistent with upper
							// levels ?");
						} else {
							aEntry
									.setTaxtualInfo("Process does nothing Or Process not consistent with upper levels Conditions ?");
							this
									.TestingGridAddRow(
											TestingAuxiliary.ActionGlobel,
											aEntry, null);
						}
					}
				}
			}
		}
		
		lifeSpanGrid.NextStep(mySys,currentStep); 	
	}

	// ////////////////// validations before activation ////////////////////////
	private boolean checkBeforeActivate(TestingEntry aEntry, TestingEvent aEvent) {
		if (TestingAuxiliary.isObject(aEntry.getIXEntry())) {
			return this.checkObjectBeforeActivate(aEntry);
		}

		if (TestingAuxiliary.isProcess(aEntry.getIXEntry())) {
			return this.checkProcessBeforeActivate(aEntry, aEvent);
		}

		if (TestingAuxiliary.isLink(aEntry.getIXEntry())) {
			return this.checkLinkBeforeActivate(aEntry, (IXLinkEntry) aEntry
					.getIXEntry());
		}
		return false;
	}

	// this function checks whether object is valid to be activated
	// it is called only by initiation procedure
	// An object can be activated if it is not a result
	// and if it is not a feature of another object
	private boolean checkObjectBeforeActivate(TestingEntry aEntry) {
		// result can't be active unless it is activated as a consequence
		IXObjectEntry iObject = (IXObjectEntry) aEntry.getIXEntry();
		if (this.isResult(iObject)) {
			aEntry.setTaxtualInfo("Object is a Result");
			return false;
		}
		// if the object is a feature of another object it can not be activated
		// unless as a consequence of root object activation
		if (aEntry.isFeature()) {
			aEntry.setTaxtualInfo("Object is a Feature");
			return false;
		}
		aEntry.setTaxtualInfo("");
		return true;
	}

	// this function checks whether the given object is result
	// of some process. Object O is result if the following is true:
	// 1. There is a result link between a process P to O or to some state of O
	// and there is no consumption link from O or one of its states to P
	// 2. A generalization of O is a result.
	private boolean isResult(IXObjectEntry iObject) {
		// initialize
		Vector allResultLinks = new Vector();
		Vector allConsumptionLinks = new Vector();
		Vector linkTypesResults = new Vector();
		linkTypesResults
				.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
		Vector linkTypesConsumption = new Vector();
		linkTypesConsumption.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
		linkTypesConsumption.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_LINK));
		boolean isResult = true;

		// get own incoming result links
		allResultLinks.addAll(this.getLinkByType(this
				.getLinksByDestination(iObject), linkTypesResults));

		// get own outgoing consumption links
		allConsumptionLinks.addAll(this.getLinkByType(this
				.getLinksBySource(iObject), linkTypesConsumption));

		// get incoming result links and outgoing consumption links of all
		// states
		Enumeration enStates = iObject.getStates();
		for (; enStates.hasMoreElements();) {
			IXStateEntry iState = (IXStateEntry) enStates.nextElement();
			// get list of result links incoming to the state
			allResultLinks.addAll(this.getLinkByType(this
					.getLinksByDestination(iState), linkTypesResults));
			allConsumptionLinks.addAll(this.getLinkByType(this
					.getLinksBySource(iState), linkTypesConsumption));
		}

		// go through both link lists
		for (int i = 0; i < allResultLinks.size(); i++) {
			IXLinkEntry iResultLink = (IXLinkEntry) allResultLinks.get(i);
			isResult = true;
			// for each result link find corresponding consumption link
			// if not found return true - the object is result
			for (int j = 0; (j < allConsumptionLinks.size()) && isResult; j++) {
				IXLinkEntry iConsumptionLink = (IXLinkEntry) allConsumptionLinks
						.get(j);
				if (iResultLink.getSourceId() == iConsumptionLink
						.getDestinationId()) {
					// found pair of consumption and result
					isResult = false;
				}
			}
			// found result link without corresponding consumption link - return
			// true
			// the object is a result
			if (isResult) {
				return true;
			}
		}

		// check if object's generalization is a product
		Enumeration en = iObject.getRelationByDestination();
		for (; en.hasMoreElements();) {
			IXRelationEntry relation = (IXRelationEntry) en.nextElement();
			if (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION) {
				// check if relations source is result, by this recursive call
				if (this.isResult((IXObjectEntry) this.findEntryById(
						relation.getSourceId()).getIXEntry())) {
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
	// 2. If process is activated by an external event, it is allowed to be
	// activated
	// only if parent process waits for this event
	// ***Validations relevant for all processes***
	// 1. If the process was triggered by event (instrument event link or
	// consumption event link) the source of this event should be active
	// and if this event is marked with a path label, this path should be valid
	// 2. Incoming links (instrument, comnsumption, effect and condition) should
	// constitute a valid combination consistent with defined paths and
	// logical conditions (not supported yet)
	// LERA!!! - adding logical conditions is here!!!!!!!!
	private boolean checkProcessBeforeActivate(TestingEntry aProcess,
			TestingEvent aEvent) {
		TestingAuxiliary.debug("Starting checkProcessBeforeActivate for "
				+ aProcess.getIXEntry().getName());
		// if the process is a child process inside a zoomed in process
		// it can be activated only if the parent is active
		Enumeration enParents = aProcess.getParents();
		TestingEntry aParent = null;
		TestingEntry oldParent = aProcess.getCurrentEvent().getParentProcess();
		TestingAuxiliary.debug("oldParent = " + oldParent);
		// future enhancement: support multiple parents
		if (enParents.hasMoreElements()) {
			IXProcessEntry parent = (IXProcessEntry) enParents.nextElement();
			aParent = this.findEntryById(parent.getId());
			// if found a not active parent
			// and process is not activated by event link then
			// return false
			if (!aProcess.isEventLinkActivated() && !aParent.isActive()) {
				aProcess.setTaxtualInfo("Parent, "
						+ aParent.getIXEntry().getName() + ", Is still Active");
				return false;
			}

			// check if the process was triggered by internal event
			boolean isInternalEvent = this.isTriggeredByInternalEvent(aProcess,
					aParent, aEvent);

			// if the process activation was triggered from activateLevel
			// function
			// or from nextStep function the waitForEvent ind will be switched
			// on
			// if it is not switched on - the process was triggered by an
			// external
			// event. In this case we should check whether the parent waits for
			// this
			// event
			if (!aProcess.getCurrentEvent().getWaitForEvent()
					&& !isInternalEvent) {
				// if ((aEvent != null) && (aEvent.getEventLink() != null) &&
				// !isInternalEvent) {
				TestingAuxiliary.debug("wait for event "
						+ aProcess.getCurrentEvent().getWaitForEvent());
				// process can be activated by event only if parent process
				// waits for
				// this event, i.e. a future activation is scheduled
				Enumeration enEvents = aProcess
						.getScheduledActivations(this.currentStep);
				TestingAuxiliary.debug("getScheduledActivations for step "
						+ this.currentStep);
				boolean found = false;
				for (; enEvents.hasMoreElements();) {
					TestingEvent aEventTemp = (TestingEvent) enEvents
							.nextElement();
					TestingAuxiliary.debug("aEventTemp.getExecutionId() = "
							+ aEventTemp.getExecutionId());
					// find the active parent according to the exceution id
					for (Enumeration enP = aProcess.getParents(); enP
							.hasMoreElements();) {
						IXEntry iParent = (IXEntry) enP.nextElement();
						TestingEntry aParentTemp = this.findEntryById(iParent
								.getId());
						if (aParentTemp.isActive(aEventTemp.getExecutionId())) {
							// if (aEventTemp.getExecutionId() ==
							// aParent.getExecutionId()) {
							aParent = aParentTemp;
							found = true;
						}
					}
				}

				// parent process do not wait for this event - return false
				// unless the event is internal
				if (!found) {
					TestingAuxiliary
							.debug("Could not find scheduled activation");
					// TestingGridAddRow(TestingAuxiliary.ActionActivate,
					// aProcess, false, "REASON TBD");
					aProcess
							.setTaxtualInfo("Could not find scheduled activation");
					return false;
				}

				// Parent process should be set here, because it is used further
				// is this function
				// to obtain the links of the process, including those inherited
				// from parent
				aProcess.getCurrentEvent().setParentProcess(aParent);
				aProcess.getCurrentEvent().setExecutionId(
						aParent.getExecutionId());
				TestingAuxiliary.debug("setExecutionId "
						+ aParent.getExecutionId());
			}

			// copy parent and execution id to current event
			// they are used further
			if (aEvent != null) {
				if ((aEvent.getEventLink() != null)
						&& (aProcess.getCurrentEvent().getParentProcess() != null)) {
					// copy parent and execution id from the process
					aEvent.setParentProcess(aProcess.getCurrentEvent()
							.getParentProcess());
					TestingAuxiliary.debug("aEvent.setParentProcess "
							+ aProcess.getCurrentEvent().getParentProcess());
					if (!isInternalEvent) {
						aEvent.setExecutionId(aProcess.getCurrentEvent()
								.getExecutionId());
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
			if (aEvent.getEventLink() != null) {
				IXLinkEntry triggerEvent = (IXLinkEntry) aEvent.getEventLink()
						.getIXEntry();
				int linkType = triggerEvent.getLinkType();
				// if the process was activated by consumption or instrument
				// event link
				// check that the object on the second end of this link is
				// active
				if ((linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK)
						|| (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK)) {
					linkTypes.add(new Integer(linkType));
				}
				// if the process was activated by event
				// and there is a path defined for this event -
				// this path should be valid
				if (!triggerEvent.getPath().equals("")) {
					if (this.checkPath(triggerEvent.getPath(), this
							.getLinksByDestination((IXProcessEntry) aProcess
									.getIXEntry(), linkTypes))) {
						TestingAuxiliary
								.debug("After checkPathDestination for trigger event "
										+ triggerEvent.getName());
						// store valid path on the process
						aProcess.setPath(triggerEvent.getPath());
						TestingAuxiliary.debug("Set path "
								+ triggerEvent.getPath());
						aProcess.setTaxtualInfo("");
						return true;
					}
					// return false if the path of the trigger is not valid
					aProcess.setTaxtualInfo("Not valid Path ("
							+ triggerEvent.getPath() + ") of "
							+ triggerEvent.getName());
					return false;
				}
			}
		}

		// check if incoming links allow process activation
		// if a path is defined for at least one of the links - this path should
		// be valid
		// all links without path should be valid, unless there is a logical
		// condition
		Vector v = this.getProcessLinks((IXProcessEntry) aProcess.getIXEntry(),
				linkTypes, false);
		for (Enumeration en = v.elements(); en.hasMoreElements();) {
			IXLinkEntry iLinkEntry = (IXLinkEntry) en.nextElement();
			if (!iLinkEntry.getPath().equals("")) {
				// this variable indicates if atleast one of the links has path
				pathExist = true;
			}
			TestingAuxiliary.debug("Before checkLinkBeforeActivate for link "
					+ iLinkEntry.getName());

			boolean retVal = this.checkLinkBeforeActivate(aProcess, iLinkEntry);
			// TestingGridAddRow(TestingAuxiliary.ActionActivate, aProcess,
			// retVal,iLinkEntry.getName() + " - " + str);

			if (retVal) {
				TestingAuxiliary
						.debug("After checkLinkBeforeActivate for link "
								+ iLinkEntry.getName());
				// At least one path should be valid
				if (!iLinkEntry.getPath().equals("")) {
					TestingAuxiliary
							.debug("Before checkPathDestination for link "
									+ iLinkEntry.getName());
					if (this.checkPath(iLinkEntry.getPath(), v.elements())) {
						TestingAuxiliary
								.debug("After checkPathDestination for link "
										+ iLinkEntry.getName());
						// store valid path on the process
						aProcess.setPath(iLinkEntry.getPath());
						TestingAuxiliary.debug("Set path "
								+ iLinkEntry.getPath());
						aProcess.setTaxtualInfo("");
						return true;
					}
				}
			} else {
				// if link's path is empty - the link is mandatory
				// return false if it is not available
				// if link has a path - don't return false meanwhile
				// because may be another path will be found valid
				if (iLinkEntry.getPath().equals("")) {
					// revert old parent and return
					aProcess.getCurrentEvent().setParentProcess(oldParent);
					aProcess.setTaxtualInfo("Problem with link From ( "
							+ this.mySys.getIXSystemStructure().getIXEntry(
									iLinkEntry.getSourceId()).getName() + " )");
					return false;
				}
				// check logical operators. May be there is another link
				// is available
				// return false;
			}
		}
		// if atleast one link has a path - the process can be activated
		// only if one of the paths is valid.
		// if the execution acheives this point - no valid path exist
		// therefore the process can not be activated
		if (pathExist) {
			// revert ald parent and return
			aProcess.getCurrentEvent().setParentProcess(oldParent);
			aProcess.setTaxtualInfo("No valid path exists");
			return false;
		}

		// there is no path and all links without path are valid
		// store empty path
		aProcess.setPath("");
		aProcess.setTaxtualInfo("");
		return true;
	}

	// private boolean checkLinksBeforeActivate(TestingEntry aProcess,
	// TestingEvent aEvent) {
	// TestingAuxiliary.debug("Starting checkProcessBeforeActivate for "
	// + aProcess.getIXEntry().getName());
	// // if the process is a child process inside a zoomed in process
	// // it can be activated only if the parent is active
	// Enumeration enParents = aProcess.getParents();
	// TestingEntry aParent = null;
	// TestingEntry oldParent = aProcess.getCurrentEvent()
	// .getParentProcess();
	// TestingAuxiliary.debug("oldParent = " + oldParent);
	// // future enhancement: support multiple parents
	// if (enParents.hasMoreElements()) {
	// IXProcessEntry parent = (IXProcessEntry) enParents.nextElement();
	// aParent = findEntryById(parent.getId());
	// // if found a not active parent return false
	// if (!aParent.isActive()) {
	// return false;
	// }
	//
	// // check if the process was triggered by internal event
	// boolean isInternalEvent = isTriggeredByInternalEvent(aProcess,
	// aParent, aEvent);
	//
	// // if the process activation was triggered from activateLevel
	// // function
	// // or from nextStep function the waitForEvent ind will be switched
	// // on
	// // if it is not switched on - the process was triggered by an
	// // external
	// // event. In this case we should check whether the parent waits for
	// // this
	// // event
	// if (!isInternalEvent) {
	// // if ((aEvent != null) && (aEvent.getEventLink() != null) &&
	// // !isInternalEvent) {
	// TestingAuxiliary.debug("wait for event "
	// + aProcess.getCurrentEvent().getWaitForEvent());
	// // process can be activated by event only if parent process
	// // waits for
	// // this event, i.e. a future activation is scheduled
	// Enumeration enEvents = aProcess
	// .getScheduledActivations(currentStep);
	// TestingAuxiliary.debug("getScheduledActivations for step "
	// + currentStep);
	// boolean found = false;
	// for (; enEvents.hasMoreElements();) {
	// TestingEvent aEventTemp = (TestingEvent) enEvents
	// .nextElement();
	// TestingAuxiliary.debug("aEventTemp.getExecutionId() = "
	// + aEventTemp.getExecutionId());
	// // find the active parent according to the exceution id
	// for (Enumeration enP = aProcess.getParents(); enP
	// .hasMoreElements();) {
	// IXEntry iParent = (IXEntry) enP.nextElement();
	// TestingEntry aParentTemp = findEntryById(iParent
	// .getId());
	// if (aParentTemp.isActive(aEventTemp.getExecutionId())) {
	// // if (aEventTemp.getExecutionId() ==
	// // aParent.getExecutionId()) {
	// aParent = aParentTemp;
	// found = true;
	// }
	// }
	// }
	//
	// // parent process do not wait for this event - return false
	// // unless the event is internal
	// if (!found) {
	// TestingAuxiliary
	// .debug("Could not find scheduled activation");
	// return false;
	// }
	//
	// // Parent process should be set here, because it is used further
	// // is this function
	// // to obtain the links of the process, including those inherited
	// // from parent
	// aProcess.getCurrentEvent().setParentProcess(aParent);
	// aProcess.getCurrentEvent().setExecutionId(
	// aParent.getExecutionId());
	// TestingAuxiliary.debug("setExecutionId "
	// + aParent.getExecutionId());
	// }
	//
	// // copy parent and execution id to current event
	// // they are used further
	// if (aEvent != null) {
	// if ((aEvent.getEventLink() != null)
	// && (aProcess.getCurrentEvent().getParentProcess() != null)) {
	// // copy parent and execution id from the process
	// aEvent.setParentProcess(aProcess.getCurrentEvent()
	// .getParentProcess());
	// TestingAuxiliary.debug("aEvent.setParentProcess "
	// + aProcess.getCurrentEvent().getParentProcess());
	// if (!isInternalEvent) {
	// aEvent.setExecutionId(aProcess.getCurrentEvent()
	// .getExecutionId());
	// }
	//
	// }
	// }
	// }
	//
	// // check the incoming links, a valid combination should exist
	// // in accordance with path and logical relations (if defined)
	// // if no path/logical relations are defined
	// // all links should be valid.
	// boolean pathExist = false;
	// Vector linkTypes = new Vector();
	//
	// linkTypes.add(new Integer(exportedAPI.OpcatConstants.INSTRUMENT_LINK));
	// linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
	// linkTypes.add(new Integer(exportedAPI.OpcatConstants.AGENT_LINK));
	// linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));
	//
	// // check if the process was activated by event
	//
	// // check if incoming links allow process activation
	// // if a path is defined for atleast one of the links - this path should
	// // be valid
	// // all links without path should be valid, unless there is a logical
	// // condition
	// Vector v = this.getProcessLinks((IXProcessEntry) aProcess.getIXEntry(),
	// linkTypes, false);
	// for (Enumeration en = v.elements(); en.hasMoreElements();) {
	// IXLinkEntry iLinkEntry = (IXLinkEntry) en.nextElement();
	// if (!iLinkEntry.getPath().equals("")) {
	// // this variable indicates if atleast one of the links has path
	// pathExist = true;
	// }
	// TestingAuxiliary.debug("Before checkLinkBeforeActivate for link "
	// + iLinkEntry.getName());
	// String str = null;
	// boolean retVal = checkLinkBeforeActivate(aProcess, iLinkEntry);
	// // TestingGridAddRow(TestingAuxiliary.ActionActivate, aProcess,
	// // retVal,iLinkEntry.getName() + " - " + str);
	//
	// if (retVal) {
	// TestingAuxiliary
	// .debug("After checkLinkBeforeActivate for link "
	// + iLinkEntry.getName());
	// // At least one path should be valid
	// if (!iLinkEntry.getPath().equals("")) {
	// TestingAuxiliary
	// .debug("Before checkPathDestination for link "
	// + iLinkEntry.getName());
	// if (checkPath(iLinkEntry.getPath(), v.elements())) {
	// TestingAuxiliary
	// .debug("After checkPathDestination for link "
	// + iLinkEntry.getName());
	// // store valid path on the process
	// aProcess.setPath(iLinkEntry.getPath());
	// TestingAuxiliary.debug("Set path "
	// + iLinkEntry.getPath());
	// return true;
	// }
	// }
	// } else {
	// // if link's path is empty - the link is mandatory
	// // return false if it is not available
	// // if link has a path - don't return false meanwhile
	// // because may be another path will be found valid
	// if (iLinkEntry.getPath().equals("")) {
	// // revert old parent and return
	// aProcess.getCurrentEvent().setParentProcess(oldParent);
	// return false;
	// }
	// // check logical operators. May be there is another link
	// // is available
	// // return false;
	// }
	// }
	// // if atleast one link has a path - the process can be activated
	// // only if one of the paths is valid.
	// // if the execution acheives this point - no valid path exist
	// // therefore the process can not be activated
	// if (pathExist) {
	// // revert ald parent and return
	// aProcess.getCurrentEvent().setParentProcess(oldParent);
	// return false;
	// }
	//
	// // there is no path and all links without path are valid
	// // store empty path
	// aProcess.setPath("");
	// return true;
	// }

	// This function checks whether link can be activated
	// It incorporates the following validations:
	// 1. Link source should be active
	// 2. If there is a path stored on the source of the link, then the link
	// can be activated only if it has the same path
	private boolean checkLinkBeforeActivate(TestingEntry aEntry,
			IXLinkEntry iLinkEntry) {
		int linkType = iLinkEntry.getLinkType(); // link Type
		TestingEntry aSrc = this.findEntryById(iLinkEntry.getSourceId()); // link
		// source
		TestingAuxiliary.debug(this, "checkLinkBeforeActivate, Src IS "
				+ aSrc.getIXEntry().getName());
		// if link is enabler its source should be active
		if ((linkType == exportedAPI.OpcatConstants.AGENT_LINK)
				|| (linkType == exportedAPI.OpcatConstants.CONDITION_LINK)
				|| (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK)
				|| (linkType == exportedAPI.OpcatConstants.CONSUMPTION_LINK)
				|| (linkType == exportedAPI.OpcatConstants.EFFECT_LINK)
				|| (linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK)
				|| (linkType == exportedAPI.OpcatConstants.INSTRUMENT_LINK)) {
			if (!aSrc.isActive()) {
				// return false if source is not active - link can't be
				// activated
				TestingAuxiliary
						.debug("checkLinkBeforeActivate: source is not active");
				if (aEntry != null) {
					aEntry.setTaxtualInfo("Source is not Active");
				}
				return false;
			}
		}
		// link path should be same as source path (if source path is not empty)
		if ((aSrc.getPath().equals(""))
				|| (iLinkEntry.getPath().equals(aSrc.getPath()))) {
			//
		} else {
			TestingAuxiliary
					.debug("checkLinkBeforeActivate: source path isn ot empty");
			if (aEntry != null) {
				aEntry.setTaxtualInfo("Source Path is not empty");
			}
			return false;
		}
		if (aEntry != null) {
			aEntry.setTaxtualInfo("");
		}
		return true;
	}

	// ////////////////// Treat activation Consequences ////////////////////////

	private void treatActivationConsequences(TestingEntry activatedEntry,
			TestingEvent aEvent) {
		if (TestingAuxiliary.isEvent(activatedEntry.getIXEntry())) {
			this.treatEventActivationConsequences((IXLinkEntry) activatedEntry
					.getIXEntry(), aEvent);
			return;
		}
		if (TestingAuxiliary.isProcess(activatedEntry.getIXEntry())) {
			this.treatProcessActivationConsequences(activatedEntry, aEvent);
			return;
		}
		if (TestingAuxiliary.isObject(activatedEntry.getIXEntry())) {
			this.treatObjectActivationConsequences(activatedEntry);
			return;
		}
		if (TestingAuxiliary.isState(activatedEntry.getIXEntry())) {
			this.treatStateActivationConsequences((IXStateEntry) activatedEntry
					.getIXEntry());
			return;
		}
		if (TestingAuxiliary.isConditionLink(activatedEntry.getIXEntry())) {
			this.treatConditionLinkConsequences((IXLinkEntry) activatedEntry
					.getIXEntry());
			return;
		}
	}

	/**
	 * this function was added to try to solve a bug wiht conditions. condition
	 * links where not functioning properly.(didnt help)
	 * 
	 * @param entry
	 */
	private void treatConditionLinkConsequences(IXLinkEntry entry) {
		/*
		 * TestingEntry Destination = findEntryById(entry.getDestinationId());
		 * TestingEvent currentEvent = new TestingEvent();
		 * currentEvent.setStep(currentStep+1);
		 * currentEvent.setSchedulingStep(currentStep);
		 * Destination.futureActivationScheduled(currentStep,currentEvent.getExecutionId());
		 */
	}

	// this function handles consequences of event links activation
	// it goes through the list of testing elements and activates the
	// processes
	// connected to the event that pass the validations.
	// if reaction time is defined for the event, it schedules the activation
	// after
	// the reaction time
	// the function also handles the reaction time out exception, which are not
	// supported yet by OPCAT
	private void treatEventActivationConsequences(IXLinkEntry activatedEvent,
			TestingEvent currentEvent) {
		if (currentEvent == null) {
			currentEvent = new TestingEvent();
		}
		TestingAuxiliary.debug("Starting treatEventActivationConsequences for "
				+ activatedEvent.getName());
		TestingAuxiliary.debug("current event exec id = "
				+ currentEvent.getExecutionId());
		// try to activate all corresponding processes
		Enumeration en = this.testingElements.elements();
		TestingEntry aProcess, aEventLink = this.findEntryById(activatedEvent
				.getId());
		long reactionTime = aEventLink.getDuration();
		for (; en.hasMoreElements();) {
			aProcess = (TestingEntry) en.nextElement();
			if (TestingAuxiliary.isProcess(aProcess.getIXEntry())) {
				if (activatedEvent.getDestinationId() == aProcess.getIXEntry()
						.getId()) {
					// if the event is connected to one of the descendant of the
					// process
					// it should not activate the parent
					if (this.isDescendantLink(activatedEvent, aProcess, false)) {
						continue;
					}

					// if process that triggers this process is its child
					// nullify the parent passed from child
					try {

						if ((currentEvent.getParentProcess() != null)
								&& currentEvent.getParentProcess().equals(
										aProcess)) {
							currentEvent.setParentProcess(null);
							TestingAuxiliary
									.debug("treatEventActivationConsequences: currentEvent.setParentProcess(null)");
						}
					} catch (NullPointerException ex) {
						APILogger
								.Print(this,
										"currentEvent.getParentProcess().equals(aProcess)");
					}

					// if no reaction time is defined - try to activate the
					// process
					// immediately
					aProcess.setEventLinkActivated(true);
					if (reactionTime == 0) {
						// store the link that tries to activate the process
						currentEvent.setEventLink(aEventLink);
						// activate the process if it passes the validations

						if (this.checkProcessBeforeActivate(aProcess,
								currentEvent)) {
							/*
							 * // copy parent and execution id from the process
							 * currentEvent.setParentProcess(aProcess.getCurrentEvent().getParentProcess()); //
							 * if (currentEvent.getExecutionId() == 0) { if
							 * (!this.isTriggeredByInternalEvent(aProcess,
							 * currentEvent.getParentProcess(), currentEvent )) {
							 * currentEvent.setExecutionId(aProcess.getCurrentEvent().getExecutionId()); }
							 * MOVED TO checkProcessBeforeActivate
							 */
							// activate the process
							this.activate(aProcess, currentEvent);
						}
					} else {
						// schedule future activation of the process after the
						// reaction time
						// the system will try to activate the process even if
						// the reaction
						// time constraints do not hold.
						// TestingEvent actvEvent = new
						// TestingEvent((currentStep +
						// this.computeStep(reactionTime)),
						// aEventLink.getPath(), 0,1, currentStep);
						currentEvent.setStep(this.currentStep
								+ this.computeStep(reactionTime));
						currentEvent.setPath(aEventLink.getPath());
						currentEvent.setSchedulingStep(this.currentStep);

						// store the link that tries to activate the process
						currentEvent.setEventLink(aEventLink);
						// currentEvent.setNumberOfInstances(currentEvent.getNumberOfInstances()
						// + 1 ) ;
						aProcess.scheduleActivation(currentEvent);

						// treat reaction time out exceptions if they are
						// defined
						Enumeration exceptions = this
								.findTimeOutException(aEventLink);
						// if found exceptions compare reaction time to min and
						// max limits
						// Note: reaction time out exceptions are not supported
						// yet
						// therefore they have not been tested
						for (; exceptions.hasMoreElements();) {
							IXLinkEntry iLink = (IXLinkEntry) exceptions
									.nextElement();
							TestingEntry aExceptionLink = this
									.findEntryById(iLink.getId());
							// reaction time out exception link exists
							if (reactionTime < aEventLink.getMinTime()) {
								// if reaction time is less than minimum -
								// schedule exception activation after reaction
								// time
								aExceptionLink.scheduleActivation(
										this.currentStep, this
												.computeStep(reactionTime));
							}
							if (reactionTime > aEventLink.getMaxTime()) {
								// if reaction time is greater than maximum
								// schedule exception activation after max time
								aExceptionLink.scheduleActivation(
										this.currentStep, this
												.computeStep(aEventLink
														.getMaxTime()));
							}
						}
					}
				}
			}
		} // for

		// now deactivate the event
		this.findEntryById(activatedEvent.getId()).deactivate(
				new TestingEvent());
	}

	// this function handles process activation consequences:
	// 1. schedules future termination of the process, unless the process is
	// zoomed in
	// 2. deactivates consumed objects/states consistent with path rules
	// 3. animates result and effect links
	// 4. For zoomed in processes: activates the objects and first level
	// processes
	// inside zoomed in OPD.
	private void treatProcessActivationConsequences(TestingEntry aProcess,
			TestingEvent currentEvent) {
		// Debug debug = Debug.getInstance() ;
		// debug.Print(this, "Starting treatProcessActivationConsequences for "
		// + aProcess.getIXEntry().getName(), "KEY") ;
		TestingAuxiliary
				.debug("Starting treatProcessActivationConsequences for "
						+ aProcess.getIXEntry().getName());

		IXProcessEntry activatedProcess = (IXProcessEntry) aProcess
				.getIXEntry();
		long duration = aProcess.getDuration();
		TestingEvent aEvent = new TestingEvent();
		aEvent.setDuration(duration);
		aEvent.setExecutionId(currentEvent.getExecutionId());

		// schedule future termination
		// if process has child process it will be terminated upon
		// termination of the last child
		// debug.Print(this, aProcess.getIXEntry().getName() + " Is LowLevel - "
		// + aProcess.isLowLevel(), "KEY") ;
		if (aProcess.isLowLevel()) {
			try {
				TestingEvent aChildEvent = new TestingEvent(currentEvent);
				aChildEvent.setSchedulingStep(this.currentStep);
				aChildEvent.setStep(this.currentStep
						+ this.computeStep(duration));
				aProcess.scheduleTermination(aChildEvent);
			} catch (Exception e) {
			}
		}

		// deactivate consumed objects/states
		Vector linkTypes = new Vector();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
		DeactivateConsumedEntriesTask task1 = new DeactivateConsumedEntriesTask(
				aProcess, aEvent);
		TestingAuxiliary
				.debug("Calling treatLinkBySource for DeactivateConsumedEntriesTask");
		this.validateAndTreatLinkByDestination(activatedProcess, linkTypes,
				task1);

		// animate results
		linkTypes.clear();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));
		AnimateResultsTask task2 = new AnimateResultsTask(aProcess, aEvent);
		TestingAuxiliary
				.debug("Calling treatLinkBySource for AnimateResultsTask");
		this.treatLinkBySource(activatedProcess, linkTypes, task2);

		// animate effect links
		linkTypes.clear();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.EFFECT_LINK));
		this.treatLinkByDestination(activatedProcess, linkTypes, task2);

		// if the process is invoked by internal event deactivate the
		// objects inside it and then initiate them again
		if (this.isTriggeredByInternalEvent(aProcess, currentEvent
				.getParentProcess(), currentEvent)) {
			this.deactivateChildObjects(aProcess);
		}

		// activate objects (not results) inside zoomed in process
		Enumeration en = (aProcess.getChildrenObjects()).elements();
		for (; en.hasMoreElements();) {
			IXObjectEntry childObject = (IXObjectEntry) en.nextElement();
			this.initiateObject(this.findEntryById(childObject.getId()));
		}

		// activate first descendant process (if exists) inside zoomed in
		// process
		TestingEvent aChildEvent = new TestingEvent();
		aChildEvent.setParentProcess(aProcess);
		aChildEvent.setExecutionId(currentEvent.getExecutionId());
		en = activatedProcess.getInstances();
		for (; en.hasMoreElements();) {
			IXProcessInstance activatedProcessInstance = (IXProcessInstance) en
					.nextElement();
			Enumeration firstLevelChildren = aProcess
					.getFirstLevelChildren(activatedProcessInstance);
			// show OPD that contains the child process
			if (TestingSettings.MOVE_BETWEEN_OPD) {
				if (firstLevelChildren.hasMoreElements()) {
					long childOpdId = ((IXProcessInstance) firstLevelChildren
							.nextElement()).getKey().getOpdId();
					long currentOpd = this.mySys.getCurrentIXOpd().getOpdId();
					if (childOpdId != currentOpd) {
						this.mySys.showOPD(childOpdId);
						this.opdStack.push(new Long(currentOpd));
						TestingAuxiliary
								.debug("treatProcessActivationConsequences for "
										+ activatedProcess.getName()
										+ ": push " + currentOpd);
					}
				}
			}
			this.activateLevel(aProcess
					.getFirstLevelChildren(activatedProcessInstance),
					aChildEvent);
		}

		// treat timeouts
		this.treatTimeoutsUponActivation(aProcess);

		// if activated process is a specialization of another process
		// activate its generalization as well
		TestingEvent aEventTemp = new TestingEvent(currentEvent);
		aEventTemp.setEventType(TestingSettings.ACTIVATION_EVENT);
		this.treatGeneralization(activatedProcess, aEventTemp);
	}

	// this function treats object activation consequences:
	// 1. activates the initial state
	// 2. if no initial state is defined - activates any state randomly
	// 3. if the object has no states treates the instrument/consumption event
	// links
	// 4. activates related objects generalization and exhibitions
	private void treatObjectActivationConsequences(TestingEntry aObject) {
		IXObjectEntry activatedObject = (IXObjectEntry) aObject.getIXEntry();
		TestingAuxiliary
				.debug("Starting treatObjectActivationConsequences for "
						+ activatedObject.getName());
		boolean hasElements = false;
		hasElements = activatedObject.getInstances().hasMoreElements();
		if (!(hasElements)) {
			TestingAuxiliary.debug("Object has no instances "
					+ activatedObject.getName());
			return;
			// this is very strange has it means the instance
			// has no elements which he must have at least one - himself

		}
		IXObjectInstance objIns = (IXObjectInstance) (activatedObject
				.getInstances().nextElement());
		boolean hasActiveState = this.hasActiveState(activatedObject);
		boolean hasInitialState = false;
		Enumeration en = null;
		int statesCounter = 0;
		// activate initial state if there is no another active state
		if (!hasActiveState) {
			en = objIns.getStateInstances();
			if (en.hasMoreElements()) {
				IXStateEntry state = null;
				for (; en.hasMoreElements();) {
					state = (IXStateEntry) ((IXStateInstance) en.nextElement())
							.getIXEntry();
					statesCounter++;
					if (state.isInitial()) {
						TestingEntry aState = this.findEntryById(state.getId());
						this.activate(aState);
						hasInitialState = true;
					}
				}
				// activate some state randomly if no initial state is defined
				if (TestingSettings.RANDOM_STATE_SELECTION) {
					if (!hasInitialState) {
						int randomStateIndex = 0;
						if (statesCounter > 1) {
							randomStateIndex = TestingAuxiliary
									.getRandomNumber(statesCounter);
						}
						TestingAuxiliary.debug("States Counter = "
								+ statesCounter + ", randomStateIndex = "
								+ randomStateIndex);
						en = objIns.getStateInstances();
						for (int i = 0; en.hasMoreElements(); i++) {
							state = (IXStateEntry) ((IXStateInstance) en
									.nextElement()).getIXEntry();
							if (i == randomStateIndex) {
								TestingAuxiliary.debug("Activate State "
										+ state.getName());
								this
										.activate(this.findEntryById(state
												.getId()));
							}
						}
					}
				}
			}
		}

		// if there are no states - treat event links (otherwise these is
		// treated by
		// state activation consequences function
		if (!activatedObject.getStates().hasMoreElements()) {
			Vector linkTypes = new Vector();
			linkTypes.add(new Integer(
					exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
			linkTypes.add(new Integer(
					exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
			this.activateLinksBySource(activatedObject, linkTypes);
		}

		// if activated object is a specialization of another object
		// activate its generalization as well
		TestingEvent aEventTemp = new TestingEvent();
		aEventTemp.setEventType(TestingSettings.ACTIVATION_EVENT);
		this.treatGeneralization(activatedObject, aEventTemp);

		// treat exhibition relations
		this.treatExhibitions(aObject, true);
	}

	// this function treats state activation consequences:
	// 1. Activates the parent object if it is not active yet
	// 2. schedules state timeout events
	// 3. treats consumption and instrument event links linked to the state
	// 4. treats consumption and instrument event links linked to the parent
	// object
	// (state change)
	private void treatStateActivationConsequences(IXStateEntry activatedState) {
		// activate parent object (if not active)
		IXObjectEntry parentObject = activatedState.getParentIXObjectEntry();
		TestingEntry aParentObject = this.findEntryById(parentObject.getId());
		if (!aParentObject.isActive()) {
			this.activate(aParentObject);
		}
		// schedule state time out exception
		TestingEntry aState = this.findEntryById(activatedState.getId());
		this.treatTimeoutsUponActivation(aState);

		// Treat state-entrance events
		Vector linkTypes = new Vector();
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
		// this one change to solve bug. condition links where not allways
		// working.
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
		this.activateLinksBySource(activatedState, linkTypes);

		// treat state change events
		this.activateLinksBySource(parentObject, linkTypes);
	}

	// this function synchonizes activation of exhibition relation root and its
	// features
	// when the root is activated/deactivated all its features are
	// acticvated/deactivated
	// as well. Activation/deactivation of feature triggeres
	// activation/deactivation
	// of the root.
	private void treatExhibitions(TestingEntry aObject, boolean activateInd) {
		// if the object is a feature of another object activate/deactivate its
		// parent
		// in this case WaitForEvent indicates that the object was not
		// activated/deactivated
		// as a consequence of root process activation/deactivation
		if (!aObject.getCurrentEvent().getWaitForEvent()) {
			Vector relations = aObject
					.getRelationByDestionation(exportedAPI.OpcatConstants.EXHIBITION_RELATION);
			for (int i = 0; i < relations.size(); i++) {
				IXRelationEntry relation = (IXRelationEntry) relations.get(i);
				TestingEntry aParent = this.findEntryById(relation
						.getSourceId());
				aObject.getCurrentEvent().setWaitForEvent(true);
				if (activateInd) {
					this.activate(aParent);
				} else {
					this.deactivate(aParent);
				}
				aObject.getCurrentEvent().setWaitForEvent(false);
			}
		}

		// if the object is a root of an exhibition relation -
		// activate/deactivate all its features
		Vector relations = aObject
				.getRelationBySource(exportedAPI.OpcatConstants.EXHIBITION_RELATION);
		for (int i = 0; i < relations.size(); i++) {
			IXRelationEntry relation = (IXRelationEntry) relations.get(i);
			TestingEntry aFeature = this.findEntryById(relation
					.getDestinationId());
			if (!TestingAuxiliary.isProcess(aFeature.getIXEntry())) {
				if (!aFeature.getCurrentEvent().getWaitForEvent()) {
					aFeature.getCurrentEvent().setWaitForEvent(true); // temporary
					// indication
					if (activateInd) {
						this.activate(aFeature);
					} else {
						this.deactivate(aFeature);
					}
					aFeature.getCurrentEvent().setWaitForEvent(false); // temporary
					// indication
				}
			}
		}
	}

	// This function sets the number of generalization instances upon
	// activation/
	// deactivation of a specialization object. Generalization reflect the
	// total numebr of instances of all its specialization and of its own
	private void treatGeneralization(IXThingEntry iThing, TestingEvent aEvent) {
		Enumeration en = iThing.getRelationByDestination();
		for (; en.hasMoreElements();) {
			IXRelationEntry relation = (IXRelationEntry) en.nextElement();
			if (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION) {
				// activate relation's source - this is the generalization
				// don't check before activate in this case
				// activateGeneralization is invoked when a specialization is
				// activated
				// in this case generalization should be valid
				TestingEntry aGenEntry = this.findEntryById(relation
						.getSourceId());
				if (aEvent.getEventType() == TestingSettings.ACTIVATION_EVENT) {
					aGenEntry.setNumberOfSpecializations(aGenEntry
							.getNumberOfSpecializations()
							+ aEvent.getNumberOfInstances());
				} else {
					aGenEntry.setNumberOfSpecializations(aGenEntry
							.getNumberOfSpecializations()
							- aEvent.getNumberOfInstances());
				}
			}
		}
	}

	// /////////// Validations before termination //////////////

	private boolean checkBeforeTerminate(TestingEntry aEntry,
			TestingEvent aEvent) {
		// Validations before termination are relevant mean while only for
		// process
		if (TestingAuxiliary.isProcess(aEntry.getIXEntry())) {
			boolean retVal = false;
			retVal = this.checkProcessBeforeTerminate(aEntry, aEvent);
			return retVal;
		}
		return true;
	}

	// process can be terminated only if all child processes have been
	// terminated
	// if child process could not be started because of any reason, except
	// insufficient condition link (e.g. missing instrument), it will have
	// a future activation event that will try to activate the child on the next
	// step.
	private boolean checkProcessBeforeTerminate(TestingEntry aProcess,
			TestingEvent aEvent) {
		TestingAuxiliary.debug("Starting checkProcessBeforeTerminate for "
				+ aProcess.getIXEntry().getName());
		// check if any child is active or has future events
		Enumeration children = aProcess.getChildrenProcesses().elements();
		for (; children.hasMoreElements();) {
			IXEntry childEntry = (IXEntry) children.nextElement();
			TestingEntry aChildEntry = this.findEntryById(childEntry.getId());
			// can not terminate if there is active child
			// or there is a termination scheduled for child
			// or child waits for event
			if (aChildEntry.isActive(aEvent.getExecutionId())
					|| aChildEntry.futureActivationScheduled(this.currentStep,
							aEvent.getExecutionId())) {
				aProcess.setTaxtualInfo("Child - "
						+ aChildEntry.getIXEntry().getName()
						+ " Is/Will Active");
				return false;
			}
		}
		// check if the process itself waits for future activation by internal
		// event
		// this is requried to support internal activation with reaction time
		TestingEvent aEventTemp = aProcess.actvScheduler.futureEventsScheduled(
				this.currentStep, aEvent.getExecutionId());
		if (aEventTemp != null) {
			TestingAuxiliary.debug("Larisa: found future activation");
			if (this.isTriggeredByInternalEvent(aProcess, aEventTemp
					.getParentProcess(), aEventTemp)) {
				TestingAuxiliary.debug("Larisa: triggered by internal event");
				aProcess.setTaxtualInfo("Triggered by Internal Event");
				return false;
			}
		}
		aProcess.setTaxtualInfo("");
		return true;
	}

	// ////////////////// Auxiliary Functions ////////////////////////

	// this function checks whether there is a link identical to the given link
	// linked to one of the direct descendants of the given process.
	// srcInd specifies whether source or destination links should be considered
	private boolean isDescendantLink(IXLinkEntry iLink, TestingEntry aProcess,
			boolean srcInd) {
		// get child processes
		// Vector descendants = getAllDescendantProcesses(aProcess);
		Vector descendants = aProcess.getChildrenProcesses();

		TestingAuxiliary.debug("Printing list of descendants");
		// TestingAuxiliary.debug(descendants);

		// go through the list of child processes
		for (int j = 0; j < descendants.size(); j++) {
			IXProcessEntry iDescProcess = (IXProcessEntry) descendants.get(j);
			Enumeration enDescLinks = null;

			// get descendant's links
			Vector linkTypes = new Vector();
			linkTypes.add(new Integer(iLink.getLinkType()));
			enDescLinks = this.getLinkByType(iDescProcess, linkTypes, srcInd)
					.elements();

			// look for a link identical to the process own link
			// among the links of descendant
			for (; enDescLinks.hasMoreElements();) {
				IXLinkEntry iDescLink = (IXLinkEntry) enDescLinks.nextElement();
				if (this.isIdentical(iLink, iDescLink, srcInd)) {
					return true;
				}
			}
		}
		return false;
	}

	// this function checks whether childThing is a specialization of
	// parentThing
	// or of one of its specializations
	// it applies also for aggregation and instantiation relations
	private boolean isSpecialization(IXThingEntry parentThing,
			IXThingEntry childThing) {
		TestingAuxiliary.debug("Starting isSpecialization, parent "
				+ parentThing.getName() + ", child " + childThing.getName());
		Enumeration enRelations = parentThing.getRelationBySource();
		for (; enRelations.hasMoreElements();) {
			IXRelationEntry relation = (IXRelationEntry) enRelations
					.nextElement();
			// if found that childThing is indeed a specialization of
			// parentThing
			// return true
			if ((relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION)
					|| (relation.getRelationType() == exportedAPI.OpcatConstants.AGGREGATION_RELATION)
					|| (relation.getRelationType() == exportedAPI.OpcatConstants.INSTANTINATION_RELATION)) {
				if (relation.getDestinationId() == childThing.getId()) {
					TestingAuxiliary.debug("isSpecialization returns true 1");
					TestingAuxiliary.debug("for parent "
							+ parentThing.getName() + ", child "
							+ childThing.getName());
					return true;
				}

				// call the function recusively for relation's destination
				TestingEntry temp = this.findEntryById(relation
						.getDestinationId());
				if (this.isSpecialization((IXThingEntry) temp.getIXEntry(),
						childThing)) {
					TestingAuxiliary.debug("isSpecialization returns true 2");
					TestingAuxiliary.debug("for parent "
							+ parentThing.getName() + ", child "
							+ childThing.getName());
					return true;
				}
			}
		}
		// return false if no specialization found
		TestingAuxiliary.debug("isSpecialization returns false ");
		TestingAuxiliary.debug("for parent " + parentThing.getName()
				+ ", child " + childThing.getName());
		return false;
	}

	// this function checks whether two links are identical.
	// it gets two links, one of them is connected to a parent process
	// and second to its child. The srcInd indicates the connection type
	// (it is true when child/parent are links' source, false otherwise
	// it will return true if one of the following conditions hold
	// 1. second end of both links is same entry
	// 2. second end of child is a specialization of second end of parent
	// 3. second end of child's link is a state of an object on the second end
	// of
	// parent's link
	// 4. second end of child is a child process of second end of parent
	// 5. second end of child is a state of an object, which is a specialization
	// of second end of parent
	// 6. on high level there is an effect link between parent and object
	// on low level there are result + consumption links between child and
	// object's states
	private boolean isIdentical(IXLinkEntry parentLink, IXLinkEntry childLink,
			boolean srcInd) {
		IXThingEntry parentThing = null, childThing = null;
		TestingAuxiliary.debug("Starting isIdentical");
		TestingAuxiliary.debug(", parentLink = " + parentLink.getName());
		TestingAuxiliary.debug(", childLink = " + childLink.getName());
		TestingAuxiliary.debug(", srcInd = " + srcInd);
		// if link type is different return false - identical links should have
		// link type
		if (parentLink.getLinkType() != childLink.getLinkType()) {
			TestingAuxiliary.debug("isIdentical returns false (link type)");
			return false;
		}

		// if parent and child are linked to the same entry
		// with the same link type remove this link
		if ((parentLink.getDestinationId() == childLink.getDestinationId())
				|| (parentLink.getSourceId() == childLink.getSourceId())) {
			TestingAuxiliary.debug("isIdentical returns true (same entry)");
			return true;
		}

		// retrieve second end entries for child and parent
		try {
			IXEntry childEntry = null;
			if (srcInd) {
				parentThing = (IXThingEntry) this.findEntryById(
						parentLink.getDestinationId()).getIXEntry();
				childEntry = this.findEntryById(childLink.getDestinationId())
						.getIXEntry();
			} else {
				parentThing = (IXThingEntry) this.findEntryById(
						parentLink.getSourceId()).getIXEntry();
				childEntry = this.findEntryById(childLink.getSourceId())
						.getIXEntry();
			}
			// obtain the thing on child's second end
			if (TestingAuxiliary.isThing(childEntry)) {
				childThing = (IXThingEntry) childEntry;
			} else {
				// if child's second end is state - get the corresponding object
				if (TestingAuxiliary.isState(childEntry)) {
					childThing = ((IXStateEntry) childEntry)
							.getParentIXObjectEntry();
				}
			}
		} catch (Exception e) {
			TestingAuxiliary.debug("isIdentical returns false (not a thing)");
			return false;
		}

		// after getting object for state:
		// if parent and child are linked to the same entry
		// with the same link type remove this link
		if (parentThing.getId() == childThing.getId()) {
			TestingAuxiliary
					.debug("isIdentical returns true (same entry with state)");
			return true;
		}

		// if second end of child's link is specialization of second end
		// of parent's link (direct or inherited) - return true
		if ((childThing != null) && (parentThing != null)) {
			if (this.isSpecialization(parentThing, childThing)) {
				TestingAuxiliary
						.debug("isIdentical returns true (Specialization)");
				return true;
			}
		}

		// if second end of child's link is a child process of second end
		// of parent's link - return true
		if ((TestingAuxiliary.isProcess(childThing))
				&& (TestingAuxiliary.isProcess(parentThing))) {
			IXProcessEntry childProcess = (IXProcessEntry) childThing;
			IXProcessEntry parentProcess = (IXProcessEntry) parentThing;
			TestingAuxiliary.debug("childProcess " + childThing.getName()
					+ " id = " + childThing.getId());
			TestingAuxiliary.debug("parentProcess " + parentThing.getName());
			TestingEntry aParentProcess = this.findEntryById(childProcess
					.getId());
			Enumeration en = aParentProcess.getChildrenProcesses().elements();
			for (; en.hasMoreElements();) {
				IXProcessEntry childProcessTemp = (IXProcessEntry) en
						.nextElement();
				TestingAuxiliary.debug("childProcessTemp  "
						+ childProcessTemp.getName() + " id = "
						+ childProcessTemp.getId());
				if (childProcessTemp.getId() == parentProcess.getId()) {
					TestingAuxiliary
							.debug("isIdentical returns true (child process)");
					return true;
				}
			}
		}

		// if neither of conditions holds - return false
		TestingAuxiliary.debug("isIdentical returns false (end)");
		return false;
	}

	// this function checks whether the given process is linked with a
	// fundamental
	// relation to another process under the same parent
	private boolean isRelatedProcess(TestingEntry aProcess, TestingEntry aParent) {
		IXProcessEntry iProcess = (IXProcessEntry) aProcess.getIXEntry();
		// get list of brothers
		Vector brothers = aParent.getChildrenProcesses();
		// go through the list of brothers
		for (int i = 0; i < brothers.size(); i++) {
			IXProcessEntry brother = (IXProcessEntry) brothers.get(i);
			long brotherId = brother.getId();
			// get relations of the current process
			Enumeration en = iProcess.getRelationByDestination();
			for (; en.hasMoreElements();) {
				IXRelationEntry relation = (IXRelationEntry) en.nextElement();
				if ((relation.getRelationType() == exportedAPI.OpcatConstants.AGGREGATION_RELATION)
						|| (relation.getRelationType() == exportedAPI.OpcatConstants.EXHIBITION_RELATION)
						|| (relation.getRelationType() == exportedAPI.OpcatConstants.INSTANTINATION_RELATION)
						|| (relation.getRelationType() == exportedAPI.OpcatConstants.SPECIALIZATION_RELATION)) {
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
	private boolean findEvents(TestingEntry aProcess,
			TestingEntry aParentProcess) {
		// get all incoming events
		Vector linkTypes = new Vector();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.AGENT_LINK));
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.EXCEPTION_LINK));
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK));
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
		Enumeration en = this.getLinksByDestination((IXProcessEntry) aProcess
				.getIXEntry(), linkTypes);
		// go through incoming events, return true if found none internal event
		for (; en.hasMoreElements();) {
			IXLinkEntry iLink = (IXLinkEntry) en.nextElement();
			if (!this.isInternalEvent(aProcess, aParentProcess, iLink)) {
				return true;
			}
		}
		// return false - no events found
		return false;
	}

	// this function checks whether the given link is internal for the given
	// process
	// internal link is a link inside a zoomed in process
	// such as brother invokes brother, or child invokes parent
	private boolean isInternalEvent(TestingEntry aProcess,
			TestingEntry aParentProcess, IXLinkEntry iLink) {
		TestingAuxiliary.debug("Starting isInternalEvent");
		TestingAuxiliary.debug("aProcess = " + aProcess.getIXEntry().getName());
		if (aParentProcess != null) {
			TestingAuxiliary.debug("aParentProcess = "
					+ aParentProcess.getIXEntry().getName());
		}
		TestingAuxiliary.debug("iLink = " + iLink.getName());
		// internal link can be only of invocation or exception type
		if ((iLink.getLinkType() != exportedAPI.OpcatConstants.EXCEPTION_LINK)
				&& (iLink.getLinkType() != exportedAPI.OpcatConstants.INVOCATION_LINK)) {
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
		for (int i = 0; i < internalProcesses.size(); i++) {
			IXEntry iProcess = (IXEntry) internalProcesses.get(i);
			if (iProcess.getId() == iLink.getSourceId()) {
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
	private boolean isTriggeredByInternalEvent(TestingEntry aProcess,
			TestingEntry aParent, TestingEvent aEvent) {
		boolean isInternalEvent = false; // initialize
		// obtain the event link that activated the process and check if it is
		// internal
		try {
			IXLinkEntry iLink = (IXLinkEntry) aEvent.getEventLink()
					.getIXEntry();
			isInternalEvent = this.isInternalEvent(aProcess, aParent, iLink);
		} catch (Exception e) {
			// the process was not activated by event - return false
			TestingAuxiliary.debug("isInternalEvent returns false (exception)");
			return false;
		}
		TestingAuxiliary.debug("isInternalEvent " + isInternalEvent);
		return isInternalEvent;
	}

	// this function checks whether there is an unavailable conditions, due to
	// which
	// the process can not be activated. It is used for the child processes
	// inside
	// zoomed in process
	private boolean findUnavailableCondition(TestingEntry process) {
		TestingAuxiliary.debug("Starting findUnavailableCondition for "
				+ process.getIXEntry().getName());
		// get all condition links linked to the process
		Vector linkTypes = new Vector();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONDITION_LINK));
		Enumeration en = this.getLinksByDestination((IXProcessEntry) process
				.getIXEntry(), linkTypes);
		// go through condition links
		for (; en.hasMoreElements();) {
			IXLinkEntry iLinkEntry = (IXLinkEntry) en.nextElement();
			// check whether condition is available

			boolean retVal = this.checkLinkBeforeActivate(process, iLinkEntry);
			// TestingGridAddRow(TestingAuxiliary.ActionActivate, process,
			// retVal,iLinkEntry.getName() + " - " + str);

			if (!retVal) {
				// found unavailable condition
				TestingAuxiliary.debug("Found unavailable condition link "
						+ iLinkEntry.getName());
				return true;
			}
		}
		// all conditions are available
		return false;
	}

	// this is an auxiliary function that converts time value to steps
	private long computeStep(long timeOffset) {
		double time = (new Double(timeOffset)).doubleValue();
		double duration = (new Double(TestingSettings.STEP_DURATION))
				.doubleValue();
		double doubleTemp = time / duration;
		long temp = java.lang.Math.round(java.lang.Math.ceil(doubleTemp));
		// TestingAuxiliary.debug("doubleTemp = " + doubleTemp + " ceil = " +
		// java.lang.Math.ceil(doubleTemp));
		// TestingAuxiliary.debug("computeStep returns " + temp + " timeOffset
		// = " + timeOffset);
		return temp;
	}

	// this function searches time out exceptions linked to the given testing
	// entry
	private Enumeration findTimeOutException(TestingEntry aEntry) {
		TestingAuxiliary.debug("Starting findTimeOutException for "
				+ aEntry.getIXEntry().getName());
		Vector linkTypes = new Vector();
		IXConnectionEdgeEntry iEntry;
		try {
			// currently it will not support reaction time outs
			// because link is not IXConnectionEdgeEntry
			iEntry = (IXConnectionEdgeEntry) aEntry.getIXEntry();
		} catch (java.lang.ClassCastException e) {
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
	private boolean checkPath(String path, Enumeration en) {
		// Enumeration en = testingElements.elements();
		// String path = link.getPath();
		IXLinkEntry tempLink;
		// long destId = link.getDestinationId();

		for (; en.hasMoreElements();) {
			/*
			 * TestingEntry aEntry = (TestingEntry)en.nextElement(); if
			 * (!TestingAuxiliary.isLink(aEntry.getIXEntry())) { continue; }
			 * tempLink = (IXLinkEntry)aEntry.getIXEntry();
			 */
			tempLink = (IXLinkEntry) en.nextElement();
			/*
			 * if ((tempLink.getDestinationId() == destId) && (tempLink.getId() !=
			 * link.getId())) {
			 */
			String tempPath = tempLink.getPath();
			// if there is another link with same destination
			// marked with same path or not marked with any path
			// then it should be active as well
			if (tempPath.equals("") || tempPath.equals(path)) {

				boolean retVal = this.checkLinkBeforeActivate(null, tempLink);
				// TestingGridAddRow(TestingAuxiliary.ActionActivate,
				// tempLink, retVal,tempLink.getName() + "(Check Path) - " +
				// str);
				if (!retVal) {
					TestingAuxiliary
							.debug("checkPathDestination fails on link "
									+ tempLink.getName());
					return false;
				}
			}
		}
		// path is valid
		return true;
	}

	// this recursive function returns all descendant processes
	// of the given process
	public Vector getAllDescendantProcesses(TestingEntry aProcess) {
		Vector v = new Vector();
		v.addAll(aProcess.getChildrenProcesses());
		Enumeration en = v.elements();
		for (; en.hasMoreElements();) {
			IXProcessEntry iChild = (IXProcessEntry) en.nextElement();
			TestingEntry aChild = this.findEntryById(iChild.getId());
			TestingAuxiliary.mergeVectors(v, aChild.getChildrenProcesses());
		}
		return v;
	}

	// this function checks whether the object has an active state
	private boolean hasActiveState(IXObjectEntry ixObjectEntry) {
		IXObjectInstance objIns = (IXObjectInstance) (ixObjectEntry
				.getInstances().nextElement());
		Enumeration en = objIns.getStateInstances();
		// check that there is no active state
		for (; en.hasMoreElements();) {
			IXStateEntry state = (IXStateEntry) ((IXStateInstance) en
					.nextElement()).getIXEntry();
			TestingEntry aState = this.findEntryById(state.getId());
			if (aState.isActive()) {
				return true;
			}
		}
		return false;
	}

	// this function deactivates objects inside a zoomed in process
	private void deactivateChildObjects(TestingEntry aProcess) {
		Enumeration childObjects = aProcess.getChildrenObjects().elements();
		for (; childObjects.hasMoreElements();) {
			IXObjectEntry childObject = (IXObjectEntry) childObjects
					.nextElement();
			TestingEvent aEvent = new TestingEvent();
			TestingEntry aChildObject = this.findEntryById(childObject.getId());
			aEvent.setNumberOfInstances(aChildObject.getNumberOfInstances());
			this.deactivate(aChildObject, aEvent);
		}
	}

	// ////////////////// Links Treatment Functions ////////////////////////

	// this function activates links with the given source and link type
	private void activateLinksBySource(IXConnectionEdgeEntry iEntry,
			Vector linkTypes) {
		Enumeration en = this.getLinksBySource(iEntry, linkTypes);
		IXLinkEntry link;
		for (; en.hasMoreElements();) {
			link = (IXLinkEntry) en.nextElement();
			this.activate(this.findEntryById(link.getId()));
		}
	}

	// this link applies the specified task to the links with specified source
	// and link type
	private void treatLinkBySource(IXConnectionEdgeEntry iEntry,
			Vector linkTypes, Task task) {
		Enumeration en = this.getLinksBySource(iEntry, linkTypes);
		this.treatLinks(en, task);
	}

	// this link applies the specified task to the links with specified
	// destination and link type
	private void treatLinkByDestination(IXConnectionEdgeEntry iEntry,
			Vector linkTypes, Task task) {
		Enumeration en = this.getLinksByDestination(iEntry, linkTypes);
		this.treatLinks(en, task);
	}

	// this link applies the validation and the treatment of the specified task
	// to
	// the links with specified source and link type
	// private void validateAndTreatLinkBySource(IXConnectionEdgeEntry iEntry,
	// Vector linkTypes, Task task) {
	// Enumeration en = getLinksBySource(iEntry, linkTypes);
	// validateAndTreatLinks(en, task);
	// }

	// this link applies the validation and the treatment of the specified task
	// to
	// the links with specified destination and link type
	private void validateAndTreatLinkByDestination(
			IXConnectionEdgeEntry iEntry, Vector linkTypes, Task task) {
		Enumeration en = this.getLinksByDestination(iEntry, linkTypes);
		this.validateAndTreatLinks(en, task);
	}

	// this function applies the given task to the given enumeration of links
	private void treatLinks(Enumeration en, Task task) {
		IXLinkEntry link;
		for (; en.hasMoreElements();) {
			link = (IXLinkEntry) en.nextElement();
			task.run(link);
		}
	}

	// this function applies the given task and validation to the given
	// enumeration of links
	private void validateAndTreatLinks(Enumeration en, Task task) {
		Vector validLinks = new Vector();
		Vector allLinks = new Vector();

		for (; en.hasMoreElements();) {
			allLinks.add(en.nextElement());
		}

		en = allLinks.elements();

		for (; en.hasMoreElements();) {
			IXLinkEntry link = (IXLinkEntry) en.nextElement();
			if (task.validate(link, allLinks.elements())) {
				TestingAuxiliary.debug("validateAndTreatLinks: Valid Link "
						+ link.getName());
				validLinks.add(link);
			}
		}
		this.treatLinks(validLinks.elements(), task);
	}

	// temporary functions written instead of
	// IXConnectionEdgeEntry.getLinksBySource,
	// which returns sometimes ancestors links
	private Enumeration getLinksBySource(IXConnectionEdgeEntry iEntry) {
		return this.getLinks(iEntry, true);
		// return iEntry.getLinksBySource();
	}

	private Enumeration getLinksByDestination(IXConnectionEdgeEntry iEntry) {
		return this.getLinks(iEntry, false);
		// return iEntry.getLinksByDestination();
	}

	private Enumeration getLinks(IXConnectionEdgeEntry iEntry, boolean srcInd) {
		Vector v = new Vector();
		Enumeration en = this.testingElements.elements();
		for (; en.hasMoreElements();) {
			TestingEntry aEntry = (TestingEntry) en.nextElement();
			if (TestingAuxiliary.isLink(aEntry.getIXEntry())) {
				IXLinkEntry iLink = (IXLinkEntry) aEntry.getIXEntry();
				if (srcInd) {
					if (iLink.getSourceId() == iEntry.getId()) {
						v.add(iLink);
					}
				} else {
					if (iLink.getDestinationId() == iEntry.getId()) {
						v.add(iLink);
					}
				}
			}
		}
		TestingAuxiliary.debug("getLinks returns " + v);
		return v.elements();
	}

	// this function returns list of links connected to the given entry (source)
	// and having specified link types
	private Enumeration getLinksBySource(IXConnectionEdgeEntry iEntry,
			Vector linkTypes) {
		if (TestingAuxiliary.isProcess(iEntry)) {
			return this.getProcessLinks((IXProcessEntry) iEntry, linkTypes,
					true).elements();
		}
		return this.getLinkByType(this.getLinksBySource(iEntry), linkTypes)
				.elements();
	}

	// this function returns list of links connected to the given entry
	// (destination)
	// and having specified link types
	private Enumeration getLinksByDestination(IXConnectionEdgeEntry iEntry,
			Vector linkTypes) {
		if (TestingAuxiliary.isProcess(iEntry)) {
			return this.getProcessLinks((IXProcessEntry) iEntry, linkTypes,
					false).elements();
		} else {
			// return getLinkByType(iEntry.getLinksByDestination(),
			// linkTypes).elements();
			return this.getLinkByType(this.getLinksByDestination(iEntry),
					linkTypes).elements();
		}
	}

	private Vector getLinkByType(IXProcessEntry process, Vector linkTypes,
			boolean srcInd) {
		if (srcInd) {
			return this
					.getLinkByType(this.getLinksBySource(process), linkTypes);
		} else {
			return this.getLinkByType(this.getLinksByDestination(process),
					linkTypes);
		}
	}

	// this function derives links having given link types from the given
	// enumeration of links
	private Vector getLinkByType(Enumeration en, Vector linkTypes) {
		Vector temp = new Vector();
		IXLinkEntry link;
		for (; en.hasMoreElements();) {
			link = (IXLinkEntry) en.nextElement();
			if (linkTypes.contains(new Integer(link.getLinkType()))) {
				// mix links is some random order. This is necessary for
				// random selection when more than one link/path are available
				temp.add(TestingAuxiliary.getRandomNumber(temp.size() + 1),
						link);
			}
		}
		return temp;
	}

	// this is a recursive function that returns a list of links
	// of the given object and all its ancestors
	// according to the given link type and source/destination
	private Vector getProcessOwnAndAncestorsLinks(IXProcessEntry process,
			Vector linkTypes, boolean srcInd) {
		TestingAuxiliary.debug("Starting getProcessOwnAndAncestorsLinks for "
				+ process.getName());
		Vector allLinks = new Vector();
		Vector newLinks = null;
		TestingEntry aProcess = this.findEntryById(process.getId());

		// get own links
		newLinks = this.getLinkByType(process, linkTypes, srcInd);

		// add newLinks to allLinks
		TestingAuxiliary.mergeVectors(allLinks, newLinks);

		// debug printings
		TestingAuxiliary.debug("getProcessOwnAndAncestorsLinks for "
				+ process.getName());
		TestingAuxiliary.debug("printing own links");
		// TestingAuxiliary.debug(allLinks);
		for (Enumeration en = newLinks.elements(); en.hasMoreElements();) {
			TestingAuxiliary.debug(((IXLinkEntry) en.nextElement()).getName());
		}
		TestingAuxiliary.debug("finish printing own links");

		// if the process was not activated in parent's context return only
		// its own links (high level process)
		if (aProcess.getCurrentEvent().getParentProcess() == null) {
			TestingAuxiliary.debug("getParentProcess() == null  for "
					+ process.getName());
			return allLinks;
		}

		// get ancestors' links by this recursive call
		IXProcessEntry parentProcess = (IXProcessEntry) aProcess
				.getCurrentEvent().getParentProcess().getIXEntry();
		Vector parentLinks = this.getProcessOwnAndAncestorsLinks(parentProcess,
				linkTypes, srcInd);

		// remove identical links from the list of parent's links
		for (int i = parentLinks.size() - 1; i >= 0; i--) {
			IXLinkEntry parentLink = (IXLinkEntry) parentLinks.get(i);
			// remove identical links
			for (int j = 0; j < allLinks.size(); j++) {
				IXLinkEntry childLink = (IXLinkEntry) allLinks.get(j);
				if (this.isIdentical(parentLink, childLink, srcInd)) {
					parentLinks.remove(i);
					j = allLinks.size(); // exit from this loop after removal
				}
			}
		}

		// remove brothers' links
		Vector brothers = this.findEntryById(parentProcess.getId())
				.getChildrenProcesses();

		// go through the brothers
		for (int j = 0; j < brothers.size(); j++) {
			IXProcessEntry iBrotherProcess = (IXProcessEntry) brothers.get(j);

			// skip the process itself, it was already handled above
			if (iBrotherProcess.getId() == process.getId()) {
				continue;
			}

			Vector brothersLinks = null;

			// get brother's links
			brothersLinks = this.getLinkByType(iBrotherProcess, linkTypes,
					srcInd);

			// look among ancestors links for an identical link linked to a
			// brother
			// process
			for (int i = parentLinks.size() - 1; i >= 0; i--) {
				IXLinkEntry iLink = (IXLinkEntry) parentLinks.get(i);
				TestingAuxiliary.debug("Running for link " + iLink.getName()
						+ ", src = " + iLink.getSourceId() + ", dest = "
						+ iLink.getDestinationId());
				Enumeration enBrotherLinks = brothersLinks.elements();
				for (boolean stop = false; enBrotherLinks.hasMoreElements()
						&& !stop;) {
					IXLinkEntry iBrotherLink = (IXLinkEntry) enBrotherLinks
							.nextElement();
					TestingAuxiliary.debug("Brother link "
							+ iBrotherLink.getName() + ", src = "
							+ iBrotherLink.getSourceId() + ", dest = "
							+ iBrotherLink.getDestinationId());
					if (this.isIdentical(iLink, iBrotherLink, srcInd)) {
						parentLinks.remove(i);
						stop = true; // exit from the loop after removal
					}
				}
			}
		}

		TestingAuxiliary.mergeVectors(allLinks, parentLinks);
		TestingAuxiliary.debug("Finish getProcessOwnAndAncestorsLinks for "
				+ process.getName());
		TestingAuxiliary.debug("printing output links");
		// TestingAuxiliary.debug(allLinks);
		for (Enumeration en = allLinks.elements(); en.hasMoreElements();) {
			TestingAuxiliary.debug(((IXLinkEntry) en.nextElement()).getName());
		}
		TestingAuxiliary.debug("finish printing output links");
		return allLinks;
	}

	// This functions returns the links that refer to the given entry
	// for objects and states it returns the list of incoming or outgoing links
	// in accordance with the given list of types
	// for processes it applies the following logic:
	// if the process is low level (i.e. hasn't child process in zoomed in OPD)
	// then it retrieves all the links linked directly to the process
	// and all none-event links linked to all ancestors of this process on all
	// levels
	// except those that refer to process brothers or its ancestors' brothers
	// but not to the process itself
	// if the process is not low level then it will retrieve
	// only the events linked directly to the process
	// except those that are linked also to process descendant.
	private Vector getProcessLinks(IXProcessEntry process, Vector linkTypes,
			boolean srcInd) {
		TestingAuxiliary.debug("Starting getProcessLinks for "
				+ process.getName());
		TestingEntry aProcess = this.findEntryById(process.getId());
		Vector allLinks = new Vector();

		// separate event links and none event links into two vectors
		Vector linkTypesNotEvents = new Vector();
		Vector linkTypesOnlyEvents = new Vector();
		for (int i = linkTypes.size() - 1; i >= 0; i--) {
			Integer linkType = (Integer) linkTypes.get(i);
			if (TestingAuxiliary.isEvent(linkType.intValue())) {
				linkTypesOnlyEvents.add(linkType);
			} else {
				linkTypesNotEvents.add(linkType);
			}
		}

		TestingAuxiliary.debug("Printing linkTypesNotEvents "
				+ linkTypesNotEvents);

		if (aProcess.isLowLevel()) {
			// if the process is low level
			// get all none event links of the process itself and its ancestors
			allLinks.addAll(this.getProcessOwnAndAncestorsLinks(process,
					linkTypesNotEvents, srcInd));
		}

		TestingAuxiliary.debug("After getProcessOwnAndAncestorsLinks");
		// TestingAuxiliary.debug(allLinks);
		// now add the events linked directly to the current entry
		// linkTypes contains only events
		// none events are not required for zoomed in processes
		// and for low level process they already was collected above
		TestingAuxiliary.mergeVectors(allLinks, this.getLinkByType(process,
				linkTypesOnlyEvents, srcInd));
		// this was added to solve condition links problem.
		if (!aProcess.isLowLevel()) {
			TestingAuxiliary.mergeVectors(allLinks, this.getLinkByType(process,
					linkTypesNotEvents, srcInd));
		}
		// debug
		TestingAuxiliary.debug("All Links before removal for "
				+ aProcess.getIXEntry().getName());
		// TestingAuxiliary.debug(allLinks);
		for (Enumeration en = allLinks.elements(); en.hasMoreElements();) {
			TestingAuxiliary.debug(((IXLinkEntry) en.nextElement()).getName());
		}
		TestingAuxiliary.debug("finish printing All Links before removal");

		// if process's link has an identical link
		// link to one of descendant processes (if the process is zoomed in)
		// this link should be treated only for the descendant and therefore
		// should be removed from the list of process own links
		TestingAuxiliary.debug("Starting removal of duplicate links");
		// add all descendants
		Vector descendants = this.getAllDescendantProcesses(aProcess);

		TestingAuxiliary.debug("Printing list of descendants");
		// TestingAuxiliary.debug(descendants);
		for (int j = 0; j < descendants.size(); j++) {
			IXProcessEntry iDescProcess = (IXProcessEntry) descendants.get(j);
			Vector descendantLinks = new Vector();

			// get descendant's links
			descendantLinks = this.getLinkByType(iDescProcess, linkTypes,
					srcInd);

			// debug
			TestingAuxiliary
					.debug("Printing links found for descendant process "
							+ iDescProcess.getName());
			for (Enumeration en = descendantLinks.elements(); en
					.hasMoreElements();) {
				TestingAuxiliary.debug(((IXLinkEntry) en.nextElement())
						.getName());
			}
			TestingAuxiliary.debug("finish printing descendant links");
			// end debug

			// look for a link identical to the process own link
			// among the links of descendant
			for (int i = allLinks.size() - 1; i >= 0; i--) {
				IXLinkEntry iLink = (IXLinkEntry) allLinks.get(i);
				Enumeration enDescLinks = descendantLinks.elements();
				for (boolean stop = false; enDescLinks.hasMoreElements()
						&& !stop;) {
					IXLinkEntry iDescLink = (IXLinkEntry) enDescLinks
							.nextElement();
					if (this.isIdentical(iLink, iDescLink, srcInd)) {
						allLinks.remove(i);
						stop = true; // exit from the loop after removal.
					}
				}
			}
		}

		// return the resulting collection
		TestingAuxiliary.debug("End of getProcessLinks for "
				+ process.getName());
		TestingAuxiliary.debug("Printing output links ");
		TestingAuxiliary.debug(allLinks);
		return allLinks;
	}

	// ///////////// TimeOut Treatment Functions ////////////////

	// this function applies time out treatment upon termination of an entry
	// it is called for states and processes
	// First it calculates the duration of process or being on state. Then
	// it goes through the list of time out exception links connected to the
	// entry
	// if the duration is less that the minimum duration, it activates the
	// exception
	// In any case it cancells the exception activation event scheduled max time
	// after
	// entry activation (this scheduling is done in treatTimeoutsUponActivation
	// function).
	private void treatTimeoutsUponTermination(TestingEntry aEntry,
			TestingEvent currentEvent) {
		TestingAuxiliary.debug("Starting treatTimeoutsUponTermination for "
				+ aEntry.getIXEntry().getName());
		// treat time outs
		Enumeration en = this.findTimeOutException(aEntry);
		for (; en.hasMoreElements();) {
			IXLinkEntry iLink = (IXLinkEntry) en.nextElement();
			TestingEntry aExceptionLink = this.findEntryById(iLink.getId());

			// compute duration
			long activationStep = aEntry.getCurrentEvent().getStep();
			long duration = (this.currentStep - activationStep)
					* TestingSettings.STEP_DURATION;
			TestingAuxiliary.debug("duration = " + duration);

			// activate exception if entry is deactivated before minimum time
			if ((duration > 0) && (duration < aEntry.getMinTime())) {
				TestingAuxiliary
						.debug("duration < aEntry.getMinTime(): Activate "
								+ aExceptionLink);
				this.activate(aExceptionLink);
			}

			// cancel scheduled event - set cancel step = current step
			Enumeration enExAct = aExceptionLink
					.getScheduledActivations(activationStep
							+ this.computeStep(aEntry.getMaxTime()));
			try {
				TestingEvent aExceptionActvEvent = (TestingEvent) enExAct
						.nextElement();
				TestingAuxiliary.debug("Cancel activation scheduled to step "
						+ (currentEvent.getSchedulingStep() + this
								.computeStep(aEntry.getMaxTime())));
				TestingAuxiliary.debug("Current Step = " + this.currentStep);
				aExceptionActvEvent.setCancelStep(this.currentStep);
			} catch (Exception e) {
			}
		}
	}

	// this function treats time outs upon entry activation.
	private void treatTimeoutsUponActivation(TestingEntry aEntry) {
		this.treatTimeoutsUponActivation(aEntry, this.currentStep);
	}

	// this function treats time outs upon entry activation
	// for each exception link connected to the entry it schedules future
	// activation
	// after the max time.
	private void treatTimeoutsUponActivation(TestingEntry aEntry, long step) {
		TestingAuxiliary.debug("Starting treatTimeoutsUponActivation for "
				+ aEntry.getIXEntry().getName());
		Enumeration en = this.findTimeOutException(aEntry);
		for (; en.hasMoreElements();) {
			IXLinkEntry iLink = (IXLinkEntry) en.nextElement();
			TestingEntry aLink = this.findEntryById(iLink.getId());
			long maxTime = aEntry.getMaxTime();
			TestingAuxiliary.debug("Schedule exception activation "
					+ iLink.getName() + " to step "
					+ (step + this.computeStep(maxTime)));
			aLink.scheduleActivation(step, step + this.computeStep(maxTime));
		}
	}

	// ///////////// Termination Consequences Treatment Functions
	// ////////////////

	private void treatTerminationConsequences(TestingEntry activatedEntry,
			TestingEvent aEvent) {
		if (TestingAuxiliary.isProcess(activatedEntry.getIXEntry())) {
			this.treatProcessTerminationConsequences(activatedEntry, aEvent);
			return;
		}
		if (TestingAuxiliary.isObject(activatedEntry.getIXEntry())) {
			this.treatObjectTerminationConsequences(activatedEntry, aEvent);
			return;
		}

		if (TestingAuxiliary.isState(activatedEntry.getIXEntry())) {
			this.treatStateTerminationConsequences(activatedEntry, aEvent);
			return;
		}
	}

	// this function treats the termination consequences of a process:
	// 1. activates results and invocations
	// 2. if process is a child process - call
	// treatChildProcessTerminationConsequences function
	// 3. if this is a zoomed in process - terminates all objects inside it
	// 4. treats time outs
	private void treatProcessTerminationConsequences(TestingEntry aProcess,
			TestingEvent currentEvent) {
		TestingAuxiliary
				.debug("Starting treatProcessTerminationConsequences for "
						+ aProcess.getIXEntry().getName());
		TestingAuxiliary.debug("currentEvent.getExecutionId() = "
				+ currentEvent.getExecutionId());
		IXProcessEntry iProcess = (IXProcessEntry) aProcess.getIXEntry();

		// activate results
		Vector linkTypes = new Vector();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.RESULT_LINK));
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.INVOCATION_LINK));
		ActivateResultsTask task = new ActivateResultsTask(aProcess,
				currentEvent);
		this.treatLinkBySource(iProcess, linkTypes, task);

		// treat terminated states
		linkTypes.clear();
		linkTypes.add(new Integer(exportedAPI.OpcatConstants.CONSUMPTION_LINK));
		linkTypes.add(new Integer(
				exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK));
		Enumeration en = this.getLinksByDestination(iProcess, linkTypes);
		for (; en.hasMoreElements();) {
			IXLinkEntry iLink = (IXLinkEntry) en.nextElement();
			TestingEntry aSrc = this.findEntryById(iLink.getSourceId());
			if (TestingAuxiliary.isState(aSrc.getIXEntry())) {
				IXObjectEntry object = ((IXStateEntry) aSrc.getIXEntry())
						.getParentIXObjectEntry();
				TestingEntry aObj = this.findEntryById(object.getId());
				if (aObj.isActive() && !this.hasActiveState(object)) {
					// if after state termination, there is no more active
					// states for this
					// object, the object should be deactivated
					this.deactivate(aObj, new TestingEvent(currentEvent));
				}
			}
		}

		// if the process was triggered by internal event
		// treat child process termination consequences of the process that
		// triggered it (source of the event link that invoked the current
		// process)
		if (this.isTriggeredByInternalEvent(aProcess, currentEvent
				.getParentProcess(), currentEvent)) {
			TestingEvent aEventNext = new TestingEvent(currentEvent);
			if (currentEvent.getParentProcess() == null) {
				// this might be true only if child process invoked its parent
				// set current process as a parent
				aEventNext.setParentProcess(aProcess);
			}
			long srcId = ((IXLinkEntry) currentEvent.getEventLink()
					.getIXEntry()).getSourceId();
			this.treatChildProcessTerminationConsequences(this
					.findEntryById(srcId), aEventNext);
		} else {
			// if the terminated process is a child process
			// of a zoomed in process activated in parent process context -
			// activate next level children or deactivate the parent
			/*
			 * Added for debugging of condition links problems
			 */
			String s = aProcess.getIXEntry().getName();
			int i = 0;
			if (s.lastIndexOf("Password\nChecking") > -1) {
				i++;
			}
			if (currentEvent.getParentProcess() != null) { // parent process
				// context
				this.treatChildProcessTerminationConsequences(aProcess,
						currentEvent);
			}
		}

		// if the terminated process is zoomed in
		// deactivate all objects inside it.
		if (aProcess.getNumberOfInstances() == 0) {
			this.deactivateChildObjects(aProcess);
		}

		// treat timeouts
		this.treatTimeoutsUponTermination(aProcess, currentEvent);

		// if terminated process is a specialization of another process
		// activate its generalization as well
		TestingEvent aEventTemp = new TestingEvent(currentEvent);
		aEventTemp.setEventType(TestingSettings.TERMINATION_EVENT);
		this.treatGeneralization(iProcess, aEventTemp);
	}

	// this function treats the consequences of child process termination
	// child process is a process that has parent and was activated in parent's
	// context
	// this function implements the following rule:
	// 1. when last child process on the active level terminates next level
	// should be activated
	// 2. if current level is the last level, then parent process should be
	// terminated
	private void treatChildProcessTerminationConsequences(
			TestingEntry aProcess, TestingEvent currentEvent) {
		TestingAuxiliary
				.debug("Starting treatChildProcessTerminationConsequences for "
						+ aProcess.getIXEntry().getName());
		IXProcessEntry iProcess = (IXProcessEntry) aProcess.getIXEntry();
		TestingEvent aChildEvent = new TestingEvent();
		aChildEvent.setParentProcess(currentEvent.getParentProcess());
		aChildEvent.setExecutionId(currentEvent.getExecutionId());
		if (aProcess.getParents().hasMoreElements()) {
			Enumeration en = iProcess.getInstances();
			for (; en.hasMoreElements();) {
				IXProcessInstance activatedProcessInstance = (IXProcessInstance) en
						.nextElement();
				IXThingInstance parentProcessInstance = activatedProcessInstance
						.getParentIXThingInstance();
				if (parentProcessInstance != null) {
					TestingEntry aParentProcess = this
							.findEntryById(parentProcessInstance.getIXEntry()
									.getId());
					if (aParentProcess.isActive(currentEvent.getExecutionId())) {
						if (this.checkProcessBeforeTerminate(aParentProcess,
								currentEvent)) {
							TestingAuxiliary
									.debug("checkProcessBeforeTerminate succeeded for "
											+ aParentProcess.getIXEntry()
													.getName());
							// activate next level if all child processes
							// already terminated
							Enumeration nextLevelChildren = aProcess
									.getNextLevelChildren(activatedProcessInstance);
							// check again checkProcessBeforeTerminate because
							// it can happen that although
							// one of children has been activated, but it was
							// terminated immediately
							if (!this.activateLevel(nextLevelChildren,
									aChildEvent)
									|| this.checkProcessBeforeTerminate(
											aParentProcess, currentEvent)) {
								// if activateLevel returns false - there are no
								// more children
								// processes to be activated - terminate the
								// parent process
								TestingAuxiliary
										.debug("Deactivate parent process "
												+ aParentProcess.getIXEntry()
														.getName());
								if (aParentProcess.getCurrentEvent(currentEvent
										.getExecutionId()) != null) {
									// return to parent's OPD
									if (TestingSettings.MOVE_BETWEEN_OPD) {
										if (!this.opdStack.isEmpty()) {
											long temp = ((Long) this.opdStack
													.pop()).longValue();
											this.mySys.showOPD(temp);
											TestingAuxiliary
													.debug("treatChildProcessTerminationConsequences for "
															+ iProcess
																	.getName()
															+ ": pop " + temp);
										}
									}
									// deactivate parent
									TestingEvent termEvent = new TestingEvent(
											aParentProcess
													.getCurrentEvent(currentEvent
															.getExecutionId()));
									termEvent.setStep(this.currentStep);
									termEvent
											.setSchedulingStep(this.currentStep);
									termEvent.setNumberOfInstances(1);
									this.deactivate(aParentProcess, termEvent);
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
	private void treatObjectTerminationConsequences(TestingEntry aObj,
			TestingEvent currentEvent) {
		// if there are no more active instances -> deactivate objects state
		IXObjectEntry terminatedObject = (IXObjectEntry) aObj.getIXEntry();
		if (aObj.isActive() == false) {
			IXObjectInstance objIns = (IXObjectInstance) (terminatedObject
					.getInstances().nextElement());
			Enumeration en = objIns.getStateInstances();
			for (; en.hasMoreElements();) {
				IXStateEntry state = (IXStateEntry) ((IXStateInstance) en
						.nextElement()).getIXEntry();
				TestingEntry aState = this.findEntryById(state.getId());
				if (aState.isActive()) {
					this.deactivate(aState);
				}
			}
		}
		// if terminated object is a specialization of another object
		// terminate its generalization as well
		TestingEvent aEventTemp = new TestingEvent();
		aEventTemp.setEventType(TestingSettings.TERMINATION_EVENT);
		this.treatGeneralization((IXThingEntry) aObj.getIXEntry(), aEventTemp);

		// treat exhibition relations
		this.treatExhibitions(aObj, false);
	}

	private void treatStateTerminationConsequences(
			TestingEntry terminatedState, TestingEvent currentEvent) {
		// treat state time out exception
		this.treatTimeoutsUponTermination(terminatedState, currentEvent);
	}

	// //////////////////////////////
	// Testing tasks classes //
	// //////////////////////////////

	// generic task interface
	private interface Task {
		public void run(IXEntry iEntry);

		public boolean validate(IXEntry iEntry, Enumeration enAllEntries);
	}

	private class AnimateResultsTask implements Task {
		TestingEntry aProcess;

		TestingEvent aEvent;

		public AnimateResultsTask() {
		}

		public AnimateResultsTask(TestingEntry aProcess, TestingEvent aEvent) {
			this.aProcess = aProcess;
			this.aEvent = aEvent;
		}

		public boolean validate(IXEntry iEntry, Enumeration en) {
			return true;
		}

		public void run(IXEntry iEntry) {
			IXLinkEntry link = (IXLinkEntry) iEntry;
			// TestingAuxiliary.debug("Running AnimateResultsTask");
			if ((this.aProcess.getPath().equals(""))
					|| (link.getPath().equals(this.aProcess.getPath()))
					|| (link.getPath().equals(""))) {
				// if process was activated without path,
				// and one of the links has path - set it to be the process
				// path.
				if (this.aProcess.getPath().equals("")
						&& !link.getPath().equals("")) {
					this.aProcess.setPath(link.getPath());
					this.aProcess.setPath(link.getPath(), this.aEvent
							.getExecutionId());
				}
				// animate
				TestingSystem.this.findEntryById(link.getId()).animate(
						this.aEvent);
				TestingSystem.this.findEntryById(link.getDestinationId())
						.animate(this.aEvent);
			}
		}
	}

	private class DeactivateConsumedEntriesTask implements Task {
		TestingEntry aProcess;

		TestingEvent aEvent;

		public DeactivateConsumedEntriesTask(TestingEntry aProcess,
				TestingEvent aEvent) {
			this.aProcess = aProcess;
			this.aEvent = aEvent;
		}

		public boolean validate(IXEntry iEntry, Enumeration enAllEntries) {
			TestingAuxiliary
					.debug("Validate consumed link " + iEntry.getName());
			IXLinkEntry link = (IXLinkEntry) iEntry;
			boolean retVal = TestingSystem.this.checkLinkBeforeActivate(
					this.aProcess, link);
			// TestingGridAddRow(TestingAuxiliary.ActionActivate, link,
			// retVal,link.getName() + "(Validate) - " + str);
			if (retVal) {
				TestingAuxiliary.debug("After  checkLinkBeforeActivate for"
						+ iEntry.getName());
				// check logical relation
				if (TestingSystem.this.checkPath(link.getPath(), enAllEntries)) {
					TestingAuxiliary.debug("After  checkPathDestination for"
							+ iEntry.getName());
					return true;
				}
			}
			return false;
		}

		public void run(IXEntry iEntry) {
			IXLinkEntry link = (IXLinkEntry) iEntry;
			TestingAuxiliary.debug("run deactivate consume entries for "
					+ this.aProcess.getIXEntry().getName());
			if ((this.aProcess.getPath().equals(""))
					|| (this.aProcess.getPath().equals(link.getPath()))
					|| (link.getPath().equals(""))) {
				// animate
				// TestingAuxiliary.debug("animate consumption link " +
				// link.getName());
				// TestingAuxiliary.debug("duration = " +
				// aEvent.getDuration());
				// TestingAuxiliary.debug("Current time " +
				// System.currentTimeMillis());
				TestingSystem.this.findEntryById(link.getId()).animate(
						this.aEvent);
				// deactivate the source object
				TestingAuxiliary.debug("deactivate "
						+ TestingSystem.this.findEntryById(link.getSourceId())
								.getIXEntry().getName());
				TestingSystem.this.deactivate(TestingSystem.this
						.findEntryById(link.getSourceId()), this.aEvent);
			}
		}

	}

	private class ActivateResultsTask implements Task {
		TestingEntry aProcess;

		TestingEvent aEvent;

		public ActivateResultsTask(TestingEntry aProcess, TestingEvent aEvent) {
			this.aProcess = aProcess;
			this.aEvent = aEvent;
		}

		public boolean validate(IXEntry iEntry, Enumeration en) {
			return true;
		}

		public void run(IXEntry iEntry) {
			IXLinkEntry link = (IXLinkEntry) iEntry;
			TestingAuxiliary.debug("Running ActivateResultsTask");
			if ((this.aProcess.getPath().equals(""))
					|| (link.getPath().equals(this.aProcess.getPath()))
					|| (link.getPath().equals(""))) {
				// activate the link if process is not marked with path
				// or if link's path is equal to the path stored on the process.
				TestingEntry aLink = TestingSystem.this.findEntryById(link
						.getId());
				if (TestingAuxiliary.isEvent(link)) {
					TestingSystem.this.activate(aLink, new TestingEvent(
							this.aEvent));
				} else {
					TestingEntry entry;
					entry = TestingSystem.this.findEntryById(link
							.getDestinationId());
					TestingSystem.this.activate(entry);
				}
			}
		}
	}

	private TestingGridPanel InitTestingGridPanel() {
		Vector cols = new Vector();
		cols.add("Step");
		cols.add("Thing Type");
		cols.add("OPD");
		cols.add("Thing Name");
		cols.add("While trying To");
		cols.add("Parent Thing");
		cols.add("Additional Information");
		TestingGridPanel locPanel;
		locPanel = new TestingGridPanel(cols.size(), cols, this.mySys);
		locPanel.doLayout();
		return locPanel;
	}

	private TestingObjectsScenrios InitObjectScnerios() {
		Vector cols = new Vector();
		cols.add("Object");
		cols.add("Connected Processes");
		cols.add("Description");
		cols.add("URL");
		cols.add("Initial State");
		cols.add("Final State");

		TestingObjectsScenrios locPanel;
		locPanel = new TestingObjectsScenrios(cols.size(), cols, Opcat2
				.getCurrentProject(), false);
		locPanel.doLayout();
		return locPanel;
	}

	// synchronized private void TestingGridAddRow(String action,
	// TestingEntry aEntry,boolean actionResult , String reason ) {
	// //if(aEntry.currentEvents.size() == 0 ) _TestingGridAddRow(action,
	// aEntry.getIXEntry() ,actionResult , "Testing Events is null" ) ;
	// if(reason == null ) reason = "";
	// _TestingGridAddRow(action, aEntry.getIXEntry() ,actionResult , reason )
	// ;
	// }

	// synchronized private void TestingGridAddRow(String action, IXEntry
	// entry ,boolean actionResult , String reason ) {
	// _TestingGridAddRow(action, entry,actionResult , reason ) ;
	// }

	synchronized private void TestingGridAddRow(String action,
			TestingEntry aEntry, TestingEvent aEvent) {

		IXEntry entry = aEntry.getIXEntry();
		Enumeration insEnum = entry.getInstances();
		for (; insEnum.hasMoreElements();) {
			IXInstance ins = null;
			OpdKey key = null;
			Object[] tag = new Object[2];
			Object[] row = new Object[this.testingPanel.getGrid()
					.getColumnCount()];

			ins = (IXInstance) insEnum.nextElement();
			key = ins.getKey();
			if (this.mySys.getCurrentIXOpd().getOpdId() == key.getOpdId()) {
				String type = "None";
				if (TestingAuxiliary.isProcess(entry)) {
					type = "Process";
				}
				if (TestingAuxiliary.isObject(entry)) {
					type = "Object";
				}
				if (TestingAuxiliary.isState(entry)) {
					type = "State";
				}
				if (entry instanceof IXLinkEntry) {
					int linkType = ((IXLinkEntry) entry).getLinkType();
					if (linkType == exportedAPI.OpcatConstants.AGENT_LINK) {
						type = "Agent Link";
					}
					if (linkType == exportedAPI.OpcatConstants.CONDITION_LINK) {
						type = "Condition Link";
					}
					if (linkType == exportedAPI.OpcatConstants.CONSUMPTION_EVENT_LINK) {
						type = "Event Link";
					}
					if (linkType == exportedAPI.OpcatConstants.CONSUMPTION_LINK) {
						type = "Consumption Link";
					}
					if (linkType == exportedAPI.OpcatConstants.EFFECT_LINK) {
						type = "Effect Link";
					}
					if (linkType == exportedAPI.OpcatConstants.INSTRUMENT_EVENT_LINK) {
						type = "Instrument Event Link";
					}
					if (linkType == exportedAPI.OpcatConstants.INSTRUMENT_LINK) {
						type = "Instrument Link";
					}
				}
				row[0] = new Long(this.currentStep);
				row[1] = type;
				row[2] = this.mySys.getIXSystemStructure().getIXOpd(
						key.getOpdId()).getName();
				row[3] = entry.getName();
				row[4] = action;
				if (aEvent != null) {
					if (aEvent.getParentProcess() == null) {
						row[5] = "None";

					} else {
						row[5] = aEvent.getParentProcess().getIXEntry()
								.getName();
					}
				} else {
					row[5] = "null";
				}
				row[6] = aEntry.getTaxtualInfo();
				// System.out.println(key.toShtring()) ;
				tag[0] = key;
				tag[1] = new Long(entry.getId());
				this.testingPanel.getGrid().addRow(row, tag);
			}
		}
	}

	/**
	 * @return Returns the testingPanel.
	 */
	public TestingGridPanel getTestingPanel() {
		return this.testingPanel;
	}
}