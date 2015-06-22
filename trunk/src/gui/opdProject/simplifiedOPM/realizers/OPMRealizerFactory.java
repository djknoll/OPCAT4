package gui.opdProject.simplifiedOPM.realizers;

import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedViewParams;
import gui.projectStructure.Instance;
import gui.projectStructure.ObjectInstance;
import gui.projectStructure.ProcessInstance;
import gui.projectStructure.StateInstance;
import y.view.NodeRealizer;
import y.view.ShapeNodeRealizer;

import java.util.Map;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 15:52
 */
public class OPMRealizerFactory {

    public static NodeRealizer getRealizer(Instance ins, Map<String, String> addedData, OpdSimplifiedViewParams params) {

        if (ins instanceof ProcessInstance) {
            OPMNodeRealizer nr = new ProcessRealizer(ins, addedData);
            return nr.getRealizer();
        }

        if (ins instanceof ObjectInstance) {
            OPMNodeRealizer nr;
            if (params.isObjectsAsGroups()) {
                nr = new ObjectGroupRelizer(ins, addedData);
            } else {
                nr = new ObjectRelizer(ins, addedData);
            }
            return nr.getRealizer();
        }


        if (ins instanceof StateInstance) {
            OPMNodeRealizer nr = new StateRealizer(ins, addedData);
            return nr.getRealizer();
        }


        return new ShapeNodeRealizer(ShapeNodeRealizer.HOTSPOT_NONE);


    }


}
