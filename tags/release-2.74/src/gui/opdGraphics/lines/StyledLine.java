package gui.opdGraphics.lines;

import exportedAPI.OpcatConstants;
import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DoublePoint;
import gui.opdGraphics.DraggedPoint;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.MoveUpdatable;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.projectStructure.Entry;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkInstance;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;



public class StyledLine implements MoveUpdatable
{
	public static final int ORTHOGONAL = 1;
	public static final int BREAKABLE = 2;
	public static final int INVOCATION = 3;

	public static final double FRAMES_PER_SECOND = 12.0;
	private String upperText;
	private String lowerText;
	private Vector points;
	private Vector lines;
	private int style;

	private OpmEntity myEntity;
	private OpdKey myKey;
	private OpdProject myProject;

	private OpdConnectionEdge source;
	private AroundDragger sourceDragger;
	private OpdConnectionEdge destination;
	private AroundDragger destinationDragger;

	private boolean autoArranged;
	private boolean selected;
	private javax.swing.Timer animationTimer;
    private boolean animated;
    private boolean animatedAsFlash;
    private boolean paused;
	private long startTime;
	private long totalTime;
    private long performedTime;
	private Color originalColor;

	private Container cn = null;

	private static final double LINE_SENSITIVITY = 5.0;
	private final static double BYPASS_DISTANCE = 15.0;

	public StyledLine(OpdConnectionEdge src, AroundDragger srcDragger,
					  OpdConnectionEdge dest, AroundDragger destDragger,
					  OpmEntity pEntity, OpdKey key, OpdProject pProject)
	{
		myProject = pProject;
		myKey = key;
		myEntity = pEntity;
		source = src;
		sourceDragger = srcDragger;
		destination = dest;
		destinationDragger = destDragger;

		points = new Vector();
		lines = new Vector();
		TextLine newLine = createTextLine(sourceDragger, destinationDragger);
		lines.add(newLine);

		sourceDragger.setLine(newLine);
		destinationDragger.setLine(newLine);

		setStyle(BREAKABLE);
		selected = false;
//        setStyle(ORTHOGONAL);
//        orthogonalArrange();

        animated = false;
        animatedAsFlash = false;
        paused = false;
	}

    public OpdConnectionEdge getSource()
    {
      return source;
    }

    public AroundDragger getSourceDragger()
    {
      return sourceDragger;
    }

    public OpdConnectionEdge getDestination()
    {
      return destination;
    }

    public AroundDragger getDestinationDragger()
    {
      return destinationDragger;
    }

    public Instance getMyInstance()
    {
        Entry myEntry = myProject.getComponentsStructure().getEntry(myEntity.getId());
        return myEntry.getInstance(myKey);
    }

	public boolean isStraight()
    {
        return (points.size() == 0);
    }
    public void makeStraight()
	{
		if (style != INVOCATION)
		{
			setNumOfPoints(0);
		}

        //        updateOrConnections
        if (getMyInstance() instanceof LinkInstance)
        {
            ((LinkInstance)getMyInstance()).updateMove(this);
        }

	}

	public void copyShapeFrom(StyledLine origin)
	{
		setStyle(origin.getStyle());

		if (style != BREAKABLE)
		{
			return;
		}

		Container originCn = origin.cn;
		Vector originPoints = origin.getPointsVector();
		setNumOfPoints(originPoints.size());

		for (int i=0; i<originPoints.size(); i++)
		{
			DraggedPoint myPoint = (DraggedPoint)points.get(i);
			DraggedPoint originPoint = (DraggedPoint)originPoints.get(i);
			Point location = SwingUtilities.convertPoint(originCn, originPoint.getX(), originPoint.getY(), cn);
			myPoint.setLocation((int)location.getX(), (int)location.getY());
		}

	}

	public void addPoint(Point newPoint)
	{
		breakLastLine(newPoint);
	}

	public void setSelected(boolean isSelected)
	{
		selected = isSelected;
		sourceDragger.setSelected(selected);
		sourceDragger.repaint();
		destinationDragger.setSelected(selected);
		destinationDragger.repaint();

		for (int i=0; i < points.size(); i++)
		{
			DraggedPoint tPoint = (DraggedPoint)points.get(i);
			tPoint.setSelected(selected);
			tPoint.repaint();
		}
	}

	public boolean isSelected()
	{
		return selected;
	}

