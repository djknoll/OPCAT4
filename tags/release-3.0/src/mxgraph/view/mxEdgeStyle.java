/**
 * $Id: mxEdgeStyle.java,v 1.3 2007/01/12 09:21:13 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import java.util.List;

import mxgraph.utils.mxConstants;
import mxgraph.utils.mxPoint;
import mxgraph.utils.mxUtils;


public class mxEdgeStyle
{

	public interface mxEdgeStyleFunction
	{

		void apply(mxCellState state, mxCellState source, mxCellState target,
				List<mxPoint> points, List<mxPoint> result);

	}

	public static mxEdgeStyleFunction SideToSide = new mxEdgeStyleFunction()
	{

		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			if (mxUtils.isTrue(state.getStyle(), mxConstants.STYLE_HORIZONTAL))
			{
				mxEdgeStyle.TopToBottom.apply(state, source, target, points,
						result);
			} else
			{
				mxPoint pt = (points != null) ? points.get(0) : null;
				if (pt != null)
				{
					mxGraphView view = state.getView();
					mxPoint tr = view.getTranslate();
					mxPoint o = state.getOrigin();
					pt = new mxPoint(view.getScale()
							* (tr.getX() + pt.getX() + o.getX()), view
							.getScale()
							* (tr.getY() + pt.getY() + o.getY()));
				}
				double l = Math.max(source.getX(), target.getX());
				double r = Math.min(source.getX() + source.getWidth(), target
						.getX()
						+ target.getWidth());
				double t = Math.max(source.getY(), target.getY());
				double b = Math.min(source.getY() + source.getHeight(), target
						.getY()
						+ target.getHeight());
				if ((r < l && b < t) || pt != null)
				{
					double x = (pt != null) ? pt.getX() : r + (l - r) / 2;
					double y1 = source.getY() + source.getHeight() / 2;
					double y2 = target.getY() + target.getHeight() / 2;
					if (!mxUtils.contains(target, x, y1)
							&& !mxUtils.contains(source, x, y1))
					{
						result.add(new mxPoint(x, y1)); // routed
					}
					if (!mxUtils.contains(target, x, y2)
							&& !mxUtils.contains(source, x, y2))
					{
						result.add(new mxPoint(x, y2)); // routed
					}
					if (pt != null && result.size() == 1)
					{
						result.add(new mxPoint(x, pt.getY())); // routed
					}
				}
			}
		}

	};

	public static mxEdgeStyleFunction TopToBottom = new mxEdgeStyleFunction()
	{

		public void apply(mxCellState state, mxCellState source,
				mxCellState target, List<mxPoint> points, List<mxPoint> result)
		{
			mxPoint pt = (points != null) ? points.get(0) : null;
			if (pt != null)
			{
				mxGraphView view = state.getView();
				mxPoint tr = view.getTranslate();
				mxPoint o = state.getOrigin();
				pt = new mxPoint(view.getScale()
						* (tr.getX() + pt.getX() + o.getX()), view.getScale()
						* (tr.getY() + pt.getY() + o.getY()));
			}

			double t = Math.max(source.getY(), target.getY());
			double b = Math.min(source.getY() + source.getHeight(), target
					.getY()
					+ target.getHeight());
			if (b < t || pt != null)
			{
				double x = source.getX() + source.getWidth() / 2;
				double y = (pt != null) ? pt.getY() : b + (t - b) / 2;
				if (!mxUtils.contains(target, x, y)
						&& !mxUtils.contains(source, x, y))
				{
					result.add(new mxPoint(x, y)); // routed
				}

				x = target.getX() + target.getWidth() / 2;
				if (!mxUtils.contains(target, x, y)
						&& !mxUtils.contains(source, x, y))
				{
					result.add(new mxPoint(x, y)); // routed
				}

				if (pt != null && result.size() == 1)
				{
					result.add(new mxPoint(pt.getX(), y)); // routed
				}
			}
		}
	};

}
