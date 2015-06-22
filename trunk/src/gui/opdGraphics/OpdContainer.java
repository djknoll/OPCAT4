package gui.opdGraphics;

import gui.Opcat2;
import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

import javax.swing.*;
import java.awt.*;

/**
 * <p/>
 * This class represents single window in MDI application -
 * <code>JInternalFrame</code>. It consist of <a href = "DrawingArea.html><code>DrawingArea</code></a>,
 * referense to <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a>
 * and reference to <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a>
 * it represents graphicaly.
 */
public class OpdContainer extends JInternalFrame// implements MouseListener
{


    /**
     *
     */


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * It's a reference to <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a>
     * this container belongs to
     */
    protected OpdProject myProject;

    /**
     * It's Actually the window area where the components are drawn
     */
    protected DrawingArea dArea;

    /**
     * It's a reference to <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a>
     * it represents graphicaly.
     */
    protected Opd parentOpd;

    /**
     * <p/>
     * Constructs resizable, closeable, iconifiable, maximisable
     * <code>JInternalFrame</code> with title given as <code>pTitle</code>
     * parametr, with new <a href = "DrawingArea.html><code>DrawingArea</code></a>
     * as client area of the window.
     *
     * @param pTitle   NAME</code>, title of newly created frame.
     * @param pProject OpdProject</code>, the project this graphic OPD
     *                 representation belongs to.
     * @param pOpd     Opd</code> that this <code>OpdContainer</code>
     *                 represents graphically
     */

    public OpdContainer(String pTitle, OpdProject pProject, Opd pOpd) {
        super(pTitle, true, true, true, true);
        this.myProject = pProject;
        this.parentOpd = pOpd;
        this.dArea = new DrawingArea(this.myProject, pOpd);
        this.dArea.addMouseListener(this.dArea);
        this.dArea.addMouseMotionListener(this.dArea);

        JScrollPane scroller = new JScrollPane(this.dArea);

        // Layout this
        this.getContentPane().setLayout(new BorderLayout());
        //this.getContentPane().setLayout(new AnimatingCardLayout(new DashboardAnimation())) ;
        //((AnimatingCardLayout)this.getContentPane().getLayout()).setAnimationDuration(2000);

        GenericTable config = this.myProject.getConfiguration();
        double normalSize = ((Integer) config.getProperty("NormalSize"))
                .doubleValue();
        double currentSize = ((Integer) config.getProperty("CurrentSize"))
                .doubleValue();
        double factor = currentSize / normalSize;
        double width = ((Integer) config.getProperty("OPDWidth")).intValue()
                * factor;
        double height = ((Integer) config.getProperty("OPDHeight")).intValue()
                * factor;

        int realWidth = 1;
        int realHeight = 1;
        if (Opcat2.getFrame() != null) {
            realWidth = (int) Opcat2.getFrame().getWidth();
            realHeight = (int) Opcat2.getFrame().getHeight();
        }

        if (realWidth > width) {
            if (realHeight > height) {
                this.dArea.setPreferredSize(new Dimension((int) realWidth, (int) realHeight));
            } else {
                this.dArea.setPreferredSize(new Dimension((int) realWidth, (int) height));
            }
        } else {
            if (realHeight > height) {
                this.dArea.setPreferredSize(new Dimension((int) width, (int) realHeight));
            } else {
                this.dArea.setPreferredSize(new Dimension((int) width, (int) height));
            }
        }
        this.getContentPane().add(scroller, BorderLayout.CENTER);
        //this.getContentPane().add(scroller,"base");
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);

        //Configuration.getInstance().getColorFromProperty("OPDBackgroundColor")

        //this.getContentPane().setBackground(Configuration.getInstance().getColorFromProperty("graphics.default.OPDBackgroundColor"));
        //this.getDrawingArea().setBackground(Configuration.getInstance().getColorFromProperty("graphics.default.OPDBackgroundColor"));
    }

    /**
     * @return a reference to <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a>
     *         this container belongs to.
     */
    public Opd getOpd() {
        return this.parentOpd;
    }

    /**
     * @return a reference to <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a>
     *         it represents graphicaly.
     */
    public DrawingArea getDrawingArea() {
        return this.dArea;
    }

}
