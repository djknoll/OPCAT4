package gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Opens an Access database, in ordre to provide backwards compatability for
 * legacy project representations.
 * 
 * @author Eran Toch
 * @see gui.controls.FileControl#selectDataBase()
 */
public class SelectDataBaseAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public SelectDataBaseAction(String name, Icon icon) {
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.fileControl.selectDataBase();

	}

}
