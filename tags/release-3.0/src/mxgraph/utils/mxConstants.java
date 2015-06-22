/**
 * $Id: mxConstants.java,v 1.3 2007/01/29 14:38:36 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.utils;

import java.awt.Color;

public class mxConstants {

    /// <summary>
    /// RAD_PER_DEG: Defines the number of radiants per degree.
    /// </summary>
    public static double RAD_PER_DEG = 0.0174532;

    /// <summary>
    /// DEG_PER_RAD: Defines the number of degrees per radiant.
    /// </summary>
    public static double DEG_PER_RAD = 57.2957795;

    /// <summary>
    /// ACTIVE_REGION: Defines the portion of the cell which is
    /// to be used as a connectable region.
    /// </summary>
    public static double ACTIVE_REGION = 0.3;

    /// <summary>
    /// MIN_ACTIVE_REGION: Defines the minimum size in pixels of the 
    /// portion of the cell cell which is to be 
    /// used as a connectable region.
    /// </summary>
    public static double MIN_ACTIVE_REGION = 8;

    /// <summary>
    /// NS_SVG: Defines the SVG namespace.
    /// </summary
    public static String NS_SVG = "http =//www.w3.org/2000/svg";

    /// <summary>
    /// NS_XLINK: Defined the XLink namespace.
    /// </summary>
    public static String NS_XLINK = "http =//www.w3.org/1999/xlink";

    public static Color SHADOW_COLOR = Color.gray;

	public static int SHADOW_OFFSET = 3;

    /// <summary>
    /// SVG_SHADOWCOLOR: Defined the color to be used to draw
    /// shadows in SVG.
    /// </summary>
    public static String SVG_SHADOWCOLOR = "gray";

    /// <summary>
    /// SVG_TRANSFORM: Defines the transformation used to draw
    /// shadows in SVG.
    /// </summary>
    public static String SVG_SHADOWTRANSFORM = "translate(2 3)";

    /// <summary>
    /// STYLE_PERIMETER: Defines the key for the perimeter style.
    /// Possible values are the functions defined
    /// in <mxPerimeter>.
    /// </summary>
    public static String STYLE_PERIMETER = "perimeter";

    /// <summary>
    /// STYLE_OPACITY: Defines the key for the opacity style (%).
    /// </summary>
    public static String STYLE_OPACITY = "opacity";

    /// <summary>
    /// STYLE_FILLCOLOR: Defines the key for the fillColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_FILLCOLOR = "fillColor";

    /// <summary>
    /// STYLE_GRADIENTCOLOR: Defines the key for the gradientColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_GRADIENTCOLOR = "gradientColor";

    /// <summary>
    /// STYLE_STROKECOLOR: Defines the key for the strokeColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_STROKECOLOR = "strokeColor";

    /// <summary>
    /// STYLE_SEPARATORCOLOR: Defines the key for the separatorColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_SEPARATORCOLOR = "separatorColor";

    /// <summary>
    /// STYLE_STROKEWIDTH: Defines the key for the strokeWidth style (in px).
    /// </summary>
    public static String STYLE_STROKEWIDTH = "strokeWidth";

    /// <summary>
    /// STYLE_ALIGN: Defines the key for the align style.
    /// Possible values are <ALIGN_LEFT>;
    /// <ALIGN_CENTER> and <ALIGN_RIGHT>.
    /// </summary>
    public static String STYLE_ALIGN = "align";

    /// <summary>
    /// STYLE_VERTICAL_ALIGN: Defines the key for the verticalAlign style.
    /// Possible values are <ALIGN_TOP>;
    /// <ALIGN_MIDDLE> and <ALIGN_BOTTOM>.
    /// </summary>
    public static String STYLE_VERTICAL_ALIGN = "verticalAlign";

    /// <summary>
    /// STYLE_IMAGE_ALIGN: Defines the key for the align style.
    /// Possible values are <ALIGN_LEFT>;
    /// <ALIGN_CENTER> and <ALIGN_RIGHT>.
    /// </summary>
    public static String STYLE_IMAGE_ALIGN = "imageAlign";

    /// <summary>
    /// STYLE_IMAGE_VERTICAL_ALIGN: Defines the key for the verticalAlign style.
    /// Possible values are <ALIGN_TOP>;
    /// <ALIGN_MIDDLE> and <ALIGN_BOTTOM>.
    /// </summary>
    public static String STYLE_IMAGE_VERTICAL_ALIGN = "imageVerticalAlign";

    /// <summary>
    /// STYLE_IMAGE: Defines the key for the image style.
    /// Possible values are any image URL.
    /// </summary>
    public static String STYLE_IMAGE = "image";

    /// <summary>
    /// STYLE_IMAGE_WIDTH: Defines the key for the imageWidth style (in px).
    /// </summary>
    public static String STYLE_IMAGE_WIDTH = "imageWidth";

    /// <summary>
    /// STYLE_IMAGE_HEIGHT: Defines the key for the imageHeight style (in px).
    /// </summary>
    public static String STYLE_IMAGE_HEIGHT = "imageHeight";

    /// <summary>
    /// STYLE_INDICATOR_SHAPE: Defines the key for the indicatorShape style.
    /// Possible values are any of the SHAPE_*
    /// constants.
    /// </summary>
    public static String STYLE_INDICATOR_SHAPE = "indicatorShape";

    /// <summary>
    /// STYLE_INDICATOR_IMAGE: Defines the key for the indicatorImage style.
    /// Possible values are any image URL.
    /// </summary>
    public static String STYLE_INDICATOR_IMAGE = "indicatorImage";

    /// <summary>
    /// STYLE_INDICATOR_COLOR: Defines the key for the indicatorColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_INDICATOR_COLOR = "indicatorColor";

    /// <summary>
    /// STYLE_INDICATOR_GRADIENTCOLOR: Defines the key for the indicatorGradientColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_INDICATOR_GRADIENTCOLOR = "indicatorGradientColor";

    /// <summary>
    /// STYLE_INDICATOR_SPACING: Defines the key for the indicatorSpacing style (in px).
    /// </summary>
    public static String STYLE_INDICATOR_SPACING = "indicatorSpacing";

    /// <summary>
    /// STYLE_INDICATOR_WIDTH: Defines the key for the indicatorWidth style (in px).
    /// </summary>
    public static String STYLE_INDICATOR_WIDTH = "indicatorWidth";

    /// <summary>
    /// STYLE_INDICATOR_HEIGHT: Defines the key for the indicatorHeight style (in px).
    /// </summary>
    public static String STYLE_INDICATOR_HEIGHT = "indicatorHeight";

    /// <summary>
    /// STYLE_SHADOW: Defines the key for the shadow style.
    /// Possible values are true or false.
    /// </summary>
    public static String STYLE_SHADOW = "shadow";

    /// <summary>
    /// STYLE_ENDARROW: Defines the key for the endArrow style.
    /// Possible values are all constants in this
    /// class that start with ARROW_. This style is
    /// supported in the mxConnector shape.
    /// </summary>
    public static String STYLE_ENDARROW = "endArrow";

    /// <summary>
    /// STYLE_STARTARROW: Defines the key for the startArrow style.
    /// Possible values are all constants in this
    /// class that start with ARROW_.
    /// See <STYLE_ENDARROW>.
    /// This style is supported in the mxConnector shape.
    /// </summary>
    public static String STYLE_STARTARROW = "startArrow";

    /// <summary>
    /// STYLE_ENDSIZE: Defines the key for the endSize style (in px).
    /// </summary>
    public static String STYLE_ENDSIZE = "endSize";

    /// <summary>
    /// STYLE_STARTSIZE: Defines the key for the startSize style (in px).
    /// </summary>
    public static String STYLE_STARTSIZE = "startSize";

    /// <summary>
    /// STYLE_DASHED: Defines the key for the dashed style.
    /// Possible values are true or false.
    /// </summary>
    public static String STYLE_DASHED = "dashed";

    /// <summary>
    /// STYLE_ROUNDED: Defines the key for the rounded style.
    /// Possible values are true or false.
    /// </summary>
    public static String STYLE_ROUNDED = "rounded";

    /// <summary>
    /// STYLE_PERIMETER_SPACING: Defines the key for the perimeter spacing (in px).
    /// This is the distance between connection point and the perimeter.
    /// </summary>
    public static String STYLE_PERIMETER_SPACING = "perimeterSpacing";

    /// <summary>
    /// STYLE_SPACING: Defines the key for the spacing (in px). The
    /// spacings below are added to this value on each
    /// side when appropriate.
    /// </summary>
    public static String STYLE_SPACING = "spacing";

    /// <summary>
    /// STYLE_SPACING_TOP: Defines the key for the spacingTop style (in px).
    /// </summary>
    public static String STYLE_SPACING_TOP = "spacingTop";

    /// <summary>
    /// STYLE_SPACING_LEFT: Defines the key for the spacingLeft style (in px).
    /// </summary>
    public static String STYLE_SPACING_LEFT = "spacingLeft";

    /// <summary>
    /// STYLE_SPACING_BOTTOM: Defines the key for the spacingBottom style (in px).
    /// </summary>
    public static String STYLE_SPACING_BOTTOM = "spacingBottom";

    /// <summary>
    /// STYLE_SPACING_RIGHT: Defines the key for the spacingRight style (in px).
    /// </summary>
    public static String STYLE_SPACING_RIGHT = "spacingRight";

    /// <summary>
    /// STYLE_HORIZONTAL: Defines the key for the horizontal style.
    /// Possible values are true or false.
    /// </summary>
    public static String STYLE_HORIZONTAL = "horizontal";

    /// <summary>
    /// STYLE_FONTCOLOR: Defines the key for the fontColor style.
    /// Possible values are all color codes.
    /// </summary>
    public static String STYLE_FONTCOLOR = "fontColor";

    /// <summary>
    /// STYLE_FONTFAMILY: Defines the key for the fontFamily style.
    /// Possible values are names such as Arial;
    /// Dialog; Verdana; Times New Roman.
    /// </summary>
    public static String STYLE_FONTFAMILY = "fontFamily";

    /// <summary>
    /// STYLE_FONTSIZE: Defines the key for the fontSize style (in pt).
    /// </summary>
    public static String STYLE_FONTSIZE = "fontSize";

    /// <summary>
    /// STYLE_FONTSTYLE: Defines the key for the fontStyle style.
    /// Values may be any logical AND (sum) of
    /// <FONT_BOLD>; <FONT_ITALIC>;
    /// <FONT_UNDERLINE> and <FONT_SHADOW>.
    /// </summary>
    public static String STYLE_FONTSTYLE = "fontStyle";

    /// <summary>
    /// STYLE_SHAPE: Defines the key for the shape style.
    /// Possible values are any of the SHAPE_*
    /// constants.
    /// </summary>
    public static String STYLE_SHAPE = "shape";

    /// <summary>
    /// STYLE_EDGE: Takes a function that creates points.
    /// Possible values are the functions defined
    /// in <mxEdgeStyle>.
    /// </summary>
    public static String STYLE_EDGE = "edgeStyle";

    /// <summary>
    /// FONT_BOLD
    /// </summary>
    public static final int FONT_BOLD = 1;

    /// <summary>
    /// FONT_ITALIC
    /// </summary>
    public static final int FONT_ITALIC = 2;

    /// <summary>
    /// FONT_UNDERLINE
    /// </summary>
    public static final int FONT_UNDERLINE = 4;

    /// <summary>
    /// FONT_SHADOW
    /// </summary>
    public static final int FONT_SHADOW = 8;

    /// <summary>
    /// SHAPE_RECTANGLE
    /// </summary>
    public static final String SHAPE_RECTANGLE = "rectangle";

    /// <summary>
    /// SHAPE_ELLIPSE
    /// </summary>
    public static final String SHAPE_ELLIPSE = "ellipse";

    /// <summary>
    /// SHAPE_RHOMBUS
    /// </summary>
    public static final String SHAPE_RHOMBUS = "rhombus";

    /// <summary>
    /// SHAPE_LINE
    /// </summary>
    public static final String SHAPE_LINE = "line";

    /// <summary>
    /// SHAPE_IMAGE
    /// </summary>
    public static final String SHAPE_IMAGE = "image";
	
    /// <summary>
    /// SHAPE_ARROW
    /// </summary>
    public static final String SHAPE_ARROW = "arrow";
	
    /// <summary>
    /// SHAPE_LABEL
    /// </summary>
    public static final String SHAPE_LABEL = "label";
	
    /// <summary>
    /// SHAPE_CYLINDER
    /// </summary>
    public static final String SHAPE_CYLINDER = "cylinder";
	
    /// <summary>
    /// SHAPE_SWIMLANE
    /// </summary>
    public static final String SHAPE_SWIMLANE = "swimlane";
		
    /// <summary>
    /// SHAPE_CONNECTOR
    /// </summary>
    public static final String SHAPE_CONNECTOR = "connector";
		
    /// <summary>
    /// SHAPE_ACTOR
    /// </summary>
    public static final String SHAPE_ACTOR = "actor";

    /// <summary>
    /// ARROW_CLASSIC
    /// </summary>
    public static final String ARROW_CLASSIC = "classic"; // use VML values

    /// <summary>
    /// ALIGN_LEFT
    /// </summary>
    public static final String ALIGN_LEFT = "left";

    /// <summary>
    /// ALIGN_CENTER
    /// </summary>
    public static final String ALIGN_CENTER = "center";

    /// <summary>
    /// ALIGN_RIGHT
    /// </summary>
    public static final String ALIGN_RIGHT = "right";

    /// <summary>
    /// ALIGN_TOP
    /// </summary>
    public static final String ALIGN_TOP = "top";

    /// <summary>
    /// ALIGN_MIDDLE
    /// </summary>
    public static final String ALIGN_MIDDLE = "middle";

    /// <summary>
    /// ALIGN_BOTTOM
    /// </summary>
    public static final String ALIGN_BOTTOM = "bottom";

}
