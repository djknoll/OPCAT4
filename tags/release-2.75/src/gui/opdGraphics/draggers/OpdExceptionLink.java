package gui.opdGraphics.draggers;

import exportedAPI.OpdKey;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdGraphics.GraphicsUtils;
import gui.opdGraphics.dialogs.LinkPropertiesDialog;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmExceptionLink;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

/**
 *  Graphic represenrartion of OPM Exception Link.<br>
 *  <p>
 *  Actually it is an <a href = "AroundDragger.html><code>AroundDragger</code></a> with implemented <code>paint()<code> method.
 *  Graphicaly every link consist of three components: two subclasses of <a href = "AroundDragger.html><code>AroundDragger</code></a> and one subclass of
 *  <a href = "ObdLine.html"><code>OpdLine</code></a> connecting them.<br></p>
 *  <p>If graphic representation has one edge of the link mark, like arrow or disk or disk with letter inside, the second edge is
 *  <a href = "TransparentAroundDragger.html"><code>TransparentAroundDragger</code></a> that has no any graphics. Otherwise
 *  both adges of the link has some graphic drawing.<br></p>
 *  <p>Exception link consist of <code>OpdExceptionLink</code> on one edge, <a href = "TransparentAroundDragger.html"><code>TransparentAroundDragger</code></a>
 *  on second edge and <a href = "OpdSimpleLine.html><code>OpdSimpleLine</code></a> connecting them.
 */
public class OpdExceptionLink extends OpdLink
{
	protected Font currentFont;
	protected FontMetrics currentMetrics;

	/**
	 *  <strong>No default constructor for this class.</strong><br>
	 *  Constructs OpdExceptionLink (subclass of <a href = "AroundDragger.html><code>AroundDragger</code></a>).
	 *  @param <code>Edge1 <a href = "Connetable.html">Connectable</a></code>, <a href = "OpdProcess.html"><code>OpdProcess</code></a> this link linked to.
	 *  @param <code>pSide1 int</code>, the side of the component as spacified in <a href = "OpdBaseComponent.html"><code>OpdBaseComponent</code></a>.
	 *  @param <code>pParam1 double</code>, connection parametr -- the position of connection point on component's side.
	 *  @param <code>pProject OpdProject</code>, OPD project this link belongs to.
	 *  @param <code>pLink OpmException</code>, logical representation of the link, for more information see <code>opmEntities.OpmExceptionLink</code>.
	 */
	public OpdExceptionLink(Connectable pEdge, RelativeConnectionPoint pPoint, OpmExceptionLink pLink,
											long pOpdId, long pEntityInOpdId, OpdProject pProject)
	{
		super(pEdge, pPoint, pLink, pOpdId, pEntityInOpdId, pProject);
		currentFont = new Font("OurFont",Font.PLAIN,10);
		currentMetrics = getFontMetrics(currentFont);
		updateDragger();
	}

	/**
	 *  Draws link.
	 */
	public void  paintComponent(Graphics g)
	{
		Point p, head, tail;
		Graphics2D g2nc = (Graphics2D)g;
		AffineTransform at = (AffineTransform)(g2nc.getTransform());

		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
		g2.setStroke(new BasicStroke(1.5f));

		p = line.getUpperPoint();
		if ( (line.getX() + p.getX()) >= getX() && (line.getX() + p.getX()) <= (getX() + getWidth()) &&
			(line.getY() + p.getY()) >= getY() && (line.getY() + p.getY()) <= (getY() + getHeight()) )
		{
			head = p;
			tail = line.getLowerPoint();
		}
		else
		{
			tail = p;
			head = line.getLowerPoint();
		}

		double angle = GraphicsUtils.calcutateRotationAngle(head, tail);

		g2.rotate(angle, getWidth()/2, getHeight()/2);

		g2.setColor(borderColor);
		g2.drawLine(getWidth()*3/14, getHeight()/7, getWidth()*11/14, getHeight()*2/7);
		if(isSelected())
		{
			g2.setStroke(new BasicStroke(1.0f));
			g2.setTransform(at);
			drawSelection(g2);
		}
	}

	/**
	 *  Creates and shows Exception Link properties dialog for given link.
	 */
		public void callPropertiesDialog()
		{
		  LinkPropertiesDialog ld = new LinkPropertiesDialog(myLink, new OpdKey(getOpdId(), getEntityInOpdId()), myProject, "Exception");
		  ld.show();
		}
}
