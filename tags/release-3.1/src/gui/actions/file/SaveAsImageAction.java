package gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Saves the current OPD as a JPG image.
 * 
 * @author Eran Toch
 * @see gui.controls.FileControl#saveAsImage()
 */
public class SaveAsImageAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public SaveAsImageAction(String name, Icon icon) {
		super(name, icon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (!this.handleWhetherOpenProject()) {
			return;
		}
		this.fileControl.saveAsImage();

	}

}
