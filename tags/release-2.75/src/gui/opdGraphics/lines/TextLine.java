package gui.opdGraphics.lines;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.OpcatContainer;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;
import gui.opmEntities.OpmEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class TextLine extends OpdSimpleLine
{
	private static final double ANIMATION_SIZE = 7.0;

	private String upperText;
	private String lowerText;
	private boolean inDrag;
	private Point dragPoint;
	private StyledLine parentLine;
	private boolean animated;
	private double animationParametr;

	public TextLine(Connectable pEdge1, RelativeConnectionPoint cPoint1,
				   Connectable pEdge2, RelativeConnectionPoint cPoint2,
				   OpmEntity pEntity, OpdKey key, OpdProject pProject)
	{
		super(pEdge1, cPoint1, pEdge2, cPoint2, pEntity, key, pProject);
		upperText = null;
		lowerText = null;
//		shiftLine = 15;
		inDrag = false;
		dragPoint = new Point(0,0);
		parentLine = null;
		animated = false;
//        animated = true;
//        animationParametr = 0.75;
	}

	public void setParentLine(StyledLine parentLine)
	{
		this.parentLine = parentLine;
	}

	public void setAnimated(boolean isAnimated)
	{
		animated = isAnimated;
	}

	public boolean isAnimated()
	{
		return animated;
	}

	public void setAnimationParametr(double parametr)
	{
		animationParametr = parametr;
	}

	public double getAnimationParametr()
	{
		return animationParametr;
	}

	public void mousePressed(MouseEvent e)
	{
		super.mousePressed(e);
		inDrag = false;
	}

	public void mouseReleased(MouseEvent e)
	{
          if (StateMachine.isWaiting() || StateMachine.isAnimated())
          {
            return;
          }

		if (!inDrag)
		{
			super.mouseReleased(e);
			inDrag = false;
			return;
		}

		if (contains(SwingUtilities.convertPoint(getParent(), dragPoint, this)))
		{
			inDrag = false;
			super.mouseReleased(e);
			repaint();
			return;
		}

		if (parentLine != null)
		{
			parentLine.breakLine(this, dragPoint);
		}
		inDrag = false;
	}

	public void mouseDragged(MouseEvent e )
	{
        if (StateMachine.isAnimated() || StateMachine.isWaiting())
        {
            return;
        }

		// following if determines if there is multiple selection, dirty!!!
		if(((OpcatContainer)(this.getParent())).getSelection().getSelectedItemsHash().size() > 1)
		{
			super.mouseDragged(e);
			return;
		}
		inDrag = true;
		dragPoint.setLocation(e.getX(), e.getY());
		dragPoint = SwingUtilities.convertPoint(this, dragPoint, getParent());

		if (edge1 instanceof AroundDragger)
		{
			((AroundDragger)edge1).updateDragger();
		}

		if (edge2 instanceof AroundDragger)
		{
			((AroundDragger)edge2).updateDragger();
		}

		Point p1, p2;

		p1 = edge1.getAbsoluteConnectionPoint(cPoint1);
		p1.setLocation(edge1.getX()+p1.getX()
									,edge1.getY()+p1.getY());
		p1 = SwingUtilities.convertPoint(((JComponent)edge1).getParent(), p1, getParent());

		p2 = edge2.getAbsoluteConnectionPoint(cPoint2);
		p2.setLocation(edge2.getX()+p2.getX()
								,edge2.getY()+p2.getY());
		p2 = SwingUtilities.convertPoint(((JComponent)edge2).getParent(), p2, getParent());

		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double shiftLine = (currentSize/normalSize)*MIN_SIZE;

		int x = (int)(Math.min(dragPoint.getX(), Math.min(p1.getX(), p2.getX())) - shiftLine);
		int y = (int)(Math.min(dragPoint.getY(), Math.min(p1.getY(), p2.getY()))- shiftLine);

		int width = (int)(Math.max(dragPoint.getX(), Math.max(p1.getX(), p2.getX())) - x + shiftLine*2);
		int height = (int)(Math.max(dragPoint.getY(), Math.max(p1.getY(), p2.getY())) - y + shiftLine*2);

		setBounds(x,y,width,height);
//        repaint();
	}


	public void paintComponent(Graphics g)
	{
		int x1;
		int y1;
		int x2;
		int y2;
		Point p1, p2;

		int stringX;
		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)myProject.getConfiguration().getProperty("LineFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;

		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);

		if (isDashed())
		{
			g2.setStroke(new BasicStroke((float)factor*1.8f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10.0f, new float[]{6,4}, 0.0f));
		}
		else
		{
			g2.setStroke(new BasicStroke((float)factor*1.8f));
		}


//        g2.setStroke(new BasicStroke(1.8f));

		g2.setColor(lineColor);

		p1 = this.getUpperPoint();
		p2 = this.getLowerPoint();
		x1 = (int)p1.getX();
		y1 = (int)p1.getY();
		x2 = (int)p2.getX();
		y2 = (int)p2.getY();

		if (!inDrag)
		{
			g2.drawLine(x1, y1, x2, y2);
		}
		else
		{
			Point tPoint = SwingUtilities.convertPoint(getParent(), dragPoint, this);
			g2.drawLine(x1, y1, (int)tPoint.getX(), (int)tPoint.getY());
			g2.drawLine((int)tPoint.getX(), (int)tPoint.getY(), x2, y2);
		}

		java.awt.geom.AffineTransform at = g2.getTransform();

		double angle = GraphicsUtils.calcutateRotationAngle(new Point(x1,y1), new Point(x2, y2));
		g2.rotate((angle+Math.PI/2)%Math.PI-Math.PI/120, getWidth()/2, getHeight()/2);

		if ( angle > 0 && angle < Math.PI/2)
		{
			g2.rotate(Math.PI-Math.PI/120, getWidth()/2, getHeight()/2);
		}


		currentFont = currentFont.deriveFont((float)(currentFont.getSize2D()*factor));

		g2.setFont(currentFont);
		g2.setColor(textColor);
		FontMetrics currentMetrics = getFontMetrics(currentFont);

		if (upperText != null)
		{
			stringX=(getWidth()-currentMetrics.stringWidth(upperText) - (int)((MIN_SIZE+10)*factor) )/2;
			g2.drawString(upperText, stringX, getHeight()/2-currentMetrics.getAscent()+2);
		}


		if (lowerText != null)
		{
			stringX=(getWidth()-currentMetrics.stringWidth(lowerText))/2;
			g2.drawString(lowerText, stringX, getHeight()/2+currentMetrics.getAscent());
		}


		if (animated)
		{
			g2.setTransform(at);
			Point sPoint = edge1.getAbsoluteConnectionPoint(cPoint1);
			sPoint = SwingUtilities.convertPoint((Container)edge1, sPoint, this);

			Point dPoint = edge2.getAbsoluteConnectionPoint(cPoint2);
			dPoint = SwingUtilities.convertPoint((Container)edge2, dPoint, this);

			Point resPoint = new Point();
			resPoint.setLocation(dPoint.getX()-sPoint.getX(), dPoint.getY()-sPoint.getY());
			resPoint.setLocation(resPoint.getX()*animationParametr+sPoint.getX(), resPoint.getY()*animationParametr+sPoint.getY());

			double aSize = ANIMATION_SIZE * factor;
			g2.setColor(Color.red);
			g2.fillOval((int)Math.round(resPoint.getX()-aSize/2), (int)Math.round(resPoint.getY()-aSize/2),
						(int)Math.round(aSize),(int)Math.round(aSize));

		}

	}


	public String getLowerText()
	{
		return this.lowerText;
	}

	public void setLowerText(String lowerText)
	{
		this.lowerText = lowerText;
	}


	public String getUpperText()
	{
		return this.upperText;
	}

	public void setUpperText(String upperText)
	{
		this.upperText = upperText;
	}

	public double getRotationAngle(Connectable ad)
	{
		Point p1, p2, head, tail;


		p1 = edge1.getAbsoluteConnectionPoint(cPoint1);
		p1.setLocation(edge1.getX()+p1.getX()
									,edge1.getY()+p1.getY());
		p1 = SwingUtilities.convertPoint(getParent(), p1, this);

		p2 = edge2.getAbsoluteConnectionPoint(cPoint2);
		p2.setLocation(edge2.getX()+p2.getX()
								,edge2.getY()+p2.getY());
		p2 = SwingUtilities.convertPoint(getParent(), p2, this);


//		p = getUpperPoint();
		if ( ad== edge1)
		{
			head = p1;
			tail = p2;
		}
		else
		{
			head = p2;
			tail = p1;
		}

		if (inDrag)
		{
			tail = SwingUtilities.convertPoint(getParent(), dragPoint, this);
		}

		return GraphicsUtils.calcutateRotationAngle(head, tail);
	}

}
