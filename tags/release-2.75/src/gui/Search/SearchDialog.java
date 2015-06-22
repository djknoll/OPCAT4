package gui.Search;
	
import gui.Opcat2;
import gui.opdGraphics.GraphicsUtils;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class is a dialog box for searching an OP tree, the user may input 
 * several perematers like string to be searched, ......
 * @author Raanan Manor
 */
public class SearchDialog extends JDialog implements ComponentListener {
	private OpdProject opd ; 
	
	public SearchDialog(OpdProject project)
	{
		super(Opcat2.getFrame(), "Search", true);
		this.addComponentListener(this);
		//remove the project from this and put into the real search class
		opd = project; 
	}
	
	public void ShowDialog() {
		this.setVisible(true); 
	}
	
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	public void componentResized(ComponentEvent e){}

}
