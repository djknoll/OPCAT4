package simulation.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class WaitWindow extends JWindow {

	public WaitWindow(Frame parentFrame, String message) {
		super(parentFrame);
		constructUI(parentFrame, message);
	}

	private void constructUI(Frame parentFrame, String message){
		JLabel msgLabel = new JLabel(message);
		JProgressBar pBar = new JProgressBar();
		pBar.setIndeterminate(true);
		msgLabel.setBorder(new EmptyBorder(25, 30, 25, 30));
		msgLabel.setForeground(new Color(0, 51, 102));
		msgLabel.setFont(new Font("Arial", Font.BOLD, 16));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(msgLabel, BorderLayout.CENTER);
		panel.add(pBar, BorderLayout.SOUTH);

		panel.setBorder(new LineBorder(new Color(0, 51, 102), 1, true));
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		pBar.setForeground(new Color(7, 65, 122));
		pBar.setBorder(new LineBorder(new Color(0, 51, 102)));
		pBar.setFont(new Font("Arial", Font.BOLD, 14));

		pack();
		setLocation(parentFrame.getWidth() / 2
				- getSize().width / 2, parentFrame.getHeight() / 2
				- getSize().height / 2);
		
	}
}
