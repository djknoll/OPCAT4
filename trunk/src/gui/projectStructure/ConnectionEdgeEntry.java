package gui.projectStructure;

import exportedAPI.OpcatConstants;
import exportedAPI.opcatAPI.IConnectionEdgeEntry;
import exportedAPI.opcatAPIx.IXConnectionEdgeEntry;
import expose.OpcatExposeConstants.OPCAT_EXPOSE_LINK_DIRECTION;
import gui.Opcat2;
import gui.controls.EditControl;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opmEntities.*;
import gui.undo.UndoableAddFundamentalRelation;
import gui.undo.UndoableAddGeneralRelation;
import gui.undo.UndoableAddLink;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import java.awt.*;
import java.util.*;

/**
 * <p/>
 * This class is super class for ThingEntry and StateEntry, both of them have
 * common property - can be connected by links or relations, therefore they
 * should have a common superclass.
 * <p/>
 * In this class we hold information (in special data structures) about links
 * and relations in which this ConnectionEdgeEntry participates as source or
 * destination. This information is very useful for Unfolding and Zooming in
 * operations.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class ConnectionEdgeEntry extends Entry implements IConnectionEdgeEntry,
        IXConnectionEdgeEntry {
    private Hashtable<RelationKey, OpmProceduralLink> linkSources;

    private Hashtable<RelationKey, OpmProceduralLink> linkDestinations;

    private Hashtable<RelationKey, OpmStructuralRelation> relationSources;

    private Hashtable<RelationKey, OpmStructuralRelation> relationDestinations;

    private Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> relationSourcesCounter;

    private Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> relationDestinationsCounter;


    /**
     * Creates ConnectionEdgeEntry that holds all information about specified
     * pEdge.
     *
     * @param pEdge object of OpmConnectionEdge class.
     */
    public ConnectionEdgeEntry(OpmConnectionEdge pEdge, OpdProject project) {
        super(pEdge, project);
        this.linkSources = new Hashtable<RelationKey, OpmProceduralLink>();
        this.linkDestinations = new Hashtable<RelationKey, OpmProceduralLink>();
        this.relationSources = new Hashtable<RelationKey, OpmStructuralRelation>();
        this.relationDestinations = new Hashtable<RelationKey, OpmStructuralRelation>();

        this.relationSourcesCounter = new Hashtable<RelationKey, ArrayList<OpmStructuralRelation>>();
        this.relationDestinationsCounter = new Hashtable<RelationKey, ArrayList<OpmStructuralRelation>>();

    }

    public Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> getRelationDestinationsCounter() {
        return relationDestinationsCounter;
    }

    public Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> getRelationSourcesCounter() {
        return relationSourcesCounter;
    }

    /**
     * This method adds pRelation to the data structure holding all structural
     * relations in which this ConnectionEdgeEntry participates as source.
     *
     */

    /**
     * This method adds pRelation to the data structure holding all structural
     * relations in which this ConnectionEdgeEntry participates as source.
     */

    public void addRelationSource(OpmStructuralRelation pRelation) {

        RelationKey relationKey = new RelationKey(Constants
                .getType4Relation(pRelation), pRelation.getDestinationId());

        ArrayList<OpmStructuralRelation> array;
        if (!(relationSourcesCounter.containsKey(relationKey))) {
            this.relationSources.put(relationKey, pRelation);
            array = new ArrayList<OpmStructuralRelation>();
        } else {
            array = relationSourcesCounter.get(relationKey);
        }

        array.add(pRelation);
        relationSourcesCounter.put(relationKey, array);


    }

    public void removeRelationSource(OpmStructuralRelation pRelation) {

        RelationKey relationKey = new RelationKey(Constants
                .getType4Relation(pRelation), pRelation.getDestinationId());

        if (!(relationSourcesCounter.containsKey(relationKey)) || (relationSourcesCounter.get(relationKey).size() - 1 <= 0)) {
            this.relationSources.remove(relationKey);
            this.relationSourcesCounter.remove(relationKey);
        } else {
            ArrayList<OpmStructuralRelation> array = relationSourcesCounter.get(relationKey);
            array.remove(pRelation);
            relationSourcesCounter.put(relationKey, array);
            relationSources.put(relationKey, array.get(0));
        }

    }

    public void addGeneralRelationSource(OpmGeneralRelation pRelation) {
        addRelationSource(pRelation);
    }

    public void removeGeneralRelationSource(OpmGeneralRelation pRelation) {
        removeRelationSource(pRelation);
    }

    /**
     * Returns OpmStructuralRelation of pRelationType(as it specified in
     * Interface Constants in opmEntities package) in which entity with
     * pEntityId participates as destination and this ConnectionEdgeEntry
     * participates as source. null is returned if there is no such
     * OpmStructuralRelation in data structure.
     */

    public OpmStructuralRelation getRelationByDestination(long pEntityId,
                                                          int pRelationType) {
        return (OpmStructuralRelation) (this.relationSources
                .get(new RelationKey(pRelationType, pEntityId)));
    }

    /**
     * This method adds pRelation to the data structure holding all structural
     * relations in which this ConnectionEdgeEntry participates as destination.
     */
    public void addRelationDestination(OpmStructuralRelation pRelation) {

        RelationKey relationKey = new RelationKey(Constants
                .getType4Relation(pRelation), pRelation.getSourceId());

        ArrayList<OpmStructuralRelation> array;

        if (!(relationDestinationsCounter.containsKey(relationKey))) {
            this.relationDestinations.put(relationKey, pRelation);
            array = new ArrayList<OpmStructuralRelation>();
        } else {
            array = relationDestinationsCounter.get(relationKey);
        }

        array.add(pRelation);
        relationDestinationsCounter.put(relationKey, array);

    }

    /**
     * returns interface of an entry.
     *
     * @return sons hash map <entry, link>
     */
    public HashMap<OpmProceduralLink, OPCAT_EXPOSE_LINK_DIRECTION> getDirectInterface() {

        HashMap<OpmProceduralLink, OPCAT_EXPOSE_LINK_DIRECTION> ret = new HashMap<OpmProceduralLink, OPCAT_EXPOSE_LINK_DIRECTION>();

        Iterator<OpmProceduralLink> iter = Collections.list(getSourceLinks())
                .iterator();

        while (iter.hasNext()) {
            OpmProceduralLink rel = (OpmProceduralLink) iter.next();

            ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
                    .getSystemStructure().getEntry(rel.getDestinationId());
            ret.put(rel, OPCAT_EXPOSE_LINK_DIRECTION.FROM);
        }

        iter = Collections.list(getDestinationLinks()).iterator();

        while (iter.hasNext()) {
            OpmProceduralLink rel = (OpmProceduralLink) iter.next();

            ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
                    .getSystemStructure().getEntry(rel.getSourceId());
            ret.put(rel, OPCAT_EXPOSE_LINK_DIRECTION.TO);
        }

        return ret;
    }

    public void removeRelationDestination(OpmStructuralRelation pRelation) {

        RelationKey relationKey = new RelationKey(Constants
                .getType4Relation(pRelation), pRelation.getSourceId());

        if (!(relationDestinationsCounter.containsKey(relationKey)) || (relationDestinationsCounter.get(relationKey).size() - 1 <= 0)) {
            this.relationDestinations.remove(relationKey);
            this.relationDestinationsCounter.remove(relationKey);
        } else {
            ArrayList<OpmStructuralRelation> array = relationDestinationsCounter.get(relationKey);
            array.remove(pRelation);
            relationDestinationsCounter.put(relationKey, array);
            relationDestinations.put(relationKey, array.get(0));
        }

    }

    public void addGeneralRelationDestination(OpmGeneralRelation pRelation) {
        addRelationDestination(pRelation);
    }

    public void removeGeneralRelationDestination(OpmGeneralRelation pRelation) {
        removeRelationDestination(pRelation);
        return;
    }

    /**
     * Returns OpmStructuralRelation of pRelationType(as it specified in
     * Interface Constants in opmEntities package) in which entity with
     * pEntityId participates as source and this ConnectionEdgeEntry
     * participates as destination. null is returned if there is no such
     * OpmStructuralRelation in data structure.
     */

    public OpmStructuralRelation getRelationBySource(long pEntityId,
                                                     int pRelationType) {
        return (OpmStructuralRelation) (this.relationDestinations
                .get(new RelationKey(pRelationType, pEntityId)));
    }

    /**
     * This method adds pLink to the data structure holding all procedural links
     * in which this ConnectionEdgeEntry participates as source.
     */

    public void addLinkSource(OpmProceduralLink pLink) {
        this.linkSources.put(new RelationKey(Constants.getType4Link(pLink),
                pLink.getDestinationId()), pLink);
        return;
    }

    public void removeLinkSource(OpmProceduralLink pLink) {
        this.linkSources.remove(new RelationKey(Constants.getType4Link(pLink),
                pLink.getDestinationId()));
        return;
    }

    /**
     * Returns OpmProceduralLink of pLinkType(as it specified in Interface
     * Constants in opmEntities package) in which entity with pEntityId
     * participates as destination and this ConnectionEdgeEntry participates as
     * source. null is returned if there is no such OpmProceduralLink in data
     * structure.
     */

    public OpmProceduralLink getLinkByDestination(long pEntityId, int pLinkType) {
        return (OpmProceduralLink) (this.linkSources.get(new RelationKey(
                pLinkType, pEntityId)));
    }

    /**
     * This method adds pLink to the data structure holding all procedural links
     * in which this ConnectionEdgeEntry participates as destination.
     */

    public void addLinkDestination(OpmProceduralLink pLink) {
        this.linkDestinations.put(new RelationKey(
                Constants.getType4Link(pLink), pLink.getSourceId()), pLink);
        return;
    }

    public void removeLinkDestination(OpmProceduralLink pLink) {
        this.linkDestinations.remove(new RelationKey(Constants
                .getType4Link(pLink), pLink.getSourceId()));
        return;
    }

    /**
     * Returns OpmProceduralLink of pLinkType(as it specified in Interface
     * Constants in opmEntities package) in which entity with pEntityId
     * participates as source and this ConnectionEdgeEntry participates as
     * destination. null is returned if there is no such OpmProceduralLink in
     * data structure.
     */
    public OpmProceduralLink getLinkBySource(long pEntityId, int pLinkType) {
        return (OpmProceduralLink) (this.linkDestinations.get(new RelationKey(
                pLinkType, pEntityId)));
    }

    /**
     * Returns an enumeration of the OpmStructuralRelation in which this
     * ConnectionEdgeEntry paricipates as source. Use the Enumeration methods on
     * the returned object to fetch the OpmStructuralRelation sequentially
     */
    public Enumeration<OpmStructuralRelation> getSourceFundRelations() {
        Vector vec = new Vector();
        for (OpmStructuralRelation rel : this.relationSources.values()) {
            if (Constants.getType4Relation(rel) == OpcatConstants.UNI_DIRECTIONAL_RELATION) continue;
            if (Constants.getType4Relation(rel) == OpcatConstants.BI_DIRECTIONAL_RELATION) continue;
            vec.add(rel);
        }

        return vec.elements();
    }

    public Enumeration<OpmStructuralRelation> getSourceRelations() {
        return relationSources.elements();
    }

    public Enumeration<OpmStructuralRelation> getDestinationRelations() {
        return relationDestinations.elements();
    }


    public Iterator<OpmGeneralRelation> getSourceGeneralRelations() {
        ArrayList<OpmGeneralRelation> list = new ArrayList<OpmGeneralRelation>();
        for (OpmStructuralRelation rel : relationSources.values()) {
            if ((Constants.getType4Relation(rel) == OpcatConstants.BI_DIRECTIONAL_RELATION) || (Constants.getType4Relation(rel) == OpcatConstants.UNI_DIRECTIONAL_RELATION)) {
                list.add((OpmGeneralRelation) rel);
            }
        }
        return list.iterator();
    }

    /**
     * Returns Enumeration of IRelationEntry - all srtructural relations having
     * this IConnectionEdgeEntry as source. Use the Enumeration methods on the
     * returned object to fetch the IRelationEntry sequentially
     */

    public Enumeration getRelationBySource() {
        Vector tVect = new Vector();
        for (Enumeration e = this.getSourceFundRelations(); e.hasMoreElements(); ) {
            OpmStructuralRelation tRel = (OpmStructuralRelation) e
                    .nextElement();
            tVect.add(this.myProject.getComponentsStructure().getEntry(
                    tRel.getId()));
        }

        //for (Iterator i = this.getSourceGeneralRelations(); i.hasNext(); ) {
        //    OpmGeneralRelation tRel = (OpmGeneralRelation) i.next();
        //    tVect.add(this.myProject.getComponentsStructure().getEntry(
        //            tRel.getId()));
        //}

        return tVect.elements();
    }

    /**
     * Returns an enumeration of the OpmStructuralRelation in which this
     * ConnectionEdgeEntry paricipates as destination. Use the Enumeration
     * methods on the returned object to fetch the OpmStructuralRelation
     * sequentially
     */
    public Enumeration getDestinationFundRelations() {
        Vector vec = new Vector();
        for (OpmStructuralRelation rel : this.relationDestinations.values()) {
            if (Constants.getType4Relation(rel) == OpcatConstants.UNI_DIRECTIONAL_RELATION) continue;
            if (Constants.getType4Relation(rel) == OpcatConstants.BI_DIRECTIONAL_RELATION) continue;
            vec.add(rel);
        }

        return vec.elements();
    }

