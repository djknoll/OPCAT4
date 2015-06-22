/*
 *  opcat2
 *  package: opdGraphics
 *  file:    AroundDragger.java
 */


package gui.opdGraphics.draggers;
import exportedAPI.OpcatConstants;
import exportedAPI.RelativeConnectionPoint;
import extensionTools.hio.CursorFactory;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DoublePoint;
import gui.opdGraphics.DrawingArea;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.MoveUpdatable;
import gui.opdGraphics.lines.OpdLine;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.opdGraphics.opdBaseComponents.OpdConnectionEdge;
import gui.opdGraphics.opdBaseComponents.OpdProcess;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *  <p>Abstract class AroundDragger is a superclass for all classes which represents
 *  items that can be dragged around Objects, Processes or States in OPD.
 *  Actually this class provides dragging functionality for edges of all kinds
 *  of OPD links and relations.</p>
 */

public abstract class AroundDragger extends JComponent implements MouseListener, MouseMotionListener, Connectable
{

/**
 *  OPD thing that this dragger connected to
 */
	protected static final double SELECTION_OFFSET = 3.2;
	protected Connectable edge;
	protected Connectable originalEdge;
	protected OpdProject myProject;

/**
 *  The line that this dragger connects to <code>edge</code>
 */
	protected OpdLine line;

/**
 *  Side of the OPD thing tis dragger connected to
 *  @see <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a> for possible values
 */
	protected RelativeConnectionPoint cPoint;
	protected RelativeConnectionPoint originalPoint;
	private int tempX;
	private int tempY;


	protected Color backgroundColor;
	protected Color textColor;
	protected Color borderColor;

/**
 *  Shift in X coordinate when dragger is inside one of the OPD graphic components, not in <a href = "DrawingArea.html"><code>DrawingArea</code></a>
 */

	protected int shiftX;  // shifts for connection to state (state not in Drawing
									// area like others but inside some Object)
/**
 *  Shift in Y coordinate when dragger is inside one of the OPD graphic components, not in <a href = "DrawingArea.html"><code>DrawingArea</code></a>
 */
	protected int shiftY;
	private boolean canLeave;
	protected boolean moveable;

	private Container dArea;

	private boolean selected;
	Hashtable updateListeners;
	protected boolean wasDragged;

/**
 *  <strong>There is no default constructor</strong>
 *  <p>All extending classes have to call <code>super()</code> in their constructors.
 *  @param <code>pEdge1</code> -- OPD graphic component this dragger connected to.
 *  @param <code>pSide1</code> -- The side OPD graphic component this dragger connected to.
 *  @param <code>pParam1</code> -- The relation of coordinates of a <code>mouseClick</code> event to length of the side.
 */
	public AroundDragger(Connectable pEdge1, RelativeConnectionPoint cPoint, OpdProject project)

	{
		myProject = project;
		edge = pEdge1;
		this.cPoint = cPoint;
		backgroundColor = new Color(230, 230, 230);
		textColor = Color.black;
		borderColor = Color.black;

		_countShift();
		selected = false;
		updateListeners = new Hashtable();
		originalPoint = new RelativeConnectionPoint(cPoint.getSide(),cPoint.getParam());
		canLeave = false;
		moveable = true;
	}

/**
 * @return background color of the dragger
 */


	public boolean isCanLeave()
	{
		return canLeave;
	}

	public void setCanLeave(boolean canLeave)
	{
		this.canLeave = canLeave;
	}

	public boolean isMoveable()
	{
		return moveable;
	}

	public void setMoveable(boolean moveable)
	{
		this.moveable = moveable;
	}

	  public void addUpdateListener(MoveUpdatable ls)
	  {
		updateListeners.put(ls, ls);
	  }

	  public void removeUpdateListener(MoveUpdatable ls)
	  {
		updateListeners.remove(ls);
	  }



	  public void setRelativeConnectionPoint(RelativeConnectionPoint cPoint)
	  {
		this.cPoint = cPoint;
	  }

	public RelativeConnectionPoint getRelativeConnectionPoint()
	{
		return cPoint;
	}
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

/**
 *  Sets background color of the dragger
 *  @param bColor -- new background color
 */
	public void setBackgroundColor(Color bColor)
	{
		backgroundColor = bColor;
	}

/**
 * @return text color of the dragger
 */
	public Color getTextColor()
	{
		return textColor;
	}

/**
 *  Sets text color of the dragger
 *  @param tColor -- new text color
 */
	public void setTextColor(Color tColor)
	{
		textColor = tColor;
	}

/**
 * @return border color of the dragger
 */
	public Color getBorderColor()
	{
		return borderColor;
	}

/**
 *  Sets border color of the dragger
 *  @param bColor -- new boreder color
 */
	public void setBorderColor(Color bColor)
	{
		borderColor = bColor;
	}

/**
 * Sets the line that this dragger connects to <code>edge</code>
 */

