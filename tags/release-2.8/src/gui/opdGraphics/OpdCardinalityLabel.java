package gui.opdGraphics;

import exportedAPI.OpcatConstants;
import exportedAPI.RelativeConnectionPoint;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdProject.GenericTable;
import gui.opdProject.OpdProject;
import gui.opdProject.StateMachine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

public class OpdCardinalityLabel extends BaseGraphicComponent
{
	private String text;
	protected static final double SELECTION_OFFSET = 3.0;

	public OpdCardinalityLabel(String text, OpdProject pProject)
	{
		super(pProject);
//		setCurrentFont(new Font("new", Font.PLAIN, 9));
		setText(text);
        setMoveable(false);
	}

	public void setText(String text)
	{
		if (text == null) return;
//		if (text.compareTo("1") == 0)
//		{
//			this.text = " ";
//		}
//		else
//		{
//			this.text = text;
//		}

		this.text = GraphicsUtils.convertCardinality2GrRepr(text.trim()).trim();

		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)config.getProperty("LabelFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;
		currentFont = currentFont.deriveFont((float)(currentFont.getSize2D()*factor));
        int selOffset = Math.round((float)(SELECTION_OFFSET*factor));

		currentMetrics = getFontMetrics(currentFont);

		setSize((int)Math.round(2*selOffset+currentMetrics.stringWidth(text)*1.3),
				(int)Math.round(2*selOffset+currentMetrics.getAscent()*1.3));
	}

	public void recalculateSize()
	{
		if (text == null)
		{
			return;
		}

		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)config.getProperty("LabelFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;
		currentFont = currentFont.deriveFont((float)(currentFont.getSize2D()*factor));
        int selOffset = Math.round((float)(SELECTION_OFFSET*factor));

		currentMetrics = getFontMetrics(currentFont);
		setSize((int)Math.round(2*selOffset+currentMetrics.stringWidth(text)*1.3),
				(int)Math.round(2*selOffset+currentMetrics.getAscent()*1.3));

	}
	public String getText()
	{
	  return text;
	}

	protected void paintComponent(Graphics g)
	{
		if (text == null || text.equals(""))
		{
		  //System.err.println("1");
		  return;
		}

		Graphics2D g2 = (Graphics2D)g;
		Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);


		GenericTable config = myProject.getConfiguration();
		Font currentFont = (Font)config.getProperty("LabelFont");
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double factor = currentSize/normalSize;

		currentFont = currentFont.deriveFont((float)(currentFont.getSize2D()*factor));

		currentMetrics = getFontMetrics(currentFont);
		g2.setFont(currentFont);

		g2.setColor(backgroundColor);
		//g2.fillRect(0, 0,  getWidth(), getHeight());
		g2.setColor(textColor);

		int stringX = (getWidth()-currentMetrics.stringWidth(text))/2;
		int stringY = (getHeight()+currentMetrics.getAscent())/2;
		g2.drawString(text,stringX,stringY);
        if (isSelected())
        {
            drawSelection(g2);
        }

	}

    public void drawSelection(Graphics2D g)
    {
        Stroke oldStroke = g.getStroke();

        g.setStroke(new BasicStroke());

        GenericTable config = myProject.getConfiguration();
        double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
        double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
        double factor = currentSize/normalSize;
        int selOffset = Math.round((float)(SELECTION_OFFSET*factor));
        int dSelOffset = 2*selOffset;

        g.setColor(Color.white);
        g.fillRect(0,0,dSelOffset,dSelOffset);
        g.fillRect(getActualWidth()-1-selOffset*2,0,dSelOffset,dSelOffset);
        g.fillRect(0,getActualHeight()-1-dSelOffset,dSelOffset,dSelOffset);
        g.fillRect(getActualWidth()-1-dSelOffset,getActualHeight()-1-dSelOffset,dSelOffset,dSelOffset);

        g.setColor(Color.black);
        g.drawRect(0,0, dSelOffset, dSelOffset);
        g.drawRect(getActualWidth()-1-dSelOffset,0,dSelOffset,dSelOffset);
        g.drawRect(0,getActualHeight()-1-dSelOffset,dSelOffset,dSelOffset);
        g.drawRect(getActualWidth()-1-dSelOffset,getActualHeight()-1-dSelOffset,dSelOffset,dSelOffset);

        g.setStroke(oldStroke);
    }

    public void mousePressed(MouseEvent e)
    {
        if (StateMachine.isWaiting())
        {
            return;
        }

        myProject.addSelection(this, true);
        super.mousePressed(e);
    }


	public void callPropertiesDialog(int showTabs, int showButtons)
	{
	}

	public void callShowUrl()
	{
	}
//	public void mouseReleased(MouseEvent e)
//	{
//		JLayeredPane tc = (JLayeredPane)this.getParent();
//		tc.setLayer(this, JLayeredPane.MODAL_LAYER.intValue()); //((int)JLayeredPane.DRAG_LAYER));
//	}


	public boolean inMove(int pX, int pY)
	{
	if (pX>0 && pY>0 && pX<getWidth() && pY<getHeight()) return true;

		return false;
	}

	public boolean inResize(int pX, int pY)
	{
	return false;
	}

	public int whichBorder(int pX, int pY)
	{
	return OpcatConstants.NOT_IN_BORDER;
	}

	public Point getAbsoluteConnectionPoint(RelativeConnectionPoint pPoint)
	{
	  return null;
	}

	public boolean isInAdjacentArea(int x, int y)
	{
		return contains(x,y);
	}

	public void showPopupMenu(int pX, int pY){}
	protected void addObjects2Move() {}

}