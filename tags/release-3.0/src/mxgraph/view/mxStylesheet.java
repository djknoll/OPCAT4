/**
 * $Id: mxStylesheet.java,v 1.4 2007/01/12 09:21:13 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import java.util.Hashtable;

import mxgraph.utils.mxConstants;


public class mxStylesheet
{

    Hashtable<String, Hashtable<String, Object>> styles =
        new Hashtable<String, Hashtable<String, Object>>();

    public mxStylesheet()
    {
        // Defines the default vertex style
    	Hashtable<String, Object> style = new Hashtable<String, Object>();
	    style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_RECTANGLE);
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.RightAngleRectanglePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#C3D9FF");
        //style[mxConstants.STYLE_GRADIENTCOLOR] = "#FFFFFF";
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
	    setDefaultVertexStyle(style);

        // Defines the default edge style
        style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        style.put(mxConstants.STYLE_FONTSIZE, "11");
        setDefaultEdgeStyle(style);
    }

    public Hashtable<String, Object> getDefaultVertexStyle()
    {
    	return styles.get("defaultVertex");
    }
    
    public void setDefaultVertexStyle(Hashtable<String, Object> value)
    {
        putCellStyle("defaultVertex", value);
    }

    public Hashtable<String, Object> getDefaultEdgeStyle()
    {
    	return styles.get("defaultEdge");
    }
    
    public void setDefaultEdgeStyle(Hashtable<String, Object> value)
    {
        putCellStyle("defaultEdge", value);
    }

    public void putCellStyle(String name, Hashtable<String, Object> style)
    {
        styles.put(name, style);
    }

    public Hashtable<String, Object> getCellStyle(String name,
    		Hashtable<String, Object> defaultStyle)
    {
    	Hashtable<String, Object> style = defaultStyle;
	    if (name != null && name.length() > 0)
        {
            int imin = 0;
            String[] pairs = name.split(";");
		    if (pairs != null)
            {
			    name = pairs[0];
		    }
            if (name.indexOf('=') < 0)
            {
            	Hashtable<String, Object> tmp = styles.get(name);
                if (tmp != null)
                {
                    style = tmp;
                }
			    imin = 1;
		    }

		    if (pairs != null)
            {
			    if (style != null)
                {
                    style = new Hashtable<String, Object>(style);
			    }
                else
                {
                    style = new Hashtable<String, Object>();
			    }
		 	    for (int i = imin; i < pairs.length; i++)
                {
                    String tmp = pairs[i];
                    int c = tmp.indexOf('=');
		 		    if (c >= 0)
                    {
                        String key = tmp.substring(0, c);
                        String value = tmp.substring(c + 1);
			 		    style.put(key, value);
				    }
			    }
		    }
	    }
	    return style;
    }

}
