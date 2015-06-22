package extensionTools.Testing;

/*
 This class implements the functions of Testing Entry, which is an extension
 of IXEntry. The class holds testing functions, such as animated and
 stopTesting. It also stores the events scheduled for the entry for future
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

class TestingEntry extends Object {

	private IXEntry iEntry = null;

	// current event stores differents setting that external functions
	// pass to each other.
	private TestingEvent currentEvent = null;

	private int defaultNumberOfInstances;

	private String taxtualInfo = "";

	private boolean isEventLinkActivated = false;

	// activation events scheduler
	// it keeps a list of events scheduled for each step
	TestingEventsScheduler actvScheduler;

	// termination events scheduler
	TestingEventsScheduler termScheduler;

	// Log table
	TestingEventsScheduler log;

	// current events list
	// usually testing entry has one current event
	// list of current events is required when more than one execution runs
	Hashtable currentEvents; // key execution Id, value TestingEvent

	// constructor
	public TestingEntry(IXEntry iEntry) {
		this.iEntry = iEntry;
		this.currentEvent = new TestingEvent();
		this.currentEvent.setNumberOfInstances(0);
		this.taxtualInfo = "";
		// set default number of instances
		if (TestingAuxiliary.isThing(iEntry)) {
			IXThingEntry iThingEntry = (IXThingEntry) iEntry;
			this.defaultNumberOfInstances = iThingEntry.getNumberOfMASInstances();
		} else {
			this.defaultNumberOfInstances = 0;
		}
		if (TestingAuxiliary.isLink(iEntry)) {
			this.currentEvent.setPath(((IXLinkEntry) iEntry).getPath());
		} else {
			this.currentEvent.setPath(new String());
		}
		this.actvScheduler = new TestingEventsScheduler();
		this.termScheduler = new TestingEventsScheduler();
		this.log = new TestingEventsScheduler();
		this.currentEvents = new Hashtable();
	}

	public IXEntry getIXEntry() {
		return this.iEntry;
	}

	// this function activates the entry - increases the number of instances
	// by one and applies testing
	public void activate(TestingEvent aEvent) {
		int insNum = this.getNumberOfInstances();
		// generate new executionId if it was not passed
		if (aEvent.getExecutionId() == 0) {
			aEvent.setExecutionId();
		}
		// store the path
		if (!this.currentEvent.getPath().equals("")) {
			TestingAuxiliary.debug(this, "Set path " + this.currentEvent.getPath()
					+ " for " + this.iEntry.getName() + " execId = "
					+ aEvent.getExecutionId());
			aEvent.setPath(this.currentEvent.getPath());
		}

		// store step
		this.currentEvent.setStep(aEvent.getStep());

		TestingAuxiliary.debug(this, "Activate " + this.iEntry.getName()
				+ ", exec ID = " + aEvent.getExecutionId());
		TestingAuxiliary.debug(this, ", number of instances = "
				+ aEvent.getNumberOfInstances());

		// store current event
		this.addCurrentEvent(new TestingEvent(aEvent));

		this.currentEvent.setParentProcess(aEvent.getParentProcess());
		TestingAuxiliary.debug(this, "setParentProcess "
				+ aEvent.getParentProcess());
		// remove all future events scheduled for it for this execution
		if (aEvent.getExecutionId() != 0) {
			TestingAuxiliary.debug(this,
					"Calling removeAllScheduledEvents from step "
							+ (aEvent.getStep() - 1));
			this.actvScheduler.removeAllScheduledEvents(aEvent.getStep() - 1,
					aEvent.getExecutionId());
		}
		// write the activation to the log
		// set scheduling step to before writing to the log
		TestingEvent logEvent = new TestingEvent(aEvent);
		logEvent.setEventType(TestingSettings.ACTIVATION_EVENT);
		logEvent.setSchedulingStep(aEvent.getStep());
		this.log.scheduleEvent(logEvent);

		this.setNumberOfInstances(insNum + aEvent.getNumberOfInstances());
		if ((insNum > 0) || (insNum + aEvent.getNumberOfInstances() == 0)) {
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
		// if (TestingAuxiliary.isThing(iEntry) && !this.isFeature()) {
		if (TestingAuxiliary.isThing(this.iEntry)) {
			IXThingEntry iThingEntry = (IXThingEntry) this.iEntry;
			if ((num + this.getNumberOfSpecializations()) > 0) {
				iThingEntry.setNumberOfMASInstances(num
						+ this.getNumberOfSpecializations());
			} else {
				iThingEntry.setNumberOfMASInstances(1);
			}
		}
		this.iEntry.updateInstances();
		// 0 is invalid number of instances for OPCAT, therefore
		// I don't set MAS instances to 0, I store it in local variable
		// currentEvent.numberOfInstances
		this.currentEvent.setNumberOfInstances(num);
	}

	// this function restores the number of instances to the default value
	// it is used when testing terminates to restore the number of MAS
	// instances
	private void setDefaultNumberOfInstances() {
		// if (TestingAuxiliary.isThing(iEntry) && !this.isFeature()) {
		if (TestingAuxiliary.isThing(this.iEntry)) {
			IXThingEntry iThingEntry = (IXThingEntry) this.iEntry;
			if (this.defaultNumberOfInstances > 0) {
				iThingEntry.setNumberOfMASInstances(this.defaultNumberOfInstances);
			} else {
				iThingEntry.setNumberOfMASInstances(1);
			}
		}
		this.iEntry.updateInstances();
	}

	public int getDefaultNumberOfInstances() {
		return this.defaultNumberOfInstances;
	}

	// this function deactivates the entry - decreases number of instances
	// and stops the testing
	public void deactivate(TestingEvent aEvent) {
		TestingAuxiliary.debug(this, "Starting deactivate for "
				+ this.iEntry.getName());

		if (aEvent == null) {
			aEvent = new TestingEvent();
			aEvent.setEventType(TestingSettings.TERMINATION_EVENT);
		}

		// restore path
		this.currentEvent.setPath(this.getPath(aEvent.getExecutionId()));

		// store step
		Long execId = new Long(aEvent.getExecutionId());
		if (execId.longValue() != 0) {
			try {
				this.currentEvent.setStep(((TestingEvent) this.currentEvents
						.get(execId)).getStep());
			} catch (Exception e) {
				TestingAuxiliary.debug(this, e.getMessage());
			}
		}

		// write the deactivation to the log
		TestingEvent logEvent = new TestingEvent(aEvent);
		logEvent.setEventType(TestingSettings.TERMINATION_EVENT);
		logEvent.setSchedulingStep(aEvent.getStep());
		this.log.scheduleEvent(logEvent);

		// remove current event
		this.removeCurrentEvent(aEvent);

		int insNum = this.getNumberOfInstances(), numberOfInstances = aEvent
				.getNumberOfInstances();
		TestingAuxiliary.debug(this, "deactivate for " + this.iEntry.getName());
		TestingAuxiliary.debug(this, "exec id = " + aEvent.getExecutionId());
		TestingAuxiliary.debug(this, "getNumberOfInstances() "
				+ this.getNumberOfInstances());
		TestingAuxiliary.debug(this, "aEvent.getNumberOfInstances() "
				+ aEvent.getNumberOfInstances());
		TestingAuxiliary.debug(this, "numberOfSpecializations "
				+ this.currentEvent.getNumberOfSpecializations());

		if (insNum >= numberOfInstances) {
			insNum -= numberOfInstances;
		} else {
			insNum = 0;
		}
		this.setNumberOfInstances(insNum);
		// ??
		if (insNum + this.currentEvent.getNumberOfSpecializations() != 0) {
			return;
		}
		this.stopTesting(aEvent);
	}

	// check if the entry is a feature of another entry
	public boolean isFeature() {
		return this.getRelationByDestionation(
				exportedAPI.OpcatConstants.EXHIBITION_RELATION).elements()
				.hasMoreElements();
	}

	public Vector getRelationByDestionation(int relationType) {
		Vector v = new Vector();
		if (TestingAuxiliary.isThing(this.iEntry)
				|| TestingAuxiliary.isState(this.iEntry)) {
			IXConnectionEdgeEntry iEdge = (IXThingEntry) this.iEntry;
			Enumeration en = iEdge.getRelationByDestination();
			for (; en.hasMoreElements();) {
				IXRelationEntry relation = (IXRelationEntry) en.nextElement();
				if (relation.getRelationType() == relationType) {
					v.add(relation);
				}
			}
		}
		return v;
	}

	// check if the entry is a root of a relation with the given type
	public Vector getRelationBySource(int relationType) {
		Vector v = new Vector();
		if (TestingAuxiliary.isThing(this.iEntry)
				|| TestingAuxiliary.isState(this.iEntry)) {
			IXConnectionEdgeEntry iEdge = (IXThingEntry) this.iEntry;
			Enumeration en = iEdge.getRelationBySource();
			for (; en.hasMoreElements();) {
				IXRelationEntry relation = (IXRelationEntry) en.nextElement();
				if (relation.getRelationType() == relationType) {
					v.add(relation);
				}
			}
		}
		return v;
	}

	public boolean isActive() {
		TestingAuxiliary.debug(this, this.iEntry.getName() + " is active = "
				+ this.currentEvent.getNumberOfInstances());
		return this.currentEvent.getNumberOfInstances() > 0;
	}

	// this function checks whether the entry is active
	// in the given execution context
	public boolean isActive(long executionId) {
		TestingAuxiliary.debug(this, "isActive for " + this.iEntry.getName()
				+ ", executionId = " + executionId);
		TestingAuxiliary.debug(this, "return "
				+ this.currentEvents.containsKey(new Long(executionId)));
		return this.currentEvents.containsKey(new Long(executionId));
	}

	public void setPath(String path) {
		this.currentEvent.setPath(path);
	}

	public void setPath(String path, long execId) {
		TestingEvent aEvent = (TestingEvent) this.currentEvents
				.get(new Long(execId));
		if (aEvent != null) {
			aEvent.setPath(path);
		}
	}

	public String getPath() {
		return this.currentEvent.getPath();
	}

	public String getPath(long execId) {
		TestingEvent aEvent = (TestingEvent) this.currentEvents
				.get(new Long(execId));
		if (aEvent != null) {
			return aEvent.getPath();
		}
		TestingAuxiliary.debug(this,
				"getPath: could not find TestingEvent for execId " + execId);
		return "";
	}

	public void setNumberOfSpecializations(int numberOfSpecialization) {
		TestingAuxiliary.debug(this, "setNumberOfSpecializations for "
				+ this.iEntry.getName());
		int oldIns = this.getNumberOfInstances()
				+ this.getNumberOfSpecializations();
		if (numberOfSpecialization < 0) {
			this.currentEvent.setNumberOfSpecializations(0);
		} else {
			this.currentEvent
					.setNumberOfSpecializations(numberOfSpecialization);
		}
		try {
			IXThingEntry iThingEntry = (IXThingEntry) this.iEntry;
			if ((this.getNumberOfInstances() + this.currentEvent
					.getNumberOfSpecializations()) > 0) {
				iThingEntry.setNumberOfMASInstances(this.getNumberOfInstances()
						+ this.currentEvent.getNumberOfSpecializations());
			} else {
				iThingEntry.setNumberOfMASInstances(1);
			}
			if ((oldIns == 0) && (numberOfSpecialization > 0)) {
				this.animate(new TestingEvent());
			}
			if ((oldIns > 0)
					&& ((this.getNumberOfInstances() + this
							.getNumberOfSpecializations()) == 0)) {
				this.stopTesting(new TestingEvent());
			}
			this.iEntry.updateInstances();
		} catch (Exception e) {
		}
	}

	public int getNumberOfSpecializations() {
		return this.currentEvent.getNumberOfSpecializations();
	}

	// get any execution id
	public long getExecutionId() {
		Enumeration en = this.currentEvents.elements();
		if (en.hasMoreElements()) {
			return ((TestingEvent) en.nextElement()).getExecutionId();
		}
		return 0;
	}

	public TestingEvent getCurrentEvent() {
		return this.currentEvent;
	}

	public TestingEvent getCurrentEvent(long executionId) {
		TestingAuxiliary.debug(this, "getCurrentEvent for "
				+ this.iEntry.getName() + ", executionId = " + executionId);
		TestingAuxiliary.debug(this, "return "
				+ this.currentEvents.get(new Long(executionId)));
		return (TestingEvent) this.currentEvents.get(new Long(executionId));
	}

	public void addCurrentEvent(TestingEvent aEvent) {
		// store current event
		Long execId = new Long(aEvent.getExecutionId());
		TestingEvent aEventTemp = null;
		try {
			aEventTemp = (TestingEvent) this.currentEvents.get(execId);
			aEvent.setNumberOfInstances(aEventTemp.getNumberOfInstances()
					+ aEvent.getNumberOfInstances());
			this.currentEvents.put(execId, aEvent);
			TestingAuxiliary.debug(this, "ExecId: " + execId.longValue()
					+ ", numb of ins = " + aEvent.getNumberOfInstances());
		} catch (NullPointerException ex) {
			this.currentEvents.put(execId, aEvent);
		}
	}

	public void removeCurrentEvent(TestingEvent aEvent) {
		// remove current event
		TestingEvent aEventTemp = null;
		Long execId = new Long(aEvent.getExecutionId());
		try {
			aEventTemp = (TestingEvent) this.currentEvents.get(execId);
			TestingAuxiliary.debug(this, "removeCurrentEvent for "
					+ this.iEntry.getName());
			if (TestingAuxiliary.isProcess(this.iEntry)
					&& (aEventTemp.getNumberOfInstances() > 1)) {
				aEventTemp.setNumberOfInstances(aEventTemp
						.getNumberOfInstances() - 1);
				this.currentEvents.put(execId, aEventTemp);
				aEvent.setNumberOfInstances(1);
				TestingAuxiliary.debug(this,
						"reduce number of instanced for exec id"
								+ execId.longValue());
				TestingAuxiliary.debug(this, "new number of instances is "
						+ aEventTemp.getNumberOfInstances());
			} else {
				TestingAuxiliary.debug(this, "remove execId "
						+ execId.longValue());
				this.currentEvents.remove(execId);
			}
		} catch (NullPointerException ex) {
		}
	}

	public void scheduleActivation(TestingEvent actvEvent) {
		if (actvEvent.getExecutionId() == 0) {
			actvEvent.setExecutionId();
		}
		this.actvScheduler.scheduleEvent(actvEvent);
	}

	public void scheduleActivation(long currentStep, long eventStep) {
		this.actvScheduler.scheduleEvent(currentStep, eventStep);
	}

	public void scheduleTermination(TestingEvent termEvent) {
		this.termScheduler.scheduleEvent(termEvent);
	}

	public void scheduleTermination(long currentStep, long eventStep) {
		this.termScheduler.scheduleEvent(currentStep, eventStep);
	}

	public Enumeration getScheduledActivations(long step) {
		return this.actvScheduler.getScheduledEvents(step);
	}

	public Enumeration getScheduledTerminations(long step) {
		return this.termScheduler.getScheduledEvents(step);
	}

	public void removeScheduledActivation(long step) {
		this.actvScheduler.removeScheduledEvent(step);
	}

	// this function checks whether there are activations
	// scheduled after given step
	public boolean futureActivationScheduled(long currentStep, long executionId) {
		TestingAuxiliary.debug(this, "futureActivationScheduled for "
				+ this.iEntry.getName() + ", executionId " + executionId);
		TestingAuxiliary
				.debug(this, " returns "
						+ this.actvScheduler.futureEventsScheduled(currentStep,
								executionId));
		return (this.actvScheduler.futureEventsScheduled(currentStep, executionId) != null);
	}

	/**
	 * TODO: here you change to take the process duration from the actual 
	 * OPM process or from the settings if it is not set. 
	 * 
	 */
	public long getDuration() {
		// temporary function - it should be part of OPCAT interface
		if (TestingAuxiliary.isProcess(this.iEntry)) {
			if (TestingSettings.FIXED_PROCESS_DURATION) {
				return TestingSettings.PROCESS_DURATION
						* TestingSettings.STEP_DURATION;
			} else {
				// Random r = new Random();
				// long temp = TestingSettings.PROCESS_DURATION_RANGE_START +
				// r.nextInt(TestingSettings.PROCESS_DURATION_RANGE_END -
				// TestingSettings.PROCESS_DURATION_RANGE_START);
				long temp = TestingSettings.PROCESS_DURATION_RANGE_START
						+ TestingAuxiliary
								.getRandomNumber(TestingSettings.PROCESS_DURATION_RANGE_END
										- TestingSettings.PROCESS_DURATION_RANGE_START);
				temp = temp * TestingSettings.STEP_DURATION;
				// TestingAuxiliary.debug(this,"random int value " + i + " for
				// " + this.getIXEntry().getName());
				return temp;
			}
		}

		if (TestingAuxiliary.isEvent(this.iEntry)) {

			if (TestingSettings.FIXED_REACTION_TIME) {
				return TestingSettings.REACTION_TIME
						* TestingSettings.STEP_DURATION;
			} else {
				// Random r = new Random();
				// long temp = TestingSettings.REACTION_TIME_RANGE_START +
				// r.nextInt(TestingSettings.REACTION_TIME_RANGE_END -
				// TestingSettings.REACTION_TIME_RANGE_START);
				long temp = TestingSettings.REACTION_TIME_RANGE_START
						+ TestingAuxiliary
								.getRandomNumber(TestingSettings.REACTION_TIME_RANGE_END
										- TestingSettings.REACTION_TIME_RANGE_START);
				temp = temp * TestingSettings.STEP_DURATION;
				return temp;
			}
		}
		return 0;
	}

	// temporary function - it should be part of OPCAT interface
	public long getMinTime() {
		if (TestingAuxiliary.isProcess(this.iEntry)) {
			IXProcessEntry iEntry = (IXProcessEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMinTimeActivation());
		}
		if (TestingAuxiliary.isLink(this.iEntry)) {
			IXLinkEntry iEntry = (IXLinkEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMinReactionTime());
		}

		if (TestingAuxiliary.isState(this.iEntry)) {
			IXStateEntry iEntry = (IXStateEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMinTime());
		}
		return 0;
	}

	public long getMaxTime() {
		if (TestingAuxiliary.isProcess(this.iEntry)) {
			IXProcessEntry iEntry = (IXProcessEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMaxTimeActivation());
		}
		if (TestingAuxiliary.isLink(this.iEntry)) {
			IXLinkEntry iEntry = (IXLinkEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMaxReactionTime());
		}

		if (TestingAuxiliary.isState(this.iEntry)) {
			IXStateEntry iEntry = (IXStateEntry) this.getIXEntry();
			return this.translateTime(iEntry.getMaxTime());
		}
		return java.lang.Long.MAX_VALUE;
	}

	private long translateTime(String timeString) {
		if (timeString.equals("infinity")) {
			return java.lang.Long.MAX_VALUE;
		}
		long l[] = { 1, 1000, 60000, 3600000, 86400000 };
		int i = 0;
		long time = 0;
		StringTokenizer st = new StringTokenizer(timeString, ";");
		for (i = 0; st.hasMoreTokens(); i++) {
			try {
				String temp = st.nextToken();
				long timeToken;
				timeToken = new Long(temp).longValue();
				if (i < 5) {
					time += timeToken * l[i];
				} else {
					time += timeToken * java.lang.Long.MAX_VALUE;
				}
			} catch (Exception e) {
				// invalid MaxTimeActivation or MinTimeActivation defined for
				// the process
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
		this.stopTesting(new TestingEvent());
	}

	public void animate(TestingEvent aEvent) {
		Enumeration eTemp = this.iEntry.getInstances();
		for (; eTemp.hasMoreElements();) {
			IXInstance i = (IXInstance) eTemp.nextElement();
			if (TestingAuxiliary.isLink(this.iEntry)) {
				IXLinkInstance iLinkInstance = (IXLinkInstance) i;
				if (TestingAuxiliary.isEvent(this.iEntry)) {
					// TestingAuxiliary.debug(this,"Event: before
					// animateAsFlash");
					iLinkInstance.animateAsFlash();
					// TestingAuxiliary.debug(this,"Event: after
					// animateAsFlash");
				} else {
					// link, but not event
					if (aEvent.getDuration() > 0) {
						// TestingAuxiliary.debug(this,"Link: before
						// animate");
						iLinkInstance.animate(aEvent.getDuration());
						// TestingAuxiliary.debug(this,"Link: after animate");
					}
				}
			} else {
				// object, process or state
				if (TestingAuxiliary.isState(this.iEntry)
						|| TestingAuxiliary.isThing(this.iEntry)) {
					// object or process
					IXConnectionEdgeInstance iEdge = (IXConnectionEdgeInstance) i;
					// TestingAuxiliary.debug(this,"getBackgroundColor " +
					// iThingInstance.getBackgroundColor());
					// TestingAuxiliary.debug(this,"defaultColor " +
					// defaultColor);
					TestingAuxiliary.debug(this, "animate thing "
							+ this.iEntry.getName());
					// TestingAuxiliary.debug(this,"duration " +
					// aEvent.getDuration());
					// TestingAuxiliary.debug(this,"Current time " +
					// System.currentTimeMillis());
					iEdge.animate(aEvent.getDuration());

					/**
					 * TODO: here ask the conf if to show th eurl or not?
					 */
					iEdge.animateUrl(aEvent.getDuration());
				}
			}
		}
	}

	public void stopTesting(TestingEvent aEvent) {
		Enumeration eTemp = this.iEntry.getInstances();
		for (; eTemp.hasMoreElements();) {

			if (TestingAuxiliary.isThing(this.iEntry)
					|| TestingAuxiliary.isState(this.iEntry)) {
				IXConnectionEdgeInstance i = (IXConnectionEdgeInstance) eTemp
						.nextElement();
				// TestingAuxiliary.debug(this,"stopTesting for " +
				// iEntry.getName());
				// TestingAuxiliary.debug(this,"duration = " +
				// aEvent.getDuration());
				i.stopTesting(aEvent.getDuration());
			} else {
				if (TestingAuxiliary.isLink(this.iEntry)) {
					// stopTesting for link was not supplied.
					IXLinkInstance i = (IXLinkInstance) eTemp.nextElement();
					// TestingAuxiliary.debug(this,"stopTesting for link " +
					// iEntry.getName());
					// TestingAuxiliary.debug(this,"duration " +
					// aEvent.getDuration());
					// i.stopTesting(aEvent.getDuration());
					i.stopTesting();
				}
			}
		}
	}

	public void pauseTesting() {
		Enumeration en = this.iEntry.getInstances();
		for (; en.hasMoreElements();) {
			if (TestingAuxiliary.isLink(this.iEntry)) {
				IXLinkInstance i = (IXLinkInstance) en.nextElement();
				i.pauseTesting();
			} else {
				if (TestingAuxiliary.isState(this.iEntry)
						|| TestingAuxiliary.isThing(this.iEntry)) {
					IXConnectionEdgeInstance i = (IXConnectionEdgeInstance) en
							.nextElement();
					// temporary remarked
					i.pauseTesting();
				}
			}
		}
	}

	public void continueTesting() {
		Enumeration en = this.iEntry.getInstances();
		for (; en.hasMoreElements();) {
			if (TestingAuxiliary.isLink(this.iEntry)) {
				IXLinkInstance i = (IXLinkInstance) en.nextElement();
				i.continueTesting();
			} else {
				if (TestingAuxiliary.isState(this.iEntry)
						|| TestingAuxiliary.isThing(this.iEntry)) {
					IXConnectionEdgeInstance i = (IXConnectionEdgeInstance) en
							.nextElement();
					i.continueTesting();
				}
			}
		}
	}

	// //////////////////////////
	// CHILD-PARENT functions//
	// //////////////////////////

	// this function returns a list of parents processes (not objects)
	public Enumeration getParents() {
		Enumeration en = this.iEntry.getInstances();
		Vector parents = new Vector();
		if (!TestingAuxiliary.isThing(this.iEntry)) {
			// return empty enumeration
			return parents.elements();
		}
		for (; en.hasMoreElements();) {
			IXThingInstance i = (IXThingInstance) en.nextElement();
			IXThingInstance parentInstance = i.getParentIXThingInstance();
			if (parentInstance != null) {
				if (TestingAuxiliary.isProcess(parentInstance.getIXEntry())) {
					IXEntry parentEntry = parentInstance.getIXEntry();
					if (!parents.contains(parentEntry)) {
						parents.add(parentEntry);
					}
				}
			}
		}
		return parents.elements();
	}

	public long getNextLevelY(IXThingInstance parentInstance, long levelY) {
		TestingAuxiliary.debug(this, "Starting getNextLevelY, parent = "
				+ parentInstance.getIXEntry().getName());
		TestingAuxiliary.debug(this, ", levelY = " + levelY);
		Enumeration en = parentInstance.getChildThings();
		long minY = java.lang.Long.MAX_VALUE;

		for (; en.hasMoreElements();) {
			IXThingInstance tempChildInstance = (IXThingInstance) en
					.nextElement();
			long tempChildY = tempChildInstance.getY();
			// check that this is a process
			if ( (tempChildInstance.getIXEntry() instanceof IXProcessEntry )  &&  (tempChildY <= minY) && (tempChildY > (levelY + 7))) {
				if (tempChildY < minY) {
					// children.clear();
					minY = tempChildY;
				}
				// children.add(tempChildInstance);
			}
		}
		TestingAuxiliary.debug(this, "after first loop, minY = " + minY);
		return minY;
	}

	public Enumeration getChildrenByLevel(IXThingInstance parentInstance,
			long levelY) {
		TestingAuxiliary.debug(this, "Starting getChildrenByLevel, parent = "
				+ parentInstance.getIXEntry().getName());
		TestingAuxiliary.debug(this, ", levelY = " + levelY);
		Enumeration en = parentInstance.getChildThings();
		Vector children = new Vector();
		long minY = java.lang.Long.MAX_VALUE;

		// find the Y coordinate of the next level
		// this is the minimal Y that is greater than levelY
		minY = this.getNextLevelY(parentInstance, levelY);

		// obtain all processes from this level
		// this is a temporary solution, because it is not possible
		// to set several entries exactly on the same level
		en = parentInstance.getChildThings();
		for (; en.hasMoreElements();) {
			IXThingInstance tempChildInstance = (IXThingInstance) en
					.nextElement();
			long tempChildY = tempChildInstance.getY();
			// check that this is a process
			IXProcessEntry temp = null;
			try {
				temp = (IXProcessEntry) tempChildInstance.getIXEntry();
			} catch (ClassCastException e) {
				// if not a process continue to a next child
				continue;
			}
			if ((tempChildY <= (minY + 3)) && (tempChildY >= minY)) {
				TestingAuxiliary.debug(this,
						"Found process in the same level,  y = " + tempChildY
								+ ",process = " + temp.getName());
				children.add(tempChildInstance);
			}
		}
		return children.elements();
	}

	// Returns all childern located on the next level after given child
	// on the zoomed in process
	public Enumeration getNextLevelChildren(IXThingInstance childInstance) {
		return this.getChildrenByLevel(childInstance.getParentIXThingInstance(),
				childInstance.getY());
	}

	public Enumeration getFirstLevelChildren(IXThingInstance parentInstance) {
		return this.getChildrenByLevel(parentInstance, java.lang.Long.MIN_VALUE);
	}

	// return true if the following conditions hold:
	// 1. process has no children processes
	// (i.e. is not zoomed in, or zoomed in without children processes)
	// 2. process has parent and it was activated in parent's context
	public boolean isLowLevel() {
		if (TestingAuxiliary.isProcess(this.iEntry)) {
			TestingAuxiliary
					.debug(this, "isLowLevel for " + this.iEntry.getName());
			TestingAuxiliary.debug(this,
					"this.getChildrenProcesses().isEmpty() = "
							+ this.getChildrenProcesses().isEmpty());
			/*
			 * TestingAuxiliary.debug(this,"this.getParents().hasMoreElements() = " +
			 * this.getParents().hasMoreElements());
			 * TestingAuxiliary.debug(this,"currentEvent.getParentProcess() = " +
			 * currentEvent.getParentProcess());
			 * TestingAuxiliary.debug(this,"isLowLevel returns " +
			 * (!this.getChildrenProcesses().hasMoreElements() &&
			 * (!this.getParents().hasMoreElements() ||
			 * currentEvent.getParentProcess() != null))); return
			 * (!this.getChildrenProcesses().hasMoreElements() &&
			 * (!this.getParents().hasMoreElements() ||
			 * currentEvent.getParentProcess() != null));
			 */

			return (this.getChildrenProcesses().isEmpty());
		}
		return true;
	}

	public Vector getChildrenProcesses() {
		return this.getChildrenByClass("gui.projectStructure.ProcessInstance");
	}

	public Vector getChildrenObjects() {
		return this.getChildrenByClass("gui.projectStructure.ObjectInstance");
	}

	public Vector getChildrenEntries() {
		return this.getChildrenByClass("");
	}

	private Vector getChildrenByClass(String className) {
		Vector results = new Vector();
		if (!TestingAuxiliary.isProcess(this.iEntry)) {
			return results;
		}
		Enumeration en = this.iEntry.getInstances();
		for (; en.hasMoreElements();) {
			IXProcessInstance processInstance = (IXProcessInstance) en
					.nextElement();
			Enumeration children = processInstance.getChildThings();
			for (; children.hasMoreElements();) {
				IXThingInstance childInstance = (IXThingInstance) children
						.nextElement();
				if ((childInstance.getClass().getName().equals(className))
						|| (className.equals(""))) {
					IXEntry childEntry = childInstance.getIXEntry();
					if (!results.contains(childEntry)) {
						results.add(childEntry);
					}
				}
			}
		}
		return results;
	}

	// this function is used for undo - it restores the entry from log
	// it updates the number of instances and the testing status
	public void restore(long fromStep, long toStep) {
		TestingAuxiliary.debug(this, "restore for " + this.iEntry.getName()
				+ ", from " + fromStep + ", to " + toStep);
		if (TestingAuxiliary.isLink(this.iEntry)) {
			if (fromStep > toStep) {
				// remove from the log all events that occured after toStep
				this.log.removeAllScheduledEvents(toStep, 0);
				// remove scheduled future events
				this.actvScheduler.removeAllScheduledEvents(toStep, 0);
				this.termScheduler.removeAllScheduledEvents(toStep, 0);
				return;
			}
		}
		// fromStep should be greater than toStep
		if (fromStep > toStep) {
			// find all events that occured after the new step
			int numOfInstances = this.getNumberOfInstances();
			TestingAuxiliary.debug(this, "Initial  numOfInstances "
					+ numOfInstances);
			for (long step = fromStep; step > toStep; step--) {
				Enumeration enEvents = this.log.getScheduledEvents(step);
				for (; enEvents.hasMoreElements();) {
					TestingEvent aEvent = (TestingEvent) enEvents
							.nextElement();
					TestingAuxiliary
							.debug(this, "Get event for step " + step);
					TestingAuxiliary.debug(this, "aEvent.getEventType() "
							+ aEvent.getEventType());
					// restore number of instances
					// if event is of activation type
					if (aEvent.getEventType() == TestingSettings.ACTIVATION_EVENT) {
						numOfInstances -= aEvent.getNumberOfInstances();
						TestingAuxiliary.debug(this, "minus "
								+ numOfInstances);
						// remove the corresponding entry from currentEvents
						// this.currentEvents.remove(new
						// Long(aEvent.getExecutionId()));
						this.removeCurrentEvent(aEvent);
					} else {
						numOfInstances += aEvent.getNumberOfInstances();
						TestingAuxiliary
								.debug(this, "plus " + numOfInstances);
						// find the corresponding activation event and add it to
						// currentEvents list
						TestingEvent actvEvent = this.log.futureEventsScheduled(0,
								aEvent.getExecutionId());
						if (actvEvent != null) {
							TestingAuxiliary.debug(this,
									"Step of restored activation event = "
											+ actvEvent.getStep());
							// this.currentEvents.put(new
							// Long(aEvent.getExecutionId()), new
							// TestingEvent(actvEvent));
							this.addCurrentEvent(new TestingEvent(actvEvent));
						}
					}
				}
			}

			// restore the testing in accordance with number of instances
			TestingAuxiliary.debug(this, "final " + numOfInstances);
			this.setNumberOfInstances(numOfInstances);
			if (numOfInstances > 0) {
				this.animate(new TestingEvent());
			} else {
				this.stopTesting(new TestingEvent());
			}
			// remove from the log all events that occured after toStep
			this.log.removeAllScheduledEvents(toStep, 0);
			// remove scheduled future events
			this.actvScheduler.removeAllScheduledEvents(toStep, 0);
			this.termScheduler.removeAllScheduledEvents(toStep, 0);
		}
	}

	public String getTaxtualInfo() {
		return this.taxtualInfo;
	}

	public void setTaxtualInfo(String taxtualInfo) {
		this.taxtualInfo = taxtualInfo;
	}

	public boolean isEventLinkActivated() {
		return this.isEventLinkActivated;
	}

	public void setEventLinkActivated(boolean isEventLinkActivated) {
		this.isEventLinkActivated = isEventLinkActivated;
	}
}
