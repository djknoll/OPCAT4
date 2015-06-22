/**
 * $Id: mxGraphModel.java,v 1.5 2007/01/23 11:34:10 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

public class mxGraphModel implements mxIGraphModel
{

	// / <summary>
	// / GraphModelChange: Fires when the graph model was changed.
	// / </summary>
	List<mxGraphModelChangeListener> graphModelChangeListeners;

	// / <summary>
	// / root: Holds the root cell.
	// / </summary>
	mxICell root;

	// / <summary>
	// / createIds: Specifiec if the model should automatically create Ids and
	// / resolve collisions for existing Ids. Default is true.
	// / </summary>
	boolean createIds = true;

	// / <summary>
	// / isCreateIds: Specifiec if the model should automatically create Ids and
	// / resolve collisions for existing Ids. Default is true.
	// / </summary>
	int nextId = 0;

	// / <summary>
	// / cells: Maps from ids to cells.
	// / </summary>
	Hashtable cells;

	// / <summary>
	// / updateLevel: Holds the current update level for
	// / compound edits.
	// / </summary>
	int updateLevel;

	// / <summary>
	// / public MxGraphModel: Constructs a new graph model.
	// / </summary>
	public mxGraphModel()
	{
		this(null);
	}

	// / <summary>
	// / public MxGraphModel: Constructs a new graph model using the
	// / specified root.
	// / </summary>
	public mxGraphModel(Object root)
	{
		if (root == null)
		{
			root = new mxCell();
			((mxCell) root).insert(new mxCell());
		}
		setRoot(root);
	}

	public Object getRoot()
	{
		return root;
	}

	public void setRoot(Object value)
	{
		beginUpdate();
		try
		{
			root = (mxICell) value;
			this.nextId = 0;
			this.cells = null;
			cellAdded(root);
		}
		finally
		{
			endUpdate();
		}
	}

	public boolean isCreateIds()
	{
		return createIds;
	}

	public void setCreateIds(boolean value)
	{
		createIds = value;
	}

	// / <summary>
	// / getCell: Returns the cell for the specified id.
	// / </summary>
	public Object getCell(Object id)
	{
		Object result = null;
		if (cells != null)
		{
			result = cells.get(id);
		}
		return result;
	}

	// / <summary>
	// / public boolean contains: Returns true if cell is contained in the
	// / model.
	// / </summary>
	public boolean contains(Object cell)
	{
		return isAncestor(getRoot(), cell);
	}

	// / <summary>
	// / public boolean isAncestor: Returns true if parent is an ancestor of
	// / child.
	// / </summary>
	public boolean isAncestor(Object parent, Object child)
	{
		while (child != null && child != parent)
		{
			child = getParent(child);
		}
		return child == parent;
	}

	// / <summary>
	// / public boolean isVertex: Returns true if cell is a vertex.
	// / </summary>
	public boolean isVertex(Object cell)
	{
		return (cell != null) ? ((mxICell) cell).isVertex() : false;
	}

	// / <summary>
	// / public boolean isEdge: Returns true if cell is an edge.
	// / </summary>
	public boolean isEdge(Object cell)
	{
		return (cell != null) ? ((mxICell) cell).isEdge() : false;
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public Object addVertex(Object parent, String id, Object value, double x,
			double y, double width, double height)
	{
		return addVertex(parent, id, value, x, y, width, height, null);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public Object addVertex(Object parent, String id, Object value, double x,
			double y, double width, double height, String style)
	{
		mxGeometry geometry = new mxGeometry(x, y, width, height);
		mxCell vertex = new mxCell(value, geometry, style);
		vertex.setId(id);
		vertex.setVertex(true);
		vertex.setConnectable(true);
		int index = getChildCount(parent);
		return add(parent, vertex, index);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public Object addEdge(Object parent, String id, Object value,
			Object source, Object target)
	{
		return addEdge(parent, id, value, source, target, null);
	}

	// / <summary>
	// / public MxGraph: Constructs a new graph using the
	// / specified model.
	// / </summary>
	public Object addEdge(Object parent, String id, Object value,
			Object source, Object target, String style)
	{
		mxCell edge = new mxCell(value, new mxGeometry(), style);
		edge.setId(id);
		edge.setEdge(true);
		int index = getChildCount(parent);
		beginUpdate();
		try
		{
			add(parent, edge, index);
			setTerminal(edge, source, true);
			setTerminal(edge, target, false);
		}
		finally
		{
			endUpdate();
		}
		return edge;
	}

	public Object add(Object parent, Object child, int index)
	{
		beginUpdate();
		try
		{
			((mxICell) parent).insert((mxICell) child, index);
			cellAdded(child);
		}
		finally
		{
			endUpdate();
		}
		return child;
	}

	protected void cellAdded(Object cell)
	{
		if (cell instanceof mxICell)
		{
			mxICell mx = (mxICell) cell;
			if (mx.getId() == null && isCreateIds())
			{
				mx.setId(createId(cell));
			}

			if (mx.getId() != null)
			{
				Object collision = getCell(mx.getId());
				if (collision != cell)
				{
					while (collision != null)
					{
						mx.setId(createId(cell));
						collision = getCell(mx.getId());
					}

					if (cells == null)
					{
						cells = new Hashtable();
					}
					cells.put(mx.getId(), cell);
				}
			}

			int childCount = mx.getChildCount();
			for (int i = 0; i < childCount; i++)
			{
				cellAdded(mx.getChildAt(i));
			}
		}
	}

	public String createId(Object cell)
	{
		String id = String.valueOf(nextId);
		nextId++;
		return id;
	}

	public Object remove(Object cell)
	{
		mxICell mx = (mxICell) cell;
		beginUpdate();
		try
		{
			if (cell == root)
			{
				setRoot(null);
			}
			else
			{
				mx.removeFromParent();
			}
			cellRemoved(cell);
		}
		finally
		{
			endUpdate();
		}
		return cell;
	}

	protected void cellRemoved(Object cell)
	{
		if (cell instanceof mxICell)
		{
			mxICell mx = (mxICell) cell;
			int childCount = mx.getChildCount();
			for (int i = 0; i < childCount; i++)
			{
				cellRemoved(mx.getChildAt(i));
			}

			mx.removeFromTerminal(true);
			mx.removeFromTerminal(false);

			if (cells != null && mx.getId() != null)
			{
				cells.remove(mx.getId());
			}
		}
	}

	public Object getParent(Object child)
	{
		return (child instanceof mxICell) ? ((mxICell) child).getParent()
				: null;
	}

	public void setTerminal(Object edge, Object terminal, boolean isSource)
	{
		mxICell mxe = (mxICell) edge;
		mxICell previous = mxe.getTerminal(isSource);
		beginUpdate();
		try
		{
			if (terminal != null)
			{
				((mxICell) terminal).insertEdge(mxe, isSource);
			}
			else if (previous != null)
			{
				previous.removeEdge(mxe, isSource);
			}
		}
		finally
		{
			endUpdate();
		}
	}

	public void setTerminals(Object edge, Object source, Object target)
	{

		beginUpdate();
		try
		{
			setTerminal(edge, source, true);
			setTerminal(edge, target, false);
		}
		finally
		{
			endUpdate();
		}
	}

	public Object getTerminal(Object edge, boolean isSource)
	{
		return (edge instanceof mxICell) ? ((mxICell) edge)
				.getTerminal(isSource) : null;
	}

	public void setValue(Object cell, Object value)
	{
		beginUpdate();
		try
		{
			((mxICell) cell).setValue(value);
		}
		finally
		{
			endUpdate();
		}
	}

	public Object getValue(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).getValue() : null;
	}

	public void setGeometry(Object cell, mxGeometry geometry)
	{
		beginUpdate();
		try
		{
			((mxICell) cell).setGeometry(geometry);
		}
		finally
		{
			endUpdate();
		}
	}

	public mxGeometry getGeometry(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).getGeometry()
				: null;
	}

	public void setStyle(Object cell, String style)
	{
		beginUpdate();
		try
		{
			((mxICell) cell).setStyle(style);
		}
		finally
		{
			endUpdate();
		}
	}

	public String getStyle(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).getStyle() : null;
	}

	public void setVisible(Object cell, boolean isVisible)
	{
		beginUpdate();
		try
		{
			((mxICell) cell).setVisible(isVisible);
		}
		finally
		{
			endUpdate();
		}
	}

	public boolean isVisible(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).isVisible() : false;
	}

	public void setCollapsed(Object cell, boolean isCollapsed)
	{
		beginUpdate();
		try
		{
			((mxICell) cell).setCollapsed(isCollapsed);
		}
		finally
		{
			endUpdate();
		}
	}

	public boolean isCollapsed(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).isCollapsed()
				: false;
	}

	public int getChildCount(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).getChildCount() : 0;
	}

	public Object GetChildAt(Object parent, int index)
	{
		return (parent instanceof mxICell) ? ((mxICell) parent)
				.getChildAt(index) : null;
	}

	public int getEdgeCount(Object cell)
	{
		return (cell instanceof mxICell) ? ((mxICell) cell).getEdgeCount() : 0;
	}

	public Object getEdgeAt(Object parent, int index)
	{
		return (parent instanceof mxICell) ? ((mxICell) parent)
				.getEdgeAt(index) : null;
	}

	public void beginUpdate()
	{
		// Monitor.Enter(updateLevel);
		updateLevel++;
		// Monitor.Exit(updateLevel);
	}

	public void endUpdate()
	{
		// Monitor.Enter(updateLevel);
		updateLevel--;
		if (updateLevel == 0)
		{
			dispatchGraphModelChangeEvent();
		}
		// Monitor.Exit(updateLevel);
	}

	public void addGraphModelChangeListener(mxGraphModelChangeListener listener)
	{
		if (graphModelChangeListeners == null)
		{
			graphModelChangeListeners = new ArrayList<mxGraphModelChangeListener>();
		}
		graphModelChangeListeners.add(listener);
	}

	public void removeGraphModelChangeListener(
			mxGraphModelChangeListener listener)
	{
		if (graphModelChangeListeners != null)
		{
			graphModelChangeListeners.remove(listener);
		}
	}

	public void dispatchGraphModelChangeEvent()
	{
		if (graphModelChangeListeners != null)
		{
			Iterator<mxGraphModelChangeListener> it = graphModelChangeListeners
					.iterator();
			while (it.hasNext())
			{
				it.next().graphModelChanged(this);
			}
		}
	}

}