	public void setLine(OpdLine pLine)
	{
		line = pLine;
	}

	private void _countShift()
	{

		dArea = ((OpdConnectionEdge)edge).getParent();

		while (!(dArea instanceof DrawingArea))
		{
			dArea = dArea.getParent();
		}


		if ( ((OpdConnectionEdge)edge).getParent() == getParent())
		{
			shiftX = 0;
			shiftY = 0;
		}
		else
		{

			Point shiftPoint;

			shiftX = ((OpdConnectionEdge)edge).getParent().getX();
			shiftY = ((OpdConnectionEdge)edge).getParent().getY();

			shiftPoint = SwingUtilities.convertPoint(
							((OpdConnectionEdge)edge).getParent().getParent(),
							shiftX, shiftY, dArea);

			shiftX = (int)shiftPoint.getX();
			shiftY = (int)shiftPoint.getY();

		}
	}

/**
 *  Recalculates dragger position repaints it and repaints the line this dragger connected to.
 */
	public synchronized void updateDragger()
	{
		Point p1;

		_countShift();
		p1 = edge.getAbsoluteConnectionPoint(cPoint);
		setLocation(shiftX+edge.getX()+(int)(p1.getX() - getWidth()/2) ,shiftY+edge.getY()+(int)(p1.getY())-getHeight()/2);

		if (line != null)
		{
			line.update();
			updateNeighbor();
		}


	}

	protected void updateListeners()
	{
		for (Enumeration e = updateListeners.elements();e.hasMoreElements();)
		{
			MoveUpdatable mu = (MoveUpdatable)e.nextElement();
			mu.updateMove(this);
//            ((MoveUpdatable)e.nextElement()).updateMove();
		}

	}

/**
 *  <code>mouseClicked</code> event handler
 */
	public void mouseClicked(MouseEvent e)
	{
		if (StateMachine.isAnimated() || StateMachine.isWaiting())
		{
			return;
		}

		if (SwingUtilities.isRightMouseButton(e))
		{
			return;
		}

		if (e.getClickCount() == 2)
		{
			callPropertiesDialog();
		}
	}

/**
 *  <code>mouseDragged</code> event handler
 */
	public void mouseDragged(MouseEvent e)
	{
		if (StateMachine.isAnimated() || StateMachine.isWaiting())
		{
			return;
		}

		if (SwingUtilities.isRightMouseButton(e) || !moveable)
		{
			return;
		}

		wasDragged = true;

		Point convPoint;
		convPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), (JComponent)edge);

		if (canLeave && !edge.isInAdjacentArea((int)convPoint.getX(), (int)convPoint.getY()))
		{
			tempX=getWidth()/2;
			tempY=getHeight()/2;

			setLocation(getX()+e.getX()-getWidth()/2, getY()+e.getY()-getHeight()/2);

			if (line != null)
			{
				line.update();
				updateNeighbor();
			}

			convPoint = SwingUtilities.convertPoint(this, e.getX(), e.getY(), dArea);

			OpdConnectionEdge te = GraphicsUtils.findAdjacentComponent(dArea, (int)convPoint.getX(), (int)convPoint.getY());


			if (te != null)
			{
				edge = te;
			}
			else
			{
				edge = originalEdge;
			}

			_countShift();

			return;
		}


		if (edge instanceof OpdProcess)
		{
			_setPointForOval(e);
		}
		else
		{
			_setPointForRectangle(e);
		}

		if (edge == originalEdge)
		{
			originalPoint.setParam(cPoint.getParam());
			originalPoint.setSide(cPoint.getSide());
		}

		updateDragger();
		this.updateListeners();
	}


		private void _setPointForOval(MouseEvent e)
		{
			int eWidth  = edge.getActualWidth();
			int eHeight = edge.getActualHeight();
			cPoint.setParam((double)(getX()+getWidth()/2+e.getX()-tempX-edge.getX()-shiftX) / (double)(eWidth));

			if ( e.getY() + getHeight()/2 + getY() - tempY - shiftY  < edge.getY() + eHeight/2)
			{
				cPoint.setSide(OpcatConstants.N_BORDER);
				return;
			}

			if ( e.getY() + getHeight()/2 + getY() -tempY - shiftY > edge.getY() + eHeight/2)
			{
				cPoint.setSide(OpcatConstants.S_BORDER);
				return;
			}

		}

	/**
	 *  According to place <code>MouseEvent e</code> occured desides if this drugger should jump to another side of the component it connected to,
	 *  and jupms to this side.
	 */


	private void _setPointForRectangle(MouseEvent e)
	{
		int eWidth  = edge.getActualWidth();
		int eHeight = edge.getActualHeight();

		GenericTable config = myProject.getConfiguration();
		Integer nSize = (Integer)config.getProperty("NormalSize");
		Integer cSize = (Integer)config.getProperty("CurrentSize");

		double proximity = 12.0*cSize.doubleValue()/nSize.doubleValue();

		double xLocation = e.getX() + getX() + getWidth()/2 - tempX - shiftX;
		double yLocation = e.getY() + getY() +getHeight()/2 - tempY - shiftY;



		if ( (int)xLocation <= edge.getX()+3+(int)proximity && (int)yLocation > edge.getY()
										&& (int)yLocation < edge.getY() + eHeight)
		{
			cPoint.setSide(OpcatConstants.W_BORDER);
			cPoint.setParam((yLocation - (double)edge.getY()) / (double)(eHeight));
			return;
		}

		if ( (int)xLocation >= edge.getX() + eWidth - 3- (int)proximity && (int)yLocation > edge.getY()
										&& (int)yLocation < edge.getY() + eHeight)
		{
			cPoint.setSide(OpcatConstants.E_BORDER);
			cPoint.setParam((yLocation - (double)edge.getY()) / (double)(eHeight));
			return;
		}

		if (((int)yLocation > edge.getY() + eHeight/2) && (e.getX() + getX() - shiftX < edge.getX() + eWidth) && (xLocation  > edge.getX()))
		{
			cPoint.setSide(OpcatConstants.S_BORDER);
			cPoint.setParam((xLocation - (double)edge.getX()) / (double)(eWidth));
			return;
		}

		if (((int)yLocation < edge.getY() + eHeight/2) && (e.getX() + getX() - shiftX < edge.getX() + eWidth) && (xLocation > edge.getX()))
		{
			cPoint.setSide(OpcatConstants.N_BORDER);
			cPoint.setParam((xLocation - (double)edge.getX()) / (double)(eWidth));
			return;
		}

	}

