package gui.opdGraphics.draggers;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.Connectable;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opmEntities.OpmProceduralLink;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 *  <p>Arrow link is a class that represents link which graphic representation is an arrow.</p>
 *
 *
 */
public abstract class ArrowLink extends OpdLink
{

/**
 *  <strong>There is no default constructor</strong>
 *  <p>All extending classes have to call <code>super()</code> in their constructors.</p>
 *  @param <code>pEdge1</code> -- OPD graphic component this dragger connected to.
 *  @param <code>pSide1</code> -- The side OPD graphic component this dragger connected to.
 *  @param <code>pParam1</code> -- The relation of coordinates of a <code>mouseClick</code> event to length of the side.
 */
	public ArrowLink(Connectable pEdge, RelativeConnectionPoint pPoint, OpmProceduralLink pLink,
									 long pOpdId, long pEntityInOpdId, OpdProject pProject)

	{
		super(pEdge, pPoint, pLink, pOpdId, pEntityInOpdId, pProject);
		updateDragger();
	}


	public void paintComponent(Graphics g)
	{
		Graphics2D g2nc = (Graphics2D)g;
		AffineTransform at = (AffineTransform)(g2nc.getTransform());
		Graphics2D g2 = getRotatedGraphics2D(g);
		// the default arrow position (without any rotation) is DOWN
		//                 |
		//               \ |/
		//                \/

		GenericTable config = myProject.getConfiguration();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;


		int Xs[] = {getWidth()*2/6, getWidth()/2, getWidth()*4/6, getWidth()/2};
		int Ys[] = {getHeight()/9, getHeight()/2, getHeight()/9, getHeight()*2/9};

		g2.setColor(backgroundColor);
		g2.fillPolygon(Xs, Ys, 4);
		g2.setColor(borderColor);
		g2.setStroke(new BasicStroke((float)factor*1.5f));
		g2.drawPolygon(Xs, Ys, 4);
		if(isSelected())
		{
			g2.setStroke(new BasicStroke(1.0f));
			g2.setTransform(at);
			drawSelection(g2);
		}
	}

}