//    public Iterator<OpmGeneralRelation> getDestinationGeneralRelations() {
//        ArrayList<OpmGeneralRelation> list = new ArrayList<OpmGeneralRelation>();
//        for (OpmStructuralRelation rel : relationDestinations.values()) {
//            if ((Constants.getType4Relation(rel) == OpcatConstants.BI_DIRECTIONAL_RELATION) || (Constants.getType4Relation(rel) == OpcatConstants.UNI_DIRECTIONAL_RELATION)) {
//                list.add((OpmGeneralRelation) rel);
//            }
//        }
//        return list.iterator();
//        //return this.generalRelationDestinations.iterator();
//    }


    /**
     * returns direct sons of an entry.
     *
     * @return sons hash map <entry, link>
     */
    public HashMap<Entry, OpmStructuralRelation> getDirectSonsWithOutInharatence() {

        HashMap<Entry, OpmStructuralRelation> ret = new HashMap<Entry, OpmStructuralRelation>();

        Enumeration<Entry> entries = myProject.getSystemStructure()
                .getElements();

        while (entries.hasMoreElements()) {
            Entry entry = entries.nextElement();
            if (entry instanceof FundamentalRelationEntry) {
                FundamentalRelationEntry fun = (FundamentalRelationEntry) entry;
                if (fun.getRelationType() != OpcatConstants.SPECIALIZATION_RELATION) {
                    if (fun.getSourceId() == this.getId()) {
                        ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
                                .getSystemStructure().getEntry(
                                        fun.getDestinationId());
                        ret.put(conncted, (OpmStructuralRelation) fun
                                .getLogicalEntity());
                    }
                }
            } else if (entry instanceof GeneralRelationEntry) {
                GeneralRelationEntry fun = (GeneralRelationEntry) entry;
                if (fun.getSourceId() == this.getId()) {
                    ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
                            .getSystemStructure().getEntry(
                                    fun.getDestinationId());
                    ret.put(conncted, (OpmStructuralRelation) fun
                            .getLogicalEntity());
                }
            }
        }

        // Iterator<OpmStructuralRelation> iter = Collections.list(
        // getSourceFundRelations()).iterator();
        //
        // while (iter.hasNext()) {
        // OpmStructuralRelation rel = (OpmStructuralRelation) iter.next();
        //
        // if ((rel instanceof OpmSpecialization)) {
        //
        // } else {
        // ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
        // .getSystemStructure().getEntry(rel.getDestinationId());
        // ret.put(conncted, rel);
        // }
        // }
        //
        // Iterator<OpmGeneralRelation> gens = getSourceGeneralRelations();
        // while (gens.hasNext()) {
        // OpmStructuralRelation rel = (OpmStructuralRelation) gens.next();
        //
        // ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
        // .getSystemStructure().getEntry(rel.getDestinationId());
        // ret.put(conncted, rel);
        // }

        return ret;
    }

    /**
     * returns direct parents of an entry.
     *
     * @return sons hash map <entry, link>
     */
    public HashMap<Entry, OpmStructuralRelation> getDirectParents() {

        HashMap<Entry, OpmStructuralRelation> ret = new HashMap<Entry, OpmStructuralRelation>();

        Iterator<OpmStructuralRelation> iter = Collections.list(
                getDestinationFundRelations()).iterator();

        while (iter.hasNext()) {
            OpmStructuralRelation rel = (OpmStructuralRelation) iter.next();

            ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
                    .getSystemStructure().getEntry(rel.getSourceId());
            ret.put(conncted, rel);

        }

        //Iterator<OpmGeneralRelation> gens = getDestinationGeneralRelations();
        //while (gens.hasNext()) {
        //    OpmStructuralRelation rel = (OpmStructuralRelation) gens.next();
        //
        //    ConnectionEdgeEntry conncted = (ConnectionEdgeEntry) myProject
        //            .getSystemStructure().getEntry(rel.getSourceId());
        //    ret.put(conncted, rel);
        //}

        return ret;
    }

    /**
     * Returns Enumeration of RelationEntry - all structural relations having
     * this ConnectionEdgeEntry as destination. Use the Enumeration methods on
     * the returned object to fetch the RelationEntry sequentially
     */

    public Enumeration getRelationByDestination() {
        Vector tVect = new Vector();
        for (Enumeration e = this.getDestinationFundRelations(); e
                .hasMoreElements(); ) {
            OpmStructuralRelation tRel = (OpmStructuralRelation) e
                    .nextElement();
            tVect.add(this.myProject.getComponentsStructure().getEntry(
                    tRel.getId()));
        }

        //for (Iterator i = this.getDestinationGeneralRelations(); i.hasNext(); ) {
        //    OpmStructuralRelation tRel = (OpmStructuralRelation) i.next();
        //    tVect.add(this.myProject.getComponentsStructure().getEntry(
        //            tRel.getId()));
        //}

        return tVect.elements();
    }

    /**
     * Returns an enumeration of the OpmProceduralLink in which this
     * ConnectionEdgeEntry paricipates as source. Use the Enumeration methods on
     * the returned object to fetch the OpmProceduralLink sequentially
     */
    public Enumeration<OpmProceduralLink> getSourceLinks() {
        return this.linkSources.elements();
    }

    /**
     * Returns Enumeration of LinkEntry - all links having this
     * ConnectionEdgeEntry as source. Use the Enumeration methods on the
     * returned object to fetch the LinkEntry sequentially
     */

    public Enumeration getLinksBySource() {

        Vector tVect = new Vector();
        for (Enumeration e = this.getSourceLinks(); e.hasMoreElements(); ) {
            OpmProceduralLink tLink = (OpmProceduralLink) e.nextElement();
            tVect.add(this.myProject.getComponentsStructure().getEntry(
                    tLink.getId()));
        }

        return tVect.elements();
    }

    /**
     * Returns an enumeration of the OpmProceduralLink in which this
     * ConnectionEdgeEntry paricipates as destination. Use the Enumeration
     * methods on the returned object to fetch the OpmProceduralLink
     * sequentially
     */
    public Enumeration<OpmProceduralLink> getDestinationLinks() {
        return this.linkDestinations.elements();
    }

    /**
     * Returns Enumeration of LinkEntry - all links having this
     * ConnectionEdgeEntry as destination. Use the Enumeration methods on the
     * returned object to fetch the LinkEntry sequentially
     */

    public Enumeration getLinksByDestination() {

        Vector tVect = new Vector();
        for (Enumeration e = this.getDestinationLinks(); e.hasMoreElements(); ) {
            OpmProceduralLink tLink = (OpmProceduralLink) e.nextElement();
            tVect.add(this.myProject.getComponentsStructure().getEntry(
                    tLink.getId()));
        }

        return tVect.elements();
    }


    public Enumeration getSourceRelations(int relType) {
        OpmStructuralRelation tmpRel;
        Vector v = new Vector();
        for (Enumeration e = this.getSourceRelations(); e.hasMoreElements(); ) {
            tmpRel = (OpmStructuralRelation) e.nextElement();
            if (Constants.getType4Relation(tmpRel) == relType) {
                v.addElement(tmpRel);
            }
        }
        return v.elements();
    }

    public Enumeration getDestinationRelations(int relType) {
        OpmStructuralRelation tmpRel;
        Vector v = new Vector();
        for (Enumeration e = getDestinationRelations(); e
                .hasMoreElements(); ) {
            tmpRel = (OpmStructuralRelation) e.nextElement();
            if (Constants.getType4Relation(tmpRel) == relType) {
                v.addElement(tmpRel);
            }
        }
        return v.elements();
    }

    public Enumeration getSourceLinks(int linkType) {
        OpmProceduralLink tmpLink;
        Vector v = new Vector();
        for (Enumeration e = this.linkSources.elements(); e.hasMoreElements(); ) {
            tmpLink = (OpmProceduralLink) e.nextElement();
            if (Constants.getType4Link(tmpLink) == linkType) {
                v.addElement(tmpLink);
            }
        }
        return v.elements();
    }

    public Enumeration getDestinationLinks(int linkType) {
        OpmProceduralLink tmpLink;
        Vector v = new Vector();
        for (Enumeration e = this.linkDestinations.elements(); e
                .hasMoreElements(); ) {
            tmpLink = (OpmProceduralLink) e.nextElement();
            if (Constants.getType4Link(tmpLink) == linkType) {
                v.addElement(tmpLink);
            }
        }
        return v.elements();
    }

    /**
     * Returns an enumeration of OpmEntity (all relations and links) in which
     * this ConnectionEdgeEntry paricipates. Use the Enumeration methods on the
     * returned object to fetch the OpmStructuralRelation sequentially
     */

    public Enumeration getAllRelatedEntities() {
        Vector v = new Vector();

        v.addAll(this.linkSources.values());
        v.addAll(this.linkDestinations.values());
        v.addAll(this.relationSources.values());
        v.addAll(this.relationDestinations.values());

        //Object tempArray[] = getSourceGeneralRelationsList().toArray();

        //for (int i = 0; i < tempArray.length; i++) {
        //    v.add(tempArray[i]);
        //}

        //tempArray = getDestinationGeneralRelationsList().toArray();

        //for (int i = 0; i < tempArray.length; i++) {
        //    v.add(tempArray[i]);
        //}

        return v.elements();
    }

    public void updateInstances() {
        for (Enumeration e = this.getInstances(false); e.hasMoreElements(); ) {
            ((ConnectionEdgeInstance) (e.nextElement())).update();
        }
    }

    public void setRelationDestinationsCounter(Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> relationDestinationsCounter) {
        this.relationDestinationsCounter = relationDestinationsCounter;
    }

    public void setRelationSourcesCounter(Hashtable<RelationKey, ArrayList<OpmStructuralRelation>> relationSourcesCounter) {
        this.relationSourcesCounter = relationSourcesCounter;
    }

    public void completeRealtions(Opd opd, boolean entryIsSource,
                                  boolean addMisingThings, ConnectionEdgeInstance myInstance, int relationType) {
        long opdID = opd.getOpdId();
        MainStructure system = myProject.getSystemStructure();

        Enumeration<OpmStructuralRelation> relationEnum = null;

        if (entryIsSource) {
            relationEnum = getSourceRelations(relationType);
        } else {
            relationEnum = getDestinationRelations(relationType);
        }

        while (relationEnum.hasMoreElements()) {

            OpmStructuralRelation opmRelation = relationEnum.nextElement();

            RelationEntry relationEntry = (RelationEntry) system.getEntry(opmRelation.getId());

            ConnectionEdgeEntry myEdge = null;

            ConnectionEdgeEntry otherEdge = null;
            if (entryIsSource) {
                otherEdge = (ConnectionEdgeEntry) system.getEntry(opmRelation
                        .getDestinationId());
                myEdge = (ConnectionEdgeEntry) system.getEntry(opmRelation
                        .getSourceId());
            } else {
                otherEdge = (ConnectionEdgeEntry) system.getEntry(opmRelation
                        .getSourceId());
                myEdge = (ConnectionEdgeEntry) system.getEntry(opmRelation
                        .getDestinationId());
            }

            if (this instanceof ObjectEntry) {

            }

            if (!(otherEdge.hasInstanceInOpd(opdID))) {
                /**
                 * if other instance is not in the OPD then add it if the user
                 * marked the addMisingThings param
                 */
                if (addMisingThings) {
                    Instance otherInstance = otherEdge.getInstances(false)
                            .nextElement();
                    EditControl.getInstance().getCurrentProject().deselectAll();
                    EditControl.getInstance().getCurrentProject().clearClipBoard();

                    Container parent = opd.getDrawingArea();
                    int x = 1;
                    int y = 1;
                    if ((myInstance != null)
                            && (myInstance.getParent() != null)) {
                        int ret = JOptionPane.showConfirmDialog(opd
                                .getDrawingArea(), "Insert into main Entity ?",
                                "Complete Relation", JOptionPane.YES_NO_OPTION);
                        if (ret == JOptionPane.YES_OPTION) {
                            parent = myInstance.getParent();
                            x = myInstance.getX() + 30;
                            y = myInstance.getY();
                        }
                    }
                    if (otherInstance instanceof StateInstance) {
                        ObjectInstance objectIns = ((StateInstance) otherInstance)
                                .getParentObjectInstance();
                        otherInstance.getOpd().getSelection().addSelection(
                                objectIns, true);
                        myProject._copy(otherInstance.getOpd(), opd, x, y,
                                parent, true);
                    } else {
                        otherInstance.getOpd().getSelection().addSelection(
                                otherInstance, true);
                        myProject._copy(otherInstance.getOpd(), opd, x, y,
                                parent, true);
                    }

                    Instance insertedInstance = otherInstance;
                    Enumeration<Instance> insertedInstances = system
                            .getInstanceInOpd(opd, otherInstance.getEntry()
                                    .getId());

                    if (insertedInstances != null) {
                        while (insertedInstances.hasMoreElements()) {
                            insertedInstance = insertedInstances.nextElement();
                            Point location = insertedInstance
                                    .getGraphicalRepresentation().getLocation();
                            ((ConnectionEdgeInstance) insertedInstance)
                                    .getConnectionEdge().fitToContent();
                            insertedInstance.getGraphicalRepresentation()
                                    .setLocation(location);
                        }
                    }
                }
            }
            /**
             * now search instances of this entry in this OPD
             */
            if (otherEdge.hasInstanceInOpd(opdID)
                    && myEdge.hasInstanceInOpd(opdID)) {
                Enumeration<Instance> otherInstancesEnum = system
                        .getInstanceInOpd(opd, otherEdge.getId());
                /**
                 * we have the connected instances in this OPD, see if they are
                 * alrady connected. if not then conect them.
                 *
                 */
                boolean found = false;
                while (otherInstancesEnum.hasMoreElements()) {
                    found = false;
                    ConnectionEdgeInstance connectionEdgeInstance = (ConnectionEdgeInstance) otherInstancesEnum
                            .nextElement();

                    Enumeration<Instance> instances = myEdge.getInstances(false);
                    Enumeration<Instance> sourceRelationInstances = relationEntry
                            .getInstances(false);

                    while (instances.hasMoreElements()) {
                        Instance instance = instances.nextElement();

                        if (opd.isContaining(instance)) {
                            while (sourceRelationInstances.hasMoreElements()) {
                                Instance sourceRelstionInstance = sourceRelationInstances
                                        .nextElement();

                                if (opd.isConnectedByRelation(instance,
                                        connectionEdgeInstance,
                                        Constants.getType4Relation((OpmStructuralRelation) sourceRelstionInstance.getEntry().getLogicalEntity()))) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                /**
                                 * add the relation here,
                                 */
                                Instance li = null;
                                if (entryIsSource) {

                                    li = myProject.addRelationOfType(instance,
                                            connectionEdgeInstance, relationEntry
                                            .getRelationType(), opd);
                                } else {
                                    li = myProject.addRelationOfType(connectionEdgeInstance,
                                            instance, relationEntry
                                            .getRelationType(), opd);
                                }
                                if (li != null) {

                                    if (li instanceof FundamentalRelationInstance) {
                                        Opcat2.getUndoManager()
                                                .undoableEditHappened(
                                                        new UndoableEditEvent(
                                                                this,
                                                                new UndoableAddFundamentalRelation(
                                                                        myProject,
                                                                        (FundamentalRelationInstance) li)));
                                    } else {
                                        Opcat2.getUndoManager()
                                                .undoableEditHappened(
                                                        new UndoableEditEvent(
                                                                this,
                                                                new UndoableAddGeneralRelation(
                                                                        myProject,
                                                                        (GeneralRelationInstance) li)));

                                    }
                                    Opcat2.setUndoEnabled(Opcat2
                                            .getUndoManager().canUndo());
                                    Opcat2.setRedoEnabled(Opcat2
                                            .getUndoManager().canRedo());
                                }

                            }
                            break;
                        }
                    }
                }
            }
        }


    }

    public void completeLinks(Opd opd, boolean entryIsSource,
                              boolean addMisingThings, ConnectionEdgeInstance myInstance) {

        long opdID = opd.getOpdId();
        Enumeration<OpmProceduralLink> linksEnum = null;
        MainStructure system = myProject.getSystemStructure();

        if (entryIsSource) {
            if (this instanceof ObjectEntry) {
                linksEnum = ((ObjectEntry) this).getSourceLinks(true);
            } else {
                linksEnum = getSourceLinks();
            }
        } else {
            if (this instanceof ObjectEntry) {
                linksEnum = ((ObjectEntry) this).getDestinationLinks(true);
            } else {
                linksEnum = getDestinationLinks();
            }
        }

        while (linksEnum.hasMoreElements()) {

            OpmProceduralLink opmLink = linksEnum.nextElement();

            LinkEntry linkEntry = (LinkEntry) system.getEntry(opmLink.getId());

            ConnectionEdgeEntry myEdge = null;

            ConnectionEdgeEntry otherEdge = null;
            if (entryIsSource) {
                otherEdge = (ConnectionEdgeEntry) system.getEntry(opmLink
                        .getDestinationId());
                myEdge = (ConnectionEdgeEntry) system.getEntry(opmLink
                        .getSourceId());
            } else {
                otherEdge = (ConnectionEdgeEntry) system.getEntry(opmLink
                        .getSourceId());
                myEdge = (ConnectionEdgeEntry) system.getEntry(opmLink
                        .getDestinationId());
            }

            if (this instanceof ObjectEntry) {

            }

            if (!(otherEdge.hasInstanceInOpd(opdID))) {
                /**
                 * if other instance is not in the OPD then add it if the user
                 * marked the addMisingThings param
                 */
                if (addMisingThings) {
                    Instance otherInstance = otherEdge.getInstances(false)
                            .nextElement();
                    EditControl.getInstance().getCurrentProject().deselectAll();
                    EditControl.getInstance().getCurrentProject()
                            .clearClipBoard();

                    Container parent = opd.getDrawingArea();
                    int x = 1;
                    int y = 1;
                    if ((myInstance != null)
                            && (myInstance.getParent() != null)) {
                        int ret = JOptionPane.showConfirmDialog(opd
                                .getDrawingArea(), "Insert into main Entity ?",
                                "Complete Link", JOptionPane.YES_NO_OPTION);
                        if (ret == JOptionPane.YES_OPTION) {
                            parent = myInstance.getParent();
                            x = myInstance.getX() + 30;
                            y = myInstance.getY();
                        }
                    }
                    if (otherInstance instanceof StateInstance) {
                        ObjectInstance objectIns = ((StateInstance) otherInstance)
                                .getParentObjectInstance();
                        otherInstance.getOpd().getSelection().addSelection(
                                objectIns, true);
                        myProject._copy(otherInstance.getOpd(), opd, x, y,
                                parent, true);
                    } else {
                        otherInstance.getOpd().getSelection().addSelection(
                                otherInstance, true);
                        myProject._copy(otherInstance.getOpd(), opd, x, y,
                                parent, true);
                    }

                    Instance insertedInstance = otherInstance;
                    Enumeration<Instance> insertedInstances = system
                            .getInstanceInOpd(opd, otherInstance.getEntry()
                                    .getId());

                    if (insertedInstances != null) {
                        while (insertedInstances.hasMoreElements()) {
                            insertedInstance = insertedInstances.nextElement();
                            Point location = insertedInstance
                                    .getGraphicalRepresentation().getLocation();
                            ((ConnectionEdgeInstance) insertedInstance)
                                    .getConnectionEdge().fitToContent();
                            insertedInstance.getGraphicalRepresentation()
                                    .setLocation(location);
                        }
                    }
                }
            }
            /**
             * now search instances of this entry in this OPD
             */
            if (otherEdge.hasInstanceInOpd(opdID)
                    && myEdge.hasInstanceInOpd(opdID)) {
                Enumeration<Instance> otherInstancesEnum = system
                        .getInstanceInOpd(opd, otherEdge.getId());
                /**
                 * we have the connected instances in this OPD, see if they are
                 * alrady connected. if not then conect them.
                 *
                 */
                boolean found = false;
                while (otherInstancesEnum.hasMoreElements()) {
                    found = false;
                    ConnectionEdgeInstance connectionEdgeInstance = (ConnectionEdgeInstance) otherInstancesEnum
                            .nextElement();

                    Enumeration<Instance> instances = myEdge.getInstances(false);
                    Enumeration<Instance> sourceLinkInstances = linkEntry
                            .getInstances(false);

                    while (instances.hasMoreElements()) {
                        Instance instance = instances.nextElement();

                        if (opd.isContaining(instance)) {
                            while (sourceLinkInstances.hasMoreElements()) {
                                LinkInstance sourceLinkInstance = (LinkInstance) sourceLinkInstances
                                        .nextElement();

                                if (opd.isConnectedByLink(instance,
                                        connectionEdgeInstance,
                                        sourceLinkInstance)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                /**
                                 * add the link here,
                                 */
                                LinkInstance li = null;
                                if (entryIsSource) {
                                    li = myProject.addLink(instance,
                                            connectionEdgeInstance, linkEntry
                                            .getLinkType(), opd);
                                } else {
                                    li = myProject.addLink(
                                            connectionEdgeInstance, instance,
                                            linkEntry.getLinkType(), opd);
                                }
                                if (li != null) {
                                    ((LinkEntry) li.getEntry()).setResourceConsumption(linkEntry.getResourceConsumption());
                                    //((LinkEntry)li.getEntry()).setResourceConsumption(linkEntry.getResourceConsumption());
                                    Opcat2
                                            .getUndoManager()
                                            .undoableEditHappened(
                                                    new UndoableEditEvent(
                                                            this,
                                                            new UndoableAddLink(
                                                                    myProject,
                                                                    li)));
                                    Opcat2.setUndoEnabled(Opcat2
                                            .getUndoManager().canUndo());
                                    Opcat2.setRedoEnabled(Opcat2
                                            .getUndoManager().canRedo());
                                }

                            }
                            break;
                        }
                    }
                }
            }
        }

    }

    public String getTypeString() {
        return "Connection Edge";
    }
}