/**
 *  <code>mouseMoved</code> event handler
 *  Empty implementation for adapting
 */
	public void mouseMoved(MouseEvent e){}

/**
 *  <code>mousePressed</code> event handler
 */
	public void mousePressed(MouseEvent e)
	{
		if (StateMachine.isAnimated() || StateMachine.isWaiting())
		{
			return;
		}

		if (!moveable)
		{
			return;
		}
		_countShift();
		tempX=e.getX();
		tempY=e.getY();
		originalEdge = edge;
		wasDragged = false;
	}

/**
 *  <code>mouseReleased</code> event handler
 *  Empty implementation for adapting
 */
	public void mouseReleased(MouseEvent e)
	{
		if (StateMachine.isAnimated() || StateMachine.isWaiting())
		{
			return;
		}

		if(e.isPopupTrigger())
		{
			showPopupMenu(e.getX(), e.getY());
		}

		if (!moveable)
		{
			return;
		}
		else
		{
			updateDragger();
			this.updateListeners();
		}

	}

/**
 *  <code>mouseEntered</code> event handler
 */
public void mouseEntered(MouseEvent e)
{
  if (StateMachine.isWaiting() || StateMachine.isAnimated())
  {
	return;
  }

  setCursor(new Cursor(Cursor.MOVE_CURSOR));
  /** ***********************HIOTeam************** */
  if (StateMachine.getCurrentState() == StateMachine.USUAL_DRAW) {
  	setCursor(CursorFactory.getDrawCursor());
  }
  /*************************HIOTeam***************/

}

/**
 *  <code>mouseExited</code> event handler<br>
 *  Empty implementation for adapting
 */
	public void mouseExited(MouseEvent e){}

/**
 *  Calls properties dialog box<br>
 *  Empty implementation for adapting
 */
	public abstract void callPropertiesDialog();

