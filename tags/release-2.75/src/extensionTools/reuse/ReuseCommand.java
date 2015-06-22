package extensionTools.reuse;


import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPIx.IXRelationInstance;
import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.Entry;
import gui.projectStructure.FundamentalRelationInstance;

import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectEntry;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.RelationEntry;
import gui.projectStructure.StateEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;


/**
 * The class contains all actions for the model import process. 
 * In order to use the class, it is necessary to create a <code>ReuseCommand</code>
 * object and to call the <code>commitReuse</code> method. 
 */
public class ReuseCommand {
    /* reuse types */
    public final static int SIMPLE_WHITE = 3;

    public static final int SIMPLE_BLACK = 2;

    public final static int OPEN = 1;

    public final static int NO_REUSE = 0;

    public final static int REGULAR_SYSTEM = 0;

    public final static int OPEN_REUSED_SYSTEM = 1;
    
    public final static int MOVE = 10 ; 

    OpdProject reusedProject = null;

    OpdProject currentProject = null;
    
    Opd reusedBaseOpd = null ;
    
    private boolean first = true ; 

    int reuseType = 0;

    // reuseTables

    /**
     * Table: reusedEntriesTable + reusedInstancesTable two hash tables that
     * save the tuples of reused and added instances/entires were created in
     * order to maintain correct entry/instance relations
     */
    private Hashtable reusedEntriesTable = new Hashtable();

    private Hashtable reusedInstancesTable = new Hashtable();

    /**
     * saves for each OPD which is open reused the correct path/link to the the
     * system it is open reused to
     */

    private LinkedList reusedInstancesGlobalList = new LinkedList();

    /**
     * a list of OPD's that were already open reused
     */
    private LinkedList reusedOpdList = new LinkedList();

    /**
     * Constructor for the reuse operation - sets the properties for the reuse,
     * and fires the reuse action.
     * 
     * @param _current
     *            the current system that the reused model will be imported into
     *            it
     * @param _reused
     *            the reused system - the external model that will be imported
     *            into the current one
     * @param _reuseType
     *            The type of the reuse commited, can be NO_REUSE, OPEN,
     *            SIMPLE_WHITE, SIMPLE-BLACK.
     */
    
    public ReuseCommand(OpdProject _current, OpdProject _reused, int _reuseType) {
    	
        //clearing undo history
        Opcat2.getUndoManager().discardAllEdits();
        Opcat2.setRedoEnabled(false);
		Opcat2.setUndoEnabled(false);
        
        reusedProject = _reused;
        currentProject = _current;
        currentProject.reusedProject = _reused;
        reuseType = _reuseType;
    	first = true ; 
    }
    
    
    public ReuseCommand(OpdProject _current, OpdProject _reused , Opd _reusedBase , int _reuseType) {
        //clearing undo history
        Opcat2.getUndoManager().discardAllEdits();
        Opcat2.setRedoEnabled(false);
		Opcat2.setUndoEnabled(false);
        
        reusedProject = _reused;
        currentProject = _current;
        currentProject.reusedProject = _reused;
        reuseType = _reuseType;
		reusedBaseOpd = _reusedBase;
    	first = true ; 
    }    

    /**
     * The method commits all types of reuse. First, the method commits a simple
     * reuse, afterwards it implies the black or open reuse means, if necessary.
     * At the end, the method sets the current OPD to do original one, and
     * presents the OPD to the user. Finally, the method updates the model with
     * the new changes.
     * 
     * @see #emptyAndLockReusedInstancesGlobalList
     */
    public void commitReuse() throws OpcatException {
    	
    	currentProject.setPresented(false) ; 
    	
        if (reuseType == NO_REUSE) {
            throw new OpcatException("No reuse type was determined");
        }
        // remember the Opd we started the reuse at so we will come to him at
        // the end
        Opd current = currentProject.getCurrentOpd();
        ThingInstance designatedContainer = null;
        boolean forceContainer = false;
        try {
            ThingInstance selectedContainer = (ThingInstance) currentProject
                    .getCurrentOpd().getSelectedItem();
            if (selectedContainer != null && selectedContainer.isZoomedIn()) {
                designatedContainer = selectedContainer;
                    forceContainer = true;
            }
        } catch (ClassCastException ex) {
        }
        current.removeSelection();    	
    	
    	if (reusedBaseOpd == null) {
    		reusedBaseOpd = reusedProject.getCurrentOpd() ; 
    	} 
    	
        addReusedOpd(reusedBaseOpd, currentProject.getCurrentOpd(), designatedContainer, forceContainer);    	
    	    	
        //Handling black and open reuse
        if (reuseType == SIMPLE_BLACK || reuseType == OPEN)
            emptyAndLockReusedInstancesGlobalList();
        currentProject.reusedProject = null;
    	currentProject.setPresented(true) ;         
        currentProject.updateChanges();
        //Returning to the original OPD
        currentProject.refresh();    	
    }

    
 
