package gui.projectStructure;

import exportedAPI.opcatAPI.IObjectEntry;
import exportedAPI.opcatAPIx.IXObjectEntry;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmObject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmState;

import java.util.*;

/**
 * <p/>
 * This class represents entry of OPM object. In this class we additionally hold
 * information about all states belong to this object.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class ObjectEntry extends ThingEntry implements IObjectEntry,
        IXObjectEntry {

    public static final String minResExeptionStateName = "Min Exception State";
    public static final String maxResExeptionStateName = "Max Exception State";

    /**
     * Creates ObjectEntry that holds all information about specified pObject.
     *
     * @param pObject object of OpmObject class.
     */

    public ObjectEntry(OpmObject pObject, OpdProject project) {
        super(pObject, project);
    }

    public ObjectEntry(OpmObject pObject, ThingEntry parentThing,
                       OpdProject project) {
        super(pObject, parentThing, project);
    }


    /**
     * Returns Enumeration of StateEntry which contains all states belonging to
     * this object. Use the Enumeration methods on the returned object to fetch
     * the StateEntry sequentially
     */

    public Enumeration<StateEntry> getStates() {
        Vector<StateEntry> tempVector = new Vector<StateEntry>();
        MainStructure pStructure = this.myProject.getComponentsStructure();

        for (Enumeration e = this.getStateEnum(); e.hasMoreElements();) {
            OpmState tState = (OpmState) e.nextElement();
            tempVector.add((StateEntry) pStructure.getEntry(tState.getId()));
        }

        return tempVector.elements();
    }


    /**
     * Returns true if this OpmObject is persistent.
     */

    public boolean isPersistent() {
        return ((OpmObject) this.logicalEntity).isPersistent();
    }

    /**
     * Sets the persistent property of this OpmObject.
     */

    public void setPersistent(boolean persistent) {
        ((OpmObject) this.logicalEntity).setPersistent(persistent);
    }

    /**
     * Returns NAME representing type of this OpmObject
     */
    public String getType() {
        return ((OpmObject) this.logicalEntity).getType();
    }

    /**
     * Sets type of this OpmObject.
     */

    public void setType(String type) {
        ((OpmObject) this.logicalEntity).setType(type);
    }

    /**
     * Returns true if this OpmObject is key.
     */

    public boolean isKey() {
        return ((OpmObject) this.logicalEntity).isKey();
    }

    /**
     * Sets key property of this OpmObject.
     */

    public void setKey(boolean key) {
        ((OpmObject) this.logicalEntity).setKey(key);
    }

    /**
     * Returns index name of this OpmObject.
     */
    public String getIndexName() {
        return ((OpmObject) this.logicalEntity).getIndexName();
    }

    /**
     * Sets index name of if this OpmObject.
     */

    public void setIndexName(String indexName) {
        ((OpmObject) this.logicalEntity).setIndexName(indexName);
    }

    /**
     * Returns index order of if this OpmObject.
     */

    public int getIndexOrder() {
        return ((OpmObject) this.logicalEntity).getIndexOrder();
    }

    /**
     * Sets index order of if this OpmObject.
     */
    public void setIndexOrder(int indexOrder) {
        ((OpmObject) this.logicalEntity).setIndexOrder(indexOrder);
    }

    /**
     * Returns initial value of if this OpmObject.
     */
    public String getInitialValue() {
        return ((OpmObject) this.logicalEntity).getInitialValue();
    }

    /**
     * Sets initial value of if this OpmObject.
     */

    public void setInitialValue(String initialValue) {
        ((OpmObject) this.logicalEntity).setInitialValue(initialValue);
    }

    public void setTypeOriginId(long typeOriginId) {
        ((OpmObject) this.logicalEntity).setTypeOriginId(typeOriginId);
    }

    public long getTypeOriginId() {
        return ((OpmObject) this.logicalEntity).getTypeOriginId();
    }

    public String toString() {
        return this.getName().replaceAll("\n", " ");
    }

    public boolean isStateEntryExists(String stateName) {

        Iterator iter = Collections.list(this.getStates()).iterator();
        while (iter.hasNext()) {
            StateEntry state = (StateEntry) iter.next();
            if (state.getName().equalsIgnoreCase(stateName)) {
                return true;
            }
        }

        return false;
    }

    public StateEntry getState(String stateName) {

        Iterator iter = Collections.list(this.getStates()).iterator();
        while (iter.hasNext()) {
            StateEntry state = (StateEntry) iter.next();
            if (state.getName().equalsIgnoreCase(stateName)) {
                return state;
            }
        }

        return null;
    }

    public boolean canBeGeneric() {
        boolean ret = super.canBeGeneric();
        // check if anyinstance has unfolded
        Iterator iter = Collections.list(this.getInstances(false)).iterator();
        while (iter.hasNext() && ret == true) {
            ObjectInstance ins = (ObjectInstance) iter.next();
            if (ins.getUnfoldingOpd() != null) {
                ret = false;
            }
        }
        return ret;
    }

    /**
     * Returns an enumeration of the OpmProceduralLink in which this
     * ConnectionEdgeEntry or his States paricipates as source. Use the
     * Enumeration methods on the returned object to fetch the OpmProceduralLink
     * sequentially
     */
    public Enumeration<OpmProceduralLink> getSourceLinks(boolean includeStates) {
        Vector<OpmProceduralLink> links = new Vector<OpmProceduralLink>();
        Enumeration<OpmProceduralLink> linksEnum = getSourceLinks();
        while (linksEnum.hasMoreElements()) {
            OpmProceduralLink opm = linksEnum.nextElement();
            links.add(opm);
        }

        Enumeration<StateEntry> states = this.getStates();
        while (states.hasMoreElements()) {
            StateEntry state = states.nextElement();
            linksEnum = state.getSourceLinks();
            while (linksEnum.hasMoreElements()) {
                OpmProceduralLink opm = linksEnum.nextElement();
                links.add(opm);
            }
        }

        return links.elements();
    }

    /**
     * Returns an enumeration of the OpmProceduralLink in which this
     * ConnectionEdgeEntry or his States paricipates as destination. Use the
     * Enumeration methods on the returned object to fetch the OpmProceduralLink
     * sequentially
     */
    public Enumeration<OpmProceduralLink> getDestinationLinks(
            boolean includeStates) {
        Vector<OpmProceduralLink> links = new Vector<OpmProceduralLink>();
        Enumeration<OpmProceduralLink> linksEnum = getDestinationLinks();
        while (linksEnum.hasMoreElements()) {
            OpmProceduralLink opm = linksEnum.nextElement();
            links.add(opm);
        }

        Enumeration<StateEntry> states = this.getStates();
        while (states.hasMoreElements()) {
            StateEntry state = states.nextElement();
            linksEnum = state.getDestinationLinks();
            while (linksEnum.hasMoreElements()) {
                OpmProceduralLink opm = linksEnum.nextElement();
                links.add(opm);
            }
        }

        return links.elements();
    }

    public StateEntry chooseState() {

        Double rand = (new Random()).nextDouble() ;

        Enumeration<StateEntry> states = getStates();

        if (!(states.hasMoreElements())) {
            return null;
        }

        //check if any exeption state is active ;
        //if yes return it
        StateEntry exState = getState(ObjectEntry.minResExeptionStateName);
        if (exState != null) {
            if (((OpmObject) getLogicalEntity()).getMesurementUnitCurrentValue() <=  ((OpmObject) getLogicalEntity()).getMesurementUnitMinValue()) {
                return exState;
            }
        }

        exState = getState(ObjectEntry.maxResExeptionStateName);
        if (exState != null) {
            if (((OpmObject) getLogicalEntity()).getMesurementUnitCurrentValue() >=  ((OpmObject) getLogicalEntity()).getMesurementUnitMaxValue()) {
                return exState;
            }
        }


        //we don't work on the exeption states
        HashMap<String, Double> probs = new HashMap<String, Double>();
        //fill the map
        while (states.hasMoreElements()) {
            StateEntry state = states.nextElement();
            if (!(state.getName().equalsIgnoreCase(ObjectEntry.maxResExeptionStateName))) {
                if (!(state.getName().equalsIgnoreCase(ObjectEntry.minResExeptionStateName))) {
                    probs.put(state.getName(), -1.0);
                }
            }
        }

        //check for initial state
        for (String name : probs.keySet()) {
            StateEntry state = getState(name);
            if (state.isInitial()) {
                return state;
            }
        }

        //sort by name
        Object[] key = probs.keySet().toArray();
        Arrays.sort(key);

        //fill map again ordered
        probs = new HashMap<String, Double>();
        for (int i = 0; i < key.length; i++) {
            probs.put(key[i].toString(), -1.0);
        }


        int i = 0;
        double acc = 0;
        for (String name : probs.keySet()) {
            StateEntry state = getState(name);
            if (state.getCreationProb() >= 0) {
                acc = acc + state.getCreationProb();
                probs.put(state.getName(), acc);
            } else {
                if (!(state.getName().equalsIgnoreCase(ObjectEntry.maxResExeptionStateName))) {
                    if (!(state.getName().equalsIgnoreCase(ObjectEntry.minResExeptionStateName))) {
                        i++;
                        probs.put(state.getName(), -1.0);
                    }
                }
            }
        }

        //add non existent as rqual prob of rest
        double evenAcc = acc;
        if (i > 0) {
            for (String id : probs.keySet()) {
                if (probs.get(id) < 0) {

                    evenAcc = evenAcc + +((1.0 - acc) / i);
                    probs.put(id, evenAcc);
                }
            }
        }

        //sort probs by value
        //probs = (HashMap<String, Double>) MapUtils.sortByValue(probs);

        StateEntry ret = getState(probs.keySet().iterator().next());

        //choose state
        for (String id : probs.keySet()) {
            if (rand < probs.get(id)) {
                ret = getState(id);
                break;
            }
        }

        return ret;
    }

    // public HashMap getConnectedProcesses() {
    //
    // ArrayList entries = new ArrayList();
    // HashMap ret = new HashMap();
    //
    // entries.add(this);
    // entries.addAll(Collections.list(this.getStates()));
    //
    // Iterator entriesIter = entries.iterator();
    // while (entriesIter.hasNext()) {
    //
    // Entry entry = (Entry) entriesIter.next();
    // Iterator destIter = null ;
    // Iterator srcIter = null ;
    //
    // if (entry instanceof StateEntry) {
    // destIter = Collections.list(
    // ((StateEntry) entry).getDestinationLinks()).iterator();
    // srcIter = Collections.list(((StateEntry) entry).getSourceLinks())
    // .iterator();
    //
    // } else {
    // destIter = Collections.list(
    // ((ObjectEntry) entry).getDestinationLinks()).iterator();
    // srcIter = Collections.list(((ObjectEntry) entry).getSourceLinks())
    // .iterator();
    // }
    //
    // while (destIter.hasNext()) {
    // Object obj = destIter.next();
    // if (obj instanceof OpmProceduralLink) {
    // OpmProceduralLink link = (OpmProceduralLink) obj;
    // Entry ent = (Entry) Opcat2.getCurrentProject()
    // .getSystemStructure().getEntry(link.getSourceId());
    // if (ent instanceof ProcessEntry) {
    // ProcessEntry process = (ProcessEntry) ent;
    // if (!ret.containsKey(new Long(process.getId()))) {
    // ret.put(new Long(process.getId()), process);
    // }
    // }
    // }
    // }
    //
    // while (srcIter.hasNext()) {
    // Object obj = srcIter.next();
    // if (obj instanceof OpmProceduralLink) {
    // OpmProceduralLink link = (OpmProceduralLink) obj;
    // Entry ent = (Entry) Opcat2.getCurrentProject()
    // .getSystemStructure().getEntry(
    // link.getDestinationId());
    // if (ent instanceof ProcessEntry) {
    // ProcessEntry process = (ProcessEntry) ent;
    // if (!ret.containsKey(new Long(process.getId()))) {
    // ret.put(new Long(process.getId()), process);
    // }
    // }
    // }
    // }
    // }
    //
    // return ret;
    // }
    public String getTypeString() {
        return "Object";
    }
}
