package gui.opdGraphics.lines;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DoublePoint;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.dialogs.LinkPropertiesDialog;
import gui.opdGraphics.popups.LinkPopup;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmEntity;
import gui.opmEntities.OpmProceduralLink;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


/**
 *  <p><a href = "OpdInvocationLink.html"><code>OpdInvocationLink</code></a> has unique
 *  graphic representation, it has not only marks at the edges of line, it has unique
 *  line. Thus <code>OpdInvocationLine</code> should be implemented.</p>
 *  This class implements the line of <a href = "OpdInvocationLink.html"><code>OpdInvocationLink</code></a>.
 */
public class OpdInvocationLine extends OpdLine
{
	int shiftComponent;
	private static double ZIGZAG = 10.0;
	private boolean shiftedX = false;
	private boolean shiftedY = false;
	private boolean animated;

	/**
	 *  Constructs an <code>OpdInvocationLine</code> component.
	 *  @param <code>pEdge1 Connectable</code>, one edge of <a href = "OpdInvocationLink.html"><code>OpdInvocationLink</code></a> a subclass of <a href = "AroundDragger.html"><code>AroundDragger</code></a>.
	 *  @param <code>pSide1 int</code>, the border of first edge as defined in <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a>.
	 *  @param <code>pParam1 double</code>, connection parameter for positioning the connection point on the component's border.
	 *  @param <code>pEdge2 Connectable</code>, second edge of <a href = "OpdInvocationLink.html"><code>OpdInvocationLink</code></a> a subclass of <a href = "AroundDragger.html"><code>AroundDragger</code></a>.
	 *  @param <code>pSide2 int</code>, the border of first edge as defined in <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a>.
	 *  @param <code>pParam2 double</code>, connection parameter for positioning the connection point on the component's border.
	 */
	public OpdInvocationLine(Connectable pEdge1, RelativeConnectionPoint cPoint1,
				   Connectable pEdge2, RelativeConnectionPoint cPoint2,
								   OpmEntity pEntity, OpdKey key,
								   OpdProject pProject)
	{
		super(pEdge1, cPoint1, pEdge2, cPoint2, pEntity, key ,pProject);
		//shiftLine = 10;
		shiftComponent = 20;
	}

	/**
	 *  updates line, used to avoid infinite loop created by calling <code>repaint()</code> function by
	 *  components trying to update each other, when they are neighbors, and moving or resizing of one affects another.
	 */
	public synchronized void update()
	{
		Point p1, p2;
		int myWidth, myHeight, myX, myY;

		Container parent = getParent();
		if (parent == null) return;

		p1 = edge1.getAbsoluteConnectionPoint(cPoint1);
		p2 = edge2.getAbsoluteConnectionPoint(cPoint2);

		p1 = SwingUtilities.convertPoint((Container)edge1, p1, parent);
		p2 = SwingUtilities.convertPoint((Container)edge2, p2, parent);


		myX = (int)Math.min(p1.getX(), p2.getX());
		myY = (int)Math.min(p1.getY(), p2.getY());

		myWidth = (int)Math.abs(p1.getX()-p2.getX());
		myHeight = (int)Math.abs(p1.getY()-p2.getY());

		if (myWidth <= shiftComponent)
		{
			myX = myX-10;
			myWidth = myWidth+shiftComponent;
		}

		if (myHeight <= shiftComponent)
		{
			myY = myY-10;
			myHeight = myHeight+shiftComponent;
		}
		setLocation(myX, myY);
		setSize(myWidth, myHeight);
	}

	public void setAnimated(boolean isAnimated)
	{
		animated = isAnimated;
	}

	public boolean isAnimated()
	{
		return animated;
	}

	public double getRotationAngle(Connectable ad)
	{
		int x1, y1, x2, y2;
		Point p1, p2;
		int myWidth, myHeight, myX, myY;

		Container parent = getParent();

		if (parent == null) return 0.0;

		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)myProject.getConfiguration().getProperty("LineFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;
		double zigzag = ZIGZAG*factor;

		p1 = edge1.getAbsoluteConnectionPoint(cPoint1);
		p2 = edge2.getAbsoluteConnectionPoint(cPoint2);

		p1 = SwingUtilities.convertPoint((Container)edge1, p1, parent);
		p2 = SwingUtilities.convertPoint((Container)edge2, p2, parent);


		myWidth = (int)Math.abs(p1.getX()-p2.getX());
		myHeight = (int)Math.abs(p1.getY()-p2.getY());

		if (myWidth <= shiftComponent)
		{
			shiftedX = true;

		}

		if (myHeight <= shiftComponent)
		{
			shiftedY = true;
		}

		/**
		 *
		 */
		x1 = (int)(p1.getX() - (getX()+ (getWidth()>-shiftComponent?0:10) ));
		y1 = (int)(p1.getY() - (getY()+ (getHeight()>-shiftComponent?0:10) ) );
		x2 = (int)(p2.getX() - (getX()+ (getWidth()>-shiftComponent?0:10) ) );
		y2 = (int)(p2.getY() - (getY()+ (getHeight()>-shiftComponent?0:10) ) );

		double zigzagAngle = Math.PI/3;
		if (x2 < x1)
		{
			zigzagAngle = Math.PI/3 + Math.PI;
		}


		double angle = Math.atan(((double)(y2 - y1))/((double)(x2 - x1)) ) + zigzagAngle;

		double middleX = Math.abs((double)(x2-x1))/2 + ( !(shiftedX) ?0:10);
		double middleY = Math.abs((double)(y2-y1))/2 + ( !(shiftedY) ?0:10) ;
		shiftedX = false;
		shiftedY = false;

		DoublePoint tmp1 = new DoublePoint(zigzag, 0.0);
		DoublePoint tmp2 = new DoublePoint(-zigzag, 0.0);

		tmp1.rotate(angle);
		tmp2.rotate(angle);

		Point headPoint = new Point((int)Math.round(middleX + tmp1.getX()), (int)Math.round(middleY + tmp1.getY()));
		Point tailPoint = new Point((int)Math.round(middleX + tmp2.getX()), (int)Math.round(middleY + tmp2.getY()));



		Point head, tail;

		if ( ad== edge1)
		{
			head = new Point(x1,y1);
			tail = headPoint;
		}
		else
		{
			head = new Point(x2, y2);
			tail = tailPoint;
		}

		return GraphicsUtils.calcutateRotationAngle(head, tail);
	}

