package gui.opdProject.simplifiedOPM.realizers;

import exportedAPI.OpcatConstants;
import gui.projectStructure.*;
import y.view.Arrow;
import y.view.EdgeLabel;
import y.view.LineType;
import y.view.PolyLineEdgeRealizer;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 14:03
 */
public class OPMEdgeRealizer extends PolyLineEdgeRealizer {

    Instance instance;
    Entry entry;
    int type;
    boolean directed = true;
    boolean orLink = false;


    public OPMEdgeRealizer(Instance instance, boolean hide, boolean directed, boolean orLink) {

        //Arrow.addCustomArrow("AGENT_LINK", Arrow.CIRCLE.getShape(), Color.black);
        //Arrow.addCustomArrow("CONDITION_LINK", Arrow.CIRCLE.getShape(), Color.white);
        //Arrow.addCustomArrow("INSTRUMENT_LINK", Arrow.CIRCLE.getShape(), Color.white);
        //Arrow.addCustomArrow("EFFECT_LINK_TARGET", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("EFFECT_LINK_SOURCE", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("CONSUMPTION_LINK", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("RESULT_LINK", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("UNI_DIRECTIONAL_RELATION", Arrow.SHORT.getShape(), Color.black);
        //Arrow.addCustomArrow("BI_DIRECTIONAL_RELATION_SOURCE", Arrow.SHORT.getShape(), Color.black);
        //Arrow.addCustomArrow("BI_DIRECTIONAL_RELATION_TARGET", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("AGGREGATION_RELATION", Arrow.CONCAVE.getShape(), Color.black);
        //Arrow.addCustomArrow("EXHIBITION_RELATION", Arrow.CONVEX.getShape(), Color.black);
        //Arrow.addCustomArrow("CONSUMPTION_EVENT_LINK", Arrow.STANDARD.getShape(), Color.black);
        //Arrow.addCustomArrow("INSTRUMENT_EVENT_LINK", Arrow.STANDARD.getShape(), Color.black);

        this.instance = instance;
        this.entry = instance.getEntry();
        this.directed = directed;
        this.orLink = orLink;
        init();
        setVisible(!hide);

    }

    private void init() {
        setSmoothedBends(true);
        setLineColor(instance.getBorderColor());

        if (entry instanceof LinkEntry) {
            type = ((LinkEntry) entry).getLinkType();
            if (((LinkEntry) entry).getPath() != null) setLabelText(((LinkEntry) entry).getPath());
        }

        if (instance instanceof GeneralRelationInstance) {
            type = ((GeneralRelationEntry) entry).getRelationType();
            String str = "";
            String path = ((GeneralRelationEntry) entry).getForwardRelationMeaning();
            if ((path != null) && !(path.equalsIgnoreCase("")))
                str = ((GeneralRelationEntry) entry).getForwardRelationMeaning();

            path = ((GeneralRelationEntry) entry).getBackwardRelationMeaning();
            if ((path != null) && !(path.equalsIgnoreCase("")))
                str = str + ":" + ((GeneralRelationEntry) entry).getBackwardRelationMeaning();

            setLabelText(str);
        }

        if (instance instanceof FundamentalRelationInstance) {
            type = ((FundamentalRelationEntry) entry).getRelationType();
            String str = "";

            String path = ((FundamentalRelationEntry) entry).getForwardRelationMeaning();
            if ((path != null) && !(path.equalsIgnoreCase("")))
                str = ((FundamentalRelationEntry) entry).getForwardRelationMeaning();

            path = ((FundamentalRelationEntry) entry).getBackwardRelationMeaning();
            if ((path != null) && !(path.equalsIgnoreCase("")))
                str = str + ":" + ((FundamentalRelationEntry) entry).getBackwardRelationMeaning();

            setLabelText(str);
        }

        getLabel().setPreferredPlacement(EdgeLabel.PLACE_AT_SOURCE);
        getLabel().setPosition(EdgeLabel.RIGHT_TEXT_POSITION);

        if (orLink) setLineType(LineType.DASHED_1);

        Arrow arrow = Arrow.STANDARD;
        switch (type) {
            case OpcatConstants.AGENT_LINK:
                arrow = Arrow.CIRCLE;
                setArrow(arrow);
                break;

            case OpcatConstants.EFFECT_LINK:
                arrow = Arrow.STANDARD;
                setArrow(arrow);
                if (!directed) {
                    setSourceArrow(arrow);
                }
                break;

            case OpcatConstants.CONSUMPTION_LINK:
                arrow = Arrow.STANDARD;
                setArrow(arrow);
                break;

            case OpcatConstants.RESULT_LINK:
                arrow = Arrow.STANDARD;
                setArrow(arrow);
                break;

            case OpcatConstants.CONDITION_LINK:
                arrow = Arrow.TRANSPARENT_CIRCLE; //Arrow.addCustomArrow("CONDITION", new ConditionArrow()) ;
                setArrow(arrow);
                break;

            case OpcatConstants.INSTRUMENT_LINK:
                arrow = Arrow.TRANSPARENT_CIRCLE;
                setArrow(arrow);
                break;

            case OpcatConstants.UNI_DIRECTIONAL_RELATION:
                arrow = Arrow.SHORT;
                setArrow(arrow);
                break;

            case OpcatConstants.BI_DIRECTIONAL_RELATION:
                arrow = Arrow.SHORT;
                setArrow(arrow);
                setSourceArrow(arrow);
                break;

            case OpcatConstants.AGGREGATION_RELATION:
                arrow = Arrow.CONCAVE;
                setArrow(arrow);
                break;

            case OpcatConstants.EXHIBITION_RELATION:
                arrow = Arrow.CONVEX;
                setArrow(arrow);
                break;

            case OpcatConstants.CONSUMPTION_EVENT_LINK:
                arrow = Arrow.STANDARD;
                setArrow(arrow);
                break;

            case OpcatConstants.INSTRUMENT_EVENT_LINK:
                arrow = Arrow.STANDARD;
                setArrow(arrow);
                break;

            default:
                setArrow(Arrow.STANDARD);
                break;
        }

    }


}
