/**
 * $Id: mxGraph.java,v 1.7 2007/02/09 18:39:34 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.view;

import java.awt.Image;
import java.util.Hashtable;

import mxgraph.canvas.mxGraphics2DCanvas;
import mxgraph.canvas.mxICanvas;
import mxgraph.model.mxGraphModel;
import mxgraph.model.mxIGraphModel;
import mxgraph.model.mxIGraphModel.mxGraphModelChangeListener;
import mxgraph.utils.mxRectangle;


public class mxGraph
{

    static Hashtable<String, Object> EMPTY_STYLE = new Hashtable<String, Object>();

    // / <summary>
    // / model: Holds the graph model.
    // / </summary>
    mxIGraphModel model;

    // / <summary>
    // / stylesheet: Holds the stylesheet.
    // / compound edits.
    // / </summary>
    mxStylesheet stylesheet;

    // / <summary>
    // / view: Holds the graph view.
    // / </summary>
    mxGraphView view;

    // / <summary>
    // / public MxGraph: Constructs a new graph.
    // / </summary>
    public mxGraph()
    {
    	this(null);
    }

    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    public mxGraph(mxIGraphModel model)
    {
        if (model == null)
        {
            model = new mxGraphModel();
        }
        setModel(model);
        setStylesheet(new mxStylesheet());
        setView(new mxGraphView(this));
        model.addGraphModelChangeListener(new mxGraphModelChangeListener() {

			public void graphModelChanged(mxIGraphModel sender)
			{
				mxGraph.this.graphModelChanged();
			}
        	
        });
    }

	/**
	 * @return the model
	 */
	public mxIGraphModel getModel()
	{
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(mxIGraphModel model)
	{
		this.model = model;
	}

	/**
	 * @return the stylesheet
	 */
	public mxStylesheet getStylesheet()
	{
		return stylesheet;
	}

	/**
	 * @param stylesheet the stylesheet to set
	 */
	public void setStylesheet(mxStylesheet stylesheet)
	{
		this.stylesheet = stylesheet;
	}

	/**
	 * @return the view
	 */
	public mxGraphView getView()
	{
		return view;
	}

	/**
	 * @param view the view to set
	 */
	public void setView(mxGraphView view)
	{
		this.view = view;
	}

    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    public Object getDefaultParent()
    {
        return model.GetChildAt(model.getRoot(), 0);
    }

    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    public String convertValueToString(Object cell)
    {
        Object result = model.getValue(cell);
        return (result != null) ? result.toString() : "";
    }

    // / <summary>
    // / public isSwimlane: Returns true if the given cell
    // / is a swimlane. This implementation always returns
    // / false.
    // / </summary>
    public boolean isSwimlane(Object cell)
    {
        return false;
    }

    // / <summary>
    // / public isSwimlane: Returns true if the given cell
    // / is a swimlane. This implementation always returns
    // / false.
    // / </summary>
    public boolean isCellVisible(Object cell)
    {
        return model.isVisible(cell);
    }

    // / <summary>
    // / public isSwimlane: Returns true if the given cell
    // / is a swimlane. This implementation always returns
    // / false.
    // / </summary>
    public boolean isCellCollapsed(Object cell)
    {
        return model.isCollapsed(cell);
    }
    
    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    public Hashtable<String, Object> getCellStyle(Object cell)
    {
    	Hashtable<String, Object> style = (model.isEdge(cell)) ?
            stylesheet.getDefaultEdgeStyle() :
            stylesheet.getDefaultVertexStyle();

    	String name = model.getStyle(cell);
        if (name != null)
        {
            style = stylesheet.getCellStyle(name, style);
        }

        if (style == null)
        {
            style = EMPTY_STYLE;
        }
        return style;
    }

    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    public Image createImage()
    {
        getView().validate();
        mxRectangle bounds = view.getBounds();
        int width = (int)bounds.getX() + (int)bounds.getWidth() + 1;
        int height = (int)bounds.getY() + (int)bounds.getHeight() + 1;
        mxGraphics2DCanvas canvas = new mxGraphics2DCanvas(width, height);
        draw(canvas);
        Image img = canvas.getImage();
        canvas.Destroy();
        return img;
    }

    public void draw(mxICanvas canvas)
    {
        drawCell(canvas, getModel().getRoot());
    }

    public void drawCell(mxICanvas canvas, Object cell)
    {
        mxCellState state = getView().getState(cell);
        if (state != null)
        {
            String label = convertValueToString(cell);
            if (model.isVertex(cell))
            {
                int x = (int)state.getX();
                int y = (int)state.getY();
                int width = (int)state.getWidth();
                int height = (int)state.getHeight();
                canvas.drawVertex(label, x, y, width, height, state.getStyle());
            }
            else if (model.isEdge(cell))
            {
                canvas.drawEdge(label, state.getAbsolutePoints(), state.getAbsoluteOffset(), state.getStyle());
            }
        }

        // Draws the children on top
        int childCount = model.getChildCount(cell);
        for (int i = 0; i < childCount; i++)
        {
            drawCell(canvas, model.GetChildAt(cell, i));
        }
    }

    // / <summary>
    // / public MxGraph: Constructs a new graph using the
    // / specified model.
    // / </summary>
    protected void graphModelChanged()
    {
    	getView().invalidate();
    }

}
