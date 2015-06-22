package gui.projectStructure;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.ISystemStructure;
import exportedAPI.opcatAPIx.IXEntry;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXSystemStructure;
import gui.controls.EditControl;
import gui.controls.GuiControl;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmStructuralRelation;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.*;

/**
 * <p/>
 * This class actually is a data structure that contains all information about
 * all entities existing in user's project. Each Entry in this data structure
 * contains some entity and in this Entry we hold all logical information about
 * this entity and information about all of its graphical representations.
 * <p/>
 * This Class also contains information about all OPDs existing in user's
 * project.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class MainStructure implements ISystemStructure, IXSystemStructure {
    private Hashtable<Long, Entry> mStructure;

    private Hashtable<Long, String> mStructureFixedNames;

    private Hashtable<Long, Opd> opdsHash;

    private Hashtable<OpdKey, Instance> instances = new Hashtable<OpdKey, Instance>();

    private Hashtable<Long, Vector<Instance>> opdInstances = new Hashtable<Long, Vector<Instance>>();

    private Hashtable<GraphicRepresentationKey, GraphicalRelationRepresentation> graphicalRepresentations;

    private DefaultTreeModel opdTree;

    private DefaultMutableTreeNode rootNode;

    private HashMap<Instance, ArrayList<Instance>> included = new HashMap<Instance, ArrayList<Instance>>();


    /**
     * Creates a MainStructure class and initializes its internal data
     * structures.
     */

    public MainStructure() {
        this.mStructure = new Hashtable<Long, Entry>();
        this.mStructureFixedNames = new Hashtable<Long, String>();
        this.opdsHash = new Hashtable<Long, Opd>();
        this.graphicalRepresentations = new Hashtable();
    }

    /**
     * Maps the specified key to the specified Entry in this MainStructure.
     * Neither the key nor the Entry can be null. The Entry can be retrieved by
     * calling the getElement method with a key that is equal to the original
     * key.
     *
     * @param pKey   entity id of entity which represented by Entry
     * @param pEntry Entry which contains information about some entity in user's
     *               project
     * @return the previous value of the specified key in this MainStruscure, or
     *         null if it did not have one.
     */

    public Entry put(long pKey, Entry pEntry) {
        Enumeration<Instance> instances = pEntry.getInstances(false);
        if (instances != null) {
            while (instances.hasMoreElements()) {
                put(instances.nextElement());
            }
        }

        this.mStructureFixedNames.put(new Long(pKey), fixName(pEntry.getName()));
        return (Entry) (this.mStructure.put(new Long(pKey), pEntry));

    }

    public static String fixName(String str) {
        return str.replaceAll(" *\n *", " ").trim();
    }

    public Iterator<Instance> getInstances() {
        return instances.values().iterator();
    }

    public ArrayList<ThingInstance> getAllThingInstances() {

        ArrayList<ThingInstance> things = new ArrayList<ThingInstance>();

        for (Instance ins : instances.values()) {
            if (ins instanceof ThingInstance) {
                things.add((ThingInstance) ins);
            }
        }

        return things;
    }

    public ArrayList<ThingEntry> getThingEntries() {

        ArrayList<ThingEntry> things = new ArrayList<ThingEntry>();

        Enumeration<Entry> entries = getElements();
        while (entries.hasMoreElements()) {
            Entry ent = entries.nextElement();
            if (ent instanceof ThingEntry) {
                things.add((ThingEntry) ent);
            }
        }

        return things;
    }

    public void put(Instance ins) {


        if (ins.getOpd() == null) return;
        if (instances.get(ins.getKey()) != null) return;
        instances.put(ins.getKey(), ins);
        if (opdInstances.get(ins.getOpd().getOpdId()) != null) {
            opdInstances.get(ins.getKey().getOpdId()).add(ins);
        } else {
            opdInstances.put(ins.getOpd().getOpdId(), new Vector<Instance>());
            opdInstances.get(ins.getKey().getOpdId()).add(ins);
        }
        updateParent(ins);

    }

    public void updateParent(Instance ins) {
        if (ins == null) return;
        if (ins.getParent() != null) {
            if (ins.getParent().getInstance() != null) {
                if (!included.containsKey(ins.getParent().getInstance())) {
                    included.get(ins.getParent().getInstance()).add(ins);
                } else {
                    ArrayList<Instance> instances = new ArrayList<Instance>();
                    included.put(ins.getParent().getInstance(), instances);
                }
            }
        }
    }


    public DefaultMutableTreeNode find(Opd opd) {

        Enumeration<DefaultMutableTreeNode> nodes = rootNode.breadthFirstEnumeration();
        DefaultMutableTreeNode ret = null;
        while (nodes.hasMoreElements()) {
            DefaultMutableTreeNode node = nodes.nextElement();
            Opd tempOpd = (Opd) node.getUserObject();
            if (opd.getOpdId() == tempOpd.getOpdId()) {
                ret = node;
                break;
            }
        }

        return ret;
    }

    public DefaultTreeModel getOpdTree() {
        return opdTree;
    }

    public String getOPDTextPath(String header, Opd opd) {

        DefaultMutableTreeNode node = find(opd);

        if (node == null) return "View";
        TreeNode[] path = node.getPath();

        String ret = null;
        /**
         * A small patch as our tree starts from position 3  (root = 0 ,project = 1 ,RootSD = 2 )
         */
        int start = 2;
        for (int i = start; i < path.length; i++) {

            int idx = 0;

            if (i == start) {
                ret = header;
            } else {
                idx = path[i - 1].getIndex(path[i]) + 1;
                if (i == start + 1) {
                    ret = ret + idx;
                } else {
                    ret = ret + "." + idx;
                }
            }

        }
        return ret;

    }


    public void refreshOPDTree() {
        opdTree = new DefaultTreeModel(rootNode);


    }

    /**
     * Maps the specified key to the specified Opd in this MainStructure.
     * Neither the key nor the Opd can be null. The Opd can be retrieved by
     * calling the getOpd method with a key that is equal to the original key.
     *
     * @param pKey opd id
     * @param pOpd instance of OPD class that represents some OPD in user's
     *             project
     * @return the previous value of the specified key in this MainStruscure, or
     *         null if it did not have one.
     */

    public Opd put(long pKey, Opd pOpd, int idxInLevel) {
        Opd parent = pOpd.getParentOpd();

        DefaultMutableTreeNode node;
        if ((pOpd.getParentOpd() == null) || (pOpd.getOpdId() == 1)) {
            node = new DefaultMutableTreeNode(pOpd);
            opdTree = new DefaultTreeModel(node);
            rootNode = node;
        } else {
            Enumeration<DefaultMutableTreeNode> nodes = rootNode.breadthFirstEnumeration();
            DefaultMutableTreeNode parentNode = null;
            while (nodes.hasMoreElements()) {
                DefaultMutableTreeNode tempNode = nodes.nextElement();
                Opd opd = (Opd) tempNode.getUserObject();
                if (opd.getOpdId() == pOpd.getParentOpd().getOpdId()) {
                    parentNode = tempNode;
                    break;
                }
            }

            node = new DefaultMutableTreeNode(pOpd);
            if (pOpd.isView()) {
                parentNode.insert(node, parentNode.getChildCount());
            } else {
                parentNode.insert(node, 0);
            }
        }

//        if (parent != null) {
//            pOpd.getProperties().setProperty("opd.general.tree.level", NAME.valueOf(NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.level")) + 1)));
//            pOpd.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(0));
//            pOpd.getProperties().setProperty("opd.general.tree.numinlevel", NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.childrencount")) + 1));
//            parent.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.childrencount")) + 1));
//        } else {
//            /**
//             * only one root SD in opcat model
//             */
//            pOpd.getProperties().setProperty("opd.general.tree.level", NAME.valueOf(1));
//            pOpd.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(0));
//            pOpd.getProperties().setProperty("opd.general.tree.numinlevel", NAME.valueOf(1));
//            //parent.getProperties().setProperty("opd.general.tree.childrencount",NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.childrencount")) + 1 ));
//        }

        opdInstances.put(pKey, new Vector<Instance>());
        return (Opd) (this.opdsHash.put(new Long(pKey), pOpd));

    }

    public Instance getInstance(OpdKey key) {

        return instances.get(key);

        //Enumeration<Entry> entries = getAllElements();
        //while (entries.hasMoreElements()) {
        //    Enumeration<Instance> instances = entries.nextElement()
        //            .getInstances();
        //    while (instances.hasMoreElements()) {
        //        Instance instance = instances.nextElement();
        //        if (key.equals(instance.getKey()))
        //            return instance;
        //    }
        //}
        //return null;
    }

    /**
     * Maps the specified key to the specified
     * GraphicalRelationRepresentationEntry in this MainStructure. Neither the
     * key nor the GraphicalRelationRepresentationEntry can be null.
     *
     * @param pKey            opd id
     * @param gRepresentation GraphicalRelationRepresentationEntry - class representing
     *                        graphically some fundamental strctural relation
     * @return the previous value of the specified key in this MainStruscure, or
     *         null if it did not have one.
     */

    public GraphicalRelationRepresentation put(GraphicRepresentationKey pKey,
                                               GraphicalRelationRepresentation gRepresentation) {
        return (GraphicalRelationRepresentation) (this.graphicalRepresentations
                .put(pKey, gRepresentation));
    }

    /**
     * Returns the OPD to which the specified key is mapped in this
     * MainStructure.
     *
     * @param pKey a key in this MainStructure
     * @return the Opd to which the key is mapped in this MainStructure; null if
     *         the key is not mapped to any Opd in this MainStructure.
     */

    public Opd getOpd(long pKey) {
        if (pKey == -1) {
            return null;
        }

        return (Opd) (this.opdsHash.get(new Long(pKey)));
    }

    public IOpd getIOpd(long pKey) {
        return (IOpd) (this.opdsHash.get(new Long(pKey)));
    }

    public IXOpd getIXOpd(long pKey) {
        return (IXOpd) (this.opdsHash.get(new Long(pKey)));
    }


    public Opd deleteOpd(long pKey) {

//        Opd opd = opdsHash.get(pKey);
//        if (opd != null) {
//            Opd parent = opd.getParentOpd();
//            while (parent != null) {
//                if (parent != null) {
//                    pOpd.getProperties().setProperty("opd.general.tree.level", NAME.valueOf(NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.level")) + 1)));
//                    pOpd.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(0));
//                    pOpd.getProperties().setProperty("opd.general.tree.numinlevel", NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.childrencount")) + 1));
//                    parent.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.tree.childrencount")) + 1));
//                } else {
//                    /**
//                     * only one root SD in opcat model
//                     */
//                    pOpd.getProperties().setProperty("opd.general.tree.level", NAME.valueOf(1));
//                    pOpd.getProperties().setProperty("opd.general.tree.childrencount", NAME.valueOf(0));
//                    pOpd.getProperties().setProperty("opd.general.tree.numinlevel", NAME.valueOf(1));
//                    //parent.getProperties().setProperty("opd.general.tree.childrencount",NAME.valueOf(Integer.valueOf(parent.getProperties().getProperty("opd.general.childrencount")) + 1 ));
//                }
//            }
//        }

        if (opdsHash.get(pKey) != null) {
            DefaultMutableTreeNode remove = find(opdsHash.get(pKey));
            Enumeration<DefaultMutableTreeNode> nodes = rootNode.breadthFirstEnumeration();
            while (nodes.hasMoreElements()) {
                DefaultMutableTreeNode node = nodes.nextElement();
                if (pKey == ((Opd) node.getUserObject()).getOpdId()) {
                    ((DefaultMutableTreeNode) node.getParent()).remove(node);
                    break;
                }
            }
        }

        opdInstances.remove(pKey);
        return (Opd) (this.opdsHash.remove(new Long(pKey)));
    }

    /**
     * Returns the Entry to which the specified key is mapped in this
     * MainStructure.
     *
     * @param pKey a key in this MainStructure
     * @return the Entry to which the key is mapped in this MainStructure; null
     *         if the key is not mapped to any Entry in this MainStructure.
     */

    public Entry getEntry(long pKey) {
        return (Entry) this.mStructure.get(new Long(pKey));
    }

    public ArrayList<Entry> getEntry(String entryName) {

        Enumeration<Entry> entries = getElements();
        ArrayList<Entry> ret = new ArrayList<Entry>();

        while (entries.hasMoreElements()) {
            Entry entry = entries.nextElement();
            if (entry.getName().equalsIgnoreCase(entryName)) {
                ret.add(entry);
            }
        }
        return ret;
    }

    public ArrayList<Entry> getEntryByFixedName(String entryName) {

        ArrayList<Entry> ret = new ArrayList<Entry>();

        //while (entries.hasNext()) {
        //    String entry = entries.next();
        if (mStructureFixedNames.values().contains(entryName)) {
            for (Long fixed : mStructureFixedNames.keySet()) {
                if (mStructureFixedNames.get(fixed).equalsIgnoreCase(entryName)) {
                    Entry ent = getEntry(fixed.longValue());
                    ret.add(ent);
                }
            }
        }

        //    }
        //}
        return ret;
    }

    public IEntry getIEntry(long pKey) {
        return (IEntry) this.mStructure.get(new Long(pKey));
    }

    public IXEntry getIXEntry(long pKey) {
        return (IXEntry) this.mStructure.get(new Long(pKey));
    }

    public boolean removeInstance(OpdKey key) {

        Instance instance = instances.get(key);

        if (instance != null) {
            this.instances.remove(key);
        }

        if ((instance != null) && (opdInstances.get(instance.getKey().getOpdId()) != null)) {
            opdInstances.get(instance.getKey().getOpdId()).remove(instance);
        }

        return true;

    }

    public boolean removeEntry(long pKey) {

        Entry entry = mStructure.get(pKey);
        if (entry != null) {
            Enumeration<Instance> instances = entry.getInstances(false);
            while (instances.hasMoreElements()) {
                removeInstance(instances.nextElement().getKey());
            }
        }

        Object result = this.mStructure.remove(new Long(pKey));

        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean removeGraphicalRelationRepresentation(
            GraphicRepresentationKey pKey) {
        GraphicalRelationRepresentation gRelR = (GraphicalRelationRepresentation) this.graphicalRepresentations
                .get(pKey);

        if (gRelR == null) {
            return false;
        }
        gRelR.removeFromContainer();
        this.graphicalRepresentations.remove(pKey);
        return true;
    }

    /**
     * Returns an enumeration of the Entries representing entities in user's
     * project in this MainStructure. Use the Enumeration methods on the
     * returned object to fetch the Entries sequentially
     *
     * @return an enumeration of the Entries in this MainStructure
     */
    public Enumeration<Entry> getElements() {

        ArrayList<Entry> entries = new ArrayList<Entry>();


        //Iterator<Entry> entIter = Collections.list(mStructure.elements())
        //        .iterator();

        for (Entry ent : mStructure.values()) {
            //Entry ent = (Entry) entIter.next();

            //this gets all instances which are not in clipboard
            Vector<Instance> insVector = ent.getInstancesArray(false);

            //boolean goodEnt = false;
            if (insVector.size() > 0) {
                entries.add(ent);
            }
        }

        return Collections.enumeration(entries);
    }

    public Enumeration<Entry> getAllElements() {
        return mStructure.elements();
    }

    public Iterator<Entry> GetObjectEntries() {

        ArrayList<Entry> objects = new ArrayList<Entry>();

        Iterator<Entry> iter = Collections.list(getElements()).iterator();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            if (ent instanceof ObjectEntry)
                objects.add(ent);
        }

        return objects.iterator();
    }

    public Iterator<Entry> GetProcessEntries() {

        ArrayList<Entry> processes = new ArrayList<Entry>();

        Iterator<Entry> iter = Collections.list(getElements()).iterator();
        while (iter.hasNext()) {
            Entry ent = (Entry) iter.next();
            if (ent instanceof ProcessEntry)
                processes.add(ent);
        }

        return processes.iterator();
    }

    /**
     * Returns an enumeration of the OPDs in this MainStructure. Use the
     * Enumeration methods on the returned object to fetch the OPDs sequentially
     * will not return the CLIPBOARD OPD
     *
     * @return an enumeration of the OPDs in this MainStructure
     */
    public Enumeration<Opd> getOpds() {

        ArrayList<Opd> opds = new ArrayList<Opd>();

        GuiControl gui = GuiControl.getInstance();
        Opd clipBoard = null;
        if (gui != null) {
            clipBoard = gui.getClipBoard();
        }
        // if cut is pending all opd's which are
        // inzoom or unfold of entries in the clipboard should not be passed

        Iterator opdIter = Collections.list(opdsHash.elements()).iterator();
        while (opdIter.hasNext()) {
            boolean remove = false;
            Opd opd = (Opd) opdIter.next();
            if (opd.getOpdId() == OpdProject.CLIPBOARD_ID) continue;

            if (EditControl.getInstance().isCutPending()) {
                if (clipBoard != null) {

                    clipBoard.selectAll();

                    Iterator iter = Collections.list(
                            clipBoard.getSelectedItems()).iterator();
                    while (iter.hasNext()) {
                        Entry ent = ((Instance) iter.next()).getEntry();
                        // opd.getMainEntry().getId() ;
                        if ((opd.getMainEntry() != null)
                                && (opd.getMainEntry().getId() == ent.getId())) {
                            remove = true;
                            break;
                        }
                    }
                } else {
                    remove = false;
                }
            }

            if (!remove) {
                opds.add(opd);
            }
        }
        return Collections.enumeration(opds);

        // return this.opdsHash.elements() ;
    }

    /**
     * will not return the CLIPBOARD OPD
     *
     * @return
     */
    public Enumeration getAllOpds() {

        Vector<Opd> opds = new Vector<Opd>();
        Enumeration<Opd> all = opdsHash.elements();
        while (all.hasMoreElements()) {
            Opd opd = all.nextElement();
            if (opd.getOpdId() != OpdProject.CLIPBOARD_ID) {
                opds.add(opd);
            }
        }

        return this.opdsHash.elements();

    }

    /**
     * Returns an enumeration of the GraphicalRelationRepresentationEntry in
     * this MainStructure. Use the Enumeration methods on the returned object to
     * fetch the GraphicalRelationRepresentationEntry sequentially
     *
     * @return an enumeration of the GraphicalRelationRepresentationEntry in
     *         this MainStructure
     */
    public Enumeration<GraphicalRelationRepresentation> getGraphicalRepresentations() {
        return this.graphicalRepresentations.elements();
    }

    public Enumeration getGraphicalRepresentationsInOpd(long opdId) {
        Vector resultVector = new Vector();
        GraphicalRelationRepresentation tempIns;

        for (Enumeration e = this.getGraphicalRepresentations(); e
                .hasMoreElements(); ) {
            tempIns = (GraphicalRelationRepresentation) e.nextElement();
            if (tempIns.getSource().getOpdId() == opdId) {
                resultVector.add(tempIns);
            }
        }

        return resultVector.elements();

    }

    public GraphicalRelationRepresentation getGraphicalRepresentation(
            GraphicRepresentationKey pKey) {
        return (GraphicalRelationRepresentation) this.graphicalRepresentations
                .get(pKey);
    }

    public ConnectionEdgeEntry getSourceEntry(OpmStructuralRelation relation) {
        return (ConnectionEdgeEntry) this.mStructure.get(new Long(relation
                .getSourceId()));
    }

    public ConnectionEdgeEntry getSourceEntry(OpmProceduralLink link) {
        return (ConnectionEdgeEntry) (this.mStructure.get(new Long(link
                .getSourceId())));
    }

    public ConnectionEdgeEntry getDestinationEntry(
            OpmStructuralRelation relation) {
        return (ConnectionEdgeEntry) (this.mStructure.get(new Long(relation
                .getDestinationId())));
    }

    public ConnectionEdgeEntry getDestinationEntry(OpmProceduralLink link) {
        return (ConnectionEdgeEntry) (this.mStructure.get(new Long(link
                .getDestinationId())));
    }

    public Vector<Instance> getInstancesInOpdVector(long opdId) {
        if (opdInstances.get(opdId) == null) {
            return new Vector<Instance>();
        }
        return opdInstances.get(opdId);
    }

    /**
     * This function returns all instances in the given OPD
     */
    public Enumeration<Instance> getInstancesInOpd(long opdId) {
        return getInstancesInOpdVector(opdId).elements();
    }

    public Enumeration getEntriesInOpd(long opdId) {
        Vector resultVector = new Vector();
        Entry tempEnt;
        Instance tempIns;
        for (Enumeration ee = this.getAllElements(); ee.hasMoreElements(); ) {
            tempEnt = (Entry) ee.nextElement();
            for (Enumeration ie = tempEnt.getInstances(false); ie.hasMoreElements(); ) {
                tempIns = (Instance) ie.nextElement();
                if (tempIns.getKey().getOpdId() == opdId) {
                    resultVector.add(tempEnt);
                    break;
                }
            }
        }
        return resultVector.elements();
    }

    /**
     * This function returns all instances in the given OPD
     */
    public Enumeration<Instance> getInstancesInOpd(Opd opd) {
        return this.getInstancesInOpd(opd.getOpdId());
    }

    /**
     * This function returns all link instances in the given OPD
     */
    public Enumeration getLinksInOpd(long opdId) {
        Vector resultVector = new Vector();
        Instance tempIns;
        for (Enumeration e = this.getInstancesInOpd(opdId); e.hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof LinkInstance) {
                resultVector.add(tempIns);
            }
        }

        return resultVector.elements();
    }

    /**
     * This function returns all link instances in the given OPD
     */
    public Enumeration getLinksInOpd(Opd opd) {
        return this.getLinksInOpd(opd.getOpdId());
    }

    /**
     * This function returns all General Relations instances in the given OPD
     */
    public Enumeration getGeneralRelationsInOpd(long opdId) {
        Vector resultVector = new Vector();
        Instance tempIns;
        for (Enumeration e = this.getInstancesInOpd(opdId); e.hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof GeneralRelationInstance) {
                resultVector.add(tempIns);
            }
        }

        return resultVector.elements();
    }

    /**
     * This function returns all General Relations instances in the given OPD
     */
    public Enumeration getGeneralRelationsInOpd(Opd opd) {
        return this.getGeneralRelationsInOpd(opd.getOpdId());
    }

    /**
     * This function returns all Fundamental Relations instances in the given
     * OPD
     */
    public Enumeration<FundamentalRelationInstance> getFundamentalRelationsInOpd(
            long opdId) {
        Vector<FundamentalRelationInstance> resultVector = new Vector<FundamentalRelationInstance>();
        Instance tempIns;
        for (Enumeration<Instance> e = this.getInstancesInOpd(opdId); e
                .hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof FundamentalRelationInstance) {
                resultVector.add((FundamentalRelationInstance) tempIns);
            }
        }

        return resultVector.elements();
    }

    /**
     * This function returns all Fundamental Relations instances in the given
     * OPD
     */
    public Enumeration<FundamentalRelationInstance> getFundamentalRelationsInOpd(
            Opd opd) {
        return this.getFundamentalRelationsInOpd(opd.getOpdId());
    }

    /**
     * This function returns all Things instances in the given OPD
     */
    public Enumeration getThingsInOpd(long opdId) {
        Vector resultVector = new Vector();
        Instance tempIns;
        for (Enumeration e = this.getInstancesInOpd(opdId); e.hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof ThingInstance) {
                resultVector.add(tempIns);
            }
        }

        return resultVector.elements();
    }

    /**
     * This function returns all Connection Edge instances in the given OPD
     */
    public ArrayList<ConnectionEdgeInstance> getConnetionEdgeInOpd(long opdId) {

        ArrayList<ConnectionEdgeInstance> resultVector = new ArrayList<ConnectionEdgeInstance>();
        Instance tempIns;
        for (Enumeration e = this.getInstancesInOpd(opdId); e.hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof ConnectionEdgeInstance) {
                resultVector.add((ConnectionEdgeInstance) tempIns);
            }
        }

        return resultVector;
    }

    /**
     * This function returns all Connection Edge Entries in the given OPD
     */
    public ArrayList<ConnectionEdgeEntry> getConnetionEdgeEntriesInOpd(
            long opdId) {

        ArrayList<ConnectionEdgeEntry> resultVector = new ArrayList<ConnectionEdgeEntry>();
        Instance tempIns;
        for (Enumeration e = this.getInstancesInOpd(opdId); e.hasMoreElements(); ) {
            tempIns = (Instance) e.nextElement();
            if (tempIns instanceof ConnectionEdgeInstance) {
                if (!resultVector.contains(tempIns))
                    resultVector
                            .add((ConnectionEdgeEntry) (tempIns.getEntry()));
            }
        }

        return resultVector;
    }

    /**
     * This function returns all Things instances in the given OPD
     */
    public Enumeration getThingsInOpd(Opd opd) {
        return this.getThingsInOpd(opd.getOpdId());
    }

    public Enumeration<Instance> getInstanceInOpd(Opd opd, long entityId) {
        Vector<Instance> v = new Vector<Instance>();
        Instance inst = null;
        for (Enumeration e = this.getInstancesInOpd(opd); e.hasMoreElements(); ) {
            inst = (Instance) e.nextElement();
            if (inst.getEntry().getId() == entityId) {
                v.add(inst);
            }
        }
        if (v.size() > 0) {
            return v.elements();
        }
        return null;
    }

    /**
     * Returns the number of entries that reside in the model.
     *
     * @return Number of entries.
     */
    public int getNumOfEntries() {
        return this.mStructure.size();
    }

    public ArrayList<Instance> getPointerInstances(
            boolean includePrivatePointers) {

        ArrayList<Instance> ret = new ArrayList<Instance>();

        Enumeration<Entry> entries = getElements();
        while (entries.hasMoreElements()) {
            Entry entry = entries.nextElement();
            Enumeration<Instance> instances = entry.getInstances(false);
            while (instances.hasMoreElements()) {
                Instance instance = instances.nextElement();
                if (instance.isPointerInstance()) {

                    if (instance.getPointer().isPrivate()) {
                        if (includePrivatePointers) {
                            ret.add(instance);
                        }
                    } else {
                        ret.add(instance);
                    }
                }
            }
        }

        return ret;
    }
}
