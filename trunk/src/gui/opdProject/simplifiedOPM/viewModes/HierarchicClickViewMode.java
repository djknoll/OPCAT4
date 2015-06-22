package gui.opdProject.simplifiedOPM.viewModes;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.realizers.OPMNodeRealizer;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import util.MathUtils;
import y.base.*;
import y.geom.YPoint;
import y.layout.LayoutTool;
import y.view.*;
import y.view.hierarchy.GroupNodeRealizer;
import y.view.hierarchy.HierarchyManager;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class HierarchicClickViewMode extends ViewMode {

    protected HierarchyManager hierarchy;

    public HierarchicClickViewMode(HierarchyManager hierarchy) {
        this.hierarchy = hierarchy;
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Node v = getHitInfo(e).getHitNode();
            if (v != null) {
                navigateToInnerGraph(v);
            } else {
                navigateToParentGraph();
            }
        } else {
            Node v = getHitInfo(e).getHitNode();
            if (v != null && !hierarchy.isNormalNode(v)) {
                double x = translateX(e.getX());
                double y = translateY(e.getY());
                Graph2D graph = this.view.getGraph2D();
                NodeRealizer r = graph.getRealizer(v);
                GroupNodeRealizer gnr = null;
                if (r instanceof GroupNodeRealizer) {
                    gnr = (GroupNodeRealizer) r;
                } else if (r instanceof ProxyShapeNodeRealizer &&
                        ((ProxyShapeNodeRealizer) r).getRealizerDelegate() instanceof GroupNodeRealizer) {
                    gnr = (GroupNodeRealizer) ((ProxyShapeNodeRealizer) r).getRealizerDelegate();
                }
                if (gnr != null) {
                    NodeLabel handle = gnr.getStateLabel();
                    if (handle.getBox().contains(x, y)) {
                        if (hierarchy.isFolderNode(v)) {
                            openFolder(v);
                        } else {
                            closeGroup(v);
                        }
                    }
                }
            } else {
                //if (v != null) {
                //non group node
                OpdSimplifiedView opmView = (OpdSimplifiedView) view;
                opmView.initDataPanelData();
                double add = 0;
                double mul = 1;
                NodeList nl = new NodeList(opmView.getGraph2D().selectedNodes());
                if (nl.size() == 1) {
                    if (opmView.getGraph2D().selectedNodes().ok()) {
                        Node node = opmView.getGraph2D().selectedNodes().node();
                        Instance ins = (Instance) ((OpdSimplifiedView) view).getNodeToInstanceMap().get(node);
                        String str = "<html><body>";
                        //str = str + "<h2><font size = 6 >Properties</font></h2><br>";
                        str = str + "<font size = 5 ><b>Name : </b>" + OPMNodeRealizer.getSimplifiedEntryName(ins) + "<br>";
                        str = str + (ins.getEntry().getDescription() != null && !ins.getEntry().getDescription().equalsIgnoreCase("none") ? "<br><b>Description : </b><t>" + ins.getEntry().getDescription().replace("\n", "<br>") + "<br>" : "");
                        str = str + (ins.getEntry().getProperty("entry.general.notes") != null && !ins.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none") ? "<b>Notes : </b><t>" + ins.getEntry().getProperty("entry.general.notes").replace("\n", "<br>") + "<br>" : "");
                        str = str + (ins.getEntry().getLogicalEntity() instanceof OpmProcess ? "<b>Time (minutes) : </b><t>" + ((OpmProcess) (ins.getEntry().getLogicalEntity())).getMinActivationTimeInmSec() / (1000.0 * 60.0) + "<br>" : "");
                        for (Object key : ins.getEntry().getMyProps().keySet()) {
                            if (key.toString().startsWith("entry.extdata.")) {
                                String name = key.toString().substring( key.toString().lastIndexOf('.') + 1  , key.toString().length());
                                String value = ins.getEntry().getMyProps().getProperty(key.toString());
                                str = str + "<b>" + name + " : </b>" + MathUtils.round2(Double.valueOf(value)) + "<br>";
                            }

                        }
                        str = str + "</body></html>";
                        ((OpdSimplifiedView) view).setAddText(str);
                    }
                }

                EdgeList el = new EdgeList(opmView.getGraph2D().selectedEdges());
                if (el.size() == 1) {
                    if (opmView.getGraph2D().selectedEdges().ok()) {
                        Edge edge = opmView.getGraph2D().selectedEdges().edge();
                        NodeRealizer sourceRe = opmView.getGraph2D().getRealizer(edge.source());
                        NodeRealizer targetRe = opmView.getGraph2D().getRealizer(edge.target());

                        Instance ins = (Instance) ((OpdSimplifiedView) view).getEdgeToInstanceMap().get(edge);
                        String str = "<html><body>";
                        //str = str + "<h2><font size = 6 >Properties</font></h2><br>";
                        str = str + "<font size = 5 ><b>From </b>" + sourceRe.getLabelText() + "<b> To </b>" + targetRe.getLabelText() + "<br>";
                        str = str + (ins.getEntry().getDescription() != null && !ins.getEntry().getDescription().equalsIgnoreCase("none") && !ins.getEntry().getDescription().trim().equalsIgnoreCase("") ? "<br><b>Description : </b><t>" + ins.getEntry().getDescription().replace("\n", "<br>") + "<br>" : "");
                        str = str + (ins.getEntry().getProperty("entry.general.notes") != null && !ins.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none") ? "<b>Notes : </b><t>" + ins.getEntry().getProperty("entry.general.notes").replace("\n", "<br>") + "<br>" : "");
                        //str = str + (ins.getEntry().getLogicalEntity() instanceof OpmProcess ? "<b>Time (minutes) : </b><t>" + ((OpmProcess) (ins.getEntry().getLogicalEntity())).getMinActivationTimeInmSec() / (1000.0 * 60.0) + "<br>" : "");
                        str = str + "</body></html>";
                        ((OpdSimplifiedView) view).setAddText(str);
                    }
                }

//                    for (Object obj : nl) {
//                        Node node = (Node) obj;
//                        Instance nodeInstance = (Instance) opmView.getNodeToInstanceMap().get(node);
//                        if (nodeInstance != null) {
//                            if (nodeInstance instanceof ProcessInstance)
//                                add = add + ((OpmProcess) (((ProcessInstance) nodeInstance).getEntry()).getLogicalEntity()).getMinActivationTimeInmSec();
//                        }
//                    }
//
//                    opmView.addAdditionData(add);
//                    EdgeList el = new EdgeList(opmView.getGraph2D().selectedEdges());
//
//                    for (Object obj : el) {
//                        Edge edge = (Edge) obj;
//                        Instance edgeInstance = (Instance) opmView.getEdgeToInstanceMap().get(edge);
//                        if (edgeInstance != null) {
//
//                        }
//                    }
                //}
            }
        }
    }


    /**
     * navigates to the graph inside of the given folder node
     */
    public void navigateToInnerGraph(Node folderNode) {
        if (hierarchy.isFolderNode(folderNode)) {
            Graph2D innerGraph = (Graph2D) hierarchy.getInnerGraph(folderNode);
            Rectangle box = innerGraph.getBoundingBox();
            view.setGraph2D(innerGraph);
            view.setCenter(box.x + box.width / 2, box.y + box.height / 2);
            innerGraph.updateViews();
        }
    }

    /**
     * navigates to the parent graph of the graph currently displayed
     * in the graph view.
     */
    public void navigateToParentGraph() {
        Graph2D graph = view.getGraph2D();
        if (!hierarchy.isRootGraph(graph)) {
            Graph2D parentGraph = (Graph2D) hierarchy.getParentGraph(graph);
            view.setGraph2D(parentGraph);
            Node anchor = hierarchy.getAnchorNode(graph);
            view.setZoom(1.0);
            view.setCenter(parentGraph.getCenterX(anchor), parentGraph.getCenterY(anchor));
            view.getGraph2D().updateViews();
        }


    }

    protected void openFolder(Node folderNode) {
        Graph2D graph = view.getGraph2D();

        final double w = graph.getWidth(folderNode);
        final double h = graph.getHeight(folderNode);

        NodeList folderNodes = new NodeList();
        if (folderNode == null) {
            //use selected top level groups
            for (NodeCursor nc = graph.selectedNodes(); nc.ok(); nc.next()) {
                Node v = nc.node();
                if (hierarchy.isFolderNode(v)) {
                    folderNodes.add(v);
                }
            }
        } else {
            folderNodes.add(folderNode);
        }

        graph.firePreEvent();

        for (NodeCursor nc = folderNodes.nodes(); nc.ok(); nc.next()) {
            //get original location of folder node
            Graph2D innerGraph = (Graph2D) hierarchy.getInnerGraph(nc.node());
            YPoint folderP = graph.getLocation(nc.node());
            NodeList innerNodes = new NodeList(innerGraph.nodes());

            hierarchy.openFolder(nc.node());

            //get new location of group node
            Rectangle2D.Double gBox = graph.getRealizer(nc.node()).getBoundingBox();
            //move grouped nodes to former location of folder node
            LayoutTool.moveSubgraph(graph, innerNodes.nodes(),
                    folderP.x - gBox.x,
                    folderP.y - gBox.y);
        }
        graph.firePostEvent();


        graph.unselectAll();
        for (NodeCursor nc = folderNodes.nodes(); nc.ok(); nc.next()) {
            graph.setSelected(nc.node(), true);
        }

        // if the node size has changed, delete source ports of out-edges
        // and target ports of in-edges to ensure that all edges still connect
        // to the node
        if (w != graph.getWidth(folderNode) || h != graph.getHeight(folderNode)) {
            for (EdgeCursor ec = folderNode.outEdges(); ec.ok(); ec.next()) {
                graph.setSourcePointRel(ec.edge(), YPoint.ORIGIN);
            }
            for (EdgeCursor ec = folderNode.inEdges(); ec.ok(); ec.next()) {
                graph.setTargetPointRel(ec.edge(), YPoint.ORIGIN);
            }
        }

        graph.updateViews();
    }

    protected void closeGroup(Node groupNode) {
        Graph2D graph = view.getGraph2D();

        final double w = graph.getWidth(groupNode);
        final double h = graph.getHeight(groupNode);

        NodeList groupNodes = new NodeList();
        if (groupNode == null) {
            //use selected top level groups
            for (NodeCursor nc = graph.selectedNodes(); nc.ok(); nc.next()) {
                Node v = nc.node();
                if (hierarchy.isGroupNode(v) && hierarchy.getLocalGroupDepth(v) == 0) {
                    groupNodes.add(v);
                }
            }
        } else {
            groupNodes.add(groupNode);
        }

        graph.firePreEvent();
        for (NodeCursor nc = groupNodes.nodes(); nc.ok(); nc.next()) {
            hierarchy.closeGroup(nc.node());
        }
        graph.firePostEvent();

        graph.unselectAll();
        for (NodeCursor nc = groupNodes.nodes(); nc.ok(); nc.next()) {
            graph.setSelected(nc.node(), true);
        }

        // if the node size has changed, delete source ports of out-edges
        // and target ports of in-edges to ensure that all edges still connect
        // to the node
        if (w != graph.getWidth(groupNode) || h != graph.getHeight(groupNode)) {
            for (EdgeCursor ec = groupNode.outEdges(); ec.ok(); ec.next()) {
                graph.setSourcePointRel(ec.edge(), YPoint.ORIGIN);
            }
            for (EdgeCursor ec = groupNode.inEdges(); ec.ok(); ec.next()) {
                graph.setTargetPointRel(ec.edge(), YPoint.ORIGIN);
            }
        }

        graph.updateViews();
    }

}