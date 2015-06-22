package gui.opdGraphics.opdBaseComponents;

import exportedAPI.util.OpcatApiException;
import extensionTools.hio.DrawAppNew;
import extensionTools.reuse.MergeCommand;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.OpcatContainer;
import gui.opdGraphics.SelectionRectangle;
import gui.opdGraphics.draggers.OpdBiDirectionalRelation;
import gui.opdGraphics.draggers.OpdLink;
import gui.opdGraphics.draggers.OpdUniDirectionalRelation;
import gui.opdGraphics.popups.ThingPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.IllegalPassException;
import gui.opdProject.OpdProject;
import gui.opdProject.Selection;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmThing;
import gui.projectStructure.Instance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JLayeredPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


//endReuseComment
/**
 * <code>OpdThing</code> is a graphic representation of OPM Thing as defined
 * in OPM. It's a superclass for <a href = "OpdObject.html>
 * <code>OpdObject</code> </a> and <a href = "OpdProcess.html>
 * <code>OpdProcess</code> </a> and implements common behaviour for both of
 * them and defines interfaces that are commons for its subclasses. It can be
 * unfolded and zoomed in, it can contain other OPD components: things, links,
 * relatons. This class is abstract and cannot be instantinated directly. Direct
 * instantination has no meaning in OPM, Thing is an absract term in OPM.
 */
