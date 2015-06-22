package gui.projectStructure;

import gui.controls.FileControl;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmConnectionEdge;
import gui.opmEntities.OpmFundamentalRelation;
import gui.opmEntities.OpmStructuralRelation;

import java.util.Enumeration;
import java.util.HashMap;

/**
 * <p/>
 * This class represents OPM fundamental structural relations.
 *
 * @author Stanislav Obydionnov
 * @author Yevgeny Yaroker
 * @version 2.0
 */

public class FundamentalRelationEntry extends RelationEntry {

    /**
     * Creates FundamentalRelationEntry that holds all information about
     * specified pRelation.
     *
     * @param pRelation object of OpmFundamentalRelation class.
     */
    public FundamentalRelationEntry(OpmFundamentalRelation pRelation,
                                    OpdProject project) {
        super(pRelation, project);
    }

    public void updateInstances() {
        if (FileControl.getInstance().isGUIOFF()) return;


        for (Enumeration e = this.getInstances(false); e.hasMoreElements();) {
            FundamentalRelationInstance tempInstance = (FundamentalRelationInstance) (e
                    .nextElement());
            tempInstance.update();
        }

    }

    public String getTypeString() {
        return "Fundamental Relation";
    }

    public static FundamentalRelationEntry hasMutualParent(ConnectionEdgeEntry entry1, ConnectionEdgeEntry entry2) {

        OpmStructuralRelation ret = null;

        HashMap<Entry, OpmStructuralRelation> entry1Parents = entry1.getDirectParents();
        for (Entry entry1Parent : entry1Parents.keySet()) {
            HashMap<Entry, OpmStructuralRelation> entry2Parents = entry2.getDirectParents();
            for (Entry entry2Parent : entry1Parents.keySet()) {
                if (entry2Parent.getId() == entry1Parent.getId()) {
                    ret = entry1Parents.get(entry1Parent);
                    break ;
                }
            }
            if(ret != null) break;
        }

        if(ret != null) {
            return (FundamentalRelationEntry) entry1.getMyProject().getSystemStructure().getEntry(ret.getId());
        }
        return null;
    }
}
