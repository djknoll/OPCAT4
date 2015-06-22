package gui.opdGraphics.opdBaseComponents;

//import extensionTools.hio.DrawAppNew;

import exportedAPI.OpcatConstants;
import exportedAPI.RelativeConnectionPoint;
import gui.metaLibraries.logic.Role;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.OpcatContainer;
import gui.opdGraphics.SelectionRectangle;
import gui.opdGraphics.draggers.OpdBiDirectionalRelation;
import gui.opdGraphics.draggers.OpdLink;
import gui.opdGraphics.draggers.OpdUniDirectionalRelation;
import gui.opdGraphics.popups.ThingPopup;
import gui.opdProject.*;
import gui.opmEntities.OpmProcess;
import gui.opmEntities.OpmThing;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import util.Configuration;
import util.OpcatLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.Enumeration;
import java.util.Vector;

/**
 * <code>OpdThing</code> is a graphic representation of OPM Thing as defined in
 * OPM. It's a superclass for <a href = "OpdObject.html> <code>OpdObject</code>
 * </a> and <a href = "OpdProcess.html> <code>OpdProcess</code> </a> and
 * implements common behaviour for both of them and defines interfaces that are
 * commons for its subclasses. It can be unfolded and zoomed in, it can contain
 * other OPD components: things, links, relatons. This class is abstract and
 * cannot be instantinated directly. Direct instantination has no meaning in
 * OPM, Thing is an absract term in OPM.
 */