    /**
     * Copying elements from a reused OPD to a target OPD recursively. general
     * algorithm:
     * <p>
     * going over the entire elements of the resued system in two main loops
     * <br>
     * First Loop - reuse all the things (object/processes). For each thing, a
     * new entry and instance are created. If the thing has inner levels, the
     * inner levels are created using <code>createInnerLevels()</code> method.
     * <br>
     * Second loop - reuse all the links relations <br>
     * 
     * @param reusedOpd
     *            The OPD that the elements are copied from
     * @param targetOpd
     *            The OPD that the elements are copied to
     * @param container
     *            The instance that the elements will be placed within it. If it
     *            is null, then the elements will not be contained within an
     *            inzommed entity.
     * @param forceContainer
     *            A flag indicating whether a container which is not part of the
     *            reused model will be forced on elements of the model. The
     *            method is used mainly for the first level of reusing, when
     *            reusing inside a zoomed-in thing in the current model
     */
    public boolean addReusedOpd(Opd reusedOpd, Opd targetOpd, ThingInstance container, boolean forceContainer) throws OpcatException {
        int x = 0, y = 0; // coordinates for objects/processes at first level
        if (GraphicsUtils.getLastMouseEvent() != null ) {
			x = GraphicsUtils.getLastMouseEvent().getX();  
			y = GraphicsUtils.getLastMouseEvent().getY();
        }
		Point showPoint = new Point(x,y);
	    x = (int) showPoint.getX() ; 
	    y = (int) showPoint.getY();	
	    

	    Instance generalInstance = null;
        ThingEntry myEntry;
        ConnectionEdgeInstance conInstance;
        
        try	{
	        // first loop - running on all the elements of the reused OPD
	        Enumeration e = reusedProject.getIXSystemStructure()
	                .getInstancesInOpd(reusedOpd.getOpdId());
	        while (e.hasMoreElements()) {
	            generalInstance = (Instance) e.nextElement();
	            currentProject.setCurrentOpd(targetOpd);
	            if (generalInstance instanceof ObjectInstance
	                    || generalInstance instanceof ProcessInstance) {
	
	                //determining the location of the new objects
	                conInstance = (ConnectionEdgeInstance) generalInstance;
	                if (first) {
	                	first = false ; 
	                } else {
		                y = conInstance.getY();
		                x = conInstance.getX();
	                }
	
	                // object section in loop - also in charge of states
	                //Adding the actual objects/processes to the target OPD
	                //Note that the addign methods may return <code>null</code> if
	                // the
	                //thing was alrady added as a main instance
	                ThingInstance addedReusedInstance = null;
	                ThingInstance theContainer = null;
	                if ((container != null && forceContainer)
	                        || generalInstance.getParent() != null) {
	                    theContainer = container;
	                }
	                if (generalInstance instanceof ObjectInstance) {
	                    addedReusedInstance = addReusedObject(generalInstance, x, y, targetOpd, theContainer);
	                    //Adding process
	                } else if (generalInstance instanceof ProcessInstance) {
	                    addedReusedInstance = addReusedProcess(generalInstance, x, y, targetOpd, theContainer);
	                } else {
	                    //In this case we got something which is not an object or a
	                    // process
	                    OpcatLogger.logError("instance wrong in reuse - "
	                            + generalInstance);
	                }
	 
	                myEntry = (ThingEntry) generalInstance.getEntry();
	                ThingInstance thingInstance = (ThingInstance) generalInstance;
	                //Creates zooming levels
	                if (myEntry.getZoomedInOpd() != null
	                        && !isOpdReused(myEntry.getZoomedInOpd())) {
	                    createZoominLevels((ThingInstance) generalInstance, addedReusedInstance, targetOpd);
	                }
	                //Creates unfolding levels
	                if (thingInstance.getUnfoldingOpd() != null) {
	                    createUnfoldingLevels((ThingInstance) generalInstance, addedReusedInstance, targetOpd);
	                }
	            }
	        }
	        // end of first loop
	        // this function arranges the father-son relations between Things
	        // second loop
	        // this loop goes over all the elements in the Opd
	        // add's all the relations and the links
	        e = reusedProject.getSystemStructure().getInstancesInOpd(reusedOpd
	                .getOpdId());
	        currentProject.setCurrentOpd(targetOpd);
	        while (e.hasMoreElements()) {
	            generalInstance = (Instance) e.nextElement();
	            if (generalInstance instanceof LinkInstance)
	                addReusedLink(generalInstance, targetOpd);
	
	            if (generalInstance instanceof GeneralRelationInstance)
	                addReusedRelation(generalInstance, true);
	
	            if (generalInstance instanceof FundamentalRelationInstance)
	                addReusedRelation(generalInstance, false);
	        }
        }
        catch (OpcatException oex)	{
            throw oex;
        }
        catch (Exception ex)	{
            throw new OpcatException(ex.getMessage());
        }
        return true;
    }

