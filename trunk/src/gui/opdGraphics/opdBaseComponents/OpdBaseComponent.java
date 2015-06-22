package gui.opdGraphics.opdBaseComponents;

import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPIx.IXNode;
import gui.opdGraphics.GraphicsUtils;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import util.Configuration;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.Enumeration;
import java.util.StringTokenizer;

/**
 * <p/>
 * <code>OpdBaseComponent</code> is a superclass of all OPD components except
 * links and non funadamental relations. It implements all common graphic
 * activities such as resizing, dragging, mouse event handlers and defines
 * interfaces for common properties.
 * <p/>
 * For writing your own custom OPD graphic element you have only to inherit this
 * OpdBaseComponent and implement following methods that describe your component
 * properties<br>
 * public abstract void paint(Graphics g); - paints the component<br>
 * public abstract void callPropertiesDialog(); - calls the Dialog Window which
 * allows to edit component's properties. This method is invoked when user
 * performs double click on the component.<br>
 * public abstract boolean inResize(int pX, int pY); - Test if the point in
 * resizing area i.e. if user drags mouse in this area it resizes the component.
 * <br>
 * public abstract boolean inMove(int pX, int pY); - Test if the point in
 * moviing area i.e. if user drags mouse in this area it moves the component.<br>
 * public abstract int whichBorder(int pX, int pY); - Returns one of constants
 * which determines on which border point (pX, pY) lays.<br>
 */
