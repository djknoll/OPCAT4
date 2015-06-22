package gui.metaLibraries.dialogs;

import gui.metaLibraries.logic.MetaManager;
import gui.opdProject.OpdProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The window enable users to configure meta-libraries if there were any errors
 * while loading the libraries. Users can change the path of the library or to 
 * remove it from the list.
 * @author Eran Toch
 *
 */
public class LibrariesLoadingWindow extends JDialog {
	private MetaManager metaManager = null;
	private JButton okButton = new JButton();
	private JButton cancelButton = new JButton();
	private LibrariesFix libsFix = null;
	private boolean okPressed = false;
	private Vector updatedLibs = null;
	private OpdProject currentProject = null; 
	private JFrame parentFrame = null;
	
	public LibrariesLoadingWindow(MetaManager meta, OpdProject _project, JFrame parent) {
		super(parent, "Meta-Libraries Configuration", true);
		metaManager = meta;
		currentProject = _project;
		parentFrame = parent;
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initialize() throws Exception {
		okButton.setText("OK");
		okButton.setBounds(new Rectangle(235, 278, 96, 28));
		okButton.addActionListener(new okAdapter(this));
		cancelButton.setBounds(new Rectangle(343, 278, 96, 28));
	    cancelButton.setText("Cancel");
	    cancelButton.addActionListener(new cancelAdapter(this));
		libsFix = new LibrariesFix(metaManager, currentProject, parentFrame);
		JPanel libsFrame = new JPanel();
		libsFrame.setBorder(BorderFactory.createLineBorder (Color.LIGHT_GRAY, 2));
		libsFrame.setBounds(new Rectangle(20,13,440,250));
		libsFrame.setLayout(new BorderLayout());
		libsFrame.add(libsFix, BorderLayout.CENTER);
		this.getContentPane().setLayout(null);
		this.getContentPane().add(libsFrame, null);
		this.getContentPane().add(okButton, null);
		this.getContentPane().add(cancelButton, null);
		this.setBounds(0, 0, 480, 360);
	    this.setLocationRelativeTo(this.getParent());
		
	}
	
	/**
	 * Handles OK - Set the new libraries vector and disposes the window.
	 */
	public void doOkay()	{
		okPressed = true;
		updatedLibs = libsFix.getMetaManager().getVectorClone();
		this.dispose();	
	}
	
	/**
	 * handles Cancel - disposes the window.
	 */
	public void doCancel()	{
		okPressed = false;
		this.dispose();	
	}
	
	public boolean showDialog()	{
		this.setVisible(true);
		return okPressed;
	}
	
	/**
	 * Returns a Vector of the updated meta-libraries (after the changes that the 
	 * user has done).
	 * @return
	 */
	public Vector getUpdatedLibs()	{
		return updatedLibs;
	}
}

class okAdapter implements java.awt.event.ActionListener {
	private LibrariesLoadingWindow adaptee;

	okAdapter(LibrariesLoadingWindow adaptee) {
		this.adaptee = adaptee;
	}
	
	  public void actionPerformed(ActionEvent e) {
	    adaptee.doOkay();
	  }
}

class cancelAdapter implements java.awt.event.ActionListener {
	private LibrariesLoadingWindow adaptee;

	cancelAdapter(LibrariesLoadingWindow adaptee) {
		this.adaptee = adaptee;
	}
	
	  public void actionPerformed(ActionEvent e) {
	    adaptee.doCancel();
	  }
}