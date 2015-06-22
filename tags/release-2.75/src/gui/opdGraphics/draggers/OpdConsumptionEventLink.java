package gui.opdGraphics.draggers;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.DoublePoint;
import gui.opdGraphics.dialogs.LinkPropertiesDialog;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmTransformationLink;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

/**
 *  Graphic represenrartion of OPM Result Link.<br>
 *  <p>
 *  Actually it is an <a href = "AroundDragger.html><code>AroundDragger</code></a> with implemented <code>paint()<code> method.
 *  Graphicaly every link consist of three components: two subclasses of <a href = "AroundDragger.html><code>AroundDragger</code></a> and one subclass of
 *  <a href = "ObdLine.html"><code>OpdLine</code></a> connecting them.<br></p>
 *  <p>If graphic representation has one edge of the link mark, like arrow or disk or disk with letter inside, the second edge is
 *  <a href = "TransparentAroundDragger.html"><code>TransparentAroundDragger</code></a> that has no any graphics. Otherwise
 *  both adges of the link has some graphic drawing.<br></p>
 *  <p>Result link consist of <code>OpdResultLink</code> on one edge, <a href = "TransparentAroundDragger.html"><code>TransparentAroundDragger</code></a>
 *  on second edge and <a href = "OpdSimpleLine.html><code>OpdSimpleLine</code></a> connecting them.
 *  Because effect link has an arrow shape it inherites from a <a href = "ArrowLink.html"><code>ArrowLink</code></a> that implements all graphic functionality for arrow shape links.
 */
public class OpdConsumptionEventLink extends OpdLink
{
	/**
	 *  <strong>No default constructor for this class.</strong><br>
	 *  Constructs OpdInstrumentEventLink (subclass of <a href = "AroundDragger.html><code>AroundDragger</code></a>).
	 *  @param <code>Edge1 <a href = "Connetable.html">Connectable</a></code>, <a href = "OpdProcess.html"><code>OpdProcess</code></a> this link linked to.
	 *  @param <code>pSide1 int</code>, the side of the component as spacified in <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a>.
	 *  @param <code>pParam1 double</code>, connection parametr -- the position of connection point on component's side.
	 *  @param <code>pProject OpdProject</code>, OPD project this link belongs to.
	 *  @param <code>pLink OpmResultLink</code>, logical representation of the link, for more information see <code>opmEntities.OpmResultLink</code>.
	 */

	protected Font currentFont;
	protected FontMetrics currentMetrics;

	public OpdConsumptionEventLink(Connectable pEdge, RelativeConnectionPoint pPoint, OpmTransformationLink pLink,
							long pOpdId, long pEntityInOpdId, OpdProject pProject)
	{
		super(pEdge, pPoint, pLink, pOpdId, pEntityInOpdId, pProject);
		currentFont = new Font("OurFont",Font.PLAIN,12);
		currentMetrics = getFontMetrics(currentFont);
		updateDragger();

	}

	/**
	 *  Creates and shows Result Link properties dialog for given link.
	 */
	public void callPropertiesDialog()
	{
		LinkPropertiesDialog ld = new LinkPropertiesDialog(myLink, new OpdKey(getOpdId(), getEntityInOpdId()), myProject, "Instrument Event");
		ld.show();
	}

	public void paintComponent(Graphics g)
	{

		Graphics2D g2nc = (Graphics2D)g;
		AffineTransform at = (AffineTransform)(g2nc.getTransform());
		Graphics2D g2 = getRotatedGraphics2D(g);

		double normalSize = ((Integer)myProject.getConfiguration().getProperty("NormalSize")).doubleValue();
		double currentSize = ((Integer)myProject.getConfiguration().getProperty("CurrentSize")).doubleValue();
		double factor = currentSize/normalSize;


		// the default arrow position (without any rotation) is DOWN
		//                 |
		//            p2 \ |/ p1
		//                \/

		int Xs[] = {getWidth()*2/6, getWidth()/2, getWidth()*4/6, getWidth()/2};
		int Ys[] = {getHeight()/9, getHeight()/2, getHeight()/9, getHeight()*2/9};

		g2.setColor(backgroundColor);
		g2.fillPolygon(Xs, Ys, 4);
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke((float)factor*1.5f));
		g2.drawPolygon(Xs, Ys, 4);
		//g2.drawString("e", getWidth()*4/6, getHeight()/2);
		g2.setTransform(at);

		// draw 'e' character
		Font currentFont = (Font)(myProject.getConfiguration().getProperty("LinkFont"));
		currentFont = currentFont.deriveFont((float)(currentFont.getSize2D()*factor));
		FontMetrics currentMetrics = getFontMetrics(currentFont);

		g2.setFont(currentFont);
		double angle = getRotationAngle();
		DoublePoint p;
		if (Math.tan(angle)>0)
		{
			p = new DoublePoint(getWidth()*4/6+4, getHeight()/2-4);
		}
		else
		{
			p = new DoublePoint(getWidth()*2/6-4, getHeight()/2-4);
		}

		p.rotate(angle, new Point(getWidth()/2, getHeight()/2));

		int dX = currentMetrics.stringWidth("e")/2;
		int dY = currentMetrics.getAscent()/3;

		g2.drawString("e", p.getRoundX()-dX, p.getRoundY()+dY);

		if(isSelected())
		{
			g2.setStroke(new BasicStroke(1.0f));
			drawSelection(g2);
		}
	}
}
