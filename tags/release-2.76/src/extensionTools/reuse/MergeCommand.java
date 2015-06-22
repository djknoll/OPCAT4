package extensionTools.reuse;

import exportedAPI.util.OpcatApiException;
import gui.Opcat2;
import gui.checkModule.CheckPrecedence;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.projectStructure.ConnectionEdgeInstance;
import gui.projectStructure.FundamentalRelationInstance;
import gui.projectStructure.GeneralRelationInstance;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.RelationEntry;
import gui.projectStructure.StateInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatException;
import gui.util.OpcatLogger;

import java.awt.Color;
import java.util.Enumeration;
import java.util.LinkedList;

/**
 * Manages the merge operation - taking two elements and merging them together.
 * TODO: When dragging things with an inzoom into targets without, then things
 * that were within the container are placed outside the container 
 *  TODO: When dragging things into unfolded things,
 * then the name of the main entity in the target does not change
 */
public class MergeCommand {
    OpdProject currentProject;

    OpdProject reusedProject = null;

    ReuseCommand reuseCommand;

    public LinkedList mergedOpds;

    public final static int MERGE_THINGS = 1;

    public final static int MERGE_OPD = 2;

    public final static int MERGE_SYSTEM = 3;

   /**
     * Constructor for the merge operation. Sets the current project parameter. 
     * Does not carry any action. 
     */
    public MergeCommand(OpdProject _currentProject) {
        currentProject = _currentProject;
        // creating a reuseCommand instance so we can use its reuse functions
        reuseCommand = new ReuseCommand(currentProject, currentProject,
                ReuseCommand.NO_REUSE);
    }


    /**
     * Performs the merge action between two things.
     * 
     * @param draggedInstance
     *            The thing which is going to be absorbed into the target, and
     *            is going to be deleted
     * @param targetInstance
     *            The thing which is going to remain after the merge
     * @throws OpcatApiException	Thrown when the dragged thing has inzooming or 
     * unfolding levels
     */
    public boolean commitMerge(ThingInstance draggedInstance, ThingInstance targetInstance) 
    throws OpcatApiException
    {
        if (!isInstancesTupleOfObjectsOrProcesses(draggedInstance, targetInstance))	{
            return false;
        }
        // Handling Zooming in
        ThingEntry draggedEntry = (ThingEntry) draggedInstance.getEntry();
        if (draggedEntry.getZoomedInOpd() != null) {
            throw new OpcatApiException("Things with inzooming levels cannot be merged");
            //mergeZoominLevels(draggedInstance, targetInstance);
        }
        //Handling unfolding
        if (draggedInstance.getUnfoldingOpd() != null) {
            throw new OpcatApiException("Things with unfolding levels cannot be merged");
            //mergeUnfoldedLevels(draggedInstance, targetInstance);
        }
        // in case the drag and drop is within objects copy the states
        if (draggedInstance instanceof ObjectInstance) {
            reuseCommand
                    .addReusedStates((ObjectInstance) targetInstance, (ObjectInstance) draggedInstance, currentProject
                            .getCurrentOpd());
        }
        // link and relations copying
        mergeRelations(draggedInstance, targetInstance);

        // deleting source and everything that is connected to it
        cleanUpMerge(draggedInstance);
        
        //Updating the model, and refreshing the OPL
        currentProject.updateChanges();
        currentProject.refresh();
        Opcat2.getUndoManager().discardAllEdits();
        return true;
    }

    /**
     * Handles copying of zoomin levels between the draffed thing and the target thing.
     * @param draggedInstance	source (deleted) thing
     * @param targetInstance	target (survived) thing
     */
    protected void mergeZoominLevels(ThingInstance draggedInstance, ThingInstance targetInstance) {
        ThingEntry sourceEntry = (ThingEntry) draggedInstance.getEntry();
        ThingEntry targetEntry = (ThingEntry) targetInstance.getEntry();
        Opd opdSource = sourceEntry.getZoomedInOpd();	//Used for inner level copying - as the OPD of the dragged thing
        Opd opdTarget = targetEntry.getZoomedInOpd();;	//Used for inner level copying - as the OPD of the target thing

        ThingEntry thEntry = (ThingEntry) opdSource.getMainInstance()
                .getIXEntry();
        thEntry.setZoomedInOpd(null);
        if (opdTarget == null) {
            currentProject.zoomIn(targetInstance);
            opdTarget = targetEntry.getZoomedInOpd();
            // add the main thing
        }
        reusedProject = currentProject;
        try {
            reuseCommand.addReusedOpd(opdSource, opdTarget, null, false);
        } catch (OpcatException e) {
           OpcatLogger.logError(e);
        }
        currentProject.deleteRootOpd(opdSource);
    }
    
