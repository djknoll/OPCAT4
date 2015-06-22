package versions;

import util.OpcatLogger;

import java.util.HashMap;

/**
 * Created by raanan.
 * Date: Mar 21, 2010
 * Time: 3:40:03 PM
 */
public class OPXDiff {

    public static OPXDiffResults diff(OPXModel source, OPXModel target) {


        OPXDiffResults ret = new OPXDiffResults();
        //compare using a diff algorithm

        try {

            //checl nodes in source if exists in target
            //if does not exist then it's new
            for (OPXEntity node : source.getNodes()) {
                boolean needAddToResults = false;

                OPXDiffResult result = new OPXDiffResult(node.getID());

                //not in target
                if (!(target.getNodesMap().containsKey(node.getID()))) {
                    result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET);
                    needAddToResults = true;
                } else {
                    //in target

                    OPXEntity t = target.getEntity(node.getID());
                    HashMap<Object, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES> props = node.compareProperties(t);
                    if (props.keySet().size() > 0) {
                        //we have properties change
                        result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_PROPERTIES);
                        needAddToResults = true;
                    }

                    //check appearances diff
                    //check missing or new no need for change as we get the change at the entity level.
                    for (OPXID instance : node.getAppearances().keySet()) {
                        if (t.getAppearance(instance) == null) {
                            //in source but not in target
                            result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_APPEARANCES);
                            result.addAppearanceChange(instance, OPXDiffResult.OPX_APEARANCES_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET);
                            needAddToResults = true;
                        }
                    }


                    for (OPXID instance : t.getAppearances().keySet()) {
                        if (node.getAppearance(instance) == null) {
                            //appearance in target and not in source
                            result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_APPEARANCES);
                            result.addAppearanceChange(instance, OPXDiffResult.OPX_APEARANCES_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                            needAddToResults = true;
                        }
                    }
                    //end appearances diff

                    //check included diff
                    for (OPXID included : node.getIncludedAppearences()) {
                        if (!(t.getIncludedAppearences().contains(included))) {
                            //exists in not target but in source
                            result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_INCLUDED);
                            result.addIncludedChange(included, OPXDiffResult.OPX_INCLUDED_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET);
                            needAddToResults = true;

                        }
                        if ((t.getIncludedAppearences() != null) && t.getIncludedAppearences().contains(included)) {
                            //check if the apparance location changed order has changed.
                            OPXEntity tEntity = target.getNodesMap().get(included);
                            if ((tEntity != null) && (tEntity.getEntityType() == OPXEntity.OPX_ENTITY_TYPE.STATE)) {
                                //we are dealing with states so and we have both of them so
                                //there is no need to check those
                            } else {
                                OPXInstance tApearance = target.getEntityAppearance(included);
                                OPXInstance sApearance = source.getEntityAppearance(included);
                                if (!(tApearance.getOrder() == sApearance.getOrder())) {
                                    //changed order of apearance
                                    result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_INCLUDED);
                                    result.addIncludedChange(included, OPXDiffResult.OPX_INCLUDED_CHANGES_TYPES.ORDER_CHANGED);
                                    needAddToResults = true;
                                }
                            }


                        }
                    }

                    for (OPXID included : t.getIncludedAppearences()) {
                        if (!(node.getIncludedAppearences().contains(included))) {
                            //included exists in source  but not in target
                            result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_INCLUDED);
                            result.addIncludedChange(included, OPXDiffResult.OPX_INCLUDED_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                            needAddToResults = true;
                        }
                    }
                    // end included diff

                }
                if (needAddToResults) ret.addAtomicResult(node.getID(), result);
            }

            //check things in target6 which are not in source
            for (OPXEntity node : target.getNodes()) {
                if (!(source.getNodesMap().containsKey(node.getID()))) {
                    OPXDiffResult result = new OPXDiffResult(node.getID());
                    result.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                    ret.addAtomicResult(node.getID(), result);
                }
            }


            //check edges.
            //as  there is only one instance of every edge at least now.
            // there is only a need to check the uuid of the edge and uid of source target
            //to check that they are the same.
            for (OPXEdgeEntry edge : source.getEdges()) {
                if (target.getEdgesMap().containsKey(edge.getID())) {
                    //check source target
                    OPXEdgeEntry tEdge = target.getEdgesMap().get(edge.getID());
                    if ((tEdge.getSource() != edge.getSource()) || (tEdge.getTarget() != edge.getTarget())) {
                        //wrong edges bcourse this could not happen as every edge change is a new edge.
                        //edge chenged
                    }
                } else {
                    //edge in source but not in target
                    //need to check all source destination pairs ?
                    OPXDiffResult.OPX_ENTITY_CHANGES_TYPES tEdget = target.getEdgeExsitence(edge.getSource(), edge.getTarget(), edge.getEdgeType());
                    if (tEdget != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_NO_CHANGE) {
                        OPXDiffResult resut = new OPXDiffResult(edge.getID());
                        //edge not in target but in source.
                        if (tEdget != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_CHANGED_TYPE) {
                            resut.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET);
                        } else {
                            resut.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_CHANGED_TYPE);
                            if (target.getEdge(edge.getSource(), edge.getTarget()) != null)
                                resut.setOldType(target.getEdge(edge.getSource(), edge.getTarget()).getEdgeType());
                        }
                        ret.addAtomicResult(edge.getID(), resut);

                    }
                }
            }
            for (OPXEdgeEntry tEdge : target.getEdges()) {
                if (!(source.getEdgesMap().containsKey(tEdge.getID()))) {
                    OPXDiffResult.OPX_ENTITY_CHANGES_TYPES sEdge = source.getEdgeExsitence(tEdge.getSource(), tEdge.getTarget(), tEdge.getEdgeType());
                    if (sEdge != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_NO_CHANGE) {
                        //in target but not in source
                        OPXDiffResult resut = new OPXDiffResult(tEdge.getID());
                        if (sEdge != OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_CHANGED_TYPE) {
                            resut.addEntityChangeType(OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                            ret.addAtomicResult(tEdge.getID(), resut);
                        }
                    }
                }
            }

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }
        return ret;
    }

}
