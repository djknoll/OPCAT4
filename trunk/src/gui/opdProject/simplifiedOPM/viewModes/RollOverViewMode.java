package gui.opdProject.simplifiedOPM.viewModes;

import gui.opdProject.simplifiedOPM.realizers.OPMNodeRealizer;
import gui.projectStructure.Instance;
import y.anim.AnimationFactory;
import y.anim.AnimationObject;
import y.anim.AnimationPlayer;
import y.base.Node;
import y.base.NodeMap;
import y.util.DefaultMutableValue2D;
import y.util.Value2D;
import y.view.*;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * Created by raanan.
 * Date: 13/07/11
 * Time: 15:30
 */

/**
 * A <code>ViewMode</code> that produces a roll over effect for nodes
 * under the mouse cursor.
 */
public class RollOverViewMode extends ViewMode {
    /**
     * Animation state constant
     */
    private static final int NONE = 0;
    /**
     * Animation state constant
     */
    private static final int MARKED = 1;
    /**
     * Animation state constant
     */
    private static final int UNMARK = 2;


    /**
     * Preferred duration for roll over effect animations
     */
    private static final int PREFERRED_DURATION = 350;

    /**
     * Scale factor for the roll over effect animations
     */
    private static final Value2D SCALE_FACTOR =
            DefaultMutableValue2D.create(3, 3);


    /**
     * Stores the last node that was marked with the roll over effect
     */
    private Node lastHitNode;
    /**
     * Stores the original size of nodes
     */
    private NodeMap size;
    /**
     * Stores the animation state of nodes
     */
    private NodeMap state;

    private ViewAnimationFactory factory;
    private AnimationPlayer player;

    NodeMap nodeToInstance = null;
    Graph2DView graph2DView = null;

    RollOverViewMode(NodeMap nodesToInstance, Graph2DView graphView) {
        super();
        this.nodeToInstance = nodesToInstance;
        this.graph2DView = graphView;
    }