	/**
	 *  paints the line.
	 */
	public void paintComponent(Graphics g)
	{
		int x1, y1, x2, y2;
		Point p1, p2;
		int myWidth, myHeight, myX, myY;

		Container parent = getParent();
		if (parent == null) return;

		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)myProject.getConfiguration().getProperty("LineFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;

		double zigzag = ZIGZAG*factor;



		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
		g2.setColor(lineColor);

		if (isDashed())
		{
			g2.setStroke(new BasicStroke((float)factor*1.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{6,4}, 0.0f));
		}
		else
		{
			g2.setStroke(new BasicStroke((float)factor*1.8f));
		}


		p1 = edge1.getAbsoluteConnectionPoint(cPoint1);
		p2 = edge2.getAbsoluteConnectionPoint(cPoint2);

		p1 = SwingUtilities.convertPoint((Container)edge1, p1, parent);
		p2 = SwingUtilities.convertPoint((Container)edge2, p2, parent);

		/**
		 *
		 */
		myX = (int)Math.min(p1.getX(), p2.getX());
		myY = (int)Math.min(p1.getY(), p2.getY());

		myWidth = (int)Math.abs(p1.getX()-p2.getX());
		myHeight = (int)Math.abs(p1.getY()-p2.getY());


		if (myWidth <= shiftComponent)
		{
			myX = myX-10;
			myWidth = myWidth+shiftComponent;
			shiftedX = true;
//			setLocation(myX, myY);
//			setSize(myWidth, myHeight);

		}

		if (myHeight <= shiftComponent)
		{
			myY = myY-10;
			myHeight = myHeight+shiftComponent;
			shiftedY = true;
//			setLocation(myX, myY);
//			setSize(myWidth, myHeight);
		}

		/**
		 *
		 */


		x1 = (int)(p1.getX() - (getX()+ (getWidth()>-shiftComponent?0:10) ));
		y1 = (int)(p1.getY() - (getY()+ (getHeight()>-shiftComponent?0:10) ) );
		x2 = (int)(p2.getX() - (getX()+ (getWidth()>-shiftComponent?0:10) ) );
		y2 = (int)(p2.getY() - (getY()+ (getHeight()>-shiftComponent?0:10) ) );

		double zigzagAngle = Math.PI/3;
		if (x2 < x1)
		{
			zigzagAngle = Math.PI/3 + Math.PI;
		}


		double angle = Math.atan( ((double)(y2 - y1))/((double)(x2 - x1)) ) + zigzagAngle;

		double middleX = Math.abs((double)(x2-x1))/2 + ( !(shiftedX) ?0:10);
		double middleY = Math.abs((double)(y2-y1))/2 + ( !(shiftedY) ?0:10) ;
		shiftedX = false;
		shiftedY = false;

//		DoublePoint tmp1 = new DoublePoint(10.0, 0.0);
//		DoublePoint tmp2 = new DoublePoint(-10.0, 0.0);
		DoublePoint tmp1 = new DoublePoint(zigzag, 0.0);
		DoublePoint tmp2 = new DoublePoint(-zigzag, 0.0);

		tmp1.rotate(angle);
		tmp2.rotate(angle);

		if (animated)
		{
			g2.setColor(Color.red);
		}

		g2.drawLine(x1, y1, (int)Math.round(middleX + tmp1.getX()), (int)Math.round(middleY + tmp1.getY()) );
		g2.drawLine((int)Math.round(middleX + tmp1.getX()), (int)Math.round(middleY + tmp1.getY()),
					(int)Math.round(middleX + tmp2.getX()), (int)Math.round(middleY + tmp2.getY()));

		g2.drawLine((int)Math.round(middleX + tmp2.getX()), (int)Math.round(middleY + tmp2.getY()), x2, y2);
	}



	public void callPropertiesDialog()
	{
		LinkPropertiesDialog ld = new LinkPropertiesDialog((OpmProceduralLink)myEntity, myKey, myProject, "Invocation");
		ld.show();
		return;
	}

	public void showPopupMenu(int x, int y)
	{
		JPopupMenu jpm = null;

		jpm = new LinkPopup(myProject, myProject.getComponentsStructure().getEntry(myEntity.getId()).getInstance(this.myKey));
		jpm.show(this, x, y);
		return;

	}

}
