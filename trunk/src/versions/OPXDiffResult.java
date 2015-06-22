package versions;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by raanan.
 * Date: Mar 21, 2010
 * Time: 3:42:10 PM
 */
public class OPXDiffResult {

    public static enum OPX_ENTITY_CHANGES_TYPES {
        EDGE_NO_CHANGE, EDGE_CHANGED_TYPE, EDGE_NOT_EXISTS, EDGE_CHANGED, NODE_CHANGED_PROPERTIES, NODE_CHANGED_APPEARANCES, NODE_CHANGED_INCLUDED, IN_SOURCE_NOT_IN_TARGET, IN_TARGET_NOT_IN_SOURCE
    }

    public static enum OPX_APEARANCES_CHANGES_TYPES {
        IN_SOURCE_NOT_IN_TARGET, IN_TARGET_NOT_IN_SOURCE
    }

    public static enum OPX_INCLUDED_CHANGES_TYPES {
        ORDER_CHANGED, IN_SOURCE_NOT_IN_TARGET, IN_TARGET_NOT_IN_SOURCE
    }


    OPXID entity;
    ArrayList<OPX_ENTITY_CHANGES_TYPES> changeTypes;
    HashMap<OPXID, OPX_APEARANCES_CHANGES_TYPES> appearanceChanges;
    HashMap<OPXID, OPX_INCLUDED_CHANGES_TYPES> includedChanges;
    Object oldType = null;

    public Object getOldType() {
        return oldType;
    }

    public void setOldType(Object oldType) {
        this.oldType = oldType;
    }

    public OPXID getEntity() {
        return entity;
    }

    public static String EntityChanges2String(OPX_ENTITY_CHANGES_TYPES type) {
        String ret = null;
        switch (type) {
            case EDGE_NO_CHANGE:
                ret = "No Change";
                break;
            case EDGE_CHANGED_TYPE:
                ret = "Connection Changed Type";
                break;
            case EDGE_NOT_EXISTS:
                ret = "Connection Does not exist";
                break;
            case EDGE_CHANGED:
                ret = "Connection Changed";
                break;
            case NODE_CHANGED_PROPERTIES:
                ret = "Thing changed properties";
                break;
            case NODE_CHANGED_APPEARANCES:
                ret = "Change in Thing apearances";
                break;
            case NODE_CHANGED_INCLUDED:
                ret = "Change in Thing zoom-in";
                break;
            case IN_SOURCE_NOT_IN_TARGET:
                ret = "Added";
                break;
            case IN_TARGET_NOT_IN_SOURCE:
                ret = "Deleted";
                break;
        }
        return ret;
    }

    public ArrayList<OPX_ENTITY_CHANGES_TYPES> getEntityChanges() {
        return changeTypes;
    }

    public HashMap<OPXID, OPX_APEARANCES_CHANGES_TYPES> getAooearanceChanges() {
        return appearanceChanges;
    }

    public HashMap<OPXID, OPX_INCLUDED_CHANGES_TYPES> getIncludedChangesMap() {
        return includedChanges;
    }


    public OPXDiffResult(OPXID entity) {
        this.entity = entity;
        changeTypes = new ArrayList<OPX_ENTITY_CHANGES_TYPES>();
        appearanceChanges = new HashMap<OPXID, OPX_APEARANCES_CHANGES_TYPES>();
        includedChanges = new HashMap<OPXID, OPX_INCLUDED_CHANGES_TYPES>();
    }


    public void addEntityChangeType(OPX_ENTITY_CHANGES_TYPES type) {
        if (!changeTypes.contains(type))
            changeTypes.add(type);
    }

    public void addAppearanceChange(OPXID apearance, OPX_APEARANCES_CHANGES_TYPES type) {
        appearanceChanges.put(apearance, type);
    }

    public void addIncludedChange(OPXID included, OPX_INCLUDED_CHANGES_TYPES type) {
        includedChanges.put(included, type);
    }
}