    /**
     * Adds an object from the reused model to the current model. If the thing
     * has a container, then it is added under this container. The thing might
     * not be added if it was added before as a main instance.
     * 
     * @param reusedProcessInstance
     * @param x
     *            The x coordinate of the new thing
     * @param y
     *            The y coordinate of the new thing
     * @param current_opd
     *            The opd in which the thing is added
     * @param container
     *            The container object of the thing. Can be null, if the thing
     *            is not added under a container
     * @return The new instance, or null if the thing was not added
     */
    protected ObjectInstance addReusedObject(Instance reusedObjectInstance, int x, int y, Opd targetOpd, ThingInstance container) throws OpcatException {

        if (reusedInstancesTable.containsKey(reusedObjectInstance)) {
            return null;
        }
        ObjectInstance addedObjectInstance;
        ObjectInstance ins = (ObjectInstance) reusedObjectInstance;

        //If the thing was already copied from the reused model.
        //It can happen, for instance, when things from inner levels were
        // copied
        //before things from upper levels
        try {
            if (existReusedEntry(reusedObjectInstance.getEntry())) {
                ThingEntry originalEntry = (ThingEntry) reusedEntriesTable
                        .get(reusedObjectInstance.getEntry());
                //Handling things that have a container
                if (container != null) {
                    addedObjectInstance = (ObjectInstance) currentProject
                            .addObjectInstanceToContainer(ins.getEntry().getName(), x, y, originalEntry, container
                                    .getThing());
                } else {
                    addedObjectInstance = (ObjectInstance) currentProject
                            .addObjectInstance(ins.getEntry().getName(), x, y, originalEntry);
                }

            //Adding things for the first time
            } else {
                if (container != null) {
                    //Adding the current object to a container
                    addedObjectInstance = (ObjectInstance) currentProject
                            .addObject(x, y, (Container) container.getThing(), -1, -1, false);
                } else {
                    //Adding the current object without a container
                    addedObjectInstance = (ObjectInstance) currentProject
                            .addObject(reusedObjectInstance.getEntry().getName(), x, y, targetOpd
                                    .getOpdId());
                }
                copyReusedObjectEntryProperties((ObjectEntry) reusedObjectInstance
                        .getEntry(), (ObjectEntry) addedObjectInstance.getEntry());
            }
            ObjectInstance i = (ObjectInstance) reusedObjectInstance;
            addedObjectInstance.setSize(i.getWidth(), i.getHeight());
            addedObjectInstance.setStatesAutoArranged(i.isStatesAutoArranged());
            //Adding the states
            addReusedStates(addedObjectInstance, (ObjectInstance)reusedObjectInstance, targetOpd);
            
            addedObjectInstance
                    .copyPropsFrom((ObjectInstance) reusedObjectInstance);
            addedObjectInstance.setLocation(x, y);
            //addedObjectInstance.update();
            
            
            OpdConnectionEdge oce = addedObjectInstance.getConnectionEdge();
            OpdConnectionEdge reoce = ins.getConnectionEdge();
            
            if (addedObjectInstance.isStatesAutoArranged())	{
    			Point initLocation = new Point((int) oce.getX(), (int) oce.getY());
    			Dimension initSize = new Dimension(oce.getWidth(), oce.getHeight());

    			oce.fitToContent();
            }
            
            oce.setSize(reoce.getWidth(), reoce.getHeight()) ; 
            oce.setLocation(reoce.getX(), reoce.getY()) ; 
            
            if (reuseType == OPEN) {
                currentProject.addInstanceToOpenReuseList(addedObjectInstance);
                addedObjectInstance.getEntry().setOpenReused(true);
            }
            reuseTablesUpdate(reusedObjectInstance, addedObjectInstance);
        } catch (RuntimeException e) {
            String message = "Importing process had failed in object: "+ reusedObjectInstance +", in Opd "+ targetOpd;
            throw new OpcatException(message);
        }
        return addedObjectInstance;
    }

    
    /**
     * Adds a process from the reused model to the current model. If the thing
     * has a container, then it is added under this container. The thing might
     * not be added if it was added before as a main instance.
     * 
     * @param reusedProcessInstance
     * @param x
     *            The x coordinate of the new thing
     * @param y
     *            The y coordinate of the new thing
     * @param current_opd
     *            The opd in which the thing is added
     * @param container
     *            The container object of the thing. Can be null, if the thing
     *            is not added under a container
     * @param forceContainer
     *            If sets to <code>true</code>, then the new instance will be
     *            placed within the given <code>container</code>, even
     *            though, in the reused model, the thing was not contained
     *            originally.
     * @return The new instance, or null if the thing was not added
     */
    protected ProcessInstance addReusedProcess(Instance reusedProcessInstance, int x, int y, Opd current_opd, ThingInstance container) throws OpcatException {
        if (reusedInstancesTable.containsKey(reusedProcessInstance)) {
            return null;
        }
        ProcessInstance addedProcessInstance = null;
        ProcessInstance fatherprocessInstance = null;
        ProcessInstance reusedFatherProIns = null, pIns = null;
        try {
            if (existReusedEntry(reusedProcessInstance.getEntry())) {
                ThingEntry entry = (ThingEntry) reusedEntriesTable
                        .get(reusedProcessInstance.getEntry());
                //Handling things that have a container
                if (container != null) {
                    addedProcessInstance = (ProcessInstance) currentProject
                            .addProcessInstanceToContainer(reusedProcessInstance
                                    .getEntry().getName(), x, y, entry, container
                                    .getThing());
                }
                //Handling things without a container
                else {
                    addedProcessInstance = (ProcessInstance) currentProject
                            .addProcessInstance(reusedProcessInstance.getEntry()
                                    .getName(), x, y, entry);
                }
                //addedProcessInstance.setZoomedIn(true);
            } else {
                if (container != null) {
                    //				Creating process with a container
                    addedProcessInstance = (ProcessInstance) currentProject
                            .addProcess(x, y, container.getThing(), -1, -1);
                } else {
                    //Adding the process without a container
                    addedProcessInstance = (ProcessInstance) currentProject
                            .addProcess(reusedProcessInstance.getEntry().getName(), x, y, current_opd
                                    .getOpdId());
                }
                addedProcessInstance.getEntry().setName(reusedProcessInstance
                        .getEntry().getName());
            }
            ConnectionEdgeInstance resuedConInstance = (ConnectionEdgeInstance) reusedProcessInstance;
            addedProcessInstance
                    .setSize(resuedConInstance.getWidth(), resuedConInstance
                            .getHeight());
            addedProcessInstance
                    .copyPropsFrom((ProcessInstance) reusedProcessInstance);
            addedProcessInstance.setLocation(x, y);
            //addedProcessInstance.update();
            if (reuseType == OPEN) {
                currentProject.addInstanceToOpenReuseList(addedProcessInstance);
                addedProcessInstance.getEntry().setOpenReused(true);
            }
            reuseTablesUpdate(reusedProcessInstance, addedProcessInstance);
        } catch (RuntimeException e) {
            String message = "Importing process had failed in process: "+ reusedProcessInstance +", in Opd "+ current_opd;
            throw new OpcatException(message);
        }
        return addedProcessInstance;
    }

