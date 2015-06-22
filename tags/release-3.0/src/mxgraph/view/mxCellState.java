/**
 * $Id: mxCellState.java,v 1.4 2007/02/05 20:04:41 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import java.util.Hashtable;
import java.util.List;

import mxgraph.utils.mxPoint;
import mxgraph.utils.mxRectangle;


public class mxCellState extends mxRectangle
{
	mxGraphView view;

	Object cell;

	Hashtable<String, Object> style;

	List<mxPoint> absolutePoints;

	mxPoint origin = new mxPoint(0, 0);

	mxPoint absoluteOffset;

	double terminalDistance, length;
	
	double[] segments;

	public mxCellState(mxGraphView view, Object cell,
			Hashtable<String, Object> style)
	{
		setView(view);
		setCell(cell);
		setStyle(style);
	}

	public mxGraphView getView()
	{
		return view;
	}

	public void setView(mxGraphView value)
	{
		view = value;
	}

	public double getCenterX()
	{
		return getX() + getWidth() / 2;
	}

	public double getCenterY()
	{
		return getY() + getHeight() / 2;
	}

	public mxPoint getAbsoluteOffset()
	{
		return absoluteOffset;
	}

	public void setAbsoluteOffset(mxPoint absoluteOffset)
	{
		this.absoluteOffset = absoluteOffset;
	}

	public List<mxPoint> getAbsolutePoints()
	{
		return absolutePoints;
	}

	public void setAbsolutePoints(List<mxPoint> absolutePoints)
	{
		this.absolutePoints = absolutePoints;
	}

	public Object getCell()
	{
		return cell;
	}

	public void setCell(Object cell)
	{
		this.cell = cell;
	}

	public double getLength()
	{
		return length;
	}

	public void setLength(double length)
	{
		this.length = length;
	}

	public mxPoint getOrigin()
	{
		return origin;
	}

	public void setOrigin(mxPoint origin)
	{
		this.origin = origin;
	}

	public Hashtable<String, Object> getStyle()
	{
		return style;
	}

	public void setStyle(Hashtable<String, Object> style)
	{
		this.style = style;
	}

	public double getTerminalDistance()
	{
		return terminalDistance;
	}

	public void setTerminalDistance(double terminalDistance)
	{
		this.terminalDistance = terminalDistance;
	}

	public double[] getSegments()
	{
		return segments;
	}

	public void setSegments(double[] segments)
	{
		this.segments = segments;
	}

}
