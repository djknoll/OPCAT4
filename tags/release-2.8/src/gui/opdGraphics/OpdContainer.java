package gui.opdGraphics;

import gui.opdProject.GenericTable;
import gui.opdProject.Opd;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

/**
 *  <p>This class represents single window in MDI application - <code>JInternalFrame</code>. It consist of <a href = "DrawingArea.html><code>DrawingArea</code></a>, referense to
 *  <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a> and reference to
 *  <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a> it represents graphicaly.
 */
public	class OpdContainer extends JInternalFrame// implements MouseListener
{
	/**
	 *  It's a reference to <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a> this container belongs to
	 */
	protected OpdProject myProject;

	/**
	 *  It's Actually the window area where the components are drawn
	 */
	protected DrawingArea dArea;

	/**
	 *  It's a reference to <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a> it represents graphicaly.
	 */
	protected Opd parentOpd;

	/**
	 *  <p>Constructs resizable, closeable, iconifiable, maximisable <code>JInternalFrame</code> with title given as <code>pTitle</code> parametr, with
	 *  new <a href = "DrawingArea.html><code>DrawingArea</code></a> as client area of the window.
	 *  @param <code>pTitle String</code>, title of newly created frame.
	 *  @param <code>pProject OpdProject</code>, the project this graphic OPD representation belongs to.
	 *  @param <code>pOpd Opd</code> that this <code>OpdContainer</code> represents graphically
	 */

	public OpdContainer(String pTitle, OpdProject pProject,	Opd pOpd)
	{
		super(pTitle, true, true, true, true);
		myProject = pProject;
		parentOpd = pOpd;
		dArea = new DrawingArea(myProject, pOpd);
		dArea.addMouseListener(dArea);
		dArea.addMouseMotionListener(dArea);

		JScrollPane scroller = new JScrollPane(dArea);

		//Layout this
		getContentPane().setLayout(new BorderLayout());
		//dArea.setPreferredSize(new Dimension(1600,1000));

		GenericTable config = myProject.getConfiguration();
		double normalSize = ((Integer)config.getProperty("NormalSize")).doubleValue();
		double currentSize = ((Integer)config.getProperty("CurrentSize")).doubleValue();
		double factor = currentSize/normalSize;
		double width = ((Integer)config.getProperty("OPDWidth")).intValue()*factor;
		double height = ((Integer)config.getProperty("OPDHeight")).intValue()*factor;


		dArea.setPreferredSize(new Dimension((int)width, (int)height));
		getContentPane().add(scroller,BorderLayout.CENTER);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

	/**
	 *  @return a reference to <a href = "../projectStructure/OpdProject.html" ><code>projectStructure.OpdProject</code></a> this container belongs to.
	 */
	public Opd getOpd()
	{
		return parentOpd;
	}

	/**
	 *  @return a reference to <a href = "../projectStructure/Opd.html" ><code>projectStructure.Opd</code></a> it represents graphicaly.
	 */
	public DrawingArea getDrawingArea()
	{
		return dArea;
	}
}