    /**
     * Copies an object's states from the reused object to the new object. 
     */
    protected void addReusedStates(ObjectInstance addedObject, ObjectInstance reusedFrom, Opd targetOpd) {
        ObjectInstance reusedObjectFrom = (ObjectInstance) reusedFrom;
        StateInstance addedReusedState, instance = null;

        Entry addedState;
        Enumeration g;
        Enumeration e = reusedObjectFrom.getStateInstances();
        while (e.hasMoreElements()) {
            addedReusedState = (StateInstance) e.nextElement();
            StateInstance targetStateInstance;
            // if this state has not been entered yet
            if (!existReusedEntry(addedReusedState.getEntry())) {
                addedState = (Entry) currentProject.addState(addedReusedState
                        .getEntry().getName(), addedObject);
                
                reusedEntriesTable.put(addedReusedState.getEntry(), addedState);
                //Copy entry properties
                copyReusedStateEntryProperties((StateEntry) addedReusedState
                        .getEntry(), (StateEntry) addedState);
                g = addedObject.getStateInstances();
                //Find the instance of the new state we added.
                while (g.hasMoreElements()) {
                    instance = (StateInstance) g.nextElement();
                    if (instance.getEntry().getName().equals(addedState
                            .getName()))
                        break;
                }
                reusedInstancesTable.put(addedReusedState, instance);
                
                targetStateInstance = (StateInstance)instance;
                if (reuseType == OPEN) {
                    targetStateInstance.getEntry().setOpenReused(true);
                    currentProject
                            .addInstanceToOpenReuseList(targetStateInstance);
                }
                reusedInstancesGlobalList.add(targetStateInstance);
            }
            // if this state has been already entered
            else {
                //For some strange reason, the instance comes already with the
                //states. We delete the old ones and copy the new ones.
                g = addedObject.getStateInstances();
                StateInstance killed;
                while (g.hasMoreElements()) {
                    killed = (StateInstance) g.nextElement();
                    if (killed.getEntry().getName().equals(addedReusedState.getEntry().getName()))	{
                    	killed.removeFromContainer();
                    }
                }
                
                instance = currentProject
                        .addStateInstance(targetOpd, addedObject, (StateEntry) reusedEntriesTable
                                .get(addedReusedState.getEntry()));
                reusedInstancesTable.put(addedReusedState, instance);
                targetStateInstance = (StateInstance)instance;
                if ((reuseType == SIMPLE_BLACK) || (reuseType == OPEN)) {
                    targetStateInstance.getEntry().commitLock();

                    if (reuseType == OPEN)
                        targetStateInstance.getEntry().setOpenReused(true);
                    currentProject
                            .addInstanceToOpenReuseList(targetStateInstance);
                }
                reusedInstancesGlobalList.add(targetStateInstance);
            }
            
            targetStateInstance.copyPropsFrom(addedReusedState);
            targetStateInstance.setSize(addedReusedState.getWidth(), addedReusedState.getHeight());
            targetStateInstance.setLocation(addedReusedState.getX(), addedReusedState.getY());
            //targetStateInstance.update();
        }
    }

