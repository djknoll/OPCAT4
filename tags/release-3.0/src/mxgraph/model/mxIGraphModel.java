/**
 * $Id: mxIGraphModel.java,v 1.3 2007/01/12 09:21:13 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

public interface mxIGraphModel {
	
	public interface mxGraphModelChangeListener
	{
		
		void graphModelChanged(mxIGraphModel sender);
		
	}

    /// <summary>
    /// GraphModelChange: Fires when the graph model was changed.
    /// </summary>
    //event mxGraphModelChangeEventHandler GraphModelChange;

    /// <summary>
    /// root: Holds the root cell.
    /// </summary>
    Object getRoot();
    
    void setRoot(Object root);

    /// <summary>
    /// public bool contains: Returns true if cell is contained in the
    /// model.
    /// </summary>
    boolean contains(Object cell);

    boolean isAncestor(Object parent, Object child);

    /// <summary>
    /// public bool isVertex: Returns true if cell is a vertex.
    /// </summary>
    boolean isVertex(Object cell);

    /// <summary>
    /// public bool isEdge: Returns true if cell is an edge.
    /// </summary>
    boolean isEdge(Object cell);

    Object add(Object parent, Object child, int index);

    Object remove(Object parent);

    Object getParent(Object child);

    void setTerminal(Object edge, Object terminal, boolean isSource);

    Object getTerminal(Object edge, boolean isSource);

    void setValue(Object cell, Object value);

    Object getValue(Object cell);

    void setGeometry(Object cell, mxGeometry geometry);

    mxGeometry getGeometry(Object cell);

    void setStyle(Object cell, String style);

    String getStyle(Object cell);

    void setVisible(Object cell, boolean isVisible);

    boolean isVisible(Object cell);

    void setCollapsed(Object cell, boolean isCollapsed);

    boolean isCollapsed(Object cell);

    int getChildCount(Object cell);

    Object GetChildAt(Object parent, int index);

    int getEdgeCount(Object cell);

    Object getEdgeAt(Object cell, int index);

    void beginUpdate();

    void endUpdate();

    void addGraphModelChangeListener(mxGraphModelChangeListener listener);
    
    void removeGraphModelChangeListener(mxGraphModelChangeListener listener);

}
