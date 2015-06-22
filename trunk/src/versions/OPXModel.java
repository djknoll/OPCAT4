package versions;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.OpcatLogger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by raanan.
 * Date: May 3, 2010
 * Time: 5:04:06 PM
 */
public class OPXModel extends OPXEntity {

    File opx;
    HashMap<OPXID, OPXEntity> entries;
    HashMap<OPXID, OPXEdgeEntry> edges;
    HashMap<Long, OPXLibrary> libraries;
    boolean loaded = false;
    public static OPXID.OPX_ID_TYPE opcatIDType = OPXID.OPX_ID_TYPE.UUID;


    public static OPXID.OPX_ID_TYPE getOpcatIDType() {
        return opcatIDType;
    }

    public HashMap<OPXID, OPXEntity> getNodesMap() {
        return entries;
    }

    public HashMap<OPXID, OPXEdgeEntry> getEdgesMap() {
        return edges;
    }

    public Collection<OPXEdgeEntry> getEdges() {
        return edges.values();
    }

    public HashMap<Long, OPXLibrary> getLibraries() {
        return libraries;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public OPXModel(File opx, OPXID.OPX_ID_TYPE idType) throws IOException {
        super(OPX_ENTITY_TYPE.MODEL);

        this.opcatIDType = idType;

        if (opx == null) {
            throw new IOException("Passed a null file ");
        }

        if (!(opx instanceof File)) {
            throw new IOException(opx.getPath() + " is not a File ");
        }

        if (!opx.exists()) {
            throw new IOException("File " + opx.getPath() + " does not exist ");
        }

        if (!opx.isFile()) {
            throw new IOException(opx.getPath() + " is a directory ");
        }

        if (!opx.getName().endsWith(".opx")) {
            throw new IOException(opx.getPath() + " is not an OPX file ");
        }

        this.opx = opx;
        entries = new HashMap<OPXID, OPXEntity>();
        edges = new HashMap<OPXID, OPXEdgeEntry>();
        libraries = new HashMap<Long, OPXLibrary>();
    }

    public boolean reloadOPX() {
        return _loadOPX();
    }

    public boolean loadOPX() {
        if (loaded) {
            return loaded;
        }
        return _loadOPX();
    }


    private boolean _loadOPX() {

        loaded = false;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            //System.out.println("Starting System :  " + Calendar.getInstance().getTime().toString());
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(opx);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();

            //get OPMSystem node == root node
            XPathExpression expr = xpath.compile("/OPX/OPMSystem/self::node()");
            Node opxSystem = (Node) expr.evaluate(doc, XPathConstants.NODE);
            this.load(opxSystem, true, null);

            //load libraries
            //expr = xpath.compile("//LibraryReference");
            NodeList libs = getNAmedSubList(opxSystem, "MetaLibraries");// (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (libs != null) {
                for (int i = 0; i < libs.getLength(); i++) {
                    OPXLibrary opxLib = new OPXLibrary(OPX_ENTITY_TYPE.LIBRARY);
                    opxLib.load(libs.item(i), false, null);
                    if (opxLib.getLibID() != -1) {
                        libraries.put(opxLib.getLibID(), opxLib);
                    }
                }
            }

            //load entries = nodes and edges.
            ArrayList<String> ignore = new ArrayList<String>();
            ignore.add("instance".toLowerCase());
            ignore.add("EntityIncludedInstances".toLowerCase());

            //load nodes
            //expr = xpath.compile("//LogicalProcess");
            //System.out.println("Starting Processes :  " + Calendar.getInstance().getTime().toString());
            NodeList processes = getNAmedSubList(opxSystem, "ProcessSection");  // (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (processes != null) {
                for (int i = 0; i < processes.getLength(); i++) {
                    Node node = processes.item(i);
                    //when loading the entity ignore the folowing nodes
                    //System.out.println("Processing " + i + " of " + processes.getLength() + " processes ");
                    OPXEntity entity = loadLogicalEntity(node, OPX_ENTITY_TYPE.PROCESS, xpath, doc, ignore);
                    if (entity.getID() != null)
                        entries.put(entity.getID(), entity);
                }
            }


