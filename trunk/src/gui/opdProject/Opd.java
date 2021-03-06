package gui.opdProject;

import exportedAPI.opcatAPI.IEntry;
import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;
import expose.OpcatExposeConstants.OPCAT_EXPOSE_LINK_DIRECTION;
import gui.Opcat2;
import gui.metaLibraries.logic.Role;
import gui.opdGraphics.*;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.draggers.TextDragger;
import gui.opdGraphics.lines.LinkingLine;
import gui.opdGraphics.lines.OpdLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdThing;
import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedView;
import gui.opdProject.simplifiedOPM.gui.OpdSimplifiedViewParams;
import gui.opdProject.simplifiedOPM.gui.yOpdPanel;
import gui.opmEntities.OpmEntity;
import gui.opmEntities.OpmStructuralRelation;
import gui.projectStructure.*;
import net.java.swingfx.waitwithstyle.PerformanceInfiniteProgressPanel;
import util.Configuration;
import util.OpcatLogger;
import y.option.BoolOptionItem;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.beans.PropertyVetoException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class represents an OPD.
 * <p>
 * This class manages all needed for an OPD: graphics, selections, relations to
 * the repository manager. It also manages all its reletions to the project and
 * application.
 * </p>
 */
public class Opd implements Printable, IOpd, IXOpd, Comparable {

    private boolean view = false;
    private String opdName;

    private long opdId;
    private long entityInOpdMaxEntry;
    private boolean viewZoomIn = false;
    private Dimension normalSize = null;
    private JDesktopPane mainDesktop;
    // private Hashtable selectedItems;
    private OpdContainer opdFrame;

    private OpdProject myProject;
    private Opd parentOpd;
    private ThingEntry mainEntry;
    private ThingInstance mainInstance;
    double WZoomInFactor = 1, HZoomInFactor = 1;
    private Selection mySelection;
    private String treeLevel = "";
    private OpcatProperties properties;
    private JInternalFrame simplifiedView;
    private boolean showExtData = false;

    public boolean isShowExtData() {
        return showExtData;
    }

    public void setShowExtData(boolean showExtData) {
        this.showExtData = showExtData;
    }

    //    public JPanel createSimpleOPMPanel(Graph2DView view, String name) {
//
//        JPanel intPanel = new JPanel();
//
//        view.setAntialiasedPainting(true);
//
//        NavigationComponent navigationComponent = new NavigationComponent(view);
//
//        navigationComponent.setScrollStepSize(5);
//        navigationComponent.putClientProperty("NavigationComponent.ScrollTimerDelay",
//                new Integer(5));
//        navigationComponent.setShowZoomSlider(true);
//
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(navigationComponent, BorderLayout.WEST);
//        panel.setOpaque(false);
//        panel.setBackground(null);
//        Dimension ps = navigationComponent.getPreferredSize();
//        Dimension dim =
//                new Dimension(((int) ps.getWidth() + 15), ((int) ps.getHeight() + 15));
//
//        panel.setPreferredSize(dim);
//        panel.setMinimumSize(dim);
//
//        JPanel glassPane = view.getGlassPane();
//        glassPane.setLayout(new BorderLayout());
//        glassPane.add(panel, BorderLayout.NORTH);
//
//        intPanel.setLayout(new BorderLayout());
//        intPanel.add(view, BorderLayout.CENTER);
//
//        return intPanel;
//    }

    public void showSimplifiedView() {

        simplifiedView = new JInternalFrame(getName());
        myProject.getParentDesktop().add(simplifiedView);

        OpdSimplifiedViewParams params = new OpdSimplifiedViewParams();
        params.setOpdID(this.getOpdId());

        //params.setInZoomAsGroup(true);
        params.setShowFunRelations(true);
        //params.setStripInZoomLinks(true);
        params.setInZoomAsGroup(true);
        params.setDoTimeOrderEdges(true);

        TreeMap<String, String> data = null;
        String moreData = Configuration.getInstance().getProperty("viewer.more.data");
        if (moreData != null) {
            data = new TreeMap<String, String>();
            String[] moreDataArray = moreData.split(";");
            for (String str : moreDataArray) {
                String[] s = str.split(":");
                data.put(s[0], s[1]);
            }
        }
        OpdSimplifiedView view = null;
        if (data == null) {
            view = new OpdSimplifiedView(this, params);
        } else {
            view = new OpdSimplifiedView(this, data, params);

        }
        yOpdPanel opm = new yOpdPanel(view);


        simplifiedView.setContentPane(opm);

        try {
            simplifiedView.setMaximum(true);

        } catch (PropertyVetoException e2) {
            e2.printStackTrace();
        }

        //GuiControl.getInstance().closeOPLPane();

        opm.setPreferredSize(getDesktop().getSize());

        simplifiedView.setVisible(true);

        simplifiedView.show();


    }

    /**
     * Constructor.<br>
     * <strong>There is no default constructor for <code>Opd</code>
     * class.</strong><br>
     * <p>
     * Construacts new <code>Opd</code> that belongs to
     * <code><a href = "OpdProject.html">OpdProject</a> pProject</code>, the
     * name <code>name</code>, ID <code>pOpdId</code>, and <code>fNode</code> as
     * father node in <a href = "RepositoryManger.html">
     * <code>RepositoryManger</code></a> tree. Constructor also constructs Tree
     * Node that represents newly created object in <a href =
     * "RepositoryManger.html"><code>RepositoryManger</code></a>. The new
     * internal frame for new Opcat window is also created and added to the
     * application.
     * </p>
     * <p/>
     * //     * @param pProject <code><a href = "OpdProject.html">OpdProject</a></code>
     * //     *                 the project newly creared <code>Opd</code> belongs to.
     * //     * @param name     the name of new <code>Opd</code> and
     * //     *                 also the title of the Opd frame.
     * //     * @param pOpdId   the ID of new <code>Opd</code>.
     * //     * @param parent   the parent <a href =
     * "RepositoryManger.html"><code>RepositoryManger</code></a> tree
     * node.
     */
//    public Opd(OpdProject pProject, NAME name, long pOpdId, Opd parent) {
//        // selectedItems = new Hashtable();
//        this.entityInOpdMaxEntry = 0;
//        this.myProject = pProject;
//        this.opdName = name;
//        this.opdId = pOpdId;
//        this.mainDesktop = this.myProject.getParentDesktop();
//        this.parentOpd = parent;
//        this.opdFrame = new OpdContainer(this.opdName, this.myProject, this);
//        if (this.mainDesktop != null) {
//            this.mainDesktop.add(this.opdFrame);
//            this.opdFrame.setBounds(0, 0, this.mainDesktop.getWidth() * 3 / 4,
//                    this.mainDesktop.getHeight() * 3 / 4);
//        }
//        this.mainEntry = null;
//        this.mainInstance = null;
//
//        setProperties(new SortedProperties());
//
//        getOpdContainer().setGlassPane(
//                new PerformanceInfiniteProgressPanel(false));
//
//        if (this.mainDesktop != null) {
//            try {
//                this.opdFrame.setMaximum(true);
//            } catch (java.beans.PropertyVetoException e) {
//                OpcatLogger.error(e);
//            }
//        }
//
//        this.mySelection = new Selection(this.myProject, this);
//
//    }
    public void startBusyAnimation() {
        try {
            PerformanceInfiniteProgressPanel pane = (PerformanceInfiniteProgressPanel) getOpdContainer().getGlassPane();

            pane.setVisible(true);
            pane.start();
        } catch (Exception ex) {
            //OpcatLogger.error(ex, false);
        }
    }

