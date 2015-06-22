package gui.projectStructure;
import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DraggedPoint;
import gui.opdGraphics.MoveUpdatable;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.TransparentAroundDragger;
import gui.opdGraphics.lines.OpdSimpleLine;
import gui.opdGraphics.opdBaseComponents.OpdAggregation;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdExhibition;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdInstantination;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdGraphics.opdBaseComponents.OpdSpecialization;
import gui.opdProject.OpdProject;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JLayeredPane;

/**
 * <p>This class describes some "triangle" (graphical component
 * representing several fundamental relations).
 * We need this class because to one "triangle" user connects fundamental relation
 * that don't related logically. So this class holds data structure that contains
 * instances of all fundamental relations connected via this "triangle".
 * This class also holds the informatiom about the "tringle" itself. It compound
 * from following components - <br>
 * relation - OpdFundamentalRelation that represents graphically "triangle" itself.<br>
 * source - OpdConnectionEdge which is source for all fundamental relation connected
 * via this "triangle".<br>
 * sourceDragger - AroundDragger which is connected to the source.<br>
 * line - OpdLine which connects sourceDragger to the "triangle".
 *
 * @version	2.0
 * @author		Stanislav Obydionnov
 * @author		Yevgeny   Yaroker
 *
 */


public class GraphicalRelationRepresentation implements MoveUpdatable
{
    private OpdProject myProject;
    private AroundDragger sourceDragger;
    private OpdConnectionEdge source;
    private OpdSimpleLine lines[];
    private DraggedPoint points[];


    private OpdFundamentalRelation relation;
    private Hashtable graphicalInstances;
    private Container myContainer;
    private int myType;
    private final static int BYPASS_DISTANCE = 15;

    private int selectionsCount = 0;


    /**
     * Creates GraphicalRelationRepresentationEntry that holds all information about specified
     * pRelation ("triangle").
     *
     * @param pSource OpdConnectionEdge which is source for all fundamental relation connected
     * via this "triangle".
     * @param pSourceDragger AroundDragger which is connected to the source.
     * @param pLine OpdLine which connects sourceDragger to the "triangle".
     * @param pRelation OpdFundamentalRelation that represents graphically "triangle" itself.
     */

    public GraphicalRelationRepresentation(OpdConnectionEdge pSource, RelativeConnectionPoint pPoint1, int relType, OpdProject project, Point initLocation, Container cn)

    {
        graphicalInstances = new Hashtable();
        lines = new OpdSimpleLine[5];
        points = new DraggedPoint[4];
        myProject = project;
        source = pSource;
        myType = relType;

        relation = addRelation(relType, cn);
        cn.add(relation,  JLayeredPane.MODAL_LAYER);

        relation.setLocation(initLocation);

        sourceDragger = addDragger(source , pPoint1, myContainer);
        cn.add(sourceDragger,  JLayeredPane.MODAL_LAYER);
        createPoints(cn);
        createLines(cn);

        sourceDragger.setLine(lines[0]);
        source.addDragger(sourceDragger);

        addLines2Points();
        relation.addLine(lines[4]);
        source.repaintLines();

        cn.repaint();

        relation.setGraphicalEntry(this);
        source.addUpdateListener(this);
        sourceDragger.addUpdateListener(this);
        relation.addUpdateListener(this);


    }


    private void addLines2Points()
    {
        points[0].addLine(lines[0]);
        points[0].addLine(lines[1]);
        points[1].addLine(lines[1]);
        points[1].addLine(lines[2]);
        points[2].addLine(lines[2]);
        points[2].addLine(lines[3]);
        points[3].addLine(lines[3]);
        points[3].addLine(lines[4]);
    }