            //expr = xpath.compile("//LogicalObject");
            //System.out.println("Starting Objects :  " + Calendar.getInstance().getTime().toString());
            NodeList objects = getNAmedSubList(opxSystem, "ObjectSection"); //  (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if (objects != null) {
                //for each Object load states and set parent entity to the object
                for (int i = 0; i < objects.getLength(); i++) {
                    ignore.add("LogicalState".toLowerCase());
                    Node node = objects.item(i);

                    OPXEntity entity = loadLogicalEntity(node, OPX_ENTITY_TYPE.OBJECT, xpath, doc, ignore);

                    //load states as entities and the add them as included entities to the object
                    ignore.remove("LogicalState".toLowerCase());
                    //expr = xpath.compile("//LogicalObject/EntityAttr[@uuid = \"" + entity.getID() + "\"]/parent::node()/LogicalState");
                    //NodeList states = getNAmedSubList(node, "LogicalState") ; //(NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                    for (int j = 0; j < node.getChildNodes().getLength(); j++) {
                        if (node.getChildNodes().item(j).getNodeName().equalsIgnoreCase("LogicalState")) {
                            OPXEntity state = new OPXEntity(OPX_ENTITY_TYPE.STATE, node.getChildNodes().item(j), ignore);
                            //load state instances here.
                            //expr = xpath.compile("//LogicalState/EntityAttr[@uuid = \"" + state.getID() + "\"]/EntityInstances/instance");
                            NodeList instances = getNAmedSubList(node.getChildNodes().item(j), "EntityInstances");//NodeList instances = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                            state.addAppearances(loadInstance(state.getEntityType(), instances));
                            //

                            /**
                             * TODO: this is a patch as state Entity is putted inside the objeect
                             * I need to order this and connect real state instance to the object instance.
                             *
                             *
                             */
                            entity.addIncludedAppearance(state.getID());
                            entries.put(state.getID(), state);
                        }
                    }


