/**
 * $Id: mxGeometry.java,v 1.3 2007/02/05 20:04:41 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

import java.util.List;

import mxgraph.utils.mxPoint;
import mxgraph.utils.mxRectangle;


public class mxGeometry extends mxRectangle
{

	mxPoint sourcePoint, targetPoint, offset;

	List<mxPoint> points;

	public mxGeometry()
	{
		this(0, 0, 0, 0);
	}

	public mxGeometry(double x, double y, double width, double height)
	{
		super(x, y, width, height);
	}

	public List<mxPoint> getPoints()
	{
		return points;
	}

	public void setPoints(List<mxPoint> value)
	{
		points = value;
	}

	public mxPoint getTerminalPoint(boolean isSource)
	{
		return (isSource) ? sourcePoint : targetPoint;
	}

	public mxPoint setTerminalPoint(boolean isSource, mxPoint point)
	{
		if (isSource)
		{
			sourcePoint = point;
		}
		else
		{
			targetPoint = point;
		}
		return point;
	}

	public mxPoint getOffset()
	{
		return offset;
	}

	public void setOffset(mxPoint offset)
	{
		this.offset = offset;
	}

}
