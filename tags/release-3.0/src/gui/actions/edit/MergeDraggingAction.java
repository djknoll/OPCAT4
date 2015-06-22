package gui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Enables and disables the merge dragging option.
 * 
 * @author Eran Toch
 */
public class MergeDraggingAction extends EditAction {
	 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	 

	public MergeDraggingAction(String name, Icon icon) {
		super(name, icon);
	}

	public MergeDraggingAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
		super.actionPerformed(arg0);
		if (this.edit.getCurrentProject() == null) {
			return;
		}
		if (!this.edit.getCurrentProject().getEnableDragging()) {
			this.edit.getCurrentProject().setEnableDragging(true);
			this.edit.enableDragging(true);
		} else {
			this.edit.getCurrentProject().setEnableDragging(false);
			this.edit.enableDragging(false);
		}

	}

}
