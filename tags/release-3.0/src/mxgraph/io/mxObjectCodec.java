/**
 * $Id: mxObjectCodec.java,v 1.8 2007/02/13 08:02:30 gaudenz Exp $
 * Copyright (c) 2006, Gaudenz Alder
 */
package mxgraph.io;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import mxgraph.utils.mxUtils;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
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
public class mxObjectCodec
{

	/**
	 * 
	 */
	static Set<String> EMPTY_SET = new HashSet<String>();

	/**
	 * 
	 */
	Object template;

	/**
	 * Variable: exclude
	 *
	 * Array containing the variable names that should be
	 * ignored by the codec.
	 */
	Set<String> exclude;

	/**
	 * Variable: idrefs
	 *
	 * Array containing the variable names that should be
	 * turned into or converted from references. See
	 * <mxCodec.getId> and <mxCodec.getObject>.
	 */
	Set<String> idrefs;

	/**
	 * Variable: mapping
	 *
	 * Maps from from fieldnames to XML attribute names.
	 */
	Map mapping;

	/**
	 * Variable: reverse
	 *
	 * Maps from from XML attribute names to fieldnames.
	 */
	Map reverse;

	/**
	 * Constructor: mxObjectCodec
	 *
	 * Constructs a codec for the specified template object.
	 */
	public mxObjectCodec(Object template)
	{
		this(template, null, null, null);
	}

