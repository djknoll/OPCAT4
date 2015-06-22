/*
 * Created on 16/06/2004
 */
package gui.opdGraphics;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JLabel;

/**
 * 
 * @author Eran Toch
 * Created: 16/06/2004
 */
public class SplashLabel extends JLabel {
	private Image image = null;

	public SplashLabel(String str, Image img) {
		super(str);
		this.image = img;
	}

	public void setMyBackgroundImage(Image img) {
		this.image = img;
	}

	public void paint(Graphics g) {
		if (image != null) {
			Insets insets = getInsets();
			g.drawImage(image, insets.left, insets.top, this);
		}
		super.paint(g);
	}
}
