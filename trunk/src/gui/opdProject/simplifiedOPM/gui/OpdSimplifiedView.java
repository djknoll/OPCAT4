package gui.opdProject.simplifiedOPM.gui;

import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import gui.controls.FileControl;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.simplifiedOPM.actions.*;
import gui.opdProject.simplifiedOPM.comparators.ValueComparatorInteger;
import gui.opdProject.simplifiedOPM.realizers.OPMEdgeRealizer;
import gui.opdProject.simplifiedOPM.realizers.OPMRealizerFactory;
import gui.opdProject.simplifiedOPM.viewModes.HierarchicClickViewMode;
import gui.opdProject.simplifiedOPM.viewModes.TooltipMode;
import gui.opdProject.simplifiedOPM.yWorksHelpers.GroupUtils;
import gui.opdProject.simplifiedOPM.yWorksHelpers.LODRenderingHint;
import gui.opdProject.simplifiedOPM.yWorksHelpers.OPMLayouter;
import gui.opdProject.simplifiedOPM.yWorksHelpers.PreviewJob;
import gui.projectStructure.*;
import y.algo.Paths;
import y.base.*;
import y.layout.CopiedLayoutGraph;
import y.layout.GraphLayout;
import y.layout.PortConstraintKeys;
import y.layout.grouping.GroupingKeys;
import y.view.*;
import y.view.hierarchy.GroupNodeRealizer;
import y.view.hierarchy.HierarchyManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by raanan.
 * Date: 07/06/11
 * Time: 09:24
 */
public class OpdSimplifiedView extends Graph2DView {

    private Opd opd = null;
    private OpdProject project = null;
    private HashMap<OpdKey, Node> n = new HashMap<OpdKey, Node>();
    private NodeMap nodeToInstanceMap = null;
    private EdgeMap edgeToInstanceMap = null;
    private double multiplyData = 0;
    private double additionData = 0;

    HashMap<Instance, ArrayList<Instance>> subgraphs;
    private Map<String, String> addData = new HashMap<String, String>();
    GroupUtils groupUtils;
    List<Instance> instancesInOPD;
    //NavigationComponent myNav;
    HierarchyManager hm;
    public static final String CONFIGURATION_GROUP = "GroupingOPM_GROUP_NODE";
    private String addText;
    PreviewJob previewJob;
    LayoutAction layoutAction;
    AllPathAction allPathAction;
    UpAction upAction;
    DownAction downAction;
    GroupAction groupAction;
    UnGroupAction unGroupAction;
    FitAction fitAction;
    MagnifyAction magnifyAction;
    PathAction pathAction;
    NeigboursAction nAction;
    UnNeigboursAction unNAction;
    ReloadAction reloadAction;
    SaveAction saveAction;
    OpdSimplifiedViewParams params;
    ArrayList<Properties> properties;
    EdgeMap edgeTargetGrouping;
    EdgeMap edgeSourceGrouping;

    NodeMap nodeGrouping;
    NodeMap isGroup;
    OPMLayouter layouter = new OPMLayouter(true);


    public Opd getOpd() {
        return opd;
    }

    public AllPathAction getAllPathAction() {
        return allPathAction;
    }

    public PathAction getPathAction() {
        return pathAction;
    }

    public String getAddText() {
        return addText;
    }


    public void setAddText(String addText) {
        this.addText = addText;
        firePropertyChange("Addition", 1, 0);
    }

    public void initDataPanelData() {
        multiplyData = 1;
        additionData = 0;
    }

    public void addAdditionData(double additionData) {
        double old = this.additionData;
        this.additionData = this.additionData + additionData;
        //firePropertyChange("Addition", old, this.additionData);
    }

    public void addMultiplyData(double multiplyData) {
        double old = this.multiplyData;
        this.multiplyData = this.multiplyData * multiplyData;
        //firePropertyChange("Multiply",old, this.multiplyData);
    }

    public double getMultiplyData() {
        return multiplyData;
    }


    public double getAdditionData() {
        return additionData;
    }

    //public void setNav(NavigationComponent nyNav) {
    //    this.myNav = nyNav;
    //}

    /**
     * Name to show, Property to get
     *
     * @param addData
     */
    public void setAddData(Map<String, String> addData) {
        this.addData = addData;
    }

    public OpdSimplifiedView(Opd opd, OpdSimplifiedViewParams params) {
        super(new Graph2D());
        this.params = params;
        init(opd);
    }

    public OpdSimplifiedView(Opd opd, Map<String, String> addedData, OpdSimplifiedViewParams params) {
        super(new Graph2D());
        this.addData = addedData;
        this.params = params;
        init(opd);
        //SwingUtilities.updateComponentTreeUI(this);
    }

    public OpdSimplifiedView() {
        super(new Graph2D());
        initView();
    }


