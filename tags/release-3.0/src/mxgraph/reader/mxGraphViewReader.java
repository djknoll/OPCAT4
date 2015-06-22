/**
 * $Id: mxGraphViewReader.java,v 1.7 2007/02/09 18:39:34 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.reader;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import mxgraph.canvas.mxICanvas;
import mxgraph.utils.mxPoint;
import mxgraph.utils.mxUtils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public abstract class mxGraphViewReader extends DefaultHandler
{
	mxICanvas canvas;

	public abstract mxICanvas createCanvas(Hashtable<String, Object> attrs);

	public mxICanvas getCanvas()
	{
		return canvas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
	 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException
	{
		String tagName = qName.toUpperCase();
		Hashtable<String, Object> attrs = new Hashtable<String, Object>();
		for (int i = 0; i < atts.getLength(); i++)
		{
			attrs.put(atts.getLocalName(i), atts.getValue(i));
		}
		parseElement(tagName, attrs);
	}

	public void parseElement(String tagName, Hashtable<String, Object> attrs)
	{
		if (canvas == null && tagName.equalsIgnoreCase("GRAPH"))
		{
			canvas = createCanvas(attrs);
		}
		else if (canvas != null)
		{
			if (tagName.equalsIgnoreCase("VERTEX")
					|| tagName.equalsIgnoreCase("GROUP"))
			{
				drawVertex(attrs);
			}
			else if (tagName.equalsIgnoreCase("EDGE"))
			{
				drawEdge(attrs);
			}
		}
	}

	public void drawVertex(Hashtable<String, Object> attrs)
	{
		int width = mxUtils.getInt(attrs, "width");
		int height = mxUtils.getInt(attrs, "height");
		if (width != 0 || height != 0)
		{
			int x = mxUtils.getInt(attrs, "x");
			int y = mxUtils.getInt(attrs, "y");
			String label = mxUtils.getString(attrs, "label");
			canvas.drawVertex(label, x, y, width, height, attrs);
		}
	}

	public void drawEdge(Hashtable<String, Object> attrs)
	{
		List<mxPoint> pts = parsePoints(mxUtils.getString(attrs, "points"));
		if (pts.size() > 0)
		{
			String label = mxUtils.getString(attrs, "label");
			canvas.drawEdge(label, pts, null, attrs);
		}
	}

	public static List<mxPoint> parsePoints(String pts)
	{
		List<mxPoint> result = new ArrayList<mxPoint>();
		int len = pts.length();
		String tmp = "";
		String x = null;
		for (int i = 0; i < len; i++)
		{
			char c = pts.charAt(i);
			if (c == ',' || c == ' ')
			{
				if (x == null)
				{
					x = tmp;
				}
				else
				{
					result.add(new mxPoint(Double.parseDouble(x), Double
							.parseDouble(tmp)));
					x = null;
				}
				tmp = "";
			}
			else
			{
				tmp += c;
			}
		}
		result.add(new mxPoint(Double.parseDouble(x), Double.parseDouble(tmp)));
		return result;
	}

}
