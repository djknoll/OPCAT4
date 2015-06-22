package gui.opdProject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import gui.projectStructure.LinkEntry; 
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import exportedAPI.opcatAPI.IOpd;
import exportedAPI.opcatAPI.IThingEntry;
import exportedAPI.opcatAPI.IThingInstance;
import exportedAPI.opcatAPIx.IXOpd;
import exportedAPI.opcatAPIx.IXThingEntry;
import exportedAPI.opcatAPIx.IXThingInstance;
import gui.opdGraphics.DraggedPoint;
import gui.opdGraphics.DrawingArea;
import gui.opdGraphics.OpdCardinalityLabel;
import gui.opdGraphics.OpdContainer;
import gui.opdGraphics.OpdOr;
import gui.opdGraphics.draggers.AroundDragger;
import gui.opdGraphics.draggers.OpdRelationDragger;
import gui.opdGraphics.draggers.TextDragger;
import gui.opdGraphics.lines.LinkingLine;
import gui.opdGraphics.opdBaseComponents.BaseGraphicComponent;
import gui.opdGraphics.opdBaseComponents.OpdBaseComponent;
import gui.projectStructure.Entry;
import gui.projectStructure.GraphicalRelationRepresentation;
import gui.projectStructure.Instance;
import gui.projectStructure.LinkInstance;
import gui.projectStructure.ThingEntry;
import gui.projectStructure.ThingInstance;
import gui.util.OpcatLogger;
import gui.util.debug.Debug;


/**
 *  This class represents an OPD.
 *  <p>This class manages all needed for an OPD: graphics, selections, relations to the repository manager.
 *  It also manages all its reletions to the project and application.</p>
 */
public class Opd implements Printable, IOpd, IXOpd
{
	private String opdName;
	private long opdId;
	private long entityInOpdMaxEntry;
	private JDesktopPane mainDesktop;
//	private Hashtable selectedItems;
	private OpdContainer opdFrame;
	private OpdProject myProject;
	private Opd parentOpd;
	private ThingEntry mainEntry;
	private ThingInstance mainInstance;
	private Selection mySelection;

        //reuseComment
        private boolean isLocked ;
        private boolean isOpenReused;
        private String reusedSystemPath;
        public Hashtable inhertRelationsOverReusedThings =new Hashtable();
        public Hashtable inhertRelationsNames=new Hashtable();
        //endReuseComment


	/**
	 *  Constructor.<br>
	 *  <strong>There is no default constructor for <code>Opd</code> class.</strong><br>
	 *  <p>Construacts new <code>Opd</code> that belongs to <code><a href = "OpdProject.html">OpdProject</a> pProject</code>,
	 *  the name <code>name</code>, ID <code>pOpdId</code>, and <code>fNode</code> as father node in <a href = "RepositoryManger.html"><code>RepositoryManger</code></a> tree.
	 *  Constructor also constructs Tree Node that represents newly created object in <a href = "RepositoryManger.html"><code>RepositoryManger</code></a>.
	 *  The new internal frame for new Opcat window is also created and added to the application.</p>
	 *  @param <code>pProject <a href = "OpdProject.html">OpdProject</a></code> the project newly creared <code>Opd</code> belongs to.
	 *  @param <code>name String</code>, the name of new <code>Opd</code> and also the title of the Opd frame.
	 *  @param <code>pOpdId long</code>, the ID of new <code>Opd</code>.
	 *  @param <code>fNode DefaultMutableTreeNode</code>, the parent <a href = "RepositoryManger.html"><code>RepositoryManger</code></a> tree node.
	 */
	public Opd(OpdProject pProject, String name, long pOpdId, Opd parent)
	{
//		selectedItems = new Hashtable();
		entityInOpdMaxEntry = 0;
		myProject = pProject;
		opdName = name;
		opdId = pOpdId;
		mainDesktop = myProject.getParentDesktop();
		parentOpd = parent;
		opdFrame = new OpdContainer(opdName, myProject, this);
		mainDesktop.add(opdFrame);
		opdFrame.setBounds(0, 0, mainDesktop.getWidth()*3/4, mainDesktop.getHeight()*3/4);
		mainEntry = null;
		mainInstance = null;
                //reuseComment
                isLocked=false;
               //endReuseComment

		try
		{
			opdFrame.setMaximum(true);
		}
		catch (java.beans.PropertyVetoException e)
		{
			OpcatLogger.logMessage(e.getMessage()) ; 
		}

		mySelection = new Selection(myProject, this);
	}

