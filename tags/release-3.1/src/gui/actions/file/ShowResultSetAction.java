package gui.actions.file;

import gui.controls.FileControl;
import java.awt.event.ActionEvent;
import javax.swing.Icon;

public class ShowResultSetAction extends FileAction {
    /**
         * 
         */
    private static final long serialVersionUID = 1L;

    /**
         * 
         */

    public ShowResultSetAction(String name, Icon icon) {
	super(name, icon);
    }

    public void actionPerformed(ActionEvent arg0) {
	if (!this.handleWhetherOpenProject()) {
	    return;
	}
	FileControl.getInstance().dbDemo();
    }

}
