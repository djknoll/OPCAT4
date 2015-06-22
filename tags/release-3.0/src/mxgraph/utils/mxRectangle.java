/**
 * $Id: mxRectangle.java,v 1.3 2007/01/12 09:21:13 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.utils;

public class mxRectangle extends mxPoint
{

	double width, height;

	public mxRectangle()
	{
		this(0, 0, 0, 0);
	}

	public mxRectangle(mxRectangle rect)
	{
		this(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	public mxRectangle(double x, double y, double width, double height)
	{
		super(x, y);
		setWidth(width);
		setHeight(height);
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double value)
	{
		width = value;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double value)
	{
		height = value;
	}

	public mxRectangle copy()
	{
		return new mxRectangle(this);
	}

}