	Opd(OpdProject pProject, String name, long pOpdId, Opd parent, boolean addToDesktop)
	{
//		selectedItems = new Hashtable();
		entityInOpdMaxEntry = 0;
		myProject = pProject;
		opdName = name;
		opdId = pOpdId;
		mainDesktop = myProject.getParentDesktop();
		parentOpd = parent;
		opdFrame = new OpdContainer(opdName, myProject, this);
		opdFrame.setBounds(0, 0, mainDesktop.getWidth()*3/4, mainDesktop.getHeight()*3/4);
		mainEntry = null;
		mainInstance = null;

		if(addToDesktop)
		{
			mainDesktop.add(opdFrame);
		}
		mainEntry = null;
		mainInstance = null;

//		try
//		{
//			opdFrame.setMaximum(true);
//		}
//		catch (java.beans.PropertyVetoException e)
//		{
//		}

		mySelection = new Selection(myProject, this);
	}

	/**
	 *  Disposes the frame that represents the Opd.
	 */
	public void dispose()
	{
		opdFrame.dispose();
	}

	/**
	 *  Shows the frame that represents the Opd.
	 */
	public void show()
	{
	    opdFrame.show();
	}

	/**
	 *  @return The unique ID of the Opd.
	 */
	public long getOpdId()
	{
		return opdId;
	}

	/**
	 *  @return The name of the Opd
	 */
	public String getName()
	{
		return opdName;
	}

	public void setName(String name)
	{
		opdName = name;
		opdFrame.setTitle(name);
	}


	public Opd getParentOpd()
	{
		return parentOpd;
	}

	public IOpd getParentIOpd()
	{
		return parentOpd;
	}

	public IXOpd getParentIXOpd()
	{
		return parentOpd;
	}



	/**
	 *  Returns string representation for the <code>Opd</code>, actualy returns it's name.
	 *  @return The string representation for the <code>Opd</code>.
	 */
	public String toString()
	{
		return opdName;
	}

	/**
	 *  This method returns an <a href = "../opdGraphics/OpdContainer.html"><code>OpdContainer</code></a>
	 *  of the current <code>Opd</code>.
	 *  @return <code>OpdContainer</code> of the <code>Opd</code>.
	 */
	public OpdContainer getOpdContainer()
	{
		return opdFrame;
	}


	/**
	 *  Before adding component to Opd, we have to get ID and free entry.
	 *  This method allocates free entry for new component and ID, that actually is a entry index.
	 *  @return the unique withing this Opd ID.
	 */
  public long _getFreeEntityInOpdEntry()
	 {
	   entityInOpdMaxEntry++;
	   return entityInOpdMaxEntry;
	 }

	/**
	 *  @return <code>JDesktopPane</code> that the <code>Opd</code> belongs to.
	 *  Actualy not <code>Opd</code>, but <a href = "../opdGraphics/OpdContainer.html"><code>OpdContainer</code></a>, that represents <code>Opd</code> graphicaly, belongs.
	 */
	public JDesktopPane getDesktop()
	{
		return mainDesktop;
	}

	/**
	 *  @return <code>Opd</code>'s <a href = "../opdGraphics/DrawingArea.html"><code>DrawingArea</code></a>.
	 */
	public DrawingArea getDrawingArea()
	{
		return opdFrame.getDrawingArea();

	}


	public void applyNewBackground(Color newBackgroundColor)
	{
		GenericTable config = myProject.getConfiguration();
		Color oldColor = (Color)config.getProperty("BackgroundColor");
		config.setProperty("BackgroundColor", newBackgroundColor);
		for (Enumeration e1 = myProject.getComponentsStructure().getElements(); e1.hasMoreElements();)
		{
			Entry tempEntry = (Entry)e1.nextElement();
			for (Enumeration e2 = tempEntry.getInstances(); e2.hasMoreElements();)
			{
				Instance tempInstance = (Instance)e2.nextElement();
				if (tempInstance.getBackgroundColor().equals(oldColor) && tempInstance.getKey().getOpdId() == opdId)
				{
					tempInstance.setBackgroundColor(newBackgroundColor);
				}
			}
		}

		for (Enumeration e1 = myProject.getComponentsStructure().getGraphicalRepresentations(); e1.hasMoreElements();)
		{
			GraphicalRelationRepresentation tempRepr = (GraphicalRelationRepresentation)e1.nextElement();
			if (tempRepr.getBackgroundColor().equals(oldColor) && tempRepr.getSource().getOpdId() == opdId)
			{
					tempRepr.setBackgroundColor(newBackgroundColor);
			}
		}

	}


