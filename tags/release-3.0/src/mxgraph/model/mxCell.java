/**
 * $Id: mxCell.java,v 1.8 2007/02/13 08:02:30 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

import java.util.ArrayList;
import java.util.List;

public class mxCell implements mxICell
{
	String id;
	
	Object value;

	mxICell parent, source, target;

	mxGeometry geometry;

	String style;

	boolean vertex, edge, visible = true, collapsed, connectable;

	transient List<mxICell> children, edges;

	public mxCell()
	{
		this(null);
	}

	public mxCell(Object value)
	{
		this(value, null, null);
	}
	
	public mxCell(Object value, mxGeometry geometry, String style)
	{
		setValue(value);
		setGeometry(geometry);
		setStyle(style);
	}

	public boolean isCollapsed()
	{
		return collapsed;
	}

	public void setCollapsed(boolean collapsed)
	{
		this.collapsed = collapsed;
	}

	public boolean isConnectable()
	{
		return connectable;
	}

	public void setConnectable(boolean connectable)
	{
		this.connectable = connectable;
	}

	public boolean isEdge()
	{
		return edge;
	}

	public void setEdge(boolean edge)
	{
		this.edge = edge;
	}

	public mxGeometry getGeometry()
	{
		return geometry;
	}

	public void setGeometry(mxGeometry geometry)
	{
		this.geometry = geometry;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public mxICell getParent()
	{
		return parent;
	}

	public void setParent(mxICell parent)
	{
		this.parent = parent;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public boolean isVertex()
	{
		return vertex;
	}

	public void setVertex(boolean vertex)
	{
		this.vertex = vertex;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public mxICell getTerminal(boolean isSource)
	{
		return (isSource) ? source : target;
	}

	public mxICell setTerminal(boolean isSource, mxICell terminal)
	{
		if (isSource)
		{
			source = terminal;
		} else
		{
			target = terminal;
		}
		return terminal;
	}
	
	public mxICell getSource()
	{
		return source;
	}
	
	public void setSource(mxICell source)
	{
		this.source = source;
	}
	
	public mxICell getTarget()
	{
		return target;
	}
	
	public void setTarget(mxICell target)
	{
		this.target = target;
	}

	public int getEdgeIndex(mxICell edge)
	{
		return (edges != null) ? edges.indexOf(edge) : -1;
	}

	public int getEdgeCount()
	{
		return (edges != null) ? edges.size() : 0;
	}

	public mxICell getEdgeAt(int index)
	{
		return (edges != null) ? edges.get(index) : null;
	}

	public mxICell insertEdge(mxICell edge, boolean isOutgoing)
	{
		if (edge != null)
		{
			edge.removeFromTerminal(isOutgoing);
			edge.setTerminal(isOutgoing, this);
			if (edge.getTerminal(!isOutgoing) != this)
			{
				if (edges == null)
				{
					edges = new ArrayList<mxICell>();
				}
				edges.add(edge);
			}
		}
		return edge;
	}

	public void removeFromTerminal(boolean isSource)
	{
		mxICell terminal = getTerminal(isSource);
		if (terminal != null)
		{
			terminal.removeEdge(this, isSource);
		}
	}

	public mxICell removeEdge(mxICell edge, boolean isOutgoing)
	{
		if (edge != null)
		{
			if (edge.getTerminal(!isOutgoing) != this && edges != null)
			{
				edges.remove(edge);
				edge.setTerminal(isOutgoing, null);
			}
		}
		return edge;
	}

	public int getIndex(mxICell child)
	{
		return (children != null) ? children.indexOf(child) : -1;
	}

	public int getChildCount()
	{
		return (children != null) ? children.size() : 0;
	}

	public mxICell getChildAt(int index)
	{
		return (children != null) ? (mxICell) children.get(index) : null;
	}

	public mxICell insert(mxICell child)
	{
		return insert(child, getChildCount());
	}

	public mxICell insert(mxICell child, int index)
	{
		if (child != null)
		{
			child.removeFromParent();
			child.setParent(this);
			if (children == null)
			{
				children = new ArrayList<mxICell>();
				children.add(child);
			} else
			{
				children.add(index, child);
			}
		}
		return child;
	}

	public void removeFromParent()
	{
		if (parent != null)
		{
			parent.remove(this);
		}
	}

	public mxICell remove(int index)
	{
		mxICell child = null;
		if (children != null && index >= 0)
		{
			child = getChildAt(index);
			remove(child);
		}
		return child;
	}

	public mxICell remove(mxICell child)
	{
		if (child != null && children != null)
		{
			children.remove(child);
			child.setParent(null);
		}
		return child;
	}

}