	public void setAutoArranged(boolean autoArranged)
	{
		if (cn == null)
		{
			return;
		}

        if (autoArranged && !this.autoArranged)
        {
            arrange();
        }

		this.autoArranged = autoArranged;


//		sourceDragger.setMoveable(!autoArranged);
//		destinationDragger.setMoveable(!autoArranged);
//
//		if (autoArranged)
//		{
//			((JLayeredPane)cn).moveToBack(sourceDragger);
//			((JLayeredPane)cn).moveToBack(destinationDragger);
//
//			arrange();
//		}
//		else
//		{
//			((JLayeredPane)cn).moveToFront(sourceDragger);
//			((JLayeredPane)cn).moveToFront(destinationDragger);
//		}
	}

	public Vector getPointsVector()
	{
		return points;
	}

	public OpmEntity getEntity()
	{
		return myEntity;
	}

	public OpdKey getKey()
	{
		return myKey;
	}

	public boolean isAutoArranged()
	{
		return autoArranged;
	}

	public void arrange()
	{
		if (style == BREAKABLE || style == INVOCATION)
		{
			RelativeConnectionPoint sourcePoint;
			RelativeConnectionPoint destinationPoint;

			if (points.isEmpty())
			{
				sourcePoint = GraphicsUtils.calculateConnectionPoint(source ,destination);
				destinationPoint = GraphicsUtils.calculateConnectionPoint(destination, source);
			}
			else
			{
				sourcePoint = GraphicsUtils.calculateConnectionPoint(source, (DraggedPoint)points.get(0));
				destinationPoint = GraphicsUtils.calculateConnectionPoint(destination, (DraggedPoint)points.get(points.size()-1));
			}

			sourceDragger.setRelativeConnectionPoint(sourcePoint);
			sourceDragger.updateDragger();
			destinationDragger.setRelativeConnectionPoint(destinationPoint);
			destinationDragger.updateDragger();

		}
	}


	public void setStyle(int style)
	{
		if (this.style == style)
		{
			return;
		}

		this.style = style;

		if (style == ORTHOGONAL)
		{
			for (int i=0; i<lines.size(); i++)
			{
				TextLine tLine = (TextLine)lines.get(i);
				tLine.removeMouseMotionListener(tLine);
			}

			return;
		}

		if (style == BREAKABLE)
		{
			for (int i=0; i<lines.size(); i++)
			{
				TextLine tLine = (TextLine)lines.get(i);
				tLine.addMouseMotionListener(tLine);
			}

			return;
		}

		if (style == INVOCATION)
		{
			points.clear();
			lines.clear();
			OpdInvocationLine newLine = createInvocationLine(sourceDragger, destinationDragger);
			lines.add(newLine);
			sourceDragger.setLine(newLine);
			destinationDragger.setLine(newLine);

		}

		return;
	}

	public int getStyle()
	{
		return style;
	}
	public void add2Container(Container cn)
	{
		if (this.cn != null)
		{
			System.err.println("Can't add one styled line to more than one container");
			return;
		}

		this.cn = cn;

		source.addUpdateListener(this);
		destination.addUpdateListener(this);

		for (int i=0; i<points.size(); i++)
		{
			cn.add((DraggedPoint)points.get(i), JLayeredPane.PALETTE_LAYER);
		}

		for (int i=0; i<lines.size(); i++)
		{
			cn.add((OpdLine)lines.get(i), new Integer(JLayeredPane.PALETTE_LAYER.intValue()-1));
		}

		sourceDragger.addUpdateListener(this);
		destinationDragger.addUpdateListener(this);

		cn.add(sourceDragger, JLayeredPane.PALETTE_LAYER);
		cn.add(destinationDragger, JLayeredPane.PALETTE_LAYER);

		if (sourceDragger instanceof OpdRelationDragger)
		{
			cn.add(((OpdRelationDragger)sourceDragger).getOpdCardinalityLabel(), JLayeredPane.MODAL_LAYER);
		}

		if (destinationDragger instanceof OpdRelationDragger)
		{
			cn.add(((OpdRelationDragger)destinationDragger).getOpdCardinalityLabel(), JLayeredPane.MODAL_LAYER);
		}

		source.repaintLines();
		destination.repaintLines();

	}

