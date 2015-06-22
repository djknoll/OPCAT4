/**
 * $Id: mxGraphViewGraphics2DReader.java,v 1.1 2007/01/17 10:53:25 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */

package mxgraph.reader;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import mxgraph.canvas.mxGraphics2DCanvas;
import mxgraph.canvas.mxICanvas;
import mxgraph.utils.mxUtils;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


public class mxGraphViewGraphics2DReader extends mxGraphViewReader
{

	public mxICanvas createCanvas(Hashtable<String, Object> attrs)
	{
		int width = mxUtils.getInt(attrs, "x") + mxUtils.getInt(attrs, "width")
				+ 1;
		int height = mxUtils.getInt(attrs, "y")
				+ mxUtils.getInt(attrs, "height") + 1;
		return new mxGraphics2DCanvas(width, height);
	}

	public static BufferedImage convert(String filename)
			throws ParserConfigurationException, SAXException, IOException
	{
		return convert(new InputSource(new FileInputStream(filename)));
	}

	public static BufferedImage convert(InputSource inputSource)
			throws ParserConfigurationException, SAXException, IOException
	{

		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		XMLReader reader = parser.getXMLReader();

		mxGraphViewGraphics2DReader viewReader = new mxGraphViewGraphics2DReader();
		reader.setContentHandler(viewReader);

		reader.parse(inputSource);
		BufferedImage result = ((mxGraphics2DCanvas) viewReader.getCanvas())
				.getImage();
		((mxGraphics2DCanvas) viewReader.getCanvas()).Destroy();

		return result;
	}

}
