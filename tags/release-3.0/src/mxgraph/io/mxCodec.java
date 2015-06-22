/**
 * $Id: mxCodec.java,v 1.4 2007/02/12 15:30:30 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 */
package mxgraph.io;

import java.util.Hashtable;

import mxgraph.model.mxCell;
import mxgraph.model.mxCellPath;
import mxgraph.model.mxICell;
import mxgraph.utils.mxUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * Class: mxCodec
 * 
 * XML codec for JavaScript object graphs. See <mxObjectCodec> for a description
 * of the general encoding/decoding scheme.
 * 
 * Example:
 * 
 * The following code is used to encode a graph model:
 * 
 * (code)
 * var encoder = new mxCodec();
 * var result = encoder.encode(graph.getModel());
 * var xml = mxUtils.getXml(result);
 * (end)
 * 
 * Optionally the newlines in the XML can be coverted to <br>, in which case a '<br>'
 * argument must be passed to <mxUtils.getXml> as the second argument.
 * 
 * Adding a Codec:
 * 
 * 1. Define a default codec with a new instance of the object to be handled:
 * 
 * (code)
 * var codec = new mxObjectCodec(new mxGraphModel());
 * (end)
 * 
 * 2. Define the functions required for encoding and decoding objects:
 * 
 * (code)
 * codec.encode = function(enc, obj) { ... }
 * codec.decode = function(dec, node, into) { ... }
 * (end)
 * 
 * 3. Register the codec in <mxCodecRegistry>:
 * 
 * (code)
 * mxCodecRegistry.register(codec);
 * (end)
 * 
 * <mxObjectCodec.decode> may be used to either create a new instance of an
 * object or to configure an existing instance, in which case the into argument
 * points to the existing object. In this case, we say the codec "configures"
 * the object.
 */
public class mxCodec
{

	/**
	 * Variable: document
	 * 
	 * The owner document of the codec.
	 */
	Document document;

	/**
	 * Variable: objects
	 * 
	 * Maps from IDs to objects.
	 */
	Hashtable objects = new Hashtable();

	/**
	 * Variable: isEncodeDefaults
	 * 
	 * Specifies if default values should be encoded. Default is false.
	 */
	boolean encodeDefaults = false;

	/**
	 * 
	 * @param document
	 */
	public mxCodec()
	{
		this(mxUtils.createDocument());
	}

	/**
	 * Constructor: mxCodec
	 * 
	 * Constructs an XML encoder/decoder for the specified owner document.
	 * 
	 * Parameters:
	 * 
	 * document - Optional XML document that contains the data. If no document
	 * is specified then a new document is created using
	 * <mxUtils.createDocument>.
	 */
	public mxCodec(Document document)
	{
		if (document == null)
		{
			document = mxUtils.createDocument();
		}
		this.document = document;
	}

	/**
	 * 
	 */
	public boolean isEncodeDefaults()
	{
		return encodeDefaults;
	}

	/**
	 * 
	 */
	public void setEncodeDefaults(boolean encodeDefaults)
	{
		this.encodeDefaults = encodeDefaults;
	}

	/**
	 * 
	 * @return
	 */
	public Document getDocument()
	{
		return document;
	}

	/**
	 * 
	 * @param id
	 * @param object
	 * @return
	 */
	public Object putObject(String id, Object object)
	{
		return objects.put(id, object);
	}

	/**
	 * Function: getObject
	 * 
	 * Returns the decoded object for the element with the specified ID in
	 * <document>. If the object is not known then <lookup> is used to find an
	 * object. If no object is found, then the element with the respective ID
	 * from the document is parsed using <decode>.
	 */
	public Object getObject(String id)
	{
		Object obj = null;
		if (id != null)
		{
			obj = objects.get(id);
			if (obj == null)
			{
				obj = lookup(id);
				if (obj == null)
				{
					Node node = getElementById(id);
					if (node != null)
					{
						obj = decode(node);
					}
				}
			}
		}
		return obj;
	}