	public void removeFromContainer()
	{
		cn.remove(sourceDragger);

		if (sourceDragger instanceof OpdRelationDragger)
		{
			cn.remove(((OpdRelationDragger)sourceDragger).getOpdCardinalityLabel());
		}

		cn.remove(destinationDragger);

		if (destinationDragger instanceof OpdRelationDragger)
		{
			cn.remove(((OpdRelationDragger)destinationDragger).getOpdCardinalityLabel());
		}
		for (int i=0; i<lines.size(); i++)
		{
			cn.remove((OpdLine)lines.get(i));
		}

		for (int i=0; i<points.size(); i++)
		{
			cn.remove((DraggedPoint)points.get(i));
		}

		sourceDragger.removeUpdateListener(this);
		destinationDragger.removeUpdateListener(this);

		source.removeUpdateListener(this);
		destination.removeUpdateListener(this);
		cn.repaint();

		cn = null;
	}

	private OpdInvocationLine createInvocationLine(Connectable source, Connectable destination)
	{
		OpdInvocationLine gLine;
		RelativeConnectionPoint centerPoint = new RelativeConnectionPoint(OpdBaseComponent.CENTER, 0);

		gLine = new OpdInvocationLine(source, centerPoint,
										  destinationDragger, centerPoint,
										  myEntity, myKey, myProject);

//        gLine.setParentLine(this);
		gLine.addMouseListener(gLine);
		return gLine;
	}


	private TextLine createTextLine(Connectable source, Connectable destination)
	{
		TextLine gLine;
		RelativeConnectionPoint centerPoint = new RelativeConnectionPoint(OpdBaseComponent.CENTER, 0);

		gLine = new TextLine(source, centerPoint,
										  destination, centerPoint,
										  myEntity, myKey, myProject);

		gLine.setParentLine(this);
		gLine.addMouseListener(gLine);
		if (style == BREAKABLE)
		{
			gLine.addMouseMotionListener(gLine);
		}
		return gLine;
	}

	public void breakLine(OpdLine brLine, Point location)
	{

		int index = lines.indexOf(brLine);
		if (index == -1)
		{
			return;
		}

		if (index == lines.size() - 1)
		{
			breakLastLine(location);
			removeRedundantLines();

			if (style == BREAKABLE && isAutoArranged())
			{
				arrange();
			}

	//     updateOrConnections
            if (getMyInstance() instanceof LinkInstance)
            {
                ((LinkInstance)getMyInstance()).updateMove(this);
            }

			return;
		}


		DraggedPoint oldPoint = (DraggedPoint)points.get(index);
		Container cn = brLine.getParent();

		DraggedPoint newPoint = new DraggedPoint(myProject);
		newPoint.addUpdateListener(this);
		cn.add(newPoint, JLayeredPane.PALETTE_LAYER);
		newPoint.setLocation(location);

		newPoint.setSelected(selected);
		newPoint.addMouseListener(newPoint);
		newPoint.addMouseMotionListener(newPoint);
		brLine.setEdge2(newPoint);

		OpdLine newLine = createTextLine(newPoint, oldPoint);
		cn.add(newLine, new Integer(JLayeredPane.PALETTE_LAYER.intValue()-1));
		newLine.setTextColor(getTextColor());
		newLine.setLineColor(getLineColor());


		oldPoint.removeLine(brLine);
		oldPoint.addLine(newLine);

		newPoint.addLine(newLine);
		newPoint.addLine(brLine);

		newLine.update();
		brLine.update();
		lines.add(index+1, newLine);
		points.add(index, newPoint);

		removeRedundantLines();

	//        updateOrConnections
        if (getMyInstance() instanceof LinkInstance)
        {
            ((LinkInstance)getMyInstance()).updateMove(this);
        }


		if (style == BREAKABLE && isAutoArranged())
		{
			arrange();
		}

	}


	private void breakLastLine(Point location)
	{

		OpdLine brLine = (OpdLine)lines.get(lines.size()-1);
		Container cn = brLine.getParent();

		DraggedPoint newPoint = new DraggedPoint(myProject);

		if (cn !=null)
		{
			cn.add(newPoint, JLayeredPane.PALETTE_LAYER);
		}
		newPoint.setLocation(location);

		newPoint.addMouseListener(newPoint);
		newPoint.addMouseMotionListener(newPoint);
		newPoint.addUpdateListener(this);
		newPoint.setSelected(selected);

		brLine.setEdge2(newPoint);


		OpdLine newLine = createTextLine(newPoint, destinationDragger);

		if (cn !=null)
		{
			cn.add(newLine, new Integer(JLayeredPane.PALETTE_LAYER.intValue()-1));
		}

		newLine.setTextColor(getTextColor());
		newLine.setLineColor(getLineColor());

		destinationDragger.setLine(newLine);

		newPoint.addLine(newLine);
		newPoint.addLine(brLine);

		newLine.update();
		brLine.update();
		lines.add(newLine);
		points.add(newPoint);
	}

