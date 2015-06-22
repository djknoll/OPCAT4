package gui.projectStructure;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import exportedAPI.opcatAPI.IConnectionEdgeInstance;
import exportedAPI.opcatAPI.ILinkInstance;
import exportedAPI.opcatAPIx.IXConnectionEdgeInstance;
import exportedAPI.opcatAPIx.IXLinkInstance;
import exportedAPI.opcatAPIx.IXNode;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.MoveUpdatable;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.OpdAgentLink;
import gui.opdGraphics.draggers.OpdConditionLink;
import gui.opdGraphics.draggers.OpdConsumptionEventLink;
import gui.opdGraphics.draggers.OpdConsumptionLink;
import gui.opdGraphics.draggers.OpdEffectLink;
import gui.opdGraphics.draggers.OpdExceptionLink;
import gui.opdGraphics.draggers.OpdInstrumentEventLink;
import gui.opdGraphics.draggers.OpdInstrumentLink;
import gui.opdGraphics.draggers.OpdInvocationLink;
import gui.opdGraphics.draggers.OpdLink;
import gui.opdGraphics.draggers.OpdResultLink;
import gui.opdGraphics.draggers.TransparentLinkDragger;
import gui.opdGraphics.lines.StyledLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmAgent;
import gui.opmEntities.OpmConditionLink;
import gui.opmEntities.OpmConsumptionEventLink;
import gui.opmEntities.OpmConsumptionLink;
import gui.opmEntities.OpmEffectLink;
import gui.opmEntities.OpmExceptionLink;
import gui.opmEntities.OpmInstrument;
import gui.opmEntities.OpmInstrumentEventLink;
import gui.opmEntities.OpmInvocationLink;
import gui.opmEntities.OpmProceduralLink;
import gui.opmEntities.OpmResultLink;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JOptionPane;



