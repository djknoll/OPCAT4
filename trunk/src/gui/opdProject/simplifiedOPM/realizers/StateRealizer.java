package gui.opdProject.simplifiedOPM.realizers;

import gui.projectStructure.Instance;
import gui.projectStructure.StateInstance;
import y.view.ShapeNodeRealizer;

import java.util.Map;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 13:17
 */
public class StateRealizer extends OPMNodeRealizer {

    StateInstance instance;


    public StateRealizer(Instance instance, Map<String, String> addedData) {
        super(instance, addedData);
        setShapeType(ShapeNodeRealizer.RECT);
    }


}