public abstract class OpdBaseComponent extends BaseGraphicComponent implements
        IXNode {

    /**
     *
     */
    private static final long serialVersionUID = 1779063520696125120L;

    OpmEntity myEntity;

    protected Entry myEntry;


    private long opdId;

    private long entityInOpdId;

    protected OpdKey key;

    protected Instance myInstance;

    private boolean drawMetaData = false;

    String data = "";


    public boolean isDrawMetaData() {
        return drawMetaData;
    }

    public void setDrawMetaData(String data) {
        this.drawMetaData = drawMetaData;

        drawMetaData = ((data == null) || data.trim().equalsIgnoreCase("") ? false : true);
        this.data = data;
    }

    /**
     * <strong>No default constructor for this class.</strong><br>
     * <p>
     * This is an abstract class and cannot be instantinated directely. It
     * initializes default values, all subclasses have to call
     * <code>super()</code> in their constructors to initialize the defaults and
     * set parameters given as arguments
     * </p>
     */
    public OpdBaseComponent(long pOpdId, long pEntityInOpdId,
                            OpdProject pProject) {
        super(pProject);
        this.opdId = pOpdId;
        this.entityInOpdId = pEntityInOpdId;

        key = new OpdKey(this.opdId, this.entityInOpdId);

        myInstance = pProject.getSystemStructure().getInstance(key);
        if (myInstance != null) {
            myEntry = myInstance.getEntry();
            myEntity = myEntry.getLogicalEntity();
            myProject.getSystemStructure().updateParent(myInstance);
        }

        GenericTable config = this.myProject.getConfiguration();
        // currentFont = (Font)config.getProperty("ThingFont");

        this.backgroundColor = (Color) config.getProperty("BackgroundColor");
        this.textColor = (Color) config.getProperty("TextColor");

        this.borderColor = Configuration.getInstance().getColorFromProperty("graphics.default.BaseBorderColor");
        this
                .setMinimumSize(new Dimension(((Integer) config
                        .getProperty("MinimalThingWidth")).intValue(),
                        ((Integer) config.getProperty("MinimalThingHeight"))
                                .intValue()));
        this.selected = false;

        double normalSize = ((Integer) config.getProperty("NormalSize"))
                .doubleValue();
        double currentSize = ((Integer) config.getProperty("CurrentSize"))
                .doubleValue();
        double factor = currentSize / normalSize;
        double width = ((Integer) config.getProperty("ThingWidth")).intValue()
                * factor;
        double height = ((Integer) config.getProperty("ThingHeight"))
                .intValue()
                * factor;

        Opd opd = pProject.getCurrentOpd();
        if ((opd != null)) {
            opd.setViewZoomIn(false);
        }
        // this.setSize((int) width * (int) opd.getWZoomInFactor(),
        // (int) height * (int) opd.getHZoomInFactor());
        // } else {
        this.setSize((int) width, (int) height);
        // }
        this.setLocation(0, 0);

    }

    public Instance getInstance() {
        if (myInstance == null) {
            myInstance = myProject.getSystemStructure()
                    .getInstance(getOpdKey());
        }
        return this.myInstance;
    }

    /**
     * @return ID of the OPD this component belongs to
     */
    public long getOpdId() {
        return this.opdId;
    }

    /**
     * @return ID of the component within current OPD
     */
    public long getEntityInOpdId() {
        return this.entityInOpdId;
    }

    public OpdKey getOpdKey() {
        return key;
    }

    /**
     * @return project this component belongs to
     */
    public OpdProject getProject() {
        return this.myProject;
    }

    /**
     * <code>MouseEvent.mouseReleased(MouseEvent e)</code> event handler
     * Behavior depends on state of <a href =
     * "../opdProject.OpdStateMachine.html"><code>OpdStateMachine</code></a>
     * state. Also sets the mouse cursor.
     */
    public void mouseReleased(MouseEvent e) {
        GraphicsUtils.setLastMouseEvent(e);
        if (StateMachine.isWaiting()) {
            return;
        }

        if ((StateMachine.getCurrentState() == StateMachine.USUAL_SELECT)
                || (StateMachine.getCurrentState() == StateMachine.USUAL_MOVE)
                || (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW)
                || // *********HIOTeam
                (StateMachine.getCurrentState() == StateMachine.IN_SELECTION)
                || (StateMachine.getCurrentState() == StateMachine.EDIT_CUT)) {
            super.mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
            return;
        }

        switch (StateMachine.getCurrentState()) {
            case StateMachine.USUAL_SELECT:
            case StateMachine.USUAL_MOVE: {
                super.mouseDragged(e);
                return;
            }
        }

    }

    /**
     * <code>MouseEvent.mouseEntered(MouseEvent e)</code> event handler Behavior
     * depends on state of <a href = "../opdProject.OpdStateMachine.html">
     * <code>OpdStateMachine</code></a> state. Also sets the mouse cursor.
     */
    public void mouseEntered(MouseEvent e) {
        if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
            return;
        }
        this.setCursorForState(e);

    }

    /**
     * <code>MouseMotionEvent.mouseMoved(MouseMotionEvent e)</code> event
     * handler Behavior depends on state of <a href =
     * "../opdProject.OpdStateMachine.html"><code>OpdStateMachine</code></a>
     * state.
     */
    public void mouseMoved(MouseEvent e) {
        if (StateMachine.isWaiting() || StateMachine.isAnimated()) {
            return;
        }

        this.setCursorForState(e);
    }

    protected void setCursorForState(MouseEvent e) {
        switch (StateMachine.getCurrentState()) {
            case StateMachine.USUAL_DRAW:
//			/** *********HIOTeam*************** */
//			Cursor drawCursor = CursorFactory.getDrawCursor();
//			this.setCursor(drawCursor);
//			break;
//		/** *********HIOTeam*************** */
            case StateMachine.ADD_PROCESS:
            case StateMachine.ADD_OBJECT:
                if (this.isZoomedIn()) {
                    this.setCursor(Cursor
                            .getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                } else {
                    this.setCursorForLocation(e);
                }
                break;

            case StateMachine.ADD_RELATION:
            case StateMachine.ADD_GENERAL_RELATION:
            case StateMachine.ADD_LINK:
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                break;
            case StateMachine.USUAL_SELECT:
            case StateMachine.USUAL_MOVE:
                this.setCursorForLocation(e);
                break;
        }
    }

    public void showPopupMenu(int pX, int pY) {
    }

    public IXNode getParentIXNode() {
        return null;
    }

    public Enumeration getRelatedIXLines() {
        return null;
    }

    protected void _drawMetaData(Graphics2D g2) {


        if (drawMetaData == false) return;


        String role = data;

        int stringX = 0;
        int stringY = 0;
        GenericTable config = this.myProject.getConfiguration();
        double currentSize = ((Integer) config.getProperty("CurrentSize"))
                .doubleValue();
        double normalSize = ((Integer) config.getProperty("NormalSize"))
                .doubleValue();
        double factor = currentSize / normalSize;

        Font currentFont = (Font) config.getProperty("ThingFont");
        currentFont = currentFont
                .deriveFont((float) (currentFont.getSize2D() * factor));
        g2.setFont(currentFont);
        this.currentMetrics = this.getFontMetrics(currentFont);

        double aw = this.getActualWidth();
        double ah = this.getActualHeight();
        Point tempPoint;

        StringTokenizer st = new StringTokenizer(
                data, "\n");

        int numOfRows = 0;
        double sw = 0;

        while (st.hasMoreTokens()) {
            String tString = st.nextToken();
            sw = Math.max(sw, this.currentMetrics.stringWidth(tString));
            numOfRows++;
        }

        st = new StringTokenizer(data, "\n");
        int dY = 0;

        tempPoint = this
                .getAbsoluteConnectionPoint(new RelativeConnectionPoint(
                        OpcatConstants.S_BORDER, (aw - sw) / (2 * aw)));

        stringY = (int) (tempPoint.getY() - 6 - this.currentMetrics
                .getAscent()
                * numOfRows);

        while (st.hasMoreTokens()) {
            String tString = st.nextToken();
            AttributedString ats = new AttributedString(tString);
            ats.addAttribute(TextAttribute.FONT, currentFont);
            stringX = ((int) aw - this.currentMetrics.stringWidth(tString) - 3) / 2;
            dY = dY + this.currentMetrics.getAscent();
            g2.drawString(ats.getIterator(), stringX, stringY + dY);
        }


    }

}