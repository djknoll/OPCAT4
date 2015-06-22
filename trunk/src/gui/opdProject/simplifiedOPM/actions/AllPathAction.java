package gui.opdProject.simplifiedOPM.actions;

import gui.opdProject.simplifiedOPM.comparators.ValueComparatorDouble;
import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opmEntities.OpmProcess;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkEntry;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ProcessInstance;
import util.MathUtils;
import y.algo.Paths;
import y.base.*;
import y.view.EdgeRealizer;
import y.view.LineType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by raanan.
 * Date: 07/07/11
 * Time: 13:59
 */
public class AllPathAction extends AbstractAction {

    private OpdSimplifiedView view;
    private Color[] colors;
    private LineType[] lines;
    private String[] lables;
    //private EdgeMap em; //2.6.1
    private YCursor paths; //2.8

    boolean active = false;

    public AllPathAction(OpdSimplifiedView view) {
        this.view = view;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if (view.getGraph2D().selectedNodes().ok()) {

        NodeList nl = new NodeList(view.getGraph2D().selectedNodes());

        if (!active) {

            if (nl.size() == 2) {

                //em = view.getGraph2D().createEdgeMap();
                //Paths.findAllPaths(view.getGraph2D(), (Node) nl.get(0), (Node) nl.get(1), em); //2.6.1
                paths = y.algo.Paths.findAllPathsCursor(view.getGraph2D(), (Node) nl.get(0), (Node) nl.get(1), true);
                if ((paths == null) || !(paths.ok())) {
                    paths = Paths.findAllPathsCursor(view.getGraph2D(), (Node) nl.get(1), (Node) nl.get(0), true);

                }

//                2.6.1
//                boolean ok = false;
//                for (Object obj : view.getGraph2D().getEdgeList()) {
//                    Edge edge = (Edge) obj;
//                    if (em.getBool(edge)) {
//                        ok = true;
//                        break;
//                    }
//                }
//
//                if (!ok) {
//
//                    em = view.getGraph2D().createEdgeMap();
//                    Paths.findAllPaths(view.getGraph2D(), (Node) nl.get(1), (Node) nl.get(0), em);
//                }


                //2.8
                if ((paths != null) && (paths.ok())) {

                    colors = new Color[view.getGraph2D().E()];
                    lines = new LineType[view.getGraph2D().E()];
                    lables = new String[view.getGraph2D().E()];

                    for (int i = 0; i < view.getGraph2D().E(); i++) {
                        EdgeRealizer er = view.getGraph2D().getRealizer(view.getGraph2D().getEdgeArray()[i]);
                        colors[i] = er.getLineColor();
                        lines[i] = er.getLineType();
                        lables[i] = er.getLabelText();
                        er.setLabelText("");
                    }


                    int j = 1;

                    HashMap<Integer, Double> mul = new HashMap<Integer, Double>();

                    HashMap<Integer, Double> add = new HashMap<Integer, Double>();

                    HashMap<Integer, Color> colors = new HashMap<Integer, Color>();
                    Random numGen = new Random();

                    while (paths.ok()) {
                        EdgeList el = (EdgeList) paths.current();
                        Color col = new Color(numGen.nextInt(256), numGen.nextInt(256), numGen.nextInt(256));
                        colors.put(j, col);

                        add.put(Integer.valueOf(j), Double.valueOf(0.0));

                        NodeList nodeList = Paths.constructNodePath(el);
                        for (Object obj : nodeList) {
                            Node node = (Node) obj;
                            Instance ins = (Instance) view.getNodeToInstanceMap().get(node);
                            if (ins instanceof ProcessInstance) {
                                add.put(j, add.get(j) + ((OpmProcess) ins.getEntry().getLogicalEntity()).getMinActivationTimeInmSec());
                            }

                        }


                        mul.put(Integer.valueOf(j), Double.valueOf(1.0));
                        paths.next();
                        for (int i = 0; i < el.size(); i++) {
                            Edge edge = (Edge) el.get(i);
                            EdgeRealizer er = view.getGraph2D().getRealizer(edge);
                            er.setLineType(LineType.DASHED_3);
                            er.setLineColor(Color.blue);
                            er.setLabelText(er.getLabelText() + "<FONT size=\"5\" COLOR=rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")>" + j + "</FONT>,");
                            Instance ins = (Instance) view.getEdgeToInstanceMap().get(edge);
                            if ((ins instanceof LinkInstance)) { // && (((LinkEntry) ((LinkInstance) ins).getEntry()).getLinkType() == OpcatConstants.RESULT_LINK)) {
                                LinkEntry link = (LinkEntry) ((LinkInstance) ins).getEntry();
                                Double prec = 1.0;
                                if ((link.getPath() != null) && (link.getPath().endsWith("%"))) {
                                    String str = link.getPath().substring(0, link.getPath().length() - 1);
                                    try {
                                        prec = Double.valueOf(str) / 100.0;
                                    } catch (Exception ex) {
                                        prec = 1.0;
                                    }
                                }
                                mul.put(j, mul.get(j) * prec);
                            }


                        }
                        j++;
                    }
                    active = true;


                    for (int i = 0; i < view.getGraph2D().E(); i++) {
                        EdgeRealizer er = view.getGraph2D().getRealizer(view.getGraph2D().getEdgeArray()[i]);
                        if (er.getLabelText().endsWith(",")) {
                            er.setLabelText(er.getLabelText().substring(0, er.getLabelText().length() - 1));
                        }
                        er.setLabelText("<html><body>" + er.getLabelText() + "</body></html>");
                    }


                    String addOut = "<html><body><font size = 6 ><h2><u>Path data (time,probability,cost)</u></h2></font><br>";
                    ValueComparatorDouble bvc = new ValueComparatorDouble(add);
                    TreeMap<Integer, Double> sorted_add = new TreeMap(bvc);

                    for (Integer i : add.keySet()) {
                        sorted_add.put(i, add.get(i));
                    }

                    for (Integer i : sorted_add.keySet()) {
                        Color col = colors.get(i);
                        addOut = addOut + "<FONT size = 5 COLOR=rgb(" + col.getRed() + "," + col.getGreen() + "," + col.getBlue() + ")>" + "<b>Path " + i + " : </b><t></font><FONT size = 5>" + ((Double) (add.get(i) / (1000.0 * 60.0))).longValue() + " minutes , " + (mul.get(i) == 1.0 ? "NA" : MathUtils.round2((Double) (mul.get(i) * 100.0)) + " %") + " , $ NA" + "</font><br>";
                        i++;
                    }
                    addOut = addOut + "<body><html>";
                    view.setAddText(addOut);

                    //JOptionPane.showMessageDialog(view, addOut);

                    //String mulOut = "Path Probability\n" ;
                    //for(Integer i : mul.keySet()) {
                    //    mulOut = mulOut + "Path " + i + " : " + mul.get(i) + "\n" ;
                    //}
                    //JOptionPane.showMessageDialog(view, addOut);

                    view.getGraph2D().updateViews();
                    view.updateView();
                    return;

                }

//2.6.1
//                for (int i = 0; i < view.getGraph2D().E(); i++) {
//                    Edge edge = view.getGraph2D().getEdgeArray()[i];
//                    if (em.getBool(edge)) {
//                        EdgeRealizer er = view.getGraph2D().getRealizer(edge);
//                        er.setLineColor(Color.blue);
//                        er.setLineType(LineType.DASHED_3);
//                        view.repaint();
//                    }
//                }

            }

        }

        if (active) {
            for (int i = 0; i < view.getGraph2D().E(); i++) {
                EdgeRealizer er = view.getGraph2D().getRealizer(view.getGraph2D().getEdgeArray()[i]);
                er.setLineColor(colors[i]);
                er.setLineType(lines[i]);
                er.setLabelText(lables[i]);
            }
            active = false;
            view.getGraph2D().updateViews();

        }

    }
}
