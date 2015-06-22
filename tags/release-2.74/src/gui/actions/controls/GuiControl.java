package gui.actions.controls;

import gui.Opcat2;
import gui.opdProject.Opd;
import gui.opdProject.StateMachine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * 
 * @author Eran Toch
 */
public class GuiControl {
	
	private static GuiControl instance = null;
	private Opcat2 myOpcat = null;
	
	  protected GuiControl() {
	      // Exists only to defeat instantiation.
	  }
	  
	  public static GuiControl getInstance() {
	      if(instance == null) {
	         instance = new GuiControl();
	      }
	      return instance;
	   }
	  
	  public void setOpcat(Opcat2 opcat)	{
	  	this.myOpcat = opcat;
	  }
	  
	public void setCursor4All(final int cursorType) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myOpcat.getFrame().setCursor(Cursor.getPredefinedCursor(cursorType));
				myOpcat.getOplPane().setCursor(Cursor.getPredefinedCursor(cursorType));

				if (myOpcat.getCurrentProject()!= null) {
					for (Enumeration e =
						myOpcat.getCurrentProject().getComponentsStructure().getOpds();
						e.hasMoreElements();
						) {
						Opd tempOpd = (Opd) e.nextElement();
						tempOpd.getDrawingArea().setCursor(
							Cursor.getPredefinedCursor(cursorType));
					}
				}
			}
		});

	}
	
	public void startWaitingMode(String message, boolean indeterminate)	{
	    JProgressBar pBar = new JProgressBar();
	    if (indeterminate)	{
	        pBar.setIndeterminate(true);
	    }
        showWaitMessage(message, pBar);
	    StateMachine.setWaiting(true);
        setCursor4All(Cursor.WAIT_CURSOR);
	    
	}
	
	public void stopWaitingMode()	{
	    hideWaitMessage();
        StateMachine.reset();
        StateMachine.setWaiting(false);
        setCursor4All(Cursor.DEFAULT_CURSOR);
	}
	

	public void showWaitMessage(String message, JProgressBar pBar) {
	    myOpcat.setWaitScreen(new JWindow(Opcat2.getFrame()));
		handleWaitScreen(myOpcat.getWaitScreen(), message, pBar);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myOpcat.getWaitScreen().setVisible(true);
			}
		});

	}
	
	public void handleWaitScreen(JWindow theScreen, String message, JProgressBar pBar) {
		//theScreen = new JWindow(myFrame);
		JLabel msgLabel = new JLabel(message);
		msgLabel.setBorder(new EmptyBorder(25, 30, 25, 30));
		msgLabel.setForeground(new Color(0, 51, 102));
		msgLabel.setFont(new Font("Arial", Font.BOLD, 16));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(msgLabel, BorderLayout.CENTER);
		panel.add(pBar, BorderLayout.SOUTH);

		panel.setBorder(new LineBorder(new Color(0, 51, 102), 1, true));
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		theScreen.getContentPane().setLayout(new BorderLayout());
		theScreen.getContentPane().add(panel, BorderLayout.CENTER);
		pBar.setForeground(new Color(7, 65, 122));
		pBar.setBorder(new LineBorder(new Color(0, 51, 102)));
		pBar.setFont(new Font("Arial", Font.BOLD, 14));

		theScreen.pack();
		theScreen.setLocation(
			Opcat2.getFrame().getWidth() / 2 - theScreen.getSize().width / 2,
			Opcat2.getFrame().getHeight() / 2 - theScreen.getSize().height / 2);
	}
	
	public void hideWaitMessage() {
		if (myOpcat.getWaitScreen() == null) {
			return;
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myOpcat.getWaitScreen().dispose();
			}
		});

	}
	
	public void showMetaWaitMessage(String message, JProgressBar pBar) {
		myOpcat.setMetaWaitScreen(new JWindow(Opcat2.getFrame()));
		handleWaitScreen(myOpcat.getMetaWaitScreen(), message, pBar);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				myOpcat.getMetaWaitScreen().setVisible(true);
			}
		});
	}

	public void hideMetaWaitMessage() {
			if (myOpcat.getMetaWaitScreen() == null) {
				return;
			}

			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					myOpcat.getMetaWaitScreen().dispose();
				}
			});

		}

	public JFrame getFrame()	{
		return Opcat2.getFrame();
	}
	
	public JDesktopPane getDesktop()	{
	    return myOpcat.getDesktop();
	}
}
