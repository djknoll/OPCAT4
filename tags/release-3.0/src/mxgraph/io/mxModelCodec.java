/**
 * $Id: mxModelCodec.java,v 1.2 2007/01/25 17:43:22 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 */
package mxgraph.io;

import java.util.Map;

import mxgraph.model.mxGraphModel;
import mxgraph.model.mxICell;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * Class: mxObjectCodec
 *
 * Generic codec for JavaScript objects. See below for a detailed
 * description of the encoding/decoding scheme.
 *
 * Note: Since booleans are numbers in JavaScript, all boolean
 * values are encoded into 1 for true and 0 for false. However,
 * the decoder also accepts the string true and false for
 * boolean values.
 */
public class mxModelCodec extends mxObjectCodec
{

	/**
	 * Constructor: mxModelCodec
	 */
	public mxModelCodec()
	{
		this(new mxGraphModel(), null, null, null);
	}

	/**
	 * Constructor: mxModelCodec
	 */
	public mxModelCodec(Object template)
	{
		this(template, null, null, null);
	}

	/**
	 * Constructor: mxModelCodec
	 */
	public mxModelCodec(Object template, String[] exclude, String[] idrefs,
			Map mapping)
	{
		super(template, exclude, idrefs, mapping);
	}

	/**
	 * Function: encode
	 */
	public Node encode(mxCodec enc, Object obj)
	{
		Node node = null;
		if (obj instanceof mxGraphModel)
		{
			mxGraphModel model = (mxGraphModel) obj;
			String name = mxCodecRegistry.getName(obj.getClass());
			node = enc.document.createElement(name);
			Node rootNode = enc.document.createElement("root");
			enc.encodeCell((mxICell) model.getRoot(), rootNode, true);
			node.appendChild(rootNode);
		}
		return node;
	}

	/**
	 * Function: beforeDecode
	 */
	public Node beforeDecode(mxCodec dec, Node node, Object into)
	{
		if (node instanceof Element)
		{
			Element elt = (Element) node;
			mxGraphModel model = null;
			if (into instanceof mxGraphModel)
			{
				model = (mxGraphModel) into;
			}
			else
			{
				model = new mxGraphModel();
			}

			// Reads the cells into the graph model. All cells
			// are children of the root element in the node.
			Node root = elt.getElementsByTagName("root").item(0);
			mxICell rootCell = null;
			if (root != null)
			{
				Node tmp = root.getFirstChild();
				while (tmp != null)
				{
					mxICell cell = dec.decodeCell(tmp, true);
					if (cell != null && cell.getParent() == null)
					{
						rootCell = cell;
					}
					tmp = tmp.getNextSibling();
				}
				root.getParentNode().removeChild(root);
			}

			// Reads the templates into the graph model
			NodeList arrays = elt.getElementsByTagName("Array");
			for (int i = 0; i < arrays.getLength(); i++)
			{
				Element arr = (Element) arrays.item(i);
				String role = arr.getAttribute("as");
				if (role == "templates")
				{
					decodeTemplates(dec, arr, model);
					arr.getParentNode().removeChild(arr);
				}
			}

			// Assigns the specified templates for edges
			String defaultEdge = elt.getAttribute("defaultEdge");
			if (defaultEdge != null)
			{
				elt.removeAttribute("defaultEdge");
				//model.defaultEdge = model.templates[defaultEdge];
			}

			// Assigns the specified templates for groups
			String defaultGroup = elt.getAttribute("defaultGroup");
			if (defaultGroup != null)
			{
				elt.removeAttribute("defaultGroup");
				//into.defaultGroup = model.templates[defaultGroup];
			}

			// Sets the root on the model if one has been decoded
			if (rootCell != null)
			{
				model.setRoot(rootCell);
			}
		}

		return node;
	}

	/**
	 * Function: decodeTemplates
	 */
	protected void decodeTemplates(mxCodec dec, Node node, mxGraphModel model)
	{
		//		if (model.templates == null)
		//		{
		//			model.templates = new Array();
		//		}
		//
		//		var children = mxUtils.getChildNodes(node);
		//		for (var j = 0; j < children.length; j++)
		//		{
		//			var name = children[j].getAttribute("as");
		//			var child = children[j].firstChild;
		//
		//			while (child != null && child.nodeType != 1)
		//			{
		//				child = child.nextSibling;
		//			}
		//
		//			if (child != null)
		//			{
		//				// LATER: Only single cells means you need
		//				// to group multiple cells within another
		//				// cell. This should be changed to support
		//				// arrays of cells, or the wrapper must
		//				// be automatically handled in this class.
		//				model.templates[name] = dec.decodeCell(child);
		//			}
		//		}
	}

}