	/**
	 * Function: lookup
	 * 
	 * Hook for subclassers to implement a custom lookup mechanism for cell IDs.
	 * This implementation always returns null.
	 * 
	 * Example:
	 * 
	 * (code)
	 * var codec = new mxCodec();
	 * codec.lookup = function(id)
	 * {
	 * 		return model.getCell(id);
	 * };
	 * (end)
	 * 
	 * Parameters:
	 * 
	 * id - ID of the object to be returned.
	 */
	public Object lookup(String id)
	{
		return null;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Node getElementById(String id)
	{
		return getElementById(id, null);
	}

	/**
	 * Function: getElementById
	 * 
	 * Returns the element with the given ID from <document>. The optional attr
	 * argument specifies the name of the ID attribute. Default is "id". The
	 * XPath expression used to find the element is //*[@attr='arg'] where attr
	 * is the name of the ID attribute and arg is the given id.
	 * 
	 * Parameters:
	 * 
	 * id - String that contains the ID.
	 * attr - Optional string for the attributename. Default is id.
	 */
	public Node getElementById(String id, String attr)
	{
		if (attr == null)
		{
			attr = "id";
		}

		String expr = "//*[@" + attr + "='" + id + "']";
		return mxUtils.selectSingleNode(document, expr);
	}

	/**
	 * Function: getId
	 * 
	 * Returns the ID of the specified object. This implementation calls
	 * <reference> first and if that returns null handles the object as an
	 * <mxCell> by returning their IDs using <mxCell.getId>. If no ID exists for
	 * the given cell, then an on-the-fly ID is generated using
	 * <mxCellPath.create>.
	 * 
	 * Parameters:
	 * 
	 * obj - Object to return the ID for.
	 */
	public String getId(Object obj)
	{
		String id = null;
		if (obj != null && obj instanceof mxICell)
		{
			id = reference(obj);
			if (id == null)
			{
				id = ((mxICell) obj).getId();
				if (id == null)
				{
					// Uses an on-the-fly Id
					id = mxCellPath.create((mxICell) obj);
					if (id.length() == 0)
					{
						id = "root";
					}
				}
			}
		}
		return id;
	}

	/**
	 * Function: reference
	 * 
	 * Hook for subclassers to implement a custom method for retrieving IDs from
	 * objects. This implementation always returns null.
	 * 
	 * Example:
	 * 
	 * (code)
	 * var codec = new mxCodec();
	 * codec.reference = function(obj)
	 * {
	 *   return obj.getCustomId();
	 * };
	 * (end)
	 * 
	 * Parameters:
	 * 
	 * obj - Object who's ID should be returned.
	 */
	public String reference(Object obj)
	{
		return null;
	}

	/**
	 * Function: encode
	 * 
	 * Encodes the specified object and returns the resulting XML node.
	 * 
	 * Parameters:
	 * 
	 * obj - Object to be encoded.
	 */
	public Node encode(Object obj)
	{
		Node node = null;
		if (obj != null)
		{
			String name = mxCodecRegistry.getName(obj.getClass());
			mxObjectCodec enc = mxCodecRegistry.getCodec(name);
			if (enc != null)
			{
				node = enc.encode(this, obj);
			}
			else
			{
				System.err.println("No codec for " + name);
			}
		}
		return node;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	public Object decode(Node node)
	{
		return decode(node, null);
	}

	/**
	 * Function: decode
	 * 
	 * Decodes the given XML node. The optional "into" argument specifies an
	 * existing object to be used. If no object is given, then a new
	 * instance is created using the constructor from the codec.
	 * 
	 * The function returns the passed in object or the new instance if no
	 * object was given.
	 * 
	 * Parameters:
	 * 
	 * node - XML node to be decoded.
	 * into - Optional object to be decodec into.
	 */
	public Object decode(Node node, Object into)
	{
		Object obj = null;
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
		{
			mxObjectCodec codec = mxCodecRegistry.getCodec(node.getNodeName());
			try
			{
				obj = codec.decode(this, node, into);
			}
			catch (NullPointerException e)
			{
				System.err.println("No decoder for: " + node.getNodeName());
			}
			catch (Exception e)
			{
				System.err.println("Cannot decode " + node.getNodeName() + ": "
						+ e.getMessage());
			}
		}
		return obj;
	}

	/**
	 * Function: encodeCell
	 * 
	 * Encoding of cell hierarchies is built-into the core, but is a
	 * higher-level function that needs to be explicitely used by the
	 * respective object encoders (eg. <mxModelCodec>, <mxChildChangeCodec>
	 * and <mxRootChangeCodec>). This implementation writes the given cell
	 * and its children as a (flat) sequence into the given node. The
	 * children are not encoded if the optional isIncludeChildren is false.
	 * The function is in charge of adding the result into the given node
	 * and has no return value.
	 * 
	 * Parameters:
	 * 
	 * cell - <mxCell> to be encoded.
	 * node - Parent XML node to add the encoded cell into.
	 * isIncludeChildren - Optional boolean indicating if
	 * the function should include all descendents. Default is true.
	 */
	public void encodeCell(mxICell cell, Node node, boolean isIncludeChildren)
	{
		node.appendChild(this.encode(cell));
		if (isIncludeChildren)
		{
			int childCount = cell.getChildCount();
			for (int i = 0; i < childCount; i++)
			{
				encodeCell(cell.getChildAt(i), node, isIncludeChildren);
			}
		}
	}

	/**
	 * Function: decodeCell
	 * 
	 * Decodes cells that have been encoded using inversion, ie. where the
	 * user object is the enclosing node in the XML, and restores the group
	 * and graph structure in the cells. Returns a new <mxCell> instance
	 * that represents the given node.
	 * 
	 * Parameters:
	 * 
	 * node - XML node that contains the cell data.
	 * isRestoreStructures - Optional boolean indicating whether the graph
	 * structure should be restored by calling insert and insertEdge on the
	 * parent and terminals, respectively. Default is true.
	 */
	public mxICell decodeCell(Node node, boolean isRestoreStructures)
	{
		mxICell cell = null;
		if (node != null && node.getNodeType() == Node.ELEMENT_NODE)
		{
			// Hardcodes the decoder because the name of the
			// node is not mxCell, but the name of the user
			// object inside the cell (inversion).
			String name = mxCodecRegistry.getName(mxCell.class);
			mxObjectCodec decoder = mxCodecRegistry.getCodec(name);
			cell = (mxICell) decoder.decode(this, node);
			if (isRestoreStructures)
			{
				mxICell parent = cell.getParent();
				if (parent != null)
				{
					parent.insert(cell);
				}

				mxICell source = cell.getTerminal(true);
				if (source != null)
				{
					source.insertEdge(cell, true);
				}

				mxICell target = cell.getTerminal(false);
				if (target != null)
				{
					target.insertEdge(cell, false);
				}
			}
		}
		return cell;
	}

	/**
	 * Function: setAttribute
	 *
	 * Sets the attribute on the specified node to value. This is a
	 * helper method that makes sure the attribute and value arguments
	 * are not null.
	 *
	 * Parameters:
	 *
	 * node - XML node to set the attribute for.
	 * attributes - Attributename to be set.
	 * value - New value of the attribute.
	 */
	public static void setAttribute(Node node, String attribute, Object value)
	{
		if (node.getNodeType() == Node.ELEMENT_NODE && attribute != null
				&& value != null)
		{
			((Element) node).setAttribute(attribute, String.valueOf(value));
		}
	}

}