    private void createLines(Container cn)
    {
        RelativeConnectionPoint centerPoint = new RelativeConnectionPoint(OpdBaseComponent.CENTER, 0);

        lines[0] = createLine(sourceDragger, centerPoint,  points[0] , centerPoint);
        lines[1] = createLine(points[0], centerPoint,  points[1] , centerPoint);
        lines[2] = createLine(points[1], centerPoint,  points[2] , centerPoint);
        lines[3] = createLine(points[2], centerPoint,  points[3] , centerPoint);
        lines[4] = createLine(points[3], centerPoint,  relation , new RelativeConnectionPoint(OpcatConstants.N_BORDER, 0.5));

        cn.add(lines[0], JLayeredPane.PALETTE_LAYER);
        cn.add(lines[1], JLayeredPane.PALETTE_LAYER);
        cn.add(lines[2], JLayeredPane.PALETTE_LAYER);
        cn.add(lines[3], JLayeredPane.PALETTE_LAYER);
        cn.add(lines[4], JLayeredPane.PALETTE_LAYER);
    }


    private void createPoints(Container cn)
    {
        points[0] = new DraggedPoint(myProject);
        points[1] = new DraggedPoint(myProject);
        points[2] = new DraggedPoint(myProject);
        points[3] = new DraggedPoint(myProject);

        for (int i=0; i<points.length; i++)
        {
            points[i].setMoveable(false);
        }

        cn.add(points[0], JLayeredPane.PALETTE_LAYER);
        cn.add(points[1], JLayeredPane.PALETTE_LAYER);
        cn.add(points[2], JLayeredPane.PALETTE_LAYER);
        cn.add(points[3], JLayeredPane.PALETTE_LAYER);

        int x1 = sourceDragger.getX() + sourceDragger.getWidth()/2 - points[0].getWidth()/2 - 5;
        int y1 = sourceDragger.getY() + sourceDragger.getHeight()/2;
        int x2 = relation.getX() + relation.getWidth()/2 - points[0].getWidth()/2;
        int y2 = relation.getY();


        if (source.getY()+source.getWidth() < relation.getY())
        {

            int diff = (relation.getY() - source.getY() - source.getWidth())/2;

            points[0].setAbsolutesLocation(x1, y1 + diff);
            points[1].setAbsolutesLocation(x1, y1 + diff);
            points[2].setAbsolutesLocation(x1, y1 + diff);
            points[3].setAbsolutesLocation(x2, y1 + diff);

        }
        else
        {

            points[0].setAbsolutesLocation(x1, y1 + 20);
            points[1].setAbsolutesLocation(x2 + 50, y1 + 20);


            points[2].setAbsolutesLocation(x2 + 50, y2 - 20);
            points[3].setAbsolutesLocation(x2, y2 - 20);
        }


//          points[0].setYDirectionAllowed(true);
//          points[1].setYDirectionAllowed(true);
//          points[1].setXDirectionAllowed(true);
//          points[2].setYDirectionAllowed(true);
//          points[2].setXDirectionAllowed(true);
//          points[3].setYDirectionAllowed(true);

//          points[0].setVerticalNeighbor(points[1]);
//          points[1].setVerticalNeighbor(points[0]);
//          points[1].setHorizontalNeighbor(points[2]);
//          points[2].setHorizontalNeighbor(points[1]);
//          points[2].setVerticalNeighbor(points[3]);
//          points[3].setVerticalNeighbor(points[2]);

//          points[0].addMouseMotionListener(points[0]);
//       	  points[1].addMouseMotionListener(points[1]);
//       	  points[2].addMouseMotionListener(points[2]);
//       	  points[3].addMouseMotionListener(points[3]);

    }


    private OpdSimpleLine createLine(Connectable edge1, RelativeConnectionPoint pPoint1,
                                     Connectable edge2, RelativeConnectionPoint pPoint2)
    {

        RelativeConnectionPoint centerPoint = new RelativeConnectionPoint(OpdBaseComponent.CENTER, 0);

        return new OpdSimpleLine(edge1, pPoint1, edge2, pPoint2, null, new OpdKey(0,0), myProject);
    }