    private void init(Opd opd) {

        this.opd = opd;
        this.project = opd.getMyProject();

        instancesInOPD = project.getSystemStructure().getInstancesInOpdVector(opd.getOpdId());

        initView();

        doSimpleView();

    }

    public void doGraphLayout() {

        final boolean layoutMorphingEnabled = true;

        // Copy graph in AWT thread.
        final CopiedLayoutGraph cGraph = new CopiedLayoutGraph(getGraph2D());

        // Calculate a layout on the copy of the original graph within a separate
        // thread.
        final Thread thread = new Thread() {
            public void run() {
                //layouter.setLayers(layers);
                layouter.doLayout(cGraph);

                // Assign the calculated layout to the original graph within the AWT
                // thread.
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (layoutMorphingEnabled) {
                            // Perform an animated morphing on layout result.
                            GraphLayout gl = cGraph.getLayoutForOriginalGraph();
                            //try {
                            new LayoutMorpher(OpdSimplifiedView.this, gl).execute();
                            //} catch (Exception ex) {
                            //cGraph.commitLayoutToOriginalGraph();
                            //OpdSimplifiedView.this.updateView();
                            //}
                        } else {
                            // Assign the layout directly without morphing and display the
                            // result.
                            cGraph.commitLayoutToOriginalGraph();
                            //OpdSimplifiedView.this.fitContent();
                            OpdSimplifiedView.this.updateView();
                        }
                    }
                });
            }
        };
        // Assign minimum priority to the layout thread so that the GUI remains
        // responsive.
        thread.setPriority(Thread.MIN_PRIORITY);
        // Start the layout thread.
        thread.start();

    }

    protected void initializeRootDocument() {

        //DefaultHierarchyGraphFactory gf = new DefaultHierarchyGraphFactory();
        //gf.setProxyNodeRealizerEnabled(true);

        getGraph2D().setDefaultNodeRealizer(new ShapeNodeRealizer());
        getGraph2D().setDefaultEdgeRealizer(new PolyLineEdgeRealizer());

        hm = new HierarchyManager(getGraph2D());

        //getGraph2D().getHierarchyManager() ;
    }

    public HierarchyManager getHm() {
        return hm;
    }

    protected void configureGraphics() {
        ((DefaultGraph2DRenderer) getGraph2DRenderer()).setNestedEdgeDrawingOrderEnabled(true);
        NodeRealizer.setHotSpotColor(new Color(51, 51, 51));
        YLabel.setFractionMetricsForSizeCalculationEnabled(true);
        getRenderingHints().put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        getCanvasComponent().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("Zoom".equals(evt.getPropertyName())) {
                    double zoom = getZoom();
                    Object lodValue = null;
                    if (zoom >= 0.8) {
                        lodValue = LODRenderingHint.VALUE_LOD_HIGH;
                    } else if (zoom < 0.8 && zoom >= 0.3) {
                        lodValue = LODRenderingHint.VALUE_LOD_MEDIUM;
                    } else if (zoom < 0.3) {
                        lodValue = LODRenderingHint.VALUE_LOD_LOW;
                    }
                    getRenderingHints().put(LODRenderingHint.KEY_LOD, lodValue);
                }
            }
        });

        EditMode edit = new EditMode();
        edit.allowBendCreation(false);
        edit.allowEdgeCreation(false);
        edit.allowNodeCreation(false);
        edit.allowNodeEditing(false);
        edit.allowResizeNodes(false);
        addViewMode(edit);

        HierarchicClickViewMode hcm = new HierarchicClickViewMode(getHm());
        addViewMode(hcm);


        groupUtils = new GroupUtils(hcm, this);

        getHm().addHierarchyListener(new GroupNodeRealizer.StateChangeListener());


        addViewMode(new TooltipMode(this));

        //navigation panel
        setAntialiasedPainting(true);

        NavigationComponent navi = new NavigationComponent(this);
        navi.putClientProperty("NavigationComponent.AnimateFitContent", true);

        navi.setScrollStepSize(5);
        navi.putClientProperty("NavigationComponent.ScrollTimerDelay",
                new Integer(5));
        navi.setShowZoomSlider(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(navi, BorderLayout.WEST);

        panel.setOpaque(false);
        panel.setBackground(null);

        Dimension ps = navi.getPreferredSize();
        Dimension dim =
                new Dimension(((int) ps.getWidth() + 15), ((int) ps.getHeight() + 15));

        panel.setPreferredSize(dim);
        panel.setMinimumSize(dim);

        JPanel glassPane = getGlassPane();
        glassPane.setLayout(new BorderLayout());
        glassPane.add(panel, BorderLayout.NORTH);
        //glassPane.add(scroll, BorderLayout.EAST);

        JPanel eastHTm = new HTMPanel(params.getEastHTM(), navi.isOpaque(), navi.getForeground(), navi.getBackground());
        glassPane.add(eastHTm, BorderLayout.EAST);

        JPanel northHTM = new HTMPanel(params.getWestHTM(), navi.isOpaque(), navi.getForeground(), navi.getBackground());
        glassPane.add(northHTM, BorderLayout.WEST);

        JPanel southHTM = new HTMPanel(params.getSouthHTM(), navi.isOpaque(), navi.getForeground(), navi.getBackground());
        glassPane.add(southHTM, BorderLayout.SOUTH);


    }

    public GroupUtils getGroupUtils() {
        return groupUtils;
    }

    protected void configureKeyboardActions() {

        //Graph2DViewActions actions = new Graph2DViewActions(this);

        //ActionMap amap = actions.createActionMap();

        //InputMap imap = actions.createDefaultInputMap(amap);

        //getCanvasComponent().setActionMap(amap);

        //getCanvasComponent().setInputMap(JComponent.WHEN_FOCUSED, imap);


        previewJob = new PreviewJob(this);
        layoutAction = new LayoutAction(this);
        allPathAction = new AllPathAction(this);
        upAction = new UpAction(this);
        downAction = new DownAction(this);
        groupAction = new GroupAction(this);
        unGroupAction = new UnGroupAction(this);
        fitAction = new FitAction(this);
        magnifyAction = new MagnifyAction(this);
        pathAction = new PathAction(this);
        nAction = new NeigboursAction(this);
        unNAction = new UnNeigboursAction(this);
        saveAction = new SaveAction(this);
        reloadAction = new ReloadAction(this);

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(previewJob, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(layoutAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(allPathAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(upAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(downAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(groupAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_DOWN_MASK);
        registerKeyboardAction(unGroupAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.ALT_DOWN_MASK);
        registerKeyboardAction(fitAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(magnifyAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(saveAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
        registerKeyboardAction(nAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.ALT_DOWN_MASK);
        registerKeyboardAction(unNAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);


        stroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.ALT_DOWN_MASK);
        registerKeyboardAction(reloadAction, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);


    }


    private void initView() {
        edgeTargetGrouping = getGraph2D().createEdgeMap();
        edgeSourceGrouping = getGraph2D().createEdgeMap();
        nodeGrouping = getGraph2D().createNodeMap();
        isGroup = getGraph2D().createNodeMap();
        initializeRootDocument();
        configureGraphics();
        configureKeyboardActions();

    }


    private void setUpFolderInstanceNode(Node node, Instance ins) {
        Entry entry = ins.getEntry();

        //getGraph2D().setRealizer(node, new GroupNodeRealizer());
        //GroupNodeRealizer nodeRel = (GroupNodeRealizer) getGraph2D().getRealizer(node);
        //if (ins instanceof ProcessInstance) {
        //    nodeRel.setShapeType(ShapeNodeRealizer.ELLIPSE);
        //} else if (ins instanceof ObjectInstance) {
        //    nodeRel.setShapeType(ShapeNodeRealizer.RECT);
        //}
        //nodeRel.setFillColor(ins.getBackgroundColor());
        //nodeRel.setLocation(ins.getGraphicalRepresentation().getX(), ins.getGraphicalRepresentation().getY());
        //nodeRel.setLabelText(getSimplifiedEntryName(ins) + "\n" + entry.getCurrentMetaData());
        //double factor = 1.2;
        //double w = (nodeRel.getLabel().getWidth() > ins.getGraphicalRepresentation().getWidth() ? nodeRel.getLabel().getWidth() * factor : ins.getGraphicalRepresentation().getWidth());
        //double h = (nodeRel.getLabel().getHeight() > ins.getGraphicalRepresentation().getHeight() ? nodeRel.getLabel().getHeight() * factor : ins.getGraphicalRepresentation().getHeight());
        //nodeRel.setSize(w, h);
        //nodeRel.setLayer(Graph2DView.BG_LAYER);

    }

    public EdgeMap getEdgeToInstanceMap() {
        return edgeToInstanceMap;
    }

    public NodeMap getNodeToInstanceMap() {
        return nodeToInstanceMap;
    }

    private String[] doNodes() {

        String[] ret = new String[3];
        String entitiesStr = "";
        String tagStr = "";
        String graphStr = "";

        n = new HashMap<OpdKey, Node>();
        nodeToInstanceMap = getGraph2D().createNodeMap();
        //HashMap<OpdKey, Node> subgraphs = new HashMap<OpdKey, Node>();
        subgraphs = new HashMap<Instance, ArrayList<Instance>>();


        Node node;

        Iterator<Instance> instancesForNodes = instancesInOPD.iterator();
        //first create the groups == things which inbclude other things.
        while (instancesForNodes.hasNext()) {
            Instance ins = instancesForNodes.next();
            if (ins instanceof ThingInstance) {
                if ((ins instanceof ProcessInstance) && !(params.isShowProcesses())) continue;
                if ((ins instanceof ObjectInstance) && !(params.isShowObject())) continue;
                //ArrayList<Instance> included = ((ThingEntry) ((ThingInstance) ins).getEntry()).getIncludedThingInstancesInOpd(opd);
                ArrayList<Instance> included = ((ThingInstance) ins).getIncludedThingInstances();
                if (included.size() > 0) {
                    if (params.isInZoomAsGroup()) {
                        node = getHm().createFolderNode(getGraph2D());
                        entitiesStr = entitiesStr + "{ \"name\" : \"" + ins.getEntry().getName().replace("\n", " ").replace("<","-").replace(">","-") + "\", \"_id\" : \"" + ins.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + ins.getEntry().getLogicalEntity().getTypeString() + "__\"} ,\n";
                        String note = (((ThingEntry) ins.getEntry()).getProperty("entry.general.notes") != null ? " \"note\" : \"" + ((ThingEntry) ins.getEntry()).getProperty("entry.general.notes").replace("\n", ". ").replace("<","-").replace(">","-")  + "\"," : "" ) ;
                        tagStr = tagStr + "{ " + note +  " \"containerID\" : \"" + opd.getOpdId() + "\" ,  \"entityTypeID\" : \"__Container Link__\" ,   \"includedID\" : \"" + ins.getEntry().getUUID() + "\", \"location\" : \"" + ((ThingInstance)ins).getX() + ' ' + ((ThingInstance)ins).getY() + ' ' + ((ThingInstance)ins).getWidth() + ' ' + ((ThingInstance)ins).getHeight() + "\"" + "},\n";

                        setUpFolderInstanceNode(node, ins);
                        nodeToInstanceMap.set(node, ins);
                        n.put(ins.getKey(), node);
                        isGroup.set(node, true);
                    }
                    subgraphs.put(ins, included);
                }

//                //get all inzommes the ins is in.
//                if (((ThingInstance) ins).getParent() != null) {
//                    included.addAll(((ThingEntry) ((ThingInstance) ins).getParent().getEntry()).getIncludedThingInstancesInOpd(opd));
//                    if (included.size() > 0) {
//                        if (params.isInZoomAsGroup()) {
//                            node = getHm().createFolderNode(getGraph2D());
//                            setUpFolderInstanceNode(node, ins);
//                            nodeToInstanceMap.set(node, ins);
//                            n.put(ins.getKey(), node);
//                            isGroup.set(node, true);
//                        }
//                        subgraphs.put(ins, included);
//                    }
//                }
//
            }
        }

        instancesForNodes = instancesInOPD.iterator();
        while (instancesForNodes.hasNext()) {
            Instance ins = instancesForNodes.next();
            if ((ins instanceof ProcessInstance) && !(params.isShowProcesses())) continue;
            if ((ins instanceof ObjectInstance) && !(params.isShowObject())) continue;
            if (ins instanceof ThingInstance) {
                ArrayList<Instance> included = ((ThingInstance) ins).getIncludedThingInstances();
                if (params.isInZoomAsGroup() && (included.size() > 0)) {
                    continue;
                } else {
                    //Node last = null;
                    NodeList nl = new NodeList();
                    if ((ins instanceof ObjectInstance) && (((ObjectInstance) ins).getStateInstances().hasMoreElements())) {
                        Enumeration<StateInstance> instances = ((ObjectInstance) ins).getStateInstances();
                        //boolean done = false;
                        while (instances.hasMoreElements()) {
                            StateInstance state = instances.nextElement();
                            node = getGraph2D().createNode();

                            //if (!done) {
                            entitiesStr = entitiesStr + "{ \"name\" : \"" + state.getEntry().getName().replace("\n", " ").replace("<","-").replace(">","-") + "\", \"_id\" : \"" + state.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + state.getEntry().getLogicalEntity().getTypeString() + "__\"} ,\n";
                            tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\" ,  \"entityTypeID\" : \"__Container Link__\" ,   \"includedID\" : \"" + state.getEntry().getUUID() + "\", \"location\" : \"" + state.getX() + ' ' + state.getY() + ' ' + state.getWidth() + ' ' + state.getHeight() + "\"" + "},\n";
                            //   done = true;
                            //}
                            getGraph2D().setRealizer(node, OPMRealizerFactory.getRealizer(state, addData, params));
                            nodeToInstanceMap.set(node, state);
                            n.put(state.getKey(), node);
                            nl.add(node);
                            //nodeGrouping.set(node, ins.getEntry().getId());
                            //if (last != null)
                            //    layouter.addSameLevel(getGraph2D(), node, last);
                            //last = node;
                        }
                    }


                    if (params.isObjectsAsGroups() && ins instanceof ObjectInstance) {
                        node = getHm().createGroupNode(getGraph2D());
                    } else {
                        node = getGraph2D().createNode();
                    }
                    entitiesStr = entitiesStr + "{ \"name\" : \"" + ins.getEntry().getName().replace("\n", " ").replace("<","-").replace(">","-") + "\", \"_id\" : \"" + ins.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + ins.getEntry().getLogicalEntity().getTypeString() + "__\"} ,\n";
                    String note = (((ThingEntry) ins.getEntry()).getProperty("entry.general.notes") != null ? " \"note\" : \"" + ((ThingEntry) ins.getEntry()).getProperty("entry.general.notes").replace("\n", ". ").replace("<","-").replace(">","-")  + "\"," : "" ) ;
                    tagStr = tagStr + "{ " + note +  " \"containerID\" : \"" + opd.getOpdId() + "\" ,  \"entityTypeID\" : \"__Container Link__\" ,   \"includedID\" : \"" + ins.getEntry().getUUID() + "\", \"location\" : \"" + ((ConnectionEdgeInstance) ins).getX() + " " + ((ConnectionEdgeInstance) ins).getY() + " " + ((ConnectionEdgeInstance) ins).getWidth() + " " + ((ConnectionEdgeInstance) ins).getHeight() + "\"" + "},\n";

                    //if (last != null)
                    //    layouter.addAboveLevel(getGraph2D(), node, last);

                    if (params.isObjectsAsGroups() && nl.size() > 0) {
                        getHm().groupSubgraph(nl, node);
                    }
                    if (nl.size() > 0) {
                        for (int i = 0; i < nl.size(); i++) {
                            Node temp = (Node) nl.get(i);
                            ConnectionEdgeInstance tng = (ConnectionEdgeInstance) nodeToInstanceMap.get(temp);
                            graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "-" + i + "\" ,  \"source_id\" : \"" + ins.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + tng.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Parent Aggregation__\"},\n";
                        }
                    }

                    getGraph2D().setRealizer(node, OPMRealizerFactory.getRealizer(ins, addData, params));

                    nodeToInstanceMap.set(node, ins);
                    n.put(ins.getKey(), node);
                    //}

                }
            }
        }
        //getGraph2D().addDataProvider(, nodeGrouping);
        getGraph2D().addDataProvider(GroupingKeys.GROUP_DPKEY, isGroup);
        ret[0] = entitiesStr;
        ret[1] = tagStr;
        ret[2] = graphStr;
        return ret;
    }

    private String[] doGroupNodes() {

        String[] ret = new String[3];
        String entitiesStr = "";
        String tagStr = "";
        String graphStr = "";

        //set up folder nodes
        for (Instance ins : subgraphs.keySet()) {
            ArrayList<Instance> included = subgraphs.get(ins);
            NodeList nl = new NodeList();
            Node node = n.get(ins.getKey());
            int i = 0;
            for (Instance myins : included) {
                nl.add(n.get(myins.getKey()));
                graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "-" + i + "\" ,  \"source_id\" : \"" + ins.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + myins.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Parent Aggregation__\"},\n";
                i++;
            }
            isGroup.set(node, true);
            getHm().foldSubgraph(nl, node);
            setUpFolderInstanceNode(node, ins);
        }
        ret[0] = entitiesStr;
        ret[1] = tagStr;
        ret[2] = graphStr;
        return ret;
    }

    public OpdSimplifiedViewParams getParams() {
        return params;
    }

    private String[] doEdges() {

        String[] ret = new String[3];
        String entitiesStr = "";
        String graphStr = "";
        String tagStr = "";


        //e = new HashMap<OpdKey, Edge>();
        //edgeToLinkInstanceMap = graph.createEdgeMap();

        edgeToInstanceMap = getGraph2D().createEdgeMap();

        Iterator<Instance> instancesForEdges = instancesInOPD.iterator();

        while (instancesForEdges.hasNext()) {
            Instance ins = instancesForEdges.next();
            Edge edge = null;
            if (params.isShowLinks() && (ins instanceof LinkInstance)) {
                LinkEntry link = (LinkEntry) ((LinkInstance) ins).getEntry();

                Entry source = project.getSystemStructure().getEntry(link.getSourceId());

                Entry destination = project.getSystemStructure().getEntry(link.getDestinationId());

                ArrayList<Instance> sourceInstancesInOPD = source.getAllInstanceInOPD(opd);

                ArrayList<Instance> destinationInstancesInOPD = new ArrayList<Instance>();


                if (params.isStripInZoomLinks() && ((LinkInstance) ins).getDestination().getInstance().isMainInstance()) {
                    ThingInstance zoom = (ThingInstance) ((LinkInstance) ins).getDestination().getInstance();
                    destinationInstancesInOPD.addAll(zoom.getIncludedThingInstances());
                } else {
                    destinationInstancesInOPD = destination.getAllInstanceInOPD(opd);
                }


                if (link.getLinkType() == OpcatConstants.EFFECT_LINK) {
                    for (Instance sourceInstance : sourceInstancesInOPD) {
                        for (Instance destinationInstance : destinationInstancesInOPD) {

                            Node sourceNode = n.get(sourceInstance.getKey());
                            Node destinationNode = n.get(destinationInstance.getKey());
                            if (sourceNode == null || destinationNode == null) continue;

                            boolean directed = true;
                            if (link.getResourceConsumption() > 0) {
                                if (sourceInstance instanceof ProcessInstance) {
                                    edge = getGraph2D().createEdge(sourceNode, destinationNode);
                                    graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Effect Result Link__\"},\n";
                                    tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";
                                } else {
                                    edge = getGraph2D().createEdge(destinationNode, sourceNode);
                                    graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Effect Result Link__\"},\n";
                                    tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";
                                }
                            } else if (link.getResourceConsumption() < 0) {
                                if (sourceInstance instanceof ObjectInstance) {
                                    edge = getGraph2D().createEdge(sourceNode, destinationNode);
                                    graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Effect Consumption Link__\"},\n";
                                    tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                } else {
                                    edge = getGraph2D().createEdge(destinationNode, sourceNode);
                                    graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__Effect Consumption Link__\"},\n";
                                    tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                }
                            } else {
                                edge = getGraph2D().createEdge(sourceNode, destinationNode);
                                graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                                tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                directed = false;
                            }

                            if (edge != null)
                                getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, directed, false));
                            if (edge != null) edgeToInstanceMap.set(edge, ins);

                        }
                    }


                } else if ((link.getLinkType() == OpcatConstants.AGENT_LINK) ||
                        (link.getLinkType() == OpcatConstants.CONSUMPTION_LINK) ||
                        (link.getLinkType() == OpcatConstants.RESULT_LINK) ||
                        ((params.showCondirion) && (link.getLinkType() == OpcatConstants.CONDITION_LINK)) ||
                        ((params.showInstruments) && (link.getLinkType() == OpcatConstants.INSTRUMENT_LINK)) ||
                        ((params.showInstrumentEvent) && (link.getLinkType() == OpcatConstants.INSTRUMENT_EVENT_LINK)) ||
                        ((params.showConsumptionEvent) && (link.getLinkType() == OpcatConstants.CONSUMPTION_EVENT_LINK))) {

                    for (Instance sourceInstance : sourceInstancesInOPD) {
                        for (Instance destinationInstance : destinationInstancesInOPD) {
                            Node sourceNode = n.get(sourceInstance.getKey());
                            Node destinationNode = n.get(destinationInstance.getKey());

                            boolean created = false;
                            //handle states and not object
                            if ((sourceNode != null) && (link.getLinkType() == OpcatConstants.RESULT_LINK)) {
                                if ((sourceInstance instanceof ProcessInstance) && (((LinkInstance) ins).getDestinationInstance() instanceof ObjectInstance)) {
                                    ObjectInstance desObject = (ObjectInstance) ((LinkInstance) ins).getDestinationInstance();
                                    Enumeration<StateInstance> instances = desObject.getStateInstances();
                                    while (instances.hasMoreElements()) {
                                        StateInstance state = instances.nextElement();
                                        Node node = n.get(state.getKey());
                                        if (node != null) {
                                            edge = getGraph2D().createEdge(sourceNode, node);
                                            graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + state.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                                            tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                            if (edge != null) {
                                                getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, true, true));
                                                edgeSourceGrouping.set(edge, desObject.getEntry().getId());
                                            }
                                            if (edge != null) edgeToInstanceMap.set(edge, ins);
                                            created = true;
                                        }
                                    }

                                }
                                //continue ;
                            }

                            if ((destinationNode != null) && (link.getLinkType() == OpcatConstants.CONSUMPTION_LINK)) {
                                if ((sourceInstance instanceof ObjectInstance) && (((LinkInstance) ins).getDestinationInstance() instanceof ProcessInstance)) {
                                    ObjectInstance desObject = (ObjectInstance) sourceInstance;
                                    Enumeration<StateInstance> instances = desObject.getStateInstances();
                                    while (instances.hasMoreElements()) {
                                        StateInstance state = instances.nextElement();
                                        Node node = n.get(state.getKey());
                                        if (node != null) {
                                            edge = getGraph2D().createEdge(node, destinationNode);
                                            graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + state.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                                            tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                            if (edge != null) {
                                                getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, true, true));
                                                edgeTargetGrouping.set(edge, desObject.getEntry().getId());
                                            }
                                            if (edge != null) edgeToInstanceMap.set(edge, ins);
                                            created = true;

                                        }
                                    }

                                }
                                //continue ;
                            }

                            if (sourceNode == null || destinationNode == null) continue;
                            if (!created) {
                                edge = getGraph2D().createEdge(sourceNode, destinationNode);
                                graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                                tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                                if (edge != null)
                                    getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, true, false));
                                if (edge != null) edgeToInstanceMap.set(edge, ins);
                            }

                        }
                    }

                }

                switch (link.getLinkType()) {
                    case OpcatConstants.AGENT_LINK:
                        break;
                    case OpcatConstants.EFFECT_LINK:
                        break;
                    case OpcatConstants.CONSUMPTION_LINK:
                        break;
                    case OpcatConstants.RESULT_LINK:
                        break;
                    case OpcatConstants.CONDITION_LINK:
                        break;
                    case OpcatConstants.INSTRUMENT_LINK:
                        break;

                }


            }


            if (params.isShowGenRelations() && (ins instanceof GeneralRelationInstance)) {
                GeneralRelationEntry link = (GeneralRelationEntry) ((GeneralRelationInstance) ins).getEntry();

                Entry source = project.getSystemStructure().getEntry(link.getSourceId());

                Entry destination = project.getSystemStructure().getEntry(link.getDestinationId());

                ArrayList<Instance> destinationInstancesInOPD = destination.getAllInstanceInOPD(opd);
                ArrayList<Instance> sourceInstancesInOPD = source.getAllInstanceInOPD(opd);

                for (Instance sourceInstance : sourceInstancesInOPD) {
                    for (Instance destinationInstance : destinationInstancesInOPD) {
                        Node sourceNode = n.get(sourceInstance.getKey());
                        Node destinationNode = n.get(destinationInstance.getKey());
                        if (sourceNode == null || destinationNode == null) continue;
                        edge = getGraph2D().createEdge(sourceNode, destinationNode);
                        graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                        tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                        if (edge != null) getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, true, false));
                        if (edge != null) edgeToInstanceMap.set(edge, ins);
                    }
                }

                switch (link.getRelationType()) {
                    case OpcatConstants.UNI_DIRECTIONAL_RELATION:
                        break;

                    case OpcatConstants.BI_DIRECTIONAL_RELATION:
                        break;
                }
            }

            if (params.isShowFunRelations() && (ins instanceof FundamentalRelationInstance)) {
                FundamentalRelationEntry link = (FundamentalRelationEntry) ((FundamentalRelationInstance) ins).getEntry();

                Entry source = project.getSystemStructure().getEntry(link.getSourceId());

                Entry destination = project.getSystemStructure().getEntry(link.getDestinationId());

                ArrayList<Instance> destinationInstancesInOPD = destination.getAllInstanceInOPD(opd);
                ArrayList<Instance> sourceInstancesInOPD = source.getAllInstanceInOPD(opd);

                for (Instance sourceInstance : sourceInstancesInOPD) {
                    for (Instance destinationInstance : destinationInstancesInOPD) {
                        //if ((sourceInstance instanceof ProcessInstance) && (destinationInstance instanceof ProcessInstance))
                        if (!params.isShowContainedFunRelations())
                            if (((ThingInstance) sourceInstance).getIncludedThingInstances().contains(destinationInstance))
                                continue;
                        Node sourceNode = n.get(sourceInstance.getKey());
                        Node destinationNode = n.get(destinationInstance.getKey());
                        if (sourceNode == null || destinationNode == null) continue;
                        edge = getGraph2D().createEdge(sourceNode, destinationNode);
                        graphStr = graphStr + "{\"_id\" : \"" + ins.getUUID() + "\" ,  \"source_id\" : \"" + sourceInstance.getEntry().getUUID() + "\" ,  \"target_id\" : \"" + destinationInstance.getEntry().getUUID() + "\" ,  \"entityTypeID\" : \"__" + (ins.getEntry().getLogicalEntity().getTypeString()) + "__\"},\n";
                        tagStr = tagStr + "{ \"containerID\" : \"" + opd.getOpdId() + "\"  ,\"entityTypeID\" : \"__Container Link__\" ,  \"includedID\" : \"" + ins.getUUID() + "\"},\n";

                        if (edge != null) getGraph2D().setRealizer(edge, new OPMEdgeRealizer(ins, false, true, false));
                        if (edge != null) edgeToInstanceMap.set(edge, ins);

                    }
                }
            }


        }


        getGraph2D().addDataProvider(PortConstraintKeys.TARGET_GROUPID_KEY, edgeTargetGrouping);
        getGraph2D().addDataProvider(PortConstraintKeys.SOURCE_GROUPID_KEY, edgeSourceGrouping);

        ret[0] = entitiesStr;
        ret[1] = ""; //tagStr;
        ret[2] = graphStr;

        return ret;

    }

    private void doInZoomEdges() {

        for (Instance parentIns : subgraphs.keySet()) {
            ArrayList<Instance> included = subgraphs.get(parentIns);
            if (included.size() > 0) {

                HashMap<OpdKey, Integer> map = new HashMap<OpdKey, Integer>();
                ValueComparatorInteger bvc = new ValueComparatorInteger(map);
                TreeMap<OpdKey, Integer> sorted_map = new TreeMap(bvc);

                for (Instance include : included) {
                    if (include instanceof ThingInstance)
                        map.put(include.getKey(), ((ThingInstance) include).getOrder());
                    sorted_map.put(include.getKey(), ((ThingInstance) include).getOrder());
                }
                //sorted_map.putAll(map);

                //now we need to create edges for the sorted_map .
                Node source;
                Node dest;
                int i = sorted_map.keySet().toArray().length - 1;
                while (i > 0) {

                    Integer targOrder = -1;
                    Integer sourceOrder = -1;
                    ArrayList<OpdKey> keySources = new ArrayList<OpdKey>();
                    ArrayList<OpdKey> keyTargets = new ArrayList<OpdKey>();

                    OpdKey keySource = (OpdKey) sorted_map.keySet().toArray()[i];
                    sourceOrder = map.get(keySource);

                    //i--;
                    keySources.add(keySource);
                    while ((i > 0) && (map.get(sorted_map.keySet().toArray()[i - 1]) == map.get(keySource))) {
                        i--;
                        keySources.add((OpdKey) sorted_map.keySet().toArray()[i]);
                    }


                    if (i > 0) {
                        i--;
                        OpdKey keyTarget = (OpdKey) sorted_map.keySet().toArray()[i];
                        keyTargets.add(keyTarget);
                        while ((i > 0) && (map.get(sorted_map.keySet().toArray()[i - 1]) == map.get(keyTarget))) {
                            i--;
                            keyTargets.add((OpdKey) sorted_map.keySet().toArray()[i]);
                        }
                        targOrder = map.get(keyTarget);

                    }

                    if (targOrder != sourceOrder) {
                        for (OpdKey sourceKey : keySources) {
                            source = n.get(sourceKey);
                            for (OpdKey target : keyTargets) {
                                dest = n.get(target);
                                if (source != null && dest != null) {
                                    EdgeList paths = Paths.findPath(getGraph2D(), source, dest, true);
                                    if ((paths != null) && (paths.size() == 0)) {
                                        Edge edge = getGraph2D().createEdge(source, dest);
                                        PolyLineEdgeRealizer rel = (PolyLineEdgeRealizer) getGraph2D().getRealizer(edge);
                                        rel.setSmoothedBends(true);
                                        rel.setArrow(Arrow.STANDARD);
                                        rel.setLabelText("e");
                                    }
                                }
                            }
                        }
                    }
                    //i-- ;
                }
            }
        }
    }


    private void doSimpleView() {

        String str = "";
        String[] nStr = doNodes();
        String[] eStr = doEdges();
        String[] gStr = null;
        if (params.isDoTimeOrderEdges()) doInZoomEdges();

        if (params.isInZoomAsGroup()) gStr = doGroupNodes();

        str = " ENTITIES \n -------------------\n";
        str = str + nStr[0] + "\n";
        str = str + eStr[0] + "\n\n\n\n";

        str = str + " Containers \n -------------------\n";
        str = str + nStr[1] + "\n";
        str = str + eStr[1] + "\n\n\n\n";

        str = str + " Graph \n -------------------\n";
        str = str + nStr[2] + "\n";
        str = str + eStr[2] + "\n";
        if (params.isInZoomAsGroup()) str = str + gStr[2] + "\n";

        try {
            FileWriter json = new FileWriter(FileControl.getInstance().getOPCATDirectory() + File.separator + "Working Copy" + File.separator + "graph.json");
            json.write(str);
            json.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        if (!params.isShowNonLinksThings()) {
            ArrayList<Node> removeEmptyNodes = new ArrayList<Node>();
            for (int i = 0; i < getGraph2D().getNodeArray().length; i++) {
                if ((getGraph2D().getNodeArray()[i].inDegree() == 0) && (getGraph2D().getNodeArray()[i].outDegree() == 0)) {
                    if (getHm().isGroupNode(getGraph2D().getNodeArray()[i]) || getHm().isFolderNode(getGraph2D().getNodeArray()[i])) {

                    } else {
                        removeEmptyNodes.add(getGraph2D().getNodeArray()[i]);
                    }
                }
            }
            for (Node node : removeEmptyNodes) {
                getGraph2D().removeNode(node);
            }
        }
    }


}


