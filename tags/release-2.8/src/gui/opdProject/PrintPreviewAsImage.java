package gui.opdProject;

import gui.util.JToolBarButton;
import gui.util.JToolBarCombo;

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
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class PrintPreviewAsImage extends JFrame
{
  protected double zoom_pt;
  protected Pageable m_target;
  protected JComboBox m_cbScale;
  protected PreviewContainer m_preview;
  protected  PageFormat pf;
  protected int p_width;
  protected int p_height;

  public PrintPreviewAsImage(Pageable target) {
    this(target,"Print Preview");
  }

  public PrintPreviewAsImage(Pageable target,String title) {
    super(title);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setSize(screenSize);
    //this.pf = pf;
   // p_width = (int)pf.getWidth();
   // p_height = (int)pf.getHeight();
    m_target = target;

    JToolBar tb = new JToolBar();
    JToolBarButton bt = new JToolBarButton(printAction, "Print Preview");
    bt.setText(" Print ");
    tb.add(bt);

    bt = new JToolBarButton(closeAction, "Close");
    bt.setText(" Close ");
    tb.add(bt);

    String[] scales = { "50 %", "100 %", "150 %", "200 %",};
    m_cbScale = new JToolBarCombo(scales);
//    m_cbScale = new JComboBox(scales);
    ActionListener lst = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Thread runner = new Thread() {
          public void run() {

            String str = m_cbScale.getSelectedItem().toString();
            if (str.endsWith("%"))
              str = str.substring(0, str.length()-1);
            str = str.trim();
              int scale = 0;
            try { scale = Integer.parseInt(str); }
            catch (NumberFormatException ex) { return; }
            zoom_pt = (double)scale/100;


            int height = 20;
            int width = 0;

            Component[] comps = m_preview.getComponents();
            for (int k=0; k<comps.length; k++) {
              if ((comps[k] instanceof PagePreview))
              {
                PagePreview pp = (PagePreview)comps[k];
                pf = pp.getFormat();
                pp.setPreferredSize(new Dimension((int)(pf.getWidth()*zoom_pt)+3, (int)(pf.getHeight()*zoom_pt)+3));
                pp.setSize(new Dimension((int)(pf.getWidth()*zoom_pt)+3, (int)(pf.getHeight()*zoom_pt)+3));
                height = height + (int)(pf.getHeight()*zoom_pt) + 23;
                width= Math.max(width, (int)(pf.getWidth()*zoom_pt)+3);
              }
            }

           width = Math.max(width,  getWidth());
           height = Math.max(height, getHeight());
           m_preview.setSize(new Dimension(width, height));
           m_preview.setPreferredSize(new Dimension(width, height));
           m_preview.doLayout();
           m_preview.repaint();
          }
        };
        runner.start();
      }
    };
    m_cbScale.addActionListener(lst);
    m_cbScale.setMaximumSize(m_cbScale.getPreferredSize());
    m_cbScale.setEditable(true);
    tb.addSeparator();
    tb.add(m_cbScale);
    getContentPane().add(tb, BorderLayout.NORTH);

    m_preview = new PreviewContainer();
    //m_preview.setPreferredSize(new Dimension(1600,8000));
    m_preview.setLayout(new BoxLayout(m_preview, BoxLayout.Y_AXIS));
   // m_preview.setPreferredSize(new Dimension(1600,1700));