    private OpdFundamentalRelation addRelation(int relType, Container container)
    {
        OpdFundamentalRelation gRelation = null;
        long temp;

        switch (relType)
        {
            case OpcatConstants.SPECIALIZATION_RELATION :
            {
                gRelation = new OpdSpecialization(null, myProject, 0, 0);
                break;
            }

        case OpcatConstants.INSTANTINATION_RELATION :
        {
            gRelation = new OpdInstantination(null, myProject, 0, 0);
            break;
        }

    case OpcatConstants.EXHIBITION_RELATION :
    {
        gRelation = new OpdExhibition(null, myProject, 0, 0);
        break;
    }

case OpcatConstants.AGGREGATION_RELATION :
{
    gRelation = new OpdAggregation(null, myProject, 0, 0);
    break;
}
        }

        gRelation.addMouseListener(gRelation);
        gRelation.addMouseMotionListener(gRelation);

        container.add(gRelation);
        return gRelation;
    }

    public TransparentAroundDragger addDragger(Connectable edge, RelativeConnectionPoint pPoint, Container container)
    {
        TransparentAroundDragger gDragger;

        gDragger = new TransparentAroundDragger(edge, pPoint, myProject);
        gDragger.addMouseListener(gDragger);
        gDragger.addMouseMotionListener(gDragger);

        return gDragger;
    }


    /**
     * Returns OpdConnectionEdge which is source for all fundamental relation connected
     * via this "triangle".
     */
    public OpdConnectionEdge getSource()
    {
        return source;
    }

    public Instance getSourceInstance()
    {
        return source.getEntry().getInstance(source.getOpdKey());
    }

    /**
     * Returns OpdLine which connects sourceDragger to the "triangle".
     */
    public OpdSimpleLine getLine(int lineNumber)
    {
        if (lineNumber < lines.length)
        {
            return lines[lineNumber];
        }

        return null;
    }

    public DraggedPoint getPoint(int pointNumber)
    {
        if (pointNumber < points.length)
        {
            return points[pointNumber];
        }

        return null;
    }


    /**
     * Returns AroundDragger which is connected to the source.
     */
    public AroundDragger getSourceDragger()
    {
        return sourceDragger;
    }

    /**
     * Returns OpdFundamentalRelation OpdFundamentalRelation that represents
     * graphically "triangle" itself.
     */
    public OpdFundamentalRelation getRelation()
    {
        return relation;
    }

    public int getType()
    {
        return myType;
    }

    /**
     * Tests if the specified pKey is a key in data structure containing graphical instances
     * of this GraphicalRelationRepresentationEntry.
     */

    public boolean containsKey(OpdKey pKey)
    {
        return graphicalInstances.containsKey(pKey);
    }


    /**
     * Adds the specified pInstance with the specified key to the data
     * structure containing graphical instances
     * of this GraphicalRelationRepresentationEntry.
     *
     * @param key OpdKey - key of some FundamentalRelationInstance.
     * @param pInstance  FundamentalRelationInstance.
     * @return true if specified key is a new key in data structure containing
     * graphical instances
     * of this GraphicalRelationRepresentationEntry. Returns false and does
     * nothing if specified key already exist in the data structure.
     */

    public boolean addInstance(OpdKey key, FundamentalRelationInstance pInstance)
    {

        if (containsKey(key))
        {
            return false;
        }

        graphicalInstances.put(key, pInstance);
        return true;

    }

    public FundamentalRelationInstance getInstance(OpdKey pKey)
    {
        return (FundamentalRelationInstance)graphicalInstances.get(pKey);
    }


    /**
     * Removes from the data structure containing graphical instances of this
     * GraphicalRelationRepresentationEntry Instance with the specified pKey
     *
     * @param key OpdKey - key of some FundamentalRelationInstance.
     * @return true operation was successful . False if specified key doesn't exist
     * in the data structure.
     */
    public boolean removeInstance(OpdKey pKey)
    {

        if (!containsKey(pKey))
        {
            return false;
        }

        graphicalInstances.remove(pKey);
        return true;
    }

    /**
     * Returns an enumeration of the FundamentalRelationInstance in
     * this GraphicalRelationRepresentationEntry.
     * Use the Enumeration methods on the returned object to fetch the
     * Instances sequentially
     *
     * @return an enumeration of the FundamentalRelationInstance in this MainStructure
     */
    public Enumeration getInstances()
    {
        return graphicalInstances.elements();
    }

    public boolean isEmpty()
    {
        return graphicalInstances.isEmpty();
    }

