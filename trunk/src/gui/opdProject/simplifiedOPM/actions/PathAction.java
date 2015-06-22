package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;
import y.algo.Paths;
import y.base.EdgeList;
import y.base.Node;
import y.base.NodeList;
import y.view.LineType;
import y.view.NodeRealizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 13:59
 */
public class PathAction extends AbstractAction {

    OpdSimplifiedView view;
    NodeList nList;
    Color[] colors;
    LineType[] lines;

    boolean active = false;

    public PathAction(OpdSimplifiedView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (view.getGraph2D().selectedNodes().ok()) {

        NodeList nl = new NodeList(view.getGraph2D().selectedNodes());

        if (nl.size() == 2) {

            active = !active;

            if (active) {

                EdgeList el = Paths.findPath(view.getGraph2D(), (Node) nl.get(0), (Node) nl.get(1), true);
                if (el.isEmpty()) {
                    el = Paths.findPath(view.getGraph2D(), (Node) nl.get(1), (Node) nl.get(0), true);
                }

                this.nList = Paths.constructNodePath(el);

                if (this.nList.size() > 0) {
                    colors = new Color[this.nList.size()];
                    lines = new LineType[this.nList.size()];

                }

                for (int i = 0; i < this.nList.size(); i++) {
                    NodeRealizer er = view.getGraph2D().getRealizer((Node) this.nList.get(i));
                    colors[i] = er.getLineColor();
                    lines[i] = er.getLineType();
                    er.setLineColor(Color.blue);
                    er.setLineType(LineType.DASHED_3);
                    view.repaint();
                }
            }

        }

        if (!active && (this.nList != null)) {
            for (int i = 0; i < this.nList.size(); i++) {
                NodeRealizer er = view.getGraph2D().getRealizer((Node) this.nList.get(i));
                er.setLineColor(colors[i]);
                er.setLineType(lines[i]);
                view.repaint();
            }
        }

        //}

        //view.getGraph().

    }
}