/**
* <p>This class represents instance of OPM procedural link.
* This LinkInstance compound from several graphical elements.<br>
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

public class LinkInstance extends Instance implements MoveUpdatable, IXLinkInstance, ILinkInstance
{
	private AroundDragger sourceDragger;
	private AroundDragger destinationDragger;
	private OpdConnectionEdge source;
	private OpdConnectionEdge destination;
	private StyledLine line;

	private OpmProceduralLink myLink;
	private boolean autoArranged;
	private OpdProject myProject;
	private OrInstance destOr = null;
	private OrInstance sourceOr = null;


/**
* Creates LinkInstance with specified  parameters.
*
* @param pSource OpdConnectionEdge that represents the source graphically.
* @param pSourceDragger AroundDragger that connected to source.
* @param pDestination OpdConnectionEdge that represents the destination graphically.
* @param pDestinationDragger AroundDragger that connected to destination.
* @param pLine OpdLine that connects AroundDraggers.
* @param myKey OpdKey - key of graphical instance of this LinkInstance.
*/

	public LinkInstance(OpdConnectionEdge source, RelativeConnectionPoint pPoint1,
				OpdConnectionEdge destination, RelativeConnectionPoint pPoint2,
				OpmProceduralLink pLink, OpdKey myKey, OpdProject project,Container container)
	{
		super(myKey, project);

		myProject = project;
		this.source = source;
		this.destination = destination;
		myLink = pLink;

		destinationDragger = createOpdLink(destination, pPoint2);

		if (myLink instanceof OpmEffectLink)
		{
			sourceDragger = createOpdLink(source, pPoint1);
		}
		else
		{
			sourceDragger = createTransparentDragger(source, pPoint1);
		}


		line = new StyledLine(source, sourceDragger,
								destination, destinationDragger,
								myLink, getKey(), myProject);

		if (myLink instanceof OpmInvocationLink)
		{
			line.setStyle(StyledLine.INVOCATION);
		}
		else
		{
			line.setStyle(StyledLine.BREAKABLE);
		}


		this.add2Container(container);
		setAutoArranged(true);

	}



	private TransparentLinkDragger createTransparentDragger(Connectable edge, RelativeConnectionPoint pPoint)
	{
		TransparentLinkDragger gDragger;

		gDragger = new TransparentLinkDragger(edge, pPoint, myLink ,getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		gDragger.addMouseListener(gDragger);
		gDragger.addMouseMotionListener(gDragger);
		return gDragger;
	}

	private OpdLink createOpdLink(Connectable edge, RelativeConnectionPoint pPoint)
	{

		OpdLink nLink=null;

		if (myLink instanceof OpmAgent)
		{
			nLink = new OpdAgentLink(edge, pPoint, (OpmAgent)myLink ,getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmInstrument)
		{
			nLink = new OpdInstrumentLink(edge, pPoint, (OpmInstrument)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmConditionLink)
		{
			nLink = new OpdConditionLink(edge, pPoint, (OpmConditionLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmInstrumentEventLink)
		{
			nLink = new OpdInstrumentEventLink(edge, pPoint, (OpmInstrumentEventLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmResultLink)
		{
			nLink = new OpdResultLink(edge, pPoint, (OpmResultLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmConsumptionLink)
		{
			nLink = new OpdConsumptionLink(edge, pPoint, (OpmConsumptionLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}


		if (myLink instanceof OpmEffectLink)
		{
			nLink = new OpdEffectLink(edge, pPoint, (OpmEffectLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmExceptionLink)
		{
			nLink = new OpdExceptionLink(edge, pPoint, (OpmExceptionLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmInvocationLink)
		{
			nLink = new OpdInvocationLink(edge, pPoint, (OpmInvocationLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}

		if (myLink instanceof OpmConsumptionEventLink)
		{
			nLink = new OpdConsumptionEventLink(edge, pPoint, (OpmConsumptionEventLink)myLink , getKey().getOpdId(), getKey().getEntityInOpdId(), myProject);
		}


		if (nLink == null)
		{
			JOptionPane.showMessageDialog(null, " Serious internal bug occured in AddLink function \n Please contact software developers.", "Error" ,JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		nLink.addMouseListener(nLink);
		nLink.addMouseMotionListener(nLink);

		return nLink;
   }


/**
* Returns OpdConnectionEdge that represents the source graphically.
*
*/
	public OpdConnectionEdge getSource()
	{
		return source;
	}

/**
* Returns OpdConnectionEdge that represents the destination graphically.
*
*/

	public OpdConnectionEdge getDestination()
	{
		return destination;
	}

	public OpmProceduralLink getLink()
	{
		return myLink;
	}

    public RelativeConnectionPoint getSourceConnectionPoint()
    {
        return sourceDragger.getRelativeConnectionPoint();
    }

    public void setSourceConnectionPoint(RelativeConnectionPoint point)
    {
        sourceDragger.setRelativeConnectionPoint(point);
    }

    public RelativeConnectionPoint getDestinationConnectionPoint()
    {
        return destinationDragger.getRelativeConnectionPoint();
    }

    public void setDestinationConnectionPoint(RelativeConnectionPoint point)
    {
        destinationDragger.setRelativeConnectionPoint(point);
    }

    public IXConnectionEdgeInstance getSourceIXInstance()
    {
        return (IXConnectionEdgeInstance)source.getInstance();
    }
    public IXConnectionEdgeInstance getDestinationIXInstance()
    {
        return (IXConnectionEdgeInstance)destination.getInstance();
    }

    public IConnectionEdgeInstance getSourceIInstance()
    {
        return (IConnectionEdgeInstance)source.getInstance();
    }

    public IConnectionEdgeInstance getDestinationIInstance()
    {
        return (IConnectionEdgeInstance)destination.getInstance();
    }


	public void animate(long time)
	{
		line.animate(time);
	}

    public void animate(long totalTime, long remainedTime)
    {
        line.animate(totalTime, remainedTime);
    }

    public long getRemainedAnimationTime()
    {
        return line.getRemainedAnimationTime();
    }

    public long getTotalAnimationTime()
    {
        return line.getTotalAnimationTime();
    }

	public void animateAsFlash()
	{
		line.animateAsFlash();
	}

    public boolean isAnimated()
    {
        return line.isAnimated();
    }

	public void stopAnimation()
	{
		line.stopAnimation();
	}

    public void pauseAnimation()
    {
        line.pauseAnimaition();
    }

    public void continueAnimation()
    {
        line.continueAnimaition();
    }

	public OrInstance getDestOr()
	{
		return destOr;
	}

    public void setDestOr(OrInstance destOr)
	{
		this.destOr = destOr;
	}

    public OrInstance getSourceOr()
    {
        return sourceOr;
    }

    public void setSourceOr(OrInstance sourceOr)
    {
        this.sourceOr = sourceOr;
    }

/**
* Returns StyledLine connecting source and destination AroundDragger.
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
	public AroundDragger getSourceDragger()
	{
		return sourceDragger;
	}

/**
* Returns AroundDragger that connected to the destination.
*
*/
	public AroundDragger getDestinationDragger()
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
		return line.getLineColor();
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
		line.setDashed(myLink.isEnviromental());
		line.setUpperText(myLink.getPath());
		line.repaint();
	}

	public void copyPropsFrom(LinkInstance origin)
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
	}


	public void setSelected(boolean isSelected)
	{
		line.setSelected(isSelected);
		line.repaint();
		selected = isSelected;
	}

	 public void setVisible(boolean isVisible)
	 {
		 line.setVisible(isVisible);
		 sourceDragger.setVisible(isVisible);
		 destinationDragger.setVisible(isVisible);
	 }

         public boolean isVisible(){
           return sourceDragger.isVisible();
         }

	public void removeFromContainer()
	{
        if (destOr != null)
        {
            destOr.remove(this);
            destOr.update();
            destOr = null;
        }

        if (sourceOr != null)
        {
            sourceOr.remove(this);
            sourceOr.update();
            sourceOr = null;
        }

		line.removeFromContainer();
        source.removeUpdateListener(this);
        destination.removeUpdateListener(this);
        sourceDragger.removeUpdateListener(this);
        destinationDragger.removeUpdateListener(this);
	}

	public void add2Container(Container cn)
	{
		line.add2Container(cn);
        source.addUpdateListener(this);
        destination.addUpdateListener(this);
        sourceDragger.addUpdateListener(this);
        destinationDragger.addUpdateListener(this);
        updateOrConnections();
	}

		public long getLogicalId()
		{
		  return myLink.getId();
		}

		public void updateMove(Object origin)
		{
            updateOrConnections();
		}

		public void updateRelease(Object origin)
		{
			//empty
        }

        public void updateOrConnections()
        {
            updateDestinationConnections();
            updateSourceConnections();
        }

		public void updateDestinationConnections()
		{
			Hashtable neighbors = findDestinationNeighbors();

			if (neighbors.size()>0)
			{

				if (destOr == null)
				{
					for (Enumeration e = neighbors.elements(); e.hasMoreElements();)
					{
						destOr = ((LinkInstance)e.nextElement()).getDestOr();
					}

					if (destOr == null)
					{
						neighbors.put(this.getKey(), this);
						destOr = new OrInstance(neighbors, false, myProject);
						destOr.add2Container(sourceDragger.getParent());

						for (Enumeration e = neighbors.elements(); e.hasMoreElements();)
						{
							((LinkInstance)e.nextElement()).setDestOr(destOr);
						}
					}
				}

				destOr.add(this);
				destOr.update();
			}
			else
			{
				if (destOr == null)
				{
					return;
				}

				destOr.remove(this);
                destOr.update();
                destOr = null;
			}
		}

        public void updateSourceConnections()
        {
            Hashtable neighbors = findSourceNeighbors();

            if (neighbors.size()>0)
            {
                if (sourceOr == null)
                {
                    for (Enumeration e = neighbors.elements(); e.hasMoreElements();)
                    {
                        sourceOr = ((LinkInstance)e.nextElement()).getSourceOr();
                    }

                    if (sourceOr == null)
                    {
                        neighbors.put(this.getKey(), this);
                        sourceOr = new OrInstance(neighbors, true, myProject);
                        sourceOr.add2Container(sourceDragger.getParent());

                        for (Enumeration e = neighbors.elements(); e.hasMoreElements();)
                        {
                            ((LinkInstance)e.nextElement()).setSourceOr(sourceOr);
                        }
                    }
                }
                else
                {
                    sourceOr.add(this);
                    sourceOr.update();
                }
            }
            else
            {
                if (sourceOr == null)
                {
                    return;
                }

                sourceOr.remove(this);
                sourceOr.update();
                sourceOr = null;
            }
        }

        private boolean isDestinationNeighbour(LinkInstance li)
        {
          if (((LinkEntry)getEntry()).getLinkType()!=  ((LinkEntry)li.getEntry()).getLinkType())
          {
            return false;
          }
          GenericTable config = myProject.getConfiguration();
            double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
            double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
            double factor = currentSize/normalSize;

            double epsilon = 8.0*factor;

            if (li.getDestination() == destination)
            {
                RelativeConnectionPoint nPoint = li.getDestinationDragger().getRelativeConnectionPoint();
                RelativeConnectionPoint myPoint = destinationDragger.getRelativeConnectionPoint();

                if (isClose(destination.getAbsoluteConnectionPoint(nPoint),
                            destination.getAbsoluteConnectionPoint(myPoint), epsilon))
                {
                    return true;
                }
            }

            return false;

        }

        private boolean isClose(Point point1, Point point2, double epsilon)
        {
            double xDiff = point1.getX() - point2.getX();
            double yDiff = point1.getY() - point2.getY();
            return (xDiff*xDiff+yDiff*yDiff)<epsilon*epsilon;
        }

        private boolean isSourceNeighbour(LinkInstance li)
        {
          if (((LinkEntry)getEntry()).getLinkType()!=  ((LinkEntry)li.getEntry()).getLinkType())
          {
            return false;
          }


          GenericTable config = myProject.getConfiguration();
            double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
            double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
            double factor = currentSize/normalSize;

            double epsilon = 8.0*factor;

            if (li.getSource() == source)
            {
                RelativeConnectionPoint nPoint = li.getSourceDragger().getRelativeConnectionPoint();
                RelativeConnectionPoint myPoint = sourceDragger.getRelativeConnectionPoint();

                if (isClose(source.getAbsoluteConnectionPoint(nPoint),
                            source.getAbsoluteConnectionPoint(myPoint), epsilon))
                {
                    return true;
                }
            }

            return false;

        }

        private Hashtable findDestinationNeighbors() {

          Hashtable neighbors = new Hashtable();

          ConnectionEdgeInstance dIns = (ConnectionEdgeInstance) destination.getEntry().
              getInstance(destination.getOpdKey());

          for (Enumeration e = dIns.getRelatedInstances(); e.hasMoreElements(); ) {
            Object tmp = e.nextElement();

            if ( (tmp != this) && (tmp instanceof LinkInstance)) {
              LinkInstance li = (LinkInstance) tmp;
              if (isDestinationNeighbour(li)) {
                neighbors.put(li.getKey(), li);
              }
            }
          }

          return neighbors;
        }

        private Hashtable findSourceNeighbors()
        {

            Hashtable neighbors = new Hashtable();

            ConnectionEdgeInstance sIns = (ConnectionEdgeInstance)source.getEntry().getInstance(source.getOpdKey());

            for (Enumeration e = sIns.getRelatedInstances(); e.hasMoreElements();)
            {
                Object tmp = e.nextElement();

                if ((tmp!=this) && (tmp instanceof LinkInstance))
                {
                    LinkInstance li = (LinkInstance)tmp;
                    if (isSourceNeighbour(li))
                    {
                        neighbors.put(li.getKey(), li);
                    }
                }
            }

            return neighbors;
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

			if (yn)
			{
                updateOrConnections();
			}
		}

		public boolean isAutoArranged()
		{
			return autoArranged;
		}

		public void autoArrange()
		{
			//empty
		}


		public String toString()
		{
			return getEntry().getName();
		}

		public ConnectionEdgeInstance getSourceInstance()
		{
			return (ConnectionEdgeInstance)source.getEntry().getInstance(source.getOpdKey());
		}

		public ConnectionEdgeInstance getDestinationInstance()
		{
			return (ConnectionEdgeInstance)destination.getEntry().getInstance(destination.getOpdKey());
		}

                /**
                 * Gets IXNode which is source of this IXNode.
                 */

                public IXNode getSourceIXNode(){
                  return getSourceInstance();
                }

                /**
                 * Gets IXNode which is destination of this IXLine.
                 */
                public IXNode getDestinationIXNode(){
                  return getDestinationInstance();
                }


		public BaseGraphicComponent[] getGraphicComponents()
		{
			return line.getPointsArray();
		}

                public Enumeration getOrXorSourceNeighbours(boolean isOr)
                {
                  if (sourceOr == null || (sourceOr.isOr() == isOr))
                  {
                    return null;
                  }

                  Vector neighbours = new Vector();
                  for (Enumeration e = sourceOr.getInstances(); e.hasMoreElements();)
                  {
                    LinkInstance tIns = (LinkInstance)e.nextElement();
                    if (!tIns.equals(this))
                    {
                      neighbours.add(tIns);
                    }
                  }
                  return neighbours.elements();
                }

                public Enumeration getOrXorDestinationNeighbours(boolean isOr)
                {
                  if (destOr == null || (destOr.isOr() == isOr))
                  {
                    return null;
                  }

                  Vector neighbours = new Vector();
                  for (Enumeration e = destOr.getInstances(); e.hasMoreElements();)
                  {
                    LinkInstance tIns = (LinkInstance)e.nextElement();
                    if (!tIns.equals(this))
                    {
                      neighbours.add(tIns);
                    }
                  }
                  return neighbours.elements();
                }

}
