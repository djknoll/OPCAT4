/**
 * $Id: mxPerimeter.java,v 1.4 2007/01/24 10:44:15 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import mxgraph.utils.mxPoint;
import mxgraph.utils.mxRectangle;
import mxgraph.utils.mxUtils;

public class mxPerimeter
{

	public interface mxPerimeterFunction
	{

		mxPoint apply(mxRectangle bounds, mxCellState edgeState,
				mxCellState terminalState, boolean isSource, mxPoint next);

	}

	public static mxPerimeterFunction RectanglePerimeter = new mxPerimeterFunction()
	{
		
		public mxPoint apply(mxRectangle bounds, mxCellState edgeState,
				mxCellState terminalState, boolean isSource, mxPoint next)
		{
            double cx = bounds.getX() + bounds.getWidth() / 2;
            double cy = bounds.getY() + bounds.getHeight() / 2;
            double dx = next.getX() - cx;
            double dy = next.getY() - cy;
            double alpha = Math.atan2(dy, dx);
            mxPoint p = new mxPoint();
            double pi = Math.PI;
            double pi2 = Math.PI / 2;
            double beta = pi2 - alpha;
            double t = Math.atan2(bounds.getHeight(), bounds.getWidth());
            if (alpha < -pi + t || alpha > pi - t)
            {
                // Left edge
                p.setX(bounds.getX());
                p.setY(cy - bounds.getWidth() * Math.tan(alpha) / 2);
            }
            else if (alpha < -t)
            {
                // Top Edge
                p.setY(bounds.getY());
                p.setX(cx - bounds.getHeight() * Math.tan(beta) / 2);
            }
            else if (alpha < t)
            {
                // Right Edge
                p.setX(bounds.getX() + bounds.getWidth());
                p.setY(cy + bounds.getWidth() * Math.tan(alpha) / 2);
            }
            else
            {
                // Bottom Edge
                p.setY(bounds.getY() + bounds.getHeight());
                p.setX(cx + bounds.getHeight() * Math.tan(beta) / 2);
            }
            return p;
		}

	};

	public static mxPerimeterFunction RightAngleRectanglePerimeter = new mxPerimeterFunction()
	{
		
		public mxPoint apply(mxRectangle bounds, mxCellState edgeState,
				mxCellState terminalState, boolean isSource, mxPoint next)
		{
            mxPoint p = mxPerimeter.RectanglePerimeter.apply(bounds, edgeState,
                    terminalState, isSource, next);
            if (next.getX() >= bounds.getX() &&
                next.getX() <= bounds.getX() + bounds.getWidth())
            {
                p.setX(next.getX());
            }
            else if (next.getY() >= bounds.getY() &&
                       next.getY() <= bounds.getY() + bounds.getHeight())
            {
                p.setY(next.getY());
            }
            if (next.getX() < bounds.getX())
            {
                p.setX(bounds.getX());
            }
            else if (next.getX() > bounds.getX() + bounds.getWidth())
            {
                p.setX(bounds.getX() + bounds.getWidth());
            }
            if (next.getY() < bounds.getY())
            {
                p.setY(bounds.getY());
            }
            else if (next.getY() > bounds.getY() + bounds.getHeight())
            {
                p.setY(bounds.getY() + bounds.getHeight());
            }
            return p;
		}
		
	};

	public static mxPerimeterFunction EllipsePerimeter = new mxPerimeterFunction()
	{
		
		public mxPoint apply(mxRectangle bounds, mxCellState edgeState,
				mxCellState terminalState, boolean isSource, mxPoint next)
		{
            double x = bounds.getX();
            double y = bounds.getY();
            double a = (bounds.getWidth() + 1) / 2;
            double b = (bounds.getHeight() + 1) / 2;
            double cx = x + a;
            double cy = y + b;
            double px = next.getX();
            double py = next.getY();
            // Calculates straight line equation through
            // point and ellipse center y = d * x + h
            double dx = px - cx;
            double dy = py - cy;
            if (dx == 0)
            {
                return new mxPoint(cx, cy + b * dy / Math.abs(dy));
            }
            // Calculates intersection
            double d = dy / dx;
            double h = cy - d * cx;
            double e = a * a * d * d + b * b;
            double f = -2 * cx * e;
            double g = a * a * d * d * cx * cx +
                    b * b * cx * cx -
                    a * a * b * b;
            double det = Math.sqrt(f * f - 4 * e * g);
            // Two solutions (perimeter points)
            double xout1 = (-f + det) / (2 * e);
            double xout2 = (-f - det) / (2 * e);
            double yout1 = d * xout1 + h;
            double yout2 = d * xout2 + h;
            double dist1 = Math.sqrt(Math.pow((xout1 - px), 2)
                        + Math.pow((yout1 - py), 2));
            double dist2 = Math.sqrt(Math.pow((xout2 - px), 2)
                        + Math.pow((yout2 - py), 2));
            // Correct solution
            double xout = 0;
            double yout = 0;
            if (dist1 < dist2)
            {
                xout = xout1;
                yout = yout1;
            }
            else
            {
                xout = xout2;
                yout = yout2;
            }
            return new mxPoint(xout, yout);
		}
		
	};

	public static mxPerimeterFunction RhombusPerimeter = new mxPerimeterFunction()
	{
		
		public mxPoint apply(mxRectangle bounds, mxCellState edgeState,
				mxCellState terminalState, boolean isSource, mxPoint next)
		{
            double x = bounds.getX();
            double y = bounds.getY();
            double w = bounds.getWidth();
            double h = bounds.getHeight();
            double cx = x + w / 2;
            double cy = x + h / 2;
            double px = next.getX();
            double py = next.getY();
            // Special case for intersecting the diamond's corners
            if (cx == px)
            {
                if (cy > py)
                {
                    return new mxPoint(cx, y); // top
                }
                else
                {
                    return new mxPoint(cx, y + h); // bottom
                }
            }
            else if (cy == py)
            {
                if (cx > px)
                {
                    return new mxPoint(x, cy); // left
                }
                else
                {
                    return new mxPoint(x + w, cy); // right
                }
            }
            // In which quadrant will the intersection be?
            // set the slope and offset of the border line accordingly
            if (px < cx)
            {
                if (py < cy)
                {
                    return mxUtils.intersection(px, py, cx, cy, cx, y,
                            x, cy);
                }
                else
                {
                    return mxUtils.intersection(px, py, cx, cy, cx, y
                            + h, x, cy);
                }
            }
            else if (py < cy)
            {
                return mxUtils.intersection(px, py, cx, cy, cx, y,
                        x + w, cy);
            }
            else
            {
                return mxUtils.intersection(px, py, cx, cy, cx, y + h, x
                        + w, cy);
            }
		}
		
	};

}
