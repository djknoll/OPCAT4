package gui.actions.edit;

import java.awt.event.ActionEvent;

import javax.swing.Icon;

/**
 * Enables and disables the merge dragging option.
 * @author Eran Toch
 */
public class MergeDraggingAction extends EditAction {

    public MergeDraggingAction(String name, Icon icon) {
        super(name, icon);
    }

    public MergeDraggingAction() {
        super();
    }

    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent arg0) {
        super.actionPerformed(arg0);
        if (edit.getCurrentProject() == null)	{
            return;
        }
		if (!edit.getCurrentProject().getEnableDragging()) {
		    edit.getCurrentProject().setEnableDragging(true);
			edit.enableDragging(true);
		} else {
		    edit.getCurrentProject().setEnableDragging(false);
			edit.enableDragging(false);
		}

    }

}
