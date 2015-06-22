package gui.checkModule;


import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


class TableSeparator extends JComponent
{
	private int dir = SwingConstants.HORIZONTAL;
	private TitledBorder titledBorder1;
	TableSeparator(int direction)
	{
		super();
//		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		dir = direction;
	}

	TableSeparator()
	{
		super();
		try {
			jbInit();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g)
	{
		if(dir == SwingConstants.HORIZONTAL)
		{
			g.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
		}
		else
		{
			g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight());
		}
	}
	private void jbInit() throws Exception {
		titledBorder1 = new TitledBorder("");
	}
}