    public void stopBusyAnimation() {
        try {
            PerformanceInfiniteProgressPanel pane = (PerformanceInfiniteProgressPanel) getOpdContainer().getGlassPane();

            pane.setVisible(false);
            pane.stop();
        } catch (Exception ex) {
            //OpcatLogger.error(ex, false);
        }
    }

    public Opd(OpdProject pProject, String name, long pOpdId, Opd parent,
               boolean addToDesktop, boolean isView, int idxInLevel) {
        // selectedItems = new Hashtable();
        this.entityInOpdMaxEntry = 0;
        this.myProject = pProject;
        this.opdName = name;
        this.opdId = pOpdId;
        this.mainDesktop = this.myProject.getParentDesktop();
        this.parentOpd = parent;
        this.opdFrame = new OpdContainer(this.getName(), this.myProject, this);

        if ((this.mainDesktop != null) && addToDesktop) {
            this.opdFrame.setBounds(0, 0, this.mainDesktop.getWidth() * 3 / 4,
                    this.mainDesktop.getHeight() * 3 / 4);
        }
        this.mainEntry = null;
        this.mainInstance = null;

        if ((this.mainDesktop != null) && addToDesktop) {
            this.mainDesktop.add(this.opdFrame);
        }
        this.mainEntry = null;
        this.mainInstance = null;

        setView(isView);
        this.mySelection = new Selection(this.myProject, this);

        myProject.getSystemStructure().put(pOpdId, this, idxInLevel);

        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK);
        ActionListener opm = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSimplifiedView();
            }
        };
        opdFrame.getDrawingArea().registerKeyboardAction(opm, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Disposes the frame that represents the Opd.
     */
    public void dispose() {
        // JOptionPane.showConfirmDialog(opdFrame, "OPD : start disposing : " +
        // getName() ) ;

        try {
            if (getDesktop() != null) {
                getDrawingArea().setVisible(false);
            }
            if (opdFrame != null) {
                opdFrame.setVisible(false);
                this.opdFrame.dispose();
            }
        } catch (Exception ex) {
            //OpcatLogger.error(ex, false);
        }
        // JOptionPane.showConfirmDialog(opdFrame, "OPD : end disposing : " +
        // getName() ) ;
    }

    public ArrayList<Instance> getInstancesInOPDOfRole(Role role) {
        Enumeration<Instance> instances = myProject.getSystemStructure().getInstancesInOpd(this);
        ArrayList<Instance> ret = new ArrayList<Instance>();

        while (instances.hasMoreElements()) {
            Instance ins = instances.nextElement();
            OpmEntity opm = ins.getEntry().getLogicalEntity();
            if (opm.getRolesManager().contains(role)) {
                ret.add(ins);
            }
        }
        return ret;
    }

    /**
     * checks if there is an instance in this opd which is connected to another
     * instance which has the given role
     *
     * @param role
     * @param instance
     * @param connectionType
     * @param direction      direction of the connection
     *                       {@link OPCAT_EXPOSE_LINK_DIRECTION#FROM} signifies the
     *                       connection source is the instance
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean isRoleConnectedToInstanceWithLinK(Role role,
                                                     ConnectionEdgeInstance instance, int connectionType,
                                                     OPCAT_EXPOSE_LINK_DIRECTION direction) {

        Enumeration<LinkInstance> links;
        ArrayList<Instance> roleInstances = getInstancesInOPDOfRole(role);

        for (Instance i : roleInstances) {
            if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                links = instance.getRelatedSourceLinks();
            } else {
                links = instance.getRelatedDestinationLinks();
            }
            while (links.hasMoreElements()) {
                LinkInstance link = links.nextElement();
                Instance lookedFor;
                if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                    lookedFor = link.getDestinationInstance();
                } else {
                    lookedFor = link.getSourceInstance();
                }

                if (lookedFor.getKey().equals(i.getKey())) {
                    if (((LinkEntry) link.getEntry()).getLinkType() == connectionType) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean isInstanceConnectedToWithRelation(Role role,
                                                     ConnectionEdgeInstance instance, int connectionType,
                                                     OPCAT_EXPOSE_LINK_DIRECTION direction) {

        Enumeration<FundamentalRelationInstance> funs = null;
        Iterator<GeneralRelationInstance> gens;
        ArrayList<Instance> roleInstances = getInstancesInOPDOfRole(role);

        for (Instance i : roleInstances) {
            if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                funs = instance.getRelatedSourceFundamentalRelation();
                gens = instance.getRelatedSourceGeneralRelations().iterator();
            } else {
                gens = instance.getRelatedDestinationGeneralRelations().iterator();
            }
            if (funs != null) {
                while (funs.hasMoreElements()) {
                    FundamentalRelationInstance link = funs.nextElement();
                    Instance lookedFor;
                    if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                        lookedFor = link.getDestinationInstance();
                    } else {
                        lookedFor = link.getSourceInstance();
                    }

                    if (lookedFor.getKey().equals(i.getKey())) {
                        if (((FundamentalRelationEntry) link.getEntry()).getRelationType() == connectionType) {
                            return true;
                        }
                    }
                }
            }

            if (gens != null) {
                while (gens.hasNext()) {
                    GeneralRelationInstance link = gens.next();
                    Instance lookedFor;
                    if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                        lookedFor = link.getDestinationInstance();
                    } else {
                        lookedFor = link.getSourceInstance();
                    }

                    if (lookedFor.getKey().equals(i.getKey())) {
                        if (((GeneralRelationEntry) link.getEntry()).getRelationType() == connectionType) {
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public boolean isRoleConnectedToInstanceWithRelation(Role role,
                                                         ConnectionEdgeInstance instance, int connectionType,
                                                         OPCAT_EXPOSE_LINK_DIRECTION direction) {

        Enumeration<FundamentalRelationInstance> funs = null;
        Iterator<GeneralRelationInstance> gens;
        ArrayList<Instance> roleInstances = getInstancesInOPDOfRole(role);

        for (Instance i : roleInstances) {
            if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                funs = instance.getRelatedSourceFundamentalRelation();
                gens = instance.getRelatedSourceGeneralRelations().iterator();
            } else {
                funs = instance.getRelatedDestinationFundamentalRelation();
                gens = instance.getRelatedDestinationGeneralRelations().iterator();
            }
            if (funs != null) {
                while (funs.hasMoreElements()) {
                    FundamentalRelationInstance link = funs.nextElement();
                    Instance lookedFor;
                    if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                        lookedFor = link.getDestinationInstance();
                    } else {
                        lookedFor = link.getSourceInstance();
                    }

                    if (lookedFor.getKey().equals(i.getKey())) {
                        if (((FundamentalRelationEntry) link.getEntry()).getRelationType() == connectionType) {
                            return true;
                        }
                    }
                }
            }

            if (gens != null) {
                while (gens.hasNext()) {
                    GeneralRelationInstance link = gens.next();
                    Instance lookedFor;
                    if (direction == OPCAT_EXPOSE_LINK_DIRECTION.FROM) {
                        lookedFor = link.getDestinationInstance();
                    } else {
                        lookedFor = link.getSourceInstance();
                    }

                    if (lookedFor.getKey().equals(i.getKey())) {
                        if (((GeneralRelationEntry) link.getEntry()).getRelationType() == connectionType) {
                            return true;
                        }
                    }
                }
            }

        }

        return false;
    }

    public static String getEntryType(IEntry entry) {
        String type = "";
        if (entry instanceof ObjectEntry) {
            type = ((ObjectEntry) entry).getType();
        }
        type = (type.equalsIgnoreCase("") ? entry.getTypeString() : type);

        return type;
    }

    public ArrayList<Instance> getInstancesInOPDOfType(String typeString) {
        Enumeration<Instance> instances = myProject.getSystemStructure().getInstancesInOpd(this);
        ArrayList<Instance> ret = new ArrayList<Instance>();

        while (instances.hasMoreElements()) {
            Instance ins = instances.nextElement();
            String type = Opd.getEntryType(ins.getEntry());
            if (type.equalsIgnoreCase(typeString)) {
                ret.add(ins);
            }
        }
        return ret;
    }

    /**
     * Shows the frame that represents the Opd.
     */
    public void show() {
        // opdFrame.getDrawingArea().setPreferredSize();
        // resize() ;

        try {
            if (simplifiedView != null) {
                simplifiedView.setVisible(false);
                simplifiedView.dispose();
            }

            getOpdContainer().setSelected(true);
            this.opdFrame.setTitle(this.toString());
            refit(getDrawingArea());
            setVisible(true);
            getOpdContainer().setMaximum(true);
            this.opdFrame.show();

        } catch (Exception ex) {
            OpcatLogger.error(ex);
        }
    }

    /**
     * @return The unique ID of the Opd.
     */
    public long getOpdId() {
        return this.opdId;
    }

    public static String getViewInitials() {
        return Configuration.getInstance().getProperty("entry.opd.view.header");
    }

    public static String getSDInitials() {
        return Configuration.getInstance().getProperty("entry.opd.sd.header");
    }

    /**
     * @return The name of the Opd
     */
    public String getName() {
        return this.opdName;
    }

    public void setName(String name) {
        this.opdName = name;
        this.opdFrame.setTitle(name);
    }

    public Opd getParentOpd() {
        return this.parentOpd;
    }

    public void setParentOpd(Opd parentOpd) {
        this.parentOpd = parentOpd;
    }

    public IOpd getParentIOpd() {
        return this.parentOpd;
    }

    public IXOpd getParentIXOpd() {
        return this.parentOpd;
    }

    /**
     * Returns string representation for the <code>Opd</code>, actualy returns
     * it's name.
     *
     * @return The string representation for the <code>Opd</code>.
     */
    public String toString() {

        String name;
        if (isView()) {

            name = (myProject.getSystemStructure().getOPDTextPath(getViewInitials(), this) == null ? "" : myProject.getSystemStructure().getOPDTextPath(getViewInitials(), this) + " : ") + this.opdName;
        } else {
            name = (myProject.getSystemStructure().getOPDTextPath(getSDInitials(), this) == null ? "" : myProject.getSystemStructure().getOPDTextPath(getSDInitials(), this) + " : ") + this.opdName;
        }
        return name;
    }

    /**
     * This method returns an <a href = "../opdGraphics/OpdContainer.html">
     * <code>OpdContainer</code></a> of the current <code>Opd</code>.
     *
     * @return <code>OpdContainer</code> of the <code>Opd</code>.
     */
    public OpdContainer getOpdContainer() {
        return this.opdFrame;
    }

    /**
     * Before adding component to Opd, we have to get ID and free entry. This
     * method allocates free entry for new component and ID, that actually is a
     * entry index.
     *
     * @return the unique withing this Opd ID.
     */
    public long _getFreeEntityInOpdEntry() {
        this.entityInOpdMaxEntry++;
        return this.entityInOpdMaxEntry;
    }

    /**
     * @return <code>JDesktopPane</code> that the <code>Opd</code> belongs to.
     *         Actualy not <code>Opd</code>, but <a href =
     *         "../opdGraphics/OpdContainer.html"><code>OpdContainer</code></a>,
     *         that represents <code>Opd</code> graphicaly, belongs.
     */
    public JDesktopPane getDesktop() {
        return this.mainDesktop;
    }

    /**
     * @return <code>Opd</code>'s <a href = "../opdGraphics/DrawingArea.html">
     *         <code>DrawingArea</code></a>.
     */
    public DrawingArea getDrawingArea() {
        return this.opdFrame.getDrawingArea();

    }

    public void applyNewBackground(Color newBackgroundColor) {
        GenericTable config = this.myProject.getConfiguration();
        Color oldColor = (Color) config.getProperty("BackgroundColor");
        config.setProperty("BackgroundColor", newBackgroundColor);
        for (Enumeration e1 = this.myProject.getComponentsStructure().getElements(); e1.hasMoreElements(); ) {
            Entry tempEntry = (Entry) e1.nextElement();
            for (Enumeration e2 = tempEntry.getInstances(false); e2.hasMoreElements(); ) {
                Instance tempInstance = (Instance) e2.nextElement();
                if (tempInstance.getBackgroundColor().equals(oldColor) && (tempInstance.getKey().getOpdId() == this.opdId)) {
                    tempInstance.setBackgroundColor(newBackgroundColor);
                }
            }
        }

        for (Enumeration e1 = this.myProject.getComponentsStructure().getGraphicalRepresentations(); e1.hasMoreElements(); ) {
            GraphicalRelationRepresentation tempRepr = (GraphicalRelationRepresentation) e1.nextElement();
            if (tempRepr.getBackgroundColor().equals(oldColor) && (tempRepr.getSource().getOpdId() == this.opdId)) {
                tempRepr.setBackgroundColor(newBackgroundColor);
            }
        }

    }

    /**
     * Prints the current OPD.<br>
     * This method displays platform depandent "Print Dialog", and then sends
     * the OPD to the printer device.
     */
    public int print(Graphics g, PageFormat pf, int pi) throws PrinterException {

        try {
            PageSetupData psd = (PageSetupData) pf;
            this.removeSelection();
            Graphics2D g2 = (Graphics2D) g;
            java.awt.geom.AffineTransform at = g2.getTransform();

            // save/restore transform scope at

            Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
            DrawingArea dArea = this.opdFrame.getDrawingArea();

            Font myFont = new Font("my", Font.PLAIN, 12);
            FontMetrics currentMetrics = g2.getFontMetrics(myFont);
            int headerSize = currentMetrics.getAscent() + 10;

            int printPageWidth = (int) pf.getImageableWidth();
            int printPageHeight = (int) pf.getImageableHeight() - headerSize * 2;

            int screenPageWidth = dArea.getWidth();
            int screenPageHeight = dArea.getHeight();

            Color bgColor = dArea.getBackground();

            Color oldColor = (Color) this.myProject.getConfiguration().getProperty(
                    "BackgroundColor");
            this.applyNewBackground(Color.white);

            double zoomWidthFactor = (double) printPageWidth / (double) screenPageWidth;
            double zoomHeightFactor = (double) printPageHeight / (double) (screenPageHeight);

            dArea.setBackground(Color.white);
            this.myProject.getConfiguration().setProperty("BackgroundColor",
                    Color.white);

            Rectangle rect = this.calcDrawingCenter(dArea, g2);

            Rectangle targetRect = new Rectangle(0, 0, dArea.getWidth(), dArea.getHeight());
            g2.scale(zoomWidthFactor, zoomHeightFactor);

            double scalingFactor = 1.0;

            if (psd.isFitToPage()) {
                scalingFactor = this.fitToPage(targetRect, rect, g2);
            }

            if (psd.isCenterView()) {
                this.centerView(targetRect, rect, g2);
            }

            double xTr = pf.getImageableX();
            double yTr = pf.getImageableY() + headerSize;// + 15 +
            // currentMetrics.getAscent();
            g2.translate(xTr / (zoomWidthFactor * scalingFactor), yTr / (zoomHeightFactor * scalingFactor));

            dArea.print(g2);
            dArea.setBackground(bgColor);
            this.applyNewBackground(oldColor);

            g2.setTransform(at);

            this.printHeader(g2, psd, (int) xTr, (int) yTr, printPageWidth, myFont);
            this.printFooter(g2, psd, pi, (int) xTr, (int) yTr + printPageHeight,
                    printPageWidth, myFont);


        } catch (Exception ex) {
            OpcatLogger.error(ex, false);
        }

        return Printable.PAGE_EXISTS;


    }

    private void zoomContainer(Container cnt, double factor) {
        Component components[] = cnt.getComponents();

        for (int i = 0; i < components.length; i++) {
            if ((components[i] instanceof DraggedPoint) || (components[i] instanceof OpdOr)) {
                components[i].setLocation(
                        (int) (components[i].getX() * factor),
                        (int) (components[i].getY() * factor));
                components[i].setSize(
                        (int) (components[i].getWidth() * factor),
                        (int) (components[i].getHeight() * factor));
            }
        }

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof OpdBaseComponent) {
                components[i].setLocation(
                        (int) (components[i].getX() * factor),
                        (int) (components[i].getY() * factor));
                components[i].setSize(
                        (int) (components[i].getWidth() * factor),
                        (int) (components[i].getHeight() * factor));
                this.zoomContainer((OpdBaseComponent) components[i], factor);
            }

            if (components[i] instanceof AroundDragger) {
                components[i].setSize(
                        (int) (components[i].getWidth() * factor),
                        (int) (components[i].getHeight() * factor));
                ((AroundDragger) (components[i])).updateDragger();

                OpdCardinalityLabel label;
                double dx, dy;

                if (components[i] instanceof OpdRelationDragger) {
                    OpdRelationDragger tDragger = (OpdRelationDragger) components[i];
                    label = tDragger.getOpdCardinalityLabel();
                    dx = (label.getX() - tDragger.getX()) * factor;
                    dy = (label.getY() - tDragger.getY()) * factor;
                    label.setLocation(tDragger.getX() + (int) dx, tDragger.getY() + (int) dy);
                    label.recalculateSize();
                }

                if (components[i] instanceof TextDragger) {
                    TextDragger tDragger = (TextDragger) components[i];
                    label = tDragger.getOpdCardinalityLabel();
                    dx = (label.getX() - tDragger.getX()) * factor;
                    dy = (label.getY() - tDragger.getY()) * factor;
                    label.setLocation(tDragger.getX() + (int) dx, tDragger.getY() + (int) dy);
                    label.recalculateSize();
                }

            }
        }
    }

    /**
     * This method Zoomes the graphics in, simply enlarges it by
     * <code>param</code>
     *
     * @param factor zooming factor: size =
     *               currentSize*param;
     */
    public void viewZoomIn(double factor) {
        this.zoomContainer(this.opdFrame.getDrawingArea(), factor);

        double width = this.getDrawingArea().getPreferredSize().getWidth() * factor;
        double height = this.getDrawingArea().getPreferredSize().getHeight() * factor;
        this.getDrawingArea().setPreferredSize(
                new Dimension((int) width, (int) height));
        this.getDrawingArea().setSize(new Dimension((int) width, (int) height));
        this.mainDesktop.validate();
    }

    private void zoomContainer(Container cnt, double widthFactor,
                               double HeightFactor) {
        Component components[] = cnt.getComponents();

        for (int i = 0; i < components.length; i++) {
            if ((components[i] instanceof DraggedPoint) || (components[i] instanceof OpdOr)) {
                components[i].setLocation(
                        (int) (components[i].getX() * widthFactor),
                        (int) (components[i].getY() * HeightFactor));
                components[i].setSize(
                        (int) (components[i].getWidth() * widthFactor),
                        (int) (components[i].getHeight() * HeightFactor));
            }
        }

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof OpdBaseComponent) {
                components[i].setLocation(
                        (int) (components[i].getX() * widthFactor),
                        (int) (components[i].getY() * HeightFactor));
                components[i].setSize(
                        (int) (components[i].getWidth() * widthFactor),
                        (int) (components[i].getHeight() * HeightFactor));
                this.zoomContainer((OpdBaseComponent) components[i],
                        widthFactor, HeightFactor);
            }

            if (components[i] instanceof AroundDragger) {
                components[i].setSize(
                        (int) (components[i].getWidth() * widthFactor),
                        (int) (components[i].getHeight() * HeightFactor));
                ((AroundDragger) (components[i])).updateDragger();

                OpdCardinalityLabel label;
                double dx, dy;

                if (components[i] instanceof OpdRelationDragger) {
                    OpdRelationDragger tDragger = (OpdRelationDragger) components[i];
                    label = tDragger.getOpdCardinalityLabel();
                    dx = (label.getX() - tDragger.getX()) * widthFactor;
                    dy = (label.getY() - tDragger.getY()) * HeightFactor;
                    label.setLocation(tDragger.getX() + (int) dx, tDragger.getY() + (int) dy);
                    label.recalculateSize();
                }

                if (components[i] instanceof TextDragger) {
                    TextDragger tDragger = (TextDragger) components[i];
                    label = tDragger.getOpdCardinalityLabel();
                    dx = (label.getX() - tDragger.getX()) * widthFactor;
                    dy = (label.getY() - tDragger.getY()) * HeightFactor;
                    label.setLocation(tDragger.getX() + (int) dx, tDragger.getY() + (int) dy);
                    label.recalculateSize();
                }

            }
        }
    }

    public void viewZoomIn(double widthFactor, double HeightFactor) {
        this.zoomContainer(this.opdFrame.getDrawingArea(), widthFactor,
                HeightFactor);

        double width = this.getDrawingArea().getPreferredSize().getWidth() * widthFactor;
        double height = this.getDrawingArea().getPreferredSize().getHeight() * HeightFactor;
        this.getDrawingArea().setPreferredSize(
                new Dimension((int) width, (int) height));
        this.getDrawingArea().setSize(new Dimension((int) width, (int) height));
        this.mainDesktop.validate();

        this.WZoomInFactor = widthFactor;
        this.HZoomInFactor = HeightFactor;

    }

    /**
     * This method Zoomes the graphics out, simply makes it smaller by
     * <code>param</code>
     *
     * @param factor zooming factor: size =
     *               currentSize*param;
     */
    public void viewZoomOut(double factor) {
        this.viewZoomIn(1 / factor);
    }

    /**
     * Marks <code>sItem</code> component as selected and adds it to hte
     * selected items list.
     *
     * @param sItem component to select.
     */
    public void addSelection(Instance sItem, boolean removePrevious) {
        this.mySelection.addSelection(sItem, removePrevious);
    }

    public void addSelection(BaseGraphicComponent sItem, boolean removePrevious) {
        this.mySelection.addSelection(sItem, removePrevious);
    }

    public void addSelection(Hashtable instToSelect, boolean removePrevious) {
        Instance sItem;

        // if (removePrevious && (selectedItems.size() > 0) )
        // {
        // mySelection.removeSelection();
        // }

        for (Enumeration e = instToSelect.elements(); e.hasMoreElements(); ) {
            sItem = (Instance) e.nextElement();
            this.mySelection.addSelection(sItem, false);
        }
    }

    /**
     * Marks <code>sItem</code> component as not selected and removes it from
     * hte selected items list.
     *
     * @param inst component to deselect.
     */
    public void removeSelection(Instance inst) {
        this.mySelection.removeSelection(inst);
    }

    public void removeSelection() {
        this.mySelection.removeSelection();
    }

    /**
     * @return <code>Enumeration</code> of items selected in the
     *         <code>Opd</code>, actualy selected in <code>Opd</code>'s <a href
     *         = "../opdGraphics/DrawingArea.html"><code>DrawingArea</code><a>.
     */
    public Enumeration getSelectedItems() {
        return this.mySelection.getSelectedItems();
    }

    public Hashtable getSelectedItemsHash() {
        return this.mySelection.getSelectedItemsHash();
    }

    /**
     * If single item selected it's possible to get this item directly with this
     * method, in all other cases it will return <code>null</code>
     *
     * @return selected item, if single item selected, otherwise returns
     *         <code>null</code>.
     */
    public Instance getSelectedItem() {
        if (this.mySelection.getSelectedItemsHash().size() != 1) {
            return null;
        }
        return (Instance) this.mySelection.getSelectedItemsHash().elements().nextElement();
    }

    /**
     * Shows or hides the <code>JInternalFrame</code> that dispalys this
     * <code>Opd</code>
     *
     * @param visible <code>true</code> to make the component
     *                visible -- <code>false</code> invisible.
     */
    public void setVisible(boolean visible) {
        this.opdFrame.setVisible(visible);
    }

    /**
     * Checks wether the <code>JInternalFrame</code> that dispalys this
     * <code>Opd</code> is visible.
     *
     * @return <code>true</code> if OPD is visible.
     */
    public boolean isVisible() {
        return this.opdFrame.isVisible();
    }

    /**
     * @return number of entities in the <code>Opd</code>
     */
    public long getEntityInOpdMaxEntry() {
        return this.entityInOpdMaxEntry;
    }

    /**
     * Sets maximum entry number (for entitiInIpOpd allocation)(used in load
     * operation).
     */
    public void setEntityInOpdMaxEntry(long pEntityInOpdMaxEntry) {
        this.entityInOpdMaxEntry = pEntityInOpdMaxEntry;
    }

    // WARNING destructive -- Destructivly alters AffineTransform of
    // graphics
    // You should save the stransform before invocation
    // and restore after all centalize drawing is done
    // centalizes rect withing component
    // returns xTransleted, yTransleted in Point

    private Point centerView(Rectangle targetRect, Rectangle sourceRect,
                             Graphics2D g2) {
        double xCenter = (targetRect.getWidth() / 2 - (sourceRect.getWidth() / 2 + sourceRect.getX()));
        double yCenter = (targetRect.getHeight() / 2 - (sourceRect.getHeight() / 2 + sourceRect.getY()));
        g2.translate(xCenter, yCenter);
        return new Point((int) xCenter, (int) yCenter);
    }

    /**
     * returns enclosing rectangle od whole drawing
     */
    private Rectangle calcDrawingCenter(JComponent parent, Graphics2D g2) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int numComps = parent.getComponentCount();
        Component[] comps = parent.getComponents();
        Component c = null;
        for (int i = 0; i < numComps; i++) {
            c = comps[i];
            if (!(c instanceof LinkingLine)) {
                if (c.getX() < minX) {
                    minX = c.getX();
                }
                if (c.getY() < minY) {
                    minY = c.getY();
                }
                if (c.getX() + c.getWidth() > maxX) {
                    maxX = c.getX() + c.getWidth();
                }
                if (c.getY() + c.getHeight() > maxY) {
                    maxY = c.getY() + c.getHeight();
                }
            }
        }
        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    private double fitToPage(Rectangle targetRect, Rectangle sourceRect,
                             Graphics2D g2) {
        double widthFactor = (targetRect.getWidth() / sourceRect.getWidth());
        double heightFactor = (targetRect.getHeight() / sourceRect.getHeight());

        double scalingFactor = Math.min(widthFactor, heightFactor);

        double xTrans = (sourceRect.getX());
        double yTrans = (sourceRect.getY());

        g2.scale(scalingFactor, scalingFactor);

        if (widthFactor < heightFactor) {
            double factor = (heightFactor - widthFactor) / (2 * scalingFactor);
            yTrans = yTrans - sourceRect.getHeight() * factor;
        } else {
            double factor = (widthFactor - heightFactor) / (2 * scalingFactor);
            xTrans = xTrans - sourceRect.getWidth() * factor;
        }

        g2.translate(-xTrans, -yTrans);

        return scalingFactor;

    }

    private void printFooter(Graphics2D g2, PageSetupData psd, int pi,
                             int xBorder, int yBorder, int width, Font fnt) {
        FontMetrics currentMetrics = g2.getFontMetrics(fnt);
        int fontSize = currentMetrics.getAscent();
        String str;
        boolean drawLine = false;

        g2.setFont(fnt);

        // construct and draw Author string (down left corner)
        if (psd.isPrjCreator()) {
            drawLine = true;
            str = "Author: " + this.myProject.getCreator();
            g2.drawString(str, xBorder, yBorder + fontSize + 9);
        }

        // construct and draw page number string
        if (psd.isPageNumber()) {
            drawLine = true;
            String pIndex = "Page " + (pi + 1);
            g2.drawString(pIndex, xBorder + width - currentMetrics.stringWidth(pIndex), yBorder + fontSize + 9);
        }

        // draw line
        if (drawLine) {
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawLine(xBorder, yBorder + 7, xBorder + width, yBorder + 7);
        }
    }

    private void printHeader(Graphics2D g2, PageSetupData psd, int xBorder,
                             int yBorder, int width, Font fnt) {
        FontMetrics currentMetrics = g2.getFontMetrics(fnt);
        String str = "";
        boolean drawLine = false;

        g2.setFont(fnt);

        // construct and draw projectName and opdName string
        if (psd.isPrjName() || psd.isOpdName()) {
            drawLine = true;
            if (psd.isPrjName()) {
                str += "System: " + this.myProject.getName();
            }

            if (psd.isOpdName()) {
                if (psd.isPrjName()) {
                    str += ", ";
                }
                str += "OPD: " + this.getName();
            }
            g2.drawString(str, xBorder, yBorder - 9);
        }
        // g2.setStroke(new BasicStroke(2.0f));

        // construct and draw date+time string
        if (psd.isPrintTime()) {
            drawLine = true;

            StringBuffer dateStr = new StringBuffer("Printed on ");
            SimpleDateFormat df = new SimpleDateFormat("EEE dd/MM/yy HH:mm");
            df.format((new GregorianCalendar()).getTime(), dateStr,
                    new FieldPosition(0));
            str = new String(dateStr);
            g2.drawString(str, xBorder + width - currentMetrics.stringWidth(str) - 1, yBorder - 9);
        }

        // draw line
        if (drawLine) {
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawLine(xBorder, yBorder - 7, xBorder + width, yBorder - 7);
        }
    }

    public ThingEntry getMainEntry() {
        return this.mainEntry;
    }

    public IThingEntry getMainIEntry() {
        return this.mainEntry;
    }

    public IXThingEntry getMainIXEntry() {
        return this.mainEntry;
    }

    public void setMainEntry(ThingEntry mainEntry) {
        this.mainEntry = mainEntry;
    }

    public IThingInstance getMainIInstance() {
        return this.mainInstance;
    }

    public IXThingInstance getMainIXInstance() {
        return this.mainInstance;
    }

    public ThingInstance getMainInstance() {
        return this.mainInstance;
    }

    public void setMainInstance(ThingInstance mainInstance) {
        this.mainInstance = mainInstance;
    }

    /**
     * Creates an image out of the OPD.
     */
    public BufferedImage getImageRepresentation() {

        BufferedImage myImage = null;
        long cur = Opcat2.getCurrentProject().getCurrentOpd().getOpdId();

        try {
            myImage = new BufferedImage(getRealComponentsMaxX(), getRealComponentsMaxY(), BufferedImage.TYPE_INT_RGB);
            Opcat2.getCurrentProject().showOPD(this.getOpdId(), false);
            getDrawingArea().paintAll(myImage.getGraphics());
        } catch (Exception e) {
            OpcatLogger.error(e);
        }

        Opcat2.getCurrentProject().showOPD(cur, false);
        return myImage;
    }

    public BufferedImage getImageRepresentation(DrawingArea dArea, int width,
                                                int height, Color backGround) {

//        Opd currentOPD = this.myProject.getCurrentOpd();
//        boolean isShown = this.opdFrame.isVisible();
//        boolean isIconified = isShown && this.opdFrame.isIcon();
//
//        try {
//            if (!isShown) {
//                this.setVisible(true);
//            }
//
//            if (isIconified) {
//                this.opdFrame.setMaximum(true);
//            }
//        } catch (java.beans.PropertyVetoException pve) {
//            OpcatLogger.error(pve);
//        }
//
//        // DrawingArea dArea = this.getDrawingArea();
//
//        // dArea.repaint();
//        this.removeSelection();
//
//        BufferedImage myImage = new BufferedImage(width, height,
//                BufferedImage.TYPE_INT_RGB);
//        Graphics2D myGraphics = myImage.createGraphics();
//        // ImageIcon ic = new ImageIcon(myImage);
//        myGraphics.setColor(Color.white);
//        myGraphics.fillRect(0, 0, width, height);
//
//        Color oldColor = (Color) this.myProject.getConfiguration().getProperty(
//                "BackgroundColor");
//        this.applyNewBackground(Color.white);
//        dArea.setBackground(Color.white);
//
//        Rectangle rect = this.calcDrawingCenter(dArea, myGraphics);
//        Rectangle targetRect = new Rectangle(0, 0, width, height);
//
//        this.centerView(targetRect, rect, myGraphics);
//
//        dArea.paintAll(myGraphics);
//
//        dArea.setBackground(backGround);
//        this.applyNewBackground(oldColor);
//        try {
//            if (!isShown) {
//                this.opdFrame.setVisible(false);
//            }
//
//            if (isIconified && isShown) {
//                this.opdFrame.setIcon(true);
//            }
//
//            if (currentOPD != null) {
//                currentOPD.getOpdContainer().setSelected(true);
//            }
//        } catch (java.beans.PropertyVetoException pve) {
//            OpcatLogger.error(pve);
//        }

        return null;
    }

    private int getRealComponentsMaxX() {
        int x = 0;
        for (Component comp : getDrawingArea().getComponents()) {
            if ((comp instanceof OpdBaseComponent) || (comp instanceof OpdLine)) {
                if (comp.getX() + comp.getWidth() > x) {
                    x = comp.getX() + comp.getWidth();
                }
            }
        }

        return x;
    }

    private int getRealComponentsMaxY() {
        int y = 0;
        for (Component comp : getDrawingArea().getComponents()) {
            if ((comp instanceof OpdBaseComponent) || (comp instanceof OpdLine)) {
                if (comp.getY() + comp.getHeight() > y) {
                    y = comp.getY() + comp.getHeight();
                }
            }
        }

        return y;
    }

    public OpdProject getMyProject() {
        return myProject;
    }

    //    private int getRealComponentsMinX() {