    /**
     * Returns the number of graphical instances in this GraphicalRelationRepresentationEntry.
     *
     */

    public int getInstancesNumber()
    {
        return graphicalInstances.size();
    }

    public void setTextColor(Color textColor)
    {
        relation.setTextColor(textColor);

        for (int i = 0; i<lines.length; i++)
        {
            lines[i].setTextColor(textColor);
        }

        for (Enumeration e = getInstances() ; e.hasMoreElements();)
        {
            FundamentalRelationInstance tempInstance = (FundamentalRelationInstance)(e.nextElement());
            tempInstance.setTextColor(textColor);
        }

    }


    public Color getTextColor()
    {
        return relation.getTextColor();
    }

    public void setBorderColor(Color borderColor)
    {
        relation.setBorderColor(borderColor);

        for (int i = 0; i<lines.length; i++)
        {
            lines[i].setLineColor(borderColor);
            lines[i].repaint();
        }

        for (int i = 0; i<points.length; i++)
        {
            points[i].setBorderColor(borderColor);
            points[i].repaint();
        }


        for (Enumeration e = getInstances() ; e.hasMoreElements();)
        {
            FundamentalRelationInstance tempInstance = (FundamentalRelationInstance)(e.nextElement());
            tempInstance.setBorderColor(borderColor);
        }


    }

    public Color getBorderColor()
    {
        return relation.getBorderColor();
    }

    public void setBackgroundColor(Color backgroundColor)
    {
        relation.setBackgroundColor(backgroundColor);
        relation.repaint();

        for (Enumeration e = getInstances() ; e.hasMoreElements();)
        {
            FundamentalRelationInstance tempInstance = (FundamentalRelationInstance)(e.nextElement());
            tempInstance.setBackgroundColor(backgroundColor);
        }

    }

    public void removeFromContainer()
    {
        Container cn =  sourceDragger.getParent();

        cn.remove(sourceDragger);
        source.removeDragger(sourceDragger);
        cn.remove(relation);

        for (int i = 0; i < points.length; i++)
        {
            cn.remove(points[i]);
        }

        for (int i = 0; i < lines.length; i++)
        {
            cn.remove(lines[i]);
        }

        cn.repaint();


    }

    public void add2Container(Container cn)
    {
        for (int i = 0; i < points.length; i++)
        {
            cn.add(points[i], JLayeredPane.MODAL_LAYER);
        }

        for (int i = 0; i < lines.length; i++)
        {
            cn.add(lines[i], JLayeredPane.MODAL_LAYER);
        }

        cn.add(sourceDragger, JLayeredPane.MODAL_LAYER);
        source.addDragger(sourceDragger);
        cn.add(relation, JLayeredPane.MODAL_LAYER);

        cn.repaint();


    }

    public Color getBackgroundColor()
    {
        return relation.getBackgroundColor();
    }

    public void updateInstances()
    {
        for (Enumeration e = getInstances() ; e.hasMoreElements();)
        {
            FundamentalRelationInstance tempInstance = (FundamentalRelationInstance)(e.nextElement());
            FundamentalRelationEntry tempEntry = (FundamentalRelationEntry)tempInstance.getEntry();
            tempEntry.updateInstances();
        }
    }

    public void updateMove(Object origin)
    {
        this.arrangeLines();
        for (int i = 0; i < points.length; i++)
        {
            points[i].repaintLines();
        }

    }

    public void updateRelease(Object origin)
            {}


