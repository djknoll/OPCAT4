package gui.opdProject.simplifiedOPM.yWorksHelpers;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.viewModes.HierarchicClickViewMode;
import y.base.Node;
import y.base.NodeCursor;
import y.base.NodeList;
import y.geom.YPoint;
import y.view.Graph2D;
import y.view.NodeRealizer;
import y.view.ProxyShapeNodeRealizer;
import y.view.hierarchy.HierarchyManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 10:57
 */
public class GroupUtils {
    OpdSimplifiedView view;
    HierarchicClickViewMode hcm;
    private Set openGroupNodes = new HashSet();


    public OpdSimplifiedView getView() {
        return view;
    }

    public GroupUtils(HierarchicClickViewMode hcm, OpdSimplifiedView view) {
        this.hcm = hcm;
        this.view = view;
    }

    public void groupSelection(double x, double y) {

        Graph2D graph = getView().getGraph2D();

        if (graph.selectedNodes() != null)
            if (graph.selectedNodes().node() != null) {


                graph.firePreEvent();

                NodeList subNodes = new NodeList(graph.selectedNodes());
                Node groupNode;
                if (subNodes.isEmpty()) {
                    groupNode = view.getHm().createFolderNode(graph);
                    if (Double.isNaN(x) || Double.isNaN(y)) {
                        x = view.getCenter().getX();
                        y = view.getCenter().getY();
                    }
                    graph.setCenter(groupNode, x, y);
                } else {
                    Node nca = view.getHm().getNearestCommonAncestor(subNodes);
                    groupNode = view.getHm().createFolderNode(nca);

                    view.getHm().foldSubgraph(new NodeList(graph.selectedNodes()), groupNode);
                }
                assignGroupName(graph, groupNode);

                graph.firePostEvent();
                //do not optimize.
                graph.unselectAll();
                graph.setSelected(groupNode, true);

                graph.updateViews();


            }
    }

    protected void assignGroupName(Graph2D graph, Node groupNode) {
        NodeRealizer nr = graph.getRealizer(groupNode);
        if (nr instanceof ProxyShapeNodeRealizer) {
            ProxyShapeNodeRealizer pnr = (ProxyShapeNodeRealizer) nr;
            pnr.getRealizer(0).setLabelText(createGroupName(groupNode));
            pnr.getRealizer(1).setLabelText(createFolderName(groupNode));
        } else {
            nr.setLabelText(createGroupName(groupNode));
        }
    }


    protected String createGroupName(Node groupNode) {
        int groupCount = 0;
        for (NodeCursor nc = view.getGraph2D().nodes(); nc.ok(); nc.next()) {
            Node v = nc.node();
            if (!view.getHm().isNormalNode(v)) {
                groupCount++;
            }
        }
        return "Group " + groupCount;
    }

    protected String createFolderName(Node groupNode) {
        int groupCount = 0;
        for (NodeCursor nc = view.getGraph2D().nodes(); nc.ok(); nc.next()) {
            Node v = nc.node();
            if (!view.getHm().isNormalNode(v)) {
                groupCount++;
            }
        }
        return "Folder " + groupCount;
    }

    public void up() {

        //if (hcm != null) {
        //    hcm.navigateToParentGraph();
        //} else {
        Graph2D graph = view.getGraph2D();
        HierarchyManager hierarchy = view.getHm();
        if (!hierarchy.isRootGraph(graph)) {
            Graph2D parentGraph = (Graph2D) hierarchy.getParentGraph(graph);
            view.setGraph2D(parentGraph);
            Node anchor = hierarchy.getAnchorNode(graph);

            if (openGroupNodes.contains(anchor)) //has been closed by calliing down()
            {
                openFolder(anchor);
                openGroupNodes.remove(anchor);
            }

            view.setZoom(1.0);
            view.setCenter(parentGraph.getCenterX(anchor), parentGraph.getCenterY(anchor));
            parentGraph.unselectAll();
            parentGraph.setSelected(anchor, true);
            view.updateView();
        }
        //}
        view.getGraph2D().fitGraph2DView();
        view.repaint();
    }

    public void down(Node v) {

        HierarchyManager hierarchy = view.getHm();

        //if ((hierarchy.isFolderNode(v)) && (hcm != null)) {
        //    hcm.navigateToInnerGraph(v);
        //} else {

        if (v == null) {
            NodeCursor nc = view.getGraph2D().selectedNodes();
            if (nc.size() == 1) {
                v = nc.node();
            }
        }

        if (hierarchy.isGroupNode(v)) {
            hierarchy.closeGroup(v);
            openGroupNodes.add(v);
        }
        if (hierarchy.isFolderNode(v)) {
            Graph2D innerGraph = (Graph2D) hierarchy.getInnerGraph(v);
            Rectangle box = innerGraph.getBoundingBox();
            view.setGraph2D(innerGraph);
            view.setCenter(box.x + box.width / 2, box.y + box.height / 2);
            innerGraph.updateViews();
        }
        //}
        view.getGraph2D().fitGraph2DView();
        view.repaint();
    }

    public void openFolder(Node folderNode) {
        Graph2D graph = view.getGraph2D();
        NodeList folderNodes = new NodeList();
        if (folderNode == null) {
            //use selected top level groups
            for (NodeCursor nc = graph.selectedNodes(); nc.ok(); nc.next()) {
                Node v = nc.node();
                if (view.getHm().isFolderNode(v)) {
                    folderNodes.add(v);
                }
            }
        } else {
            folderNodes.add(folderNode);
        }

        graph.firePreEvent();
        for (NodeCursor nc = folderNodes.nodes(); nc.ok(); nc.next()) {
            //get original location of folder node
            Graph2D innerGraph = (Graph2D) view.getHm().getInnerGraph(nc.node());
            YPoint folderP = graph.getLocation(nc.node());
            NodeList innerNodes = new NodeList(innerGraph.nodes());

            view.getHm().openFolder(nc.node());

            //get new location of group node
            Rectangle2D.Double gBox = graph.getRealizer(nc.node()).getBoundingBox();
            //move grouped nodes to former location of folder node
            y.layout.LayoutTool.moveSubgraph(graph, innerNodes.nodes(), folderP.x - gBox.x, folderP.y - gBox.y);
        }
        graph.firePostEvent();

        graph.unselectAll();
        for (NodeCursor nc = folderNodes.nodes(); nc.ok(); nc.next()) {
            graph.setSelected(nc.node(), true);
        }

        graph.updateViews();

    }

    public void ungroupSelection() {

        Graph2D graph = view.getGraph2D();


        if (graph.selectedNodes() != null)
            if (graph.selectedNodes().node() != null) {

                graph.firePreEvent();
                Node node = graph.selectedNodes().node();
                if (view.getHm().isGroupNode(node) || view.getHm().isFolderNode(node)) {
                    if (view.getHm().isGroupNode(node))
                        view.getHm().ungroupSubgraph(new NodeList(view.getHm().getChildren(node)));
                    if (view.getHm().isFolderNode(node))
                        view.getHm().unfoldSubgraph(graph, new NodeList(view.getHm().getChildren(node)));
                    graph.removeNode(node);
                } else {
                    view.getHm().ungroupSubgraph(new NodeList(graph.selectedNodes()));
                }

                view.doLayout();

                //was ungroupSubgraphX
                graph.firePostEvent();

                graph.updateViews();

                view.getGraph2D().fitGraph2DView();
                view.repaint();

            }
    }
}