//    PrinterJob prnJob = PrinterJob.getPrinterJob();
//    if (pf.getHeight()==0 || pf.getWidth()==0) {
//      System.err.println("Unable to determine default page size");
//        return;
//    }

   // zoom_pt = 1.1;
    for (int i=0; i<target.getNumberOfPages(); i++)
    {
      pf = target.getPageFormat(i);
      BufferedImage bi = new BufferedImage((int)pf.getWidth(), (int)pf.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
      Graphics g = bi.createGraphics();
      g.setColor(Color.white);
      g.fillRect(0, 0, (int)pf.getWidth(), (int)pf.getHeight());

      try{
      target.getPrintable(i).print(g, pf, i);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        System.err.println("err "+e);
      }

      PagePreview pp = new PagePreview(bi, pf);
      //pp.setPreferredSize(new Dimension((int)(pf.getWidth()*zoom_pt)+3, (int)(pf.getHeight()*zoom_pt)+3));
      m_preview.add(Box.createVerticalStrut(20));
      m_preview.add(pp);
//      System.err.println(""+pp.getPreferredSize()+" "+zoom_pt+" "+pf.getWidth());
    }

    m_preview.add(Box.createVerticalStrut(20));
//    System.err.println(m_preview.getSize()+"");
//    System.err.println(" "+m_preview.getPreferredSize());
    m_cbScale.setSelectedItem("110");


//    m_preview.doLayout();
//    m_preview.getParent().getParent().validate();
//
    JScrollPane ps = new JScrollPane(m_preview);
    getContentPane().add(ps, BorderLayout.CENTER);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setVisible(true);
  }

  class PreviewContainer extends JPanel implements Scrollable
  {
      public PreviewContainer()
      {
       this.setBackground(new Color(132,130,129));
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
			return (int)(size.getHeight()/10);
		}
		return (int)(size.getWidth()/10);

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
			return (int)(size.getHeight()/50);
		}
		return (int)(size.getWidth()/50);

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
  BufferedImage img;
   int pi;

    public PagePreview(BufferedImage myImage, PageFormat pf) {
//    this.target = target;
    this.pf = pf;
//    this.pi = pi;
    img = myImage;
    this.setDoubleBuffered(true);
  /*
      m_w = w;
      m_h = h;
      m_source= source;
      m_img = m_source.getScaledInstance(m_w, m_h,
        Image.SCALE_SMOOTH);
      m_img.flush();
      setBackground(Color.white);
      setBorder(new MatteBorder(1, 1, 2, 2, Color.black));
      */
    }

/*    public void setScaledSize(int w, int h) {
      m_w = w;
      m_h = h;
      m_img = m_source.getScaledInstance(m_w, m_h,
        Image.SCALE_SMOOTH);
      repaint();
    }

    public Dimension getPreferredSize() {
      Insets ins = getInsets();
      return new Dimension(m_w+ins.left+ins.right,
        m_h+ins.top+ins.bottom);
    }
*/
    public PageFormat getFormat()
    {
      return pf;
    }

    public Dimension getMaximumSize() {
      return getPreferredSize();
    }

    public Dimension getMinimumSize() {
      return getPreferredSize();
    }

    public void paintComponent(Graphics g) {
      /*g.setColor(getBackground());
      g.fillRect(0, 0, getWidth(), getHeight());
      g.drawImage(m_img, 0, 0, this);
      paintBorder(g);
      */
      Graphics2D g2 =(Graphics2D)g;
      try{
         java.awt.geom.AffineTransform at = g2.getTransform();
         g2.scale(zoom_pt, zoom_pt);
         g2.drawImage(img, 0, 0, this);
//         target.print(g2, pf, pi);
         g2.setTransform(at);

//         g2.setColor(new Color(132,130,129));
//         g2.fillRect((int)(p_width*zoom_pt),0,3,3);
//         g2.fillRect(0,(int)(p_height*zoom_pt),3,3);
//
//         g2.setColor(Color.black);
//         g2.fillRect((int)(p_width*zoom_pt),3,3,(int)(p_height*zoom_pt));
//         g2.fillRect(3,(int)(p_height*zoom_pt),(int)(p_width*zoom_pt),3);
//

          g2.setColor(new Color(51,0,102));
         g2.drawRect(0,0,(int)(pf.getWidth()*zoom_pt-1),(int)(pf.getHeight()*zoom_pt-1));
         g2.setColor(new Color(132,130,129));
         g2.fillRect((int)(pf.getWidth()*zoom_pt),0,3,3);
         g2.fillRect(0,(int)(pf.getHeight()*zoom_pt),3,3);

         g2.setColor(Color.black);
         g2.fillRect((int)(pf.getWidth()*zoom_pt),3,3,(int)(pf.getHeight()*zoom_pt));
         g2.fillRect(3,(int)(pf.getHeight()*zoom_pt),(int)(pf.getWidth()*zoom_pt),3);


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
                PrinterJob prnJob = PrinterJob.getPrinterJob();
                prnJob.setPageable(m_target);

                  if (prnJob.printDialog()) {
                      try {
                            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            prnJob.print();
                            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                            dispose();
                      } catch (Exception ex) {
                        ex.printStackTrace();
                        System.err.println("Printing error: "+ex.toString());
                      }
                  }

                  }
              });

            }

	};

      Action closeAction = new AbstractAction("Close"){
          public void actionPerformed(ActionEvent e) {
            dispose();
          }
	};


}
