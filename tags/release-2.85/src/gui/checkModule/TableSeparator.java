package gui.checkModule;

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

class TableSeparator extends JComponent {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	private int dir = SwingConstants.HORIZONTAL;

	TableSeparator(int direction) {
		super();
		// this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.dir = direction;
	}

	TableSeparator() {
		super();
	}

	public void paintComponent(Graphics g) {
		if (this.dir == SwingConstants.HORIZONTAL) {
			g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
		} else {
			g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
		}
	}
}