	/**
	 *  Prints the current OPD.<br>
	 *  This method displays platform depandent "Print Dialog", and then sends the OPD to the printer device.
	 */
	public int print(Graphics g, PageFormat pf, int pi) throws PrinterException
	{
		PageSetupData psd = (PageSetupData)pf;
		removeSelection();
		Graphics2D g2 = (Graphics2D)g;
		java.awt.geom.AffineTransform at = g2.getTransform();

		// save/restore transform scope at

			Object AntiAlias = RenderingHints.VALUE_ANTIALIAS_ON;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, AntiAlias);
			DrawingArea dArea = opdFrame.getDrawingArea();

			Font myFont = new Font("my", Font.PLAIN, 12);
			FontMetrics currentMetrics = g2.getFontMetrics(myFont);
			int headerSize = currentMetrics.getAscent()+10;

			int printPageWidth = (int)pf.getImageableWidth();
			int printPageHeight = (int)pf.getImageableHeight() - headerSize*2;

			int screenPageWidth = dArea.getWidth();
			int screenPageHeight = dArea.getHeight();

			Color bgColor = dArea.getBackground();

			Color oldColor = (Color)myProject.getConfiguration().getProperty("BackgroundColor");
			applyNewBackground(Color.white);

			double zoomWidthFactor = (double) printPageWidth / (double)screenPageWidth;
			double zoomHeightFactor = (double) printPageHeight / (double)(screenPageHeight);

			dArea.setBackground(Color.white);
			myProject.getConfiguration().setProperty("BackgroundColor", Color.white);

			Rectangle rect = calcDrawingCenter(dArea, g2);

			Rectangle targetRect = new Rectangle(0, 0, dArea.getWidth(), dArea.getHeight());
			g2.scale(zoomWidthFactor, zoomHeightFactor);

			double scalingFactor = 1.0;

			if(psd.isFitToPage())
			{
				scalingFactor = fitToPage(targetRect, rect, g2);
			}


			if(psd.isCenterView())
			{
				centerView(targetRect, rect, g2);
			}


			double xTr = pf.getImageableX();
			double yTr = pf.getImageableY() + headerSize;// + 15 + currentMetrics.getAscent();
			g2.translate(xTr/(zoomWidthFactor*scalingFactor), yTr/(zoomHeightFactor*scalingFactor));

			dArea.print(g2);
			dArea.setBackground(bgColor);
			applyNewBackground(oldColor);


		g2.setTransform(at);