    /**
     * The method creates a new OPD with the zoomin levels of the main instance.
     * 
     * @param reusedInstance
     *            The original instance in the reused model
     * @param addedInstance
     *            The instance that was added to the base model
     * @param target
     *            The OPD in which the addedInstance was added to
     */
    protected void createZoominLevels(ThingInstance reusedInstance, ThingInstance addedInstance, Opd target) throws OpcatException 
    {

        ThingEntry myEntry = (ThingEntry) reusedInstance.getEntry();
        ThingInstance myInstance;

        try {
            //Performing the creation of inner levels
            Opd zoomIn = myEntry.getZoomedInOpd(); //Retrieving the OPD of the
            if (zoomIn == null) {
                if (reuseType  == MOVE) {
                	reusedProject.deleteOpd(reusedInstance.getOpd(), true); 
                }
                return;
            }
            Opd newOpd = currentProject.createNewOpd(true, addedInstance, target);
            reusedOpdList.add(zoomIn); //Updating the reuse opd list
            
            ThingEntry tEntry = (ThingEntry) addedInstance.getEntry();
            tEntry.setZoomedInOpd(newOpd);
            // adding another object / process
            // get the reused system main instance
            ThingInstance mainReusedIns = (ThingInstance) zoomIn.getMainIInstance();
            currentProject.setCurrentOpd(newOpd);
//        reusedProject.setCurrentOpd(zoomIn);
            ThingInstance newMainIns = null;
            if (mainReusedIns instanceof ObjectInstance) {
                newMainIns = (ThingInstance) addReusedObject((ObjectInstance) mainReusedIns, mainReusedIns
                        .getX(), mainReusedIns.getY(), newOpd, null);
            } else if (mainReusedIns instanceof ProcessInstance) {
                newMainIns = (ThingInstance) addReusedProcess((ProcessInstance) mainReusedIns, mainReusedIns
                        .getX(), mainReusedIns.getY(), newOpd, null);
            }
            newMainIns.setLocation(mainReusedIns.getX(), mainReusedIns.getY());
            newMainIns.setZoomedIn(true);
            //newMainIns.update();

            
            //Reusing the inner level OPD
            addReusedOpd(zoomIn, newOpd, newMainIns, false);
            Entry newInsEntry = newMainIns.getEntry();
            //newInsEntry.updateInstances();
            newOpd.setMainEntry((ThingEntry)newInsEntry);
            newOpd.setMainInstance(newMainIns);
            currentProject.getParentDesktop().getDesktopManager().deiconifyFrame(
            		newOpd.getOpdContainer());
            if ((reuseType == SIMPLE_BLACK) || (reuseType == OPEN)) {
                newOpd.setLocked(true);
            }
            if (reuseType == OPEN) {
                newOpd.setOpenReused(true);
            }
            if (reuseType  == MOVE) {
            	reusedProject.deleteOpd(zoomIn, true); 
            }   
        } catch (OpcatException oe) {
            throw oe;
        } catch (RuntimeException ex)	{
            String errorMessage = "Import had failed in inzooming Opd for thing: "+ reusedInstance;
            throw new OpcatException(errorMessage);
        }
    }

