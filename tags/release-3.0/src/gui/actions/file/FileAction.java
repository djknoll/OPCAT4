package gui.actions.file;

import gui.controls.FileControl;
import gui.controls.GuiControl;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * A superclass for file-related actions of Opcat. All operations that access
 * the file system can inherit from this class, and have access to file
 * operations.
 * 
 * @author Eran Toch
 */
public abstract class FileAction extends AbstractAction {
    /**
         * A singleton class handling file operation.
         */
    protected FileControl fileControl = FileControl.getInstance();

    /**
         * A singleton class handling GUI in Opcat.
         */
    protected GuiControl myGuiControl = GuiControl.getInstance();

    /**
         * A general constructor, initiates a name and an icon.
         * 
         * @param name
         *                The name of the action (e.g. "Save")
         * @param icon
         *                The icon of the action (presented by menus, toolbars
         *                etc)
         */
    public FileAction(String name, Icon icon) {
	super(name, icon);
    }

    /**
         * A general constructor, takes a name, but not an icon.
         * 
         * @param name
         */
    public FileAction(String name) {
	super(name);
    }

    /**
         * A simple constructor - does not take a name.
         */
    public FileAction() {
	super();
    }

    /**
         * Returns whether a project is currently open, and present a message to
         * the user if there is no opened project.
         * 
         * @return <code>true</code> if there is an open project, and
         *         <code>false</code> otherwise.
         */
    protected boolean handleWhetherOpenProject() {
	if (this.fileControl.isProjectOpened()) {
	    return true;
	} else {
	    JOptionPane
		    .showMessageDialog(this.myGuiControl.getFrame(),
			    "No system is opened", "Message",
			    JOptionPane.ERROR_MESSAGE);
	    return false;
	}

    }

}