	public synchronized void updateMove(Object origin)
	{
		if (origin instanceof OpdConnectionEdge)
		{
			if (getStyle() == BREAKABLE || getStyle() == INVOCATION)
			{
				if (isAutoArranged())
				{
					arrange();
				}
				else
				{
					sourceDragger.updateDragger();
					destinationDragger.updateDragger();
				}

				return;
			}

			return;

		}


		if (origin instanceof DraggedPoint)
		{
			int index = points.indexOf(origin);
			if (index == -1)
			{
				return;
			}

			if (getStyle() == BREAKABLE)
			{
				((TextLine)lines.get(index)).update();
				((TextLine)lines.get(index+1)).update();

				if (isAutoArranged())
				{
					arrange();
				}
			}

			if (getStyle() == ORTHOGONAL)
			{
				if (index == 0)
				{
					updateDragger4Point(sourceDragger);
				}
				else
				{
					updatePoint(index-1,index);
				}

				if (index == points.size()-1)
				{
					updateDragger4Point(destinationDragger);
				}
				else
				{
					updatePoint(index+1,index);
				}

			}

            if (getMyInstance() instanceof LinkInstance)
            {
                ((LinkInstance)getMyInstance()).updateMove(this);
            }

            return;
		}

		if (origin instanceof AroundDragger)
		{
			if (getStyle() == ORTHOGONAL)
			{
				updatePoint4Dragger((AroundDragger)origin);
				return;
			}
		}
	}

	private void updatePoint4Dragger(AroundDragger dragger)
	{
		DraggedPoint tPoint;
		TextLine tLine;

		if (dragger == sourceDragger)
		{
			tPoint = (DraggedPoint)points.get(0);
			tLine = (TextLine)lines.get(0);
		}
		else
		{
			tPoint = (DraggedPoint)points.get(points.size()-1);
			tLine = (TextLine)lines.get(lines.size()-1);
		}


		if (tLine.isVertical())
		{
			tPoint.setLocation(dragger.getX()+dragger.getWidth()/2-tPoint.getWidth()/2, tPoint.getY());
		}
		else
		{

			tPoint.setLocation(tPoint.getX(), dragger.getY()+dragger.getHeight()/2-tPoint.getHeight()/2);
		}

	}


	private void updateDragger4Point(AroundDragger dragger)
	{
		DraggedPoint tPoint;
		TextLine tLine;

		if (dragger == sourceDragger)
		{
			tPoint = (DraggedPoint)points.get(0);
			tLine = (TextLine)lines.get(0);
		}
		else
		{
			tPoint = (DraggedPoint)points.get(points.size()-1);
			tLine = (TextLine)lines.get(lines.size()-1);
		}

		Connectable edge = dragger.getEdge();

		double param;
		if (tLine.isVertical())
		{
			param = (double)(tPoint.getX()+tPoint.getWidth()/2-edge.getX())/(double)edge.getActualWidth();
		}
		else
		{
			param = (double)(tPoint.getY()+tPoint.getHeight()/2-edge.getY())/(double)edge.getActualHeight();
		}

		dragger.setParam(param);
		dragger.updateDragger();
	}



	private void updatePoint(int pointToChangeIndex, int originIndex)
	{
		TextLine tLine = (TextLine)lines.get(Math.min(pointToChangeIndex,pointToChangeIndex));
		DraggedPoint tPoint = (DraggedPoint)points.get(pointToChangeIndex);
		DraggedPoint origPoint = (DraggedPoint)points.get(originIndex);

		if (tLine.isVertical())
		{
			tPoint.setLocation(origPoint.getX(), tPoint.getY());
		}
		else
		{
			tPoint.setLocation(tPoint.getX(), origPoint.getY());
		}
	}



	public void updateRelease(Object origin)
	{
		removeRedundantLines();
		if (style != INVOCATION)
		{
			setUpperText(upperText);
		}
	}

	public void removeRedundantLines()
	{
		for (int i=0; i<points.size();i++)
		{
			DraggedPoint tPoint = (DraggedPoint)points.get(i);
			if (onLine(tPoint))
			{
				removePoint(tPoint);
			}
		}
	}


