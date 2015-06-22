/**
 * $Id: mxCellCodec.java,v 1.3 2007/02/12 15:30:30 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 */
package mxgraph.io;

import java.util.Map;

import mxgraph.model.mxCell;

import org.w3c.dom.Element;
import org.w3c.dom.Node;


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
public class mxCellCodec extends mxObjectCodec
{

	/**
	 * Constructor: mxCellCodec
	 */
	public mxCellCodec()
	{
		this(new mxCell(), null, new String[] { "parent", "source", "target" },
				null);
	}

	/**
	 * Constructor: mxCellCodec
	 */
	public mxCellCodec(Object template)
	{
		this(template, null, null, null);
	}

	/**
	 * Constructor: mxCellCodec
	 */
	public mxCellCodec(Object template, String[] exclude, String[] idrefs,
			Map mapping)
	{
		super(template, exclude, idrefs, mapping);
	}

	/**
	 * Function: isExcluded
	 */
	public boolean isExcluded(Object obj, String attr, Object value,
			boolean isWrite)
	{
		return exclude.contains(attr)
				|| (isWrite && attr.equals("value") && value instanceof Node && ((Node) value)
						.getNodeType() == Node.ELEMENT_NODE);
	}

	/**
	 * Function: afterEncode
	 */
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		if (obj instanceof mxCell && node instanceof Element)
		{
			mxCell cell = (mxCell) obj;
			if (cell.getValue() != null)
			{
				if (cell.getValue() instanceof Node)
				{
					// Wraps the graphical annotation up in the
					// user object (inversion) by putting the
					// result of the default encoding into
					// a clone of the user object (node type 1)
					// and returning this cloned user object.
					Element tmp = (Element) node;
					node = ((Element) cell.getValue()).cloneNode(true);
					node.appendChild(tmp);

					// Moves the id attribute to the outermost
					// XML node, namely the node which denotes
					// the object boundaries in the file.
					String id = tmp.getAttribute("id");
					((Element) node).setAttribute("id", id);
					tmp.removeAttribute("id");
				}
			}
		}
		return node;
	}

	/**
	 * Function: beforeDecode
	 */
	public Node beforeDecode(mxCodec dec, Node node, Object obj)
	{
		Element inner = (Element) node;
		if (obj instanceof mxCell)
		{
			mxCell cell = (mxCell) obj;
			if (!node.getNodeName().equals("mxCell"))
			{
				// Passes the inner graphical annotation node to the
				// object codec for further processing of the cell.
				Node tmp = inner.getElementsByTagName("mxCell").item(0);
				if (tmp != null && tmp.getParentNode() == node)
				{
					inner = (Element) tmp;

					// Removes annotation and whitespace from node
					Node tmp2 = tmp.getPreviousSibling();
					while (tmp2 != null && tmp2.getNodeType() == Node.TEXT_NODE)
					{
						Node tmp3 = tmp2.getPreviousSibling();
						tmp2.getParentNode().removeChild(tmp2);
						tmp2 = tmp3;
					}

					// Removes more whitespace
					tmp2 = tmp.getNextSibling();
					while (tmp2 != null && tmp2.getNodeType() == Node.TEXT_NODE)
					{
						Node tmp3 = tmp2.getPreviousSibling();
						tmp2.getParentNode().removeChild(tmp2);
						tmp2 = tmp3;
					}
					tmp.getParentNode().removeChild(tmp);
				}
				else
				{
					inner = null;
				}

				// Creates the user object out of the XML node
				Element value = (Element) node.cloneNode(true);
				cell.setValue(value);
				String id = value.getAttribute("id");
				if (id != null)
				{
					cell.setId(id);
					value.removeAttribute("id");
				}
			}

			// Preprocesses and removes all Id-references
			// in order to use the correct encoder (this)
			// for the known references to cells (all).
			if (inner != null && idrefs != null)
			{
				for (String attr : idrefs)
				{
					String ref = inner.getAttribute(attr);
					if (ref != null && ref.length() > 0)
					{
						inner.removeAttribute(attr);
						Object object = dec.objects.get(ref);
						if (object == null)
						{
							object = dec.lookup(ref);
						}

						if (object == null)
						{
							// Needs to decode forward reference
							Node element = dec.getElementById(ref);
							if (element != null)
							{
								mxObjectCodec decoder = mxCodecRegistry
										.getCodec(element.getNodeName());
								if (decoder == null)
								{
									decoder = this;
								}
								object = decoder.decode(dec, element);
							}
						}
						setFieldValue(obj, attr, object);
					}
				}
			}
		}
		return inner;
	}

}
