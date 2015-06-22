/**
 * $Id: mxGraphView.java,v 1.8 2007/02/07 10:18:23 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import mxgraph.model.mxGeometry;
import mxgraph.model.mxIGraphModel;
import mxgraph.utils.mxConstants;
import mxgraph.utils.mxPoint;
import mxgraph.utils.mxRectangle;
import mxgraph.utils.mxUtils;
import mxgraph.view.mxEdgeStyle.mxEdgeStyleFunction;
import mxgraph.view.mxPerimeter.mxPerimeterFunction;


public class mxGraphView
{

	// / <summary>
	// / graph: Reference to the enclosing graph.
	// / </summary>
	mxGraph graph;

	// / <summary>
	// / bounds: Holds the current bounds of the graph.
	// / </summary>
	mxRectangle bounds;

	// / <summary>
	// / scale: Specifies the current scale.
	// / </summary>
	double scale = 1;

	// / <summary>
	// / scale: Specifies the current scale.
	// / </summary>
	mxPoint translate = new mxPoint(0, 0);

	// / <summary>
	// / scale: Specifies the current scale.
	// / </summary>
	Hashtable<Object, mxCellState> states = new Hashtable<Object, mxCellState>();

	// / <summary>
	// / public MxGraphView: Constructs a new view for the given graph.
	// / </summary>
	public mxGraphView(mxGraph graph)
	{
		setGraph(graph);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxGraph getGraph()
	{
		return graph;
	}

	public void setGraph(mxGraph value)
	{
		graph = value;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxRectangle getBounds()
	{
		return bounds;
	}

	public void setBounds(mxRectangle value)
	{
		bounds = value;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public double getScale()
	{
		return scale;
	}

	public void setScale(double value)
	{
		scale = value;
		invalidate();
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxPoint getTranslate()
	{
		return translate;
	}

	public void setTranslate(mxPoint value)
	{
		translate = value;
		invalidate();
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void invalidate()
	{
		states.clear();
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void validate()
	{
		Object cell = graph.getModel().getRoot();
		if (cell != null && states.size() == 0)
		{
			validateBounds(null, null, cell);
			bounds = validatePoints(cell);
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void validateBounds(Object parent, mxCellState parentState,
			Object cell)
	{
		mxIGraphModel model = graph.getModel();
		mxCellState state = getState(cell, true);
		mxGeometry geo = model.getGeometry(cell);
		if (state != null && geo != null)
		{
			if (parent != null && parentState != null)
			{
				// TODO: Relative geometries
				state.setOrigin(new mxPoint(parentState.getOrigin().getX(),
						parentState.getOrigin().getY()));
				if (!model.isEdge(cell))
				{
					state.getOrigin().setX(
							state.getOrigin().getX() + geo.getX());
					state.getOrigin().setY(
							state.getOrigin().getY() + geo.getY());
				}
			}
			else
			{
				state.setOrigin(new mxPoint(0, 0));
			}
			// TODO: Relative Geometries
			state.setX(scale * (translate.getX() + state.getOrigin().getX()));
			state.setY(scale * (translate.getY() + state.getOrigin().getY()));
			state.setWidth(scale * geo.getWidth());
			state.setHeight(scale * geo.getHeight());
		}
		if (!graph.isCellCollapsed(cell))
		{
			int childCount = model.getChildCount(cell);
			for (int i = 0; i < childCount; i++)
			{
				validateBounds(cell, state, model.GetChildAt(cell, i));
			}
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxRectangle validatePoints(Object cell)
	{
		mxCellState state = getState(cell);
		double minX = 0;
		double minY = 0;
		double maxX = 0;
		double maxY = 0;
		boolean isEdge = graph.getModel().isEdge(cell);
		if (state != null)
		{
			if (isEdge)
			{
				Object src = getVisibleTerminal(cell, true);
				if (src != null && !graph.getModel().isAncestor(src, cell))
				{
					validatePoints(src);
				}
				Object trg = getVisibleTerminal(cell, false);
				if (trg != null && !graph.getModel().isAncestor(trg, cell))
				{
					validatePoints(trg);
				}
				mxGeometry geo = graph.getModel().getGeometry(cell);
				updatePoints(state, geo.getPoints());
				updateTerminalPoints(state);
				updateEdgeBounds(state);
				updateEdgeLabelOffset(state);
			}
			if (isEdge || graph.getModel().isVertex(cell))
			{
				minX = state.getX();
				minY = state.getY();
				maxX = state.getX() + state.getWidth();
				maxY = state.getY() + state.getHeight();
			}
		}
		if (!graph.isCellCollapsed(cell))
		{
			int childCount = graph.getModel().getChildCount(cell);
			for (int i = 0; i < childCount; i++)
			{
				mxRectangle bounds = validatePoints(graph.getModel()
						.GetChildAt(cell, i));
				// FIXME: Bad initial values minX, minY
				minX = Math.min(minX, bounds.getX());
				minY = Math.min(minY, bounds.getY());
				maxX = Math.max(maxX, bounds.getX() + bounds.getWidth());
				maxY = Math.max(maxY, bounds.getY() + bounds.getHeight());
			}
		}
		return new mxRectangle(minX, minY, maxX - minX, maxY - minY);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void updatePoints(mxCellState state, List<mxPoint> points)
	{
		if (state != null)
		{
			Object edge = state.getCell();
			Object src = getVisibleTerminal(edge, true);
			Object trg = getVisibleTerminal(edge, false);
			List<mxPoint> pts = new ArrayList<mxPoint>();
			pts.add(null);
			Object edgeStyle = state.getStyle().get(mxConstants.STYLE_EDGE);
			if (edgeStyle instanceof mxEdgeStyleFunction && src != null
					&& trg != null)
			{
				mxCellState source = this.getState(src);
				mxCellState target = this.getState(trg);
				if (source != null && target != null)
				{
					((mxEdgeStyleFunction) edgeStyle).apply(state, source,
							target, points, pts);
				}
			}
			else if (points != null)
			{
				for (int i = 0; i < points.size(); i++)
				{
					mxPoint pt = points.get(i).copy();
					pt.setX(translate.getX() + state.getOrigin().getX());
					pt.setY(translate.getY() + state.getOrigin().getY());
					pt.setX(pt.getX() * scale);
					pt.setY(pt.getY() * scale);
					pts.add(pt);
				}
			}
			pts.add(null);
			state.setAbsolutePoints(pts);
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void updateTerminalPoints(mxCellState state)
	{
		Object edge = state.getCell();
		mxGeometry geo = graph.getModel().getGeometry(edge);
		mxPoint pt = geo.getTerminalPoint(true);
		if (pt != null)
		{
			pt = new mxPoint(
					scale
							* (translate.getX() + pt.getX() + state.getOrigin()
									.getX()), scale
							* (translate.getY() + pt.getY() + state.getOrigin()
									.getY()));
			state.getAbsolutePoints().set(0, pt);
		}
		pt = geo.getTerminalPoint(false);
		if (pt != null)
		{
			pt = new mxPoint(
					scale
							* (translate.getX() + pt.getX() + state.getOrigin()
									.getX()), scale
							* (translate.getY() + pt.getY() + state.getOrigin()
									.getY()));
			int n = state.getAbsolutePoints().size();
			state.getAbsolutePoints().set(n - 1, pt);
		}
		Object src = getVisibleTerminal(edge, true);
		Object trg = getVisibleTerminal(edge, false);
		if (trg != null)
		{
			updateTerminalPoint(state, trg, src, false);
		}
		if (src != null)
		{
			updateTerminalPoint(state, src, trg, true);
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void updateTerminalPoint(mxCellState state, Object start,
			Object end, boolean isSource)
	{
		int index = (isSource) ? 0 : state.getAbsolutePoints().size() - 1;
		state.getAbsolutePoints().set(index,
				getPerimeterPoint(state, start, end, isSource));
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxPoint getPerimeterPoint(mxCellState state, Object start,
			Object end, boolean isSource)
	{
		mxPoint point = null;
		mxCellState terminalState = getState(start);
		if (terminalState != null)
		{
			Object perimeter = terminalState.getStyle().get(
					mxConstants.STYLE_PERIMETER);
			mxPoint next = getNextPoint(state, end, isSource);
			if (perimeter instanceof mxPerimeterFunction && next != null)
			{
				mxRectangle bounds = new mxRectangle(terminalState);
				double spacing = mxUtils.getDouble(state.getStyle(),
						mxConstants.STYLE_PERIMETER_SPACING);
				spacing += mxUtils.getDouble(terminalState.getStyle(),
						mxConstants.STYLE_PERIMETER_SPACING);
				if (spacing > 0)
				{
					bounds.setX(bounds.getX() - spacing);
					bounds.setY(bounds.getY() - spacing);
					bounds.setWidth(bounds.getWidth() + 2 * spacing);
					bounds.setHeight(bounds.getHeight() + 2 * spacing);
				}
				point = ((mxPerimeterFunction) perimeter).apply(bounds, state,
						terminalState, isSource, next);
			}
			else
			{
				point = new mxPoint(terminalState.getCenterX(), terminalState
						.getCenterY());
			}
		}
		return point;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxPoint getNextPoint(mxCellState state, Object opposite,
			boolean isSource)
	{
		mxPoint point = null;
		List<mxPoint> pts = state.getAbsolutePoints();
		if (pts != null && (isSource || pts.size() > 2 || opposite == null))
		{
			int count = pts.size();
			int index = (isSource) ? Math.min(1, count - 1) : Math.max(0,
					count - 2);
			point = pts.get(index);
		}
		if (point == null && opposite != null)
		{
			mxCellState oppositeState = getState(opposite);
			if (oppositeState != null)
			{
				point = new mxPoint(oppositeState.getCenterX(), oppositeState
						.getCenterY());
			}
		}
		return point;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public Object getVisibleTerminal(Object edge, boolean isSource)
	{
		mxIGraphModel model = graph.getModel();
		Object result = model.getTerminal(edge, isSource);
		Object best = result;
		while (result != null)
		{
			if (!graph.isCellVisible(best) || graph.isCellCollapsed(result))
			{
				best = result;
			}
			result = model.getParent(result);
		}
		return best;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void updateEdgeBounds(mxCellState state)
	{
		List<mxPoint> points = state.getAbsolutePoints();
		if (points != null && points.size() > 0)
		{
			mxPoint p0 = points.get(0);
			mxPoint pe = points.get(points.size() - 1);

			if (p0 == null || pe == null)
			{
				// Note: This is an error that normally occurs
				// if a connected edge has a null-terminal, ie.
				// edge.source == null or edge.target == null
				// and no additional control poins defined.
			}
			else
			{
				if (p0.getX() != pe.getX() || p0.getY() != pe.getY())
				{
					double dx = pe.getX() - p0.getX();
					double dy = pe.getY() - p0.getY();
					state.setTerminalDistance(Math.sqrt(dx * dx + dy * dy));
				}
				else
				{
					state.setTerminalDistance(0);
				}
				
				double length = 0;
				double[] segments = new double[points.size() - 1];
				mxPoint pt = p0;
				
				if (pt != null)
				{
					double minX = pt.getX();
					double minY = pt.getY();
					double maxX = minX;
					double maxY = minY;
					
					for (int i = 1; i < points.size(); i++)
					{
						mxPoint tmp = points.get(i);
						if (tmp != null)
						{
							double dx = pt.getX() - tmp.getX();
							double dy = pt.getY() - tmp.getY();
							
							double segment = Math.sqrt(dx * dx + dy * dy);
							segments[i - 1] = segment;
							length += segment;
							pt = tmp;
							
							minX = Math.min(pt.getX(), minX);
							minY = Math.min(pt.getY(), minY);
							maxX = Math.max(pt.getX(), maxX);
							maxY = Math.max(pt.getY(), maxY);
						}
					}
					
					state.setLength(length);
					state.setSegments(segments);
					double markerSize = 1; // TODO: include marker size
					
					state.setX(minX);
					state.setY(minY);
					state.setWidth(Math.max(markerSize, maxX - minX));
					state.setHeight(Math.max(markerSize, maxY - minY));
				}
				else
				{
					state.setLength(0);
				}
			}
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public void updateEdgeLabelOffset(mxCellState state)
	{
		List<mxPoint> points = state.getAbsolutePoints();
		state.setAbsoluteOffset(new mxPoint(state.getCenterX(), state
				.getCenterY()));
		
		if (points != null && points.size() > 0)
		{
			mxGeometry geometry = graph.model.getGeometry(state.cell);
			double x = geometry.getX() / 2;
			double y = geometry.getY();
			double dist = (x + 0.5) * state.getLength();

			double[] segments = state.getSegments();

			int index = 1;
			double length = 0;
			double segment = segments[0];
			int pointCount = points.size();
			
			while (dist > length + segment && index < pointCount - 1)
			{
				length += segment;
				segment = segments[index++];
			}

			double factor = (dist - length) / segment;
			mxPoint p0 = points.get(index - 1);
			mxPoint pe = points.get(index);

			if (p0 != null && pe != null)
			{
				double dx = pe.getX() - p0.getX();
				double dy = pe.getY() - p0.getY();
				double nx = dy / segment;
				double ny = dx / segment;

				double offsetX = 0;
				double offsetY = 0;

				mxPoint offset = geometry.getOffset();
				if (offset != null)
				{
					offsetX = offset.getX();
					offsetY = offset.getY();
				}

				x = p0.getX() + dx * factor + nx * y + offsetX * scale;
				y = p0.getY() + dy * factor + ny * y + offsetY * scale;
				state.setAbsoluteOffset(new mxPoint(x, y));
			}
		}
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxCellState getState(Object cell)
	{
		return getState(cell, false);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxCellState getState(Object cell, boolean create)
	{
		mxCellState state = null;
		if (cell != null)
		{
			if (states.containsKey(cell))
			{
				state = states.get(cell);
			}
			else if (create && graph.isCellVisible(cell))
			{
				state = createState(cell);
				states.put(cell, state);
			}
		}
		return state;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public mxCellState createState(Object cell)
	{
		Hashtable<String, Object> style = graph.getCellStyle(cell);
		return new mxCellState(this, cell, style);
	}

}
