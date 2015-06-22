package gui.actions.file;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Loads an OPX file from the <code>examples</code> folder.
 * 
 * @author Eran Toch
 */
public class LoadExampleAction extends FileAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	 
	private String actionCommand = "";

	public LoadExampleAction(String name, Icon icon) {
		super(name, icon);
		// TODO Auto-generated constructor stub
	}

	public LoadExampleAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		this.actionCommand = arg0.getActionCommand();
		if (!this.fileControl.handleOpenedSystem()) {
			return;
		}
		Thread runner = new Thread() {
			public void run() {
				LoadExampleAction.this.fileControl.loadExample(LoadExampleAction.this.actionCommand);
			}
		};

		runner.start();

	}
}