	public void removePoint(int index)
	{
		if (index < 0 || index >= points.size())
		{
			return;
		}

		DraggedPoint removedPoint = (DraggedPoint)points.get(index);
		TextLine removedLine = (TextLine)lines.get(index+1);

		TextLine tLine = (TextLine)lines.get(index);

		if (index == points.size() - 1)
		{
			destinationDragger.setLine((TextLine)lines.get(index));
			tLine.setEdge2(destinationDragger);
		}
		else
		{
			DraggedPoint tPoint = (DraggedPoint)points.get(index+1);
			tLine.setEdge2(tPoint);
		}

		cn.remove(removedPoint);
		cn.remove(removedLine);

		tLine.update();

		points.remove(index);
		lines.remove(index+1);

	}

	public void removePoint(DraggedPoint point)
	{
		removePoint(points.indexOf(point));
	}

	private boolean onLine(DraggedPoint point)
	{
		int index = points.indexOf(point);
		if (index == -1)
		{
			return false;
		}

		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();

		int lineSensitivity = (int)Math.round(LINE_SENSITIVITY*(currentSize/normalSize));

		DoublePoint myPoint = new DoublePoint(point.getX()+point.getWidth()/2, point.getY()+point.getHeight()/2);
		DoublePoint src,dest;
		DraggedPoint tPoint;
		if (index==0)
		{
			src = new DoublePoint(sourceDragger.getX()+sourceDragger.getWidth()/2, sourceDragger.getY()+sourceDragger.getHeight()/2);
		}
		else
		{
			tPoint = (DraggedPoint)points.get(index-1);
			src = new DoublePoint(tPoint.getX()+tPoint.getWidth()/2, tPoint.getY()+tPoint.getHeight()/2);
		}

		if (index==points.size()-1)
		{
			dest = new DoublePoint(destinationDragger.getX()+destinationDragger.getWidth()/2, destinationDragger.getY()+destinationDragger.getHeight()/2);
		}
		else
		{
			tPoint = (DraggedPoint)points.get(index+1);
			dest = new DoublePoint(tPoint.getX()+tPoint.getWidth()/2, tPoint.getY()+tPoint.getHeight()/2);
		}



		if (src.getY() > dest.getY())
		{
			DoublePoint temp = src;
			src = dest;
			dest = temp;
		}


		double deltaX = src.getX()-dest.getX();
		if (deltaX == 0.0)
		{
			deltaX = 0.0001;
		}

		double angle = Math.atan((double)(src.getY()-dest.getY())/deltaX);


		if(angle < 0)
		{
			angle += Math.PI;
		}
		myPoint.rotate(-angle, src);
		dest.rotate(-angle, src);
		if( (myPoint.getX() >= src.getX()-lineSensitivity) && (myPoint.getX() <= dest.getX()+lineSensitivity) &&
			(myPoint.getY() <= src.getY()+lineSensitivity) && (myPoint.getY() >= src.getY()-lineSensitivity))
		{
			//System.err.println("Contains "+this);
			return true;
		}
		return false;


	}

	public void setLineColor(Color clr)
	{
        for (int i=0; i<lines.size();i++)
        {
            ((OpdLine)lines.get(i)).setLineColor(clr);
        }

        sourceDragger.setBorderColor(clr);
        destinationDragger.setBorderColor(clr);
	}


	public Color getLineColor()
	{
        return ((OpdLine)lines.get(0)).getLineColor();
	}

	public void setTextColor(Color clr)
	{
		for (int i=0; i<lines.size();i++)
		{
			((OpdLine)lines.get(i)).setTextColor(clr);
		}

		if (sourceDragger instanceof OpdRelationDragger)
		{
			((OpdRelationDragger)sourceDragger).getOpdCardinalityLabel().setTextColor(clr);
		}

		if (destinationDragger instanceof OpdRelationDragger)
		{
			((OpdRelationDragger)destinationDragger).getOpdCardinalityLabel().setTextColor(clr);
		}
	}


	public Color getTextColor()
	{
		return ((OpdLine)lines.get(0)).getTextColor();
	}

	public void setDashed(boolean isDashed)
	{
		for (int i=0; i<lines.size();i++)
		{
			((OpdLine)lines.get(i)).setDashed(isDashed);
		}

	}

	public void setUpperText(String text)
	{
		if (style == INVOCATION)
		{
			return;
		}

		upperText = text;
		for (int i=0; i<lines.size();i++)
		{
		   TextLine line = (TextLine)lines.get(i);
		   line.setUpperText("");
		}

		TextLine line = (TextLine)lines.get(getLongestLine());
		line.setUpperText(text);
		repaint();
	}

