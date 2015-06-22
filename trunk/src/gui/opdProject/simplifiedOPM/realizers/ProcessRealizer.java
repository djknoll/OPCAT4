package gui.opdProject.simplifiedOPM.realizers;

import gui.projectStructure.Instance;
import y.view.ShapeNodeRealizer;

import java.util.Map;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 13:23
 */
public class ProcessRealizer extends OPMNodeRealizer {

    public ProcessRealizer(Instance instance, Map<String, String> addedData) {
        super(instance, addedData);
        setShapeType(ShapeNodeRealizer.ELLIPSE);
    }
}
