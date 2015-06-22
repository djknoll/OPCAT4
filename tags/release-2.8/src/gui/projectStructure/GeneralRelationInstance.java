package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.IConnectionEdgeInstance;
import exportedAPI.opcatAPI.IRelationInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXNode;
import exportedAPI.opcatAPIx.IXRelationInstance;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.draggers.OpdBiDirectionalRelation;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.draggers.OpdUniDirectionalRelation;
import gui.opdGraphics.draggers.TransparentRelationDragger;
import gui.opdGraphics.lines.StyledLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmBiDirectionalRelation;
import gui.opmEntities.OpmGeneralRelation;
import gui.opmEntities.OpmUniDirectionalRelation;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JOptionPane;

/**
* <p>This class represents instance of OPM general structural relation.
* This GeneralRelationInstance compound from several graphical elements.<br>
* sourceDragger - AroundDragger that connected to source.<br>
* destinationDragger - AroundDragger that connected to destination.<br>
* line - OpdLine that connects AroundDraggers.<br>
* Additionaly this class holds information about source and destination instances.
*
* @version	2.0
* @author		Stanislav Obydionnov
* @author		Yevgeny   Yaroker
*
*/

public class GeneralRelationInstance extends Instance implements IRelationInstance, IXRelationInstance
{
	private OpdRelationDragger sourceDragger;
	private OpdRelationDragger destinationDragger;
	private OpdConnectionEdge source;
	private OpdConnectionEdge destination;
	private StyledLine line;

	private OpmGeneralRelation myRelation;
	private boolean autoArranged;

/**
* Creates GeneralRelationInstance with specified  parameters.
*
* @param pSource OpdConnectionEdge that represents the source graphically.
* @param pSourceDragger AroundDragger that connected to source.
* @param pDestination OpdConnectionEdge that represents the destination graphically.
* @param pDestinationDragger AroundDragger that connected to destination.
* @param pLine OpdLine that connects AroundDraggers.
* @param myKey OpdKey - key of graphical instance of this GeneralRelationInstance.
*/


		public GeneralRelationInstance(OpdConnectionEdge source, RelativeConnectionPoint pPoint1,
				OpdConnectionEdge destination, RelativeConnectionPoint pPoint2,
				OpmGeneralRelation pRelation, OpdKey myKey, OpdProject project,Container container)
		{
		  super(myKey, project);
		  this.source = source;
		  this.destination = destination;
		  myRelation = pRelation;

		  destinationDragger = createOpdRelation(destination, pPoint2);


		  if (myRelation instanceof OpmBiDirectionalRelation)
		  {
			sourceDragger = createOpdRelation(source, pPoint1);
		  }
		  else
		  {
			sourceDragger = createTransparentDragger(source, pPoint1);
		  }

		  line = new StyledLine(source, sourceDragger,
								destination, destinationDragger,
								myRelation, getKey(), myProject);


		  this.add2Container(container);
		  setAutoArranged(true);
	  }

