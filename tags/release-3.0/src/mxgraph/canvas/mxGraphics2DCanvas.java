/**
 * $Id: mxGraphics2DCanvas.java,v 1.10 2007/02/13 13:08:34 gaudenz Exp $
 * Copyright (c) 2007, Gaudenz Alder
 */
package mxgraph.canvas;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import mxgraph.utils.mxConstants;
import mxgraph.utils.mxPoint;
import mxgraph.utils.mxUtils;


public class mxGraphics2DCanvas implements mxICanvas
{

	public static boolean indexed = false;

	public static int defaultLineSpacing = 0;

	//CellRendererPane rendererPane = new CellRendererPane();

	//JTextPane textRenderer = new mxLightweightTextPane();

	BufferedImage image;

	Graphics2D g;

	int width;

	int height;

	public mxGraphics2DCanvas(int width, int height)
	{
		this(width, height, null);
	}

	public mxGraphics2DCanvas(int width, int height, Color backgroundColor)
	{
		//add(rendererPane);
		this.width = width;
		this.height = height;
		image = new BufferedImage(
				width,
				height,
				(backgroundColor != null) ? ((indexed) ? BufferedImage.TYPE_BYTE_INDEXED
						: BufferedImage.TYPE_INT_RGB)
						: BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) ((BufferedImage) image).createGraphics();
		if (backgroundColor != null)
		{
			g.setBackground(backgroundColor);
			g.fillRect(0, 0, width, height);
		}
		else
		{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR,
					0.0f));
			g.fillRect(0, 0, width, height);
			g.setComposite(AlphaComposite.SrcOver);
		}
		// g.SmoothingMode = SmoothingMode.HighQuality;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public void drawVertex(String text, int x, int y, int width, int height,
			Hashtable<String, Object> style)
	{
		int start = mxUtils.getInt(style, mxConstants.STYLE_STARTSIZE);
		if (start == 0)
		{
			drawShape(x, y, width, height, style);
			drawText(text, x, y, width, height, style);
		}
		else
		{
			drawShape(x, y, width, start, style);
			drawText(text, x, y, width, start, style);

			// Draws the border
			Hashtable<String, Object> cloned = new Hashtable<String, Object>(
					style);
			cloned.remove(mxConstants.STYLE_FILLCOLOR);
			drawShape(x, y, width, height, cloned);
		}
	}

	public void drawEdge(String text, List<mxPoint> pts, mxPoint label,
			Hashtable<String, Object> style)
	{
		drawLine(pts, style);

		if (label != null)
		{
			Hashtable<String, Object> labelStyle = new Hashtable<String, Object>(
					style);
			labelStyle.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_LEFT);
			labelStyle.put(mxConstants.STYLE_VERTICAL_ALIGN,
					mxConstants.ALIGN_TOP);
			drawText(text, (int) label.getX(), (int) label.getY(), 0, 0,
					labelStyle);
		}
	}

	public void drawShape(int x, int y, int width, int height,
			Hashtable<String, Object> style)
	{
		// Prepares the background
		boolean isShadow = mxUtils.isTrue(style, mxConstants.STYLE_SHADOW,
				false);
		Color fillColor = mxUtils.getColor(style, mxConstants.STYLE_FILLCOLOR);
		Paint fillPaint = null;
		if (fillColor != null)
		{
			Color gradientColor = mxUtils.getColor(style,
					mxConstants.STYLE_GRADIENTCOLOR);
			if (gradientColor != null)
			{
				fillPaint = new GradientPaint(x, y, fillColor, x, y + height,
						gradientColor, true);
			}
			else
			{
				g.setColor(fillColor);
			}
		}

		// Prepares the foreground / stroke
		Color penColor = mxUtils.getColor(style, mxConstants.STYLE_STROKECOLOR);
		float penWidth = mxUtils.getFloat(style, mxConstants.STYLE_STROKEWIDTH,
				1);
		if (penWidth > 0)
		{
			g.setStroke(new BasicStroke(penWidth));
		}

		// Draws the shape
		String shape = mxUtils.getString(style, mxConstants.STYLE_SHAPE);
		if (shape.equals(mxConstants.SHAPE_ELLIPSE))
		{
			drawOval(x, y, width, height, fillColor, fillPaint, penColor,
					isShadow);
		}
		else if (shape.equals(mxConstants.SHAPE_RHOMBUS))
		{
			drawRhombus(x, y, width, height, fillColor, fillPaint, penColor,
					isShadow);
		}
		else if (shape.equals(mxConstants.SHAPE_CYLINDER))
		{
			drawCylinder(x, y, width, height, fillColor, fillPaint, penColor,
					isShadow);
		}
		else
		{
			drawRect(x, y, width, height, fillColor, fillPaint, penColor,
					isShadow, mxUtils.isTrue(style, mxConstants.STYLE_ROUNDED));
		}
	}

	protected void drawRect(int x, int y, int width, int height,
			Color fillColor, Paint fillPaint, Color penColor, boolean isShadow,
			boolean isRounded)
	{
		int radius = (isRounded) ? getArcSize(width, height) : 0;

		if (fillColor != null || fillPaint != null)
		{
			if (isShadow)
			{
				g.setColor(mxConstants.SHADOW_COLOR);
				if (isRounded)
				{
					g.fillRoundRect(x + mxConstants.SHADOW_OFFSET, y
							+ mxConstants.SHADOW_OFFSET, width, height, radius,
							radius);
				}
				else
				{
					g.fillRect(x + mxConstants.SHADOW_OFFSET, y
							+ mxConstants.SHADOW_OFFSET, width, height);
				}
			}
			if (fillPaint != null)
			{
				((Graphics2D) g).setPaint(fillPaint);
			}
			else
			{
				g.setColor(fillColor);
			}
			if (isRounded)
			{
				g.fillRoundRect(x, y, width, height, radius, radius);
			}
			else
			{
				g.fillRect(x, y, width, height);
			}
		}
		if (penColor != null)
		{
			g.setColor(penColor);
			if (isRounded)
			{
				g.drawRoundRect(x, y, width, height, radius, radius);
			}
			else
			{
				g.drawRect(x, y, width, height);
			}
		}
	}

	protected void drawOval(int x, int y, int width, int height,
			Color fillColor, Paint fillPaint, Color penColor, boolean isShadow)
	{
		if (fillColor != null || fillPaint != null)
		{
			if (isShadow)
			{
				g.setColor(mxConstants.SHADOW_COLOR);
				g.fillOval(x + mxConstants.SHADOW_OFFSET, y
						+ mxConstants.SHADOW_OFFSET, width, height);
			}
			if (fillPaint != null)
			{
				((Graphics2D) g).setPaint(fillPaint);
			}
			else
			{
				g.setColor(fillColor);
			}
			g.fillOval(x, y, width, height);
		}
		if (penColor != null)
		{
			g.setColor(penColor);
			g.drawOval(x, y, width, height);
		}
	}

	protected void drawRhombus(int x, int y, int width, int height,
			Color fillColor, Paint fillPaint, Color penColor, boolean isShadow)
	{
		int halfWidth = width / 2;
		int halfHeight = height / 2;

		Polygon diamond = new Polygon();
		diamond.addPoint(x + halfWidth, y);
		diamond.addPoint(x + width, y + halfHeight);
		diamond.addPoint(x + halfWidth, y + height);
		diamond.addPoint(x, y + halfHeight);

		if (fillColor != null || fillPaint != null)
		{
			if (isShadow)
			{
				g.setColor(mxConstants.SHADOW_COLOR);
				((Graphics2D) g).translate(mxConstants.SHADOW_OFFSET,
						mxConstants.SHADOW_OFFSET);
				g.fillPolygon(diamond);
				((Graphics2D) g).translate(-mxConstants.SHADOW_OFFSET,
						-mxConstants.SHADOW_OFFSET);
			}
			if (fillPaint != null)
			{
				((Graphics2D) g).setPaint(fillPaint);
			}
			else
			{
				g.setColor(fillColor);
			}
			g.fillPolygon(diamond);
		}
		if (penColor != null)
		{
			g.setColor(penColor);
			g.drawPolygon(diamond);
		}
	}

	protected void drawCylinder(int x, int y, int width, int height,
			Color fillColor, Paint fillPaint, Color penColor, boolean isShadow)
	{
		int h4 = height / 4;
		int r = width - 1;

		if (fillColor != null || fillPaint != null)
		{
			Area area = new Area(new Rectangle(x, y + h4 / 2, r, height - h4));
			area.add(new Area(new Rectangle(x, y + h4 / 2, r, height - h4)));
			area.add(new Area(new Ellipse2D.Double(x, y, r, h4)));
			area.add(new Area(new Ellipse2D.Double(x, y + height - h4, r, h4)));

			if (isShadow)
			{
				g.setColor(mxConstants.SHADOW_COLOR);
				((Graphics2D) g).translate(mxConstants.SHADOW_OFFSET,
						mxConstants.SHADOW_OFFSET);
				g.fill(area);
				((Graphics2D) g).translate(-mxConstants.SHADOW_OFFSET,
						-mxConstants.SHADOW_OFFSET);
			}
			if (fillPaint != null)
			{
				((Graphics2D) g).setPaint(fillPaint);
			}
			else
			{
				g.setColor(fillColor);
			}
			g.fill(area);
		}
		if (penColor != null)
		{
			g.setColor(penColor);
			int h2 = h4 / 2;
			g.drawOval(x, y, r, h4);
			g.drawLine(x, y + h2, x, y + height - h2);
			g.drawLine(x + width - 1, y + h2, x + width - 1, y + height - h2);
			g.drawArc(x, y + height - h4, r, h4, 0, 180);
		}
	}

	public static int getArcSize(int width, int height)
	{
		int arcSize;
		if (width <= height)
		{
			arcSize = height / 5;
			if (arcSize > (width / 2))
			{
				arcSize = width / 2;
			}
		}
		else
		{
			arcSize = width / 5;
			if (arcSize > (height / 2))
			{
				arcSize = height / 2;
			}
		}
		return arcSize;
	}

	public void drawLine(List<mxPoint> pts, Hashtable<String, Object> style)
	{
		Color penColor = mxUtils.getColor(style, mxConstants.STYLE_STROKECOLOR,
				Color.black);
		float penWidth = mxUtils.getFloat(style, mxConstants.STYLE_STROKEWIDTH,
				1);
		if (penColor != null && penWidth > 0)
		{
			g.setStroke(new BasicStroke(penWidth));
			g.setColor(penColor);
			mxPoint pt = pts.get(0);
			for (int i = 0; i < pts.size(); i++)
			{
				mxPoint tmp = pts.get(i);
				g.drawLine((int) pt.getX(), (int) pt.getY(), (int) tmp.getX(),
						(int) tmp.getY());
				pt = tmp;
			}
		}
	}

	public void drawText(String text, int x, int y, int width, int height,
			Hashtable<String, Object> style)
	{
		if (text != null && text.length() > 0)
		{
			int fontSize = mxUtils
					.getInt(style, mxConstants.STYLE_FONTSIZE, 10);
			Color fontColor = mxUtils.getColor(style,
					mxConstants.STYLE_FONTCOLOR, Color.black);
			String fontFamily = mxUtils.getString(style,
					mxConstants.STYLE_FONTFAMILY, "Arial");
			if (fontFamily != null && fontColor != null && fontSize > 0)
			{
				//	Font font = new Font(fontFamily, Font.PLAIN, fontSize);
				//	int horizontalAlignment = StyleConstants.ALIGN_CENTER;
				//	setFont(textRenderer, font, horizontalAlignment, fontColor);
				//	textRenderer.setText(text);
				//	rendererPane.paintComponent(g, textRenderer, this, x, y, width,
				//		height, true);
				g.setColor(fontColor);
				g.setFont(new Font(fontFamily, Font.PLAIN, fontSize));
				drawString(text, x, y, width, height, style);
			}
		}
	}

	protected void drawString(String text, int x, int y, int width, int height,
			Hashtable<String, Object> style)
	{
		FontMetrics fm = g.getFontMetrics();
		String[] lines = text.split("\n");
		y += fm.getHeight();

		Object align = style.get(mxConstants.STYLE_ALIGN);
		Object valign = style.get(mxConstants.STYLE_VERTICAL_ALIGN);

		int dy = fm.getHeight() + defaultLineSpacing;
		if (valign == null || valign.equals(mxConstants.ALIGN_MIDDLE))
		{
			y += (height - (lines.length + 0.5) * dy) / 2;
		}
		else if (valign.equals(mxConstants.ALIGN_BOTTOM))
		{
			y += height - lines.length * dy;
		}

		for (int i = 0; i < lines.length; i++)
		{
			int tmp = x;
			if (align == null || align.equals(mxConstants.ALIGN_CENTER))
			{
				tmp = x + (width - fm.stringWidth(lines[i])) / 2;
			}
			else if (align.equals(mxConstants.ALIGN_RIGHT))
			{
				tmp = x + (width - fm.stringWidth(lines[i]));
			}
			g.drawString(lines[i], tmp, y);
			y += fm.getHeight() + defaultLineSpacing;
		}
	}

	/**
	 * Utility method for setting the font and color of a JTextPane. The result
	 * is roughly equivalent to calling setFont(...) and setForeground(...) on
	 * an AWT TextArea.
	 */
	public static void setFont(JTextPane textPane, Font font,
			int horizontalAlignment, Color c)
	{
		// FIXME: Check if font already assigned
		MutableAttributeSet attrs = textPane.getInputAttributes();
		if (font != null)
		{
			StyleConstants.setFontFamily(attrs, font.getFamily());
			StyleConstants.setFontSize(attrs, font.getSize());
			StyleConstants.setItalic(attrs,
					(font.getStyle() & Font.ITALIC) != 0);
			StyleConstants.setBold(attrs, (font.getStyle() & Font.BOLD) != 0);
		}
		if (c != null)
		{
			StyleConstants.setForeground(attrs, c);
		}
		StyledDocument doc = textPane.getStyledDocument();
		doc.setCharacterAttributes(0, doc.getLength() + 1, attrs, false);
		attrs = textPane.getInputAttributes();
		StyleConstants.setAlignment(attrs, horizontalAlignment);
		doc.setParagraphAttributes(0, doc.getLength() + 1, attrs, false);
	}

	public void Destroy()
	{
		g.dispose();
	}

}
