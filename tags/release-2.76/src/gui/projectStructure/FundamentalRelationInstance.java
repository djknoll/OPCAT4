package gui.projectStructure;
import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.IConnectionEdgeInstance;
import exportedAPI.opcatAPI.IRelationInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXNode;
import exportedAPI.opcatAPIx.IXRelationInstance;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DraggedPoint;
import gui.opdGraphics.MoveUpdatable;
import gui.opdGraphics.draggers.TextDragger;
import gui.opdGraphics.lines.OpdSimpleLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdFundamentalRelation;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmFundamentalRelation;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;


/**
* <p>This class represents instance of OPM fundamental relation.
* This FundamentalRelationInstance compound from several graphical elements.<br>
* destinationDragger - AroundDragger that connected to destination.<br>
* line - OpdLine that connect destination AroundDragger to "triangle" representing graphically
* this fundamental relation.<br>
* Additionaly this class holds information about source and destination instances,
* and logical entity - OpmFundamentalRelation representing logically this fundamental relation,
* OpdFundamentalRelation representing graphically this fundamental relation.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class FundamentalRelationInstance extends Instance implements MoveUpdatable, IXRelationInstance, IRelationInstance
{
	private OpmFundamentalRelation logicalRelation;
	private GraphicalRelationRepresentation myRepresentation;
	private TextDragger destinationDragger;
	private OpdConnectionEdge destination;

	private OpdSimpleLine lines[];
	private DraggedPoint points[];
	private OpdProject myProject;
	private final static int BYPASS_DISTANCE = 15;


/**
* Creates FundamentalRelationInstance with specified  parameters.
*
* @param pSource OpdConnectionEdge that represents the source graphically.
* @param pRelation OpdFundamentalRelation representing graphically this fundamental relation
* @param pDestination OpdConnectionEdge that represents the destination graphically.
* @param pDestinationDragger AroundDragger that connected to destination.
* @param pLine  OpdLine that connects destination AroundDragger to "triangle" representing graphically
* this fundamental relation.
* @param myKey OpdKey - key of graphical instance of this LinkInstance.
* @param pLogicalRelation OpmFundamentalRelation representing this fundamental relation.
*/
	public FundamentalRelationInstance(OpmFundamentalRelation pLogicalRelation, OpdConnectionEdge pDestination, RelativeConnectionPoint pPoint,
											GraphicalRelationRepresentation representation,   OpdKey myKey,
											  OpdProject project,Container cn)
	{
		super(myKey, project);

		myProject = project;
		lines = new OpdSimpleLine[5];
		points = new DraggedPoint[4];

		myRepresentation = representation;
		destination = pDestination;
		logicalRelation = pLogicalRelation;


		destinationDragger = addTextDragger(destination , pPoint,logicalRelation.getDestinationCardinality());
//                cn.add(destinationDragger,  JLayeredPane.MODAL_LAYER);
//                cn.add(destinationDragger.getOpdCardinalityLabel(),JLayeredPane.MODAL_LAYER);


		createPoints(cn);
		createLines(cn);

		destinationDragger.setLine(lines[4]);
		destination.addDragger(destinationDragger);


		addLines2Points();
		representation.getRelation().addLine(lines[0]);

		representation.getRelation().repaintLines();
		destination.repaintLines();
		setBackgroundColor(representation.getBackgroundColor());
		setBorderColor(representation.getBorderColor());
		setTextColor(representation.getTextColor());
		this.add2Container(cn);
		this.arrangeLines();
		this.update();
		destination.addUpdateListener(this);
		destinationDragger.addUpdateListener(this);
		getGraphicalRelationRepresentation().getRelation().addUpdateListener(this);


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

		  lines[0] = createLine(myRepresentation.getRelation(), new RelativeConnectionPoint(OpcatConstants.S_BORDER, 0.5) ,  points[0] , centerPoint);
		  lines[1] = createLine(points[0], centerPoint,  points[1] , centerPoint);
		  lines[2] = createLine(points[1], centerPoint,  points[2] , centerPoint);
		  lines[3] = createLine(points[2], centerPoint,  points[3] , centerPoint);
		  lines[4] = createLine(points[3], centerPoint,  destinationDragger , centerPoint);

//	  cn.add(lines[0], JLayeredPane.PALETTE_LAYER);
//      	  cn.add(lines[1], JLayeredPane.PALETTE_LAYER);
//	  cn.add(lines[2], JLayeredPane.PALETTE_LAYER);
//	  cn.add(lines[3], JLayeredPane.PALETTE_LAYER);
//	  cn.add(lines[4], JLayeredPane.PALETTE_LAYER);
	}


	private void createPoints(Container cn)
	{
		  points[0] = new DraggedPoint(myProject);
		  points[1] = new DraggedPoint(myProject);
		  points[2] = new DraggedPoint(myProject);
		  points[3] = new DraggedPoint(myProject);

          for (int i=0; i<points.length;i++)
          {
              points[i].setMoveable(false);
          }
//          cn.add(points[0], JLayeredPane.PALETTE_LAYER);
//	  cn.add(points[1], JLayeredPane.PALETTE_LAYER);
//	  cn.add(points[2], JLayeredPane.PALETTE_LAYER);
//	  cn.add(points[3], JLayeredPane.PALETTE_LAYER);

		  OpdFundamentalRelation rel = myRepresentation.getRelation();

		  int x1 = rel.getX() + rel.getWidth()/2 - points[0].getWidth()/2 - 5;
		  int y1 = rel.getY() + rel.getHeight();

		  int x2 = destinationDragger.getX() + destinationDragger.getWidth()/2 - points[0].getWidth()/2;
		  int y2 = destinationDragger.getY();


		  if (rel.getY()+rel.getWidth() < destination.getY())
		  {
			int diff = (destination.getY() - rel.getY() - rel.getWidth())/2;

			points[0].setAbsolutesLocation(x1, y1 + diff);
			points[1].setAbsolutesLocation(x1, y1 + diff);
			points[2].setAbsolutesLocation(x1 , y1 + diff);
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
//
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
		OpdSimpleLine gLine;
		gLine = new OpdSimpleLine(edge1, pPoint1, edge2, pPoint2, logicalRelation,
									 getKey(), myProject);
		gLine.addMouseListener(gLine);
		return gLine;
	}


  public TextDragger addTextDragger(Connectable edge, RelativeConnectionPoint pPoint, String text)
   {
	TextDragger gDragger;

	gDragger = new TextDragger(edge, pPoint, text, myProject);
	gDragger.addMouseListener(gDragger);
	gDragger.addMouseMotionListener(gDragger);

//	container.add(gDragger,  JLayeredPane.MODAL_LAYER);
//	container.repaint();
	return gDragger;
   }

   public void setAutoArranged(boolean isAutorranged)  {
	   //
   }
   public boolean isAutoArranged() {return true;}
   public void makeStraight(){
	   //
   }
   public boolean isStraight(){return false;}

   /**
    * Gets source RelativeConnectionPoint - the point on
    * source of this IXRelationInstance to which line the link is connected.
    * You can translate the RelativeConnectionPoint to regular Point by
    * getAbsoluteConnectionPoint of IXConnectionEdgeInstance method.
    * @see RelativeConnectionPoint
    * @see IXConnectionEdgeInstance#getAbsoluteConnectionPoint(RelativeConnectionPoint relPoint)
    */
   public RelativeConnectionPoint getSourceConnectionPoint()
   {
       return myRepresentation.getSourceDragger().getRelativeConnectionPoint();
   }

   /**
    * Sets source RelativeConnectionPoint - the point on
    * source of this IXRelationInstance to which line of this link is connected.
    * @see RelativeConnectionPoint
    */
   public void setSourceConnectionPoint(RelativeConnectionPoint point)
   {
       myRepresentation.getSourceDragger().setRelativeConnectionPoint(point);
   }

   /**
    * Gets destination RelativeConnectionPoint - the point on
    * destination of this IXRelationInstance to which line of this link is connected.
    * You can translate the RelativeConnectionPoint to regular Point by
    * getAbsoluteConnectionPoint of IXConnectionEdgeInstance method.
    * @see RelativeConnectionPoint
    * @see IXConnectionEdgeInstance#getAbsoluteConnectionPoint(RelativeConnectionPoint relPoint)
    */
   public RelativeConnectionPoint getDestinationConnectionPoint()
   {
       return destinationDragger.getRelativeConnectionPoint();
   }

   /**
    * Sets destination RelativeConnectionPoint - the point on
    * destination of this IXRelationInstance to which line of this link is connected.
    * @see RelativeConnectionPoint
    */

   public void setDestinationConnectionPoint(RelativeConnectionPoint point)
   {
       destinationDragger.setRelativeConnectionPoint(point);
   }

   /**
    * Gets IXConnectionEdgeInstance which is source of this IXRelationInstance.
    */

   public IXConnectionEdgeInstance getSourceIXInstance()
   {
       return (IXConnectionEdgeInstance)myRepresentation.getSourceInstance();
   }

   /**
    * Gets IXConnectionEdgeInstance which is destination of this IXRelationInstance.
    */
   public IXConnectionEdgeInstance getDestinationIXInstance()
   {
       return (IXConnectionEdgeInstance)destination.getInstance();
   }


   /**
    * Gets IConnectionEdgeInstance which is source of this IRelationInstance.
    */

   public IConnectionEdgeInstance getSourceIInstance()
   {
       return (IConnectionEdgeInstance)myRepresentation.getSourceInstance();
   }

   /**
    * Gets IConnectionEdgeInstance which is destination of this IRelationInstance.
    */
   public IConnectionEdgeInstance getDestinationIInstance()
   {
       return (IConnectionEdgeInstance)destination.getInstance();
   }


   /**
    * Gets IXNode which is source of this IXNode.
    */

   public IXNode getSourceIXNode() {
     return getSourceIXInstance();
   }

   /**
    * Gets IXNode which is destination of this IXLine.
    */
   public IXNode getDestinationIXNode() {
     return (IXConnectionEdgeInstance)getDestinationInstance();
   }


/**
* Returns OpdConnectionEdge that represents the destination graphically.
*
*/


	public OpdConnectionEdge getDestination()
	{
		return destination;
	}

	public Instance getDestinationInstance()
	{
		return destination.getEntry().getInstance(destination.getOpdKey());
	}

		public GraphicalRelationRepresentation getGraphicalRelationRepresentation()
		{
		  return myRepresentation;
		}

/**
* Returns OpdLine that connects destination AroundDragger to "triangle" representing
* graphically this fundamental relation.
*
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
* Returns AroundDragger that connected to the destination.
*
*/
	public TextDragger getDestinationDragger()
	{
		return destinationDragger;
	}

/**
* Returns OpmFundamentalRelation that represents this fundamental relation logically.
*
*/
	public OpmFundamentalRelation getLogicalRelation()
	{
		return logicalRelation;
	}

		public Color getBackgroundColor()
		{
		  return destinationDragger.getBackgroundColor();
		}

		public void setBackgroundColor(Color backgroundColor)
		{
		  destinationDragger.setBackgroundColor(backgroundColor);
		}

		public Color getBorderColor()
		{
		  return destinationDragger.getBorderColor();
		}

		public void setBorderColor(Color borderColor)
		{
		  destinationDragger.setBorderColor(borderColor);
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
		}

		public Color getTextColor()
		{
		  return destinationDragger.getTextColor();
		}

		public void setTextColor(Color textColor)
		{
		  destinationDragger.setTextColor(textColor);
		  destinationDragger.repaint();
		  destinationDragger.getOpdCardinalityLabel().setTextColor(textColor);
		  destinationDragger.getOpdCardinalityLabel().repaint();
		}

		public void setSelected(boolean isSelected)
		{
		  for (int i = 0; i < points.length; i++)
		  {
			points[i].setSelected(isSelected);
			points[i].repaint();
		  }

		  destinationDragger.setSelected(isSelected);
		  destinationDragger.repaint();
		  myRepresentation.incdecSelections(isSelected);
		  selected = isSelected;
		}

		public void setVisible(boolean isVisible)
		{
			destinationDragger.setVisible(isVisible);
			for (int i = 0; i < points.length; i++)
			{
				points[i].setVisible(isVisible);
			}
			for (int i = 0; i < lines.length; i++)
			{
				lines[i].setVisible(isVisible);
			}
			myRepresentation.setVisible(isVisible);
		}

                public boolean isVisible() {
                  return destinationDragger.isVisible();
                }


		public void removeFromContainer()
		{
		  Container cn =  destinationDragger.getParent();

		  cn.remove(destinationDragger);
		  cn.remove(destinationDragger.getOpdCardinalityLabel());
		  destination.removeDragger(destinationDragger);

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
			cn.add(points[i], JLayeredPane.PALETTE_LAYER);
		  }

		  for (int i = 0; i < lines.length; i++)
		  {
			cn.add(lines[i], new Integer(JLayeredPane.PALETTE_LAYER.intValue()-1));
		  }

		  cn.add(destinationDragger, new Integer(JLayeredPane.PALETTE_LAYER.intValue()+1));
		  destination.addDragger(destinationDragger);
		  cn.add(destinationDragger.getOpdCardinalityLabel(), JLayeredPane.MODAL_LAYER);
		  destinationDragger.updateDragger();
		  cn.repaint();

		}



		public void update()
		{
			destinationDragger.setText(logicalRelation.getDestinationCardinality());
			destination.repaintLines();
			for (int i = 0; i < points.length; i++)
			{
			  points[i].repaintLines();
			}

		}



		public long getLogicalId()
		{
		  return logicalRelation.getId();
		}


		public void updateMove(Object origin)
		{
		  this.arrangeLines();

//          for (int i = 0; i < points.length; i++)
//          {
//            points[i].repaintLines();
//          }
//

//            System.err.println(points[0]+" "+points[1]+" "+points[2]+" "+points[3]);
		}

		public void updateRelease(Object origin)
		{}

		public void arrangeLines()
		{
			GenericTable config = myProject.getConfiguration();
			double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
			double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
			double factor = currentSize/normalSize;

			int bypassDistance = (int)Math.round(BYPASS_DISTANCE*factor);

		  JComponent rel = getGraphicalRelationRepresentation().getRelation();
		  int sX = rel.getX()+rel.getWidth()/2-points[0].getWidth()/2;
		  int sY = rel.getY()+rel.getHeight()-points[3].getHeight()/2;
		  int dX = destinationDragger.getX() + destinationDragger.getWidth()/2 - points[3].getWidth()/2;
		  int dY = destinationDragger.getY() + destinationDragger.getHeight()/2-points[3].getHeight()/2;

		  int draggerSide=0;

		  if (destination instanceof OpdProcess)
		  {
			int side = destinationDragger.getSide();
			double param = destinationDragger.getParam();

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
			draggerSide = destinationDragger.getSide();
		  }

		  if (dX < sX && dY > sY) //1
		  {
			if (draggerSide == OpcatConstants.N_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[1].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[2].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[3].setAbsolutesLocation(dX, sY+(int)Math.round((dY-sY)/2.0));
			  return;
			}

			if (draggerSide == OpcatConstants.E_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, dY);
			  points[1].setAbsolutesLocation(sX, dY);
			  points[2].setAbsolutesLocation(sX, dY);
			  points[3].setAbsolutesLocation(sX, dY);
			  return;
			}

			if (draggerSide == OpcatConstants.S_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[1].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[2].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[3].setAbsolutesLocation(dX, dY+bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.W_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[1].setAbsolutesLocation(dX-bypassDistance, sY+(int)Math.round((dY-sY)/2.0));
			  points[2].setAbsolutesLocation(dX-bypassDistance, sY+(int)Math.round((dY-sY)/2.0));
			  points[3].setAbsolutesLocation(dX-bypassDistance, dY);
			  return;
			}
		  }

		  if (dX > sX && dY > sY)  //2
		  {
			if (draggerSide == OpcatConstants.N_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[1].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[2].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[3].setAbsolutesLocation(dX, sY+(int)Math.round((dY-sY)/2.0));
			  return;
			}

			if (draggerSide == OpcatConstants.E_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+(int)Math.round((dY-sY)/2.0));
			  points[1].setAbsolutesLocation(dX+bypassDistance, sY+(int)Math.round((dY-sY)/2.0));
			  points[2].setAbsolutesLocation(dX+bypassDistance, sY+(int)Math.round((dY-sY)/2.0));
			  points[3].setAbsolutesLocation(dX+bypassDistance, dY);
			  return;
			}

			if (draggerSide == OpcatConstants.S_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[1].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[2].setAbsolutesLocation(sX, dY+bypassDistance);
			  points[3].setAbsolutesLocation(dX, dY+bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.W_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, dY);
			  points[1].setAbsolutesLocation(sX, dY);
			  points[2].setAbsolutesLocation(sX, dY);
			  points[3].setAbsolutesLocation(sX, dY);
			  return;
			}
		  }


		  if (dX < sX && dY < sY)  //3
		  {
			if (draggerSide == OpcatConstants.N_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), dY-bypassDistance);
			  points[3].setAbsolutesLocation(dX, dY-bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.E_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), dY);
			  return;
			}

			if (draggerSide == OpcatConstants.S_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX, sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX, sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX, sY+bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.W_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX-bypassDistance, sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX-bypassDistance, sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX-bypassDistance, dY);
			  return;
			}
		  }


		  if (dX > sX && dY < sY)  //4
		  {
			if (draggerSide == OpcatConstants.N_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), dY-bypassDistance);
			  points[3].setAbsolutesLocation(dX, dY-bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.E_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX+bypassDistance, sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX+bypassDistance, sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX+bypassDistance, dY);
			  return;
			}

			if (draggerSide == OpcatConstants.S_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX, sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX, sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX, sY+bypassDistance);
			  return;
			}

			if (draggerSide == OpcatConstants.W_BORDER)
			{
			  points[0].setAbsolutesLocation(sX, sY+bypassDistance);
			  points[1].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[2].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), sY+bypassDistance);
			  points[3].setAbsolutesLocation(dX+(int)Math.round((sX-dX)/2.0), dY);
			  return;

			}
		  }



		}

		public BaseGraphicComponent[] getGraphicComponents()
		{
			return points;
		}
			

              //reuseComment
                 public Instance getSourceInstance()
                 {
                   return  myRepresentation.getSourceInstance();
                 }
               //endReuseComment

}
