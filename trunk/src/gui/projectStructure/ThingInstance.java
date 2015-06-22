package gui.projectStructure;

import exportedAPI.OpdKey;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.controls.FileControl;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdState;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmThing;
import util.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * <p/>
 * This class is super class for ObjectInstance and ProcessInstance. In this
 * class we additionally hold information about OPD unfolding this thing
 * instance.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public abstract class ThingInstance extends ConnectionEdgeInstance implements
        IThingInstance, IXThingInstance {
    private Opd unfoldingOpd;

    private boolean zoomedIn;

    private OpcatProperties myProps;

    int order = -1;

    /**
     * Creates ThingInstance with specified myKey, which holds pThing that
     * represents this ThingInstance graphically.
     *
     * @param myKey OpdKey - key of graphical instance of this entity.
     */
    public ThingInstance(OpdKey myKey, OpdProject project) {
        super(myKey, project);
        this.unfoldingOpd = null;
        this.zoomedIn = false;
        this.myProps = new OpcatProperties();

    }

    public void addProperty(String key, String data) {
        getMyProps().put(key, data);
    }

    public String getProperty(String key) {
        return getMyProps().getProperty(key);
    }

    /**
     * Returns OpdThing which represents this ThingInstance graphically.
     */
    public OpdThing getThing() {
        return (OpdThing) this.myConnectionEdge;
    }

    /**
     * Returns OPD unfolding this ThingInstance. null returned if there is no
     * such OPD.
     */

    public Opd getUnfoldingOpd() {
        return ((ThingEntry) this.getEntry()).getUnfoldingOpd();
    }


    /**
     * Returns OPD in-zoom this ThingInstance. null returned if there is no
     * such OPD.
     */

    public Opd getInZoomOpd() {
        return ((ThingEntry) this.getEntry()).getZoomedInOpd();
    }

    /**
     * used only by ThingEntry to patch the Unfolded = Instance bug
     *
     * @return
     */
    public Opd _getUnfoldingOpd() {
        return this.unfoldingOpd;
    }

    /**
     * Returns OPD unfolding this ThingInstance. null returned if there is no
     * such OPD. Added by YG
     */

    public IXOpd getUnfoldingIXOpd() {
        return this.getUnfoldingOpd();
    }

    /**
     * Sets pUnfoldingOpd to be unfolding for this ThingInstance.
     */
    public void setUnfoldingOpd(Opd pUnfoldingOpd) {
        Iterator iter = Collections.list(
                ((ThingEntry) this.getEntry()).getInstances(false)).iterator();
        while (iter.hasNext()) {
            ThingInstance ins = (ThingInstance) iter.next();
            ins._setUnfoldingOpd(pUnfoldingOpd);
        }
        if (pUnfoldingOpd != null) {
            ((OpdThing) this.myConnectionEdge).setBoldBorder(true);
        } else {
            ((OpdThing) this.myConnectionEdge).setBoldBorder(false);
        }
    }

    public void _setUnfoldingOpd(Opd pUnfoldingOpd) {
        unfoldingOpd = pUnfoldingOpd;
    }

    /**
     * Removes UnfoldingOpd from this ThingInstance.
     */
    public void removeUnfoldingOpd() {
        this.unfoldingOpd = null;
        if (((ThingEntry) this.getEntry()).getUnfoldingOpd() == null)
            ((OpdThing) this.myConnectionEdge).setBoldBorder(false);
    }

    public boolean isZoomedIn() {
        return this.zoomedIn;
    }

    // public IThingInstance getParentIThingInstance() {
    // Container tCont = this.myConnectionEdge.getParent();
    //
    // if (tCont == null) {
    // return null;
    // }
    //
    // if (tCont instanceof OpdThing) {
    // OpdThing tThing = (OpdThing) tCont;
    // return (IThingInstance) tThing.getEntry().getInstance(
    // tThing.getOpdKey());
    // }
    //
    // return null;
    // }

    // public IXThingInstance getParentIXThingInstance() {
    // Container tCont = this.myConnectionEdge.getParent();
    //
    // if (tCont == null) {
    // return null;
    // }
    //
    // if (tCont instanceof OpdThing) {
    // OpdThing tThing = (OpdThing) tCont;
    // return (IXThingInstance) tThing.getEntry().getInstance(
    // tThing.getOpdKey());
    // }
    //
    // return null;
    // }

    /**
     * Sets if this ThingInstance is zoomed in, i.e. if it can contain another
     * entities graphically.
     */
    public void setZoomedIn(boolean pZoomedIn) {
        this.zoomedIn = pZoomedIn;
        OpdThing myThing = (OpdThing) this.myConnectionEdge;
        myThing.setZoomedIn(this.zoomedIn);
        myThing.setBoldBorder(this.zoomedIn);
    }

    public boolean isContainsChilds() {
        if (!this.myConnectionEdge.isZoomedIn()) {
            return false;
        }

        Component components[] = this.myConnectionEdge.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (!(components[i] instanceof OpdState)) {
                return true;
            }
        }

        return false;
    }

    public Enumeration getChildThings() {
        Vector things = new Vector();
        if (!this.myConnectionEdge.isZoomedIn()) {
            return things.elements();
        }

        Component components[] = this.myConnectionEdge.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof OpdThing) {
                OpdThing tThing = (OpdThing) components[i];
                things.add(tThing.getEntry().getInstance(tThing.getOpdKey()));
            }
        }

        return things.elements();

    }

    public void setTextPosition(String tp) {
        ((OpdThing) this.myConnectionEdge).setTextPosition(tp);
    }

    public String getTextPosition() {
        return ((OpdThing) this.myConnectionEdge).getTextPosition();
    }

    protected void copyPropsFrom(ThingInstance origin) {
        super.copyPropsFrom(origin);
        this.setTextPosition(origin.getTextPosition());
    }

    public void update() {

        if (FileControl.getInstance().isGUIOFF()) return;

        OpmThing logThing = (OpmThing) this.getEntry().getLogicalEntity();
        OpdThing grThing = (OpdThing) this.myConnectionEdge;

        grThing.setShadow(logThing.isPhysical());
        grThing.setDashed(logThing.isEnviromental());

//        if (getUnfoldingOpd() != null) {
//            this.unfoldingOpd = getUnfoldingOpd();
//            StringTokenizer st = new StringTokenizer(this.unfoldingOpd
//                    .getName(), " ");
////            this.unfoldingOpd.setName(st.nextToken() + " - "
////                    + logThing.getName().replace('\n', ' ') + " unfolded");
//            this.unfoldingOpd.setName(logThing.getName().replace('\n', ' ') + " unfolded");
//        }

        if ((((ThingEntry) this.getEntry()).getZoomedInOpd() != null)
                || (this.unfoldingOpd != null)) {
            grThing.setBoldBorder(true);
        } else {
            grThing.setBoldBorder(false);
        }

        super.update();
    }

    public int getLayer() {
        return ((JLayeredPane) this.myConnectionEdge.getParent())
                .getPosition(this.myConnectionEdge);
    }

    public void setLayer(int position) {
        ((JLayeredPane) this.myConnectionEdge.getParent()).setPosition(
                this.myConnectionEdge, position);
    }

    public ArrayList<Instance> getIncludedThingInstances() {
        ArrayList<Instance> ret = new ArrayList<Instance>();
        Component[] comps = getGraphicalRepresentation().getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof OpdThing) {
                OpdBaseComponent base = (OpdBaseComponent) comps[i];
                if (base.getInstance() instanceof ThingInstance)
                    ret.add(base.getInstance());
            }
        }
        return ret;
    }

    public ArrayList<Instance> getIncludedProcessInstances() {
        ArrayList<Instance> ret = new ArrayList<Instance>();
        Component[] comps = getGraphicalRepresentation().getComponents();
        for (int i = 0; i < comps.length; i++) {
            if (comps[i] instanceof OpdThing) {
                OpdBaseComponent base = (OpdBaseComponent) comps[i];
                if (base.getInstance() instanceof ProcessInstance)
                    ret.add(base.getInstance());
            }
        }
        return ret;
    }

    // private method !!! uses local constants with no 'defines'
    // Gets 1 - things
    // 2 - links
    // 3 - gen rel
    // 4 - fund rel

    //private HashMap<Instance, Integer> _getIncludedSomethingInstancesInMyInZoomOPDAndOrder(int what) {

    //}

    private Vector<Instance> _getIncludedSomethingInstancesInMyInZoomOPD(int what) {

        Vector<Instance> retVector = new Vector<Instance>();

        Enumeration e;
        if (this.getInZoomOpd() != null) {
            // return retVector.elements();
            e = this.myProject.getComponentsStructure()
                    .getInstancesInOpd(this.getInZoomOpd().getOpdId());
        } else {
            return retVector;
            //e = this.myProject.getComponentsStructure()
            //        .getInstancesInOpd(this.getKey().getOpdId());
            //Vector<Instance> temp = new Vector<Instance>();
            //temp.add(this);
            //e = temp.elements();
        }


        Instance inst = null;
        while (e.hasMoreElements()) {
            inst = (Instance) e.nextElement();

            // Attantion: the follwing statment means there is
            // only one OpdThing in OPD that can hold other OPD things
            if ((inst.getParent() != null) && (this.getInZoomOpd().getMainInstance() != null) && inst.getParent().getInstance().getKey().equals(this.getInZoomOpd().getMainInstance().getKey())) {
                switch (what) {
                    case 2:
                        if (inst instanceof LinkInstance) {
                            retVector.add(inst);
                        }
                        break;
                    case 3:
                        if (inst instanceof GeneralRelationInstance) {
                            retVector.add(inst);
                        }
                        break;
                    case 4:
                        if (inst instanceof FundamentalRelationInstance) {
                            retVector.add(inst);
                        }
                        break;
                    case 5:
                        if (inst instanceof ProcessInstance) {
                            retVector.add(inst);
                        }
                        break;
                    case 6:
                        if (inst instanceof ObjectInstance) {
                            retVector.add(inst);
                        }
                        break;
                }


            }
        }

        //now order it by X value of position
        Collections.sort(retVector, new Comparator<Instance>() {
            public int compare(Instance one, Instance two) {
                return isSameOrder(one, two);
            }

        });


        //end order
        return retVector;
    }

    private int isSameOrder(Instance one, Instance two) {
        double oneY = one.graphicalRepresentation.getY();
        double twoY = two.graphicalRepresentation.getY();
        double diff = Double.valueOf(Configuration.getInstance().getProperty("simulation_level_range"));
        return (oneY - twoY > diff ? 1 : (Math.abs(oneY - twoY) < diff ? 0 : -1));
    }

    private void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        if (order <= 0 || isChangedLocation()) {
            reCalculateOrder();
        }
        return order;
    }

    public void reCalculateOrder() {

        setChangedLocation(false);

        if (this instanceof ObjectInstance) {
            setOrder(0);
            return;
        }

        if (this.getParentThingInstance() == null) {
            setOrder(0);
            return;
        }

        Vector<Instance> processes = this.getParentThingInstance().getIncludedProcessInstancesInMyInZoomOPD();
        //int i = 1;
        int order = 1;

        for (int j = 0; j < processes.size() - 1; j++) {
            Instance one = processes.get(j);
            Instance two = processes.get(j + 1);

            if (one.getKey().equals(this.getKey())) {
                setOrder(order);
                return;
            }

            boolean eqOrder = isSameOrder(one, two) == 0;
            if (!eqOrder) {
                order++;
            }
        }

        setOrder(order);
    }

    public IXOpd createInzoomedOpd() {
        return this.myProject.zoomIn(this);
    }

    public IXOpd createUnfoldedOpd(boolean bringRelatedThings,
                                   boolean bringRoleRelatedThings) {
        return this.myProject.unfolding(this, bringRelatedThings,
                bringRelatedThings, true, bringRoleRelatedThings);
    }

    public Vector<Instance> getIncludedProcessInstancesInMyInZoomOPD() {
        return this._getIncludedSomethingInstancesInMyInZoomOPD(5);
    }

    public Vector<Instance> getIncludedObjectInstancesInMyInZoomOPD() {
        return this._getIncludedSomethingInstancesInMyInZoomOPD(6);
    }

    public Vector<Instance> getIncludedLinkInstancesInMyInZoomOPD() {
        return this._getIncludedSomethingInstancesInMyInZoomOPD(2);
    }

    public Vector<Instance> getIncludedGeneralRelationInstancesInMyInZoomOPD() {
        return this._getIncludedSomethingInstancesInMyInZoomOPD(3);
    }

    public Vector<Instance> getIncludedFundamentalRelationInstancesInMyInZoomOPD() {
        return this._getIncludedSomethingInstancesInMyInZoomOPD(4);
    }

    public boolean isIncluding(Instance ins) {
        Opd inzoom = ((ThingEntry) getEntry()).getZoomedInOpd();
        if (inzoom == null) {
            return false;
        }

        return ((getOpd().getOpdId() == inzoom.getOpdId()) && (ins.getParent() != null));
    }

    public String toString() {
        return this.getThing().getName();
    }

    public String getThingName() {
        return this.getThing().getName();
    }

    public String getTypeString() {
        return "Thing";
    }

    public HashMap<ThingInstance, FundamentalRelationInstance> getFundemantulRelationInstancesSons(
            int type) {
        HashMap<Entry, FundamentalRelationEntry> entries = ((ThingEntry) getEntry())
                .getFundemantulRelationSons(type);
        HashMap<ThingInstance, FundamentalRelationInstance> ret = new HashMap<ThingInstance, FundamentalRelationInstance>(
                0);
        Iterator<FundamentalRelationEntry> relationsEntries = entries.values()
                .iterator();
        while (relationsEntries.hasNext()) {
            FundamentalRelationEntry relEntry = relationsEntries.next();
            FundamentalRelationInstance ins = (FundamentalRelationInstance) relEntry
                    .getInstanceInOPD(getOpd());
            if (ins != null) {
                ret.put((ThingInstance) ins.getDestinationInstance(), ins);
            }
        }
        return ret;

    }

    public OpcatProperties getMyProps() {
        return myProps;
    }
}