/**
 *  @return Point that this dragger connected to in coordinates of dragger
 */
	public  Point getAbsoluteConnectionPoint(RelativeConnectionPoint rPoint)
	{
		Point returnedPoint = new Point(0,0);

		switch (rPoint.getSide())
		{
			case OpcatConstants.N_BORDER:
				returnedPoint.setLocation(getWidth()*rPoint.getParam(), 0);
				break;
			case OpcatConstants.S_BORDER:
				returnedPoint.setLocation(getWidth()*rPoint.getParam(), getHeight());
				break;
			case OpcatConstants.E_BORDER:
				returnedPoint.setLocation(getWidth(), getHeight()*rPoint.getParam());
				break;
			case OpcatConstants.W_BORDER:
				returnedPoint.setLocation(0, getHeight()*rPoint.getParam());
				break;
			case OpdBaseComponent.CENTER:
				returnedPoint.setLocation(getWidth()/2, getHeight()/2);
				break;
		}

				return returnedPoint;
	}

	public abstract void paintComponent(Graphics g);

/**
 *  Updates the dragger on the other edge of the line
 */
	private void updateNeighbor()
	{
		if (line == null) return;

		Connectable neighbor = line.getNeighbor(this);

		if (neighbor instanceof AroundDragger)
		{
				  ((AroundDragger)neighbor).repaint();
		}
	}

/**
 *  @return OPD graphic component this dragger connected to
 */
	public Connectable getEdge()
	{
		return edge;
	}

/**
 *  @return The relation of coordinates of a <code>mouseClick</code> event to length of the side.
 */
	public double getParam()
	{
		return cPoint.getParam();
	}

	public void setParam(double param)
	{
		cPoint.setParam(param);
	}

/**
 *  @return The side OPD graphic component this dragger connected to.
 */
	public int getSide()
	{
		return cPoint.getSide();
	}

	public void setSide(int side)
	{
		cPoint.setSide(side);
	}

	public double getRotationAngle()
	{
//		Point p, head, tail;
//
//		p = line.getUpperPoint();
//		if ( (line.getX() + p.getX()) >= getX() && (line.getX() + p.getX()) <= (getX() + getWidth()) &&
//			(line.getY() + p.getY()) >= getY() && (line.getY() + p.getY()) <= (getY() + getHeight()) )
//		{
//			head = p;
//			tail = line.getLowerPoint();
//		}
//		else
//		{
//			tail = p;
//			head = line.getLowerPoint();
//		}
//
//		return GraphicsUtils.calcutateRotationAngle(head, tail);

		return line.getRotationAngle(this);
	}

	protected Graphics2D getRotatedGraphics2D(Graphics g)
	{
		double angle = getRotationAngle();

		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);

		g2.rotate(angle, getWidth()/2, getHeight()/2);

		return g2;
	}

		public int getActualWidth()
		{
		  return getWidth();
		}

	public int getActualHeight()
	{
		return getHeight();
	}

	public boolean contains(int x, int y)
	{
		if(line == null)
		{
			return false;
		}
		double angle = getRotationAngle();
		DoublePoint center = new DoublePoint(getWidth()/2, getWidth()/4);
		center.rotate(angle, new Point(getWidth()/2, getHeight()/2));
		double click = (x-center.getX())*(x-center.getX())+(y-center.getY())*(y-center.getY());
		if(click < getWidth()*getWidth()/4)
		{
			return true;
		}
		return false;
	}

	/**
	 * Sets the component state. Sets <code>OpdProject.setSystemChangeProbability</code>
	 * to true. If a component was selected, there is high probability that something
	 * in the system was changed.
	 * @see OpdProject#setSystemChangeProbability
	 * */
	public void setSelected(boolean newSelected)
	{
		selected = newSelected;
	}

	public boolean isSelected()
	{
		return selected;
	}

	protected void drawSelection(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		Stroke oldStroke = g2.getStroke();

		g2.setStroke(new BasicStroke());

		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;
		int selOffset = Math.round((float)(SELECTION_OFFSET*factor));
		int dSelOffset = 2*selOffset;

		g2.setColor(Color.white);
		g2.fillRect(getWidth()/2-selOffset, getHeight()/2-selOffset, dSelOffset, dSelOffset);
		g2.setColor(Color.black);
		g2.drawRect(getWidth()/2-selOffset, getHeight()/2-selOffset, dSelOffset, dSelOffset);

		g2.setStroke(oldStroke);
	}

	public boolean isInAdjacentArea(int x, int y)
	{
		return contains(x,y);
	}


	public abstract void showPopupMenu(int pX, int pY);

}
