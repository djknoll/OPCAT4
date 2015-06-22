package gui.opdProject.simplifiedOPM.yWorksHelpers;

import y.base.Node;
import y.layout.LayoutGraph;
import y.layout.LayoutOrientation;
import y.layout.Layouter;
import y.layout.hierarchic.HierarchicGroupLayouter;
import y.layout.hierarchic.HierarchicLayouter;
import y.layout.hierarchic.IncrementalHierarchicLayouter;
import y.layout.hierarchic.incremental.LayerConstraintFactory;
import y.layout.organic.OrganicLayouter;
import y.layout.organic.SmartOrganicLayouter;
import y.layout.orthogonal.DirectedOrthogonalLayouter;
import y.layout.orthogonal.OrthogonalGroupLayouter;

/**
 * Created by raanan.
 * Date: 08/06/11
 * Time: 13:55
 */
public class OPMLayouter implements Layouter {

    static private int status = 5;
    boolean glChoosen = false;
    boolean shuffle = false;
    Layouter myLAyouter;
    //HashMap<Long, NodeList> layers = new HashMap<Long, NodeList>();

    public Layouter getMyLAyouter() {
        return myLAyouter;
    }

    public OPMLayouter(boolean shuffle) {
        super();

        this.shuffle = shuffle;
        myLAyouter = layoutFactory();
    }

    public boolean canLayout(LayoutGraph layoutGraph) {
        return true;
    }

    public void addSameLevel(LayoutGraph graph, Node n, Node n1) {
        if ((myLAyouter instanceof IncrementalHierarchicLayouter)) {
            IncrementalHierarchicLayouter il = (IncrementalHierarchicLayouter) myLAyouter;
            LayerConstraintFactory lcf = il.createLayerConstraintFactory(graph);
            lcf.addPlaceNodeInSameLayerConstraint(n, n1);
        }
    }

    public void addAboveLevel(LayoutGraph graph, Node above, Node below) {
        if ((myLAyouter instanceof IncrementalHierarchicLayouter)) {
            IncrementalHierarchicLayouter il = (IncrementalHierarchicLayouter) myLAyouter;
            LayerConstraintFactory lcf = il.createLayerConstraintFactory(graph);
            lcf.addPlaceNodeAboveConstraint(below, above);
        }
    }

    public void doLayout(LayoutGraph graph) {
        try {
            //graph.firePreEvent();

            //GroupLayoutConfigurator glc = null;

            //if (glChoosen) {
            //    glc = new GroupLayoutConfigurator((Graph2D) graph);
            //    glc.prepareAll();
            //}


//            if ((myLAyouter instanceof IncrementalHierarchicLayouter) && (layers != null) && (layers.keySet().size() > 0)) {
//                IncrementalHierarchicLayouter il = (IncrementalHierarchicLayouter) myLAyouter;
//                LayerConstraintFactory lcf = il.createLayerConstraintFactory(graph);
//                for (Long l : layers.keySet()) {
//                    NodeList nl = layers.get(l);
//                    NodeCursor nc = nl.nodes();
//                    if (nl != null && nl.size() > 0) {
//                        Node baseNode = nc.node();
//                        nc.next();
//                        while (nc.ok()) {
//                            Node node = nc.node();
//                            lcf.addPlaceNodeInSameLayerConstraint(baseNode, node);
//                            baseNode = nc.node();
//                            nc.next();
//                        }
//                    }
//                }
//            }
            if (this.shuffle) myLAyouter = layoutFactory();
            myLAyouter.doLayout(graph);


            //if (glChoosen) glc.restoreAll();


            //graph.firePostEvent();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Layouter layoutFactory() {
        Layouter ret = null;

        switch (status) {
            case (1):
                glChoosen = false;
                ret = createSmartOrganicLayouter();
                status = 2;
                break;
            case (2):
                glChoosen = true;
                ret = createOrthogonalGroupLayouter();
                status = 3;
                break;
            case (3):
                glChoosen = false;
                ret = createDirectedOrthogonalLayouter();
                status = 4;
                break;
            case (4):
                glChoosen = false;
                ret = createOrganicLayouter();
                status = 5;
                break;
            case (5):
                glChoosen = false;
                ret = createHierarchicGroupLayouter();
                status = 6;
                break;
            case (6):
                glChoosen = false;
                ret = createHierarchicGroupLayouterLeftToRight();
                status = 5;
                break;
            case (7):
                glChoosen = true;
                ret = createHierarchicLayouter();
                status = 8;
                break;
            case (8):
                glChoosen = true;
                ret = createIncrementalHierarchicLayouter();
                status = 1;
                break;
        }
        return ret;
    }


    private IncrementalHierarchicLayouter createIncrementalHierarchicLayouter() {
        IncrementalHierarchicLayouter hl = new IncrementalHierarchicLayouter();

        hl.setLabelLayouterEnabled(true);
        //hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        //hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);
        hl.setGroupCompactionEnabled(false);
        //hl.setSubgraphLayouterEnabled(true);
        //hl.setSelfLoopLayouterEnabled(true);
        hl.setBackloopRoutingEnabled(true);
        return hl;
    }

    private HierarchicGroupLayouter createHierarchicGroupLayouter() {
        HierarchicGroupLayouter hl = new HierarchicGroupLayouter();
        hl.setLabelLayouterEnabled(false);
        hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);
        return hl;
    }

    private HierarchicGroupLayouter createHierarchicGroupLayouterLeftToRight() {
        HierarchicGroupLayouter hl = new HierarchicGroupLayouter();
        hl.setLabelLayouterEnabled(false);
        hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.LEFT_TO_RIGHT);
        return hl;
    }

    private HierarchicLayouter createHierarchicLayouter() {
        HierarchicLayouter hl = new HierarchicLayouter();
        hl.setLabelLayouterEnabled(false);
        hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);

        return hl;
    }

    private SmartOrganicLayouter createSmartOrganicLayouter() {
        SmartOrganicLayouter hl = new SmartOrganicLayouter();

        hl.setLabelLayouterEnabled(false);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);

        return hl;
    }

    private OrthogonalGroupLayouter createOrthogonalGroupLayouter() {
        OrthogonalGroupLayouter hl = new OrthogonalGroupLayouter();

        hl.setLabelLayouterEnabled(false);
        // hl.setLabelLayouter(new GreedyMISLabeling());

        //hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        //    hl.setLayoutStyle(HierarchicLayouter.MEDIAN_SIMPLEX);
        //hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);

        return hl;
    }

    private DirectedOrthogonalLayouter createDirectedOrthogonalLayouter() {
        DirectedOrthogonalLayouter hl = new DirectedOrthogonalLayouter();

        hl.setLabelLayouterEnabled(false);
        // hl.setLabelLayouter(new GreedyMISLabeling());

        //hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        //    hl.setLayoutStyle(HierarchicLayouter.MEDIAN_SIMPLEX);
        //hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.BOTTOM_TO_TOP);

        return hl;
    }

    private OrganicLayouter createOrganicLayouter() {
        OrganicLayouter hl = new OrganicLayouter();

        hl.setLabelLayouterEnabled(false);
        // hl.setLabelLayouter(new GreedyMISLabeling());

        //hl.setLayoutStyle(HierarchicLayouter.PENDULUM);
        //    hl.setLayoutStyle(HierarchicLayouter.MEDIAN_SIMPLEX);
        //hl.setRoutingStyle(HierarchicLayouter.ROUTE_ORTHOGONAL);
        hl.setLayoutOrientation(LayoutOrientation.TOP_TO_BOTTOM);

        return hl;
    }
}
