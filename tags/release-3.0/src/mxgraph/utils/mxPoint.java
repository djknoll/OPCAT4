/**
 * $Id: mxPoint.java,v 1.3 2007/01/12 09:21:13 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.utils;

import java.awt.geom.Point2D;

public class mxPoint
{

	double x, y;

	public mxPoint()
	{
		this(0, 0);
	}

	public mxPoint(Point2D point)
	{
		this(point.getX(), point.getY());
	}

	public mxPoint(mxPoint point)
	{
		this(point.getX(), point.getY());
	}

	public mxPoint(double x, double y)
	{
		setX(x);
		setY(y);
	}

	public double getX()
	{
		return x;
	}

	public void setX(double value)
	{
		x = value;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double value)
	{
		y = value;
	}

	public mxPoint copy()
	{
		return new mxPoint(this);
	}

}