    /**
     * The method creates a new OPD with the unfolding levels of the main instance.
     * 
     * @param reusedInstance
     *            The original instance in the reused model
     * @param addedInstance
     *            The instance that was added to the base model
     * @param target
     *            The OPD in which the addedInstance was added to
     */
    protected void createUnfoldingLevels(ThingInstance reusedInstance, ThingInstance addedInstance, Opd target) throws OpcatException {
         Opd unfold = reusedInstance.getUnfoldingOpd();	//The unfolded OPD from the reused model
        if (unfold == null)	{
            if (reuseType  == MOVE) {
            	reusedProject.deleteOpd(reusedInstance.getOpd(), true); 
            }         	
            return;
        }
        try {
            ThingInstance myInstance = null;
            ThingInstance newMainIns = null;
            ThingInstance mainReusedIns = (ThingInstance) unfold.getMainIInstance();      
            myInstance = (ThingInstance) reusedInstance;
            //If the OPD was already reused, we don't reuse it again
            if (!isOpdReused(unfold)) {
                //Performing the creation of inner levels
                Opd newOpd = currentProject.createNewOpd(false, addedInstance, target);
                reusedOpdList.add(unfold);
                //adding the original instance objects/process
                currentProject.setCurrentOpd(newOpd);
                //reusedProject.setCurrentOpd(unfold);
                newMainIns = null;
                int x = mainReusedIns.getX();
                int y = mainReusedIns.getY();
                if (mainReusedIns instanceof ObjectInstance) {
                    newMainIns = (ThingInstance) addReusedObject((ObjectInstance) mainReusedIns, x, y, newOpd, null);
                } else {
                    if (mainReusedIns instanceof ProcessInstance) {
                        newMainIns = (ThingInstance) addReusedProcess((ProcessInstance) mainReusedIns, x, y, newOpd, null);
                    }
                }
                newMainIns.setLocation(x, y);
                //newMainIns.update();
                newOpd.setMainInstance(newMainIns);

                //Adding the elements of the inner levels to the new OPD
                addReusedOpd(unfold, newOpd, newMainIns, false);

                if ((reuseType == SIMPLE_BLACK) || (reuseType == OPEN)) {
                    newOpd.setLocked(true);
                }
                if (reuseType == OPEN) {
                    newOpd.setOpenReused(true);
                }               
                if (reuseType  == MOVE) {
                	reusedProject.deleteOpd(unfold, true); 
                }                   
            }
        } catch (OpcatException e) {
            throw e;
        } catch (RuntimeException e) {
            String errorMessage = "Import had failed in unfolding Opd for thing: "+ reusedInstance;
            throw new OpcatException(errorMessage);
        } 
    }

	/**
	 * Adds a link, according to the given instance, to the target OPD. 
	 * @param	reusedLinkInstance		The link to be reused
	 * @param	target					The OPD that the link is copied to.
	 */
    protected LinkInstance addReusedLink(Instance reusedLinkInstance, Opd target) throws OpcatException {
        LinkInstance linkInstance = (LinkInstance) reusedLinkInstance;
        Instance source, destination;
        Instance n_source, n_destination;
        source = linkInstance.getSourceInstance();
        destination = linkInstance.getDestinationInstance();
        n_source = (Instance) reusedInstancesTable.get(source);
        n_destination = (Instance) reusedInstancesTable.get(destination);
        LinkInstance myLinkInstance = null;
        try {
            LinkEntry lEntry = (LinkEntry) linkInstance.getEntry();
            myLinkInstance = (LinkInstance) currentProject
                    .addLink(n_source, n_destination, lEntry.getLinkType(), target);
            
            LinkEntry newEntry = (LinkEntry)myLinkInstance.getEntry();
            newEntry.setPath(lEntry.getPath());
            newEntry.setCondition(lEntry.getCondition());
            newEntry.setDescription(lEntry.getDescription());
            newEntry.setEnvironmental(lEntry.isEnvironmental());
            newEntry.setMaxReactionTime(lEntry.getMaxReactionTime());
            newEntry.setMinReactionTime(lEntry.getMinReactionTime());
            
            if (myLinkInstance == null)
                return null;
            myLinkInstance.copyPropsFrom(linkInstance);
            myLinkInstance.setDestinationConnectionPoint(linkInstance.getDestinationConnectionPoint());
            myLinkInstance.setSourceConnectionPoint(linkInstance.getSourceConnectionPoint()) ; 
            //myLinkInstance.makeStraight();
            //myLinkInstance.update();
            if (reuseType == OPEN) {
                currentProject.addInstanceToOpenReuseList(myLinkInstance);
                myLinkInstance.getEntry().setOpenReused(true);
            }
            reusedInstancesGlobalList.add(myLinkInstance);
        } catch (RuntimeException e) {
            String errorMessage = "Import had failed in creating link: "+ reusedLinkInstance +", in Opd "+ target;
            throw new OpcatException(errorMessage);
        }
        return myLinkInstance;
    }

