/**
 * $Id: mxICanvas.java,v 1.5 2007/02/09 18:39:34 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.canvas;

import java.util.Hashtable;
import java.util.List;

import mxgraph.utils.mxPoint;


public interface mxICanvas
{

	void drawVertex(String text, int x, int y, int width, int height,
			Hashtable<String, Object> style);

	void drawEdge(String text, List<mxPoint> pts, mxPoint label,
			Hashtable<String, Object> style);

}
