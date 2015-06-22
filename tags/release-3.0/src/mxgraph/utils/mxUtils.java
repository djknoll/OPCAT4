/**
 * $Id: mxUtils.java,v 1.11 2007/02/13 08:02:29 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.utils;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import com.sun.org.apache.xpath.internal.XPathAPI;

public class mxUtils
{

	/**
	 * 
	 * @param bounds
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean contains(mxRectangle bounds, double x, double y)
	{
		return (bounds.getX() <= x && bounds.getX() + bounds.getWidth() >= x
				&& bounds.getY() <= y && bounds.getY() + bounds.getHeight() >= y);
	}

	/**
	 * 
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @return
	 */
	public static mxPoint intersection(double x0, double y0, double x1,
			double y1, double x2, double y2, double x3, double y3)
	{
		// m = delta y / delta x, the slope of a line
		// b = y - mx, the axis intercept
		double m1 = (y1 - y0) / (x1 - x0);
		double b1 = y0 - m1 * x0;
		double m2 = (y3 - y2) / (x3 - x2);
		double b2 = y2 - m2 * x2;
		double x = (b1 - b2) / (m2 - m1);
		double y = m1 * x + b1;
		return new mxPoint(x, y);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static boolean isTrue(Hashtable<String, Object> dict, String key)
	{
		return isTrue(dict, key, false);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean isTrue(Hashtable<String, Object> dict, String key,
			boolean defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return Boolean.parseBoolean(value.toString());
		}
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static int getInt(Hashtable<String, Object> dict, String key)
	{
		return getInt(dict, key, 0);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static int getInt(Hashtable<String, Object> dict, String key,
			int defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return Integer.parseInt(value.toString());
		}
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static float getFloat(Hashtable<String, Object> dict, String key)
	{
		return getFloat(dict, key, 0);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static float getFloat(Hashtable<String, Object> dict, String key,
			float defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return Float.parseFloat(value.toString());
		}
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static double getDouble(Hashtable<String, Object> dict, String key)
	{
		return getDouble(dict, key, 0);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static double getDouble(Hashtable<String, Object> dict, String key,
			double defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return Double.parseDouble(value.toString());
		}
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static String getString(Hashtable<String, Object> dict, String key)
	{
		return getString(dict, key, null);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getString(Hashtable<String, Object> dict, String key,
			String defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			return value.toString();
		}
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @return
	 */
	public static Color getColor(Hashtable<String, Object> dict, String key)
	{
		return getColor(dict, key, null);
	}

	/**
	 * 
	 * @param dict
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Color getColor(Hashtable<String, Object> dict, String key,
			Color defaultValue)
	{
		Object value = dict.get(key);
		if (value == null)
		{
			return defaultValue;
		}
		else
		{
			// TODO: convert method
			return parseColor(value.toString());
		}
	}

	/**
	 * Convert a string representing a 24/32bit hex color value into a Color
	 * value.
	 * 
	 * @param colorString
	 *            the 24/32bit hex string value (ARGB)
	 * @return java.awt.Color (24bit RGB on JDK 1.1, 24/32bit ARGB on JDK1.2)
	 * @exception NumberFormatException
	 *                if the specified string cannot be interpreted as a
	 *                hexidecimal integer
	 */
	public static Color parseColor(String colorString)
			throws NumberFormatException
	{
		if (colorString.equalsIgnoreCase("white"))
		{
			return Color.white;
		}
		else if (colorString.equalsIgnoreCase("black"))
		{
			return Color.black;
		}
		else if (colorString.equalsIgnoreCase("red"))
		{
			return Color.red;
		}
		else if (colorString.equalsIgnoreCase("green"))
		{
			return Color.green;
		}
		else if (colorString.equalsIgnoreCase("blue"))
		{
			return Color.blue;
		}

		int value;
		try
		{
			value = (int) Long.parseLong(colorString, 16);
		}
		catch (NumberFormatException nfe)
		{
			value = Long.decode(colorString).intValue();
			// If decode can't catch it, throw an Exception...
		}

		// We want to test for this - if the length of the colorString
		// is less than 7, then the caller probably doesn't care about
		// transparency and wants the color to be opaque. However,
		// "0" is a common number for clear, and should be
		// transparent.
		if (colorString.length() < 7 && !colorString.equals("0"))
		{
			// Just a RGB value, use regular JDK1.1 constructor
			return new Color(value);
		}
		return new Color(value);
	}

	/**
	 * 
	 * @param color
	 * @return
	 */
	public static String getHexColorString(Color color)
	{
		return Integer.toHexString((color.getRGB() & 0x00FFFFFF)
				| (color.getAlpha() << 24));
	}

	/**
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String filename) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(filename)));
		StringBuffer result = new StringBuffer();
		String tmp = reader.readLine();
		while (tmp != null)
		{
			result.append(tmp);
			tmp = reader.readLine();
		}
		reader.close();
		return result.toString();
	}

	/**
	 * 
	 * @param in
	 * @return
	 */
	public static String getMd5Hash(String in)
	{
		StringBuffer result = new StringBuffer(32);
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(in.getBytes());
			Formatter f = new Formatter(result);
			for (byte b : md5.digest())
			{
				f.format("%02x", b);
			}
		}
		catch (NoSuchAlgorithmException ex)
		{
			ex.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static Document createDocument()
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			return parser.newDocument();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
	public static Document loadDocument(String uri)
	{
		try
		{
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			return docBuilder.parse(uri);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
//	public static Document parse(String xml)
//	{
//		try
//		{
//			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
//					.newInstance();
//			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
//			return docBuilder.parse(new InputSource(new StringReader(xml)));
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//		return null;
//	}

	/**
	 * 
	 * @param doc
	 * @param expression
	 * @return
	 */
	public static Node selectSingleNode(Document doc, String expression)
	{
		try
		{
			return XPathAPI.selectSingleNode(doc.getDocumentElement(),
					expression);
		}
		catch (TransformerException e)
		{
			// ignore
		}
		return null;
	}

	public static String getXml(Node node)
	{
		try
		{
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();

			Source src = new DOMSource(node);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			Result dest = new StreamResult(stream);
			aTransformer.transform(src, dest);

			return stream.toString("UTF-8");
		}
		catch (Exception e)
		{
			// ignore
		}
		return "";
	}

	public static String getPrettyXml(Node node)
	{
		return getPrettyXml(node, "  ", "");
	}

	/**
	 * Function: getPrettyXML
	 * XML pretty printer. TODO: Special character handling.
	 */
	public static String getPrettyXml(Node node, String tab, String indent)
	{
		StringBuffer result = new StringBuffer();
		if (node != null)
		{
			if (node.getNodeType() == Node.TEXT_NODE)
			{
				result.append(node.getNodeValue());
			}
			else
			{
				result.append(indent + "<" + node.getNodeName());
				NamedNodeMap attrs = node.getAttributes();
				if (attrs != null)
				{
					for (int i = 0; i < attrs.getLength(); i++)
					{
						// TODO: htmlEntities
						String value = attrs.item(i).getNodeValue();
						result.append(" " + attrs.item(i).getNodeName() + "=\""
								+ value + "\"");
					}
				}
				Node tmp = node.getFirstChild();
				if (tmp != null)
				{
					result.append(">\n");
					while (tmp != null)
					{
						result.append(mxUtils.getPrettyXml(tmp, tab, indent
								+ tab));
						tmp = tmp.getNextSibling();
					}
					result.append(indent + "</" + node.getNodeName() + ">\n");
				}
				else
				{
					result.append("/>\n");
				}
			}
		}
		return result.toString();
	}

}
