package versions;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.Configuration;

import java.util.*;

/**
 * Created by raanan.
 * Date: May 6, 2010
 * Time: 2:41:08 PM
 */
public class OPXEntity extends Properties implements Comparable {

    private static final String UUID_PROPERTY_IDENTIFIEW = "uuid";
    private static final String NAME_PROPERTY_IDENTIFIEW = "entity.general.name";
    private static final String NAME_PROPERTY_OLD_IDENTIFIEW = "name";

    public static enum OPX_ENTITY_TYPE {
        PROCESS, OBJECT, STATE, RELEATION, LINK, LIBRARY, MODEL
    }

    private OPX_ENTITY_TYPE ENTITYTYPE;
    private OPXID id = null;
    private String name = null;
    HashMap<OPXID, OPXInstance> appearances = new HashMap<OPXID, OPXInstance>();
    ArrayList<OPXID> includedAppearences = new ArrayList<OPXID>();
    OPXID parent = null;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return (name != null ? name : getEntityType().toString());
    }

    public int compareTo(Object o) {
        if (o instanceof OPXEntity) {
            OPXEntity opx = (OPXEntity) o;
            return (opx.getID().equals(getID()) ? 1 : 0);
        }
        return 0;
    }


    public HashMap<Object, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES> compareProperties(OPXEntity t) {
        HashMap<Object, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES> diff = new HashMap<Object, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES>();
        List<String> ignoredPropes = new ArrayList<String>();
        ignoredPropes = Arrays.asList(Configuration.getInstance().getProperty("versions.diff.properties.ignorelist").split(";"));

        for (Object key : this.keySet()) {
            if (t.get(key) != null) {
                if (this.get(key) != null) {
                    if (!(ignoredPropes.contains(key)) && !(this.get(key).toString().equalsIgnoreCase(t.get(key).toString()))) {
                        diff.put(key, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.NODE_CHANGED_PROPERTIES); //, OPXEntityDiff.OPX_ENTITY_CHANGES_TYPES.CHANGED);
                    } else {
                        //No change
                        int i = 0;
                    }
                } else {
                    diff.put(key, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                }
            } else if (this.get(key) != null) {
                diff.put(key, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_SOURCE_NOT_IN_TARGET);
            }
        }

        for (Object key : t.keySet()) {
            if (this.get(key) == null) {
                if (t.get(key) != null) {
                    diff.put(key, OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.IN_TARGET_NOT_IN_SOURCE);
                }
            }
        }
        return diff;
    }


    public boolean hasIncluded() {
        return includedAppearences.size() > 0;
    }

    public OPXID getParent() {
        return parent;
    }

    public OPXInstance getAppearance(OPXID uuid) {
        return appearances.get(uuid);
    }

    public boolean isIncluded(OPXID uuid) {
        return includedAppearences.contains(uuid);
    }

    public HashMap<OPXID, OPXInstance> getAppearances() {
        return appearances;
    }

    public ArrayList<OPXID> getIncludedAppearences() {
        return includedAppearences;
    }

    public void setParent(OPXID parent) {
        this.parent = parent;
    }

    public void addAppearance(OPXInstance appearance) {
        appearances.put(appearance.getID(), appearance);
    }

    public void addAppearances(ArrayList<OPXInstance> appearances) {
        for (OPXInstance ins : appearances) {
            this.appearances.put(ins.getID(), ins);
        }
    }

    public void addIncludedAppearances(ArrayList<OPXID> appearances) {
        for (OPXID ins : appearances) {
            this.includedAppearences.add(ins);
        }
    }

    public void addIncludedAppearance(OPXID appearance) {
        this.includedAppearences.add(appearance);
    }


    public OPX_ENTITY_TYPE getEntityType() {
        return ENTITYTYPE;
    }

    public OPXID getID() {
        //switch(ID_TYPE) {
        //    case UUID:
        //        return id ;
        //    case NAME:
        //        return new OPXID(getName());
        //}
        return id;
    }

    /**
     * creates and loads an OPXEntity from the given Node
     *
     * @param type
     * @param node
     * @param ignoreList
     */
    protected OPXEntity(OPX_ENTITY_TYPE type, Node node, ArrayList<String> ignoreList) {
        super();
        this.ENTITYTYPE = type;
        load(node, false, ignoreList);
    }

    protected OPXEntity(OPX_ENTITY_TYPE ENTITYTYPE, OPXID id) {
        this.ENTITYTYPE = ENTITYTYPE;
        this.id = id;
    }

    public OPXEntity(OPX_ENTITY_TYPE ENTITYTYPE) {
        this.ENTITYTYPE = ENTITYTYPE;
    }

    protected OPXEntity(OPXEntity entity) {
        this.putAll(entity);

        ENTITYTYPE = entity.getEntityType();
        id = entity.getID();
        appearances = entity.getAppearances();
        includedAppearences = entity.getIncludedAppearences();
        parent = entity.getParent();
        setName(entity.getName());
    }

    /**
     * Loads the OPXEntity from the given Node.
     * The node can contain Attribute nodes or ChildNodes
     * Attributes AND ChildNodes are loaded into this OPXEntity
     *
     * @param node
     */
    public void load(Node node, boolean attributesOnly, ArrayList<String> ignoreList) {
        if (attributesOnly) {
            loadNodeAttributes(node, node.getNodeName().toLowerCase(), ignoreList);
        } else {
            loadNode(node, node.getNodeName().toLowerCase(), ignoreList);
        }
    }

    protected boolean loadNodeAttributes(Node node, String header, ArrayList<String> ignoreList) {
        int nodeType = OPXModel.getPropertyNodeType(node);
        if (nodeType > 0) {
            loadPropertyNode(node, header, nodeType);
            return (nodeType == 2);
        } else {
            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    Node attr = attributes.item(i);
                    if (ignoreList == null || !(ignoreList.contains(attr.getNodeName().toLowerCase()))) {
                        if (id == null) {
                            if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.UUID) && (attr.getNodeName().equalsIgnoreCase(UUID_PROPERTY_IDENTIFIEW))) {
                                id = new OPXID(UUID.fromString(attr.getNodeValue()));
                            } else if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.NAME) && (attr.getNodeName().equalsIgnoreCase(NAME_PROPERTY_IDENTIFIEW))) {
                                id = new OPXID(attr.getNodeValue());
                            } else if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.NAME) && (attr.getNodeName().equalsIgnoreCase(NAME_PROPERTY_OLD_IDENTIFIEW))) {
                                id = new OPXID(attr.getNodeValue());
                            }
                        }
                        this.put(header + "." + attr.getNodeName().toLowerCase(), attr.getNodeValue());
                    }
                }
            }
            return false;
        }
    }

    private void loadPropertyNode(Node node, String header, int type) {
        NamedNodeMap attributes = node.getAttributes();
        String key = attributes.item(0).getNodeValue();
        String value = null;
        if (type == 1) {
            value = attributes.item(1).getNodeValue();
        }

        if (type == 2) {
            for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                if (node.getChildNodes().item(i).getNodeName().equalsIgnoreCase(OPXModel.OPX_VALUE))
                    value = node.getChildNodes().item(i).getChildNodes().item(0).getNodeValue();
            }
        }

        if (key != null && value != null) {
            if (name == null && (key.endsWith(NAME_PROPERTY_IDENTIFIEW))) {
                name = value;
            }

            if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.UUID) && (key.equalsIgnoreCase(UUID_PROPERTY_IDENTIFIEW))) {
                id = new OPXID(UUID.fromString(value));
            } else if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.NAME) && (key.equalsIgnoreCase(NAME_PROPERTY_IDENTIFIEW))) {
                id = new OPXID(value);
            } else if ((OPXModel.getOpcatIDType() == OPXID.OPX_ID_TYPE.NAME) && (key.equalsIgnoreCase(NAME_PROPERTY_OLD_IDENTIFIEW))) {
                id = new OPXID(value);
            }
            put(header + "." + key, value);
        }
    }

    private void loadNode(Node node, String header, ArrayList<String> ignoreList) {
        if (ignoreList != null && ignoreList.contains(node.getNodeName().toLowerCase())) {
            return;
        }
        //no need to load the next element which could be the value of the property
        //so we use the skip thingy        
        boolean skip = loadNodeAttributes(node, header, ignoreList);
        if (!skip) {
            NodeList elements = node.getChildNodes();
            if (elements != null) {
                for (int i = 0; i < elements.getLength(); i++) {
                    Node element = elements.item(i);
                    if (element.getNodeName().equalsIgnoreCase(OPXModel.OPX_VALUE) && skip) {
                        skip = false;
                        continue;
                    }
                    if (ignoreList == null || !(ignoreList.contains(element.getNodeName().toLowerCase()))) {
                        if (element.getChildNodes().getLength() > 0) {
                            loadNode(element, header + "." + element.getNodeName().toLowerCase(), ignoreList);
                        } else {
                            //no need to load the next element which could be the value of the property
                            //so we use the skip thingy
                            skip = loadNodeAttributes(element, header, ignoreList);
                        }

                        if (element.getNodeValue() != null && !(element.getNodeValue().trim().equalsIgnoreCase(""))) {
                            this.put(header, element.getNodeValue());
                        }
                    }
                }
            }
        }

    }

}
