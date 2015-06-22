package versions;

import exportedAPI.OpcatConstants;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by raanan.
 * Date: May 6, 2010
 * Time: 12:37:12 PM
 */
public class OPXEdgeEntry extends OPXEntity {


    OPXID source = null;
    OPXID target = null;
    OPX_EDGE_TYPE edgeType = OPX_EDGE_TYPE.EFFECT_LINK;

    public OPXEdgeEntry(OPXEntity entity, OPXID source, OPXID target, OPX_EDGE_TYPE edgeType) {
        super(entity);
        this.source = source;
        this.target = target;
        this.edgeType = edgeType;
    }

    public OPXEdgeEntry(OPX_ENTITY_TYPE type, Node node, ArrayList<String> ignoreList) {
        super(type, node, ignoreList);
    }

    public OPXID getSource() {
        return source;
    }

    public void setSource(OPXID source) {
        this.source = source;
    }

    public OPXID getTarget() {
        return target;
    }

    public void setTarget(OPXID target) {
        this.target = target;
    }

    public OPX_EDGE_TYPE getEdgeType() {
        return edgeType;
    }

    public void setEdgeType(OPX_EDGE_TYPE edgeType) {
        this.edgeType = edgeType;
    }

    protected static enum OPX_EDGE_TYPE {
        SPECIALIZATION_RELATION, EXHIBITION_RELATION, INSTANTINATION_RELATION,
        AGGREGATION_RELATION, UNI_DIRECTIONAL_RELATION, BI_DIRECTIONAL_RELATION,
        AGENT_LINK, INSTRUMENT_LINK, RESULT_LINK, CONSUMPTION_LINK, EFFECT_LINK,
        CONDITION_LINK, INVOCATION_LINK, CONSUMPTION_EVENT_LINK,
        INSTRUMENT_EVENT_LINK, EXCEPTION_LINK
    }

    public static OPX_EDGE_TYPE opcatConstants2EdgeType(int opcat_type) {
        switch (opcat_type) {
            case OpcatConstants.SPECIALIZATION_RELATION:
                return OPX_EDGE_TYPE.SPECIALIZATION_RELATION;
            case OpcatConstants.EXHIBITION_RELATION:
                return OPX_EDGE_TYPE.EXHIBITION_RELATION;
            case OpcatConstants.INSTANTINATION_RELATION:
                return OPX_EDGE_TYPE.INSTANTINATION_RELATION;
            case OpcatConstants.AGGREGATION_RELATION:
                return OPX_EDGE_TYPE.AGGREGATION_RELATION;
            case OpcatConstants.UNI_DIRECTIONAL_RELATION:
                return OPX_EDGE_TYPE.UNI_DIRECTIONAL_RELATION;
            case OpcatConstants.BI_DIRECTIONAL_RELATION:
                return OPX_EDGE_TYPE.BI_DIRECTIONAL_RELATION;
            case OpcatConstants.AGENT_LINK:
                return OPX_EDGE_TYPE.AGENT_LINK;
            case OpcatConstants.INSTRUMENT_LINK:
                return OPX_EDGE_TYPE.INSTRUMENT_LINK;
            case OpcatConstants.RESULT_LINK:
                return OPX_EDGE_TYPE.RESULT_LINK;
            case OpcatConstants.CONSUMPTION_LINK:
                return OPX_EDGE_TYPE.CONSUMPTION_LINK;
            case OpcatConstants.EFFECT_LINK:
                return OPX_EDGE_TYPE.EFFECT_LINK;
            case OpcatConstants.CONDITION_LINK:
                return OPX_EDGE_TYPE.CONDITION_LINK;
            case OpcatConstants.INVOCATION_LINK:
                return OPX_EDGE_TYPE.INVOCATION_LINK;
            case OpcatConstants.CONSUMPTION_EVENT_LINK:
                return OPX_EDGE_TYPE.CONSUMPTION_EVENT_LINK;
            case OpcatConstants.INSTRUMENT_EVENT_LINK:
                return OPX_EDGE_TYPE.INSTRUMENT_EVENT_LINK;
            case OpcatConstants.EXCEPTION_LINK:
                return OPX_EDGE_TYPE.EXCEPTION_LINK;
        }

        return null;
    }
}