    /**
     * Handles copying of unfolded levels between the draffed thing and the target thing.
     * @param draggedInstance	source (deleted) thing
     * @param targetInstance	target (survived) thing
     */
    protected void mergeUnfoldedLevels(ThingInstance draggedInstance, ThingInstance targetInstance) {
        Opd opdSource = draggedInstance.getUnfoldingOpd();
        Opd opdTarget = targetInstance.getUnfoldingOpd();

        targetInstance.setUnfoldingOpd(opdSource);
        draggedInstance.removeUnfoldingOpd();
        ThingInstance oldMainInstance = opdSource.getMainInstance();
        ThingInstance newMainInstance = createMainInstance(draggedInstance, targetInstance, opdSource);
        try	{
            commitMerge(oldMainInstance, newMainInstance);
        }
        catch (OpcatApiException ex)	{}
        currentProject.setCurrentOpd(opdSource.getParentOpd());
//        if (opdTarget == null) {
//            opdTarget = currentProject
//                    .createNewOpd(false, (Instance) targetInstance, currentProject
//                            .getCurrentOpd());
//        }
//        reuseCommand.addReusedOpd(opdSource, opdTarget, null, false);
        //currentProject.deleteRootOpd(opdSource);
    }
    
    private ThingInstance createMainInstance(ThingInstance draggedInstance, ThingInstance targetInstance, Opd opdSource)	{
        ThingInstance added = null;
        currentProject.setCurrentOpd(opdSource);
        if (draggedInstance instanceof ObjectInstance)	{
            added = currentProject.addObjectInstance(draggedInstance.getEntry().getName(), targetInstance.getX(), targetInstance.getY(), (ThingEntry)draggedInstance.getEntry());
        }
        else	{
            added = currentProject.addProcessInstance(draggedInstance.getEntry().getName(), targetInstance.getX(), targetInstance.getY(), (ThingEntry)draggedInstance.getEntry());
        }
        return added;
    }
    
    
    protected void mergeRelations(ThingInstance draggedInstance, ThingInstance targetInstance)	{
//      link and relations copying
        ThingEntry draggedEntry = (ThingEntry) draggedInstance.getEntry();
        ThingEntry targetEntry = (ThingEntry) targetInstance.getEntry();
        LinkInstance linkInstance;
        LinkEntry linkEntry;
        Instance tmpImstance, in2;
        GeneralRelationInstance g_relationInstance = null;
        FundamentalRelationInstance f_relationInstance = null;
        RelationEntry rEntry;
        CheckPrecedence check = new CheckPrecedence();

        Enumeration relatedInstances = draggedInstance.getRelatedInstances();
        while (relatedInstances.hasMoreElements()) {
            tmpImstance = (Instance) relatedInstances.nextElement();
            // link section
            ///////////////
            if (tmpImstance instanceof LinkInstance) {
                linkInstance = (LinkInstance) tmpImstance;
                linkEntry = (LinkEntry) linkInstance.getIXEntry();
                boolean addLink = true;
                // cases - if the sourc and target of this link are the dragged
                // and dropped
                // elements remove it !!!!
                if (((linkInstance.getSource().getEntry().getId() == draggedEntry
                        .getId()) && (linkInstance.getDestination().getEntry()
                        .getId() == targetEntry.getId()))
                        || ((linkInstance.getSource().getEntry().getId() == targetEntry
                                .getId()) && (linkInstance.getDestination()
                                .getEntry().getId() == draggedEntry.getId()))) {
                    currentProject.deleteLink(linkInstance);
                    addLink = false;
                    continue;
                }
                ThingInstance otherSideInstance;
                if (linkInstance.getSource().getEntry().getId() == draggedEntry
                        .getId())
                    otherSideInstance = (ThingInstance) linkInstance
                            .getDestinationInstance();
                else
                    otherSideInstance = (ThingInstance) linkInstance
                            .getSourceIXInstance();

                Enumeration e = otherSideInstance.getRelatedInstances();
                while (e.hasMoreElements()) {
                    Instance ele = (Instance) e.nextElement();
                    if (ele instanceof LinkInstance) {
                        LinkInstance lIns = (LinkInstance) ele;
                        if (lIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || lIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(lIns, linkInstance) == lIns) {
                                currentProject.deleteLink(linkInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteLink(lIns);
                        }

                    }
                    if (ele instanceof GeneralRelationInstance) {
                        GeneralRelationInstance gIns = (GeneralRelationInstance) ele;
                        if (gIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || gIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(gIns, linkInstance)
                                    .getKey() == gIns.getKey()) {
                                currentProject.deleteLink(linkInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteGeneralRelation(gIns);
                        }

                    }

                    if (ele instanceof FundamentalRelationInstance) {
                        FundamentalRelationInstance fIns = (FundamentalRelationInstance) ele;
                        if (fIns.getSourceInstance().getEntry().getId() == targetEntry
                                .getId()
                                || fIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(fIns, linkInstance)
                                    .getKey() == fIns.getKey()) {
                                currentProject.deleteLink(linkInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteFundamentalRelation(fIns);
                        }
                    }
                }
                if (linkInstance.getSource().getEntry().getId() == draggedEntry
                        .getId())
                    currentProject
                            .addLink((ConnectionEdgeInstance) targetInstance, (ConnectionEdgeInstance) linkInstance
                                    .getDestinationInstance(), linkEntry
                                    .getLinkType());
                else
                    currentProject
                            .addLink((ConnectionEdgeInstance) linkInstance
                                    .getSourceIInstance(), (ConnectionEdgeInstance) targetInstance, linkEntry
                                    .getLinkType());
                currentProject.deleteLink(linkInstance);

                // end if link section
            }

            // general relation section
            ///////////////////////////
            if (tmpImstance instanceof GeneralRelationInstance) {
                g_relationInstance = (GeneralRelationInstance) tmpImstance;
                rEntry = (RelationEntry) g_relationInstance.getIXEntry();
                boolean addLink = true;
                // cases - if the sourc and target of this relation are the
                // dragged and dropped
                // elements remove it !!!!
                if (((g_relationInstance.getSource().getEntry().getId() == draggedEntry
                        .getId()) && (g_relationInstance.getDestination()
                        .getEntry().getId() == targetEntry.getId()))
                        || ((g_relationInstance.getSource().getEntry().getId() == targetEntry
                                .getId()) && (g_relationInstance
                                .getDestination().getEntry().getId() == draggedEntry
                                .getId()))) {
                    currentProject.deleteGeneralRelation(g_relationInstance);
                    addLink = false;
                    continue;
                }
                ThingInstance otherSideInstance;
                if (g_relationInstance.getSource().getEntry().getId() == draggedEntry
                        .getId())
                    otherSideInstance = (ThingInstance) g_relationInstance
                            .getDestinationInstance();
                else
                    otherSideInstance = (ThingInstance) g_relationInstance
                            .getSourceIXInstance();

                Enumeration e = otherSideInstance.getRelatedInstances();
                while (e.hasMoreElements()) {
                    Instance ele = (Instance) e.nextElement();
                    if (ele instanceof LinkInstance) {
                        LinkInstance lIns = (LinkInstance) ele;
                        if (lIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || lIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(lIns, g_relationInstance) == lIns) {
                                currentProject
                                        .deleteGeneralRelation(g_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteLink(lIns);
                        }
                    }
                    if (ele instanceof GeneralRelationInstance) {
                        GeneralRelationInstance gIns = (GeneralRelationInstance) ele;
                        if (gIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || gIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(gIns, g_relationInstance)
                                    .getKey() == gIns.getKey()) {
                                currentProject
                                        .deleteGeneralRelation(g_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteGeneralRelation(gIns);
                        }
                    }
                    if (ele instanceof FundamentalRelationInstance) {
                        FundamentalRelationInstance fIns = (FundamentalRelationInstance) ele;
                        if (fIns.getSourceInstance().getEntry().getId() == targetEntry
                                .getId()
                                || fIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(fIns, g_relationInstance)
                                    .getKey() == fIns.getKey()) {
                                currentProject
                                        .deleteGeneralRelation(g_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteFundamentalRelation(fIns);
                        }
                    }
                }
                if (g_relationInstance.getSource().getEntry().getId() == draggedEntry
                        .getId())
                    currentProject
                            .addRelation((ConnectionEdgeInstance) targetInstance, (ConnectionEdgeInstance) g_relationInstance
                                    .getDestinationInstance(), rEntry
                                    .getRelationType());
                else
                    currentProject
                            .addLink((ConnectionEdgeInstance) targetInstance, (ConnectionEdgeInstance) g_relationInstance
                                    .getDestinationInstance(), rEntry
                                    .getRelationType());
                currentProject
                        .deleteGeneralRelation((GeneralRelationInstance) tmpImstance);

                // end of general relation section
            }

            if (tmpImstance instanceof FundamentalRelationInstance) {

                f_relationInstance = (FundamentalRelationInstance) tmpImstance;
                rEntry = (RelationEntry) f_relationInstance.getIXEntry();
                boolean addLink = true;
                // cases - if the sourc and target of this link are the dragged
                // and dropped
                // elements remove it !!!!
                if (((f_relationInstance.getSourceInstance().getEntry().getId() == draggedEntry
                        .getId()) && (f_relationInstance.getDestination()
                        .getEntry().getId() == targetEntry.getId()))
                        || ((f_relationInstance.getSourceInstance().getEntry()
                                .getId() == targetEntry.getId()) && (f_relationInstance
                                .getDestination().getEntry().getId() == draggedEntry
                                .getId()))) {
                    currentProject
                            .deleteFundamentalRelation(f_relationInstance);
                    addLink = false;
                    continue;
                }
                ThingInstance otherSideInstance;
                if (f_relationInstance.getSourceInstance().getEntry().getId() == draggedEntry
                        .getId())
                    otherSideInstance = (ThingInstance) f_relationInstance
                            .getDestinationInstance();
                else
                    otherSideInstance = (ThingInstance) f_relationInstance
                            .getSourceIXInstance();

                Enumeration e = otherSideInstance.getRelatedInstances();
                while (e.hasMoreElements()) {
                    Instance ele = (Instance) e.nextElement();
                    if (ele instanceof LinkInstance) {
                        LinkInstance lIns = (LinkInstance) ele;
                        if (lIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || lIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(lIns, f_relationInstance) == lIns) {
                                currentProject
                                        .deleteFundamentalRelation(f_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteLink(lIns);
                        }

                    }
                    if (ele instanceof GeneralRelationInstance) {
                        GeneralRelationInstance gIns = (GeneralRelationInstance) ele;
                        if (gIns.getSource().getEntry().getId() == targetEntry
                                .getId()
                                || gIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(gIns, f_relationInstance)
                                    .getKey() == gIns.getKey()) {
                                currentProject
                                        .deleteFundamentalRelation(f_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject.deleteGeneralRelation(gIns);
                        }

                    }

                    if (ele instanceof FundamentalRelationInstance) {
                        FundamentalRelationInstance fIns = (FundamentalRelationInstance) ele;
                        if (fIns.getSourceInstance().getEntry().getId() == targetEntry
                                .getId()
                                || fIns.getDestination().getEntry().getId() == targetEntry
                                        .getId()) {
                            if (check
                                    .checkPredenceBetweenInstances(fIns, f_relationInstance)
                                    .getKey() == fIns.getKey()) {
                                currentProject
                                        .deleteFundamentalRelation(f_relationInstance);
                                addLink = false;
                                continue;
                            } else
                                currentProject
                                        .deleteFundamentalRelation((FundamentalRelationInstance) tmpImstance);
                        }
                    }
                }
                if (f_relationInstance.getSourceInstance().getEntry().getId() == draggedEntry
                        .getId())
                    currentProject
                            .addRelation((ConnectionEdgeInstance) targetInstance, (ConnectionEdgeInstance) f_relationInstance
                                    .getDestinationInstance(), rEntry
                                    .getRelationType());
                else
                    currentProject
                            .addRelation((ConnectionEdgeInstance) f_relationInstance
                                    .getSourceInstance(), (ConnectionEdgeInstance) targetInstance, rEntry
                                    .getRelationType());
                currentProject.deleteFundamentalRelation(f_relationInstance);
                // end if fundamental relation section
            }
        }
    }
    /**
     * Cleans up the model from the dragged thing instances. 
     * @param draggedInstance
     */
    protected void cleanUpMerge(ThingInstance draggedInstance)	{
        ThingEntry deletedDraggedEntry = (ThingEntry) draggedInstance.getIXEntry();
        if (deletedDraggedEntry.getZoomedInOpd() != null)
            currentProject.deleteRootOpd(deletedDraggedEntry.getZoomedInOpd());
        //openReusedInstanceList.remove(dragged);
        if (draggedInstance instanceof ObjectInstance) {
            currentProject.deleteObject((ObjectInstance) draggedInstance);
        } else {
            currentProject.deleteProcess((ProcessInstance) draggedInstance);
        }
    }
    
    /**
     * checking that the drag and drop action is between a tuple of the same
     * type (of objects or processes)
     * 
     * @return <code>true</code> if both the instances are of the same type,
     *         <code>false</code> otherwise
     */
    public boolean isInstancesTupleOfObjectsOrProcesses(Instance aInstance, Instance bInstance) {
        return (((aInstance instanceof ObjectInstance) && (bInstance instanceof ObjectInstance)) || ((aInstance instanceof ProcessInstance) && (bInstance instanceof ProcessInstance)));
    }
    

    public boolean mergeOpd(Opd opdToMerge) {
        if (mergedOpds.contains(opdToMerge))
            return true;
        mergedOpds.add(opdToMerge);
        Enumeration e = currentProject.getSystemStructure()
                .getInstancesInOpd(opdToMerge);

        while (e.hasMoreElements()) {
            Instance a = (Instance) e.nextElement();
            a.getEntry().setOpenReused(false);
            a.getEntry().releaseLock();
            if (a instanceof ObjectInstance)
                a.setBorderColor((Color) currentProject.getConfiguration()
                        .getProperty("ObjectColor"));
            else if (a instanceof ProcessInstance)
                a.setBorderColor((Color) currentProject.getConfiguration()
                        .getProperty("ProcessColor"));
            else if (a instanceof StateInstance)
                a.setBorderColor((Color) currentProject.getConfiguration()
                        .getProperty("StateColor"));
            else
                a.setBorderColor(Color.black);
            a.update();
            if (a instanceof ThingInstance) {
                Instance target = currentProject.getCurrentOpd()
                        .getOpenReuseTargetInheritedInstance(a);
                if (target != null)
                    try {
                        commitMerge((ThingInstance) a, (ThingInstance) target);
                    } catch (OpcatApiException e1) {}
                ThingInstance thi = (ThingInstance) a;
                if (thi.isZoomedIn()) {
                    ThingEntry te = (ThingEntry) thi.getEntry();
                    if (te.getZoomedInOpd() != null)
                        mergeOpd(te.getZoomedInOpd());
                }
            }

        }
        opdToMerge.setOpenReused(false);
        opdToMerge.setReusedSystemPath(null);
        return true;
    }

    /** ********************************************************************* */
    /* function: mergeProject */
    /** ********************************************************************* */
    public boolean mergeProject(OpdProject curr) {
        Enumeration e = curr.getSystemStructure().getOpds();
        while (e.hasMoreElements()) {
            Opd aOpd = (Opd) e.nextElement();
            if (aOpd.isOpenReused())
                mergeOpd(aOpd);
        }
        return true;
    }
}