package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;
import y.base.Node;
import y.base.NodeList;
import y.base.YCursor;
import y.view.YLabel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 10:53
 */
public class NeigboursAction extends AbstractAction {

    GroupUtils utils;
    OpdSimplifiedView view;

    public NeigboursAction(OpdSimplifiedView view) {
        this.utils = view.getGroupUtils();
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (view.getGraph2D().selectedNodes().ok()) {

        if (view.getAllPathAction().active) {
            view.getAllPathAction().actionPerformed(null);
        }

        if (view.getPathAction().active) {
            view.getPathAction().actionPerformed(null);
        }

        NodeList nl = new NodeList(utils.getView().getGraph2D().selectedNodes());

        NodeList subNodes = new NodeList();
        for (Object obj : nl) {
            Node node = (Node) obj;
            subNodes.add(node);

            NodeList list = new NodeList(node.neighbors());
            for (Object nObj : list) {
                if (!subNodes.contains(nObj)) subNodes.add(nObj);
            }
        }

        utils.getView().getGraph2D().setSelected(utils.getView().getGraph2D().selectedEdges(), false);
        utils.getView().getGraph2D().setSelected(utils.getView().getGraph2D().selectedNodes(), false);
        YCursor cur = utils.getView().getGraph2D().selectedLabels();
        while (cur.ok()) {
            utils.getView().getGraph2D().setSelected((YLabel) cur.current(), false);
            cur.next();
        }


        Node root = utils.getView().getHm().createGroupNode(utils.getView().getGraph2D());
        utils.getView().getHm().groupSubgraph(subNodes, root);

        utils.down(root);

        utils.getView().doGraphLayout();

        utils.getView().getGraph2D().updateViews();
        utils.getView().repaint();


    }
}