    public void arrangeLines()
    {

        int sX = sourceDragger.getX() + sourceDragger.getWidth()/2 - points[0].getWidth()/2;
        int sY = sourceDragger.getY() + sourceDragger.getHeight()/2-points[0].getHeight()/2;
        int dX = relation.getX()+relation.getWidth()/2-points[3].getWidth()/2;
        int dY = relation.getY()-points[3].getHeight()/2;

        int draggerSide=0;

        if (source instanceof OpdProcess)
        {
            int side = sourceDragger.getSide();
            double param = sourceDragger.getParam();

            if (side == OpcatConstants.N_BORDER &&
                (param>=0.2 && param <=0.8))
            {
                draggerSide = OpcatConstants.N_BORDER;
            }

            if (side == OpcatConstants.S_BORDER &&
                (param>=0.2 && param <=0.8))
            {
                draggerSide = OpcatConstants.S_BORDER;
            }

            if (param >= 0.8)
            {
                draggerSide = OpcatConstants.E_BORDER;
            }

            if (param <= 0.2)

            {
                draggerSide = OpcatConstants.W_BORDER;
            }

        }
        else
        {
            draggerSide = sourceDragger.getSide();
        }

        if (dX < sX && dY > sY) //1
        {
            if (draggerSide == OpcatConstants.N_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY-BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.E_BORDER)
            {
                points[0].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY);
                points[1].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY+(dY-sY)/2);
                points[2].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY+(dY-sY)/2);
                points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
                return;
            }

            if (draggerSide == OpcatConstants.S_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[1].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[2].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
                return;
            }

            if (draggerSide == OpcatConstants.W_BORDER)
            {
                points[0].setAbsolutesLocation(dX, sY);
                points[1].setAbsolutesLocation(dX, sY);
                points[2].setAbsolutesLocation(dX, sY);
                points[3].setAbsolutesLocation(dX, sY);
                return;
            }
        }

        if (dX > sX && dY > sY)  //2
        {
            if (draggerSide == OpcatConstants.N_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY-BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.E_BORDER)
            {
                points[0].setAbsolutesLocation(dX, sY);
                points[1].setAbsolutesLocation(dX, sY);
                points[2].setAbsolutesLocation(dX, sY);
                points[3].setAbsolutesLocation(dX, sY);
                return;

            }

            if (draggerSide == OpcatConstants.S_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[1].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[2].setAbsolutesLocation(sX, sY+(dY-sY)/2);
                points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
                return;
            }

            if (draggerSide == OpcatConstants.W_BORDER)
            {
                points[0].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY);
                points[1].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY+(dY-sY)/2);
                points[2].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY+(dY-sY)/2);
                points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
                return;
            }
        }


        if (dX > sX && dY < sY)  //3
        {
            if (draggerSide == OpcatConstants.N_BORDER)
            {
                points[0].setAbsolutesLocation(sX, dY-BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.E_BORDER)
            {
                points[0].setAbsolutesLocation(sX+(dX-sX)/2, sY);
                points[1].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY - BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.S_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY+BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(sX+(dX-sX)/2, sY+BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.W_BORDER)
            {

                points[0].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY);
                points[1].setAbsolutesLocation(sX-BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX-BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;

            }
        }


        if (dX < sX && dY < sY)  //4
        {
            if (draggerSide == OpcatConstants.N_BORDER)
            {
                points[0].setAbsolutesLocation(sX, dY-BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.E_BORDER)
            {
                points[0].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY);
                points[1].setAbsolutesLocation(sX+BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX+BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.S_BORDER)
            {
                points[0].setAbsolutesLocation(sX, sY+BYPASS_DISTANCE);
                points[1].setAbsolutesLocation(sX+(dX-sX)/2, sY+BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
                return;
            }

            if (draggerSide == OpcatConstants.W_BORDER)
            {
                points[0].setAbsolutesLocation(sX+(dX-sX)/2, sY);
                points[1].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
                points[3].setAbsolutesLocation(dX, dY - BYPASS_DISTANCE);
                return;
            }
        }

    }

    public void setVisible(boolean isVisible)
    {
        sourceDragger.setVisible(isVisible);
        relation.setVisible(isVisible);
        for (int i = 0; i < points.length; i++)
        {
            points[i].setVisible(isVisible);
        }
        for (int i = 0; i < lines.length; i++)
        {
            lines[i].setVisible(isVisible);
        }
    }

    public void incrementSelections()
    {
        selectionsCount++;
    }

    public void decrementSelections()
    {
        selectionsCount--;
    }

    public void incdecSelections(boolean incdec)
    {
        if(incdec)
        {
            this.incrementSelections();
        }
        else
        {
            this.decrementSelections();
        }
    }

    public int getSelectionsCount()
    {
        return selectionsCount;
    }

}

