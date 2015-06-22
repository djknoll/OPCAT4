package gui.opdGraphics;

import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Hashtable;

import javax.swing.JComponent;

/**
 * this class is a variable in DrawingArea and OpdThing
 */

public class SelectionRectangle extends JComponent
{
	private Hashtable selected, all;
	private OpdProject myProject;
	private int containerToSelectFrom = 0; // 1 - OPD, 2 -- Thing
	private Opd opdSelectingFrom;
	private OpcatContainer containerSelectingFrom;

	private Hashtable graphicalSelection = new Hashtable();

	public SelectionRectangle(OpdProject prj)
	{
		myProject = prj;
//		container = cont;
		selected  = new Hashtable();
		all  = new Hashtable();
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setColor(Color.black);
		g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 5.0f, new float[]{3}, 0.0f));
		g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
	}
}