	public void setLowerText(String text)
	{
		if (style == INVOCATION)
		{
			return;
		}

		lowerText = text;
		for (int i=0; i<lines.size();i++)
		{
		   TextLine line = (TextLine)lines.get(i);
		   line.setLowerText("");
		}

		TextLine line = (TextLine)lines.get(getLongestLine());
		line.setLowerText(text);
		repaint();

	}


	public void repaint()
	{
		for (int i=0; i<lines.size();i++)
		{
			((OpdLine)lines.get(i)).repaint();
		}

        sourceDragger.repaint();
        destinationDragger.repaint();
	}

	public void callPropertiesDialog()
	{
		((OpdLine)lines.get(0)).callPropertiesDialog();
	}

	private int getLongestLine()
	{
		int index = 0;
		TextLine line = (TextLine)lines.get(0);
		double length = Math.sqrt(line.getWidth()*line.getWidth()+line.getHeight()*line.getHeight());

		for (int i=1; i<lines.size(); i++)
		{
			line = (TextLine)lines.get(i);
			double tLength = Math.sqrt(line.getWidth()*line.getWidth()+line.getHeight()*line.getHeight());
			if (tLength > length)
			{
				index = i;
				length = tLength;
			}
		}

		return index;
	}

	private int getTotalLength()
	{
		double totalLength = 0;
		for (int i=0; i<lines.size(); i++)
		{
			TextLine line = (TextLine)lines.get(i);
			totalLength += Math.sqrt(line.getWidth()*line.getWidth()+line.getHeight()*line.getHeight());
		}

		return (int)totalLength;
	}


	private void setNumOfPoints(int num)
	{
		if (num == points.size())
		{
			return;
		}

		if (num < points.size())
		{
			for (int i=points.size()-1; i>num-1; i--)
			{
				removePoint(i);
			}

			return;
		}


		for (int i=points.size()-1;i<num-1; i++)
		{
			TextLine lastLine = (TextLine)lines.get(lines.size()-1);
			breakLastLine(new Point(lastLine.getX()+lastLine.getWidth()/2, lastLine.getY()+lastLine.getHeight()/2));
		}
	}


	private void orthogonalArrange()
	{
		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;

		double pointSize = (8.0/2.0)*factor;

		setNumOfPoints(1);

		int x = (int)Math.round(sourceDragger.getX()+ (double)sourceDragger.getWidth()/2-pointSize);
		int y = (int)Math.round(destinationDragger.getY()+(double)destinationDragger.getHeight()/2-pointSize);

		((DraggedPoint)points.get(0)).setLocation(x,y);
		((TextLine)lines.get(0)).setVertical(true);
		((TextLine)lines.get(1)).setVertical(false);
	}

//    private void orthogonalArrange()
//    {
//		Configuration config = myProject.getConfiguration();
//		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
//		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
//        double factor = currentSize/normalSize;
//
//        int pointSize = (int)Math.round((8.0/2.0)*factor);
//        int bypassDistance = BYPASS_DISTANCE*factor;
//
//        int sX = source.getX() + source.getWidth()/2 - pointSize;
//        int sY = source.getY() + source.getHeight()/2 - pointSize;
//        int dX = destination.getX() + destination.getWidth()/2 - pointSize;
//        int dY = destination.getY() + destination.getHeight()/2 - pointSize;
//
//        int draggerSide = getSide(source);
//
//        Connectable sEdge = source.getEdge();
//
//
//          if (dX < sX && dY > sY) //1
//          {
//            if (draggerSide == BaseGraphicComponent.N_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY-BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.E_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY);
//              points[1].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY+(dY-sY)/2);
//              points[2].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY+(dY-sY)/2);
//              points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.S_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[1].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[2].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.W_BORDER)
//            {
//              points[0].setAbsolutesLocation(dX, sY);
//              points[1].setAbsolutesLocation(dX, sY);
//              points[2].setAbsolutesLocation(dX, sY);
//              points[3].setAbsolutesLocation(dX, sY);
//              return;
//            }
//          }
//
//          if (dX > sX && dY > sY)  //2
//          {
//            if (draggerSide == BaseGraphicComponent.N_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY-BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, sY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.E_BORDER)
//            {
//              points[0].setAbsolutesLocation(dX, sY);
//              points[1].setAbsolutesLocation(dX, sY);
//              points[2].setAbsolutesLocation(dX, sY);
//              points[3].setAbsolutesLocation(dX, sY);
//              return;
//
//            }
//
//            if (draggerSide == BaseGraphicComponent.S_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[1].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[2].setAbsolutesLocation(sX, sY+(dY-sY)/2);
//              points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.W_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY);
//              points[1].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY+(dY-sY)/2);
//              points[2].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY+(dY-sY)/2);
//              points[3].setAbsolutesLocation(dX, sY+(dY-sY)/2);
//              return;
//            }
//          }
//
//
//          if (dX > sX && dY < sY)  //3
//          {
//            if (draggerSide == BaseGraphicComponent.N_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, dY-BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.E_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX+(dX-sX)/2, sY);
//              points[1].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY - BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.S_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY+BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(sX+(dX-sX)/2, sY+BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.W_BORDER)
//            {
//
//              points[0].setAbsolutesLocation(sX-BYPASS_DISTANCE, sY);
//              points[1].setAbsolutesLocation(sX-BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX-BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//
//            }
//          }
//
//
//          if (dX < sX && dY < sY)  //4
//          {
//            if (draggerSide == BaseGraphicComponent.N_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, dY-BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.E_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX+BYPASS_DISTANCE, sY);
//              points[1].setAbsolutesLocation(sX+BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX+BYPASS_DISTANCE, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.S_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX, sY+BYPASS_DISTANCE);
//              points[1].setAbsolutesLocation(sX+(dX-sX)/2, sY+BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY-BYPASS_DISTANCE);
//              return;
//            }
//
//            if (draggerSide == BaseGraphicComponent.W_BORDER)
//            {
//              points[0].setAbsolutesLocation(sX+(dX-sX)/2, sY);
//              points[1].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[2].setAbsolutesLocation(sX+(dX-sX)/2, dY-BYPASS_DISTANCE);
//              points[3].setAbsolutesLocation(dX, dY - BYPASS_DISTANCE);
//              return;
//            }
//          }
//    }