public abstract class OpdThing extends OpdConnectionEdge implements
        OpcatContainer {

    /**
     * The shadow width, when OpdThing is drawn with shadow (it means when Thing
     * is physical)
     */

    protected static final int SHADOW_OFFSET = 10;

    private boolean shadow;

    private boolean dashed;

    private boolean boldBorder;

    private String textPosition;

    private int startSelX, startSelY;

    private SelectionRectangle selRect;

    private DrawAppNew drawing = gui.Opcat2.drawing;

    /** ****************HIOTeam******************** */

    //reuseComment
    private Instance targetInstance;

    private ThingInstance draggedTInstance;

    //endReuseComment

    /**
     * <strong>No default constructor for this class. </strong> <br>
     * <p>
     * This is an abstract class and cannot be instantinated directely. All
     * subclasses have to call <code>super()</code> in their constructors
     * proper initialization of parameters given as arguments
     * </p>
     * 
     * @param <code>pOpdId long</code>, uniqe identifier of the OPD this
     *            component is added to.
     * @param <code>pEntityInOpdId long</code>, uniqe identifier of the
     *            component within OPD it is added to.
     * @param <code>pProject OpdProject</code>, project this component is added
     *            to.
     * @param <code>pEntry ThingEntry</code>, the entry of the thing, this
     *            <code>OpdThing</code> represents graphicaly.
     */
    public OpdThing(long pOpdId, long pEntityInOpdId, OpdProject pProject,
            ThingEntry pEntry) {
        super(pOpdId, pEntityInOpdId, pProject);
        myEntry = pEntry;
        myEntity = (OpmThing) pEntry.getLogicalEntity();
        setShadow(((OpmThing) myEntity).isPhysical());
        setDashed(((OpmThing) myEntity).isEnviromental());
        setTextPosition("C");
        selRect = new SelectionRectangle(pProject);
        this.add(selRect, JLayeredPane.DRAG_LAYER);
    }

    /**
     * @return the string repersentation of Thing, actually returns Entity name.
     */
    public String toString() {
        return myEntity.getName();
    }

    /**
     * Returns the name of the element.
     */
    public String getName() {
        return myEntity.getName();
    }

    /**
     * When component is drawn, there is some spare spase, for selection
     * drawing, or if thing is phisical it's drawn with shadow, this method
     * returns the width of the <code>OpdThing</code> with no counting this
     * spare space, where the original <code>getWidth()</code> method
     * inherited from superclass counts all space the component occupes.
     * 
     * @retun the actual width of the thing, no counting spare space for
     *        selection or shadow.
     */
    public int getActualWidth() {
        if (isShadow()) {
            GenericTable config = myProject.getConfiguration();
            double currentSize = ((Integer) config.getProperty("CurrentSize"))
                    .doubleValue();
            double normalSize = ((Integer) config.getProperty("NormalSize"))
                    .doubleValue();
            double factor = currentSize / normalSize;
            int shadowOffset = Math.round((float) (SHADOW_OFFSET * factor));

            return getWidth() - shadowOffset;
        } else {
            return getWidth();
        }
    }

    public void setActualSize(int width, int height) {
        if (isShadow()) {
            GenericTable config = myProject.getConfiguration();
            double currentSize = ((Integer) config.getProperty("CurrentSize"))
                    .doubleValue();
            double normalSize = ((Integer) config.getProperty("NormalSize"))
                    .doubleValue();
            double factor = currentSize / normalSize;
            int shadowOffset = Math.round((float) (SHADOW_OFFSET * factor));

            setSize(width + shadowOffset, height + shadowOffset);
        } else {
            setSize(width, height);
        }
    }

    /**
     * When component is drawn, there is some spare spase, for selection
     * drawing, or if thing is phisical it's drawn with shadow, this method
     * returns the height of the <code>OpdThing</code> with no counting this
     * spare space, where the original <code>getHeight()</code> method
     * inherited from superclass counts all space the component occupes.
     * 
     * @retun the actual height of the thing, no counting spare space for
     *        selection or shadow.
     */
    public int getActualHeight() {

        if (isShadow()) {
            GenericTable config = myProject.getConfiguration();
            double currentSize = ((Integer) config.getProperty("CurrentSize"))
                    .doubleValue();
            double normalSize = ((Integer) config.getProperty("NormalSize"))
                    .doubleValue();
            double factor = currentSize / normalSize;
            int shadowOffset = Math.round((float) (SHADOW_OFFSET * factor));

            return getHeight() - shadowOffset;
        } else {
            return getHeight();
        }
    }

    /**
     * <p>
     * Checks whether this component "contains" the specified point, where x and
     * y are defined to be relative to the coordinate system of this component.
     * </p>
     * 
     * @param <code>x</code> the x coordinate of the point.
     * @param <code>y</code> the y coordinate of the point.
     */
    public boolean contains(int x, int y) {
        if (x >= 0 && x < getActualWidth() && y >= 0 && y < getActualHeight()) {
            return true;
        } else {
            return false;
        }

    }

    public void setShadow(boolean shadowed) {
        GenericTable config = myProject.getConfiguration();
        double currentSize = ((Integer) config.getProperty("CurrentSize"))
                .doubleValue();
        double normalSize = ((Integer) config.getProperty("NormalSize"))
                .doubleValue();
        double factor = currentSize / normalSize;
        int shadowOffset = Math.round((float) (SHADOW_OFFSET * factor));

        if (this.shadow && !shadowed) {
            setSize(getWidth() - shadowOffset, getHeight() - shadowOffset);
        } else if (!this.shadow && shadowed) {
            setSize(getWidth() + shadowOffset, getHeight() + shadowOffset);
        }

        this.shadow = shadowed;
        repaint();
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
        repaint();
    }

    public boolean isDashed() {
        return dashed;
    }

    public boolean isBoldBorder() {
        return boldBorder;
    }

    public void setBoldBorder(boolean bold) {
        boldBorder = bold;
        repaint();
    }

    public void setTextPosition(String tp) {
        textPosition = tp;
    }

    public String getTextPosition() {
        return textPosition;
    }

    public void showPopupMenu(int pX, int pY) {
        JPopupMenu jpm = new ThingPopup(this.myProject, getInstance(), pX, pY);
        Point showPoint = GraphicsUtils
                .findPlace4Menu(this, new Point(pX, pY), jpm.getPreferredSize());
        jpm.show(this, (int) showPoint.getX(), (int) showPoint.getY());
    }

    private void _addChilds2Sizes(OpdConnectionEdge rootThing) {
        Component components[] = rootThing.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof OpdConnectionEdge) {
                OpdConnectionEdge t = (OpdConnectionEdge) components[i];
                _addChilds2Sizes(t);
                objects2Move.put(t, new Rectangle(t.getX(), t.getY(), t
                        .getWidth(), t.getHeight()));
            }
        }

    }

    protected void addObjects2Move() {
        _addChilds2Sizes(this);
    }

    // private method !!! uses local constants with no 'defines'
    // Gets 1 - things
    //      2 - links
    //      3 - gen rel
    //      4 - fund rel
    // This method uses java methods to get components!!
    private Enumeration _getIncludedSomething(int what) {
        Component[] comps = getComponents();
        Vector retVector = new Vector();
        if (this.isZoomedIn()) {
            return retVector.elements();
        }
        for (int i = 0; i < comps.length; i++) {
            switch (what) {
            case 1:
                if (comps[i] instanceof OpdThing) {
                    retVector.add(comps[i]);
                }
                break;
            case 2:
                if (comps[i] instanceof OpdLink) {
                    retVector.add(comps[i]);
                }
                break;
            case 3:
                if (comps[i] instanceof OpdBiDirectionalRelation
                        || comps[i] instanceof OpdUniDirectionalRelation) {
                    retVector.add(comps[i]);
                }
                break;
            case 4:
                if (comps[i] instanceof OpdFundamentalRelation) {
                    retVector.add(comps[i]);
                }
                break;
            }
        }
        return retVector.elements();
    }

    // The following 4 methods returns OpdXxx.. not Xxx..Instances
    public Enumeration getIncludedThings() {
        return _getIncludedSomething(1);
    }

    public Enumeration getIncludedLinks() {
        return _getIncludedSomething(2);
    }

    public Enumeration getIncludedGeneralRelations() {
        return _getIncludedSomething(3);
    }

    public Enumeration getIncludedFundamentalRelations() {
        return _getIncludedSomething(4);
    }

    public Container getContainer() {
        return this;
    }

    public BaseGraphicComponent[] graphicalSelectionComponents() {
        return ((OpcatContainer) this.getContainer()).getSelection()
                .graphicalSelectionComponents();
    }

    public Enumeration getGraphicalSelection() {
        //		return multiSel.getGraphicalSelection();
        return null;
    }

    public void mousePressed(MouseEvent e) {
        if (StateMachine.isWaiting()) {
            return;
        }

        if (((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                .isZoomedIn()
                && StateMachine.getCurrentState() == StateMachine.USUAL_SELECT) {
            //			myProject.getCurrentOpd().removeSelection();

            //			getSelection().initSelection(this, this);
            startSelX = e.getX();
            startSelY = e.getY();
            selRect.setVisible(true);
            //			getSelection().ini
        }
        /** **************HIOTeam**************** */
        //
        else if (((ThingInstance) (this.getEntry()
                .getInstance(this.getOpdKey()))).isZoomedIn()
                && StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {

            if (SwingUtilities.isRightMouseButton(e)
                    || SwingUtilities.isMiddleMouseButton(e)) {
                return;
            }
            if (drawing == null)
                drawing = gui.Opcat2.drawing;
            Point eventPoint = SwingUtilities.convertPoint(this, e.getX(), e
                    .getY(), myProject.getCurrentOpd().getDrawingArea());
            drawing.ourMousePressed(eventPoint);
            return;
        }
        /** **************HIOTeam*********** */

        super.mousePressed(e);
    }

    /**
     * Handles dragging of things. The method takes care of the following
     * scenarios: - if in merge reuse mode, then do merge command <br>- if not
     * in select mode just do super.mouseDraged(), and move/resize the thing.
     * <br>- if the thing is in-zoomed, then do super.mouseDraged(). Old
     * behavior (if in selection mode select the contents of the zomed-in thing)
     * was cancelled by Eran Toch
     */
    public void mouseDragged(MouseEvent e) {
        if (StateMachine.isWaiting()) {
            return;
        }

        //Open reuse restrictions
        if (myProject.getCurrentOpd().isOpenReused()) {
            Point opdPoint = myProject.getCurrentOpd().getDrawingArea()
                    .getLocationOnScreen();
            Point pElm = this.getLocation();
            int OpdHeight = myProject.getCurrentOpd().getDrawingArea()
                    .getHeight() / 2;
            if (this.getEntry().isOpenReused()) {
                if ((((pElm.getY()) > (OpdHeight * 0.85 - 20)) && (e.getY() > 0)))
                    return;
            } else {
                if ((((pElm.getY()) < (OpdHeight)))) {
                    this.setLocation(this.getX(), OpdHeight + 30);
                    return;
                } else if (e.getY() > 30)
                    return;
            }
        }
        //End Open reuse restrictions

        //Handling cases in which the thing has not got any zooming ins
        if (!((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                .isZoomedIn()) {
            super.mouseDragged(e);

            //Handling reuse merge option
            if (myProject.getEnableDragging()) {
                draggedTInstance = (ThingInstance) (this.getEntry()
                        .getInstance(this.getOpdKey()));
                targetInstance = myProject
                        .returnThingIntersection(draggedTInstance);
                if (targetInstance != null) {
                    if (draggedTInstance.getTargetThingInstance() == null) {
                        draggedTInstance
                                .setTargetThingInstance((ThingInstance) targetInstance);
                        draggedTInstance
                                .setTargetBackgroundColor(targetInstance
                                        .getBackgroundColor());
                        targetInstance.setBackgroundColor(new Color(255, 0, 0));
                        targetInstance.update();
                    }
                } else {
                    if (draggedTInstance.getTargetThingInstance() != null) {
                        draggedTInstance.getTargetThingInstance()
                                .setBackgroundColor(draggedTInstance
                                        .getTargetBackgroundColor());
                        draggedTInstance.getTargetThingInstance().update();
                        draggedTInstance.setTargetBackgroundColor(null);
                        draggedTInstance.setTargetThingInstance(null);
                    }
                }
            }
            //End merge action
            //Handling cases in which the dragged thing is the container thing
            // (zoomed into)
        } else if (StateMachine.getCurrentState() == StateMachine.ADD_LINK
                || StateMachine.getCurrentState() == StateMachine.ADD_GENERAL_RELATION
                || StateMachine.getCurrentState() == StateMachine.ADD_RELATION) {
            super.mouseDragged(e);
        } else {
            if (StateMachine.getCurrentState() == StateMachine.USUAL_MOVE
                    || StateMachine.getCurrentState() == StateMachine.USUAL_SELECT) {
                super.mouseDragged(e);
            }
            //Remarked by Eran Toch - in order to enable sizing containers
            // while in select mode
            //			if (StateMachine.getCurrentState() == StateMachine.USUAL_SELECT)
            // {
            //				try {
            //					StateMachine.go(StateMachine.IN_SELECTION, 0);
            //				} catch (IllegalPassException ipe) {
            //					ipe.printStackTrace();
            //				}
            //
            //				int minX = Math.min(startSelX, e.getX());
            //				int minY = Math.min(startSelY, e.getY());
            //				int maxX = Math.max(startSelX, e.getX());
            //				int maxY = Math.max(startSelY, e.getY());
            //				getSelection().initSelection(this, this);
            //				selRect.setLocation(minX, minY);
            //				selRect.setSize(maxX - minX, maxY - minY);
            //				getSelection().inSelection();
            //				return;
            //			}

            ///****************HIOTeam*****************///
            if (((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                    .isZoomedIn()
                    && StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {
                Point eventPoint = SwingUtilities
                        .convertPoint(this, e.getX(), e.getY(), myProject
                                .getCurrentOpd().getDrawingArea());
                drawing.ourMouseDragged(eventPoint);
                return;
            }
            ///****************HIOTeam end*****************///

            if (StateMachine.getCurrentState() == StateMachine.IN_SELECTION) {
                int minX = Math.min(startSelX, e.getX());
                int minY = Math.min(startSelY, e.getY());
                int maxX = Math.max(startSelX, e.getX());
                int maxY = Math.max(startSelY, e.getY());
                if (this.contains(e.getX(), e.getY())) {
                    selRect.setLocation(minX, minY);
                    selRect.setSize(maxX - minX, maxY - minY);
                    getSelection().inSelection();
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        //reuseComment
        Instance targetInstance;
        //endReuseComment
        if (StateMachine.isWaiting()) {
            return;
        }
        ///****************HIOTeam*****************///
        if (((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                .isZoomedIn()
                && StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {
            if (e == null)
                return;

            if (SwingUtilities.isMiddleMouseButton(e))
                return;

            if (SwingUtilities.isRightMouseButton(e)) {
                super.mouseReleased(e);
            } else {
                drawing.ourMouseReleased(e);
            }
            return;
        }
        /** **************HIOTeam end*********** */

        if (StateMachine.getCurrentState() != StateMachine.IN_SELECTION) {
            super.mouseReleased(e);

            /****************Commiting Merge*********** */
            targetInstance = myProject
                    .returnThingIntersection((ThingInstance) (this.getEntry()
                            .getInstance(this.getOpdKey())));
            if (targetInstance != null) {
                if (!((targetInstance.getEntry().isLocked()) || this.getEntry()
                        .isLocked())) {
                    MergeCommand command = new MergeCommand(myProject);
                    
                    try {
                        command.commitMerge((ThingInstance) (this
                                .getEntry().getInstance(this.getOpdKey())), (ThingInstance) targetInstance);
                    } catch (OpcatApiException e1) {
                        
                    }
                    
                    if (draggedTInstance.getTargetThingInstance() != null) {
                        draggedTInstance.getTargetThingInstance()
                                .setBackgroundColor(draggedTInstance
                                        .getTargetBackgroundColor());
                        draggedTInstance.getTargetThingInstance().update();
                        draggedTInstance.setTargetBackgroundColor(null);
                        draggedTInstance.setTargetThingInstance(null);
                    }
                }
            }
            /****************End Commiting Merge*********** */

        } else {
            getSelection().finishSelection();
            selRect.setVisible(false);
            // add code for selection of entities
            selRect.setSize(0, 0);
            try {
                StateMachine.go(StateMachine.USUAL_SELECT, 0);
            } catch (IllegalPassException ipe) {
                ipe.printStackTrace();
            }
        }
    }

    public Selection getSelection() {
        return myProject.getComponentsStructure().getOpd(getOpdId())
                .getSelection();
    }

    public SelectionRectangle getSelectionRectangle() {
        return selRect;
    }

    /**
     * Calls the Dialog Window which allows to edit component's properties.
     * This method is invoked when user performs double click on
     * the component.Abstract method, defines interface for subclasses
     */
    public abstract boolean callPropertiesDialogWithFeedback(int showTabs, int showButtons);

    //reuseComment
    public ThingEntry getMyEntry() {
        return (ThingEntry) myEntry;
    }
    //endReuseComment

}