                    entries.put(entity.getID(), entity);
                }


            }

            //load edges
            //expr = xpath.compile("//LogicalRelation");
            //System.out.println("Starting Relations :  " + Calendar.getInstance().getTime().toString());
            if (opcatIDType == OPXID.OPX_ID_TYPE.UUID) {
                NodeList releations = getNAmedSubList(opxSystem, "RelationSection");// (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                if (releations != null) {
                    for (int i = 0; i < releations.getLength(); i++) {
                        Node node = releations.item(i);
                        //when loading the entity ignore the folowing nodes
                        OPXEntity entity = loadLogicalEntity(node, OPX_ENTITY_TYPE.RELEATION, xpath, doc, ignore);
                        if (entity.getID() != null) {
                            OPXID source = OPXID.fromString(entity.get("logicalrelation.source-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                            OPXID dest = OPXID.fromString(entity.get("logicalrelation.destination-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                            int opcatType = Integer.valueOf(entity.get("logicalrelation.relationtype").toString());
                            OPXEdgeEntry edge = new OPXEdgeEntry(entity, source, dest, OPXEdgeEntry.opcatConstants2EdgeType(opcatType));
                            edges.put(edge.getID(), edge);
                        }
                    }
                }
            }

            //
            //expr = xpath.compile("//LogicalLink");
            //System.out.println("Starting Links :  " + Calendar.getInstance().getTime().toString());

            //not supporting links if using names as uuid
            if (opcatIDType == OPXID.OPX_ID_TYPE.UUID) {
                NodeList links = getNAmedSubList(opxSystem, "LinkSection");// NodeList links = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
                if (links != null) {
                    for (int i = 0; i < links.getLength(); i++) {
                        Node node = links.item(i);
                        //when loading the entity ignore the folowing nodes
                        OPXEntity entity = loadLogicalEntity(node, OPX_ENTITY_TYPE.LINK, xpath, doc, ignore);
                        if (entity.getID() == null) continue;
                        OPXID source;
                        OPXID dest;
                        source = OPXID.fromString(entity.get("logicallink.source-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                        dest = OPXID.fromString(entity.get("logicallink.destination-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                        //if (opcatIDType == OPXID.OPX_ID_TYPE.UUID) {
                        //    source = OPXID.fromString(entity.get("logicallink.source-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                        //    dest = OPXID.fromString(entity.get("logicallink.destination-uuid").toString(), OPXID.OPX_ID_TYPE.UUID);
                        //} else {
                        //
                        //}
                        int opcatType = Integer.valueOf(entity.get("logicallink.linktype").toString());
                        OPXEdgeEntry edge = new OPXEdgeEntry(entity, source, dest, OPXEdgeEntry.opcatConstants2EdgeType(opcatType));
                        edges.put(edge.getID(), edge);
                    }
                }

            }
            //System.out.println("Finished System :  " + Calendar.getInstance().getTime().toString());


        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
            return loaded;
        }


        loaded = true;
        return loaded;
    }


    private OPXEntity loadLogicalEntity(Node node, OPX_ENTITY_TYPE type, XPath xpath, Document doc, ArrayList<String> ignore) {
        OPXEntity entity = null;
        try {
            XPathExpression expr;
            entity = new OPXEntity(type, node, ignore);

            if (entity.getID() == null) return entity;
            //is it included inside another Node ?
            //expr = xpath.compile("//EntityIncludedInstances/instance[@uuid = \"" + entity.getID() + "\"]/parent::node()/parent::node()/parent::node()");
            //NodeList parent = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            //if yes set parent.
            //if (parent != null && parent.getLength() > 0) {
            //    OPXEntity parEntiry = new OPXEntity(OPX_ENTITY_TYPE.PROCESS, parent.item(0), ignore);
            //    entity.setParent(UUID.fromString(parEntiry.get("logicalprocess.Entityattr.uuid".toLowerCase()).toString()));
            //}

            //get Intances of OPXNode
            //expr = xpath.compile("//EntityAttr[@uuid = \"" + entity.getID() + "\"]/EntityInstances/instance");
            NodeList instances = getNAmedSubList(node, "EntityInstances"); // (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            ArrayList<OPXInstance> tmpInstances = loadInstance(entity.getEntityType(), instances);
            if (tmpInstances.size() > 0)
                entity.addAppearances(tmpInstances);

            //get included in OPXNode
            //expr = xpath.compile("//EntityAttr[@uuid = \"" + entity.getID() + "\"]/parent::node()/ThingAttr/EntityIncludedInstances/instance");
            NodeList included = getNAmedSubList(node, "EntityIncludedInstances"); //NodeList included =  (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            entity.addIncludedAppearances(loadIncludedUUID(included));

        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }

        return entity;

    }

    public OPXInstance getEntityAppearance(OPXID appearance) {
        for (OPXEntity node : getNodes()) {
            OPXInstance ret = node.getAppearance(appearance);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    public ArrayList<OPXInstance> getEntityAppearances() {
        ArrayList<OPXInstance> ret = new ArrayList<OPXInstance>();
        for (OPXEntity node : getNodes()) {
            ret.addAll(node.getAppearances().values());
        }
        return ret;
    }

    //private NodeList generateNodeListFromNodeChildren(Node node, NAME name) {
    //    for(int i = 0 ; i < node.getChildNodes().getLength() ;i++) {
    //        if(node.getChildNodes().item(i).getNodeName().equalsIgnoreCase(name)) {
    //
    //        }
    //    }
    //
    //}

    private NodeList getNAmedSubList(Node node, String name) {
        NodeList ret = null;
        if (node.getNodeName().equalsIgnoreCase(name)) {
            ret = node.getChildNodes();
        } else {
            NodeList subNodes = node.getChildNodes();
            for (int i = 0; i < subNodes.getLength(); i++) {
                ret = getNAmedSubList(subNodes.item(i), name);
                if (ret != null) break;
            }
        }
        return ret;
    }


    private ArrayList<OPXID> loadIncludedUUID(NodeList instances) {
        ArrayList<OPXID> ret = new ArrayList<OPXID>();
        if (instances != null) {
            for (int i = 0; i < instances.getLength(); i++) {
                Node instance = instances.item(i);
                if ((instance.getAttributes() == null) || (instance.getAttributes().getLength() <= 0)) continue;
                OPXID uuid;
                if (opcatIDType == OPXID.OPX_ID_TYPE.UUID) {
                    uuid = OPXID.fromString(instance.getAttributes().getNamedItem(OPX_UUID_KEY).getNodeValue(), OPXID.OPX_ID_TYPE.UUID);
                } else {
                    uuid = OPXID.fromString(instance.getAttributes().getNamedItem(OPX_NAME).getNodeValue(), OPXID.OPX_ID_TYPE.NAME);
                }
                ret.add(uuid);
            }
        }
        return ret;
    }

    private ArrayList<OPXInstance> loadInstance(OPX_ENTITY_TYPE type, NodeList instances) {
        ArrayList<OPXInstance> ret = new ArrayList<OPXInstance>();
        if (instances != null) {
            for (int i = 0; i < instances.getLength(); i++) {
                Node instance = instances.item(i);
                if ((instance.getAttributes() == null) || (instance.getAttributes().getLength() <= 0)) continue;
                int order = -1;
                if (instance.getAttributes().getNamedItem("realetive.order") != null) {
                    order = Integer.valueOf(instance.getAttributes().getNamedItem("realetive.order").getNodeValue());
                }
                String name = null;
                if (instance.getAttributes().getNamedItem("name") != null) {
                    name = String.valueOf(instance.getAttributes().getNamedItem("name").getNodeValue());
                }

                OPXID uuid;
                if (opcatIDType == OPXID.OPX_ID_TYPE.UUID) {
                    uuid = OPXID.fromString(instance.getAttributes().getNamedItem(OPX_UUID_KEY).getNodeValue(), OPXID.OPX_ID_TYPE.UUID);
                } else {
                    uuid = OPXID.fromString(instance.getAttributes().getNamedItem(OPX_NAME).getNodeValue(), OPXID.OPX_ID_TYPE.NAME);
                }
                OPXInstance ins = new OPXInstance(type, order, uuid);
                ins.setName(name);

                if (ins.getID() != null)
                    ret.add(ins);
            }
        }
        return ret;
    }

    /**
     * gets entity in the model could be
     * Node or Edge
     *
     * @param uuid
     * @return
     */
    public OPXEntity getEntity(OPXID uuid) {
        boolean loaded = loadOPX();
        if (!loaded) {
            return null;
        }
        return (entries.get(uuid) != null ? entries.get(uuid) : edges.get(uuid));
    }

    public OPXEntity getNode(OPXID uuid) {
        return entries.get(uuid);
    }

    public OPXEdgeEntry getEdge(OPXID uuid) {
        return edges.get(uuid);
    }


    /**
     * 0 == no property
     * 1 == type 1 reguler property
     * 2 = type 2 cdata property
     *
     * @param node
     * @return
     */
    public static int getPropertyNodeType(Node node) {
        int type = 0;
        if (node.getAttributes() != null) {
            if (node.getAttributes().getLength() == 2) {
                if (node.getAttributes().item(0).getNodeName().toLowerCase().equalsIgnoreCase(OPX_KEY)) {
                    if (node.getAttributes().item(1).getNodeName().toLowerCase().equalsIgnoreCase(OPX_VALUE)) {
                        type = 1;
                    }
                }
            } else if (node.getAttributes().getLength() == 1) {
                if (node.getAttributes().item(0).getNodeName().toLowerCase().equalsIgnoreCase(OPX_KEY)) {
                    if ((node.getChildNodes().getLength() >= 1)) {
                        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
                            if (node.getChildNodes().item(i).getNodeName().equalsIgnoreCase(OPX_VALUE)) {
                                type = 2;
                            }
                        }
                    }
                }

            }
        }
        return type;
    }

    public Collection<OPXEntity> getNodes() {
        return entries.values();
    }

    public OPXDiffResult.OPX_ENTITY_CHANGES_TYPES getEdgeExsitence(OPXID source, OPXID target, OPXEdgeEntry.OPX_EDGE_TYPE type) {
        OPXEdgeEntry edge = getEdge(source, target);
        //for (OPXEdgeEntry edge : getEdges()) {
        if (edge != null) {
            if (edge.getEdgeType() == type) {
                //same link
                return OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_NO_CHANGE;
            }
            return OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_CHANGED_TYPE;
        }
        //}
        return OPXDiffResult.OPX_ENTITY_CHANGES_TYPES.EDGE_NOT_EXISTS;
    }

    public OPXEdgeEntry getEdge(OPXID source, OPXID target) {
        for (OPXEdgeEntry edge : getEdges()) {
            if ((edge.getSource().equals(source)) && (edge.getTarget().equals(target))) {
                return edge;
            }
        }
        return null;
    }

    public OPXEdgeEntry getEdge(OPXID source, OPXID target, OPXEdgeEntry.OPX_EDGE_TYPE type) {
        OPXEdgeEntry edge = getEdge(source, target);
        if (edge != null) {
            if (edge.getEdgeType() == type) {
                return edge;
            }
        }
        return null;
    }

    public static String OPX_KEY = "key";
    public static String OPX_VALUE = "value";
    public static String OPX_UUID_KEY = "uuid";
    public static String OPX_NAME = "name";

}