	private int getSide(AroundDragger dragger)
	{
		if (dragger.getEdge() instanceof OpdProcess)
		{
			double param = dragger.getParam();

			if (param <= 0.2)
			{
				return OpcatConstants.W_BORDER;
			}

			if (param >= 0.8)
			{
				return OpcatConstants.E_BORDER;
			}
		}

		return dragger.getSide();
	}

	public void setVisible(boolean isVisible)
	{
		for(int i = 0; i < lines.size(); i++)
		{
			((OpdLine)lines.elementAt(i)).setVisible(isVisible);
		}
	}

	public BaseGraphicComponent[] getPointsArray()
	{
		BaseGraphicComponent[] bgcs = new BaseGraphicComponent[points.size()];
		for(int i = 0; i < points.size(); i++)
		{
			bgcs[i] = (BaseGraphicComponent)points.get(i);
		}
		return bgcs;
	}

	public synchronized void animateAsFlash()
	{
		if (animated)
        {
            return;
        }

        animated = true;
        animatedAsFlash = true;

        originalColor = getLineColor();

        setLineColor(Color.red);
        repaint();
        animationTimer = new javax.swing.Timer(500, stopFlash);
        animationTimer.setInitialDelay(500);
        animationTimer.setRepeats(false);

        animationTimer.start();

//        try
//        {
//            Thread.currentThread().sleep(350);
//        }
//        catch (Exception e){}
//        setLineColor(originalColor);
	}

    public synchronized boolean isAnimated()
    {
        return animated;
    }


    public synchronized void pauseAnimaition()
    {
        if (!animated || paused)
        {
            return;
        }

        paused = true;
        animationTimer.stop();
        performedTime = (new GregorianCalendar()).getTime().getTime() - startTime;
    }

    public synchronized void continueAnimaition()
    {
        if (!animated || !paused)
        {
            return;
        }

        startTime = (new GregorianCalendar()).getTime().getTime() - performedTime;
        animationTimer.start();
        paused = false;
    }

	public synchronized void animate(long time)
	{
        if (animated)
        {
            if (time == 0)
            {
                totalTime = 0;
            }
            return;
        }

		if (time < (long)(1000/FRAMES_PER_SECOND))
		{
			return;
		}

        animated = true;

		if (style == INVOCATION)
		{
			((OpdInvocationLine)lines.get(0)).setAnimated(true);;
			((OpdInvocationLine)lines.get(0)).repaint();
			animationTimer = new javax.swing.Timer((int)time, stAnimation);
			animationTimer.setInitialDelay((int)time);
			animationTimer.setRepeats(false);
			animationTimer.start();
			return;
		}

		animationTimer = new javax.swing.Timer((int)(1000/FRAMES_PER_SECOND), updateAnimation);
		startTime = (new GregorianCalendar()).getTime().getTime();
		totalTime = time;
		animationTimer.start();
	}