	  private OpdRelationDragger createOpdRelation(Connectable edge, RelativeConnectionPoint pPoint)
	  {

		OpdRelationDragger nRelation=null;


		if (myRelation instanceof OpmUniDirectionalRelation)
		{
			nRelation = new OpdUniDirectionalRelation(edge, pPoint, (OpmUniDirectionalRelation)myRelation , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myRelation instanceof OpmBiDirectionalRelation)
		{
			nRelation = new OpdBiDirectionalRelation(edge, pPoint, (OpmBiDirectionalRelation)myRelation , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}


		if (nRelation == null)
		{
			JOptionPane.showMessageDialog(null, " Serious internal bug occured in AddLink function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		nRelation.addMouseListener(nRelation);
		nRelation.addMouseMotionListener(nRelation);

		return nRelation;
   }

	private TransparentRelationDragger createTransparentDragger(Connectable edge, RelativeConnectionPoint pPoint)
	{
		TransparentRelationDragger gDragger;

		gDragger = new TransparentRelationDragger(edge, pPoint, (OpmUniDirectionalRelation)myRelation , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		gDragger.addMouseListener(gDragger);
		gDragger.addMouseMotionListener(gDragger);
		return gDragger;
	}




	public OpmGeneralRelation getGeneralRelation()
	{
		return myRelation;
	}

/**
* Returns OpdConnectionEdge that represents the source graphically.
*
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

/**
* Returns OpdLine connecting source and destination AroundDragger.
*
*/
	public StyledLine getLine()
	{
		return line;
	}

/**
* Returns AroundDragger that connected to the source.
*
*/
	public OpdRelationDragger getSourceDragger()
	{
		return sourceDragger;
	}

/**
* Returns AroundDragger that connected to the destination.
*
*/
	public OpdRelationDragger getDestinationDragger()
	{
		return destinationDragger;
	}

		public Color getBackgroundColor()
		{
		  return sourceDragger.getBackgroundColor();
		}

		public void setBackgroundColor(Color backgroundColor)
		{
		  sourceDragger.setBackgroundColor(backgroundColor);
		  destinationDragger.setBackgroundColor(backgroundColor);
		  sourceDragger.repaint();
		  destinationDragger.repaint();
		}

		public Color getBorderColor()
		{
		  return sourceDragger.getBorderColor();
		}

		public void setBorderColor(Color borderColor)
		{
		  line.setLineColor(borderColor);
		  line.repaint();
		}

		public Color getTextColor()
		{
		  return line.getTextColor();
		}

		public void setTextColor(Color textColor)
		{
		  line.setTextColor(textColor);
		  line.repaint();
		}

		public void update()
		{
			sourceDragger.updateDragger();
			destinationDragger.updateDragger();
			sourceDragger.setText(myRelation.getSourceCardinality());
			destinationDragger.setText(myRelation.getDestinationCardinality());
			line.setUpperText(myRelation.getForwardRelationMeaning());
			line.setLowerText(myRelation.getBackwardRelationMeaning());
			line.setDashed(myRelation.isEnviromental());
			line.repaint();
		}

		public void copyPropsFrom(GeneralRelationInstance origin)
		{
			super.copyPropsFrom(origin);
			sourceDragger.setSide(origin.getSourceDragger().getSide());
			sourceDragger.setParam(origin.getSourceDragger().getParam());
			destinationDragger.setSide(origin.getDestinationDragger().getSide());
			destinationDragger.setParam(origin.getDestinationDragger().getParam());
			line.copyShapeFrom(origin.getLine());
			setAutoArranged(origin.isAutoArranged());
            if (isAutoArranged())
            {
                line.arrange();
            }

			int dX = origin.getSourceDragger().getOpdCardinalityLabel().getX() - origin.getSourceDragger().getX();
			int dY = origin.getSourceDragger().getOpdCardinalityLabel().getY() - origin.getSourceDragger().getY();;
			sourceDragger.getOpdCardinalityLabel().setLocation(sourceDragger.getX()+dX, sourceDragger.getY()+dY);

			dX = origin.getDestinationDragger().getOpdCardinalityLabel().getX() - origin.getDestinationDragger().getX();
			dY = origin.getDestinationDragger().getOpdCardinalityLabel().getY() - origin.getDestinationDragger().getY();
			destinationDragger.getOpdCardinalityLabel().setLocation(destinationDragger.getX()+dX, destinationDragger.getY()+dY);
		}


		public void setSelected(boolean isSelected)
		{
			line.setSelected(isSelected);
			selected = isSelected;
		}

		public void removeFromContainer()
		{
			line.removeFromContainer();
		}

		public void add2Container(Container cn)
		{
			line.add2Container(cn);
		}


		public long getLogicalId()
		{
		  return myRelation.getId();
		}

        public void makeStraight()
        {
            line.makeStraight();
        }

        public boolean isStraight()
        {
            return line.isStraight();
        }

		public void setAutoArranged(boolean yn)
		{
			autoArranged = yn;
			line.setAutoArranged(yn);
		}

		public boolean isAutoArranged()
		{
			return autoArranged;
		}

        public RelativeConnectionPoint getSourceConnectionPoint()
        {
            return sourceDragger.getRelativeConnectionPoint();
        }

        /**
         * Sets source RelativeConnectionPoint - the point on
         * source of this IXRelationInstance to which line of this link is connected.
         * @see RelativeConnectionPoint
         */
        public void setSourceConnectionPoint(RelativeConnectionPoint point)
        {
            sourceDragger.setRelativeConnectionPoint(point);
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
            return (IXConnectionEdgeInstance)source.getInstance();
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
            return (IConnectionEdgeInstance)source.getInstance();
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
          return (IXConnectionEdgeInstance)getSourceInstance();
        }

        /**
         * Gets IXNode which is destination of this IXLine.
         */
        public IXNode getDestinationIXNode() {
          return (IXConnectionEdgeInstance)getDestinationInstance();
        }


		public void autoArrange()
		{}
		public void setVisible(boolean isVisible)
		{
			line.setVisible(isVisible);
			sourceDragger.setVisible(isVisible);
			destinationDragger.setVisible(isVisible);
		}

                public boolean isVisible()
                {
                  return sourceDragger.isVisible();
                }

		public BaseGraphicComponent[] getGraphicComponents()
		{
			return line.getPointsArray();
		}
}