    /**
     * Copies a relation from the reused model to the current project. 
     */
    protected void addReusedRelation(Instance reusedRelationInstance, boolean isGeneral) throws OpcatException {
        GeneralRelationInstance relationInstance;
        FundamentalRelationInstance funRelationInstance;
        Instance source, destination, instance = null;
        ConnectionEdgeInstance n_source, n_destination;
        RelativeConnectionPoint destPoint = null , sourcePoint = null; 
        RelationEntry rEntry = null;
        try {
            if (isGeneral) {
                relationInstance = (GeneralRelationInstance) reusedRelationInstance;
                source = relationInstance.getSourceInstance();
                destination = relationInstance.getDestinationInstance();
                rEntry = (RelationEntry) relationInstance.getEntry();
                destPoint = ((GeneralRelationInstance) reusedRelationInstance).getDestinationConnectionPoint();
                sourcePoint = ((GeneralRelationInstance) reusedRelationInstance).getSourceConnectionPoint();
                
            } else {
                funRelationInstance = (FundamentalRelationInstance) reusedRelationInstance;
                source = funRelationInstance.getSourceInstance();
                destination = funRelationInstance.getDestinationInstance();
                rEntry = (RelationEntry) funRelationInstance.getEntry();                
                destPoint = ((FundamentalRelationInstance) reusedRelationInstance).getDestinationConnectionPoint(); 
                sourcePoint = ((FundamentalRelationInstance) reusedRelationInstance).getSourceConnectionPoint();
            }
            try {
	            n_source = (ConnectionEdgeInstance) reusedInstancesTable.get(source);
	            n_destination = (ConnectionEdgeInstance) reusedInstancesTable
	                    .get(destination);
            } catch (Exception e) {
            	n_source =null ; 
            	n_destination = null ; 
            }

            IXRelationInstance myRelationInstance = currentProject
            .addRelation(n_source, n_destination, rEntry.getRelationType());
        	myRelationInstance.setDestinationConnectionPoint(destPoint) ;
        	myRelationInstance.setSourceConnectionPoint(sourcePoint) ;             
        	
            RelationEntry newEntry = (RelationEntry)myRelationInstance.getIXEntry();
            newEntry.setBackwardRelationMeaning(rEntry.getBackwardRelationMeaning());
            newEntry.setForwardRelationMeaning(rEntry.getForwardRelationMeaning());
            newEntry.setDestinationCardinality(rEntry.getDestinationCardinality());
            newEntry.setSourceCardinality(rEntry.getSourceCardinality());
            newEntry.setDescription(rEntry.getDescription());
            newEntry.setEnvironmental(rEntry.isEnvironmental());
            //newEntry.updateInstances();
            if (isGeneral) {
                relationInstance = (GeneralRelationInstance) myRelationInstance;
                relationInstance
                        .copyPropsFrom((GeneralRelationInstance) reusedRelationInstance);
                //relationInstance.update();
                
            } else {
                ((FundamentalRelationInstance) myRelationInstance).setGraphicalRelationRepresentation(((FundamentalRelationInstance) reusedRelationInstance)) ;              	
                funRelationInstance = (FundamentalRelationInstance) myRelationInstance;
                
                funRelationInstance.arrangeLines();
                
                //funRelationInstance.update();
                
            }
            
            reusedInstancesGlobalList.add(myRelationInstance);
            if (reuseType == OPEN) {
                currentProject.addInstanceToOpenReuseList(instance);
                instance = (Instance) myRelationInstance;
                instance.getEntry().setOpenReused(true);
            }
        } catch (RuntimeException e) {
        	e.printStackTrace() ; 
            String errorMessage = "Import had failed in creating relation: "+ reusedRelationInstance;
            throw new OpcatException(errorMessage);

        }
    }

    /** ********************************************************************* */
    /* function: copyReusedThingEntryProperties */
    /* purpose: */
    /* copying properties between entires */
    /** ********************************************************************* */
    //TODO: add this command to the Entry class
    protected void copyReusedEntryProperties(Entry reusedEntry, Entry targetEntry) {
        targetEntry.setDescription(reusedEntry.getDescription());
        targetEntry.setName(reusedEntry.getName());
        targetEntry.setEnvironmental(reusedEntry.isEnvironmental());
        //targetEntry.updateInstances();
    }