    public synchronized void animate(long animationTime, long remainedTime)
    {
        if (animated)
        {
            if (animationTime == 0)
            {
                totalTime = 0;
            }
            return;
        }


        if (animationTime < (long)(1000/FRAMES_PER_SECOND))
        {
            return;
        }

        animated = true;

        if (style == INVOCATION)
        {
            ((OpdInvocationLine)lines.get(0)).setAnimated(true);;
            ((OpdInvocationLine)lines.get(0)).repaint();
            animationTimer = new javax.swing.Timer((int)remainedTime, stAnimation);
            animationTimer.setInitialDelay((int)remainedTime);
            animationTimer.setRepeats(false);
            animationTimer.start();
            return;
        }

        animationTimer = new javax.swing.Timer((int)(1000/FRAMES_PER_SECOND), updateAnimation);
        totalTime = animationTime;
        startTime = (new GregorianCalendar()).getTime().getTime() - (totalTime - remainedTime);
        animationTimer.start();
    }

    public synchronized long getRemainedAnimationTime()
    {
        if (!animated || animatedAsFlash)
        {
            return 0;
        }

        if (paused)
        {
            return totalTime - performedTime;
        }

        return totalTime + startTime - (new GregorianCalendar()).getTime().getTime();
    }

    public synchronized long getTotalAnimationTime()
    {
        if (!animated || animatedAsFlash)
        {
            return 0;
        }

        return totalTime;
    }

    public synchronized void stopAnimation()
    {
        if (!animated)
        {
            return;
        }

        if (paused)
        {
            paused = false;
        }

        if (animatedAsFlash)
        {
            setLineColor(originalColor);
            repaint();
            animated = false;
            animatedAsFlash = false;
        }
        else
        {
            _stpAnimation();
        }
    }

	private void _stpAnimation()
	{
        if (!animated)
        {
            return;
        }

        if (paused)
        {
            paused = false;
        }

        animationTimer.stop();

		if (style == INVOCATION)
		{
			((OpdInvocationLine)lines.get(0)).setAnimated(false);
			((OpdInvocationLine)lines.get(0)).repaint();
            animated = false;
			return;
		}

		for (int i=0; i<lines.size(); i++)
		{
			TextLine line = (TextLine)lines.get(i);
			if (line.isAnimated())
			{
				line.setAnimated(false);
				line.repaint();
			}
		}

        animated = false;

	}

	Action updateAnimation = new AbstractAction(){
		public synchronized void actionPerformed(java.awt.event.ActionEvent e){
            long timeLeft = totalTime + startTime - (new GregorianCalendar()).getTime().getTime();

            if (timeLeft <= 0)
            {
                _stpAnimation();
                return;
            }

			double totalLength = getTotalLength();
			double currLength = totalLength*(double)(totalTime - timeLeft)/(double)totalTime;

//			timeLeft = timeLeft - animationTimer.getDelay();
			TextLine tl = (TextLine)lines.get(0);
			double aParam = 0.0;

			double cLength = 0;
			for (int i=0; i<lines.size(); i++)
			{
				TextLine line = (TextLine)lines.get(i);
				double myLength = Math.sqrt(line.getWidth()*line.getWidth()+line.getHeight()*line.getHeight());
				cLength += myLength;
				if (currLength <= cLength)
				{
					tl = line;
					aParam = 1.0 - (cLength-currLength)/myLength;
					break;
				}
				else
				{
					if (line.isAnimated())
					{
						line.setAnimated(false);
						line.repaint();
					}
				}
			}

			tl.setAnimated(true);
			tl.setAnimationParametr(aParam);
			tl.repaint();

		}
	};

	Action stAnimation = new AbstractAction(){
		public synchronized void actionPerformed(java.awt.event.ActionEvent e){
			_stpAnimation();
		}
	};

	Action stopFlash = new AbstractAction(){
		public synchronized void actionPerformed(java.awt.event.ActionEvent e){
            setLineColor(originalColor);
            repaint();
            animated = false;
            animatedAsFlash = false;
		}
	};

}
