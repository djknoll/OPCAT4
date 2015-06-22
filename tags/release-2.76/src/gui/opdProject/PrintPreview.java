package gui.opdProject;

import gui.images.standard.StandardImages;
import gui.util.JToolBarButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PrintPreview extends JFrame
{
	private final ImageIcon ICON_PRINT = StandardImages.PRINT;
	private final ImageIcon ICON_PREVIEW_CLOSE = StandardImages.PREVIEW_CLOSE;
	private final ImageIcon ICON_PAGE_SETUP = StandardImages.PAGE_SETUP;
	private final ImageIcon ICON_PRINTER_SETUP = StandardImages.PRINTER_SETUP;

    protected double zoom_pt;
    protected Pageable my_target;
    protected JComboBox m_cbScale;
    protected PreviewContainer m_preview;
    protected PageFormat pf;
    protected PrinterJob printerJob;
    protected PageSetupData pageSetupData;

    protected final Color BACKGROUND_COLOR = new Color(132,130,129);
    protected final Color SHADDOW_COLOR = new Color(0,0,0);
    protected final Color FRAME_COLOR = new Color(51,0,102);
    protected final int SHADDOW_SIZE = 3;
    protected final int PAGE_OFFSET = 20;
//  protected final int

  public PrintPreview(PrinterJob pj, Pageable target, PageSetupData psd) {
	this(pj, target,"Print Preview", psd);
  }

  public PrintPreview(PrinterJob pj, Pageable target,String title, PageSetupData psd) {
	super(title);

    final ImageIcon tempIcon = gui.images.misc.MiscImages.LOGO_SMALL_ICON;

    setIconImage(tempIcon.getImage());

	this.setBackground(BACKGROUND_COLOR);
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	setSize(screenSize);
	my_target = target;
	printerJob = pj;
	pageSetupData = psd;
//	this.pf = pf;
//	printProps = new PrintProperties();

	JToolBar tb = new JToolBar();
	JToolBarButton bt = new JToolBarButton(printAction, "Print OPDs");
	bt.setText("Print  ");
	bt.setIcon(ICON_PRINT);
	tb.add(bt);

	String[] scales = { "50 %", "100 %", "150 %", "200 %",};
	//m_cbScale = new JToolBarCombo(scales);
	m_cbScale = new JComboBox(scales);

	m_cbScale.addActionListener(comboLst);
	m_cbScale.setMaximumSize(m_cbScale.getPreferredSize());
	m_cbScale.setEditable(true);
	tb.addSeparator();
	tb.add(m_cbScale);

	bt = new JToolBarButton(pageSetup, "Page Setup");
	bt.setText("Page Setup  ");
	bt.setIcon(ICON_PAGE_SETUP);
	tb.addSeparator();
	tb.add(bt);

	bt = new JToolBarButton(printerSetup, "Printer Setup");
	bt.setText("Printer Setup  ");
	bt.setIcon(ICON_PRINTER_SETUP);
	tb.addSeparator();
	tb.add(bt);


	bt = new JToolBarButton(closeAction, "Close");
	bt.setText("Close  ");
	bt.setIcon(ICON_PREVIEW_CLOSE);
	tb.addSeparator();
	tb.addSeparator();
	tb.add(bt);

	getContentPane().add(tb, BorderLayout.NORTH);

	m_preview = new PreviewContainer();
        m_preview.setDoubleBuffered(true);
//	m_preview.setLayout(new BoxLayout(m_preview, BoxLayout.Y_AXIS));
        m_preview.setLayout(null);

	for (int i=0; i<target.getNumberOfPages(); i++)
	{
	  pf = target.getPageFormat(i);
	  PagePreview pp = new PagePreview(target.getPrintable(i),pf , i);
          pp.setVisible(false);
//	  m_preview.add(Box.createVerticalStrut(PAGE_OFFSET));
	  m_preview.add(pp);
	}

	m_preview.add(Box.createVerticalStrut(PAGE_OFFSET));
	m_cbScale.setSelectedItem("110 %");

        Component[] comps = m_preview.getComponents();

        for (int k=0; k<comps.length; k++) {
          if ((comps[k] instanceof PagePreview))
          {
            comps[k].setVisible(true);
          }
        }


	JScrollPane ps = new JScrollPane(m_preview);
	getContentPane().add(ps, BorderLayout.CENTER);
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setVisible(true);
  }

  class PreviewContainer extends JPanel implements Scrollable
  {
	  public PreviewContainer()
	  {
	   this.setBackground(BACKGROUND_COLOR);
	  }

		public Dimension getPreferredScrollableViewportSize()
	{
		return getPreferredSize();
	}


	public int getScrollableBlockIncrement(Rectangle r, int orientation, int direction)
	{
		Dimension size;

		size = getPreferredSize();
		if (orientation == SwingConstants.VERTICAL)
		{
			return (int)(size.getHeight()/(my_target.getNumberOfPages()));
		}
		else
		{
			return (int)(size.getWidth()/10);
		}

	}

	public boolean getScrollableTracksViewportHeight()
	{
		return false;
	}

	public boolean getScrollableTracksViewportWidth()
	{
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle r, int orientation, int direction)
	{
		Dimension size;

		size = getPreferredSize();
		if (orientation == SwingConstants.VERTICAL)
		{
			return (int)(size.getHeight()/(5*my_target.getNumberOfPages()));
		}
		else
		{
			return (int)(size.getWidth()/50);
		}

	}

  }

  class PagePreview extends JComponent
  {
	protected int m_w;
	protected int m_h;
	protected Image m_source;
	protected Image m_img;

	Printable target;
	PageFormat pf;
	int pi;

	public PagePreview(Printable target, PageFormat pf, int pi)
	{
		this.target = target;
		this.pf = pf;
		this.pi = pi;
		this.setDoubleBuffered(true);
	}

	public PageFormat getFormat()
	{
		return pf;
	}

	public Dimension getMaximumSize()
	{
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
	  return getPreferredSize();
	}

	public void paintComponent(Graphics g) {

	  Graphics2D g2 =(Graphics2D)g;
	  try{
                 g2.setColor(Color.white);
                 g2.fillRect(0,0,getWidth()-1, getHeight()-1);
		 java.awt.geom.AffineTransform at = g2.getTransform();
		 g2.scale(zoom_pt, zoom_pt);
		 target.print(g2, pf, pi);

		 g2.setTransform(at);

		 // Here we draw the frame and the shaddow

		 g2.setColor(FRAME_COLOR);
		 g2.drawRect(0,0,(int)(pf.getWidth()*zoom_pt-1),(int)(pf.getHeight()*zoom_pt-1));

		 g2.setColor(BACKGROUND_COLOR);
		 g2.fillRect((int)(pf.getWidth()*zoom_pt),0,SHADDOW_SIZE,SHADDOW_SIZE);
		 g2.fillRect(0,(int)(pf.getHeight()*zoom_pt),SHADDOW_SIZE,SHADDOW_SIZE);

		 g2.setColor(SHADDOW_COLOR);
		 g2.fillRect((int)(pf.getWidth()*zoom_pt), SHADDOW_SIZE, SHADDOW_SIZE, (int)(pf.getHeight()*zoom_pt));
		 g2.fillRect(SHADDOW_SIZE ,(int)(pf.getHeight()*zoom_pt),(int)(pf.getWidth()*zoom_pt), SHADDOW_SIZE);


	   }
	   catch (Exception e)
	   {
		 System.err.println(e);
	   }

	}
  }

	Action printAction = new AbstractAction("Print"){
		  public void actionPerformed(ActionEvent e) {
			SwingUtilities.invokeLater(new Runnable() {
			  public void run() {
				PrintPreview.this.printerJob.setPageable(my_target);

//				  if (PrintPreview.this.printerJob.printDialog()) {
					  try {
							setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
							PrintPreview.this.printerJob.print();
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							dispose();
					  } catch (Exception ex) {
						ex.printStackTrace();
						System.err.println("Printing error: "+ex.toString());
					  }
//				  }

				  }
			  });

			}

	};

	Action pageSetup = new AbstractAction("Page Setup"){
		public void actionPerformed(ActionEvent e)
		{
			new PageSetupDialog(PrintPreview.this,
							"Page Setup",
							PrintPreview.this.pageSetupData);
			if(PrintPreview.this.pageSetupData.isApplyed())
			{
				PrintPreview.this.m_preview.repaint();
			}
		}
	};

	Action printerSetup = new AbstractAction("Printer Setup"){
		public void actionPerformed(ActionEvent e)
		{
			printerJob.printDialog();
		}
	};


	  Action closeAction = new AbstractAction("Close"){
		  public void actionPerformed(ActionEvent e) {
			dispose();
		  }
	};


	ActionListener comboLst = new ActionListener() {
	  public void actionPerformed(ActionEvent e)
      {
        String str = m_cbScale.getSelectedItem().toString();
        if (str.endsWith("%"))
        str = str.substring(0, str.length()-1);
        str = str.trim();
        int scale = 0;
        try { scale = Integer.parseInt(str); }
        catch (NumberFormatException ex) { return; }
        zoom_pt = (double)scale/100;


        int height = PAGE_OFFSET;
        int width = (int)(pf.getWidth()*zoom_pt)+SHADDOW_SIZE;

        int xLocation = 10;

        if (getWidth()>width)
        {
            xLocation = (getWidth()-width)/2;
        }


        Component[] comps = m_preview.getComponents();
        for (int k=0; k<comps.length; k++)
        {
            if ((comps[k] instanceof PagePreview))
            {
                PagePreview pp = (PagePreview)comps[k];
				pf = pp.getFormat();
                //pp.setPreferredSize(new Dimension((int)(pf.getWidth()*zoom_pt)+SHADDOW_SIZE, (int)(pf.getHeight()*zoom_pt)+SHADDOW_SIZE));
				pp.setSize(new Dimension((int)(pf.getWidth()*zoom_pt)+SHADDOW_SIZE, (int)(pf.getHeight()*zoom_pt)+SHADDOW_SIZE));
                pp.setPreferredSize(new Dimension((int)(pf.getWidth()*zoom_pt)+SHADDOW_SIZE, (int)(pf.getHeight()*zoom_pt)+SHADDOW_SIZE));
                pp.setLocation(xLocation, height);
                height = height + (int)(pf.getHeight()*zoom_pt) + PAGE_OFFSET + SHADDOW_SIZE;

				//width= Math.max(width, (int)(pf.getWidth()*zoom_pt)+SHADDOW_SIZE);


            }
        }

        width = Math.max(width,  getWidth());
        height = Math.max(height, getHeight());

        m_preview.setSize(new Dimension(width, height));
        m_preview.setPreferredSize(new Dimension(width, height));
        getContentPane().validate();
        getContentPane().repaint();
		   //m_preview.repaint();
		   //doLayout();
		   //repaint();

        }
    };

}
