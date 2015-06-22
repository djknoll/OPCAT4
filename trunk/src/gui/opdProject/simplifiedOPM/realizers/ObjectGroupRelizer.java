package gui.opdProject.simplifiedOPM.realizers;

import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import y.view.NodeLabel;
import y.view.ShapeNodeRealizer;
import y.view.hierarchy.GroupNodeRealizer;

import java.awt.*;
import java.util.Map;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 13:23
 */
public class ObjectGroupRelizer extends OPMNodeRealizer {


    Instance instance;
    Entry entry;
    String addedData;

    public ObjectGroupRelizer(Instance instance, Map<String, String> addedData) {
        super(instance, addedData);
        my = new GroupNodeRealizer();
        setShapeType(ShapeNodeRealizer.RECT);
        init();
        my.getLabel().setAlignment(NodeLabel.ALIGN_CENTER);
        my.getLabel().setBackgroundColor(my.getFillColor());
        my.getLabel().setTextColor(Color.black);
    }

}
