/**
 * $Id: mxICell.java,v 1.4 2007/01/23 11:34:10 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.model;

public interface mxICell {
	
	
    String getId();
    
    void setId(String value);
    
    Object getValue();
    
    void setValue(Object value);
    
    mxICell getParent();
    
    void setParent(mxICell parent);
    
    mxGeometry getGeometry();
    
    void setGeometry(mxGeometry value);
    
    String getStyle();
    
    void setStyle(String value);
    
    boolean isVertex();
    
    boolean isEdge();

    boolean isVisible();
    
    void setVisible(boolean value);
    
    boolean isCollapsed();
    
    void setCollapsed(boolean value);
    
    boolean isConnectable();
    
    void setConnectable(boolean value);

    mxICell getTerminal(boolean isSource);

    int getEdgeIndex(mxICell edge);

    int getEdgeCount();

    mxICell getEdgeAt(int index);

    int getIndex(mxICell child);

    int getChildCount();

    mxICell getChildAt(int index);

    mxICell insert(mxICell child);

    mxICell insert(mxICell child, int index);

    void removeFromParent();

    mxICell remove(int index);

    mxICell remove(mxICell child);

    mxICell setTerminal(boolean isSource, mxICell terminal);

    mxICell insertEdge(mxICell edge, boolean isOutgoing);

    void removeFromTerminal(boolean isSource);

    mxICell removeEdge(mxICell edge, boolean isOutgoing);

}