//        int x = 99999999;
//        for (Component comp : getDrawingArea().getComponents()) {
//            if (comp.getX() < x) {
//                x = comp.getX();
//            }
//        }
//
//        return x;
//    }
//
//    private int getRealComponentsMinY() {
//        int y = 9999999;
//        for (Component comp : getDrawingArea().getComponents()) {
//            if (comp.getY() < y) {
//                y = comp.getY();
//            }
//        }
//        return y;
//    }


    public Selection getSelection() {
        return this.mySelection;
    }

    public void selectAll() {
        this.getSelection().selectAll();
    }

    /**
     * This function returns True if the two instances are conected with the
     * link in the OPD. it retuens false if not connected.
     *
     * @param source - The link Source instance
     * @param dest   - the link destination Instance
     * @param link   - The link to check for.
     */
    public boolean isConnectedByLink(Instance source, Instance dest, LinkInstance link) {
        boolean retVal = false;
        Enumeration linkEnum = link.getEntry().getInstances(false);

        while (linkEnum.hasMoreElements()) {
            LinkInstance locLink = (LinkInstance) linkEnum.nextElement();
            if (locLink.getKey().getOpdId() == this.getOpdId()) {
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public boolean isConnectedByRelation(Instance source, Instance dest, int type) {
        boolean retVal = false;

        ArrayList<OpmStructuralRelation> relations = new ArrayList<OpmStructuralRelation>();

        ArrayList<OpmStructuralRelation> temp = ((ConnectionEdgeEntry) source.getEntry()).getRelationSourcesCounter().get(new RelationKey(type, source.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) source.getEntry()).getRelationDestinationsCounter().get(new RelationKey(type, source.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) source.getEntry()).getRelationSourcesCounter().get(new RelationKey(type, dest.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) source.getEntry()).getRelationDestinationsCounter().get(new RelationKey(type, dest.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) dest.getEntry()).getRelationSourcesCounter().get(new RelationKey(type, dest.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) dest.getEntry()).getRelationDestinationsCounter().get(new RelationKey(type, dest.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) dest.getEntry()).getRelationSourcesCounter().get(new RelationKey(type, source.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        temp = ((ConnectionEdgeEntry) dest.getEntry()).getRelationDestinationsCounter().get(new RelationKey(type, source.getIEntry().getId()));
        if (temp != null)
            relations.addAll(temp);

        if (relations != null)
            for (OpmStructuralRelation rel : relations) {
                Enumeration<Instance> instances = myProject.getSystemStructure().getEntry(rel.getId()).getInstances(false);
                while (instances.hasMoreElements()) {
                    if (instances.nextElement().getOpd().getOpdId() == source.getOpd().getOpdId()) return true;
                }
            }

        return retVal;
    }

    /**
     * isContained returns true if the instance is inside the OPD
     *
     * @param instance
     * @return
     */
    public boolean isContaining(Instance instance) {
        return (instance.getKey().getOpdId() == this.getOpdId());
    }

    public boolean isContainingInstanceOfEntry(Entry entry) {
        boolean found = false;
        Enumeration<Instance> instances = entry.getInstances(false);
        while (instances.hasMoreElements() && !found) {
            Instance instance = (Instance) instances.nextElement();
            found = (instance.getKey().getOpdId() == this.getOpdId());
        }
        return found;
    }

    public void refit(Container cont) {
        Enumeration thingsEnum = null;
        if (cont instanceof OpdThing) {
            ThingEntry entry = (ThingEntry) ((OpdThing) cont).getEntry();
            thingsEnum = Collections.enumeration(entry.GetInZoomedIncludedInstances(this));
        } else {
            thingsEnum = this.myProject.getSystemStructure().getInstancesInOpd(
                    this.getOpdId());
        }
        _refit(thingsEnum, cont);
    }

    public void refit() {
        Enumeration thingsEnum;
        ThingInstance mainInstance = this.getMainInstance();
        ThingEntry mainThing = this.getMainEntry();

        if ((mainThing != null) && (mainThing instanceof ProcessEntry)) {
            Vector thingsVec = mainThing.GetInZoomedIncludedInstances(this);
            thingsEnum = thingsVec.elements();
            Container dArea = mainInstance.getConnectionEdge();
            this._refit(thingsEnum, dArea);

            ArrayList<ThingInstance> tmp = new ArrayList<ThingInstance>();
            tmp.add(mainInstance);
            thingsEnum = Collections.enumeration(tmp);
            this._refit(thingsEnum, this.getDrawingArea());
            // return ;
        }

        // now refit the main frame.
        // this.removeSelection() ;
        // this.selectAll() ;
        thingsEnum = null;
        thingsEnum = this.myProject.getSystemStructure().getInstancesInOpd(
                this.getOpdId());
        this._refit(thingsEnum, this.getDrawingArea());
        // this.removeSelection() ;
    }

    private void _refit(Enumeration thingsEnum, Container container) {
        boolean change = false;
        int maxX = 0;
        int maxY = 0;
        while (thingsEnum.hasMoreElements()) {
            Object obj = thingsEnum.nextElement();
            if (obj instanceof ThingInstance) {
                ThingInstance ins = (ThingInstance) obj;
                //Dimension dim = ins.getConnectionEdge().getActualSize();
                int actualWidth = ins.getConnectionEdge().getActualWidth();
                int actualHeight = ins.getConnectionEdge().getActualHeight();


                int locX = ins.getWidth();
                int locY = ins.getHeight();

                if (actualWidth > locX) {
                    locX = (int) actualWidth;
                }

                if (actualHeight > locY) {
                    locY = (int) actualHeight;
                }

                if (ins.getX() + locX > maxX) {
                    maxX = ins.getX() + locX;
                }
                if (ins.getY() + locY > maxY) {
                    maxY = ins.getY() + locY;
                }
            }

            if (obj instanceof FundamentalRelationInstance) {
                FundamentalRelationInstance ins = (FundamentalRelationInstance) obj;
                if (ins.getDestinationDragger().getX() + ins.getDestinationDragger().getWidth() > maxX) {
                    maxX = ins.getDestinationDragger().getX() + ins.getDestinationDragger().getWidth();
                }
                if (ins.getDestinationDragger().getY() + ins.getDestinationDragger().getHeight() > maxY) {
                    maxY = ins.getDestinationDragger().getY() + ins.getDestinationDragger().getHeight();
                }
            }

        }
        if (maxX < container.getWidth()) {
            maxX = container.getWidth();
        }

        if (maxY < container.getHeight()) {
            maxY = container.getHeight();
        }
        Dimension newdim = new Dimension(maxX, maxY);
        change = (container.getWidth() < newdim.getWidth()) || (container.getHeight() < newdim.getHeight());
        if (change) {
            if (container instanceof DrawingArea) {
                ((DrawingArea) container).revalidate();
                container.setPreferredSize(newdim);
            }
            if (container instanceof OpdConnectionEdge) {
                ((OpdConnectionEdge) container).setSize((int) (maxX),
                        (int) (maxY));
            }
            container.repaint();
        }
    }

    /**
     * *********************** OWL-S Start ************************************
     */
    public Instance selectOne(String name) {
        return this.getSelection().selectOne(name);
    }

    /**
     * *********************** OWL-S End ************************************
     */
    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public boolean isView() {
        return view;
    }

    public int getIndexInLevel() {
        DefaultMutableTreeNode me = myProject.getSystemStructure().find(this);
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) me.getParent();
        int idx = 0;
        if (parent != null) {
            idx = parent.getIndex(me);
        }
        return idx;
    }

    public void setView(boolean view) {
        this.view = view;
    }

    public boolean isViewZoomIn() {
        return viewZoomIn;
    }

    public void setViewZoomIn(boolean viewZoomIn) {
        if (viewZoomIn && !this.viewZoomIn) {
            normalSize = new Dimension(this.getDrawingArea().getWidth(), this.getDrawingArea().getHeight());

        }

        this.viewZoomIn = viewZoomIn;

        if (!this.viewZoomIn && (normalSize != null)) {
            viewZoomIn(normalSize.getWidth() / getDrawingArea().getWidth(),
                    normalSize.getHeight() / getDrawingArea().getHeight());

            this.WZoomInFactor = 1;
            this.HZoomInFactor = 1;
        }
    }

    public OpcatProperties getProperties() {
        return properties;
    }

    public void setProperties(OpcatProperties properties) {
        this.properties = properties;
    }

    @Override
    public int compareTo(Object o) {

        if (o instanceof Opd) {
            Opd opd = (Opd) o;
            if (this.getOpdId() == opd.getOpdId()) return 0;
            DefaultMutableTreeNode myNode = myProject.getSystemStructure().find(this);
            DefaultMutableTreeNode hisNode = myProject.getSystemStructure().find(opd);

            if (myNode.getDepth() > hisNode.getDepth()) {
                return 1;
            }

            if (myNode.getDepth() < hisNode.getDepth()) {
                return -1;
            }

        }
        return -1;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