	/**
	 * Constructor: mxObjectCodec
	 *
	 * Constructs a codec for the specified template object.
	 * The variables in the optional exclude array are ignored by
	 * the codec. Variables in the optional idrefs array are
	 * turned into references in the XML. The optional mapping
	 * may be used to map from variable names to XML attributes.
	 * The argument is created as follows:
	 *
	 * (code)
	 * var mapping = new Object();
	 * mapping['variableName'] = 'attribute-name';
	 * (end)
	 *
	 * Parameters:
	 *
	 * template - Prototypical instance of the object to be
	 * encoded/decoded.
	 * exclude - Optional array of fieldnames to be ignored.
	 * idrefs - Optional array of fieldnames to be converted to/from
	 * references.
	 * mapping - Optional mapping from field- to attributenames.
	 */
	public mxObjectCodec(Object template, String[] exclude, String[] idrefs,
			Map mapping)
	{
		this.template = template;

		if (exclude != null)
		{
			this.exclude = new HashSet<String>();
			for (String s : exclude)
			{
				this.exclude.add(s);
			}
		}
		else
		{
			this.exclude = EMPTY_SET;
		}

		if (idrefs != null)
		{
			this.idrefs = new HashSet<String>();
			for (String s : idrefs)
			{
				this.idrefs.add(s);
			}
		}
		else
		{
			this.idrefs = EMPTY_SET;
		}

		if (mapping == null)
		{
			mapping = new Hashtable();
		}
		this.mapping = mapping;

		reverse = new Hashtable();
		Iterator it = mapping.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry e = (Map.Entry) it.next();
			reverse.put(e.getValue(), e.getKey());
		}
	}

	/**
	 * 
	 * @return
	 */
	public Object getTemplate()
	{
		return template;
	}

	/**
	 * 
	 * @return
	 */
	protected Object cloneTemplate(Node node)
	{
		Object obj = null;
		try
		{
			obj = template.getClass().newInstance();

			// Special case: Check if the collection
			// should be a map. This is if the first
			// child has an "as"-attribute. This
			// assumes that all childs will have
			// as attributes in this case. This is
			// required because in JavaScript, the
			// map and array object are the same.
			if (obj instanceof Collection)
			{
				node = node.getFirstChild();
				if (node.getAttributes().getNamedItem("as") != null)
				{
					obj = new Hashtable();
				}
			}
		}
		catch (InstantiationException e)
		{
			// ignore
		}
		catch (IllegalAccessException e)
		{
			// ignore
		}
		return obj;
	}

	/**
	 * Function: isExcluded
	 *
	 * Returns true if the given attribute is to be ignored
	 * by the codec. This implementation returns true if the
	 * given fieldname is in <exclude>.
	 *
	 * Parameters:
	 *
	 * obj - Object instance that contains the field.
	 * attr - Fieldname of the field.
	 * value - Value of the field.
	 * isWrite - Boolean indicating if the field is being
	 * encoded or decoded. isWrite is true if the field is being
	 * encoded, else it is being decoded.
	 */
	public boolean isExcluded(Object obj, String attr, Object value,
			boolean isWrite)
	{
		return exclude.contains(attr);
	}

	/**
	 * Function: isReference
	 *
	 * Returns true if the given fieldname is to be treated
	 * as a textual reference (ID). This implementation returns
	 * true if the given fieldname is in <idrefs>.
	 *
	 * Parameters:
	 *
	 * obj - Object instance that contains the field.
	 * attr - Fieldname of the field.
	 * value - Value of the field. 
	 * isWrite - Boolean indicating if the field is being
	 * encoded or decoded. isWrite is true if the field is being
	 * encoded, else it is being decoded.
	 */
	public boolean isReference(Object obj, String attr, Object value,
			boolean isWrite)
	{
		return idrefs.contains(attr);
	}

	/**
	 * Function: encode
	 *
	 * Encodes the specified object and returns a node
	 * representing then given object. Calls <beforeEncode>
	 * after creating the node and <afterEncode> with the 
	 * resulting node after processing.
	 *
	 * Enc is a reference to the calling encoder. It is used
	 * to encode complex objects and create references.
	 *
	 * This implementation encodes all variables of an
	 * object according to the following rules:
	 *
	 * - If the variable name is in <exclude> then it is ignored.
	 * - If the variable name is in <idrefs> then <mxCodec.getId>
	 * is used to replace the object with its ID.
	 * - The variable name is mapped using <mapping>.
	 * - If obj is an array and the variable name is numeric
	 * (ie. an index) then it is not encoded.
	 * - If the value is an object, then the codec is used to
	 * create a child node with the variable name encoded into
	 * the "as" attribute.
	 * - Else, if <isEncodeDefaults> is true or the value differs
	 * from the template value, then ...
	 * - ... if obj is not an array, then the value is mapped to
	 * an attribute.
	 * - ... else if obj is an array, the value is mapped to an
	 * add child with a value attribute or a text child node,
	 * if the value is a function.
	 *
	 * If no ID exists for a variable in <idrefs> or if an object
	 * cannot be encoded, a warning is issued using <mxLog.warn>.
	 *
	 * Returns the resulting XML node that represents the given
	 * object.
	 *
	 * Parameters:
	 *
	 * enc - <mxCodec> that controls the encoding process.
	 * obj - Object to be encoded.
	 */
	public Node encode(mxCodec enc, Object obj)
	{
		String name = mxCodecRegistry.getName(obj.getClass());
		Node node = enc.document.createElement(name);
		obj = beforeEncode(enc, obj, node);
		encodeObject(enc, obj, node);
		return afterEncode(enc, obj, node);
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param node
	 */
	protected void encodeObject(mxCodec enc, Object obj, Node node)
	{
		mxCodec.setAttribute(node, "id", enc.getId(obj));
		encodeFields(enc, obj, node);
		encodeElements(enc, obj, node);
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param node
	 */
	protected void encodeFields(mxCodec enc, Object obj, Node node)
	{
		Class type = obj.getClass();
		while (type != null)
		{
			Field[] fields = type.getDeclaredFields();
			for (Field f : fields)
			{
				if ((f.getModifiers() & Modifier.TRANSIENT) != Modifier.TRANSIENT)
				{
					String fieldname = f.getName();
					Object value = getFieldValue(obj, fieldname);
					encodeValue(enc, obj, fieldname, value, node);
				}
			}
			type = type.getSuperclass();
		}
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param node
	 */
	protected void encodeElements(mxCodec enc, Object obj, Node node)
	{
		if (obj.getClass().isArray())
		{
			for (Object o : (Object[]) obj)
			{
				encodeValue(enc, obj, null, o, node);
			}
		}
		else if (obj instanceof Map)
		{
			Iterator<Map.Entry> it = ((Map) obj).entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry e = it.next();
				encodeValue(enc, obj, String.valueOf(e.getKey()), e.getValue(),
						node);
			}
		}
		else if (obj instanceof Collection)
		{
			Iterator it = ((Collection) obj).iterator();
			while (it.hasNext())
			{
				Object value = it.next();
				encodeValue(enc, obj, null, value, node);
			}
		}
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param fieldname
	 * @param value
	 * @param node
	 */
	protected void encodeValue(mxCodec enc, Object obj, String fieldname,
			Object value, Node node)
	{
		if (value != null && !isExcluded(obj, fieldname, value, true))
		{
			if (isReference(obj, fieldname, value, true))
			{
				Object tmp = enc.getId(value);
				if (tmp == null)
				{
					System.err.println("mxObjectCodec.encode: No ID for "
							+ mxCodecRegistry.getName(obj.getClass()) + "."
							+ fieldname + "=" + value);
					return; // exit
				}
				value = tmp;
			}

			Object defaultValue = getFieldValue(template, fieldname);
			if (fieldname == null || enc.isEncodeDefaults()
					|| defaultValue == null || !defaultValue.equals(value))
			{
				writeAttribute(enc, obj, getAttributeName(fieldname), value,
						node);
			}
		}
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param attr
	 * @param value
	 * @param node
	 */
	protected boolean isPrimitiveValue(Object value)
	{
		return value instanceof String || value instanceof Boolean
				|| value instanceof Character || value instanceof Byte
				|| value instanceof Short || value instanceof Integer
				|| value instanceof Long || value instanceof Float
				|| value instanceof Double || value.getClass().isPrimitive();
	}

	/**
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	protected Object convertValueFromXml(Class type, Object value)
	{
		if (value instanceof String && type.isPrimitive())
		{
			String tmp = (String) value;
			if (type.equals(boolean.class))
			{
				if (tmp.equals("1") || tmp.equals("0"))
				{
					tmp = (tmp.equals("1")) ? "true" : "false";
				}
				value = new Boolean(tmp);
			}
			else if (type.equals(char.class))
			{
				value = new Character(tmp.charAt(0));
			}
			else if (type.equals(byte.class))
			{
				value = new Byte(tmp);
			}
			else if (type.equals(short.class))
			{
				value = new Short(tmp);
			}
			else if (type.equals(int.class))
			{
				value = new Integer(tmp);
			}
			else if (type.equals(long.class))
			{
				value = new Long(tmp);
			}
			else if (type.equals(float.class))
			{
				value = new Float(tmp);
			}
			else if (type.equals(double.class))
			{
				value = new Double(tmp);
			}
		}
		return value;
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param attr
	 * @param value
	 * @param node
	 */
	protected void writeAttribute(mxCodec enc, Object obj, String attr,
			Object value, Node node)
	{
		value = convertValueToXml(value);
		if (isPrimitiveValue(value))
		{
			writePrimitiveAttribute(enc, obj, attr, value, node);
		}
		else
		{
			writeComplexAttribute(enc, obj, attr, value, node);
		}
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param attr
	 * @param value
	 * @param node
	 */
	protected void writePrimitiveAttribute(mxCodec enc, Object obj,
			String attr, Object value, Node node)
	{
		if (attr == null || obj instanceof Map)
		{
			Node child = enc.document.createElement("add");
			if (attr != null)
			{
				mxCodec.setAttribute(child, "as", attr);
			}
			mxCodec.setAttribute(child, "value", value);
			node.appendChild(child);
		}
		else
		{
			mxCodec.setAttribute(node, attr, value);
		}
	}

	/**
	 * 
	 * @param enc
	 * @param obj
	 * @param attr
	 * @param value
	 * @param node
	 */
	protected void writeComplexAttribute(mxCodec enc, Object obj, String attr,
			Object value, Node node)
	{
		Node child = enc.encode(value);
		if (child != null)
		{
			if (attr != null)
			{
				mxCodec.setAttribute(child, "as", attr);
			}
			node.appendChild(child);
		}
		else
		{
			System.err.println("mxObjectCodec.encode: No node for "
					+ mxCodecRegistry.getName(obj.getClass()) + "." + attr
					+ ": " + value);
		}
	}

	/**
	 * Makes sure to encode boolean values as numeric values 0
	 * or 1 (0=false, 1=true). No other conversions are applied
	 * here.
	 */
	protected Object convertValueToXml(Object value)
	{
		if (value instanceof Boolean)
		{
			value = ((Boolean) value).booleanValue() ? "1" : "0";
		}
		return value;
	}

	/**
	 * Returns the XML node attribute name for the given
	 * Java field name. That is, it returns the mapping
	 * of the field name.
	 * 
	 * @param fieldname
	 * @return
	 */
	protected String getAttributeName(String fieldname)
	{
		Object mapped = mapping.get(fieldname);
		if (mapped != null)
		{
			fieldname = mapped.toString();
		}
		return fieldname;
	}

	/**
	 * Returns the Java field name for the given XML attribute
	 * name. That is, it returns the reverse mapping of the
	 * attribute name.
	 * 
	 * @param attributename The attribute name to be mapped.
	 * @return String that represents the mapped field name.
	 */
	protected String getFieldName(String attributename)
	{
		Object mapped = reverse.get(attributename);
		if (mapped != null)
		{
			attributename = mapped.toString();
		}
		return attributename;
	}

	/**
	 * 
	 */
	protected Field getField(Object obj, String fieldname)
	{
		Class type = obj.getClass();
		while (type != null)
		{
			try
			{
				Field field = type.getDeclaredField(fieldname);
				if (field != null)
				{
					return field;
				}
			}
			catch (Exception e)
			{
				// ignore
			}
			type = type.getSuperclass();
		}
		return null;
	}

	/**
	 * 
	 */
	protected Method getAccessor(Object obj, Field field, boolean isGetter)
	{
		String name = field.getName();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		if (!isGetter)
		{
			name = "set" + name;
		}
		else if (boolean.class.isAssignableFrom(field.getType()))
		{
			name = "is" + name;
		}
		else
		{
			name = "get" + name;
		}
		try
		{
			if (isGetter)
			{
				return getMethod(obj, name, null);
			}
			else
			{
				return getMethod(obj, name, new Class[] { field.getType() });
			}
		}
		catch (Exception e1)
		{
			// ignore
		}
		return null;
	}

	/**
	 * 
	 */
	protected Method getMethod(Object obj, String methodname, Class[] params)
	{
		Class type = obj.getClass();
		while (type != null)
		{
			try
			{
				Method method = type.getDeclaredMethod(methodname, params);
				if (method != null)
				{
					return method;
				}
			}
			catch (Exception e)
			{
				// ignore
			}
			type = type.getSuperclass();
		}
		return null;
	}

	/**
	 * Returns the value of the field with the specified name
	 * in the specified object instance.
	 * 
	 * @param obj The instance to use for lookup up the value.
	 * @param fieldname The fieldname to return the value for.
	 * @return An object representing the value of the field.
	 */
	protected Object getFieldValue(Object obj, String fieldname)
	{
		Object value = null;
		if (obj != null && fieldname != null)
		{
			Field field = getField(obj, fieldname);
			try
			{
				if (field != null)
				{
					value = field.get(template);
				}
			}
			catch (IllegalAccessException e1)
			{
				if (field != null)
				{
					try
					{
						Method method = getAccessor(obj, field, true);
						value = method.invoke(obj, (Object[]) null);
					}
					catch (Exception e2)
					{
						// ignore
					}
				}
			}
			catch (Exception e)
			{
				// ignore
			}
		}
		return value;
	}

	/**
	 * 
	 * @param obj
	 * @param fieldname
	 * @param value
	 */
	protected void setFieldValue(Object obj, String fieldname, Object value)
	{
		Field field = null;
		try
		{
			field = getField(obj, fieldname);
			if (field.getType() == Boolean.class)
			{
				value = value.equals("1")
						|| String.valueOf(value).equalsIgnoreCase("true");
			}
			field.set(obj, value);
		}
		catch (IllegalAccessException e1)
		{
			if (field != null)
			{
				try
				{
					Method method = getAccessor(obj, field, false);
					Class type = method.getParameterTypes()[0];
					value = convertValueFromXml(type, value);
					method.invoke(obj, new Object[] { value });
				}
				catch (Exception e2)
				{
					System.err.println("setFieldValue: " + e2 + " on "
							+ obj.getClass().getSimpleName() + "." + fieldname
							+ " (" + field.getType().getSimpleName() + ") = "
							+ value + " (" + value.getClass().getSimpleName()
							+ ")");
				}
			}
		}
		catch (Exception e)
		{
			// ignore
		}
	}

	/**
	 * Function: beforeEncode
	 *
	 * Hook for subclassers to pre-process the object before
	 * encoding. This returns the input object. The return
	 * value of this function is used in <encode> to perform
	 * the default encoding into the given node.
	 *
	 * Parameters:
	 *
	 * enc - <mxCodec> that controls the encoding process.
	 * obj - Object to be encoded.
	 * node - XML node to encode the object into.
	 */
	public Object beforeEncode(mxCodec enc, Object obj, Node node)
	{
		return obj;
	}

	/**
	 * Function: afterEncode
	 *
	 * Hook for subclassers to post-process the node
	 * for the given object after encoding and return the
	 * post-processed node. This implementation returns 
	 * the input node. The return value of this method
	 * is returned to the encoder from <encode>.
	 *
	 * Parameters:
	 *
	 * enc - <mxCodec> that controls the encoding process.
	 * obj - Object to be encoded.
	 * node - XML node that represents the default encoding.
	 */
	public Node afterEncode(mxCodec enc, Object obj, Node node)
	{
		return node;
	}

	/**
	 * 
	 * @param dec
	 * @param node
	 * @return
	 */
	public Object decode(mxCodec dec, Node node)
	{
		return decode(dec, node, null);
	}

	/**
	 * Function: decode
	 *
	 * Parses the given node into the object or returns a new object
	 * representing the given node.
	 *
	 * Dec is a reference to the calling decoder. It is used to decode
	 * complex objects and resolve references.
	 *
	 * If a node has an id attribute then the object cache is checked for the
	 * object. If the object is not yet in the cache then it is constructed
	 * using the constructor of <template> and cached in <mxCodec.objects>.
	 *
	 * This implementation decodes all attributes and childs of a node
	 * according to the following rules:
	 *
	 * - If the variable name is in <exclude> or if the attribute name is "id"
	 * or "as" then it is ignored.
	 * - If the variable name is in <idrefs> then <mxCodec.getObject> is used
	 * to replace the reference with an object.
	 * - The variable name is mapped using a reverse <mapping>.
	 * - If the value has a child node, then the codec is used to create a
	 * child object with the variable name taken from the "as" attribute.
	 * - If the object is an array and the variable name is empty then the
	 * value or child object is appended to the array.
	 * - If an add child has no value or the object is not an array then
	 * the child text content is evaluated using <mxUtils.eval>.
	 *
	 * If no object exists for an ID in <idrefs> a warning is issued
	 * using <mxLog.warn>.
	 *
	 * Returns the resulting object that represents the given XML
	 * node or the configured given object.
	 *
	 * Parameters:
	 *
	 * enc - <mxCodec> that controls the encoding process.
	 * node - XML node to be decoded.
	 * into - Optional objec to encode the node into.
	 */
	public Object decode(mxCodec dec, Node node, Object into)
	{
		Object obj = null;
		if (node.getNodeType() == Node.ELEMENT_NODE)
		{
			String id = ((Element) node).getAttribute("id");
			obj = dec.objects.get(id);
			if (obj == null)
			{
				obj = into;
				if (obj == null)
				{
					obj = cloneTemplate(node);
				}
				if (id != null && id.length() > 0)
				{
					dec.putObject(id, obj);
				}
			}
			node = beforeDecode(dec, node, obj);
			decodeNode(dec, node, obj);
			obj = afterDecode(dec, node, obj);
		}
		return obj;
	}

	/**
	 * 
	 * @param dec
	 * @param node
	 * @param obj
	 */
	protected void decodeNode(mxCodec dec, Node node, Object obj)
	{
		if (node != null)
		{
			decodeAttributes(dec, node, obj);
			decodeChildren(dec, node, obj);
		}
	}

	/**
	 * 
	 * @param dec
	 * @param node
	 * @param obj
	 */
	protected void decodeAttributes(mxCodec dec, Node node, Object obj)
	{
		NamedNodeMap attrs = node.getAttributes();
		if (attrs != null)
		{
			for (int i = 0; i < attrs.getLength(); i++)
			{
				Node attr = attrs.item(i);
				decodeAttribute(dec, attr, obj);
			}
		}
	}

	/**
	 * 
	 * @param dec
	 * @param obj
	 * @param attr
	 */
	protected void decodeAttribute(mxCodec dec, Node attr, Object obj)
	{
		String name = attr.getNodeName();
		if (!name.equalsIgnoreCase("as") && !name.equalsIgnoreCase("id"))
		{
			Object value = attr.getNodeValue();
			String fieldname = getFieldName(name);
			if (isReference(obj, fieldname, value, false))
			{
				Object tmp = dec.getObject(String.valueOf(value));
				if (tmp == null)
				{
					System.err.println("mxObjectCodec.decode: No object for "
							+ mxCodecRegistry.getName(obj.getClass()) + "."
							+ fieldname + "=" + value);
					return; // exit
				}
				value = tmp;
			}

			if (!isExcluded(obj, fieldname, value, false))
			{
				setFieldValue(obj, fieldname, value);
				//mxLog.debug(type+"."+name+"="+value);
			}
		}
	}

	/**
	 * 
	 * @param dec
	 * @param node
	 * @param obj
	 */
	protected void decodeChildren(mxCodec dec, Node node, Object obj)
	{
		Node child = node.getFirstChild();
		while (child != null)
		{
			if (child.getNodeType() == Node.ELEMENT_NODE
					&& !this.processInclude(dec, child, obj))
			{
				decodeChild(dec, child, obj);
			}
			child = child.getNextSibling();
		}
	}

	/**
	 * 
	 * @param dec
	 * @param node
	 * @param obj
	 * @param child
	 */
	protected void decodeChild(mxCodec dec, Node child, Object obj)
	{
		String fieldname = ((Element) child).getAttribute("as");
		if (fieldname == null || !isExcluded(obj, fieldname, child, false))
		{
			Object value = null;
			Object template = getFieldValue(obj, fieldname); // FIXME: obj vs. into?
			if (child.getNodeName().equals("add"))
			{
				value = ((Element) child).getAttribute("value");
				if (value == null)
				{
					value = child.getTextContent();
					//mxLog.debug("Decoded "+role+" "+mxUtils.getTextContent(child));
				}
			}
			else
			{
				value = dec.decode(child, template);
				//System.out.println("Decoded " + child.getNodeName() + "."
				//		+ fieldname + "=" + value);
			}

			if (value != null && !value.equals(template))
			{
				if (fieldname != null && obj instanceof Map)
				{
					((Map) obj).put(fieldname, value);
				}
				else if (fieldname != null)
				{
					setFieldValue(obj, fieldname, value);
				}
				else if (obj instanceof Collection)
				{
					((Collection) obj).add(value);
				}
				else if (obj.getClass().isArray())
				{
					// FIXME: add to array
				}
				//mxLog.debug("Decoded "+type+"."+role+": "+tmp);
			}
		}
	}

	/**
	 * Function: processInclude
	 *
	 * Returns true if the given node is an include directive and
	 * executes the include by decoding the XML document. Returns
	 * false if the given node is not an include directive.
	 *
	 * Parameters:
	 *
	 * dec - <mxCodec> that controls the encoding/decoding process.
	 * node - XML node to be checked.
	 * into - Optional object to pass-thru to the codec.
	 */
	public boolean processInclude(mxCodec dec, Node node, Object into)
	{
		if (node.getNodeType() == Node.ELEMENT_NODE
				&& node.getNodeName().equalsIgnoreCase("include"))
		{
			String name = ((Element) node).getAttribute("name");
			if (name != null)
			{
				Node xml = mxUtils.loadDocument(name).getDocumentElement();
				if (xml != null)
				{
					dec.decode(xml, into);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Function: beforeDecode
	 *
	 * Hook for subclassers to pre-process the node for
	 * the specified object and return the node to be
	 * used for further processing by <decode>.
	 * The object is created based on the template in the 
	 * calling method and is never null. This implementation
	 * returns the input node. The return value of this
	 * function is used in <decode> to perform
	 * the default decoding into the given object.
	 *
	 * Parameters:
	 *
	 * dec - <mxCodec> that controls the decoding process.
	 * node - XML node to be decoded.
	 * obj - Object to encode the node into.
	 */
	public Node beforeDecode(mxCodec dec, Node node, Object obj)
	{
		return node;
	}

	/**
	 * Function: afterDecode
	 *
	 * Hook for subclassers to post-process the object after
	 * decoding. This implementation returns the given object
	 * without any changes. The return value of this method
	 * is returned to the decoder from <decode>.
	 *
	 * Parameters:
	 *
	 * enc - <mxCodec> that controls the encoding process.
	 * node - XML node to be decoded.
	 * obj - Object that represents the default decoding.
	 */
	public Object afterDecode(mxCodec dec, Node node, Object obj)
	{
		return obj;
	}

}