    public void copyStringToClipboard(String str) {
        StringSelection stringSelection = new StringSelection(str);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    @Override
    public void mouseClicked(double v, double v1) {
        super.mouseClicked(v, v1);    //To change body of overridden methods use File | Settings | File Templates.
        final HitInfo hi = getHitInfo(v, v1);
        if (hi.hasHitNodes()) {
            final Node node = (Node) hi.hitNodes().current();

            Instance e = (Instance) nodeToInstance.get(node);
            copyStringToClipboard(OPMNodeRealizer.getSimplifiedEntryName(e));
        }
    }


    /**
     * Triggers a rollover effect for the first node at the specified location.
     */
    public void mouseMoved(final double x, final double y) {
        final HitInfo hi = getHitInfo(x, y);
        if (hi.hasHitNodes()) {
            final Node node = (Node) hi.hitNodes().current();

            if (node != lastHitNode) {
                unmark(lastHitNode);
            }
            if (state.getInt(node) == NONE) {
                mark(node);
                lastHitNode = node;
            }
        } else {
            unmark(lastHitNode);
            lastHitNode = null;
        }
    }

    /**
     * Overwritten to initialize/dispose this <code>ViewMode</code>'s
     * helper data.
     */
    public void activate(final boolean b) {
        if (b) {
            factory = new ViewAnimationFactory(new Graph2DViewRepaintManager(view));
            player = factory.createConfiguredPlayer();
            size = view.getGraph2D().createNodeMap();
            state = view.getGraph2D().createNodeMap();
        } else {
            view.getGraph2D().disposeNodeMap(state);
            view.getGraph2D().disposeNodeMap(size);
            state = null;
            size = null;
            player = null;
            factory = null;
        }
        super.activate(b);
    }

    /**
     * Overwritten to take only nodes into account for hit testing.
     */
    protected HitInfo getHitInfo(final double x, final double y) {
        final HitInfo hi = new HitInfo(view, x, y, true, HitInfo.NODE);
        setLastHitInfo(hi);
        return hi;
    }

    /**
     * Triggers a <em>mark</em> animation for the specified node.
     * Sets the animation state of the given node to <em>MARKED</em>.
     */
    protected void mark(final Node node) {
        // only start a mark animation if no other animation is playing
        // for the given node
        if (state.getInt(node) == NONE) {
            Instance e = (Instance) nodeToInstance.get(node);
            boolean hasNote = e.getEntry().getProperty("entry.general.notes") != null && !e.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none");
            boolean hasDesc = e.getEntry().getDescription() != null && !e.getEntry().getDescription().equalsIgnoreCase("none");
            if (!hasNote && !hasDesc) return;

            state.setInt(node, MARKED);

            final NodeRealizer nr = getGraph2D().getRealizer(node);

            nr.setLabelText("<html><b> " + OPMNodeRealizer.getSimplifiedEntryName(e) + "</b><br>" + e.getEntry().getCurrentMetaData().replace("\n", "<br>") + "<br>"
                    + (e.getEntry().getProperty("entry.general.notes") != null && !e.getEntry().getProperty("entry.general.notes").equalsIgnoreCase("none") ? "<b>Notes : </b>" + e.getEntry().getProperty("entry.general.notes").replace("\n", "<br>") : "")
                    + (e.getEntry().getDescription() != null && !e.getEntry().getDescription().equalsIgnoreCase("none") ? "<br><b>Description : </b>" + e.getEntry().getDescription().replace("\n", "<br>") : ""));

            double width = (nr.getLabel().getWidth() > nr.getWidth() ? nr.getLabel().getWidth() : nr.getWidth());
            double hight = (nr.getLabel().getHeight() > nr.getHeight() ? nr.getLabel().getHeight() : nr.getHeight());


            Value2D factor;

            double hFactor = (hight / nr.getHeight() > 3 ? (hight / nr.getHeight()) * 1.5 : 3);
            double wFactor = (width / nr.getWidth() > 3 ? (width / nr.getWidth()) * 1.5 : 3);
            factor = DefaultMutableValue2D.create(wFactor, hFactor);

            size.set(node, DefaultMutableValue2D.create(nr.getWidth(), nr.getHeight()));
            nr.setLayer(Graph2DView.FG_LAYER);
            final AnimationObject ao = factory.scale(
                    nr,
                    factor,
                    ViewAnimationFactory.APPLY_EFFECT,
                    PREFERRED_DURATION);
            player.animate(AnimationFactory.createEasedAnimation(ao));


        }

    }

    /**
     * Triggers an <em>unmark</em> animation for the specified node.
     * Sets the animation state of the given node to <em>UNMARKED</em>.
     */
    protected void unmark(final Node node) {
        if (node == null) {
            return;
        }

        // only start an unmark animation if the node is currently marked
        // (or in the process of being marked)
        if (state.getInt(node) == MARKED) {
            state.setInt(node, UNMARK);
            final Value2D oldSize = (Value2D) size.get(node);
            Instance e = (Instance) nodeToInstance.get(node);
            final NodeRealizer nr = getGraph2D().getRealizer(node);
            nr.setLabelText(OPMNodeRealizer.getSimplifiedEntryName(e) + "\n" + e.getEntry().getCurrentMetaData());
            nr.setLayer(Graph2DView.BG_LAYER);
            final AnimationObject ao = factory.resize(
                    nr,
                    oldSize,
                    ViewAnimationFactory.APPLY_EFFECT,
                    PREFERRED_DURATION);
            final AnimationObject eao = AnimationFactory.createEasedAnimation(ao);
            player.animate(new Reset(eao, node, nr, oldSize));
        }
    }

    /**
     * Custom animation object that resets node size and state upon disposal.
     */
    private final class Reset implements AnimationObject {
        private AnimationObject ao;
        private final Node node;
        private final NodeRealizer nr;
        private final Value2D oldSize;

        Reset(
                final AnimationObject ao,
                final Node node,
                final NodeRealizer nr,
                final Value2D size
        ) {
            this.ao = ao;
            this.node = node;
            this.nr = nr;
            this.oldSize = size;
        }

        public void initAnimation() {
            ao.initAnimation();
        }

        public void calcFrame(final double time) {
            ao.calcFrame(time);
        }

        /**
         * Resets the target node to its original size and its animation state
         * to <em>NONE</em>.
         */
        public void disposeAnimation() {
            ao.disposeAnimation();
            nr.setSize(oldSize.getX(), oldSize.getY());
            size.set(node, null);
            state.setInt(node, NONE);
        }

        public long preferredDuration() {
            return ao.preferredDuration();
        }
    }
}