		printHeader(g2, psd, (int)xTr, (int)yTr, printPageWidth, myFont);
		printFooter(g2, psd, pi, (int)xTr, (int)yTr + printPageHeight, printPageWidth, myFont);
		return Printable.PAGE_EXISTS;
	}

	private void zoomContainer(Container cnt,double factor)
	{
		Component components[] = cnt.getComponents();

		for (int i=0; i < components.length; i++)
		{
			if (components[i] instanceof DraggedPoint || components[i] instanceof OpdOr)
			{
				components[i].setLocation((int)(components[i].getX()*factor), (int)(components[i].getY()*factor));
				components[i].setSize((int)(components[i].getWidth()*factor), (int)(components[i].getHeight()*factor));
			}
		}


		for (int i=0; i < components.length; i++)
		{
			if (components[i] instanceof OpdBaseComponent)
			{
				components[i].setLocation((int)(components[i].getX()*factor), (int)(components[i].getY()*factor));
				components[i].setSize((int)(components[i].getWidth()*factor), (int)(components[i].getHeight()*factor));
				zoomContainer((OpdBaseComponent)components[i], factor);
			}

			if (components[i] instanceof AroundDragger)
			{
				components[i].setSize((int)(components[i].getWidth()*factor), (int)(components[i].getHeight()*factor));
				((AroundDragger)(components[i])).updateDragger();

				OpdCardinalityLabel label;
				double dx, dy;

				if (components[i] instanceof OpdRelationDragger)
				{
					OpdRelationDragger tDragger = (OpdRelationDragger)components[i];
					label = tDragger.getOpdCardinalityLabel();
					dx = (label.getX() - tDragger.getX())*factor;
					dy = (label.getY() - tDragger.getY())*factor;
					label.setLocation(tDragger.getX()+(int)dx, tDragger.getY()+(int)dy);
					label.recalculateSize();
				}

				if (components[i] instanceof TextDragger)
				{
					TextDragger tDragger = (TextDragger)components[i];
					label = tDragger.getOpdCardinalityLabel();
					dx = (label.getX() - tDragger.getX())*factor;
					dy = (label.getY() - tDragger.getY())*factor;
					label.setLocation(tDragger.getX()+(int)dx, tDragger.getY()+(int)dy);
					label.recalculateSize();
				}

			}
		}
	}

	/**
	 *  This method Zoomes the graphics in, simply enlarges it by <code>param</code>
	 *  @param <code>param double</code> zooming factor: size = currentSize*param;
	 */
	public void viewZoomIn(double factor)
	{
		zoomContainer(opdFrame.getDrawingArea(), factor);


		double width = getDrawingArea().getPreferredSize().getWidth()*factor;
		double height = getDrawingArea().getPreferredSize().getHeight()*factor;
		getDrawingArea().setPreferredSize(new Dimension((int)width, (int)height));
		getDrawingArea().setSize(new Dimension((int)width, (int)height));
		mainDesktop.validate();
	}

	/**
	 *  This method Zoomes the graphics out, simply makes it smaller by <code>param</code>
	 *  @param <code>param double</code> zooming factor: size = currentSize*param;
	 */
	public void viewZoomOut(double factor)
	{
		viewZoomIn(1/factor);
	}

	/**
	 *  Marks <code>sItem</code> component as selected and adds it to hte selected items list.
	 *  @param <code>sItem OpdBaseComponent</code> component to select.
	 */
	public void addSelection(Instance sItem, boolean removePrevious)
	{
		mySelection.addSelection(sItem, removePrevious);
	}

    public void addSelection(BaseGraphicComponent sItem, boolean removePrevious)
    {
        mySelection.addSelection(sItem, removePrevious);
    }

	public void addSelection(Hashtable instToSelect, boolean removePrevious)
	{
		Instance sItem;

//		if (removePrevious && (selectedItems.size() > 0) )
//		{
//			mySelection.removeSelection();
//		}

		for(Enumeration e = instToSelect.elements(); e.hasMoreElements();)
		{
			sItem = (Instance)e.nextElement();
			mySelection.addSelection(sItem, false);
		}
	}

	/**
	 *  Marks <code>sItem</code> component as not selected and removes it from hte selected items list.
	 *  @param <code>sItem OpdBaseComponent</code> component to deselect.
	 */

	public void removeSelection(Instance inst)
	{
		mySelection.removeSelection(inst);
	}


	public void removeSelection()
	{
		mySelection.removeSelection();
	}

	/**
	 *  @return <code>Enumeration</code> of items selected in the <code>Opd</code>, actualy selected in <code>Opd</code>'s
	 *  <a href = "../opdGraphics/DrawingArea.html"><code>DrawingArea</code><a>.
	 */
	public Enumeration getSelectedItems()
	{
		return  mySelection.getSelectedItems();
	}

	public Hashtable getSelectedItemsHash()
	{
		return mySelection.getSelectedItemsHash();
	}

	/**
	 *  If single item selected it's possible to get this item directly with this method, in all other cases it will return <code>null</code>
	 *  @return selected item, if single item selected, otherwise returns <code>null</code>.
	 */
	public Instance getSelectedItem()
	{
		if (mySelection.getSelectedItemsHash().size() != 1)
		{
			return null;
		}
		return (Instance)mySelection.getSelectedItemsHash().elements().nextElement();
	}

	/**
	 *  Shows or hides the <code>JInternalFrame</code> that dispalys this <code>Opd</code>
	 *  @param <code>visible</code> <code>true</code> to make the component visible -- <code>false</code> invisible.
	 */
	public void setVisible(boolean visible)
	{
		opdFrame.setVisible(visible);
	}

	/**
	 *  Checks wether the <code>JInternalFrame</code> that dispalys this <code>Opd</code> is visible.
	 *  @return <code>true</code> if OPD is visible.
	 */
	public boolean isVisible()
	{
		return opdFrame.isVisible();
	}

	/**
	 *  @return number of entities in the <code>Opd</code>
	 */
	public long getEntityInOpdMaxEntry()
	{
		return entityInOpdMaxEntry;
	}


		/**
	 * Sets maximum entry number (for entitiInIpOpd allocation)(used in load operation).
	 */
	public void setEntityInOpdMaxEntry(long pEntityInOpdMaxEntry)
	{
		entityInOpdMaxEntry = pEntityInOpdMaxEntry;
	}

		// WARNING destructive -- Destructivly alters AffineTransform of graphics
	// You should save the stransform before invocation
	// and restore after all centalize drawing is done
	// centalizes rect withing component
	// returns xTransleted, yTransleted in Point
	private Point centerView(Rectangle targetRect ,Rectangle sourceRect,  Graphics2D g2)
	{
		double xCenter = (targetRect.getWidth()/2 - (sourceRect.getWidth()/2+sourceRect.getX()));
		double yCenter = (targetRect.getHeight()/2 - (sourceRect.getHeight()/2+sourceRect.getY()));
		g2.translate(xCenter, yCenter);
		return new Point((int)xCenter, (int)yCenter);
	}

	/**
	 * returns enclosing rectangle od whole drawing
	 */
	private Rectangle calcDrawingCenter(JComponent parent, Graphics2D g2)
	{
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int numComps = parent.getComponentCount();
		Component[] comps = parent.getComponents();
		Component c = null;
		for(int i = 0; i < numComps; i++)
		{
			c = comps[i];
			if(!(c instanceof LinkingLine))
			{
				if(c.getX() < minX) minX = c.getX();
				if(c.getY() < minY) minY = c.getY();
				if(c.getX() + c.getWidth() > maxX) maxX = c.getX() + c.getWidth();
				if(c.getY() + c.getHeight() > maxY) maxY = c.getY() + c.getHeight();
			}
		}
		return new Rectangle(minX, minY, maxX-minX, maxY-minY);
	}

	private double fitToPage(Rectangle targetRect ,Rectangle sourceRect, Graphics2D g2)
	{
		double widthFactor = (targetRect.getWidth() / sourceRect.getWidth());
		double heightFactor = (targetRect.getHeight() / sourceRect.getHeight());

		double scalingFactor = Math.min(widthFactor, heightFactor);

		double xTrans = (sourceRect.getX());
		double yTrans = (sourceRect.getY());

		g2.scale(scalingFactor, scalingFactor);

		if (widthFactor<heightFactor)
		{
			double factor = (heightFactor-widthFactor)/(2*scalingFactor);
			yTrans = yTrans-sourceRect.getHeight()*factor;
		}
		else
		{
			double factor = (widthFactor-heightFactor)/(2*scalingFactor);
			xTrans = xTrans-sourceRect.getWidth()*factor;
		}

		g2.translate(-xTrans, -yTrans);

		return scalingFactor;

	}

	private void printFooter(Graphics2D g2, PageSetupData psd, int pi, int xBorder, int yBorder, int width, Font fnt)
	{
		FontMetrics currentMetrics = g2.getFontMetrics(fnt);
		int fontSize = currentMetrics.getAscent();
		String str;
		boolean drawLine = false;

		g2.setFont(fnt);

		//construct and draw Author string (down left corner)
		if(psd.isPrjCreator())
		{
			drawLine = true;
			str = "Author: "+ myProject.getCreator();
			g2.drawString(str, xBorder, yBorder+fontSize+9);
		}

		//construct and draw page number string
		if(psd.isPageNumber())
		{
			drawLine = true;
			String pIndex = "Page " + (pi+1);
			g2.drawString(pIndex, xBorder+width-currentMetrics.stringWidth(pIndex),
					 yBorder+fontSize+9);
		}

		// draw line
		if(drawLine)
		{
			g2.setStroke(new BasicStroke(1.2f));
			g2.drawLine(xBorder, yBorder + 7 , xBorder+width, yBorder + 7);
		}
	}

	private void printHeader(Graphics2D g2, PageSetupData psd, int xBorder,int yBorder, int width, Font fnt)
	{
		FontMetrics currentMetrics = g2.getFontMetrics(fnt);
		String str = "";
		boolean drawLine = false;

		g2.setFont(fnt);

		//construct and draw projectName and opdName string
		if(psd.isPrjName() || psd.isOpdName())
		{
			drawLine = true;
			if(psd.isPrjName())
			{
				str += "System: "+ myProject.getName();
			}

			if(psd.isOpdName())
			{
				if(psd.isPrjName())
				{
					str += ", ";
				}
				str += "OPD: " + this.getName();
			}
			g2.drawString(str, xBorder, yBorder-9);
		}
		//g2.setStroke(new BasicStroke(2.0f));

		//construct and draw date+time string
		if(psd.isPrintTime())
		{
			drawLine = true;

			StringBuffer dateStr = new StringBuffer("Printed on ");
			SimpleDateFormat df = new SimpleDateFormat("EEE dd/MM/yy HH:mm");
			df.format((new GregorianCalendar()).getTime(), dateStr, new FieldPosition(0));
			str = new String(dateStr);
			g2.drawString(str, xBorder+width - currentMetrics.stringWidth(str) - 1,yBorder-9);
		}

		// draw line
		if(drawLine)
		{
			g2.setStroke(new BasicStroke(1.2f));
			g2.drawLine(xBorder,yBorder-7, xBorder+width, yBorder-7);
		}
	}

	public ThingEntry getMainEntry()
	{
		return mainEntry;
	}

	public IThingEntry getMainIEntry()
	{
		return mainEntry;
	}

	public IXThingEntry getMainIXEntry()
	{
		return mainEntry;
	}

	public void setMainEntry(ThingEntry mainEntry)
	{
		this.mainEntry = mainEntry;
	}

	public IThingInstance getMainIInstance()
	{
		return mainInstance;
	}

	public IXThingInstance getMainIXInstance()
	{
		return mainInstance;
	}

	public ThingInstance getMainInstance()
	{
		return mainInstance;
	}

	public void setMainInstance(ThingInstance mainInstance)
	{
		this.mainInstance = mainInstance;
	}

	
	/**
	 * Creates an image out of the OPD. 
	 */
	public BufferedImage getImageRepresentation()
	{

        Opd currentOPD = myProject.getCurrentOpd();
        boolean isShown = opdFrame.isVisible();
        boolean isIconified = isShown && opdFrame.isIcon();

        try
        {
            if (!isShown)
            {
                this.setVisible(true);
            }

            if (isIconified)
            {
                opdFrame.setMaximum(true);
            }
        }
        catch (java.beans.PropertyVetoException pve){
        	Debug debug = Debug.getInstance() ; 
        	debug.Print(this, pve.getMessage(), "1"); 
        }

        DrawingArea dArea = this.getDrawingArea();
//        dArea.repaint();
		removeSelection();

		BufferedImage myImage = new BufferedImage(dArea.getWidth(), dArea.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D myGraphics = myImage.createGraphics();
//        ImageIcon ic = new ImageIcon(myImage);
		myGraphics.setColor(Color.white);
		myGraphics.fillRect(0,0,dArea.getWidth(), dArea.getHeight());

		Color bgColor = dArea.getBackground();

		Color oldColor = (Color)myProject.getConfiguration().getProperty("BackgroundColor");
		applyNewBackground(Color.white);
		dArea.setBackground(Color.white);

		Rectangle rect = calcDrawingCenter(dArea, myGraphics);
		Rectangle targetRect = new Rectangle(0, 0, dArea.getWidth(), dArea.getHeight());

		centerView(targetRect, rect, myGraphics);

		dArea.paintAll(myGraphics);

		dArea.setBackground(bgColor);
		applyNewBackground(oldColor);
        try
        {
            if (!isShown)
            {
                opdFrame.setVisible(false);
            }

            if (isIconified && isShown)
            {
                opdFrame.setIcon(true);
            }

            if (currentOPD!=null)
            {
                currentOPD.getOpdContainer().setSelected(true);
            }
        }
        catch (java.beans.PropertyVetoException pve){
        	Debug debug = Debug.getInstance() ; 
        	debug.Print(this, pve.getMessage(), "1");         	
        }


		return myImage;
	}

	public Selection getSelection()
	{
		return mySelection;
	}

	public void selectAll()
	{
		this.getSelection().selectAll();
	}
        //reuseComment

        /**
         *
         * @param _isLocked
         * this function set the locking status variable
         */
        public void setLocked(boolean _isLocked)
        {
          isLocked=_isLocked;
        }

        public boolean isLocked()
        {
          return isLocked;
        }

        public void setOpenReused(boolean _isOpenReused)
        {
          isOpenReused=_isOpenReused;
        }

        public boolean isOpenReused()
        {
          return isOpenReused;
        }

        public void setReusedSystemPath(String _path)
        {
          reusedSystemPath=_path;
        }
        public String getReusedSystemPath()
        {
          return reusedSystemPath;
        }

        public ThingInstance getOpenReusedSource(ThingInstance aThingInstance)
        {
          Enumeration e=inhertRelationsOverReusedThings.keys();
          while (e.hasMoreElements())
          {
            Instance aInstance=(Instance)e.nextElement();
            if (inhertRelationsOverReusedThings.get(aInstance)==aThingInstance)
              return (ThingInstance)aInstance;
          }
          return null;
        }


      public Instance getOpenReuseTargetInheritedInstance(Instance source)
      {
        return (Instance)inhertRelationsOverReusedThings.get(source);
      }
      /**
       *
       * @param aInstance
       * @return
       */
      public boolean isInstanceOpenReusedInherited(Instance aInstance)
      {
        return inhertRelationsOverReusedThings.contains(aInstance);
      }


      /**
     *
     * adding of an inheritence realtion to the table
     * @param source
     * @param target
     */
    public void addOpenReusedInheritRelationToTable(ThingInstance source,ThingInstance target)
    {
      inhertRelationsOverReusedThings.put(source,target);
    }

    public boolean isOpenReusedInherited(Instance source)
    {
      return inhertRelationsOverReusedThings.get(source) != null;
    }

//    public static void DeleteAllOPDSons(OpdProject project, Enumeration ee) { 
//    	for (; ee.hasMoreElements(); ) {
//    		ThingInstance instance = (ThingInstance) ee.nextElement() ; 
//    		if ( instance != null && instance.isZoomedIn() ) {
//    			IXOpd sonOpd = project.zoomIn(instance);  
//    			DeleteAllOPDSons(project, sonOpd.getOpdId()) ; 
//    		} 
//    	}     	
//    }
//    
//    private static void DeleteAllOPDSons(OpdProject project, long opdID) { 
//    	Opd opd = project.getOpdByID(opdID) ;
//    	opd.selectAll() ; 
//    	for (Enumeration ee = opd.getSelectedItems(); ee.hasMoreElements(); ) {
//    		ThingInstance instance = (ThingInstance) ee.nextElement() ; 
//    		if ( instance != null && instance.isZoomedIn() ) {
//    			IXOpd sonOpd = instance.createInzoomedOpd() ;  
//    			DeleteAllOPDSons(project, sonOpd.getOpdId()) ; 
//    		} else {
//    			project.deleteOpd(project.getOpdByID(opdID)) ; 
//    		}
//    	}
//    	 
//    	
//    }

    /** 
     * This function returns True if the two instances are conected with the link
     * in the OPD. 
     * it retuens false if not connected.
     * 
     *  @param source - The link Source instance
     *  @param dest - the link destination Instance
     *  @param link - The link to check for. 
     */
    
    public boolean isConnected(Instance source , Instance dest, LinkInstance link) {
    	boolean retVal = false ; 	
    	Enumeration linkEnum = link.getEntry().getInstances() ; 
    	
    	while (linkEnum.hasMoreElements()) {
    		LinkInstance locLink =(LinkInstance)  linkEnum.nextElement() ; 
    		if (locLink.getKey().getOpdId() ==  this.getOpdId()) {
    			retVal = true ;
    			break ; 
    		}
    	} 	
    	return retVal ; 
    }
    
    /**
     * isContained returns true if the instance is inside the OPD
     * 
     * @param instance
     * @return
     */
    public boolean isContaining(Instance instance) {     	
    	return (instance.getKey().getOpdId() == this.getOpdId()) ; 
    }

      //endReuseCommet

    /** *********************** OWL-S Start ************************************ */
	public Instance selectOne(String name) {
		return this.getSelection().selectOne(name);
	}
	/************************* OWL-S End	*************************************/
	
}


