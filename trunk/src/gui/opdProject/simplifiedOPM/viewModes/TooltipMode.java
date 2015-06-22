package gui.opdProject.simplifiedOPM.viewModes;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.realizers.OPMNodeRealizer;
import gui.opdProject.simplifiedOPM.yWorksHelpers.LODRenderingHint;
import gui.projectStructure.Instance;
import y.base.Edge;
import y.base.Node;
import y.view.HitInfo;
import y.view.NodeRealizer;
import y.view.ViewMode;

/**
 * ViewMode responsible for displaying a tooltip for graph elements.
 */
public class TooltipMode extends ViewMode {

    OpdSimplifiedView view;

    public TooltipMode(OpdSimplifiedView view) {
        this.view = view;
    }

    @Override
    public void mousePressedLeft(double v, double v1) {
        super.mousePressedLeft(v, v1);    //To change body of overridden methods use File | Settings | File Templates.

        HitInfo info = getHitInfo(v, v1);
        Node node = info.getHitNode();
        if (node != null) {   //&& getGraph2D().getHierarchyManager().isNormalNode(node)) {

        }

    }

    public void mouseMoved(double x, double y) {
        HitInfo info = getHitInfo(x, y);
        Edge edge = info.getHitEdge();
        Node node = info.getHitNode();
        if (node != null) {   //&& getGraph2D().getHierarchyManager().isNormalNode(node)) {
            Instance e = (Instance) view.getNodeToInstanceMap().get(node);
            Object lod = view.getRenderingHints().get(LODRenderingHint.KEY_LOD);
            if (lod == LODRenderingHint.VALUE_LOD_LOW || lod == LODRenderingHint.VALUE_LOD_MEDIUM) { //tiny
                view.setToolTipText("<html><b>" + OPMNodeRealizer.getSimplifiedEntryName(e) + "</b><br>"
                        + (e.getEntry().getProperty("entry.general.notes") != null && !e.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none") ? "Notes : " + e.getEntry().getProperty("entry.general.notes") : "")
                        + (e.getEntry().getDescription() != null && !e.getEntry().getDescription().equalsIgnoreCase("none") ? "<br><b>Description : </b>" + e.getEntry().getDescription() : ""));
            } else if (lod == LODRenderingHint.VALUE_LOD_HIGH) {
                view.setToolTipText("<html><b>" + OPMNodeRealizer.getSimplifiedEntryName(e) + "</b><br>"
                        + (e.getEntry().getProperty("entry.general.notes") != null && !e.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none") ? "<b>Notes : </b>" + e.getEntry().getProperty("entry.general.notes") : "")
                        + (e.getEntry().getDescription() != null && !e.getEntry().getDescription().equalsIgnoreCase("none") ? "<br><b>Description : </b>" + e.getEntry().getDescription() : ""));
                //NodeLabel stateLabel = view.getGraph2D().getRealizer(node).getLabel(6);
                //if (stateLabel.contains(x, y)) {
                //    view.setToolTipText("Nodes " + e.getEntry().getName());
                //} else {
                //    view.setToolTipText(null);
                //}
            }
        } else {
            view.setToolTipText(null);
        }
        if (edge != null) {
            OpdSimplifiedView view = (OpdSimplifiedView) this.view;
            NodeRealizer sourceRe = view.getGraph2D().getRealizer(edge.source());
            NodeRealizer targetRe = view.getGraph2D().getRealizer(edge.target());

//            Instance ins = (Instance) view.getEdgeToInstanceMap().get(edge);
//
//            if (ins != null) {
//                Instance dest = null;
//                Instance src = null;
//
//                if (ins instanceof LinkInstance) {
//                    dest = ((LinkInstance) ins).getDestinationInstance();
//                    src = ((LinkInstance) ins).getSourceInstance();
//
//                }
//
//                if (ins instanceof GeneralRelationInstance) {
//                    dest = ((GeneralRelationInstance) ins).getDestinationInstance();
//                    src = ((GeneralRelationInstance) ins).getSourceInstance();
//
//                }
//
//                if (ins instanceof FundamentalRelationInstance) {
//                    dest = ((FundamentalRelationInstance) ins).getDestinationInstance();
//                    src = ((FundamentalRelationInstance) ins).getSourceInstance();
//
//                }

            String tip = (sourceRe != null ? sourceRe.getLabelText() + " To " : "");
            tip = tip + (targetRe != null ? targetRe.getLabelText() : "");
            view.setToolTipText("<html><b>" + tip + "</b></html>");
            //}
        }
    }


}