    /** ********************************************************************* */
    /* function: copyReusedThingEntryProperties */
    /* purpose: */
    /* copying properties between thing entires */
    /** ********************************************************************* */
    protected void copyReusedThingEntryProperties(ThingEntry reusedThingEntry, ThingEntry targetThingEntry) {
        targetThingEntry.setPhysical(reusedThingEntry.isPhysical());
        targetThingEntry.setScope(reusedThingEntry.getScope());
        targetThingEntry.setRole(reusedThingEntry.getRole());
        targetThingEntry.setNumberOfMASInstances(reusedThingEntry
                .getNumberOfMASInstances());
        targetThingEntry.setDescription(targetThingEntry.getDescription());
        //targetThingEntry.updateInstances();
    }

    /** ********************************************************************* */
    /* function: copyReusedObjectEntryProperties */
    /* purpose: */
    /* copying properties between object entires */
    /** ********************************************************************* */
    protected void copyReusedObjectEntryProperties(ObjectEntry reusedObjectEntry, ObjectEntry targetObjectEntry) {
        targetObjectEntry.setPersistent(reusedObjectEntry.isPersistent());
        targetObjectEntry.setEnvironmental(reusedObjectEntry.isEnvironmental());
        targetObjectEntry.setDescription(reusedObjectEntry.getDescription());
        targetObjectEntry.setName(reusedObjectEntry.getName());
        targetObjectEntry.setNumberOfMASInstances(reusedObjectEntry
                .getNumberOfMASInstances());
        targetObjectEntry.setRole(reusedObjectEntry.getRole());
        targetObjectEntry.setScope(reusedObjectEntry.getScope());
        targetObjectEntry.setType(reusedObjectEntry.getType());
        targetObjectEntry.setInitialValue(reusedObjectEntry.getInitialValue());
        targetObjectEntry.setKey(reusedObjectEntry.isKey());
        targetObjectEntry.setIndexOrder(reusedObjectEntry.getIndexOrder());
        targetObjectEntry.setPhysical(reusedObjectEntry.isPhysical());
        targetObjectEntry.setPersistent(reusedObjectEntry.isPersistent());
        targetObjectEntry.setIndexName(reusedObjectEntry.getIndexName());
        targetObjectEntry.setTypeOriginId(reusedObjectEntry.getTypeOriginId());
        
        //targetObjectEntry.updateInstances();
    }

    /** ********************************************************************* */
    /* function: copyReusedStateEntryProperties */
    /* purpose: */
    /* copying properties between state entires */
    /** ********************************************************************* */
    protected void copyReusedStateEntryProperties(StateEntry reusedStateEntry, StateEntry targetStateEntry) {
        targetStateEntry.setDefault(reusedStateEntry.isDefault());
        targetStateEntry.setFinal(reusedStateEntry.isFinal());
        targetStateEntry.setInitial(reusedStateEntry.isInitial());
        targetStateEntry.setEnvironmental(reusedStateEntry.isEnvironmental());
        targetStateEntry.setDescription(reusedStateEntry.getDescription());
        //targetStateEntry.updateInstances();
    }


    /** ********************************************************************* */
    /* function: emptyAndLockReusedInstancesGlobalList */
    /* purpose: */
    /* locking all the reuse instanes after the reuse was ended */
    /** ********************************************************************* */
    protected void emptyAndLockReusedInstancesGlobalList() {
        Color color = new Color(192, 192, 192);
        while (!reusedInstancesGlobalList.isEmpty()) {
            Instance aInstance = (Instance) reusedInstancesGlobalList
                    .getFirst();
            reusedInstancesGlobalList.removeFirst();
            if (aInstance instanceof StateInstance)
                continue;
            aInstance.setBorderColor(color);
            aInstance.getEntry().commitLock();
            //aInstance.update();
        }
    }

  


    /**
     * Checks whether an entry was already reused by the process. 
     * @param specialEntry	
     * @return
     */
    protected boolean existReusedEntry(Entry specialEntry) {
        return reusedEntriesTable.containsKey(specialEntry);
    }

    /**
     * Updates the reuse hash tables with the new instance that was reused.
     * @param	 reusedInstance	The instance of the external model
     * @param	targetInstance  The instance of the current model, which was added
     */
    public void reuseTablesUpdate(Instance reusedInstance, Instance targetInstance) {
        reusedInstancesTable.put(reusedInstance, targetInstance);
        reusedEntriesTable.put(reusedInstance.getEntry(), targetInstance
                .getEntry());
        reusedInstancesGlobalList.add(targetInstance);
    }

    /**
     * Returns whether a given OPD was reused until now.
     */
    protected boolean isOpdReused(Opd aOpd) {
        if (aOpd == null)
            return true;
        return reusedOpdList.contains(aOpd);
    }

}