public abstract class OpdThing extends OpdConnectionEdge implements
        OpcatContainer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * The shadow width, when OpdThing is drawn with shadow (it means when Thing
     * is physical)
     */


    private boolean shadow;

    private boolean dashed;

    private boolean boldBorder;

    private String textPosition;

    private int startSelX, startSelY;

    private SelectionRectangle selRect;


    // private DrawAppNew drawing = gui.Opcat2.drawing;

    /** ****************HIOTeam******************** */

    /**
     * <strong>No default constructor for this class. </strong> <br>
     * <p>
     * This is an abstract class and cannot be instantinated directely. All
     * subclasses have to call <code>super()</code> in their constructors proper
     * initialization of parameters given as arguments
     * </p>
     *
     * @param pOpdId         uniqe identifier of the OPD this
     *                       component is added to.
     * @param pEntityInOpdId uniqe identifier of the
     *                       component within OPD it is added to.
     * @param pProject       project this component is added
     *                       to.
     * @param pEntry         the entry of the thing, this
     *                       <code>OpdThing</code> represents graphicaly.
     */
    public OpdThing(long pOpdId, long pEntityInOpdId, OpdProject pProject,
                    ThingEntry pEntry) {
        super(pOpdId, pEntityInOpdId, pProject);
        this.myEntry = pEntry;
        this.myEntity = (OpmThing) pEntry.getLogicalEntity();
        this.setShadow(((OpmThing) this.myEntity).isPhysical());
        this.setDashed(((OpmThing) this.myEntity).isEnviromental());
        this.setTextPosition("C");
        this.selRect = new SelectionRectangle(pProject);
        this.add(this.selRect, JLayeredPane.DRAG_LAYER);
        setSentToBAck(DEFAULT_SENT_TO_BACK);
    }

    /**
     * @return the string repersentation of Thing, actually returns Entity name.
     */
    public String toString() {
        return this.myEntity.getName();
    }

    /**
     * Returns the name of the element.
     */
    public String getName() {
        return this.myEntity.getName();
    }

    /**
     * When component is drawn, there is some spare spase, for selection
     * drawing, or if thing is phisical it's drawn with shadow, this method
     * returns the width of the <code>OpdThing</code> with no counting this
     * spare space, where the original <code>getWidth()</code> method inherited
     * from superclass counts all space the component occupes.
     *
     * @retun the actual width of the thing, no counting spare space for
     * selection or shadow.
     */
    public int getActualWidth() {
        if (this.isShadow()) {
            return this.getWidth() - shadowOffset;
        } else {
            return this.getWidth();
        }
    }

    public void setActualSize(int width, int height) {
        if (this.isShadow()) {
            this.setSize(width + shadowOffset, height + shadowOffset);
        } else {
            this.setSize(width, height);
        }
    }

    /**
     * When component is drawn, there is some spare spase, for selection
     * drawing, or if thing is phisical it's drawn with shadow, this method
     * returns the height of the <code>OpdThing</code> with no counting this
     * spare space, where the original <code>getHeight()</code> method inherited
     * from superclass counts all space the component occupes.
     *
     * @retun the actual height of the thing, no counting spare space for
     * selection or shadow.
     */
    public int getActualHeight() {

        if (this.isShadow()) {
            return this.getHeight() - shadowOffset;
        } else {
            return this.getHeight();
        }
    }

    /**
     * <p>
     * Checks whether this component "contains" the specified point, where x and
     * y are defined to be relative to the coordinate system of this component.
     * </p>
     *
     * @param x the x coordinate of the point.
     * @param y the y coordinate of the point.
     */
    public boolean contains(int x, int y) {
        if ((x >= 0) && (x < this.getActualWidth()) && (y >= 0)
                && (y < this.getActualHeight())) {
            return true;
        }

        return false;
    }

    public void setShadow(boolean shadowed) {
        if (this.shadow && !shadowed) {
            this.setSize(this.getWidth() - shadowOffset, this.getHeight()
                    - shadowOffset);
        } else if (!this.shadow && shadowed) {
            this.setSize(this.getWidth() + shadowOffset, this.getHeight()
                    + shadowOffset);
        }

        this.shadow = shadowed;
        this.repaint();
    }

    public boolean isShadow() {
        return this.shadow;
    }

    public void setDashed(boolean dashed) {
        this.dashed = dashed;
        this.repaint();
    }

    public boolean isDashed() {
        return this.dashed;
    }

    public boolean isBoldBorder() {
        return this.boldBorder;
    }

    public void setBoldBorder(boolean bold) {
        this.boldBorder = bold;
        this.repaint();
    }

    public void setTextPosition(String tp) {
        this.textPosition = tp;
    }

    public String getTextPosition() {
        return this.textPosition;
    }

    public void showPopupMenu(int pX, int pY) {
        JPopupMenu jpm = new ThingPopup(this.myProject, this.getInstance(), pX,
                pY);
        // Point showPoint = GraphicsUtils.findPlace4Menu(this, new Point(pX,
        // pY),
        // jpm.getPreferredSize());
        // jpm.show(this, (int) showPoint.getX(), (int) showPoint.getY());
        jpm.show(this, pX, pY);
    }

    private void _addChilds2Sizes(OpdConnectionEdge rootThing) {
        Component components[] = rootThing.getComponents();

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof OpdConnectionEdge) {
                OpdConnectionEdge t = (OpdConnectionEdge) components[i];
                this._addChilds2Sizes(t);
                this.objects2Move.put(t, new Rectangle(t.getX(), t.getY(), t
                        .getWidth(), t.getHeight()));
            }
        }

    }

    protected void addObjects2Move() {
        this._addChilds2Sizes(this);
    }

    // private method !!! uses local constants with no 'defines'
    // Gets 1 - things
    // 2 - links
    // 3 - gen rel
    // 4 - fund rel
    // This method uses java methods to get components!!

    private Enumeration _getIncludedSomething(int what) {
        Component[] comps = this.getComponents();
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
                    if ((comps[i] instanceof OpdBiDirectionalRelation)
                            || (comps[i] instanceof OpdUniDirectionalRelation)) {
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
        return this._getIncludedSomething(1);
    }

    public Enumeration getIncludedLinks() {
        return this._getIncludedSomething(2);
    }

    public Enumeration getIncludedGeneralRelations() {
        return this._getIncludedSomething(3);
    }

    public Enumeration getIncludedFundamentalRelations() {
        return this._getIncludedSomething(4);
    }

    public Container getContainer() {
        return this;
    }

    public BaseGraphicComponent[] graphicalSelectionComponents() {
        return ((OpcatContainer) this.getContainer()).getSelection()
                .graphicalSelectionComponents();
    }

    public Enumeration getGraphicalSelection() {
        // return multiSel.getGraphicalSelection();
        return null;
    }


    public void mousePressed(MouseEvent e) {

        if (StateMachine.isWaiting()) {
            return;
        }

        if (((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                .isZoomedIn()
                && ((StateMachine.getCurrentState() == StateMachine.USUAL_SELECT) || (StateMachine
                .getCurrentState() == StateMachine.EDIT_CUT))) {
            // myProject.getCurrentOpd().removeSelection();

            // getSelection().initSelection(this, this);
            this.startSelX = e.getX();
            this.startSelY = e.getY();
            this.selRect.setVisible(true);
            // getSelection().ini
        }
        // /** **************HIOTeam**************** */
        // //
        // else if (((ThingInstance) (this.getEntry()
        // .getInstance(this.getOpdKey()))).isZoomedIn()
        // && (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW)) {
        //
        // if (SwingUtilities.isRightMouseButton(e)
        // || SwingUtilities.isMiddleMouseButton(e)) {
        // return;
        // }
        // if (this.drawing == null) {
        // this.drawing = gui.Opcat2.drawing;
        // }
        // Point eventPoint = SwingUtilities.convertPoint(this, e.getX(), e
        // .getY(), this.myProject.getCurrentOpd().getDrawingArea());
        // this.drawing.ourMousePressed(eventPoint);
        // return;
        // }
        // /** **************HIOTeam*********** */

        super.mousePressed(e);
    }

    /**
     * Handles dragging of things. The method takes care of the following
     * scenarios: - if in merge reuse mode, then do merge command <br>
     * - if not in select mode just do super.mouseDraged(), and move/resize the
     * thing. <br>
     * - if the thing is in-zoomed, then do super.mouseDraged(). Old behavior
     * (if in selection mode select the contents of the zomed-in thing) was
     * cancelled by Eran Toch
     */
    public void mouseDragged(MouseEvent e) {
        if (StateMachine.isWaiting()) {
            return;
        }

        // Handling cases in which the thing has not got any zooming ins
        if (!((ThingInstance) (this.getEntry().getInstance(this.getOpdKey())))
                .isZoomedIn()) {
            super.mouseDragged(e);

        } else if ((StateMachine.getCurrentState() == StateMachine.ADD_LINK)
                || (StateMachine.getCurrentState() == StateMachine.ADD_GENERAL_RELATION)
                || (StateMachine.getCurrentState() == StateMachine.ADD_RELATION)) {
            super.mouseDragged(e);
        } else {
            if ((StateMachine.getCurrentState() == StateMachine.USUAL_MOVE)
                    || (StateMachine.getCurrentState() == StateMachine.USUAL_SELECT)) {
                super.mouseDragged(e);
            }

            // // /****************HIOTeam*****************///
            // if (((ThingInstance)
            // (this.getEntry().getInstance(this.getOpdKey())))
            // .isZoomedIn()
            // && (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW)) {
            // Point eventPoint = SwingUtilities.convertPoint(this, e.getX(),
            // e.getY(), this.myProject.getCurrentOpd()
            // .getDrawingArea());
            // this.drawing.ourMouseDragged(eventPoint);
            // return;
            // }
            // // /****************HIOTeam end*****************///

            if (StateMachine.getCurrentState() == StateMachine.IN_SELECTION) {
                int minX = Math.min(this.startSelX, e.getX());
                int minY = Math.min(this.startSelY, e.getY());
                int maxX = Math.max(this.startSelX, e.getX());
                int maxY = Math.max(this.startSelY, e.getY());
                if (this.contains(e.getX(), e.getY())) {
                    this.selRect.setLocation(minX, minY);
                    this.selRect.setSize(maxX - minX, maxY - minY);
                    this.getSelection().inSelection();
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

        try {
            GraphicsUtils.setLastMouseEvent(e);

            if (!e.isPopupTrigger()) {
                ((ThingInstance) this.getInstance()).reCalculateOrder();
            }

            if (StateMachine.isWaiting()) {
                return;
            }
            // // /****************HIOTeam*****************///
            // if (((ThingInstance)
            // (this.getEntry().getInstance(this.getOpdKey())))
            // .isZoomedIn()
            // && (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW)) {
            // if (e == null) {
            // return;
            // }
            //
            // if (SwingUtilities.isMiddleMouseButton(e)) {
            // return;
            // }
            //
            // if (SwingUtilities.isRightMouseButton(e)) {
            // super.mouseReleased(e);
            // } else {
            // this.drawing.ourMouseReleased(e);
            // }
            // return;
            // }
            // /** **************HIOTeam end*********** */

            if (StateMachine.getCurrentState() != StateMachine.IN_SELECTION) {
                super.mouseReleased(e);
            } else {
                this.getSelection().finishSelection();
                this.selRect.setVisible(false);
                // add code for selection of entities
                this.selRect.setSize(0, 0);
                try {
                    StateMachine.go(StateMachine.USUAL_SELECT, 0);
                } catch (IllegalPassException ipe) {
                    ipe.printStackTrace();
                }
            }
        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }


    public Selection getSelection() {
        return this.myProject.getComponentsStructure().getOpd(this.getOpdId())
                .getSelection();
    }

    public SelectionRectangle getSelectionRectangle() {
        return this.selRect;
    }

    /**
     * Calls the Dialog Window which allows to edit component's properties. This
     * method is invoked when user performs double click on the
     * component.Abstract method, defines interface for subclasses
     */
    public abstract boolean callPropertiesDialogWithFeedback(int showTabs,
                                                             int showButtons);

    protected void drawExtData(Graphics2D g2) {

        if(myInstance == null) return; ;
        if(myInstance.getOpd() == null) return ;
        if (!(myInstance.getOpd().isShowExtData())) return;

        Font currentFont = (Font) config.getProperty("TimesFont");
        currentFont = currentFont
                .deriveFont((float) (currentFont.getSize2D() * factor));
        g2.setColor(Configuration.getInstance().getColorFromProperty("graphics.fonts.TimesFont.color"));
        g2.setFont(currentFont);
        this.currentMetrics = this.getFontMetrics(currentFont);

        Point tempPoint = this
                .getAbsoluteConnectionPoint(new RelativeConnectionPoint(
                        OpcatConstants.N_BORDER, 0.2));
        int stringX, stringY;

        int i = 0;
        for (Object key : myInstance.getEntry().getMyProps().keySet()) {
            if (key.toString().startsWith("entry.extdata.")) {
                String val = myInstance.getEntry().getMyProps().getProperty(key.toString());

                boolean show = false;
                float fVal = 0;

                try {
                    fVal = Float.valueOf(val);

                    if (fVal == 0) {
                        continue;
                    }

                } catch (Exception ex) {

                }

                i++;

                String data = key.toString().substring(key.toString().lastIndexOf(".") + 1 , key.toString().length()) + " : " +(fVal == 0 ? val : fVal) ;
                stringY = (int) (i * this.currentMetrics.getHeight() + tempPoint.getY() - 3 * factor);
                stringX =  10  ; //(int) (tempPoint.getX());
                g2.drawString(data, stringX, stringY);
                setTextPosition("S");
            }


        }


        return;
    }

}
