package gui.projectStructure;

import gui.controls.FileControl;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmFundamentalRelation;

import java.util.Enumeration;

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
}
