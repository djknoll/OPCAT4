package gui.opdProject.simplifiedOPM.realizers;

import gui.opmEntities.OpmProcess;
import gui.projectStructure.*;
import y.view.Graph2DView;
import y.view.NodeLabel;
import y.view.NodeRealizer;
import y.view.ShapeNodeRealizer;

import java.awt.*;
import java.util.Map;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 13:29
 */
public class OPMNodeRealizer {

    Instance instance;
    Entry entry;
    String addedData;
    NodeRealizer my;

    public OPMNodeRealizer(Instance instance, Map<String, String> addedData) {
        super();
        this.instance = instance;
        this.entry = instance.getEntry();
        this.addedData = calcDataString(addedData);
        my = new ShapeNodeRealizer();
        init();
    }

    public void setShapeType(byte type) {
        ((ShapeNodeRealizer) my).setShapeType(type);
    }

    public OPMNodeRealizer() {
        super();
        my = new ShapeNodeRealizer();
    }


    static public String getSimplifiedEntryName(Instance ins) {

        String ret = "";
        if (ins == null) return ret;
        if (ins instanceof StateInstance) {
            ret = (MainStructure.fixName((((StateInstance) ins).getParentObjectInstance().getThingName())) + " < " + MainStructure.fixName(ins.getEntry().getName()) + " >");
        } else {
            ret = MainStructure.fixName(ins.getEntry().getName());
        }

        return MainStructure.fixName(ret);
    }

    private String calcDataString(Map<String, String> addedDat) {

        String str = "";
        if (addedDat.size() > 0) {
            for (String key : addedDat.keySet()) {
                //adddedData = "Data : ";
                String property = addedDat.get(key);

                String entProp = entry.getMyProps().getProperty(property);
                String logicalProp = entry.getLogicalEntity().getMyProps().getProperty(property);


                if (property.equalsIgnoreCase("entity.opm.MinTimeActivation")) {
                    entProp = null;
                    if (entry instanceof ProcessEntry) {
                        String time = ((ProcessEntry) entry).getMinTimeActivation();
                        if (time.equalsIgnoreCase(OpmProcess.DEFAULT_MIN_TIME_ACTIBVATION)) {
                            logicalProp = null;
                        } else {
                            logicalProp = ((OpmProcess) entry.getLogicalEntity()).getMinTimeActivationString();
                        }
                    }
                }

                if (property.equalsIgnoreCase("entity.opm.MaxTimeActivation")) {
                    entProp = null;
                    if (entry instanceof ProcessEntry) {
                        String time = ((ProcessEntry) entry).getMaxTimeActivation();
                        if (time.equalsIgnoreCase(OpmProcess.DEFAULT_MAX_TIME_ACTIBVATION)) {
                            logicalProp = null;
                        } else {
                            logicalProp = ((OpmProcess) entry.getLogicalEntity()).getMaxTimeActivationString();
                        }
                    }
                }


                if (entProp != null) {
                    str = str + "\n" + (key.trim().equalsIgnoreCase("") ? "" : key + ": ") + entProp;
                }

                if (logicalProp != null) {
                    str = str + "\n" + (key.trim().equalsIgnoreCase("") ? "" : key + ": ") + logicalProp;
                }

            }
        }
        return str;
    }

    protected void init() {

        my.setFillColor(instance.getBackgroundColor());
        my.setLocation(instance.getGraphicalRepresentation().getX(), instance.getGraphicalRepresentation().getY());

        my.setLabelText(getSimplifiedEntryName(instance));
        my.getLabel().setAlignment(NodeLabel.ALIGN_CENTER);
        my.getLabel().setBackgroundColor(instance.getBackgroundColor());
        my.getLabel().setTextColor(instance.getTextColor());

        double labH = my.getLabel().getHeight();
        double labW = my.getLabel().getWidth();

        //boolean  hasExtraData = (entry.getCurrentMetaData().equalsIgnoreCase("") && addedData.equalsIgnoreCase(""))  ;

        if (!(entry.getCurrentMetaData().equalsIgnoreCase(""))) {
            NodeLabel metaData = new NodeLabel("<html><center>" + (entry.getCurrentMetaData().equalsIgnoreCase("") ? "" : "\n" + entry.getCurrentMetaData()).replace("\n", "<br>") + "</center>" + "</html>", NodeLabel.INTERNAL);
            metaData.setPosition(NodeLabel.TOP);
            metaData.setDistance(1);
            my.addLabel(metaData);
            labH = labH + metaData.getHeight();
            labW = labW + metaData.getWidth();
        }

        if (!(addedData.equalsIgnoreCase(""))) {
            NodeLabel extraData = new NodeLabel("<html><center>" + (addedData.equalsIgnoreCase("") ? "" : addedData).replace("\n", "<br>") + "</center>" + "</html>", NodeLabel.INTERNAL);
            extraData.setPosition(NodeLabel.BOTTOM);
            extraData.setDistance(1);
            my.addLabel(extraData);
            labH = labH + extraData.getHeight();
            labW = labW + extraData.getWidth();
        }

        if (!instance.isMainInstance()) {
            double factor = 1.2;
            double w = (labW > instance.getGraphicalRepresentation().getWidth() ? labW * factor : instance.getGraphicalRepresentation().getWidth());
            double h = (labH > instance.getGraphicalRepresentation().getHeight() ? labH * factor : instance.getGraphicalRepresentation().getHeight());
            my.setSize(w, h);
        } else {
            double factor = 1.2;
            my.setLineColor(Color.GREEN);
            my.setSize(factor * labW, factor * labW);
        }

        my.setLayer(Graph2DView.BG_LAYER);

    }

    public NodeRealizer getRealizer() {
        return my;
    }
}
